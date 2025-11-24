package logic;

public class Factura {
    private final Huesped huesped;
    private final String fechaCheckout; // Formato yyyy/MM/dd
    private final double totalHabitacion;
    private final double totalServicios;
    private final double totalFinal;

    public Factura(Estadia estadia, String fechaCheckout) {
        this.huesped = estadia.getReserva().getHuesped();
        this.fechaCheckout = fechaCheckout;
        this.totalHabitacion = estadia.calcularTotalHabitacion();
        this.totalServicios = estadia.calcularTotalServicios();
        this.totalFinal = estadia.calcularTotalFinal();
    }

    public Huesped getHuesped() {
        return huesped;
    }

    public String getFechaCheckout() {
        return fechaCheckout;
    }

    public double getTotalHabitacion() {
        return totalHabitacion;
    }

    public double getTotalServicios() {
        return totalServicios;
    }

    public double getTotalFinal() {
        return totalFinal;
    }

    @Override
    public String toString() {
        return "logic.Factura [" +
                "Fecha: " + fechaCheckout +
                ", Huésped: " + huesped.getNombres() + " " + huesped.getApellidos() +
                ", Total Habitación: " + totalHabitacion +
                ", Total Servicios: " + totalServicios +
                ", Total Final: " + totalFinal +
                ']';
    }
}
