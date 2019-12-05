package com.java.controlador;

import com.java.bean.modelo.Hospitales;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class HospitalCRUD{
    
    //Sentencia SQL para actualizar datos del hospital
    private static final String SQL_UPDATE
        = "update hospital set nombre = ?, direccion = ?, foto = ?, telefono = ?, pagina = ?,"
            + "rankeo = ?, verificado = ? where idhospital = ?";
    
    private static final String SQL_APROBAR_HOSPITAL
        = "update hospital set verificado = ? where idhospital = ?";
    
    //Sentencia SQL para insertar datos del hospital
    private static final String SQL_INSERT
        = "insert into hospital (nombre, direccion, foto, telefono, pagina, rankeo, verificado)"
        + "values(?, ?, ?, ?, ?, ?, ?)";
    
    //Sentencia SQL para seleccionar datos del hospital por nombre
    private static final String SQL_SELECT
        = "select idhospital, nombre, direccion, foto, telefono, pagina, rankeo, verificado"
        + " from hospital where nombre like ? and verificado = 1";
    
    //Sentencia SQL para seleccionar datos del hospital que todavia no sean verificados
    private static final String SQL_SELECT_NO_VERIFICADOS
        = "select idhospital, nombre, direccion, foto, telefono, pagina, rankeo, verificado"
        + " from hospital where verificado = 0";
    
    //Sentencia SQL para seleccionar datos del hospital que todavia no sean verificados
    private static final String SQL_SELECT_UN_HOSPITAL_NO_VERIFICADO
        = "select idhospital, nombre, direccion, foto, telefono, pagina, rankeo, verificado"
        + " from hospital where verificado = 0 and idhospital = ?";
    
    //Sentencia SQL para seleccionar hospital por su id
    private static final String SQL_SELECT_ONE
        = "select idhospital, nombre, direccion, foto, telefono, pagina, rankeo, verificado"
        + " from hospital where idhospital = ?";
    
    //Sentencia SQL para seleccionar hospital por su id
    private static final String SQL_SELECT_ONE1
        = "select idhospital, nombre, direccion, foto, telefono, pagina, rankeo, verificado"
        + " from hospital where idhospital = ? and verificado = 1";
    
    //Sentencia SQL para seleccionar hospital por su id
    private static final String SQL_SELECT_ONE_NO
        = "select idhospital, nombre, direccion, foto, telefono, pagina, rankeo, verificado"
        + " from hospital where idhospital = ? and verificado = 0";
    
    //Sentencia SQL para seleccionar todos los datos de los hospitales
    private static final String SQL_SELECT_ALL
        = "select idhospital, nombre, direccion, foto, telefono, pagina, rankeo, verificado"
        + " from hospital";
    
    //Sentencia SQL para seleccionar todos los datos de los hospitales
    private static final String SQL_SELECT_ALL1
        = "select idhospital, nombre, direccion, foto, telefono, pagina, rankeo, verificado"
        + " from hospital where verificado = 1";
    
    //Sentencia SQL para seleccionar por dirección
    private static final String SQL_SELECT_DIRECCION
        = "select idhospital, nombre, direccion, foto, telefono, pagina, rankeo, verificado"
        + " from hospital where direccion like ? and verificado = 1";
    
    private static final String SQL_DELETE
        = "delete from medicos where idmedico = ?";

    
    Connection con = null;
    
    public void actualizar(Hospitales h) throws SQLException {
        //Se crea la variable que ejecutará la sentencia SQL
        PreparedStatement ps = null;
        
        //Obtencion de la conexion en la base de datos
        ObtenerConexion();
        try {
            //Insercion de datos
            ps = con.prepareStatement(SQL_UPDATE);
            ps.setString(1, h.getNombre());
            ps.setString(2, h.getDireccion());
            ps.setString(3, h.getFoto());
            ps.setString(4, h.getTelefono());
            ps.setString(5, h.getPagina());
            ps.setInt(6, h.getRankeo());
            ps.setInt(7, h.getVerificar());
            ps.setInt(8, h.getIdHospital());
            ps.executeUpdate();
        } finally {
            //Se cierran recursos
            cerrar(ps);
            cerrar(con);
        }
    }
    
    public void Aprobar_Hospital(Hospitales h) throws SQLException {
        //Se crea la variable que ejecutará la sentencia SQL
        PreparedStatement ps = null;
        
        //Obtencion de la conexion en la base de datos
        ObtenerConexion();
        try {
            //Insercion de datos
            ps = con.prepareStatement(SQL_APROBAR_HOSPITAL);
            ps.setInt(1, h.getVerificar());
            ps.setInt(2, h.getIdHospital());
            ps.executeUpdate();
        } finally {
            //Se cierran recursos
            cerrar(ps);
            cerrar(con);
        }
    }
    
    //Método para eliminar al Hospital
    public void eliminar(Hospitales h) throws SQLException {
        PreparedStatement ps = null;
        ObtenerConexion();
        try {
            ps = con.prepareStatement(SQL_DELETE);
            ps.setInt(1, h.getIdHospital());
            ps.executeUpdate();
        } finally {
            cerrar(ps);
            cerrar(con);
        }
    }


    //Método para insertar nuevos datos de hospitales
    public void crear(Hospitales h) throws SQLException {
        //Se crea la variable que ejecutará la sentencia SQL
        PreparedStatement ps = null;
        
        //Obtencion de la conexion en la base de datos
        ObtenerConexion();
        try {
            //Insercion de datos
            ps = con.prepareStatement(SQL_INSERT);
            ps.setString(1, h.getNombre());
            ps.setString(2, h.getDireccion());
            ps.setString(3, h.getFoto());
            ps.setString(4, h.getTelefono());
            ps.setString(5, h.getPagina());
            ps.setInt(6, h.getRankeo());
            ps.setInt(7, h.getVerificar());
            ps.executeUpdate();
        } finally {
            //Se cierran recursos
            cerrar(ps);
            cerrar(con);
        }
    }

    //Método para seleccionar todos los datos del hospital
    public List readAll() throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        //Se obtiene la conexión
        ObtenerConexion();
        try {
            ps = con.prepareStatement(SQL_SELECT_ALL);
            rs = ps.executeQuery();
            //Se gusrdan los datos en una lista
            List resultados = obtenerResultados(rs);
            //Se regresa el valor en forma de lista
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
    
    //Método para seleccionar todos los datos del hospital
    public List readAll1() throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        //Se obtiene la conexión
        ObtenerConexion();
        try {
            ps = con.prepareStatement(SQL_SELECT_ALL1);
            rs = ps.executeQuery();
            //Se gusrdan los datos en una lista
            List resultados = obtenerResultados(rs);
            //Se regresa el valor en forma de lista
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
    
    
    //Método para seleccionar todos los datos del hospital
    public List Leer_NoVerificados() throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        //Se obtiene la conexión
        ObtenerConexion();
        try {
            ps = con.prepareStatement(SQL_SELECT_NO_VERIFICADOS);
            rs = ps.executeQuery();
            //Se gusrdan los datos en una lista
            List resultados = obtenerResultados(rs);
            //Se regresa el valor en forma de lista
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
    
    
    public List readbyaddress(Hospitales h) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ObtenerConexion();
        try {
            ps = con.prepareStatement(SQL_SELECT_DIRECCION);
            ps.setString(1, "%" + h.getDireccion()+ "%");
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
    
    public List readbyname(Hospitales h) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ObtenerConexion();
        try {
            ps = con.prepareStatement(SQL_SELECT);
            ps.setString(1, "%" + h.getNombre()+ "%");
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
    
    
    public Hospitales readOne(Hospitales h) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ObtenerConexion();
        try {
            ps = con.prepareStatement(SQL_SELECT_ONE);
            ps.setInt(1, h.getIdHospital());
            rs = ps.executeQuery();
            List resultados = obtenerResultados(rs);
            if (resultados.size() > 0) {
                return (Hospitales)resultados.get(0);
            } else {
                return null;
            }
        } finally {
            cerrar(rs);
            cerrar(ps);
            cerrar(con);
        }
    }
    
    public Hospitales readOne1(Hospitales h) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ObtenerConexion();
        try {
            ps = con.prepareStatement(SQL_SELECT_ONE1);
            ps.setInt(1, h.getIdHospital());
            rs = ps.executeQuery();
            List resultados = obtenerResultados(rs);
            if (resultados.size() > 0) {
                return (Hospitales)resultados.get(0);
            } else {
                return null;
            }
        } finally {
            cerrar(rs);
            cerrar(ps);
            cerrar(con);
        }
    }
    
    
     public Hospitales readOneNO(Hospitales h) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ObtenerConexion();
        try {
            ps = con.prepareStatement(SQL_SELECT_ONE_NO);
            ps.setInt(1, h.getIdHospital());
            rs = ps.executeQuery();
            List resultados = obtenerResultados(rs);
            if (resultados.size() > 0) {
                return (Hospitales)resultados.get(0);
            } else {
                return null;
            }
        } finally {
            cerrar(rs);
            cerrar(ps);
            cerrar(con);
        }
    }
    
    public Hospitales LeerHospital_NoVerificado(Hospitales h) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ObtenerConexion();
        try {
            ps = con.prepareStatement(SQL_SELECT_UN_HOSPITAL_NO_VERIFICADO);
            ps.setInt(1, h.getIdHospital());
            rs = ps.executeQuery();
            List resultados = obtenerResultados(rs);
            if (resultados.size() > 0) {
                return (Hospitales)resultados.get(0);
            } else {
                return null;
            }
        } finally {
            cerrar(rs);
            cerrar(ps);
            cerrar(con);
        }
    }
    
    private List<Hospitales> obtenerResultados(ResultSet rs) throws SQLException {
        List<Hospitales> resultados = new ArrayList();
        while (rs.next()) {
            Hospitales h = new Hospitales();
            h.setIdHospital(rs.getInt("idhospital"));
            h.setNombre(rs.getString("nombre"));
            h.setDireccion(rs.getString("direccion"));
            h.setFoto(rs.getString("foto"));
            h.setTelefono(rs.getString("telefono"));
            h.setPagina(rs.getString("pagina"));
            h.setRankeo(rs.getInt("rankeo"));
            h.setVerificar(rs.getInt("verificado"));
            resultados.add(h);
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


