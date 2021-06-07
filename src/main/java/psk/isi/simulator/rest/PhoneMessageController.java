package psk.isi.simulator.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import psk.isi.simulator.errors.NoSuchPhoneNumber;
import psk.isi.simulator.model.database.entities.SmsHistory;
import psk.isi.simulator.model.transport.converter.SmsHistoryConverter;
import psk.isi.simulator.model.transport.dto.SmsHistoryDTO;
import psk.isi.simulator.service.SendMessageService;

import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sms")
public class PhoneMessageController {

    private SendMessageService sendMessageService;

    @Autowired
    public PhoneMessageController(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @PostMapping(path = "/singlemessage")
    public ResponseEntity<SmsHistoryDTO> saveMessage(@RequestBody SmsHistoryDTO smsHistoryDTO) {
        SmsHistory smsHistory1;
        try {
            smsHistory1 = sendMessageService.saveMessage(smsHistoryDTO);
            return ResponseEntity.ok(SmsHistoryConverter.toDto(smsHistory1));

        } catch (NoSuchPhoneNumber noSuchPhoneNumber) {

            return ResponseEntity.notFound().build();
        }


    }
}
