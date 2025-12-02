package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import modelo.Movimiento;

public class MovimientoDAO {

    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;

    public MovimientoDAO() {
        con = ConnBD.conectar();
    }

    public List<Movimiento> listar() {
        List<Movimiento> lista = null;

        try {
            String sql = "SELECT * FROM MOVIMIENTO";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            lista = new ArrayList<>();

            while (rs.next()) {
                Movimiento m = new Movimiento();

                m.setId_movimiento(rs.getInt("ID_MOVIMIENTO"));
                m.setValor_movimiento(rs.getInt("VALOR_MOVIMIENTO"));
                m.setDescuento(rs.getInt("DESCUENTO"));
                m.setId_cliente(rs.getInt("CLIENTE"));
                m.setFecha_movimiento(rs.getString("FECHA_MOVIMIENTO"));
                m.setObservaciones(rs.getString("OBSERVACIONES"));
                m.setTipo_movimiento(rs.getString("TIPO_MOVIMIENTO"));
                m.setEstado_movimiento(rs.getString("ESTADO_MOVIMIENTO"));

                lista.add(m);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "No se cargaron los movimientos", e.getMessage()));
        }

        return lista;
    }

    // GUARDAR
    public void guardar(Movimiento m) {
        try {
            String sql = "INSERT INTO MOVIMIENTO VALUES(null,?,?,?,?,?,?,?)";
            ps = con.prepareStatement(sql);

            ps.setString(1, m.getFecha_movimiento());
            ps.setString(2, m.getObservaciones());
            ps.setInt(3, m.getValor_movimiento());
            ps.setInt(4, m.getDescuento());
            ps.setString(5, m.getTipo_movimiento());
            ps.setString(6, m.getEstado_movimiento());
            ps.setInt(7, m.getId_cliente());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "No se pudo guardar el movimiento", e.getMessage()));
        }
    }

    public Movimiento buscar(int id_movimiento) {
        Movimiento m = null;

        try {
            String sql = "SELECT * FROM movimiento WHERE id_movimiento = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, id_movimiento);

            rs = ps.executeQuery();

            if (rs.next()) {
                m = new Movimiento();

                m.setId_movimiento(rs.getInt("id_movimiento"));
                m.setValor_movimiento(rs.getInt("valor_movimiento"));
                m.setDescuento(rs.getInt("descuento"));
                m.setId_cliente(rs.getInt("CLIENTE"));
                m.setFecha_movimiento(rs.getString("FECHA_MOVIMIENTO"));
                m.setObservaciones(rs.getString("OBSERVACIONES"));
                m.setTipo_movimiento(rs.getString("tipo_movimiento"));
                m.setEstado_movimiento(rs.getString("estado_movimiento"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return m;
    }

    public void actualizar(Movimiento m) {
        try {
            String sql = "UPDATE movimiento SET "
                    + "valor_movimiento=?, descuento=?, cliente=?, fecha_movimiento=?, "
                    + "observaciones=?, tipo_movimiento=?, estado_movimiento=? "
                    + "WHERE id_movimiento=?";

            ps = con.prepareStatement(sql);

            ps.setInt(1, m.getValor_movimiento());
            ps.setInt(2, m.getDescuento());
            ps.setInt(3, m.getId_cliente());
            ps.setString(4, m.getFecha_movimiento());
            ps.setString(5, m.getObservaciones());
            ps.setString(6, m.getTipo_movimiento());
            ps.setString(7, m.getEstado_movimiento());
            ps.setInt(8, m.getId_movimiento());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "No se actualizó el movimiento", e.getMessage()));
        }
    }

    public void eliminar(int id_movimiento) {
        try {
            String sql = "DELETE FROM movimiento WHERE id_movimiento=?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, id_movimiento);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
