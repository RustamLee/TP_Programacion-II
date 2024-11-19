
package Gestion;

import Enumeraciones.EstadoHabitacion;
import Excepciones.HabitacionNoDisponibleException;
import Excepciones.HabitacionNoEncontradaException;
import Excepciones.ReservaNoEncontradaException;
import Modelo.Cliente;
import Modelo.Habitacion;
import Modelo.Reserva;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class GestorReservas {
    private Map<Integer, List<Reserva>> reservasPorHabitacion;
    private GestorHabitaciones gestorHabitaciones;

    // constructor
    public GestorReservas(GestorHabitaciones gestorHabitaciones) {
        this.reservasPorHabitacion = new HashMap<>();
        this.gestorHabitaciones = gestorHabitaciones;
    }

    // getters y setters
    public Map<Integer, List<Reserva>> getReservasPorHabitacion() {
        return reservasPorHabitacion;
    }
    public GestorHabitaciones getGestorHabitaciones() {
        return gestorHabitaciones;
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
        if (habitacion.getEstado() != EstadoHabitacion.DISPONIBLE) {
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
    public void checkOut(Cliente cliente, Habitacion habitacion) throws ReservaNoEncontradaException {
        if (cliente == null || habitacion == null) {
            throw new IllegalArgumentException("El cliente o la habitación no pueden ser nulos");
        }
        List<Reserva> reservas = reservasPorHabitacion.getOrDefault(habitacion.getIdHabitacion(), new ArrayList<>());
        Reserva reservaActiva = null;
        for (Reserva reserva : reservas) {
            if (reserva.getCliente().equals(cliente)) {
                reservaActiva = reserva;
                break;
            }
        }
        if (reservaActiva == null) {
            throw new ReservaNoEncontradaException("No se encontró una reserva activa para este cliente.");
        }
        LocalDate fechaSalidaReal = LocalDate.now();
        reservaActiva.actualizaDataSalida(fechaSalidaReal);
        System.out.println("El precio total de la estancia es: " + reservaActiva.getPrecioTotal() + " EUR.");
        habitacion.cambiarEstado(EstadoHabitacion.DISPONIBLE);
        reservas.remove(reservaActiva);
        if (reservas.isEmpty()) {
            reservasPorHabitacion.remove(habitacion.getIdHabitacion());
        }
        System.out.println("Check-out realizado correctamente. La habitación está ahora disponible.");
    }


    // metodo para mostrar todas las reservas
    public void mostrarReservas() {
        System.out.println("Lista de todas las reservas:");
        if (reservasPorHabitacion == null || reservasPorHabitacion.isEmpty()) {
            System.out.println("No hay reservas registradas.");
            return;
        }

        for (Map.Entry<Integer, List<Reserva>> entry : reservasPorHabitacion.entrySet()) {
            Integer idHabitacion = entry.getKey();
            List<Reserva> reservas = entry.getValue();
            System.out.println("Habitacion №: " + idHabitacion);
            mostrarReservasParaHabitacion(reservas);
            System.out.println();
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

    // metodo para mostrar las reservas de una habitacion concreta por numero
    private void mostrarReservasParaHabitacion(List<Reserva> reservas) {
        if (reservas == null || reservas.isEmpty()) {
            System.out.println("No hay reservas para esta habitación.");
        } else {
            for (Reserva reserva : reservas) {
                System.out.println(reserva);
            }
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
                String fechaStr = scanner.nextLine();
                for (DateTimeFormatter formatter : formatters) {
                    try {
                        fecha = LocalDate.parse(fechaStr, formatter);
                        break;
                    } catch (Exception ignored) {
                    }
                }
                if (fecha == null) {
                    throw new Exception("Formato de fecha incorrecto. Intente nuevamente.");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
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
