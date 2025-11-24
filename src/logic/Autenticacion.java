package logic;

public class Autenticacion {
    private Empleado empleadoLogueado;
    private final GestionEmpleados gestionEmpleados;

    public Autenticacion(GestionEmpleados gestionEmpleados) {
        this.gestionEmpleados = gestionEmpleados;
        this.empleadoLogueado = null;
    }

    public boolean login(int dni, String password) {
        Empleado empleado = gestionEmpleados.buscarPorDNI(dni);
        if (empleado != null && empleado.checkPassword(password)) {
            empleadoLogueado = empleado;
            System.out.println("Bienvenido, " + empleado.getNombres());
            return true;
        }
        System.out.println("DNI o contraseña incorrectos.");
        return false;
    }

    public void logout() {
        if (empleadoLogueado != null) {
            System.out.println("Sesión cerrada para " + empleadoLogueado.getNombres());
            empleadoLogueado = null;
        }
    }

    public Empleado getEmpleadoLogueado() {
        return empleadoLogueado;
    }

    public boolean esAdmin() {
        return empleadoLogueado != null && empleadoLogueado.getRolEmpleado() == RolEmpleado.ADMINISTRADOR;
    }

    public boolean esRecepcionista() {
        return empleadoLogueado != null && empleadoLogueado.getRolEmpleado() == RolEmpleado.RECEPCIONISTA;
    }
}
