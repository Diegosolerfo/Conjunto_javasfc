package modelo;

public class Productos {
    
    // Propiedades del POJO en CamelCase (Base de datos)
    private int idProducto;
    private String nombreProducto; 
    private String descripcionProducto;
    private int valorUnitario;
    private String unidadMedida;
    private String estadoProducto;

    // ----------------------------------------------------------------------
    // --- GETTERS y SETTERS ESTÁNDAR (CamelCase) ---
    // ----------------------------------------------------------------------
    
    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    // [Resto de los Getters y Setters en CamelCase son correctos y se mantienen]
    // ...

    public String getDescripcionProducto() { return descripcionProducto; }
    public void setDescripcionProducto(String descripcionProducto) { this.descripcionProducto = descripcionProducto; }
    public int getValorUnitario() { return valorUnitario; }
    public void setValorUnitario(int valorUnitario) { this.valorUnitario = valorUnitario; }
    public String getUnidadMedida() { return unidadMedida; }
    public void setUnidadMedida(String unidadMedida) { this.unidadMedida = unidadMedida; }
    public String getEstadoProducto() { return estadoProducto; }
    public void setEstadoProducto(String estadoProducto) { this.estadoProducto = estadoProducto; }

    // ----------------------------------------------------------------------
    // --- GETTERS y SETTERS de COMPATIBILIDAD (Corregidos) ---
    // Estos métodos resuelven los errores 'cannot find symbol: method XXX' 
    // y los 'UnsupportedOperationException' al llamar a la propiedad CamelCase.
    // ----------------------------------------------------------------------

    /** Resuelve el uso de #{p.nombre} en la vista y las llamadas DAO que usan 'NOMBRE' */
    public String getNombre() {
        return nombreProducto;
    }

    /** Resuelve el uso de setNombre(String) en los DAOs */
    public void setNombre(String nombre) {
        this.nombreProducto = nombre;
    }
    
    /** Resuelve el uso de getId_producto() en InventarioDAO y CompraBean */
    public int getId_producto() {
        return idProducto;
    }

    /** Resuelve el uso de setId_producto(int) en los DAOs */
    public void setId_producto(int id) {
        this.idProducto = id;
    }
}