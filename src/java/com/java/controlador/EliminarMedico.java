package com.java.controlador;

import com.java.bean.modelo.Medicos;
import java.io.IOException;
import java.io.PrintWriter;
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

@WebServlet(name = "EliminarMedico", urlPatterns = {"/EliminarMedico"})
public class EliminarMedico extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            //Se elimina un Medico a partir de su cedula profesional
            Medicos m = new Medicos();
            int idusuario = Integer.parseInt(request.getParameter("idusuario"));
            m.setIdMedicos(Integer.parseInt(request.getParameter("idmedico")));
            
            MedicosCRUD dao = new MedicosCRUD();
            try {
                dao.eliminar(m);
                final String claveEncriptacion = "sec";            
                String datosOriginales = idusuario+"";            
                EncriptadorAES encriptador = new EncriptadorAES();
                String encriptado = encriptador.encriptar(datosOriginales, claveEncriptacion);
                //Una vez que se elimina va a la pagina de inicio
                response.sendRedirect("/Prototipo4_1/Paginas/PerfilAdmon.jsp?id=" + encriptado+"#Medicos");
            } catch (UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException ex) {
                Logger.getLogger(EncriptadorAES.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(EliminarMedico.class.getName()).log(Level.SEVERE, null, ex);
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
