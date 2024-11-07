package Modelo;

import Enumeraciones.RoleUsuario;
import Interfaces.IAccionesUsuarios;

public class Cliente extends Usuario implements IAccionesUsuarios {
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
    public void login() {
        System.out.println("Login de Cliente");
    }

    @Override
    public void logout() {
        System.out.println("Logout de Cliente");
    }


}
