package psk.isi.simulator.model.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import psk.isi.simulator.model.database.entities.PhoneHistory;

public interface PhoneHistoryRepository extends JpaRepository<PhoneHistory, Long> {

}
