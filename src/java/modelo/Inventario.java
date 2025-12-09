package modelo;

import java.sql.Date; // Usaremos java.sql.Date para mapear la FECHA_VENCIMIENTO
import java.util.Objects;

public class Inventario {

    private Productos id_item; // Mapea la FK a la clase Productos (si existe, asumo que sí)
    private String ubicacion;
    private int cantidad;
    private String especificaciones;
    private Date fecha_vencimiento;
    private String estado;

    // Constructor por defecto
    public Inventario() {
    }

    // Getters y Setters
    
    public Productos getId_item() {
        return id_item;
    }

    public void setId_item(Productos id_item) {
        this.id_item = id_item;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getEspecificaciones() {
        return especificaciones;
    }

    public void setEspecificaciones(String especificaciones) {
        this.especificaciones = especificaciones;
    }

    public Date getFecha_vencimiento() {
        return fecha_vencimiento;
    }

    public void setFecha_vencimiento(Date fecha_vencimiento) {
        this.fecha_vencimiento = fecha_vencimiento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    // Nota: Es crucial implementar hashCode y equals para usar objetos complejos
    // como 'Productos' en la clave compuesta o para la selección en JSF.
    // Aunque la clave primaria es solo ID_ITEM, se recomienda para buenas prácticas.
    
    @Override
    public int hashCode() {
        return Objects.hash(id_item);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Inventario other = (Inventario) obj;
        return Objects.equals(id_item, other.id_item);
    }
}