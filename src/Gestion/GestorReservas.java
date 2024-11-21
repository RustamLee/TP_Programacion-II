
package Gestion;

import Enumeraciones.EstadoHabitacion;
import Excepciones.HabitacionNoDisponibleException;
import Excepciones.HabitacionNoEncontradaException;
import Excepciones.ReservaNoEncontradaException;
import Modelo.Cliente;
import Modelo.Habitacion;
import Modelo.HistoriaReservas;
import Modelo.Reserva;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class GestorReservas {
    private Map<Integer, List<Reserva>> reservasPorHabitacion;
    private GestorHabitaciones gestorHabitaciones;
    private HistoriaReservas historiaReservas;

    // constructor
    public GestorReservas(GestorHabitaciones gestorHabitaciones, HistoriaReservas historiaReservas) {
        this.reservasPorHabitacion = new HashMap<>();
        this.gestorHabitaciones = gestorHabitaciones;
        this.historiaReservas = historiaReservas;
    }

    // getters y setters
    public Map<Integer, List<Reserva>> getReservasPorHabitacion() {
        return reservasPorHabitacion;
    }

    public GestorHabitaciones getGestorHabitaciones() {
        return gestorHabitaciones;
    }

    public HistoriaReservas getHistoriaReservas() {
        return historiaReservas;
    }

    // metodo check-in
    public void checkIn(Cliente cliente, int numeroHabitacion, LocalDate fechaEntrada, LocalDate fechaSalida) throws HabitacionNoDisponibleException, HabitacionNoEncontradaException {
        validarDatosCheckIn(cliente, numeroHabitacion, fechaEntrada, fechaSalida);
        Habitacion habitacion = obtenerHabitacion(numeroHabitacion);
        validarDisponibilidadHabitacion(habitacion, fechaEntrada, fechaSalida);
        realizarCheckIn(cliente, habitacion, fechaEntrada, fechaSalida);
    }

    // aux metodo para obtener una habitacion
    private Habitacion obtenerHabitacion(int numeroHabitacion) throws HabitacionNoEncontradaException {
        Habitacion habitacion = gestorHabitaciones.buscarHabitacionPorNumero(numeroHabitacion);
        if (habitacion == null) {
            throw new HabitacionNoEncontradaException("La habitación no existe.");
        }
        return habitacion;
    }

    // aux metodo para validar la disponibilidad de la habitacion
    private void validarDisponibilidadHabitacion(Habitacion habitacion, LocalDate fechaEntrada, LocalDate fechaSalida) throws HabitacionNoDisponibleException {
        if (!isHabitacionDisponible(habitacion, fechaEntrada, fechaSalida)) {
            throw new HabitacionNoDisponibleException("La habitación no está disponible en las fechas solicitadas.");
        }
    }

    // metodo aux para comprobar si la habitacion esta disponible en las fechas concretas
    private boolean isHabitacionDisponible(Habitacion habitacion, LocalDate fechaEntrada, LocalDate fechaSalida) {
        if (habitacion.getEstado() == EstadoHabitacion.REPARACION) {
            return false;
        }
        List<Reserva> reservas = reservasPorHabitacion.get(habitacion.getIdHabitacion());
        if (reservas == null || reservas.isEmpty()) {
            return true;
        }
        for (Reserva reserva : reservas) {
            if (fechaEntrada.isBefore(reserva.getFechaSalida()) && fechaSalida.isAfter(reserva.getFechaEntrada())) {
                return false;
            }
        }
        return true;
    }


    // metodo aux para realizar el check-in
    private void realizarCheckIn(Cliente cliente, Habitacion habitacion, LocalDate fechaEntrada, LocalDate fechaSalida) {
        habitacion.cambiarEstado(EstadoHabitacion.OCUPADO);
        Reserva nuevaReserva = new Reserva(cliente, habitacion, fechaEntrada, fechaSalida);
        reservasPorHabitacion.computeIfAbsent(habitacion.getIdHabitacion(), k -> new ArrayList<>()).add(nuevaReserva);
        System.out.println("Reserva realizada!");
    }

    //  metodo check-out
    public void checkOut(Cliente cliente, int idReserva) throws ReservaNoEncontradaException {
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser nulo");
        }
        Reserva reservaSeleccionada = null;
        for (List<Reserva> reservas : reservasPorHabitacion.values()) {
            for (Reserva reserva : reservas) {
                if (reserva.getCliente().equals(cliente) && reserva.getIdReserva() == idReserva) {
                    reservaSeleccionada = reserva;
                    break;
                }
            }
            if (reservaSeleccionada != null) {
                break;
            }
        }
        if (reservaSeleccionada == null) {
            throw new ReservaNoEncontradaException("No se encontró la reserva con IdReserva: " + idReserva);
        }
        Habitacion habitacion = reservaSeleccionada.getHabitacion();
        LocalDate fechaSalidaReal = LocalDate.now();
        reservaSeleccionada.actualizaDataSalida(fechaSalidaReal);
        habitacion.cambiarEstado(EstadoHabitacion.DISPONIBLE);
        reservaSeleccionada.setActive(false);
        historiaReservas.agregarReserva(reservaSeleccionada);
        List<Reserva> reservas = reservasPorHabitacion.get(habitacion.getIdHabitacion());
        if (reservas != null) {
            reservas.remove(reservaSeleccionada);
        }
        if (reservas == null || reservas.isEmpty()) {
            reservasPorHabitacion.remove(habitacion.getIdHabitacion());
        }

        System.out.println("Check-out realizado correctamente. La habitación está disponible.");
    }


    // metodo para mostrar todas las reservas
    public void mostrarReservas(Scanner scanner) {
        while (true) {
            System.out.println("===============================");
            System.out.println("Seleccione qué tipo de reservas desea ver:");
            System.out.println("1. Reservas activas (habitaciones ocupadas)");
            System.out.println("2. Reservas no activas (cerradas/completadas)");
            System.out.println("===============================");
            System.out.print("Seleccione una opción (1 о 2): ");
            String input = scanner.nextLine().trim();

            if (!input.isEmpty() && (input.equals("1") || input.equals("2"))) {
                int option = Integer.parseInt(input);

                if (option == 1) {
                    mostrarReservasActivas();
                    break;
                } else if (option == 2) {
                    mostrarReservasNoActivas();
                    break;
                }
            } else {
                System.out.println("Entrada no válida. Por favor, introduzca un número válido (1, 2 o 3).");
            }
        }
    }

    // metodo para mostrar las reservas activas
    private void mostrarReservasActivas() {
        System.out.println("Lista de reservas activas:");
        boolean isExiste = false;
        for (List<Reserva> reservas : reservasPorHabitacion.values()) {
            for (Reserva reserva : reservas) {
                System.out.println(reserva);
                isExiste = true;
            }
        }
        if (!isExiste) {
            System.out.println("No hay reservas activas.");
        }
    }

    // metodo para mostrar las reservas no activas
    private void mostrarReservasNoActivas() {
        System.out.println("Lista de reservas no activas (completadas):");
        List<Reserva> reservasCompletadas = historiaReservas.getReservasСompletadas();
        if (reservasCompletadas == null || reservasCompletadas.isEmpty()) {
            System.out.println("No hay reservas completadas.");
            return;
        }
        for (Reserva reserva : reservasCompletadas) {
            System.out.println(reserva);
        }
    }

    // metodo para mostrar las reservas de un cliente concreto por dni
    public void mostrarReservasPorCliente(String dni) {
        System.out.println("Lista de reservas para el cliente con DNI " + dni + ":");
        if (reservasPorHabitacion == null || reservasPorHabitacion.isEmpty()) {
            System.out.println("No hay reservas registradas.");
            return;
        }
        boolean hayReservas = false;
        for (Map.Entry<Integer, List<Reserva>> entry : reservasPorHabitacion.entrySet()) {
            List<Reserva> reservas = entry.getValue();
            for (Reserva reserva : reservas) {
                if (reserva.getCliente().getDNI().equals(dni)) {
                    System.out.println(reserva);
                    System.out.println("El precio total de la estancia es: " + reserva.getPrecioTotal() + "$");
                    hayReservas = true;
                }
            }
        }
        if (!hayReservas) {
            System.out.println("No hay reservas para este cliente.");
        }
    }

    // aux metodo para obtener una fecha (se usa en el menu)
    public LocalDate obtenerFecha(Scanner scanner, String mensaje) {
        DateTimeFormatter[] formatters = {
                DateTimeFormatter.ofPattern("yyyy-MM-dd"),
                DateTimeFormatter.ofPattern("dd-MM-yyyy"),
                DateTimeFormatter.ofPattern("MM/dd/yyyy")
        };
        LocalDate fecha = null;
        while (fecha == null) {
            try {
                System.out.print(mensaje);
                String fechaStr = scanner.nextLine().trim();
                if (fechaStr.isEmpty()) {
                    throw new IllegalArgumentException("La fecha no puede estar vacía. Intente nuevamente.");
                }
                for (DateTimeFormatter formatter : formatters) {
                    try {
                        fecha = LocalDate.parse(fechaStr, formatter);
                        break;
                    } catch (DateTimeParseException ignored) {
                    }
                }
                if (fecha == null) {
                    throw new IllegalArgumentException("Formato de fecha incorrecto. Intente nuevamente.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("Ocurrió un error inesperado: " + e.getMessage());
            }
        }
        return fecha;
    }


    // un metodo aux para validar los datos del check-in
    private void validarDatosCheckIn(Cliente cliente, int numeroHabitacion, LocalDate fechaEntrada, LocalDate fechaSalida) {
        if (cliente == null || numeroHabitacion <= 0 || fechaEntrada == null || fechaSalida == null) {
            throw new IllegalArgumentException("El cliente, el número de habitación o las fechas no pueden ser nulos");
        }
        if (fechaEntrada.isAfter(fechaSalida)) {
            throw new IllegalArgumentException("La fecha de entrada no puede ser posterior a la fecha de salida.");
        }
    }

}
