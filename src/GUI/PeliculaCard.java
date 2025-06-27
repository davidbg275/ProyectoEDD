package GUI;

import Datos.Pelicula;
import Datos.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PeliculaCard extends JPanel {
    private Pelicula pelicula;
    private Usuario usuario;

    public PeliculaCard(Pelicula pelicula, Usuario usuario) {
        this.pelicula = pelicula;
        this.usuario = usuario;
        setPreferredSize(new Dimension(180, 280));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        setBackground(new Color(30, 30, 30));
        setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        setCursor(new Cursor(Cursor.HAND_CURSOR)); // cursor tipo botón


        initComponents();

// 👉 Listener para mostrar información al hacer clic
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mostrarInformacion();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setBorder(BorderFactory.createLineBorder(new Color(229, 9, 20), 2)); // Rojo Netflix al pasar
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2)); // Gris oscuro al salir
            }
        });

    }

    private void initComponents() {
        ImageIcon icon = new ImageIcon(pelicula.getRutaImagen());
        Image img = icon.getImage().getScaledInstance(180, 160, Image.SCALE_SMOOTH);
        JLabel lblImg = new JLabel(new ImageIcon(img));
        lblImg.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(lblImg);
        // Nombre
        JLabel lblNombre = new JLabel("<html><center>" + pelicula.getNombre() + "</center></html>");
        lblNombre.setHorizontalAlignment(SwingConstants.CENTER);

        // Calificación
        JLabel lblCalif = new JLabel("Calificación: " + String.format("%.1f", pelicula.getCalificacion()) + " ★");
        lblCalif.setHorizontalAlignment(SwingConstants.CENTER);

        // Botón calificar para usuarios normales
        JButton btnCalificar = new JButton("Calificar");
        btnCalificar.setEnabled(usuario.getTipo().equalsIgnoreCase("normal"));
        btnCalificar.setBackground(new Color(229, 9, 20)); // rojo Netflix
        btnCalificar.setForeground(Color.WHITE);
        btnCalificar.setFocusPainted(false);
        btnCalificar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));


        btnCalificar.addActionListener(e -> {
            String s = JOptionPane.showInputDialog(this, "Ingrese calificación (0 a 5):");
            if (s != null) {
                try {
                    double cal = Double.parseDouble(s);
                    if (cal >= 0 && cal <= 5) {
                        pelicula.agregarCalificacion(cal);
                        lblCalif.setText("Calificación: " + String.format("%.1f", pelicula.getCalificacion()) + " ★");
                    } else {
                        JOptionPane.showMessageDialog(this, "Calificación inválida");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Ingrese un número válido");
                }
            }
        });

        JPanel panelAbajo = new JPanel(new GridLayout(3, 1));
        panelAbajo.add(lblNombre);
        panelAbajo.add(lblCalif);
        panelAbajo.add(btnCalificar);

        add(panelAbajo, BorderLayout.SOUTH);
    }

    private void mostrarInformacion() {
        JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);
        DetallePeliculaDialog dialog = new DetallePeliculaDialog(parent, pelicula);
        dialog.setVisible(true);
    }


    public Pelicula getPelicula() {
        return pelicula;
    }


}
