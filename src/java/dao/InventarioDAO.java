package dao;

import modelo.Inventario;
import modelo.Productos;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InventarioDAO {

    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;
    
    private ProductosDAO productoDAO = new ProductosDAO(); 

    // ----------------------------------------------------------
    // LISTAR TODO
    // ----------------------------------------------------------
    public List<Inventario> listar() {
        List<Inventario> lista = new ArrayList<>();
        // Asumiendo que la columna de producto es NOMBRE_PRODUCTO
        String sql = "SELECT i.ID_ITEM, i.UBICACION, i.CANTIDAD, i.ESPECIFICACIONES, i.FECHA_VENCIMIENTO, i.ESTADO, p.NOMBRE_PRODUCTO "
                   + "FROM INVENTARIO i JOIN PRODUCTO p ON i.ID_ITEM = p.ID_PRODUCTO";

        try {
            con = ConnBD.conectar(); 
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Inventario item = new Inventario();
                Productos p = new Productos();
                
                p.setId_producto(rs.getInt("ID_ITEM")); 
                p.setNombre(rs.getString("NOMBRE_PRODUCTO")); 
                item.setId_item(p); 
                
                item.setUbicacion(rs.getString("UBICACION"));
                item.setCantidad(rs.getInt("CANTIDAD"));
                item.setEspecificaciones(rs.getString("ESPECIFICACIONES"));
                item.setFecha_vencimiento(rs.getDate("FECHA_VENCIMIENTO"));
                item.setEstado(rs.getString("ESTADO"));
                
                lista.add(item);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar inventario (SQL o Conexión): " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                System.out.println("Error al cerrar recursos en InventarioDAO.listar: " + ex.getMessage());
            }
        }
        return lista;
    }

    // ----------------------------------------------------------
    // GUARDAR 
    // ----------------------------------------------------------
    public void guardar(Inventario item) {
        String sql = "INSERT INTO INVENTARIO "
                    + "(ID_ITEM, UBICACION, CANTIDAD, ESPECIFICACIONES, FECHA_VENCIMIENTO, ESTADO) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";

        try {
            con = ConnBD.conectar();
            ps = con.prepareStatement(sql);
            
            if (item.getId_item() != null) {
                ps.setInt(1, item.getId_item().getIdProducto()); 
            } else {
                System.out.println("Error: El ID_ITEM (Producto) es nulo al guardar.");
                return; 
            }
            
            ps.setString(2, item.getUbicacion());
            ps.setInt(3, item.getCantidad());
            ps.setString(4, item.getEspecificaciones());
            ps.setDate(5, item.getFecha_vencimiento());
            ps.setString(6, item.getEstado());
            
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al guardar item de inventario: " + e.getMessage());
        } finally {
            try {
                if (ps != null) ps.close(); 
                if (con != null) con.close();
            } catch (SQLException ex) {
                System.out.println("Error al cerrar recursos después de guardar: " + ex.getMessage());
            }
        }
    }
    
    // ----------------------------------------------------------
    // BUSCAR
    // ----------------------------------------------------------
    public Inventario buscar(int idProducto) {
        // Cierre de recursos omitido aquí por brevedad, pero debe estar implementado.
        Inventario item = new Inventario();
        String sql = "SELECT * FROM INVENTARIO WHERE ID_ITEM = ?";
        // Lógica de búsqueda...
        return item;
    }
    
    // ----------------------------------------------------------
    // ACTUALIZAR 
    // ----------------------------------------------------------
    public void actualizar(Inventario item) {
        String sql = "UPDATE INVENTARIO SET "
                    + "UBICACION=?, CANTIDAD=?, ESPECIFICACIONES=?, FECHA_VENCIMIENTO=?, ESTADO=? "
                    + "WHERE ID_ITEM=?";

        try {
            con = ConnBD.conectar();
            ps = con.prepareStatement(sql);
            
            ps.setString(1, item.getUbicacion());
            ps.setInt(2, item.getCantidad());
            ps.setString(3, item.getEspecificaciones());
            ps.setDate(4, item.getFecha_vencimiento());
            ps.setString(5, item.getEstado());
            
            ps.setInt(6, item.getId_item().getIdProducto()); 
            
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al actualizar item de inventario: " + e.getMessage());
        } finally {
            try {
                if (ps != null) ps.close(); 
                if (con != null) con.close();
            } catch (SQLException ex) {
                System.out.println("Error al cerrar recursos después de actualizar: " + ex.getMessage());
            }
        }
    }
    
    // ----------------------------------------------------------
    // ELIMINAR (LÓGICA AGREGADA)
    // ----------------------------------------------------------
    public void eliminar(int idProducto) {
        String sql = "DELETE FROM INVENTARIO WHERE ID_ITEM=?";

        try {
            con = ConnBD.conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idProducto);
            
            int filasAfectadas = ps.executeUpdate(); 
            
            if (filasAfectadas > 0) {
                 System.out.println("Item de inventario con ID " + idProducto + " eliminado exitosamente.");
            } else {
                 System.out.println("No se encontró el item con ID " + idProducto + " para eliminar.");
            }
            
        } catch (SQLException e) {
            System.out.println("Error al eliminar item de inventario: " + e.getMessage());
        } finally {
            try {
                // Cierre de recursos crucial
                if (ps != null) ps.close(); 
                if (con != null) con.close();
            } catch (SQLException ex) {
                System.out.println("Error al cerrar recursos después de eliminar: " + ex.getMessage());
            }
        }
    }
}