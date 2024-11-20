import Excepciones.ClienteNoEncontradoException;
import Excepciones.ClienteYaExistenteException;
import Excepciones.EmpleadoNoEncontradoException;
import Excepciones.HabitacionNoEncontradaException;
import Modelo.AppStarter;


import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            AppStarter appStarter = new AppStarter();
            appStarter.inicializar();
        } catch (IOException | EmpleadoNoEncontradoException |
                 ClienteNoEncontradoException | ClienteYaExistenteException |
                 HabitacionNoEncontradaException e) {
            e.printStackTrace();
        }
    }
}
