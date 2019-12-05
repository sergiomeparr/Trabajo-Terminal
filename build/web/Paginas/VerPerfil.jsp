<%@page import="java.security.InvalidKeyException"%>
<%@page import="com.java.controlador.EncriptadorAES"%>
<%@page import="com.java.controlador.RankeoCRUD"%>
<%@page import="com.java.bean.modelo.Rankeo"%>
<%@page import="com.java.bean.modelo.Hospitales"%>
<%@page import="com.java.controlador.HospitalCRUD"%>
<%@page import="com.java.bean.modelo.Paciente"%>
<%@page import="com.java.controlador.PacienteDAO"%>
<%@page import="com.java.bean.modelo.RankeoMedicos"%>
<%@page import="com.java.controlador.RankeoMedicosCRUD"%>
<%@page import="java.util.List"%>
<%@page import="com.java.controlador.MedicosCRUD"%>
<%@page import="com.java.bean.modelo.Medicos"%>
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
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
        <link rel="stylesheet" href="../CSS/MenuAdmon_1.css">
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
            int idusuario = Integer.parseInt(request.getParameter("idusuario"));
            String comentarios, comentarioshospital;
            int estrellas, todos_usuarios, paciente, estrellashospital, id_medico, idhospital;

            PacienteDAO pdao = new PacienteDAO();
            Paciente p = new Paciente();
            RankeoMedicos rm =  new RankeoMedicos();
            RankeoMedicosCRUD rmcrud = new RankeoMedicosCRUD();
            Rankeo rmh =  new Rankeo();
            RankeoCRUD rmhcrud = new RankeoCRUD();
            
            rm.setIdUsuario(idusuario);
            List<RankeoMedicos> listrm = rmcrud.read(rm);
            
            rmh.setIdPaciente(idusuario);
            List<Rankeo> listrh = rmhcrud.read(rmh);

            List<Medicos> listM;
            
            p = pdao.seleccionar_informacion(idusuario);
            
            String nombre = p.getNombrePaciente();
            String materno = p.getApellidoMaterno();
            String paterno = p.getApellidoPaterno();
            String telefono = p.getTelefono();
            String delegacion = p.getDelegacion(); 
            /*try {
                final String claveEncriptacion = "sect!";            
                EncriptadorAES encriptador = new EncriptadorAES();
                nombre = encriptador.desencriptar(p.getNombrePaciente(), claveEncriptacion);
                materno = encriptador.desencriptar(p.getApellidoMaterno(), claveEncriptacion);
                paterno = encriptador.desencriptar(p.getNombrePaciente(), claveEncriptacion);
                telefono = encriptador.desencriptar(p.getTelefono(), claveEncriptacion);
                delegacion= encriptador.desencriptar(p.getDelegacion(), claveEncriptacion);
                
            } catch (UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException ex) {
                Logger.getLogger(EncriptadorAES.class.getName()).log(Level.SEVERE, null, ex);
            }*/
                
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
                    <span class="dropdown">
                        <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">
                            <img src="../Imagenes/img_avatar1.png" class="img-circle" width = "35px">
                            <span class="caret"></span></button>
                        <ul class="dropdown-menu">
                            <li style="display: inline-block; padding: 0 5px; width: 158px;">
                            <form action='/Prototipo4_1/Paginas/VerPerfil.jsp' method ='post' onmouseover="this.style.background='#F5F5F5'"
                                    onmouseout="this.style.background='white'">
                                    <input type ='hidden' name='idusuario' value ="<%=idusuario%>">
                                    <button style='background-color:transparent; border:none; padding:0'>
                                    <h5 style='color:black;padding-left:15px'>Ver perfil</h5>
                                    </button>
                            </form>
                            <li style="display: inline-block; padding: 0 5px; width: 158px;">
                            <form action='/Prototipo4_1/Paginas/Buscador.jsp' method ='post' onmouseover="this.style.background='#F5F5F5'"
                                onmouseout="this.style.background='white'">
                                <input type ='hidden' name='id' value ="<%=idusuario%>">
                                <button style='background-color:transparent; border:none; padding:0' role='link' width='10%'>
                                <h5 style='color:black;padding-left:15px'>Página de inicio</h5>
                                </button>
                            </form>
                            <li><a href="LoginPaciente.jsp">Cerrar sesión</a><hr></li>
                            <li style="display: inline-block; padding: 0 5px; width: 158px;" >
                            	<form action='/Prototipo4_1/Paginas/ActualizacionDatosPaciente.jsp' method ='post' onmouseover="this.style.background='#F5F5F5'"
                                    onmouseout="this.style.background='white'">
                                  <input type ='hidden' name='idusuario' value ="<%=idusuario%>">
                                  <button style='background-color:transparent; border:none; padding:0' role='link' width='5%'>
                                  <h5 style='color:black;padding-left:15px'>Configuración</h5>
                                  </button>
                                </form>
                            </li>
                        </ul>
                        <img src="../Imagenes/Tcuido2.png" width = "80px">
                </span>
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
        
        <!--<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                        <span class="icon-bar" style = 'background-color: white'></span>
                        <span class="icon-bar" style = 'background-color: white'></span>
                        <span class="icon-bar" style = 'background-color: white'></span>
                    </button>
                    <div class="dropdown" style="padding-left:40px;">
                        <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">
                            <img src="../Imagenes/img_avatar1.png" class="img-circle" width = "35px">
                            <span class="caret"></span></button>
                        <ul class="dropdown-menu">
                            <li style="display: inline-block; padding: 0 5px; width: 158px;">
                            	<form action='/Prototipo4_1/Paginas/VerPerfil.jsp' method ='post' onmouseover="this.style.background='#F5F5F5'"
                                    onmouseout="this.style.background='white'">
                                  <input type ='hidden' name='idusuario' value ="<%=idusuario%>">
                                  <button style='background-color:transparent; border:none; padding:0'>
                                  <h5 style='color:black;padding-left:15px'>Ver perfil</h5>
                                  </button>
                                </form>
                    		<li style="display: inline-block; padding: 0 5px; width: 158px;">
                            	<form action='/Prototipo4_1/Paginas/Buscador.jsp' method ='post' onmouseover="this.style.background='#F5F5F5'"
                                    onmouseout="this.style.background='white'">
                                  <input type ='hidden' name='id' value ="<%=idusuario%>">
                                  <button style='background-color:transparent; border:none; padding:0' role='link' width='10%'>
                                  <h5 style='color:black;padding-left:15px'>Página de inicio</h5>
                                  </button>
                                </form>
                            <li><a href="LoginPaciente.jsp">Cerrar sesión</a><hr></li>
                            <li style="display: inline-block; padding: 0 5px; width: 158px;" >
                            	<form action='/Prototipo4_1/Paginas/ActualizacionDatosPaciente.jsp' method ='post' onmouseover="this.style.background='#F5F5F5'"
                                    onmouseout="this.style.background='white'">
                                  <input type ='hidden' name='idusuario' value ="<%=idusuario%>">
                                  <button style='background-color:transparent; border:none; padding:0' role='link' width='5%'>
                                  <h5 style='color:black;padding-left:15px'>Configuración</h5>
                                  </button>
                                </form>
                            </li>
                        </ul>
                        <img src="../Imagenes/Tcuido2.png"  width = "65px">
                    </div>
                </div>
                <div class="collapse navbar-collapse" id="myNavbar">
                    <form class="form-inline" action = "../BuscarDatos" style = "padding:7px; text-align: right" method="post">
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
        </nav>-->
        <div class="container-fluid text-center">    
            <div class="row content">
                <div class="col-sm-2">
                </div>
                <div class="col-md-8 text-left"> 
                    <div class="panel-group">
                    <br>
                        <div class="panel panel-default">
                            <div class="panel-heading" align = "center"><img src ='<%=p.getFoto()%>' width = '150px' align = "center"><br>
                            <p>Imagen de usuario</p>
                                        </div>
                            <div class="panel-body">
                                <div class = "container">
                                    <div class ="col-sm-5" style = "text-align:left">
                                        <h2 align = "center"> Información del usuario</h2>
                                        <p> Nombre: <%=nombre%> <%= paterno%> <%=materno%></p>
                                        <p> Teléfono: <%=telefono%></p>
                                    </div>
                                </div>    
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-sm-2">
                    
                </div>
                                    
                <div class="container">
                    <div class ="col-sm-2">
                    </div>
                    <div class="col-md-8 text-left"> 
                    
                        <h3>
                            Comentarios a hospitales<hr>
                        </h3>
                        
                        <%
                            if (listrh != null) {
                                for (int i = 0; i < listrh.size(); i++) {
                                    Rankeo listaRanking = (Rankeo) listrh.get(i);
                                    estrellashospital = listaRanking.getRankeo();
                                    comentarioshospital = listaRanking.getComentario();
                                    paciente = listaRanking.getIdPaciente();
                                    idhospital = listaRanking.getIdHospital();
                                    Hospitales h =  new Hospitales();
                                    HospitalCRUD hcrud = new HospitalCRUD();
                                    h.setIdHospital(idhospital);
                                    h = hcrud.readOne(h);
                                    
                                    
                        %>
                        <div class='col-sm-2 text-center'>
                            <img src='../Imagenes/plantillaHospital.jpg' class='img-circle' height='65' width='65' alt='Avatar'>
                            <%=h.getNombre()%>
                        </div>
                        <div class='col-sm-10'>
                            <h4><%=nombre%> <%= paterno%>  <img align = 'right' src ="../Imagenes/<%=estrellashospital%>estrellas.png">
                            </h4>
                            <p><%=comentarioshospital%></p>
                            <br>
                            <hr>
                        </div>
                        <%
                                }
                            }
                        %>   

                        
                        
                        
                        <h3>
                            Comentarios a médicos especialistas<hr>
                        </h3>
                       
                        <%
                            if (listrm != null) {
                                for (int i = 0; i < listrm.size(); i++) {
                                    RankeoMedicos listaRankingMedicos = (RankeoMedicos) listrm.get(i);
                                    estrellas = listaRankingMedicos.getRankeo();
                                    comentarios = listaRankingMedicos.getComentario();
                                    todos_usuarios = listaRankingMedicos.getIdUsuario();
                                    id_medico = listaRankingMedicos.getIdMedico();
                                    Medicos m = new Medicos();
                                    MedicosCRUD mcrud =  new MedicosCRUD();
                                    m.setIdMedicos(id_medico);
                                    listM = mcrud.readMedicos(m);
                                    m = (Medicos) listM.get(0);
                        %>
                        <div class='col-sm-2 text-center'>
                            <img src='../Imagenes/doctor.png' class='img-circle' height='65' width='65' alt='Avatar'>
                            <%=m.getNombreMedico()%> <%=m.getAPaternoMedico()%> <%=m.getAMaternoMedico()%>
                        </div>
                        <div class='col-sm-10'>
                            <h4><%=nombre%> <%= paterno%>  <img align = 'right' src ="../Imagenes/<%=estrellas%>estrellas.png">
                            </h4>
                            <p><%=comentarios%></p>
                            <br>
                            <hr>
                        </div>
                        <%
                                }
                            }
                        %>     
                    </div>
                    <div class ="col-sm-2">
                    </div>
                </div>    
            </div>
        </div>
    </body>
</html>
