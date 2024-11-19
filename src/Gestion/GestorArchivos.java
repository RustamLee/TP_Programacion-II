package Gestion;

import Modelo.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestorArchivos {

    private GestorAccesos gestorAccesos;
    private GestorReservas gestorReservas;
    private String EMPLEADOS_PATH = "empleados.json";
    private String CLIENTES_PATH = "clientes.json";
    private String LOGIN_PATH = "loginContrasenas.json";
    private String RESERVAS_PATH = "reservas.json";
    private String HABITACIONES_PATH = "habitaciones.json";
    private String HISTORIA_RESERVAS_PATH = "historiaReservas.json";
    private Gson gson;

    // Constructor
    public GestorArchivos(GestorAccesos gestorAccesos, GestorReservas gestorReservas) {
        this.gestorAccesos = gestorAccesos; //usamos este objeto para acceder a colecciones de empleados y clientes.
        this.gestorReservas = gestorReservas; // este objeto para acceder a la colección de reservas y, a través de las reservas, obtenemos acceso a la colección de habitaciones.
        this.gson = new GsonBuilder() // este objeto para serializar y deserializar objetos de fecha local.
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
    }

    // metodo para verificar si el archivo existe (se usa para cualquier archivo)
    private void isArchivoExiste(String path, Object defaultData) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            file.createNewFile();
            try (FileWriter writer = new FileWriter(file)) {
                gson.toJson(defaultData, writer);
            }
        }
    }

    // metodo generico para cargar datos desde un archivo JSON
    private <T> void cargarDesdeArchivo(String path, Map<String, T> coleccion, Type type) throws IOException {
        isArchivoExiste(path, new HashMap<>());
        try (FileReader reader = new FileReader(path)) {
            Map<String, T> dataDeArchivo = gson.fromJson(reader, type);
            if (dataDeArchivo != null) {
                coleccion.putAll(dataDeArchivo);
            }
        } catch (IOException e) {
            System.err.println("Error verificando o creando el archivo: " + e.getMessage());
        }
    }

    // metodo generico para guardar datos en un archivo JSON
    private <T> void guardarEnArchivo(String path, Map<String, T> coleccion) {
        try (FileWriter writer = new FileWriter(path)) {
            gson.toJson(coleccion, writer);
        } catch (IOException e) {
            System.err.println("Error guardando los datos: " + e.getMessage());
        }
    }

    // guardamos empleados en un archivo JSON
    public void guardarEmpleados() {
        guardarEnArchivo(EMPLEADOS_PATH, gestorAccesos.getGestorEmpleado().getEmpleados());
    }

    // guardamos clientes en un archivo JSON
    public void guardarClientes() {
        guardarEnArchivo(CLIENTES_PATH, gestorAccesos.getGestorCliente().getClientes());
    }

    // guardamos login y contrasena en un archivo JSON
    public void guardarLoginContrasena() {
        try (FileWriter writer = new FileWriter(LOGIN_PATH)) {
            gson.toJson(gestorAccesos.getLoginContrasenas(), writer);
        } catch (IOException e) {
            System.err.println("Error guardando login y contrasena: " + e.getMessage());
        }
    }

    // guardamos habitaciones en un archivo JSON
    public void guardarHabitaciones() {
        try(FileWriter writer = new FileWriter(HABITACIONES_PATH)) {
            gson.toJson(gestorReservas.getGestorHabitaciones().getListaHabitaciones(), writer);
        } catch (IOException e) {
            System.err.println("Error guardando habitaciones: " + e.getMessage());
        }
    }

    // guardamos reservas en un archivo JSON
    public void guardarReservas() {
        try (FileWriter writer = new FileWriter(RESERVAS_PATH)) {
            Map<Integer, List<Reserva>> reservasPorHabitacion = gestorReservas.getReservasPorHabitacion();
            gson.toJson(reservasPorHabitacion, writer);
        } catch (IOException e) {
            System.err.println("Error guardando reservas: " + e.getMessage());
        }
    }

    // metodo para guardar la historia de reservas en un archivo JSON
    public void guardarHistoriaReservas() {
        try (FileWriter writer = new FileWriter(HISTORIA_RESERVAS_PATH)) {
            gson.toJson(gestorReservas.getHistoriaReservas().getReservasСompletadas(), writer);
        } catch (IOException e) {
            System.err.println("Error guardando la historia de reservas: " + e.getMessage());
        }
    }

    // cargar empleados desde el archivo JSON al colección empleados
    public void cargarEmpleadosDesdeArchivo() throws IOException {
        Type type = new TypeToken<HashMap<String, Empleado>>(){}.getType();
        cargarDesdeArchivo(EMPLEADOS_PATH, gestorAccesos.getGestorEmpleado().getEmpleados(), type);
    }

    // cargar clientes desde el archivo JSON al colección clientes
    public void cargarClientesDesdeArchivo() throws IOException {
        Type type = new TypeToken<HashMap<String, Cliente>>(){}.getType();
        cargarDesdeArchivo(CLIENTES_PATH, gestorAccesos.getGestorCliente().getClientes(), type);
    }

    // cargar login y contrasena desde el archivo JSON al colección loginContrasenas
    public void cargarLoginContrasenaDesdeArchivo() throws IOException {
        isArchivoExiste(LOGIN_PATH, new HashMap<String, String>());
        try (FileReader reader = new FileReader(LOGIN_PATH)) {
            Type type = new TypeToken<HashMap<String, String>>() {
            }.getType();
            HashMap<String, String> loginContrasenaFromFile = gson.fromJson(reader, type);
            if (loginContrasenaFromFile != null) {
                gestorAccesos.getLoginContrasenas().putAll(loginContrasenaFromFile);
            }
        }
    }

    // cargar habitaciones desde el archivo JSON al colección habitaciones
    public void cargarHabitacionesDesdeArchivo() throws IOException {
        isArchivoExiste(HABITACIONES_PATH, new ArrayList<Habitacion>());
        try (FileReader reader = new FileReader(HABITACIONES_PATH)) {
            Type type = new TypeToken<ArrayList<Habitacion>>() {}.getType();
            ArrayList<Habitacion> habitacionesDeArchivo = gson.fromJson(reader, type);
            if (habitacionesDeArchivo != null) {
                gestorReservas.getGestorHabitaciones().getListaHabitaciones().addAll(habitacionesDeArchivo);
            }
        } catch (IOException e) {
            System.err.println("Error verificando o creando el archivo de habitaciones: " + e.getMessage());
        } catch (JsonSyntaxException e) {
            System.err.println("Error de sintaxis en el archivo JSON: " + e.getMessage());
        }
    }


    // cargar reservas desde el archivo JSON al colección reservasPorHabitacion
    public void cargarReservasDesdeArchivo () throws IOException {
        isArchivoExiste(RESERVAS_PATH, new HashMap<Integer, List<Reserva>>());
        try (FileReader reader = new FileReader(RESERVAS_PATH)) {
            Type type = new TypeToken<Map<Integer, List<Reserva>>>() {
            }.getType();
            Map<Integer, List<Reserva>> reservasDeArchivo = gson.fromJson(reader, type);
            if (reservasDeArchivo != null) {
                gestorReservas.getReservasPorHabitacion().putAll(reservasDeArchivo);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Archivo no encontrado: " + e.getMessage());
        } catch (JsonSyntaxException e) {
            System.err.println("Error de sintaxis en el archivo JSON: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error leyendo el archivo: " + e.getMessage());
        }
    }

    // metodo para cargar la historia de reservas desde el archivo JSON a la colección reservasCompletadas
    public void cargarHistoriaReservasDesdeArchivo() throws IOException {
        isArchivoExiste(HISTORIA_RESERVAS_PATH, new ArrayList<Reserva>());
        try (FileReader reader = new FileReader(HISTORIA_RESERVAS_PATH)) {
            Type type = new TypeToken<ArrayList<Reserva>>() {
            }.getType();
            ArrayList<Reserva> reservasDeArchivo = gson.fromJson(reader, type);
            if (reservasDeArchivo != null) {
                gestorReservas.getHistoriaReservas().getReservasСompletadas().addAll(reservasDeArchivo);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Archivo no encontrado: " + e.getMessage());
        } catch (JsonSyntaxException e) {
            System.err.println("Error de sintaxis en el archivo JSON: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error leyendo el archivo: " + e.getMessage());
        }
    }

}
