package beans;

import dao.PagoDAO;
import dao.DeudaDAO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped; 
import modelo.Pago;
import modelo.Deuda;

@ManagedBean
@SessionScoped 
public class PagoBean implements Serializable {

    private Pago pago = new Pago();
    private PagoDAO pDAO = new PagoDAO();
    private DeudaDAO deudaDAO = new DeudaDAO();
    
    private List<Pago> listaPagos = new ArrayList<>();
    private List<Deuda> listaDeudasPendientes = new ArrayList<>();
    private int idPagoParam;
    private Integer deudaSeleccionadaId; // ID de la deuda seleccionada
    private int saldoPendiente; // Saldo pendiente de la deuda seleccionada

    // Constructor vacío
    public PagoBean() {}
    
    @PostConstruct
    public void init() {
        cargarDeudasPendientes();
    }

    // ---------------------------------------------
    // --- Métodos de Acción ---
    // ---------------------------------------------

    public void cargarDeudasPendientes() {
        listaDeudasPendientes = deudaDAO.listarDeudasPendientes();
    }
    
    public void cargarSaldoDeuda() {
        if (deudaSeleccionadaId != null && deudaSeleccionadaId > 0) {
            Deuda deuda = deudaDAO.buscar(deudaSeleccionadaId);
            if (deuda != null) {
                saldoPendiente = deuda.getSaldo();
                pago.setId_deuda(deudaSeleccionadaId);
            } else {
                saldoPendiente = 0;
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", 
                        "No se encontró la deuda seleccionada."));
            }
        } else {
            saldoPendiente = 0;
        }
    }
    
    public String realizarPago() {
        FacesContext context = FacesContext.getCurrentInstance();
        
        // Validar que se haya seleccionado una deuda
        if (deudaSeleccionadaId == null || deudaSeleccionadaId <= 0) {
            context.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", 
                    "Debe seleccionar una deuda."));
            return null;
        }
        
        // Validar que el abono sea mayor a 0
        if (pago.getAbono() <= 0) {
            context.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", 
                    "El abono debe ser mayor a 0."));
            return null;
        }
        
        // Validar que el abono no sea mayor al saldo pendiente
        Deuda deuda = deudaDAO.buscar(deudaSeleccionadaId);
        if (deuda == null) {
            context.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", 
                    "No se encontró la deuda seleccionada."));
            return null;
        }
        
        if (pago.getAbono() > deuda.getSaldo()) {
            context.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", 
                    "El abono ($" + pago.getAbono() + ") no puede ser mayor al saldo pendiente ($" + 
                    deuda.getSaldo() + ")."));
            return null;
        }
        
        // Asignar el ID de deuda al pago
        pago.setId_deuda(deudaSeleccionadaId);
        
        // Registrar el pago
        pDAO.registrarPago(pago);
        
        // Limpiar formulario
        pago = new Pago();
        deudaSeleccionadaId = null;
        saldoPendiente = 0;
        cargarDeudasPendientes();
        
        return "index?faces-redirect=true"; 
    }
    
    // Método para cargar el listado de pagos
    public void listarPagos() {
        // Limpiamos la instancia de pago para formularios
        pago = new Pago(); 
        
        System.out.println("DEBUG PAGO: Ejecutando listarPagos() en el Bean."); 
        this.listaPagos = pDAO.listar();
        System.out.println("DEBUG PAGO: Pagos encontrados: " + this.listaPagos.size()); 
    }
    
    public void cargarPagoParaEdicion() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (this.idPagoParam != 0) {
            this.pago = pDAO.buscar(this.idPagoParam); 
            if (this.pago == null) {
                 context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Pago ID " + this.idPagoParam + " no encontrado."));
            }
        }
    }
    
    public String actualizarPago() {
        FacesContext context = FacesContext.getCurrentInstance();
        
        // Verificar permisos: solo ADMINISTRADOR puede editar
        String tipoUsuario = (String) context.getExternalContext().getSessionMap().get("tipo_usuario");
        if (!"ADMINISTRADOR".equals(tipoUsuario)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", 
                "No tiene permisos para editar pagos. Solo los administradores pueden realizar esta acción."));
            return null;
        }
        
        pDAO.actualizarPago(pago);
        listarPagos(); 
        return "index?faces-redirect=true";
    }
    
    /**
     * Verifica si el usuario tiene permisos para editar pagos.
     * Redirige a index si es CAJERO.
     */
    public void verificarPermisosEdicion() {
        FacesContext context = FacesContext.getCurrentInstance();
        String tipoUsuario = (String) context.getExternalContext().getSessionMap().get("tipo_usuario");
        
        if (!"ADMINISTRADOR".equals(tipoUsuario)) {
            try {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Acceso Denegado", 
                    "No tiene permisos para editar pagos. Solo los administradores pueden realizar esta acción."));
                context.getExternalContext().redirect(context.getExternalContext().getRequestContextPath() + "/faces/pago/index.xhtml");
            } catch (Exception e) {
                System.err.println("Error al redirigir: " + e.getMessage());
            }
        }
    }

    // ---------------------------------------------
    // --- Getters y Setters ---
    // ---------------------------------------------
    
    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }
    
    public List<Pago> getListaPagos() {
        return listaPagos;
    }

    public void setListaPagos(List<Pago> listaPagos) {
        this.listaPagos = listaPagos;
    }

    public int getIdPagoParam() {
        return idPagoParam;
    }

    public void setIdPagoParam(int idPagoParam) {
        this.idPagoParam = idPagoParam;
    }
    
    public List<Deuda> getListaDeudasPendientes() {
        if (listaDeudasPendientes == null) {
            cargarDeudasPendientes();
        }
        return listaDeudasPendientes;
    }
    
    public void setListaDeudasPendientes(List<Deuda> listaDeudasPendientes) {
        this.listaDeudasPendientes = listaDeudasPendientes;
    }
    
    public Integer getDeudaSeleccionadaId() {
        return deudaSeleccionadaId;
    }
    
    public void setDeudaSeleccionadaId(Integer deudaSeleccionadaId) {
        this.deudaSeleccionadaId = deudaSeleccionadaId;
    }
    
    public int getSaldoPendiente() {
        return saldoPendiente;
    }
    
    public void setSaldoPendiente(int saldoPendiente) {
        this.saldoPendiente = saldoPendiente;
    }
    
    public String nuevo() {
        pago = new Pago();
        deudaSeleccionadaId = null;
        saldoPendiente = 0;
        cargarDeudasPendientes();
        return "nuevo?faces-redirect=true";
    }
}