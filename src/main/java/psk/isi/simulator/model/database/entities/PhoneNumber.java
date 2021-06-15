package psk.isi.simulator.model.database.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@NoArgsConstructor
@Entity
@AllArgsConstructor
@Data
/**
 * Klasa odpowiadająca za tabelę ,,PhoneNumber" (przechowuje informacje na temat numerów klientów).
 */
public class PhoneNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPhoneNumber;
    private String number;
    private String pin;


    public PhoneNumber(String number, String pin) {
        this.number = number;
        this.pin = pin;
    }
}
