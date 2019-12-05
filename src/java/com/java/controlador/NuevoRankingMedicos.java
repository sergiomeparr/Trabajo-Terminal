package com.java.controlador;

import com.java.bean.modelo.RankeoMedicos;
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

@WebServlet(name = "NuevoRankingMedicos", urlPatterns = {"/NuevoRankingMedicos"})
public class NuevoRankingMedicos extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("</head>");
            out.println("<body>");
            String mensajeAMostrar = "";
            int ranking = Integer.parseInt(request.getParameter("txtranking"));
            String comentario = request.getParameter("txtcomentario");
            int usuario = Integer.parseInt(request.getParameter("idusuario"));
            int hospital = Integer.parseInt(request.getParameter("idhospital"));
            int medico = Integer.parseInt(request.getParameter("idmedico"));
            
            //Insercion de datos Rankeo
            RankeoMedicos r = new RankeoMedicos();
            r.setRankeo(ranking);
            r.setComentario(comentario);
            r.setIdUsuario(usuario);
            r.setIdHospital(hospital);
            r.setIdMedico(medico);
            //Crecion del rankeo
            RankeoMedicosCRUD dao = new RankeoMedicosCRUD();
            try {
                dao.crear(r);
                response.sendRedirect("/Prototipo4_1/Paginas/InformacionEspecialistas.jsp?idmedico=" + medico + "&idusuario="+usuario + "&idhospital=" + hospital);
            } catch (SQLException ex) {
                mensajeAMostrar = "No se pudó agregar el registro" + ex.toString();
                Logger.getLogger(NuevoRanking.class.getName()).log(Level.SEVERE, null, ex);
            }
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(NuevoRanking.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(NuevoRanking.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
