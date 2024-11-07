package Gestion;

import Enumeraciones.EstadoHabitacion;
import Excepciones.HabitacionNoEncontradaException;
import Modelo.Habitacion;

import java.util.ArrayList;

public class GestorHabitaciones {
    private ArrayList<Habitacion> listaHabitaciones;

    // constructor
    public GestorHabitaciones() {
        this.listaHabitaciones = new ArrayList<>();
    }

    // metodo para agregar una nueva habitación
    public void agregarNuevaHabitacion(Habitacion nuevo) {
        if (nuevo == null) {
            throw new IllegalArgumentException("La habitación no puede ser nula");
        }
        if (isHabitacionExiste(nuevo.getIdHabitacion())) {
            throw new IllegalArgumentException("el numero de habitacion ya existe");
        } else {
            listaHabitaciones.add(nuevo);
            System.out.println("se agrego una nueva habitacion: " + nuevo);
        }
    }

    // metodo para  cambiar el estado de la habitación
    public void actualizarEstadoHabitacion(int numeroHabitacion, EstadoHabitacion nuevoEstado) throws HabitacionNoEncontradaException {
        Habitacion habitacion = buscarHabitacionPorNumero(numeroHabitacion);
        habitacion.cambiarEstado(nuevoEstado);
        System.out.println("Estado de la habitación " + numeroHabitacion + " actualizado a " + nuevoEstado);
    }

    // metodo para buscar la habitación por el número
    public Habitacion buscarHabitacionPorNumero(int numeroHabitacion) throws HabitacionNoEncontradaException {
        for (Habitacion habitacion : listaHabitaciones) {
            if (habitacion.getIdHabitacion() == numeroHabitacion) {
                return habitacion;
            }
        }
        throw new HabitacionNoEncontradaException("No se encontró la habitación con el número: " + numeroHabitacion);
    }

    public void mostrarTodasHabitaciones() {
        System.out.println("Todas las habitaciones");
        for (Habitacion aux : listaHabitaciones) {
            System.out.println(aux);
        }
    }

    // metodo para mostrar todas las habitaciones disponibles SIN FECHAS CONCRETAS
    public void mostrarHabitacionesDisponibles() {
        System.out.println("todas las habitaciones disponibles");
        for (Habitacion aux : listaHabitaciones) {
            if (aux.getEstado() == EstadoHabitacion.DISPONIBLE) {
                System.out.println(aux);
            }
        }
    }

    public boolean isHabitacionExiste(int nuemero_habitacion) {
        for (Habitacion aux : listaHabitaciones) {
            if (aux.getIdHabitacion() == nuemero_habitacion) {
                return true;
            }
        }
        return false;
    }
}