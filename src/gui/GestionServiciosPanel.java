package gui;

import logic.GestionServicios;
import logic.Servicio;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class GestionServiciosPanel extends JPanel {
    private final MainFrame mainFrame;
    private final GestionServicios gestionServicios;

    private JTable tablaServicios;
    private DefaultTableModel tableModel;

    public GestionServiciosPanel(MainFrame mainFrame, GestionServicios gestionServicios) {
        this.mainFrame = mainFrame;
        this.gestionServicios = gestionServicios;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(new JLabel("Gestión de Servicios Adicionales", SwingConstants.CENTER) {{
            setFont(new Font("Arial", Font.BOLD, 24));
        }}, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"Nombre", "Precio"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaServicios = new JTable(tableModel);
        add(new JScrollPane(tablaServicios), BorderLayout.CENTER);

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
        btnCrear.addActionListener(e -> crearServicio());
        btnModificar.addActionListener(e -> modificarServicio());
        btnEliminar.addActionListener(e -> eliminarServicio());
    }

    private void actualizarTabla() {
        tableModel.setRowCount(0);
        ArrayList<Servicio> servicios = gestionServicios.getServicios();
        for (Servicio servicio : servicios) {
            tableModel.addRow(new Object[]{servicio.getNombre(), servicio.getPrecio()});
        }
    }

    private void crearServicio() {
        JTextField nombreField = new JTextField();
        JTextField precioField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.add(new JLabel("Nombre:"));
        panel.add(nombreField);
        panel.add(new JLabel("Precio:"));
        panel.add(precioField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Crear Nuevo Servicio",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String nombre = nombreField.getText();
                double precio = Double.parseDouble(precioField.getText());

                if (nombre.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (gestionServicios.crearServicio(nombre, precio)) {
                    JOptionPane.showMessageDialog(this, "Servicio creado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    actualizarTabla();
                } else {
                    JOptionPane.showMessageDialog(this, "Ya existe un servicio con ese nombre.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El precio debe ser un número válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void modificarServicio() {
        int selectedRow = tablaServicios.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un servicio para modificar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nombreActual = (String) tableModel.getValueAt(selectedRow, 0);
        Servicio servicio = gestionServicios.buscarPorNombre(nombreActual);

        JTextField nombreField = new JTextField(servicio.getNombre());
        JTextField precioField = new JTextField(String.valueOf(servicio.getPrecio()));

        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.add(new JLabel("Nombre:"));
        panel.add(nombreField);
        panel.add(new JLabel("Precio:"));
        panel.add(precioField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Modificar Servicio",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String nuevoNombre = nombreField.getText();
                double nuevoPrecio = Double.parseDouble(precioField.getText());

                if (nuevoNombre.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (gestionServicios.modificarServicio(nombreActual, nuevoNombre, nuevoPrecio)) {
                    JOptionPane.showMessageDialog(this, "Servicio modificado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    actualizarTabla();
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo modificar. El nuevo nombre ya podría estar en uso.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El precio debe ser un número válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void eliminarServicio() {
        int selectedRow = tablaServicios.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un servicio para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nombre = (String) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar el servicio '" + nombre + "'?",
                "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (gestionServicios.eliminarServicio(nombre)) {
                JOptionPane.showMessageDialog(this, "Servicio eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                actualizarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar el servicio.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
