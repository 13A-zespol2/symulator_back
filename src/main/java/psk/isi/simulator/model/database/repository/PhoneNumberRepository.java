package psk.isi.simulator.model.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import psk.isi.simulator.model.database.entities.PhoneNumber;

import java.util.Optional;
/**
 * Interfejs odpowiadający za dostęp do tabeli ,,phone_number" w bazie danych.
 */
@Repository
public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, Long> {
    Optional<PhoneNumber> findByNumber(String phoneNumber);
}
