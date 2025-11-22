package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Usuario;

public class UsuarioDAO {
    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;

    public UsuarioDAO() {
        con = ConnBD.conectar();
    }
    
    public List<Usuario> listarU(){
        List<Usuario> listUsr = null;
        
        try {
            String sql = "SELECT * FROM usuario";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            listUsr = new ArrayList<>();
            
            while(rs.next()){
                Usuario u = new Usuario();
                
                u.setCedula(rs.getInt("cedula"));
                u.setNombre(rs.getString("nombre"));
                u.setApellido(rs.getString("apellido"));
                u.setCorreo(rs.getString("correo"));
                u.setClave(rs.getString("clave"));
                u.setTelefono(rs.getInt("telefono"));
                u.setGenero(rs.getString("genero"));
                u.setFecha_nacimiento(rs.getString("Fecha_nacimiento"));
                u.setEstado(rs.getString("Estado"));
                
                
                String tipo = "";
                
                switch(rs.getString("tipo")){
                    case "A":
                        tipo = "Administrador";
                        break;
                    case "C":
                        tipo = "Cliente";
                        break;
                    case "V":
                        tipo = "Cajero";
                        break;
                }
                
                u.setTipo(tipo);
                
                listUsr.add(u);
            }
        } catch (SQLException e) {
        }
        
        return listUsr;
    }
    
    public void guardar(Usuario usr){
        try {
            String sql = "INSERT INTO usuario VALUES(1,2,3,4,5,6,7,8,9,10)";
            ps = con.prepareStatement(sql);
            ps.setInt(1, usr.getCedula());
            ps.setString(2, usr.getNombre());
            ps.setString(3, usr.getApellido());
            ps.setString(4, usr.getClave());
            ps.setString(5, usr.getCorreo());
            ps.setInt(6, usr.getTelefono());
            ps.setString(7, usr.getGenero());
            ps.setString(8, usr.getFecha_nacimiento());
            ps.setString(9, usr.getTipo());
            ps.setString(10, usr.getEstado());
            
            ps.executeUpdate();
            
        } catch (SQLException e) {
        }
    }
    
    public Usuario buscar(int cedula){
        Usuario usr = null;
        
        try {
            String sql = "SELECT * FROM usuario WHERE cedula = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, cedula);
            
            rs = ps.executeQuery();
            
            if(rs.next()){
                usr = new Usuario();
                
                usr.setCedula(rs.getInt("cedula"));
                usr.setNombre(rs.getString("nombre"));
                usr.setApellido(rs.getString("apellido"));
                usr.setCorreo(rs.getString("correo"));
                usr.setClave(rs.getString("clave"));
                usr.setTelefono(rs.getInt("telefono"));
                usr.setGenero(rs.getString("genero"));
                usr.setFecha_nacimiento(rs.getString("Fecha_nacimiento"));
                usr.setEstado(rs.getString("Estado"));
                String tipo = "";
                
                switch(rs.getString("tipo")){
                    case "A":
                        tipo = "Administrador";
                        break;
                    case "C":
                        tipo = "Cliente";
                        break;
                    case "V":
                        tipo = "Cajero";
                        break;
                }
                
                usr.setTipo(tipo);
            }                        
        } catch (SQLException e) {
        }
        
        return usr;
    }
    /*
    public void actualizar(Usuario usr){
        try {
            String sql = "UPDATE usuario SET doc = ?, nombre = ?, correo = ?, pass = ?, tipo = ? WHERE id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, usr.getDoc());
            ps.setString(2, usr.getNombre());
            ps.setString(3, usr.getCorreo());
            ps.setString(4, usr.getPass());
            ps.setString(5, usr.getTipo());
            ps.setInt(6, usr.getId());
                        
            ps.executeUpdate();
        } catch (SQLException e) {
        }        
    }
    
    public void eliminar(int id){
        try {
            String sql = "DELETE FROM usuario WHERE id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            
            ps.executeUpdate();
        } catch (SQLException e) {
        }
    }
*/
}
