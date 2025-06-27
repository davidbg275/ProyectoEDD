package Datos;

import java.io.*;
import java.util.ArrayList;

public class GestionUsuarios implements Serializable {
    private ArrayList<Usuario> usuarios;

    public GestionUsuarios() {
        usuarios = new ArrayList<>();
        // Usuario admin default
        usuarios.add(new Usuario("admin", "admin123", "admin"));
        usuarios.add(new Usuario("user", "user123", "normal"));
    }

    public boolean agregarUsuario(Usuario u) {
        for (Usuario usr : usuarios) {
            if (usr.getUsername().equalsIgnoreCase(u.getUsername())) {
                return false;
            }
        }
        usuarios.add(u);
        return true;
    }

    public Usuario login(String username, String password) {
        for (Usuario u : usuarios) {
            if (u.getUsername().equalsIgnoreCase(username) && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }

    public void guardarArchivo(String ruta) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ruta))) {
            oos.writeObject(this);
        }
    }

    public static GestionUsuarios cargarArchivo(String ruta) throws IOException, ClassNotFoundException {
        File f = new File(ruta);
        if (!f.exists()) {
            return new GestionUsuarios();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ruta))) {
            return (GestionUsuarios) ois.readObject();
        }
    }
}
