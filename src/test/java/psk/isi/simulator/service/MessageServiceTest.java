package psk.isi.simulator.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import psk.isi.simulator.errors.NoSmsBalance;
import psk.isi.simulator.errors.NoSuchPhoneNumber;
import psk.isi.simulator.model.database.entities.NumberBalance;
import psk.isi.simulator.model.database.entities.PhoneNumber;
import psk.isi.simulator.model.database.entities.SmsHistory;
import psk.isi.simulator.model.database.repository.NumberBalanceRepository;
import psk.isi.simulator.model.database.repository.PhoneNumberRepository;
import psk.isi.simulator.model.database.repository.SmsHistoryRepository;
import psk.isi.simulator.model.transport.converter.PhoneNumberConverter;
import psk.isi.simulator.model.transport.converter.SmsHistoryConverter;
import psk.isi.simulator.model.transport.dto.PhoneNumberDto;
import psk.isi.simulator.model.transport.dto.SmsHistoryDTO;


import java.util.Date;
import java.util.Optional;


class MessageServiceTest {

    private SmsHistoryRepository smsHistoryRepository;
    private PhoneNumberRepository phoneNumberRepository;
    private NumberBalanceRepository numberBalanceRepository;
    private MessageService messageService;

    @BeforeEach
    void setUp(){
        System.out.println("setup");
        smsHistoryRepository = Mockito.mock(SmsHistoryRepository.class);
        phoneNumberRepository = Mockito.mock(PhoneNumberRepository.class);
        numberBalanceRepository = Mockito.mock(NumberBalanceRepository.class);
        this.messageService = new MessageService(smsHistoryRepository, phoneNumberRepository,numberBalanceRepository);

    }

    @Test
    public void ifSubSmsFromBalance() throws NoSmsBalance {
        //given
        PhoneNumber phoneNumber = new PhoneNumber("123456789", null);
        NumberBalance numberBalance = new NumberBalance(phoneNumber, 30D, 30D, 30D ,30);
        Mockito.when(numberBalanceRepository.findByPhoneNumber(phoneNumber)).thenReturn(numberBalance);

        //when
        //messageService.subSmsFromBalance(phoneNumber);
        //then
        int expectedSmsBalance = 29;
        NumberBalance expectedNumberBalance =  new NumberBalance(phoneNumber, 30D, 30D,  30D,expectedSmsBalance);
        Mockito.verify(numberBalanceRepository, Mockito.times(1)).save(Mockito.eq(expectedNumberBalance));
    }

    @Test
    public void ifSavingMessage() throws NoSuchPhoneNumber, NoSmsBalance {
        //given
        PhoneNumber phoneNumberReceiver = new PhoneNumber("111111111", "123");
        PhoneNumber phoneNumberSender = new PhoneNumber("222222222", "123");
        Date date = new Date();
        Mockito.when(phoneNumberRepository.findByNumber(phoneNumberReceiver.getNumber())).thenReturn(Optional.of(phoneNumberReceiver));
        Mockito.when(phoneNumberRepository.findByNumber(phoneNumberSender.getNumber())).thenReturn(Optional.of(phoneNumberSender));
        PhoneNumberDto phoneNumberSenderDto = PhoneNumberConverter.toDto(phoneNumberSender);
        SmsHistoryDTO smsHistoryDTO = new SmsHistoryDTO(phoneNumberSenderDto, phoneNumberReceiver.getNumber(), "", date);
        NumberBalance numberBalance = new NumberBalance(phoneNumberSender, 30D, 30D, 30D ,30);
        Mockito.when(numberBalanceRepository.findByPhoneNumber(phoneNumberSender)).thenReturn(numberBalance);


        //when
        messageService.saveMessage(smsHistoryDTO);
        //then
        int expectedSmsBalance = 29;
        NumberBalance expectedNumberBalance =  new NumberBalance(phoneNumberSender, 30D, 30D,  30D,expectedSmsBalance);
        Mockito.verify(numberBalanceRepository, Mockito.times(1)).save(Mockito.eq(expectedNumberBalance));

        SmsHistory expectedSms = new SmsHistory(null, phoneNumberSender, phoneNumberReceiver, "", date);
        Mockito.verify(smsHistoryRepository, Mockito.times(1)).save(Mockito.eq(expectedSms));



    }
}