package Main;

import Datos.*;
import GUI.LoginFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            // Cargar datos
            ColaPeliculas peliculas = ColaPeliculas.cargarArchivo("peliculas.txt");
            GestionUsuarios usuarios = GestionUsuarios.cargarArchivo("usuarios.dat");

            SwingUtilities.invokeLater(() -> {
                LoginFrame login = new LoginFrame(usuarios, peliculas);
                login.setVisible(true);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
