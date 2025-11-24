package gui;

import logic.GestionHuespedes;
import logic.Huesped;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class GestionHuespedesPanel extends JPanel {
    private final MainFrame mainFrame;
    private final GestionHuespedes gestionHuespedes;

    private JTable tablaHuespedes;
    private DefaultTableModel tableModel;

    public GestionHuespedesPanel(MainFrame mainFrame, GestionHuespedes gestionHuespedes) {
        this.mainFrame = mainFrame;
        this.gestionHuespedes = gestionHuespedes;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(new JLabel("Gestión de Huéspedes", SwingConstants.CENTER) {{
            setFont(new Font("Arial", Font.BOLD, 24));
        }}, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"DNI", "Nombres", "Apellidos", "Teléfono", "Correo"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaHuespedes = new JTable(tableModel);
        add(new JScrollPane(tablaHuespedes), BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton btnCrear = new JButton("Crear");
        JButton btnModificar = new JButton("Modificar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnVolver = new JButton("Volver al Menú");
        
        buttonsPanel.add(btnCrear);
        buttonsPanel.add(btnModificar);
        buttonsPanel.add(btnEliminar);
        buttonsPanel.add(btnVolver);
        add(buttonsPanel, BorderLayout.SOUTH);

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                actualizarTabla();
            }
        });

        btnVolver.addActionListener(e -> mainFrame.showPanel("MAIN_MENU"));
        btnCrear.addActionListener(e -> crearHuesped());
        btnModificar.addActionListener(e -> modificarHuesped());
        btnEliminar.addActionListener(e -> eliminarHuesped());
    }

    private void actualizarTabla() {
        tableModel.setRowCount(0);
        ArrayList<Huesped> huespedes = gestionHuespedes.getHuespedes();
        for (Huesped huesped : huespedes) {
            tableModel.addRow(new Object[]{
                    huesped.getDNI(),
                    huesped.getNombres(),
                    huesped.getApellidos(),
                    huesped.getTelefono(),
                    huesped.getCorreo()
            });
        }
    }

    private void crearHuesped() {
        JTextField dniField = new JTextField();
        JTextField nombresField = new JTextField();
        JTextField apellidosField = new JTextField();
        JTextField telefonoField = new JTextField();
        JTextField correoField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.add(new JLabel("DNI (8 dígitos):"));
        panel.add(dniField);
        panel.add(new JLabel("Nombres:"));
        panel.add(nombresField);
        panel.add(new JLabel("Apellidos:"));
        panel.add(apellidosField);
        panel.add(new JLabel("Teléfono:"));
        panel.add(telefonoField);
        panel.add(new JLabel("Correo:"));
        panel.add(correoField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Crear Nuevo Huésped",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                int dni = Integer.parseInt(dniField.getText());
                String nombres = nombresField.getText();
                String apellidos = apellidosField.getText();
                String telefono = telefonoField.getText();
                String correo = correoField.getText();

                if (nombres.trim().isEmpty() || apellidos.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "DNI, Nombres y Apellidos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Huesped nuevoHuesped = new Huesped(dni, nombres, apellidos, telefono, correo);
                if (nuevoHuesped.getDNI() == 0) {
                    JOptionPane.showMessageDialog(this, "El DNI debe tener 8 dígitos.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (gestionHuespedes.crearHuesped(nuevoHuesped)) {
                    JOptionPane.showMessageDialog(this, "Huésped creado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    actualizarTabla();
                } else {
                    JOptionPane.showMessageDialog(this, "Ya existe un huésped con ese DNI.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El DNI debe ser un número válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void modificarHuesped() {
        int selectedRow = tablaHuespedes.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un huésped para modificar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int dniActual = (int) tableModel.getValueAt(selectedRow, 0);
        Huesped huesped = gestionHuespedes.buscarPorDNI(dniActual);

        JTextField dniField = new JTextField(String.valueOf(huesped.getDNI()));
        JTextField nombresField = new JTextField(huesped.getNombres());
        JTextField apellidosField = new JTextField(huesped.getApellidos());
        JTextField telefonoField = new JTextField(huesped.getTelefono());
        JTextField correoField = new JTextField(huesped.getCorreo());

        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.add(new JLabel("DNI:"));
        panel.add(dniField);
        panel.add(new JLabel("Nombres:"));
        panel.add(nombresField);
        panel.add(new JLabel("Apellidos:"));
        panel.add(apellidosField);
        panel.add(new JLabel("Teléfono:"));
        panel.add(telefonoField);
        panel.add(new JLabel("Correo:"));
        panel.add(correoField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Modificar Huésped",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                int nuevoDNI = Integer.parseInt(dniField.getText());
                String nombres = nombresField.getText();
                String apellidos = apellidosField.getText();
                String telefono = telefonoField.getText();
                String correo = correoField.getText();

                if (gestionHuespedes.modificarHuesped(dniActual, nombres, apellidos, telefono, correo, nuevoDNI)) {
                    JOptionPane.showMessageDialog(this, "Huésped modificado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    actualizarTabla();
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo modificar. El nuevo DNI ya podría estar en uso.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El DNI debe ser un número válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void eliminarHuesped() {
        int selectedRow = tablaHuespedes.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un huésped para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int dni = (int) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar al huésped con DNI " + dni + "?",
                "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (gestionHuespedes.eliminarHuesped(dni)) {
                JOptionPane.showMessageDialog(this, "Huésped eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                actualizarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar al huésped.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
