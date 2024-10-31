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

    private GestionEmpleado gestionEmpleado;
    private String EMPLEADOS_PATH = "empleados.json";
    private String LOGIN_PATH = "loginContrasenas.json";
    private Gson gson;

    // Constructor
    public GestionArchivo(GestionEmpleado gestionEmpleado) {
        this.gestionEmpleado = gestionEmpleado;
        gson = new Gson();
    }

    // guardamos empleados en un archivo JSON
    public void guardarEmpleados() {
        try (FileWriter writer = new FileWriter(EMPLEADOS_PATH)) {
            gson.toJson(gestionEmpleado.getEmpleados(), writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // guardamos login y contrasena en un archivo JSON
    public void guardarLoginContrasena() {
        try (FileWriter writer = new FileWriter(LOGIN_PATH)) {
            gson.toJson(gestionEmpleado.getLoginContrasenas(), writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // cargar empleados desde el archivo JSON al colección empleados
    public void cargarEmpleadosDesdeArchivo() throws IOException {
        verificarEmpleadosFileExists();
        try (FileReader reader = new FileReader(EMPLEADOS_PATH)) {
            Type type = new TypeToken<HashMap<String, Empleado>>() {
            }.getType();
            HashMap<String, Empleado> empleadosDeArchivo = gson.fromJson(reader, type);
            if (empleadosDeArchivo != null) {
                gestionEmpleado.getEmpleados().putAll(empleadosDeArchivo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // cargar login y contrasena desde el archivo JSON al colección loginContrasenas
    public void cargarLoginContrasenaDesdeArchivo() throws IOException {
        verificarLoginContrasenaFileExists();
        try (FileReader reader = new FileReader(LOGIN_PATH)) {
            Type type = new TypeToken<HashMap<String, String>>() {
            }.getType();
            HashMap<String, String> loginContrasenaFromFile = gson.fromJson(reader, type);
            if (loginContrasenaFromFile != null) {
                gestionEmpleado.getLoginContrasenas().putAll(loginContrasenaFromFile);
            }
        }
    }

    // aux metodo para verificar si el archivo para guardar empleados existe, si no, crearlo
    private void verificarEmpleadosFileExists() throws IOException {
        File file = new File(EMPLEADOS_PATH);
        if (!file.exists()) {
            file.createNewFile();
            try (FileWriter writer = new FileWriter(file)) {
                gson.toJson(new HashMap<String, Empleado>(), writer);
            }
        }
    }

    //aux metodo para verificar si el archivo para guardar login y contrasena existe, si no, crearlo
    private void verificarLoginContrasenaFileExists() throws IOException {
        File file = new File(LOGIN_PATH);
        if (!file.exists()) {
            file.createNewFile();
            try (FileWriter writer = new FileWriter(file)) {
                gson.toJson(new HashMap<String, String>(), writer);
            }
        }
    }

}
