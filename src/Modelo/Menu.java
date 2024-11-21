package Modelo;

import Enumeraciones.EstadoHabitacion;
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

    public void start() {
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


    private void login(Scanner scanner) {
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

    private void adminMenu(Scanner scanner) {
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
                        System.out.println("Error: El empleado no fue encontrado. " + e.getMessage());
                    } catch (Exception e) {
                        System.out.println("Ha ocurrido un error inesperado al eliminar el empleado: " + e.getMessage());
                    }
                    break;
                case 3:
                    try {
                        gestorAccesos.getGestorEmpleado().editarDatosEmpleado(scanner);
                    } catch (EmpleadoNoEncontradoException e) {
                        System.out.println("Error: El empleado no fue encontrado. " + e.getMessage());
                    } catch (Exception e) {
                        System.out.println("Ha ocurrido un error inesperado al editar los datos del empleado: " + e.getMessage());
                    }
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

    private void receptionistMenu(Scanner scanner) {
        while (true) {
            mostrarMenuRecepcionista();
            int option = obtenerOpcion(scanner);
            if (option == 9) break;
            switch (option) {
                case 1:
                    gestorAccesos.getGestorCliente().agregarUsuarioAColeccion();
                    break;
                case 2:
                    Cliente clienteCheckIn = null;
                    try {
                        clienteCheckIn = gestorAccesos.getGestorCliente().solicitarCliente(scanner);
                        if (clienteCheckIn == null) {
                            System.out.println("Cliente no encontrado. Intente nuevamente.");
                            break;
                        }
                    } catch (ClienteNoEncontradoException e) {
                        System.out.println("Error al buscar el cliente: " + e.getMessage());
                    }
                    int numeroHabitacion = -1;
                    while (numeroHabitacion <= 0) {
                        numeroHabitacion = gestorReservas.getGestorHabitaciones().solicitarNumeroHabitacion(scanner);
                        if (numeroHabitacion <= 0) {
                            System.out.println("Número de habitación inválido. Intente nuevamente.");
                        }
                    }

                    if (numeroHabitacion <= 0) {
                        break;  // Прерывание цикла, если была ошибка с номером комнаты
                    }

                    LocalDate fechaEntrada = gestorReservas.obtenerFecha(scanner, "Ingrese la fecha de entrada (yyyy-MM-dd o dd-MM-yyyy o MM/dd/yyyy): ");
                    LocalDate fechaSalida = gestorReservas.obtenerFecha(scanner, "Ingrese la fecha de salida (yyyy-MM-dd o dd-MM-yyyy o MM/dd/yyyy): ");
                    try {
                        gestorReservas.checkIn(clienteCheckIn, numeroHabitacion, fechaEntrada, fechaSalida);
                        System.out.println("Check-in realizado con éxito.");
                    } catch (HabitacionNoDisponibleException e) {
                        System.out.println("Error: La habitación no está disponible. " + e.getMessage());
                    } catch (Exception e) {
                        System.out.println("Ha ocurrido un error inesperado durante el check-in: " + e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println("Ingrese el DNI del cliente: ");
                    Cliente clienteCheckOut = null;
                    try {
                        clienteCheckOut = gestorAccesos.getGestorCliente().buscarClientePorDNI(scanner.nextLine());
                        if (clienteCheckOut == null) {
                            System.out.println("Cliente no encontrado.");
                            break;
                        }
                    } catch (ClienteNoEncontradoException e) {
                        System.out.println("Error al buscar el cliente: " + e.getMessage());
                        break;
                    }

                    // Mostrar todas las reservas activas del cliente
                    gestorReservas.mostrarReservasPorCliente(clienteCheckOut.getDNI());

                    // El cliente selecciona cuál reserva desea cancelar
                    System.out.println("Seleccione el número de la reserva que desea cancelar: ");
                    int numeroReserva;
                    try {
                        numeroReserva = Integer.parseInt(scanner.nextLine().trim());
                    } catch (NumberFormatException e) {
                        System.out.println("Número de reserva inválido.");
                        break;
                    }

                    // Llamar al metodo de check-out para la reserva seleccionada
                    try {
                        gestorReservas.checkOut(clienteCheckOut, numeroReserva);  // Metodo modificado para aceptar número de reserva
                    } catch (ReservaNoEncontradaException e) {
                        System.out.println("Error durante el check-out: " + e.getMessage());
                    } catch (Exception e) {
                        System.out.println("Ha ocurrido un error inesperado durante el check-out: " + e.getMessage());
                    }
                    break;

                case 4:
                    gestorReservas.getGestorHabitaciones().mostrarHabitacionesPorEstado(EstadoHabitacion.DISPONIBLE);
                    break;
                case 5:
                    gestorReservas.getGestorHabitaciones().listarHabitaciones(scanner);
                    break;
                case 6:
                    System.out.println("Ingrese el DNI del cliente: ");
                    Cliente cliente1 = null;
                    try {
                        cliente1 = gestorAccesos.getGestorCliente().buscarClientePorDNI(scanner.nextLine());
                        if (cliente1 != null) {
                            cliente1.mostrarCliente();
                        } else {
                            System.out.println("Cliente no encontrado.");
                        }
                    } catch (ClienteNoEncontradoException e) {
                        System.out.println("Error al buscar el cliente: " + e.getMessage());
                    }
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
