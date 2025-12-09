package beans;

import dao.CompraDAO;
import dao.ProveedorDAO; 
import dao.ProductosDAO; 
import dao.MovimientoDAO;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct; 
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import modelo.Compra;
import modelo.Proveedor; 
import modelo.Productos; 
import modelo.Movimiento;

@ManagedBean
@ViewScoped
public class CompraBean {

    // 1. PROPIEDADES (Datos)
    private Compra compra = new Compra();

    // Instanciación directa.
    CompraDAO compraDAO = new CompraDAO();

    // Lista principal para la tabla del index
    List<Compra> listaCompras = new ArrayList<>();

    // Listas para los dropdowns (Proveedor, Producto, Movimiento)
    private List<Proveedor> listaProveedores = new ArrayList<>(); 
    private List<Productos> listaProductos = new ArrayList<>();
    private List<Movimiento> listaMovimientos = new ArrayList<>(); 


    // 2. INICIALIZACIÓN
    @PostConstruct
    public void init() {
        // Carga la tabla principal
        listar(); 
        // Carga las listas para los dropdowns
        cargarListasDropdowns();
    }

    private void cargarListasDropdowns() {
        ProveedorDAO provDAO = new ProveedorDAO();
        this.listaProveedores = provDAO.listarP(); 

        MovimientoDAO movsDAO = new MovimientoDAO();
        this.listaMovimientos = movsDAO.listar(); 

        ProductosDAO prodDAO = new ProductosDAO();
        this.listaProductos = prodDAO.listar();
    }

    // 3. GETTERS Y SETTERS

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }
    
    // --- CORRECCIÓN CLAVE ---
    // El código original tenía un error de símbolo aquí.
    // Si el getter auxiliar es necesario, debe usar el nombre correcto:
    public int getIdCompra() { // Renombrado a getIdCompra()
        // Asumiendo que la propiedad real en Compra es 'idCompra'
        return compra.getIdCompra(); // Llamada corregida
    }
    

    public List<Compra> getListaCompras() {
        return listaCompras;
    }

    public void setListaCompras(List<Compra> listaCompras) {
        this.listaCompras = listaCompras;
    }

    public List<Proveedor> getListaProveedores() { 
        return listaProveedores;
    }

    public List<Movimiento> getListaMovimientos() { 
        return listaMovimientos;
    }

    public List<Productos> getListaProductos() { 
        return listaProductos;
    }

    // 4. MÉTODOS DE ACCIÓN Y LÓGICA DE NEGOCIO (CRUD)
    
    public void listar() {
        this.compra = new Compra(); 
        listaCompras = compraDAO.listar();
    }

    public void guardar() {
        compraDAO.guardar(compra);
        this.compra = new Compra(); 
    }

    public String buscar(int idCompra) {
        compra = compraDAO.buscar(idCompra);
        cargarListasDropdowns(); 
        return "editar?faces-redirect=true";
    }

    public String actualizar() {
        compraDAO.actualizar(compra);
        return "index?faces-redirect=true"; 
    }

    public void eliminar(int idCompra) {
        compraDAO.eliminar(idCompra);
        listar(); 
    }
}