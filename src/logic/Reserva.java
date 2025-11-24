package logic;

public class Reserva {
    protected Huesped huesped;
    protected TipoHabitacion tipoHabitacion;
    protected String fechaInicio;
    protected String fechaFin;
    protected Habitacion habitacionAsignada;

    public Reserva(Huesped huesped, TipoHabitacion tipoHabitacion,
                   String fechaInicio, String fechaFin) {

        this.huesped = huesped;
        this.tipoHabitacion = tipoHabitacion;

        // Validar fecha
        if (fechaInicio == null || !fechaInicio.matches("\\d{4}/\\d{2}/\\d{2}")) {
            System.err.println("Formato de fecha de inicio inválido. Use yyyy/mm/dd.");
            this.fechaInicio = "1900/01/01";
        } else {
            this.fechaInicio = fechaInicio;
        }

        if (fechaFin == null || !fechaFin.matches("\\d{4}/\\d{2}/\\d{2}")) {
            System.err.println("Formato de fecha de fin inválido. Use yyyy/mm/dd.");
            this.fechaFin = "1900/01/01";
        } else {
            this.fechaFin = fechaFin;
        }

        this.habitacionAsignada = null;
    }


    public Huesped getHuesped() {
        return huesped;
    }

    public TipoHabitacion getTipoHabitacion() {
        return tipoHabitacion;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public Habitacion getHabitacionAsignada() {
        return habitacionAsignada;
    }


    public void setFechaInicio(String fechaInicio) {
        if (fechaInicio != null && fechaInicio.matches("\\d{4}/\\d{2}/\\d{2}")) {
            this.fechaInicio = fechaInicio;
        } else {
            System.err.println("Formato de fecha de inicio inválido. Use yyyy/MM/dd.");
        }
    }

    public void setFechaFin(String fechaFin) {
        if (fechaFin != null && fechaFin.matches("\\d{4}/\\d{2}/\\d{2}")) {
            this.fechaFin = fechaFin;
        } else {
            System.err.println("Formato de fecha de fin inválido. Use yyyy/MM/dd.");
        }
    }

    public void setHabitacionAsignada(Habitacion habitacion) {
        this.habitacionAsignada = habitacion;
    }


    public int calcularNoches() {
        return DateUtil.calcularNoches(this.fechaInicio, this.fechaFin);
    }

    @Override
    public String toString() {
        return "logic.Reserva de: " + huesped.getNombres() + " " + huesped.getApellidos() +
                ", Tipo: " + tipoHabitacion +
                ", Inicio: " + fechaInicio +
                ", Fin: " + fechaFin +
                ", Habitación: " + (habitacionAsignada == null ? "Sin asignar" : habitacionAsignada.getNumero());
    }
}
