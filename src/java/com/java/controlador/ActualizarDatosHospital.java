package com.java.controlador;

import com.java.bean.modelo.Hospitales;
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

@WebServlet(name = "ActualizarDatosHospital", urlPatterns = {"/ActualizarDatosHospital"})
public class ActualizarDatosHospital extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        //Especifica el tipo de recurso, compatibilidad con el navegador(Codificación de caracteres)
        response.setContentType("text/html;charset=UTF-8");
        
        //Especifica la sintáxis utilizada
        request.setCharacterEncoding("UTF-8");
        
        //Solicitudes de información para actualizar 
        String nombre = request.getParameter("txtNombre");
        String direccion = request.getParameter("txtDireccion");
        String telefono = request.getParameter("txtTelefono");
        String pagina = request.getParameter("txtPagina");
        String foto = "https://previews.123rf.com/images/neyro2008/neyro20081512/neyro2008151200317/49781838-ilustraci%C3%B3n-de-la-ciudad-del-edificio-del-hospital-en-el-estilo-de-dise%C3%B1o-plano-arquitectura-cl%C3%ADnica-hospita.jpg";
        
        /*
            Identificadores para saber el administrador y el
            hospital a gestionar.
        */
        
        int idusuario = Integer.parseInt(request.getParameter("idusuario"));
        int idhospital = Integer.parseInt(request.getParameter("idhospital"));

        //Evitar Scripting
        if(nombre.contains("<") || nombre.contains(">") || 
                direccion.contains("<") || direccion.contains(">") ||
                telefono.contains("<") || telefono.contains(">") || 
                pagina.contains("<") || pagina.contains(">")){
            response.sendRedirect("/Prototipo4_1/Paginas/ActualizarDatosHospital.jsp?idusuario="+idusuario+ "&idhospital="+idhospital);
        }else{
            //Se crea objeto hospital para insertar valores
            Hospitales h = new Hospitales();
            HospitalCRUD crud = new HospitalCRUD();
            h.setNombre(nombre);
            h.setDireccion(direccion);
            h.setPagina(pagina);
            h.setFoto(foto);
            h.setRankeo(5);
            h.setTelefono(telefono);
            h.setIdHospital(idhospital);
            h.setVerificar(1);
            //Actualización de datos
            crud.actualizar(h);
            try {
                final String claveEncriptacion = "sec";            
                String datosOriginales = idusuario+"";            
                EncriptadorAES encriptador = new EncriptadorAES();
                String encriptado = encriptador.encriptar(datosOriginales, claveEncriptacion);
                 //Como respuesta, nos manda a la página del perfil del administrador
                response.sendRedirect("/Prototipo4_1/Paginas/PerfilAdmon.jsp?id=" + encriptado+"#Inicio");
            
                } catch (UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException ex) {
                    Logger.getLogger(EncriptadorAES.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ActualizarDatosHospital.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ActualizarDatosHospital.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
