package beans;

import dao.ConnBD;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import modelo.Usuario;
import beans.Utils;

@ManagedBean
public class LoginBean {
    Usuario usuario = new Usuario();
    String nombreUsuario = "";

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public void autenticar(){        
        try {
            Connection con = ConnBD.conectar();
                        
            String sql = "SELECT * FROM usuario WHERE cedula = ? AND clave = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, usuario.getCedula());
            ps.setString(2, (usuario.getClave()));
            
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", rs.getString("nombre"));
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("tipo_usuario", rs.getString("tipo_usuario"));
                
                String dir = "";
                switch(rs.getString("tipo_usuario")){
                    case "ADMINISTRADOR":
                        dir = "/faces/admin";
                        break;
                    case "CAJERO":
                        dir = "/faces/caje";
                        break;
                    case "CLIENTE":
                        dir = "/faces/clie";
                        break;
                }
                
                String rootPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
                FacesContext.getCurrentInstance().getExternalContext().redirect(rootPath + dir + "/index.xhtml");
                FacesContext.getCurrentInstance().responseComplete();
            }else{
                FacesContext.getCurrentInstance().getExternalContext().redirect("error.xhtml");
            }            
        } catch (SQLException | IOException e) {
            System.out.println("Login error: " + e.getMessage());
        }        
    }
    
    public void cerrar_sesion(){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
        String rootPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(rootPath + "/faces/index.xhtml");
        } catch (IOException e) {
        }
    }
    
    public void verif_sesion(String t){
        String nom = (String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        String tipo = (String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tipo_usuario");
        
        if(nom == null){
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("sinacceso.xhtml");
            } catch (IOException e) {
            }
        }else{
            if(!tipo.equals(t)){
                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("sinacceso.xhtml");
                } catch (IOException e) {
                }
            }else{
                nombreUsuario = nom;
            }
        }
    }   
    
}
