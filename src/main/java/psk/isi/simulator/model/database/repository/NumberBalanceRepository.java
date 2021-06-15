package psk.isi.simulator.model.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import psk.isi.simulator.model.database.entities.NumberBalance;
import psk.isi.simulator.model.database.entities.PhoneNumber;

/**
 * Interfejs odpowiadający za dostęp do tabeli ,,number_balance" w bazie danych.
 */
public interface NumberBalanceRepository extends JpaRepository<NumberBalance, Long> {

    NumberBalance findByPhoneNumber(PhoneNumber phoneNumber);
}
