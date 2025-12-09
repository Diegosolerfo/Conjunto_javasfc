package dao;

import modelo.Proveedor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProveedorDAO {

    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;

    // ----------------------------------------------------------
    // LISTAR PROVEEDORES
    // ----------------------------------------------------------
    public List<Proveedor> listarP() {
        List<Proveedor> lista = new ArrayList<>();
        // CLAVE: La consulta SQL debe reflejar los nombres correctos (NOMBRE_PROVEEDOR)
        String sql = "SELECT ID_PROVEEDOR, NOMBRE_PROVEEDOR, TELEFONO, CORREO, DIRECCION, ESTADO FROM PROVEEDOR";

        try {
            con = ConnBD.conectar();
            if (con == null) {
                System.out.println("ERROR FATAL: La conexión a la BD es nula. Revise ConnBD.conectar().");
                return lista; 
            }
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Proveedor p = new Proveedor();
                // CORRECCIÓN CLAVE: Usar los nombres EXACTOS de las columnas SQL (MAYÚSCULAS)
                p.setId_proveedor(rs.getInt("ID_PROVEEDOR"));
                // ¡AQUÍ ESTABA EL ERROR! Usar NOMBRE_PROVEEDOR
                p.setNombre(rs.getString("NOMBRE_PROVEEDOR"));
                p.setTelefono(rs.getLong("TELEFONO"));
                p.setCorreo(rs.getString("CORREO"));
                p.setDireccion(rs.getString("DIRECCION"));
                p.setEstado(rs.getString("ESTADO"));
                lista.add(p);
            }
            System.out.println("Listado de proveedores OK. Filas encontradas: " + lista.size());
            
        } catch (SQLException e) {
            System.err.println("Error FATAL al listar proveedores. SQL: " + sql + ". Causa: " + e.getMessage());
            e.printStackTrace(); 
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                System.err.println("Error al cerrar recursos en ProveedorDAO.listarP: " + ex.getMessage());
            }
        }
        return lista;
    }

    // ----------------------------------------------------------
    // GUARDAR 
    // ----------------------------------------------------------
    public void guardar(Proveedor prv) {
        // CLAVE: Ajustar la consulta INSERT para usar NOMBRE_PROVEEDOR
        String sql = "INSERT INTO PROVEEDOR (NOMBRE_PROVEEDOR, TELEFONO, CORREO, DIRECCION, ESTADO) VALUES (?, ?, ?, ?, ?)";

        try {
            con = ConnBD.conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, prv.getNombre());
            ps.setLong(2, prv.getTelefono());
            ps.setString(3, prv.getCorreo());
            ps.setString(4, prv.getDireccion());
            ps.setString(5, prv.getEstado());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al guardar proveedor: " + e.getMessage());
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                System.err.println("Error al cerrar recursos en ProveedorDAO.guardar: " + ex.getMessage());
            }
        }
    }
    
    // ----------------------------------------------------------
    // BUSCAR
    // ----------------------------------------------------------
    public Proveedor buscar(int id) {
        Proveedor p = new Proveedor();
        // CLAVE: La consulta SELECT debe usar NOMBRE_PROVEEDOR
        String sql = "SELECT ID_PROVEEDOR, NOMBRE_PROVEEDOR, TELEFONO, CORREO, DIRECCION, ESTADO FROM PROVEEDOR WHERE ID_PROVEEDOR = ?";

        try {
            con = ConnBD.conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                // CORRECCIÓN CLAVE: Usar los nombres EXACTOS de las columnas SQL (MAYÚSCULAS)
                p.setId_proveedor(rs.getInt("ID_PROVEEDOR"));
                p.setNombre(rs.getString("NOMBRE_PROVEEDOR")); // ¡AQUÍ ESTABA EL ERROR!
                p.setTelefono(rs.getLong("TELEFONO"));
                p.setCorreo(rs.getString("CORREO"));
                p.setDireccion(rs.getString("DIRECCION"));
                p.setEstado(rs.getString("ESTADO"));
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar proveedor: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                System.err.println("Error al cerrar recursos en ProveedorDAO.buscar: " + ex.getMessage());
            }
        }
        return p;
    }
    
    // ----------------------------------------------------------
    // ACTUALIZAR
    // ----------------------------------------------------------
    public void actualizar(Proveedor prv) {
        // CLAVE: La consulta UPDATE debe usar NOMBRE_PROVEEDOR
        String sql = "UPDATE PROVEEDOR SET NOMBRE_PROVEEDOR=?, TELEFONO=?, CORREO=?, DIRECCION=?, ESTADO=? WHERE ID_PROVEEDOR=?";

        try {
            con = ConnBD.conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, prv.getNombre());
            ps.setLong(2, prv.getTelefono());
            ps.setString(3, prv.getCorreo());
            ps.setString(4, prv.getDireccion());
            ps.setString(5, prv.getEstado());
            ps.setInt(6, prv.getId_proveedor());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al actualizar proveedor: " + e.getMessage());
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                System.err.println("Error al cerrar recursos en ProveedorDAO.actualizar: " + ex.getMessage());
            }
        }
    }
    
    // ----------------------------------------------------------
    // ELIMINAR
    // ----------------------------------------------------------
    public void eliminar(int id) {
        String sql = "DELETE FROM PROVEEDOR WHERE ID_PROVEEDOR=?";

        try {
            con = ConnBD.conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al eliminar proveedor: " + e.getMessage());
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                System.err.println("Error al cerrar recursos en ProveedorDAO.eliminar: " + ex.getMessage());
            }
        }
    }
}