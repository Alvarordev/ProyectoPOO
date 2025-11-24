package logic;

import java.util.ArrayList;

public class GestionServicios {
    private ArrayList<Servicio> servicios;

    public GestionServicios() {
        servicios = new ArrayList<>();
    }

    public ArrayList<Servicio> getServicios() {
        return servicios;
    }

    public Servicio buscarPorNombre(String nombre) {
        for (Servicio s : servicios) {
            if (s.getNombre().equalsIgnoreCase(nombre)) {
                return s;
            }
        }
        return null;
    }

    public boolean crearServicio(String nombre, double precio) {
        if (buscarPorNombre(nombre) != null) {
            return false;
        }
        Servicio nuevoServicio = new Servicio(nombre, precio);
        servicios.add(nuevoServicio);
        return true;
    }

    public boolean modificarServicio(String nombreActual, String nuevoNombre, Double nuevoPrecio) {
        Servicio servicio = buscarPorNombre(nombreActual);
        if (servicio == null) {
            return false;
        }

        if (nuevoNombre != null && !nuevoNombre.equalsIgnoreCase(nombreActual) && buscarPorNombre(nuevoNombre) != null) {
            return false;
        }

        if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
            servicio.setNombre(nuevoNombre);
        }
        if (nuevoPrecio != null) {
            servicio.setPrecio(nuevoPrecio);
        }
        return true;
    }

    public boolean eliminarServicio(String nombre) {
        Servicio servicio = buscarPorNombre(nombre);
        if (servicio == null) {
            return false;
        }
        servicios.remove(servicio);
        return true;
    }

    public void listarServicios() {
        if (servicios.isEmpty()) {
            System.out.println("No hay servicios registrados.");
            return;
        }
        System.out.println("--- Lista de Servicios del Hotel ---");
        for (Servicio s : servicios) {
            System.out.println(s);
        }
    }
}
