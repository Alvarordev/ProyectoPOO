package gui;

import logic.GestionHabitaciones;
import logic.Habitacion;
import logic.EstadoHabitacion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

public class ReporteOcupacionPanel extends JPanel {
    private final MainFrame mainFrame;
    private final GestionHabitaciones gestionHabitaciones;

    private JTable tablaOcupacion;
    private DefaultTableModel tableModel;
    private JTextArea summaryArea;

    public ReporteOcupacionPanel(MainFrame mainFrame, GestionHabitaciones gestionHabitaciones) {
        this.mainFrame = mainFrame;
        this.gestionHabitaciones = gestionHabitaciones;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(new JLabel("Reporte de Ocupación", SwingConstants.CENTER) {{
            setFont(new Font("Arial", Font.BOLD, 24));
        }}, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));

        tableModel = new DefaultTableModel(new String[]{"Número Habitación", "Tipo", "Estado"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaOcupacion = new JTable(tableModel);
        centerPanel.add(new JScrollPane(tablaOcupacion), BorderLayout.CENTER);

        summaryArea = new JTextArea(5, 30);
        summaryArea.setEditable(false);
        summaryArea.setFont(new Font("Monospaced", Font.BOLD, 14));
        summaryArea.setBorder(BorderFactory.createTitledBorder("Resumen General"));
        centerPanel.add(summaryArea, BorderLayout.SOUTH);

        add(centerPanel, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton btnActualizar = new JButton("Actualizar Reporte");
        JButton btnVolver = new JButton("Volver al Menú");
        buttonsPanel.add(btnActualizar);
        buttonsPanel.add(btnVolver);
        add(buttonsPanel, BorderLayout.SOUTH);

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                actualizarReporte();
            }
        });

        btnVolver.addActionListener(e -> mainFrame.showPanel("MAIN_MENU"));
        btnActualizar.addActionListener(e -> actualizarReporte());
    }

    private void actualizarReporte() {
        tableModel.setRowCount(0);
        ArrayList<Habitacion> habitaciones = gestionHabitaciones.getHabitaciones();

        if (habitaciones.isEmpty()) {
            summaryArea.setText("No hay habitaciones registradas en el hotel.");
            return;
        }

        for (Habitacion habitacion : habitaciones) {
            tableModel.addRow(new Object[]{
                    habitacion.getNumero(),
                    habitacion.getTipo(),
                    habitacion.getEstado()
            });
        }

        Map<EstadoHabitacion, Long> summary = habitaciones.stream()
                .collect(Collectors.groupingBy(Habitacion::getEstado, Collectors.counting()));

        long ocupadas = summary.getOrDefault(EstadoHabitacion.OCUPADA, 0L);
        long libres = summary.getOrDefault(EstadoHabitacion.LIBRE, 0L);
        long sucias = summary.getOrDefault(EstadoHabitacion.SUCIA, 0L);
        long limpias = summary.getOrDefault(EstadoHabitacion.LIMPIA, 0L);

        String summaryText = String.format(
                "Total de Habitaciones: %d\n" +
                "Ocupadas:              %d\n" +
                "Libres (Disponibles):  %d\n" +
                "En Limpieza (Sucias):  %d\n" +
                "Listas para Check-in:  %d",
                habitaciones.size(), ocupadas, libres, sucias, limpias
        );
        
        summaryArea.setText(summaryText);
    }
}
