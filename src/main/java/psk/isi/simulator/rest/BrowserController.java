package psk.isi.simulator.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import psk.isi.simulator.errors.NoSuchPhoneNumber;
import psk.isi.simulator.model.database.repository.NumberBalanceRepository;
import psk.isi.simulator.model.database.repository.PhoneNumberRepository;
import psk.isi.simulator.model.transport.dto.BrowserDto;
import psk.isi.simulator.service.BrowserService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/browser")
public class BrowserController {

    private PhoneNumberRepository phoneNumberRepository;
    private NumberBalanceRepository numberBalanceRepository;
    private BrowserService browserService;

    @Autowired
    public BrowserController(PhoneNumberRepository phoneNumberRepository, NumberBalanceRepository numberBalanceRepository, BrowserService browserService) {
        this.phoneNumberRepository = phoneNumberRepository;
        this.numberBalanceRepository = numberBalanceRepository;
        this.browserService = browserService;
    }


    @PostMapping(path = "/time")
    public ResponseEntity<?> getTimeOnBrowser(@RequestBody BrowserDto browserDto){
        try {
            browserService.saveBrowsing(browserDto);
        } catch (NoSuchPhoneNumber noSuchPhoneNumber) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }
}

