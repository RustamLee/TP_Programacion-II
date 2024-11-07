package Gestion;

import Enumeraciones.RoleUsuario;
import Excepciones.ClienteNoEncontradoException;
import Excepciones.ClienteYaExistenteException;
import Interfaces.IUserCreator;
import Modelo.Cliente;

import java.util.HashMap;
import java.util.Scanner;

public class GestorClientes implements IUserCreator {
    private HashMap<String, Cliente> clientes;// coleccion para almacenar clientes (key: DNI, value: Cliente)

    // constructor
    public GestorClientes() {
        this.clientes = new HashMap<>();
    }

    // getters y setters
    public HashMap<String, Cliente> getClientes() {
        return clientes;
    }

    // aux metodo para agregar un cliente a la coleccion (se usa en el metodo agregarUsuarioAColeccion)
    public void addClienteToCollection(Cliente c) {
        try {
            if (clientes.containsKey(c.getDNI())) {
                throw new ClienteYaExistenteException("Cliente ya existe");
            }
            clientes.put(c.getDNI(), c);
            System.out.println("Cliente agregado con éxito.");
        } catch (ClienteYaExistenteException e) {
            System.out.println(e.getMessage());
        }
    }

    // el metodo para crear nuevoCliente
    public void agregarUsuarioAColeccion() throws ClienteYaExistenteException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el nombre del cliente: ");
        String nombre = scanner.nextLine();
        System.out.println("Ingrese el apellido del cliente: ");
        String apellido = scanner.nextLine();
        System.out.println("Ingrese el DNI del cliente: ");
        String DNI = scanner.nextLine();
        RoleUsuario role = RoleUsuario.CLIENTE;
        System.out.println("Ingrese el email del cliente: ");
        String email = scanner.nextLine();
        System.out.println("Ingrese la direccion del cliente: ");
        String direccion = scanner.nextLine();
        System.out.println("Ingrese el telefono del cliente: ");
        String telefono = scanner.nextLine();
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

    // buscar clientes por email
    public Cliente buscarClientePorEmail(String email) throws ClienteNoEncontradoException {
        for (Cliente c : clientes.values()) {
            if (c.getEmail().equals(email)) {
                return c;
            }
        }
        throw new ClienteNoEncontradoException("Cliente no encontrado");
    }

    // metodos para mostrar todos los clientes
    public void mostrarClientes() {
        for (Cliente e : clientes.values()) {
            e.mostrarCliente();
        }
    }
}
