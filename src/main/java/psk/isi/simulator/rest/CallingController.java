package psk.isi.simulator.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import psk.isi.simulator.errors.NoMinutesBalance;
import psk.isi.simulator.errors.NoSuchPhoneNumber;
import psk.isi.simulator.model.transport.dto.CallDto;
import psk.isi.simulator.service.CallingService;

/**
 * Klasa kontrolera obsługująca endpointy dotyczące obslugi polaczen telefonicznych.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/call")
public class CallingController {

    private CallingService callingService;

    @Autowired
    public CallingController(CallingService callingService) {
        this.callingService = callingService;
    }

    /**
     * Metoda odbierajaca obiekt CallDto i przekazujaca je do metody zapisania czasu spedzonego na polaczeniu.
     * @param callDto
     * @return
     */
    @PostMapping(path = "/endcall")
    public ResponseEntity<?> getTimeOnBrowser(@RequestBody CallDto callDto){
        try {
            callingService.saveCalling(callDto);
        } catch (NoSuchPhoneNumber | NoMinutesBalance noSuchPhoneNumber) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }

}
