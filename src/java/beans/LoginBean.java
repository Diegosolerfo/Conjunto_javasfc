package beans;

import dao.ConnBD;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import modelo.Usuario;

@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {

    Usuario usuario = new Usuario();
    String nombreUsuario = "";
    
    // --- Getters y Setters ---
    public Usuario getUsuario() {return usuario;}
    public void setUsuario(Usuario usuario) {this.usuario = usuario;}
    public String getNombreUsuario() {return nombreUsuario;}
    public void setNombreUsuario(String nombreUsuario) {this.nombreUsuario = nombreUsuario;}
    
    // ------------------------------------------------------------------
    // CLAVE: Método para obtener el rol desde la sesión (usado en XHTML)
    // ------------------------------------------------------------------
    public String getTipoUsuarioActual() {
        return (String) FacesContext.getCurrentInstance()
                                    .getExternalContext()
                                    .getSessionMap().get("tipo_usuario");
    }

    // ------------------------------------------------------------------
    // AUTENTICACIÓN (USA CLAVE EN TEXTO PLANO)
    // ------------------------------------------------------------------
    public void autenticar(){
        try {
            Connection con = ConnBD.conectar();
            String sql = "SELECT * FROM USUARIO WHERE cedula = ? AND clave = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setLong(1, usuario.getCedula());
            // **IMPORTANTE:** Aquí se usa la clave en texto plano.
            ps.setString(2, (usuario.getClave()));
            
            ResultSet rs = ps.executeQuery();
            if(rs.next()){

                // Guardar datos clave en la sesión
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", rs.getString("nombre"));
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("tipo_usuario", rs.getString("TIPO_USUARIO"));
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("cedula_usuario", rs.getLong("CEDULA"));

                // Redirección
                String dir = "/faces/admin"; 
                if ("CLIENTE".equals(rs.getString("TIPO_USUARIO"))) {
                    dir = "/faces/clie";
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
    
    // ------------------------------------------------------------------
    // CERRAR SESIÓN (AÑADIDO)
    // ------------------------------------------------------------------
    public void cerrar_sesion(){
        // Limpiar la sesión
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
        
        // Redirigir a la página de inicio
        String rootPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(rootPath + "/faces/index.xhtml");
        } catch (IOException e) {
             System.err.println("Error al redirigir después de cerrar sesión: " + e.getMessage());
        }
    }
    
    // ------------------------------------------------------------------
    // VERIFICACIÓN DE SESIÓN 
    // ------------------------------------------------------------------
    public void verif_sesion(String t){
        String nom = (String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        String tipo = (String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tipo_usuario");
        
        if(nom == null){
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/faces/sinacceso.xhtml");
            } catch (IOException e) {}
        }else{
            if ("ADMINISTRADOR".equals(t) && !"ADMINISTRADOR".equals(tipo)) {
                 try {
                     FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/faces/sinacceso.xhtml");
                 } catch (IOException e) {}
            }
            nombreUsuario = nom;
        }
    }
}