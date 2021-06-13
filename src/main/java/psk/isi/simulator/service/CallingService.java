package psk.isi.simulator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import psk.isi.simulator.errors.NoInternetBalance;
import psk.isi.simulator.errors.NoMinutesBalance;
import psk.isi.simulator.errors.NoSuchPhoneNumber;
import psk.isi.simulator.model.database.entities.NumberBalance;
import psk.isi.simulator.model.database.entities.PhoneNumber;
import psk.isi.simulator.model.database.repository.NumberBalanceRepository;
import psk.isi.simulator.model.database.repository.PhoneNumberRepository;
import psk.isi.simulator.model.transport.dto.BrowserDto;
import psk.isi.simulator.model.transport.dto.CallDto;

import java.util.Optional;

@Service
public class CallingService {

    private final NumberBalanceRepository numberBalanceRepository;
    private final PhoneNumberRepository phoneNumberRepository;

    @Autowired
    public CallingService(NumberBalanceRepository numberBalanceRepository, PhoneNumberRepository phoneNumberRepository) {
        this.numberBalanceRepository = numberBalanceRepository;
        this.phoneNumberRepository = phoneNumberRepository;
    }

    private Optional<PhoneNumber> findPhoneNumber(String phoneNumberString) {
        return phoneNumberRepository.findByNumber(phoneNumberString);
    }

    public void saveCalling(CallDto callDto) throws NoSuchPhoneNumber, NoMinutesBalance {

        PhoneNumber phoneNumber = findPhoneNumber(callDto.getPhoneNumber()).
                orElseThrow(() -> new NoSuchPhoneNumber("No such phone number " + callDto.getPhoneNumber()));

        NumberBalance byPhoneNumber = numberBalanceRepository.findByPhoneNumber(phoneNumber);
        Double balanceMinutes = byPhoneNumber.getBalanceMinutes();
        if (balanceMinutes < 0) {
            throw new NoMinutesBalance("Not enough internet balance for phone number" + phoneNumber.getNumber());
        }
        Double timeSpent = Math.ceil((callDto.getSeconds())/60.0);

        byPhoneNumber.setBalanceMinutes(balanceMinutes - timeSpent);
        numberBalanceRepository.save(byPhoneNumber);
    }
}
