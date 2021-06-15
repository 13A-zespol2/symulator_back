package psk.isi.simulator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import psk.isi.simulator.errors.NoMinutesBalance;
import psk.isi.simulator.errors.NoSuchPhoneNumber;
import psk.isi.simulator.model.database.entities.NumberBalance;
import psk.isi.simulator.model.database.entities.PhoneNumber;
import psk.isi.simulator.model.database.repository.NumberBalanceRepository;
import psk.isi.simulator.model.database.repository.PhoneNumberRepository;
import psk.isi.simulator.model.transport.dto.CallDto;

import java.util.Optional;


/**
 * Klasa zarządzająca polaczeniami telefonicznymi.
 */
@Service
public class CallingService {

    private final NumberBalanceRepository numberBalanceRepository;
    private final PhoneNumberRepository phoneNumberRepository;

    @Autowired
    public CallingService(NumberBalanceRepository numberBalanceRepository, PhoneNumberRepository phoneNumberRepository) {
        this.numberBalanceRepository = numberBalanceRepository;
        this.phoneNumberRepository = phoneNumberRepository;
    }

    /**
     * Metoda znajdujaca numer telefonu w bazie danych.
     * @param phoneNumberString
     * @return
     */
    private Optional<PhoneNumber> findPhoneNumber(String phoneNumberString) {
        return phoneNumberRepository.findByNumber(phoneNumberString);
    }

    /**
     * Metoda odpowiedzialna za przeliczenie czasu spedzonego na polaczeniu na pakiet minut i aktualizowanie jego stanu.
     * @param callDto
     * @throws NoSuchPhoneNumber
     * @throws NoMinutesBalance
     */
    public void saveCalling(CallDto callDto) throws NoSuchPhoneNumber, NoMinutesBalance {

        PhoneNumber phoneNumber = findPhoneNumber(callDto.getPhoneNumber()).
                orElseThrow(() -> new NoSuchPhoneNumber("No such phone number " + callDto.getPhoneNumber()));

        NumberBalance byPhoneNumber = numberBalanceRepository.findByPhoneNumber(phoneNumber);
        Double balanceMinutes = byPhoneNumber.getBalanceMinutes();
        if (balanceMinutes < 0) {
            throw new NoMinutesBalance("Not enough minutes balance for phone number" + phoneNumber.getNumber());
        }
        Double timeSpent = Math.ceil((callDto.getSeconds()) / 60.0);

        byPhoneNumber.setBalanceMinutes(balanceMinutes - timeSpent);
        numberBalanceRepository.save(byPhoneNumber);
    }

    /**
     * Metoda sprawdzajaca stan pakietu minut dla danego numeru telefonu.
     * @param phoneNumber
     * @return
     */
    public double takeMinutes(String phoneNumber) {
        Optional<PhoneNumber> byNumber = phoneNumberRepository.findByNumber(phoneNumber);
        if (byNumber.isEmpty()) {
            return 0;
        }
        PhoneNumber phoneNumber1 = byNumber.get();
        return numberBalanceRepository.findByPhoneNumber(phoneNumber1).getBalanceMinutes();
    }
}
