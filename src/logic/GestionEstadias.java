package logic;

import java.util.ArrayList;

public class GestionEstadias {
    private ArrayList<Estadia> estadiasActivas;
    private GestionReservas gestionReservas;
    private GestionHabitaciones gestionHabitaciones;
    private GestionFacturas gestionFacturas; // Para registrar la factura al hacer checkout

    public GestionEstadias(GestionReservas gestionReservas, GestionHabitaciones gestionHabitaciones, GestionFacturas gestionFacturas) {
        this.estadiasActivas = new ArrayList<>();
        this.gestionReservas = gestionReservas;
        this.gestionHabitaciones = gestionHabitaciones;
        this.gestionFacturas = gestionFacturas;
    }

    public Estadia checkIn(Reserva reserva, int numeroHabitacion) {
        // Validar que la reserva existe y no tiene ya una habitación.
        if (reserva == null || reserva.getHabitacionAsignada() != null) {
            System.err.println("Error: logic.Reserva inválida o ya se ha hecho check-in.");
            return null;
        }

        // Buscar la habitación específica.
        Habitacion habitacion = gestionHabitaciones.buscarPorNumero(numeroHabitacion);
        if (habitacion == null) {
            System.err.println("Error: La habitación no existe.");
            return null;
        }

        // Validar que la habitación sea del tipo correcto y esté disponible y limpia.
        if (habitacion.getTipo() != reserva.getTipoHabitacion()) {
            System.err.println("Error: La habitación no es del tipo reservado.");
            return null;
        }
        if (habitacion.getEstado() != EstadoHabitacion.LIMPIA) {
            System.err.println("Error: La habitación no está limpia y lista para check-in.");
            return null;
        }
        
        // Asignar habitación a la reserva.
        reserva.setHabitacionAsignada(habitacion);
        
        // Cambiar estado de la habitación a OCUPADA.
        habitacion.setEstado(EstadoHabitacion.OCUPADA);
        
        // Crear y registrar la nueva estadía.
        Estadia nuevaEstadia = new Estadia(reserva, habitacion);
        estadiasActivas.add(nuevaEstadia);
        
        System.out.println("Check-in exitoso para " + reserva.getHuesped().getNombres() + " en la habitación " + numeroHabitacion);
        return nuevaEstadia;
    }

    public void registrarConsumo(int dniHuesped, Servicio servicio, int cantidad) {
        Estadia estadia = buscarEstadiaPorHuesped(dniHuesped);
        if (estadia == null) {
            System.err.println("Error: No se encontró una estadía activa para el huésped con DNI " + dniHuesped);
            return;
        }
        estadia.registrarServicio(servicio, cantidad);
        System.out.println("Consumo registrado para " + estadia.getReserva().getHuesped().getNombres());
    }

    public double checkOut(int dniHuesped, String fechaActual) {
        Estadia estadia = buscarEstadiaPorHuesped(dniHuesped);
        if (estadia == null) {
            System.err.println("Error: No se encontró una estadía activa para el huésped con DNI " + dniHuesped);
            return -1.0;
        }

        Factura nuevaFactura = new Factura(estadia, fechaActual);

        gestionFacturas.agregarFactura(nuevaFactura);

        estadia.getHabitacion().setEstado(EstadoHabitacion.SUCIA);

        estadiasActivas.remove(estadia);
        
        System.out.println("Check-out exitoso para " + estadia.getReserva().getHuesped().getNombres());
        System.out.println("Habitación " + estadia.getHabitacion().getNumero() + " marcada como SUCIA.");
        System.out.println("logic.Factura generada y guardada en el historial.");
        System.out.println("Total a pagar: $" + nuevaFactura.getTotalFinal());
        
        return nuevaFactura.getTotalFinal();
    }

    public Estadia buscarEstadiaPorHuesped(int dni) {
        for (Estadia e : estadiasActivas) {
            if (e.getReserva().getHuesped().getDNI() == dni) {
                return e;
            }
        }
        return null;
    }

    public void listarEstadiasActivas() {
        if (estadiasActivas.isEmpty()) {
            System.out.println("No hay huéspedes con check-in realizado (estadías activas).");
            return;
        }
        System.out.println("--- Estadias Activas ---");
        for (Estadia e : estadiasActivas) {
            System.out.println(e);
        }
    }
}
