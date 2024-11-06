package Gestion;

import Enumeraciones.RoleUsuario;
import Excepciones.EmpleadoNoEncontradoException;
import Excepciones.EmpleadoYaExistenteException;
import Interfaces.IUserCreator;
import Modelo.Empleado;

import java.util.HashMap;

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
        System.out.println("Ingrese el nombre del empleado: ");
        String nombre = System.console().readLine();
        System.out.println("Ingrese el apellido del empleado: ");
        String apellido = System.console().readLine();
        System.out.println("Ingrese el DNI del empleado: ");
        String DNI = System.console().readLine();
        System.out.println("Ingrese el role del empleado ADMINISTRADOR or RECEPCIONISTA: ");
        RoleUsuario role = RoleUsuario.valueOf(System.console().readLine());
        System.out.println("Ingrese el email del empleado: ");
        String email = System.console().readLine();
        System.out.println("Ingrese el telefono del empleado: ");
        String telefono = System.console().readLine();
        Empleado empleado = new Empleado(nombre, apellido, DNI, role, email, telefono);
        try {
            addEmpleadoToCollection(empleado);
        } catch (EmpleadoYaExistenteException e) {
            System.out.println(e.getMessage());
        }
    }

    // metodo para eliminar cliente de la coleccion
    public void eliminarEmpleadoDeColeccion(String DNI) throws EmpleadoNoEncontradoException {
        if (!empleados.containsKey(DNI)) {
            throw new EmpleadoNoEncontradoException("Empleado no encontrado");
        }
        empleados.remove(DNI);
    }

    // get metodo para obtener coleccion empleados y loginContrasenas
    public HashMap<String, Empleado> getEmpleados() {
        return empleados;
    }

    public Empleado buscarEmpleadoPorEmail(String email) throws EmpleadoNoEncontradoException {
        for (Empleado e : empleados.values()) {
            if (e.getEmail().equals(email)) {
                return e; // Возвращаем e только если email совпадает
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
}
