package beans;

import dao.ProductosDAO;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import modelo.Productos;

@ManagedBean
@SessionScoped
public class ProductosBean {

    private Productos productos = new Productos();
    private List<Productos> listaProductos = new ArrayList<>();
    private final ProductosDAO productosDAO = new ProductosDAO();

    public Productos getProductos() {
        return productos;
    }

    public void setProductos(Productos productos) {
        this.productos = productos;
    }

    public List<Productos> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<Productos> listaProductos) {
        this.listaProductos = listaProductos;
    }

    // LISTAR
    public void listar() {
        productos = new Productos();
        listaProductos = productosDAO.listar();
    }

    // GUARDAR
     public String guardar() {
     productosDAO.guardar(productos);
        // Limpiamos el objeto productos para evitar que los datos persistan
        productos = new Productos();
        // Redirigimos a la página principal de listado de productos
     return "index?faces-redirect=true";
    }

  public String buscar(int id) {
      productos = productosDAO.buscar(id);
 
      return "editar?faces-redirect=true"; 
    }

    // ACTUALIZAR
    public String actualizar() {
    // 1. Llama al DAO para guardar los cambios en la base de datos
    productosDAO.actualizar(productos);
    
    // 2. Devuelve la cadena que redirige a la vista "index.xhtml"
    return "index?faces-redirect=true";
}   

    // ELIMINAR
    public void eliminar(int id) {
        productosDAO.eliminar(id);
    }
}
