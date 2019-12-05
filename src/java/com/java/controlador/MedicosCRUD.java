package com.java.controlador;

import com.java.bean.modelo.Medicos;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicosCRUD{
    
    private static final String SQL_INSERT
        = "insert into medicos (nombre, appaterno, apmaterno, telefono, especialidad, "
            + "historial, email, foto, rankeo, cedula, idhospital) "
            + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    private static final String SQL_Verificar
        = "select count(*) from medicos where cedula like ?";
    
    private static final String SQL_UPDATE
        = "update medicos set nombre = ?, appaterno = ?, apmaterno = ?, telefono = ?, especialidad = ?, "
            + "historial = ?, email = ?, foto = ?, rankeo = ?, cedula = ? where  idhospital = ? and idmedico = ?";
    
    private static final String SQL_DELETE
        = "delete from medicos where idmedico = ?";

    private static final String SQL_SELECT_ALL
        = "select idmedico, nombre, appaterno, apmaterno, telefono, especialidad, "
            + "historial, email, foto, rankeo, cedula, idhospital from medicos where idhospital = ?";
    
    private static final String SQL_SELECT_POR_NOMBRE
        = "select idmedico, nombre, appaterno, apmaterno, telefono, especialidad, "
            + "historial, email, foto, rankeo, cedula, idhospital from medicos where nombre like ?";
    
    private static final String SQL_SELECT_ONE
        = "select nombre, appaterno, apmaterno, telefono, especialidad, "
            + "historial, email, foto, rankeo, cedula, idhospital from medicos"
            + "where cedula = ?";
    
    private static final String SQL_SELECT
        = "select idmedico, nombre, appaterno, apmaterno, telefono, especialidad, "
            + "historial, email, foto, rankeo, cedula, idhospital from medicos where idmedico = ?";
    
    private static final String SELECT_MEDICOS
        = "select idmedico, nombre, appaterno, apmaterno, telefono, especialidad, "
            + "historial, email, foto, rankeo, cedula, idhospital from medicos";
    
    private static final String SQL_SELECT_DISTINCT
        = "select idmedico, nombre, appaterno, apmaterno, telefono, especialidad, "
            + "historial, email, foto, rankeo, cedula, idhospital from medicos where especialidad like ?";
    
    private static final String SQL_SERVICIO_MEDICO
        = "select idmedico, nombre, appaterno, apmaterno, telefono, especialidad, "
            + "historial, email, foto, rankeo, cedula, idhospital from medicos where especialidad like ? and idhospital = ?";
    
    //Variable para conectar la base de datos con java
    Connection con = null;
    
    public void crear(Medicos m) throws SQLException {
        //Se crea la variable que ejecutará la sentencia SQL
        PreparedStatement ps = null;
        
        //Obtencion de la conexion en la base de datos
        ObtenerConexion();
        try {
            //Insercion de datos
            ps = con.prepareStatement(SQL_INSERT);
            //ps.setInt(1, a.getIdUsuario());
            ps.setString(1, m.getNombreMedico());
            ps.setString(2, m.getAPaternoMedico());
            ps.setString(3, m.getAMaternoMedico());
            ps.setString(4, m.getTelefonos());
            ps.setString(5, m.getEspecialidades());
            ps.setString(6, m.getHistorial());
            ps.setString(7, m.getEmail());
            ps.setString(8, m.getFoto());
            ps.setInt(9, m.getRankeo());
            ps.setString(10, m.getCedulap());
            ps.setInt(11, m.getIdHospital());
            ps.executeUpdate();
        } finally {
            cerrar(ps);
            cerrar(con);
        }
    }

    public void update(Medicos m) throws SQLException {
        //Se crea la variable que ejecutará la sentencia SQL
        PreparedStatement ps = null;
        
        //Obtencion de la conexion en la base de datos
        ObtenerConexion();
        try {
            //Insercion de datos
            ps = con.prepareStatement(SQL_UPDATE);
            //ps.setInt(1, a.getIdUsuario());
            ps.setString(1, m.getNombreMedico());
            ps.setString(2, m.getAPaternoMedico());
            ps.setString(3, m.getAMaternoMedico());
            ps.setString(4, m.getTelefonos());
            ps.setString(5, m.getEspecialidades());
            ps.setString(6, m.getHistorial());
            ps.setString(7, m.getEmail());
            ps.setString(8, m.getFoto());
            ps.setInt(9, m.getRankeo());
            ps.setString(10, m.getCedulap());
            ps.setInt(11, m.getIdHospital());
            ps.setInt(12, m.getIdMedicos());
            ps.executeUpdate();
        } finally {
            cerrar(ps);
            cerrar(con);
        }
    }
    
    //Método para eliminar al Administrador
    public void eliminar(Medicos a) throws SQLException {
        PreparedStatement ps = null;
        ObtenerConexion();
        try {
            ps = con.prepareStatement(SQL_DELETE);
            ps.setInt(1, a.getIdMedicos());
            ps.executeUpdate();
        } finally {
            cerrar(ps);
            cerrar(con);
        }
    }

    public int repetidos(Medicos m) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        int total = 0;
        ObtenerConexion();
        try {
            ps = con.prepareStatement(SQL_Verificar);
            ps.setString(1, "%"+m.getCedulap()+"%");
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
    
    public List read(Medicos m) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ObtenerConexion();
        try {
            ps = con.prepareStatement(SQL_SELECT_ONE);
            ps.setString(1, m.getCedulap());
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
    
    public List medicos() throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ObtenerConexion();
        try {
            ps = con.prepareStatement(SELECT_MEDICOS);
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
    
    public List LeerMedicos(Medicos m) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ObtenerConexion();
        try {
            ps = con.prepareStatement(SQL_SELECT_POR_NOMBRE);
            ps.setString(1, "%" +m.getNombreMedico()+"%");
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
    
    public List readAll(Medicos m) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ObtenerConexion();
        try {
            ps = con.prepareStatement(SQL_SELECT_ALL);
            ps.setInt(1, m.getIdHospital());
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
    
    
    public List Hospitales_especialidad(Medicos m) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ObtenerConexion();
        try {
            ps = con.prepareStatement(SQL_SELECT_DISTINCT);
            ps.setString(1, "%"+m.getEspecialidades() +"%");
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
    
    public List readMedicos(Medicos m) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ObtenerConexion();
        try {
            ps = con.prepareStatement(SQL_SELECT);
            ps.setInt(1, m.getIdMedicos());
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

    public Medicos ServiciosMedicos(Medicos m) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ObtenerConexion();
        try {
            ps = con.prepareStatement(SQL_SERVICIO_MEDICO);
            ps.setString(1, "%" + m.getEspecialidades() + "%");
            ps.setInt(2, m.getIdHospital());
            rs = ps.executeQuery();
            List resultados = obtenerResultados(rs);
            if (resultados.size() > 0) {
                return (Medicos)resultados.get(0);
            } else {
                return null;
            }
        } finally {
            cerrar(rs);
            cerrar(ps);
            cerrar(con);
        }
    }
    
    private List obtenerResultados(ResultSet rs) throws SQLException {
        List resultados = new ArrayList();
        while (rs.next()) {
            Medicos m = new Medicos();
            m.setIdMedicos(rs.getInt("idmedico"));
            m.setNombreMedico(rs.getString("nombre"));
            m.setAPaternoMedico(rs.getString("appaterno"));
            m.setAMaternoMedico(rs.getString("apmaterno"));
            m.setTelefonos(rs.getString("telefono"));
            m.setEspecialidades(rs.getString("especialidad"));
            m.setHistorial(rs.getString("historial"));
            m.setEmail(rs.getString("email"));
            m.setFoto(rs.getString("foto"));
            m.setRankeo(rs.getInt("rankeo"));
            m.setCedulap(rs.getString("cedula"));
            m.setIdHospital(rs.getInt("idhospital"));
            resultados.add(m);
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

