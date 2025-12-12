package beans;

import dao.InventarioDAO;
import dao.MovimientoDAO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import modelo.Inventario;
import modelo.Movimiento;

@ManagedBean
@SessionScoped
public class ReporteBean implements Serializable {
    
    private InventarioDAO inventarioDAO = new InventarioDAO();
    private MovimientoDAO movimientoDAO = new MovimientoDAO();
    
    // Para reportes de inventario
    private List<Inventario> inventarioAltaDisponibilidad;
    private List<Inventario> inventarioBajaDisponibilidad;
    private int umbralAlta = 50; // Valor por defecto
    private int umbralBaja = 10; // Valor por defecto
    
    // Para reportes de movimientos
    private List<Movimiento> movimientosPorTipo;
    private List<Movimiento> movimientosPorFecha;
    private String tipoMovimientoSeleccionado = "";
    private String fechaInicio = "";
    private String fechaFin = "";
    private Map<String, Integer> resumenPorTipo;
    
    @PostConstruct
    public void init() {
        // Inicializar listas vacías
        inventarioAltaDisponibilidad = new ArrayList<>();
        inventarioBajaDisponibilidad = new ArrayList<>();
        movimientosPorTipo = new ArrayList<>();
        movimientosPorFecha = new ArrayList<>();
    }
    
    // ============================================
    // MÉTODOS PARA REPORTES DE INVENTARIO
    // ============================================
    
    /**
     * Genera reporte de productos con alta disponibilidad.
     */
    public void generarReporteAltaDisponibilidad() {
        inventarioAltaDisponibilidad = inventarioDAO.listarAltaDisponibilidad(umbralAlta);
    }
    
    /**
     * Genera reporte de productos con baja disponibilidad.
     */
    public void generarReporteBajaDisponibilidad() {
        inventarioBajaDisponibilidad = inventarioDAO.listarBajaDisponibilidad(umbralBaja);
    }
    
    /**
     * Limpia los reportes de inventario.
     */
    public void limpiarReportesInventario() {
        inventarioAltaDisponibilidad = new ArrayList<>();
        inventarioBajaDisponibilidad = new ArrayList<>();
    }
    
    // ============================================
    // MÉTODOS PARA REPORTES DE MOVIMIENTOS
    // ============================================
    
    /**
     * Genera reporte de movimientos por tipo.
     */
    public void generarReportePorTipo() {
        if (tipoMovimientoSeleccionado != null && !tipoMovimientoSeleccionado.isEmpty()) {
            movimientosPorTipo = movimientoDAO.listarPorTipo(tipoMovimientoSeleccionado);
        } else {
            movimientosPorTipo = new ArrayList<>();
        }
    }
    
    /**
     * Genera reporte de movimientos por rango de fechas.
     */
    public void generarReportePorFechas() {
        if (fechaInicio != null && !fechaInicio.isEmpty() && 
            fechaFin != null && !fechaFin.isEmpty()) {
            movimientosPorFecha = movimientoDAO.listarPorRangoFechas(fechaInicio, fechaFin);
        } else {
            movimientosPorFecha = new ArrayList<>();
        }
    }
    
    /**
     * Genera resumen de totales por tipo de movimiento.
     */
    public void generarResumenPorTipo() {
        resumenPorTipo = movimientoDAO.obtenerResumenPorTipo();
    }
    
    /**
     * Limpia los reportes de movimientos.
     */
    public void limpiarReportesMovimientos() {
        movimientosPorTipo = new ArrayList<>();
        movimientosPorFecha = new ArrayList<>();
        tipoMovimientoSeleccionado = "";
        fechaInicio = "";
        fechaFin = "";
        resumenPorTipo = null;
    }
    
    // ============================================
    // GETTERS Y SETTERS
    // ============================================
    
    public List<Inventario> getInventarioAltaDisponibilidad() {
        return inventarioAltaDisponibilidad;
    }
    
    public void setInventarioAltaDisponibilidad(List<Inventario> inventarioAltaDisponibilidad) {
        this.inventarioAltaDisponibilidad = inventarioAltaDisponibilidad;
    }
    
    public List<Inventario> getInventarioBajaDisponibilidad() {
        return inventarioBajaDisponibilidad;
    }
    
    public void setInventarioBajaDisponibilidad(List<Inventario> inventarioBajaDisponibilidad) {
        this.inventarioBajaDisponibilidad = inventarioBajaDisponibilidad;
    }
    
    public int getUmbralAlta() {
        return umbralAlta;
    }
    
    public void setUmbralAlta(int umbralAlta) {
        this.umbralAlta = umbralAlta;
    }
    
    public int getUmbralBaja() {
        return umbralBaja;
    }
    
    public void setUmbralBaja(int umbralBaja) {
        this.umbralBaja = umbralBaja;
    }
    
    public List<Movimiento> getMovimientosPorTipo() {
        return movimientosPorTipo;
    }
    
    public void setMovimientosPorTipo(List<Movimiento> movimientosPorTipo) {
        this.movimientosPorTipo = movimientosPorTipo;
    }
    
    public List<Movimiento> getMovimientosPorFecha() {
        return movimientosPorFecha;
    }
    
    public void setMovimientosPorFecha(List<Movimiento> movimientosPorFecha) {
        this.movimientosPorFecha = movimientosPorFecha;
    }
    
    public String getTipoMovimientoSeleccionado() {
        return tipoMovimientoSeleccionado;
    }
    
    public void setTipoMovimientoSeleccionado(String tipoMovimientoSeleccionado) {
        this.tipoMovimientoSeleccionado = tipoMovimientoSeleccionado;
    }
    
    public String getFechaInicio() {
        return fechaInicio;
    }
    
    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    
    public String getFechaFin() {
        return fechaFin;
    }
    
    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }
    
    public Map<String, Integer> getResumenPorTipo() {
        return resumenPorTipo;
    }
    
    public void setResumenPorTipo(Map<String, Integer> resumenPorTipo) {
        this.resumenPorTipo = resumenPorTipo;
    }
}

