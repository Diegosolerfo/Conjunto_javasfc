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

@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Usuario usuario = new Usuario();
    private String nombreUsuario = "";
    private String tipo_usuario;

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

    public void autenticar() {
        try {
            Connection con = ConnBD.conectar();

            String sql = "SELECT * FROM usuarios WHERE cedula = ? AND clave = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, usuario.getCedula());
            ps.setString(2, usuario.getClave());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                tipo_usuario = rs.getString("tipo_usuario");
                String nombre = rs.getString("nombre");

                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", nombre);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("tipo_usuario", tipo_usuario);

                nombreUsuario = nombre;

                String dir = "/faces/panel/index.xhtml";

                String rootPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
                FacesContext.getCurrentInstance().getExternalContext().redirect(rootPath + dir);
                FacesContext.getCurrentInstance().responseComplete();
            } else {
                FacesContext.getCurrentInstance().getExternalContext().redirect("error.xhtml");
            }
        } catch (SQLException | IOException e) {
        }
    }

    public boolean isAdministrador() {
        String tipo = (String) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("tipo_usuario");
        return "ADMINISTRADOR".equalsIgnoreCase(tipo != null ? tipo : "");
    }

    public boolean isCliente() {
        String tipo = (String) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("tipo_usuario");
        return "CLIENTE".equalsIgnoreCase(tipo != null ? tipo : "");
    }

    public boolean isCajero() {
        String tipo = (String) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("tipo_usuario");
        return "CAJERO".equalsIgnoreCase(tipo != null ? tipo : "");
    }

    public void cerrar_sesion() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
        String rootPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(rootPath + "/faces/index.xhtml");
        } catch (IOException e) {
        }
    }

    public void verif_sesion(String t) {
        String nom = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        String tipo = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tipo_usuario");

        if (nom == null) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("sinacceso.xhtml");
            } catch (IOException e) {
            }
            return;
        }

        if (tipo == null || !tipo.equalsIgnoreCase(t)) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("sinacceso.xhtml");
            } catch (IOException e) {
            }
            return;
        }

        nombreUsuario = nom;
    }

}
