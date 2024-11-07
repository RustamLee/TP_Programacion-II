//import Enumeraciones.Clase_Habitacion;
//import Enumeraciones.Size_Habitacion;
//import Excepciones.HabitacionNoDisponibleException;
//import Excepciones.ReservaNoEncontrada;
//import Gestion.Gestor_Reservas;
//import Modelo.Cliente;
//import Modelo.Habitaciones;
//
//public static void main(String[] args) {
//    try {
//
//        Cliente cliente1 = new Cliente("Juan", "torres","45291298","3 de febrero","2235557088");
//        Cliente cliente2 = new Cliente("Maria Gomez", "gomez","123","aconcagua","1234567");
//
//        Habitaciones habitacion1 = new Habitaciones(101, Size_Habitacion.SINGLE, Clase_Habitacion.DELUXE);
//        Habitaciones habitacion2 = new Habitaciones(102,Size_Habitacion.FAMILY,Clase_Habitacion.STANDARD);
//
//        // Caso 1: Check-in exitoso
//        System.out.println("** Caso 1: Check-in exitoso **");
//        gestorReservas.checkIN(cliente1, habitacion1);
//        gestorReservas.mostrar_reservas();
//
//        // Caso 2: Intentar hacer check-in para la misma habitación que ya está ocupada
//        System.out.println("\n** Caso 2: Check-in en habitación ocupada **");
//        try {
//            gestorReservas.checkIN(cliente2, habitacion1);
//        } catch (HabitacionNoDisponibleException e) {
//            System.out.println(e.getMessage());
//        }
//
//        // Caso 3: Intentar hacer check-in para el mismo cliente que ya tiene una reserva activa
//        System.out.println("\n Caso 3: Check-in con cliente que ya tiene una reserva activa ");
//        try {
//            gestorReservas.checkIN(cliente1, habitacion2);  // Debería lanzar excepción
//        } catch (IllegalStateException e) {
//            System.out.println(e.getMessage());
//        }
//
//        // Caso 4: Check-out exitoso
//        System.out.println("\n** Caso 4: Check-out exitoso **");
//        gestorReservas.checkOut(cliente1, habitacion1);
//        gestorReservas.mostrar_reservas();
//
//        // Caso 5: Intentar hacer check-out de una reserva no existente
//        System.out.println("\n** Caso 5: Check-out de reserva no existente **");
//        try {
//            gestorReservas.checkOut(cliente2, habitacion1);
//        } catch (ReservaNoEncontrada e) {
//            System.out.println(e.getMessage());
//        }
//
//        // Caso 6: Realizar una nueva reserva para el cliente después del check-out exitoso
//        System.out.println("\n  Caso 6: Check-in después de check-out ");
//        gestorReservas.checkIN(cliente1, habitacion1);
//        gestorReservas.mostrar_reservas();
//
//    } catch (Exception e) {
//        e.printStackTrace();
//    }
//}
