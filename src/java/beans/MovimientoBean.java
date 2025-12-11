package beans;

import dao.MovimientoDAO;
import dao.ClienteDAO; // Importar el DAO para cargar clientes
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct; // Importar PostConstruct
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import modelo.Movimiento;
import modelo.Cliente; // Importar el modelo Cliente

@ManagedBean
@SessionScoped
public class MovimientoBean implements Serializable {

    // DAOs
    MovimientoDAO mDAO = new MovimientoDAO();
    ClienteDAO cDAO = new ClienteDAO(); // Inicializar DAO de Cliente
    
    // Modelos y Listas para Movimiento
    Movimiento movimiento = new Movimiento();
    List<Movimiento> listaM = new ArrayList<>();
    
    // Lista para Clientes (Usuarios con rol 'CLIENTE')
    private List<Cliente> listaClientes; 

    // Constructor vacío (puedes dejarlo vacío si usas @PostConstruct)
    public MovimientoBean() {
    }

    // Usamos @PostConstruct para asegurar que la lista se cargue al crear el Bean
    @PostConstruct
    public void init() {
        cargarClientes();
        listar(); // También puedes iniciar la lista de movimientos aquí
    }

    // --- Lógica para Clientes ---

    public void cargarClientes() {
        this.listaClientes = cDAO.listarClientes();
    }
    
    // Getter para la lista de clientes (Usado en nuevo.xhtml)
    public List<Cliente> getListaClientes() {
        if (listaClientes == null) {
            cargarClientes();
        }
        return listaClientes;
    }

    public void setListaClientes(List<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }

    // --- Getters y Setters de Movimiento (Tus métodos originales) ---

    public Movimiento getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(Movimiento movimiento) {
        this.movimiento = movimiento;
    }

    public List<Movimiento> getListaM() {
        return listaM;
    }

    public void setListaM(List<Movimiento> listaM) {
        this.listaM = listaM;
    }
    
    // --- Lógica de Seguridad Auxiliar (Tu método original) ---
    
    private String obtenerRolSesion() {
        return (String) FacesContext.getCurrentInstance()
                                     .getExternalContext()
                                     .getSessionMap().get("tipo_usuario");
    }

    // --- Métodos de Acceso (Tus métodos originales) ---

    public void listar() {
        // Reiniciar el objeto movimiento para formularios nuevos, si es necesario
        movimiento = new Movimiento(); 
        listaM = mDAO.listar();
    }

    public void guardar() {
        // Permitido para CAJERO (registrar venta) y ADMINISTRADOR
        mDAO.guardar(movimiento);
    }

    // --- Métodos Restringidos (Solo ADMIN) (Tus métodos originales) ---

    public String buscar(int id_movimiento) {
        if ("ADMINISTRADOR".equals(obtenerRolSesion())) {
            movimiento = mDAO.buscar(id_movimiento);
            return "editar?faces-redirect=true";
        }
        return "index?faces-redirect=true";
    }

    public String actualizar() {
        if ("ADMINISTRADOR".equals(obtenerRolSesion())) {
            mDAO.actualizar(movimiento);
        }
        return "index?faces-redirect=true";
    }

    public void eliminar(int id_movimiento) {
        if ("ADMINISTRADOR".equals(obtenerRolSesion())) {
            mDAO.eliminar(id_movimiento);
        }
    }
}