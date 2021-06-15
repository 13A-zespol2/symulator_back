package psk.isi.simulator.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import psk.isi.simulator.errors.NoSmsBalance;
import psk.isi.simulator.errors.NoSuchPhoneNumber;
import psk.isi.simulator.model.database.entities.SmsHistory;
import psk.isi.simulator.model.database.repository.PhoneNumberRepository;
import psk.isi.simulator.model.database.repository.SmsHistoryRepository;
import psk.isi.simulator.model.transport.converter.SmsHistoryConverter;
import psk.isi.simulator.model.transport.dto.DtoLoad;
import psk.isi.simulator.model.transport.dto.SmsHistoryDTO;
import psk.isi.simulator.service.MessageService;

import java.util.List;

/**
 * Klasa kontrolera obsługująca endpointy dotyczące obslugi SMS
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/sms")
public class MessagePhoneController {

    private MessageService messageService;

    private PhoneNumberRepository phoneNumberRepository;

    private SmsHistoryRepository smsHistoryRepository;

    @Autowired
    public MessagePhoneController(MessageService messageService, PhoneNumberRepository phoneNumberRepository, SmsHistoryRepository smsHistoryRepository) {
        this.messageService = messageService;
        this.phoneNumberRepository = phoneNumberRepository;
        this.smsHistoryRepository = smsHistoryRepository;
    }

    /**
     * Metoda odbierajaca obiekt SmsHistoryDTO i i przekazujaca go do metody zapisywania wiadomosci SMS.
     * @param smsHistoryDTO
     * @return
     */
    @PostMapping(path = "/singlemessage")
    public ResponseEntity<SmsHistoryDTO> saveMessage(@RequestBody SmsHistoryDTO smsHistoryDTO) {
        SmsHistory smsHistory1;
        try {
            smsHistory1 = messageService.saveMessage(smsHistoryDTO);
            messageService.smsHistoryDTOList(smsHistoryDTO.getPhoneNumberSender().getNumber(),smsHistoryDTO.getPhoneNumberSender().getNumber());
            return ResponseEntity.ok(SmsHistoryConverter.toDto(smsHistory1));
        } catch (NoSuchPhoneNumber | NoSmsBalance noSuchPhoneNumber) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Metoda odbierajaca obiekt z numerem telefonu i przekazujaca go do metody sprawdzajacej ostatni SMS dla numeru.
     * @param phoneNumber
     * @return
     */
    @GetMapping(path = "/{phoneNumber}")
    public ResponseEntity<List<SmsHistoryDTO>> getLastNumber(@PathVariable String phoneNumber) {
        try {
            List<SmsHistoryDTO> lastSms = messageService.findLastSms(phoneNumber);
            return ResponseEntity.ok(lastSms);
        } catch (NoSuchPhoneNumber noSuchPhoneNumber) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Metoda odbierajaca obiekt DtoLoad i przekazujaca go do metody, ktora zwraca historie konwersacji dwóch użytkowników.
     * @param receiver
     * @return
     */
    @PostMapping(path = "/load")
    public ResponseEntity<List<SmsHistoryDTO>> getAllNumbers(@RequestBody DtoLoad receiver) {

            List<SmsHistoryDTO> lastSms = messageService.smsHistoryDTOList(receiver.getPhoneNumberSender().getNumber(),receiver.getPhoneNumberReceiver());
            return ResponseEntity.ok(lastSms);

    }

}
