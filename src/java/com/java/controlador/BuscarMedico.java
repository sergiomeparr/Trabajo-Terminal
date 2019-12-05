package com.java.controlador;

import com.java.bean.modelo.Medicos;
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

@WebServlet(name = "BuscarMedico", urlPatterns = {"/BuscarMedico"})
public class BuscarMedico extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            Medicos m = new Medicos();
            
            //Solicitud de dato id del médico
            m.setCedulap(request.getParameter("id"));
            
            MedicosCRUD dao = new MedicosCRUD();
            try {
                //Se busca al médico
                dao.read(m);
                //Si no hay problemas, ir al perfil del Administrador
                response.sendRedirect("/Prototipo4_1/Paginas/PerfilAdmon.jsp");
            } catch (SQLException ex) {
                Logger.getLogger(BuscarMedico.class.getName()).log(Level.SEVERE, null, ex);
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
