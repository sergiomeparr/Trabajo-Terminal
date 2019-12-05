<%-- 
    Document   : PerfilAdmonBE
    Created on : 18/08/2019, 06:06:03 PM
    Author     : leoip
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
                padding: 110px 25px 10px 25px;
            }
            .boton-navbar{
                padding: 15px 15px;
            }
            .enlace-principal{
                padding: 22px 15px;
                font-size: 14px;
                font-weight: bold;
                text-align: center;
                background-color: transparent;
            }
            .enlaces-navbar{
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
                    <button type="button" class="navbar-toggle collapsed boton-navbar" data-toggle="collapse" data-target="#navbar"
                            aria-expanded="false" aria-controls="navbar" style="border:2px solid white;">
                        <span class="sr-only"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand enlace-principal" style="color:white;" href="PerfilAdmonSistema.jsp">
                    Administrador del sistema </a>
                    <img style="padding-top:10px;" class="img-responsive img-circle borde-imagen"
                         src="../Imagenes/Tcuido2.png"
                         width="75px">
                </div>
                <div id="navbar" class="navbar-collapse collapse" aria-expanded="false" style="height: 1px;">
                   <ul class="nav navbar-nav navbar-right">
                        <li>
                            <a class="enlaces-navbar" href="VerPerfilAdmonSistema.jsp">Ver perfil</a>
                        </li>
                        <li>
                            <a class="enlaces-navbar" href="RegDisponibles_AdmonSistema.jsp">Registros pendientes</a>
                        </li>
                        <li><a class="enlaces-navbar" href="LoginAdministradorSistema.jsp">Cerrar sesión</a></li>
                    </ul>
                </div>
            </div>
        </nav>
        <div class="container">
            <div class="abs-center">
                <center>
                    <h1><b>Administrador del sistema</b></h1>
                    <br>
                    <img class="img-responsive img-circle borde-imagen"
                         src="../Imagenes/plantillaHospital.jpg"
                         width="400" height="400">
                </center>
            </div>
            <div>
                <center>
                    <p class="lead">
                        Módulo para verificar la información de:
                        <br>
                        &bull; Administradores de hospital
                        <br>
                        &bull; Hospitales particulares
                    </p>
                </center>
            </div>
        </div>
    </body>
</html>
