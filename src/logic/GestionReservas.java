package logic;

import java.util.ArrayList;

public class GestionReservas {
    private ArrayList<Reserva> reservas;
    private GestionHabitaciones gestionHabitaciones;

    public GestionReservas(GestionHabitaciones gestionHabitaciones) {
        this.reservas = new ArrayList<>();
        this.gestionHabitaciones = gestionHabitaciones;
    }

    /**
     * Lógica de disponibilidad:
     * 1. Obtiene todas las habitaciones del tipo solicitado.
     * 2. Para cada una, comprueba si tiene alguna reserva que se superponga con las fechas deseadas.
     * 3. Si una habitación NO tiene reservas superpuestas, está disponible.
     */
    public boolean hayDisponibilidad(TipoHabitacion tipo, String fechaInicio, String fechaFin) {
        ArrayList<Habitacion> habitacionesDisponibles = buscarHabitacionesDisponibles(tipo, fechaInicio, fechaFin);
        return !habitacionesDisponibles.isEmpty();
    }

    public ArrayList<Habitacion> buscarHabitacionesDisponibles(TipoHabitacion tipo, String fechaInicio, String fechaFin) {
        ArrayList<Habitacion> habitacionesDisponibles = new ArrayList<>();
        ArrayList<Habitacion> habitacionesDelTipo = gestionHabitaciones.buscarPorTipo(tipo);

        for (Habitacion habitacion : habitacionesDelTipo) {
            boolean ocupadaEnFechas = false;
            for (Reserva reserva : reservas) {
                if (reserva.getHabitacionAsignada() != null && reserva.getHabitacionAsignada().getNumero() == habitacion.getNumero()) {
                    if (DateUtil.seSuperponen(fechaInicio, fechaFin, reserva.getFechaInicio(), reserva.getFechaFin())) {
                        ocupadaEnFechas = true;
                        break;
                    }
                }
            }
            if (!ocupadaEnFechas) {
                habitacionesDisponibles.add(habitacion);
            }
        }
        return habitacionesDisponibles;
    }

    public Reserva crearReserva(Huesped huesped, TipoHabitacion tipo, String fechaInicio, String fechaFin) {
        if (!hayDisponibilidad(tipo, fechaInicio, fechaFin)) {
            System.err.println("No hay habitaciones disponibles de tipo " + tipo + " para las fechas seleccionadas.");
            return null;
        }

        Reserva nuevaReserva = new Reserva(huesped, tipo, fechaInicio, fechaFin);
        reservas.add(nuevaReserva);
        System.out.println("logic.Reserva creada exitosamente (aún sin habitación asignada).");
        return nuevaReserva;
    }
    
    public Reserva buscarReservaPorHuesped(int dni) {
        for (Reserva reserva : reservas) {
            if (reserva.getHuesped().getDNI() == dni) {
                return reserva;
            }
        }
        return null;
    }

    public void listarReservas() {
        if (reservas.isEmpty()) {
            System.out.println("No hay reservas registradas.");
            return;
        }
        System.out.println("--- Lista de Reservas ---");
        for (Reserva r : reservas) {
            System.out.println(r);
        }
    }
}
