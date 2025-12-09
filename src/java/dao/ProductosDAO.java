package dao;

import modelo.Productos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException; // Importamos SQLException para un mejor manejo
import java.util.ArrayList;
import java.util.List;

public class ProductosDAO {

    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;

    // ----------------------------------------------------------
    // LISTAR
    // ----------------------------------------------------------
    public List<Productos> listar() {
        List<Productos> lista = new ArrayList<>();
        String sql = "SELECT * FROM PRODUCTO";

        try {
            con = ConnBD.conectar();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Productos p = new Productos();
                p.setIdProducto(rs.getInt("ID_PRODUCTO"));
                p.setNombreProducto(rs.getString("NOMBRE_PRODUCTO"));
                p.setDescripcionProducto(rs.getString("DESCRIPCION_PRODUCTO"));
                p.setValorUnitario(rs.getInt("VALOR_UNITARIO"));
                p.setUnidadMedida(rs.getString("UNIDAD_MEDIDA"));
                p.setEstadoProducto(rs.getString("ESTADO_PRODUCTO"));
                lista.add(p);
            }
        } catch (SQLException e) { // Cambiamos Exception por SQLException
            System.out.println("Error al listar productos: " + e.getMessage());
        } finally {
            // Asegurar que los recursos se cierren SIEMPRE
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                System.out.println("Error al cerrar recursos en ProductosDAO.listar: " + ex.getMessage());
            }
        }
        return lista;
    }

    // ----------------------------------------------------------
    // GUARDAR (Se recomienda agregar el cierre de recursos)
    // ----------------------------------------------------------
    public void guardar(Productos p) {
        String sql = "INSERT INTO PRODUCTO "
                + "(NOMBRE_PRODUCTO, DESCRIPCION_PRODUCTO, VALOR_UNITARIO, UNIDAD_MEDIDA, ESTADO_PRODUCTO) "
                + "VALUES (?, ?, ?, ?, ?)";

        try {
            con = ConnBD.conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, p.getNombreProducto());
            ps.setString(2, p.getDescripcionProducto());
            ps.setInt(3, p.getValorUnitario());
            ps.setString(4, p.getUnidadMedida());
            ps.setString(5, p.getEstadoProducto());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al guardar producto: " + e.getMessage());
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                System.out.println("Error al cerrar recursos en ProductosDAO.guardar: " + ex.getMessage());
            }
        }
    }

    // ----------------------------------------------------------
    // BUSCAR (Se recomienda agregar el cierre de recursos)
    // ----------------------------------------------------------
    public Productos buscar(int id) {
        Productos p = new Productos();
        String sql = "SELECT * FROM PRODUCTO WHERE ID_PRODUCTO = ?";

        try {
            con = ConnBD.conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                p.setIdProducto(rs.getInt("ID_PRODUCTO"));
                p.setNombreProducto(rs.getString("NOMBRE_PRODUCTO"));
                p.setDescripcionProducto(rs.getString("DESCRIPCION_PRODUCTO"));
                p.setValorUnitario(rs.getInt("VALOR_UNITARIO"));
                p.setUnidadMedida(rs.getString("UNIDAD_MEDIDA"));
                p.setEstadoProducto(rs.getString("ESTADO_PRODUCTO"));
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar producto: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                System.out.println("Error al cerrar recursos en ProductosDAO.buscar: " + ex.getMessage());
            }
        }
        return p;
    }

    // ----------------------------------------------------------
    // ACTUALIZAR (Se recomienda agregar el cierre de recursos)
    // ----------------------------------------------------------
    public void actualizar(Productos p) {
        String sql = "UPDATE PRODUCTO SET "
                    + "NOMBRE_PRODUCTO=?, DESCRIPCION_PRODUCTO=?, VALOR_UNITARIO=?, UNIDAD_MEDIDA=?, ESTADO_PRODUCTO=? "
                    + "WHERE ID_PRODUCTO=?";

        try {
            con = ConnBD.conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, p.getNombreProducto());
            ps.setString(2, p.getDescripcionProducto());
            ps.setInt(3, p.getValorUnitario());
            ps.setString(4, p.getUnidadMedida());
            ps.setString(5, p.getEstadoProducto());
            ps.setInt(6, p.getIdProducto());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al actualizar producto: " + e.getMessage());
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                System.out.println("Error al cerrar recursos en ProductosDAO.actualizar: " + ex.getMessage());
            }
        }
    }

    // ----------------------------------------------------------
    // ELIMINAR (Se recomienda agregar el cierre de recursos)
    // ----------------------------------------------------------
    public void eliminar(int id) {
        String sql = "DELETE FROM PRODUCTO WHERE ID_PRODUCTO=?";

        try {
            con = ConnBD.conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al eliminar producto: " + e.getMessage());
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                System.out.println("Error al cerrar recursos en ProductosDAO.eliminar: " + ex.getMessage());
            }
        }
    }
}