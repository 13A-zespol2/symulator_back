package psk.isi.simulator.model.transport.converter;

import psk.isi.simulator.model.database.entities.PhoneNumber;
import psk.isi.simulator.model.transport.dto.PhoneNumberDto;

/**
 * Klasa odpowiedzialna za konwersję obiektów PhoneNumber.
 */
public class PhoneNumberConverter {

    /**
     * Metoda odpowiedzialna za konwersję obiektu klasy PhoneNumber do obiektu klasy PhoneNumberDto.
     */
    public static PhoneNumberDto toDto(PhoneNumber phoneNumber) {
        return new PhoneNumberDto(phoneNumber.getNumber(), phoneNumber.getPin());
    }
    /**
     * Metoda odpowiedzialna za aktualizacje obiektu klasy PhoneNumber.
     */
    public static PhoneNumber updateEntity( PhoneNumber phoneNumber) {
        return new PhoneNumber(phoneNumber.getNumber(), phoneNumber.getPin());
    }
    /**
     * Metoda odpowiedzialna za konwersję obiektu klasy PhoneNumberDto do obiektu klasy PhoneNumber.
     */
    public static PhoneNumber toEntity( PhoneNumberDto phoneNumber) {
        return new PhoneNumber(phoneNumber.getNumber(), phoneNumber.getPin());
    }
}
