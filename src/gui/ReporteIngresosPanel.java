package gui;

import logic.GestionFacturas;

import javax.swing.*;
import java.awt.*;

public class ReporteIngresosPanel extends JPanel {
    private final MainFrame mainFrame;
    private final GestionFacturas gestionFacturas;

    private JTextField fechaInicioField;
    private JTextField fechaFinField;
    private JTextArea reporteArea;

    public ReporteIngresosPanel(MainFrame mainFrame, GestionFacturas gestionFacturas) {
        this.mainFrame = mainFrame;
        this.gestionFacturas = gestionFacturas;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(new JLabel("Reporte de Ingresos", SwingConstants.CENTER) {{
            setFont(new Font("Arial", Font.BOLD, 24));
        }}, BorderLayout.NORTH);

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        filterPanel.add(new JLabel("Fecha Inicio (yyyy/MM/dd):"));
        fechaInicioField = new JTextField(10);
        filterPanel.add(fechaInicioField);
        filterPanel.add(new JLabel("Fecha Fin (yyyy/MM/dd):"));
        fechaFinField = new JTextField(10);
        filterPanel.add(fechaFinField);
        JButton btnGenerar = new JButton("Generar Reporte");
        filterPanel.add(btnGenerar);

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.add(filterPanel, BorderLayout.NORTH);

        reporteArea = new JTextArea();
        reporteArea.setEditable(false);
        reporteArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        centerPanel.add(new JScrollPane(reporteArea), BorderLayout.CENTER);
        
        add(centerPanel, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnVolver = new JButton("Volver al Menú");
        buttonsPanel.add(btnVolver);
        add(buttonsPanel, BorderLayout.SOUTH);

        btnVolver.addActionListener(e -> mainFrame.showPanel("MAIN_MENU"));
        btnGenerar.addActionListener(e -> generarReporte());
        
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                // Limpiar campos al mostrar el panel
                fechaInicioField.setText("");
                fechaFinField.setText("");
                reporteArea.setText("Ingrese un rango de fechas y genere el reporte.");
            }
        });
    }

    private void generarReporte() {
        String fechaInicio = fechaInicioField.getText();
        String fechaFin = fechaFinField.getText();

        if (!fechaInicio.matches("\\d{4}/\\d{2}/\\d{2}") || !fechaFin.matches("\\d{4}/\\d{2}/\\d{2}")) {
            JOptionPane.showMessageDialog(this, "Formato de fecha incorrecto. Use yyyy/MM/dd.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (fechaInicio.compareTo(fechaFin) > 0) {
            JOptionPane.showMessageDialog(this, "La fecha de fin no puede ser anterior a la fecha de inicio.", "Error de Fechas", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String reporte = gestionFacturas.generarReporteIngresosGUI(fechaInicio, fechaFin);
        reporteArea.setText(reporte);
        reporteArea.setCaretPosition(0);
    }
}
