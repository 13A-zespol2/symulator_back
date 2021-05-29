package psk.isi.simulator.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import psk.isi.simulator.model.database.entities.PhoneNumber;
import psk.isi.simulator.model.database.repository.PhoneNumberRepository;
import psk.isi.simulator.model.transport.converter.PhoneNumberConverter;
import psk.isi.simulator.model.transport.dto.PhoneNumberDto;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class LoginPhoneController {
    private final PhoneNumberRepository phoneNumberRepository;

    @PostMapping(path = "/login")
    public ResponseEntity<PhoneNumberDto> loginPhoneNumber(@RequestBody PhoneNumberDto phoneNumber) {

        String pin = phoneNumber.getPin();
        String number = phoneNumber.getNumber();
        Optional<PhoneNumber> byNumber = phoneNumberRepository.findByNumber(number);

        if (byNumber.isPresent()) {
            PhoneNumber phoneNumber1 = byNumber.get();
            if (phoneNumber1.getPin().equals(pin)) {
                return ResponseEntity.ok(PhoneNumberConverter.toDto(phoneNumber1));
            }
        }
        return ResponseEntity.notFound().build();
    }






}
