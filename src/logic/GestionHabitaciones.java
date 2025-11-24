package logic;

import java.util.ArrayList;

public class GestionHabitaciones {
    private ArrayList<Habitacion> habitaciones;

    public GestionHabitaciones() {
        habitaciones = new ArrayList<>();
    }

    public ArrayList<Habitacion> getHabitaciones() {
        return habitaciones;
    }

    public Habitacion buscarPorNumero(Integer numero) {
        for (Habitacion habitacion : habitaciones) {
            if (numero == habitacion.getNumero()) {
                return habitacion;
            }
        }
        return null;
    }

    public ArrayList<Habitacion> buscarPorTipo(TipoHabitacion tipo) {
        ArrayList<Habitacion> habitacionesPorTipo = new ArrayList<>();
        for (Habitacion habitacion : habitaciones) {
            if (habitacion.getTipo() == tipo) {
                habitacionesPorTipo.add(habitacion);
            }
        }
        return habitacionesPorTipo;
    }

    public boolean crearHabitacion(Habitacion habitacion) {
        if (buscarPorNumero(habitacion.getNumero()) != null) {
            System.err.println("Error: Ya existe una habitación con el número " + habitacion.getNumero());
            return false;
        }
        habitaciones.add(habitacion);
        return true;
    }

    public boolean modificarHabitacion(Integer numero, TipoHabitacion tipo, Integer capacidadMaxima, Double precioPorNoche, EstadoHabitacion estadoHabitacion, Integer nuevoNumero) {
        Habitacion habitacion = buscarPorNumero(numero);
        if (habitacion == null) {
            return false;
        }

        if (nuevoNumero != null && !nuevoNumero.equals(numero) && buscarPorNumero(nuevoNumero) != null) {
            System.err.println("Error: El nuevo número de habitación ya está en uso.");
            return false;
        }

        if (tipo != null) habitacion.setTipo(tipo);
        if (capacidadMaxima != null) habitacion.setCapacidadMaxima(capacidadMaxima);
        if (precioPorNoche != null) habitacion.setPrecioPorNoche(precioPorNoche);
        if (estadoHabitacion != null) habitacion.setEstado(estadoHabitacion);
        if (nuevoNumero != null) habitacion.setNumero(nuevoNumero);
        
        return true;
    }

    public boolean eliminarHabitacion(Integer numero) {
        Habitacion habitacion = buscarPorNumero(numero);
        if (habitacion != null) {
            habitaciones.remove(habitacion);
            return true;
        }
        return false;
    }

    public void listarHabitaciones() {
        for (Habitacion habitacion : habitaciones) {
            System.out.println(habitacion);
        }
    }

    public void generarReporteOcupacion() {
        System.out.println("--- Reporte de Ocupación de Habitaciones ---");
        if (habitaciones.isEmpty()) {
            System.out.println("No hay habitaciones registradas en el hotel.");
            return;
        }

        int ocupadas = 0;
        int libres = 0;
        int sucias = 0;
        int limpias = 0;

        for (Habitacion habitacion : habitaciones) {
            System.out.println(" - Habitación " + habitacion.getNumero() + ": " + habitacion.getEstado());
            switch (habitacion.getEstado()) {
                case OCUPADA:
                    ocupadas++;
                    break;
                case LIBRE:
                    libres++;
                    break;
                case SUCIA:
                    sucias++;
                    break;
                case LIMPIA:
                    limpias++;
                    break;
            }
        }

        System.out.println("\n--- Resumen de Ocupación ---");
        System.out.println("Total de Habitaciones: " + habitaciones.size());
        System.out.println("Ocupadas: " + ocupadas);
        System.out.println("Libres (Disponibles para asignar): " + libres);
        System.out.println("En Limpieza (Sucias): " + sucias);
        System.out.println("Listas para Check-in (Limpia y Libre): " + limpias);
    }
}
