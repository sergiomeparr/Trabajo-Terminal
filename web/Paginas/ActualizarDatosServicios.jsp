<%@page import="java.security.InvalidKeyException"%>
<%@page import="com.java.controlador.EncriptadorAES"%>
<%@page import="java.util.List"%>
<%@page import="com.java.controlador.ServiciosMedicosCRUD"%>
<%@page import="com.java.bean.modelo.ServiciosMedicos"%>
<%@page import="java.util.logging.Logger"%>
<%@page import="java.util.logging.Level"%>
<%@page import="java.io.UnsupportedEncodingException"%>
<%@page import="java.security.NoSuchAlgorithmException"%>
<%@page import="javax.crypto.NoSuchPaddingException"%>
<%@page import="javax.crypto.IllegalBlockSizeException"%>
<%@page import="javax.crypto.BadPaddingException"%>
<%@page import="java.security.InvalidKeyException"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>T-Cuido</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
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
            int idhospital = Integer.parseInt(request.getParameter("idhospital"));
            int idusuario = Integer.parseInt(request.getParameter("idusuario"));
            int idservicio = Integer.parseInt(request.getParameter("idservicio")); 
            int bandera = Integer.parseInt(request.getParameter("1"));
            ServiciosMedicos sm = new ServiciosMedicos();
            String encriptado = null;
            sm.setIdServicio(idservicio);
            ServiciosMedicosCRUD crud = new ServiciosMedicosCRUD();
            List<ServiciosMedicos> lista = crud.read(sm);
        %>
        <%if(bandera==1){%>
            <script>
                alert("Dato existente");
            </script>
        <%}%>
        <div class="container-contact100">
            <div class="wrap-contact100">
                <form  action = "../ActualizarDatosServiciosMedicos" class="contact100-form validate-form" id="smart-form">
                    <span class="contact100-form-title" style="font-family: Verdana; letter-spacing: 0.25em;">
                        <h1><strong>Actualizar servicio médico</strong></h1>
                    </span>


                    <div class="wrap-input100 bg1" >
                        <span class="label-input100">Servicio médico *</span>
                        <input class="input100" type="text" name = "ServicioMedico" maxlength="90" value="<%=lista.get(0).getTipo()%>"
                        minlength="5" maxlength="50" required pattern="[A-Za-z áéíóúÁÉÍÓÚñÑ,.]+"
                        title="<h6 style='color:red; font-size:15px'>Sólo letras y espacios, tamaño mínimo: 5, tamaño máximo: 50.</h6>"
                        data-rule-required="true" 
                        required="true"
                        data-msg-required="<h6 style='color:red; font-size:15px'>Por favor ingresa el servicio médico</h6>" >
                    </div>
                    
                    <div class="wrap-input100 bg1" >
                        <span class="label-input100">Precio *</span>
                        <input class="input100" type="text" name = "ServicioMedicoPrecio" maxlength="9" 
                               value="<% if(lista.get(0).getPrecio() == null){
                                            out.println("0");
                                        }else{
                                            out.println(lista.get(0).getPrecio());
                                        }%>">
                    </div>
                    <input type ="hidden" name = "idservicio" value="<%=idservicio%>">
                    <input type ="hidden" name = "idusuario" value="<%=idusuario%>">
                    <input type ="hidden" name = "idhospital" value="<%=idhospital%>">
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
                                    
                                <a  class="contact100-form-btn" href="/Prototipo4_1/Paginas/PerfilAdmon.jsp?id=<%=encriptado%>#Servicios">Regresar</a>
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
