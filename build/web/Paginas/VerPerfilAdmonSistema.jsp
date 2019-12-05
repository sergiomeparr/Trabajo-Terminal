<%-- 
    Document   : VerPerfilABE
    Created on : 24/08/2019, 06:52:37 PM
    Author     : leoip
--%>

<%@page import="com.java.controlador.UsuarioDAO"%>
<%@page import="com.java.bean.modelo.Usuario"%>
<%@page import="java.util.List"%>
<%@page import="com.java.controlador.HospitalCRUD"%>
<%@page import="com.java.bean.modelo.Hospitales"%>
<%@page import="com.java.controlador.AdministradorDAO"%>
<%@page import="com.java.bean.modelo.Administrador"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ page import="java.sql.*" %> 
<%@ page import="java.io.*" %> 
<%

    Administrador a = new Administrador();
    AdministradorDAO acrud = new AdministradorDAO();
    
    Hospitales h =  new Hospitales();
    HospitalCRUD hcrud = new HospitalCRUD();
    
    Usuario u =  new Usuario();
    UsuarioDAO ucrud = new UsuarioDAO();
    
    List<Hospitales> lista;
    lista = hcrud.readAll();
    int[] ida= new int[lista.size()];
    int contador = 0;
    for(int i = 0; i < lista.size(); i++){
        if(lista.get(i).getVerificar() == 1){
            ida[contador] = lista.get(i).getIdHospital();
            contador++;
        }
    }
    
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
            
            .table th {
                text-align: center;
            }
            .table td {
                text-align: center;   
            }
            .table {
                margin: auto;
                float: none;
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
            .nombre-abe{
                font-size: 20px;
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
                    Administrador del sistema</a>
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
                    <br>
                    <div class="row wrap-contact100">
                        <div class="col-sm-6">
                            <img class="img-responsive img-circle borde-imagen"
                                src="../Imagenes/admonbe.png" width="175" height="175">
                        </div>
                        <div class="col-sm-6">
                            <h1><b>Perfil administrador del sistema</b></h1>
                            <br>
                            <label class="nombre-abe">Nombre: Administrador</label>
                            
                        </div>
                    </div>
                    <br>
                    <h3><b>Registros aceptados</b></h3>
                </center>
            </div>
            
            <div class="table-responsive" style="display:block;height:280px;overflow-y:scroll;">
                <center>
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th class="col-xs-2"> Administrador de hospital</th>
                                <th class="col-xs-2"> Primer apellido</th>
                                <th class="col-xs-2"> Correo electrónico</th>
                                <th class="col-xs-2"> Hospital particular perteneciente</th>
                                <th class="col-xs-2"> Teléfono del hospital</th>
                                <th class="col-xs-2"> Página web del hospital</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%  
                                if(contador != 0){
                                    for(int i = 0; i < contador; i++){
                                        a =  new Administrador();
                                        a.setIdHospital(ida[i]);
                                        a = acrud.leer_por_hospital(a);

                                        if(a != null){
                                            u = new Usuario();
                                            h = new Hospitales();
                                            u.setIdUsuario(a.getIdUsuario());
                                            u = ucrud.read(u);
                                            if(u != null){
                    
                                                h.setIdHospital(ida[i]);
                                                h = hcrud.readOne(h);
                                
                            %>
                                                <tr>
                                                    <td class="col-xs-2"><%=a.getNombreAdministrador()%></td>
                                                    <td class="col-xs-2"><%=a.getApellidoPaterno()%></td>
                                                    <td class="col-xs-2"><%=u.getUsuario()%></td>
                                                    <td class="col-xs-2"><%=h.getNombre()%></td>
                                                    <td class="col-xs-2"><%=h.getTelefono()%></td>
                                                    <td class="col-xs-2"><%=h.getPagina()%></td>
                                                </tr>
                            <%
                                            }
                                        }   
                                    }
                                }
                                    
                            %>
                        </tbody>
                    </table>
                </center>
                </div>
                </div>
            </div>
        <br><br>
        </div>
    </body>
</html>
