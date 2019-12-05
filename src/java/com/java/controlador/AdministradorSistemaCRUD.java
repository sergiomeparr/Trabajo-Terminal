package com.java.controlador;

import com.java.bean.modelo.Administrador;
import com.java.bean.modelo.AdministradorSistema;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdministradorSistemaCRUD extends UsuarioDAO{
    
    //Sentencia para consultar 
    private static final String SQL_SELECT
        = "select idadmon, llaveadmon, id_usuario from administradorsistema where llaveadmon = ?";

    //Variable para conectar la base de datos con java
    Connection con = null;
   
    //Método para seleccionar datos del Administrador
    public AdministradorSistema leer(AdministradorSistema a) throws SQLException {
        PreparedStatement ps = null;
        //Contiene los resutados de la consulta SQL
        ResultSet rs = null;
        //Se obtiene la conexión con la base de datos
        ObtenerConexion();
        try {
            ps = con.prepareStatement(SQL_SELECT);
            ps.setString(1, a.getLlaveAdmon());
            rs = ps.executeQuery();
            //Se guardan datos en una lista
            List resultados = obtenerResultados(rs);
            //Si tiene datos la lista se devuelve
            if (resultados.size() > 0) {
                //Se hace casting de tipo Administrador
                return (AdministradorSistema) resultados.get(0);
            } else { //De lo contrario manda nulo
                return null;
            }
        } finally {
            cerrar(ps);
            cerrar(rs);
            cerrar(con);
        }
    }

    //Método para seleccionar datos del Administrador
    public boolean existe(AdministradorSistema a) throws SQLException {
        PreparedStatement ps = null;
        //Contiene los resutados de la consulta SQL
        ResultSet rs = null;
        //Se obtiene la conexión con la base de datos
        ObtenerConexion();
        try {
            ps = con.prepareStatement(SQL_SELECT);
            ps.setString(1, a.getLlaveAdmon());
            rs = ps.executeQuery();
            return rs.next();
        } finally {
            cerrar(rs);
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

    private List obtenerResultados(ResultSet rs) throws SQLException {
        List resultados = new ArrayList();
        while (rs.next()) {
            AdministradorSistema a = new AdministradorSistema();
            a.setIdAdmonSistema(rs.getInt("idadmon"));
            a.setLlaveAdmon(rs.getString("llaveadmon"));
            a.setIdUsuario(rs.getInt("id_usuario"));
            resultados.add(a);
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

