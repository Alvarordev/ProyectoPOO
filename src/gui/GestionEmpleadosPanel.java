package gui;

import logic.Autenticacion;
import logic.Empleado;
import logic.GestionEmpleados;
import logic.RolEmpleado;
import logic.Administrador;
import logic.Recepcionista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class GestionEmpleadosPanel extends JPanel {
    private final MainFrame mainFrame;
    private final GestionEmpleados gestionEmpleados;
    private final Autenticacion autenticacion;

    private JTable tablaEmpleados;
    private DefaultTableModel tableModel;

    public GestionEmpleadosPanel(MainFrame mainFrame, GestionEmpleados gestionEmpleados, Autenticacion autenticacion) {
        this.mainFrame = mainFrame;
        this.gestionEmpleados = gestionEmpleados;
        this.autenticacion = autenticacion;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Gestión de Empleados", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        String[] columnNames = {"DNI", "Nombres", "Apellidos", "Rol"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaEmpleados = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tablaEmpleados);
        add(scrollPane, BorderLayout.CENTER);

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
        btnCrear.addActionListener(e -> crearEmpleado());
        btnModificar.addActionListener(e -> modificarEmpleado());
        btnEliminar.addActionListener(e -> eliminarEmpleado());
    }

    private void actualizarTabla() {
        tableModel.setRowCount(0);
        ArrayList<Empleado> empleados = gestionEmpleados.getEmpleados();
        for (Empleado empleado : empleados) {
            Object[] rowData = {
                    empleado.getDNI(),
                    empleado.getNombres(),
                    empleado.getApellidos(),
                    empleado.getRolEmpleado().toString()
            };
            tableModel.addRow(rowData);
        }
    }

    private void crearEmpleado() {
        JTextField dniField = new JTextField();
        JTextField nombresField = new JTextField();
        JTextField apellidosField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JComboBox<RolEmpleado> rolComboBox = new JComboBox<>(RolEmpleado.values());

        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.add(new JLabel("DNI (8 dígitos):"));
        panel.add(dniField);
        panel.add(new JLabel("Nombres:"));
        panel.add(nombresField);
        panel.add(new JLabel("Apellidos:"));
        panel.add(apellidosField);
        panel.add(new JLabel("Contraseña:"));
        panel.add(passwordField);
        panel.add(new JLabel("Rol:"));
        panel.add(rolComboBox);

        int result = JOptionPane.showConfirmDialog(this, panel, "Crear Nuevo Empleado",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                int dni = Integer.parseInt(dniField.getText());
                String nombres = nombresField.getText();
                String apellidos = apellidosField.getText();
                String password = new String(passwordField.getPassword());
                RolEmpleado rol = (RolEmpleado) rolComboBox.getSelectedItem();

                if (nombres.isEmpty() || apellidos.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Empleado nuevoEmpleado;
                if (rol == RolEmpleado.ADMINISTRADOR) {
                    nuevoEmpleado = new Administrador(dni, nombres, apellidos, password);
                } else {
                    nuevoEmpleado = new Recepcionista(dni, nombres, apellidos, password);
                }
                
                if (nuevoEmpleado.getDNI() == 0) {
                     JOptionPane.showMessageDialog(this, "El DNI debe tener 8 dígitos.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                     return;
                }

                if (gestionEmpleados.crearEmpleado(nuevoEmpleado)) {
                    JOptionPane.showMessageDialog(this, "Empleado creado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    actualizarTabla();
                } else {
                    JOptionPane.showMessageDialog(this, "Ya existe un empleado con ese DNI.", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El DNI debe ser un número válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void modificarEmpleado() {
        int selectedRow = tablaEmpleados.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un empleado de la tabla para modificar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int dniActual = (int) tableModel.getValueAt(selectedRow, 0);
        Empleado empleadoAModificar = gestionEmpleados.buscarPorDNI(dniActual);

        if (empleadoAModificar == null) {
            JOptionPane.showMessageDialog(this, "Error: No se encontró al empleado en la base de datos.", "Error Interno", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JTextField dniField = new JTextField(String.valueOf(empleadoAModificar.getDNI()));
        JTextField nombresField = new JTextField(empleadoAModificar.getNombres());
        JTextField apellidosField = new JTextField(empleadoAModificar.getApellidos());
        JPasswordField passwordField = new JPasswordField();
        JComboBox<RolEmpleado> rolComboBox = new JComboBox<>(RolEmpleado.values());
        rolComboBox.setSelectedItem(empleadoAModificar.getRolEmpleado());

        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.add(new JLabel("DNI (8 dígitos):"));
        panel.add(dniField);
        panel.add(new JLabel("Nombres:"));
        panel.add(nombresField);
        panel.add(new JLabel("Apellidos:"));
        panel.add(apellidosField);
        panel.add(new JLabel("Nueva Contraseña (opcional):"));
        panel.add(passwordField);
        panel.add(new JLabel("Rol:"));
        panel.add(rolComboBox);

        int result = JOptionPane.showConfirmDialog(this, panel, "Modificar Empleado",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                int nuevoDNI = Integer.parseInt(dniField.getText());
                String nombres = nombresField.getText();
                String apellidos = apellidosField.getText();
                String password = new String(passwordField.getPassword());
                RolEmpleado rol = (RolEmpleado) rolComboBox.getSelectedItem();

                if (nombres.isEmpty() || apellidos.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Los campos de nombres y apellidos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Validar el nuevo DNI
                if (String.valueOf(nuevoDNI).length() != 8) {
                    JOptionPane.showMessageDialog(this, "El DNI debe tener 8 dígitos.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (gestionEmpleados.modificarEmpleado(dniActual, nombres, apellidos, password, rol, nuevoDNI)) {
                    JOptionPane.showMessageDialog(this, "Empleado modificado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    actualizarTabla();
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo modificar el empleado. El nuevo DNI podría estar en uso.", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El DNI debe ser un número válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void eliminarEmpleado() {
        int selectedRow = tablaEmpleados.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un empleado de la tabla para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int dni = (int) tableModel.getValueAt(selectedRow, 0);
        
        if (autenticacion.getEmpleadoLogueado() != null && autenticacion.getEmpleadoLogueado().getDNI() == dni) {
            JOptionPane.showMessageDialog(this, "No puedes eliminar tu propia cuenta de administrador.", "Acción no permitida", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea eliminar al empleado con DNI " + dni + "?",
                "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (gestionEmpleados.eliminarEmpleado(dni)) {
                JOptionPane.showMessageDialog(this, "Empleado eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                actualizarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo encontrar al empleado para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
