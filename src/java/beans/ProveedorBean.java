package beans;

import dao.ProveedorDAO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import modelo.Proveedor;

@ManagedBean
@ApplicationScoped  
public class ProveedorBean implements Serializable {
    
    private Proveedor proveedor = new Proveedor();
    private List<Proveedor> listaP = new ArrayList<>();
    private ProveedorDAO pDAO = new ProveedorDAO();

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
        proveedor = new Proveedor();
        listaP = pDAO.listarP();
    }
    
    public void guardar(){
        pDAO.guardar(proveedor);
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
        listarP();
    }
}