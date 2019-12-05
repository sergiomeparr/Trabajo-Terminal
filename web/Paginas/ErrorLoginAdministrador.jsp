<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>T-Cuido</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" href="CSS/pantalla_responsiva2.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
        <link rel="icon" type="image/png" href="../Imagenes/Tcuido4.png"/>
        <style type="text/css">
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
                border-color: #BCE8FL;
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
            .enlaces-login{
                padding-top: 5px;
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
                        <img src="../Imagenes/Tcuido2.png" width = "80px">
                    </div>
                </div>
            </div>
        </nav>
        <div class="container">
            <div class="text-center">
                <div class="modal-dialog">
                    <h2>Iniciar sesión administrador del hospital</h2>
                    <div class="col-lg-12 col-sm-12 col-12 main-section">
                        <div class="col-lg-12 col-sm-12 col-12 user-img">
                            <img class="icono-img" src="https://image.flaticon.com/icons/png/512/16/16480.png">
                        </div>
                        <div class="panel-group">
                            <div class="panel panel-info" style = "border:gray 1px solid">
                                <div class="panel-heading titulo-panel"  style = "background:gray"></div>
                                <div class="panel-body contenido-panel">
                                <br><br>
                                    <div class="col-lg-12 col-sm-12 col-12 form-input">
                                        <form class="border p-3 form" method="get" action="../BuscarAdministrador">
                                            <div class="form-group row">
                                                <label class="col-sm-3 col-form-label" for="email" style = "padding-top:10px">Correo:</label>
                                                <div class="col-sm-9">
                                                    <input type="text" name="txtUsuario" id="email" class="form-control" placeholder="Ingresa tu correo electrónico">
                                                </div>
                                            </div>
                                            <div class="form-group row">
                                                <label class="col-sm-3 col-form-label" for="pwd" style = "padding-top:10px">Contraseña:</label>
                                                <div class="col-sm-9">
                                                    <input type="password" name="txtContrasena" id="pwd" class="form-control" placeholder="Ingresa tu contraseña">
                                                </div>
                                            </div>
                                            <p style = "color:red"> Correo y contraseña incorrectos
                                            
                                            <div class="form-row">
                                                <div class="form-group col-md-12">
                                                    <div class="form-group col-md-3">
                                                        <button type="submit" class="btn btn-default">Iniciar sesión</button>
                                                    </div>
                                                    <div class="form-group col-md-9 enlaces-login">
                                                        <label class="registro"><a href="DatosAdministrador.jsp?1=0">Registrarse</a></label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                        <label class="colvidada"><a href="ContrasenaOlvidadaAdmon.jsp">Contraseña olvidada</a></label>
                                                    </div>
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
