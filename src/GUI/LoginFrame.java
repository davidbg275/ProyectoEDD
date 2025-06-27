package GUI;

import Datos.*;
import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private JTextField txtUser;
    private JPasswordField txtPass;
    private JButton btnLogin;
    private GestionUsuarios gestionUsuarios;
    private ColaPeliculas colaPeliculas;

    public LoginFrame(GestionUsuarios usuarios, ColaPeliculas peliculas) {
        this.gestionUsuarios = usuarios;
        this.colaPeliculas = peliculas;

        setTitle("Netflix - Iniciar Sesión");
        setSize(400, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(null); // Usamos layout nulo para posición absoluta
        panel.setBackground(new Color(20, 20, 20));
        add(panel);

        JLabel lblTitulo = new JLabel("Netflix EDD");
        lblTitulo.setFont(new Font("Arial Black", Font.BOLD, 33));
        lblTitulo.setForeground(Color.RED);
        lblTitulo.setBounds(100, 50, 200, 40);
        panel.add(lblTitulo);

        JLabel lblUser = new JLabel("Usuario:");
        lblUser.setForeground(Color.WHITE);
        lblUser.setBounds(60, 130, 100, 25);
        panel.add(lblUser);

        txtUser = new JTextField();
        txtUser.setBounds(60, 160, 280, 35);
        txtUser.setBackground(new Color(30, 30, 30));
        txtUser.setForeground(Color.WHITE);
        txtUser.setCaretColor(Color.WHITE);
        txtUser.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panel.add(txtUser);

        JLabel lblPass = new JLabel("Contraseña:");
        lblPass.setForeground(Color.WHITE);
        lblPass.setBounds(60, 210, 100, 25);
        panel.add(lblPass);

        txtPass = new JPasswordField();
        txtPass.setBounds(60, 240, 280, 35);
        txtPass.setBackground(new Color(30, 30, 30));
        txtPass.setForeground(Color.WHITE);
        txtPass.setCaretColor(Color.WHITE);
        txtPass.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panel.add(txtPass);

        btnLogin = new JButton("Iniciar Sesión");
        btnLogin.setBounds(60, 300, 280, 40);
        btnLogin.setBackground(new Color(229, 9, 20));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogin.setFocusPainted(false);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.addActionListener(e -> login());
        panel.add(btnLogin);

    }

    private void login() {
        String user = txtUser.getText().trim();
        String pass = new String(txtPass.getPassword());

        Usuario usuario = gestionUsuarios.login(user, pass);
        if (usuario != null) {
            JOptionPane.showMessageDialog(this, "Bienvenido " + usuario.getUsername());
            this.dispose();
            CatalogoFrame catalogo = new CatalogoFrame(colaPeliculas, gestionUsuarios, usuario);
            catalogo.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos");
        }
    }
}
