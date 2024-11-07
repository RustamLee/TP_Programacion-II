package Modelo;

import Enumeraciones.RoleUsuario;
import Excepciones.ClienteNoEncontradoException;
import Excepciones.ClienteYaExistenteException;
import Excepciones.EmpleadoNoEncontradoException;
import Gestion.GestorAccesos;

import java.util.Scanner;

public class Menu {
    private GestorAccesos gestorAccesos;

    public Menu(GestorAccesos gestorAccesos) {
        this.gestorAccesos = gestorAccesos;
    }

    public void start() throws EmpleadoNoEncontradoException, ClienteNoEncontradoException, ClienteYaExistenteException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("===============================");
            System.out.println("      Sistema de Hotel");
            System.out.println("===============================");
            System.out.println("1. Iniciar sesion");
            System.out.println("2. Salir del programa");
            System.out.println("===============================");
            System.out.print("Seleccione una opcion: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            if (option == 1) {
                login(scanner);
            } else if (option == 2) {
                System.out.println("Saliendo del sistema...");
                break;
            } else {
                System.out.println("Opcion no válida. Por favor, seleccione una opción valida.");
            }
        }
        scanner.close();
    }

    private void login(Scanner scanner) throws EmpleadoNoEncontradoException, ClienteNoEncontradoException, ClienteYaExistenteException {
        System.out.println("===============================");
        System.out.println("       Iniciar sesion");
        System.out.println("===============================");
        System.out.print("Ingrese su correo electronico: ");
        String email = scanner.nextLine();
        System.out.print("Ingrese su contraseña: ");
        String password = scanner.nextLine();

        // Verificar el usuario utilizando
        Usuario usuario = gestorAccesos.verificarUsuario(email, password);

        if (usuario == null) {
            System.out.println("Volviendo al menu principal...");
            return;
        }
        RoleUsuario role = usuario.getRole();

        switch (role) {
            case ADMINISTRADOR:
                adminMenu(scanner);
                break;
            case RECEPCIONISTA:
                receptionistMenu(scanner);
                break;
            case CLIENTE:
                clientMenu(scanner);
                break;
            default:
                System.out.println("Error en el login.");
        }
    }

    private void adminMenu(Scanner scanner) throws EmpleadoNoEncontradoException {
        while (true) {
            System.out.println("===============================");
            System.out.println("       Menu Administrador");
            System.out.println("===============================");
            System.out.println("1. Agregar nuevo empleado"); //listo
            System.out.println("2. Eliminar empleado"); //listo
            System.out.println("3. Editar datos (telefono) de empleado"); //listo
            System.out.println("4. Mostrar datos de empleado por DNI"); //listo
            System.out.println("5. Mostrar todos los empleados"); //listo
            System.out.println("6. Agregar habitación");
            System.out.println("7. Eliminar habitación");
            System.out.println("8. Ver informes de reservas");
            System.out.println("9. Salir");
            System.out.println("===============================");
            System.out.print("Seleccione una opcion: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            if (option == 9) {
                break; // Salir del menú del Administrador
            }

            switch (option) {
                case 1:
                    gestorAccesos.getGestorEmpleado().agregarUsuarioAColeccion();
                    break;
                case 2:
                    gestorAccesos.getGestorEmpleado().eliminarEmpleadoDeColeccion(scanner);
                    break;
                case 3:
                    gestorAccesos.getGestorEmpleado().editarDatosEmpleado(scanner);
                    break;
                case 4:
                    gestorAccesos.getGestorEmpleado().mostrarEmpleadoPorDNI(scanner);
                    break;
                case 5:
                    gestorAccesos.getGestorEmpleado().mostrarEmpleados();
                    break;
                case 9:
                    return; // Salir del menú del Administrador
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    private void receptionistMenu(Scanner scanner) throws ClienteYaExistenteException {
        while (true) {
            System.out.println("===============================");
            System.out.println("     Menu Recepcionista");
            System.out.println("===============================");
            System.out.println("1. Registrar un cliente"); // listo
            System.out.println("2. Realizar Check-in");
            System.out.println("3. Realizar Check-out");
            System.out.println("4. Consultar disponibilidad de habitaciones");
            System.out.println("5. Listar habitaciones ocupadas");
            System.out.println("6. Listar habitaciones disponibles");
            System.out.println("7. Ver datos de un pasajero");
            System.out.println("8. Salir");
            System.out.println("===============================");
            System.out.print("Seleccione una opción: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            if (option == 8) {
                break; // Salir del menú del Recepcionista
            }
            switch (option) {
                case 1:
                    System.out.println("Registrar un cliente");
                    gestorAccesos.getGestorCliente().agregarUsuarioAColeccion();
                    break;
                case 2:
                    System.out.println("Check-in");
                    break;
                case 3:
                    System.out.println("Check-out");
                    break;
                case 8:
                    return; // Salir del menú del Recepcionista
                default:
                    System.out.println("Opción no válida.");
            }

        }
    }

    private void clientMenu(Scanner scanner) {
        while (true) {
            System.out.println("===============================");
            System.out.println("      Menu Cliente");
            System.out.println("===============================");
            System.out.println("1. Consultar mi reserva");
            System.out.println("2. Realizar una nueva reserva");
            System.out.println("3. Ver historial de estancias");
            System.out.println("4. Salir");
            System.out.println("===============================");
            System.out.print("Seleccione una opcion: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            if (option == 4) {
                break; // Salir del menu del Cliente
            }

            System.out.println("Funcionalidad no implementada aun.");
        }
    }
}
