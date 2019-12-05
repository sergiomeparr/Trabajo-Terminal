<%-- 
    Document   : Verifica_RegistroAdminHospital
    Created on : 22/08/2019, 05:31:03 PM
    Author     : leoip
--%>

<%@page import="com.java.controlador.EnviarCorreoAdmon"%>
<%@page import="com.java.controlador.UsuarioDAO"%>
<%@page import="com.java.bean.modelo.Usuario"%>
<%@page import="com.java.controlador.AdministradorDAO"%>
<%@page import="com.java.controlador.HospitalCRUD"%>
<%@page import="com.java.bean.modelo.Hospitales"%>
<%@page import="com.java.bean.modelo.Administrador"%>
<!--%@page contentType="text/html" pageEncoding="UTF-8"%-->

<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>

<%
    
    String idhospital = request.getParameter("id_reg");
    Administrador a =  new Administrador();
    AdministradorDAO acrud = new AdministradorDAO();
    
    Usuario u = new Usuario();
    UsuarioDAO ucrud =  new UsuarioDAO();
    
    Hospitales h =  new Hospitales();
    HospitalCRUD hcrud = new HospitalCRUD();
    a.setIdHospital(Integer.parseInt(idhospital));
    a = acrud.leer_por_hospital(a);
    
    h.setIdHospital(Integer.parseInt(idhospital));
    h = hcrud.LeerHospital_NoVerificado(h);
    
    u.setIdUsuario(a.getIdUsuario());
    u = ucrud.read(u);
    
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>T-Cuido</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
        <link rel ="icon" type ="image/png" href="../Imagenes/Tcuido4.png"/>
        <style type="text/css">
            .abs-center{
                display: flex;
                align-items: center;
                justify-content: center;
                min-height: 40vh;
                padding: 100px 25px 10px 25px;
            }
            .boton-navbar{
                padding: 15px 15px;
            }
            .titulo-adminbe{
                padding: 22px 15px;
                font-size: 14px;
                font-weight: bold;
                text-align: center;
                background-color: transparent;
            }
            .titulos-navbar{
                font-size: 18px;
                text-align: center;
                background-color: transparent;
            }
            .btn{
                font-size: 20px;
                font-weight: bold;
                color: white;
            }
            .boton-aceptar{
                background-color: #1DAF1D;
                border-color: #1DAF1D;
            }
            .boton-cancelar{
                background-color: silver;
                border-color: silver;
            }
            .boton-rechazar{
                background-color: red;
                border-color: red;
            }
            .wrap-contact100 {
                background: #FFFFC1;
                border-style: solid;
                border-radius: 10px;
                border-color: #E6D690;
                overflow: hidden;
            }
            .contenido-formulario{
                padding-right: 15px;
                padding-left: 15px;
            }
            .estilo-label{
                background-color: #244623;
		color: white;
                padding: 8px;
		border-radius: 10px 10px 0px 0px;
            }
            .plantilla-label{
                font-size: 25px;
            }
            .estilo-input{
                height: 50px;
                font-size: 25px;
                border-radius: 0px 0px 10px 10px;
                border-color: black;
                text-align: center;
            }
            .estilo-enlace{
                padding: 10px 10px;
                border-radius: 5px;
                font-weight: bold;
                font-size: 20px;
            }
            .borde-imagen{
                border-radius: 15%;
            }
        </style>
    </head>
    <body>
        <nav class="navbar navbar-inverse navbar-fixed-top">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed boton-navbar"
                            data-toggle="collapse" data-target="#navbar"
                            aria-expanded="false" aria-controls="navbar" style="border:2px solid white;">
                        <span class="sr-only"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand titulo-adminbe" href="PerfilAdmonSistema.jsp" style="color:white;">
                    Administrador del sistema</a>
                    <img style="padding-top:10px;" class="img-responsive img-circle borde-imagen"
                    src="../Imagenes/Tcuido2.png"
                    width="75px">
                </div>
                <div id="navbar" class="navbar-collapse collapse" aria-expanded="false" style="height: 1px;">
                    <ul class="nav navbar-nav navbar-right">
                        <li>
                            <a class="titulos-navbar" href="VerPerfilAdmonSistema.jsp">
                                Ver perfil</a>
                        </li>
                        <li>
                            <a class="titulos-navbar" href="RegDisponibles_AdmonSistema.jsp">Registros pendientes</a>
                        </li>
                        <li><a class="titulos-navbar" href="LoginAdministradorSistema.jsp">Cerrar sesión</a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
        <div class="container">
            <div class="abs-center">
                <center>
                    <h1><b>Verificar información - administrador del hospital</b></h1>
                    <br>
                    <img class="img-responsive borde-imagen"
                         src="../Imagenes/examinainfo.png" width="120" height="120">
                </center>
            </div>
            <div class="contenido-formulario wrap-contact100">
                <br>
                <form method="post" action="../AprobarDatosHospital">
                    <center>
                        <h2><b>Datos personales</b></h2>
                    </center>
                    <br>
                    <input type="hidden" name="id_reg" value="<%=idhospital%>">
                    <div class="form-row">
                        <div class="form-group col-md-12">
                            <div class="text-center estilo-label">
                                <label class="plantilla-label" for="nombre_AdmH">Nombre(s)</label>
                            </div>
                            <input type="text" class="form-control input-lg estilo-input" id="nombre_AdmH" name="nom_admh" value="<%=a.getNombreAdministrador()%>" readonly>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <div class="text-center estilo-label">
                                <label class="plantilla-label" for="ap_AdmH">Primer apellido</label>
                            </div>
                            <input type="text" class="form-control input-lg estilo-input" id="ap_AdmH" name="ap_admh" value="<%=a.getApellidoPaterno()%>" readonly>
                        </div>
                        <div class="form-group col-md-6">
                            <div class="text-center estilo-label">
                                <label class="plantilla-label" for="am_AdmH">Segundo apellido</label>
                            </div>
                            <input type="text" class="form-control input-lg estilo-input" id="am_AdmH" name="am_admh" value="<%=a.getApellidoMaterno()%>" readonly>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <div class="text-center estilo-label">
                                <label class="plantilla-label" for="telefono_AdmH">Teléfono</label>
                            </div>
                            <input type="text" class="form-control input-lg estilo-input" id="telefono_AdmH" name="tel_admh" value="<%=a.getTelefono()%>" readonly>
                        </div>
                        <div class="form-group col-md-6">
                            <div class="text-center estilo-label">
                                <label class="plantilla-label" for="us_AdmH">Correo electrónico</label>
                            </div>
                            <input type="text" class="form-control input-lg estilo-input" id="us_AdmH" name="usr_admh" value="<%=u.getUsuario()%>" readonly>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-12">
                            <label><!--Equivalente a un <br><br>--></label>
                        </div>
                    </div>
                    <center>
                        <h2><b>Datos del hospital</b></h2>
                    </center>
                    <br>
                    <div class="form-row">
                        <div class="form-group col-md-12">
                            <div class="text-center estilo-label">
                                <label class="plantilla-label" for="nombre_Hospital">Hospital donde labora</label>
                            </div>
                            <input type="text" class="form-control input-lg estilo-input" id="nombre_Hospital" name="nom_h" value="<%=h.getNombre()%>" readonly>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <div class="text-center estilo-label">
                                <label class="plantilla-label" for="calle_Hospital">Calle</label>
                            </div>
                            <input type="text" class="form-control input-lg estilo-input" id="calle_Hospital" name="calle_h" value="<%=h.getDireccion().substring(h.getDireccion().indexOf(" "), h.getDireccion().indexOf("No"))%>" readonly>
                        </div>
                        <div class="form-group col-md-6">
                            <div class="text-center estilo-label">
                                <label class="plantilla-label" for="numero_Hospital">Número</label>
                            </div>
                            <input type="text" class="form-control input-lg estilo-input" id="numero_Hospital" name="num_h" value="<%=h.getDireccion().substring(h.getDireccion().indexOf("No.")+4, h.getDireccion().indexOf("Col"))%>" readonly>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <div class="text-center estilo-label">
                                <label class="plantilla-label" for="alcaldia_Hospital">Alcaldía</label>
                            </div>
                            <input type="text" class="form-control input-lg estilo-input" id="alcaldia_Hospital" name="alc_h" value="<%=h.getDireccion().substring(h.getDireccion().indexOf("C.P.")+12, h.getDireccion().indexOf("CDMX")-2)%>" readonly>
                        </div>
                        <div class="form-group col-md-6">
                            <div class="text-center estilo-label">
                                <label class="plantilla-label" for="colonia_Hospital">Colonia</label>
                            </div>
                            <input type="text" class="form-control input-lg estilo-input" id="colonia_Hospital" name="col_h" value="<%=h.getDireccion().substring(h.getDireccion().indexOf("Col.")+5, h.getDireccion().indexOf("C.P")-2)%>" readonly>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <div class="text-center estilo-label">
                                <label class="plantilla-label" for="cp_Hospital">Código postal</label>
                            </div>
                            <input type="text" class="form-control input-lg estilo-input" id="cp_Hospital" name="cp_h" value="<%=h.getDireccion().substring(h.getDireccion().indexOf("C.P.")+5, h.getDireccion().indexOf("C.P")+10)%>" readonly>
                        </div>
                        <div class="form-group col-md-6">
                            <div class="text-center estilo-label">
                                <label class="plantilla-label" for="telefono_Hospital">Teléfono</label>
                            </div>
                            <input type="text" class="form-control input-lg estilo-input" id="telefono_Hospital" name="tel_h" value="<%=h.getTelefono()%>" readonly>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-12">
                            <div class="text-center estilo-label">
                                <label class="plantilla-label" for="pw_Hospital">Página web del hospital</label>
                            </div>
                            <input type="text" class="form-control input-lg estilo-input" id="pw_Hospital" name="pw_h" value="<%=h.getPagina()%>" readonly>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-12">
                            <label><!--Equivalente a un <br><br>--></label>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-12">
                            <label><!--***Equivalente a un <br><br>--></label>
                        </div>
                    </div>
                    
                    <div class="form-row text-center">
                        <div class="form-group col-md-4">
                            <button OnClick="if (! confirm('Estás a punto de aceptar una solicitud de registro')) return false;" type="submit" value="submit" class="btn btn-outline-info active
                                boton-aceptar" style="color:white;">
                                <span class="glyphicon glyphicon-ok"></span>
                                Aprueba datos
                            </button>
                        </div>
                        <br>
                        <div class="form-group col-md-4" style="padding-top:7px;">
                            <a class="estilo-enlace boton-cancelar" OnClick="if (! confirm('Estás a punto de cancelar una solicitud de registro')) return false;" href="RegDisponibles_AdmonSistema.jsp"
                               style="color:white;text-decoration:none;" >
                                <span class="glyphicon glyphicon-remove"></span> Cancelar
                            </a>
                        </div>
                        <br>
                        <div class="form-group col-md-4" style="padding-top:7px;">
                            <a class="estilo-enlace boton-rechazar" OnClick="if (! confirm('Estás a punto de rechazar una solicitud de registro')) return false;" href="RechazaInfo_AdmonSistema.jsp?id_reg=<%=idhospital%>"
                               style="color:white;text-decoration:none;">
                                <span class="glyphicon glyphicon-remove"></span> Rechaza datos
                            </a>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-12">
                            <label><!--Equivalente a un <br><br>--></label>
                        </div>
                    </div>
                </form>                       
            </div>
            <br><br>
        </div>
    </body>
</html>
