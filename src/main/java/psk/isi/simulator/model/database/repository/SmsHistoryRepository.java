package psk.isi.simulator.model.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import psk.isi.simulator.model.database.entities.PhoneNumber;
import psk.isi.simulator.model.database.entities.SmsHistory;

import java.util.List;

public interface SmsHistoryRepository extends JpaRepository<SmsHistory, Long> {

    List<SmsHistory> findAllByPhoneNumberSender(PhoneNumber phoneNumber);

    @Query("SELECT sh FROM SmsHistory sh WHERE sh.phoneNumberSender = ?1 OR sh.phoneNumberReceiver = ?1")
    List<SmsHistory> findAllByNumberQuery(PhoneNumber phoneNumber);


}
