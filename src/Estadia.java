import java.util.ArrayList;

public class Estadia {
    protected Reserva reserva;
    protected Habitacion habitacion;
    protected ArrayList<ServicioConsumido> serviciosConsumidos;

    public Estadia(Reserva reserva, Habitacion habitacion) {
        this.reserva = reserva;
        this.habitacion = habitacion;
        this.serviciosConsumidos = new ArrayList<>();
    }


    public Reserva getReserva() {
        return reserva;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public ArrayList<ServicioConsumido> getServiciosConsumidos() {
        return serviciosConsumidos;
    }


    public void registrarServicio(Servicio servicio, int cantidad) {
        if (cantidad <= 0) {
            System.out.println("Cantidad inválida");
            return;
        }
        serviciosConsumidos.add(new ServicioConsumido(servicio, cantidad));
    }

    public double calcularTotalServicios() {
        double total = 0;
        for (ServicioConsumido sc : serviciosConsumidos) {
            total += sc.getCostoTotal();
        }
        return total;
    }

    public double calcularTotalHabitacion() {
        int noches = reserva.calcularNoches();
        return noches * habitacion.getPrecioPorNoche();
    }

    public double calcularTotalFinal() {
        return calcularTotalHabitacion() + calcularTotalServicios();
    }

    @Override
    public String toString() {
        return "Estadía en habitación: " + habitacion.getNumero() +
                ", Huesped: " + reserva.getHuesped().getNombres() + " " + reserva.getHuesped().getApellidos() +
                ", Total: " + calcularTotalFinal();
    }
}
