package beans;

import dao.ProveedorDAO;
import java.io.Serializable; // Implementar Serializable es necesario para ViewScoped/SessionScoped
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct; 
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped; // Ámbito recomendado para páginas con tablas
import modelo.Proveedor;

@ManagedBean
@ViewScoped // Cambio a ViewScoped para mejor rendimiento y actualización de la vista
public class ProveedorBean implements Serializable {
    
    private Proveedor proveedor = new Proveedor();
    private List<Proveedor> listaP = new ArrayList<>();
    private ProveedorDAO pDAO = new ProveedorDAO();

    /**
     * Inicializa el bean y carga la lista de proveedores al cargar la vista.
     */
    @PostConstruct
    public void init() {
        listarP();  
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public List<Proveedor> getListaP() {         
        return listaP;
    }

    public void setListaP(List<Proveedor> listaP) {
        this.listaP = listaP;
    }
    
    public void listarP(){
        this.listaP = pDAO.listarP();
    }
    
    public void guardar(){
        pDAO.guardar(proveedor);
        // Reiniciar el objeto después de guardar y refrescar la lista
        proveedor = new Proveedor(); 
        listarP();  
    }
    
    public String buscar(int id_proveedor){
        proveedor = pDAO.buscar(id_proveedor);
        return "editar?faces-redirect=true";
    }
    
    public String actualizar(){
        pDAO.actualizar(proveedor);
        return "index?faces-redirect=true";
    }
    
    public void eliminar(int id_proveedor){
        pDAO.eliminar(id_proveedor);
        listarP();  // Recargar la lista después de eliminar
    }
}