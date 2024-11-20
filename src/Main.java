import Excepciones.ClienteNoEncontradoException;
import Excepciones.ClienteYaExistenteException;
import Excepciones.EmpleadoNoEncontradoException;
import Excepciones.HabitacionNoEncontradaException;
import Modelo.AppStarter;


import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        AppStarter appStarter = new AppStarter();
        try {
            appStarter.inicializar();
        } catch (IOException e) {
            System.err.println("Error al cargar datos: " + e.getMessage());
            System.exit(1);
        } catch (EmpleadoNoEncontradoException | ClienteNoEncontradoException |
                 ClienteYaExistenteException | HabitacionNoEncontradaException e) {
            System.err.println("Error de datos: " + e.getMessage());
            System.exit(2);
        }
        System.out.println("El programa se ha lanzado con éxito!");
    }
}
