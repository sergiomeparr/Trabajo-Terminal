package com.java.controlador;

import com.java.bean.modelo.Medicos;
import com.java.bean.modelo.ServiciosMedicos;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiciosMedicosCRUD {
    private static final String SQL_INSERT
        = "insert into servicios_medicos (tipo, idhospital, precio) "
        + "values (?, ?, ?)";
    
    private static final String SQL_UPDATE
        = "update servicios_medicos set tipo = ?, precio = ? where idservicio = ? ";
    
    private static final String SQL_DELETE
        = "delete from servicios_medicos where idservicio = ?";

    private static final String SQL_DELETE_POR_HOSPITAL
        = "delete from servicios_medicos where idhospital = ?";

    private static final String SQL_SELECT_ALL
        = "select idservicio, tipo, idhospital, precio from servicios_medicos where idhospital = ?";
    
    private static final String SQL_SELECT_ONE
        = "select idservicio, tipo, idhospital, precio from servicios_medicos where idservicio = ?";
    
    private static final String SQL_TIPO
        = "select idservicio, tipo, idhospital,precio from servicios_medicos where tipo like ?";
    
    private static final String SQL_SERVICIO
        = "select idservicio, tipo, idhospital, precio from servicios_medicos where tipo like ? and idservicio = ?";
        
    private static final String SQL_SERVICIO1
        = "select count(*)  from servicios_medicos where tipo like ? and idhospital = ?";
    
    
    //Variable para conectar la base de datos con java
    Connection con = null;
    
    public void crear(ServiciosMedicos sm) throws SQLException {
        //Se crea la variable que ejecutará la sentencia SQL
        PreparedStatement ps = null;
        
        //Obtencion de la conexion en la base de datos
        ObtenerConexion();
        try {
            //Insercion de datos
            ps = con.prepareStatement(SQL_INSERT);
            //ps.setInt(1, a.getIdUsuario());
            ps.setString(1, sm.getTipo());
            ps.setInt(2, sm.getIdHospital());
            ps.setString(3, sm.getPrecio());
            
            ps.executeUpdate();
        } finally {
            cerrar(ps);
            cerrar(con);
        }
    }

    
    public void update(ServiciosMedicos sm) throws SQLException {
        //Se crea la variable que ejecutará la sentencia SQL
        PreparedStatement ps = null;
        
        //Obtencion de la conexion en la base de datos
        ObtenerConexion();
        try {
            //Insercion de datos
            ps = con.prepareStatement(SQL_UPDATE);
            //ps.setInt(1, a.getIdUsuario());
            ps.setString(1, sm.getTipo());
            ps.setString(2, sm.getPrecio());
            ps.setInt(3, sm.getIdServicio());
            ps.executeUpdate();
        } finally {
            cerrar(ps);
            cerrar(con);
        }
    }

    //Método para eliminar al Administrador
    public void eliminar(ServiciosMedicos sm) throws SQLException {
        PreparedStatement ps = null;
        ObtenerConexion();
        try {
            ps = con.prepareStatement(SQL_DELETE);
            ps.setInt(1, sm.getIdServicio());
            ps.executeUpdate();
        } finally {
            cerrar(ps);
            cerrar(con);
        }
    }

    public List read(ServiciosMedicos sm) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ObtenerConexion();
        try {
            ps = con.prepareStatement(SQL_SELECT_ONE);
            ps.setInt(1, sm.getIdServicio());
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
    
    public int repetidos1(ServiciosMedicos sm) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        int total = 0;
        ObtenerConexion();
        try {
            ps = con.prepareStatement(SQL_SERVICIO1);
            ps.setString(1, "%"+sm.getTipo()+"%");
            ps.setInt(2, sm.getIdHospital());
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
    
    /*
    public ServiciosMedicos seleccionar_servicio(ServiciosMedicos sm) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ObtenerConexion();
        try {
            ps = con.prepareStatement(SQL_SERVICIO);
            //ps.setString(3, sm.getPrecio());
            
            rs = ps.executeQuery();
            List resultados = obtenerResultados(rs);
            if (resultados.size() > 0) {
                return (ServiciosMedicos) resultados.get(0);
            } else {
                return null;
            }
        } finally {
            cerrar(rs);
            cerrar(ps);
            cerrar(con);
        }
    }*/
    
    public List<ServiciosMedicos> repetidos(ServiciosMedicos m) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        int total = 0;
        ObtenerConexion();
        try {
            ps = con.prepareStatement(SQL_SERVICIO1);
            ps.setString(1, "%"+m.getTipo()+"%");
            ps.setInt(2, m.getIdHospital());
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
    
    public List seleccionar_tipo_servicio(ServiciosMedicos sm) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ObtenerConexion();
        try {
            ps = con.prepareStatement(SQL_TIPO);
            ps.setString(1, "%"+sm.getTipo()+"%");
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
    
    public List readAll(ServiciosMedicos sm) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ObtenerConexion();
        try {
            ps = con.prepareStatement(SQL_SELECT_ALL);
            ps.setInt(1, sm.getIdHospital());
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
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private List obtenerResultados(ResultSet rs) throws SQLException {
        List resultados = new ArrayList();
        while (rs.next()) {
            ServiciosMedicos sm = new ServiciosMedicos();
            sm.setIdServicio(rs.getInt("idservicio"));
            sm.setTipo(rs.getString("tipo"));
            sm.setIdHospital(rs.getInt("idhospital"));
            sm.setPrecio(rs.getString("precio"));
            resultados.add(sm);
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
