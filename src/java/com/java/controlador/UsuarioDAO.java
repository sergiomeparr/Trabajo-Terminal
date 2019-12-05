package com.java.controlador;

import com.java.bean.modelo.Usuario;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO{
    
    // Sentencia SQL para insertar Usuarios en la Base de Datos
    private static final String SQL_INSERT
        = "insert into usuario (usuarioo, contrasena) "
        + "values (?, ?)";
    
    //Sentencia SQL para eliminar Usuarios de la Base de Datos
    private static final String SQL_DELETE
        = "delete from usuario where id_usuario = ?";

    //Sentencia SQL para eliminar Usuarios de la Base de Datos
    private static final String SQL_UPDATE
        = "update usuario set usuarioo = ?, contrasena = ? where id_usuario = ?";

    private static final String SQL_SELECT_ALL
        = "select MAX(id_usuario) from usuario";    
    
    private static final String SQL_SELECT_BY_EMAIL
        = "select usuarioo from usuario where usuarioo like ?";    
    
    private static final String SQL_SELECT
        = "select id_usuario, usuarioo, contrasena from usuario where id_usuario = ?";
    
    //Variable para conectar la base de datos con java
    Connection con = null;
        
    public void crear(Usuario u) throws SQLException {
        //Se crea la variable que ejecutará la sentencia SQL
        PreparedStatement ps = null;
        
        //Obtencion de la conexion en la base de datos
        ObtenerConexion();
        try {
            //Insercion de datos
            ps = con.prepareStatement(SQL_INSERT);
            ps.setString(1, u.getUsuario());
            ps.setString(2, u.getContrasena());
            ps.executeUpdate();
        } finally {
            cerrar(ps);
            cerrar(con);
        }
    }

    public boolean seleccionar_by_email(String email) throws SQLException{
        PreparedStatement ps = null;
        ResultSet rs = null;
        ObtenerConexion();
        try {
            ps = con.prepareStatement(SQL_SELECT_BY_EMAIL);
            ps.setString(1, email);
            rs = ps.executeQuery();
            return rs.next();
        } finally {
            cerrar(ps);
            cerrar(con);
        }
    }
    
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
            cerrar(ps);
            cerrar(con);
        }
    }

    //Método para eliminar al Usuario
    public void eliminar(Usuario u) throws SQLException {
        PreparedStatement ps = null;
        ObtenerConexion();
        try {
            ps = con.prepareStatement(SQL_DELETE);
            ps.setInt(1, u.getIdUsuario());
            ps.executeUpdate();
        } finally {
            cerrar(ps);
            cerrar(con);
        }
    }

    public int seleccionar() throws SQLException{
        PreparedStatement ps = null;
        ResultSet rs = null;
        ObtenerConexion();
        try {
            ps = con.prepareStatement(SQL_SELECT_ALL);
            rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }
        } finally {
            cerrar(ps);
            cerrar(con);
            cerrar(rs);
        }
        return 0;
    }
    
    public Usuario read(Usuario u) throws SQLException{
        PreparedStatement ps = null;
        ResultSet rs = null;
        ObtenerConexion();
        try {
            ps = con.prepareStatement(SQL_SELECT);
            ps.setInt(1, u.getIdUsuario());
            rs = ps.executeQuery();
            List resultados = obtenerResultados(rs);
            if (resultados.size() > 0) {
                return (Usuario) resultados.get(0);
            } else {
                return null;
            }
        } finally {
            cerrar(ps);
            cerrar(con);
        }
    }
    
    
    private List obtenerResultados(ResultSet rs) throws SQLException {
        List resultados = new ArrayList();
        while (rs.next()) {
            Usuario u = new Usuario();
            //m.setIdMedicos(rs.getInt("idespecialista"));
            u.setUsuario(rs.getString("usuarioo"));
            u.setContrasena(rs.getString("contrasena"));
            resultados.add(u);
        }
        return resultados;
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
    
    //Se cierra la conexion con la base de datos 
    private void cerrar(ResultSet rs) {
        //Si todavia esta abierta la conexion, se cierra
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
            }
        }
    }
}