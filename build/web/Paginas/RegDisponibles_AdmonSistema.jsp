
<%@page import="java.util.List"%>
<%@page import="com.java.controlador.UsuarioDAO"%>
<%@page import="com.java.bean.modelo.Usuario"%>
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
    lista = hcrud.Leer_NoVerificados();
    int contador = 0;
    int[] ida = new int[0];
    if(lista!= null){
        ida= new int[lista.size()];
        for(int i = 0; i < lista.size(); i++){
            if(lista.get(i).getVerificar() == 0){
                ida[contador] = lista.get(i).getIdHospital();
                contador++;
            }
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
                padding: 120px 25px 10px 25px;
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
            /*.btn{
                font-size: 20px;
                font-weight: bold;
                background-color: blue;
                color: white;
            }*/
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
                    <h1><b>Registros pendientes</b></h1>
                    <br>
                    <img class="img-responsive img-circle borde-imagen" src="../Imagenes/regdisp.png"
                         width="100" height="100">
                    <br>
                </center>
            </div>
            <div class="container-fluid">
                <div class="row" align= "center">
                    <div class="col-sm-2"></div>
                    <div class="col-sm-3">
                        <a target="_blank" align = "right" href="http://burocomercial.profeco.gob.mx/" style="color: #0062cc">
                        "Verificar existencia de buró de crédito"</a>   
                    </div>
                    <div class="col-sm-1"><br></div>
                    <div class="col-sm-3">
                        <a target="_blank" align = "right" href="https://www.gob.mx/conamed#3180" style="color: #0062cc">
                        "Verificar negligencia médica"</a>    
                    </div>
                    <div class="col-sm-2"></div>
                </div>
            </div>
            <br>
            <div class="table-responsive">
                <center>
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th># No. registro</th>
                                <th> Nombre</th>
                                <th> Primer apellido</th>
                                <th> Correo electrónico</th>
                                <th> Teléfono del administrador</th>
                                <th> Hospital particular perteneciente</th>
                                <th> Teléfono del hospital</th>
                                <th> Página web del hospital</th>
                                <th>Acciones</th>
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
                                                h = hcrud.readOneNO(h);
                                
                            %>
                                                <tr>
                                                    <td><%=(i+1)%></td>
                                                    <td><%=a.getNombreAdministrador()%></td>
                                                    <td><%=a.getApellidoPaterno()%></td>
                                                    <td><%=u.getUsuario()%></td>
                                                    <td><%=a.getTelefono()%></td>
                                                    <td><%=h.getNombre()%></td>
                                                    <td><%=h.getTelefono()%></td>
                                                    <td><%=h.getPagina()%></td>
                                                    <td>
                                                        <a class="estilo-enlace"
                                                            accesskey="" href="Verifica_RegistroAdminHospital.jsp?id_reg=<%=h.getIdHospital()%>"
                                                            style="color:white;text-decoration:none;">Verificar datos</a>
                                                    </td>
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
    </body>
</html>
