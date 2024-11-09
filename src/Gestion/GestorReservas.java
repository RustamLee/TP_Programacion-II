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

    // metodo para realizar checkIN
    public void checkIn(Cliente cliente, int numeroHabitacion, LocalDate fechaEntrada, LocalDate fechaSalida) throws HabitacionNoDisponibleException, HabitacionNoEncontradaException {
        if (cliente == null || numeroHabitacion <= 0 || fechaEntrada == null || fechaSalida == null) {
            throw new IllegalArgumentException("El cliente, el número de habitación o las fechas no pueden ser nulos");
        }
        //PODRIAMOS PONER ESTO:
       /* // Validar que la fecha de entrada que no sea posterior a la fecha de salida
    if (fechaEntrada.isAfter(fechaSalida)) {
        throw new IllegalArgumentException("La fecha de entrada no puede ser posterior a la fecha de salida.");
    } */

        Habitacion habitacion = gestorHabitaciones.buscarHabitacionPorNumero(numeroHabitacion);
        if (!isHabitacionDisponible(habitacion, fechaEntrada, fechaSalida)) {
            throw new HabitacionNoDisponibleException("La habitación no está disponible en las fechas solicitadas.");
        }
        habitacion.cambiarEstado(EstadoHabitacion.OCUPADO);
        Reserva nuevaReserva = new Reserva(cliente, habitacion, fechaEntrada, fechaSalida);
        reservasPorHabitacion.computeIfAbsent(habitacion.getIdHabitacion(), k -> new ArrayList<>()).add(nuevaReserva);
        System.out.println("Reserva realizada!");
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
        
        //PODRIAMOS CAMBIAR ESTO :
        /*// Obtener todas las reservas de la habitación específica
    List<Reserva> reservas = reservasPorHabitacion.get(habitacion.getIdHabitacion());
    
    // Si hay reservas para esta habitación, buscar la activa para el cliente
    if (reservas != null) {
        for (Reserva reserva : reservas) {
            if (reserva.getCliente().equals(cliente)) {
                reservaActiva = reserva;
                break; // Salir del ciclo cuando encontramos la reserva activa
            }
        }
    }*/
        
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

    // mostrar reservas por cliente dni
    public void mostrarReservasPorCliente(int dni) {
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
                    hayReservas = true;
                }
            }
        }
        if (!hayReservas) {
            System.out.println("No hay reservas para este cliente.");
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

    // aux metodo para obtener object LocalDate desde el usuario (se usa en checkIN)
    public static LocalDate obtenerFecha(Scanner scanner, String mensaje) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fecha = null;
        while (fecha == null) {
            try {
                System.out.print(mensaje);
                String fechaStr = scanner.nextLine();
                fecha = LocalDate.parse(fechaStr, formatter);
            } catch (Exception e) {
                System.out.println("Error: la fecha debe tener el formato yyyy-MM-dd.");
            }
        }
        return fecha;
    }

}
