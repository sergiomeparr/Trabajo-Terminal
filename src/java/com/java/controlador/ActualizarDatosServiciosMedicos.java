package com.java.controlador;

import com.java.bean.modelo.ServiciosMedicos;
import java.io.IOException;
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

@WebServlet(name = "ActualizarDatosServiciosMedicos", urlPatterns = {"/ActualizarDatosServiciosMedicos"})
public class ActualizarDatosServiciosMedicos extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        //Especifica el tipo de recurso, compatibilidad con el navegador(Codificación de caracteres)
        response.setContentType("text/html;charset=UTF-8");
        
        //Especifa la sintáxis utilizada
        request.setCharacterEncoding("UTF-8");
        
        //Solicitud de información para actualizar servicio médico 
        String tipo = request.getParameter("ServicioMedico");
        String precio = request.getParameter("ServicioMedicoPrecio");
        
        
        //Identificador del usuario y servicios médicos
        int idservicio = Integer.parseInt(request.getParameter("idservicio"));
        int idusuario = Integer.parseInt(request.getParameter("idusuario"));
        int idhospital = Integer.parseInt(request.getParameter("idhospital"));
        
        //Evitar Scripting
        if(tipo.contains("<") || tipo.contains(">")){
            response.sendRedirect("/Prototipo4_1/Paginas/ActualizarDatosServicios.jsp?idusuario="+idusuario+"&idservicio="+idservicio+"&idhospital="+idhospital+"&1=0");
        
        }else{
        
            //Creacion de objetos para actualizar datos
            ServiciosMedicos sm = new ServiciosMedicos();
            ServiciosMedicosCRUD crud = new ServiciosMedicosCRUD();
            sm.setTipo(tipo);
            sm.setIdServicio(idservicio);
            sm.setIdHospital(idhospital);
            sm.setPrecio(precio);
            int repetidos = 0;
            
            
            //Actualización de datos
            try {
                List<ServiciosMedicos> lsm = crud.readAll(sm);
                for(int i = 0; i < lsm.size(); i++){
                    if(lsm.get(i).getIdServicio() != idservicio && lsm.get(i).getTipo().contains(tipo))
                        repetidos++;
                }
                if(repetidos == 0){
                    sm = new ServiciosMedicos();
                    crud = new ServiciosMedicosCRUD();
                    sm.setTipo(tipo);
                    sm.setIdServicio(idservicio);
                    sm.setIdHospital(idhospital);
                    sm.setPrecio(precio);
                    crud.update(sm);
                    final String claveEncriptacion = "sec";            
                    String datosOriginales = idusuario+"";            
                    EncriptadorAES encriptador = new EncriptadorAES();
                    String encriptado = encriptador.encriptar(datosOriginales, claveEncriptacion);
                    response.sendRedirect("/Prototipo4_1/Paginas/PerfilAdmon.jsp?id=" + encriptado+"#Servicios");
                }else{
                    response.sendRedirect("/Prototipo4_1/Paginas/ActualizarDatosServicios.jsp?idusuario="+idusuario+"&idhospital="+idhospital+"&idservicio="+idservicio+"&1=1");
                }
                    
            } catch (UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException ex) {
                Logger.getLogger(EncriptadorAES.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(NuevoMedico.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ActualizarDatosServiciosMedicos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ActualizarDatosServiciosMedicos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
