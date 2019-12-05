package com.java.controlador;

import com.java.bean.modelo.Paciente;
import com.java.bean.modelo.Usuario;
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

@WebServlet(name = "NuevoUsuarioPaciente", urlPatterns = {"/NuevoUsuarioPaciente"})
public class NuevoUsuarioPaciente extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            String Nombre_Paciente = request.getParameter("txtNombrePaciente");
            String Apellido_Materno = request.getParameter("txtApellidoMaterno");
            String Apellido_Paterno = request.getParameter("txtApellidoPaterno");
            String Telefono = request.getParameter("txtTelefono");
            String Usuarioo = request.getParameter("txtUsuario");
            String Contrasena = request.getParameter("txtContrasena");
            String Delegacion = request.getParameter("txtDelegacion");
            String sexo = request.getParameter("txtSexo");
            String foto;
            if("Hombre".equals(sexo)){
                foto = "https://www.congresotmv.org/images/Congresotmv/Login/UsuarioHombre.png";
            }else{
                foto = "https://i.pinimg.com/originals/83/59/b3/8359b3a99dcc02f245eeb29a3d4b9e0e.png";
            }
            
            //Evitar Scripting
            if(Nombre_Paciente.contains("<") || Nombre_Paciente.contains(">") || Apellido_Materno.contains("<") 
                    || Apellido_Materno.contains(">") || Apellido_Paterno.contains("<") || Apellido_Paterno.contains(">") || 
                    Usuarioo.contains("<") || Usuarioo.contains(">") ||
                    Contrasena.contains("<") || Contrasena.contains(">") || Delegacion.contains("<") || 
                    Delegacion.contains(">") || Telefono.contains("<") || Telefono.contains(">")){
                response.sendRedirect("/Prototipo4_1/Paginas/DatosPaciente.jsp");
            }else{
        
                //Crecion de la carrera
                UsuarioDAO dao = new UsuarioDAO();
                PacienteDAO dao2 = new PacienteDAO();

                //Insercion de datos Carrera
                Usuario u = new Usuario();
                u.setUsuario(Usuarioo);
                u.setContrasena(Contrasena);
               
                //Encriptación de datos
                EncriptadorAES e = new EncriptadorAES();
                String nombre_paciente = Nombre_Paciente;
                String apellido_paterno = Apellido_Paterno;
                String apellido_materno = Apellido_Materno;
                String telefono = Telefono;
                String delegacion = Delegacion;
                String Sexo = sexo;
                /*
                try {
                    final String claveEncriptacion = "sect!";            
                    String dato1 = Nombre_Paciente;
                    String dato2 = Apellido_Materno; 
                    String dato3 = Apellido_Paterno;
                    String dato4 = Telefono;
                    String dato5 = Delegacion;
                    String dato6 = sexo;
                    
                    EncriptadorAES encriptador = new EncriptadorAES();
                    nombre_paciente = encriptador.encriptar(dato1, claveEncriptacion);
                    apellido_materno = encriptador.encriptar(dato2, claveEncriptacion);
                    apellido_paterno = encriptador.encriptar(dato3, claveEncriptacion);
                    telefono = encriptador.encriptar(dato4, claveEncriptacion);
                    delegacion = encriptador.encriptar(dato5, claveEncriptacion);
                    Sexo = encriptador.encriptar(dato6, claveEncriptacion);
                    
                } catch (UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException ex) {
                    Logger.getLogger(EncriptadorAES.class.getName()).log(Level.SEVERE, null, ex);
                }
                */
                //Insercion de datos Administrador
                Paciente a = new Paciente();
                a.setNombrePaciente(nombre_paciente);
                a.setApellidoMaterno(apellido_materno);
                a.setApellidoPaterno(apellido_paterno);
                a.setTelefono(telefono);
                a.setDelegacion(delegacion);
                a.setSexo(Sexo);
                a.setFoto(foto);
                try {
                    if(dao.seleccionar_by_email(Usuarioo) == false){
                        dao.crear(u);
                        int Idhospital = dao.seleccionar(); 
                        a.setIdUsuario(Idhospital);
                        dao2.crear(a);
                        response.sendRedirect("Paginas/LoginPaciente.jsp");
                    }else{
                        response.sendRedirect("Paginas/DatosPaciente.jsp?1=1");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(NuevoUsuarioPaciente.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(NuevoUsuarioPaciente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(NuevoUsuarioPaciente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
