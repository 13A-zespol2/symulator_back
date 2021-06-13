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
import psk.isi.simulator.model.transport.dto.BrowserDto;
import psk.isi.simulator.model.transport.dto.CallDto;
import psk.isi.simulator.service.BrowserService;
import psk.isi.simulator.service.CallingService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/call")
public class CallingController {

    private CallingService callingService;

    @Autowired
    public CallingController(CallingService callingService) {
        this.callingService = callingService;
    }


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
