package beans;

import dao.UsuarioDAO;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import modelo.Usuario;

@ManagedBean
@ApplicationScoped
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
    
    public void buscar(int id){
        usuario = uDAO.buscar(id);
    }
    /*
    public void actualizar(){
        if(usuario.getClave().equals("")){
            usuario.setClav
    e(usuario.getClave());
        }else{
            usuario.setClave(Utils.encriptar(usuario.getClave()));
        }        
        uDAO.actualizar(usuario);
    }
    
    public void eliminar(int id){
        uDAO.eliminar(id);
    }
*/
}
