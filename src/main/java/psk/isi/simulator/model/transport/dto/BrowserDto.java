package psk.isi.simulator.model.transport.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import psk.isi.simulator.model.database.entities.PhoneNumber;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrowserDto {
    private String phoneNumber;
    private Double time;
}
