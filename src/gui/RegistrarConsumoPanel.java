package gui;

import logic.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RegistrarConsumoPanel extends JPanel {
    private final MainFrame mainFrame;
    private final GestionEstadias gestionEstadias;
    private final GestionServicios gestionServicios;

    private JTextField dniField;
    private JButton buscarButton;
    private JPanel detailsPanel;
    private JLabel estadiaInfoLabel;
    private JComboBox<Servicio> serviciosComboBox;
    private JSpinner cantidadSpinner;
    private JButton confirmarButton;

    private Estadia estadiaActual;

    public RegistrarConsumoPanel(MainFrame mainFrame, GestionEstadias gestionEstadias, GestionServicios gestionServicios) {
        this.mainFrame = mainFrame;
        this.gestionEstadias = gestionEstadias;
        this.gestionServicios = gestionServicios;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        searchPanel.add(new JLabel("DNI del Huésped Alojado:"));
        dniField = new JTextField(10);
        searchPanel.add(dniField);
        buscarButton = new JButton("Buscar Estadia Activa");
        searchPanel.add(buscarButton);
        add(searchPanel, BorderLayout.NORTH);


        detailsPanel = new JPanel(new GridBagLayout());
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Registrar Nuevo Consumo"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        estadiaInfoLabel = new JLabel("Busque una estadía para registrar un consumo.");
        serviciosComboBox = new JComboBox<>();
        cantidadSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1)); // (valor inicial, min, max, paso)
        confirmarButton = new JButton("Añadir Consumo");

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; detailsPanel.add(estadiaInfoLabel, gbc);
        gbc.gridy = 1; gbc.gridwidth = 1; detailsPanel.add(new JLabel("Servicio:"), gbc);
        gbc.gridx = 1; detailsPanel.add(serviciosComboBox, gbc);
        gbc.gridy = 2; gbc.gridx = 0; detailsPanel.add(new JLabel("Cantidad:"), gbc);
        gbc.gridx = 1; detailsPanel.add(cantidadSpinner, gbc);
        gbc.gridy = 3; gbc.gridx = 0; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER; detailsPanel.add(confirmarButton, gbc);
        
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
        buscarButton.addActionListener(e -> buscarEstadia());
        confirmarButton.addActionListener(e -> registrarConsumo());
    }

    private void buscarEstadia() {
        try {
            int dni = Integer.parseInt(dniField.getText());
            estadiaActual = gestionEstadias.buscarEstadiaPorHuesped(dni);

            if (estadiaActual == null) {
                JOptionPane.showMessageDialog(this, "No se encontró una estadía activa para el DNI " + dni, "Estadía no encontrada", JOptionPane.ERROR_MESSAGE);
                resetPanel();
                return;
            }

            ArrayList<Servicio> servicios = gestionServicios.getServicios();
            if (servicios.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay servicios adicionales configurados en el sistema.", "Error de Configuración", JOptionPane.ERROR_MESSAGE);
                resetPanel();
                return;
            }

            Huesped huesped = estadiaActual.getReserva().getHuesped();
            estadiaInfoLabel.setText("Registrando consumo para: " + huesped.getNombres() + " " + huesped.getApellidos());
            serviciosComboBox.setModel(new DefaultComboBoxModel<>(servicios.toArray(new Servicio[0])));
            detailsPanel.setVisible(true);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El DNI debe ser un número.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void registrarConsumo() {
        Servicio servicioSeleccionado = (Servicio) serviciosComboBox.getSelectedItem();
        int cantidad = (int) cantidadSpinner.getValue();

        if (servicioSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un servicio.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        gestionEstadias.registrarConsumo(estadiaActual.getReserva().getHuesped().getDNI(), servicioSeleccionado, cantidad);
        JOptionPane.showMessageDialog(this, "Consumo de " + cantidad + "x " + servicioSeleccionado.getNombre() + " registrado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

        cantidadSpinner.setValue(1);
        serviciosComboBox.setSelectedIndex(0);
    }

    private void resetPanel() {
        dniField.setText("");
        detailsPanel.setVisible(false);
        estadiaActual = null;
        estadiaInfoLabel.setText("Busque una estadía para registrar un consumo.");
        serviciosComboBox.setModel(new DefaultComboBoxModel<>());
        cantidadSpinner.setValue(1);
    }
}
