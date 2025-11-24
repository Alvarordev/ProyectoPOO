package gui;

import logic.GestionHabitaciones;
import logic.Habitacion;
import logic.TipoHabitacion;
import logic.EstadoHabitacion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class GestionHabitacionesPanel extends JPanel {
    private final MainFrame mainFrame;
    private final GestionHabitaciones gestionHabitaciones;

    private JTable tablaHabitaciones;
    private DefaultTableModel tableModel;

    public GestionHabitacionesPanel(MainFrame mainFrame, GestionHabitaciones gestionHabitaciones) {
        this.mainFrame = mainFrame;
        this.gestionHabitaciones = gestionHabitaciones;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Gestión de Habitaciones", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        String[] columnNames = {"Número", "Tipo", "Capacidad", "Precio/Noche", "Estado"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaHabitaciones = new JTable(tableModel);
        add(new JScrollPane(tablaHabitaciones), BorderLayout.CENTER);

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
        btnCrear.addActionListener(e -> crearHabitacion());
        btnModificar.addActionListener(e -> modificarHabitacion());
        btnEliminar.addActionListener(e -> eliminarHabitacion());
    }

    private void actualizarTabla() {
        tableModel.setRowCount(0);
        ArrayList<Habitacion> habitaciones = gestionHabitaciones.getHabitaciones();
        for (Habitacion habitacion : habitaciones) {
            tableModel.addRow(new Object[]{
                    habitacion.getNumero(),
                    habitacion.getTipo(),
                    habitacion.getCapacidadMaxima(),
                    habitacion.getPrecioPorNoche(),
                    habitacion.getEstado()
            });
        }
    }

    private void crearHabitacion() {
        JTextField numeroField = new JTextField();
        JComboBox<TipoHabitacion> tipoComboBox = new JComboBox<>(TipoHabitacion.values());
        JTextField capacidadField = new JTextField();
        JTextField precioField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.add(new JLabel("Número:"));
        panel.add(numeroField);
        panel.add(new JLabel("Tipo:"));
        panel.add(tipoComboBox);
        panel.add(new JLabel("Capacidad Máxima:"));
        panel.add(capacidadField);
        panel.add(new JLabel("Precio por Noche:"));
        panel.add(precioField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Crear Nueva Habitación",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                int numero = Integer.parseInt(numeroField.getText());
                TipoHabitacion tipo = (TipoHabitacion) tipoComboBox.getSelectedItem();
                int capacidad = Integer.parseInt(capacidadField.getText());
                double precio = Double.parseDouble(precioField.getText());

                if (gestionHabitaciones.buscarPorNumero(numero) != null) {
                    JOptionPane.showMessageDialog(this, "Ya existe una habitación con ese número.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Habitacion nuevaHabitacion = new Habitacion(numero, tipo, capacidad, precio, EstadoHabitacion.LIMPIA);
                gestionHabitaciones.crearHabitacion(nuevaHabitacion);
                
                JOptionPane.showMessageDialog(this, "Habitación creada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                actualizarTabla();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Número, capacidad y precio deben ser valores numéricos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void modificarHabitacion() {
        int selectedRow = tablaHabitaciones.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una habitación para modificar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int numeroActual = (int) tableModel.getValueAt(selectedRow, 0);
        Habitacion habitacion = gestionHabitaciones.buscarPorNumero(numeroActual);

        JTextField numeroField = new JTextField(String.valueOf(habitacion.getNumero()));
        JComboBox<TipoHabitacion> tipoComboBox = new JComboBox<>(TipoHabitacion.values());
        tipoComboBox.setSelectedItem(habitacion.getTipo());
        JTextField capacidadField = new JTextField(String.valueOf(habitacion.getCapacidadMaxima()));
        JTextField precioField = new JTextField(String.valueOf(habitacion.getPrecioPorNoche()));
        JComboBox<EstadoHabitacion> estadoComboBox = new JComboBox<>(EstadoHabitacion.values());
        estadoComboBox.setSelectedItem(habitacion.getEstado());

        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.add(new JLabel("Número:"));
        panel.add(numeroField);
        panel.add(new JLabel("Tipo:"));
        panel.add(tipoComboBox);
        panel.add(new JLabel("Capacidad Máxima:"));
        panel.add(capacidadField);
        panel.add(new JLabel("Precio por Noche:"));
        panel.add(precioField);
        panel.add(new JLabel("Estado:"));
        panel.add(estadoComboBox);

        int result = JOptionPane.showConfirmDialog(this, panel, "Modificar Habitación",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                int nuevoNumero = Integer.parseInt(numeroField.getText());
                TipoHabitacion tipo = (TipoHabitacion) tipoComboBox.getSelectedItem();
                int capacidad = Integer.parseInt(capacidadField.getText());
                double precio = Double.parseDouble(precioField.getText());
                EstadoHabitacion estado = (EstadoHabitacion) estadoComboBox.getSelectedItem();

                if (nuevoNumero != numeroActual && gestionHabitaciones.buscarPorNumero(nuevoNumero) != null) {
                    JOptionPane.showMessageDialog(this, "El nuevo número de habitación ya está en uso.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                gestionHabitaciones.modificarHabitacion(numeroActual, tipo, capacidad, precio, estado, nuevoNumero);
                JOptionPane.showMessageDialog(this, "Habitación modificada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                actualizarTabla();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Número, capacidad y precio deben ser valores numéricos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void eliminarHabitacion() {
        int selectedRow = tablaHabitaciones.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una habitación para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int numero = (int) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar la habitación número " + numero + "?",
                "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            gestionHabitaciones.eliminarHabitacion(numero);
            JOptionPane.showMessageDialog(this, "Habitación eliminada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            actualizarTabla();
        }
    }
}
