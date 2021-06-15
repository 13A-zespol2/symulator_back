package psk.isi.simulator.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import psk.isi.simulator.service.CallingService;

/**
 * Klasa kontrolera obsługująca endpointy Calling
 */
@RestController
@RequestMapping("/phone")
@RequiredArgsConstructor
public class PhoneNumberController {
    private CallingService callingService;

    @Autowired
    public PhoneNumberController(CallingService callingService) {
        this.callingService = callingService;
    }

    /**
     * Metoda odbierajaca obiekt z numerem telefonu i przekazujaca go do metody sprawdzajacej stan pakietu minut.
     * @param phoneNumber
     * @return
     */
    @GetMapping
    public ResponseEntity<Double> getMinutesBalance(String phoneNumber) {
        return ResponseEntity.ok(callingService.takeMinutes(phoneNumber));
    }

}
