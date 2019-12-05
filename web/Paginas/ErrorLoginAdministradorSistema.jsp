<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>T-Cuido</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" href="../CSS/pantalla_responsiva2.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
        <link rel ="icon" type ="image/png" href="../Imagenes/Tcuido4.png"/>
        <style>
            .main-section{
                margin: 0 auto;
                margin-top: 120px;
                background-color: #FFF;
                border-radius: 5px;
                padding: 0px;
            }
            .user-img{
                margin-top: -65px;
            }
            .icono-img{
                height: 100px;
                width: 100px;
            }
            .panel-group>.panel{
                border-radius: 10px;
                padding-bottom: 8px;
                border-color: gray;
            }
            .panel>.panel-heading{
                background-image: none;
                background-color: gray;
            }
            .panel-info>.panel-heading{
                border-radius: 10px 10px 0px 0px;
                border-color: #BCE8FL;
                padding-top: 30px;
            }
            .panel-info>.panel-body{
                padding-top: 0px;
                padding-bottom: 0px;
            }
        </style>
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
            <div class="container">
                <div class="navbar-header">
                    <div class="dropdown">
                        <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">
                            <img src="../Imagenes/img_avatar1.png" class="img-circle" width = "35px" > Tipo de usuario
                            <span class="caret"></span>
                        </button>
                        <ul class="dropdown-menu">
                            <li><a href="LoginPaciente.jsp">Paciente</a></li>
                            <li><a href="LoginAdministrador.jsp">Administrador del hospital</a></li>
                            <li><a href="LoginAdministradorSistema.jsp">Administrador del sistema</a></li>
                            <li><a href="../Index.jsp">Página de inicio</a></li>
                        </ul>
                        <img src="../Imagenes/Tcuido2.png" width = "80px" >
                    </div>
                </div>
            </div>
        </nav>
        <div class="container">
            <div class="text-center">
                <div class="modal-dialog">
                    <h1>Iniciar sesión administrador del sistema</h1>
                    
                    <div class="col-lg-12 col-sm-12 col-12 main-section">
                        <div class="col-lg-12 col-sm-12 col-12 user-img">
                            <img class="icono-img" src="../Imagenes/admonbe.png">
                        </div>
                        <div class="panel-group">
                            <div class="panel panel-info">
                                <div class="panel-heading titulo-panel"></div>
                                <div class="panel-body contenido-panel">
                                    <br>
                                    <div class="col-lg-12 col-sm-12 col-12 form-input">
                                        <form class="border p-3 form" method = "get" action = "../BuscarAdministradorSistema">
                                            <!--<h3>Administrador de Back-End</h3>-->
                                            <br>
                                            <div class="form-group row">
                                                <label class="col-sm-2 col-form-label text-left" for="usuario" style = "font-size:20px">Clave:</label> 
                                                <div class="col-sm-9">
                                                    <input type="password" name="txtUsuario" id="usuario" class="form-control" placeholder="Ingresa la clave/llave...">
                                                    <!--id="email"-->
                                                </div>
                                            </div>
                                            <p class="error-login" style = "color:red">Clave incorrecta, vuelva a intentarlo</p>
                                            <div class="form-row">
                                                <div class="form-group col-md-12">
                                                    <button type="submit" class="btn btn-default">Ingresar</button>
                                                </div>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
