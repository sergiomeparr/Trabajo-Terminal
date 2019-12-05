package com.java.controlador;

import com.java.bean.modelo.AdministradorSistema;
import com.java.bean.modelo.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "BuscarAdministradorSistema", urlPatterns = {"/BuscarAdministradorSistema"})
public class BuscarAdministradorSistema extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        try (PrintWriter out = response.getWriter()) {
        
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        //Solicitud de variables
        String llave_admon =  request.getParameter("txtUsuario");
        
        
        AdministradorSistema admon_sis = new AdministradorSistema();
        AdministradorSistemaCRUD admon_sis_crud = new AdministradorSistemaCRUD();
        
        admon_sis.setLlaveAdmon(llave_admon);
        if(admon_sis_crud.existe(admon_sis) == true)
            response.sendRedirect("/Prototipo4_1/Paginas/PerfilAdmonSistema.jsp");
        else
            response.sendRedirect("/Prototipo4_1/Paginas/ErrorLoginAdministradorSistema.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(BuscarAdministradorSistema.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(BuscarAdministradorSistema.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
