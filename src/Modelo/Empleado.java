package Modelo;

import Enumeraciones.RoleUsuario;

public class Empleado extends Usuario {
    public String telefono;

    // Constructor

    public Empleado(String nombre, String apellido, String DNI, RoleUsuario role, String email, String telefono) {
        super(nombre, apellido, DNI, role, email);
        if (role != RoleUsuario.ADMINISTRADOR && role != RoleUsuario.RECEPCIONISTA) {
            throw new IllegalArgumentException("Role solo puede ser ADMINISTRADOR or RECEPCIONISTA");
        }
        this.telefono = telefono;
    }

    // Getters y Setters
    public RoleUsuario getRole() {
        return role;
    }

    public void mostrarEmpleado() {
        System.out.println("Nombre:" + nombre + " Apellido:" + apellido + " DNI:" + DNI + " Email:" + email + " Teléfono:" + telefono);
    }
}
