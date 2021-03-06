package psk.isi.simulator.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import psk.isi.simulator.model.database.entities.PhoneNumber;
import psk.isi.simulator.model.database.repository.PhoneNumberRepository;
import psk.isi.simulator.model.transport.converter.PhoneNumberConverter;
import psk.isi.simulator.model.transport.dto.PhoneNumberDto;

import java.util.Optional;


/**
 * Klasa kontrolera obsługująca endpointy dotyczące obslugi logowania.
 */
@RestController
public class LoginPhoneController {
    private final PhoneNumberRepository phoneNumberRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public LoginPhoneController(PhoneNumberRepository phoneNumberRepository, PasswordEncoder passwordEncoder) {
        this.phoneNumberRepository = phoneNumberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Metoda odbierajaca obiekt PhoneNumberDto i sprawdzajaca czy dane logowania sa poprawne.
     * @param phoneNumber
     * @return
     */
    @PostMapping(path = "/login")
    public ResponseEntity<PhoneNumberDto> loginPhoneNumber(@RequestBody PhoneNumberDto phoneNumber) {
        String number = phoneNumber.getNumber();
        Optional<PhoneNumber> byNumber = phoneNumberRepository.findByNumber(number);

        if (byNumber.isPresent()) {
            PhoneNumber phoneNumberDatabase = byNumber.get();
            boolean matches = passwordEncoder.matches(phoneNumber.getPin(),phoneNumberDatabase.getPin());

            return matches ? ResponseEntity.ok(PhoneNumberConverter.toDto(phoneNumberDatabase)) : ResponseEntity.badRequest().build();
        }
        return ResponseEntity.notFound().build();
    }


}
