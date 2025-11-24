package gui;

import logic.*;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CheckOutPanel extends JPanel {
    private final MainFrame mainFrame;
    private final GestionEstadias gestionEstadias;

    private JTextField dniField;
    private JButton buscarButton;
    private JPanel detailsPanel;
    private JTextArea facturaArea;
    private JButton confirmarButton;

    private Estadia estadiaActual;

    public CheckOutPanel(MainFrame mainFrame, GestionEstadias gestionEstadias) {
        this.mainFrame = mainFrame;
        this.gestionEstadias = gestionEstadias;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        searchPanel.add(new JLabel("DNI del Huésped a dar de alta:"));
        dniField = new JTextField(10);
        searchPanel.add(dniField);
        buscarButton = new JButton("Buscar Estadia Activa");
        searchPanel.add(buscarButton);
        add(searchPanel, BorderLayout.NORTH);

        detailsPanel = new JPanel(new BorderLayout(10, 10));
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Resumen de Facturación"));
        
        facturaArea = new JTextArea();
        facturaArea.setEditable(false);
        facturaArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        detailsPanel.add(new JScrollPane(facturaArea), BorderLayout.CENTER);

        confirmarButton = new JButton("Confirmar Check-out y Facturar");
        detailsPanel.add(confirmarButton, BorderLayout.SOUTH);
        
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
        confirmarButton.addActionListener(e -> realizarCheckOut());
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

            facturaArea.setText(generarTextoFactura(estadiaActual));
            detailsPanel.setVisible(true);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El DNI debe ser un número.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String generarTextoFactura(Estadia estadia) {
        StringBuilder sb = new StringBuilder();
        Reserva reserva = estadia.getReserva();
        Huesped huesped = reserva.getHuesped();
        Habitacion habitacion = estadia.getHabitacion();

        sb.append("--- FACTURA HOTEL ---\n\n");
        sb.append("Huésped: ").append(huesped.getNombres()).append(" ").append(huesped.getApellidos()).append("\n");
        sb.append("DNI: ").append(huesped.getDNI()).append("\n\n");

        sb.append("--- Detalles de la Estadía ---\n");
        sb.append("Habitación: ").append(habitacion.getNumero()).append(" (Tipo ").append(habitacion.getTipo()).append(")\n");
        sb.append("Fechas: ").append(reserva.getFechaInicio()).append(" a ").append(reserva.getFechaFin()).append("\n");
        int noches = reserva.calcularNoches();
        sb.append(String.format("Costo por %d noche(s) (%.2f c/u): $%.2f\n\n", noches, habitacion.getPrecioPorNoche(), estadia.calcularTotalHabitacion()));

        sb.append("--- Servicios Consumidos ---\n");
        if (estadia.getServiciosConsumidos().isEmpty()) {
            sb.append("Ninguno.\n");
        } else {
            for (ServicioConsumido sc : estadia.getServiciosConsumidos()) {
                sb.append(String.format("- %s (x%d): $%.2f\n", sc.getServicio().getNombre(), sc.getCantidad(), sc.getCostoTotal()));
            }
        }
        sb.append(String.format("\nSubtotal Servicios: $%.2f\n\n", estadia.calcularTotalServicios()));

        sb.append("---------------------------------\n");
        sb.append(String.format("TOTAL A PAGAR: $%.2f\n", estadia.calcularTotalFinal()));
        sb.append("---------------------------------\n");

        return sb.toString();
    }

    private void realizarCheckOut() {
        int confirm = JOptionPane.showConfirmDialog(this, "¿Confirma el pago y la finalización de la estadía?", "Confirmar Check-out", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        String fechaActual = new SimpleDateFormat("yyyy/MM/dd").format(new Date());

        double totalPagado = gestionEstadias.checkOut(estadiaActual.getReserva().getHuesped().getDNI(), fechaActual);

        if (totalPagado >= 0) {
            JOptionPane.showMessageDialog(this, "Check-out realizado exitosamente.\nTotal pagado: $" + String.format("%.2f", totalPagado), "Éxito", JOptionPane.INFORMATION_MESSAGE);
            resetPanel();
        } else {
            JOptionPane.showMessageDialog(this, "Ocurrió un error al procesar el check-out.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetPanel() {
        dniField.setText("");
        detailsPanel.setVisible(false);
        estadiaActual = null;
        facturaArea.setText("");
    }
}
