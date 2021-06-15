package psk.isi.simulator.model.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import psk.isi.simulator.model.database.entities.PhoneHistory;
/**
 * Interfejs odpowiadający za dostęp do tabeli ,,phone_history" w bazie danych.
 */
@Repository
public interface PhoneHistoryRepository extends JpaRepository<PhoneHistory, Long> {

}
