package gui;

import logic.GestionHuespedes;
import logic.GestionReservas;
import logic.Huesped;
import logic.TipoHabitacion;

import javax.swing.*;
import java.awt.*;

public class CrearReservaPanel extends JPanel {
    private final MainFrame mainFrame;
    private final GestionReservas gestionReservas;
    private final GestionHuespedes gestionHuespedes;

    public CrearReservaPanel(MainFrame mainFrame, GestionReservas gestionReservas, GestionHuespedes gestionHuespedes) {
        this.mainFrame = mainFrame;
        this.gestionReservas = gestionReservas;
        this.gestionHuespedes = gestionHuespedes;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        add(new JLabel("Crear Nueva Reserva", SwingConstants.CENTER) {{
            setFont(new Font("Arial", Font.BOLD, 24));
        }}, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField dniHuespedField = new JTextField(15);
        JComboBox<TipoHabitacion> tipoHabitacionComboBox = new JComboBox<>(TipoHabitacion.values());
        JTextField fechaInicioField = new JTextField(10);
        JTextField fechaFinField = new JTextField(10);
        
        // Añadir componentes al panel del formulario
        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(new JLabel("DNI del Huésped:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; formPanel.add(dniHuespedField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(new JLabel("Tipo de Habitación:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; formPanel.add(tipoHabitacionComboBox, gbc);

        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(new JLabel("Fecha Inicio (yyyy/MM/dd):"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; formPanel.add(fechaInicioField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; formPanel.add(new JLabel("Fecha Fin (yyyy/MM/dd):"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; formPanel.add(fechaFinField, gbc);

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton btnCrearReserva = new JButton("Crear Reserva");
        JButton btnVolver = new JButton("Volver al Menú");
        buttonsPanel.add(btnCrearReserva);
        buttonsPanel.add(btnVolver);
        add(buttonsPanel, BorderLayout.SOUTH);

        btnVolver.addActionListener(e -> mainFrame.showPanel("MAIN_MENU"));

        btnCrearReserva.addActionListener(e -> {
            try {
                int dni = Integer.parseInt(dniHuespedField.getText());
                TipoHabitacion tipo = (TipoHabitacion) tipoHabitacionComboBox.getSelectedItem();
                String fechaInicio = fechaInicioField.getText();
                String fechaFin = fechaFinField.getText();

                if (fechaInicio.trim().isEmpty() || fechaFin.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Las fechas son obligatorias.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (!fechaInicio.matches("\\d{4}/\\d{2}/\\d{2}") || !fechaFin.matches("\\d{4}/\\d{2}/\\d{2}")) {
                    JOptionPane.showMessageDialog(this, "Formato de fecha incorrecto. Use yyyy/MM/dd.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (fechaInicio.compareTo(fechaFin) >= 0) {
                    JOptionPane.showMessageDialog(this, "La fecha de fin debe ser posterior a la fecha de inicio.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Huesped huesped = gestionHuespedes.buscarPorDNI(dni);
                if (huesped == null) {
                    JOptionPane.showMessageDialog(this, "No se encontró ningún huésped con el DNI " + dni + ".\nPor favor, registre al huésped primero.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (gestionReservas.crearReserva(huesped, tipo, fechaInicio, fechaFin) != null) {
                    JOptionPane.showMessageDialog(this, "Reserva creada exitosamente para " + huesped.getNombres() + ".", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    // Limpiar campos
                    dniHuespedField.setText("");
                    fechaInicioField.setText("");
                    fechaFinField.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "No hay habitaciones de tipo '" + tipo + "' disponibles para las fechas seleccionadas.", "Disponibilidad No Encontrada", JOptionPane.WARNING_MESSAGE);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El DNI debe ser un número válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
