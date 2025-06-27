package Datos;

import java.io.Serializable;

public class Usuario implements Serializable {
    private String username;
    private String password;
    private String tipo; // "admin" o "normal"

    public Usuario(String username, String password, String tipo) {
        this.username = username;
        this.password = password;
        this.tipo = tipo;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getTipo() { return tipo; }
}
