package Gestion;

import Enumeraciones.RoleUsuario;
import Excepciones.ClienteNoEncontradoException;
import Excepciones.ClienteYaExistenteException;
import Interfaces.IUserCreator;
import Modelo.Cliente;

import java.util.Base64;
import java.util.HashMap;

public class GestionCliente implements IUserCreator {
    private HashMap<String, Cliente> clientes;// coleccion para almacenar clientes (key: DNI, value: Cliente)

    // constructor
    public GestionCliente() {
        this.clientes = new HashMap<>();
    }

    // aux metodo para agregar un cliente a la coleccion
    public void addClienteToCollection(Cliente e) throws ClienteYaExistenteException {
        if (clientes.containsKey(e.getDNI())) {
            throw new ClienteYaExistenteException("Cliente ya existe");
        }
        clientes.put(e.getDNI(), e);
    }

    // el metodo para crear nuevoCliente
    public void agregarUsuarioAColeccion() throws ClienteYaExistenteException {
        System.out.println("Ingrese el nombre del cliente: ");
        String nombre = System.console().readLine();
        System.out.println("Ingrese el apellido del cliente: ");
        String apellido = System.console().readLine();
        System.out.println("Ingrese el DNI del cliente: ");
        String DNI = System.console().readLine();
        RoleUsuario role = RoleUsuario.CLIENTE;
        System.out.println("Ingrese el email del cliente: ");
        System.out.println("Ingrese la direccion del cliente: ");
        String direccion = System.console().readLine();
        String email = System.console().readLine();
        System.out.println("Ingrese el telefono del empleado: ");
        String telefono = System.console().readLine();
        Cliente cliente = new Cliente(nombre, apellido, DNI, role, email, direccion, telefono);
        addClienteToCollection(cliente);
    }

    // metodo para eliminar cliente de la coleccion
    public void eliminarClienteDeColeccion(String DNI) throws ClienteNoEncontradoException {
        if (!clientes.containsKey(DNI)) {
            throw new ClienteNoEncontradoException("Cliente no encontrado");
        }
        clientes.remove(DNI);
    }

    // get metodo para obtener coleccion clientes y loginContrasenas
    public HashMap<String, Cliente> getClientes() {
        return clientes;
    }

    // buscar clientes por email
    public Cliente buscarClientePorEmail(String email) throws ClienteNoEncontradoException {
        for (Cliente с : clientes.values()) {
            if (с.getEmail().equals(email)) {
                return с;
            }
        }
        throw new ClienteNoEncontradoException("Cliente no encontrado");
    }

    // metodos para mostrar los datos de emppleados
    public void mostrarCliente(){
        for (Cliente e : clientes.values()){
            e.mostrarDatos();
        }
    }
}
