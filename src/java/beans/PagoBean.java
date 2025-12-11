package beans;

import dao.PagoDAO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped; 
import modelo.Pago;

@ManagedBean
@SessionScoped 
public class PagoBean implements Serializable {

    private Pago pago = new Pago();
    private PagoDAO pDAO = new PagoDAO();
    
    private List<Pago> listaPagos = new ArrayList<>();
    private int idPagoParam; 

    // Constructor vacío
    public PagoBean() {}

    // ---------------------------------------------
    // --- Métodos de Acción ---
    // ---------------------------------------------

    public String realizarPago() {
        pDAO.registrarPago(pago);
        // Redirección al listado de deudas (asumido)
        return "/deuda/index?faces-redirect=true"; 
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
        pDAO.actualizarPago(pago);
        listarPagos(); 
        return "/pago/index?faces-redirect=true";
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
}