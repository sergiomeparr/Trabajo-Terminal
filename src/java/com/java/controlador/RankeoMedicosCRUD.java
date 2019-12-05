package com.java.controlador;

import com.java.bean.modelo.RankeoMedicos;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RankeoMedicosCRUD {
        // Sentencia SQL para insertar Administrador en la Base de Datos
    private static final String SQL_INSERT
        = "insert into rankingmedicos (comentarios, estrellas, idhospital, idusuario, idmedico) "
        + "values (?, ?, ?, ?, ?)";
    
        //Sentencia SQL para eliminar Administrador de la Base de Datos
    private static final String SQL_DELETE
        = "delete from rankingmedicos where idrankingmedico = ?";

    private static final String SQL_SELECT
        = "select idrankingmedico, comentarios, estrellas, idhospital, idusuario, idmedico from rankingmedicos where idusuario = ?";
    
    private static final String SQL_SELECT_ALL
        = "select idrankingmedico, comentarios, estrellas, idhospital, idusuario, idmedico from rankingmedicos where idhospital = ? and idmedico = ?";
    
    private static final String SQL_CONTAR
        = "select count(*) from rankingmedicos where idhospital = ? and idmedico = ?";
    
    private static final String SQL_PROMEDIO
        = "select SUM(estrellas)/count(*) from rankingmedicos where idhospital = ? and idmedico=?";     
    
    private static final String SQL_SELECT_RANKEO_MEDICO
        = "select idrankingmedico, comentarios, estrellas, idhospital, idusuario, idmedico from "
            + "rankingmedicos where idmedico = ?";     
        
    //Variable para conectar la base de datos con java
    Connection con = null;
    
    public void crear(RankeoMedicos r) throws SQLException {
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
            ps.setInt(4, r.getIdUsuario());
            ps.setInt(5, r.getIdMedico());
            ps.executeUpdate();
        } finally {
            cerrar(ps);
            cerrar(con);
        }
    }

    //Método para eliminar al Administrador
    public void eliminar(RankeoMedicos r) throws SQLException {
        PreparedStatement ps = null;
        ObtenerConexion();
        try {
            ps = con.prepareStatement(SQL_DELETE);
            ps.setInt(1, r.getIdRankingMedicos());
            ps.executeUpdate();
        } finally {
            cerrar(ps);
            cerrar(con);
        }
    }

     public List<RankeoMedicos> read(RankeoMedicos r) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ObtenerConexion();
        try {
            ps = con.prepareStatement(SQL_SELECT);
            ps.setInt(1, r.getIdUsuario());
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
    
    
    public List readAll(RankeoMedicos r) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ObtenerConexion();
        try {
            ps = con.prepareStatement(SQL_SELECT_ALL);
            ps.setInt(1, r.getIdHospital());
            ps.setInt(2, r.getIdMedico());
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
    
    
    public RankeoMedicos selecionar_medico(RankeoMedicos r) throws SQLException{
        PreparedStatement ps = null;
        ResultSet rs = null;
        ObtenerConexion();
        try {
            ps = con.prepareStatement(SQL_SELECT_RANKEO_MEDICO);
            ps.setInt(1, r.getIdMedico());
            rs = ps.executeQuery();
            List resultados = obtenerResultados(rs);
            if(resultados.size() > 0)
                return (RankeoMedicos) resultados.get(0);
            else
                return null;    
        } finally {
            cerrar(rs);
            cerrar(ps);
            cerrar(con);
        }
    }
    
    
    
    public int count(RankeoMedicos r) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        int total = 0;
        ObtenerConexion();
        try {
            ps = con.prepareStatement(SQL_CONTAR);
            ps.setInt(1, r.getIdHospital());
            ps.setInt(2, r.getIdMedico());
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

    public int promedio(RankeoMedicos r) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        int total = 0;
        ObtenerConexion();
        try {
            ps = con.prepareStatement(SQL_PROMEDIO);
            ps.setInt(1, r.getIdHospital());
            ps.setInt(2, r.getIdMedico());
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
            RankeoMedicos r = new RankeoMedicos();
            //m.setIdMedicos(rs.getInt("idespecialista"));
            r.setIdRankingMedicos(rs.getInt("idrankingmedico"));
            r.setComentario(rs.getString("comentarios"));
            r.setRankeo(rs.getInt("estrellas"));
            r.setIdHospital(rs.getInt("idhospital"));
            r.setIdUsuario(rs.getInt("idusuario"));
            r.setIdMedico(rs.getInt("idmedico"));
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