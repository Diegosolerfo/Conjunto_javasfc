package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import modelo.Pago;

// Asume que ConnBD.conectar() existe y funciona correctamente

public class PagoDAO {

    // --- Implementación de registrarPago (Transaccional) ---

    public void registrarPago(Pago p) {
        String sqlInsert = "INSERT INTO PAGOS (ID_DEUDA, ABONO, FECHA) VALUES (?, ?, ?)";
        String sqlUpdateDeuda = "UPDATE DEUDA SET SALDO = SALDO - ? WHERE ID_DEUDA = ?";
        Connection con = null;

        try {
            con = ConnBD.conectar();
            con.setAutoCommit(false); 

            // 1. Insertar el Pago
            try (PreparedStatement psInsert = con.prepareStatement(sqlInsert)) {
                psInsert.setInt(1, p.getId_deuda());
                psInsert.setInt(2, p.getAbono());
                psInsert.setString(3, p.getFecha());
                psInsert.executeUpdate();
            }

            // 2. Actualizar la Deuda (descontar el abono del saldo)
            try (PreparedStatement psUpdate = con.prepareStatement(sqlUpdateDeuda)) {
                psUpdate.setInt(1, p.getAbono());
                psUpdate.setInt(2, p.getId_deuda());
                int filasAfectadas = psUpdate.executeUpdate();
                
                if (filasAfectadas == 0) {
                    throw new SQLException("No se encontró la deuda con ID: " + p.getId_deuda());
                }
            }
            
            con.commit();
            
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Pago registrado y deuda actualizada correctamente."));

        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback(); 
                } catch (SQLException ex) {
                    System.err.println("Error en Rollback: " + ex.getMessage());
                }
            }
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error de BD", "Fallo en el registro del pago: " + e.getMessage()));
            System.err.println("Error SQL al registrar pago: " + e.getMessage());
            e.printStackTrace();
            
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close(); 
                } catch (SQLException ex) {
                    System.err.println("Error al cerrar conexión: " + ex.getMessage());
                }
            }
        }
    }

    // --- LISTAR TODOS LOS PAGOS ---

    public List<Pago> listar() {
        List<Pago> lista = new ArrayList<>();
        String sql = "SELECT ID_PAGO, ID_DEUDA, ABONO, FECHA FROM PAGOS ORDER BY ID_PAGO DESC";
        
        Connection con = null;
        try {
            con = ConnBD.conectar();
            try (PreparedStatement ps = con.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Pago p = new Pago();
                    p.setId_pago(rs.getInt("ID_PAGO"));
                    p.setId_deuda(rs.getInt("ID_DEUDA"));
                    p.setAbono(rs.getInt("ABONO"));
                    p.setFecha(rs.getString("FECHA")); 
                    lista.add(p);
                }
            }
        } catch (SQLException e) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error Crítico de Listado", "Fallo SQL: " + e.getMessage()));
            System.err.println("Error SQL al listar pagos: " + e.getMessage());
            
        } finally {
            if (con != null) {
                try { con.close(); } catch (SQLException ex) {
                    System.err.println("Error al cerrar conexión después de listar: " + ex.getMessage());
                }
            }
        }
        return lista;
    }
    
    /**
     * Lista pagos filtrados por ID de cliente (a través de deuda -> movimiento).
     */
    public List<Pago> listarPorCliente(long idCliente) {
        List<Pago> lista = new ArrayList<>();
        String sql = "SELECT p.ID_PAGO, p.ID_DEUDA, p.ABONO, p.FECHA " +
                     "FROM PAGOS p " +
                     "INNER JOIN DEUDA d ON p.ID_DEUDA = d.ID_DEUDA " +
                     "INNER JOIN MOVIMIENTO m ON d.MOVIMIENTO = m.ID_MOVIMIENTO " +
                     "WHERE m.CLIENTE = ? ORDER BY p.ID_PAGO DESC";
        
        Connection con = null;
        try {
            con = ConnBD.conectar();
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setLong(1, idCliente);
                
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Pago p = new Pago();
                        p.setId_pago(rs.getInt("ID_PAGO"));
                        p.setId_deuda(rs.getInt("ID_DEUDA"));
                        p.setAbono(rs.getInt("ABONO"));
                        p.setFecha(rs.getString("FECHA"));
                        lista.add(p);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error SQL al listar pagos por cliente: " + e.getMessage());
        } finally {
            if (con != null) {
                try { con.close(); } catch (SQLException ex) {
                    System.err.println("Error al cerrar conexión después de listar: " + ex.getMessage());
                }
            }
        }
        return lista;
    }

    // --- Buscar un pago por ID ---

    public Pago buscar(int id) {
        Pago pago = null;
        String sql = "SELECT ID_PAGO, ID_DEUDA, ABONO, FECHA FROM PAGOS WHERE ID_PAGO = ?";
        Connection con = null;
        try {
            con = ConnBD.conectar();
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        pago = new Pago();
                        pago.setId_pago(rs.getInt("ID_PAGO"));
                        pago.setId_deuda(rs.getInt("ID_DEUDA"));
                        pago.setAbono(rs.getInt("ABONO"));
                        pago.setFecha(rs.getString("FECHA"));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error SQL al buscar pago: " + e.getMessage());
        } finally {
            if (con != null) { try { con.close(); } catch (SQLException ex) {} }
        }
        return pago;
    }

    // --- Actualizar un pago ---

    public void actualizarPago(Pago p) {
        String sqlUpdate = "UPDATE PAGOS SET ID_DEUDA = ?, ABONO = ?, FECHA = ? WHERE ID_PAGO = ?";
        Connection con = null;

        try {
            con = ConnBD.conectar();
            try (PreparedStatement psUpdate = con.prepareStatement(sqlUpdate)) {
                psUpdate.setInt(1, p.getId_deuda());
                psUpdate.setInt(2, p.getAbono());
                psUpdate.setString(3, p.getFecha());
                psUpdate.setInt(4, p.getId_pago());
                psUpdate.executeUpdate();
            }
            
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Pago ID " + p.getId_pago() + " actualizado."));

        } catch (SQLException e) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error de BD", "Fallo al actualizar el pago."));
            System.err.println("Error SQL al actualizar pago: " + e.getMessage());
            
        } finally {
            if (con != null) {
                try { con.close(); } catch (SQLException ex) {
                    System.err.println("Error al cerrar conexión después de actualizar: " + ex.getMessage());
                }
            }
        }
    }
}