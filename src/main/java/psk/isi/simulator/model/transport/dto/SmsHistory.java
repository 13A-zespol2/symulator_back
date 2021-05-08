package psk.isi.simulator.model.transport.dto;

import psk.isi.simulator.model.database.entities.PhoneNumber;

import javax.persistence.ManyToOne;
import java.util.Date;

public class SmsHistory {
    private PhoneNumber phoneNumberSender;

    private PhoneNumber phoneNumberReceiver;
    private String message;
    private Date dateSms;
}
