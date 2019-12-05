<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html lang="en">
    <head>
        <title>T-Cuido</title>
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
        
        <script src = "https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
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
        <div class="container-contact100">
            <div class="wrap-contact100">
                <form  action = "../NuevoHospital" class="contact100-form validate-form" onsubmit = "return submitUserForm();" id="smart-form">
                    <span class="contact100-form-title" style="font-family: Verdana; letter-spacing: 0.25em;">
                        <h1><strong>Hospital</strong></h1>
                    </span>

                    <div class="wrap-input100 bg1" >
                        <span class="label-input100">Nombre hospital*</span>
                        <input class="input100" type="text" name="txtNombre" placeholder="Nombre"  
                            minlength="3" maxlength="100" required 
                            title="<h6 style='color:red; font-size:15px'>Sólo letras y espacios, tamaño mínimo: 5, tamaño máximo: 145.</h6>"
                            data-rule-required="true" required="true"
                            data-msg-required="<h6 style='color:red; font-size:15px'>Por favor ingresa el nombre del hospital.</h6>" >
                    </div>
                    
                    <div class="wrap-input100 bg1 rs1-wrap-input100">
                        <span class="label-input100">Calle *</span>
                        <input class="input100" type="text" name="txtCalle" placeholder="calle"
                        minlength="5" maxlength="60" 
                        title="<h6 style='color:red; font-size:15px'>Sólo letras y espacios, tamaño mínimo: 5, tamaño máximo: 60</h6>"
                        required="true"
                        data-msg-required="<h6 style='color:red; font-size:15px'>Por favor ingresa la calle.</h6>">
                    </div>

                    <div class="wrap-input100 bg1 rs1-wrap-input100" >
                        <span class="label-input100">Número *</span>
                        <input class="input100" type="text" name="txtNumero" placeholder="Número"
                        minlength = "1"  required="true"
                        maxlength="10" required pattern="[0-9]+" Title="h6 style='color:red; font-size:15px'> Solo números, tamaño mínimo de dígitos 1, tamaño máximo 10 dígitos</h6>"
                        data-rule-required="true"
                        data-rule-number="true"
                        required="true"
                        data-msg-required="<h6 style='color:red; font-size:15px'>Por favor ingresa el número</h6>" >
                    </div>

                    <div class="wrap-input100 bg1 rs1-wrap-input100" >
                        <span class="label-input100">Alcaldía *</span>
                        <select class="input100" style = "border:0px;" name = "txtDelegacion"
                        data-rule-required="true" required="true"
                        data-msg-required="<h6 style='color:red; font-size:15px'>Por favor ingresa la alcaldía.</h6>">
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
                        <span class="label-input100">Colonia *</span>
                        <input class="input100" type="text" name="txtColonia" placeholder="colonia" 
                        minlength="5" maxlength="60" required
                        title="<h6 style='color:red; font-size:15px'>Sólo letras y espacios, tamaño mínimo: 5, tamaño máximo: 60</h6>"
                        data-rule-required="true" required="true"
                        data-msg-required="<h6 style='color:red; font-size:15px'>Por favor ingresa la colonia.</h6>">
                    </div>
                    
                    
                    <div class="wrap-input100 bg1 rs1-wrap-input100" >
                        <span class="label-input100">Código postal</span>
                        <input class="input100" type="text" name="txtCodigo" placeholder="Código postal" 
                        minlength = "4" 
                        maxlength="6" required pattern="[0-9]+" Title="<h6 style='color:red; font-size:15px'>Solo números, tamaño mínimo de dígitos 4, tamaño máximo 6 dígitos.</h6>" 
                        data-rule-required="true"
                        data-rule-number="true"
                        required="true"
                        data-msg-required="<h6 style='color:red; font-size:15px'>Por favor ingresa el código postal.</h6>" >
                    </div>
                    
                    <div class="wrap-input100 bg1 rs1-wrap-input100" >
                        <span class="label-input100">Teléfono</span>
                        <input class="input100" type="text" name="txtTelefono" placeholder="Teléfono" minlength = "8" maxlength="10" required pattern="[0-9]+" Title="<h6 style='color:red; font-size:15px'>Solo números, tamaño mínimo de dígitos 8, tamaño máximo 10 dígitos.</h6>"
                        data-rule-required="true"
                        data-rule-number="true"
                        required="true"
                        data-msg-required="<h6 style='color:red; font-size:15px'>Por favor ingresa el teléfono.</h6>">
                    </div>
                    
                    <div class="wrap-input100 bg1">
                        <span class="label-input100">Página del hospital</span>
                        <input class="input100" type="text" name="txtPagina" placeholder="Página del hospital" maxlength="100"
                        Title="<h6 style='color:red; font-size:15px'>Ingresa una direccion web válida. </h6>" 
                        data-rule-required="true"
                        required="true"
                        data-msg-required="<h6 style='color:red; font-size:15px'>Por favor ingresa la página web del hospital.</h6>"
                        data-rule-url="true" >
                    </div>
                    
                    <div class="container" align = "center">
                        <div class="g-recaptcha" data-sitekey="6LezxMMUAAAAACI0pe8uRNahZbg75PIhCLDONzVw"
                             data-callback="verifyCaptcha"></div>
                        <div id="g-recaptcha-error"></div>
                    </div>    
                    
                    <div class="container">
                        <br>
                        <div class="row" >
                            <div class="col-sm-6">
                                <a  class="contact100-form-btn" href="/Prototipo4_1/Paginas/DatosAdministrador.jsp?1=0">Regresar</a>
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

    </body>
</html>