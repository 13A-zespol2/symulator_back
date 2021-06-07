package psk.isi.simulator.model.database.repository;

import psk.isi.simulator.model.database.entities.PhoneNumber;
import psk.isi.simulator.model.database.entities.SmsHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface SmsHistoryRepository extends JpaRepository<SmsHistory, Long> {

    List<SmsHistory> findAllByPhoneNumberSender(PhoneNumber phoneNumber);

}
