public class Reserva {
    protected Huesped huesped;
    protected TipoHabitacion tipoHabitacion;
    protected String fechaInicio;   // formato dd/mm/aa
    protected String fechaFin;      // formato dd/mm/aa
    protected Habitacion habitacionAsignada;

    public Reserva(Huesped huesped, TipoHabitacion tipoHabitacion,
                   String fechaInicio, String fechaFin) {

        this.huesped = huesped;
        this.tipoHabitacion = tipoHabitacion;

        if (fechaInicio == null || fechaInicio.trim().isEmpty()) {
            System.out.println("Fecha inicio inválida");
            this.fechaInicio = "";
        } else {
            this.fechaInicio = fechaInicio;
        }

        if (fechaFin == null || fechaFin.trim().isEmpty()) {
            System.out.println("Fecha fin inválida");
            this.fechaFin = "";
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
        if (fechaInicio != null && !fechaInicio.trim().isEmpty()) {
            this.fechaInicio = fechaInicio;
        } else {
            System.out.println("Fecha inicio inválida");
        }
    }

    public void setFechaFin(String fechaFin) {
        if (fechaFin != null && !fechaFin.trim().isEmpty()) {
            this.fechaFin = fechaFin;
        } else {
            System.out.println("Fecha fin inválida");
        }
    }

    public void setHabitacionAsignada(Habitacion habitacion) {
        this.habitacionAsignada = habitacion;
    }


    public int calcularNoches() {
        return 1;
    }

    @Override
    public String toString() {
        return "Reserva de: " + huesped.getNombres() + " " + huesped.getApellidos() +
                ", Tipo: " + tipoHabitacion +
                ", Inicio: " + fechaInicio +
                ", Fin: " + fechaFin +
                ", Habitación: " + (habitacionAsignada == null ? "Sin asignar" : habitacionAsignada.getNumero());
    }
}
