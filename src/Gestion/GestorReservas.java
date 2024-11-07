package Gestion;

import Enumeraciones.EstadoHabitacion;
import Excepciones.HabitacionNoDisponibleException;
import Excepciones.HabitacionNoEncontradaException;
import Excepciones.ReservaNoEncontradaException;
import Modelo.Cliente;
import Modelo.Habitacion;
import Modelo.Reserva;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    // metodo para realizar checkIN
    public void checkIN(Cliente cliente, int numeroHabitacion, LocalDate fechaEntrada, LocalDate fechaSalida) throws HabitacionNoDisponibleException, HabitacionNoEncontradaException {
        if (cliente == null || numeroHabitacion <= 0 || fechaEntrada == null || fechaSalida == null) {
            throw new IllegalArgumentException("El cliente, el número de habitación o las fechas no pueden ser nulos");
        }
        Habitacion habitacion = gestorHabitaciones.buscarHabitacionPorNumero(numeroHabitacion);
        if (!isHabitacionDisponible(habitacion, fechaEntrada, fechaSalida)) {
            throw new HabitacionNoDisponibleException("La habitación no está disponible en las fechas solicitadas.");
        }
        habitacion.cambiarEstado(EstadoHabitacion.OCUPADO);
        Reserva nuevaReserva = new Reserva(cliente, habitacion, fechaEntrada, fechaSalida);
        reservasPorHabitacion.computeIfAbsent(habitacion.getIdHabitacion(), k -> new ArrayList<>()).add(nuevaReserva);
    }

    // metodo para verificar si la habitación está disponible en las FECHAS CONCRETAS
    private boolean isHabitacionDisponible(Habitacion habitacion, LocalDate fechaEntrada, LocalDate fechaSalida) {
        // Verificamos el estado de la habitación
        if (habitacion.getEstado() != EstadoHabitacion.DISPONIBLE) {
            return false; // La habitación no está disponible
        }
        // Obtenemos las reservas de la habitación
        List<Reserva> reservas = reservasPorHabitacion.get(habitacion.getIdHabitacion());
        // Si no hay reservas para esta habitación, está disponible
        if (reservas == null || reservas.isEmpty()) {
            return true;
        }
        // Comprobamos si alguna de las reservas existentes se superpone con las fechas solicitadas
        for (Reserva reserva : reservas) {
            if (fechaEntrada.isBefore(reserva.getFechaSalida()) && fechaSalida.isAfter(reserva.getFechaEntrada())) {
                return false; // La habitación ya está reservada para este periodo
            }
        }
        return true; // La habitación está disponible
    }

    // metodo para realizar checkOUT
    public void checkOut(Cliente cliente, Habitacion habitacion) throws ReservaNoEncontradaException {
        if (cliente == null || habitacion == null) {
            throw new IllegalArgumentException("El cliente o la habitación no pueden ser nulos");
        }
        // Buscamos la reserva activa para este cliente
        Reserva reservaActiva = null;
        for (Reserva reserva : reservasPorHabitacion.get(habitacion.getIdHabitacion())) {
            if (reserva.getCliente().equals(cliente)) {
                reservaActiva = reserva;
                break;
            }
        }
        if (reservaActiva == null) {
            throw new ReservaNoEncontradaException("No se encontró una reserva activa para este cliente.");
        }
        habitacion.cambiarEstado(EstadoHabitacion.DISPONIBLE);
        reservasPorHabitacion.get(habitacion.getIdHabitacion()).remove(reservaActiva);
    }

    // metodo para mostrar todas las reservas
    public void mostrarReservas() {
        System.out.println("Lista de todas las reservas:");
        for (Map.Entry<Integer, List<Reserva>> entry : reservasPorHabitacion.entrySet()) {
            Integer idHabitacion = entry.getKey();
            List<Reserva> reservas = entry.getValue();
            System.out.println("Habitacion №: " + idHabitacion);
            mostrarReservasParaHabitacion(reservas);
            System.out.println();
        }
    }

    // aux metodo para mostrar reservas para una habitacion(se usa en mostrarReservas)
    private void mostrarReservasParaHabitacion(List<Reserva> reservas) {
        if (reservas == null || reservas.isEmpty()) {
            System.out.println("No hay reservas para esta habitación.");
        } else {
            for (Reserva reserva : reservas) {
                System.out.println(reserva);
            }
        }
    }

}
