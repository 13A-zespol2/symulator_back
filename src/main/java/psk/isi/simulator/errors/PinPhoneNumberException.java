package psk.isi.simulator.errors;
/**
 * Klasa odpwiadająca za obsługe customowych wyjątków o złym PIN'ie.
 */
public class PinPhoneNumberException extends  Exception{
    public PinPhoneNumberException(String message) {
        super(message);
    }
}
