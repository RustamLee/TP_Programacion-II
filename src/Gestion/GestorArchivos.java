package Gestion;

import Modelo.Cliente;
import Modelo.Empleado;

import Modelo.LocalDateAdapter;
import Modelo.Reserva;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
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
    private Gson gson;

    // Constructor
    public GestorArchivos(GestorAccesos gestorAccesos, GestorReservas gestorReservas) {
        this.gestorAccesos = gestorAccesos;
        this.gestorReservas = gestorReservas;
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter()) // Регистрируем адаптер
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

    // guardamos empleados en un archivo JSON
    public void guardarEmpleados() {
        try (FileWriter writer = new FileWriter(EMPLEADOS_PATH)) {
            gson.toJson(gestorAccesos.getGestorEmpleado().getEmpleados(), writer);
        } catch (IOException e) {
            System.err.println("Error guardando empleados: " + e.getMessage());
        }
    }

    // guardamos clientes en un archivo JSON
    public void guardarClientes() {
        try (FileWriter writer = new FileWriter(CLIENTES_PATH)) {
            gson.toJson(gestorAccesos.getGestorCliente().getClientes(), writer);
        } catch (IOException e) {
            System.err.println("Error guardando clientes: " + e.getMessage());
        }
    }

    // guardamos login y contrasena en un archivo JSON
    public void guardarLoginContrasena() {
        try (FileWriter writer = new FileWriter(LOGIN_PATH)) {
            gson.toJson(gestorAccesos.getLoginContrasenas(), writer);
        } catch (IOException e) {
            System.err.println("Error guardando login y contrasena: " + e.getMessage());
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

    // cargar empleados desde el archivo JSON al colección empleados
    public void cargarEmpleadosDesdeArchivo() throws IOException {
        isArchivoExiste(EMPLEADOS_PATH, new HashMap<String, Empleado>());
        try (FileReader reader = new FileReader(EMPLEADOS_PATH)) {
            Type type = new TypeToken<HashMap<String, Empleado>>() {
            }.getType();
            HashMap<String, Empleado> empleadosDeArchivo = gson.fromJson(reader, type);
            if (empleadosDeArchivo != null) {
                gestorAccesos.getGestorEmpleado().getEmpleados().putAll(empleadosDeArchivo);
            }
        } catch (IOException e) {
            System.err.println("Error verificando o creando el archivo de empleados: " + e.getMessage());
        }
    }

    // cargar clientes desde el archivo JSON al colección clientes
    public void cargarClientesDesdeArchivo() throws IOException {
        isArchivoExiste(CLIENTES_PATH, new HashMap<String, Cliente>());
        try (FileReader reader = new FileReader(CLIENTES_PATH)) {
            Type type = new TypeToken<HashMap<String, Cliente>>() {
            }.getType();
            HashMap<String, Cliente> clientesDeArchivo = gson.fromJson(reader, type);
            if (clientesDeArchivo != null) {
                gestorAccesos.getGestorCliente().getClientes().putAll(clientesDeArchivo);
            }
        } catch (IOException e) {
            System.err.println("Error verificando o creando el archivo de clientes: " + e.getMessage());
        }
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

    // cargar reservas desde el archivo JSON al colección reservasPorHabitacion
    public void cargarReservasDesdeArchivo() throws IOException {
        isArchivoExiste(RESERVAS_PATH, new HashMap<Integer, List<Reserva>>());
        try (FileReader reader = new FileReader(RESERVAS_PATH)) {
            Type type = new TypeToken<Map<Integer, List<Reserva>>>() {}.getType();
            Map<Integer, List<Reserva>> reservasDeArchivo = gson.fromJson(reader, type);
            if (reservasDeArchivo != null) {
                gestorReservas.getReservasPorHabitacion().putAll(reservasDeArchivo);
            }
        } catch (IOException e) {
            System.err.println("Error verificando o creando el archivo de reservas: " + e.getMessage());
        }
    }


}
