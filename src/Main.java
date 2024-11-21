import Excepciones.*;
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
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            System.exit(2);
        }
    }
}
