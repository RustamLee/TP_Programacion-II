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
        GestionAcceso.cargarLoginContrasenaAColeccion(email, DNI);
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
    public RoleUsuario getRole() {
        return role;
    }
    public static void setGestionUsuario(GestionAcceso gestionAcceso) {
    }

    // otros metodos
    public String getDniPorEmail(String email) {
        return DNI;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", DNI='" + DNI + '\'' +
                ", role=" + role +
                ", email='" + email + '\'' +
                '}';
    }
}
