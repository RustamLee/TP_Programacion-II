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
    private double precioTotal;
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
        this.precioTotal = calcularPrecioTotal();
        this.isActive = true;

        // Asignamos un ID único para esta reserva
        contador++;
        this.idReserva = contador;
        // Cambiar el estado de la habitación a OCUPADO
        habitacion.cambiarEstado(EstadoHabitacion.OCUPADO);
    }

    // getters y setters

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public double getPrecioTotal() {
        return precioTotal;
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

    // otros métodos

    // Metodo para calcular el precio total de la reserva
    private double calcularPrecioTotal() {
        long diasEstancia = java.time.temporal.ChronoUnit.DAYS.between(fechaEntrada, fechaSalida);
        if (diasEstancia <= 0) {
            diasEstancia = 1; // si las fexhas son iguales, se cobra al menos un día
        }
        return this.habitacion.getPrecioPorDia() * diasEstancia;
    }

    // Metodo para actualizar la fecha de salida de la reserva (se usa en el check-out)
    public void actualizaDataSalida(LocalDate nuevaFechaSalida) {
        if (nuevaFechaSalida != null && !nuevaFechaSalida.equals(this.fechaSalida)) {
            this.fechaSalida = nuevaFechaSalida;
            this.precioTotal = calcularPrecioTotal();
        }
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "idReserva=" + idReserva +
                ", cliente=" + cliente.getNombre() + " " + cliente.getApellido() +
                ", DNI=" + cliente.getDNI() +
                ", habitacion=" + habitacion.getIdHabitacion() +
                ", fechaEntrada=" + fechaEntrada +
                ", fechaSalida=" + fechaSalida +
                ", precioTotal=" + precioTotal +
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
