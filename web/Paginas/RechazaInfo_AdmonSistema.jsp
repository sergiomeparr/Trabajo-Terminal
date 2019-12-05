<%-- 
    Document   : RechazaInfo_ABE
    Created on : 24/08/2019, 08:33:21 PM
    Author     : leoip
--%>

<%@page import="com.java.controlador.EnviarCorreoAdmon"%>
<%@page import="com.java.controlador.EnviarCorreo"%>
<%@page import="com.java.controlador.UsuarioDAO"%>
<%@page import="com.java.bean.modelo.Usuario"%>
<%@page import="com.java.controlador.AdministradorDAO"%>
<%@page import="com.java.bean.modelo.Administrador"%>
<%@page import="com.java.controlador.ServiciosMedicosCRUD"%>
<%@page import="com.java.bean.modelo.ServiciosMedicos"%>
<%@page import="com.java.controlador.MedicosCRUD"%>
<%@page import="com.java.bean.modelo.Medicos"%>
<%@page import="com.java.controlador.HospitalCRUD"%>
<%@page import="com.java.bean.modelo.Hospitales"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="java.sql.*,java.util.*"%>

<%

    String idhospital = request.getParameter("id_reg");
    Hospitales h =  new Hospitales();
    HospitalCRUD hcrud =  new HospitalCRUD();
    Medicos m = new Medicos();
    MedicosCRUD mcrud =  new MedicosCRUD();
    ServiciosMedicos sm =  new ServiciosMedicos();
    ServiciosMedicosCRUD smcrud =  new ServiciosMedicosCRUD();
    Administrador a =  new Administrador();
    AdministradorDAO acrud =  new AdministradorDAO();
    Usuario u =  new Usuario();
    UsuarioDAO ucrud =  new UsuarioDAO();
    
    sm.setIdHospital(Integer.parseInt(idhospital));
    smcrud.eliminar(sm);
    
    a.setIdHospital(Integer.parseInt(idhospital));
    a = acrud.leer_por_hospital(a);
    
    u.setIdUsuario(a.getIdUsuario());
    u = ucrud.read(u);
    
    m.setIdHospital(Integer.parseInt(idhospital));
    mcrud.eliminar(m);
    
    h.setIdHospital(Integer.parseInt(idhospital));
    h = hcrud.LeerHospital_NoVerificado(h);
    
    String Asunto = "Se dio de baja tu hospital:" + h.getNombre();
    String Correo = u.getUsuario();
    EnviarCorreoAdmon ec =  new EnviarCorreoAdmon();
    ec.enviarMail(Correo, Asunto, 2);
    
    hcrud.eliminar(h);
    ucrud.eliminar(u);
    acrud.eliminar(a);
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
                min-height: 60vh;
                padding: 100px 25px 10px 25px;
            }
            .estilo-enlace{
                font-size: 20px;
                font-weight: bold;
                background-color: blue;
                color: white;
                text-decoration: none;
                padding: 8px;
                border-radius: 5px;
                border-color: blue;
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
            .borde-imagen{
                border-radius: 35%;
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
                    <a class="navbar-brand titulo-adminbe" style="color:white;" href="PerfilAdmonSistema.jsp">
                    Administrador del sistema</a>
                    <img style="padding-top:10px;" class="img-responsive img-circle borde-imagen"
                    src="../Imagenes/Tcuido2.png"
                    width="75px">
                </div>
                <div id="navbar" class="navbar-collapse collapse" aria-expanded="false" style="height: 1px;">
                    <ul class="nav navbar-nav navbar-right">
                        <li>
                            <a class="titulos-navbar" href="VerPerfilAdmonSistema.jsp">Ver perfil</a>
                        </li>
                        <li>
                            <a class="titulos-navbar" href="RegDisponibles_AdmonSistema.jsp">Registros pendientes</a>
                        </li>
                        <li><a class="titulos-navbar" href="LoginAdministradorSistema.jsp">Cerrar sesión</a></li>
                    </ul>
                </div>
            </div>
        </nav>
        <div class="container">
            <div class="abs-center">
                <center>
                    <h1><b>Rechazar datos - administrador del hospital</b></h1>
                    <br>
                    <img class="img-responsive img-circle borde-imagen" 
                         src="../Imagenes/rechazar.png" width="150" height="150">
                </center>
            </div>
            <div>
                <center>
                    <h3>El registro ha sido rechazado del sistema "T-cuido"</h3>
                    <br>
                    <a class="estilo-enlace" href="RegDisponibles_AdmonSistema.jsp"
                        style="color:white;text-decoration:none;">
                        Regresar - registros pendientes
                    </a>
                    <br><br>
                </center>
            </div>
        </div>
    </body>
</html>
