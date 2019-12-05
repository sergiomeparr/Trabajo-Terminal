package com.java.controlador;

import com.java.bean.modelo.Hospitales;
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

@WebServlet(name = "NuevoHospital", urlPatterns = {"/NuevoHospital"})
public class NuevoHospital extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            //Solicitud de datos del hospital
            String nombre = request.getParameter("txtNombre");
            String calle = request.getParameter("txtCalle");
            String numero = request.getParameter("txtNumero");
            String delegacion = request.getParameter("txtDelegacion");
            String colonia = request.getParameter("txtColonia");
            String cp = request.getParameter("txtCodigo");
            String telefono = request.getParameter("txtTelefono");
            String pagina = request.getParameter("txtPagina");
            
            String foto = "https://previews.123rf.com/images/neyro2008/neyro20081512/neyro2008151200317/49781838-ilustraci%C3%B3n-de-la-ciudad-del-edificio-del-hospital-en-el-estilo-de-dise%C3%B1o-plano-arquitectura-cl%C3%ADnica-hospita.jpg";
            
            //Evitar Scripting
            if(nombre.contains("<") || nombre.contains(">") || calle.contains("<") || calle.contains(">") ||
                numero.contains("<") || numero.contains(">") ||  delegacion.contains("<") || delegacion.contains(">") || 
                colonia.contains("<") || colonia.contains(">") || cp.contains("<") || cp.contains(">") ||
                telefono.contains("<") || telefono.contains(">") || pagina.contains("<") || pagina.contains(">")){
                
                response.sendRedirect("/Prototipo4_1/Paginas/DatosHospital.jsp?1=0");
            }else{

                out.println("aqui");
                //Se crea objeto hospital para insertar nuevos datos
                Hospitales h = new Hospitales();
                HospitalCRUD crud = new HospitalCRUD();
                h.setNombre(cp);
                h.setNombre(nombre);
                h.setDireccion("Calle "+ calle + " No. " + numero + " Col. " + colonia + ", C.P. "
                        + cp + ", " + delegacion + ", CDMX");
                h.setPagina(pagina);
                h.setTelefono(telefono);
                h.setFoto(foto);
                h.setRankeo(5);
                h.setVerificar(0);

                //Se crea el hospital
                crud.crear(h);

                //Se manda la pagina de Datos Administrador
                response.sendRedirect("/Prototipo4_1/Paginas/DatosAdministrador.jsp?1=0");
            }
        }
    }
    

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(NuevoHospital.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(NuevoHospital.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
