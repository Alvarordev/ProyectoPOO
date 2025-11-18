public class Servicio {
    protected String nombre;
    protected double precio;

    public Servicio(String nombre, double precio) {

        if (nombre == null || nombre.trim().isEmpty()) {
            System.out.println("Nombre de servicio inválido");
            this.nombre = "SIN NOMBRE";
        } else {
            this.nombre = nombre;
        }

        if (precio <= 0) {
            System.out.println("Precio inválido");
            this.precio = 1.0;
        } else {
            this.precio = precio;
        }
    }

    public void setNombre(String nombre) {
        if (nombre != null && !nombre.trim().isEmpty()) {
            this.nombre = nombre;
        } else {
            System.out.println("Nombre inválido");
        }
    }

    public void setPrecio(double precio) {
        if (precio > 0) {
            this.precio = precio;
        } else {
            System.out.println("Precio inválido");
        }
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    @Override
    public String toString() {
        return "Servicio: " + nombre + " - Precio: " + precio;
    }
}
