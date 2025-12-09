package modelo;

public class Movimiento {
    private int id_movimiento, valor_movimiento,descuento, id_cliente;
    private String fecha_movimiento, observaciones, tipo_movimiento,estado_movimiento;

    public int getId_movimiento() {
        return id_movimiento;
    }

    public void setId_movimiento(int id_movimiento) {
        this.id_movimiento = id_movimiento;
    }

    // --- GETTER DE COMPATIBILIDAD CON XHTML ---
    // Permite que la vista use #{m.idMovimiento}
    public int getIdMovimiento() {
        return id_movimiento;
    }
    
    // El resto de los getters/setters que ya tenías:

    public int getValor_movimiento() {
        return valor_movimiento;
    }

    public void setValor_movimiento(int valor_movimiento) {
        this.valor_movimiento = valor_movimiento;
    }

    public int getDescuento() {
        return descuento;
    }

    public void setDescuento(int descuento) {
        this.descuento = descuento;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getFecha_movimiento() {
        return fecha_movimiento;
    }

    public void setFecha_movimiento(String fecha_movimiento) {
        this.fecha_movimiento = fecha_movimiento;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getTipo_movimiento() {
        return tipo_movimiento;
    }

    public void setTipo_movimiento(String tipo_movimiento) {
        this.tipo_movimiento = tipo_movimiento;
    }

    public String getEstado_movimiento() {
        return estado_movimiento;
    }

    public void setEstado_movimiento(String estado_movimiento) {
        this.estado_movimiento = estado_movimiento;
    }
}