package Datos;

import java.io.Serializable;
import java.util.ArrayList;

public class Pelicula implements Serializable {
    private String nombre;
    private int año;
    private String reseña;
    private double calificacion;
    private String rutaImagen;
    private int duracion;
    private String clasificacion;
    private String idioma;
    private ArrayList<String> actores;
    private int votos;

    public Pelicula(String nombre, int año, String reseña, String rutaImagen,
                    int duracion, String clasificacion, String idioma, ArrayList<String> actores) {
        this.nombre = nombre;
        this.año = año;
        this.reseña = reseña;
        this.rutaImagen = rutaImagen;
        this.duracion = duracion;
        this.clasificacion = clasificacion;
        this.idioma = idioma;
        this.actores = actores;
        this.calificacion = 0;
        this.votos = 0;
    }

    // Getters
    public String getNombre() { return nombre; }
    public int getAño() { return año; }
    public String getReseña() { return reseña; }
    public double getCalificacion() { return calificacion; }
    public String getRutaImagen() { return rutaImagen; }
    public int getDuracion() { return duracion; }
    public String getClasificacion() { return clasificacion; }
    public String getIdioma() { return idioma; }
    public ArrayList<String> getActores() { return actores; }

    // Setters (agregados para permitir edición)
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setAño(int año) { this.año = año; }
    public void setReseña(String reseña) { this.reseña = reseña; }
    public void setRutaImagen(String rutaImagen) { this.rutaImagen = rutaImagen; }
    public void setDuracion(int duracion) { this.duracion = duracion; }
    public void setClasificacion(String clasificacion) { this.clasificacion = clasificacion; }
    public void setIdioma(String idioma) { this.idioma = idioma; }
    public void setActores(ArrayList<String> actores) { this.actores = actores; }
    public void setCalificacion(double calificacion) { this.calificacion = calificacion; }
    public void setVotos(int votos) { this.votos = votos; }

    public void agregarCalificacion(double nuevaCalificacion) {
        double total = this.calificacion * votos;
        votos++;
        this.calificacion = (total + nuevaCalificacion) / votos;
    }

    @Override
    public String toString() {
        return nombre + " (" + año + ") - " + clasificacion + " - " + calificacion + " estrellas";
    }

    public String toTexto() {
        String reseñaSafe = reseña.replace("|", "/");
        String actoresTexto = String.join(",", actores);
        return nombre + "|" + año + "|" + reseñaSafe + "|" + calificacion + "|" + votos
                + "|" + rutaImagen + "|" + duracion + "|" + clasificacion + "|" + idioma
                + "||" + actoresTexto;
    }

    public static Pelicula desdeTexto(String linea) {
        String[] partes = linea.split("\\|", -1);
        if (partes.length != 11) {
            throw new IllegalArgumentException("Formato inválido para Pelicula: " + linea);
        }
        String nombre = partes[0];
        int año = Integer.parseInt(partes[1]);
        String reseña = partes[2];
        double calificacion = Double.parseDouble(partes[3]);
        int votos = Integer.parseInt(partes[4]);
        String rutaImagen = partes[5];
        int duracion = Integer.parseInt(partes[6]);
        String clasificacion = partes[7];
        String idioma = partes[8];
        ArrayList<String> actores = new ArrayList<>();
        if (!partes[10].isEmpty()) {
            for (String actor : partes[10].split(",")) {
                actores.add(actor.trim());
            }
        }
        Pelicula p = new Pelicula(nombre, año, reseña, rutaImagen, duracion, clasificacion, idioma, actores);
        p.setCalificacion(calificacion);
        p.setVotos(votos);
        return p;
    }
}
