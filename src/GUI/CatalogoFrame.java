package GUI;

import Datos.*;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class CatalogoFrame extends JFrame {
    private ColaPeliculas peliculas;
    private GestionUsuarios gestionUsuarios;
    private Usuario usuarioActual;

    private JTextField txtBusqueda;
    private JComboBox<String> cbFiltro;
    private JComboBox<String> cbOrden;
    private JPanel panelPeliculas;
    private JButton btnAgregar, btnModificar, btnEliminar;

    public CatalogoFrame(ColaPeliculas peliculas, GestionUsuarios gestionUsuarios, Usuario usuario) {
        this.peliculas = peliculas;
        this.gestionUsuarios = gestionUsuarios;
        this.usuarioActual = usuario;

        // Fuente general
        Font fuente = new Font("Segoe UI", Font.PLAIN, 14);
        UIManager.put("Label.font", fuente);
        UIManager.put("Button.font", fuente);
        UIManager.put("TextField.font", fuente);
        UIManager.put("ComboBox.font", fuente);

        setTitle("Catálogo de Películas - Usuario: " + usuario.getUsername());
        setSize(900, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initComponents();

        // Fondo oscuro para todo el frame
        getContentPane().setBackground(new Color(20, 20, 20));

        cargarPeliculas(peliculas.listarPeliculas());
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Panel superior con título y barra de búsqueda
        JPanel panelArriba = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelArriba.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelArriba.setBackground(new Color(20, 20, 20));

        txtBusqueda = new JTextField(20);
        txtBusqueda.setBackground(Color.DARK_GRAY);
        txtBusqueda.setForeground(Color.WHITE);
        txtBusqueda.setCaretColor(Color.WHITE);
        txtBusqueda.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        cbFiltro = new JComboBox<>(new String[]{"Todos", "Por Letra", "Por Clasificación", "Por Idioma"});
        cbFiltro.setBackground(Color.DARK_GRAY);
        cbFiltro.setForeground(Color.WHITE);

        cbOrden = new JComboBox<>(new String[]{"Original", "Orden Ascendente", "Orden Descendente"});
        cbOrden.setBackground(Color.DARK_GRAY);
        cbOrden.setForeground(Color.WHITE);

        panelArriba.add(new JLabel("Buscar:"));
        panelArriba.add(txtBusqueda);
        panelArriba.add(new JLabel("Filtro:"));
        panelArriba.add(cbFiltro);
        panelArriba.add(new JLabel("Orden:"));
        panelArriba.add(cbOrden);

        // Poner labels en blanco en panelArriba
        for (Component comp : panelArriba.getComponents()) {
            if (comp instanceof JLabel) {
                comp.setForeground(Color.WHITE);
            }
        }

        // Panel para título + barra superior
        JPanel panelNorte = new JPanel();
        panelNorte.setLayout(new BorderLayout());
        panelNorte.setBackground(new Color(20, 20, 20));

        JLabel titulo = new JLabel("Netflix EDD");
        titulo.setForeground(new Color(229, 9, 20));
        titulo.setFont(new Font("Segoe UI Black", Font.BOLD, 30));
        titulo.setBorder(new EmptyBorder(10, 20, 10, 20));

        panelNorte.add(titulo, BorderLayout.NORTH);
        panelNorte.add(panelArriba, BorderLayout.SOUTH);

        add(panelNorte, BorderLayout.NORTH);

        // Panel central con películas
        panelPeliculas = new JPanel();
        panelPeliculas.setLayout(new WrapLayout(FlowLayout.LEFT, 10, 10));
        panelPeliculas.setBackground(new Color(20, 20, 20));

        JScrollPane scroll = new JScrollPane(panelPeliculas);
        scroll.getViewport().setBackground(new Color(20, 20, 20));
        scroll.setBorder(null);  // sin borde
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        add(scroll, BorderLayout.CENTER);

        // Panel inferior con botones
        JPanel panelAbajo = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        panelAbajo.setBackground(new Color(20, 20, 20));

        btnAgregar = new JButton("Agregar");
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");

        if (!usuarioActual.getTipo().equalsIgnoreCase("admin")) {
            btnAgregar.setEnabled(false);
            btnModificar.setEnabled(false);
            btnEliminar.setEnabled(false);
        }

        Color rojoNetflix = new Color(229, 9, 20);
        for (JButton btn : new JButton[]{btnAgregar, btnModificar, btnEliminar}) {
            btn.setBackground(rojoNetflix);
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
            btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        panelAbajo.add(btnAgregar);
        panelAbajo.add(btnModificar);
        panelAbajo.add(btnEliminar);

        add(panelAbajo, BorderLayout.SOUTH);

        // Listeners de búsqueda y filtro
        txtBusqueda.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                filtrarYMostrar();
            }

            public void removeUpdate(DocumentEvent e) {
                filtrarYMostrar();
            }

            public void changedUpdate(DocumentEvent e) {
                filtrarYMostrar();
            }
        });

        cbFiltro.addActionListener(e -> filtrarYMostrar());
        cbOrden.addActionListener(e -> filtrarYMostrar());

        btnAgregar.addActionListener(e -> abrirDialogoAgregar());
        btnModificar.addActionListener(e -> abrirDialogoModificar());
        btnEliminar.addActionListener(e -> eliminarPeliculaSeleccionada());
    }

    private void cargarPeliculas(ArrayList<Pelicula> lista) {
        panelPeliculas.removeAll();
        for (Pelicula p : lista) {
            panelPeliculas.add(new PeliculaCard(p, usuarioActual));
        }
        panelPeliculas.revalidate();
        panelPeliculas.repaint();
    }

    private void filtrarYMostrar() {
        String texto = txtBusqueda.getText().trim().toLowerCase();
        String filtro = (String) cbFiltro.getSelectedItem();
        String orden = (String) cbOrden.getSelectedItem();

        // Obtener todas las películas
        ArrayList<Pelicula> lista = peliculas.listarPeliculas();

        // Filtrar por coincidencia en nombre
        if (!texto.isEmpty()) {
            lista.removeIf(p -> !p.getNombre().toLowerCase().contains(texto));
        }

        // Filtro adicional (clasificación, idioma, letra)
        switch (filtro) {
            case "Por Letra":
                if (!texto.isEmpty()) {
                    char letra = texto.charAt(0);
                    lista.removeIf(p -> Character.toLowerCase(p.getNombre().charAt(0)) != letra);
                }
                break;

            case "Por Clasificación":
                lista.removeIf(p -> !p.getClasificacion().toLowerCase().contains(texto));
                break;

            case "Por Idioma":
                lista.removeIf(p -> !p.getIdioma().toLowerCase().contains(texto));
                break;

            case "Todos":
            default:
                // Nada adicional
                break;
        }

        // Ordenar resultados
        switch (orden) {
            case "Orden Ascendente":
                lista.sort((a, b) -> a.getNombre().compareToIgnoreCase(b.getNombre()));
                break;
            case "Orden Descendente":
                lista.sort((a, b) -> b.getNombre().compareToIgnoreCase(a.getNombre()));
                break;
        }

        cargarPeliculas(lista); // Actualiza el panel con las películas filtradas
    }


    private PeliculaCard peliculaSeleccionada = null;

    private void abrirDialogoAgregar() {
        PeliculaDialog dialog = new PeliculaDialog(this, "Agregar Película", null);
        dialog.setVisible(true);
        if (dialog.isGuardado()) {
            Pelicula nueva = dialog.getPelicula();
            if (peliculas.agregarPelicula(nueva)) {
                try {
                    peliculas.guardarArchivo("peliculas.txt");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                filtrarYMostrar();
            } else {
                JOptionPane.showMessageDialog(this, "La película ya existe.");
            }
        }
    }

    private void abrirDialogoModificar() {
        Pelicula p = seleccionarPelicula();
        if (p == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una película para modificar");
            return;
        }

        PeliculaDialog dialog = new PeliculaDialog(this, "Modificar Película", p);
        dialog.setVisible(true);
        if (dialog.isGuardado()) {
            try {
                peliculas.guardarArchivo("peliculas.txt");
            } catch (Exception e) {
                e.printStackTrace();
            }
            filtrarYMostrar();
        }
    }

    private void eliminarPeliculaSeleccionada() {
        if (peliculas.estaVacia()) {
            JOptionPane.showMessageDialog(this, "No hay películas para eliminar.");
            return;
        }

        Pelicula primero = peliculas.obtenerPrimero();
        int res = JOptionPane.showConfirmDialog(this,
                "¿Está seguro que desea eliminar la película \"" + primero.getNombre() + "\" (" + primero.getAño() + ")?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (res == JOptionPane.YES_OPTION) {
            peliculas.eliminarPelicula(primero.getNombre(), primero.getAño());
            try {
                peliculas.guardarArchivo("peliculas.txt");
            } catch (IOException e) {
                e.printStackTrace();
            }
            filtrarYMostrar();
        }
    }

    private Pelicula seleccionarPelicula() {
        String nombre = JOptionPane.showInputDialog(this, "Ingrese el nombre exacto de la película:");
        if (nombre == null || nombre.trim().isEmpty()) return null;

        for (Pelicula p : peliculas.listarPeliculas()) {
            if (p.getNombre().equalsIgnoreCase(nombre.trim())) {
                return p;
            }
        }
        return null;
    }
}
