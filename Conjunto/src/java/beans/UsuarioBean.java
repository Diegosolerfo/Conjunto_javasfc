package beans;

import dao.UsuarioDAO;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import modelo.Usuario;

@ManagedBean
@SessionScoped
public class UsuarioBean {
    Usuario usuario = new Usuario();
    List<Usuario> listaU = new ArrayList<>();
    UsuarioDAO uDAO = new UsuarioDAO();

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Usuario> getListaU() {        
        return listaU;
    }

    public void setListaU(List<Usuario> listaU) {
        this.listaU = listaU;
    }
    
    public void listar(){
        usuario = new Usuario();
        listaU = uDAO.listarU();
    }
    
    public void guardar(){
        usuario.setClave(Utils.encriptar(usuario.getClave()));
        uDAO.guardar(usuario);
    }
    
    public String buscar(long Cedula){
        usuario = uDAO.buscar(Cedula);
        usuario.setClaveNueva(null);
        return "editar?faces-redirect=true";
    }
    
    public String actualizar(){
        uDAO.actualizar(usuario);
        return "index?faces-redirect=true";
    }
    
    public void eliminar(long Cedula){
        uDAO.eliminar(Cedula);
    }
}
