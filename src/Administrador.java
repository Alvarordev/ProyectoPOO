public class Administrador extends Empleado{
    public Administrador(int DNI,String nombres, String apellidos){
        super(DNI, nombres, apellidos, RolEmpleado.ADMINISTRADOR);
    }
    @Override
    public String obtenerTipoEmpleado(){
        return "Administrador";

    }
}
