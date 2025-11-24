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
    
    public List<Usuario> listar(){
        usuario = new Usuario();
        return listaU = uDAO.listarU();
    }
    
    public void guardar(){
        usuario.setClave(Utils.encriptar(usuario.getClave()));
        uDAO.guardar(usuario);
    }
    
    public void buscar(int Cedula){
        usuario = uDAO.buscar(Cedula);
    }
    
    public void actualizar(){
        if(usuario.getClave().equals("")){
            usuario.setClave(usuario.getClave());
        }else{
            usuario.setClave(Utils.encriptar(usuario.getClave()));
        }        
        uDAO.actualizar(usuario);
    }
    
    public void eliminar(int Cedula){
        uDAO.eliminar(Cedula);
    }
}
