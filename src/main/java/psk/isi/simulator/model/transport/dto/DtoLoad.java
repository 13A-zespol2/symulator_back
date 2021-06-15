package psk.isi.simulator.model.transport.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import psk.isi.simulator.model.database.entities.PhoneNumber;

@Getter
@Setter
@Data
/**
 * Klasa transportowa dla SMS.
 */
public class DtoLoad {
    private String phoneNumberReceiver;
    private PhoneNumber phoneNumberSender;
    private String message;


    public DtoLoad(String phoneNumberReceiver, PhoneNumber phoneNumberSender, String message) {
        this.phoneNumberReceiver = phoneNumberReceiver;
        this.phoneNumberSender = phoneNumberSender;
        this.message = message;
    }
}
