package psk.isi.simulator.model.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import psk.isi.simulator.model.database.entities.PhoneNumber;
import psk.isi.simulator.model.database.entities.SmsHistory;

import java.util.List;

public interface SmsHistoryRepository extends JpaRepository<SmsHistory, Long> {

    List<SmsHistory> findAllByPhoneNumberSender(PhoneNumber phoneNumber);


    List<SmsHistory> findByPhoneNumberReceiverAndPhoneNumberSender(PhoneNumber receiver, PhoneNumber sender);

}
