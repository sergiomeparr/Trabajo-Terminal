package com.java.controlador;

import com.java.bean.modelo.Usuario;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Buscar_Usuario {
    //Variable para conectar la base de datos con java
    Connection con = null;
    
    //Sentencia SQL para buscar usuario registrado
    private static final String SQL_SELECT_USUARIO
        = "select usuarioo from Usuario where usuarioo = ?";
    private static final String SQL_SELECT_CONTRASENA
        = "select contrasena from Usuario where contrasena = ?";
    private static final String SQL_SELECT
        = "select id_usuario, usuarioo, contrasena from Usuario where usuarioo = ?";
    private static final String SQL_SELECT_ALL
        = "select id_usuario from usuario where usuarioo = ? and contrasena = ?";   
    private static final String SQL_UPDATE
        = "update usuario set usuarioo = ?, contrasena = ? where id_usuario = ?";   
    
    
    public String LeerUsuario(String usuario, String contrasena) throws SQLException {
        //Verificamos si el usuario existe al igual que su contraseña
        if(Existe_Usuario(usuario) == true){
            if(Existe_Contrasena(contrasena) == true)
                return "Usuario y contrasena correctos";
            else
                return "Contraseña incorrecta";
        }
        else{
            return "Usuario Incorrecto";
        }
    }

    public int identificador(String usuario, String contrasena) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ObtenerConexion();
        try {
            ps = con.prepareStatement(SQL_SELECT_ALL);
            ps.setString(1, usuario);
            ps.setString(2, contrasena);
            rs = ps.executeQuery();
            while (rs.next()) {
                //ResultSet devuelve verdadero si el registro se encuentra
                return rs.getInt("id_usuario");
            }
        } finally {
            cerrar(rs);
            cerrar(ps);
            cerrar(con);
        }
        return 0;
    }
    
    
    //Método para actualizar datos del Administrador
    public void actualizar(Usuario u) throws SQLException {
        //Se crea la variable que ejecutará la sentencia SQL
        PreparedStatement ps = null;
        //Obtencion de la conexion en la base de datos
        ObtenerConexion();
        try {
            //Insercion de datos
            ps = con.prepareStatement(SQL_UPDATE);
            ps.setString(1, u.getUsuario());
            ps.setString(2, u.getContrasena());
            ps.setInt(3, u.getIdUsuario());
            ps.executeUpdate();
        } finally {
            //Se cierran recursos
            cerrar(ps);
            cerrar(con);
        }
    }
    
    
    //Método para obtener la conexion con la base de datos
    private void ObtenerConexion() {
        //Usuario
        String user = "root";
        //Contraseña
        String pwd = "root";
        //URL de la base de datos
        String url = "jdbc:mysql://localhost:3306/auxiliar_prototipo4_1";
        //String url = "jdbc:mysql://localhost:3306/auxilir_prototipo5";
        
        //Driver JDBC
        String mySqlDriver = "com.mysql.jdbc.Driver";
        try {
            Class.forName(mySqlDriver);
            //La variable con(Connection) tiene la conexion con la BD
            con = DriverManager.getConnection(url, user, pwd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public boolean Existe_Usuario(String usuario) throws SQLException{
        PreparedStatement ps = null;
        ResultSet rs = null;
        ObtenerConexion();
        try {
            ps = con.prepareStatement(SQL_SELECT_USUARIO);
            ps.setString(1, usuario);
            rs = ps.executeQuery();
            //ResultSet devuelve verdadero si el registro se encuentra
            return rs.next();
        } finally {
            cerrar(rs);
            cerrar(ps);
            cerrar(con);
        }
    }
    
    public Usuario ObtenerContrasena(Usuario u) throws SQLException{
        PreparedStatement ps = null;
        ResultSet rs = null;
        ObtenerConexion();
        try {
            ps = con.prepareStatement(SQL_SELECT);
            ps.setString(1, u.getUsuario());
            rs = ps.executeQuery();
            List resultados = obtenerResultados(rs);
            if (resultados.size() > 0) {
                return (Usuario) resultados.get(0); 
            } else {
                return null;
            }
        } finally {
            cerrar(rs);
            cerrar(ps);
            cerrar(con);
        }
    }
    
    private boolean Existe_Contrasena(String contrasena) throws SQLException{
        PreparedStatement ps = null;
        ResultSet rs = null;
        ObtenerConexion();
        try {
            ps = con.prepareStatement(SQL_SELECT_CONTRASENA);
            ps.setString(1, contrasena);
            rs = ps.executeQuery();
            return rs.next();
        } finally {
            cerrar(rs);
            cerrar(ps);
            cerrar(con);
        }
    }
        
    private List obtenerResultados(ResultSet rs) throws SQLException {
        List resultados = new ArrayList();
        while (rs.next()) {
            Usuario u = new Usuario();
            u.setUsuario(rs.getString("usuarioo"));
            u.setContrasena(rs.getString("contrasena"));
            resultados.add(u);
        }
        return resultados;
    }
    
    //Se cierra el objecto PreparedStatement despues de ejecutar una sentencia
    private void cerrar(PreparedStatement ps) throws SQLException {
        //Si todavia esta abierto, se cierra
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
            }
        }
    }

    //Se cierra la conexion con la base de datos 
    private void cerrar(Connection cnn) {
        //Si todavia esta abierta la conexion, se cierra
        if (cnn != null) {
            try {
                cnn.close();
            } catch (SQLException e) {
            }
        }
    }

    private void cerrar(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
            }
        }
    }
}
