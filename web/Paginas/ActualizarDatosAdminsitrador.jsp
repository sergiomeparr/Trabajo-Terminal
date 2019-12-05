<%@page import="com.java.bean.modelo.Usuario"%>
<%@page import="com.java.controlador.UsuarioDAO"%>
<%@page import="com.java.bean.modelo.Administrador"%>
<%@page import="com.java.controlador.AdministradorDAO"%>
<%@page import="java.util.List"%>
<%@page import="com.java.controlador.HospitalCRUD"%>
<%@page import="com.java.bean.modelo.Hospitales"%>
<%@page import="java.util.logging.Logger"%>
<%@page import="java.util.logging.Level"%>
<%@page import="java.io.UnsupportedEncodingException"%>
<%@page import="java.security.NoSuchAlgorithmException"%>
<%@page import="javax.crypto.NoSuchPaddingException"%>
<%@page import="javax.crypto.IllegalBlockSizeException"%>
<%@page import="javax.crypto.BadPaddingException"%>
<%@page import="java.security.InvalidKeyException"%>
<%@page import="com.java.controlador.EncriptadorAES"%>
<%@page import="java.security.InvalidKeyException"%>
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
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
        <link rel ="icon" type ="image/png" href="../Imagenes/Tcuido4.png"/>
        <script src = "https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
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
        <%  int idusuario = Integer.parseInt(request.getParameter("idusuario"));
            int idhospital = Integer.parseInt(request.getParameter("idhospital"));
            String encriptado = null;
            Administrador a = new Administrador();
            AdministradorDAO dao = new AdministradorDAO();
            a.setIdUsuario(idusuario);
            a = dao.leer(a);
            
            Usuario u = new Usuario();
            UsuarioDAO udao = new UsuarioDAO();
            u.setIdUsuario(idusuario);
            u = udao.read(u);
        %>
        <div class="container-contact100">
            <div class="wrap-contact100">
                <form  action = "../ActualizarDatosAdministrador" class="contact100-form validate-form" id="smart-form">
                    <span class="contact100-form-title" style="font-family: Verdana; letter-spacing: 0.25em;">
                        <h1><strong>Actualizar datos</strong></h1>
                    </span>

                    <div class="wrap-input100 bg1" >
                        <span class="label-input100">Nombre *</span>
                        <input class="input100" type="text" name="txtNombreAdministrador" value="<%=a.getNombreAdministrador()%>"  minlength="3" maxlength="35" 
                            required pattern="[a-zA-Z ñÑáéíóúÁÉÍÓÚüÜöÖëËäÄãẽÃẼêôàèìòùÀÈÌÒÙ]{3,35}"
                            title="<h6 style='color:red; font-size:15px'>Sólo letras y espacios, tamaño mínimo: 3, tamaño máximo: 35.</h6>"
                            data-rule-required="true" required="true"
                            data-msg-required="<h6 style='color:red; font-size:15px'>Por favor ingresa el nombre del médico especialista</h6>" >
                    </div>

                    <div class="wrap-input100 bg1 rs1-wrap-input100" >
                        <span class="label-input100">Primer apellido *</span>
                        <input class="input100" type="text" name="txtApellidoPaterno" value="<%=a.getApellidoPaterno()%>"  minlength="3" maxlength="35" 
                            required pattern="[a-zA-Z ñÑáéíóúÁÉÍÓÚüÜöÖëËäÄãẽÃẼêôàèìòùÀÈÌÒÙ]{3,35}"
                            title="<h6 style='color:red; font-size:15px'>Sólo letras y espacios, tamaño mínimo: 3, tamaño máximo: 35.</h6>"
                            data-rule-required="true" required="true"
                            data-msg-required="<h6 style='color:red; font-size:15px'>Por favor ingresa el primer apellido del médico especialista</h6>" >
                    </div>

                    <div class="wrap-input100 bg1 rs1-wrap-input100" >
                        <span class="label-input100">Segundo apellido *</span>
                        <input class="input100" type="text" name="txtApellidoMaterno" value="<%=a.getApellidoMaterno()%>"  minlength="3"
                            maxlength="35" required pattern="[a-zA-Z ñÑáéíóúÁÉÍÓÚüÜöÖëËäÄãẽÃẼêôàèìòùÀÈÌÒÙ]{3,35}"
                            title="<h6 style='color:red; font-size:15px'>Sólo letras y espacios, tamaño mínimo: 3, tamaño máximo: 35.</h6>"
                            data-rule-required="true" required="true"
                            data-msg-required="<h6 style='color:red; font-size:15px'>Por favor ingresa el segundo apellido del médico especialista</h6>" >
                    </div>

                    <div class="wrap-input100 bg1 rs1-wrap-input100" >
                        <span class="label-input100">Teléfono *</span>
                        <input class="input100" type="text" name="txtTelefono" value="<%=a.getTelefono()%>" minlength = "8" maxlength="10" required pattern="[0-9]+" Title="<h6 style='color:red; font-size:15px'>Solo números, tamaño mínimo de dígitos 8, tamaño máximo 10 dígitos</h6>"
                        data-rule-required="true"
                        data-rule-number="true"
                        required="true"
                        data-msg-number="<h6 style='color:red; font-size:15px'>Solo números, tamaño mínimo de dígitos 8, tamaño máximo 10 dígitos</h6>"
                        data-msg-required="<h6 style='color:red; font-size:15px'>Por favor ingresa el teléfono</h6>" >
                    </div>

                    <div class="wrap-input100 bg1 rs1-wrap-input100" >
                        <span class="label-input100">Sexo *</span>
                        <select class="input100" style = "border:0px;" name = "txtSexo"
                        data-rule-required="true" required="true"
                        data-msg-required="<br><h6 style='color:red; font-size:15px'>Por favor ingresa el sexo</h6>" >
                            <option><%=a.getSexo()%></option>
                            <option>Hombre</option>
                            <option>Mujer</option>
                        </select>
                    </div>

                    <div class="wrap-input100 bg1" >
                        <span class="label-input100">Correo electrónico *</span>
                        <input class="input100" type="email" name="txtUsuario" value="<%=u.getUsuario()%>" maxlength="50"
                        data-rule-required="true"
                        data-rule-email="true"
                        required="true"
                        data-msg-email="<h6 style='color:red; font-size:15px'>Ingresa un correo electrónico válido</h6>"
                        data-msg-required="<h6 style='color:red; font-size:15px'>Por favor ingresa el correo electrónico</h6>" >
                    </div>

                    <div class="wrap-input100 bg1">
                        <span class="label-input100">Contraseña *</span>
                        <input class="input100" type="password" name="txtContrasena" value="<%=u.getContrasena()%>" required minlength="8" maxlength="50"
                            pattern="^(?=\w*\d)(?=\w*[A-Z])(?=\w*[a-z])\S{8,16}$" title="<h6 style='color:red; font-size:15px'>Mínimo 8 dígitos, al menos una letra mayúscula, 1 letra minúscula, 1 número y caracteres especiales.</h6>"
                            required="true"
                            data-msg-required="<h6 style='color:red; font-size:15px'>Ingresa tu contraseña</h6>" >
                    </div>

                    <input type ="hidden" name ="idusuario" value="<%=idusuario%>">
                    <input type ="hidden" name ="idhospital" value="<%=idhospital%>">
                    <div class="container">
                        <div class="row" >
                            <div class="col-sm-6">
                                <%
                                    try {
                                        final String claveEncriptacion = "sec";            
                                        String datosOriginales = idusuario+"";            
                                        EncriptadorAES encriptador = new EncriptadorAES();
                                        encriptado = encriptador.encriptar(datosOriginales, claveEncriptacion);
                                        
                                    } catch (UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException ex) {
                                        Logger.getLogger(EncriptadorAES.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                %>
                                    
                                <a  class="contact100-form-btn" href="/Prototipo4_1/Paginas/PerfilAdmon.jsp?id=<%=encriptado%>#Configuracion">Regresar</a>
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