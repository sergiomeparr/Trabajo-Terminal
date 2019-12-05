package com.java.controlador;

import com.java.bean.modelo.Paciente;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO extends UsuarioDAO{
    
    // Sentencia SQL para insertar Pacientes en la Base de Datos
    private static final String SQL_INSERT
        = "insert into Paciente (nombrepaciente, apellidomaterno, telefono, idusuario, apellidopaterno, delegacion, sexo, foto) "
        + "values (?, ?, ?, ?, ?, ?, ? , ?)";
    //Sentencia SQL para eliminar Pacientes de la Base de Datos
    private static final String SQL_DELETE
        = "delete from paciente where idusuario = ?";
    private static final String SQL_SELECT
        = "select idusuario, nombrepaciente, apellidopaterno, delegacion, foto from paciente where idusuario = ?";    
    private static final String SQL_SELECT_ALL
        = "select nombrepaciente, apellidomaterno, telefono, idusuario, apellidopaterno, delegacion, sexo, foto from paciente where idusuario = ?";    
    private static final String SQL_UPDATE
        = "update paciente set nombrepaciente = ?, apellidomaterno = ?, telefono = ?, " +            
            "apellidopaterno = ?, delegacion = ?, sexo = ?, foto = ? where idusuario = ?";    
    //Variable para conectar la base de datos con java
    Connection con = null;
    
    public void crear(Paciente p) throws SQLException {
        //Se crea la variable que ejecutará la sentencia SQL
        PreparedStatement ps = null;
        //Obtencion de la conexion en la base de datos
        ObtenerConexion();
        try {
            //Insercion de datos
            ps = con.prepareStatement(SQL_INSERT);
            //ps.setInt(1, p.getIdUsuario().getIdUsuario());
            ps.setString(1, p.getNombrePaciente());
            ps.setString(2, p.getApellidoMaterno());
            ps.setString(3, p.getTelefono());
            ps.setInt(4, p.getIdUsuario());
            ps.setString(5, p.getApellidoPaterno());
            ps.setString(6, p.getDelegacion());
            ps.setString(7, p.getSexo());
            ps.setString(8, p.getFoto());
            ps.executeUpdate();
        } finally {
            cerrar(ps);
            cerrar(con);
        }
    }

    //Método para eliminar al Pacientes
    public void eliminar(Paciente p) throws SQLException {
        PreparedStatement ps = null;
        ObtenerConexion();
        try {
            ps = con.prepareStatement(SQL_DELETE);
            ps.setInt(1, p.getIdUsuario());
            ps.executeUpdate();
        } finally {
            cerrar(ps);
            cerrar(con);
        }
    }

    public boolean seleccionar(int id) throws SQLException{
        PreparedStatement ps = null;
        ResultSet rs = null;
        ObtenerConexion();
        try {
            ps = con.prepareStatement(SQL_SELECT);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            return rs.next();
        } finally {
            cerrar(ps);
            cerrar(con);
        }
    }
    
    public Paciente seleccionar_informacion(int id) throws SQLException{
        PreparedStatement ps = null;
        ResultSet rs = null;
        ObtenerConexion();
        try {
            ps = con.prepareStatement(SQL_SELECT_ALL);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            List resultados = obtenerResultados(rs);
            if (resultados.size() > 0) {
                return (Paciente) resultados.get(0);
            } else {
                return null;
            }
        } finally {
            cerrar(ps);
            cerrar(con);
        }
    }
    
    public void actualizar(Paciente p) throws SQLException {
        //Se crea la variable que ejecutará la sentencia SQL
        PreparedStatement ps = null;
        //Obtencion de la conexion en la base de datos
        ObtenerConexion();
        try {
            //Actualización de datos
            ps = con.prepareStatement(SQL_UPDATE);
            ps.setString(1, p.getNombrePaciente());
            ps.setString(2, p.getApellidoMaterno());
            ps.setString(3, p.getTelefono());
            ps.setString(4, p.getApellidoPaterno());
            ps.setString(5, p.getDelegacion());
            ps.setString(6, p.getSexo());
            ps.setString(7, p.getFoto());
            ps.setInt(8, p.getIdUsuario());
            ps.executeUpdate();
        } finally {
            cerrar(ps);
            cerrar(con);
        }
    }

    
    private List obtenerResultados(ResultSet rs) throws SQLException {
        List resultados = new ArrayList();
        while (rs.next()) {
            Paciente p = new Paciente();
            //m.setIdMedicos(rs.getInt("idespecialista"));
            p.setIdUsuario(rs.getInt("idusuario"));
            p.setNombrePaciente(rs.getString("nombrepaciente"));
            p.setApellidoMaterno(rs.getString("apellidomaterno"));
            p.setTelefono(rs.getString("telefono"));
            p.setIdUsuario(rs.getInt("idusuario"));
            p.setApellidoPaterno(rs.getString("apellidopaterno"));
            p.setDelegacion(rs.getString("delegacion"));
            p.setSexo(rs.getString("sexo"));
            p.setFoto(rs.getString("foto"));
            resultados.add(p);
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
}


