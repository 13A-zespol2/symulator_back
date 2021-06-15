package psk.isi.simulator.errors;
/**
 * Klasa odpwiadająca za obsługe customowych wyjątków o braku pakietu minut.
 */
public class NoMinutesBalance extends Exception{
    public NoMinutesBalance(String message) {
        super(message);
    }
}
