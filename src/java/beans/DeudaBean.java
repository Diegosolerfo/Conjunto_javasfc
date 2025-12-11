package beans;

import dao.DeudaDAO;
import modelo.Deuda;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.sql.Date; // Para convertir java.util.Date a java.sql.Date

@ManagedBean
@ViewScoped 
public class DeudaBean implements Serializable {
    
    private Deuda deuda = new Deuda();
    private List<Deuda> listaDeudas = new ArrayList<>();
    private DeudaDAO dDAO = new DeudaDAO();
    
    // Este campo nos ayuda a recibir la fecha desde el formulario XHTML
    private java.util.Date fechaUtil;
    
    // *************************************************************
    // NUEVAS PROPIEDADES PARA SOPORTE DE EDICIÓN CON VIEWPARAM (START)
    // *************************************************************
    
    // 1. Variable para capturar el ID de la URL
    private int idDeudaParam;
    
    // *************************************************************
    // NUEVAS PROPIEDADES PARA SOPORTE DE EDICIÓN CON VIEWPARAM (END)
    // *************************************************************

    public DeudaBean() {
        listarDeudas();
    }
    
    // ---------------------------------------------
    // --- Métodos de Acción ---
    // ---------------------------------------------
    
    // *************************************************************
    // NUEVO MÉTODO DE CARGA PARA VIEWPARAM (START)
    // *************************************************************
    
    /**
     * Carga la deuda al inicio de la vista de edición usando el ID de la URL.
     * Este método es llamado por f:viewAction en editar.xhtml.
     */
    public void cargarDeudaParaEdicion() {
        FacesContext context = FacesContext.getCurrentInstance();
        
        // Solo intentamos buscar si el ID es válido
        if (this.idDeudaParam != 0) {
            this.deuda = dDAO.buscar(this.idDeudaParam); 
            
            if (this.deuda != null) {
                // Si la deuda existe, preparamos la fecha para el campo de entrada
                if (this.deuda.getFechaVencimiento() != null) {
                    this.fechaUtil = new java.util.Date(this.deuda.getFechaVencimiento().getTime());
                } else {
                    this.fechaUtil = null;
                }
            } else {
                 // Manejar el caso si la deuda no se encuentra (opcionalmente podrías redirigir)
                 context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Deuda ID " + this.idDeudaParam + " no encontrada."));
            }
        }
    }
    
    // *************************************************************
    // NUEVO MÉTODO DE CARGA PARA VIEWPARAM (END)
    // *************************************************************

    public void listarDeudas() {
        this.listaDeudas = dDAO.listar();
    }
    
    public void nuevaDeuda() {
        this.deuda = new Deuda();
        this.fechaUtil = null;
    }

    public void guardarDeuda() {
        FacesContext context = FacesContext.getCurrentInstance();
        
        try {
            if (this.fechaUtil != null) {
                this.deuda.setFechaVencimiento(new Date(this.fechaUtil.getTime()));
            } else {
                 throw new IllegalArgumentException("La fecha de vencimiento no puede estar vacía.");
            }
            
            dDAO.guardar(this.deuda);
            
            listarDeudas();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Deuda registrada."));
            
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo registrar la deuda."));
            System.err.println("Error al guardar deuda en Bean: " + e.getMessage());
        }
    }
    
    public void actualizarDeuda() {
        FacesContext context = FacesContext.getCurrentInstance();
        
        try {
            if (this.fechaUtil != null) {
                this.deuda.setFechaVencimiento(new Date(this.fechaUtil.getTime()));
            } else {
                 throw new IllegalArgumentException("La fecha de vencimiento no puede estar vacía.");
            }
            
            dDAO.actualizar(this.deuda);
            
            listarDeudas();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Deuda actualizada."));
            
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo actualizar la deuda."));
            System.err.println("Error al actualizar deuda en Bean: " + e.getMessage());
        }
    }
    
    /**
     * Método obsoleto/NO usado con la nueva implementación de ViewParam/h:outputLink.
     * Si usaste la Solución 2, este método ya no será llamado.
     */
    public String prepararEdicion(Deuda d) {
        this.deuda = dDAO.buscar(d.getIdDeuda());
        
        if (this.deuda.getFechaVencimiento() != null) {
            this.fechaUtil = new java.util.Date(this.deuda.getFechaVencimiento().getTime());
        } else {
            this.fechaUtil = null;
        }
        return "editar?faces-redirect=true";
    }
    
    /**
     * NUEVO: Elimina una deuda por su ID.
     */
    public void eliminarDeuda(Deuda d) {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            dDAO.eliminar(d.getIdDeuda());
            listarDeudas();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Deuda ID " + d.getIdDeuda() + " eliminada."));
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo eliminar la deuda."));
            System.err.println("Error al eliminar deuda en Bean: " + e.getMessage());
        }
    }
    
    // ---------------------------------------------
    // --- Getters y Setters ---
    // ---------------------------------------------
    
    // *************************************************************
    // NUEVOS GETTERS Y SETTERS PARA VIEWPARAM (START)
    // *************************************************************

    public int getIdDeudaParam() {
        return idDeudaParam;
    }

    public void setIdDeudaParam(int idDeudaParam) {
        this.idDeudaParam = idDeudaParam;
    }
    
    // *************************************************************
    // NUEVOS GETTERS Y SETTERS PARA VIEWPARAM (END)
    // *************************************************************

    public Deuda getDeuda() {
        return deuda;
    }

    public void setDeuda(Deuda deuda) {
        this.deuda = deuda;
    }

    public List<Deuda> getListaDeudas() {
        return listaDeudas;
    }

    public void setListaDeudas(List<Deuda> listaDeudas) {
        this.listaDeudas = listaDeudas;
    }

    public java.util.Date getFechaUtil() {
        return fechaUtil;
    }

    public void setFechaUtil(java.util.Date fechaUtil) {
        this.fechaUtil = fechaUtil;
    }
}