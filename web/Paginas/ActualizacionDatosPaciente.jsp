<%@page import="com.java.controlador.EncriptadorAES"%>
<%@page import="com.java.controlador.UsuarioDAO"%>
<%@page import="com.java.bean.modelo.Usuario"%>
<%@page import="com.java.bean.modelo.Paciente"%>
<%@page import="com.java.controlador.PacienteDAO"%>
<%@page import="java.util.logging.Logger"%>
<%@page import="java.util.logging.Level"%>
<%@page import="java.io.UnsupportedEncodingException"%>
<%@page import="java.security.NoSuchAlgorithmException"%>
<%@page import="javax.crypto.NoSuchPaddingException"%>
<%@page import="javax.crypto.IllegalBlockSizeException"%>
<%@page import="javax.crypto.BadPaddingException"%>
<%@page import="java.security.InvalidKeyException"%>
<%@page import="com.java.controlador.EncriptadorAES"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>T-Cuido</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" href="../Bootstrap/vendor/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="../Bootstrap/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="../Bootstrap/fonts/iconic/css/material-design-iconic-font.min.css">
        <link rel="stylesheet" type="text/css" href="../Bootstrap/vendor/animate/animate.css">
        <link rel="stylesheet" type="text/css" href="../Bootstrap/vendor/css-hamburgers/hamburgers.min.css">
        <link rel="stylesheet" type="text/css" href="../Bootstrap/vendor/animsition/css/animsition.min.css">
        <link rel="stylesheet" type="text/css" href="../Bootstrap/vendor/select2/select2.min.css">
        <link rel="stylesheet" type="text/css" href="../Bootstrap/vendor/daterangepicker/daterangepicker.css">
        <link rel="stylesheet" type="text/css" href="../Bootstrap/vendor/noui/nouislider.min.css">
        <link rel="stylesheet" type="text/css" href="../Bootstrap/css/util.css">
        <link rel="stylesheet" type="text/css" href="../Bootstrap/css/main.css">
        <link rel ="icon" type ="image/png" href="../Imagenes/Tcuido4.png"/>
        <script type="text/javascript" src="http://code.jquery.com/jquery-1.9.1.js"></script>
        <script type="text/javascript" src="../js/jquery.validate.min.js"></script>
        <script type="text/javascript" src="../js/additional-methods.min.js"></script>

        <script type="text/javascript">
        jQuery(document).ready(function ($) {
            $("#smart-form").validate({
                
            });
        });
    </script>

    </head>
    <body>
        <% 
            int idusuario = Integer.parseInt(request.getParameter("idusuario"));
            PacienteDAO pdao = new PacienteDAO();
            Paciente p = new Paciente();
            p = pdao.seleccionar_informacion(idusuario);
            
            String nombre = p.getNombrePaciente();
            String materno = p.getApellidoMaterno();
            String paterno = p.getApellidoPaterno();
            String telefono = p.getTelefono();
            String delegacion = p.getDelegacion(); 
            String sexo = p.getSexo(); 
            
            Usuario u = new Usuario();
            UsuarioDAO dao =  new UsuarioDAO();
            u.setIdUsuario(idusuario);
            u = dao.read(u);
            
            /*if(nombre.contains("<") || nombre.contains("-") || nombre.contains("@") || nombre.contains("!")){
                try {
                    final String claveEncriptacion = "sect!";            
                    EncriptadorAES encriptador = new EncriptadorAES();
                    nombre = encriptador.desencriptar(p.getNombrePaciente(), claveEncriptacion);
                    materno = encriptador.desencriptar(p.getApellidoMaterno(), claveEncriptacion);
                    paterno = encriptador.desencriptar(p.getNombrePaciente(), claveEncriptacion);
                    telefono = encriptador.desencriptar(p.getTelefono(), claveEncriptacion);
                    delegacion= encriptador.desencriptar(p.getDelegacion(), claveEncriptacion);
                    sexo= encriptador.desencriptar(p.getSexo(), claveEncriptacion);

                } catch (UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException ex) {
                    Logger.getLogger(EncriptadorAES.class.getName()).log(Level.SEVERE, null, ex);
                }
            }*/
        %>
        <div class="container-contact100">
            <div class="wrap-contact100">
                <form  action = "../ActualizarDatosPaciente" method = "post" class="contact100-form validate-form" id="smart-form">
                    <span class="contact100-form-title" style="font-family: Verdana; letter-spacing: 0.25em;">
                        <h1><strong>Actualización de datos</strong></h1>
                    </span>
                    <div class="wrap-input100 bg1" >
                        <span class="label-input100">Nombre *</span>
                        <input class="input100" type="text" name="txtNombrePaciente" value = "<%=nombre%>"
                            minlength="3" maxlength="35" required pattern="[a-zA-Z ñÑáéíóúÁÉÍÓÚüÜöÖëËäÄãẽÃẼêôàèìòùÀÈÌÒÙ]{3,35}"
                            title="<h6 style='color:red; font-size:15px'>Sólo letras y espacios, tamaño mínimo: 3, tamaño máximo: 35.</h6>"
                            data-rule-required="true" required="true"
                            data-msg-required="<h6 style='color:red; font-size:15px'>Por favor ingresa tu nombre</h6>" >
                    </div>
                    
                    <div class="wrap-input100 bg1 rs1-wrap-input100" >
                        <span class="label-input100">Primer apellido *</span>
                        <input class="input100" type="text" name="txtApellidoPaterno" value="<%=paterno%>" maxlength="35"
                            minlength="3" required pattern="[a-zA-Z ñÑáéíóúÁÉÍÓÚüÜöÖëËäÄãẽÃẼêôàèìòùÀÈÌÒÙ]{3,35}"
                            title="<h6 style='color:red; font-size:15px'>Sólo letras y espacios, tamaño mínimo: 3, tamaño máximo: 35.</h6>"
                            data-rule-required="true" required="true"
                            data-msg-required="<h6 style='color:red; font-size:15px'>Por favor ingresa tu primer apellido</h6>" >
                    </div>

                    <div class="wrap-input100 bg1 rs1-wrap-input100" >
                        <span class="label-input100">Segundo apellido </span>
                        <input class="input100" type="text" name="txtApellidoMaterno" value="<%=materno%>" maxlength="35" minlength="3"
                            required pattern="[a-zA-Z ñÑáéíóúÁÉÍÓÚüÜöÖëËäÄãẽÃẼêôàèìòùÀÈÌÒÙ]{3,35}"
                            title="<h6 style='color:red; font-size:15px'>Sólo letras y espacios, tamaño mínimo: 3, tamaño máximo: 35.</h6>"
                            data-rule-required="true" required="true"
                            data-msg-required="<h6 style='color:red; font-size:15px'>Por favor ingresa tu segundo apellido</h6>" >
                    </div>

                    <div class="wrap-input100 bg1 rs1-wrap-input100" >
                        <span class="label-input100">Teléfono * </span>
                        <input class="input100" type="text" name="txtTelefono" value="<%=telefono%>" minlength = "8" maxlength="10" required pattern="[0-9]+" Title="<h6 style='color:red; font-size:15px'>Solo números, tamaño mínimo de dígitos 8, tamaño máximo 10 dígitos</h6>"
                        data-rule-required="true"
                        data-rule-number="true"
                        required="true"
                        data-msg-required="<h6 style='color:red; font-size:15px'>Por favor ingresa tu teléfono</h6>" >
                    </div>
    
                    <div class="wrap-input100 bg1 rs1-wrap-input100" >
                        <span class="label-input100">Sexo *</span>
                        <select class="input100" style = "border:0px;" name = "txtSexo"
                        data-rule-required="true" required="true"
                        data-msg-required="<br><h6 style='color:red; font-size:15px'>Por favor ingresa tu sexo</h6>">
                            <option><%=sexo%></option>
                            <option>Hombre</option>
                            <option>Mujer</option>
                        </select>    
                    </div>
    
                    <div class="wrap-input100 bg1 rs1-wrap-input100" >
                        <span class="label-input100">Alcaldía *</span>
                        <select class="input100" style = "border:0px;" name = "txtDelegacion"
                        data-rule-required="true" required="true"
                        data-msg-required="<br><h6 style='color:red; font-size:15px'>Por favor ingresa tu alcaldía</h6>">
                            <option><%=delegacion%></option>
                            <option>Álvaro Obregón</option>
                            <option>Azcapotzalco</option>
                            <option>Benito Júarez</option>
                            <option>Coyoacán</option>
                            <option>Cuajimalpa de Morelos</option>
                            <option>Cuauhtémoc</option>
                            <option>Gustavo A. Madero</option>
                            <option>Iztacalco</option>
                            <option>Iztapalapa</option>
                            <option>La Magdalena Contreras</option>
                            <option>Miguel Hidalgo</option>
                            <option>Milpa Alta</option>
                            <option>Tláhuac</option>
                            <option>Tlalpan</option>
                            <option>Venustiano Carranza</option>
                            <option>Xochimilco</option>
                        </select>
                    </div>
    
                    <div class="wrap-input100 bg1 rs1-wrap-input100" >
                        <span class="label-input100">Correo electrónico *</span>
                        <input class="input100" type="email" name="txtUsuario" value="<%=u.getUsuario()%>" maxlength="50"
                        data-rule-required="true"
                        data-rule-email="true"
                        required="true"
                        title="<h6 style='color:red; font-size:15px'>Por favor ingresa un correo válido.</h6>"
                        data-msg-required="<h6 style='color:red; font-size:15px'>Por favor ingresa tu correo electrónico</h6>">
                    </div>
                    
                    <div class="wrap-input100 bg1" >
                        <span class="label-input100">Contraseña *</span>
                        <input class="input100" type="password" name="txtContrasena" value="<%=u.getContrasena()%>" maxlength="50" 
                            required minlength="8" pattern="^(?=\w*\d)(?=\w*[A-Z])(?=\w*[a-z])\S{8,16}$" title="<h6 style='color:red; font-size:15px'>Mínimo 8 dígitos, al menos una letra mayúscula, 1 letra minúscula, 1 número y caracteres especiales.</h6>"
                            required="true"
                            data-msg-required="<h6 style='color:red; font-size:15px'>Ingresa tu contraseña</h6>" >
                     </div>
                    <input type="hidden" value="<%=idusuario%>" name="idusuario">
                    
                    <div class="container">
                        <div class="row" >
                            <div class="col-sm-6">
                                <a  class="contact100-form-btn" href="/Prototipo4_1/Paginas/Buscador.jsp?id=<%=idusuario%>">Regresar</a>
                               <br>
                            </div>
                            <div class="col-sm-6">
                                <button class="contact100-form-btn">
                                    <span>
                                        Guardar datos
                                        <i class="fa fa-long-arrow-right m-l-7" aria-hidden="true"></i>
                                    </span>
                                </button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <script src="../Bootstrap/vendor/jquery/jquery-3.2.1.min.js"></script>
        <script src="../Bootstrap/vendor/animsition/js/animsition.min.js"></script>
        <script src="../Bootstrap/vendor/bootstrap/js/popper.js"></script>
        <script src="../Bootstrap/vendor/bootstrap/js/bootstrap.min.js"></script>
        <script src="../Bootstrap/vendor/select2/select2.min.js"></script>
        
        <script src="../Bootstrap/vendor/daterangepicker/moment.min.js"></script>
        <script src="../Bootstrap/vendor/daterangepicker/daterangepicker.js"></script>
        <script src="../Bootstrap/vendor/countdowntime/countdowntime.js"></script>
        <script src="../Bootstrap/vendor/noui/nouislider.min.js"></script>
        <script src="../Bootstrap/js/main.js"></script>

        <script async src="https://www.googletagmanager.com/gtag/js?id=UA-23581568-13"></script>
        
    </body>
</html>
