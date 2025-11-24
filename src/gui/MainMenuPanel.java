package gui;

import logic.Autenticacion;
import logic.Empleado;

import javax.swing.*;
import java.awt.*;

public class MainMenuPanel extends JPanel {
    private final MainFrame mainFrame;
    private JLabel welcomeLabel;

    public MainMenuPanel(MainFrame mainFrame, Autenticacion autenticacion) {
        this.mainFrame = mainFrame;

        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0)); // Margen a la izquierda

        welcomeLabel = new JLabel("Bienvenido, ");
        welcomeLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        topPanel.add(welcomeLabel, BorderLayout.WEST);

        JLabel titleLabel = new JLabel("Menú Principal", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        topPanel.add(titleLabel, BorderLayout.CENTER);
        
        add(topPanel, BorderLayout.NORTH);

        JPanel buttonsPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton btnGestionEmpleados = new JButton("Gestionar Empleados");
        JButton btnGestionHabitaciones = new JButton("Gestionar Habitaciones");
        JButton btnGestionHuespedes = new JButton("Gestionar Huéspedes");
        JButton btnGestionServicios = new JButton("Gestionar Servicios");
        JButton btnCrearReserva = new JButton("Crear Reserva");
        JButton btnCheckIn = new JButton("Hacer Check-in");
        JButton btnCheckOut = new JButton("Hacer Check-out");
        JButton btnRegistrarConsumo = new JButton("Registrar Consumo");
        JButton btnReporteOcupacion = new JButton("Reporte de Ocupación");
        JButton btnReporteIngresos = new JButton("Reporte de Ingresos");
        
        buttonsPanel.add(btnGestionEmpleados);
        buttonsPanel.add(btnGestionHabitaciones);
        buttonsPanel.add(btnGestionHuespedes);
        buttonsPanel.add(btnGestionServicios);
        buttonsPanel.add(btnCrearReserva);
        buttonsPanel.add(btnCheckIn);
        buttonsPanel.add(btnCheckOut);
        buttonsPanel.add(btnRegistrarConsumo);
        buttonsPanel.add(btnReporteOcupacion);
        buttonsPanel.add(btnReporteIngresos);
        
        add(buttonsPanel, BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnLogout = new JButton("Cerrar Sesión");
        southPanel.add(btnLogout);
        add(southPanel, BorderLayout.SOUTH);

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                actualizarPanel(autenticacion, btnGestionEmpleados, btnGestionHabitaciones, btnGestionHuespedes, btnGestionServicios, btnCrearReserva, btnCheckIn, btnCheckOut, btnRegistrarConsumo, btnReporteOcupacion, btnReporteIngresos);
            }
        });

        btnLogout.addActionListener(e -> {
            autenticacion.logout();
            mainFrame.showPanel("LOGIN");
        });
        
        btnGestionEmpleados.addActionListener(e -> mainFrame.showPanel("GESTION_EMPLEADOS"));
        btnGestionHabitaciones.addActionListener(e -> mainFrame.showPanel("GESTION_HABITACIONES"));
        btnGestionServicios.addActionListener(e -> mainFrame.showPanel("GESTION_SERVICIOS"));
        btnGestionHuespedes.addActionListener(e -> mainFrame.showPanel("GESTION_HUESPEDES"));
        btnCrearReserva.addActionListener(e -> mainFrame.showPanel("CREAR_RESERVA"));
        btnCheckIn.addActionListener(e -> mainFrame.showPanel("CHECK_IN"));
        btnRegistrarConsumo.addActionListener(e -> mainFrame.showPanel("REGISTRAR_CONSUMO"));
        btnCheckOut.addActionListener(e -> mainFrame.showPanel("CHECK_OUT"));
        btnReporteOcupacion.addActionListener(e -> mainFrame.showPanel("REPORTE_OCUPACION"));
        btnReporteIngresos.addActionListener(e -> mainFrame.showPanel("REPORTE_INGRESOS"));
    }

    private void actualizarPanel(Autenticacion autenticacion, JButton... buttons) {
        Empleado empleadoLogueado = autenticacion.getEmpleadoLogueado();
        if (empleadoLogueado != null) {
            welcomeLabel.setText("Bienvenido, " + empleadoLogueado.getNombres() + " (" + empleadoLogueado.getRolEmpleado() + ")");
        } else {
            welcomeLabel.setText("Bienvenido");
        }

        boolean esAdmin = autenticacion.esAdmin();
        boolean esRecepcionista = autenticacion.esRecepcionista();

        buttons[0].setVisible(esAdmin);
        buttons[1].setVisible(esAdmin);
        buttons[2].setVisible(esAdmin || esRecepcionista);
        buttons[3].setVisible(esAdmin);
        buttons[4].setVisible(esAdmin || esRecepcionista);
        buttons[5].setVisible(esAdmin || esRecepcionista);
        buttons[6].setVisible(esAdmin || esRecepcionista);
        buttons[7].setVisible(esAdmin || esRecepcionista);
        buttons[8].setVisible(esAdmin || esRecepcionista);
        buttons[9].setVisible(esAdmin);
    }
}
