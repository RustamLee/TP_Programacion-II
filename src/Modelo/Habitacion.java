package Modelo;

import Enumeraciones.ClaseHabitacion;
import Enumeraciones.EstadoHabitacion;
import Enumeraciones.SizeHabitacion;

import java.util.Objects;

public class Habitacion {
    private int IdHabitacion;
    private SizeHabitacion tamano;
    private ClaseHabitacion tipoHabitacion;
    private EstadoHabitacion estado;
    private double precioPorDia;

    public Habitacion(int IdHabitacion, SizeHabitacion tamano, ClaseHabitacion tipoHabitacion, double precioPorDia) {
        this.IdHabitacion = IdHabitacion;
        this.tamano = tamano;
        this.tipoHabitacion = tipoHabitacion;
        this.precioPorDia = precioPorDia;
        this.estado = EstadoHabitacion.DISPONIBLE;
    }

    // getters y setters
    public int getIdHabitacion() {
        return IdHabitacion;
    }
    public void setIdHabitacion(int numeroHabitacion) {
        this.IdHabitacion = numeroHabitacion;
    }
    public SizeHabitacion getTamano() {
        return tamano;
    }
    public void setTamano(SizeHabitacion tamano) {
        this.tamano = tamano;
    }
    public ClaseHabitacion getTipoHabitacion() {
        return tipoHabitacion;
    }
    public void setPrecioPorDia(double precioPorDia) {
        this.precioPorDia = precioPorDia;
    }
    public double getPrecioPorDia() {
        return precioPorDia;
    }

    // otros metodos
    public void cambiarTipoHabitacion(ClaseHabitacion tipoHabitacion) {
        this.tipoHabitacion = tipoHabitacion;
    }

    public EstadoHabitacion getEstado() {
        return estado;
    }

    public void cambiarEstado(EstadoHabitacion estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Habitacion{" +
                "numero habitacion=" + IdHabitacion +
                ", tamaño=" + tamano +
                ", tipo habitacion=" + tipoHabitacion +
                ", estado=" + estado +
                ", precio por dia=" + precioPorDia +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Habitacion that)) return false;
        return IdHabitacion == that.IdHabitacion && tamano == that.tamano && tipoHabitacion == that.tipoHabitacion && estado == that.estado;
    }

    @Override
    public int hashCode() {
        return Objects.hash(IdHabitacion, tamano, tipoHabitacion, estado);
    }

    public int getNumero() {
        return IdHabitacion;
    }
}
