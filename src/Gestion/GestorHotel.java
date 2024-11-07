package Gestion;

import Modelo.Empleado;
import java.util.HashMap;

public class GestorHotel {

    private HashMap<String, Empleado> empleados;
    private GestorArchivos gestorArchivos;
    private GestorAccesos gestorAccesos;

    // Constructor
    public GestorHotel(GestorAccesos gestorAccesos) {
        this.gestorAccesos = gestorAccesos;
        this.empleados = new HashMap<>();
    }


}
