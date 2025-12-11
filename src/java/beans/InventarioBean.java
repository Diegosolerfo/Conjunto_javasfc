package beans;

import dao.InventarioDAO;
import dao.ProductosDAO;
import modelo.Inventario;
import modelo.Productos;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class InventarioBean implements Serializable {

    private Inventario inventario = new Inventario();
    private List<Inventario> listaInventario = new ArrayList<>();
    private List<Productos> listaProductosDisponibles = new ArrayList<>();
    private int idProductoSeleccionado; // Campo temporal para el ID del producto seleccionado
    private java.util.Date fechaVencimientoUtil; // Campo temporal para la fecha (java.util.Date)
    
    private InventarioDAO inventarioDAO = new InventarioDAO();
    private ProductosDAO productosDAO = new ProductosDAO();

    @PostConstruct
    public void init() {
        listar();
        // Cargar todos los productos para el dropdown del formulario
        listaProductosDisponibles = productosDAO.listar(); 
    }
    
    // ----------------------
    // MÉTODOS DE ACCIÓN (CRUD)
    // ----------------------

    public void listar() {
        listaInventario = inventarioDAO.listar();
    }
    
    public String nuevo() {
        this.inventario = new Inventario();
        this.idProductoSeleccionado = 0;
        this.fechaVencimientoUtil = null;
        // Recargar productos disponibles
        listaProductosDisponibles = productosDAO.listar();
        return "nuevo?faces-redirect=true";
    }
    
    public String guardar() {
        // Convertir el ID seleccionado a un objeto Productos
        if (idProductoSeleccionado > 0) {
            Productos producto = productosDAO.buscar(idProductoSeleccionado);
            inventario.setId_item(producto);
            
            // Verificar si ya existe un registro de inventario para este producto
            Inventario inventarioExistente = inventarioDAO.buscar(idProductoSeleccionado);
            
            if (inventarioExistente != null) {
                // Si existe, sumar la cantidad nueva a la existente
                int cantidadNueva = inventario.getCantidad();
                int cantidadExistente = inventarioExistente.getCantidad();
                int cantidadTotal = cantidadExistente + cantidadNueva;
                
                // Actualizar el inventario existente con la cantidad sumada
                inventarioExistente.setCantidad(cantidadTotal);
                
                // Convertir java.util.Date a java.sql.Date si hay fecha nueva
                if (fechaVencimientoUtil != null) {
                    inventarioExistente.setFecha_vencimiento(new Date(fechaVencimientoUtil.getTime()));
                }
                
                // Actualizar ubicación y especificaciones si se proporcionaron
                if (inventario.getUbicacion() != null && !inventario.getUbicacion().isEmpty()) {
                    inventarioExistente.setUbicacion(inventario.getUbicacion());
                }
                if (inventario.getEspecificaciones() != null && !inventario.getEspecificaciones().isEmpty()) {
                    inventarioExistente.setEspecificaciones(inventario.getEspecificaciones());
                }
                if (inventario.getEstado() != null && !inventario.getEstado().isEmpty()) {
                    inventarioExistente.setEstado(inventario.getEstado());
                }
                
                // Actualizar el registro existente
                inventarioDAO.actualizar(inventarioExistente);
                
                // Mensaje informativo
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Inventario Actualizado", 
                        "El producto ya existía en inventario. Se sumaron " + cantidadNueva + 
                        " unidades. Cantidad total: " + cantidadTotal));
            } else {
                // Si no existe, crear un nuevo registro
                // Convertir java.util.Date a java.sql.Date
                if (fechaVencimientoUtil != null) {
                    inventario.setFecha_vencimiento(new Date(fechaVencimientoUtil.getTime()));
                }
                inventarioDAO.guardar(inventario);
                
                // Mensaje informativo
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Inventario Creado", 
                        "Se creó un nuevo registro de inventario con " + inventario.getCantidad() + " unidades."));
            }
        }
        
        // Reiniciar y recargar
        this.inventario = new Inventario();
        this.idProductoSeleccionado = 0;
        this.fechaVencimientoUtil = null;
        listar();
        return "index?faces-redirect=true";
    }
    
    public String buscar(int idProducto) {
        this.inventario = inventarioDAO.buscar(idProducto);
        // Establecer el ID del producto seleccionado para el dropdown
        if (inventario.getId_item() != null) {
            this.idProductoSeleccionado = inventario.getId_item().getIdProducto();
        }
        // Convertir java.sql.Date a java.util.Date para el formulario
        if (inventario.getFecha_vencimiento() != null) {
            this.fechaVencimientoUtil = new java.util.Date(inventario.getFecha_vencimiento().getTime());
        } else {
            this.fechaVencimientoUtil = null;
        }
        // Recargar productos disponibles para el dropdown
        listaProductosDisponibles = productosDAO.listar();
        return "editar?faces-redirect=true";
    }
    
    public String actualizar() {
        // Convertir el ID seleccionado a un objeto Productos
        if (idProductoSeleccionado > 0) {
            Productos producto = productosDAO.buscar(idProductoSeleccionado);
            inventario.setId_item(producto);
        }
        // Convertir java.util.Date a java.sql.Date
        if (fechaVencimientoUtil != null) {
            inventario.setFecha_vencimiento(new Date(fechaVencimientoUtil.getTime()));
        }
        inventarioDAO.actualizar(inventario);
        return "index?faces-redirect=true";
    }
    
    public void eliminar(int idProducto) {
        inventarioDAO.eliminar(idProducto);
        listar();
    }

    // ----------------------
    // GETTERS Y SETTERS
    // ----------------------

    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }

    public List<Inventario> getListaInventario() {
        return listaInventario;
    }

    public List<Productos> getListaProductosDisponibles() {
        return listaProductosDisponibles;
    }
    
    public int getIdProductoSeleccionado() {
        return idProductoSeleccionado;
    }
    
    public void setIdProductoSeleccionado(int idProductoSeleccionado) {
        this.idProductoSeleccionado = idProductoSeleccionado;
    }
    
    public java.util.Date getFechaVencimientoUtil() {
        return fechaVencimientoUtil;
    }
    
    public void setFechaVencimientoUtil(java.util.Date fechaVencimientoUtil) {
        this.fechaVencimientoUtil = fechaVencimientoUtil;
    }
}