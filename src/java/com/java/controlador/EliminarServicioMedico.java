package com.java.controlador;

import com.java.bean.modelo.ServiciosMedicos;
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

@WebServlet(name = "EliminarServicioMedico", urlPatterns = {"/EliminarServicioMedico"})
public class EliminarServicioMedico extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        ServiciosMedicos m = new ServiciosMedicos();
        //Solicitud de datos identificador del usuario y del servicio médico
        int idusuario = Integer.parseInt(request.getParameter("idusuario"));
        m.setIdServicio(Integer.parseInt(request.getParameter("idservicio")));
        ServiciosMedicosCRUD dao = new ServiciosMedicosCRUD();
        try {
            //Eliminación del servicio médico
            dao.eliminar(m);
        
            final String claveEncriptacion = "sec";            
            String datosOriginales = idusuario+"";            
            EncriptadorAES encriptador = new EncriptadorAES();
            String encriptado = encriptador.encriptar(datosOriginales, claveEncriptacion);
            //Una vez que se elimina va a la pagina de inicio
        
            response.sendRedirect("/Prototipo4_1/Paginas/PerfilAdmon.jsp?id=" + encriptado+"#Servicios");

        } catch (UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(EncriptadorAES.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(EliminarMedico.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(EliminarServicioMedico.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(EliminarServicioMedico.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
