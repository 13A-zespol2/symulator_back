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

import java.util.Date;
import java.util.Optional;

@Service
public class SendMessageService {
    private final SmsHistoryRepository smsHistoryRepository;
    private final PhoneNumberRepository phoneNumberRepository;

    @Autowired
    public SendMessageService(SmsHistoryRepository smsHistoryRepository, PhoneNumberRepository phoneNumberRepository) {
        this.smsHistoryRepository = smsHistoryRepository;
        this.phoneNumberRepository = phoneNumberRepository;
    }

    private Optional<PhoneNumber> findPhoneNumberReceiver(String phoneNumberString) {
        return phoneNumberRepository.findByNumber(phoneNumberString);
    }


    public SmsHistory saveMessage(SmsHistoryDTO smsToSend) throws NoSuchPhoneNumber {
        PhoneNumber phoneNumber = findPhoneNumberReceiver(smsToSend.getPhoneNumberReceiver()).
                orElseThrow(() -> new NoSuchPhoneNumber("No such phone number " + smsToSend.getPhoneNumberReceiver()));
        Optional<PhoneNumber> phoneNumberSender = findPhoneNumberReceiver(smsToSend.getPhoneNumberSender().getNumber());
        smsToSend.setDateSms(new Date());

        SmsHistory smsHistory = SmsHistoryConverter.toEntity(smsToSend, phoneNumber);
        smsHistory.setPhoneNumberSender(phoneNumberSender.get());

        return smsHistoryRepository.save(smsHistory);
    }

}
