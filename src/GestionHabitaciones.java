import java.util.ArrayList;

public class GestionHabitaciones {
    private ArrayList<Habitacion> habitaciones;

    public GestionHabitaciones() {
        habitaciones = new ArrayList<>();
    }

    public Habitacion buscarPorNumero(Integer numero) {
        Habitacion habitacion = null;
        for (int i = 0; i < habitaciones.size(); i++) {
            if (numero == habitaciones.get(i).getNumero()) {
                habitacion = habitaciones.get(i);
            }
        }

        if(habitacion == null) {
            System.err.println("La habitacion con numero: " + habitacion.getNumero() + " no existe.");
        }

        return habitacion;
    }

    public void crearEmpleado(Habitacion habitacion) {
        habitaciones.add(habitacion);
        System.out.println("Se creo correctamente la habitacion numero: " + habitacion.getNumero() +  " de tipo: " + habitacion.getTipo());
    }

    public void modificarHabitacion(Integer numero, TipoHabitacion tipo, Integer capacidadMaxima, Double precioPorNoche, EstadoHabitacion estadoHabitacion, Integer nuevoNumero) {
        Habitacion habitacion = buscarPorNumero(numero);

        if (habitacion == null) {
            System.out.println("No se encontró la habitacion");
        } else {
            if (tipo != null) habitacion.setTipo(tipo);
            if (capacidadMaxima != null) habitacion.setCapacidadMaxima(capacidadMaxima);
            if (precioPorNoche != null) habitacion.setPrecioPorNoche(precioPorNoche);
            if (estadoHabitacion != null) habitacion.setEstado(estadoHabitacion);
            if (nuevoNumero != null) habitacion.setNumero(nuevoNumero);
        }
    }

    public void eliminarHabitacion(Integer numero) {
        for (int i = 0; i < habitaciones.size(); i++) {
            if (numero == habitaciones.get(i).getNumero()) {
                habitaciones.remove(i);
            }
        }
        System.out.println("Se elimino correctamente el empleado con DNI "  + numero);
    }

    public void listarHabitaciones() {
        for (int i = 0; i < habitaciones.size(); i++) {
            System.out.println(habitaciones.get(i));
        }
    }
}
