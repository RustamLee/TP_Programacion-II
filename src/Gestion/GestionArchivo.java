package Gestion;

import Modelo.Empleado;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;

public class GestionArchivo {

    private GestionAcceso gestionAcceso;
    private String EMPLEADOS_PATH = "empleados.json";
    private String CLIENTES_PATH = "clientes.json";
    private String LOGIN_PATH = "loginContrasenas.json";
    private Gson gson;

    // Constructor
    public GestionArchivo(GestionAcceso gestionAcceso) {
        this.gestionAcceso = gestionAcceso;
        gson = new Gson();
    }

    // metodo para verificar si el archivo existe
    private void verificarArchivoExiste(String path, Object defaultData) throws IOException {
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
            gson.toJson(gestionAcceso.getGestionEmpleado().getEmpleados(), writer);
        } catch (IOException e) {
            System.err.println("Error guardando empleados: " + e.getMessage());
        }
    }

    // guardamos clientes en un archivo JSON
    public void guardarClientes() {
        try (FileWriter writer = new FileWriter(CLIENTES_PATH)) {
            gson.toJson(gestionAcceso.getGestionCliente().getClientes(), writer);
        } catch (IOException e) {
            System.err.println("Error guardando clientes: " + e.getMessage());
        }
    }

    // cargar login y contrasena desde el archivo JSON al colección loginContrasenas
    public void cargarLoginContrasenaDesdeArchivo() throws IOException {
        verificarArchivoExiste(LOGIN_PATH, new HashMap<String, String>());
        try (FileReader reader = new FileReader(LOGIN_PATH)) {
            Type type = new TypeToken<HashMap<String, String>>() {
            }.getType();
            HashMap<String, String> loginContrasenaFromFile = gson.fromJson(reader, type);
            if (loginContrasenaFromFile != null) {
                gestionAcceso.getLoginContrasenas().putAll(loginContrasenaFromFile);
            }
        }
    }

    // cargar empleados desde el archivo JSON al colección empleados
    public void cargarEmpleadosDesdeArchivo() throws IOException {
        verificarArchivoExiste(EMPLEADOS_PATH, new HashMap<String, Empleado>());
        try (FileReader reader = new FileReader(EMPLEADOS_PATH)) {
            Type type = new TypeToken<HashMap<String, Empleado>>() {
            }.getType();
            HashMap<String, Empleado> empleadosDeArchivo = gson.fromJson(reader, type);
            if (empleadosDeArchivo != null) {
                gestionAcceso.getGestionEmpleado().getEmpleados().putAll(empleadosDeArchivo);
            }
        } catch (IOException e) {
            System.err.println("Error verificando o creando el archivo de empleados: " + e.getMessage());
        }
    }

    // guardamos login y contrasena en un archivo JSON
    public void guardarLoginContrasena() {
        try (FileWriter writer = new FileWriter(LOGIN_PATH)) {
            gson.toJson(gestionAcceso.getLoginContrasenas(), writer);
        } catch (IOException e) {
            System.err.println("Error guardando login y contrasena: " + e.getMessage());
        }
    }

}
