package com.java.controlador;

import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.*;

@WebServlet(name = "BuscarPaciente", urlPatterns = {"/BuscarPaciente"})
public class BuscarPaciente extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, InvalidKeyException {
        try (PrintWriter out = response.getWriter()) {
            response.setContentType("text/html;charset=UTF-8");
            request.setCharacterEncoding("UTF-8");
            //Obtencion de datos del login de Usuario
            int intentos = 0;
            String usuario =  request.getParameter("txtUsuario");
            String contrasena =  request.getParameter("txtContrasena");
            if(request.getParameter("txtIntentos") != null){
                intentos = Integer.parseInt(request.getParameter("a"));
            }
            Buscar_Usuario bu = new Buscar_Usuario();
            //Aqui buscamos al Usuario
            PacienteDAO pa = new PacienteDAO();
            int idusuario = bu.identificador(usuario, contrasena);
            boolean idpaciente = pa.seleccionar(idusuario);
            //Si Existe paciente, ir a buscador
            if(idpaciente){
                try {
                    final String claveEncriptacion = "secreto!";            
                    String datosOriginales = idusuario+"";            
                    EncriptadorAES encriptador = new EncriptadorAES();
                    String encriptado = encriptador.encriptar(datosOriginales, claveEncriptacion);
                    response.sendRedirect("/Prototipo4_1/Paginas/Buscador.jsp?id=" + encriptado);
            
                } catch (UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException ex) {
                    Logger.getLogger(EncriptadorAES.class.getName()).log(Level.SEVERE, null, ex);
                }
            //Si no Existe no va a entrar al buscador
            }else{
                intentos++;
                response.sendRedirect("/Prototipo4_1/Paginas/ErrorLoginPaciente.jsp?a="+intentos);
            }
            if(intentos == 10)
                response.sendRedirect("/Prototipo4_1/Paginas/ContrasenaOlvidadaPaciente.jsp");
            
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(BuscarPaciente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(BuscarPaciente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(BuscarPaciente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(BuscarPaciente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
