package Gestion;

import Enumeraciones.RoleUsuario;
import Excepciones.EmpleadoYaExistenteException;
import Modelo.Empleado;
import java.util.HashMap;

public class GestionHotel {

    private HashMap<String, Empleado> empleados;
    private GestionArchivo gestionArchivo;
    private GestionAcceso gestionAcceso;

    // Constructor
    public GestionHotel(GestionAcceso gestionAcceso) {
        this.gestionAcceso = gestionAcceso;
        this.empleados = new HashMap<>();
    }


}
