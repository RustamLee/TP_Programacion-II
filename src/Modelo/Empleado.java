package Modelo;

import Enumeraciones.RoleUsuario;

import java.util.Objects;

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
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void mostrarEmpleado() {
        System.out.println("Nombre:" + nombre + " Apellido:" + apellido + " DNI:" + DNI +"Role:"+ role+" Email:" + email + " Teléfono:" + telefono);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Empleado empleado)) return false;
        return Objects.equals(telefono, empleado.telefono);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(telefono);
    }
}
