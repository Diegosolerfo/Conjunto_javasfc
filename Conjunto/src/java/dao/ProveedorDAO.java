package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import modelo.Proveedor;

public class ProveedorDAO {
    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;

    public ProveedorDAO() {
        con = ConnBD.conectar();
    }
    
    public List<Proveedor> listarP(){
        List<Proveedor> listPrv = null;
        
        try {
            String sql = "SELECT * FROM PROVEEDOR";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            listPrv = new ArrayList<>();
            
            while(rs.next()){
                Proveedor p = new Proveedor();
                
                p.setId_proveedor(rs.getInt("ID_PROVEEDOR"));
                p.setNombre(rs.getString("NOMBRE_PROVEEDOR"));
                p.setTelefono(rs.getLong("TELEFONO"));
                p.setCorreo(rs.getString("CORREO"));
                p.setDireccion(rs.getString("DIRECCION"));
                p.setEstado(rs.getString("ESTADO"));
                listPrv.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            String mensaje = e.getMessage();
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se cargaron los datos", mensaje));
        }
        
        return listPrv;
    }
    
    public void guardar(Proveedor prv){
        try {
            String sql = "INSERT INTO proveedor VALUES(null,?,?,?,?,?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, prv.getNombre());
            ps.setLong(2, prv.getTelefono());
            ps.setString(3, prv.getCorreo());
            ps.setString(4, prv.getDireccion());
            ps.setString(5, prv.getEstado());
            
            ps.executeUpdate();
            
        } catch (SQLException e) {
        }
    }
    
    public Proveedor buscar(int id_proveedor){
        Proveedor prv = null;
        
        try {
            String sql = "SELECT * FROM PROVEEDOR WHERE ID_PROVEEDOR = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, id_proveedor);
            
            rs = ps.executeQuery();
            
            if(rs.next()){
                prv = new Proveedor();
                
                prv.setId_proveedor(rs.getInt("id_proveedor"));
                prv.setNombre(rs.getString("nombre_proveedor"));
                prv.setTelefono(rs.getLong("telefono"));
                prv.setCorreo(rs.getString("CORREO"));
                prv.setDireccion(rs.getString("direccion"));
                prv.setEstado(rs.getString("estado"));
            }                        
        } catch (SQLException e) {
        }
        
        return prv;
    }
    
    public void actualizar(Proveedor usr){
        try {
                String sql = "UPDATE proveedor SET NOMBRE_PROVEEDOR = ?, CORREO = ?, TELEFONO = ?, DIRECCION = ?, ESTADO = ? WHERE ID_PROVEEDOR = ?;";
            ps = con.prepareStatement(sql);
            ps.setString(1, usr.getNombre());
            ps.setString(2, usr.getCorreo());
            ps.setLong(3, usr.getTelefono());
            ps.setString(4, usr.getDireccion());
            ps.setString(5, usr.getEstado());
            ps.setInt(6, usr.getId_proveedor());    
            
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            String mensaje = e.getMessage();
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se actualizo", mensaje));
        }        
    }
    
    public void eliminar(int id_proveedor){
        try {
            String sql = "DELETE FROM proveedor WHERE id_proveedor = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, id_proveedor);
            
            ps.executeUpdate();
        } catch (SQLException e) {
        }
    }
}
