public class Huesped {
    protected int DNI;
    protected String nombres;
    protected String apellidos;
    protected String telefono;
    protected String correo;

    public Huesped(int DNI, String nombres, String apellidos, String telefono, String correo) {


        if (DNI < 10000000 || DNI > 99999999) {
            System.out.println("El DNI es incorrecto");
            this.DNI = 0;
        } else {
            this.DNI = DNI;
        }

        this.nombres = nombres;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.correo = correo;
    }


    public void setDNI(int DNI) {
        if (DNI < 10000000 || DNI > 99999999) {
            System.out.println("DNI inválido");
            return;
        }
        this.DNI = DNI;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }


    public int getDNI() {
        return DNI;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCorreo() {
        return correo;
    }


    @Override
    public String toString() {
        return "DNI: " + DNI +
                ", Nombres: " + nombres +
                ", Apellidos: " + apellidos +
                ", Teléfono: " + telefono +
                ", Correo: " + correo;
    }
}
