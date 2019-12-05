package com.java.controlador;

import com.java.bean.modelo.ServiciosMedicos;
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

@WebServlet(name = "NuevoServicioMedico", urlPatterns = {"/NuevoServicioMedico"})
public class NuevoServicioMedico extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
        
        int idhospital = Integer.parseInt(request.getParameter("idhospital"));
        int idusuario = Integer.parseInt(request.getParameter("idusuario"));
        String tiposervicio = request.getParameter("ServicioMedico");
        String precio = request.getParameter("ServicioMedicoPrecio");
        
        //Evitar scripting
        if(tiposervicio.contains("<") || tiposervicio.contains(">")){
            response.sendRedirect("/Prototipo4_1/Paginas/DatosServiciosMedicos.jsp?idhospital="+idhospital+"&idusuario="+idusuario+"&1=0");
        }else{
            //Se crean los objetos para verificar si existe o no el servicio médico
            ServiciosMedicos sm = new ServiciosMedicos();
            ServiciosMedicosCRUD crud = new ServiciosMedicosCRUD();
            //Se ingresan los datos obtenidos
            sm.setTipo(tiposervicio);
            sm.setIdHospital(idhospital);
            //sm.setPrecio(precio);
            int contador = 0;
            try {
                //Se verifica si existe el servicio médico en el hospital
                
                contador = crud.repetidos1(sm);
                
                //Si es nulo, se crea el servicio médico
                if(contador == 0){
                    sm = new ServiciosMedicos();
                    sm.setTipo(tiposervicio);
                    sm.setIdHospital(idhospital);
                    if("".equals(precio))
                        precio = "0";
                    sm.setPrecio(precio);
                    crud.crear(sm);
                    //Clave para encriptar el idusuario
                    final String claveEncriptacion = "sec";            
                    String datosOriginales = idusuario+"";            
                    //Se crea el objeto para encriptar datos
                    EncriptadorAES encriptador = new EncriptadorAES();
                    //Ahora se hace la operacion de encriptar el Id de usuario con la clave de encriptación
                    String encriptado = encriptador.encriptar(datosOriginales, claveEncriptacion);
                    //Posteriormente, se manda a la página del perfil del administrador del hospital
                    response.sendRedirect("/Prototipo4_1/Paginas/PerfilAdmon.jsp?id=" + encriptado+"#Servicios");
               
                    /*De lo contrario, no se crea el servicio médico y se manda a la pagina de los datos del
                    servicio médico*/
                }else{
                    response.sendRedirect("/Prototipo4_1/Paginas/DatosServiciosMedicos.jsp?idusuario="+idusuario+"&idhospital="+idhospital+"&1=1");
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
        } catch (SQLException ex) {
            Logger.getLogger(NuevoServicioMedico.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(NuevoServicioMedico.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(NuevoServicioMedico.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(NuevoServicioMedico.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(NuevoServicioMedico.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(NuevoServicioMedico.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, UnsupportedEncodingException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(NuevoServicioMedico.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(NuevoServicioMedico.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(NuevoServicioMedico.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(NuevoServicioMedico.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(NuevoServicioMedico.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(NuevoServicioMedico.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
