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

    public Habitacion(int IdHabitacion, SizeHabitacion tamano, ClaseHabitacion tipoHabitacion) {
        this.IdHabitacion = IdHabitacion;
        this.tipoHabitacion = tipoHabitacion;
        this.estado = EstadoHabitacion.DISPONIBLE;
        this.tamano = tamano;
    }

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
