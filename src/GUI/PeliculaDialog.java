package GUI;

import Datos.Pelicula;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PeliculaDialog extends JDialog {
    private JTextField txtNombre, txtAño, txtDuracion, txtActores, txtRutaImagen;
    private JTextArea txtReseña;
    private JComboBox<String> comboClasificacion, comboIdioma;

    private boolean guardado = false;
    private Pelicula pelicula;

    private static final String[] CLASIFICACIONES = { "G", "PG", "PG-13", "R", "NC-17", "AA" };
    private static final String[] IDIOMAS = { "Español", "Inglés", "Francés", "Alemán", "Japonés" };

    private final Color bgColor = new Color(20, 20, 20);
    private final Color fgColor = Color.WHITE;
    private final Color fieldBgColor = new Color(30, 30, 30);
    private final Color borderColor = new Color(80, 80, 80);
    private final Font font = new Font("Segoe UI", Font.PLAIN, 14);

    public PeliculaDialog(JFrame parent, String titulo, Pelicula pelicula) {
        super(parent, titulo, true);
        this.pelicula = pelicula;

        setSize(500, 500);
        setLocationRelativeTo(parent);
        initComponents();
        getContentPane().setBackground(bgColor);
        if (pelicula != null) cargarDatos();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridLayout(9, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        panel.setBackground(bgColor);

        panel.add(crearLabel("Nombre:"));
        txtNombre = crearTextField();
        panel.add(txtNombre);

        panel.add(crearLabel("Año:"));
        txtAño = crearTextField();
        panel.add(txtAño);

        panel.add(crearLabel("Reseña:"));
        txtReseña = new JTextArea(3, 20);
        txtReseña.setLineWrap(true);
        txtReseña.setWrapStyleWord(true);
        estiloTextArea(txtReseña);
        panel.add(new JScrollPane(txtReseña));

        panel.add(crearLabel("Duración (minutos):"));
        txtDuracion = crearTextField();
        panel.add(txtDuracion);

        panel.add(crearLabel("Clasificación:"));
        comboClasificacion = crearComboBox(CLASIFICACIONES);
        panel.add(comboClasificacion);

        panel.add(crearLabel("Idioma:"));
        comboIdioma = crearComboBox(IDIOMAS);
        panel.add(comboIdioma);

        panel.add(crearLabel("Actores (separados por coma):"));
        txtActores = crearTextField();
        panel.add(txtActores);

        panel.add(crearLabel("Ruta imagen:"));
        txtRutaImagen = crearTextField();
        panel.add(txtRutaImagen);

        JButton btnGuardar = crearBoton("Guardar", new Color(229, 9, 20));
        JButton btnCancelar = crearBoton("Cancelar", new Color(80, 80, 80));

        btnGuardar.addActionListener(e -> guardar());
        btnCancelar.addActionListener(e -> cancelar());

        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(bgColor);
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(panelBotones, BorderLayout.SOUTH);
    }

    private JLabel crearLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setForeground(fgColor);
        label.setFont(font);
        return label;
    }

    private JTextField crearTextField() {
        JTextField tf = new JTextField();
        tf.setBackground(fieldBgColor);
        tf.setForeground(fgColor);
        tf.setCaretColor(fgColor);
        tf.setFont(font);
        tf.setBorder(BorderFactory.createLineBorder(borderColor));
        return tf;
    }

    private void estiloTextArea(JTextArea ta) {
        ta.setBackground(fieldBgColor);
        ta.setForeground(fgColor);
        ta.setCaretColor(fgColor);
        ta.setFont(font);
        ta.setBorder(BorderFactory.createLineBorder(borderColor));
    }

    private JComboBox<String> crearComboBox(String[] items) {
        JComboBox<String> combo = new JComboBox<>(items);
        combo.setBackground(fieldBgColor);
        combo.setForeground(fgColor);
        combo.setFont(font);
        combo.setBorder(BorderFactory.createLineBorder(borderColor));
        return combo;
    }

    private JButton crearBoton(String texto, Color bg) {
        JButton btn = new JButton(texto);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(font);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void cargarDatos() {
        txtNombre.setText(pelicula.getNombre());
        txtAño.setText(String.valueOf(pelicula.getAño()));
        txtReseña.setText(pelicula.getReseña());
        txtDuracion.setText(String.valueOf(pelicula.getDuracion()));
        comboClasificacion.setSelectedItem(pelicula.getClasificacion());
        comboIdioma.setSelectedItem(pelicula.getIdioma());
        txtActores.setText(String.join(",", pelicula.getActores()));
        txtRutaImagen.setText(pelicula.getRutaImagen());
    }

    private void guardar() {
        try {
            String nombre = txtNombre.getText().trim();
            int año = Integer.parseInt(txtAño.getText().trim());
            String reseña = txtReseña.getText().trim();
            int duracion = Integer.parseInt(txtDuracion.getText().trim());
            String clasificacion = (String) comboClasificacion.getSelectedItem();
            String idioma = (String) comboIdioma.getSelectedItem();
            String rutaImagen = txtRutaImagen.getText().trim();
            String actoresTexto = txtActores.getText().trim();

            if (nombre.isEmpty() || reseña.isEmpty() || rutaImagen.isEmpty() || actoresTexto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe llenar todos los campos.");
                return;
            }

            ArrayList<String> actores = new ArrayList<>();
            for (String a : actoresTexto.split(",")) {
                actores.add(a.trim());
            }

            if (pelicula == null) {
                pelicula = new Pelicula(nombre, año, reseña, rutaImagen, duracion, clasificacion, idioma, actores);
            } else {
                pelicula.setNombre(nombre);
                pelicula.setAño(año);
                pelicula.setReseña(reseña);
                pelicula.setRutaImagen(rutaImagen);
                pelicula.setDuracion(duracion);
                pelicula.setClasificacion(clasificacion);
                pelicula.setIdioma(idioma);
                pelicula.setActores(actores);
            }

            guardado = true;
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Año y duración deben ser números válidos.");
        }
    }

    private void cancelar() {
        guardado = false;
        dispose();
    }

    public boolean isGuardado() {
        return guardado;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }
}
