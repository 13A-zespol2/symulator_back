package psk.isi.simulator.errors;
/**
 * Klasa zawierajaca za obsługe customowych wyjątków o braku pakietu SMS.
 */
public class NoSmsBalance extends Exception {
    public NoSmsBalance(String message) {
        super(message);
    }
}
