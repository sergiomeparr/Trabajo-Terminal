package com.java.controlador;

import com.java.bean.modelo.Rankeo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RankeoCRUD {
        // Sentencia SQL para insertar Administrador en la Base de Datos
    private static final String SQL_INSERT
        = "insert into ranking (comentarios, estrellas, idhospital, idusuario) "
        + "values (?, ?, ?, ?)";
    
    //Sentencia SQL para eliminar Administrador de la Base de Datos
    private static final String SQL_DELETE
        = "delete from ranking where idranking = ?";

    private static final String SQL_SELECT_ALL
        = "select idranking, comentarios, estrellas, idhospital, idusuario from ranking where idhospital = ?";
    
    private static final String SQL_SELECT
        = "select idranking, comentarios, estrellas, idhospital, idusuario from ranking where idusuario = ?";
    
    private static final String SQL_CONTAR
        = "select count(*) from ranking where idhospital = ?";
    
    private static final String SQL_SELECT_RANKING
        = "select idranking, comentarios, estrellas, idhospital, idusuario from ranking where estrellas = ?";
    
    private static final String SQL_PROMEDIO
        = "select SUM(estrellas)/count(*) from ranking where idhospital = ?";     
    //Variable para conectar la base de datos con java
    Connection con = null;
    
    public void crear(Rankeo r) throws SQLException {
        //Se crea la variable que ejecutará la sentencia SQL
        PreparedStatement ps = null;
        //Obtencion de la conexion en la base de datos
        ObtenerConexion();
        try {
            //Insercion de datos
            ps = con.prepareStatement(SQL_INSERT);
            ps.setString(1, r.getComentario());
            ps.setInt(2, r.getRankeo());
            ps.setInt(3, r.getIdHospital());
            ps.setInt(4, r.getIdPaciente());
            ps.executeUpdate();
        } finally {
            cerrar(ps);
            cerrar(con);
        }
    }

    //Método para eliminar al Administrador
    public void eliminar(Rankeo r) throws SQLException {
        PreparedStatement ps = null;
        ObtenerConexion();
        try {
            ps = con.prepareStatement(SQL_DELETE);
            ps.setInt(1, r.getIdRanking());
            ps.executeUpdate();
        } finally {
            cerrar(ps);
            cerrar(con);
        }
    }

    public List readAll(Rankeo r) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ObtenerConexion();
        try {
            ps = con.prepareStatement(SQL_SELECT_ALL);
            ps.setInt(1, r.getIdHospital());
            rs = ps.executeQuery();
            List resultados = obtenerResultados(rs);
            if (resultados.size() > 0) {
                return resultados;
            } else {
                return null;
            }
        } finally {
            cerrar(rs);
            cerrar(ps);
            cerrar(con);
        }
    }
    
    public List read(Rankeo r) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ObtenerConexion();
        try {
            ps = con.prepareStatement(SQL_SELECT);
            ps.setInt(1, r.getIdPaciente());
            rs = ps.executeQuery();
            List resultados = obtenerResultados(rs);
            if (resultados.size() > 0) {
                return resultados;
            } else {
                return null;
            }
        } finally {
            cerrar(rs);
            cerrar(ps);
            cerrar(con);
        }
    }
    
    
    public List readRankeo(Rankeo r) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ObtenerConexion();
        try {
            ps = con.prepareStatement(SQL_SELECT_RANKING);
            ps.setInt(1, r.getRankeo());
            rs = ps.executeQuery();
            List resultados = obtenerResultados(rs);
            if (resultados.size() > 0) {
                return resultados;
            } else {
                return null;
            }
        } finally {
            cerrar(rs);
            cerrar(ps);
            cerrar(con);
        }
    }
    
    public int count(int idhospital) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        int total = 0;
        ObtenerConexion();
        try {
            ps = con.prepareStatement(SQL_CONTAR);
            ps.setInt(1, idhospital);
            rs = ps.executeQuery();
            if(rs.next())
                return total = rs.getInt(1);
        } finally {
            cerrar(rs);
            cerrar(ps);
            cerrar(con);
        }
        return total;
    }

    public int promedio(int idhospital) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        int total = 0;
        ObtenerConexion();
        try {
            ps = con.prepareStatement(SQL_PROMEDIO);
            ps.setInt(1, idhospital);
            rs = ps.executeQuery();
            if(rs.next())
                return total = rs.getInt(1);
        } finally {
            cerrar(rs);
            cerrar(ps);
            cerrar(con);
        }
        return total;
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

    private List obtenerResultados(ResultSet rs) throws SQLException {
        List resultados = new ArrayList();
        while (rs.next()) {
            Rankeo r = new Rankeo();
            //m.setIdMedicos(rs.getInt("idespecialista"));
            r.setIdRanking(rs.getInt("idranking"));
            r.setComentario(rs.getString("comentarios"));
            r.setRankeo(rs.getInt("estrellas"));
            r.setIdHospital(rs.getInt("idhospital"));
            r.setIdPaciente(rs.getInt("idusuario"));
            resultados.add(r);
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