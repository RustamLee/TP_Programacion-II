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
    public void addClienteToCollection(Cliente c) throws ClienteYaExistenteException {
        if (clientes.containsKey(c.getDNI())) {
            throw new ClienteYaExistenteException("Cliente ya existe");
        }
        clientes.put(c.getDNI(), c);
        System.out.println("Cliente agregado con éxito.");
    }


    // el metodo para crear nuevoCliente
    public void agregarUsuarioAColeccion() {
        Scanner scanner = new Scanner(System.in);
        String nombre = obtenerDatoValido(scanner, "Ingrese el nombre del cliente: ", "El nombre no puede contener números/caracteres especiales/solo espacios.", "[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+");
        String apellido = obtenerDatoValido(scanner, "Ingrese el apellido del cliente: ", "El apellido no puede contener números/caracteres especiales/solo espacios.", "[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+");
        String DNI = obtenerDatoValido(scanner, "Ingrese el DNI del cliente: ", "El DNI no puede estar vacío o contener solo espacios.", "\\d+");
        if (clientes.containsKey(DNI)) {
            System.out.println("Ya existe un cliente con ese DNI. No se puede agregar.");
            return;
        }
        String email = obtenerDatoValido(scanner, "Ingrese el email del cliente: ", "El email no puede estar vacío o contener solo espacios.", "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
        String direccion = obtenerDatoValido(scanner, "Ingrese la direccion del cliente: ", "La dirección no puede estar vacía o contener solo espacios.", ".*");
        String telefono = obtenerDatoValido(scanner, "Ingrese el teléfono del cliente: ", "El teléfono no puede estar vacío o contener solo espacios.", "\\+?[0-9]+");
        RoleUsuario role = RoleUsuario.CLIENTE;
        Cliente cliente = new Cliente(nombre, apellido, DNI, role, email, direccion, telefono);
        try {
            addClienteToCollection(cliente);
        } catch (ClienteYaExistenteException e) {
            System.out.println(e.getMessage());
        }
    }

    // metodo para verificar datos ingresados
    private String obtenerDatoValido(Scanner scanner, String mensaje, String mensajeError, String patron) {
        String dato;
        while (true) {
            System.out.println(mensaje);
            dato = scanner.nextLine().trim();
            if (dato.isEmpty()) {
                System.out.println(mensajeError);
            } else if (!dato.matches(patron)) {
                System.out.println("El dato ingresado no es válido. Intente nuevamente.");
            } else {
                break;
            }
        }
        return dato;
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
    // no manejamos la excepción porque no la necesitamos aca
    public Cliente buscarClientePorEmail(String email) throws ClienteNoEncontradoException {
        for (Cliente c : clientes.values()) {
            if (c.getEmail().equals(email)) {
                return c;
            }
        }
        throw new ClienteNoEncontradoException("Cliente no encontrado");
    }

    // buscar clientes por DNI
    public Cliente buscarClientePorDNI(String DNI) throws ClienteNoEncontradoException {
        if (!clientes.containsKey(DNI)) {
            throw new ClienteNoEncontradoException("Cliente no encontrado");
        }
        return clientes.get(DNI);
    }

    // metodo para solicitar cliente
    public Cliente solicitarCliente(Scanner scanner) throws ClienteNoEncontradoException {
        String dni = null;
        while (true) {
            System.out.println("Ingrese el DNI del cliente (solo números, sin espacios): ");
            dni = scanner.nextLine().trim();
            if (dni.isEmpty()) {
                System.out.println("El DNI no puede estar vacío.");
                continue;
            }
            if (!dni.matches("\\d+")) { //
                System.out.println("El DNI debe contener solo números.");
                continue;
            }
            Cliente cliente = buscarClientePorDNI(dni); // Это может выбросить исключение
            if (cliente == null) {
                throw new ClienteNoEncontradoException("No se encontró un cliente con ese DNI."); // Прокидываем исключение
            }
            return cliente;
        }
    }
}

