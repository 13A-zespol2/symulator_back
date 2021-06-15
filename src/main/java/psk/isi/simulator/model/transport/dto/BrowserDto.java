package psk.isi.simulator.model.transport.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Klasa transportowa dla klasy Browser.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrowserDto {
    private String phoneNumber;
    private Double time;
}
