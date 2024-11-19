package Modelo;

import Enumeraciones.RoleUsuario;
import Excepciones.*;
import Gestion.GestorAccesos;
import Gestion.GestorReservas;

import java.time.LocalDate;
import java.util.Scanner;

public class Menu {
    // hacemos eses atributos finales para que no se puedan modificar
    private final GestorAccesos gestorAccesos;
    private final GestorReservas gestorReservas;

    public Menu(GestorAccesos gestorAccesos, GestorReservas gestorReservas) {
        this.gestorAccesos = gestorAccesos;
        this.gestorReservas = gestorReservas;
    }

    public void start() throws EmpleadoNoEncontradoException, ClienteNoEncontradoException, ClienteYaExistenteException, HabitacionNoEncontradaException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("===============================");
            System.out.println("      Sistema de Hotel");
            System.out.println("===============================");
            System.out.println("1. Iniciar sesion");
            System.out.println("2. Salir del programa");
            System.out.println("===============================");
            System.out.print("Seleccione una opcion: ");
            int option = obtenerOpcion(scanner);
            if (option == 1) {
                login(scanner);
            } else if (option == 2) {
                System.out.println("Saliendo del sistema...");
                break;
            }
        }
        scanner.close();
    }


    private void login(Scanner scanner) throws EmpleadoNoEncontradoException, ClienteNoEncontradoException, ClienteYaExistenteException, HabitacionNoEncontradaException {
        System.out.println("===============================");
        System.out.println("       Iniciar sesión");
        System.out.println("===============================");
        System.out.print("Ingrese su correo electronico: ");
        String email = scanner.nextLine().trim();
        if (email.isEmpty()) {
            System.out.println("El correo electrónico no puede estar vacío o contener solo espacios.");
            System.out.println("Volviendo al menú principal...");
            return;
        }
        System.out.print("Ingrese su contraseña: ");
        String password = scanner.nextLine().trim();
        if (password.isEmpty()) {
            System.out.println("La contraseña no puede estar vacía o contener solo espacios.");
            System.out.println("Volviendo al menú principal...");
            return;
        }
        Usuario usuario = gestorAccesos.verificarUsuario(email, password);
        if (usuario == null) {
            System.out.println("Volviendo al menú principal...");
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
                Cliente cliente = (Cliente) usuario;
                clientMenu(scanner, cliente);
                break;
            default:
                System.out.println("Error en el login.");
        }
    }

    private void adminMenu(Scanner scanner) throws EmpleadoNoEncontradoException {
        while (true) {
            mostrarMenuAdmin();
            int option = obtenerOpcion(scanner);
            if (option == 10) break;
            switch (option) {
                case 1:
                    gestorAccesos.getGestorEmpleado().agregarUsuarioAColeccion();
                    break;
                case 2:
                    try {
                        gestorAccesos.getGestorEmpleado().eliminarEmpleadoDeColeccion(scanner);
                    } catch (EmpleadoNoEncontradoException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
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
                case 6:
                    gestorReservas.getGestorHabitaciones().listarHabitaciones(scanner);
                    break;
                case 7:
                    gestorReservas.getGestorHabitaciones().agregarHabitacion();
                    break;
                case 8:
                    gestorReservas.getGestorHabitaciones().eliminarHabitacionDeColeccion(scanner);
                    break;
                case 9:
                    gestorReservas.mostrarReservas(scanner);
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    private void receptionistMenu(Scanner scanner) throws ClienteYaExistenteException, HabitacionNoEncontradaException {
        while (true) {
            mostrarMenuRecepcionista();
            int option = obtenerOpcion(scanner);
            if (option == 9) break;
            switch (option) {
                case 1:
                    gestorAccesos.getGestorCliente().agregarUsuarioAColeccion();
                    break;
                case 2:
                    Cliente clienteCheckIn = gestorAccesos.getGestorCliente().solicitarCliente(scanner);
                    if (clienteCheckIn == null) {
                        System.out.println("Cliente no encontrado. Intente nuevamente.");
                        break;
                    }
                    int numeroHabitacion = -1;
                    while (numeroHabitacion <= 0) {
                        numeroHabitacion = gestorReservas.getGestorHabitaciones().solicitarNumeroHabitacion(scanner);
                        if (numeroHabitacion <= 0) {
                            System.out.println("Número de habitación inválido. Intente nuevamente.");
                        }
                    }
                    LocalDate fechaEntrada = gestorReservas.obtenerFecha(scanner, "Ingrese la fecha de entrada (yyyy-MM-dd o dd-MM-yyyy o MM/dd/yyyy): ");
                    LocalDate fechaSalida = gestorReservas.obtenerFecha(scanner, "Ingrese la fecha de salida (yyyy-MM-dd o dd-MM-yyyy o MM/dd/yyyy): ");
                    try {
                        gestorReservas.checkIn(clienteCheckIn, numeroHabitacion, fechaEntrada, fechaSalida);
                        System.out.println("Check-in realizado con éxito.");
                    } catch (HabitacionNoDisponibleException | HabitacionNoEncontradaException e) {
                        System.out.println("Error durante el check-in: " + e.getMessage());
                    } catch (Exception e) {
                        System.out.println("Ha ocurrido un error inesperado: " + e.getMessage());
                    }
                    break;

                case 3:
                    System.out.println("Ingrese el DNI del cliente: ");
                    Cliente clienteCheckOut = gestorAccesos.getGestorCliente().buscarClientePorDNI(scanner.nextLine());
                    if (clienteCheckOut == null) {
                        System.out.println("Cliente no encontrado.");
                        break;
                    }
                    System.out.println("Ingrese el número de habitación: ");
                    Habitacion habitacionCheckOut = gestorReservas.getGestorHabitaciones().buscarHabitacionPorNumero(scanner.nextInt());
                    scanner.nextLine();
                    if (habitacionCheckOut == null) {
                        System.out.println("Habitación no encontrada.");
                        break;
                    }
                    try {
                        gestorReservas.checkOut(clienteCheckOut, habitacionCheckOut);
                    } catch (ReservaNoEncontradaException e) {
                        System.out.println("Error durante el check-out: " + e.getMessage());
                    } catch (Exception e) {
                        System.out.println("Ha ocurrido un error inesperado: " + e.getMessage());
                    }
                    break;

                case 4:
                    gestorReservas.getGestorHabitaciones().mostrarHabitacionesDisponibles();
                    break;
                case 5:
                    gestorReservas.getGestorHabitaciones().listarHabitaciones(scanner);
                    break;
                case 6:
                    System.out.println("Ingrese el DNI del cliente: ");
                    Cliente cliente1 = gestorAccesos.getGestorCliente().buscarClientePorDNI(scanner.nextLine());
                    cliente1.mostrarCliente();
                    break;
                case 7:
                    gestorReservas.mostrarReservas(scanner);
                    break;
                case 8:
                    gestorReservas.getGestorHabitaciones().cambiarEstadoHabitacion(scanner);
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    private void clientMenu(Scanner scanner, Cliente cliente) {
        while (true) {
            mostrarMenuCliente();
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Entrada no válida. Por favor, introduzca un número.");
                continue;
            }
            try {
                int option = Integer.parseInt(input);
                if (option == 3) break;
                switch (option) {
                    case 1:
                        gestorReservas.mostrarReservasPorCliente(cliente.getDNI());
                        break;
                    case 2:
                        gestorReservas.getHistoriaReservas().mostrarReservasCompletadasPorDni(cliente.getDNI());
                        break;
                    default:

                        System.out.println("Opción no válida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Por favor, introduzca un número.");
            }
        }
    }

    // aux metodo para obtener el nuero de opcion
    private int obtenerOpcion(Scanner scanner) {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Por favor, introduzca un número.");
            }
        }
    }

    private void mostrarMenuAdmin() {
        System.out.println("===============================");
        System.out.println("       Menu Administrador");
        System.out.println("===============================");
        System.out.println("1. Agregar nuevo empleado");
        System.out.println("2. Eliminar empleado");
        System.out.println("3. Editar datos de empleado");
        System.out.println("4. Mostrar datos de empleado por DNI");
        System.out.println("5. Mostrar todos los empleados");
        System.out.println("6. Listar habitaciones");
        System.out.println("7. Agregar habitación");
        System.out.println("8. Eliminar habitación");
        System.out.println("9. Ver informes de reservas");
        System.out.println("10. Salir");
        System.out.println("===============================");
    }

    private void mostrarMenuRecepcionista() {
        System.out.println("===============================");
        System.out.println("     Menu Recepcionista");
        System.out.println("===============================");
        System.out.println("1. Registrar un cliente");
        System.out.println("2. Realizar Check-in");
        System.out.println("3. Realizar Check-out");
        System.out.println("4. Consultar disponibilidad de habitaciones");
        System.out.println("5. Listar habitaciones");
        System.out.println("6. Ver datos de un cliente");
        System.out.println("7. Mostrar todas las reservas");
        System.out.println("8. Cambiar estado de una habitación");
        System.out.println("9. Salir");
        System.out.println("===============================");
    }

    private void mostrarMenuCliente() {
        System.out.println("===============================");
        System.out.println("      Menu Cliente");
        System.out.println("===============================");
        System.out.println("1. Consultar mi reserva activa");
        System.out.println("2. Ver mi historial de estancias");
        System.out.println("3. Salir");
        System.out.println("===============================");
        System.out.print("Seleccione una opcion: ");
    }

}
