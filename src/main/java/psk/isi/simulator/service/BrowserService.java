package psk.isi.simulator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import psk.isi.simulator.errors.NoInternetBalance;
import psk.isi.simulator.errors.NoSuchPhoneNumber;
import psk.isi.simulator.model.database.entities.NumberBalance;
import psk.isi.simulator.model.database.entities.PhoneNumber;
import psk.isi.simulator.model.database.repository.NumberBalanceRepository;
import psk.isi.simulator.model.database.repository.PhoneNumberRepository;
import psk.isi.simulator.model.transport.dto.BrowserDto;

import java.util.Optional;
/**
 * Klasa zarządzająca przegladarka.
 */
@Service
public class BrowserService {


    private final NumberBalanceRepository numberBalanceRepository;
    private final PhoneNumberRepository phoneNumberRepository;

    @Autowired
    public BrowserService(NumberBalanceRepository numberBalanceRepository, PhoneNumberRepository phoneNumberRepository) {
        this.numberBalanceRepository = numberBalanceRepository;
        this.phoneNumberRepository = phoneNumberRepository;
    }
    private Optional<PhoneNumber> findPhoneNumber(String phoneNumberString) {
        return phoneNumberRepository.findByNumber(phoneNumberString);
    }

    /**
     * Metoda odpowiedzialna za przeliczenie czasu spedzonego na przegladarce na pakiet Internetu i aktualizowanie jego stanu.
     * @param browserDto
     * @throws NoSuchPhoneNumber
     * @throws NoInternetBalance
     */
    public void saveBrowsing(BrowserDto browserDto) throws NoSuchPhoneNumber, NoInternetBalance {

        PhoneNumber phoneNumber = findPhoneNumber(browserDto.getPhoneNumber()).
                orElseThrow(() -> new NoSuchPhoneNumber("No such phone number " + browserDto.getPhoneNumber()));

        NumberBalance byPhoneNumber = numberBalanceRepository.findByPhoneNumber(phoneNumber);
        Double balanceInternet = byPhoneNumber.getBalanceInternet();
        if (balanceInternet < 0) {
            throw new NoInternetBalance("Not enough internet balance for phone number" + phoneNumber.getNumber());
        }
        Double timeSpent = Math.ceil(browserDto.getTime())/10;

        byPhoneNumber.setBalanceInternet(balanceInternet - timeSpent);
        numberBalanceRepository.save(byPhoneNumber);
    }

    /**
     * Metoda sprawdzajaca stan pakietu Internet dla danego numeru telefonu.
     * @param phoneNumber
     * @return
     */
    public double takeInternet(String phoneNumber) {
        Optional<PhoneNumber> byNumber = phoneNumberRepository.findByNumber(phoneNumber);
        if (byNumber.isEmpty()) {
            return 0;
        }
        PhoneNumber phoneNumber1 = byNumber.get();
        return numberBalanceRepository.findByPhoneNumber(phoneNumber1).getBalanceInternet();
    }
}
