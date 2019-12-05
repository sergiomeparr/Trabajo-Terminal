<%@page import="com.java.bean.modelo.Paciente"%>
<%@page import="com.java.controlador.PacienteDAO"%>
<%@page import="com.java.controlador.MedicosCRUD"%>
<%@page import="com.java.bean.modelo.Medicos"%>
<%@page import="com.java.controlador.ServiciosMedicosCRUD"%>
<%@page import="com.java.bean.modelo.ServiciosMedicos"%>
<%@page import="com.java.controlador.RankeoCRUD"%>
<%@page import="java.util.List"%>
<%@page import="com.java.bean.modelo.Rankeo"%>
<%@page import="com.java.controlador.HospitalCRUD"%>
<%@page import="com.java.bean.modelo.Hospitales"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>T-Cuido</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <link rel="stylesheet" href="../CSS/MenuAdmon_1.css">
        <link rel ="icon" type ="image/png" href="../Imagenes/Tcuido4.png"/>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
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
            RankeoCRUD rankeo = new RankeoCRUD();
            Rankeo r = new Rankeo();
            //idhospital
            int id = Integer.parseInt(request.getParameter("id"));
            //idusuario
            int usuario = Integer.parseInt(request.getParameter("usuario"));
            int todos_usuarios;
            int estrellas;
            int idmedico;
            String comentarios, Tipo_servicio;
            String nombre, appaterno, telefono, especialidad, precio;

            //Hospital información
            HospitalCRUD crud = new HospitalCRUD();
            Hospitales h = new Hospitales();
            h.setIdHospital(id);
            h = crud.readOne(h);

            //Ranking del hospital
            r.setIdHospital(id);
            List<Rankeo> lista = rankeo.readAll(r);

            //Servicios Médicos
            ServiciosMedicos sm = new ServiciosMedicos();
            ServiciosMedicosCRUD smcrud = new ServiciosMedicosCRUD();
            sm.setIdHospital(id);
            List<ServiciosMedicos> lista2 = smcrud.readAll(sm);

            //Datos Medicos
            Medicos m = new Medicos();
            MedicosCRUD mcrud = new MedicosCRUD();
            m.setIdHospital(id);
            List<Medicos> lista3 = mcrud.readAll(m);
            PacienteDAO pcrud = new PacienteDAO();
            Paciente p1 = new Paciente();    
            if(usuario!=0){
                p1 = pcrud.seleccionar_informacion(usuario);
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
                    <%if(usuario!=0){%>
                    <span class="dropdown">
                        <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">
                            <img src="../Imagenes/img_avatar1.png" class="img-circle" width = "35px">
                            <span class="caret"></span></button>
                        <ul class="dropdown-menu">
                            <li style="display: inline-block; padding: 0 5px; width: 158px;">
                            <form action='/Prototipo4_1/Paginas/VerPerfil.jsp' method ='post' onmouseover="this.style.background='#F5F5F5'"
                                    onmouseout="this.style.background='white'">
                                    <input type ='hidden' name='idusuario' value ="<%=usuario%>">
                                    <button style='background-color:transparent; border:none; padding:0'>
                                    <h5 style='color:black;padding-left:15px'>Ver perfil</h5>
                                    </button>
                            </form>
                            <li style="display: inline-block; padding: 0 5px; width: 158px;">
                            <form action='/Prototipo4_1/Paginas/Buscador.jsp' method ='post' onmouseover="this.style.background='#F5F5F5'"
                                onmouseout="this.style.background='white'">
                                <input type ='hidden' name='id' value ="<%=usuario%>">
                                <button style='background-color:transparent; border:none; padding:0' role='link' width='10%'>
                                <h5 style='color:black;padding-left:15px'>Página de inicio</h5>
                                </button>
                            </form>
                            <li><a href="LoginPaciente.jsp">Cerrar sesión</a><hr></li>
                            <li style="display: inline-block; padding: 0 5px; width: 158px;" >
                            	<form action='/Prototipo4_1/Paginas/ActualizacionDatosPaciente.jsp' method ='post' onmouseover="this.style.background='#F5F5F5'"
                                    onmouseout="this.style.background='white'">
                                  <input type ='hidden' name='idusuario' value ="<%=usuario%>">
                                  <button style='background-color:transparent; border:none; padding:0' role='link' width='5%'>
                                  <h5 style='color:black;padding-left:15px'>Configuración</h5>
                                  </button>
                                </form>
                            </li>
                        </ul>
                        <img src="../Imagenes/Tcuido2.png" width = "80px">
                </span>
                                  <%}else{%>
                        <span class="dropdown">
                        <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">
                            <img src="../Imagenes/img_avatar1.png" class="img-circle" width = "35px">
                            <span class="caret"></span></button>
                        <ul class="dropdown-menu">
                            <li style="display: inline-block; padding: 0 5px; width: 158px;">
                                <form action='/Prototipo4_1/Index.jsp' method ='post' onmouseover="this.style.background='#F5F5F5'"
                                    onmouseout="this.style.background='white'">
                                  <button style='background-color:transparent; border:none; padding:0'>
                                  <h5 style='color:black;padding-left:15px'>Página principal</h5>
                                  </button>
                                </form>
                            <li style="display: inline-block; padding: 0 5px; width: 158px;">
                            	<form action='/Prototipo4_1/BuscarDatos' method ='post' onmouseover="this.style.background='#F5F5F5'"
                                    onmouseout="this.style.background='white'">
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
                        <img src="../Imagenes/Tcuido2.png" width = "80px" >
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
                        <input type="hidden" name="id" value="<%=usuario%>">
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
        <!--<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
            <div class="container">
                <div class="navbar-header">
                    <button style = "border-color:white" type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                        <span class="icon-bar" style = 'background-color: white'></span>
                        <span class="icon-bar" style = 'background-color: white'></span>
                        <span class="icon-bar" style = 'background-color: white'></span>
                    </button>
                    
                    <%if(usuario!=0){%>
                        
                    <div class="dropdown">
                        <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">
                            <img src="../Imagenes/img_avatar1.png" class="img-circle" width = "35px">
                            <span class="caret"></span></button>
                        <ul class="dropdown-menu">
                            <li style="display: inline-block; padding: 0 5px; width: 158px;">
                        
                                <form action='/Prototipo4_1/Paginas/VerPerfil.jsp' method ='post' onmouseover="this.style.background='#F5F5F5'"
                                    onmouseout="this.style.background='white'">
                                  <input type ='hidden' name='idusuario' value ="<%=usuario%>">
                                  <button style='background-color:transparent; border:none; padding:0'>
                                  <h5 style='color:black;padding-left:15px'>Ver perfil</h5>
                                  </button>
                                </form>
                    		<li style="display: inline-block; padding: 0 5px; width: 158px;">
                            	<form action='/Prototipo4_1/Paginas/Buscador.jsp' method ='post' onmouseover="this.style.background='#F5F5F5'"
                                    onmouseout="this.style.background='white'">
                                  <input type ='hidden' name='id' value ="<%=usuario%>">
                                  <button style='background-color:transparent; border:none; padding:0' role='link' width='10%'>
                                  <h5 style='color:black;padding-left:15px'>Página de inicio</h5>
                                  </button>
                                </form>
                            <li><a href="LoginPaciente.jsp">Cerrar sesión</a><hr></li>
                            <li style="display: inline-block; padding: 0 5px; width: 158px;" >
                            	<form action='/Prototipo4_1/Paginas/ActualizacionDatosPaciente.jsp' method ='post' onmouseover="this.style.background='#F5F5F5'"
                                    onmouseout="this.style.background='white'">
                                  <input type ='hidden' name='idusuario' value ="<%=usuario%>">
                                  <button style='background-color:transparent; border:none; padding:0' role='link' width='5%'>
                                  <h5 style='color:black;padding-left:15px'>Configuración</h5>
                                  </button>
                                </form>
                            </li>
                        </ul>
                        <img src="../Imagenes/Tcuido2.png" width = "80px" >
                    </div><%}else{%>
                        <div class="dropdown">
                        <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">
                            <img src="../Imagenes/img_avatar1.png" class="img-circle" width = "35px">
                            <span class="caret"></span></button>
                        <ul class="dropdown-menu">
                            <li style="display: inline-block; padding: 0 5px; width: 158px;">
                                <form action='/Prototipo4_1/Index.jsp' method ='post' onmouseover="this.style.background='#F5F5F5'"
                                    onmouseout="this.style.background='white'">
                                  <button style='background-color:transparent; border:none; padding:0'>
                                  <h5 style='color:black;padding-left:15px'>Página principal</h5>
                                  </button>
                                </form>
                            <li style="display: inline-block; padding: 0 5px; width: 158px;">
                            	<form action='/Prototipo4_1/BuscarDatos' method ='post' onmouseover="this.style.background='#F5F5F5'"
                                    onmouseout="this.style.background='white'">
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
                        <img src="../Imagenes/Tcuido2.png" width = "80px" >
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
                        <input type="hidden" name="id" value="<%=usuario%>">
                        <input type ="hidden" name="rankeo" value =''>
                        <input type ="hidden" name="especialidad" value =''>
                        <input type ="hidden" name="servicios" value =''>
                        <input type ="hidden" name="cercania" value =''>
                    
                    </form>
                </div>
            </div>
        </nav>-->
  
        <div class="container-fluid">
            <div class="row">
                <div class="col-sm-3 sidenav visible-lg" style = "height:2000px">
                    <%if(usuario!=0){
                        out.println("<h4>Usuario: "+ p1.getNombrePaciente()+"</h4><br>");
                    }%>
                    <ul class="nav nav-pills nav-stacked">
                        <li><a href="#section1">Perfil hospital</a></li>
                        <li><a href="#section2">Servicios médicos</a></li>
                        <li><a href="#section3">Especialistas médicos</a></li>
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
                                                <img src = "https://previews.123rf.com/images/neyro2008/neyro20081512/neyro2008151200317/49781838-ilustraci%C3%B3n-de-la-ciudad-del-edificio-del-hospital-en-el-estilo-de-dise%C3%B1o-plano-arquitectura-cl%C3%ADnica-hospita.jpg" width ="200" >
                                            </div>
                                            <div class ="col-sm-5" style = "text-align:center">
                                                <h2><%= h.getNombre()%></h2>
                                                <%out.println("<img src ='../Imagenes/" + rankeo.promedio(id) + "estrellas.png' width = '150px'>");
                                                %>
                                                <p> Comentarios: <span class='badge'><%=rankeo.count(id)%></span></p>
                                                <p> Para más información: <%
                                                    if(h.getPagina()!=null){out.println("<a href = "+h.getPagina()+">"+h.getPagina()+"</a>");}
                                                    else if(h.getPagina() ==null){}%></p>
                                            </div>
                                        </div>    
                                    </div>
                                </div>
                            </div>
                        </section>    
                        <!--Sección de servicios médicos-->
                        <section id ="section2">
                            <div class="container-sm-7">
                                <div class="panel panel-default" style="border:0">
                                    <div class="row">
                                         <div class="col-md-6">
                                         <div class="panel panel-default">
                                                 <div class="panel-heading">Servicio médico</div>
                                                 <%
                                                    if (lista2 != null) {
                                                        for (int i = 0; i < lista2.size(); i++) {
                                                            ServiciosMedicos listaServicios = (ServiciosMedicos) lista2.get(i);
                                                            Tipo_servicio = listaServicios.getTipo();
                                                    %>
                                                    
                                                    <div class="panel-body">
                                                        <span class="glyphicon glyphicon-briefcase" style="color:#ff3333"></span>    
                                                        &nbsp;&nbsp;&nbsp;&nbsp;
                                                        <%=Tipo_servicio%></div>
                                                 
                                                    <%
                                                            }
                                                        }
                                                    %>
                                         </div>
                                         </div>
                                        <div class="col-md-6">
                                        <div class="panel panel-default">
                                                <div class="panel-heading">Precio</div>
                                                 <%
                                                    if (lista2 != null) {
                                                        for (int i = 0; i < lista2.size(); i++) {
                                                            ServiciosMedicos listaServicios = (ServiciosMedicos) lista2.get(i);
                                                            precio = listaServicios.getPrecio();
                                                    %>
                                                <div class="panel-body"> <i class="fa fa-dollar" style = "color:#efb810"></i>&nbsp;&nbsp;&nbsp;&nbsp;
                                                    <%
                                                        if(precio==null || "0".equals(precio)){
                                                            out.println("No disponible");} 
                                                        else{out.println(precio);}
                                                    %></div>

                                                    <%
                                                            }
                                                        }
                                                    %>
                                                </div>
                                         </div>
                                         </div>
                                 </div>

                                
                            </div>
                        </section>

                        <!--Sección de especialistas médicos-->
                        <section id ="section3">
                            <div class="container-sm-7">
                                <div class="panel-group">
                                    <div class="panel panel-default">
                                        <div class="panel-heading">Médicos especialistas</div>
                                    </div>
                                </div>
                            </div>

                            <form>
                                <div class="input-group">
                                    <input type="text" class="form-control" placeholder="Buscar médico..." name = "id"  id="Entrada2">
                                    <div class="input-group-btn">
                                        <button class="btn btn-default" type = "submit" disabled="true">
                                            <img src ="../Imagenes/buscar.png" width = "19px">
                                        </button>
                                    </div>
                                </div>
                            </form><br>

                            <div class="container-sm-7">
                                <div class="panel-group">
                                    <div class="panel panel-default">
                                        <table class="table table-striped" id="myTable">
                                            <thead>
                                                <tr align="center">
                                                    <th scope="col"></th>
                                                    <th scope="col">Nombre</th>
                                                    <th scope="col">Especialidad</th>
                                                    <th scope="col">Teléfono</th>
                                                </tr>
                                            </thead>
                                            <tbody id="Tabla2">
                                                <%
                                                    if (lista3 != null) {
                                                        for (int i = 0; i < lista3.size(); i++) {
                                                            Medicos listaMedicos = (Medicos) lista3.get(i);
                                                            nombre = listaMedicos.getNombreMedico();
                                                            appaterno = listaMedicos.getAPaternoMedico();
                                                            especialidad = listaMedicos.getEspecialidades();
                                                            telefono = listaMedicos.getTelefonos();
                                                            idmedico = listaMedicos.getIdMedicos();
                                                %>
                                                <tr>
                                                    <td align = "center">
                                                        <form action='/Prototipo4_1/Paginas/InformacionEspecialistas.jsp' method ='post'>
                                                            <input type ='hidden' name='idusuario' value ="<%=usuario%>">
                                                            <input type ='hidden' name='idhospital' value ="<%=id%>">
                                                            <input type ='hidden' name='idmedico' value ="<%=idmedico%>">
                                                            <button style='background-color:transparent; border:none; padding:0' role='link' width='5%'>
                                                            <img src = "../Imagenes/doctor.png"
                                                                onmouseover="this.width=55;this.height=55;" 
                                                                onmouseout="this.width=50;this.height=50;" 
                                                                width="50" height="50"class="img-circle">
                                                            </button>
                                                        </form>
                                                    </td>
                                                    <td><br><%= nombre%> <%= appaterno%></td>
                                                    <td><br><%= especialidad%></td>
                                                    <td><br><%= telefono%></td>
                                                </tr>
                                                <%
                                                        }
                                                    }
                                                %> 
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </section>
                        <!--Sección de ranking del hospital-->
                        <section id ="section4">
                            <div class="container-sm-7">
                                <div class="panel-group">
                                    <div class="panel panel-default">
                                        <div class="panel-heading">Sección de comentarios</div>
                                    </div>
                                </div>
                            </div>
                            <%if(usuario!=0){%>
                            <form action = '../NuevoRanking' method = 'get'>
                                <div class='form-group'>
                                    <label class = 'mover' for='exampleFormControlSelect1'>Calificación: </label>
                                    <select class = 'mover2' id='exampleFormControlSelect1' name = 'txtranking'>
                                        <option value = '1'>1 Estrella</option>
                                        <option value = '2'>2 Estrellas</option>
                                        <option value = '3'>3 Estrellas</option>
                                        <option value = '4'>4 Estrellas</option>
                                        <option value = '5'>5 Estrellas</option>
                                    </select>
                                    <input type='hidden' name='idhospital' value='<%=id%>'>
                                    <input type='hidden' name='idusuario' value='<%=usuario%>'>
                                    <br>
                                    <label class = 'mover' for='exampleFormControlSelect1'>Comentario: </label>
                                    <select name = 'txtcomentario'> 
                                        <option value = 'Excelente servicio, lo recomiendo!'>Excelente servicio, lo recomiendo!</option>
                                        <option value = 'Buen servicio del hospital'>Buen servicio del hospital</option>
                                        <option value = 'Parcialmente bueno el servicio y atención del hospital'>Parcialmente bueno el servicio y atención del hospital</option>
                                        <option value = 'No me gustó mucho la organización y los servicios del hospital'>No me gustó mucho la organización y los servicios del hospital</option>
                                        <option value = 'Pésimo, no recomiendo al hospital'>Pésimo, no recomiendo al hospital</option>
                                    </select>
                                    <br>
                                    <br>
                                    <div class='container'>
                                        <button type='submit' class='btn btn-primary'>Respuesta</button>
                                    </div>
                                </div>
                            </form><%}%>
                            <%
                                if (lista != null) {
                                    for (int i = 0; i < lista.size(); i++) {
                                        Rankeo listaRanking = (Rankeo) lista.get(i);
                                        estrellas = listaRanking.getRankeo();
                                        comentarios = listaRanking.getComentario();
                                        todos_usuarios = listaRanking.getIdPaciente();
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
                    </div>
                </div>
            </div>
                        
            <!--JQuery para seleccionar el médico que se quiere buscar-->
            <script>
                $(document).ready(function () {
                    $("#Entrada2").on("keyup", function () {
                        var value = $(this).val().toLowerCase();
                        $("#Tabla2 tr").filter(function () {
                            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1);
                        });
                    });
                });
            </script>            
    </body>
</html>
