package com.java.controlador;

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

@WebServlet(name = "BuscarAdministrador", urlPatterns = {"/BuscarAdministrador"})
public class BuscarAdministrador extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
    try (PrintWriter out = response.getWriter()) {
        
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        //Solicitud de variables
        String usuario =  request.getParameter("txtUsuario");
        String contrasena =  request.getParameter("txtContrasena");
        
        //Se crea el objeto buscar usuario
        Buscar_Usuario bu = new Buscar_Usuario();
        
        //Se crea el objeto que contiene el método para buscar al Administrador
        AdministradorDAO da = new AdministradorDAO();
        int idusuario = bu.identificador(usuario, contrasena);
        boolean idpaciente = da.seleccionar(idusuario);
        //Si Existe paciente, ir a buscador
        if(idpaciente){
            try {
                final String claveEncriptacion = "sec";            
                String datosOriginales = Integer.toString(idusuario);            
                EncriptadorAES encriptador = new EncriptadorAES();
                String encriptado = encriptador.encriptar(datosOriginales, claveEncriptacion);
                response.sendRedirect("/Prototipo4_1/Paginas/PerfilAdmon.jsp?id=" + encriptado);

            } catch (UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException ex) {
                //response.sendRedirect("/Prototipo4_1/Paginas/Error.jsp");
                Logger.getLogger(EncriptadorAES.class.getName()).log(Level.SEVERE, null, ex);
            }
        //Si no Existe no va a entrar al buscador
        }else
            response.sendRedirect("/Prototipo4_1/Paginas/ErrorLoginAdministrador.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(BuscarAdministrador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(BuscarAdministrador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
