package beans;

import dao.UsuarioDAO;
import java.io.IOException; 
import java.io.Serializable;
import java.util.ArrayList;

import java.util.List;

// IMPORTACIONES NECESARIAS PARA EL MENSAJE DE ÉXITO

import javax.faces.application.FacesMessage; 

import javax.faces.context.FacesContext;

import javax.faces.bean.ManagedBean;

import javax.faces.bean.SessionScoped;

import modelo.Usuario;



@ManagedBean

@SessionScoped

public class UsuarioBean implements Serializable {

    

    Usuario usuario = new Usuario();

    List<Usuario> listaU = new ArrayList<>();

    UsuarioDAO uDAO = new UsuarioDAO();



    // --- Getters y Setters ---



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

    

    // ----------------------------------------------------------

    // CARGAR MI PROPIO PERFIL (Usa la redirección robusta)

    // ----------------------------------------------------------

    public void cargarMiPerfil() {

        FacesContext context = FacesContext.getCurrentInstance();

        String rootPath = context.getExternalContext().getRequestContextPath();

        

        Long cedula = (Long) context.getExternalContext().getSessionMap().get("cedula_usuario");

        

        try {

            if (cedula != null && cedula > 0) {

                usuario = uDAO.buscar(cedula);

                

                usuario.setClaveNueva(null); 

                

                context.getExternalContext().redirect(rootPath + "/faces/usuarios/editarCajero.xhtml");

                context.responseComplete();

                

            } else {

                context.getExternalContext().redirect(rootPath + "/faces/admin/index.xhtml");

                context.responseComplete();

            }

        } catch (IOException e) {

            System.err.println("Error de redirección en cargarMiPerfil: " + e.getMessage());

        }

    }

    

    // ----------------------------------------------------------

    // CRUD existente

    // ----------------------------------------------------------

    

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

    

    /**

     * Actualiza los datos del usuario, maneja la clave, actualiza la sesión 

     * y genera un mensaje de confirmación.

     */

    public String actualizar(){

        

        FacesContext context = FacesContext.getCurrentInstance();

        boolean claveCambiada = false; // Flag para el mensaje



        // 1. CLAVE: Lógica para manejar la nueva clave (si se ingresó) o mantener la antigua

        if (usuario.getClaveNueva() != null && !usuario.getClaveNueva().isEmpty()) {

            usuario.setClave(Utils.encriptar(usuario.getClaveNueva()));

            claveCambiada = true; // La clave fue modificada

        } else {

            // Si no se cambió la clave, recuperamos la clave encriptada actual de la DB

            Usuario tempUsuario = uDAO.buscar(usuario.getCedula());

            if (tempUsuario != null) {

                usuario.setClave(tempUsuario.getClave());

            }

        }

        

        // 2. Llamar al DAO para actualizar la BD

        uDAO.actualizar(usuario);

        

        // 3. ACTUALIZAR LA SESIÓN (LÍNEA CLAVE PARA EL REFRESCO DEL NOMBRE)

        context.getExternalContext().getSessionMap().put("nombre_usuario", usuario.getNombre());

        

        // 4. GENERAR MENSAJE DE CONFIRMACIÓN

        String detalle = "Tus datos generales han sido actualizados. ";

        if (claveCambiada) {

            detalle += "La clave ha sido modificada con éxito.";

        }       
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualización Exitosa", detalle);

        context.addMessage(null, message);     
        // 5. Determinar la redirección y forzar el refresco de la vista

        String tipo = (String) context.getExternalContext().getSessionMap().get("tipo_usuario");

        

        if ("CAJERO".equals(tipo) || "CLIENTE".equals(tipo)) {

             return "/faces/admin/index.xhtml?faces-redirect=true";
        }
        return "index?faces-redirect=true"; // Para Administrador
    }
    public void eliminar(long Cedula){
        uDAO.eliminar(Cedula);
    }
}

