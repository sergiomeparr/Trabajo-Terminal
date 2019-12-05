package com.java.controlador;

import com.java.bean.modelo.Medicos;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
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

@WebServlet(name = "ActualizarDatosMedico", urlPatterns = {"/ActualizarDatosMedico"})
public class ActualizarDatosMedico extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        
        //Especifica el tipo de recurso, compatibilidad con el navegador(Codificación de caracteres)
        response.setContentType("text/html;charset=UTF-8");
        
        //Especifica la sintáxis utilizada
        request.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
        
        //Solicitudes de información para actualizar 
        String Cedulap = request.getParameter("Cedula");
        String Nombre = request.getParameter("Nombre");
        String Apellido_Paterno = request.getParameter("ApellidoP");
        String Apellido_Materno = request.getParameter("ApellidoM");
        String Especialidad = request.getParameter("Especialidad");
        String Telefono = request.getParameter("Telefono");
        String Historial = request.getParameter("Historial");
        String Email = request.getParameter("Email");
        /*
        Identificadores para saber el administrador, el
        hospital y el médico a gestionar.
        */
        int idhospital = Integer.parseInt(request.getParameter("idhospital"));
        int idusuario = Integer.parseInt(request.getParameter("idusuario"));
        int idmedico = Integer.parseInt(request.getParameter("idmedico"));
        
        
        //Evitar Scripting
        if(Cedulap.contains("<") || Cedulap.contains(">") || Nombre.contains("<") || Nombre.contains(">") ||
                Apellido_Paterno.contains("<") || Apellido_Paterno.contains(">") || 
                Apellido_Materno.contains("<") || Apellido_Materno.contains(">") ||
                Especialidad.contains("<") || Especialidad.contains(">") || Telefono.contains("<") || 
                Telefono.contains(">") || Historial.contains("<") || Historial.contains(">") || 
                Email.contains("<") || Email.contains(">")){
            response.sendRedirect("/Prototipo4_1/Paginas/ModificarMedico.jsp?idusuario="+idusuario+ "&id="+idhospital + "&idmedico="+idmedico+"&1=0");
        }else{
        
            //Creación del objeto médico, actualizar datos del médico
            Medicos m = new Medicos();
            m.setIdHospital(idhospital);
            //Objeto para actualizar datos del médico
            MedicosCRUD dao = new MedicosCRUD();
            //Se seleccionan los médicos para verificar que no hayan médicos con la misma cédula
            List<Medicos> lm = dao.readAll(m);
            int repetidos = 0;
            for(int i = 0; i < lm.size(); i++){
                if(lm.get(i).getCedulap().contains(Cedulap) && lm.get(i).getIdMedicos() != idmedico){
                    repetidos++;
                }
            }
            //Si no hay datos repetidos se actualizan los datos
            if(repetidos == 0){
                m =  new Medicos();
                dao = new MedicosCRUD();
                m.setCedulap(Cedulap);
                m.setNombreMedico(Nombre);
                m.setAPaternoMedico(Apellido_Paterno);
                m.setAMaternoMedico(Apellido_Materno);
                m.setEspecialidades(Especialidad);
                m.setTelefonos(Telefono);
                m.setEmail(Email);
                m.setHistorial(Historial);
                m.setFoto("0");
                m.setRankeo(0);
                m.setIdMedicos(idmedico);
                m.setIdHospital(idhospital);
            
                dao.update(m);
                final String claveEncriptacion = "sec";            
                String datosOriginales = idusuario+"";            
                EncriptadorAES encriptador = new EncriptadorAES();
                String encriptado = encriptador.encriptar(datosOriginales, claveEncriptacion);
                response.sendRedirect("/Prototipo4_1/Paginas/PerfilAdmon.jsp?id=" + encriptado+"#Medicos");
                //Como respuesta, nos manda a la página del perfil de administrador
            }else{
                response.sendRedirect("/Prototipo4_1/Paginas/ModificarMedico.jsp?idusuario=" +idusuario +"&id="+idhospital+"&idmedico="+idmedico+"&1=1");
            }
            }    
        }
    }
    

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, UnsupportedEncodingException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ActualizarDatosMedico.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ActualizarDatosMedico.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(ActualizarDatosMedico.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(ActualizarDatosMedico.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(ActualizarDatosMedico.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(ActualizarDatosMedico.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, UnsupportedEncodingException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ActualizarDatosMedico.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ActualizarDatosMedico.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(ActualizarDatosMedico.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(ActualizarDatosMedico.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(ActualizarDatosMedico.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(ActualizarDatosMedico.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
