package GUI;

import Datos.Pelicula;

import javax.swing.*;
import java.awt.*;

public class DetallePeliculaDialog extends JDialog {
    public DetallePeliculaDialog(JFrame parent, Pelicula pelicula) {
        super(parent, "Información de la Película", true);
        setSize(400, 450);
        setLocationRelativeTo(parent);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(18, 18, 18)); // Fondo oscuro estilo Netflix
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblNombre = new JLabel(pelicula.getNombre());
        lblNombre.setForeground(Color.WHITE);
        lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblNombre.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblAño = new JLabel("Año: " + pelicula.getAño());
        lblAño.setForeground(Color.LIGHT_GRAY);
        lblAño.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblAño.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextArea txtReseña = new JTextArea(pelicula.getReseña());
        txtReseña.setLineWrap(true);
        txtReseña.setWrapStyleWord(true);
        txtReseña.setEditable(false);
        txtReseña.setBackground(new Color(30, 30, 30));
        txtReseña.setForeground(Color.WHITE);
        txtReseña.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtReseña.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        txtReseña.setAlignmentX(Component.CENTER_ALIGNMENT);
        txtReseña.setMaximumSize(new Dimension(370, 120));

        JLabel lblDuracion = new JLabel("Duración: " + pelicula.getDuracion() + " min");
        lblDuracion.setForeground(Color.LIGHT_GRAY);
        lblDuracion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblDuracion.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblClasificacion = new JLabel("Clasificación: " + pelicula.getClasificacion());
        lblClasificacion.setForeground(Color.LIGHT_GRAY);
        lblClasificacion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblClasificacion.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblIdioma = new JLabel("Idioma: " + pelicula.getIdioma());
        lblIdioma.setForeground(Color.LIGHT_GRAY);
        lblIdioma.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblIdioma.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblActores = new JLabel("Actores: " + String.join(", ", pelicula.getActores()));
        lblActores.setForeground(Color.LIGHT_GRAY);
        lblActores.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblActores.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblCalificacion = new JLabel("Calificación: " + String.format("%.1f", pelicula.getCalificacion()) + " ★");
        lblCalificacion.setForeground(new Color(229, 9, 20)); // Rojo Netflix
        lblCalificacion.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblCalificacion.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setBackground(new Color(229, 9, 20));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setFocusPainted(false);
        btnCerrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCerrar.addActionListener(e -> dispose());

        panel.add(lblNombre);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(lblAño);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(txtReseña);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(lblDuracion);
        panel.add(lblClasificacion);
        panel.add(lblIdioma);
        panel.add(lblActores);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(lblCalificacion);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(btnCerrar);

        add(panel);
    }
}
