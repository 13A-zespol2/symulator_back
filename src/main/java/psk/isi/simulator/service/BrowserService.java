package psk.isi.simulator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import psk.isi.simulator.errors.NoSuchPhoneNumber;
import psk.isi.simulator.model.database.entities.NumberBalance;
import psk.isi.simulator.model.database.entities.PhoneNumber;
import psk.isi.simulator.model.database.repository.NumberBalanceRepository;
import psk.isi.simulator.model.database.repository.PhoneNumberRepository;
import psk.isi.simulator.model.transport.dto.BrowserDto;

import java.util.Optional;

@Service
public class BrowserService {


    private NumberBalanceRepository numberBalanceRepository;
    private PhoneNumberRepository phoneNumberRepository;

    @Autowired
    public BrowserService(NumberBalanceRepository numberBalanceRepository, PhoneNumberRepository phoneNumberRepository) {
        this.numberBalanceRepository = numberBalanceRepository;
        this.phoneNumberRepository = phoneNumberRepository;
    }
    private Optional<PhoneNumber> findPhoneNumber(String phoneNumberString) {
        return phoneNumberRepository.findByNumber(phoneNumberString);
    }

    public void saveBrowsing(BrowserDto browserDto) throws NoSuchPhoneNumber {

        PhoneNumber phoneNumber = findPhoneNumber(browserDto.getPhoneNumber()).
                orElseThrow(() -> new NoSuchPhoneNumber("No such phone number " + browserDto.getPhoneNumber()));

        NumberBalance byPhoneNumber = numberBalanceRepository.findByPhoneNumber(phoneNumber);
        Double balanceInternet = byPhoneNumber.getBalanceInternet();
        Double timeSpent = (browserDto.getTime())/10;

        byPhoneNumber.setBalanceInternet(balanceInternet - timeSpent);
        numberBalanceRepository.save(byPhoneNumber);
    }
}
