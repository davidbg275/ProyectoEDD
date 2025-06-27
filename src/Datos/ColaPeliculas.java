package Datos;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;

public class ColaPeliculas implements Serializable {
    private ArrayList<Pelicula> cola;

    public ColaPeliculas() {
        cola = new ArrayList<>();
    }

    public boolean agregarPelicula(Pelicula p) {
        for (Pelicula pel : cola) {
            if (pel.getNombre().equalsIgnoreCase(p.getNombre()) && pel.getAño() == p.getAño()) {
                return false;
            }
        }
        cola.add(p);
        return true;
    }

    public boolean eliminarPelicula(String nombre, int año) {
        return cola.removeIf(p -> p.getNombre().equalsIgnoreCase(nombre) && p.getAño() == año);
    }

    public Pelicula buscarPelicula(String nombre, int año) {
        for (Pelicula p : cola) {
            if (p.getNombre().equalsIgnoreCase(nombre) && p.getAño() == año) {
                return p;
            }
        }
        return null;
    }

    public ArrayList<Pelicula> listarPeliculas() {
        return new ArrayList<>(cola);
    }

    public void ordenarAscendente() {
        cola.sort(Comparator.comparing(Pelicula::getNombre));
    }

    public void ordenarDescendente() {
        cola.sort(Comparator.comparing(Pelicula::getNombre).reversed());
    }

    public ArrayList<Pelicula> filtrarPorLetra(char letra) {
        ArrayList<Pelicula> filtradas = new ArrayList<>();
        for (Pelicula p : cola) {
            if (p.getNombre().toLowerCase().startsWith(String.valueOf(letra).toLowerCase())) {
                filtradas.add(p);
            }
        }
        return filtradas;
    }

    public ArrayList<Pelicula> filtrarPorClasificacion(String clasificacion) {
        ArrayList<Pelicula> filtradas = new ArrayList<>();
        for (Pelicula p : cola) {
            if (p.getClasificacion().equalsIgnoreCase(clasificacion)) {
                filtradas.add(p);
            }
        }
        return filtradas;
    }

    public ArrayList<Pelicula> filtrarPorIdioma(String idioma) {
        ArrayList<Pelicula> filtradas = new ArrayList<>();
        for (Pelicula p : cola) {
            if (p.getIdioma().equalsIgnoreCase(idioma)) {
                filtradas.add(p);
            }
        }
        return filtradas;
    }

    public void guardarArchivoTexto(String ruta) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ruta))) {
            for (Pelicula p : cola) {
                writer.println(p.toTexto());
            }
        }
    }

    public static ColaPeliculas cargarArchivoTexto(String ruta) throws IOException {
        ColaPeliculas cp = new ColaPeliculas();
        File f = new File(ruta);
        if (!f.exists()) {
            return cp;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                linea = linea.trim();
                if (!linea.isEmpty()) {
                    try {
                        Pelicula p = Pelicula.desdeTexto(linea);
                        cp.cola.add(p);
                    } catch (Exception e) {
                        System.err.println("Error al cargar línea: " + linea);
                        e.printStackTrace();
                    }
                }
            }
        }
        return cp;
    }

    public void guardarArchivo(String ruta) throws IOException {
        guardarArchivoTexto(ruta);
    }

    public static ColaPeliculas cargarArchivo(String ruta) throws IOException, ClassNotFoundException {
        return cargarArchivoTexto(ruta);
    }

    // Verifica si la cola está vacía
    public boolean estaVacia() {
        return cola.isEmpty();
    }

    // Devuelve la primera película de la cola sin eliminarla
    public Pelicula obtenerPrimero() {
        if (!cola.isEmpty()) {
            return cola.get(0);
        }
        return null;
    }

    // Elimina la primera película de la cola
    public boolean eliminarPrimero() {
        if (!cola.isEmpty()) {
            cola.remove(0);
            return true;
        }
        return false;
    }

}
