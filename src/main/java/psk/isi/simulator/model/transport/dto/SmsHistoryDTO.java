package psk.isi.simulator.model.transport.dto;

import lombok.Getter;
import lombok.Setter;
import psk.isi.simulator.model.database.entities.PhoneNumber;

import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Klasa transportowa dla historii SMS.
 */
@Getter
@Setter
public class SmsHistoryDTO {
    private PhoneNumberDto phoneNumberSender;
    private String phoneNumberReceiver;
    private String message;
    private Date dateSms;


    public SmsHistoryDTO(PhoneNumberDto phoneNumberSender, String phoneNumberReceiver, String message, Date dateSms) {
        this.phoneNumberSender = phoneNumberSender;
        this.phoneNumberReceiver = phoneNumberReceiver;
        this.message = message;
        this.dateSms = dateSms;

    }


}
