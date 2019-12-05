package com.java.controlador;

import com.java.bean.modelo.Administrador;
import com.java.bean.modelo.Hospitales;
import com.java.bean.modelo.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileUploadException;

@WebServlet(name = "NuevoUsuarioAdministrador", urlPatterns = {"/NuevoUsuarioAdministrador"})
public class NuevoUsuarioAdministrador extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, FileUploadException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
        String Nombre_Administrador = request.getParameter("txtNombreAdministrador");
        String Apellido_Materno = request.getParameter("txtApellidoMaterno");
        String Apellido_Paterno = request.getParameter("txtApellidoPaterno");
        String Telefono = request.getParameter("txtTelefono");
        String Usuarioo = request.getParameter("txtUsuario");
        String Contrasena = request.getParameter("txtContrasena");
        String foto;
            
        String hospital = request.getParameter("txtHospital");
        String sexo = request.getParameter("txtSexo");
        if("Hombre".equals(sexo)){
            foto = "https://www.ge2.co/wp-content/uploads/2018/05/administrador.png";
        }else{
            foto = "https://i1.wp.com/www.ramter.cl/wp-content/uploads/2017/05/woman.png?fit=512%2C512&ssl=1";
        }
        
        //Evitar Scripting
        if(Nombre_Administrador.contains("<") || Nombre_Administrador.contains(">") || 
                Apellido_Materno.contains("<") || Apellido_Materno.contains(">") ||
                Apellido_Paterno.contains("<") || Apellido_Paterno.contains(">") || 
                Telefono.contains(">") || Telefono.contains("<") || Usuarioo.contains(">") || 
                Usuarioo.contains("<") || Contrasena.contains(">") ||  Contrasena.contains("<")){
            response.sendRedirect("/Prototipo4_1/Paginas/DatosAdministrador.jsp?1=0");
        }else{
        
            Hospitales h = new Hospitales();
            HospitalCRUD crud = new HospitalCRUD();
            h.setNombre(hospital);
            List <Hospitales> h1 = crud.readbyname(h);
            //Insercion de datos Usuario
            Usuario u = new Usuario();
            u.setUsuario(Usuarioo);
            u.setContrasena(Contrasena);
            
            //Insercion de datos Administrador
            Administrador a = new Administrador();
            a.setNombreAdministrador(Nombre_Administrador);
            a.setApellidoPaterno(Apellido_Paterno);
            a.setApellidoMaterno(Apellido_Materno);
            a.setTelefono(Telefono);
            a.setFoto(foto);
            a.setIdHospital(h1.get(0).getIdHospital());
            a.setSexo(sexo);
            UsuarioDAO dao = new UsuarioDAO();
            AdministradorDAO dao2 = new AdministradorDAO();
            //Creacion de usuario administrador
            
            try {
                if(dao.seleccionar_by_email(Usuarioo) == false){
                    dao.crear(u);
                    int idusuario = dao.seleccionar(); 
                    a.setIdUsuario(idusuario);
                    dao2.crear(a);
                    response.sendRedirect("Paginas/LoginAdministrador.jsp");
                }else{
                    response.sendRedirect("Paginas/DatosAdministrador.jsp?1=1");
                }
            } catch (SQLException ex) {
                Logger.getLogger(NuevoUsuarioAdministrador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(NuevoUsuarioAdministrador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(NuevoUsuarioAdministrador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
