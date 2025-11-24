package logic;

public class DateUtil {


    public static boolean seSuperponen(String inicio1, String fin1, String inicio2, String fin2) {
        // Validar que las fechas no sean nulas
        if (inicio1 == null || fin1 == null || inicio2 == null || fin2 == null) {
            return false;
        }
        return inicio1.compareTo(fin2) <= 0 && fin1.compareTo(inicio2) >= 0;
    }

    public static int calcularNoches(String fechaInicio, String fechaFin) {
        if (fechaInicio == null || fechaFin == null || fechaInicio.compareTo(fechaFin) >= 0) {
            return 0;
        }

        return 1; // Devolvemos al menos 1 noche si el rango es válido.
    }
}
