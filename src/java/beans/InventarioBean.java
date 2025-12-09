package beans;

import dao.InventarioDAO;
import dao.ProductosDAO;
import modelo.Inventario;
import modelo.Productos;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class InventarioBean {

    private Inventario inventario = new Inventario();
    private List<Inventario> listaInventario = new ArrayList<>();
    private List<Productos> listaProductosDisponibles = new ArrayList<>();
    
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
    
    public void guardar() {
        inventarioDAO.guardar(inventario);
        // Reiniciar y recargar
        this.inventario = new Inventario(); 
        listar();
    }
    
    public String buscar(int idProducto) {
        this.inventario = inventarioDAO.buscar(idProducto);
        // Si el objeto Inventario usa la clase Productos, no es necesario hacer un setter manual,
        // ya que el DAO lo devuelve lleno.
        return "editar?faces-redirect=true";
    }
    
    public String actualizar() {
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
}