package beans;

import dao.MovimientoDAO;
import dao.ClienteDAO;
import dao.ProductosDAO;
import dao.DeudaDAO;
import dao.InventarioDAO;
import dao.ProveedorDAO;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import modelo.Movimiento;
import modelo.Cliente;
import modelo.Productos;
import modelo.ProductoMovimiento;
import modelo.Deuda;
import modelo.Inventario;
import modelo.Proveedor;

@ManagedBean
@SessionScoped
public class MovimientoBean implements Serializable {

    // DAOs
    MovimientoDAO mDAO = new MovimientoDAO();
    ClienteDAO cDAO = new ClienteDAO();
    ProductosDAO productosDAO = new ProductosDAO();
    DeudaDAO deudaDAO = new DeudaDAO();
    InventarioDAO inventarioDAO = new InventarioDAO();
    ProveedorDAO proveedorDAO = new ProveedorDAO();
    
    // Modelos y Listas para Movimiento
    Movimiento movimiento = new Movimiento();
    List<Movimiento> listaM = new ArrayList<>();
    
    // Lista para Clientes (Usuarios con rol 'CLIENTE')
    private List<Cliente> listaClientes;
    
    // Listas para productos
    private List<Productos> listaProductosDisponibles;
    private List<ProductoMovimiento> productosSeleccionados;
    
    // Campos temporales para agregar productos
    private Integer productoSeleccionadoId;
    private int cantidadProducto;
    
    // Campo para fecha de vencimiento de deuda
    private java.util.Date fechaVencimientoDeuda;
    
    // Campos para COMPRA
    private List<Proveedor> listaProveedores;
    private Integer proveedorSeleccionadoId; // ID del proveedor seleccionado
    private Integer productoCompraId; // ID del producto comprado
    private int cantidadCompra; // Cantidad comprada 

    // Constructor vacío (puedes dejarlo vacío si usas @PostConstruct)
    public MovimientoBean() {
    }

    // Usamos @PostConstruct para asegurar que la lista se cargue al crear el Bean
    @PostConstruct
    public void init() {
        cargarClientes();
        cargarProductos();
        cargarProveedores();
        productosSeleccionados = new ArrayList<>();
        listar(); // También puedes iniciar la lista de movimientos aquí
    }
    
    public void cargarProveedores() {
        this.listaProveedores = proveedorDAO.listarP();
    }
    
    public String nuevo() {
        movimiento = new Movimiento();
        if (productosSeleccionados == null) {
            productosSeleccionados = new ArrayList<>();
        } else {
            productosSeleccionados.clear();
        }
        productoSeleccionadoId = null;
        cantidadProducto = 0;
        fechaVencimientoDeuda = null;
        return "nuevo?faces-redirect=true";
    }

    // --- Lógica para Clientes ---

    public void cargarClientes() {
        this.listaClientes = cDAO.listarClientes();
    }
    
    // --- Lógica para Productos ---
    
    public void cargarProductos() {
        this.listaProductosDisponibles = productosDAO.listar();
    }
    
    public void agregarProducto() {
        // Asegurar que la lista esté inicializada
        if (productosSeleccionados == null) {
            productosSeleccionados = new ArrayList<>();
        }
        
        // Validar que se haya seleccionado un producto y una cantidad
        if (productoSeleccionadoId == null || productoSeleccionadoId <= 0) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", 
                    "Debe seleccionar un producto."));
            return;
        }
        
        if (cantidadProducto <= 0) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", 
                    "La cantidad debe ser mayor a 0."));
            return;
        }
        
        Productos producto = productosDAO.buscar(productoSeleccionadoId);
        if (producto != null) {
            ProductoMovimiento pm = new ProductoMovimiento(producto, cantidadProducto);
            productosSeleccionados.add(pm);
            
            // Recalcular total
            int total = calcularTotal();
            
            // Limpiar campos después de agregar
            productoSeleccionadoId = 0;
            cantidadProducto = 0;
            
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", 
                    "Producto agregado: " + producto.getNombreProducto() + " - Total: $" + total));
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", 
                    "No se encontró el producto seleccionado."));
        }
    }
    
    public void eliminarProducto(ProductoMovimiento pm) {
        if (productosSeleccionados != null && productosSeleccionados.contains(pm)) {
            productosSeleccionados.remove(pm);
            calcularTotal();
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", 
                    "Producto eliminado de la lista."));
        }
    }
    
    public int calcularTotal() {
        int total = 0;
        if (productosSeleccionados != null && !productosSeleccionados.isEmpty()) {
            for (ProductoMovimiento pm : productosSeleccionados) {
                pm.recalcularSubtotal(); // Asegurar que el subtotal esté actualizado
                total += pm.getSubtotal();
            }
        }
        movimiento.setValor_movimiento(total);
        return total;
    }
    
    public int calcularTotalConDescuento() {
        int total = calcularTotal();
        int descuento = movimiento.getDescuento();
        if (descuento > 0 && descuento <= 100) {
            int descuentoAplicado = (total * descuento) / 100;
            return total - descuentoAplicado;
        }
        return total;
    }
    
    // Getter para la lista de clientes (Usado en nuevo.xhtml)
    public List<Cliente> getListaClientes() {
        if (listaClientes == null) {
            cargarClientes();
        }
        return listaClientes;
    }

    public void setListaClientes(List<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }

    // --- Getters y Setters de Movimiento (Tus métodos originales) ---

    public Movimiento getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(Movimiento movimiento) {
        this.movimiento = movimiento;
    }

    public List<Movimiento> getListaM() {
        return listaM;
    }

    public void setListaM(List<Movimiento> listaM) {
        this.listaM = listaM;
    }
    
    // --- Lógica de Seguridad Auxiliar (Tu método original) ---
    
    private String obtenerRolSesion() {
        return (String) FacesContext.getCurrentInstance()
                                     .getExternalContext()
                                     .getSessionMap().get("tipo_usuario");
    }

    // --- Métodos de Acceso (Tus métodos originales) ---

    public void listar() {
        // Reiniciar el objeto movimiento para formularios nuevos, si es necesario
        movimiento = new Movimiento(); 
        listaM = mDAO.listar();
    }

    public String guardar() {
        try {
            // Si es VENTA o DEUDA, calcular el total de productos
            if ("VENTA".equals(movimiento.getTipo_movimiento()) || "DEUDA".equals(movimiento.getTipo_movimiento())) {
                if (productosSeleccionados == null || productosSeleccionados.isEmpty()) {
                    FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", 
                            "Debe agregar al menos un producto para venta o deuda."));
                    return null;
                }
                // Calcular total y aplicar descuento
                int total = calcularTotal();
                int totalConDescuento = calcularTotalConDescuento();
                movimiento.setValor_movimiento(totalConDescuento);
            }
            
            // Si es tipo VENTA, validar stock ANTES de guardar el movimiento
            if ("VENTA".equals(movimiento.getTipo_movimiento())) {
                // Validar stock de todos los productos antes de proceder
                for (ProductoMovimiento pm : productosSeleccionados) {
                    int idProducto = pm.getProducto().getIdProducto();
                    int cantidadVendida = pm.getCantidad();
                    
                    // Buscar el inventario del producto
                    Inventario inventario = inventarioDAO.buscar(idProducto);
                    
                    if (inventario == null) {
                        FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", 
                                "El producto " + pm.getProducto().getNombreProducto() + 
                                " no existe en inventario."));
                        return null;
                    }
                    
                    // Validar que haya suficiente stock
                    int cantidadDisponible = inventario.getCantidad();
                    if (cantidadDisponible < cantidadVendida) {
                        FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", 
                                "Stock insuficiente para " + pm.getProducto().getNombreProducto() + 
                                ". Disponible: " + cantidadDisponible + ", Solicitado: " + cantidadVendida));
                        return null;
                    }
                }
            }
            
            // Guardar el movimiento y obtener el ID generado
            int idMovimientoGenerado = mDAO.guardar(movimiento);
            
            // Si es tipo VENTA, descontar productos del inventario
            if ("VENTA".equals(movimiento.getTipo_movimiento()) && idMovimientoGenerado > 0) {
                // Descontar productos del inventario (ya validamos el stock anteriormente)
                for (ProductoMovimiento pm : productosSeleccionados) {
                    int idProducto = pm.getProducto().getIdProducto();
                    int cantidadVendida = pm.getCantidad();
                    
                    System.out.println("DEBUG: Descontando producto ID: " + idProducto + ", Cantidad: " + cantidadVendida);
                    
                    // Buscar el inventario del producto
                    Inventario inventario = inventarioDAO.buscar(idProducto);
                    
                    if (inventario == null) {
                        System.err.println("ERROR: No se encontró inventario para producto ID: " + idProducto);
                        FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", 
                                "No se encontró inventario para el producto " + pm.getProducto().getNombreProducto()));
                        continue;
                    }
                    
                    // Descontar la cantidad vendida del inventario
                    int cantidadDisponible = inventario.getCantidad();
                    System.out.println("DEBUG: Cantidad disponible antes: " + cantidadDisponible);
                    
                    int nuevaCantidad = cantidadDisponible - cantidadVendida;
                    inventario.setCantidad(nuevaCantidad);
                    
                    System.out.println("DEBUG: Nueva cantidad después del descuento: " + nuevaCantidad);
                    System.out.println("DEBUG: Actualizando inventario para producto ID: " + idProducto);
                    
                    inventarioDAO.actualizar(inventario);
                    
                    System.out.println("DEBUG: Inventario actualizado para producto ID: " + idProducto);
                }
                
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", 
                        "Venta registrada y productos descontados del inventario correctamente."));
            }
            
            // Si es tipo COMPRA, sumar cantidad al inventario
            if ("COMPRA".equals(movimiento.getTipo_movimiento()) && idMovimientoGenerado > 0) {
                // Validar que se haya seleccionado un producto y una cantidad
                if (productoCompraId == null || productoCompraId <= 0) {
                    FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", 
                            "Debe seleccionar un producto para la compra."));
                    return null;
                }
                
                if (cantidadCompra <= 0) {
                    FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", 
                            "La cantidad debe ser mayor a 0."));
                    return null;
                }
                
                // Buscar el inventario del producto
                Inventario inventario = inventarioDAO.buscar(productoCompraId);
                
                if (inventario == null) {
                    // Si el producto no existe en inventario, crear un nuevo registro
                    inventario = new Inventario();
                    Productos producto = productosDAO.buscar(productoCompraId);
                    if (producto == null) {
                        FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", 
                                "No se encontró el producto seleccionado."));
                        return null;
                    }
                    inventario.setId_item(producto);
                    inventario.setCantidad(cantidadCompra);
                    inventario.setUbicacion("Almacén Principal");
                    inventario.setEstado("DISPONIBLE");
                    inventarioDAO.guardar(inventario);
                    
                    FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", 
                            "Compra registrada. Se creó un nuevo registro en inventario con " + 
                            cantidadCompra + " unidades."));
                } else {
                    // Si existe, sumar la cantidad
                    int cantidadExistente = inventario.getCantidad();
                    int cantidadTotal = cantidadExistente + cantidadCompra;
                    inventario.setCantidad(cantidadTotal);
                    inventarioDAO.actualizar(inventario);
                    
                    FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", 
                            "Compra registrada. Se agregaron " + cantidadCompra + 
                            " unidades al inventario. Cantidad total: " + cantidadTotal));
                }
            }
            
            // Si es tipo DEUDA, registrar también en la tabla DEUDA
            if ("DEUDA".equals(movimiento.getTipo_movimiento()) && idMovimientoGenerado > 0) {
                Deuda deuda = new Deuda();
                deuda.setSaldo(calcularTotalConDescuento());
                // Usar la fecha del movimiento como fecha de vencimiento por defecto
                if (movimiento.getFecha_movimiento() != null && !movimiento.getFecha_movimiento().isEmpty()) {
                    try {
                        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
                        java.util.Date fechaUtil = sdf.parse(movimiento.getFecha_movimiento());
                        deuda.setFechaVencimiento(new Date(fechaUtil.getTime()));
                    } catch (Exception e) {
                        // Si hay error, usar fecha actual + 30 días
                        java.util.Calendar cal = java.util.Calendar.getInstance();
                        cal.add(java.util.Calendar.DAY_OF_MONTH, 30);
                        deuda.setFechaVencimiento(new Date(cal.getTimeInMillis()));
                    }
                } else {
                    // Si no hay fecha, usar fecha actual + 30 días
                    java.util.Calendar cal = java.util.Calendar.getInstance();
                    cal.add(java.util.Calendar.DAY_OF_MONTH, 30);
                    deuda.setFechaVencimiento(new Date(cal.getTimeInMillis()));
                }
                deuda.setMovimiento(idMovimientoGenerado);
                // Si hay productos, usar el primero como producto pendiente
                if (!productosSeleccionados.isEmpty()) {
                    deuda.setProductoPendiente(productosSeleccionados.get(0).getProducto().getIdProducto());
                } else {
                    deuda.setProductoPendiente(0);
                }
                
                deudaDAO.guardar(deuda);
                
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", 
                        "Movimiento y deuda registrados correctamente."));
            }
            
            // Reiniciar el objeto y recargar la lista
            nuevo();
            listar();
            return "index?faces-redirect=true";
            
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", 
                    "Error al guardar el movimiento: " + e.getMessage()));
            System.err.println("Error al guardar movimiento: " + e.getMessage());
            return null;
        }
    }

    // --- Métodos Restringidos (Solo ADMIN) (Tus métodos originales) ---

    public String buscar(int id_movimiento) {
        if ("ADMINISTRADOR".equals(obtenerRolSesion())) {
            movimiento = mDAO.buscar(id_movimiento);
            return "editar?faces-redirect=true";
        }
        return "index?faces-redirect=true";
    }

    public String actualizar() {
        if ("ADMINISTRADOR".equals(obtenerRolSesion())) {
            mDAO.actualizar(movimiento);
            // Reiniciar y recargar la lista
            movimiento = new Movimiento();
            listar();
        }
        return "index?faces-redirect=true";
    }

    public void eliminar(int id_movimiento) {
        if ("ADMINISTRADOR".equals(obtenerRolSesion())) {
            mDAO.eliminar(id_movimiento);
            listar(); // Recargar la lista después de eliminar
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", 
                    "No tiene permisos para eliminar movimientos. Solo los administradores pueden realizar esta acción."));
        }
    }
    
    /**
     * Verifica si el usuario tiene permisos para editar movimientos.
     * Redirige a index si no es ADMINISTRADOR.
     */
    public void verificarPermisosEdicion() {
        String tipoUsuario = obtenerRolSesion();
        
        if (!"ADMINISTRADOR".equals(tipoUsuario)) {
            FacesContext context = FacesContext.getCurrentInstance();
            try {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Acceso Denegado", 
                    "No tiene permisos para editar movimientos. Solo los administradores pueden realizar esta acción."));
                context.getExternalContext().redirect(context.getExternalContext().getRequestContextPath() + "/faces/movimientos/index.xhtml");
            } catch (Exception e) {
                System.err.println("Error al redirigir: " + e.getMessage());
            }
        }
    }
    
    // --- Getters y Setters adicionales ---
    
    public List<Productos> getListaProductosDisponibles() {
        if (listaProductosDisponibles == null) {
            cargarProductos();
        }
        return listaProductosDisponibles;
    }
    
    public List<ProductoMovimiento> getProductosSeleccionados() {
        if (productosSeleccionados == null) {
            productosSeleccionados = new ArrayList<>();
        }
        return productosSeleccionados;
    }
    
    public Integer getProductoSeleccionadoId() {
        return productoSeleccionadoId;
    }
    
    public void setProductoSeleccionadoId(Integer productoSeleccionadoId) {
        this.productoSeleccionadoId = productoSeleccionadoId;
    }
    
    public int getCantidadProducto() {
        return cantidadProducto;
    }
    
    public void setCantidadProducto(int cantidadProducto) {
        this.cantidadProducto = cantidadProducto;
    }
    
    public java.util.Date getFechaVencimientoDeuda() {
        return fechaVencimientoDeuda;
    }
    
    public void setFechaVencimientoDeuda(java.util.Date fechaVencimientoDeuda) {
        this.fechaVencimientoDeuda = fechaVencimientoDeuda;
    }
    
    // Getters y Setters para COMPRA
    public List<Proveedor> getListaProveedores() {
        if (listaProveedores == null) {
            cargarProveedores();
        }
        return listaProveedores;
    }
    
    public void setListaProveedores(List<Proveedor> listaProveedores) {
        this.listaProveedores = listaProveedores;
    }
    
    public Integer getProveedorSeleccionadoId() {
        return proveedorSeleccionadoId;
    }
    
    public void setProveedorSeleccionadoId(Integer proveedorSeleccionadoId) {
        this.proveedorSeleccionadoId = proveedorSeleccionadoId;
    }
    
    public Integer getProductoCompraId() {
        return productoCompraId;
    }
    
    public void setProductoCompraId(Integer productoCompraId) {
        this.productoCompraId = productoCompraId;
    }
    
    public int getCantidadCompra() {
        return cantidadCompra;
    }
    
    public void setCantidadCompra(int cantidadCompra) {
        this.cantidadCompra = cantidadCompra;
    }
}