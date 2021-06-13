package psk.isi.simulator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import psk.isi.simulator.errors.NoSmsBalance;
import psk.isi.simulator.errors.NoSuchPhoneNumber;
import psk.isi.simulator.model.database.entities.NumberBalance;
import psk.isi.simulator.model.database.entities.PhoneNumber;
import psk.isi.simulator.model.database.entities.SmsHistory;
import psk.isi.simulator.model.database.repository.NumberBalanceRepository;
import psk.isi.simulator.model.database.repository.PhoneNumberRepository;
import psk.isi.simulator.model.database.repository.SmsHistoryRepository;
import psk.isi.simulator.model.transport.converter.SmsHistoryConverter;
import psk.isi.simulator.model.transport.dto.SmsHistoryDTO;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class MessageService {
    private final SmsHistoryRepository smsHistoryRepository;
    private final PhoneNumberRepository phoneNumberRepository;
    private final NumberBalanceRepository numberBalanceRepository;

    @Autowired
    public MessageService(SmsHistoryRepository smsHistoryRepository, PhoneNumberRepository phoneNumberRepository, NumberBalanceRepository numberBalanceRepository) {
        this.smsHistoryRepository = smsHistoryRepository;
        this.phoneNumberRepository = phoneNumberRepository;
        this.numberBalanceRepository = numberBalanceRepository;
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    private Optional<PhoneNumber> findPhoneNumber(String phoneNumberString) {
        return phoneNumberRepository.findByNumber(phoneNumberString);
    }

    private void subSmsFromBalance(PhoneNumber phoneNumber) throws NoSmsBalance {
        NumberBalance byPhoneNumber = numberBalanceRepository.findByPhoneNumber(phoneNumber);
        int smsBalance = byPhoneNumber.getSmsBalance();
        if (smsBalance < 0) {
            throw new NoSmsBalance("not enough balance sms for phone number" + phoneNumber.getNumber());
        }
        byPhoneNumber.setSmsBalance(smsBalance - 1);
        numberBalanceRepository.save(byPhoneNumber);
    }

    public SmsHistory saveMessage(SmsHistoryDTO smsToSend) throws NoSuchPhoneNumber, NoSmsBalance {
        PhoneNumber phoneNumber = findPhoneNumber(smsToSend.getPhoneNumberReceiver()).
                orElseThrow(() -> new NoSuchPhoneNumber("No such phone number " + smsToSend.getPhoneNumberReceiver()));

        PhoneNumber phoneNumberSender = findPhoneNumber(smsToSend.getPhoneNumberSender().getNumber())
                .orElseThrow(() -> new NoSuchPhoneNumber("No such phone number " + smsToSend.getPhoneNumberReceiver()));

        smsToSend.setDateSms(new Date());

        SmsHistory smsHistory = SmsHistoryConverter.toEntity(smsToSend, phoneNumber);
        smsHistory.setPhoneNumberSender(phoneNumberSender);
        subSmsFromBalance(phoneNumberSender);
        return smsHistoryRepository.save(smsHistory);
    }

    public List<SmsHistoryDTO> findLastSms(String phoneNumber) throws NoSuchPhoneNumber {
        PhoneNumber byNumber = phoneNumberRepository.findByNumber(phoneNumber).orElseThrow(() -> new NoSuchPhoneNumber("No such phone number " + phoneNumber));
        List<SmsHistory> collect = smsHistoryRepository.findAllByNumberQuery(byNumber);

        List<SmsHistory> collect1 = collect.stream()
                .sorted(Comparator.comparing(SmsHistory::getDateSms).reversed())
                .filter(distinctByKey(SmsHistory::getPhoneNumberReceiver))
                .filter(distinctByKey(SmsHistory::getPhoneNumberSender))
                .collect(Collectors.toList());

        List<SmsHistoryDTO> smsHistoryDTOS = new ArrayList<>();
        for (SmsHistory el : collect1) {
            if (!el.getPhoneNumberSender().equals(byNumber)) {
                PhoneNumber phoneNumberReceiver = el.getPhoneNumberReceiver();
                el.setPhoneNumberReceiver(el.getPhoneNumberSender());
                el.setPhoneNumberSender(phoneNumberReceiver);
            }
            smsHistoryDTOS.add(SmsHistoryConverter.toDto(el));
        }
        return smsHistoryDTOS;
    }

    public List<SmsHistoryDTO> smsHistoryDTOList(String phoneNumberSenderString, String phoneNumberReceiverString) {

        Optional<PhoneNumber> optionalPhoneNumberSender = phoneNumberRepository.findByNumber(phoneNumberSenderString);
        if (optionalPhoneNumberSender.isPresent()) {
            PhoneNumber phoneNumberSender = optionalPhoneNumberSender.get();
            return smsHistoryRepository.findAllByNumberQuery(phoneNumberSender).stream().map(SmsHistoryConverter::toDto).collect(Collectors.toList());
        }

        return null;
    }


}
