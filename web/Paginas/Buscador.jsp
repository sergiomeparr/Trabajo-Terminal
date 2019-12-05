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
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
       
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="author" content="colorlib.com">
        <link href="https://fonts.googleapis.com/css?family=Poppins" rel="stylesheet" />
        <link href="../CSS/main.css" rel="stylesheet" />
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
        
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>

        <link rel ="icon" type ="image/png" href="../Imagenes/Tcuido4.png"/>

        <style>
            body {font-family: Arial, Helvetica, sans-serif;}
            * {box-sizing: border-box;}

            .bg-img {
                min-height: 380px;

                background-position: center;
                background-repeat: no-repeat;
                background-size: cover;

                position: relative;
            }

            .container {
                position: absolute;
                margin: 20px;
                width: auto;
            }

            .topnav {
                overflow: hidden;
                background-color: #fff;
            
            }

            .topnav a {
                float: left;
                color: #f2f2f2;
                text-align: center;
                padding: 14px 16px;
                text-decoration: none;
                font-size: 17px;
            }

            .topnav a:hover {
                background-color: #ddd;
                color: white;
            }

            * {box-sizing: border-box;}

            body { 
                margin: 0;
                font-family: Arial, Helvetica, sans-serif;
            }

            .header {
                overflow: hidden;
                background-color: #2874A6;
            }

            .header a {
                float: left;
                color: black;
                text-align: center;
                padding: 20px;
                text-decoration: none;
                font-size: 18px; 
                line-height: 25px;
                border-radius: 4px;
            }
            
            .header .img{
                padding-left: 100px;
            }
            
            .header a:hover {
                background-color: #2E86C1;
                color: black;
            }

            .header a.active {
                background-color: inherit;
                color: black;
            }

            .header-right {
                float: right;
            }

            @media screen and (max-width: 500px) {
                .header a {
                    float: none;
                    display: block;
                    text-align: left;
                }

                .header-right {
                    float: none;
                }
            }


            .header-right .logo{
                text-align:left;
            }
            .dropdown {
                float: right;
                padding: 10px;
            }

            .dropdown .dropbtn {
                font-size: 17px;    
                border: none;
                outline: none;
                color: black;
                padding: 14px 16px;
                background-color: inherit;
                font-family: #ddd;
                margin: 0;
            }

            .dropdown-content {
                display: none;
                position: absolute;
                background-color: #f9f9f9;
                min-width: 160px;
                box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
                z-index: 1;
            }

            .dropdown-content a {
                float: none;
                color: black;
                text-decoration: none;
                display: block;
                text-align: left;
            }

            .topnav a:hover, .dropdown:hover .dropbtn {
                background-color: #ddd;
                color: black;
            }

            .dropdown-content a:hover {
                background-color: #ddd;
                color: black;
            }

            .dropdown:hover .dropdown-content {
                display: block;
            }
        </style>

    </head>
    <body>
        <%//int id = Integer.parseInt(request.getParameter("id"));
            String identi = request.getParameter("id");
            if(identi.contains(" ")){
                identi = identi.replace(" ", "+");
            }
            
            int id = 0;
            if(identi.length() > 5){
                try {
                        final String claveEncriptacion = "secreto!";            
                        EncriptadorAES encriptador = new EncriptadorAES();
                        String desencriptado = encriptador.desencriptar(identi, claveEncriptacion);
                        id = Integer.parseInt(desencriptado);
                } catch (UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException ex) {
                    Logger.getLogger(EncriptadorAES.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                id = Integer.parseInt(identi);
            }
        %>
        
        <nav class="navbar navbar-expand-lg navbar-dark bg-primary"> 
            <div class="container-fluid">
                <div class="navbar-header">
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="../Imagenes/Tcuido2.png" width = "75px">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar" style = "border-color:white;">
                        <span class="icon-bar" style = 'background-color: white'></span>
                        <span class="icon-bar" style = 'background-color: white'></span>
                        <span class="icon-bar" style = 'background-color: white'></span>
                    </button>
                </div>
                
                <div class="collapse navbar-collapse" id="myNavbar"  align="center">
                    <ul class="nav navbar-nav navbar-right">
                        <li>
                            <form action="/Prototipo4_1/Paginas/VerPerfil.jsp" method ="post">
                                <input type ="hidden" name="idusuario" value ='<%=id%>'>
                                <button style="background-color:transparent; border:none;" role="link" width="10%">
                                    <h6 style="color:white;padding-top:0px;font-size:20px;text-align:center;background-color:transparent;">Ver perfil</h6>
                                </button>
                            </form>
                        </li>
                        <li>
                            <form action="/Prototipo4_1/Paginas/ActualizacionDatosPaciente.jsp" method ="post"  align="center">
                                <input type ="hidden" name="idusuario" value ='<%=id%>'>
                                <button style="background-color:transparent; border:none;" role="link" width="10%">
                                    <h6 style="color:white;padding-top:0px;font-size:20px;text-align:center;background-color:transparent;">Configuración</h6>
                                </button>
                            </form>
                        </li>
                        <li><a style="color:white;padding-top:12px;font-size:20px;text-align:center;background-color:transparent;" href="LoginPaciente.jsp">Cerrar sesión</a></li>
                    </ul>
                </div>
            </div>
        </nav>
        <div class="s01">
            <form action="../BuscarDatos" method = "post">
                <div class="inner-form">
                    <div class="input-field first-wrap">
                        <input id="search" type="text" name = "txtbuscar" placeholder="p. ej. Hospital los Ángeles, cardiología, urgencias" />
                        <input type ="hidden" name="id" value ='<%=id%>'>
                        <input type ="hidden" name="rankeo" value =''>
                        <input type ="hidden" name="especialidad" value =''>
                        <input type ="hidden" name="servicios" value =''>
                        <input type ="hidden" name="cercania" value =''>
                    </div>
                        &nbsp;&nbsp;
                    <div class="input-field third-wrap">
                        <input class="btn-search" type="submit" value = "Buscar">
                    </div>
                </div>
            </form>
        </div>
    </body>
</html>
