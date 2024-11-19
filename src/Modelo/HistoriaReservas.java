package Modelo;

import java.util.ArrayList;
import java.util.List;

public class HistoriaReservas {
    private List<Reserva> reservasСompletadas;

    // сonstructor
    public HistoriaReservas() {
        reservasСompletadas = new ArrayList<>();
    }

    // getters and setters
    public List<Reserva> getReservasСompletadas() {
        return reservasСompletadas;
    }

    // otros methods

    // metodo para buscar reservas por DNI
    public List<Reserva> buscarReservasPorDNI(String dni) {
        List<Reserva> reservasEncontradas = new ArrayList<>();
        for (Reserva reserva : reservasСompletadas) {
            if (reserva.getCliente().getDNI().equals(dni)) {
                reservasEncontradas.add(reserva);
            }
        }
        return reservasEncontradas;
    }

    // metodos para mostrar reservas completadas
    public void mostrarReservasCompletadasPorDni (String dni) {
        List<Reserva> reservasEncontradas = buscarReservasPorDNI(dni);
        if (reservasEncontradas.isEmpty()) {
            System.out.println("No hay reservas completadas para el cliente con DNI " + dni);
        } else {
            System.out.println("Lista de reservas completadas para el cliente con DNI " + dni + ":");
            for (Reserva reserva : reservasEncontradas) {
                System.out.println(reserva);
            }
        }

    }

    // metodo para anadir reserva a la historia
    public void agregarReserva(Reserva reserva) {
        reservasСompletadas.add(reserva);
    }

}
