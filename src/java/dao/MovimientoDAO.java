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

    // Se eliminan las variables de clase (con, ps, rs) para evitar fugas de conexión.
    
    // Se elimina el constructor que inicializaba la conexión persistente.

    public List<Movimiento> listar() {
        List<Movimiento> lista = new ArrayList<>();
        String sql = "SELECT * FROM MOVIMIENTO";

        // Usamos try-with-resources para asegurar que la conexión y el statement se cierren.
        try (Connection con = ConnBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

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
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error de BD", "No se cargaron los movimientos: " + e.getMessage()));
            System.err.println("Error SQL en listar: " + e.getMessage());
        }

        return lista;
    }

    // GUARDAR (CORREGIDO con Try-with-resources y orden de parámetros)
    public void guardar(Movimiento m) {
        // La consulta SQL debe ser precisa. Si ID_MOVIMIENTO es AUTO_INCREMENT, no se debe incluir.
        // Se asume el orden de la base de datos: (fecha, obs, valor, descuento, tipo, estado, cliente)
        String sql = "INSERT INTO MOVIMIENTO (FECHA_MOVIMIENTO, OBSERVACIONES, VALOR_MOVIMIENTO, DESCUENTO, TIPO_MOVIMIENTO, ESTADO_MOVIMIENTO, CLIENTE) "
                   + "VALUES(?,?,?,?,?,?,?)";
        
        try (Connection con = ConnBD.conectar(); 
             PreparedStatement ps = con.prepareStatement(sql)) {

            // El orden de los ps.setX debe coincidir con los '?' en la consulta
            ps.setString(1, m.getFecha_movimiento());
            ps.setString(2, m.getObservaciones());
            ps.setInt(3, m.getValor_movimiento());
            ps.setInt(4, m.getDescuento());
            ps.setString(5, m.getTipo_movimiento());
            ps.setString(6, m.getEstado_movimiento());
            ps.setInt(7, (int) m.getId_cliente());

            ps.executeUpdate();
            
            // Opcional: Mensaje de éxito visible para el usuario
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Movimiento registrado correctamente."));

        } catch (SQLException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Error de BD", "No se pudo guardar el movimiento: " + e.getMessage()));
            System.err.println("Error SQL al guardar: " + e.getMessage());
        }
    }

    public Movimiento buscar(int id_movimiento) {
        Movimiento m = null;
        String sql = "SELECT * FROM movimiento WHERE id_movimiento = ?";
        
        try (Connection con = ConnBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id_movimiento);
            
            try (ResultSet rs = ps.executeQuery()) {
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
            }
        } catch (SQLException e) {
             System.err.println("Error SQL al buscar: " + e.getMessage());
        }

        return m;
    }

    public void actualizar(Movimiento m) {
        String sql = "UPDATE movimiento SET "
                        + "valor_movimiento=?, descuento=?, cliente=?, fecha_movimiento=?, "
                        + "observaciones=?, tipo_movimiento=?, estado_movimiento=? "
                        + "WHERE id_movimiento=?";

        try (Connection con = ConnBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, m.getValor_movimiento());
            ps.setInt(2, m.getDescuento());
            ps.setInt(3, (int) m.getId_cliente());
            ps.setString(4, m.getFecha_movimiento());
            ps.setString(5, m.getObservaciones());
            ps.setString(6, m.getTipo_movimiento());
            ps.setString(7, m.getEstado_movimiento());
            ps.setInt(8, m.getId_movimiento());

            ps.executeUpdate();
             // Opcional: Mensaje de éxito visible para el usuario
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Movimiento actualizado correctamente."));

        } catch (SQLException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Error de BD", "No se pudo actualizar el movimiento: " + e.getMessage()));
             System.err.println("Error SQL al actualizar: " + e.getMessage());
        }
    }

    public void eliminar(int id_movimiento) {
        String sql = "DELETE FROM movimiento WHERE id_movimiento=?";
        
        try (Connection con = ConnBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id_movimiento);
            ps.executeUpdate();
            
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Movimiento eliminado."));

        } catch (SQLException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Error de BD", "No se pudo eliminar el movimiento: " + e.getMessage()));
            System.err.println("Error SQL al eliminar: " + e.getMessage());
        }
    }
}