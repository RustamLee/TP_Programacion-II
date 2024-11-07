import Excepciones.ClienteNoEncontradoException;
import Excepciones.ClienteYaExistenteException;
import Excepciones.EmpleadoNoEncontradoException;
import Gestion.*;
import Modelo.Menu;


import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, EmpleadoNoEncontradoException, ClienteNoEncontradoException, ClienteYaExistenteException {

        GestorEmpleados gestorEmpleados = new GestorEmpleados();
        GestorClientes gestorClientes = new GestorClientes();
        GestorHabitaciones gestorHabitaciones = new GestorHabitaciones();
        GestorReservas gestorReservas = new GestorReservas(gestorHabitaciones);
        GestorAccesos gestorAccesos = new GestorAccesos(gestorEmpleados, gestorClientes);
        GestorArchivos gestorArchivos = new GestorArchivos(gestorAccesos, gestorReservas);
        Menu menu = new Menu(gestorAccesos, gestorReservas);

        // cargamos los objetos desde archivos JSON a las colecciones
        gestorArchivos.cargarEmpleadosDesdeArchivo();
        gestorArchivos.cargarClientesDesdeArchivo();
        gestorArchivos.cargarLoginContrasenaDesdeArchivo();
        gestorArchivos.cargarReservasDesdeArchivo();
        gestorArchivos.cargarHabitacionesDesdeArchivo();

        menu.start();

        // guardamos los objetos desde colecciones a archivos JSON
        gestorArchivos.guardarEmpleados();
        gestorArchivos.guardarClientes();
        gestorArchivos.guardarLoginContrasena();
        gestorArchivos.guardarReservas();
        gestorArchivos.guardarHabitaciones();
    }
}

