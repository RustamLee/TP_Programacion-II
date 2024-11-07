package Modelo;

import Enumeraciones.RoleUsuario;

import java.util.Objects;

public class Cliente extends Usuario {
    public String direccion;
    public String telefono;

    public Cliente(String nombre, String apellido, String DNI, RoleUsuario roleUsuario, String email, String direccion, String telefono) {
        super(nombre, apellido, DNI, roleUsuario, email);
        this.direccion = direccion;
        this.telefono = telefono;
    }

    // Getters y Setters
    public RoleUsuario getRole() {
        return role;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void mostrarCliente() {
        System.out.println("Nombre:" + nombre + " Apellido:" + apellido + " DNI:" + DNI + " Direccion:" + direccion + " Email:" + email + " Teléfono:" + telefono);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cliente cliente)) return false;
        return Objects.equals(direccion, cliente.direccion) && Objects.equals(telefono, cliente.telefono);
    }

    @Override
    public int hashCode() {
        return Objects.hash(direccion, telefono);
    }
}
