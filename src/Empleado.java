public abstract class Empleado {
    protected int DNI;
    protected String nombres;
    protected  String apellidos;
    protected  RolEmpleado rolEmpleado;

    public Empleado(int DNI,String nombres, String apellidos,RolEmpleado rolEmpleado){
        if(DNI<10000000 || DNI >99999999){
            System.out.println("El DNI es incorrecto");
            this.DNI=0;
        }else{
            this.DNI=DNI;
        }
        this.nombres = nombres;
        this.apellidos=apellidos;
        this.rolEmpleado=rolEmpleado;
    }

    public void setDNI(int DNI) {
        if(DNI < 10000000 || DNI > 99999999){
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

    public void setRolEmpleado(RolEmpleado rolEmpleado) {
        this.rolEmpleado = rolEmpleado;

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

    public RolEmpleado getRolEmpleado() {
        return rolEmpleado;
    }

    public abstract String obtenerTipoEmpleado();

}
