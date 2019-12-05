package com.java.controlador;

import com.java.bean.modelo.Administrador;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdministradorDAO extends UsuarioDAO{
    
    // Sentencia SQL para insertar datos del Administrador en la Base de Datos
    private static final String SQL_INSERT
        = "insert into Administrador (nombre, paterno, materno, telefono, foto, idusuario, idhospital, sexo) "
        + "values (?, ?, ?, ?, ?, ?, ?, ?)";
    
    //Sentencia SQL para eliminar Administrador de la Base de Datos
    private static final String SQL_DELETE
        = "delete from administrador where idusuario = ?";

    //Sentencia SQL para eliminar Administrador de la Base de Datos
    private static final String SQL_UPDATE
        = "update administrador set nombre = ?, paterno = ?, materno = ?, "
            + "telefono = ?, foto = ?, idhospital = ?, sexo = ? where idusuario = ?";

    //Sentencia SQL para seleccionar Administrador de la Base de Datos
    private static final String SQL_SELECT
        = "select idadmon, nombre, paterno, materno, telefono, foto, idusuario, idhospital, sexo "
            + "from administrador where idusuario = ?";

    private static final String SQL_SELECT_POR_HOSPITAL
        = "select idadmon, nombre, paterno, materno, telefono, foto, idusuario, idhospital, sexo "
            + "from administrador where idhospital = ?";

    //Variable para conectar la base de datos con java
    Connection con = null;
    
    //Método para insertar nuevos datos del administrador
    public void crear(Administrador a) throws SQLException {
        //Se crea la variable que ejecutará la sentencia SQL
        PreparedStatement ps = null;
        //Obtencion de la conexion en la base de datos
        ObtenerConexion();
        try {
            //Insercion de datos
            ps = con.prepareStatement(SQL_INSERT);
            //ps.setInt(1, a.getIdUsuario());
            ps.setString(1, a.getNombreAdministrador());
            ps.setString(2, a.getApellidoPaterno());
            ps.setString(3, a.getApellidoMaterno());
            ps.setString(4, a.getTelefono());
            ps.setString(5, a.getFoto());
            ps.setInt(6, a.getIdUsuario());
            ps.setInt(7, a.getIdHospital());
            ps.setString(8, a.getSexo());
            ps.executeUpdate();
        } finally {
            //Se cierran recursos
            cerrar(ps);
            cerrar(con);
        }
    }

    //Método para actualizar datos del Administrador
    public void actualizar(Administrador a) throws SQLException {
        //Se crea la variable que ejecutará la sentencia SQL
        PreparedStatement ps = null;
        //Obtencion de la conexion en la base de datos
        ObtenerConexion();
        try {
            //Insercion de datos
            ps = con.prepareStatement(SQL_UPDATE);
            ps.setString(1, a.getNombreAdministrador());
            ps.setString(2, a.getApellidoPaterno());
            ps.setString(3, a.getApellidoMaterno());
            ps.setString(4, a.getTelefono());
            ps.setString(5, a.getFoto());
            ps.setInt(6, a.getIdHospital());
            ps.setString(7, a.getSexo());
            ps.setInt(8, a.getIdUsuario());
            ps.executeUpdate();
        } finally {
            //Se cierran recursos
            cerrar(ps);
            cerrar(con);
        }
    }

    //Método para seleccionar datos del Administrador
    public Administrador leer_por_hospital(Administrador a) throws SQLException {
        PreparedStatement ps = null;
        //Contiene los resutados de la consulta SQL
        ResultSet rs = null;
        //Se obtiene la conexión con la base de datos
        ObtenerConexion();
        try {
            ps = con.prepareStatement(SQL_SELECT_POR_HOSPITAL);
            ps.setInt(1, a.getIdHospital());
            rs = ps.executeQuery();
            //Se guardan datos en una lista
            List resultados = obtenerResultados(rs);
            //Si tiene datos la lista se devuelve
            if (resultados.size() > 0) {
                //Se hace casting de tipo Administrador
                return (Administrador) resultados.get(0);
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
    public Administrador leer(Administrador a) throws SQLException {
        PreparedStatement ps = null;
        //Contiene los resutados de la consulta SQL
        ResultSet rs = null;
        //Se obtiene la conexión con la base de datos
        ObtenerConexion();
        try {
            ps = con.prepareStatement(SQL_SELECT);
            ps.setInt(1, a.getIdUsuario());
            rs = ps.executeQuery();
            //Se guardan datos en una lista
            List resultados = obtenerResultados(rs);
            //Si tiene datos la lista se devuelve
            if (resultados.size() > 0) {
                //Se hace casting de tipo Administrador
                return (Administrador) resultados.get(0);
            } else { //De lo contrario manda nulo
                return null;
            }
        } finally {
            cerrar(ps);
            cerrar(rs);
            cerrar(con);
        }
    }

    //Método para saber si un Administrador existe
    public boolean seleccionar(int id) throws SQLException{
        PreparedStatement ps = null;
        ResultSet rs = null;
        //Se obtine la conexión
        ObtenerConexion();
        try {
            ps = con.prepareStatement(SQL_SELECT);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            //Si existe manda 1, de lo contrario manda 0
            return rs.next();
        } finally {
            //Se cierran recursos
            cerrar(ps);
            cerrar(con);
        }
    }
    
    
    //Método para eliminar al Administrador
    public void eliminar(Administrador a) throws SQLException {
        PreparedStatement ps = null;
        ObtenerConexion();
        try {
            ps = con.prepareStatement(SQL_DELETE);
            ps.setInt(1, a.getIdUsuario());
            ps.executeUpdate();
        } finally {
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

    private List obtenerResultados(ResultSet rs) throws SQLException {
        List resultados = new ArrayList();
        while (rs.next()) {
            Administrador a = new Administrador();
            a.setIdAdmon(rs.getInt("idadmon"));
            a.setNombreAdministrador(rs.getString("nombre"));
            a.setApellidoPaterno(rs.getString("paterno"));
            a.setApellidoMaterno(rs.getString("materno"));
            a.setTelefono(rs.getString("telefono"));
            a.setFoto(rs.getString("foto"));
            a.setIdUsuario(rs.getInt("idusuario"));
            a.setIdHospital(rs.getInt("idhospital"));
            a.setSexo(rs.getString("sexo"));
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

