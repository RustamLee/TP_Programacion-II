package Gestion;

import Enumeraciones.RoleUsuario;
import Excepciones.EmpleadoNoEncontradoException;
import Excepciones.EmpleadoYaExistenteException;
import Interfaces.IUserCreator;
import Modelo.Empleado;

import java.util.HashMap;
import java.util.Scanner;

public class GestorEmpleados implements IUserCreator {
    private HashMap<String, Empleado> empleados;// coleccion para almacenar empleados (key: DNI, value: Empleado)

    // constructor
    public GestorEmpleados() {
        this.empleados = new HashMap<>();
    }

    // aux metodo para agregar un empleado a la coleccion
    public void addEmpleadoACollection(Empleado e) throws EmpleadoYaExistenteException {
        if (empleados.containsKey(e.getDNI())) {
            throw new EmpleadoYaExistenteException("Empleado con DNI " + e.getDNI() + " ya existe.");
        }
        empleados.put(e.getDNI(), e);
    }

    // el metodo para cargar nuevo Empleado
    public void agregarUsuarioAColeccion() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el nombre del empleado (sin espacios): ");
        String nombre = scanner.nextLine().trim();
        if (nombre.isEmpty() || !nombre.matches("[a-zA-Z]+")) {
            System.out.println("Error: El nombre debe contener solo letras y no estar vacío.");
            return;
        }
        System.out.println("Ingrese el apellido del empleado (sin espacios): ");
        String apellido = scanner.nextLine().trim();
        if (apellido.isEmpty() || !apellido.matches("[a-zA-Z]+")) {
            System.out.println("Error: El apellido debe contener solo letras y no estar vacío.");
            return;
        }
        System.out.println("Ingrese el DNI del empleado: ");
        String DNI = scanner.nextLine().trim();
        if (DNI.isEmpty()) {
            System.out.println("Error: El DNI no puede estar vacío.");
            return;
        }
        if (!DNI.matches("\\d+")) {
            System.out.println("Error: El DNI debe contener solo números.");
            return;
        }
        System.out.println("Ingrese el rol del empleado (ADMINISTRADOR o RECEPCIONISTA): ");
        String roleInput = scanner.nextLine().trim();
        RoleUsuario role = null;
        try {
            role = RoleUsuario.valueOf(roleInput.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: Rol no válido.");
            return;
        }
        System.out.println("Ingrese el email del empleado: ");
        String email = scanner.nextLine().trim();
        if (email.isEmpty()) {
            System.out.println("Error: El email no puede estar vacío o contener solo espacios.");
            return;
        }
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        if (!email.matches(emailRegex)) {
            System.out.println("Error: El email no tiene un formato válido.");
            return;
        }
        System.out.println("Ingrese el teléfono del empleado: ");
        String telefono = scanner.nextLine().trim();
        if (telefono.isEmpty()) {
            System.out.println("Error: El teléfono no puede estar vacío o contener solo espacios.");
            return;
        }
        Empleado empleado = new Empleado(nombre, apellido, DNI, role, email, telefono);
        try {
            addEmpleadoACollection(empleado);
            System.out.println("Empleado agregado con éxito.");
        } catch (EmpleadoYaExistenteException e) {
            System.out.println(e.getMessage());
        }
    }


    // metodo para eliminar empleado de la coleccion
    public void eliminarEmpleadoDeColeccion(Scanner scanner) throws EmpleadoNoEncontradoException {
        System.out.print("Ingrese el número de DNI del empleado a eliminar: ");
        String DNI = scanner.nextLine();

        if (!empleados.containsKey(DNI)) {
            throw new EmpleadoNoEncontradoException("Empleado no encontrado");
        }
        empleados.remove(DNI);
        System.out.println("Empleado con DNI " + DNI + " ha sido eliminado.");
    }

    // metodo para mostrar datos de un empleado
    public void mostrarEmpleadoPorDNI(Scanner scanner) {
        System.out.print("Ingrese el número de DNI del empleado a mostrar: ");
        String DNI = scanner.nextLine();

        if (!empleados.containsKey(DNI)) {
            System.out.println("Empleado no encontrado con el DNI: " + DNI);
            return;
        }
        empleados.get(DNI).mostrarEmpleado();
    }

    // get metodo para obtener coleccion empleados
    public HashMap<String, Empleado> getEmpleados() {
        return empleados;
    }

    // buscar empleados por email
    public Empleado buscarEmpleadoPorEmail(String email) throws EmpleadoNoEncontradoException {
        for (Empleado e : empleados.values()) {
            if (e.getEmail().equals(email)) {
                return e;
            }
        }
        throw new EmpleadoNoEncontradoException("Empleado no encontrado");
    }

    // metodos para mostrar los datos de emppleados
    public void mostrarEmpleados() {
        for (Empleado e : empleados.values()) {
            e.mostrarEmpleado();
        }
    }

    // metodos para editar los datos de un empleado
    public void editarDatosEmpleado(Scanner scanner) throws EmpleadoNoEncontradoException {
        System.out.print("Ingrese el DNI del empleado para actualizar los datos: ");
        String DNI = scanner.nextLine();
        Empleado empleado = empleados.get(DNI);
        if (empleado == null) {
            System.out.println("Empleado con DNI " + DNI + " no encontrado.");
            return;
        }
        cambiarTelefono(scanner, empleado);
        cambiarEmail(scanner, empleado);
        cambiarRole(scanner, empleado);
        System.out.println("Datos actualizados correctamente.");
    }

    // aux metodo para cambiar Role
    public void cambiarRole(Scanner scanner, Empleado empleado) {
        RoleUsuario rolActual = empleado.getRole();
        RoleUsuario nuevoRole = null;
        String mensaje = "";
        if (rolActual == RoleUsuario.ADMINISTRADOR) {
            mensaje = "El empleado tiene el role Administrador. Desea cambiar el rol a RECEPCIONISTA?";
            nuevoRole = RoleUsuario.RECEPCIONISTA;
        }
        else if (rolActual == RoleUsuario.RECEPCIONISTA) {
            mensaje = "El empleado tiene el role Recepcionista. Desea cambiar el rol a ADMINISTRADOR?";
            nuevoRole = RoleUsuario.ADMINISTRADOR;
        }
        System.out.print(mensaje + " (s/n): ");
        String respuesta = scanner.nextLine();

        if (respuesta.equalsIgnoreCase("s")) {
            empleado.setRole(nuevoRole);
            System.out.println("El rol del empleado ha sido actualizado a " + nuevoRole + ".");
        } else {
            System.out.println("No se cambió el rol.");
        }
    }

    // aux metodo para cambiar telefono
    public void cambiarTelefono(Scanner scanner, Empleado empleado) {
        System.out.print("¿Desea cambiar el teléfono del empleado? (s/n): ");
        String respuesta = scanner.nextLine();
        if (respuesta.equalsIgnoreCase("s")) {
            System.out.print("Ingrese el nuevo teléfono: ");
            String nuevoTelefono = scanner.nextLine();

            if (!nuevoTelefono.isEmpty()) {
                empleado.setTelefono(nuevoTelefono);
                System.out.println("El teléfono ha sido actualizado a " + nuevoTelefono + ".");
            } else {
                System.out.println("No se introdujo un número de teléfono válido.");
            }
        } else {
            System.out.println("No se cambió el teléfono.");
        }
    }

    // aux metodo para cambiar email

    public void cambiarEmail(Scanner scanner, Empleado empleado) {
        System.out.print("¿Desea cambiar el email del empleado? (s/n): ");
        String respuesta = scanner.nextLine();

        if (respuesta.equalsIgnoreCase("s")) {
            System.out.print("Ingrese el nuevo email: ");
            String nuevoEmail = scanner.nextLine();

            if (!nuevoEmail.isEmpty()) {
                empleado.setEmail(nuevoEmail);
                System.out.println("El email ha sido actualizado a " + nuevoEmail + ".");
            } else {
                System.out.println("No se introdujo un email válido.");
            }
        } else {
            System.out.println("No se cambió el email.");
        }
    }



}
