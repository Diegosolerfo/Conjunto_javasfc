// Archivo: dao/ClienteDAO.java
package dao;

import modelo.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    
    public List<Cliente> listarClientes() {
        List<Cliente> clientes = new ArrayList<>();
        
        // CORRECCIÓN: Filtrar por TIPO_USUARIO = 'CLIENTE'
        String sql = "SELECT CEDULA, NOMBRE, APELLIDO FROM USUARIO WHERE TIPO_USUARIO = 'CLIENTE'"; 
        
        try (Connection con = ConnBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Cliente c = new Cliente();
                
                long cedula = rs.getLong("CEDULA");
                String nombre = rs.getString("NOMBRE");
                String apellido = rs.getString("APELLIDO");
                
                c.setIdCliente(cedula);
                // Formato que se mostrará en el desplegable: [CÉDULA] - [NOMBRE APELLIDO]
                c.setNombreCompleto(cedula + " - " + nombre + " " + apellido);
                
                clientes.add(c);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar clientes (DAO): " + e.getMessage());
        }
        return clientes;
    }
}