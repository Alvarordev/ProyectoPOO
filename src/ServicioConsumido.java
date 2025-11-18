public class ServicioConsumido {
    protected Servicio servicio;
    protected int cantidad;

    public ServicioConsumido(Servicio servicio, int cantidad) {

        this.servicio = servicio;

        if (cantidad <= 0) {
            System.out.println("Cantidad inválida");
            this.cantidad = 0;
        } else {
            this.cantidad = cantidad;
        }
    }


    public Servicio getServicio() {
        return servicio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getCostoTotal() {
        return servicio.getPrecio() * cantidad;
    }

    @Override
    public String toString() {
        return servicio.getNombre() + " x" + cantidad +
                " = " + getCostoTotal();
    }
}
