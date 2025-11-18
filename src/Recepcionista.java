public class Recepcionista extends Empleado{
    public Recepcionista(int DNI,String nombres, String apellidos){
        super(DNI, nombres, apellidos, RolEmpleado.RECEPCIONISTA);
    }
    @Override
    public String obtenerTipoEmpleado(){
        return "recepcionista";
    }
}
