package psk.isi.simulator.model.transport.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CallDto {
    private String phoneNumber;
    private Double seconds;
}
