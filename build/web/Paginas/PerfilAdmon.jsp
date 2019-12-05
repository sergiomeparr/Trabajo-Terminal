<%@page import="java.io.UnsupportedEncodingException"%>
<%@page import="java.security.InvalidKeyException"%>
<%@page import="java.security.InvalidKeyException"%>
<%@page import="com.java.bean.modelo.Hospitales"%>
<%@page import="com.java.controlador.HospitalCRUD"%>
<%@page import="com.java.controlador.AdministradorDAO"%>
<%@page import="com.java.bean.modelo.Administrador"%>
<%@page import="com.java.bean.modelo.ServiciosMedicos"%>
<%@page import="com.java.controlador.ServiciosMedicosCRUD"%>
<%@page import="com.java.controlador.MedicosCRUD"%>
<%@page import="java.util.List"%>
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
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>T-Cuido</title>

        <link href="../Bootstrap/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Saira+Extra+Condensed:500,700" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Muli:400,400i,800,800i" rel="stylesheet">
        <link href="../Booststrap/vendor/fontawesome-free/css/all.min.css" rel="stylesheet">
        <link href="../Bootstrap/css/resume.min.css" rel="stylesheet">
        <link rel ="icon" type ="image/png" href="../Imagenes/Tcuido4.png"/>
        <script src="../Bootstrap/vendor/jquery/jquery.min.js"></script>
        <script src="../Bootstrap/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
        <script src="../Bootstrap/vendor/jquery-easing/jquery.easing.min.js"></script>
        <script src="../Bootstrap/js/resume.min.js"></script>
    </head>

    <body id="Inicio">
        <%
            String identi = request.getParameter("id");
            if(identi.contains(" ")){
                identi = identi.replace(" ", "+");
            }
            int idusuario = 0;
            if (identi.length() > 5) {
                try {
                    final String claveEncriptacion = "sec";
                    EncriptadorAES encriptador = new EncriptadorAES();
                    String desencriptado = encriptador.desencriptar(identi, claveEncriptacion);
                    idusuario = Integer.parseInt(desencriptado);
                } catch (UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException ex) {
                    Logger.getLogger(EncriptadorAES.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                idusuario = Integer.parseInt(identi);
            }
            //Datos del administrador
            AdministradorDAO admon = new AdministradorDAO();
            Administrador a = new Administrador();
            //Datos del Hospital
            HospitalCRUD crud = new HospitalCRUD();
            Hospitales h = new Hospitales();
            a.setIdUsuario(idusuario);
            a = admon.leer(a);
            h.setIdHospital(a.getIdHospital());
            h = crud.readOne(h);
            
            //Datos de los servicios médicos
            String tipo;
            int id;
            ServiciosMedicosCRUD dao1 = new ServiciosMedicosCRUD();
            ServiciosMedicos sm = new ServiciosMedicos();
            sm.setIdHospital(a.getIdHospital());
            List<ServiciosMedicos> lista1 = dao1.readAll(sm);

            //Datos de médicos que trabajan en un hospital
            String nombre, Cedula;
            String apmaterno, appaterno;
            String especialidad, telefono, precio;
            int idhospital, idmedico;

            MedicosCRUD dao = new MedicosCRUD();
            Medicos m = new Medicos();
            m.setIdHospital(a.getIdHospital());
            List<Medicos> lista = dao.readAll(m);
                
        %>
          
        <nav class="navbar navbar-expand-lg navbar-dark  fixed-top" id="sideNav" style ="background: gray">
            <a class="navbar-brand js-scroll-trigger" href="#page-top">
                <span class="d-block d-lg-none">Administrador  <img class="img-responsive img-circle borde-imagen"
                         src="../Imagenes/Tcuido2.png"
                         width="75px"></span>
                <span class="d-none d-lg-block" style = "padding-top:70%">
                    <img class="img-fluid img-profile rounded-circle mx-auto mb-2" src="<%=a.getFoto()%>" alt="">
                </span>
            </a>
            <button class="navbar-toggler" style="border-color: white" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent" align = "center">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link js-scroll-trigger" style="color: white" href="#Inicio">Inicio</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link js-scroll-trigger" style="color: white" href="#Medicos">Gestionar médicos</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link js-scroll-trigger" style="color: white" href="#Servicios">Gestionar servicios médicos</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link js-scroll-trigger" style="color: white" href="#Configuracion">Configuración</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link js-scroll-trigger" style="color: white" href="LoginAdministrador.jsp">Cerrar sesión</a>
                    </li>
                </ul>
            </div>
        </nav>

        <div class="container-fluid p-0">
            <div class="container">
                <form action="ActualizarDatosHospital.jsp" method ="post">
                    <input type ="hidden" name="idhospital" value ='<%=a.getIdHospital()%>'>
                    <input type ="hidden" name="idusuario" value ='<%=idusuario%>'>
                    <button style="background-color:transparent; border:none;" role="link" width="10%">
                        <img src ="../Imagenes/editar.png" 
                             onmouseover="this.width = 53;this.height = 53;" 
                             onmouseout="this.width = 50;this.height = 50;" 
                             width="50" height="50"><h6 style="color:sandybrown">Modificar</h6>
                    </button>
                </form>
            </div>
            <section class="resume-section p-3 p-lg-5 d-flex align-items-center" id="Inicio">
                <div class="w-100">
                    <h1 class="mb-0" style = "font-size: 70px"> <%= h.getNombre()%>
                    </h1>
                    <div > Dirección: <%= h.getDireccion()%>
                        <br> Teléfono: <%= h.getTelefono()%> 
                    </div>
                </div>
            </section>

            <hr class="m-0">

            <section class="resume-section p-3 p-lg-5 d-flex justify-content-center" id="Medicos">
                <div class="w-100">
                    <div class="container">
                        <h1 style = "font-size: 50px">Médicos</h1>
                        <form action="/Prototipo4_1/Paginas/DatosMedicos.jsp" method ="post">
                            <div class="container-fluid">
                                <div class="row">

                                    <div class="col-sm-3">                       
                                        <input type ="hidden" name="id" value ='<%=a.getIdHospital()%>'>
                                        <input type ="hidden" name="idusuario" value ='<%=idusuario%>'>
                                        <input type ="hidden" name="1" value ='0'>
                                        <button style="background-color:transparent; border:none;" role="link" width="10%">
                                            <img src ="../Imagenes/nuevo.png" 
                                                 onmouseover="this.width = 28;this.height = 28;" 
                                                 onmouseout="this.width = 25;this.height = 25;" 
                                                 width="25" height="25"> <h8 style="color:sandybrown">Nuevo médico</h8>
                                        </button>
                                    </div>
                                    <div class="col-sm-1"><br></div>
                                    <div class="col-sm-5">
                                        <a target="_blank" align = "right" href="https://cedulaprofesional.sep.gob.mx/cedula/presidencia/indexAvanzada.action" style="color: #0062cc">"Página 		oficial de cédulas profesionales"</a>    
                                    </div>
                                </div>
                           </div>
                        </form>
                        
                        <form>
                            <div class="input-group">
                                <input type="text" class="form-control" placeholder="Buscar..." name = "id"  id="Entrada2">
                                <div class="input-group-btn">
                                    <button class="btn btn-default" type="submit" disabled="true">
                                        <img src ="../Imagenes/buscar.png" width = "20px">
                                    </button>
                                </div>
                            </div>
                        </form><br>
                        <div class="table-responsive">
                            <table class="table" id="myTable">
                                <thead>
                                    <tr>
                                        <th>Cédula profesional</th>
                                        <th>Nombre</th>
                                        <th>Primer apellido</th>
                                        <th>Segundo apellido</th>
                                        <th>Especialidad médica</th>
                                        <th colspan=2>Modificar /&nbsp; &nbsp; Eliminar </th>
                                    </tr>
                                </thead>
                                <%
                                    if (lista != null) {
                                        for (int i = 0; i < lista.size(); i++) {

                                            Medicos listaMedicos = (Medicos) lista.get(i);
                                            nombre = listaMedicos.getNombreMedico();
                                            apmaterno = listaMedicos.getAMaternoMedico();
                                            appaterno = listaMedicos.getAPaternoMedico();
                                            especialidad = listaMedicos.getEspecialidades();
                                            telefono = listaMedicos.getTelefonos();
                                            Cedula = listaMedicos.getCedulap();
                                            idhospital = listaMedicos.getIdHospital();
                                            idmedico = listaMedicos.getIdMedicos();
                                %>

                                <tbody id="Tabla2">
                                    <tr>
                                        <td><%= Cedula%></td>
                                        <td><%= nombre%></td>
                                        <td><%= appaterno%></td>
                                        <td><%= apmaterno%></td>
                                        <td><%= especialidad%></td>
                                        <td align="right">
                                            <form action="/Prototipo4_1/Paginas/ModificarMedico.jsp" method ="post">
                                                <input type ="hidden" name="id" value ='<%=idhospital%>'>
                                                <input type ="hidden" name="idusuario" value ='<%=idusuario%>'>
                                                <input type ="hidden" name="idmedico" value ='<%=idmedico%>'>
                                                <input type ="hidden" name="1" value ='0'>
                                                <button style="background-color:transparent; border:none;" role="link" width="10%">
                                                    <img src ="../Imagenes/editar.png" 
                                                         onmouseover="this.width = 28;this.height = 28;" 
                                                         onmouseout="this.width = 25;
                                                                 this.height = 25;" 
                                                         width="25" height="25">
                                                </button>
                                            </form>
                                        </td><td><form action="../EliminarMedico" method ="post">
                                                <input type ="hidden" name="idusuario" value ='<%=idusuario%>'>
                                                <input type ="hidden" name="idmedico" value ='<%=idmedico%>'>
                                                <button OnClick="if (!confirm('Estás a punto de eliminar un médico especialista'))
                                                            return false;"
                                                        style="background-color:transparent; border:none;" role="link" width="10%">
                                                    <img src ="../Imagenes/eliminar.png" 
                                                         onmouseover="this.width = 28;
                                                                 this.height = 28;" 
                                                         onmouseout="this.width = 25;
                                                                 this.height = 25;" 
                                                         width="25" height="25">
                                                </button>
                                            </form>
                                        </td>
                                    </tr>  
                                    <%  }
                                        }%>    
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </section>
            <hr class="m-0">
            <section class="resume-section p-3 p-lg-5 d-flex align-items-center" id="Servicios">
                <div class="w-100">
                    <div class="container">
                        <h1 class="mb-0" style = "font-size: 50px">Servicios médicos</h1>

                        <form action="/Prototipo4_1/Paginas/DatosServiciosMedicos.jsp" method ="post">
                            <input type ="hidden" name="idhospital" value ='<%=a.getIdHospital()%>'>
                            <input type ="hidden" name="idusuario" value ='<%=idusuario%>'>
                            <input type ="hidden" name="1" value ='0'>
                            <button style="background-color:transparent; border:none;" role="link" width="10%">
                                <img src ="../Imagenes/nuevo.png" 
                                     onmouseover="this.width = 28;
                                             this.height = 28;" 
                                     onmouseout="this.width = 25;
                                             this.height = 25;" 
                                     width="25" height="25"> <h8 style="color:sandybrown">Nuevo servicio</h8>
                            </button>
                        </form>

                        <form>
                            <div class="input-group">
                                <input type="text" class="form-control" placeholder="Buscar..." name = "id" id="Entrada">
                                <div class="input-group-btn">
                                    <button class="btn btn-default" type="submit" disabled="true">
                                        <img src ="../Imagenes/buscar.png" width = "20px">
                                    </button>
                                </div>
                            </div>
                        </form><br>
                        <div class="table-responsive">

                            <table class="table">
                                <thead>
                                    <tr>
                                        <th align="center">Id</th>
                                        <th>Servicio Médico</th>
                                        <th>Precio</th>
                                        <th colspan="2" width="500">Modificar /&nbsp; &nbsp; Eliminar </th>
                                    </tr>
                                </thead >
                                <%
                                    if (lista1 != null) {
                                        for (int i = 0; i < lista1.size(); i++) {

                                            ServiciosMedicos listaMedicos1 = (ServiciosMedicos) lista1.get(i);
                                            id = i + 1;
                                            tipo = listaMedicos1.getTipo();
                                            precio = listaMedicos1.getPrecio();

                                %>

                                <tbody id="Tabla">
                                    <tr>
                                        <td width="100"><%= id%></td>
                                        <td width="500"><%= tipo%></td>
                                        <td width="500"><%

                                            if (precio == null) {
                                                out.println("No disponible");
                                            } else {
                                                out.println(precio);
                                            }
                                            %></td>
                                        <td align="right" width="200">
                                            <form action="/Prototipo4_1/Paginas/ActualizarDatosServicios.jsp" method ="post">
                                                <input type ="hidden" name="idhospital" value ='<%=listaMedicos1.getIdHospital()%>'>
                                                <input type ="hidden" name="idusuario" value ='<%=idusuario%>'>
                                                <input type ="hidden" name="idservicio" value ='<%= listaMedicos1.getIdServicio()%>'>
                                                <input type ="hidden" name="1" value ='0'>
                                                <button style="background-color:transparent; border:none;" role="link" width="10%">
                                                    <img src ="../Imagenes/editar.png" 
                                                         onmouseover="this.width = 28;this.height = 28;" 
                                                         onmouseout="this.width = 25;
                                                                 this.height = 25;" 
                                                         width="25" height="25">
                                                </button>
                                            </form>
                                        </td>

                                        <td width="700">  
                                            <form action="../EliminarServicioMedico" method ="post">
                                                <input type ="hidden" name="idusuario" value ='<%=idusuario%>'>
                                                <input type ="hidden" name="idservicio" value ='<%= listaMedicos1.getIdServicio()%>'>
                                                <button OnClick="if (!confirm('Estás a punto de eliminar un servicio médico'))
                                                            return false;"
                                                        style="background-color:transparent; border:none;" role="link" width="10%">
                                                    <img src ="../Imagenes/eliminar.png" 
                                                         onmouseover="this.width = 28;
                                                                 this.height = 28;" 
                                                         onmouseout="this.width = 25;
                                                                 this.height = 25;" 
                                                         width="25" height="25">
                                                </button>
                                            </form>
                                        </td>
                                    </tr>  
                                    <%  }
                                        }%>    
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </section>
            <section class="resume-section p-3 p-lg-5 d-flex align-items-center" id="Configuracion">
                <div class="container">
                    <form action="/Prototipo4_1/Paginas/ActualizarDatosAdminsitrador.jsp" method ="post">
                        <input type ="hidden" name="idhospital" value ='<%=a.getIdHospital()%>'>
                        <input type ="hidden" name="idusuario" value ='<%=idusuario%>'>
                        <button style="background-color:transparent; border:none;" role="link" width="10%">
                            <img src ="../Imagenes/editar.png" 
                                 onmouseover="this.width = 53;this.height = 53;" 
                                 onmouseout="this.width = 50;this.height = 50;" 
                                 width="50" height="50"> <h6 style="color:sandybrown">Modificar</h6>
                        </button>
                    </form>

                    <div class="w-100">
                        <h1 class="mb-0" style = "font-size: 60px">Admón: <%= a.getNombreAdministrador()%>  <%=a.getApellidoPaterno()%> <%=a.getApellidoMaterno()%>        <!--Emanuel Castillo Cabrera--> 
                        </h1>
                        <div>
                            <br>Teléfono: <%=a.getTelefono()%>  <!--01 55 5516 9900--> ·
                            <!--<br>Email:  Emanuel@gmail.com-->
                        </div>
                    </div>
                </div>

            </section>
            <hr class="m-0">
        </div>

        <!--buscadores-->
        <script>
            $(document).ready(function () {
                $("#Entrada").on("keyup", function () {
                    var value = $(this).val().toLowerCase();
                    $("#Tabla tr").filter(function () {
                        $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1);
                    });
                });
            });

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