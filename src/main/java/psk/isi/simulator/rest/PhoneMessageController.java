package psk.isi.simulator.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import psk.isi.simulator.model.database.entities.SmsHistory;
import psk.isi.simulator.service.SendMessageService;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class PhoneMessageController {

    private SendMessageService sendMessageService;

    @PostMapping(value = "/singlemessage")
    public ResponseEntity<?> saveMessage(@RequestBody SmsHistory smsHistory){
        boolean smsHistory1 = sendMessageService.saveMessage(smsHistory);

        if(smsHistory1) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
