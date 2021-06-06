package psk.isi.simulator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import psk.isi.simulator.model.database.entities.SmsHistory;
import psk.isi.simulator.model.database.repository.SmsHistoryRepository;

import java.util.Optional;

@Service
public class SendMessageService {
    private final SmsHistoryRepository smsHistoryRepository;

    @Autowired
    public SendMessageService(SmsHistoryRepository smsHistoryRepository) {
        this.smsHistoryRepository = smsHistoryRepository;
    }

        public boolean saveMessage(SmsHistory smsToSend){


        return true;
    }

}
