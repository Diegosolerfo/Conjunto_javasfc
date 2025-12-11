package dao;

import modelo.Deuda;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeudaDAO {
    
    // --- Métodos de la DeudaDAO ---

    /**
     * Registra una nueva deuda en la base de datos.
     */
    public void guardar(Deuda d) {
        String sql = "INSERT INTO DEUDA (SALDO, FECHA_VENCIMIENTO, MOVIMIENTO, PRODUCTO_PENDIENTE) VALUES (?, ?, ?, ?)";
        try (Connection con = ConnBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, d.getSaldo());
            ps.setDate(2, d.getFechaVencimiento()); 
            ps.setInt(3, d.getMovimiento());
            ps.setInt(4, d.getProductoPendiente());
            ps.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Error al guardar deuda: " + e.getMessage());
        }
    }
    
    /**
     * Lista todas las deudas.
     */
    public List<Deuda> listar() {
        List<Deuda> lista = new ArrayList<>();
        String sql = "SELECT * FROM DEUDA";
        
        try (Connection con = ConnBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Deuda d = new Deuda();
                d.setIdDeuda(rs.getInt("ID_DEUDA"));
                d.setSaldo(rs.getInt("SALDO"));
                d.setFechaVencimiento(rs.getDate("FECHA_VENCIMIENTO"));
                d.setMovimiento(rs.getInt("MOVIMIENTO"));
                d.setProductoPendiente(rs.getInt("PRODUCTO_PENDIENTE"));
                lista.add(d);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar deudas: " + e.getMessage());
        }
        return lista;
    }
    
    /**
     * Actualiza una deuda existente.
     */
    public void actualizar(Deuda d) {
        // En este ejemplo, solo permitiremos actualizar el Saldo y la Fecha de Vencimiento
        String sql = "UPDATE DEUDA SET SALDO = ?, FECHA_VENCIMIENTO = ? WHERE ID_DEUDA = ?";
        try (Connection con = ConnBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, d.getSaldo());
            ps.setDate(2, d.getFechaVencimiento());
            ps.setInt(3, d.getIdDeuda());
            ps.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar deuda: " + e.getMessage());
        }
    }

    /**
     * Busca una deuda por su ID.
     */
    public Deuda buscar(int id) {
        Deuda d = null;
        String sql = "SELECT * FROM DEUDA WHERE ID_DEUDA = ?";
        
        try (Connection con = ConnBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    d = new Deuda();
                    d.setIdDeuda(rs.getInt("ID_DEUDA"));
                    d.setSaldo(rs.getInt("SALDO"));
                    d.setFechaVencimiento(rs.getDate("FECHA_VENCIMIENTO"));
                    d.setMovimiento(rs.getInt("MOVIMIENTO"));
                    d.setProductoPendiente(rs.getInt("PRODUCTO_PENDIENTE"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar deuda: " + e.getMessage());
        }
        return d;
    }

    /**
     * NUEVO: Elimina una deuda por su ID.
     */
    public void eliminar(int id_deuda) {
        String sql = "DELETE FROM DEUDA WHERE ID_DEUDA = ?";
        
        try (Connection con = ConnBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id_deuda);
            ps.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar deuda: " + e.getMessage());
        }
    }
}