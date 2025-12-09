package modelo;

public class Compra {

    private int idCompra; // Propiedad estándar
    private String fechaLlegada;
    private int cantidad;
    private int total;
    
    // Claves Foráneas
    private int idUsuario;
    private int idProducto;
    private int idProveedor;
    private int idMovimiento;

    // Constructor vacío
    public Compra() {}

    // --- GETTERS y SETTERS ESTÁNDAR (CamelCase) ---
    
    public int getIdCompra() { return idCompra; }
    public void setIdCompra(int idCompra) { this.idCompra = idCompra; }
    
    public String getFechaLlegada() { return fechaLlegada; }
    public void setFechaLlegada(String fechaLlegada) { this.fechaLlegada = fechaLlegada; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public int getTotal() { return total; }
    public void setTotal(int total) { this.total = total; }
    
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public int getIdProducto() { return idProducto; }
    public void setIdProducto(int idProducto) { this.idProducto = idProducto; }

    public int getIdProveedor() { return idProveedor; }
    public void setIdProveedor(int idProveedor) { this.idProveedor = idProveedor; }

    public int getIdMovimiento() { return idMovimiento; }
    public void setIdMovimiento(int idMovimiento) { this.idMovimiento = idMovimiento; }

    // ----------------------------------------------------------------------
    // --- GETTERS Y SETTERS DE COMPATIBILIDAD (Corregidos/Añadidos) ---
    // ----------------------------------------------------------------------

    // Compatibilidad para la clave primaria: id_compra (Resuelve el error de compilación)
    public int getId_compra() { 
        return idCompra; // Devuelve el valor de la propiedad estándar
    }
    public void setId_compra(int id_compra) { 
        this.idCompra = id_compra; // Asigna el valor a la propiedad estándar
    }
    
    // Compatibilidad para nuevo.xhtml (usa proveedorC)
    public int getProveedorC() { return idProveedor; }
    public void setProveedorC(int idProveedor) { this.idProveedor = idProveedor; }

    // Compatibilidad para nuevo.xhtml (usa movimiento)
    public int getMovimiento() { return idMovimiento; }
    public void setMovimiento(int idMovimiento) { this.idMovimiento = idMovimiento; }
    
    // Compatibilidad para nuevo.xhtml (usa productoComprado)
    public int getProductoComprado() { return idProducto; }
    public void setProductoComprado(int idProducto) { this.idProducto = idProducto; }
    
    // Compatibilidad para editarCompra.xhtml (usa fecha)
    public String getFecha() { return fechaLlegada; }
    public void setFecha(String fecha) { this.fechaLlegada = fecha; } 
}