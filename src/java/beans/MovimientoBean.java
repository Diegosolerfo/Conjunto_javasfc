package beans;

import dao.MovimientoDAO;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import modelo.Movimiento;

@ManagedBean
@SessionScoped
public class MovimientoBean {

    Movimiento movimiento = new Movimiento();
    List<Movimiento> listaM = new ArrayList<>();
    MovimientoDAO mDAO = new MovimientoDAO();

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

    public void listar() {
        movimiento = new Movimiento();
        listaM = mDAO.listar();
    }

    public void guardar() {
        mDAO.guardar(movimiento);
    }

    public String buscar(int id_movimiento) {
        movimiento = mDAO.buscar(id_movimiento);
        return "editar?faces-redirect=true";
    }

    public String actualizar() {
        mDAO.actualizar(movimiento);
        return "index?faces-redirect=true";
    }

    public void eliminar(int id_movimiento) {
        mDAO.eliminar(id_movimiento);
    }
}
