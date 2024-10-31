package Gestion;

import Excepciones.EmpleadoNoEncontradoException;
import Interfaces.IAccionesUsuarios;
import Interfaces.ICifradorContrasena;

import java.util.Base64;
import java.util.Scanner;

public class GestionAcceso implements ICifradorContrasena, IAccionesUsuarios {

    private GestionEmpleado gestionEmpleado;

    // Constructor
    public GestionAcceso(GestionEmpleado gestionEmpleado) {
        this.gestionEmpleado = gestionEmpleado;
    }

    // el metodo para verificar que contrasena esta cambiada
    public boolean verificarLoginContrasena(String login, String contrasenaIngresada) {
        try{
            String contrasenaEnColeccion = gestionEmpleado.getLoginContrasenas().get(login);
            String dniEmpleado = gestionEmpleado.buscarEmpleadoPorEmail(login).getDNI();
            if (contrasenaEnColeccion != null) {
                if ((contrasenaEnColeccion.equals(dniEmpleado)) && (contrasenaEnColeccion.equals(contrasenaIngresada))) {
                    cambiarContrasena(login);
                    return true;
                }
                return contrasenaEnColeccion.equals(contrasenaIngresada);
            }
        } catch (EmpleadoNoEncontradoException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    // el metodo para cambiarContrasena
    public void cambiarContrasena(String email) throws EmpleadoNoEncontradoException {
        Scanner scanner = new Scanner(System.in);
        try{
            String dniEmpleado = gestionEmpleado.buscarEmpleadoPorEmail(email).getDNI();
            String nuevaContrasena;
            while (true) {
                System.out.println("Tenes que cambiar su contraseña. La nueva contraseña no puede ser igual al DNI.");
                System.out.print("Ingrese una nueva contraseña: ");
                nuevaContrasena = scanner.nextLine();
                if (nuevaContrasena == null || nuevaContrasena.isEmpty()) {
                    System.out.println("La nueva contraseña no puede ser nula o vacía. Inténtelo de nuevo.");
                }else if (nuevaContrasena.equals(dniEmpleado)) {
                    System.out.println("La nueva contraseña no puede ser igual al DNI. Inténtelo de nuevo.");
                } else {
                    break;
                }
            }
            gestionEmpleado.getLoginContrasenas().put(email, nuevaContrasena);
            System.out.println("La contraseña se cambió correctamente.");
        } catch (EmpleadoNoEncontradoException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String cifrarContrasena(String contrasena) {
        return Base64.getEncoder().encodeToString(contrasena.getBytes());
    }

    @Override
    public void login() {
        System.out.println("Para iniciar sesión, ingrese su email:");
    }

    @Override
    public void logout() {
        System.out.println("Logout");
    }
}
