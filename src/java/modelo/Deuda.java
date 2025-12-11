package modelo;

import java.io.Serializable;
import java.sql.Date; // Usaremos java.sql.Date para las fechas de la DB

public class Deuda implements Serializable {
    
    // Campos que coinciden con la estructura de la tabla DEUDA
    private int idDeuda; // ID_DEUDA (TINYINT UNSIGNED -> int)
    private int saldo; // SALDO (INT UNSIGNED -> int)
    private Date fechaVencimiento; // FECHA_VENCIMIENTO (DATE -> java.sql.Date)
    
    // Claves Foráneas (Usaremos los IDs simples, asumiendo que el DAO de Deuda los necesita)
    private int movimiento; // MOVIMIENTO (SMALLINT UNSIGNED -> int)
    private int productoPendiente; // PRODUCTO_PENDIENTE (TINYINT UNSIGNED -> int)

    // --- Constructor Vacío ---
    public Deuda() {
    }

    // --- Constructor con Parámetros (Opcional) ---
    public Deuda(int idDeuda, int saldo, Date fechaVencimiento, int movimiento, int productoPendiente) {
        this.idDeuda = idDeuda;
        this.saldo = saldo;
        this.fechaVencimiento = fechaVencimiento;
        this.movimiento = movimiento;
        this.productoPendiente = productoPendiente;
    }
    
    // --- Getters y Setters ---

    public int getIdDeuda() {
        return idDeuda;
    }

    public void setIdDeuda(int idDeuda) {
        this.idDeuda = idDeuda;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    // Nota: El Setter puede recibir un java.util.Date o un String si se maneja en el Bean/DAO
    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public int getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(int movimiento) {
        this.movimiento = movimiento;
    }

    public int getProductoPendiente() {
        return productoPendiente;
    }

    public void setProductoPendiente(int productoPendiente) {
        this.productoPendiente = productoPendiente;
    }

    // Opcional: Implementar toString()
    @Override
    public String toString() {
        return "Deuda{" + "idDeuda=" + idDeuda + ", saldo=" + saldo + ", fechaVencimiento=" + fechaVencimiento + ", movimiento=" + movimiento + ", productoPendiente=" + productoPendiente + '}';
    }
}