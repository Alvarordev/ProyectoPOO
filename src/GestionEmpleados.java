import java.util.ArrayList;

public class GestionEmpleados {
    private ArrayList<Empleado> empleados;

    public GestionEmpleados() {
        empleados = new ArrayList<>();
    }

    public ArrayList<Empleado> getEmpleados() {
        return empleados;
    }

    public Empleado buscarPorDNI(Integer DNI) {
        Empleado empleado = null;
        for (int i = 0; i < empleados.size(); i++) {
            if (DNI == empleados.get(i).getDNI()) {
                empleado = empleados.get(i);
            }
        }

        if(empleado == null) {
            System.err.println("El emplado con DNI: " + DNI + " no existe.");
        }

        return empleado;
    }

    public void crearEmpleado(Empleado empleado) {
        empleados.add(empleado);
        System.out.println("Se creo correctamente el empleado de tipo " + empleado.rolEmpleado);
    }

    public void modificarEmpleado(int DNI, String nombres, String appelidos, RolEmpleado rol, Integer NuevoDNI) {
        Empleado empleado = null;

        for (int i = 0; i < empleados.size(); i++) {
            if (DNI == empleados.get(i).getDNI()) {
                empleado = empleados.get(i);
            }
        }

        if (empleado == null) {
            System.out.println("No se encontró el empleado");
        } else {
            if (rol != null) empleado.setRolEmpleado(rol);
            if (nombres != null) empleado.setNombres(nombres);
            if (appelidos != null) empleado.setApellidos(appelidos);
            if (NuevoDNI != null) empleado.setDNI(NuevoDNI);
        }
    }

    public void eliminarEmpleado(Integer DNI) {
        for (int i = 0; i < empleados.size(); i++) {
            if (DNI == empleados.get(i).getDNI()) {
                empleados.remove(i);
            }
        }
        System.out.println("Se elimino correctamente el empleado con DNI "  + DNI);
    }

    public void listarEmpleados() {
        for (int i = 0; i < empleados.size(); i++) {
            System.out.println(empleados.get(i));
        }
    }
}
