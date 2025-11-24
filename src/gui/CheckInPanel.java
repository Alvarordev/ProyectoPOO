package gui;

import logic.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class CheckInPanel extends JPanel {
    private final MainFrame mainFrame;
    private final GestionReservas gestionReservas;
    private final GestionEstadias gestionEstadias;
    private final GestionHabitaciones gestionHabitaciones;

    private JTextField dniField;
    private JButton buscarButton;
    private JPanel detailsPanel;
    private JLabel reservaInfoLabel;
    private JComboBox<Habitacion> habitacionesComboBox;
    private JButton confirmarButton;

    private Reserva reservaActual;

    public CheckInPanel(MainFrame mainFrame, GestionReservas gestionReservas, GestionEstadias gestionEstadias, GestionHabitaciones gestionHabitaciones) {
        this.mainFrame = mainFrame;
        this.gestionReservas = gestionReservas;
        this.gestionEstadias = gestionEstadias;
        this.gestionHabitaciones = gestionHabitaciones;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        searchPanel.add(new JLabel("DNI del Huésped:"));
        dniField = new JTextField(10);
        searchPanel.add(dniField);
        buscarButton = new JButton("Buscar Reserva");
        searchPanel.add(buscarButton);
        add(searchPanel, BorderLayout.NORTH);

        detailsPanel = new JPanel(new GridBagLayout());
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Detalles del Check-in"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        reservaInfoLabel = new JLabel("Por favor, busque una reserva.");
        reservaInfoLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        habitacionesComboBox = new JComboBox<>();
        confirmarButton = new JButton("Confirmar Check-in");

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; detailsPanel.add(reservaInfoLabel, gbc);
        gbc.gridy = 1; gbc.gridwidth = 1; detailsPanel.add(new JLabel("Habitación a Asignar:"), gbc);
        gbc.gridx = 1; detailsPanel.add(habitacionesComboBox, gbc);
        gbc.gridy = 2; gbc.gridx = 0; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER; detailsPanel.add(confirmarButton, gbc);
        
        detailsPanel.setVisible(false);
        add(detailsPanel, BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnVolver = new JButton("Volver al Menú");
        southPanel.add(btnVolver);
        add(southPanel, BorderLayout.SOUTH);

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                resetPanel();
            }
        });

        btnVolver.addActionListener(e -> mainFrame.showPanel("MAIN_MENU"));
        buscarButton.addActionListener(e -> buscarReserva());
        confirmarButton.addActionListener(e -> realizarCheckIn());
    }

    private void buscarReserva() {
        try {
            int dni = Integer.parseInt(dniField.getText());
            reservaActual = gestionReservas.buscarReservaPorHuesped(dni);

            if (reservaActual == null) {
                JOptionPane.showMessageDialog(this, "No se encontró una reserva activa para el DNI " + dni, "Error", JOptionPane.ERROR_MESSAGE);
                resetPanel();
                return;
            }
            
            if (reservaActual.getHabitacionAsignada() != null) {
                JOptionPane.showMessageDialog(this, "Ya se ha realizado el check-in para esta reserva.", "Información", JOptionPane.INFORMATION_MESSAGE);
                resetPanel();
                return;
            }

            ArrayList<Habitacion> habitacionesDisponibles = gestionReservas.buscarHabitacionesDisponibles(reservaActual.getTipoHabitacion(), reservaActual.getFechaInicio(), reservaActual.getFechaFin());
            ArrayList<Habitacion> habitacionesLimpias = habitacionesDisponibles.stream()
                    .filter(h -> h.getEstado() == EstadoHabitacion.LIMPIA)
                    .collect(Collectors.toCollection(ArrayList::new));

            if (habitacionesLimpias.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay habitaciones de tipo '" + reservaActual.getTipoHabitacion() + "' limpias y disponibles.", "Disponibilidad No Encontrada", JOptionPane.WARNING_MESSAGE);
                resetPanel();
                return;
            }

            reservaInfoLabel.setText("Check-in para: " + reservaActual.getHuesped().getNombres() + " (" + reservaActual.getTipoHabitacion() + ")");
            habitacionesComboBox.setModel(new DefaultComboBoxModel<>(habitacionesLimpias.toArray(new Habitacion[0])));
            detailsPanel.setVisible(true);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El DNI debe ser un número.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void realizarCheckIn() {
        Habitacion habitacionSeleccionada = (Habitacion) habitacionesComboBox.getSelectedItem();
        if (habitacionSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una habitación.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Estadia nuevaEstadia = gestionEstadias.checkIn(reservaActual, habitacionSeleccionada.getNumero());

        if (nuevaEstadia != null) {
            JOptionPane.showMessageDialog(this, "Check-in realizado exitosamente en la habitación " + habitacionSeleccionada.getNumero(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
            resetPanel();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo realizar el check-in. La habitación podría no estar disponible.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetPanel() {
        dniField.setText("");
        detailsPanel.setVisible(false);
        reservaActual = null;
        reservaInfoLabel.setText("Por favor, busque una reserva.");
        habitacionesComboBox.setModel(new DefaultComboBoxModel<>());
    }
}
