<%@page import="java.util.List"%>
<%@page import="com.java.controlador.HospitalCRUD"%>
<%@page import="com.java.bean.modelo.Hospitales"%>
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
        <link rel="stylesheet" href="../Bootstrap/css/bootstrap.min.css">
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
        <%int bandera = Integer.parseInt(request.getParameter("1"));%>
        
        <%if(bandera==1){%>
            <script>
                alert("Usuario existente");
            </script>
        <%}%>
        <div class="container-contact100">
            <div class="wrap-contact100">
                <form  action = "../NuevoUsuarioAdministrador" class="contact100-form validate-form" onsubmit = "return submitUserForm();" id="smart-form">
                    <span class="contact100-form-title" style="font-family: Verdana; letter-spacing: 0.25em;">
                        <h1><strong>Administrador</strong></h1>
                    </span>

                    <div class="wrap-input100 bg1" >
                        <span class="label-input100">Nombre *</span>
                        <input class="input100" type="text" name="txtNombreAdministrador" placeholder="Nombre"  
                            minlength="3" maxlength="35" required pattern="[A-Za-z-0-9 ñÑáéíóúÁÉÍÓÚüÜöÖëËäÄãẽÃẼêôàèìòùÀÈÌÒÙ]+"
                            title="<h6 style='color:red; font-size:15px'>Sólo letras y espacios, tamaño mínimo: 3, tamaño máximo: 35.</h6>"
                            data-rule-required="true" required="true"
                            data-msg-required="<h6 style='color:red; font-size:15px'>Por favor ingresa tu nombre.</h6>">
                    </div>

                    <div class="wrap-input100 bg1 rs1-wrap-input100" >
                        <span class="label-input100">Primer apellido *</span>
                        <input class="input100" type="text" name="txtApellidoPaterno" placeholder="Primer apellido"  
                            minlength="3" maxlength="35" required pattern="[A-Za-z-0-9 ñÑáéíóúÁÉÍÓÚüÜöÖëËäÄãẽÃẼêôàèìòùÀÈÌÒÙ]+"
                            title="<h6 style='color:red; font-size:15px'>Sólo letras y espacios, tamaño mínimo: 3, tamaño máximo: 35.</h6>"
                            data-rule-required="true" required="true"
                            data-msg-required="<h6 style='color:red; font-size:15px'>Por favor ingresa tu primer apellido.</h6>" >
                    </div>

                    <div class="wrap-input100 bg1 rs1-wrap-input100" >
                        <span class="label-input100">Segundo apellido *</span>
                        <input class="input100" type="text" name="txtApellidoMaterno" placeholder="Segundo apellido"  
                            minlength="3" maxlength="35" required pattern="[A-Za-z-0-9 ñÑáéíóúÁÉÍÓÚüÜöÖëËäÄãẽÃẼêôàèìòùÀÈÌÒÙ]+"
                            title="<h6 style='color:red; font-size:15px'>Sólo letras y espacios, tamaño mínimo: 3, tamaño máximo: 35.</h6>"
                            data-rule-required="true" required="true"
                            data-msg-required="<h6 style='color:red; font-size:15px'>Por favor ingresa tu segundo apellido.</h6>" >
                    </div>

                    <div class="wrap-input100 bg1 " >
                        <span class="label-input100">Teléfono *</span>
                        <input class="input100" type="text" name="txtTelefono" placeholder="Ingresa teléfono"  minlength = "8" maxlength="10" required pattern="[0-9]+" 
                               Title="<h6 style='color:red; font-size:15px'>Solo números, tamaño mínimo de dígitos 8, tamaño máximo 10 dígitos</h6>"
                        data-rule-required="true"
                        data-rule-number="true"
                        required="true"
                        data-msg-required="<h6 style='color:red; font-size:15px'>Por favor ingresa tu teléfono</h6>" >
                    </div>

                    <div class="wrap-input100 bg1" >
                        <span class="label-input100">Sexo *</span>
                        <select class="input100" style = "border:0px;" name = "txtSexo"
                        data-rule-required="true" required="true"
                        data-msg-required="<h6 style='color:red; font-size:15px'><br>Por favor ingresa el sexo</h6>" >
                            <option></option>
                            <option>Hombre</option>
                            <option>Mujer</option>
                        </select>
                    </div>

                    
                    <div class="wrap-input100 bg1 rs1-wrap-input100">
                        <span class="label-input100">Selecciona hospital *</span>
                        <select class="input100" style = "border:0px;" name = "txtHospital"                        
                        data-rule-required="true" required="true"
                        data-msg-required="<h6 style='color:red; font-size:15px'>Por favor selecciona el hospital</h6>">
                            <option></option>
                            <%
                                String nombre, direccion, telefono;
                                String pagina, foto;
                                int rankeo;
                                HospitalCRUD crud = new HospitalCRUD();
                                List<Hospitales> lista = crud.readAll();
                                for (int i = 0; i < lista.size(); i++) {
                                    Hospitales listaHospitales = (Hospitales) lista.get(i);
                                    nombre = listaHospitales.getNombre();
                                    direccion = listaHospitales.getDireccion();
                                    telefono = listaHospitales.getTelefono();
                                    pagina = listaHospitales.getPagina();
                                    foto = listaHospitales.getFoto();
                                    rankeo = listaHospitales.getRankeo();
                            %>
                            <option><%=nombre%> </option>
                            <% }%>
                        </select>
                    </div>
                    <div class ="wrap-input100 rs1-wrap-input100" style = "border:0px">    
                        <br>
                        <span class="label-input100"><u><a href="DatosHospital.jsp">Registrar hospital</a></u></span>
                    </div>
                    
                    <div class="wrap-input100 bg1" >
                        <span class="label-input100">Correo electrónico *</span>
                        <input id="email" class="input100" type="email" name="txtUsuario" placeholder="Correo electrónico" maxlength="50"
                        title="Ingresa un correo electrónico válido"
                        data-rule-required="true"
                        data-rule-email="true"
                        required="true"
                        data-msg-required="<h6 style='color:red; font-size:15px'>Por favor ingresa el correo electrónico</h6>" >
                    </div>

                    <div class="wrap-input100 bg1">
                        <span class="label-input100">Contraseña *</span>
                        <input class="input100" type="password" name="txtContrasena" placeholder="Contraseña" maxlength="50" 
                               required minlength="8" pattern="^(?=\w*\d)(?=\w*[A-Z])(?=\w*[a-z])\S{8,16}$" 
                               title="<h6 style='color:red; font-size:15px'>Mínimo 8 dígitos, al menos una letra mayúscula, 1 letra minúscula, 1 número y caracteres especiales.</h6>"
                               required="true"
                               data-msg-required="<h6 style='color:red; font-size:15px'>Ingresa tu Contraseña</h6>" >
                    </div>

                    <div class="container" align = "center">
                        <div class="g-recaptcha" data-sitekey="6LezxMMUAAAAACI0pe8uRNahZbg75PIhCLDONzVw"
                             data-callback="verifyCaptcha"></div>
                        <div id="g-recaptcha-error"></div>
                    </div>    
                        
                    <div class="container">
                        <br>
                        <div class="row">
                            <div class="col-sm-6">
                                <a class="contact100-form-btn" href="/Prototipo4_1/Paginas/LoginAdministrador.jsp">Regresar</a>
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
        
        <!-- Script para poder poner Captcha-->
        <script src="https://www.google.com/recaptcha/api.js" async defer></script>
        <!-- Script para que el campo del captcha sea obligatorio-->
        <script>
            function submitUserForm(){
                    var response = grecaptcha.getResponse();
                    console.log(response.length);
                    if(response.length === 0){
                            document.getElementById('g-recaptcha-error').innerHTML = '<span style="color:red;">Campo requerido</span>';
                            return false;
                    }
                    return true;
            }
            function verifyCaptcha(){
                    console.log('verified');
                    document.getElementById('g-recaptcha-error').innerHTML = '';
            }
        </script>
        
        <script src="../Bootstrap/vendor/daterangepicker/moment.min.js"></script>
        <script src="../Bootstrap/vendor/daterangepicker/daterangepicker.js"></script>
        <script src="../Bootstrap/vendor/countdowntime/countdowntime.js"></script>
        <script src="../Bootstrap/vendor/noui/nouislider.min.js"></script>
        <script src="../Bootstrap/js/main.js"></script>

        <script async src="https://www.googletagmanager.com/gtag/js?id=UA-23581568-13"></script>
    </body>
</html>