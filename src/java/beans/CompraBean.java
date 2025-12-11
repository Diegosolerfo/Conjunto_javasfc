package beans;

import dao.CompraDAO;
import dao.ProveedorDAO;
import dao.ProductosDAO;
import dao.MovimientoDAO;
import dao.UsuarioDAO; // Importación añadida

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import modelo.Compra;
import modelo.Proveedor;
import modelo.Productos;
import modelo.Movimiento;
import modelo.Usuario; // Importación añadida

@ManagedBean
@ViewScoped
public class CompraBean {

    // 1. PROPIEDADES (Datos)
    private Compra compra = new Compra();

    // DAOs
    CompraDAO compraDAO = new CompraDAO();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO(); // Instancia del DAO de Usuario

    // Lista principal para la tabla del index
    List<Compra> listaCompras = new ArrayList<>();

    // Listas para los dropdowns (Proveedor, Producto, Movimiento, USUARIO)
    private List<Proveedor> listaProveedores = new ArrayList<>();
    private List<Productos> listaProductos = new ArrayList<>();
    private List<Movimiento> listaMovimientos = new ArrayList<>();
    private List<Usuario> listaUsuarios = new ArrayList<>(); // Lista de Usuarios AÑADIDA


    // 2. INICIALIZACIÓN
    @PostConstruct
    public void init() {
    // Carga la tabla principal
    listar();
    // Carga las listas para los dropdowns
    cargarListasDropdowns();
    }

    private void cargarListasDropdowns() {
    ProveedorDAO provDAO = new ProveedorDAO();
    this.listaProveedores = provDAO.listarP();

    MovimientoDAO movsDAO = new MovimientoDAO();
    this.listaMovimientos = movsDAO.listar();

    ProductosDAO prodDAO = new ProductosDAO();
    this.listaProductos = prodDAO.listar();

        // Carga de Usuarios AÑADIDA - Se cambió de listarTodos() a listarU()
        this.listaUsuarios = usuarioDAO.listarU();
    }

    // 3. GETTERS Y SETTERS

    public Compra getCompra() {
    return compra;
    }

    public void setCompra(Compra compra) {
    this.compra = compra;
    }

    // Método auxiliar si es necesario para compatibilidad (usando la propiedad 'idCompra' del modelo)
    public int getIdCompra() {
    return compra.getIdCompra();
    }


    public List<Compra> getListaCompras() {
    return listaCompras;
    }

    public void setListaCompras(List<Compra> listaCompras) {
    this.listaCompras = listaCompras;
    }

    public List<Proveedor> getListaProveedores() {
    return listaProveedores;
    }

    public List<Movimiento> getListaMovimientos() {
    return listaMovimientos;
    }

    public List<Productos> getListaProductos() {
    return listaProductos;
    }

    // GETTER DE USUARIOS AÑADIDO
    public List<Usuario> getListaUsuarios() {
    return listaUsuarios;
    }

    // 4. MÉTODOS DE ACCIÓN Y LÓGICA DE NEGOCIO (CRUD)

    public void listar() {
    this.compra = new Compra();
    listaCompras = compraDAO.listar();
    }

    public void guardar() {
    compraDAO.guardar(compra);
    this.compra = new Compra();
    }

    public String buscar(int idCompra) {
    compra = compraDAO.buscar(idCompra);
    cargarListasDropdowns();
    return "editar?faces-redirect=true";
    }

    public String actualizar() {
    compraDAO.actualizar(compra);
    return "index?faces-redirect=true";
    }

    public void eliminar(int idCompra) {
    compraDAO.eliminar(idCompra);
    listar();
    }
}