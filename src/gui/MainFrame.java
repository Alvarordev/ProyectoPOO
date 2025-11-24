package gui;

import javax.swing.*;
import java.awt.*;

import logic.*;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public MainFrame(Autenticacion autenticacion, GestionEmpleados gestionEmpleados, GestionHabitaciones gestionHabitaciones,
                     GestionHuespedes gestionHuespedes, GestionServicios gestionServicios, GestionReservas gestionReservas,
                     GestionEstadias gestionEstadias, GestionFacturas gestionFacturas) {
        
        setTitle("Sistema de Gestión Hotelera");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        LoginPanel loginPanel = new LoginPanel(autenticacion, this);
        MainMenuPanel mainMenuPanel = new MainMenuPanel(this, autenticacion);
        GestionEmpleadosPanel gestionEmpleadosPanel = new GestionEmpleadosPanel(this, gestionEmpleados, autenticacion);
        GestionHabitacionesPanel gestionHabitacionesPanel = new GestionHabitacionesPanel(this, gestionHabitaciones);
        GestionServiciosPanel gestionServiciosPanel = new GestionServiciosPanel(this, gestionServicios);
        GestionHuespedesPanel gestionHuespedesPanel = new GestionHuespedesPanel(this, gestionHuespedes);
        CrearReservaPanel crearReservaPanel = new CrearReservaPanel(this, gestionReservas, gestionHuespedes);
        CheckInPanel checkInPanel = new CheckInPanel(this, gestionReservas, gestionEstadias, gestionHabitaciones);
        RegistrarConsumoPanel registrarConsumoPanel = new RegistrarConsumoPanel(this, gestionEstadias, gestionServicios);
        CheckOutPanel checkOutPanel = new CheckOutPanel(this, gestionEstadias);
        ReporteOcupacionPanel reporteOcupacionPanel = new ReporteOcupacionPanel(this, gestionHabitaciones);
        ReporteIngresosPanel reporteIngresosPanel = new ReporteIngresosPanel(this, gestionFacturas);


        mainPanel.add(loginPanel, "LOGIN");
        mainPanel.add(mainMenuPanel, "MAIN_MENU");
        mainPanel.add(gestionEmpleadosPanel, "GESTION_EMPLEADOS");
        mainPanel.add(gestionHabitacionesPanel, "GESTION_HABITACIONES");
        mainPanel.add(gestionServiciosPanel, "GESTION_SERVICIOS");
        mainPanel.add(gestionHuespedesPanel, "GESTION_HUESPEDES");
        mainPanel.add(crearReservaPanel, "CREAR_RESERVA");
        mainPanel.add(checkInPanel, "CHECK_IN");
        mainPanel.add(registrarConsumoPanel, "REGISTRAR_CONSUMO");
        mainPanel.add(checkOutPanel, "CHECK_OUT");
        mainPanel.add(reporteOcupacionPanel, "REPORTE_OCUPACION");
        mainPanel.add(reporteIngresosPanel, "REPORTE_INGRESOS");

        add(mainPanel);

        cardLayout.show(mainPanel, "LOGIN");
    }

    public void showPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
    }
}
