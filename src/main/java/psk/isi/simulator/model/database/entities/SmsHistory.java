package psk.isi.simulator.model.database.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@Entity
@AllArgsConstructor
@Data
/**
 * Klasa odpowiadająca za tabelę ,,SmsHistory" (przechowuje wszystkie wysłane SMS).
 */
public class SmsHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSmsHistory;
    @ManyToOne
    private PhoneNumber phoneNumberSender;
    @ManyToOne
    private PhoneNumber phoneNumberReceiver;
    private String message;
    private Date dateSms;

}
