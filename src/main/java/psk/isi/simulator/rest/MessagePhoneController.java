package psk.isi.simulator.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import psk.isi.simulator.errors.NoSuchPhoneNumber;
import psk.isi.simulator.model.database.entities.SmsHistory;
import psk.isi.simulator.model.database.repository.PhoneNumberRepository;
import psk.isi.simulator.model.database.repository.SmsHistoryRepository;
import psk.isi.simulator.model.transport.converter.SmsHistoryConverter;
import psk.isi.simulator.model.transport.dto.SmsHistoryDTO;
import psk.isi.simulator.service.MessageService;

import java.util.List;

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

    @PostMapping(path = "/singlemessage")
    public ResponseEntity<SmsHistoryDTO> saveMessage(@RequestBody SmsHistoryDTO smsHistoryDTO) {
        SmsHistory smsHistory1;
        try {
            smsHistory1 = messageService.saveMessage(smsHistoryDTO);

            return ResponseEntity.ok(SmsHistoryConverter.toDto(smsHistory1));

        } catch (NoSuchPhoneNumber noSuchPhoneNumber) {

            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(path = "/{phoneNumber}")
    public ResponseEntity<List<SmsHistoryDTO>> getAllNumber(@PathVariable String phoneNumber) {
        try {
            List<SmsHistoryDTO> lastSms = messageService.findLastSms(phoneNumber);
            return ResponseEntity.ok(lastSms);
        } catch (NoSuchPhoneNumber noSuchPhoneNumber) {
            return ResponseEntity.notFound().build();
        }
    }


}
