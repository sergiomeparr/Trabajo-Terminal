package com.java.controlador;

import com.java.bean.modelo.Administrador;
import com.java.bean.modelo.Hospitales;
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

@WebServlet(name = "NuevoMedico", urlPatterns = {"/NuevoMedico"})
public class NuevoMedico extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            //Solicitud de datos para insertar nuevos datos de médicos
            String Cedulap = request.getParameter("Cedula");
            String Nombre = request.getParameter("Nombre");
            String Apellido_Paterno = request.getParameter("ApellidoP");
            String Apellido_Materno = request.getParameter("ApellidoM");
            String Especialidad = request.getParameter("Especialidad");
            String Telefono = request.getParameter("Telefono");
            String Historial = request.getParameter("Historial");
            String Email = request.getParameter("Email");
            
            //Identificadores del hospital y usuario
            int idhospital = Integer.parseInt(request.getParameter("idhospital"));
            int idusuario = Integer.parseInt(request.getParameter("idusuario"));
            
            //Evitar Scripting
            if(Cedulap.contains("<") || Cedulap.contains(">") || Nombre.contains("<") || Nombre.contains(">") ||
                    Apellido_Paterno.contains("<") || Apellido_Paterno.contains(">") || 
                    Apellido_Materno.contains("<") || Apellido_Materno.contains(">") ||
                    Especialidad.contains("<") || Especialidad.contains(">") || Telefono.contains("<") || 
                    Telefono.contains(">") || Historial.contains("<") || Historial.contains(">") || 
                    Email.contains("<") || Email.contains(">")){
                response.sendRedirect("/Prototipo4_1/Paginas/DatosMedicos.jsp?idusuario="+idusuario+ "&id="+idhospital+"&1=0");
            }else{
            
                //Insercion de datos Carrera
                Medicos m = new Medicos();
                m.setCedulap(Cedulap);
                m.setNombreMedico(Nombre);
                m.setAPaternoMedico(Apellido_Paterno);
                m.setAMaternoMedico(Apellido_Materno);
                m.setEspecialidades(Especialidad);
                m.setTelefonos(Telefono);
                m.setIdHospital(idhospital);
                m.setEmail(Email);
                m.setHistorial(Historial);
                m.setFoto("0");
                m.setRankeo(0);

                //Hospitales h = new Hospitales();
                //h.setIdHospital(idhospital);
                //h.setVerificar(0);

                //Crecion de la carrera
                MedicosCRUD dao = new MedicosCRUD();
                int cont = dao.repetidos(m);
                try {
                    if(cont == 0){
                    //Se insertan los datos del Medico
                        m = new Medicos();
                        m.setCedulap(Cedulap);
                        m.setNombreMedico(Nombre);
                        m.setAPaternoMedico(Apellido_Paterno);
                        m.setAMaternoMedico(Apellido_Materno);
                        m.setEspecialidades(Especialidad);
                        m.setTelefonos(Telefono);
                        m.setIdHospital(idhospital);
                        m.setEmail(Email);
                        m.setHistorial(Historial);
                        m.setFoto("0");
                        m.setRankeo(0);
                        dao.crear(m);
                        final String claveEncriptacion = "sec";            
                        String datosOriginales = idusuario+"";            
                        EncriptadorAES encriptador = new EncriptadorAES();
                        String encriptado = encriptador.encriptar(datosOriginales, claveEncriptacion);
                         //Como respuesta, nos manda a la página del perfil del administrador
                        response.sendRedirect("/Prototipo4_1/Paginas/PerfilAdmon.jsp?id=" + encriptado+"#Medicos");

                    //Se manda a la página de Datos de médico porque ya se tienen datos registrados
                    }else if(cont >= 1){
                        response.sendRedirect("/Prototipo4_1/Paginas/DatosMedicos.jsp?idusuario=" +idusuario +"&id="+idhospital+"&1=1");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(NuevoMedico.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, UnsupportedEncodingException {
        try {
            processRequest(request, response);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(NuevoMedico.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(NuevoMedico.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(NuevoMedico.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(NuevoMedico.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(NuevoMedico.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(NuevoMedico.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, UnsupportedEncodingException {
        try {
            processRequest(request, response);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(NuevoMedico.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(NuevoMedico.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(NuevoMedico.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(NuevoMedico.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(NuevoMedico.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(NuevoMedico.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
