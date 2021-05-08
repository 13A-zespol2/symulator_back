package psk.isi.simulator.model.database.repository;

import psk.isi.simulator.model.database.entities.NumberBalance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NumberBalanceRepository extends JpaRepository<NumberBalance, Long> {
}
