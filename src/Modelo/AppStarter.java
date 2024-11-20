package Modelo;

import Excepciones.ClienteNoEncontradoException;
import Excepciones.ClienteYaExistenteException;
import Excepciones.EmpleadoNoEncontradoException;
import Excepciones.HabitacionNoEncontradaException;
import Gestion.GestorAccesos;
import Gestion.GestorArchivos;
import Gestion.GestorClientes;
import Gestion.GestorEmpleados;
import Gestion.GestorHabitaciones;
import Gestion.GestorReservas;

import java.io.IOException;


public class AppStarter {
    private GestorEmpleados gestorEmpleados;
    private GestorClientes gestorClientes;
    private GestorHabitaciones gestorHabitaciones;
    private HistoriaReservas historiaReservas;
    private GestorReservas gestorReservas;
    private GestorAccesos gestorAccesos;
    private GestorArchivos gestorArchivos;
    private Menu menu;

    public void inicializar() throws IOException,
            EmpleadoNoEncontradoException, ClienteNoEncontradoException,
            ClienteYaExistenteException, HabitacionNoEncontradaException {

        // Inicialización de objetos
        gestorEmpleados = new GestorEmpleados();
        gestorClientes = new GestorClientes();
        gestorHabitaciones = new GestorHabitaciones();
        historiaReservas = new HistoriaReservas();
        gestorReservas = new GestorReservas(gestorHabitaciones, historiaReservas);
        gestorAccesos = new GestorAccesos(gestorEmpleados, gestorClientes);
        gestorArchivos = new GestorArchivos(gestorAccesos, gestorReservas);
        menu = new Menu(gestorAccesos, gestorReservas);

        // Cargar los datos
        cargarDatos();

        // Iniciar el menú
        menu.start();

        // Guardar los datos
        guardarDatos();
    }

    private void cargarDatos() throws IOException {
        gestorArchivos.cargarEmpleadosDesdeArchivo();
        gestorArchivos.cargarClientesDesdeArchivo();
        gestorArchivos.cargarLoginContrasenaDesdeArchivo();
        gestorArchivos.cargarReservasDesdeArchivo();
        gestorArchivos.cargarHistoriaReservasDesdeArchivo();
        gestorArchivos.cargarHabitacionesDesdeArchivo();
    }

    private void guardarDatos() {
        gestorArchivos.guardarEmpleados();
        gestorArchivos.guardarClientes();
        gestorArchivos.guardarLoginContrasena();
        gestorArchivos.guardarReservas();
        gestorArchivos.guardarHistoriaReservas();
        gestorArchivos.guardarHabitaciones();
    }
}
