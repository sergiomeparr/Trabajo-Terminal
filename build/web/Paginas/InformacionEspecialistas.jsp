<%@page import="com.java.bean.modelo.Hospitales"%>
<%@page import="com.java.controlador.HospitalCRUD"%>
<%@page import="com.java.bean.modelo.Paciente"%>
<%@page import="com.java.controlador.PacienteDAO"%>
<%@page import="com.java.bean.modelo.RankeoMedicos"%>
<%@page import="com.java.controlador.RankeoMedicosCRUD"%>
<%@page import="java.util.List"%>
<%@page import="com.java.controlador.MedicosCRUD"%>
<%@page import="com.java.bean.modelo.Medicos"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>T-Cuido</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>

        <link rel ="icon" type ="image/png" href="../Imagenes/Tcuido4.png"/>

        <style>
            html {  
                overflow-x:hidden;
            }
            /* Remove the navbar's default margin-bottom and rounded borders */ 
            .navbar {
                margin-bottom: 0;
                border-radius: 0;
            }

            /* Set height of the grid so .sidenav can be 100% (adjust as needed) */
            .row.content {height: 1000px}

            /* Set gray background color and 100% height */
            .sidenav {
                padding-top: 20px;
                background-color: #f1f1f1;
                height: 134%;
                width: 20%;
            }

            /* Set black background color, white text and some padding */
            footer {
                background-color: #555;
                color: white;
                padding: 15px;
            }

            /* On small screens, set height to 'auto' for sidenav and grid */
            @media screen and (max-width: 1000px) {
                .sidenav {
                    height: auto;
                    padding: 15px;
                }
                .row.content {height:auto;} 
            }
        </style>
    </head>
    <body>
        <%

            int idmedico = Integer.parseInt(request.getParameter("idmedico"));
            int idusuario = Integer.parseInt(request.getParameter("idusuario"));
            int idhospital = Integer.parseInt(request.getParameter("idhospital"));

            //Variable para datos del médico
            String nombre, paterno, materno, cedula, historial, telefono, email, especialidad;
            String comentarios;
            int estrellas, todos_usuarios;

            //Obtener datos de los comentarios que han deja del médico
            RankeoMedicosCRUD rankeo = new RankeoMedicosCRUD();
            RankeoMedicos r = new RankeoMedicos();
            r.setIdHospital(idhospital);
            r.setIdMedico(idmedico);
            List<RankeoMedicos> lista2 = rankeo.readAll(r);

            //Obtención de datos del usuario por su identificador
            Medicos m = new Medicos();
            MedicosCRUD mcrud = new MedicosCRUD();
            m.setIdMedicos(idmedico);
            List<Medicos> lista = mcrud.readMedicos(m);
            Medicos listaMedicos = (Medicos) lista.get(0);
            nombre = listaMedicos.getNombreMedico();
            paterno = listaMedicos.getAPaternoMedico();
            materno = listaMedicos.getAMaternoMedico();
            cedula = listaMedicos.getCedulap();
            historial = listaMedicos.getHistorial();
            email = listaMedicos.getEmail();
            especialidad = listaMedicos.getEspecialidades();
            telefono = listaMedicos.getTelefonos();

            Hospitales h = new Hospitales();
            HospitalCRUD hcrud = new HospitalCRUD();
            h.setIdHospital(idhospital);
            Hospitales lista3 = hcrud.readOne(h);

            PacienteDAO pcrud = new PacienteDAO();
            Paciente p1 = new Paciente();
            if (idusuario != 0) {
                p1 = pcrud.seleccionar_informacion(idusuario);
            }
        %>


        <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed boton-navbar" data-toggle="collapse" data-target="#navbar"
                            aria-expanded="false" aria-controls="navbar" style="border:2px solid white;">
                        <span class="sr-only"></span>
                        <span class="icon-bar" style = "background-color:white"></span>
                        <span class="icon-bar" style = "background-color:white"></span>
                        <span class="icon-bar" style = "background-color:white"></span>
                    </button>
                    <%if (idusuario != 0) {%>

                    <span class="dropdown">
                        <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">
                            <img src="../Imagenes/img_avatar1.png" class="img-circle" width = "35px">
                            <span class="caret"></span></button>
                        <ul class="dropdown-menu">
                            <li style="display: inline-block; padding: 0 5px; width: 158px;">
                                <form action='/Prototipo4_1/Paginas/VerPerfil.jsp' method ='post' onmouseover="this.style.background = '#F5F5F5'"
                                      onmouseout="this.style.background = 'white'">
                                    <input type ='hidden' name='idusuario' value ="<%=idusuario%>">
                                    <button style='background-color:transparent; border:none; padding:0'>
                                        <h5 style='color:black;padding-left:15px'>Ver perfil</h5>
                                    </button>
                                </form>
                            <li style="display: inline-block; padding: 0 5px; width: 158px;">
                                <form action='/Prototipo4_1/Paginas/Buscador.jsp' method ='post' onmouseover="this.style.background = '#F5F5F5'"
                                      onmouseout="this.style.background = 'white'">
                                    <input type ='hidden' name='id' value ="<%=idusuario%>">
                                    <button style='background-color:transparent; border:none; padding:0' role='link' width='10%'>
                                        <h5 style='color:black;padding-left:15px'>Página de inicio</h5>
                                    </button>
                                </form>
                            <li><a href="LoginPaciente.jsp">Cerrar sesión</a><hr></li>
                            <li style="display: inline-block; padding: 0 5px; width: 158px;" >
                                <form action='/Prototipo4_1/Paginas/ActualizacionDatosPaciente.jsp' method ='post' onmouseover="this.style.background = '#F5F5F5'"
                                      onmouseout="this.style.background = 'white'">
                                    <input type ='hidden' name='idusuario' value ="<%=idusuario%>">
                                    <button style='background-color:transparent; border:none; padding:0' role='link' width='5%'>
                                        <h5 style='color:black;padding-left:15px'>Configuración</h5>
                                    </button>
                                </form>
                            </li>
                        </ul>
                        <img src="../Imagenes/Tcuido2.png" width = "80px">
                    </span>
                    <%} else {%>
                    <span class="dropdown">
                        <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">
                            <img src="../Imagenes/img_avatar1.png" class="img-circle" width = "35px">
                            <span class="caret"></span></button>
                        <ul class="dropdown-menu">
                            <li style="display: inline-block; padding: 0 5px; width: 158px;">
                                <form action='/Prototipo4_1/Index.jsp' method ='post' onmouseover="this.style.background = '#F5F5F5'"
                                      onmouseout="this.style.background = 'white'">
                                    <button style='background-color:transparent; border:none; padding:0'>
                                        <h5 style='color:black;padding-left:15px'>Página principal</h5>
                                    </button>
                                </form>
                            <li style="display: inline-block; padding: 0 5px; width: 158px;">
                                <form action='/Prototipo4_1/BuscarDatos' method ='post' onmouseover="this.style.background = '#F5F5F5'"
                                      onmouseout="this.style.background = 'white'">
                                    <input type="hidden" name = "txtbuscar" value=''>
                                    <input type ="hidden" name="id" value ='0'>
                                    <input type ="hidden" name="rankeo" value =''>
                                    <input type ="hidden" name="especialidad" value =''>
                                    <input type ="hidden" name="servicios" value =''>
                                    <input type ="hidden" name="cercania" value =''>
                                    <button style='background-color:transparent; border:none; padding:0' role='link' width='10%'>
                                        <h5 style='color:black;padding-left:15px'>Página anterior</h5>
                                    </button>
                                </form>
                            <li>
                        </ul>
                        <img src="../Imagenes/Tcuido2.png" width = "80px">
                    </span>
                    <%}%>
                </div>
                <div id="navbar" class="navbar-collapse collapse" aria-expanded="false" style="height: 1px;">
                    <ul class="nav navbar-nav navbar-right">
                        <li>
                            <form class="form-inline" action = "../BuscarDatos" method="post" style = "padding:7px; text-align: right">
                                <div class="input-group">
                                    <input name = "txtbuscar" type="text" class="form-control mr-sm-2" placeholder="Buscar" name="search">
                                    <div class="input-group-btn">
                                        <button class="btn btn-default" type="submit">
                                            <i class="glyphicon glyphicon-search"></i>
                                        </button>
                                    </div>
                                </div>
                                <input type="hidden" name="id" value="<%=idusuario%>">
                                <input type ="hidden" name="rankeo" value =''>
                                <input type ="hidden" name="especialidad" value =''>
                                <input type ="hidden" name="servicios" value =''>
                                <input type ="hidden" name="cercania" value =''>

                            </form>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>

<!--
        <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                        <span class="icon-bar" style = 'background-color: white'></span>
                        <span class="icon-bar" style = 'background-color: white'></span>
                        <span class="icon-bar" style = 'background-color: white'></span>
                    </button>
                    <%if (idusuario != 0) {%>

                    <div class="dropdown">
                        <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">
                            <img src="../Imagenes/img_avatar1.png" class="img-circle" width = "35px">
                            <span class="caret"></span></button>
                        <ul class="dropdown-menu">
                            <li style="display: inline-block; padding: 0 5px; width: 158px;">

                                <form action='/Prototipo4_1/Paginas/VerPerfil.jsp' method ='post' onmouseover="this.style.background = '#F5F5F5'"
                                      onmouseout="this.style.background = 'white'">
                                    <input type ='hidden' name='idusuario' value ="<%=idusuario%>">
                                    <button style='background-color:transparent; border:none; padding:0'>
                                        <h5 style='color:black;padding-left:15px'>Ver perfil</h5>
                                    </button>
                                </form>
                            <li style="display: inline-block; padding: 0 5px; width: 158px;">
                                <form action='/Prototipo4_1/Paginas/Buscador.jsp' method ='post' onmouseover="this.style.background = '#F5F5F5'"
                                      onmouseout="this.style.background = 'white'">
                                    <input type ='hidden' name='id' value ="<%=idusuario%>">
                                    <button style='background-color:transparent; border:none; padding:0' role='link' width='10%'>
                                        <h5 style='color:black;padding-left:15px'>Página de inicio</h5>
                                    </button>
                                </form>
                            <li><a href="LoginPaciente.jsp">Cerrar sesión</a><hr></li>
                            <li style="display: inline-block; padding: 0 5px; width: 158px;" >
                                <form action='/Prototipo4_1/Paginas/ActualizacionDatosPaciente.jsp' method ='post' onmouseover="this.style.background = '#F5F5F5'"
                                      onmouseout="this.style.background = 'white'">
                                    <input type ='hidden' name='idusuario' value ="<%=idusuario%>">
                                    <button style='background-color:transparent; border:none; padding:0' role='link' width='5%'>
                                        <h5 style='color:black;padding-left:15px'>Configuración</h5>
                                    </button>
                                </form>
                            </li>
                        </ul>
                        <img src="../Imagenes/Tcuido2.png" width = "80px">

                    </div><%} else {%>
                    <div class="dropdown">
                        <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">
                            <img src="../Imagenes/img_avatar1.png" class="img-circle" width = "35px">
                            <span class="caret"></span></button>
                        <ul class="dropdown-menu">
                            <li style="display: inline-block; padding: 0 5px; width: 158px;">
                                <form action='/Prototipo4_1/Index.jsp' method ='post' onmouseover="this.style.background = '#F5F5F5'"
                                      onmouseout="this.style.background = 'white'">
                                    <button style='background-color:transparent; border:none; padding:0'>
                                        <h5 style='color:black;padding-left:15px'>Página principal</h5>
                                    </button>
                                </form>
                            <li style="display: inline-block; padding: 0 5px; width: 158px;">
                                <form action='/Prototipo4_1/BuscarDatos' method ='post' onmouseover="this.style.background = '#F5F5F5'"
                                      onmouseout="this.style.background = 'white'">
                                    <input type="hidden" name = "txtbuscar" value=''>
                                    <input type ="hidden" name="id" value ='0'>
                                    <input type ="hidden" name="rankeo" value =''>
                                    <input type ="hidden" name="especialidad" value =''>
                                    <input type ="hidden" name="servicios" value =''>
                                    <input type ="hidden" name="cercania" value =''>
                                    <button style='background-color:transparent; border:none; padding:0' role='link' width='10%'>
                                        <h5 style='color:black;padding-left:15px'>Página anterior</h5>
                                    </button>
                                </form>
                            <li>
                        </ul>
                        <img src="../Imagenes/Tcuido2.png" width = "80px">
                    </div>
                    <%}%>
                </div>
                <div class="collapse navbar-collapse" id="myNavbar">
                    <form class="form-inline" action = "../BuscarDatos" method="post" style = "padding:7px; text-align: right">
                        <div class="input-group">
                            <input name = "txtbuscar" type="text" class="form-control mr-sm-2" placeholder="Buscar" name="search">
                            <div class="input-group-btn">
                                <button class="btn btn-default" type="submit">
                                    <i class="glyphicon glyphicon-search"></i>
                                </button>
                            </div>
                        </div>
                        <input type="hidden" name="id" value="<%=idusuario%>">
                        <input type ="hidden" name="rankeo" value =''>
                        <input type ="hidden" name="especialidad" value =''>
                        <input type ="hidden" name="servicios" value =''>
                        <input type ="hidden" name="cercania" value =''>

                    </form>
                </div>
            </div>
        </nav>

-->
        <div class="container-fluid">
            <div class="row">
                <div class="col-sm-3 sidenav visible-lg" style = "height:2000px">
                    <%if (idusuario != 0) {
                            out.println("<h4>Usuario: " + p1.getNombrePaciente() + "</h4><br>");
                        }%>
                    <ul class="nav nav-pills nav-stacked">
                        <li><a href="#section1">Perfil médico</a></li>
                        <li><a href="#section2">Historial académico</a></li>
                        <li><a href="#section3">Información médico</a></li>
                        <li><a href="#section4">Comentarios</a></li>
                    </ul><br>
                </div>
                <div class="col-lg-9">
                    <div class="container-sm-7">
                        <br>
                        <section id ="section1">
                            <div class="panel-group">
                                <div class="panel panel-default">
                                    <div class="panel-heading"></div>
                                    <div class="panel-body">
                                        <div class = "container" align = "center">
                                            <div class ="col-md-3">
                                                <img src = "../Imagenes/doctor_default.png" width ="200">
                                            </div>
                                            <div class ="col-sm-5" style = "text-align:left">
                                                <h2><%=nombre%> <%=paterno%> <%=materno%></h2>
                                                <p> Especialidad: <%=especialidad%></p>
                                                <%out.println("<img src ='../Imagenes/" + rankeo.promedio(r) + "estrellas.png' width = '150px'>");
                                                %>
                                                <p> Comentarios: <span class='badge'><%=rankeo.count(r)%></span></p><br>
                                            </div>
                                        </div>    
                                    </div>
                                </div>
                            </div>
                        </section>    
                        <section id ="section2">
                            <div class="container-sm-7">
                                <div class="panel-group">
                                    <div class="panel panel-default">
                                        <div class="panel-heading">Historial académico</div>
                                    </div>
                                </div>
                            </div>

                            <div class="container-sm-7">
                                <div class="panel-group">
                                    <div class="panel panel-default">
                                        <div class="panel-body">
                                            <span class="glyphicon glyphicon-info-sign"></span>
                                            &nbsp;&nbsp;
                                            &nbsp;&nbsp;Estudió : <% if (historial != null) {
                                                    out.println(historial);
                                                } else if (historial == null) {
                                                    out.println("Sin información");
                                                } %>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </section>
                        <section id ="section3">
                            <div class="container-sm-7">
                                <div class="panel-group">
                                    <div class="panel panel-default">
                                        <div class="panel-heading">Información del especialista</div>
                                    </div>
                                </div>
                            </div>

                            <div class="container-sm-7">
                                <div class="panel-group">
                                    <div class="panel panel-default">
                                        <div class="panel-body">
                                            <span class="glyphicon glyphicon-info-sign"></span>
                                            &nbsp;&nbsp;
                                            &nbsp;&nbsp;Teléfono : <% if (telefono != null) {
                                                    out.println(telefono);
                                                } else if (telefono == null) {
                                                    out.println("Sin información");
                                                } %>
                                        </div>
                                    </div>
                                </div>
                            </div>            

                            <div class="container-sm-7">
                                <div class="panel-group">
                                    <div class="panel panel-default">
                                        <div class="panel-body">
                                            <span class="glyphicon glyphicon-info-sign"></span>
                                            &nbsp;&nbsp;
                                            &nbsp;&nbsp;Email : <% if (email != null) {
                                                    out.println(email);
                                                } else if (email == null) {
                                                    out.println("Sin información");
                                                } %>
                                        </div>
                                    </div>
                                </div>
                            </div>      
                            <div class="container-sm-7">
                                <div class="panel-group">
                                    <div class="panel panel-default">
                                        <div class="panel-body">
                                            <span class="glyphicon glyphicon-info-sign"></span>
                                            &nbsp;&nbsp;
                                            &nbsp;&nbsp;Cédula : <% if (cedula != null) {
                                                    out.println(cedula);
                                                } else if (cedula == null) {
                                                    out.println("Sin información");
                                                } %>
                                        </div>
                                    </div>
                                </div>
                            </div>      

                            <div class="container-sm-7">
                                <div class="panel-group">
                                    <div class="panel panel-default">
                                        <div class="panel-body">
                                            <span class="glyphicon glyphicon-info-sign"></span>
                                            &nbsp;&nbsp;
                                            &nbsp;&nbsp;Hospital donde trabaja : <%
                                                if (lista3.getNombre() != null) {
                                                    out.println(lista3.getNombre());
                                                } else if (lista3.getNombre() == null) {
                                                    out.println("Sin información");
                                                } %>
                                        </div>
                                    </div>
                                </div>
                            </div>      
                            <div class="container-sm-7">
                                <div class="panel-group">
                                    <div class="panel panel-default">
                                        <div class="panel-body">
                                            <span class="glyphicon glyphicon-info-sign"></span>
                                            &nbsp;&nbsp;
                                            &nbsp;&nbsp;Página del hospital : <%
                                                if (lista3.getPagina() != null) {
                                                    out.println("<a href =" + lista3.getPagina() + ">" + lista3.getPagina() + "</a>");
                                                } else if (lista3.getPagina() == null) {
                                                    out.println("Sin información");
                                                }%>
                                        </div>
                                    </div>
                                </div>
                            </div>      

                        </section>

                        <section id ="section4">

                            <div class="container-sm-7">
                                <div class="panel-group">
                                    <div class="panel panel-default">
                                        <div class="panel-heading">Sección de comentarios</div>
                                    </div>
                                </div>
                            </div>
                            <%if (idusuario != 0) {%>

                            <form action = '../NuevoRankingMedicos' method = 'get'>
                                <div class='form-group'>
                                    <label class = 'mover' for='exampleFormControlSelect1'>Ranking: </label>
                                    <select class = 'mover2' id='exampleFormControlSelect1' name = 'txtranking'>
                                        <option value = '1'>1 Estrella</option>
                                        <option value = '2'>2 Estrellas</option>
                                        <option value = '3'>3 Estrellas</option>
                                        <option value = '4'>4 Estrellas</option>
                                        <option value = '5'>5 Estrellas</option>
                                    </select>
                                    <input type='hidden' name='idhospital' value='<%=idhospital%>'>
                                    <input type='hidden' name='idusuario' value='<%=idusuario%>'>
                                    <input type='hidden' name='idmedico' value='<%=idmedico%>'>
                                    <br>
                                    <label class = 'mover' for='exampleFormControlSelect1'>Comentario: </label>
                                    <select name = 'txtcomentario'> 
                                        <option value = 'Excelente doctor, muy profesional, el mejor que he conocido!'>Excelente doctor, muy profesional, el mejor que he conocido!</option>
                                        <option value = 'Muy buen doctor'>Muy buen doctor</option>
                                        <option value = 'Buen doctor aunque tardó en atenderme'>Buen doctor aunque tardó en atenderme</option>
                                        <option value = 'Se tardó mucho en atenderme y no es bueno'>Se tardó mucho en atenderme y no es bueno</option>
                                        <option value = 'El doctor hace caras si algo no le parece y muy grosero'>El doctor hace caras si algo no le parece y muy grosero</option>
                                    </select>
                                    <!--<textarea class='form-control' rows='5' id='comment' value='Comentario...' name = 'txtcomentario'></textarea>
                                    --><br>
                                    <div class='container'>
                                        <button type='submit' class='btn btn-primary'>Respuesta</button>
                                    </div>
                                </div>
                            </form><%}%>
                            <%
                                if (lista2 != null) {
                                    for (int i = 0; i < lista2.size(); i++) {
                                        RankeoMedicos listaRankingMedicos = (RankeoMedicos) lista2.get(i);
                                        estrellas = listaRankingMedicos.getRankeo();
                                        comentarios = listaRankingMedicos.getComentario();
                                        todos_usuarios = listaRankingMedicos.getIdUsuario();
                                        PacienteDAO pdao = new PacienteDAO();
                                        Paciente p = pdao.seleccionar_informacion(todos_usuarios);
                            %>
                            <div class='col-sm-2 text-center'>
                                <img src='../Imagenes/img_avatar1.png' class='img-circle' height='65' width='65' alt='Avatar'>
                            </div>
                            <div class='col-sm-10'>
                                <h4><%=p.getNombrePaciente()%> <%= p.getApellidoPaterno()%>  <img align = 'right' src ="../Imagenes/<%=estrellas%>estrellas.png">
                                </h4>
                                <p><%=comentarios%></p>
                                <br>
                                <hr>
                            </div>
                            <%
                                    }
                                }
                            %>     
                        </section>
                        </section>    
                    </div>
                </div>
            </div>
    </body>
</html>
