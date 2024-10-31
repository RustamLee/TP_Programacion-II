package Modelo;

import Enumeraciones.RoleUsuario;
import Gestion.GestionAcceso;

public abstract class Usuario {
    protected String nombre;
    protected String apellido;
    protected String DNI;
    protected RoleUsuario role;
    protected String email;

    // constructor
    public Usuario(String nombre, String apellido, String DNI, RoleUsuario role, String email) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.DNI = DNI;
        this.role = role;
        this.email = email;
    }

    // getters y setters
    public String getDNI() {
        return DNI;
    }
    public String getEmail() {
        return email;
    }
    public static void setGestionUsuario(GestionAcceso gestionAcceso) {
    }

    // otros metodos
    public String getDniPorEmail(String email) {
        return DNI;
    }
}
