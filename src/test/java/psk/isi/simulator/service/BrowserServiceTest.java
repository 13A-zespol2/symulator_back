package psk.isi.simulator.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import psk.isi.simulator.errors.NoInternetBalance;
import psk.isi.simulator.errors.NoSuchPhoneNumber;
import psk.isi.simulator.model.database.entities.NumberBalance;
import psk.isi.simulator.model.database.entities.PhoneNumber;
import psk.isi.simulator.model.database.repository.NumberBalanceRepository;
import psk.isi.simulator.model.database.repository.PhoneNumberRepository;
import psk.isi.simulator.model.transport.dto.BrowserDto;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


class BrowserServiceTest {

    private NumberBalanceRepository numberBalanceRepository;
    private PhoneNumberRepository phoneNumberRepository;
    private BrowserService browserService;

    @BeforeEach
    void setup(){
        System.out.println("setup");
        numberBalanceRepository = Mockito.mock(NumberBalanceRepository.class);
        phoneNumberRepository = Mockito.mock(PhoneNumberRepository.class);
        this.browserService = new BrowserService(numberBalanceRepository, phoneNumberRepository);
    }

    @Test
    public void ifSavingBrowsing() throws NoSuchPhoneNumber, NoInternetBalance {
        //given
        PhoneNumber phoneNumber = new PhoneNumber("123123123", null);
        NumberBalance numberBalance = new NumberBalance(null ,phoneNumber, 30D, 30D,30D, 30);
        Mockito.when(phoneNumberRepository.findByNumber(phoneNumber.getNumber())).thenReturn(Optional.of(phoneNumber));
        Mockito.when(numberBalanceRepository.findByPhoneNumber(phoneNumber)).thenReturn(numberBalance);
        BrowserDto browserDto = new BrowserDto(phoneNumber.getNumber(), 1D);
        //when
        browserService.saveBrowsing(browserDto);

        //then
        NumberBalance expectedBalance = new NumberBalance(null, phoneNumber,30D, 29.9, 30D, 30 );
        Mockito.verify(numberBalanceRepository, Mockito.times(1)).save(Mockito.eq(expectedBalance));



    }
}