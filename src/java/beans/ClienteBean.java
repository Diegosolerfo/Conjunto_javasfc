package beans;

import dao.MovimientoDAO;
import dao.DeudaDAO;
import dao.PagoDAO;
import dao.UsuarioDAO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import modelo.Movimiento;
import modelo.Deuda;
import modelo.Pago;
import modelo.Usuario;
import beans.Utils;

@ManagedBean
@SessionScoped
public class ClienteBean implements Serializable {
    
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private MovimientoDAO movimientoDAO = new MovimientoDAO();
    private DeudaDAO deudaDAO = new DeudaDAO();
    private PagoDAO pagoDAO = new PagoDAO();
    
    private Usuario miPerfil;
    private List<Movimiento> misMovimientos;
    private List<Deuda> misDeudas;
    private List<Pago> misPagos;
    
    @PostConstruct
    public void init() {
        cargarMiPerfil();
        cargarMisMovimientos();
        cargarMisDeudas();
        cargarMisPagos();
    }
    
    /**
     * Obtiene el ID del cliente de la sesión.
     */
    private Long obtenerIdClienteSesion() {
        return (Long) FacesContext.getCurrentInstance()
                                 .getExternalContext()
                                 .getSessionMap().get("cedula_usuario");
    }
    
    /**
     * Carga el perfil del cliente actual.
     */
    public void cargarMiPerfil() {
        Long cedula = obtenerIdClienteSesion();
        if (cedula != null && cedula > 0) {
            miPerfil = usuarioDAO.buscar(cedula);
            if (miPerfil != null) {
                miPerfil.setClaveNueva(null); // Limpiar campo de nueva clave
            }
        }
    }
    
    /**
     * Carga los movimientos del cliente actual.
     */
    public void cargarMisMovimientos() {
        Long idCliente = obtenerIdClienteSesion();
        if (idCliente != null && idCliente > 0) {
            misMovimientos = movimientoDAO.listarPorCliente(idCliente);
        } else {
            misMovimientos = new ArrayList<>();
        }
    }
    
    /**
     * Carga las deudas del cliente actual.
     */
    public void cargarMisDeudas() {
        Long idCliente = obtenerIdClienteSesion();
        if (idCliente != null && idCliente > 0) {
            misDeudas = deudaDAO.listarPorCliente(idCliente);
        } else {
            misDeudas = new ArrayList<>();
        }
    }
    
    /**
     * Carga los pagos del cliente actual.
     */
    public void cargarMisPagos() {
        Long idCliente = obtenerIdClienteSesion();
        if (idCliente != null && idCliente > 0) {
            misPagos = pagoDAO.listarPorCliente(idCliente);
        } else {
            misPagos = new ArrayList<>();
        }
    }
    
    /**
     * Actualiza el perfil del cliente.
     */
    public String actualizarPerfil() {
        FacesContext context = FacesContext.getCurrentInstance();
        boolean claveCambiada = false;
        
        // Manejar la nueva clave si se ingresó
        if (miPerfil.getClaveNueva() != null && !miPerfil.getClaveNueva().isEmpty()) {
            miPerfil.setClave(Utils.encriptar(miPerfil.getClaveNueva()));
            claveCambiada = true;
        } else {
            // Si no se cambió la clave, recuperar la actual de la BD
            Usuario tempUsuario = usuarioDAO.buscar(miPerfil.getCedula());
            if (tempUsuario != null) {
                miPerfil.setClave(tempUsuario.getClave());
            }
        }
        
        // Actualizar en la BD
        usuarioDAO.actualizar(miPerfil);
        
        // Actualizar la sesión
        context.getExternalContext().getSessionMap().put("user", miPerfil.getNombre());
        
        // Mensaje de confirmación
        String detalle = "Tus datos han sido actualizados correctamente.";
        if (claveCambiada) {
            detalle += " La clave ha sido modificada con éxito.";
        }
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", detalle));
        
        // Recargar perfil
        cargarMiPerfil();
        
        return null; // Permanecer en la misma página
    }
    
    // Getters y Setters
    public Usuario getMiPerfil() {
        if (miPerfil == null) {
            cargarMiPerfil();
        }
        return miPerfil;
    }
    
    public void setMiPerfil(Usuario miPerfil) {
        this.miPerfil = miPerfil;
    }
    
    public List<Movimiento> getMisMovimientos() {
        if (misMovimientos == null) {
            cargarMisMovimientos();
        }
        return misMovimientos;
    }
    
    public void setMisMovimientos(List<Movimiento> misMovimientos) {
        this.misMovimientos = misMovimientos;
    }
    
    public List<Deuda> getMisDeudas() {
        if (misDeudas == null) {
            cargarMisDeudas();
        }
        return misDeudas;
    }
    
    public void setMisDeudas(List<Deuda> misDeudas) {
        this.misDeudas = misDeudas;
    }
    
    public List<Pago> getMisPagos() {
        if (misPagos == null) {
            cargarMisPagos();
        }
        return misPagos;
    }
    
    public void setMisPagos(List<Pago> misPagos) {
        this.misPagos = misPagos;
    }
}

