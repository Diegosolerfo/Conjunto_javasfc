package modelo;

/**
 * Clase auxiliar para representar un producto con cantidad en un movimiento
 */
public class ProductoMovimiento {
    private Productos producto;
    private int cantidad;
    private int subtotal; // precio * cantidad
    
    public ProductoMovimiento() {
    }
    
    public ProductoMovimiento(Productos producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.subtotal = producto.getValorUnitario() * cantidad;
    }
    
    public Productos getProducto() {
        return producto;
    }
    
    public void setProducto(Productos producto) {
        this.producto = producto;
        if (producto != null && cantidad > 0) {
            this.subtotal = producto.getValorUnitario() * cantidad;
        }
    }
    
    public int getCantidad() {
        return cantidad;
    }
    
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        if (producto != null && cantidad > 0) {
            this.subtotal = producto.getValorUnitario() * cantidad;
        }
    }
    
    public int getSubtotal() {
        return subtotal;
    }
    
    public void setSubtotal(int subtotal) {
        this.subtotal = subtotal;
    }
    
    public void recalcularSubtotal() {
        if (producto != null && cantidad > 0) {
            this.subtotal = producto.getValorUnitario() * cantidad;
        }
    }
}

