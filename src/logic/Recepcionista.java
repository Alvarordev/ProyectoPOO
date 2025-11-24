package logic;

public class Recepcionista extends Empleado {
    public Recepcionista(int DNI,String nombres, String apellidos, String password){
        super(DNI, nombres, apellidos, RolEmpleado.RECEPCIONISTA, password);
    }
    @Override
    public String obtenerTipoEmpleado(){
        return "recepcionista";
    }
}
