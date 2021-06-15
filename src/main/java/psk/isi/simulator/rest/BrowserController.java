package psk.isi.simulator.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import psk.isi.simulator.errors.NoInternetBalance;
import psk.isi.simulator.errors.NoSuchPhoneNumber;
import psk.isi.simulator.model.transport.dto.BrowserDto;
import psk.isi.simulator.service.BrowserService;


/**
 * Klasa kontrolera obsługująca endpointy dotyczące obslugi przegladarki.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/browser")
public class BrowserController {

    private BrowserService browserService;

    @Autowired
    public BrowserController(BrowserService browserService) {
        this.browserService = browserService;
    }

    /**
     * Metoda odbierajaca obiekt PhoneNumber i przekazujaca je do metody pobrania stanu pakietu Internet
     * @param phoneNumber
     * @return
     */
    @PostMapping
    public ResponseEntity<Double> getInternetBalance(@RequestBody String phoneNumber) {
        return ResponseEntity.ok(browserService.takeInternet(phoneNumber));
    }

    /**
     * Metoda odbierajaca obiekt BrowserDto i przekazujaca go do metody zapisywania czasu spedzonego na przegladarce
     * @param browserDto
     * @return
     */
    @PostMapping(path = "/time")
    public ResponseEntity<?> getTimeOnBrowser(@RequestBody BrowserDto browserDto){
        try {
            browserService.saveBrowsing(browserDto);
        } catch (NoSuchPhoneNumber | NoInternetBalance noSuchPhoneNumber) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }
}

