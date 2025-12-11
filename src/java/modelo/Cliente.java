// Archivo: modelo/Cliente.java
package modelo;

public class Cliente {
    private long idCliente; // Usamos long para que coincida con la cédula de Usuario
    private String nombreCompleto; 

    public long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(long idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }
}