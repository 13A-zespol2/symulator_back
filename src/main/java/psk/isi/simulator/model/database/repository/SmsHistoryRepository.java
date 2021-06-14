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


    @Query(value = "SELECT sh.phoneNumberSender FROM SmsHistory sh WHERE sh.phoneNumberReceiver = ?1 OR sh.phoneNumberSender = ?1")
    List<PhoneNumber> findALlContactsSms(PhoneNumber phoneNumber);



    @Query(value = "SELECT * FROM sms_history se WHERE date_sms = (SELECT MAX(sh.date_sms) FROM sms_history sh WHERE sh.phone_number_receiver_id_phone_number = ?2 AND sh.phone_number_sender_id_phone_number = ?1  OR sh.phone_number_receiver_id_phone_number = ?1 AND sh.phone_number_sender_id_phone_number = ?2 ORDER BY sh.date_sms DESC)",nativeQuery = true)
    SmsHistory findALlContactsSms(PhoneNumber phoneNumber,PhoneNumber phoneNumber2);


}
