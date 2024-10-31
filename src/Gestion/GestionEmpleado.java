package Gestion;

import Enumeraciones.RoleUsuario;
import Excepciones.EmpleadoNoEncontradoException;
import Excepciones.EmpleadoYaExistenteException;
import Modelo.Empleado;

import java.util.Base64;
import java.util.HashMap;

public class GestionEmpleado {
    private HashMap<String, Empleado> empleados;// coleccion para almacenar empleados (key: DNI, value: Empleado)
    private HashMap<String, String> loginContrasenas; // coleccion para almacenar login y contrasenas (key: login, value: contrasena)

    // constructor
    public GestionEmpleado() {
        this.empleados = new HashMap<>();
        this.loginContrasenas = new HashMap<>();
    }

    // el metodo para crear nuevoEmpleado
    public void cargarEmpleadoAColeccion () {
        Empleado empleado = obtenerDatosEmpleado();
        try {
            addEmpleadoToCollection(empleado);
            addLoginYContrasenaAColecion(empleado);
        } catch (EmpleadoYaExistenteException e) {
            System.out.println(e.getMessage());
        }
    }

    // aux metodo para agregar un empleado a la coleccion
    public void addEmpleadoToCollection(Empleado e){
        empleados.put(e.getDNI(), e);
    }

    // aux metodo para crear login y contrasena para el empleado y cargarlo a la coleccion loginContrasenas
    public void addLoginYContrasenaAColecion(Empleado e){
        String login = e.getEmail();
        //String contrasena = Base64.getEncoder().encodeToString(e.getDNI().getBytes());
        String contrasena = e.getDNI();
        loginContrasenas.put(login, contrasena);
    }

    // get metodo para obtener coleccion empleados y loginContrasenas
    public HashMap<String, Empleado> getEmpleados() {
        return empleados;
    }
    public HashMap<String, String> getLoginContrasenas() {
        return loginContrasenas;
    }

    // buscar empleado por email
    public Empleado buscarEmpleadoPorEmail(String email) throws EmpleadoNoEncontradoException {
        for (Empleado e : empleados.values()) {
            if (e.getEmail().equals(email)) {
                return e; // Возвращаем e только если email совпадает
            }
        }
        throw new EmpleadoNoEncontradoException("Empleado no encontrado");
    }

    // un metodo que lee datos del usuario en la consola y devuelve un objeto Empleado
    public Empleado obtenerDatosEmpleado(){
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
        return new Empleado(nombre, apellido, DNI, role,email,telefono);
    }

    // metodos para mostrar los datos de emppleados
    public void mostrarEmpleados(){
        for (Empleado e : empleados.values()){
            e.mostrarEmpleado();
        }
    }
    public void mostrarLoginContrasenas(){
        for (String login : loginContrasenas.keySet()){
            System.out.println("Login: " + login + " Contrasena: " + loginContrasenas.get(login));
        }
    }
}
