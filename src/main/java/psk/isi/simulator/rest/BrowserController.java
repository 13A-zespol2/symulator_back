package psk.isi.simulator.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import psk.isi.simulator.errors.NoInternetBalance;
import psk.isi.simulator.errors.NoSuchPhoneNumber;
import psk.isi.simulator.model.database.repository.NumberBalanceRepository;
import psk.isi.simulator.model.database.repository.PhoneNumberRepository;
import psk.isi.simulator.model.transport.dto.BrowserDto;
import psk.isi.simulator.service.BrowserService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/browser")
public class BrowserController {

    private BrowserService browserService;

    @Autowired
    public BrowserController(BrowserService browserService) {
        this.browserService = browserService;
    }


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

