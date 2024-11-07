package Gestion;

import Enumeraciones.ClaseHabitacion;
import Enumeraciones.EstadoHabitacion;
import Enumeraciones.SizeHabitacion;
import Excepciones.HabitacionNoEncontradaException;
import Excepciones.HabitacionYaExistenteException;
import Modelo.Habitacion;

import java.util.ArrayList;
import java.util.Scanner;

public class GestorHabitaciones {
    private ArrayList<Habitacion> listaHabitaciones;

    // constructor
    public GestorHabitaciones() {
        this.listaHabitaciones = new ArrayList<>();
    }

    // getter y setter
    public ArrayList<Habitacion> getListaHabitaciones() {
        return listaHabitaciones;
    }

    // aux metodo para agregar una nueva habitación a la colección
    public void addHabitacionAColeccion(Habitacion nuevo) {
        try {
            if (nuevo == null) {
                throw new IllegalArgumentException("La habitación no puede ser nula");
            }
            if (isHabitacionExiste(nuevo.getIdHabitacion())) {
                throw new HabitacionYaExistenteException("El número de habitación ya existe");
            } else {
                listaHabitaciones.add(nuevo);
                System.out.println("Se agregó una nueva habitación: " + nuevo);
            }
        } catch (HabitacionYaExistenteException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    // metodo para agregar una nueva habitación
    public void agregarHabitacion() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Ingrese el número de la habitación: ");
            int numeroHabitacion = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Ingrese la clase de la habitación (ECONOMICO, STANDARD, DELUXE): ");
            String claseHabitacion = scanner.nextLine();
            ClaseHabitacion clase = ClaseHabitacion.valueOf(claseHabitacion);
            System.out.println("Ingrese el tamaño de la habitación (SINGLE, TWIN, FAMILY): ");
            String sizeHabitacion = scanner.nextLine();
            SizeHabitacion size = SizeHabitacion.valueOf(sizeHabitacion);
            Habitacion nuevaHabitacion = new Habitacion(numeroHabitacion, size, clase);
            addHabitacionAColeccion(nuevaHabitacion);
        } catch (IllegalArgumentException e) {
            System.out.println("Error de entrada: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Se produjo un error inesperado: " + e.getMessage());
        }
    }

    // el metodo para eliminar la habitación de la colección
    public void eliminarHabitacionDeColeccion(Scanner scanner) {
        try {
            System.out.print("Ingrese el número de la habitación que desea eliminar: ");
            int numeroHabitacion = scanner.nextInt();
            if (!isHabitacionExiste(numeroHabitacion)) {
                throw new HabitacionNoEncontradaException("La habitación con el número " + numeroHabitacion + " no existe.");
            }
            boolean removed = listaHabitaciones.removeIf(habitacion -> habitacion.getIdHabitacion() == numeroHabitacion);
            if (removed) {
                System.out.println("Habitación eliminada con éxito.");
            } else {
                throw new HabitacionNoEncontradaException("No se pudo eliminar la habitación, no se encontró.");
            }
        } catch (HabitacionNoEncontradaException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Se produjo un error inesperado: " + e.getMessage());
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

    // metodo para mostrar todas las habitaciones
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