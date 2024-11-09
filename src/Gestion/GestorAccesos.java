package Gestion;

import Excepciones.ClienteNoEncontradoException;
import Excepciones.EmpleadoNoEncontradoException;
import Modelo.Cliente;
import Modelo.Empleado;
import Modelo.Usuario;

import java.util.HashMap;
import java.util.Scanner;

public class GestorAccesos {

    private GestorEmpleados gestorEmpleados;
    private GestorClientes gestorClientes;
    private static HashMap<String, String> loginContrasenas = new HashMap<>();

    // Constructor
    public GestorAccesos(GestorEmpleados gestorEmpleados, GestorClientes gestorClientes) {
        this.gestorEmpleados = gestorEmpleados;
        this.gestorClientes = gestorClientes;
    }

    // getters y setters
    public GestorEmpleados getGestorEmpleado() {
        return gestorEmpleados;
    }
    public GestorClientes getGestorCliente() {
        return gestorClientes;
    }
    public static HashMap<String, String> getLoginContrasenas() {
        return loginContrasenas;
    }

    // metodo para cargar login y contrasenas a la coleccion
    public static void cargarLoginContrasenaAColeccion(String email, String contrasena) {
        loginContrasenas.put(email, contrasena);
    }

    // el metodo para verificar que contrasena esta cambiada
    public Usuario verificarUsuario(String login, String contrasenaIngresada) {
        String contrasenaEnColeccion = loginContrasenas.get(login);
        if (contrasenaEnColeccion == null) {
            return null;
        }
        Usuario usuario = null;
        try {
            usuario = gestorEmpleados.buscarEmpleadoPorEmail(login);
        } catch (EmpleadoNoEncontradoException e) {
            System.err.println("Empleado no encontrado.");
        }
        if (usuario == null) {
            try {
                usuario = gestorClientes.buscarClientePorEmail(login);
            } catch (ClienteNoEncontradoException e) {
                System.out.println("Usuario no encontrado.");
                return null;
            }
        }
        if (contrasenaEnColeccion.equals(contrasenaIngresada)) {
            if (contrasenaEnColeccion.equals(usuario.getDNI())) {
                cambiarContrasena(usuario);
            }
            return usuario;
        } else {
            System.out.println("Contraseña incorrecta.");
        }
        return null;
    }

    // el metodo para cambiarContrasena
    public void cambiarContrasena(Usuario usuario) {
        Scanner scanner = new Scanner(System.in);
        String nuevaContrasena;
        String dni = usuario.getDNI();
        while (true) {
            System.out.println("Debe cambiar su contraseña. La nueva contraseña no puede ser igual al DNI.");
            System.out.print("Ingrese una nueva contraseña: ");
            nuevaContrasena = scanner.nextLine();
            if (nuevaContrasena == null || nuevaContrasena.isEmpty()) {
                System.out.println("La nueva contraseña no puede ser nula o vacía. Inténtelo de nuevo.");
            } else if (nuevaContrasena.equals(dni)) {
                System.out.println("La nueva contraseña no puede ser igual al DNI. Inténtelo de nuevo.");
            } else {
                break;
            }
        }
        loginContrasenas.put(usuario.getEmail(), nuevaContrasena);
        System.out.println("La contraseña se cambió correctamente.");
    }

    // metodo para buscar usuario por email
    public Usuario buscarUsuarioPorEmail(String email) throws EmpleadoNoEncontradoException, ClienteNoEncontradoException {
        Empleado empleado = gestorEmpleados.buscarEmpleadoPorEmail(email);
        if (empleado != null) {
            return empleado;
        }
        Cliente cliente = gestorClientes.buscarClientePorEmail(email);
        if (cliente != null) {
            return cliente;
        }
        return null;
    }




}
