package Modelo;

import Enumeraciones.EstadoHabitacion;

import java.time.LocalDate;
import java.util.Objects;

public class Reserva {
    private int idReserva;
    private static int contador = 0;
    private Cliente cliente;
    private LocalDate fechaEntrada;
    private LocalDate fechaSalida;
    private Habitacion habitacion;
    private boolean isActive;

    // constructor
    public Reserva(Cliente cliente, Habitacion habitacion, LocalDate fechaEntrada, LocalDate fechaSalida) {
        // Comprobamos si los parámetros son nulos
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser nulo");
        }
        if (habitacion == null) {
            throw new IllegalArgumentException("La habitación no puede ser nula");
        }
        if (fechaEntrada == null || fechaSalida == null) {
            throw new IllegalArgumentException("Las fechas de entrada y salida no pueden ser nulas");
        }
        if (fechaEntrada.isAfter(fechaSalida)) {
            throw new IllegalArgumentException("La fecha de entrada no puede ser posterior a la fecha de salida");
        }

        this.cliente = cliente;
        this.habitacion = habitacion;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.isActive = true;

        // Asignamos un ID único para esta reserva
        contador++;
        this.idReserva = contador;
        // Cambiar el estado de la habitación a OCUPADO
        habitacion.cambiarEstado(EstadoHabitacion.OCUPADO);
    }

    // getters y setters

    public boolean isActive() {
        return isActive;
    }

    public int getIdReserva() {
        return idReserva;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public LocalDate getFechaEntrada() {
        return fechaEntrada;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "idReserva=" + idReserva +
                ", cliente=" + cliente.getNombre() + " " + cliente.getApellido() +
                ", habitacion=" + habitacion.getIdHabitacion() +
                ", fechaEntrada=" + fechaEntrada +
                ", fechaSalida=" + fechaSalida +
                ", isActive=" + isActive +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reserva reserva)) return false;
        return idReserva == reserva.idReserva && isActive == reserva.isActive && Objects.equals(cliente, reserva.cliente) && Objects.equals(fechaEntrada, reserva.fechaEntrada) && Objects.equals(fechaSalida, reserva.fechaSalida) && Objects.equals(habitacion, reserva.habitacion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idReserva, cliente, fechaEntrada, fechaSalida, habitacion, isActive);
    }
}
