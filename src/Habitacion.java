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

}
