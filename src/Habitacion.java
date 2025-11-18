public  class Habitacion {
    private int numero;
    private TipoHabitacion tipo;
    private int capacidadMaxima;
    private double precioPorNoche;
    private  EstadoHabitacion estado;

    public Habitacion(int numero, TipoHabitacion tipo, int capacidadMaxima, double precioPorNoche, EstadoHabitacion estado){
        if(numero <= 0){
            System.out.println("Número de habitación inválido.");
            this.numero = 0;
        }else{
            this.numero=numero;
        }
        this.tipo=tipo;
        if (capacidadMaxima <= 0) {
            System.out.println("Capacidad inválida.");
            this.capacidadMaxima = 1;
        } else {
            this.capacidadMaxima = capacidadMaxima;
        }
        if (precioPorNoche <= 0) {
            System.out.println("Precio por noche inválido.");
            this.precioPorNoche = 1.0;
        } else {
            this.precioPorNoche = precioPorNoche;
        }
        this.estado=EstadoHabitacion.LIMPIA; //Por defecto una habitación esta limpia xd
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setTipo(TipoHabitacion tipo) {
        this.tipo = tipo;
    }

    public void setCapacidadMaxima(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    public void setPrecioPorNoche(double precioPorNoche) {
        this.precioPorNoche = precioPorNoche;
    }

    public void setEstado(EstadoHabitacion estado) {
        this.estado = estado;
    }

    public int getNumero() {
        return numero;
    }

    public TipoHabitacion getTipo() {
        return tipo;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public double getPrecioPorNoche() {
        return precioPorNoche;
    }

    public EstadoHabitacion getEstado() {
        return estado;
    }
}
