package modelo;

public class Proveedor {
    private int id_proveedor;
    private long telefono;
    private String nombre, correo, direccion, estado;

    // --- GETTERS y SETTERS FUNCIONALES (Para el DAO) ---
    
    public void setId_proveedor(int id_proveedor) {
        this.id_proveedor = id_proveedor; 
    }

    public int getId_proveedor() {
        return id_proveedor;
    }

    // --- GETTERS CLAVE PARA JSF/XHTML (Compatibilidad) ---
    
    // Resuelve el error 'idProveedor' not found
    public int getIdProveedor() {
        return id_proveedor;
    }

    // Getter del nombre que usa la vista
    public String getNombre() {
        return nombre;
    }
    
    // --- RESTO DE GETTERS Y SETTERS ---
    
    public long getTelefono() {
        return telefono;
    }

    public void setTelefono(long telefono) {
        this.telefono = telefono;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}