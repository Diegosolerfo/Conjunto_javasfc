package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import modelo.Compra;

public class CompraDAO {

    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;

    public CompraDAO() {
        con = ConnBD.conectar();
    }

    // ---------------------------------------------------------
    // LISTAR TODAS LAS COMPRAS
    // ---------------------------------------------------------
    public List<Compra> listar() {
        List<Compra> lista = null;

        try {
            String sql = "SELECT * FROM compra";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            lista = new ArrayList<>();

            while (rs.next()) {
                Compra c = new Compra();

                c.setIdCompra(rs.getInt("ID_COMPRA"));
                c.setFechaLlegada(rs.getString("FECHA_LLEGADA"));
                c.setProveedorC(rs.getInt("PROVEEDOR_C"));
                c.setMovimiento(rs.getInt("MOVIMIENTO"));
                c.setProductoComprado(rs.getInt("PRODUCTO_COMPRADO"));

                lista.add(c);
            }

        } catch (SQLException e) {
            System.out.println(e);
        }

        return lista;
    }

    // ---------------------------------------------------------
    // GUARDAR UNA NUEVA COMPRA
    // ---------------------------------------------------------
    public void guardar(Compra c) {
        try {
            String sql = "INSERT INTO compra (FECHA_LLEGADA, PROVEEDOR_C, MOVIMIENTO, PRODUCTO_COMPRADO) "
                    + "VALUES (?, ?, ?, ?)";
            ps = con.prepareStatement(sql);

            ps.setString(1, c.getFechaLlegada());
            ps.setInt(2, c.getProveedorC());
            ps.setInt(3, c.getMovimiento());
            ps.setInt(4, c.getProductoComprado());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ---------------------------------------------------------
    // BUSCAR UNA COMPRA
    // ---------------------------------------------------------
    public Compra buscar(int idCompra) {
        Compra c = null;

        try {
            String sql = "SELECT * FROM compra WHERE ID_COMPRA = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, idCompra);

            rs = ps.executeQuery();

            if (rs.next()) {
                c = new Compra();

                c.setIdCompra(rs.getInt("ID_COMPRA"));
                c.setFechaLlegada(rs.getString("FECHA_LLEGADA"));
                c.setProveedorC(rs.getInt("PROVEEDOR_C"));
                c.setMovimiento(rs.getInt("MOVIMIENTO"));
                c.setProductoComprado(rs.getInt("PRODUCTO_COMPRADO"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return c;
    }

    // ---------------------------------------------------------
    // ACTUALIZAR UNA COMPRA
    // ---------------------------------------------------------
    public void actualizar(Compra c) {
        try {
            String sql = "UPDATE compra SET FECHA_LLEGADA = ?, PROVEEDOR_C = ?, MOVIMIENTO = ?, "
                    + "PRODUCTO_COMPRADO = ? WHERE ID_COMPRA = ?";
            ps = con.prepareStatement(sql);

            ps.setString(1, c.getFechaLlegada());
            ps.setInt(2, c.getProveedorC());
            ps.setInt(3, c.getMovimiento());
            ps.setInt(4, c.getProductoComprado());
            ps.setInt(5, c.getIdCompra());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            String mensaje = e.getMessage();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se actualizó", mensaje));
        }
    }

    // ---------------------------------------------------------
    // ELIMINAR UNA COMPRA
    // ---------------------------------------------------------
    public void eliminar(int idCompra) {
        try {
            String sql = "DELETE FROM compra WHERE ID_COMPRA = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, idCompra);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
