package psk.isi.simulator.model.transport.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Klasa transportowa dla PhoneNumber.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneNumberDto {
    private String number;
    private String pin;
}
