package psk.isi.simulator.errors;
/**
 * Klasa zawierajaca za obsługe customowych wyjątków o braku numeru telefonu.
 */
public class NoSuchPhoneNumber extends Exception{
    public NoSuchPhoneNumber(String message) {
        super(message);
    }
}
