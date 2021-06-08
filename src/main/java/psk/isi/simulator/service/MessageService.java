package psk.isi.simulator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import psk.isi.simulator.errors.NoSuchPhoneNumber;
import psk.isi.simulator.model.database.entities.PhoneNumber;
import psk.isi.simulator.model.database.entities.SmsHistory;
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

    @Autowired
    public MessageService(SmsHistoryRepository smsHistoryRepository, PhoneNumberRepository phoneNumberRepository) {
        this.smsHistoryRepository = smsHistoryRepository;
        this.phoneNumberRepository = phoneNumberRepository;
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    private Optional<PhoneNumber> findPhoneNumber(String phoneNumberString) {
        return phoneNumberRepository.findByNumber(phoneNumberString);
    }

    public SmsHistory saveMessage(SmsHistoryDTO smsToSend) throws NoSuchPhoneNumber {
        PhoneNumber phoneNumber = findPhoneNumber(smsToSend.getPhoneNumberReceiver()).
                orElseThrow(() -> new NoSuchPhoneNumber("No such phone number " + smsToSend.getPhoneNumberReceiver()));

        PhoneNumber phoneNumberSender = findPhoneNumber(smsToSend.getPhoneNumberSender().getNumber())
                .orElseThrow(() -> new NoSuchPhoneNumber("No such phone number " + smsToSend.getPhoneNumberReceiver()));

        smsToSend.setDateSms(new Date());

        SmsHistory smsHistory = SmsHistoryConverter.toEntity(smsToSend, phoneNumber);
        smsHistory.setPhoneNumberSender(phoneNumberSender);

        return smsHistoryRepository.save(smsHistory);
    }

    public List<SmsHistoryDTO> findLastSms(String phoneNumber) throws NoSuchPhoneNumber {
        PhoneNumber byNumber = phoneNumberRepository.findByNumber(phoneNumber).orElseThrow(() -> new NoSuchPhoneNumber("No such phone number " + phoneNumber));
        List<SmsHistory> collect = smsHistoryRepository.findAllByPhoneNumberSender(byNumber);

        return collect.stream()
                .sorted(Comparator.comparing(SmsHistory::getDateSms).reversed())
                .filter(distinctByKey(SmsHistory::getPhoneNumberReceiver))
                .map(SmsHistoryConverter::toDto).collect(Collectors.toList());
    }


}
