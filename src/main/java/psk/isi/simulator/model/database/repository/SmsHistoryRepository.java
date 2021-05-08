package psk.isi.simulator.model.database.repository;

import psk.isi.simulator.model.database.entities.SmsHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmsHistoryRepository extends JpaRepository<SmsHistory, Long> {
}
