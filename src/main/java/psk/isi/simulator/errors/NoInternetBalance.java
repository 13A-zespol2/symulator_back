package psk.isi.simulator.errors;

/**
 * Klasa zawierajaca za obsługe customowych wyjątków o braku pakietu Internetu.
 */
public class NoInternetBalance extends Exception{
    public NoInternetBalance(String message) {
        super(message);
}
}
