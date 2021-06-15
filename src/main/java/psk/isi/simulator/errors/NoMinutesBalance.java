package psk.isi.simulator.errors;
/**
 * Klasa zawierajaca za obsługe customowych wyjątków o braku pakietu minut.
 */
public class NoMinutesBalance extends Exception{
    public NoMinutesBalance(String message) {
        super(message);
    }
}
