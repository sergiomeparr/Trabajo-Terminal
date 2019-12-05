package com.java.controlador;

import com.java.bean.modelo.Usuario;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "EnviarCorreoAdmon", urlPatterns = {"/EnviarCorreoAdmon"})
public class EnviarCorreoAdmon extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String correo = request.getParameter("Email");
        Buscar_Usuario bu = new Buscar_Usuario();
        boolean respuesta = bu.Existe_Usuario(correo);
        //Se manda correo siempre y cuando el usuario exista 
        if (respuesta) {
            //Obtencion de la contraseña a partir del usuario
            Buscar_Usuario dao = new Buscar_Usuario();
            Usuario u = new Usuario();
            u.setUsuario(correo);
            u = dao.ObtenerContrasena(u);
            //Envio del correo con usuario y contraseña
            enviarMail(correo, u.getContrasena(), 0);
            //Lo manda a la pagina principal de T-Cuido
            response.sendRedirect("/Prototipo4_1/Paginas/LoginAdministrador.jsp");
        } else {
            //En caso de no encontrar usuario no le manda correo
            response.sendRedirect("/Prototipo4_1/Paginas/ContrasenaOlvidadaAdmon.jsp");
        }
    }

    //Metodo para el envio del correo
    public void enviarMail(String correoDestinatario, String contrasena, int bandera) {
        final String username = "t.cuido.2018b021@gmail.com";          //correo de T-Cuido
        final String password = "fkowloemugsoiglj";                    //contraseña 
        String Asunto = "";
        String TextoCorreo = "";
        if(bandera == 0){
            Asunto = "Recuperación de Contraseña";            //Asunto del correo
            TextoCorreo = "\nUsuario: " + correoDestinatario  //Texto del correo (Usuario y Contraseña)
                + " \n\nContraseña: " + contrasena;
        }else if(bandera == 1){ //aceptar hospital
            Asunto = contrasena;            //Asunto del correo
            TextoCorreo = "Que tal, Administrador!"
                    + "\n"
                    + "\n"
                    + "Se le informa que el registro solicitado para dar de alta al administrador del hospital"
                    + " fue aprobado, ya puede registrar a médicos especialistas y servicios médicos del hospital "
                    + "donde trabaja."
                    + "\n"
                    + "\n"
                    + "\n"
                    + "\n"
                    + "\n"
                    + "Atte: Soporte técnico de T-Cuido :D";
        }else if(bandera == 2){ //rechazar hospital
            Asunto = contrasena;            //Asunto del correo
            TextoCorreo = "Que tal, Administrador!"
                    + "\n"
                    + "\n"
                    + "Se le informa que el registro solicitado para de alta al administrador del hospital " 
                    + "fue rechazado, por incongruencias en la solicitud. Si quiere darse de alta en T-Cuido vuelva" 
                    + " a realizar su registro"
                    + "\n"
                    + "\n"
                    + "\n"
                    + "\n"
                    + "\n"
                    + "Atte: Soporte técnico de T-Cuido :D";
            
        }
        try {
            //Propiedades de la conexion
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.port", "587");
            props.setProperty("mail.smtp.user", username);
            props.setProperty("mail.smtp.auth", "true");

            //Inicializar la sesion la sesion
            Session session = Session.getDefaultInstance(props,
                    new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });
            session.setDebug(true);

            //mensaje
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.addRecipient(
                    Message.RecipientType.TO,
                    new InternetAddress(correoDestinatario)
            );
            message.addRecipient(
                    Message.RecipientType.TO,
                    new InternetAddress(correoDestinatario)
            );
            //CC A quien se le envia una copia Oculpa
            //BCC A quien se le envia una copia Oculta
            message.setSubject(Asunto);
            message.setText(TextoCorreo);
            //Envio Mensaje
            Transport transporte = session.getTransport("smtp");
            transporte.connect(username, password);
            transporte.sendMessage(message, message.getAllRecipients());
            //Cierre
            transporte.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(EnviarCorreoAdmon.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(EnviarCorreoAdmon.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
