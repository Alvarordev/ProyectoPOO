package logic;

import java.util.ArrayList;

public class GestionFacturas {
    private ArrayList<Factura> historialFacturas;

    public GestionFacturas() {
        this.historialFacturas = new ArrayList<>();
    }

    public void agregarFactura(Factura factura) {
        historialFacturas.add(factura);
    }

    public String generarReporteIngresosGUI(String fechaInicio, String fechaFin) {
        StringBuilder reporte = new StringBuilder();
        double ingresosHabitaciones = 0;
        double ingresosServicios = 0;
        int facturasEnRango = 0;

        reporte.append("--- Reporte de Ingresos desde ").append(fechaInicio).append(" hasta ").append(fechaFin).append(" ---\n\n");

        for (Factura factura : historialFacturas) {
            String fechaFactura = factura.getFechaCheckout();
            if (fechaFactura.compareTo(fechaInicio) >= 0 && fechaFactura.compareTo(fechaFin) <= 0) {
                ingresosHabitaciones += factura.getTotalHabitacion();
                ingresosServicios += factura.getTotalServicios();
                facturasEnRango++;
                reporte.append("  - ").append(factura.toString()).append("\n");
            }
        }

        if (facturasEnRango == 0) {
            reporte.append("No se encontraron ingresos en el rango de fechas especificado.");
        } else {
            reporte.append("\n--- Resumen del Periodo ---\n");
            reporte.append(String.format("Total Ingresos por Habitaciones: $%.2f\n", ingresosHabitaciones));
            reporte.append(String.format("Total Ingresos por Servicios:    $%.2f\n", ingresosServicios));
            reporte.append("--------------------------------------\n");
            reporte.append(String.format("Total General:                   $%.2f\n", (ingresosHabitaciones + ingresosServicios)));
        }
        
        return reporte.toString();
    }
    
    public ArrayList<Factura> getHistorialFacturas() {
        return historialFacturas;
    }
}
