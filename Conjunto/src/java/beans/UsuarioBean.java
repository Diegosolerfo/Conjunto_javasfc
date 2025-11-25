package beans;

import dao.UsuarioDAO;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import modelo.Usuario;

@ManagedBean
@RequestScoped
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
    
    public void buscar(long Cedula){
        usuario = uDAO.buscar(Cedula);
    }
    
    public void actualizar(){
        // if a new password was entered, encrypt it and set as the stored clave
        if(usuario.getClaveNueva() != null && !usuario.getClaveNueva().trim().isEmpty()){
            usuario.setClave(Utils.encriptar(usuario.getClaveNueva()));
        }
        // if claveNueva is empty/null, we keep usuario.clave as loaded from DB (hashed)
        
        uDAO.actualizar(usuario);
    }
    
    public void eliminar(long Cedula){
        uDAO.eliminar(Cedula);
    }
}
