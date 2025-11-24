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
                
                u.setCedula(rs.getInt("CEDULA"));
                u.setNombre(rs.getString("NOMBRE"));
                u.setApellido(rs.getString("APELLIDO"));
                u.setClave(rs.getString("CLAVE"));
                u.setCorreo(rs.getString("CORREO"));
                u.setTelefono(rs.getInt("TELEFONO"));
                u.setGenero(rs.getString("GENERO"));
                u.setFecha_nacimiento(rs.getString("FECHA_NACIMIENTO"));
                u.setTipo_usuario(rs.getString("TIPO_USUARIO"));
                u.setEstado(rs.getString("ESTADO"));
                
                listUsr.add(u);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        return listUsr;
    }
    
    public void guardar(Usuario usr){
        try {
            String sql = "INSERT INTO usuario VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setInt(1, usr.getCedula());
            ps.setString(2, usr.getNombre());
            ps.setString(3, usr.getApellido());
            ps.setString(4, usr.getClave());
            ps.setString(5, usr.getCorreo());
            ps.setInt(6, usr.getTelefono());
            ps.setString(7, usr.getGenero());
            ps.setString(8, usr.getFecha_nacimiento());
            ps.setString(9, usr.getTipo_usuario());
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
                usr.setTelefono(rs.getInt("telefono"));
                usr.setGenero(rs.getString("genero"));
                usr.setFecha_nacimiento(rs.getString("fecha_nacimiento"));
                usr.setTipo_usuario(rs.getString("tipo_usuario"));
                usr.setEstado(rs.getString("estado"));
            }                        
        } catch (SQLException e) {
        }
        
        return usr;
    }
    
    public void actualizar(Usuario usr){
        try {
            String sql = "UPDATE usuario SET NOMBRE = ?, APELLIDO = ?, CLAVE = ?, CORREO = ?, TELEFONO = ?,"
                    + " GENERO = ?, FECHA_NACIMIENTO = ?, TIPO_USUARIO = ?, ESTADO = ? WHERE id = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, usr.getNombre());
            ps.setString(2, usr.getApellido());
            ps.setString(3, usr.getClave());
            ps.setString(4, usr.getCorreo());
            ps.setInt(5, usr.getTelefono());
            ps.setString(6, usr.getGenero());
            ps.setString(7, usr.getFecha_nacimiento());
            ps.setString(8, usr.getTipo_usuario());
            ps.setString(9, usr.getEstado());
            ps.setInt(10, usr.getCedula());
                        
            ps.executeUpdate();
        } catch (SQLException e) {
        }        
    }
    
    public void eliminar(int cedula){
        try {
            String sql = "DELETE FROM usuario WHERE cedula = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, cedula);
            
            ps.executeUpdate();
        } catch (SQLException e) {
        }
    }
}
