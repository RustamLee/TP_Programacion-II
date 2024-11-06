package Gestion;

import Enumeraciones.RoleUsuario;
import Excepciones.EmpleadoNoEncontradoException;
import Excepciones.EmpleadoYaExistenteException;
import Interfaces.IUserCreator;
import Modelo.Empleado;

import java.util.HashMap;
import java.util.Scanner;

public class GestionEmpleado implements IUserCreator {
    private HashMap<String, Empleado> empleados;// coleccion para almacenar empleados (key: DNI, value: Empleado)

    // constructor
    public GestionEmpleado() {
        this.empleados = new HashMap<>();
    }

    // aux metodo para agregar un empleado a la coleccion
    public void addEmpleadoToCollection(Empleado e) throws EmpleadoYaExistenteException {
        if (empleados.containsKey(e.getDNI())) {
            throw new EmpleadoYaExistenteException("Empleado ya existe");
        }
        empleados.put(e.getDNI(), e);
    }

    // el metodo para cargar nuevoEmpleado
    public void agregarUsuarioAColeccion() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el nombre del empleado: ");
        String nombre = scanner.nextLine();
        System.out.println("Ingrese el apellido del empleado: ");
        String apellido = scanner.nextLine();
        System.out.println("Ingrese el DNI del empleado: ");
        String DNI = scanner.nextLine();
        if (nombre.isEmpty() || apellido.isEmpty() || DNI.isEmpty()) {
            System.out.println("Error: Todos los campos deben ser completos.");
            return;
        }
        System.out.println("Ingrese el rol del empleado (ADMINISTRADOR o RECEPCIONISTA): ");
        String roleInput = scanner.nextLine();
        RoleUsuario role = null;
        try {
            role = RoleUsuario.valueOf(roleInput.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: Rol no válido.");
            return;
        }
        System.out.println("Ingrese el email del empleado: ");
        String email = scanner.nextLine();
        System.out.println("Ingrese el teléfono del empleado: ");
        String telefono = scanner.nextLine();

        Empleado empleado = new Empleado(nombre, apellido, DNI, role, email, telefono);
        try {
            addEmpleadoToCollection(empleado);
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

    // get metodo para obtener coleccion empleados y loginContrasenas
    public HashMap<String, Empleado> getEmpleados() {
        return empleados;
    }

    public Empleado buscarEmpleadoPorEmail(String email) throws EmpleadoNoEncontradoException {
        for (Empleado e : empleados.values()) {
            if (e.getEmail().equals(email)) {
                return e;
            }
        }
        throw new EmpleadoNoEncontradoException("Empleado no encontrado");
    }    // buscar empleado por email


    // metodos para mostrar los datos de emppleados
    public void mostrarEmpleados() {
        for (Empleado e : empleados.values()) {
            e.mostrarEmpleado();
        }
    }

    // metodos para editar los datos de un empleado
    public void editarDatosEmpleado(Scanner scanner) throws EmpleadoNoEncontradoException {
        System.out.print("Ingrese el DNI del empleado para actualizar el teléfono: ");
        String DNI = scanner.nextLine();
        Empleado empleado = empleados.get(DNI);
        if (empleado == null) {
            throw new EmpleadoNoEncontradoException("Empleado con DNI " + DNI + " no encontrado.");
        }
        System.out.print("Ingrese el nuevo teléfono para el empleado con DNI " + DNI + ": ");
        String nuevoTelefono = scanner.nextLine();
        empleado.setTelefono(nuevoTelefono);
        System.out.println("El teléfono del empleado con DNI " + DNI + " ha sido actualizado a " + nuevoTelefono + ".");
    }

}
