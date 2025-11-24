import gui.MainFrame;
import logic.*;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        GestionEmpleados gestionEmpleados = new GestionEmpleados();
        GestionHabitaciones gestionHabitaciones = new GestionHabitaciones();
        GestionHuespedes gestionHuespedes = new GestionHuespedes();
        GestionServicios gestionServicios = new GestionServicios();
        GestionFacturas gestionFacturas = new GestionFacturas();
        GestionReservas gestionReservas = new GestionReservas(gestionHabitaciones);
        GestionEstadias gestionEstadias = new GestionEstadias(gestionReservas, gestionHabitaciones, gestionFacturas);

        Autenticacion autenticacion = new Autenticacion(gestionEmpleados);

        if (gestionEmpleados.buscarPorDNI(1) == null) {
            Administrador adminPorDefecto = new Administrador(12345678, "Admin", "Principal", "admin123");
            gestionEmpleados.crearEmpleado(adminPorDefecto);
            System.out.println("Usuario logic.Administrador por defecto creado (DNI: 12345678, Pass: admin123)");
        }

        SwingUtilities.invokeLater(() -> {
            new MainFrame(autenticacion, gestionEmpleados, gestionHabitaciones, gestionHuespedes,
                          gestionServicios, gestionReservas, gestionEstadias, gestionFacturas).setVisible(true);
        });
    }
}
