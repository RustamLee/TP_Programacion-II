package Excepciones;

public class ClienteYaExistenteException extends Exception {
    public ClienteYaExistenteException(String message) {
        super(message);
    }
}
