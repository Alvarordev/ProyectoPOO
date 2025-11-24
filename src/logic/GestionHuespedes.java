package logic;

import java.util.ArrayList;

public class GestionHuespedes {
    private ArrayList<Huesped> huespedes;

    public GestionHuespedes() {
        huespedes = new ArrayList<>();
    }

    public ArrayList<Huesped> getHuespedes() {
        return huespedes;
    }

    public Huesped buscarPorDNI(Integer DNI) {
        for (Huesped huesped : huespedes) {
            if (DNI.equals(huesped.getDNI())) {
                return huesped;
            }
        }
        return null;
    }

    public boolean crearHuesped(Huesped huesped) {
        if (buscarPorDNI(huesped.getDNI()) != null) {
            return false;
        }
        huespedes.add(huesped);
        return true;
    }

    public boolean modificarHuesped(int DNI, String nombres, String apellidos, String telefono, String correo, Integer nuevoDNI) {
        Huesped huesped = buscarPorDNI(DNI);
        if (huesped == null) {
            return false;
        }

        if (nuevoDNI != null && !nuevoDNI.equals(DNI) && buscarPorDNI(nuevoDNI) != null) {
            return false;
        }

        huesped.setNombres(nombres);
        huesped.setApellidos(apellidos);
        huesped.setTelefono(telefono);
        huesped.setCorreo(correo);
        
        if (nuevoDNI != null) {
            huesped.setDNI(nuevoDNI);
        }
        return true;
    }

    public boolean eliminarHuesped(Integer DNI) {
        Huesped huesped = buscarPorDNI(DNI);
        if (huesped != null) {
            huespedes.remove(huesped);
            return true;
        }
        return false;
    }

    public void listarHuespedes() {
        if (huespedes.isEmpty()) {
            System.out.println("No hay huéspedes registrados.");
            return;
        }
        System.out.println("--- Lista de Huéspedes Registrados ---");
        for (Huesped h : huespedes) {
            System.out.println(h);
        }
    }
}
