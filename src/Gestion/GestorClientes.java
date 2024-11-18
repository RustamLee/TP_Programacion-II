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
        String nombre;
        while (true) {
            System.out.println("Ingrese el nombre del cliente: ");
            nombre = scanner.nextLine().trim();
            if (nombre.isEmpty() || !nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                System.out.println("El nombre no puede contener números/caracteres especiales/ solo espacios.");
            } else {
                break;
            }
        }
        String apellido;
        while (true) {
            System.out.println("Ingrese el apellido del cliente: ");
            apellido = scanner.nextLine().trim();
            if (apellido.isEmpty() || !apellido.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                System.out.println("El apellido no puede contener números/caracteres especiales/ solo espacios.");
            } else {
                break;
            }
        }
        String DNI;
        while (true) {
            System.out.println("Ingrese el DNI del cliente: ");
            DNI = scanner.nextLine().trim();
            if (DNI.isEmpty()) {
                System.out.println("El DNI no puede estar vacío o contener solo espacios.");
            } else {
                break;
            }
        }
        String email;
        while (true) {
            System.out.println("Ingrese el email del cliente: ");
            email = scanner.nextLine().trim();
            if (email.isEmpty()) {
                System.out.println("El email no puede estar vacío o contener solo espacios.");
            } else if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                System.out.println("El email no es válido. Por favor, ingrese un email válido (sin espacios).");
            } else {
                break;
            }
        }
        String direccion;
        while (true) {
            System.out.println("Ingrese la direccion del cliente: ");
            direccion = scanner.nextLine().trim();
            if (direccion.isEmpty()) {
                System.out.println("La dirección no puede estar vacía o contener solo espacios.");
            } else {
                break;
            }
        }
        String telefono;
        while (true) {
            System.out.println("Ingrese el teléfono del cliente: ");
            telefono = scanner.nextLine().trim();
            if (telefono.isEmpty()) {
                System.out.println("El teléfono no puede estar vacío o contener solo espacios.");
            } else if (!telefono.matches("\\+?[0-9]+")) {
                System.out.println("El teléfono no es válido. Debe contener solo números.");
            } else {
                break;
            }
        }

        RoleUsuario role = RoleUsuario.CLIENTE;
        Cliente cliente = new Cliente(nombre, apellido, DNI, role, email, direccion, telefono);
        addClienteToCollection(cliente);
    }


    // alternativa al metodo anterior
//    public void agregarUsuarioAColeccion() throws ClienteYaExistenteException {
//        String nombre, apellido, DNI, email, direccion, telefono;
//        Scanner scanner = new Scanner(System.in);
//        while (true) {
//            System.out.println("Ingrese el nombre del cliente: ");
//            nombre = scanner.nextLine();
//            if (nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ]+")) {
//                break;
//            } else {
//                System.out.println("intente nuevamente");
//            }
//        }
//        while (true) {
//            System.out.println("Ingrese el apellido del cliente: ");
//            apellido = scanner.nextLine();
//            if (apellido.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ]+")) {
//                break;
//            } else {
//                System.out.println("intente nuevamente");
//            }
//        }
//        while (true) {
//            System.out.println("Ingrese el DNI del cliente: ");
//            DNI = scanner.nextLine();
//            if (DNI.matches("\\d+")) {
//                break;
//            } else {
//                System.out.println("El DNI solo debe contener números. Intente nuevamente.");
//            }
//        }
//        RoleUsuario role = RoleUsuario.CLIENTE;
//        System.out.println("Ingrese el email del cliente: ");
//        email = scanner.nextLine();
//        System.out.println("Ingrese la direccion del cliente: ");
//        direccion = scanner.nextLine();
//        System.out.println("Ingrese el telefono del cliente: ");
//        telefono = scanner.nextLine();
//        Cliente cliente = new Cliente(nombre, apellido, DNI, role, email, direccion, telefono);
//        addClienteToCollection(cliente);
//    }

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

    // buscar clientes por DNI
    public Cliente buscarClientePorDNI(String DNI) {
        try {
            if (!clientes.containsKey(DNI)) {
                throw new ClienteNoEncontradoException("Cliente no encontrado");
            }
            return clientes.get(DNI);
        } catch (ClienteNoEncontradoException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    // metodos para mostrar todos los clientes
    public void mostrarClientes() {
        for (Cliente e : clientes.values()) {
            e.mostrarCliente();
        }
    }

    // metodo para solicitar cliente
    public Cliente solicitarCliente(Scanner scanner) {
        String dni = null;
        while (true) {
            System.out.println("Ingrese el DNI del cliente (solo nгmeros, sin espacios): ");
            dni = scanner.nextLine().trim();
            if (dni.isEmpty()) {
                System.out.println("El DNI no puede estar vacío.");
                continue;
            }
            if (!dni.matches("\\d+")) { //
                System.out.println("El DNI debe contener solo números.");
                continue;
            }
            Cliente cliente = buscarClientePorDNI(dni);
            if (cliente == null) {
                System.out.println("No se encontró un cliente con ese DNI.");
                return null;
            }
            return cliente;
        }
    }
}
