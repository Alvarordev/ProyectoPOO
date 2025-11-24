package logic;

public class Administrador extends Empleado {
    public Administrador(int DNI,String nombres, String apellidos, String password){
        super(DNI, nombres, apellidos, RolEmpleado.ADMINISTRADOR, password);
    }
    @Override
    public String obtenerTipoEmpleado(){
        return "Administrador";

    }
}
