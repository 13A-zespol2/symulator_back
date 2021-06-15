package psk.isi.simulator.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import psk.isi.simulator.errors.NoMinutesBalance;
import psk.isi.simulator.errors.NoSuchPhoneNumber;
import psk.isi.simulator.model.database.entities.NumberBalance;
import psk.isi.simulator.model.database.entities.PhoneNumber;
import psk.isi.simulator.model.database.repository.NumberBalanceRepository;
import psk.isi.simulator.model.database.repository.PhoneNumberRepository;
import psk.isi.simulator.model.transport.dto.CallDto;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


class CallingServiceTest {

    private NumberBalanceRepository numberBalanceRepository;
    private PhoneNumberRepository phoneNumberRepository;
    private CallingService callingService;

    @BeforeEach
    void setup(){
        System.out.println("setup");
        numberBalanceRepository = Mockito.mock(NumberBalanceRepository.class);
        phoneNumberRepository = Mockito.mock(PhoneNumberRepository.class);
        this.callingService = new CallingService(numberBalanceRepository, phoneNumberRepository);
    }

    @Test
    public void ifSavingCalling() throws NoSuchPhoneNumber, NoMinutesBalance {
        //given
        PhoneNumber phoneNumber = new PhoneNumber("203840358", null);
        NumberBalance numberBalance = new NumberBalance(phoneNumber, 30D, 30D, 30D, 30);
        Mockito.when(phoneNumberRepository.findByNumber(phoneNumber.getNumber())).thenReturn(Optional.of(phoneNumber));
        Mockito.when(numberBalanceRepository.findByPhoneNumber(phoneNumber)).thenReturn(numberBalance);
        CallDto callDto = new CallDto(phoneNumber.getNumber(), 15D);
        //when
        callingService.saveCalling(callDto);
        //then

        NumberBalance expectedBalance = new NumberBalance(phoneNumber, 30D, 30D, 29D, 30);
        Mockito.verify(numberBalanceRepository, Mockito.times(1)).save(Mockito.eq(expectedBalance));



    }


}