package psk.isi.simulator.model.transport.converter;

import psk.isi.simulator.model.database.entities.PhoneNumber;
import psk.isi.simulator.model.transport.dto.PhoneNumberDto;

public class PhoneNumberConverter {

    public static PhoneNumberDto toDto(PhoneNumber phoneNumber) {
        return new PhoneNumberDto(phoneNumber.getNumber(), phoneNumber.getPin());
    }

    public static PhoneNumber updateEntity(Long id, PhoneNumber phoneNumber) {
        return new PhoneNumber(id, phoneNumber.getNumber(), phoneNumber.getPin());
    }
}
