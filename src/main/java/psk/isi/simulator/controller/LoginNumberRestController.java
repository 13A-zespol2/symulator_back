package psk.isi.simulator.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/phone-login")
@RequiredArgsConstructor
public class LoginNumberRestController {
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        return "Hello, swagger!";
    }

    @RequestMapping(value = "/hello", method = RequestMethod.POST)
    public void helloOnConsole() {
        System.out.println("Hello, swagger on console!");
    }

    @RequestMapping(value = "/bye/{name}", method = RequestMethod.DELETE)
    public void byePerson(@PathVariable String name) {
        System.out.println("Bye bye, " + name);
    }
}
