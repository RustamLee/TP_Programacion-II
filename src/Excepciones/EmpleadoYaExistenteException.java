package Excepciones;

public class EmpleadoYaExistenteException extends RuntimeException  {
    public EmpleadoYaExistenteException(String message) {
        super(message);
    }
}
