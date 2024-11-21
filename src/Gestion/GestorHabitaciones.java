package Gestion;

import Enumeraciones.ClaseHabitacion;
import Enumeraciones.EstadoHabitacion;
import Enumeraciones.SizeHabitacion;
import Excepciones.HabitacionNoEncontradaException;
import Excepciones.HabitacionYaExistenteException;
import Modelo.Habitacion;

import java.util.ArrayList;
import java.util.Collections;
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

    // metodo para obtener la habitación por el número
    private Habitacion obtenerHabitacion(int numeroHabitacion) throws HabitacionNoEncontradaException {
        Habitacion habitacion = buscarHabitacionPorNumero(numeroHabitacion);
        if (habitacion == null) {
            throw new HabitacionNoEncontradaException("La habitación no existe.");
        }
        return habitacion;
    }


    // aux metodo para agregar una nueva habitación a la colección
    public void addHabitacionAColeccion(Habitacion nuevo) {
        try {
            if (nuevo == null) {
                throw new IllegalArgumentException("La habitación no puede ser nula");
            }
            if (isHabitacionExiste(nuevo.getIdHabitacion())) {
                throw new HabitacionYaExistenteException("El número de habitación"+ nuevo.getIdHabitacion() + " ya existe.");
            } else {
                listaHabitaciones.add(nuevo);
                System.out.println("Se agregó una nueva habitación: " + nuevo);
            }
        } catch (HabitacionYaExistenteException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    // metodo para agregar una nueva habitación
    public void agregarHabitacion() {
        Scanner scanner = new Scanner(System.in);
        int numeroHabitacion = 0;
        while (true) {
            System.out.println("Ingrese el número de la habitación: ");
            String inputNumero = scanner.nextLine().trim();
            if (inputNumero.isEmpty()) {
                System.out.println("Error: El número de la habitación no puede estar vacío o contener solo espacios.");
            } else {
                try {
                    numeroHabitacion = Integer.parseInt(inputNumero);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Error: El número de la habitación debe ser un número válido.");
                }
            }
        }
        ClaseHabitacion clase = null;
        while (true) {
            System.out.println("Ingrese la clase de la habitación (ECONOMICO, STANDARD, DELUXE): ");
            String claseHabitacion = scanner.nextLine().trim().toUpperCase();
            try {
                clase = ClaseHabitacion.valueOf(claseHabitacion);
                System.out.println("Clase de habitación seleccionada: " + clase);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: Clase de habitación no válida. Debe ser ECONOMICO, STANDARD o DELUXE.");
            }
        }
        SizeHabitacion size = null;
        while (true) {
            System.out.println("Ingrese el tamaño de la habitación (SINGLE, TWIN, FAMILY): ");
            String sizeHabitacion = scanner.nextLine().trim().toUpperCase();
            try {
                size = SizeHabitacion.valueOf(sizeHabitacion);
                System.out.println("Tamaño de habitación seleccionado: " + size);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: Tamaño de habitación no válido. Debe ser SINGLE, TWIN o FAMILY.");
            }
        }
        double precioPorDia = 0;
        while (true) {
            System.out.println("Ingrese el precio por día de la habitación: ");
            String inputPrecio = scanner.nextLine().trim();
            if (inputPrecio.isEmpty()) {
                System.out.println("Error: El precio por día no puede estar vacío o contener solo espacios.");
            } else {
                try {
                    precioPorDia = Double.parseDouble(inputPrecio);
                    if (precioPorDia <= 0) {
                        System.out.println("Error: El precio debe ser mayor que cero.");
                    } else {
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error: El precio por día debe ser un número válido.");
                }
            }
        }
        Habitacion nuevaHabitacion = new Habitacion(numeroHabitacion, size, clase, precioPorDia);
        addHabitacionAColeccion(nuevaHabitacion);
    }

    // el metodo para eliminar la habitación de la colección
    public void eliminarHabitacionDeColeccion(Scanner scanner) {
        try {
            int numeroHabitacion = 0;
            boolean validInput = false;
            while (!validInput) {
                System.out.print("Ingrese el número de la habitación que desea eliminar: ");
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.println("Error: El número de la habitación no puede estar vacío.");
                } else if (!input.matches("\\d+")) {
                    System.out.println("Error: El número de la habitación debe ser un número válido.");
                } else {
                    try {
                        numeroHabitacion = Integer.parseInt(input);
                        validInput = true;
                    } catch (NumberFormatException e) {
                        System.out.println("Error: El número de la habitación debe ser un número válido.");
                    }
                }
            }
            if (!isHabitacionExiste(numeroHabitacion)) {
                throw new HabitacionNoEncontradaException("La habitación con el número " + numeroHabitacion + " no existe.");
            }
            boolean removed = false;
            for (int i = 0; i < listaHabitaciones.size(); i++) {
                if (listaHabitaciones.get(i).getIdHabitacion() == numeroHabitacion) {
                    listaHabitaciones.remove(i);
                    removed = true;
                    break;
                }
            }
            if (removed) {
                System.out.println("Habitación eliminada con éxito.");
            } else {
                throw new HabitacionNoEncontradaException("No se pudo eliminar la habitación, no se encontró.");
            }
        } catch (HabitacionNoEncontradaException e) {
            System.out.println("Error: " + e.getMessage());
        }
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

    // metodo para mostrar todas las habitaciones (disponibles y ocupadas)
    public void listarHabitaciones(Scanner scanner) {
        while (true) {
            System.out.println("===============================");
            System.out.println("Seleccione las habitaciones que desea ver:");
            System.out.println("1. Habitaciones disponibles");
            System.out.println("2. Habitaciones ocupadas");
            System.out.println("3. Habitaciones en limpieza");
            System.out.println("4. Habitaciones en reparación");
            System.out.println("5. Todas las habitaciones");
            System.out.println("===============================");
            System.out.print("Seleccione una opción (1, 2, 3, 4 o 5): ");
            String input = scanner.nextLine().trim();

            if (!input.isEmpty() && (input.equals("1") || input.equals("2") || input.equals("3") || input.equals("4") || input.equals("5"))) {
                int option = Integer.parseInt(input);

                if (option == 1) {
                    mostrarHabitacionesPorEstado(EstadoHabitacion.DISPONIBLE);
                    break;
                } else if (option == 2) {
                    mostrarHabitacionesPorEstado(EstadoHabitacion.OCUPADO);
                    break;
                } else if (option == 3) {
                    mostrarHabitacionesPorEstado(EstadoHabitacion.LIMPIEZA);
                    break;
                } else if (option == 4) {
                    mostrarHabitacionesPorEstado(EstadoHabitacion.REPARACION);
                    break;
                } else if (option == 5) {
                    mostrarTodasLasHabitaciones();
                    break;
                }
            } else {
                System.out.println("Entrada no válida. Por favor, introduzca un número válido (1, 2, 3, 4 o 5).");
            }
        }
    }


    // aux metodo para mostrar todas las habitaciones por estado
    public void mostrarHabitacionesPorEstado(EstadoHabitacion estado) {
        System.out.println("Habitaciones con estado: " + estado);
        boolean found = false;
        for (Habitacion aux : listaHabitaciones) {
            if (aux.getEstado() == estado) {
                System.out.println(aux);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No hay habitaciones con el estado " + estado + ".");
        }
    }

    // metodo para cambiar estado de la habitación (para habitaciones no ocupadas)
    public void cambiarEstadoHabitacion(Scanner scanner) {
        int numeroHabitacion = solicitarNumeroHabitacion(scanner);
        try {
            Habitacion habitacion = obtenerHabitacion(numeroHabitacion);
            if (habitacion.getEstado() == EstadoHabitacion.OCUPADO) {
                System.out.println("Error: La habitación está ocupada y no se puede cambiar de estado.");
                return;
            }
            EstadoHabitacion nuevoEstado = solicitarNuevoEstado(scanner);
            habitacion.cambiarEstado(nuevoEstado);
            System.out.println("Estado de la habitación " + numeroHabitacion + " actualizado a " + nuevoEstado);
        } catch (HabitacionNoEncontradaException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // aux metodo para mostrar todas las habitaciones
    public void mostrarTodasLasHabitaciones() {
        System.out.println("Todas las habitaciones (disponibles y ocupadas), ordenadas por número:");
        Collections.sort(listaHabitaciones, (h1, h2) -> Integer.compare(h1.getNumero(), h2.getNumero()));

        boolean found = false;
        for (Habitacion aux : listaHabitaciones) {
            System.out.println(aux);
            found = true;
        }

        if (!found) {
            System.out.println("No hay habitaciones registradas.");
        }
    }

    // aux metodo para verificar si la habitación existe(se usa en el metodo addHabitacionAColeccion y eliminarHabitacionDeColeccion)
    public boolean isHabitacionExiste(int nuemero_habitacion) {
        for (Habitacion aux : listaHabitaciones) {
            if (aux.getIdHabitacion() == nuemero_habitacion) {
                return true;
            }
        }
        return false;
    }

    public int solicitarNumeroHabitacion(Scanner scanner) {
        int numeroHabitacion = 0;
        while (true) {
            System.out.print("Ingrese el número de habitación: ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("El número de habitación no puede estar vacío.");
                continue;
            }
            try {
                numeroHabitacion = Integer.parseInt(input);
                if (numeroHabitacion <= 0) {
                    System.out.println("El número de habitación debe ser un número positivo.");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("El número de habitación debe ser un número válido.");
                continue;
            }
            break;
        }
        return numeroHabitacion;
    }

    //  aux metodo para solicitar el nuevo estado de la habitación (se usa en el metodo cambiarEstadoHabitacion)
    private EstadoHabitacion solicitarNuevoEstado(Scanner scanner) {
        EstadoHabitacion nuevoEstado = null;
        while (nuevoEstado == null) {
            System.out.println("Ingrese el nuevo estado de la habitación (DISPONIBLE, LIMPIEZA, REPARACION): ");
            String inputEstado = scanner.nextLine().trim().toUpperCase();

            try {
                nuevoEstado = EstadoHabitacion.valueOf(inputEstado);
                if (nuevoEstado == EstadoHabitacion.OCUPADO) {
                    System.out.println("Error: No se puede asignar el estado 'OCUPADO' manualmente.");
                    nuevoEstado = null;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: Estado no válido. Debe ser DISPONIBLE, LIMPIEZA o REPARACION.");
            }
        }
        return nuevoEstado;
    }
}