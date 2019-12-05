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
                        <img src="../Imagenes/Tcuido2.png" width = "80px" >
                    </div>
                </div>
            </div>
        </nav>


        <div class="container">
            <div class="text-center">
                <div class="modal-dialog">
                    <div class="col-lg-12 col-sm-12 col-12 main-section">
                        <div class="panel-group">
                            <div class="panel panel-info">
                                <div class="panel-heading titulo-panel" style = "font-size: 20px;" align = "left">Recuperar contraseña</div>
                                <div style="float:right; font-size: 80%; position: relative; top:-20px"><a href="LoginAdministrador.jsp">Iniciar sesi&oacute;n</a></div>
                                <div class="panel-body contenido-panel">
                                    <br><br>
                                    <div class="col-lg-12 col-sm-12 col-12 form-input">
                                        <form id="loginform" class="form-horizontal" action="../EnviarCorreoAdmon" method="post">

                                            <div  class="input-group" >
                                                <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                                                <input id="email" type="email" class="form-control col-sm" name="Email" placeholder="email" required>                                        
                                            </div>
                                            <div style="margin-top:10px" class="form-group">
                                                <div class="col-sm-12 controls" align = "left">
                                                    <button id="btn-login" type="submit" class="btn btn-success">Enviar</button>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="col-md-12 control" align ="left">
                                                    <div style="border-top: 1px solid#888; padding-top:15px; font-size:85%" >
                                                        No tiene una cuenta! <a href="DatosAdministrador.jsp?1=0">Registrate aquí</a>
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
