package com.java.controlador;

import com.java.bean.modelo.Administrador;
import com.java.bean.modelo.Usuario;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ActualizarDatosAdministrador", urlPatterns = {"/ActualizarDatosAdministrador"})
public class ActualizarDatosAdministrador extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //Especifica el tipo de recurso, compatibilidad con el navegador(Codificación de caracteres)
        response.setContentType("text/html;charset=UTF-8");
        
        //Especifa la sintáxis utilizada
        request.setCharacterEncoding("UTF-8");
        
        //Solicitudes de información para actualizar 
        String Nombre_Administrador = request.getParameter("txtNombreAdministrador");
        String Apellido_Materno = request.getParameter("txtApellidoMaterno");
        String Apellido_Paterno = request.getParameter("txtApellidoPaterno");
        String Telefono = request.getParameter("txtTelefono");
        String Usuarioo = request.getParameter("txtUsuario");
        String Contrasena = request.getParameter("txtContrasena");
        String foto;
        String sexo = request.getParameter("txtSexo");
        
        if("Hombre".equals(sexo)){
            foto = "https://www.ge2.co/wp-content/uploads/2018/05/administrador.png";
        }else{
            foto = "https://i1.wp.com/www.ramter.cl/wp-content/uploads/2017/05/woman.png?fit=512%2C512&ssl=1";
        }
        
        /*
            Identificadores para saber el administrador y el
            hospital a gestionar.
        */
        
        int idusuario = Integer.parseInt(request.getParameter("idusuario"));
        int idhospital = Integer.parseInt(request.getParameter("idhospital"));
        
        
        //Evitar Scripting
        if(Nombre_Administrador.contains("<") || Nombre_Administrador.contains(">") || 
                Apellido_Materno.contains("<") || Apellido_Materno.contains(">") ||
                Apellido_Paterno.contains("<") || Apellido_Paterno.contains(">") || 
                Telefono.contains(">") || Telefono.contains("<") || Usuarioo.contains(">") || 
                Usuarioo.contains("<") || Contrasena.contains(">") ||  Contrasena.contains("<")){
            response.sendRedirect("/Prototipo4_1/Paginas/ActualizarDatosAdminsitrador.jsp?idusuario="+idusuario+ "&idhospital="+idhospital);
        }else{
        
        
            //Asignación de valor
            Usuario u = new Usuario();
            u.setUsuario(Usuarioo);
            u.setContrasena(Contrasena);

            //Insercion de datos Administrador
            Administrador a = new Administrador();
            a.setNombreAdministrador(Nombre_Administrador);
            a.setApellidoMaterno(Apellido_Materno);
            a.setApellidoPaterno(Apellido_Paterno);
            a.setFoto(foto);
            a.setTelefono(Telefono);
            a.setIdUsuario(idusuario);
            a.setIdHospital(idhospital);
            a.setSexo(sexo);
            //Objetos con métodos CRUD
            UsuarioDAO dao = new UsuarioDAO();
            AdministradorDAO dao2 = new AdministradorDAO();

            try {
                //Se actualizan los datos
                try {
                    dao.actualizar(u);
                    dao2.actualizar(a);

                    final String claveEncriptacion = "sec";            
                    String datosOriginales = idusuario+"";            
                    EncriptadorAES encriptador = new EncriptadorAES();
                    String encriptado = encriptador.encriptar(datosOriginales, claveEncriptacion);
                    /*
                        Una vez que se actualizan los datos nos manda a la página del 
                        perfil del administrador
                    */
                
                    response.sendRedirect("/Prototipo4_1/Paginas/PerfilAdmon.jsp?id=" +encriptado+"#Configuracion");

            } catch (UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException ex) {
                Logger.getLogger(EncriptadorAES.class.getName()).log(Level.SEVERE, null, ex);
            }} catch (SQLException ex) {
                //Registro de error en la clase 
                Logger.getLogger(ActualizarDatosAdministrador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
