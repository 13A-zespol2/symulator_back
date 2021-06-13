package psk.isi.simulator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import psk.isi.simulator.model.database.entities.NumberBalance;
import psk.isi.simulator.model.database.repository.NumberBalanceRepository;
import psk.isi.simulator.model.database.repository.PhoneNumberRepository;
import psk.isi.simulator.model.transport.dto.BrowserDto;

@Service
public class BrowserService {


    private NumberBalanceRepository numberBalanceRepository;
    private PhoneNumberRepository phoneNumberRepository;

    @Autowired
    public BrowserService(NumberBalanceRepository numberBalanceRepository, PhoneNumberRepository phoneNumberRepository) {
        this.numberBalanceRepository = numberBalanceRepository;
        this.phoneNumberRepository = phoneNumberRepository;
    }


    public NumberBalance saveBrowsing(BrowserDto browserDto){


        return null;
    }
}
