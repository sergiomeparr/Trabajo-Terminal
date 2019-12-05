package com.java.controlador;

import com.java.bean.modelo.Hospitales;
import com.java.bean.modelo.Medicos;
import com.java.bean.modelo.Paciente;
import com.java.bean.modelo.Rankeo;
import com.java.bean.modelo.RankeoMedicos;
import com.java.bean.modelo.ServiciosMedicos;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "BuscarDatos", urlPatterns = {"/BuscarDatos"})
public class BuscarDatos extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");

            out.println("<head>");
            out.println("<title>T-Cuido</title>");
            out.println("<meta charset = 'utf-8'>");
            out.println("<meta name='viewport' content='width=device-width, initial-scale=1'>");
            out.println("<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css'>");
            out.println("<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js'></script>");
            out.println("<script src='https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js'></script>");
            out.println("<link rel ='icon' type ='image/png' href='Imagenes/Tcuido4.png'/>");
            out.println("</head>");
            out.println("<body>");

            //Peticiones que se obtienen de los filtros de búsqueda y del buscador//
            String datos = request.getParameter("txtbuscar");
            int id = Integer.parseInt(request.getParameter("id"));
            String rankeo = request.getParameter("rankeo");
            String especialidad = request.getParameter("especialidad");
            String servicios = request.getParameter("servicios");
            String cercania = request.getParameter("cercania");
            if(datos.contains("<") || datos.contains(">")){
                response.sendRedirect("BuscarDatos?txtbuscar=&id="+id+"&rankeo=&especialidad=&servicios&cercania=");
            }
            
            //Variables que son ocupadas para obtención de datos//
            String substring;
            int idHospital, ranking, contador = 1, contador1 = 1, contador2 = 1, contador3 = 1, contador4 = 1;
            int prom, contador5=1;
            int[] idh, idm, idmm;
            String foto, nombre, direccion, pagina, telefono, appaterno, apmaterno;
            int rankeo_entero, pivote;
            
            
            //Arreglo bidimensional para encontrar hospital mas cercano
            String[][] alcaldias =  new String[8][16];
            alcaldias[0][0] = "Cuauhtémoc"; alcaldias[1][0] = "Azcapotzalco"; alcaldias[2][0] = "Gustavo A. Madero"; alcaldias[3][0] = "Venustiano Carranza"; alcaldias[4][0] = "Iztacalco"; alcaldias[5][0] = "Benito Júarez";alcaldias[6][0] = "Miguel Hidalgo";
            alcaldias[0][1] = "Tlalpan"; alcaldias[1][1] = "Milpa Alta"; alcaldias[2][1] = "Xochimilco"; alcaldias[3][1] = "Coyoacán"; alcaldias[4][1] = "Álvaro Obregón"; alcaldias[5][1] = "La Magdalena Contreras";
            alcaldias[0][2] = "Venustiano Carranza"; alcaldias[1][2] = "Iztacalco"; alcaldias[2][2] = "Cuauhtémoc"; alcaldias[2][3] = "Gustavo A. Madero";
            alcaldias[0][3] = "Iztapalapa"; alcaldias[1][3] = "Iztacalco"; alcaldias[2][3] = "Benito Júarez"; alcaldias[3][3] = "Coyoacán"; alcaldias[4][3] = "Xochimilco"; alcaldias[5][3] = "Tláhuac";
            alcaldias[0][4] = "Iztacalco"; alcaldias[1][4] = "Venustiano Carranza"; alcaldias[2][4] = "Cuauhtémoc"; alcaldias[3][4] = "Benito Júarez"; alcaldias[4][4] = "Iztapalapa"; 
            alcaldias[0][5] = "Miguel Hidalgo"; alcaldias[1][5] = "Azcapotzalco"; alcaldias[2][5] = "Cuauhtémoc"; alcaldias[3][5] = "Benito Júarez"; alcaldias[4][5] = "Álvaro Obregón"; alcaldias[5][5] = "Cuajimalpa de Morelos";
            alcaldias[0][6] = "La Magdalena Contreras"; alcaldias[1][6] = "Álvaro Obregón"; alcaldias[2][6] = "Coyoacán"; alcaldias[3][6] = "Tlalpan";
            alcaldias[0][7] = "Coyoacan"; alcaldias[1][7] = "Iztapalapa"; alcaldias[2][7] = "Xochimilco"; alcaldias[3][7] = "Tlalpan"; alcaldias[4][7] = "Álvaro Obregón"; alcaldias[5][12] = "Azcapotzalco";
            alcaldias[0][8] = "Milpa Alta"; alcaldias[1][8] = "Tláhuac"; alcaldias[2][8] = "Xochimilco"; alcaldias[3][8] = "Tlalpan";
            alcaldias[0][9] = "Tláhuac"; alcaldias[1][9] = "Iztapalapa"; alcaldias[2][9] = "Xochimilco"; alcaldias[3][9] = "Milpa Alta";
            alcaldias[0][10] = "Benito Júarez"; alcaldias[1][10] = "Iztacalco"; alcaldias[2][10] = "Venustiano Carranza"; alcaldias[3][10] = "Cuauhtémoc"; alcaldias[4][10] = "Miguel Hidalgo"; alcaldias[5][10] = "Álvaro Obregón"; alcaldias[6][10] = "Coyoacán"; alcaldias[7][10] = "Iztapalapa";
            alcaldias[0][11] = "Cuajimalpa de Morelos"; alcaldias[1][11] = "Álvaro Obregón"; alcaldias[2][11] = "Miguel Hidalgo";
            alcaldias[0][12] = "Gustavo A. Madero"; alcaldias[1][12] = "Azcapotzalco"; alcaldias[2][12] = "Cuauhtémoc"; alcaldias[3][12] = "Gustavo A. Madero";
            alcaldias[0][13] = "Álvaro Obregón"; alcaldias[1][13] = "Cuajimalpa de Morelos"; alcaldias[2][13] = "Miguel Hidalgo"; alcaldias[3][13] = "Benito Júarez"; alcaldias[4][13] = "Coyoacán"; alcaldias[5][13] = "Tlalpan"; alcaldias[6][13] = "La Magdalena Contreras";
            alcaldias[0][14] = "Xochimilco"; alcaldias[1][14] = "Iztapalapa"; alcaldias[2][14] = "Tláhuac"; alcaldias[3][14] = "Coyoacán"; alcaldias[4][14] = "Tlalpan"; alcaldias[5][14] = "Milpa Alta";
            alcaldias[0][15] = "Azcapotzalco"; alcaldias[1][15] = "Miguel Hidalgo"; alcaldias[2][15] = "Cuauhtémoc"; alcaldias[3][15] = "Gustavo A. Madero";
            
            //Búsqueda de información por hospitales
            Hospitales h;
            HospitalCRUD hcrud;
            List<Hospitales> lista;
            
            //Búsqueda de información por médicos
            Medicos m;
            MedicosCRUD mcrud;
            List<Medicos> lista2;
        
            //Busqueda de información por servicio médico
            ServiciosMedicos sm;
            ServiciosMedicosCRUD smcrud;
            List<ServiciosMedicos> lista3;

            //Busqueda de infromación por rankeo
            Rankeo r = new Rankeo();
            RankeoCRUD rcrud;
            RankeoMedicos rm;
            RankeoMedicosCRUD rmcrud;
            List<RankeoMedicos> listrm;
            
            Paciente p = null;
            PacienteDAO pcrud;

            //Cadenas auxiliares para limpiar de tíldes los datos recibidos del usuario  
            String original = "àáâãäåæçèéêëìíîïðñòóôõöøùúûüýÿ";
            String ascii = "aaaaaaaceeeeiiiionoooooouuuuyy";

            
            /*Header de la aplicación web, contiene: 
                * Filtros de búsqueda.
                * Buscardor.
                * Menú desplegable del usuario
            */
          /*  
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
            out.println("<div class="input-group-btn">
            out.println("<button class="btn btn-default" type="submit">
            out.println("<i class="glyphicon glyphicon-search"></i>
            out.println("</button>
            out.println("</div>
            out.println("</div>
            out.println("<input type="hidden" name="id" value="<%=idusuario%>">
            out.println("<input type ="hidden" name="rankeo" value =''>
            out.println("<input type ="hidden" name="especialidad" value =''>
            out.println("<input type ="hidden" name="servicios" value =''>
            out.println("<input type ="hidden" name="cercania" value =''>
            out.println("</form>
            out.println("</li>
            out.println("</ul>
            out.println("</div>
            out.println("</div>
            out.println("</nav>");
        */
            
            
            
            out.println("<nav class='navbar navbar-expand-lg navbar-dark bg-primary'>");
            out.println("<div class='container'>");
            out.println("<div class='navbar-header'>");
            out.println("<button type='button' style = 'border-color: white' class='navbar-toggle' data-toggle='collapse' data-target='#myNavbar'>");
            out.println("<span class='icon-bar' style = 'background-color: white'></span>");
            out.println("<span class='icon-bar' style = 'background-color: white'></span>");
            out.println("<span class='icon-bar' style = 'background-color: white'></span>");
            out.println("</button>");
            if(id!=0){
                out.println("<span class='dropdown'>");
                out.println("<button class='btn btn-primary dropdown-toggle' type='button' data-toggle='dropdown'>");
                out.println("<img src='/Prototipo4_1/Imagenes/img_avatar1.png' class='img-circle' width = '35px'>");
                out.println("<span class='caret'></span></button>");
                out.println("<ul class='dropdown-menu'>");
                out.println("<li style='display: inline-block; padding: 0 5px; width: 158px;'>" +
                        "<form action='/Prototipo4_1/Paginas/VerPerfil.jsp' method ='post' onmouseover='this.style.background='#F5F5F5'" +
                        "onmouseout='this.style.background='white'>" +
                        "<input type ='hidden' name='idusuario' value ='"+id+"'>" +
                        "<button style='background-color:transparent; border:none; padding:0' role='link' width='5%'>\n" +
                        "<h5 style='color:black;padding-left:15px'>Ver perfil</h5>" +
                        "</button>" +
                        "</form>" +
                        "</li>");
                out.println("<li style='display: inline-block; padding: 0 5px; width: 158px;'>"
                        +"<form action='/Prototipo4_1/Paginas/Buscador.jsp' method ='post' "
                        + "onmouseover='this.style.background='#F5F5F5'onmouseout='this.style.background='white'>"
                        +"<input type ='hidden' name='id' value ='"+id+"'>"
                        +"<button style='background-color:transparent; border:none; padding:0' role='link' width='10%'>"
                        +"<h5 style='color:black; padding-left:13px'>Página de inicio</h5>"
                        +"</button>"
                        +"</form></li>");
                out.println("<li><a href='/Prototipo4_1/Paginas/LoginPaciente.jsp'>Cerrar Sesión</a><hr></hr></li>");
                out.println("<li style='display: inline-block; padding: 0 5px; width: 158px;'>"
                        +"<form action='/Prototipo4_1/Paginas/ActualizacionDatosPaciente.jsp' method ='post' onmouseover='this.style.background='#F5F5F5' onmouseout='this.style.background='white'>"
                        +"<input type ='hidden' name='idusuario' value ='"+id+"'>"
                        +"<button style='background-color:transparent; border:none; padding:0' role='link' width='10%'>"
                        +"<h5 style='color:black;padding-left:13px'>Configuración</h5>"
                        +"</button>"
                        +"</form></li>");
                out.println("</ul>");
                out.println("<img src='Imagenes/Tcuido2.png'  width = '65px'>");
                out.println("</span>");
                out.println("</div>");
                out.println("<div class='collapse navbar-collapse' id='myNavbar'>");
            }else{
                out.println("<span class='dropdown'>");
                out.println("<button class='btn btn-primary dropdown-toggle' type='button' data-toggle='dropdown'>");
                out.println("<img src='/Prototipo4_1/Imagenes/img_avatar1.png' class='img-circle' width = '35px'>");
                out.println("<span class='caret'></span></button>");
                out.println("<ul class='dropdown-menu'>");
                out.println("<li style='display: inline-block; padding: 0 5px; width: 158px;'>" +
                        "<form action='/Prototipo4_1/Index.jsp' method ='post' onmouseover='this.style.background='#F5F5F5'" +
                        "onmouseout='this.style.background='white'>" +
                        "<button style='background-color:transparent; border:none; padding:0' role='link' width='5%'>" +
                        "<h5 style='color:black;padding-left:15px'>Página principal</h5>" +
                        "</button>" +
                        "</form>" +
                        "</li>");
                out.println("</ul>");
                out.println("<img src='Imagenes/Tcuido2.png'  width = '65px'>");
                out.println("</span>");
                out.println("</div>");
                out.println("<div class='collapse navbar-collapse' id='myNavbar'>");
            }
            out.println("<form class='form-inline' action = 'BuscarDatos' style = 'padding:7px; text-align: right' method ='post'>");
            out.println("<div class='input-group'>");
            out.println("<input name = 'txtbuscar' type='text' class='form-control mr-sm-2' placeholder='Buscar' name='search'>");
            out.println("<div class='input-group-btn'>");
            out.println("<button class='btn btn-default' type='submit'>");
            out.println("<i class='glyphicon glyphicon-search'></i>");
            out.println("</button>");
            out.println("</div>");
            out.println("</div>");
            out.println("<input type = 'hidden' name = 'id' value='" + id + "'>");
            out.println("<input type = 'hidden' name = 'rankeo'>");
            out.println("<input type = 'hidden' name = 'servicios'>");
            out.println("<input type = 'hidden' name = 'especialidad'>");
            out.println("<input type = 'hidden' name = 'cercania'>");
            out.println("</form>");
            out.println("</div>");
            out.println("</div>");
            out.println("</nav>");
            out.println("<div class='container'>");
            out.println("<p align = 'center'>Filtros de búsqueda</p>");
            out.println("<form action = 'BuscarDatos' method='post'>");
            out.println("<table class='table'><tr>");
            out.println("<th style='vertical-align:middle;'>");
            out.println("<div align = 'center'>Servicio médico ofrecido por hospital:");
            out.println("<select name = 'servicios' class='form-control'>");
            if(servicios != null){
                out.println("<option value = ''>"+servicios+"</option>");
            }
            out.println("<option value = ''></option>");
            out.println("<option value = '' disabled style = 'color:blue;'>--Servicios de neurología</option>");
            out.println("<option value = 'Punción lumbar'>Punción lumbar</option>");
            out.println("<option value = 'Electromiografía'>Electromiografía</option>");
            out.println("<option value = 'Resonancia magnética estructural'>Resonancia magnética estructural</option>");
            
            out.println("<option value = '' disabled style = 'color:blue;'>--Servicios de medicina general</option>");
            out.println("<option value = 'Curación de heridas'>Curación de heridas</option>");
            out.println("<option value = 'Examen de triglicéridos'>Examen de triglicéridos</option>");
            out.println("<option value = 'Nebulización'>Nebulización</option>");
            
            out.println("<option value = '' disabled style = 'color:blue;'>--Servicios de urología</option>");
            out.println("<option value = 'Vasectomía'>Vasectomía</option>");
            out.println("<option value = 'Cistoscopia'>Cistoscopia</option>");
            out.println("<option value = 'Nefrectomía'>Nefrectomía</option>");
            
            out.println("<option value = '' disabled style = 'color:blue;'>--Servicios de pediatría</option>");
            out.println("<option value = 'Nutrición pediátrica'>Nutrición pediátrica </option>");
            out.println("<option value = 'Atención al recién nacido'>Atención al recién nacido</option>");
            out.println("<option value = 'Seguimiento del neurodesarrollo'>Seguimiento del neurodesarrollo</option>");
            
            
            out.println("<option value = '' disabled style = 'color:blue;'>--Servicios de cardiología</option>");
            out.println("<option value = 'Holter'>Holter</option>");
            out.println("<option value = 'Colocación de marcapasos'>Colocación de marcapasos</option>");
            out.println("<option value = 'Ecocardiograma transesofágico'>Ecocardiograma transesofágico</option>");
            
            out.println("<option value = '' align= 'center' disabled style = 'color:blue;'>--Servicios generales</option>");
            out.println("<option value = 'Laboratorio clínico'>Laboratorio clínico</option>");
            out.println("<option value = 'Hospitalización'>Hospitalización</option>");
            out.println("<option value = 'Terapia intensiva'>Terapia intensiva</option>");
            out.println("<option value = 'Banco de sangre'>Banco de sangre</option>");
            out.println("<option value = 'Rayos X'>Rayos X</option>");
            
            /*out.println("<option value = 'Ambulancia'>Ambulancia</option>");
            out.println("<option value = 'Urgencias'>Urgencias</option>");
            out.println("<option value = 'Radioterapia'>Radioterapia</option>");
            out.println("<option value = 'Apoyo respiratorio'>Apoyo Respiratorio</option>");
            out.println("<option value = 'Rehabilitación'>Rehabilitación</option>");
            out.println("<option value = 'Neurofisiología'>Neurofisiología</option>");
            out.println("<option value = 'Audiología'>Audiología</option>");
            out.println("<option value = 'Cuidados intensivo'>Cuidados Intensivos</option>");
            //out.println("<option value = 'Densitro'>Densitrometría osea</option>");
            out.println("<option value = 'Transplante'>Transplantes</option>");
            out.println("<option value = 'Hemodinamia'>Hemodinamia</option>");*/
            out.println("</select></div></th>");
            out.println("<th style='vertical-align:middle;'><div align = 'center'> Buscar médico por especialidad: ");
            out.println("<select name = 'especialidad' class='form-control'>");
            if(especialidad != null){
                out.println("<option value = ''>"+especialidad+"</option>");
            }
            out.println("<option value = ''></option>");
            out.println("<option value = 'Urología'>Urología</option>");
            out.println("<option value = 'Cardiología'>Cardiología</option>");
            out.println("<option value = 'Medicina general'>Medicina General</option>");
            out.println("<option value = 'Pediatría'>Pediatría</option>");
            out.println("<option value = 'Neurología'>Neurología</option>");
            out.println("</select> </div></th>");
            if(id!=0){
                out.println("<td rowspan='4' align='center' style='vertical-align:middle;'><button class='btn btn-info' type='submit'>Buscar</button>");
                out.println("</td>");
                out.println("</tr><tr>");
            }
            out.println("<th style='vertical-align:middle;'>");
            out.println("<div align = 'center'>");
            out.println("Calificación o evaluación: <select name='rankeo' class='form-control'>");
            if(rankeo != null){
                out.println("<option value = ''>"+rankeo+"</option>");
            }
            out.println("<option value = ''></option>");
            out.println("<option value = '0'>0 estrellas</option>");
            out.println("<option value = '1'>1 estrella</option>");
            out.println("<option value = '2'>2 estrellas</option>");
            out.println("<option value = '3'>3 estrellas</option>");
            out.println("<option value = '4'>4 estrellas</option>");
            out.println("<option value = '5'>5 estrellas</option>");
            out.println("</select> </div></th>");
            if(id == 0){
                out.println("<input type='hidden' value = '' name='cercania'></option>");
                out.println("<td rowspan='4' align='center' style='vertical-align:middle;'><button class='btn btn-info' type='submit'>Buscar</button>");
                out.println("</tr><tr>");
            }
            if(id != 0){
                out.println("<th style='vertical-align:middle;'><div align = 'center'>");
                out.println("Pertenencia por alcaldía (hospital): <select name='cercania' class='form-control'>");
                if(cercania != null){
                    out.println("<option value = ''>"+cercania+"</option>");
                }
                out.println("<option value = ''></option>");
                out.println("<option value = 'Si'>Si</option>");
                out.println("<option value = 'No'>No</option>");
                out.println("</select> </div></th>");
            }
            out.println("<input type='hidden' name='id' value='" + id + "'>");
            out.println("<input type='hidden' name='txtbuscar' value=''>");
            out.println("</tr></table></form></div>");
                        
            /*Si no se ingresó datos en los filtros de búsqueda,
                verificar que en el buscador se ingresaron datos*/
            
            if ("".equals(rankeo) && "".equals(especialidad) && "".equals(servicios) && "".equals(cercania)) {
                /*Si no hay datos ingresados, seleccionamos todos los hospitales que
                    estén ingresados en el sistema de T-Cuido*/
                if ("".equals(datos)) {
                    out.println("<div class='container'>");
                    out.println("<h2> Te podría interesar: </h2>");
                    out.println("</div>");
                    hcrud = new HospitalCRUD();
                    h = new Hospitales();
                    
                    //Se seleccionan todos los hospitales en una lista de "Hospitales"//
                    lista = hcrud.readAll1();
                    
                    //Se muestran los hospitales en la aplicación web
                    for (int i = 0; i < lista.size(); i++) {
                        h = (Hospitales) lista.get(i);
                        idHospital = h.getIdHospital();
                        foto = h.getFoto();
                        direccion = h.getDireccion();
                        telefono = h.getTelefono();
                        pagina = h.getPagina();

                        rcrud = new RankeoCRUD();
                        prom = rcrud.promedio(idHospital);
                        
                        out.println("<div class='container'>");
                        out.println("<div class='panel-group'>");
                        out.println("<div class='panel panel-default'>");
                        out.println("<div class='panel-body'>");

                        out.println("<div class='col-lg-3 sidenav' align = 'center'>");
   /*aqui*/                     if(foto == null){out.println("<img src = '/Imagenes/plantillaHospital.jpg' width= '225px'><br>");}
                        else{out.println("<img src = '" + foto + "' width= '225px'><br>");}
                        out.println("</div>");
                        out.println("<div class='col-lg-6 text-left'>"); 
                        out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                        out.println("<input type ='hidden' name='id' value ='"+idHospital+"'>");
                        out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                        out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                        out.println("<h2 style='color:#1073c5' id='letras'>"+h.getNombre()+"</h2>");
                        out.println("</button></form>");
                        out.println("<img src = 'Imagenes/" + prom + "estrellas.png' width = '125px'><br><br>");
                        out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                        out.println("Dirección: <br>");
                        out.println(direccion + "<br>");
                        out.println("Teléfono : " + telefono);

                        out.println("</div><br>");
                        out.println("<div class='col-lg-2 sidenav' align='center'>");
                        out.println("<iframe width='250' height='150' "
                                + "src='https://maps.google.com/maps?&hl=es&q="
                                + (h.getNombre())
                                + "&ie=UTF8&t=&z=12&iwloc=B&output=embed' "
                                + "frameborder='0' scrolling='no' marginheight='0' marginwidth='0'></iframe>");
                        out.println("</div>");
                        out.println("</div>");
                        out.println("</div>");
                        out.println("</div>");
                        out.println("</div>");
                    }
                    
                    //Si hay datos ingresados, verificar que datos se quieren obtener//
                } else if (!"".equals(datos)) {
                    h = new Hospitales();
                    hcrud = new HospitalCRUD();
                    h.setNombre(datos);
                    
                    //Se seleccionan los hospital para ver si es lo que busca el usuario.
                    lista = hcrud.readbyname(h);
                    
                    //Si no es lo que buscar pasar al siguiente criterio.
                    if (lista == null) {
                        m = new Medicos();
                        mcrud = new MedicosCRUD();
                        substring = datos.substring(0, datos.length() - 3);
                        m.setEspecialidades(substring);
                        
                        //Seleccionar por especialidad médica 
                        lista2 = mcrud.Hospitales_especialidad(m);
                        
                        //Si no es lo que se buscaba, pasar al siguiente criterio
                        if (lista2 == null) {
                            sm = new ServiciosMedicos();
                            smcrud = new ServiciosMedicosCRUD();
                            substring = datos.substring(0, datos.length() - 3);
                            sm.setTipo(substring);
                            sm.setTipo(substring);
                            
                            //Seleccionar por tipo de servicio que ofrece el hospital
                            lista3 = smcrud.seleccionar_tipo_servicio(sm);
                            
                            //Si tampoco es lo que buscaba el usuario, pasar al último criterio
                            if (lista3 == null) {
                                m = new Medicos();
                                mcrud = new MedicosCRUD();
                                
                                //Se selecciona por médico especialista
                                lista2 = mcrud.medicos();
                                
                                contador = 0;
                                
                                //Se límpia la cadena ingresada 
                                for (int i = 0; i < original.length(); i++) {
                                    datos = datos.replace(original.charAt(i), ascii.charAt(i));
                                }
                                datos = datos.toLowerCase();
                                //Se selecciona el doctor o doctores que coinciden con los datos inhresados
                                for(int i = 0; i < lista2.size(); i++){
                                    String aux = lista2.get(i).getNombreMedico() + " " + lista2.get(i).getAPaternoMedico() + " " + lista2.get(i).getAMaternoMedico();
                                    aux = aux.toLowerCase();
                                    //Se límpia la cadena aux de tíldes.
                                    for (int j = 0; j < original.length(); j++) {
                                        aux = aux.replace(original.charAt(j), ascii.charAt(j));
                                    }
                                    
                                    //Si hay una coincidencia, mostrar los datos
                                    if(aux.contains(datos)){
                                        contador++;
                                        h =  new Hospitales();
                                        h.setIdHospital(lista2.get(i).getIdHospital());
                                        h = hcrud.readOne1(h);
                                        
                                        rm = new RankeoMedicos();
                                        rmcrud = new RankeoMedicosCRUD();
                                        rm.setIdHospital(h.getIdHospital());
                                        rm.setIdMedico(lista2.get(i).getIdMedicos());
                                        prom = rmcrud.promedio(rm);
                                        out.println("<div class='container'>");
                                        out.println("<div class='panel-group'>");
                                        out.println("<div class='panel panel-default'>");
                                        out.println("<div class='panel-body'>");

                                        out.println("<div class='col-md-3 sidenav' align = 'center'>");
                                        out.println("<img src = '/Prototipo4_1/Imagenes/doctor.png' width= '150px'>");
                                        out.println("</div>");
                                        out.println("<div class='col-md-6 text-left'>"); 
                                        out.println("<form action='/Prototipo4_1/Paginas/InformacionEspecialistas.jsp' method ='post'>");
                                        out.println("<input type ='hidden' name='idhospital' value ='"+h.getIdHospital()+"'>");
                                        out.println("<input type ='hidden' name='idusuario' value ='"+id+"'>");
                                        out.println("<input type ='hidden' name='idmedico' value ='"+lista2.get(i).getIdMedicos()+"'>");
                                        out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                        out.println("<h2 style='color:#1073c5' id='letras'>"+lista2.get(i).getNombreMedico() + " " + lista2.get(i).getAPaternoMedico() + " " + lista2.get(i).getAMaternoMedico()+"</h2>");
                                        out.println("</button></form>");
                        
                                        out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                        out.println("<input type ='hidden' name='id' value ='"+h.getIdHospital()+"'>");
                                        out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                        out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                        out.println("<h7 style='color:#1073c5' id='letras'>"+h.getNombre()+"</h7>");
                                        out.println("</button></form>");
                                        out.println("<img src = '/Prototipo4_1/Imagenes/"+prom+"estrellas.png' width = '125px'><br><br>");

                                        out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                        out.println("Dirección: <br>");
                                        out.println(h.getDireccion() + "<br>");
                                        out.println("Teléfono : " + lista2.get(i).getTelefonos());

                                        out.println("</div>");
                                        out.println("</div>");
                                        out.println("</div>");
                                        out.println("</div>");
                                        out.println("</div>");
                                    }
                                }
            
                                /*En dado caso que no se encontró un médico en la BD, se hace una selección 
                                   de hospitales*/
                                if(contador == 0){
                                    out.println("<div class='container'>");
                                    out.println("<h2> Sugerencias </h2>");
                                    out.println("</div>");
                                    lista = hcrud.readAll1();
                                    for (int i = 0; i < lista.size(); i++) {

                                        Hospitales listaHospitales = (Hospitales) lista.get(i);
                                        idHospital = listaHospitales.getIdHospital();
                                        foto = listaHospitales.getFoto();
                                        nombre = listaHospitales.getNombre();
                                        direccion = listaHospitales.getDireccion();
                                        telefono = listaHospitales.getTelefono();
                                        ranking = listaHospitales.getRankeo();
                                        pagina = listaHospitales.getPagina();

                                        rcrud = new RankeoCRUD();
                                        prom = rcrud.promedio(idHospital);
                                        out.println("<div class='container'>");
                                        out.println("<div class='panel-group'>");
                                        out.println("<div class='panel panel-default'>");
                                        out.println("<div class='panel-body'>");

                                        out.println("<div class='col-lg-3 sidenav' align = 'center'>");
                                        out.println("<img src = '" + foto + "' width= '225px'><br>");
                                        out.println("</div>");
                                        out.println("<div class='col-lg-6 text-left'>"); 
                                        out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                        out.println("<input type ='hidden' name='id' value ='"+idHospital+"'>");
                                        out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                        out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                        out.println("<h2 style='color:#1073c5' id='letras'>"+nombre+"</h2>");
                                        out.println("</button></form>");
                        
                                        out.println("<img src = 'Imagenes/" + prom + "estrellas.png' width = '125px'><br><br>");
                                        out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                        out.println("Dirección: <br>");
                                        out.println(direccion + "<br>");
                                        out.println("Teléfono : " + telefono);

                                        out.println("</div>");
                                        out.println("<div class='col-lg-2 sidenav' align='center'><br>");
                                        out.println("<iframe width='250' height='150' "
                                                + "src='https://maps.google.com/maps?&hl=es&q="
                                                + (nombre)
                                                + "&ie=UTF8&t=&z=12&iwloc=B&output=embed' "
                                                + "frameborder='0' scrolling='no' marginheight='0' marginwidth='0'></iframe>");
                                        out.println("</div>");
                                        out.println("</div>");
                                        out.println("</div>");
                                        out.println("</div>");
                                        out.println("</div>");

                                    }
                                }
                                /*Si hay coincidencia en los datos ingresados por el usuario 
                                    con los servicios médicos*/ 
                            }else{
                                out.println("<div class='container'>");
                                out.println("<h2> Coincidencia en: " + datos + "</h2>");
                                out.println("</div>");
                            
                                /*Se seleccionan los servicios médicos que coinciden con los datos ingresados
                                    por el usuario*/
                                for (int i = 0; i < lista3.size(); i++) {
                                    h = new Hospitales();
                                    hcrud = new HospitalCRUD();
                                    h.setIdHospital(lista3.get(i).getIdHospital());
                                    h = hcrud.readOne1(h);

                                    idHospital = h.getIdHospital();
                                    foto = h.getFoto();
                                    nombre = h.getNombre();
                                    direccion = h.getDireccion();
                                    telefono = h.getTelefono();
                                    ranking = h.getRankeo();
                                    pagina = h.getPagina();

                                    rcrud = new RankeoCRUD();
                                    prom = rcrud.promedio(idHospital);

                                    out.println("<div class='container'>");
                                    out.println("<div class='panel-group'>");
                                    out.println("<div class='panel panel-default'>");
                                    out.println("<div class='panel-body'>");

                                    out.println("<div class='col-lg-3 sidenav' align = 'center'>");
                                    out.println("<img src = '" + foto + "' width= '225px'>");
                                    out.println("</div>");
                                    out.println("<div class='col-lg-6 text-left'>"); 
                                    out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                    out.println("<input type ='hidden' name='id' value ='"+idHospital+"'>");
                                    out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                    out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                    out.println("<h2 style='color:#1073c5' id='letras'>"+nombre+"</h2>");
                                    out.println("</button></form>");
                        
                                    out.println("<img src = 'Imagenes/" + prom + "estrellas.png' width = '125px'><br><br>");
                                    out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                    out.println("Dirección: <br>");
                                    out.println(direccion + "<br>");
                                    out.println("Teléfono : " + telefono);

                                    out.println("</div>");
                                    out.println("<div class='col-lg-2 sidenav' align='center'><br>");
                                    out.println("<iframe width='250' height='150' "
                                            + "src='https://maps.google.com/maps?&hl=es&q="
                                            + (nombre)
                                            + "&ie=UTF8&t=&z=12&iwloc=B&output=embed' "
                                            + "frameborder='0' scrolling='no' marginheight='0' marginwidth='0'></iframe>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                }
                            }
                            
                            /*Si hay datos que coinciden con especialidad médica, se muestran en la
                                aplicación web*/
                        } else {
                            out.println("<div class='container'>");
                            out.println("<h2> Coincidencia en: " + datos + "</h2>");
                            out.println("</div>");
                            
                            //Ciclo para mostrar los datos que coinciden
                            for (int i = 0; i < lista2.size(); i++) {
                                h.setIdHospital(lista2.get(i).getIdHospital());
                                h = hcrud.readOne1(h);
                                if (h != null) {
                                    idHospital = h.getIdHospital();
                                    nombre = h.getNombre();
                                    direccion = h.getDireccion();
                                    
                                    rm = new RankeoMedicos();
                                    rmcrud = new RankeoMedicosCRUD();
                                    rm.setIdHospital(idHospital);
                                    rm.setIdMedico(lista2.get(i).getIdMedicos());
                                    prom = rmcrud.promedio(rm);
                                    
                                    
                                    out.println("<div class='container'>");
                                    out.println("<div class='panel-group'>");
                                    out.println("<div class='panel panel-default'>");
                                    out.println("<div class='panel-body'>");

                                    out.println("<div class='col-md-3 sidenav' align = 'center'>");
                                    out.println("<img src = '/Prototipo4_1/Imagenes/doctor.png' width= '150px'>");
                                    out.println("</div>");
                                    out.println("<div class='col-md-6 text-left'>"); 
                                    
                                    out.println("<form action='/Prototipo4_1/Paginas/InformacionEspecialistas.jsp' method ='post'>");
                                    out.println("<input type ='hidden' name='idhospital' value ='"+idHospital+"'>");
                                    out.println("<input type ='hidden' name='idusuario' value ='"+id+"'>");
                                    out.println("<input type ='hidden' name='idmedico' value ='"+lista2.get(i).getIdMedicos()+"'>");
                                    out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                    out.println("<h2 style='color:#1073c5' id='letras'>"+lista2.get(i).getNombreMedico() + " " + lista2.get(i).getAPaternoMedico() + " " + lista2.get(i).getAMaternoMedico()+"</h2>");
                                    out.println("</button></form>");

                                    out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                    out.println("<input type ='hidden' name='id' value ='"+idHospital+"'>");
                                    out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                    out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                    out.println("<h7 style='color:#1073c5' id='letras'>"+nombre+"</h7>");
                                    out.println("</button></form>");
                                        
                                    out.println("<img src = '/Prototipo4_1/Imagenes/"+prom+"estrellas.png' width = '125px'><br><br>");
                                    
                                    out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                    out.println("Dirección: <br>");
                                    out.println(direccion + "<br>");
                                    out.println("Teléfono : " + lista2.get(i).getTelefonos());
                                    
                                    out.println("</div>");
                                    /*out.println("<div class='col-sm-2 sidenav' align='center'>");
                                    out.println("<iframe width='250' height='150' "
                                            + "src='https://maps.google.com/maps?&hl=es&q="
                                            + (direccion.substring(0, 20))
                                            + "&ie=UTF8&t=&z=12&iwloc=B&output=embed' "
                                            + "frameborder='0' scrolling='no' marginheight='0' marginwidth='0'></iframe>");
                                    out.println("</div>");
                                    */out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                }
                            }
                        }
                        
                        //Si hay datos que coinciden con algún hospital, se selecciona
                    } if (lista != null) {
                        
                        /*
                            Verificar porque está causando ruido
                        */
                        out.println("<div class='container'>");
                        out.println("<h2> Coincidencia en: " + datos + "</h2>");
                        out.println("</div>");

                        //Ciclo para mostrar datos que coinciden
                        for (int i = 0; i < lista.size(); i++) {

                            Hospitales listaHospitales = (Hospitales) lista.get(i);
                            idHospital = listaHospitales.getIdHospital();
                            foto = listaHospitales.getFoto();
                            nombre = listaHospitales.getNombre();
                            direccion = listaHospitales.getDireccion();
                            telefono = listaHospitales.getTelefono();
                            ranking = listaHospitales.getRankeo();
                            pagina = listaHospitales.getPagina();

                            rcrud = new RankeoCRUD();
                            prom = rcrud.promedio(idHospital);
                            out.println("<div class='container'>");
                            out.println("<div class='panel-group'>");
                            out.println("<div class='panel panel-default'>");
                            out.println("<div class='panel-body'>");

                            out.println("<div class='col-lg-3 sidenav' align = 'center'>");
                            out.println("<img src = '" + foto + "' width= '225px'><br>");
                            out.println("</div>");
                            out.println("<div class='col-lg-6 text-left'>"); 
                            out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                            out.println("<input type ='hidden' name='id' value ='"+idHospital+"'>");
                            out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                            out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                            out.println("<h2 style='color:#1073c5' id='letras'>"+nombre+"</h2>");
                            out.println("</button></form>");
                            out.println("<img src = 'Imagenes/" + prom + "estrellas.png' width = '125px'><br><br>");
                            out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                            out.println("Dirección: <br>");
                            out.println(direccion + "<br>");
                            out.println("Teléfono : " + telefono);
                            
                            out.println("</div>");
                            out.println("<div class='col-lg-2 sidenav' align='center'><br>");
                            out.println("<iframe width='250' height='150' "
                                    + "src='https://maps.google.com/maps?&hl=es&q="
                                    + (nombre)
                                    + "&ie=UTF8&t=&z=12&iwloc=B&output=embed' "
                                    + "frameborder='0' scrolling='no' marginheight='0' marginwidth='0'></iframe>");
                            out.println("</div>");
                            out.println("</div>");
                            out.println("</div>");
                            out.println("</div>");
                            out.println("</div>");
                        }
                    }
                }
            }

            
            ///////////////////////////Filtro por servicios////////////////////////////////////////////////
            if ("".equals(cercania) && "".equals(rankeo) && "".equals(especialidad) && !"".equals(servicios)) {
                sm = new ServiciosMedicos();
                smcrud = new ServiciosMedicosCRUD();
                sm.setTipo(servicios);
                lista3 = smcrud.seleccionar_tipo_servicio(sm);
                if (lista3 != null) {
                    out.println("<div class='container'>");
                    out.println("<h2> Coincidencia en: " + servicios + "</h2>");
                    out.println("</div>");

                    for (int i = 0; i < lista3.size(); i++) {
                        h = new Hospitales();
                        hcrud = new HospitalCRUD();
                        h.setIdHospital(lista3.get(i).getIdHospital());
                        h = hcrud.readOne1(h);

                        idHospital = h.getIdHospital();
                        foto = h.getFoto();
                        nombre = h.getNombre();
                        direccion = h.getDireccion();
                        telefono = h.getTelefono();
                        ranking = h.getRankeo();
                        pagina = h.getPagina();

                        rcrud = new RankeoCRUD();
                        prom = rcrud.promedio(idHospital);
                        
                        out.println("<div class='container'>");
                        out.println("<div class='panel-group'>");
                        out.println("<div class='panel panel-default'>");
                        out.println("<div class='panel-body'>");

                        out.println("<div class='col-lg-3 sidenav' align = 'center'>");
                        out.println("<img src = '" + foto + "' width= '225px'>");
                        out.println("</div>");
                        out.println("<div class='col-lg-6 text-left'>"); 
                        
                        out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                        out.println("<input type ='hidden' name='id' value ='"+idHospital+"'>");
                        out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                        out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                        out.println("<h2 style='color:#1073c5' id='letras'>"+nombre+"</h2>");
                        out.println("</button></form>");
                        
                        out.println("<img src = 'Imagenes/" + prom + "estrellas.png' width = '125px'><br><br>");
                        out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                        out.println("Dirección: <br>");
                        out.println(direccion + "<br>");
                        out.println("Teléfono : " + telefono);
                        
                        out.println("</div>");
                        out.println("<div class='col-lg-2 sidenav' align='center'><br>");
                        out.println("<iframe width='250' height='150' "
                                + "src='https://maps.google.com/maps?&hl=es&q="
                                + (nombre)
                                + "&ie=UTF8&t=&z=12&iwloc=B&output=embed' "
                                + "frameborder='0' scrolling='no' marginheight='0' marginwidth='0'></iframe>");
                        out.println("</div>");
                        out.println("</div>");
                        out.println("</div>");
                        out.println("</div>");
                        out.println("</div>");
                    }
                } else {
                    out.println("<div class='container'>");
                    out.println("<h2> No se encontraron resultados </h2>");
                    out.println("</div>");
                }
            }
            
            
            
            
            ///////////////////////////Filtro por especialidad////////////////////////////////////////////////
            if ("".equals(cercania) &&  "".equals(rankeo) && !"".equals(especialidad) && "".equals(servicios)) {
                m = new Medicos();
                mcrud = new MedicosCRUD();
                m.setEspecialidades(especialidad);
                lista2 = mcrud.Hospitales_especialidad(m);
                if(lista2 != null){
                    out.println("<div class='container'>");
                    out.println("<h2> Coincidencia en: " + especialidad + "</h2>");
                    out.println("</div>");

                    for (int i = 0; i < lista2.size(); i++) {
                        h = new Hospitales();
                        hcrud = new HospitalCRUD();
                        h.setIdHospital(lista2.get(i).getIdHospital());
                        h = hcrud.readOne1(h); 
                        if (h != null) {
                            rm = new RankeoMedicos();
                            rmcrud = new RankeoMedicosCRUD();
                            rm.setIdHospital(h.getIdHospital());
                            rm.setIdMedico(lista2.get(i).getIdMedicos());
                            prom = rmcrud.promedio(rm);

                            out.println("<div class='container'>");
                            out.println("<div class='panel-group'>");
                            out.println("<div class='panel panel-default'>");
                            out.println("<div class='panel-body'>");
                            
                            out.println("<div class='col-md-3 sidenav' align = 'center'>");
                            out.println("<img src = '/Prototipo4_1/Imagenes/doctor.png' width= '150px'>");
                            out.println("</div>");
                            out.println("<div class='col-md-6 text-left'>"); 
                            
                            out.println("<form action='/Prototipo4_1/Paginas/InformacionEspecialistas.jsp' method ='post'>");
                            out.println("<input type ='hidden' name='idhospital' value ='"+h.getIdHospital()+"'>");
                            out.println("<input type ='hidden' name='idusuario' value ='"+id+"'>");
                            out.println("<input type ='hidden' name='idmedico' value ='"+lista2.get(i).getIdMedicos()+"'>");
                            out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                            out.println("<h2 style='color:#1073c5' id='letras'>"+lista2.get(i).getNombreMedico() + " " + lista2.get(i).getAPaternoMedico() + " " + lista2.get(i).getAMaternoMedico()+"</h2>");
                            out.println("</button></form>");

                            out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                            out.println("<input type ='hidden' name='id' value ='"+h.getIdHospital()+"'>");
                            out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                            out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                            out.println("<h7 style='color:#1073c5' id='letras'>"+h.getNombre()+"</h7>");
                            out.println("</button></form>");
                                    
                            out.println("<img src = '/Prototipo4_1/Imagenes/"+prom+"estrellas.png' width = '125px><br><br>");
                            
                            out.println("<span class='glyphicon glyphicon-map-marker'></span><br><br>");
                            out.println("Dirección: <br>");
                            out.println(h.getDireccion() + "<br>");
                            out.println("Teléfono : " + lista2.get(i).getTelefonos());
                            
                            out.println("</div>");
                            out.println("</div>");
                            out.println("</div>");
                            out.println("</div>");
                            out.println("</div>");
                        }
                    }
                }
                else{
                    out.println("<div class='container'>");
                    out.println("<h2> No se encontraron resultados </h2>");
                    out.println("</div>");
                }
            }
            
            
            
            ///////////////////////////Filtro por especialidad y servicio médico//////////////////////////////
            if ("".equals(cercania) &&  "".equals(rankeo) && !"".equals(especialidad) && !"".equals(servicios)) {
                sm = new ServiciosMedicos();
                smcrud = new ServiciosMedicosCRUD();
                sm.setTipo(servicios);
                lista3 = smcrud.seleccionar_tipo_servicio(sm);
                if(lista3 != null){
                    idh = new int[lista3.size()];
                    contador = 0;
                    for (int i = 0; i < lista3.size(); i++) {
                        m = new Medicos();
                        mcrud = new MedicosCRUD();
                        m.setEspecialidades(especialidad);
                        m.setIdHospital(lista3.get(i).getIdHospital());
                        m = mcrud.ServiciosMedicos(m);
                        if (m != null) {
                            idh[contador] = m.getIdHospital();
                            contador++;
                        }
                    }
                    if(contador != 0){
                        Integer promedio = 0;
                        out.println("<div class='container'>");
                        out.println("<h2> Coincidencia en: " + servicios + " y " + especialidad + "</h2>");
                        out.println("</div>");
                        for (int i = 0; i < contador; i++) {
                            h = new Hospitales();
                            hcrud = new HospitalCRUD();
                            h.setIdHospital(idh[i]);
                            h = hcrud.readOne1(h);
                            if(h != null){
                                rcrud = new RankeoCRUD();
                                promedio = rcrud.promedio(idh[i]);
                                out.println("<div class='container'>");
                                out.println("<div class='panel-group'>");
                                out.println("<div class='panel panel-default'>");
                                out.println("<div class='panel-body'>");

                                out.println("<div class='col-lg-3 sidenav' align = 'center'>");
                                out.println("<img src = '" + h.getFoto() + "' width= '225px'><br>");
                                out.println("</div>");
                                out.println("<div class='col-lg-6 text-left'>"); 
                                
                                out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                out.println("<input type ='hidden' name='id' value ='"+h.getIdHospital()+"'>");
                                out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                out.println("<h2 style='color:#1073c5' id='letras'>"+h.getNombre()+"</h2>");
                                out.println("</button></form>");
                        
                                out.println("<img src = 'Imagenes/" + promedio + "estrellas.png' width = '125px'><br><br>");
                                out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                out.println("Dirección: <br>");
                                out.println(h.getDireccion() + "<br>");
                                out.println("Teléfono : " + h.getTelefono());

                                out.println("</div>");
                                out.println("<div class='col-lg-2 sidenav' align='center'><br>");
                                out.println("<iframe width='250' height='150' "
                                        + "src='https://maps.google.com/maps?&hl=es&q="
                                        + (h.getNombre())
                                        + "&ie=UTF8&t=&z=12&iwloc=B&output=embed' "
                                        + "frameborder='0' scrolling='no' marginheight='0' marginwidth='0'></iframe>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                            }
                        }
                    }
                    else{
                        out.println("<div class='container'>");
                        out.println("<h2>Sugerencias</h2>");
                        out.println("</div>");
                        
                        out.println("<div class='container'>");
                        out.println("<h2>Servicio médico: "+ servicios +"</h2>");
                        out.println("</div>");
                        Integer promedio = 0;
                        idh = new int[lista3.size()];
                        contador = 0;
                        for (int i = 0; i < lista3.size(); i++) {
                            h = new Hospitales();
                            hcrud = new HospitalCRUD();
                            h.setIdHospital(lista3.get(i).getIdHospital());
                            h = hcrud.readOne1(h);
                            if(h != null){
                                out.println("<div class='container'>");
                                out.println("<div class='panel-group'>");
                                out.println("<div class='panel panel-default'>");
                                out.println("<div class='panel-body'>");

                                out.println("<div class='col-lg-3 sidenav' align = 'center'>");
                                out.println("<img src = '" + h.getFoto() + "' width= '225px'><br>");
                                out.println("</div>");
                                out.println("<div class='col-lg-6 text-left'>"); 
                                
                                out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                out.println("<input type ='hidden' name='id' value ='"+h.getIdHospital()+"'>");
                                out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                out.println("<h2 style='color:#1073c5' id='letras'>"+h.getNombre()+"</h2>");
                                out.println("</button></form>");
                        
                                out.println("<img src = 'Imagenes/" + h.getRankeo() + "estrellas.png' width = '125px'><br><br>");
                                out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                out.println("Dirección: <br>");
                                out.println(h.getDireccion() + "<br>");
                                out.println("Teléfono : " + h.getTelefono());

                                out.println("</div>");
                                out.println("<div class='col-lg-2 sidenav' align='center'><br>");
                                out.println("<iframe width='250' height='150' "
                                        + "src='https://maps.google.com/maps?&hl=es&q="
                                        + (h.getNombre())
                                        + "&ie=UTF8&t=&z=12&iwloc=B&output=embed' "
                                        + "frameborder='0' scrolling='no' marginheight='0' marginwidth='0'></iframe>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                            }  
                        }

                            
                        //Ahora se busca la especialidad con el ranking deseado     
                        out.println("<div class='container'>");
                        out.println("<h2>Especialidad médica: "+ especialidad +"</h2>");
                        out.println("</div>");
                        rm = new RankeoMedicos();
                        rmcrud = new RankeoMedicosCRUD();
                        m =  new Medicos();
                        mcrud =  new MedicosCRUD();
                        m.setEspecialidades(especialidad);
                        lista2 = mcrud.Hospitales_especialidad(m);
                        for(int i = 0; i < lista2.size(); i++){
                            h = new Hospitales();
                            hcrud = new HospitalCRUD();
                            h.setIdHospital(lista2.get(i).getIdHospital());
                            h = hcrud.readOne1(h);

                            m.setIdMedicos(lista2.get(i).getIdMedicos());
                            m = (Medicos)mcrud.readMedicos(m).get(0);
                            rm.setIdHospital(lista2.get(i).getIdHospital());
                            rm.setIdMedico(lista2.get(i).getIdMedicos());

                            out.println("<div class='container'>");
                            out.println("<div class='panel-group'>");
                            out.println("<div class='panel panel-default'>");
                            out.println("<div class='panel-body'>");

                            out.println("<div class='col-md-3 sidenav' align = 'center'>");
                            out.println("<img src = '/Prototipo4_1/Imagenes/doctor.png' width= '150px'>");
                            out.println("</div>");
                            out.println("<div class='col-md-6 text-left'>"); 
                            
                            out.println("<form action='/Prototipo4_1/Paginas/InformacionEspecialistas.jsp' method ='post'>");
                            out.println("<input type ='hidden' name='idhospital' value ='"+lista2.get(i).getIdHospital()+"'>");
                            out.println("<input type ='hidden' name='idusuario' value ='"+id+"'>");
                            out.println("<input type ='hidden' name='idmedico' value ='"+lista2.get(i).getIdMedicos()+"'>");
                            out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                            out.println("<h2 style='color:#1073c5' id='letras'>"+ m.getNombreMedico() + " " + m.getAPaternoMedico() + " " + m.getAMaternoMedico() +"</h2>");
                            out.println("</button></form>");

                            out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                            out.println("<input type ='hidden' name='id' value ='"+lista2.get(i).getIdHospital()+"'>");
                            out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                            out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                            out.println("<h7 style='color:#1073c5' id='letras'>"+h.getNombre()+"</h7>");
                            out.println("</button></form>");
                            
                            out.println("<img src = '/Prototipo4_1/Imagenes/"+lista2.get(i).getRankeo()+"estrellas.png' width = '125px'><br><br>");

                            out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                            out.println("Dirección: <br>");
                            out.println(h.getDireccion() + "<br>");
                            out.println("Teléfono : " + m.getTelefonos());

                            out.println("</div>");
                            out.println("</div>");
                            out.println("</div>");
                            out.println("</div>");
                            out.println("</div>");
                        }
                    }
                }
                else{
                    out.println("<div class='container'>");
                    out.println("<h3> No se encontraron resultados </h2>");
                    out.println("</div>");
                }
            }
            
            /////////////////////////////////Filtro por calificación///////////////////////////////////////////
            if ( "".equals(cercania) &&  !"".equals(rankeo) && "".equals(especialidad) && "".equals(servicios)) {
                hcrud = new HospitalCRUD();
                List<Hospitales> hospitales = hcrud.readAll1();
                int auxiliar = 0;
                boolean n = false;
                if(hospitales != null){
                    contador = 0;
                    for(int i = 0; i < hospitales.size(); i++){
                        r = new Rankeo();
                        rcrud = new RankeoCRUD();
                        rankeo_entero = Integer.parseInt(rankeo);
                        prom = rcrud.promedio(hospitales.get(i).getIdHospital());
                        if(prom == rankeo_entero){
                            if(!n){
                                out.println("<div class='container'>");
                                out.println("<h2> Calificación: "+ prom +" estrellas</h2>");
                                out.println("</div>");
                                n = true;
                            }
                            out.println("<div class='container'>");
                            out.println("<div class='panel-group'>");
                            out.println("<div class='panel panel-default'>");
                            out.println("<div class='panel-body'>");

                            out.println("<div class='col-lg-3 sidenav' align = 'center'>");
                            out.println("<img src = '" + hospitales.get(i).getFoto() + "' width= '225px'><br>");
                            out.println("</div>");
                            out.println("<div class='col-lg-6 text-left'>"); 
                            out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                            out.println("<input type ='hidden' name='id' value ='"+hospitales.get(i).getIdHospital()+"'>");
                            out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                            out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                            out.println("<h2 style='color:#1073c5' id='letras'>"+hospitales.get(i).getNombre()+"</h2>");
                            out.println("</button></form>");
                        
                            out.println("<img src = 'Imagenes/" + prom + "estrellas.png' width = '125px'><br><br>");
                            out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                            out.println("Dirección: <br>");
                            out.println(hospitales.get(i).getDireccion() + "<br>");
                            out.println("Teléfono : " + hospitales.get(i).getTelefono());
                            
                            out.println("</div>");
                            out.println("<div class='col-lg-2 sidenav' align='center'><br>");
                            out.println("<iframe width='250' height='150' "
                                    + "src='https://maps.google.com/maps?&hl=es&q="
                                    + (hospitales.get(i).getNombre())
                                    + "&ie=UTF8&t=&z=12&iwloc=B&output=embed' "
                                    + "frameborder='0' scrolling='no' marginheight='0' marginwidth='0'></iframe>");
                            out.println("</div>");
                            out.println("</div>");
                            out.println("</div>");
                            out.println("</div>");
                            out.println("</div>");
                            contador++;
                        }
                    }
                    n=false;
                    //4 estrellas
                    if(contador == 0){
                        contador1 = 0;
                    
                        for(int i = 0; i < hospitales.size(); i++){
                            r = new Rankeo();
                            rcrud = new RankeoCRUD();
                            rankeo_entero = Integer.parseInt(rankeo);
                            prom = rcrud.promedio(hospitales.get(i).getIdHospital());
                    
                            if(prom == rankeo_entero - 2){
                                if(n == false){
                                    out.println("<div class='container'>");
                                    out.println("<h2> Sugerencias </h2>");
                                    out.println("</div>");
                                    out.println("<div class='container'>");
                                    out.println("<h2> Calificación: "+(rankeo_entero - 2)+" estrellas </h2>");
                                    out.println("</div>");
                                    n = true;
                                }
                                out.println("<div class='container'>");
                                out.println("<div class='panel-group'>");
                                out.println("<div class='panel panel-default'>");
                                out.println("<div class='panel-body'>");

                                out.println("<div class='col-lg-3 sidenav' align = 'center'>");
                                out.println("<img src = '" + hospitales.get(i).getFoto() + "' width= '225px'><br>");
                                out.println("</div>");
                                out.println("<div class='col-lg-6 text-left'>"); 
                                
                                out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                out.println("<input type ='hidden' name='id' value ='"+hospitales.get(i).getIdHospital()+"'>");
                                out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                out.println("<h2 style='color:#1073c5' id='letras'>"+hospitales.get(i).getNombre()+"</h2>");
                                out.println("</button></form>");
                        
                                out.println("<img src = 'Imagenes/" + prom + "estrellas.png' width = '125px'><br><br>");
                                out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                out.println("Dirección: <br>");
                                out.println(hospitales.get(i).getDireccion() + "<br>");
                                out.println("Teléfono : " + hospitales.get(i).getTelefono());

                                out.println("</div>");
                                out.println("<div class='col-lg-2 sidenav' align='center'><br>");
                                out.println("<iframe width='250' height='150' "
                                        + "src='https://maps.google.com/maps?&hl=es&q="
                                        + (hospitales.get(i).getNombre())
                                        + "&ie=UTF8&t=&z=12&iwloc=B&output=embed' "
                                        + "frameborder='0' scrolling='no' marginheight='0' marginwidth='0'></iframe>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                                contador1++;
                            }
                        }
                    }
                        
                    n = false;
                    //3 estrellas    
                    if(contador1 == 0){
                        contador2 = 0;
                    
                        for(int i = 0; i < hospitales.size(); i++){
                            r = new Rankeo();
                            rcrud = new RankeoCRUD();
                            rankeo_entero = Integer.parseInt(rankeo);
                            prom = rcrud.promedio(hospitales.get(i).getIdHospital());
                    
                            if(prom == rankeo_entero - 3){
                                if(n == false){
                                    out.println("<div class='container'>");
                                    out.println("<h2> Sugerencias </h2>");
                                    out.println("</div>");
                                    out.println("<div class='container'>");
                                    out.println("<h2> Calificación: "+(rankeo_entero - 3)+" estrellas </h2>");
                                    out.println("</div>");
                                    n = true;
                                }
                                out.println("<div class='container'>");
                                out.println("<div class='panel-group'>");
                                out.println("<div class='panel panel-default'>");
                                out.println("<div class='panel-body'>");

                                out.println("<div class='col-lg-3 sidenav' align = 'center'>");
                                out.println("<img src = '" + hospitales.get(i).getFoto() + "' width= '225px'><br>");
                                out.println("</div>");
                                out.println("<div class='col-lg-6 text-left'>"); 
                                
                                out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                out.println("<input type ='hidden' name='id' value ='"+hospitales.get(i).getIdHospital()+"'>");
                                out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                out.println("<h2 style='color:#1073c5' id='letras'>"+hospitales.get(i).getNombre()+"</h2>");
                                out.println("</button></form>");
                        
                                out.println("<img src = 'Imagenes/" + prom + "estrellas.png' width = '125px'><br><br>");
                                out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                out.println("Dirección: <br>");
                                out.println(hospitales.get(i).getDireccion() + "<br>");
                                out.println("Teléfono : " + hospitales.get(i).getTelefono());

                                out.println("</div>");
                                out.println("<div class='col-lg-2 sidenav' align='center'><br>");
                                out.println("<iframe width='250' height='150' "
                                        + "src='https://maps.google.com/maps?&hl=es&q="
                                        + (hospitales.get(i).getNombre())
                                        + "&ie=UTF8&t=&z=12&iwloc=B&output=embed' "
                                        + "frameborder='0' scrolling='no' marginheight='0' marginwidth='0'></iframe>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                                contador2++;
                            }
                        }
                    }    
                        
                    n = false;
                    //2 estrellas    
                    if(contador2 == 0){
                        contador3 = 0;
                    
                        for(int i = 0; i < hospitales.size(); i++){
                            r = new Rankeo();
                            rcrud = new RankeoCRUD();
                            rankeo_entero = Integer.parseInt(rankeo);
                            prom = rcrud.promedio(hospitales.get(i).getIdHospital());
                    
                            if(prom == rankeo_entero - 4){
                                if(n == false){
                                    out.println("<div class='container'>");
                                    out.println("<h2> Sugerencias </h2>");
                                    out.println("</div>");
                                    out.println("<div class='container'>");
                                    out.println("<h2> Calificación: "+(rankeo_entero - 4)+" estrellas </h2>");
                                    out.println("</div>");
                                    n = true;
                                }
                                out.println("<div class='container'>");
                                out.println("<div class='panel-group'>");
                                out.println("<div class='panel panel-default'>");
                                out.println("<div class='panel-body'>");

                                out.println("<div class='col-lg-3 sidenav' align = 'center'>");
                                out.println("<img src = '" + hospitales.get(i).getFoto() + "' width= '225px'><br>");
                                out.println("</div>");
                                out.println("<div class='col-lg-6 text-left'>"); 
                                
                                out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                out.println("<input type ='hidden' name='id' value ='"+hospitales.get(i).getIdHospital()+"'>");
                                out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                out.println("<h2 style='color:#1073c5' id='letras'>"+hospitales.get(i).getNombre()+"</h2>");
                                out.println("</button></form>");
                        
                                out.println("<img src = 'Imagenes/" + prom + "estrellas.png' width = '125px'><br><br>");
                                out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                out.println("Dirección: <br>");
                                out.println(hospitales.get(i).getDireccion() + "<br>");
                                out.println("Teléfono : " + hospitales.get(i).getTelefono());

                                out.println("</div>");
                                out.println("<div class='col-lg-2 sidenav' align='center'><br>");
                                out.println("<iframe width='250' height='150' "
                                        + "src='https://maps.google.com/maps?&hl=es&q="
                                        + (hospitales.get(i).getNombre())
                                        + "&ie=UTF8&t=&z=12&iwloc=B&output=embed' "
                                        + "frameborder='0' scrolling='no' marginheight='0' marginwidth='0'></iframe>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                                contador3++;
                            }
                        }
                    }
                    
                    n = false;
                    //1 estrella
                    if(contador3 == 0){
                        contador4 = 0;
                    
                        for(int i = 0; i < hospitales.size(); i++){
                            r = new Rankeo();
                            rcrud = new RankeoCRUD();
                            rankeo_entero = Integer.parseInt(rankeo);
                            prom = rcrud.promedio(hospitales.get(i).getIdHospital());
                    
                            if(prom == rankeo_entero - 5){
                                if(n == false){
                                    out.println("<div class='container'>");
                                    out.println("<h2> Sugerencias </h2>");
                                    out.println("</div>");
                                    out.println("<div class='container'>");
                                    out.println("<h2> Calificación: "+(rankeo_entero - 5)+" estrellas </h2>");
                                    out.println("</div>");
                                    n = true;
                                }
                                out.println("<div class='container'>");
                                out.println("<div class='panel-group'>");
                                out.println("<div class='panel panel-default'>");
                                out.println("<div class='panel-body'>");

                                out.println("<div class='col-lg-3 sidenav' align = 'center'>");
                                out.println("<img src = '" + hospitales.get(i).getFoto() + "' width= '225px'><br>");
                                out.println("</div>");
                                out.println("<div class='col-lg-6 text-left'>"); 
                                
                                out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                out.println("<input type ='hidden' name='id' value ='"+hospitales.get(i).getIdHospital()+"'>");
                                out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                out.println("<h2 style='color:#1073c5' id='letras'>"+hospitales.get(i).getNombre()+"</h2>");
                                out.println("</button></form>");
                        
                                out.println("<img src = 'Imagenes/" + prom + "estrellas.png' width = '125px'><br><br>");
                                out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                out.println("Dirección: <br>");
                                out.println(hospitales.get(i).getDireccion() + "<br>");
                                out.println("Teléfono : " + hospitales.get(i).getTelefono());

                                out.println("</div>");
                                out.println("<div class='col-lg-2 sidenav' align='center'><br>");
                                out.println("<iframe width='250' height='150' "
                                        + "src='https://maps.google.com/maps?&hl=es&q="
                                        + (hospitales.get(i).getNombre())
                                        + "&ie=UTF8&t=&z=12&iwloc=B&output=embed' "
                                        + "frameborder='0' scrolling='no' marginheight='0' marginwidth='0'></iframe>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                                contador4++;
                            }
                        }
                    }
                        
                    n = false;
                    //0 estrellas
                    if(contador4 == 0){
                        for(int i = 0; i < hospitales.size(); i++){
                            r = new Rankeo();
                            rcrud = new RankeoCRUD();
                            rankeo_entero = Integer.parseInt(rankeo);
                            prom = rcrud.promedio(hospitales.get(i).getIdHospital());
                    
                            if(prom == rankeo_entero - 1){
                                if(n == false){
                                    out.println("<div class='container'>");
                                    out.println("<h2> Sugerencias </h2>");
                                    out.println("</div>");
                                    out.println("<div class='container'>");
                                    out.println("<h2> Calificación: "+(rankeo_entero - 1)+" estrellas </h2>");
                                    out.println("</div>");
                                    n = true;
                                }
                                
                                out.println("<div class='container'>");
                                out.println("<div class='panel-group'>");
                                out.println("<div class='panel panel-default'>");
                                out.println("<div class='panel-body'>");

                                out.println("<div class='col-lg-3 sidenav' align = 'center'>");
                                out.println("<img src = '" + hospitales.get(i).getFoto() + "' width= '225px'><br>");
                                out.println("</div>");
                                out.println("<div class='col-lg-6 text-left'>"); 
                                out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                out.println("<input type ='hidden' name='id' value ='"+hospitales.get(i).getIdHospital()+"'>");
                                out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                out.println("<h2 style='color:#1073c5' id='letras'>"+hospitales.get(i).getNombre()+"</h2>");
                                out.println("</button></form>");
                        
                                out.println("<img src = 'Imagenes/" + prom + "estrellas.png' width = '125px'><br><br>");
                                out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                out.println("Dirección: <br>");
                                out.println(hospitales.get(i).getDireccion() + "<br>");
                                out.println("Teléfono : " + hospitales.get(i).getTelefono());

                                out.println("</div>");
                                out.println("<div class='col-lg-2 sidenav' align='center'><br>");
                                out.println("<iframe width='250' height='150' "
                                        + "src='https://maps.google.com/maps?&hl=es&q="
                                        + (hospitales.get(i).getNombre())
                                        + "&ie=UTF8&t=&z=12&iwloc=B&output=embed' "
                                        + "frameborder='0' scrolling='no' marginheight='0' marginwidth='0'></iframe>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                            }
                        }
                    }
                }
            }
            
            
            
            ///////////////////////////Filtro por rankeo y servicio médico//////////////////////////////
            if ("".equals(cercania) &&  !"".equals(rankeo) && "".equals(especialidad) && !"".equals(servicios)) {
                sm = new ServiciosMedicos();
                smcrud = new ServiciosMedicosCRUD();
                r = new Rankeo();
                rcrud = new RankeoCRUD();
        
                sm.setTipo(servicios);
                lista3 = smcrud.seleccionar_tipo_servicio(sm);
                rankeo_entero = Integer.parseInt(rankeo);
                Integer promedio = 0;
                if (lista3 != null) {
                    idh = new int[lista3.size()];
                    contador = 0;
                    for (int i = 0; i < lista3.size(); i++) {
                        promedio = rcrud.promedio(lista3.get(i).getIdHospital());
                        if (promedio == rankeo_entero){
                            idh[contador] = lista3.get(i).getIdHospital();
                            contador++;
                        }
                    }
                    if (contador != 0) {
                        out.println("<div class='container'>");
                        out.println("<h2> Coincidencia en: "+servicios+" y calificación "+rankeo+"</h2>");
                        out.println("</div>");

                        for (int i = 0; i < contador; i++) {
                            h = new Hospitales();
                            hcrud = new HospitalCRUD();
                            h.setIdHospital(idh[i]);
                            h = hcrud.readOne1(h);
                            if(h != null){
                                out.println("<div class='container'>");
                                out.println("<div class='panel-group'>");
                                out.println("<div class='panel panel-default'>");
                                out.println("<div class='panel-body'>");

                                out.println("<div class='col-lg-3 sidenav' align = 'center'>");
                                out.println("<img src = '" + h.getFoto() + "' width= '225px'><br>");
                                out.println("</div>");
                                out.println("<div class='col-lg-6 text-left'>"); 
                                out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                out.println("<input type ='hidden' name='id' value ='"+h.getIdHospital()+"'>");
                                out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                out.println("<h2 style='color:#1073c5' id='letras'>"+h.getNombre()+"</h2>");
                                out.println("</button></form>");
                        
                                out.println("<img src = 'Imagenes/" + rankeo_entero + "estrellas.png' width = '125px'><br><br>");
                                out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                out.println("Dirección: <br>");
                                out.println(h.getDireccion() + "<br>");
                                out.println("Teléfono : " + h.getTelefono());
                                
                                out.println("</div>");
                                out.println("<div class='col-lg-2 sidenav' align='center'><br>");
                                out.println("<iframe width='250' height='150' "
                                        + "src='https://maps.google.com/maps?&hl=es&q="
                                        + (h.getNombre())
                                        + "&ie=UTF8&t=&z=12&iwloc=B&output=embed' "
                                        + "frameborder='0' scrolling='no' marginheight='0' marginwidth='0'></iframe>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                            }
                        }
                    }
                    //Agregado Sugerencias Servicio Medico
                    else if(contador == 0){
                        out.println("<div class='container'>");
                        out.println("<h2> Sugerencia </h2>");
                        out.println("</div>");

                        out.println("<div class='container'>");
                        out.println("<h2> Coincidencia en: "+servicios+"</h2>");
                        out.println("</div>");

                        for (int i = 0; i < lista3.size(); i++) {
                            h = new Hospitales();
                            hcrud = new HospitalCRUD();
                            h.setIdHospital(lista3.get(i).getIdHospital());
                            h = hcrud.readOne1(h);
                            
                            promedio = rcrud.promedio(lista3.get(i).getIdHospital());
                            if(h != null){
                                out.println("<div class='container'>");
                                out.println("<div class='panel-group'>");
                                out.println("<div class='panel panel-default'>");
                                out.println("<div class='panel-body'>");

                                out.println("<div class='col-lg-3 sidenav' align = 'center'>");
                                out.println("<img src = '" + h.getFoto() + "' width= '225px'><br>");
                                out.println("</div>");
                                out.println("<div class='col-lg-6 text-left'>"); 
                                out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                out.println("<input type ='hidden' name='id' value ='"+h.getIdHospital()+"'>");
                                out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                out.println("<h2 style='color:#1073c5' id='letras'>"+h.getNombre()+"</h2>");
                                out.println("</button></form>");
                        
                                out.println("<img src = 'Imagenes/" + promedio + "estrellas.png' width = '125px'><br><br>");
                                out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                out.println("Dirección: <br>");
                                out.println(h.getDireccion() + "<br>");
                                out.println("Teléfono : " + h.getTelefono());
                                
                                out.println("</div>");
                                out.println("<div class='col-lg-2 sidenav' align='center'><br>");
                                out.println("<iframe width='250' height='150' "
                                        + "src='https://maps.google.com/maps?&hl=es&q="
                                        + (h.getNombre())
                                        + "&ie=UTF8&t=&z=12&iwloc=B&output=embed' "
                                        + "frameborder='0' scrolling='no' marginheight='0' marginwidth='0'></iframe>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                            }
                        }
                    }
                }
                else{
                    out.println("<div class='container'>");
                    out.println("<h2> No se encontraron resultados </h2>");
                    out.println("</div>");
                }
            }
            
            
            ///////////////////////////Filtro por rankeo y especialidad//////////////////////////////
            if ("".equals(cercania) && !"".equals(rankeo) && !"".equals(especialidad) && "".equals(servicios)) {
                m = new Medicos();
                mcrud = new MedicosCRUD();
                rm = new RankeoMedicos();
                rmcrud = new RankeoMedicosCRUD();
                int promedio = 0;
                m.setEspecialidades(especialidad);
                lista2 = mcrud.Hospitales_especialidad(m);
                rankeo_entero = Integer.parseInt(rankeo);
                if(lista2 != null){
                    idh = new int[lista2.size()];
                    idm = new int[lista2.size()];
                    contador = 0;
                    for(int i = 0; i < lista2.size();i++){
                        rm.setIdHospital(lista2.get(i).getIdHospital());
                        rm.setIdMedico(lista2.get(i).getIdMedicos());
                        promedio = rmcrud.promedio(rm);
                
                        if(promedio == rankeo_entero){
                            idh[contador] = lista2.get(i).getIdHospital();
                            idm[contador] = lista2.get(i).getIdMedicos();
                            contador++;
                        }
                    }
                    if(contador != 0){
                        out.println("<div class='container'>");
                        out.println("<h2> Coincidencia en: "+servicios+" y calificación "+promedio+" </h2>");
                        out.println("</div>");

                        for(int i = 0; i < contador; i++){
                            h = new Hospitales();
                            hcrud = new HospitalCRUD();
                            h.setIdHospital(idh[i]);
                            h = hcrud.readOne1(h);

                            m.setIdMedicos(idm[i]);
                            m = (Medicos)mcrud.readMedicos(m).get(0);
                            rm.setIdHospital(idh[i]);
                            rm.setIdMedico(idm[i]);
                            promedio = rmcrud.promedio(rm);

                    
                            out.println("<div class='container'>");
                            out.println("<div class='panel-group'>");
                            out.println("<div class='panel panel-default'>");
                            out.println("<div class='panel-body'>");

                            out.println("<div class='col-md-3 sidenav' align = 'center'>");
                            out.println("<img src = '/Prototipo4_1/Imagenes/doctor.png' width= '150px'>");
                            out.println("</div>");
                            out.println("<div class='col-md-6 text-left'>"); 
                            out.println("<form action='/Prototipo4_1/Paginas/InformacionEspecialistas.jsp' method ='post'>");
                            out.println("<input type ='hidden' name='idhospital' value ='"+idh[i]+"'>");
                            out.println("<input type ='hidden' name='idusuario' value ='"+id+"'>");
                            out.println("<input type ='hidden' name='idmedico' value ='"+idm[i]+"'>");
                            out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                            out.println("<h2 style='color:#1073c5' id='letras'>"+m.getNombreMedico() + " " + m.getAPaternoMedico() + " " + m.getAMaternoMedico()+"</h2>");
                            out.println("</button></form>");

                            out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                            out.println("<input type ='hidden' name='id' value ='"+h.getIdHospital()+"'>");
                            out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                            out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                            out.println("<h7 style='color:#1073c5' id='letras'>"+h.getNombre()+"</h7>");
                            out.println("</button></form>");
                            out.println("<img src = '/Prototipo4_1/Imagenes/"+rankeo_entero+"estrellas.png' width = '125px'><br><br>");

                            out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                            out.println("Dirección: <br>");
                            out.println(h.getDireccion() + "<br>");
                            out.println("Teléfono : " + m.getTelefonos());
                            
                            out.println("</div>");
                            out.println("</div>");
                            out.println("</div>");
                            out.println("</div>");
                            out.println("</div>");
                        }
                    }
                    //Agregado recomendación especialidad
                    else if(contador == 0){
                        out.println("<div class='container'>");
                        out.println("<h2> Sugerencias </h2>");
                        out.println("<div class='container'>");
                        out.println("<h2> Coincidencia en: "+especialidad+"</h2>");
                        out.println("</div>");
                        for(int i = 0; i < lista2.size();i++){
                            h = new Hospitales();
                            hcrud = new HospitalCRUD();
                            h.setIdHospital(lista2.get(i).getIdHospital());
                            h = hcrud.readOne1(h);
                           
                            rm = new RankeoMedicos();
                            rmcrud = new RankeoMedicosCRUD();
                            rm.setIdHospital(lista2.get(i).getIdHospital());
                            rm.setIdMedico(lista2.get(i).getIdMedicos());
                            ranking = rmcrud.promedio(rm);
                            
                            out.println("<div class='container'>");
                            out.println("<div class='panel-group'>");
                            out.println("<div class='panel panel-default'>");
                            out.println("<div class='panel-body'>");

                            out.println("<div class='col-md-3 sidenav' align = 'center'>");
                            out.println("<img src = '/Prototipo4_1/Imagenes/doctor.png' width= '150px'>");
                            out.println("</div>");
                            out.println("<div class='col-md-6 text-left'>"); 
                            out.println("<a href = '/Prototipo4_1/Paginas/InformacionEspecialistas.jsp?idhospital=" + h.getIdHospital() + "&idusuario=" + id + "&idmedico=" +lista2.get(i).getIdMedicos()+ "' style = 'font-size:35px'> "
                                    + lista2.get(i).getNombreMedico() + " " + lista2.get(i).getAPaternoMedico() + " " + lista2.get(i).getAMaternoMedico() + "</a><br>");
                            out.println("<a href = '/Prototipo4_1/Paginas/Informacion_Hospital.jsp?id=" + h.getIdHospital() + "&usuario=" + id + "' style = 'font-size:15px'>" + h.getNombre() + "</a><br>");
                            out.println("<img src = '/Prototipo4_1/Imagenes/"+ranking+"estrellas.png' width = '125px'><br><br>");
                            
                            out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                            out.println("Dirección: <br>");
                            out.println(h.getDireccion() + "<br>");
                            out.println("Teléfono : " + lista2.get(i).getTelefonos());
                            
                            out.println("</div>");
                            out.println("</div>");
                            out.println("</div>");
                            out.println("</div>");
                            out.println("</div>");
                        }
                    }
                }
                else{
                    out.println("<div class='container'>");
                    out.println("<h2> No se encontraron resultados </h2>");
                    out.println("</div>");
                }
            }
        
        
        
            ///////////////////////////Filtro por rankeo, especialidad y servicios//////////////////////////////
            if ("".equals(cercania) && !"".equals(rankeo) && !"".equals(especialidad) && !"".equals(servicios)) {
                sm = new ServiciosMedicos();
                smcrud = new ServiciosMedicosCRUD();
                r = new Rankeo();
                rcrud = new RankeoCRUD();
                m = new Medicos();
                mcrud = new MedicosCRUD();
                sm.setTipo(servicios);
                lista3 = smcrud.seleccionar_tipo_servicio(sm);
                rankeo_entero = Integer.parseInt(rankeo);
                Integer promedio = 0;
                if (lista3 != null) {
                    idh = new int[lista3.size()];
                    contador = 0; contador1 = 0;
                    contador2 = 0; contador3 = 0;
                    contador4 = 0; contador5 = 0;
                    
                    for (int i = 0; i < lista3.size(); i++) {
                        rcrud = new RankeoCRUD();
                        promedio = rcrud.promedio(lista3.get(i).getIdHospital());
                        if (promedio == rankeo_entero){
                            idh[contador] = lista3.get(i).getIdHospital();
                            contador++;
                        }
                    }
                    if (contador != 0) {
                        out.println("<div class='container'>");
                        out.println("<h2> Coincidencia en: " + servicios + ", "+especialidad+", calificación "+ rankeo+"</h2>");
                        out.println("</div>");
                        
                        for (int i = 0; i < contador; i++) {
                            h = new Hospitales();
                            hcrud = new HospitalCRUD();
                            h.setIdHospital(idh[i]);
                            h = hcrud.readOne1(h);
                            if(h != null){
                                out.println("<div class='container'>");
                                out.println("<div class='panel-group'>");
                                out.println("<div class='panel panel-default'>");
                                out.println("<div class='panel-body'>");

                                out.println("<div class='col-lg-3 sidenav' align = 'center'>");
                                out.println("<img src = '" + h.getFoto() + "' width= '225px'><br>");
                                out.println("</div>");
                                out.println("<div class='col-lg-6 text-left'>"); 
                                out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                out.println("<input type ='hidden' name='id' value ='"+h.getIdHospital()+"'>");
                                out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                out.println("<h2 style='color:#1073c5' id='letras'>"+h.getNombre()+"</h2>");
                                out.println("</button></form>");
                        
                                out.println("<img src = 'Imagenes/" + rankeo_entero + "estrellas.png' width = '125px'><br><br>");
                                out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                out.println("Dirección: <br>");
                                out.println(h.getDireccion() + "<br>");
                                out.println("Teléfono : " + h.getTelefono());
                                
                                out.println("</div>");
                                out.println("<div class='col-lg-2 sidenav' align='center'><br>");
                                out.println("<iframe width='250' height='150' "
                                        + "src='https://maps.google.com/maps?&hl=es&q="
                                        + (h.getNombre())
                                        + "&ie=UTF8&t=&z=12&iwloc=B&output=embed' "
                                        + "frameborder='0' scrolling='no' marginheight='0' marginwidth='0'></iframe>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                            }
                        }
                    }
                    
                    //Si no hay datos exactos requeridos por el usuario se hacen otras busquedas
                    else if(contador == 0){
                        //Buscar servicio médico con el ranking deseado
                        idh = new int[lista3.size()];
                        contador = 0;
                        for (int i = 0; i < lista3.size(); i++) {
                            promedio = rcrud.promedio(lista3.get(i).getIdHospital());
                            if (promedio == rankeo_entero){
                                idh[contador] = lista3.get(i).getIdHospital();
                                contador++;
                            }
                        }
                        if (contador != 0) {
                            out.println("<div class='container'>");
                            out.println("<h2>Sugerencias:</h2>");
                            out.println("</div>");
                            out.println("<div class='container'>");
                            out.println("<h2> Coincidencia: "+servicios+"</h2>");
                            out.println("</div>");
                            for (int i = 0; i < contador; i++) {
                                h = new Hospitales();
                                hcrud = new HospitalCRUD();
                                h.setIdHospital(idh[i]);
                                h = hcrud.readOne1(h);
                                if(h != null){
                                    out.println("<div class='container'>");
                                    out.println("<div class='panel-group'>");
                                    out.println("<div class='panel panel-default'>");
                                    out.println("<div class='panel-body'>");

                                    out.println("<div class='col-lg-3 sidenav' align = 'center'>");
                                    out.println("<img src = '" + h.getFoto() + "' width= '225px'><br>");
                                    out.println("</div>");
                                    out.println("<div class='col-lg-6 text-left'>"); 
                                    out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                    out.println("<input type ='hidden' name='id' value ='"+h.getIdHospital()+"'>");
                                    out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                    out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                    out.println("<h2 style='color:#1073c5' id='letras'>"+h.getNombre()+"</h2>");
                                    out.println("</button></form>");
                        
                                    out.println("<img src = 'Imagenes/" + rankeo_entero + "estrellas.png' width = '125px'><br><br>");
                                    out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                    out.println("Dirección: <br>");
                                    out.println(h.getDireccion() + "<br>");
                                    out.println("Teléfono : " + h.getTelefono());

                                    out.println("</div>");
                                    out.println("<div class='col-lg-2 sidenav' align='center'><br>");
                                    out.println("<iframe width='250' height='150' "
                                            + "src='https://maps.google.com/maps?&hl=es&q="
                                            + (h.getNombre())
                                            + "&ie=UTF8&t=&z=12&iwloc=B&output=embed' "
                                            + "frameborder='0' scrolling='no' marginheight='0' marginwidth='0'></iframe>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                }
                            }
                        }

                        /*Si no se encuentra el servicio médico con el ranking deseado, se busca
                          el mismo servicio pero con diferente rankeo*/ 
                        else if(contador == 0){
                            out.println("<div class='container'>");
                            out.println("<h2>Sugerencias:</h2>");
                            out.println("</div>");
                            out.println("<div class='container'>");
                            out.println("<h2> Coincidencia en: "+servicios);
                            out.println("</div>");
                            
                            for (int i = 0; i < lista3.size(); i++) {
                                h = new Hospitales();
                                hcrud = new HospitalCRUD();
                                h.setIdHospital(lista3.get(i).getIdHospital());
                                h = hcrud.readOne1(h);
                                promedio = rcrud.promedio(lista3.get(i).getIdHospital());
                                if(h != null){
                                    out.println("<div class='container'>");
                                    out.println("<div class='panel-group'>");
                                    out.println("<div class='panel panel-default'>");
                                    out.println("<div class='panel-body'>");

                                    out.println("<div class='col-lg-3 sidenav' align = 'center'>");
                                    out.println("<img src = '" + h.getFoto() + "' width= '225px'><br>");
                                    out.println("</div>");
                                    out.println("<div class='col-lg-6 text-left'>"); 
                                    out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                    out.println("<input type ='hidden' name='id' value ='"+h.getIdHospital()+"'>");
                                    out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                    out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                    out.println("<h2 style='color:#1073c5' id='letras'>"+h.getNombre()+"</h2>");
                                    out.println("</button></form>");
                        
                                    out.println("<img src = 'Imagenes/" + promedio + "estrellas.png' width = '125px'><br><br>");
                                    out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                    out.println("Dirección: <br>");
                                    out.println(h.getDireccion() + "<br>");
                                    out.println("Teléfono : " + h.getTelefono());

                                    out.println("</div>");
                                    out.println("<div class='col-lg-2 sidenav' align='center'><br>");
                                    out.println("<iframe width='250' height='150' "
                                            + "src='https://maps.google.com/maps?&hl=es&q="
                                            + (h.getNombre())
                                            + "&ie=UTF8&t=&z=12&iwloc=B&output=embed' "
                                            + "frameborder='0' scrolling='no' marginheight='0' marginwidth='0'></iframe>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                }
                            }
                            
                            //Ahora se busca la especialidad con el ranking deseado     
                            rm = new RankeoMedicos();
                            rmcrud = new RankeoMedicosCRUD();
                            m.setEspecialidades(especialidad);
                            lista2 = mcrud.Hospitales_especialidad(m);
                            if(lista2 != null){
                                idh = new int[lista2.size()];
                                idm = new int[lista2.size()];
                                contador = 0;
                                for(int i = 0; i < lista2.size();i++){
                                    rm.setIdHospital(lista2.get(i).getIdHospital());
                                    rm.setIdMedico(lista2.get(i).getIdMedicos());
                                    promedio = rmcrud.promedio(rm);

                                    if(promedio == rankeo_entero){
                                        idh[contador] = lista2.get(i).getIdHospital();
                                        idm[contador] = lista2.get(i).getIdMedicos();
                                        contador++;
                                    }
                                }
                                if(contador != 0){
                                    out.println("<div class='container'>");
                                    out.println("<h2> Coincidencia en: "+especialidad);
                                    //out.println("<h2> Especialidad: "+especialidad+" y calificación: "+promedio+"</h2>");
                                    out.println("</div>");
                            
                                    for(int i = 0; i < contador; i++){
                                        h = new Hospitales();
                                        hcrud = new HospitalCRUD();
                                        h.setIdHospital(idh[i]);
                                        h = hcrud.readOne1(h);

                                        m.setIdMedicos(idm[i]);
                                        m = (Medicos)mcrud.readMedicos(m).get(0);
                                        rm.setIdHospital(idh[i]);
                                        rm.setIdMedico(idm[i]);
                                        promedio = rmcrud.promedio(rm);


                                        out.println("<div class='container'>");
                                        out.println("<div class='panel-group'>");
                                        out.println("<div class='panel panel-default'>");
                                        out.println("<div class='panel-body'>");

                                        out.println("<div class='col-md-3 sidenav' align = 'center'>");
                                        out.println("<img src = '/Prototipo4_1/Imagenes/doctor.png' width= '150px'>");
                                        out.println("</div>");
                                        out.println("<div class='col-md-6 text-left'>"); 
                                        
                                        out.println("<form action='/Prototipo4_1/Paginas/InformacionEspecialistas.jsp' method ='post'>");
                                        out.println("<input type ='hidden' name='idhospital' value ='"+idh[i]+"'>");
                                        out.println("<input type ='hidden' name='idusuario' value ='"+id+"'>");
                                        out.println("<input type ='hidden' name='idmedico' value ='"+idm[i]+"'>");
                                        out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                        out.println("<h2 style='color:#1073c5' id='letras'>"+m.getNombreMedico() + " " + m.getAPaternoMedico() + " " + m.getAMaternoMedico()+"</h2>");
                                        out.println("</button></form>");

                                        out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                        out.println("<input type ='hidden' name='id' value ='"+h.getIdHospital()+"'>");
                                        out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                        out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                        out.println("<h7 style='color:#1073c5' id='letras'>"+h.getNombre()+"</h7>");
                                        out.println("</button></form>");
                            
                                        out.println("<img src = '/Prototipo4_1/Imagenes/"+rankeo_entero+"estrellas.png' width = '125px'><br><br>");

                                        out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                        out.println("Dirección: <br>");
                                        out.println(h.getDireccion() + "<br>");
                                        out.println("Teléfono : " + m.getTelefonos());

                                        out.println("</div>");
                                        out.println("</div>");
                                        out.println("</div>");
                                        out.println("</div>");
                                        out.println("</div>");
                                    }
                                }
                                //Agregado recomendación especialidad
                                else if(contador == 0){
                                    out.println("<div class='container'>");
                                    out.println("<h2> Coincidencia en: "+especialidad+"</h2>");
                                    //out.println("<h2> Especialidad médica: "+especialidad+"</h2>");
                                    out.println("</div>");
                                    for(int i = 0; i < lista2.size();i++){
                                        h = new Hospitales();
                                        hcrud = new HospitalCRUD();
                                        h.setIdHospital(lista2.get(i).getIdHospital());
                                        h = hcrud.readOne1(h);

                                        rm = new RankeoMedicos();
                                        rmcrud = new RankeoMedicosCRUD();
                                        rm.setIdHospital(lista2.get(i).getIdHospital());
                                        rm.setIdMedico(lista2.get(i).getIdMedicos());
                                        ranking = rmcrud.promedio(rm);

                                        out.println("<div class='container'>");
                                        out.println("<div class='panel-group'>");
                                        out.println("<div class='panel panel-default'>");
                                        out.println("<div class='panel-body'>");

                                        out.println("<div class='col-md-3 sidenav' align = 'center'>");
                                        out.println("<img src = '/Prototipo4_1/Imagenes/doctor.png' width= '150px'>");
                                        out.println("</div>");
                                        out.println("<div class='col-md-6 text-left'>"); 
                                        out.println("<form action='/Prototipo4_1/Paginas/InformacionEspecialistas.jsp' method ='post'>");
                                        out.println("<input type ='hidden' name='idhospital' value ='"+h.getIdHospital() +"'>");
                                        out.println("<input type ='hidden' name='idusuario' value ='"+id+"'>");
                                        out.println("<input type ='hidden' name='idmedico' value ='"+lista2.get(i).getIdMedicos()+"'>");
                                        out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                        out.println("<h2 style='color:#1073c5' id='letras'>"+lista2.get(i).getNombreMedico() + " " + lista2.get(i).getAPaternoMedico() + " " + lista2.get(i).getAMaternoMedico()+"</h2>");
                                        out.println("</button></form>");

                                        out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                        out.println("<input type ='hidden' name='id' value ='"+h.getIdHospital()+"'>");
                                        out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                        out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                        out.println("<h7 style='color:#1073c5' id='letras'>"+h.getNombre()+"</h7>");
                                        out.println("</button></form>");
                            
                                        out.println("<img src = '/Prototipo4_1/Imagenes/"+ranking+"estrellas.png' width = '125px'><br><br>");

                                        out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                        out.println("Dirección: <br>");
                                        out.println(h.getDireccion() + "<br>");
                                        out.println("Teléfono : " + lista2.get(i).getTelefonos());

                                        out.println("</div>");
                                        out.println("</div>");
                                        out.println("</div>");
                                        out.println("</div>");
                                        out.println("</div>");
                                    }
                                }
                            }
                        } 
                    }
                }
                else{
                    out.println("<div class='container'>");
                    out.println("<h2> No se encontraron resultados </h2>");
                    out.println("</div>");
                }
            }
            
            
            //////////////////////////////////Filtro por cercania///////////////////////////////////////////////
            if (!"".equals(cercania) && "".equals(rankeo) && "".equals(especialidad) && "".equals(servicios)) {
                pcrud = new PacienteDAO();
                p = pcrud.seleccionar_informacion(id);
                h = new Hospitales();
                hcrud = new HospitalCRUD();
                
                if("Si".equals(cercania)){
                    out.println("<div class='container'>");
                    out.println("<h2> Coincidencia en alcaldía: "+p.getDelegacion()+"</h2>");
                    out.println("</div>");
                            
                    lista = hcrud.readAll1();
                    Integer promedio = 0;
                    contador = 0;
                    if (lista != null) {
                        for (int i = 0; i < lista.size(); i++) {
                            rcrud = new RankeoCRUD();
                            promedio = rcrud.promedio(lista.get(i).getIdHospital());
                            String delegacion = lista.get(i).getDireccion().substring(lista.get(i).getDireccion().indexOf(","), lista.get(i).getDireccion().length());
                            if(delegacion.contains(p.getDelegacion())){
                                out.println("<div class='container'>");
                                out.println("<div class='panel-group'>");
                                out.println("<div class='panel panel-default'>");
                                out.println("<div class='panel-body'>");

                                out.println("<div class='col-lg-3 sidenav' align = 'center'>");
                                out.println("<img src = '" + lista.get(i).getFoto() + "' width= '225px'><br>");
                                out.println("</div>");
                                out.println("<div class='col-lg-6 text-left'>"); 
                                out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                out.println("<input type ='hidden' name='id' value ='"+lista.get(i).getIdHospital()+"'>");
                                out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                out.println("<h2 style='color:#1073c5' id='letras'>"+lista.get(i).getNombre()+"</h2>");
                                out.println("</button></form>");
                        
                                out.println("<img src = 'Imagenes/" + promedio + "estrellas.png' width = '125px'><br><br>");
                                out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                out.println("Dirección: <br>");
                                out.println(lista.get(i).getDireccion() + "<br>");
                                out.println("Teléfono : " + lista.get(i).getTelefono());

                                out.println("</div>");
                                out.println("<div class='col-lg-2 sidenav' align='center'><br>");
                                out.println("<iframe width='250' height='150' "
                                        + "src='https://maps.google.com/maps?&hl=es&q="
                                        + (lista.get(i).getNombre())
                                        + "&ie=UTF8&t=&z=12&iwloc=B&output=embed' "
                                        + "frameborder='0' scrolling='no' marginheight='0' marginwidth='0'></iframe>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                                contador++;
                            }
                        }
                        
                        //No encontró nada de hospitales cercanos en la delegación del paciente
                        pivote = 0;
                        if(contador == 0){
                            out.println("<div class='container'>");
                            out.println("<h2> Sugerencias: </h2>");
                            out.println("</div>");
                            out.println("<div class='container'>");
                            out.println("<h2> Alcaldías vecinas </h2>");
                            out.println("</div>");
                            for(int i = 0; i < 16; i++){
                                if(p.getDelegacion().equals(alcaldias[0][i])){
                                    pivote = i;
                                }
                            }
                            contador = 0;
                            for(int i = 0; i < 8; i++){
                                if(alcaldias[i][pivote] != null){
                                    contador++;
                                }else{
                                    break;
                                }
                            }
                            
                            for(int i = 0; i < contador; i++){
                                h.setDireccion(alcaldias[i][pivote]);
                                lista = hcrud.readbyaddress(h);
                                if(lista != null){
                                    for(int j = 0; j < lista.size(); j++){
                                        rcrud = new RankeoCRUD();
                                        promedio = rcrud.promedio(lista.get(j).getIdHospital());
                                        
                                        out.println("<div class='container'>");
                                        out.println("<div class='panel-group'>");
                                        out.println("<div class='panel panel-default'>");
                                        out.println("<div class='panel-body'>");

                                        out.println("<div class='col-lg-3 sidenav' align = 'center'>");
                                        out.println("<img src = '" + lista.get(j).getFoto() + "' width= '225px'><br>");
                                        out.println("</div>");
                                        out.println("<div class='col-lg-6 text-left'>"); 
                                        
                                        out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                        out.println("<input type ='hidden' name='id' value ='"+lista.get(j).getIdHospital()+"'>");
                                        out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                        out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                        out.println("<h2 style='color:#1073c5' id='letras'>"+lista.get(j).getNombre()+"</h2>");
                                        out.println("</button></form>");
                        
                                        out.println("<img src = 'Imagenes/" + promedio + "estrellas.png' width = '125px'><br><br>");
                                        out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                        out.println("Dirección: <br>");
                                        out.println(lista.get(j).getDireccion() + "<br>");
                                        out.println("Teléfono : " + lista.get(j).getTelefono());

                                        out.println("</div>");
                                        out.println("<div class='col-lg-2 sidenav' align='center'><br>");
                                        out.println("<iframe width='250' height='150' "
                                                + "src='https://maps.google.com/maps?&hl=es&q="
                                                + (lista.get(j).getNombre())
                                                + "&ie=UTF8&t=&z=12&iwloc=B&output=embed' "
                                                + "frameborder='0' scrolling='no' marginheight='0' marginwidth='0'></iframe>");
                                        out.println("</div>");
                                        out.println("</div>");
                                        out.println("</div>");
                                        out.println("</div>");
                                        out.println("</div>");
                                    }
                                }
                            }
                        }
                    }
                    
                    //Mandar hospitales cercanos
                    else{
                        out.println("<div class='container'>");
                        out.println("<h2> Sugerencias: </h2>");
                        out.println("</div>");
                        
                        for (int i = 0; i < lista.size(); i++) {
                            rcrud = new RankeoCRUD();
                            promedio = rcrud.promedio(lista.get(i).getIdHospital());
                            String delegacion = lista.get(i).getDireccion().substring(lista.get(i).getDireccion().indexOf(","), lista.get(i).getDireccion().length());
                            if(delegacion.contains(p.getDelegacion())){
                                out.println("<div class='container'>");
                                out.println("<div class='panel-group'>");
                                out.println("<div class='panel panel-default'>");
                                out.println("<div class='panel-body'>");

                                out.println("<div class='col-lg-3 sidenav' align = 'center'>");
                                out.println("<img src = '" + lista.get(i).getFoto() + "' width= '225px'><br>");
                                out.println("</div>");
                                out.println("<div class='col-lg-6 text-left'>"); 
                                
                                out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                out.println("<input type ='hidden' name='id' value ='"+lista.get(i).getIdHospital()+"'>");
                                out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                out.println("<h2 style='color:#1073c5' id='letras'>"+lista.get(i).getNombre()+"</h2>");
                                out.println("</button></form>");
                        
                                out.println("<img src = 'Imagenes/" + promedio + "estrellas.png' width = '125px'><br><br>");
                                out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                out.println("Dirección: <br>");
                                out.println(lista.get(i).getDireccion() + "<br>");
                                out.println("Teléfono : " + lista.get(i).getTelefono());

                                out.println("</div>");
                                out.println("<div class='col-lg-2 sidenav' align='center'><br>");
                                out.println("<iframe width='250' height='150' "
                                        + "src='https://maps.google.com/maps?&hl=es&q="
                                        + (lista.get(i).getNombre())
                                        + "&ie=UTF8&t=&z=12&iwloc=B&output=embed' "
                                        + "frameborder='0' scrolling='no' marginheight='0' marginwidth='0'></iframe>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                            }
                        }
                    }
                }
                else{
                    out.println("<div class='container'>");
                    out.println("<h2> No hay hospitales cercanos: </h2>");
                    out.println("</div>");
                    out.println("<div class='container'>");
                    out.println("<h2> Sugerencias de hospitales: </h2>");
                    out.println("</div>");
                    lista = hcrud.readAll1();
                    Integer promedio = 0;
                    if (lista != null) {
                        for (int i = 0; i < lista.size(); i++) {
                            rcrud = new RankeoCRUD();
                            promedio = rcrud.promedio(lista.get(i).getIdHospital());
                            out.println("<div class='container'>");
                            out.println("<div class='panel-group'>");
                            out.println("<div class='panel panel-default'>");
                            out.println("<div class='panel-body'>");

                            out.println("<div class='col-lg-3 sidenav' align = 'center'>");
                            out.println("<img src = '" + lista.get(i).getFoto()+ "' width= '225px'><br>");
                            out.println("</div>");
                            out.println("<div class='col-lg-6 text-left'>"); 
                            out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                            out.println("<input type ='hidden' name='id' value ='"+lista.get(i).getIdHospital()+"'>");
                            out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                            out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                            out.println("<h2 style='color:#1073c5' id='letras'>"+lista.get(i).getNombre()+"</h2>");
                            out.println("</button></form>");

                            out.println("<img src = 'Imagenes/" + promedio + "estrellas.png' width = '125px'><br><br>");
                            out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                            out.println("Dirección: <br>");
                            out.println(lista.get(i).getDireccion() + "<br>");
                            out.println("Teléfono : " + lista.get(i).getTelefono());

                            out.println("</div>");
                            out.println("<div class='col-lg-2 sidenav' align='center'><br>");
                            out.println("<iframe width='250' height='150' "
                                    + "src='https://maps.google.com/maps?&hl=es&q="
                                    + (lista.get(i).getNombre())
                                    + "&ie=UTF8&t=&z=12&iwloc=B&output=embed' "
                                    + "frameborder='0' scrolling='no' marginheight='0' marginwidth='0'></iframe>");
                            out.println("</div>");
                            out.println("</div>");
                            out.println("</div>");
                            out.println("</div>");
                            out.println("</div>");
                        }
                    }
                }
            }
            
            //Tiene problema con la seleccion de cercania, no hace la condicion de contains "checar"
            //checar tambien lo de la seleccion de sevicio médico porque está raro
            
            ///////////////////////////Filtro por servicios y cercanía/////////////////////////////////////////
            if (!"".equals(cercania) && "".equals(rankeo) && "".equals(especialidad) && !"".equals(servicios)) {
                pcrud = new PacienteDAO();
                p = pcrud.seleccionar_informacion(id);
                boolean n = false;
                h = new Hospitales();
                hcrud = new HospitalCRUD();
                if("Si".equals(cercania)){
                    sm = new ServiciosMedicos();
                    smcrud = new ServiciosMedicosCRUD();
                    sm.setTipo(servicios);
                    lista3 = smcrud.seleccionar_tipo_servicio(sm);
                    if (lista3 != null) {
                        Integer promedio = 0;
                        contador = 0;
                        for (int i = 0; i < lista3.size(); i++) {
                            h = new Hospitales();
                            hcrud = new HospitalCRUD();
                            h.setIdHospital(lista3.get(i).getIdHospital());
                            h = hcrud.readOne1(h);
                            String delegacion = h.getDireccion().substring(h.getDireccion().indexOf(","), h.getDireccion().length());
                            if(delegacion.contains(p.getDelegacion())){
                                if(n==false){
                                    out.println("<div class='container'>");
                                    out.println("<h2> Coincidencia en: "+p.getDelegacion()+", "+servicios+"</h2>");
                                    //out.println("<h2> Coincidencia en: "+p.getDelegacion()+" y servicio médico: "+servicios+"</h2>");
                                    out.println("</div>");
                                    n = true;
                                } 
                                idHospital = h.getIdHospital();
                                foto = h.getFoto();
                                nombre = h.getNombre();
                                direccion = h.getDireccion();
                                telefono = h.getTelefono();
                                ranking = h.getRankeo();
                                pagina = h.getPagina();

                                rcrud = new RankeoCRUD();
                                prom = rcrud.promedio(idHospital);

                                out.println("<div class='container'>");
                                out.println("<div class='panel-group'>");
                                out.println("<div class='panel panel-default'>");
                                out.println("<div class='panel-body'>");

                                out.println("<div class='col-lg-3 sidenav' align = 'center'>");
                                out.println("<img src = '" + foto + "' width= '225px'>");
                                out.println("</div>");
                                out.println("<div class='col-lg-6 text-left'>"); 

                                out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                out.println("<input type ='hidden' name='id' value ='"+idHospital+"'>");
                                out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                out.println("<h2 style='color:#1073c5' id='letras'>"+nombre+"</h2>");
                                out.println("</button></form>");

                                out.println("<img src = 'Imagenes/" + prom + "estrellas.png' width = '125px'><br><br>");
                                out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                out.println("Dirección: <br>");
                                out.println(direccion + "<br>");
                                out.println("Teléfono : " + telefono);

                                out.println("</div>");
                                out.println("<div class='col-lg-2 sidenav' align='center'><br>");
                                out.println("<iframe width='250' height='150' "
                                        + "src='https://maps.google.com/maps?&hl=es&q="
                                        + (nombre)
                                        + "&ie=UTF8&t=&z=12&iwloc=B&output=embed' "
                                        + "frameborder='0' scrolling='no' marginheight='0' marginwidth='0'></iframe>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                                contador++;
                            }
                        }
                        /*
                        //No encontró nada de hospitales cercanos en la delegación del paciente
                        pivote = 0;
                        if(contador == 0){
                            for(int i = 0; i < 16; i++){
                                if(p.getDelegacion().equals(alcaldias[0][i])){
                                    pivote = i;
                                }
                            }
                            contador = 0;
                            for(int i = 1; i < 8; i++){
                                if(alcaldias[i][pivote] != null){
                                    contador++;
                                }else{
                                    break;
                                }
                            }
                            out.println("<div class='container'>");
                            out.println("<h2> Sugerencias </h2>");
                            out.println("</div>");
                            contador2 = 0;
                            for(int i = 0; i < contador; i++){
                                h.setDireccion(alcaldias[i][pivote]);
                                lista = hcrud.readbyaddress(h);
                                if(lista != null){
                                    for(int j = 0; j < lista.size(); j++){
                                        rcrud = new RankeoCRUD();
                                        promedio = rcrud.promedio(lista.get(j).getIdHospital());
                                        smcrud = new ServiciosMedicosCRUD();
                                        sm =  new ServiciosMedicos();
                                        sm.setIdHospital(lista.get(j).getIdHospital());
                                        sm.setTipo(servicios);
                                        sm = smcrud.seleccionar_servicio(sm);
                                        if(sm != null){
                                            out.println("<div class='container'>");
                                            out.println("<div class='panel-group'>");
                                            out.println("<div class='panel panel-default'>");
                                            out.println("<div class='panel-body'>");

                                            out.println("<div class='col-lg-3 sidenav' align = 'center'>");
                                            out.println("<img src = '" + lista.get(j).getFoto() + "' width= '225px'><br>");
                                            out.println("</div>");
                                            out.println("<div class='col-lg-6 text-left'>"); 
                                            out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                            out.println("<input type ='hidden' name='id' value ='"+lista.get(j).getIdHospital()+"'>");
                                            out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                            out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                            out.println("<h2 style='color:#1073c5' id='letras'>"+lista.get(j).getNombre()+"</h2>");
                                            out.println("</button></form>");

                                            out.println("<img src = 'Imagenes/" + promedio + "estrellas.png' width = '125px'><br><br>");
                                            out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                            out.println("Dirección: <br>");
                                            out.println(lista.get(j).getDireccion() + "<br>");
                                            out.println("Teléfono : " + lista.get(j).getTelefono());

                                            out.println("</div>");
                                            out.println("<div class='col-lg-2 sidenav' align='center'><br>");
                                            out.println("<iframe width='250' height='150' "
                                                    + "src='https://maps.google.com/maps?&hl=es&q="
                                                    + (lista.get(j).getNombre())
                                                    + "&ie=UTF8&t=&z=12&iwloc=B&output=embed' "
                                                    + "frameborder='0' scrolling='no' marginheight='0' marginwidth='0'></iframe>");
                                            out.println("</div>");
                                            out.println("</div>");
                                            out.println("</div>");
                                            out.println("</div>");
                                            out.println("</div>");
                                            contador2++;
                                        }
                                    }
                                }
                            }
                        }
                        */
                        if(contador == 0){
                            out.println("<div class='container'>");
                            out.println("<h2> Sugerencias: </h2>");
                            out.println("</div>");
                            out.println("<div class='container'>");
                            out.println("<h2> Servicio médico: "+servicios+"</h2>");
                            out.println("</div>");
                            smcrud = new ServiciosMedicosCRUD();
                            sm =  new ServiciosMedicos();
                            sm.setTipo(servicios);
                            lista3 = smcrud.seleccionar_tipo_servicio(sm);
                            for(int i = 0; i < lista3.size(); i++){
                                h = new Hospitales();
                                hcrud = new HospitalCRUD();
                                h.setIdHospital(lista3.get(i).getIdHospital());
                                h = hcrud.readOne1(h);
                                out.println("<div class='container'>");
                                out.println("<div class='panel-group'>");
                                out.println("<div class='panel panel-default'>");
                                out.println("<div class='panel-body'>");

                                out.println("<div class='col-lg-3 sidenav' align = 'center'>");
                                out.println("<img src = '" + h.getFoto() + "' width= '225px'><br>");
                                out.println("</div>");
                                out.println("<div class='col-lg-6 text-left'>"); 
                                out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                out.println("<input type ='hidden' name='id' value ='"+h.getIdHospital()+"'>");
                                out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                out.println("<h2 style='color:#1073c5' id='letras'>"+h.getNombre()+"</h2>");
                                out.println("</button></form>");

                                out.println("<img src = 'Imagenes/" + promedio + "estrellas.png' width = '125px'><br><br>");
                                out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                out.println("Dirección: <br>");
                                out.println(h.getDireccion() + "<br>");
                                out.println("Teléfono : " + h.getTelefono());

                                out.println("</div>");
                                out.println("<div class='col-lg-2 sidenav' align='center'><br>");
                                out.println("<iframe width='250' height='150' "
                                        + "src='https://maps.google.com/maps?&hl=es&q="
                                        + (h.getNombre())
                                        + "&ie=UTF8&t=&z=12&iwloc=B&output=embed' "
                                        + "frameborder='0' scrolling='no' marginheight='0' marginwidth='0'></iframe>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                            }            
                        }
                    } else {
                        out.println("<div class='container'>");
                        out.println("<h2> No se encontraron resultados </h2>");
                        out.println("</div>");
                    }
                }else{
                    out.println("<div class='container'>");
                    out.println("<h2> Sugerencias sin cercanía: </h2>");
                    out.println("</div>");
                    out.println("<div class='container'>");
                    out.println("<h2> Servicio médico: "+servicios+"</h2>");
                    out.println("</div>");
                    sm = new ServiciosMedicos();
                    smcrud = new ServiciosMedicosCRUD();
                    sm.setTipo(servicios);
                    lista3 = smcrud.seleccionar_tipo_servicio(sm);
                    if (lista3 != null) {
                        for (int i = 0; i < lista3.size(); i++) {
                            h = new Hospitales();
                            hcrud = new HospitalCRUD();
                            h.setIdHospital(lista3.get(i).getIdHospital());
                            h = hcrud.readOne1(h);

                            idHospital = h.getIdHospital();
                            foto = h.getFoto();
                            nombre = h.getNombre();
                            direccion = h.getDireccion();
                            telefono = h.getTelefono();
                            ranking = h.getRankeo();
                            pagina = h.getPagina();

                            rcrud = new RankeoCRUD();
                            prom = rcrud.promedio(idHospital);

                            out.println("<div class='container'>");
                            out.println("<div class='panel-group'>");
                            out.println("<div class='panel panel-default'>");
                            out.println("<div class='panel-body'>");

                            out.println("<div class='col-lg-3 sidenav' align = 'center'>");
                            out.println("<img src = '" + foto + "' width= '225px'>");
                            out.println("</div>");
                            out.println("<div class='col-lg-6 text-left'>"); 
                            
                            out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                            out.println("<input type ='hidden' name='id' value ='"+idHospital+"'>");
                            out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                            out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                            out.println("<h2 style='color:#1073c5' id='letras'>"+nombre+"</h2>");
                            out.println("</button></form>");
                        
                            out.println("<img src = 'Imagenes/" + prom + "estrellas.png' width = '125px'><br><br>");
                            out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                            out.println("Dirección: <br>");
                            out.println(direccion + "<br>");
                            out.println("Teléfono : " + telefono);

                            out.println("</div>");
                            out.println("<div class='col-lg-2 sidenav' align='center'><br>");
                            out.println("<iframe width='250' height='150' "
                                    + "src='https://maps.google.com/maps?&hl=es&q="
                                    + (nombre)
                                    + "&ie=UTF8&t=&z=12&iwloc=B&output=embed' "
                                    + "frameborder='0' scrolling='no' marginheight='0' marginwidth='0'></iframe>");
                            out.println("</div>");
                            out.println("</div>");
                            out.println("</div>");
                            out.println("</div>");
                            out.println("</div>");
                        }
                    } else {
                        out.println("<div class='container'>");
                        out.println("<h2> No se encontraron resultados </h2>");
                        out.println("</div>");
                    }
                }
                /*
                pcrud = new PacienteDAO();
                p = pcrud.seleccionar_informacion(id);
                
                h = new Hospitales();
                hcrud = new HospitalCRUD();
                if("Si".equals(cercania)){
                    sm = new ServiciosMedicos();
                    smcrud = new ServiciosMedicosCRUD();
                    sm.setTipo(servicios);
                    lista3 = smcrud.seleccionar_tipo_servicio(sm);
                    if (lista3 != null) {
                        Integer promedio = 0;
                        contador = 0;
                        for (int j = 0; j < lista3.size(); j++) {
                            h = new Hospitales();
                            hcrud = new HospitalCRUD();
                            h.setIdHospital(lista3.get(j).getIdHospital());
                            h = hcrud.readOne1(h);
                            String delegacion = h.getDireccion().substring(h.getDireccion().indexOf(","), h.getDireccion().length());
                            if(delegacion.contains(p.getDelegacion())){
                                idHospital = h.getIdHospital();
                                foto = h.getFoto();
                                nombre = h.getNombre();
                                direccion = h.getDireccion();
                                telefono = h.getTelefono();
                                ranking = h.getRankeo();
                                pagina = h.getPagina();

                                rcrud = new RankeoCRUD();
                                prom = rcrud.promedio(idHospital);

                                out.println("<div class='container'>");
                                out.println("<div class='panel-group'>");
                                out.println("<div class='panel panel-default'>");
                                out.println("<div class='panel-body'>");

                                out.println("<div class='col-lg-3 sidenav' align = 'center'>");
                                out.println("<img src = '" + foto + "' width= '225px'>");
                                out.println("</div>");
                                out.println("<div class='col-lg-6 text-left'>"); 
                                
                                out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                out.println("<input type ='hidden' name='id' value ='"+idHospital+"'>");
                                out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                out.println("<h2 style='color:#1073c5' id='letras'>"+nombre+"</h2>");
                                out.println("</button></form>");
                        
                                out.println("<img src = 'Imagenes/" + prom + "estrellas.png' width = '125px'><br><br>");
                                out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                out.println("Dirección: <br>");
                                out.println(direccion + "<br>");
                                out.println("Teléfono : " + telefono);

                                out.println("</div>");
                                out.println("<div class='col-lg-2 sidenav' align='center'><br>");
                                out.println("<iframe width='250' height='150' "
                                        + "src='https://maps.google.com/maps?&hl=es&q="
                                        + (nombre)
                                        + "&ie=UTF8&t=&z=12&iwloc=B&output=embed' "
                                        + "frameborder='0' scrolling='no' marginheight='0' marginwidth='0'></iframe>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                                contador++;
                            }
                        }
                        
                        //No encontró nada de hospitales cercanos en la delegación del paciente
                        pivote = 0;
                        if(contador == 0){
                            for(int i = 0; i < 16; i++){
                                if(p.getDelegacion().equals(alcaldias[0][i])){
                                    pivote = i;
                                }
                            }
                            contador = 0;
                            for(int i = 1; i < 8; i++){
                                if(alcaldias[i][pivote] != null){
                                    contador++;
                                }else{
                                    break;
                                }
                            }
                            out.println("<div class='container'>");
                            out.println("<h2> Sugerencias </h2>");
                            out.println("</div>");
                            contador2 = 0;
                            for(int i = 0; i < contador; i++){
                                h.setDireccion(alcaldias[i][pivote]);
                                lista = hcrud.readbyaddress(h);
                                if(lista != null){
                                    for(int j = 0; j < lista.size(); j++){
                                        rcrud = new RankeoCRUD();
                                        promedio = rcrud.promedio(lista.get(j).getIdHospital());
                                        smcrud = new ServiciosMedicosCRUD();
                                        sm =  new ServiciosMedicos();
                                        sm.setIdHospital(lista.get(j).getIdHospital());
                                        sm.setTipo(servicios);
                                        sm = smcrud.seleccionar_servicio(sm);
                                        if(sm != null){
                                            out.println("<div class='container'>");
                                            out.println("<div class='panel-group'>");
                                            out.println("<div class='panel panel-default'>");
                                            out.println("<div class='panel-body'>");

                                            out.println("<div class='col-lg-3 sidenav' align = 'center'>");
                                            out.println("<img src = '" + lista.get(j).getFoto() + "' width= '225px'><br>");
                                            out.println("</div>");
                                            out.println("<div class='col-lg-6 text-left'>"); 
                                            out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                            out.println("<input type ='hidden' name='id' value ='"+lista.get(j).getIdHospital()+"'>");
                                            out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                            out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                            out.println("<h2 style='color:#1073c5' id='letras'>"+lista.get(j).getNombre()+"</h2>");
                                            out.println("</button></form>");

                                            out.println("<img src = 'Imagenes/" + promedio + "estrellas.png' width = '125px'><br><br>");
                                            out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                            out.println("Dirección: <br>");
                                            out.println(lista.get(j).getDireccion() + "<br>");
                                            out.println("Teléfono : " + lista.get(j).getTelefono());

                                            out.println("</div>");
                                            out.println("<div class='col-lg-2 sidenav' align='center'><br>");
                                            out.println("<iframe width='250' height='150' "
                                                    + "src='https://maps.google.com/maps?&hl=es&q="
                                                    + (lista.get(j).getNombre())
                                                    + "&ie=UTF8&t=&z=12&iwloc=B&output=embed' "
                                                    + "frameborder='0' scrolling='no' marginheight='0' marginwidth='0'></iframe>");
                                            out.println("</div>");
                                            out.println("</div>");
                                            out.println("</div>");
                                            out.println("</div>");
                                            out.println("</div>");
                                            contador2++;
                                        }
                                    }
                                }
                            }
                        }
                        if( contador2==0 ){
                            
                            smcrud = new ServiciosMedicosCRUD();
                            sm =  new ServiciosMedicos();
                            sm.setTipo(servicios);
                            lista3 = smcrud.seleccionar_tipo_servicio(sm);
                            for(int i = 0; i < lista3.size(); i++){
                                h = new Hospitales();
                                hcrud = new HospitalCRUD();
                                h.setIdHospital(lista3.get(i).getIdHospital());
                                h = hcrud.readOne1(h);
                                out.println("<div class='container'>");
                                out.println("<div class='panel-group'>");
                                out.println("<div class='panel panel-default'>");
                                out.println("<div class='panel-body'>");

                                out.println("<div class='col-lg-3 sidenav' align = 'center'>");
                                out.println("<img src = '" + h.getFoto() + "' width= '225px'><br>");
                                out.println("</div>");
                                out.println("<div class='col-lg-6 text-left'>"); 
                                out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                out.println("<input type ='hidden' name='id' value ='"+h.getIdHospital()+"'>");
                                out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                out.println("<h2 style='color:#1073c5' id='letras'>"+h.getNombre()+"</h2>");
                                out.println("</button></form>");

                                out.println("<img src = 'Imagenes/" + promedio + "estrellas.png' width = '125px'><br><br>");
                                out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                out.println("Dirección: <br>");
                                out.println(h.getDireccion() + "<br>");
                                out.println("Teléfono : " + h.getTelefono());

                                out.println("</div>");
                                out.println("<div class='col-lg-2 sidenav' align='center'><br>");
                                out.println("<iframe width='250' height='150' "
                                        + "src='https://maps.google.com/maps?&hl=es&q="
                                        + (h.getNombre())
                                        + "&ie=UTF8&t=&z=12&iwloc=B&output=embed' "
                                        + "frameborder='0' scrolling='no' marginheight='0' marginwidth='0'></iframe>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                            }            
                        }
                    }
                }else{
                    sm = new ServiciosMedicos();
                    smcrud = new ServiciosMedicosCRUD();
                    sm.setTipo(servicios);
                    lista3 = smcrud.seleccionar_tipo_servicio(sm);
                    if (lista3 != null) {
                        for (int i = 0; i < lista3.size(); i++) {
                            h = new Hospitales();
                            hcrud = new HospitalCRUD();
                            h.setIdHospital(lista3.get(i).getIdHospital());
                            h = hcrud.readOne1(h);

                            idHospital = h.getIdHospital();
                            foto = h.getFoto();
                            nombre = h.getNombre();
                            direccion = h.getDireccion();
                            telefono = h.getTelefono();
                            ranking = h.getRankeo();
                            pagina = h.getPagina();

                            rcrud = new RankeoCRUD();
                            prom = rcrud.promedio(idHospital);

                            out.println("<div class='container'>");
                            out.println("<div class='panel-group'>");
                            out.println("<div class='panel panel-default'>");
                            out.println("<div class='panel-body'>");

                            out.println("<div class='col-lg-3 sidenav' align = 'center'>");
                            out.println("<img src = '" + foto + "' width= '225px'>");
                            out.println("</div>");
                            out.println("<div class='col-lg-6 text-left'>"); 
                            
                            out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                            out.println("<input type ='hidden' name='id' value ='"+idHospital+"'>");
                            out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                            out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                            out.println("<h2 style='color:#1073c5' id='letras'>"+nombre+"</h2>");
                            out.println("</button></form>");
                        
                            out.println("<img src = 'Imagenes/" + prom + "estrellas.png' width = '125px'><br><br>");
                            out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                            out.println("Dirección: <br>");
                            out.println(direccion + "<br>");
                            out.println("Teléfono : " + telefono);

                            out.println("</div>");
                            out.println("<div class='col-lg-2 sidenav' align='center'><br>");
                            out.println("<iframe width='250' height='150' "
                                    + "src='https://maps.google.com/maps?&hl=es&q="
                                    + (nombre)
                                    + "&ie=UTF8&t=&z=12&iwloc=B&output=embed' "
                                    + "frameborder='0' scrolling='no' marginheight='0' marginwidth='0'></iframe>");
                            out.println("</div>");
                            out.println("</div>");
                            out.println("</div>");
                            out.println("</div>");
                            out.println("</div>");
                        }
                    } else {
                        out.println("<div class='container'>");
                        out.println("<h2> No se encontraron resultados </h2>");
                        out.println("</div>");
                    }
                }*/
            }
            
            //Tiene muchos detalles
            
            ///////////////////////////Filtro por especialidad y cercanía///////////////////////////////////////
            if (!"".equals(cercania) &&  "".equals(rankeo) && !"".equals(especialidad) && "".equals(servicios)) {
                pcrud = new PacienteDAO();
                p = pcrud.seleccionar_informacion(id);
                Integer promedio = 0;
                h = new Hospitales();
                hcrud = new HospitalCRUD();
                boolean n = false;        
                if("Si".equals(cercania)){
                    m = new Medicos();
                    mcrud = new MedicosCRUD();
                    m.setEspecialidades(especialidad);
                    lista2 = mcrud.Hospitales_especialidad(m);
                    if (lista2 != null) {
                        promedio = 0;
                        contador = 0;
                        for (int j = 0; j < lista2.size(); j++) {
                            h = new Hospitales();
                            hcrud = new HospitalCRUD();
                            h.setIdHospital(lista2.get(j).getIdHospital());
                            h = hcrud.readOne1(h);
                            String delegacion = h.getDireccion().substring(h.getDireccion().indexOf(","), h.getDireccion().length());
                            if(delegacion.contains(p.getDelegacion())){
                                if(n==false){
                                    out.println("<div class='container'>");
                                    out.println("<h2> Coincidencia en: "+p.getDelegacion()+", "+especialidad+"</h2>");
                                    out.println("</div>");
                                    n = true;
                                } 
                                rmcrud =  new RankeoMedicosCRUD();
                                rm =  new RankeoMedicos();
                                rm.setIdMedico(lista2.get(j).getIdMedicos());
                                promedio = rmcrud.promedio(rm);
                                out.println("<div class='container'>");
                                out.println("<div class='panel-group'>");
                                out.println("<div class='panel panel-default'>");
                                out.println("<div class='panel-body'>");

                                out.println("<div class='col-md-3 sidenav' align = 'center'>");
                                out.println("<img src = '/Prototipo4_1/Imagenes/doctor.png' width= '150px'>");
                                out.println("</div>");
                                out.println("<div class='col-md-6 text-left'>"); 
                                
                                out.println("<form action='/Prototipo4_1/Paginas/InformacionEspecialistas.jsp' method ='post'>");
                                out.println("<input type ='hidden' name='idhospital' value ='"+h.getIdHospital() +"'>");
                                out.println("<input type ='hidden' name='idusuario' value ='"+id+"'>");
                                out.println("<input type ='hidden' name='idmedico' value ='"+lista2.get(j).getIdMedicos()+"'>");
                                out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                out.println("<h2 style='color:#1073c5' id='letras'>"+lista2.get(j).getNombreMedico() + " " + lista2.get(j).getAPaternoMedico() + " " + lista2.get(j).getAMaternoMedico()+"</h2>");
                                out.println("</button></form>");

                                out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                out.println("<input type ='hidden' name='id' value ='"+h.getIdHospital()+"'>");
                                out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                out.println("<h7 style='color:#1073c5' id='letras'>"+h.getNombre()+"</h7>");
                                out.println("</button></form>");
                            
                                out.println("<img src = '/Prototipo4_1/Imagenes/"+promedio+"estrellas.png' width = '125px><br><br>");

                                out.println("<span class='glyphicon glyphicon-map-marker'></span><br><br>");
                                out.println("Dirección: <br>");
                                out.println(h.getDireccion() + "<br>");
                                out.println("Teléfono : " + lista2.get(j).getTelefonos());

                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                                contador++;
                            }
                        }
                    }
                    
                    //No encontró nada de hospitales cercanos en la delegación del paciente
                    /*pivote = 0;
                    if(contador == 0){
                        for(int i = 0; i < 16; i++){
                            if(p.getDelegacion().equals(alcaldias[0][i])){
                                pivote = i;
                            }
                        }
                        contador = 0;
                        for(int i = 1; i < 8; i++){
                            if(alcaldias[i][pivote] != null){
                                contador++;
                            }else{
                                break;
                            }
                        }
                        out.println("<div class='container'>");
                        out.println("<h2> Sugerencias </h2>");
                        out.println("</div>");

                        contador2 = 0;
                        for(int i = 0; i < contador; i++){
                            h.setDireccion(alcaldias[i][pivote]);
                            lista = hcrud.readbyaddress(h);
                            if(lista != null){
                                for(int j = 0; j < lista.size(); j++){
                                    m = new Medicos();
                                    mcrud = new MedicosCRUD();
                                    m.setIdHospital(lista.get(j).getIdHospital());
                                    lista2 = mcrud.readAll(m);
                                    for(int k = 0; k < lista2.size(); k++){
                                        rm =  new RankeoMedicos();
                                        rm.setIdHospital(lista2.get(k).getIdHospital());
                                        rm.setIdMedico(lista2.get(k).getIdMedicos());
                                        rmcrud = new RankeoMedicosCRUD();
                                        promedio = rmcrud.promedio(rm);
                                        out.println("<p>aqui</p>");
                                        out.println("<p>"+lista.get(j).getNombre()+"</p>");
                                        out.println("<p>"+lista2.get(k).getEspecialidades()+"</p>");
                                        out.println("<p>"+especialidad+"</p>");
                                        
                                        
                                        if(lista2.get(k).getEspecialidades().contains(especialidad)){
                                            out.println("<div class='container'>");
                                            out.println("<div class='panel-group'>");
                                            out.println("<div class='panel panel-default'>");
                                            out.println("<div class='panel-body'>");

                                            out.println("<div class='col-md-3 sidenav' align = 'center'>");
                                            out.println("<img src = '/Prototipo4_1/Imagenes/doctor.png' width= '150px'>");
                                            out.println("</div>");
                                            out.println("<div class='col-md-6 text-left'>"); 

                                            out.println("<form action='/Prototipo4_1/Paginas/InformacionEspecialistas.jsp' method ='post'>");
                                            out.println("<input type ='hidden' name='idhospital' value ='"+h.getIdHospital() +"'>");
                                            out.println("<input type ='hidden' name='idusuario' value ='"+id+"'>");
                                            out.println("<input type ='hidden' name='idmedico' value ='"+lista2.get(k).getIdMedicos()+"'>");
                                            out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                            out.println("<h2 style='color:#1073c5' id='letras'>"+lista2.get(k).getNombreMedico() + " " + lista2.get(k).getAPaternoMedico() + " " + lista2.get(k).getAMaternoMedico()+"</h2>");
                                            out.println("</button></form>");

                                            out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                            out.println("<input type ='hidden' name='id' value ='"+lista.get(j).getIdHospital()+"'>");
                                            out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                            out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                            out.println("<h7 style='color:#1073c5' id='letras'>"+lista.get(j).getNombre()+"</h7>");
                                            out.println("</button></form>");

                                            out.println("<img src = '/Prototipo4_1/Imagenes/"+promedio+"estrellas.png' width = '125px><br><br>");

                                            out.println("<span class='glyphicon glyphicon-map-marker'></span><br><br>");
                                            out.println("Dirección: <br>");
                                            out.println(h.getDireccion() + "<br>");
                                            out.println("Teléfono : " + lista2.get(k).getTelefonos());

                                            out.println("</div>");
                                            out.println("</div>");
                                            out.println("</div>");
                                            out.println("</div>");
                                            out.println("</div>");
                                        }
                                    }
                                }
                            }
                        }
                    }*/
                    if(contador == 0){
                        m = new Medicos();
                        mcrud = new MedicosCRUD();
                        m.setEspecialidades(especialidad);
                        lista2 = mcrud.Hospitales_especialidad(m);
                        if(lista2 != null){
                            out.println("<div class='container'>");
                            out.println("<h2> Sugerencias</h2>");
                            out.println("</div>");
                            
                            out.println("<div class='container'>");
                            out.println("<h2> Especialidad médica: "+especialidad+"</h2>");
                            out.println("</div>");
                            for (int i = 0; i < lista2.size(); i++) {
                                h = new Hospitales();
                                hcrud = new HospitalCRUD();
                                h.setIdHospital(lista2.get(i).getIdHospital());
                                h = hcrud.readOne1(h); 
                                if (h != null) {
                                    rm = new RankeoMedicos();
                                    rmcrud = new RankeoMedicosCRUD();
                                    rm.setIdHospital(h.getIdHospital());
                                    rm.setIdMedico(lista2.get(i).getIdMedicos());
                                    prom = rmcrud.promedio(rm);

                                    out.println("<div class='container'>");
                                    out.println("<div class='panel-group'>");
                                    out.println("<div class='panel panel-default'>");
                                    out.println("<div class='panel-body'>");

                                    out.println("<div class='col-md-3 sidenav' align = 'center'>");
                                    out.println("<img src = '/Prototipo4_1/Imagenes/doctor.png' width= '150px'>");
                                    out.println("</div>");
                                    out.println("<div class='col-md-6 text-left'>"); 

                                    out.println("<form action='/Prototipo4_1/Paginas/InformacionEspecialistas.jsp' method ='post'>");
                                    out.println("<input type ='hidden' name='idhospital' value ='"+h.getIdHospital()+"'>");
                                    out.println("<input type ='hidden' name='idusuario' value ='"+id+"'>");
                                    out.println("<input type ='hidden' name='idmedico' value ='"+lista2.get(i).getIdMedicos()+"'>");
                                    out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                    out.println("<h2 style='color:#1073c5' id='letras'>"+lista2.get(i).getNombreMedico() + " " + lista2.get(i).getAPaternoMedico() + " " + lista2.get(i).getAMaternoMedico()+"</h2>");
                                    out.println("</button></form>");

                                    out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                    out.println("<input type ='hidden' name='id' value ='"+h.getIdHospital()+"'>");
                                    out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                    out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                    out.println("<h7 style='color:#1073c5' id='letras'>"+h.getNombre()+"</h7>");
                                    out.println("</button></form>");

                                    out.println("<img src = '/Prototipo4_1/Imagenes/"+prom+"estrellas.png' width = '125px><br><br>");

                                    out.println("<span class='glyphicon glyphicon-map-marker'></span><br><br>");
                                    out.println("Dirección: <br>");
                                    out.println(h.getDireccion() + "<br>");
                                    out.println("Teléfono : " + lista2.get(i).getTelefonos());

                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                }
                            }
                        } 
                    }
                }
                else{
                    out.println("<div class='container'>");
                    out.println("<h2> Sugerencias sin cercanía</h2>");
                    out.println("</div>");
                    out.println("<div class='container'>");
                    out.println("<h2> Especialidad médica: "+especialidad+"</h2>");
                    out.println("</div>");
                    m = new Medicos();
                    mcrud = new MedicosCRUD();
                    m.setEspecialidades(especialidad);
                    lista2 = mcrud.Hospitales_especialidad(m);
                    if (lista2 != null) {
                        for(int j = 0; j < lista2.size(); j++) {
                            h = new Hospitales();
                            hcrud = new HospitalCRUD();
                            h.setIdHospital(lista2.get(j).getIdHospital());
                            h = hcrud.readOne1(h);
                            rmcrud = new RankeoMedicosCRUD();
                            rm = new RankeoMedicos();
                            rm.setIdMedico(lista2.get(j).getIdMedicos());
                            prom = rmcrud.promedio(rm);
                            out.println("<div class='container'>");
                            out.println("<div class='panel-group'>");
                            out.println("<div class='panel panel-default'>");
                            out.println("<div class='panel-body'>");

                            out.println("<div class='col-md-3 sidenav' align = 'center'>");
                            out.println("<img src = '/Prototipo4_1/Imagenes/doctor.png' width= '150px'>");
                            out.println("</div>");
                            out.println("<div class='col-md-6 text-left'>"); 
                            
                            out.println("<form action='/Prototipo4_1/Paginas/InformacionEspecialistas.jsp' method ='post'>");
                            out.println("<input type ='hidden' name='idhospital' value ='"+h.getIdHospital() +"'>");
                            out.println("<input type ='hidden' name='idusuario' value ='"+id+"'>");
                            out.println("<input type ='hidden' name='idmedico' value ='"+lista2.get(j).getIdMedicos()+"'>");
                            out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                            out.println("<h2 style='color:#1073c5' id='letras'>"+lista2.get(j).getNombreMedico() + " " + lista2.get(j).getAPaternoMedico() + " " + lista2.get(j).getAMaternoMedico()+"</h2>");
                            out.println("</button></form>");

                            out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                            out.println("<input type ='hidden' name='id' value ='"+h.getIdHospital()+"'>");
                            out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                            out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                            out.println("<h7 style='color:#1073c5' id='letras'>"+h.getNombre()+"</h7>");
                            out.println("</button></form>");
                            
                            out.println("<img src = '/Prototipo4_1/Imagenes/"+prom+"estrellas.png' width = '125px><br><br>");

                            out.println("<span class='glyphicon glyphicon-map-marker'></span><br><br>");
                            out.println("Dirección: <br>");
                            out.println(h.getDireccion() + "<br>");
                            out.println("Teléfono : " + lista2.get(j).getTelefonos());

                            out.println("</div>");
                            out.println("</div>");
                            out.println("</div>");
                            out.println("</div>");
                            out.println("</div>");
                        }
                    }
                }
            }
            
            
            
            ////////////////Filtro por especialidad, servicio médico y cercanía/////////////////////////////////
            if (!"".equals(cercania) &&  "".equals(rankeo) && !"".equals(especialidad) && !"".equals(servicios)) {
                pcrud = new PacienteDAO();
                p = new Paciente();
                p = pcrud.seleccionar_informacion(id);
                int contadoraux;
                h = new Hospitales();
                hcrud = new HospitalCRUD();
                boolean n = false;
                if("Si".equals(cercania)){
                    sm = new ServiciosMedicos();
                    smcrud = new ServiciosMedicosCRUD();
                    sm.setTipo(servicios);
                    lista3 = smcrud.seleccionar_tipo_servicio(sm);
                    if(lista3 != null){
                        idh = new int[lista3.size()];
                        contador = 0;
                        for (int i = 0; i < lista3.size(); i++) {
                            m = new Medicos();
                            mcrud = new MedicosCRUD();
                            m.setEspecialidades(especialidad);
                            m.setIdHospital(lista3.get(i).getIdHospital());
                            m = mcrud.ServiciosMedicos(m);
                            if (m != null) {
                                idh[contador] = m.getIdHospital();
                                contador++;
                            }
                        }
                        Integer promedio = 0;
                        contadoraux = 0;
                        if(contador != 0){
                            
                            for (int i = 0; i < contador; i++) {
                                h = new Hospitales();
                                hcrud = new HospitalCRUD();
                                h.setIdHospital(idh[i]);
                                h = hcrud.readOne1(h);
                                if(h != null){
                                    rcrud = new RankeoCRUD();
                                    promedio = rcrud.promedio(idh[i]);
                                    String delegacion = h.getDireccion().substring(h.getDireccion().indexOf(","), h.getDireccion().length());
                                    if(delegacion.contains(p.getDelegacion())){
                                        if(n == false){
                                            out.println("<div class='container'>");
                                            out.println("<h2> Sugerencia</h2>");
                                            out.println("</div>");
                                            out.println("<div class='container'>");
                                            out.println("<h2> Coincidencia en: "+servicios+", "+especialidad+"</h2>");
                                            out.println("</div>");
                                            n = true;
                                        }
                                        contadoraux++;
                                        out.println("<div class='container'>");
                                        out.println("<div class='panel-group'>");
                                        out.println("<div class='panel panel-default'>");
                                        out.println("<div class='panel-body'>");

                                        out.println("<div class='col-lg-3 sidenav' align = 'center'>");
                                        out.println("<img src = '" + h.getFoto() + "' width= '225px'><br>");
                                        out.println("</div>");
                                        out.println("<div class='col-lg-6 text-left'>"); 

                                        out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                        out.println("<input type ='hidden' name='id' value ='"+h.getIdHospital()+"'>");
                                        out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                        out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                        out.println("<h2 style='color:#1073c5' id='letras'>"+h.getNombre()+"</h2>");
                                        out.println("</button></form>");

                                        out.println("<img src = 'Imagenes/" + promedio + "estrellas.png' width = '125px'><br><br>");
                                        out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                        out.println("Dirección: <br>");
                                        out.println(h.getDireccion() + "<br>");
                                        out.println("Teléfono : " + h.getTelefono());

                                        out.println("</div>");
                                        out.println("<div class='col-lg-2 sidenav' align='center'><br>");
                                        out.println("<iframe width='250' height='150' "
                                                + "src='https://maps.google.com/maps?&hl=es&q="
                                                + (h.getNombre())
                                                + "&ie=UTF8&t=&z=12&iwloc=B&output=embed' "
                                                + "frameborder='0' scrolling='no' marginheight='0' marginwidth='0'></iframe>");
                                        out.println("</div>");
                                        out.println("</div>");
                                        out.println("</div>");
                                        out.println("</div>");
                                        out.println("</div>");
                                    }
                                }
                            }n=false;
                        }
                        if(contadoraux == 0){
                            out.println("<div class='container'>");
                            out.println("<h2> Sugerencias</h2>");
                            out.println("</div>");
                            out.println("<div class='container'>");
                            //Modificar por el título
                            out.println("<h2> Coincidencia en: "+servicios+", "+especialidad+"</h2>");
                            out.println("</div>");
                                        
                            for (int i = 0; i < contador; i++) {
                                h = new Hospitales();
                                hcrud = new HospitalCRUD();
                                h.setIdHospital(idh[i]);
                                h = hcrud.readOne1(h);
                                if(h != null){
                                    rcrud = new RankeoCRUD();
                                    promedio = rcrud.promedio(idh[i]);
                                    out.println("<div class='container'>");
                                    out.println("<div class='panel-group'>");
                                    out.println("<div class='panel panel-default'>");
                                    out.println("<div class='panel-body'>");

                                    out.println("<div class='col-lg-3 sidenav' align = 'center'>");
                                    out.println("<img src = '" + h.getFoto() + "' width= '225px'><br>");
                                    out.println("</div>");
                                    out.println("<div class='col-lg-6 text-left'>"); 

                                    out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                    out.println("<input type ='hidden' name='id' value ='"+h.getIdHospital()+"'>");
                                    out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                    out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                    out.println("<h2 style='color:#1073c5' id='letras'>"+h.getNombre()+"</h2>");
                                    out.println("</button></form>");

                                    out.println("<img src = 'Imagenes/" + promedio + "estrellas.png' width = '125px'><br><br>");
                                    out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                    out.println("Dirección: <br>");
                                    out.println(h.getDireccion() + "<br>");
                                    out.println("Teléfono : " + h.getTelefono());

                                    out.println("</div>");
                                    out.println("<div class='col-lg-2 sidenav' align='center'><br>");
                                    out.println("<iframe width='250' height='150' "
                                            + "src='https://maps.google.com/maps?&hl=es&q="
                                            + (h.getNombre())
                                            + "&ie=UTF8&t=&z=12&iwloc=B&output=embed' "
                                            + "frameborder='0' scrolling='no' marginheight='0' marginwidth='0'></iframe>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                }
                            }
                        }if(contador == 0 && contadoraux == 0){
                                        
                            /*out.println("<div class='container'>");
                            out.println("<h2>Sugerencia: servicio médico"+servicios+"</h2>");
                            out.println("</div>");
                            */promedio = 0;
                            idh = new int[lista3.size()];
                            contador = 0;
                            for (int i = 0; i < lista3.size(); i++) {
                                h = new Hospitales();
                                hcrud = new HospitalCRUD();
                                h.setIdHospital(lista3.get(i).getIdHospital());
                                h = hcrud.readOne1(h);
                                if(h != null){
                                    out.println("<div class='container'>");
                                    out.println("<div class='panel-group'>");
                                    out.println("<div class='panel panel-default'>");
                                    out.println("<div class='panel-body'>");

                                    out.println("<div class='col-lg-3 sidenav' align = 'center'>");
                                    out.println("<img src = '" + h.getFoto() + "' width= '225px'><br>");
                                    out.println("</div>");
                                    out.println("<div class='col-lg-6 text-left'>"); 

                                    out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                    out.println("<input type ='hidden' name='id' value ='"+h.getIdHospital()+"'>");
                                    out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                    out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                    out.println("<h2 style='color:#1073c5' id='letras'>"+h.getNombre()+"</h2>");
                                    out.println("</button></form>");

                                    out.println("<img src = 'Imagenes/" + h.getRankeo() + "estrellas.png' width = '125px'><br><br>");
                                    out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                    out.println("Dirección: <br>");
                                    out.println(h.getDireccion() + "<br>");
                                    out.println("Teléfono : " + h.getTelefono());

                                    out.println("</div>");
                                    out.println("<div class='col-lg-2 sidenav' align='center'><br>");
                                    out.println("<iframe width='250' height='150' "
                                            + "src='https://maps.google.com/maps?&hl=es&q="
                                            + (h.getNombre())
                                            + "&ie=UTF8&t=&z=12&iwloc=B&output=embed' "
                                            + "frameborder='0' scrolling='no' marginheight='0' marginwidth='0'></iframe>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                }  
                            }


                            //Ahora se busca la especialidad con el ranking deseado     
                            out.println("<div class='container'>");
                            out.println("<h2>Sugerencia:</h2> <br>");
                            out.println("<h2>Coincidencia en: "+especialidad+"</h2>");
                            out.println("</div>");
                            
                            rm = new RankeoMedicos();
                            rmcrud = new RankeoMedicosCRUD();
                            m =  new Medicos();
                            mcrud =  new MedicosCRUD();
                            m.setEspecialidades(especialidad);
                            lista2 = mcrud.Hospitales_especialidad(m);
                            for(int i = 0; i < lista2.size(); i++){
                                h = new Hospitales();
                                hcrud = new HospitalCRUD();
                                h.setIdHospital(lista2.get(i).getIdHospital());
                                //h.setIdHospital(idh[i]);
                                h = hcrud.readOne1(h);

                                m.setIdMedicos(lista2.get(i).getIdMedicos());
                                m = (Medicos)mcrud.readMedicos(m).get(0);
                                rm.setIdHospital(lista2.get(i).getIdHospital());
                                rm.setIdMedico(lista2.get(i).getIdMedicos());
                                out.println("<div class='container'>");
                                out.println("<div class='panel-group'>");
                                out.println("<div class='panel panel-default'>");
                                out.println("<div class='panel-body'>");

                                out.println("<div class='col-md-3 sidenav' align = 'center'>");
                                out.println("<img src = '/Prototipo4_1/Imagenes/doctor.png' width= '150px'>");
                                out.println("</div>");
                                out.println("<div class='col-md-6 text-left'>"); 

                                out.println("<form action='/Prototipo4_1/Paginas/InformacionEspecialistas.jsp' method ='post'>");
                                out.println("<input type ='hidden' name='idhospital' value ='"+lista2.get(i).getIdHospital()+"'>");
                                out.println("<input type ='hidden' name='idusuario' value ='"+id+"'>");
                                out.println("<input type ='hidden' name='idmedico' value ='"+lista2.get(i).getIdMedicos()+"'>");
                                out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                out.println("<h2 style='color:#1073c5' id='letras'>"+ m.getNombreMedico() + " " + m.getAPaternoMedico() + " " + m.getAMaternoMedico() +"</h2>");
                                out.println("</button></form>");

                                out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                out.println("<input type ='hidden' name='id' value ='"+h.getIdHospital()+"'>");
                                out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                out.println("<h7 style='color:#1073c5' id='letras'>"+h.getNombre()+"</h7>");
                                out.println("</button></form>");

                                out.println("<img src = '/Prototipo4_1/Imagenes/"+lista2.get(i).getRankeo()+"estrellas.png' width = '125px'><br><br>");

                                out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                out.println("Dirección: <br>");
                                out.println(h.getDireccion() + "<br>");
                                out.println("Teléfono : " + m.getTelefonos());

                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                            }
                        }
                    }
                    else{
                        out.println("<div class='container'>");
                        out.println("<h3> No se encontraron resultados </h2>");
                        out.println("</div>");
                    }
                }else{
                    //out.println("<div class='container'>");
                    //out.println("<h2> Sugerencias </h2>");
                    //out.println("</div>");
                    sm = new ServiciosMedicos();
                    smcrud = new ServiciosMedicosCRUD();
                    sm.setTipo(servicios);
                    lista3 = smcrud.seleccionar_tipo_servicio(sm);
                    if(lista3 != null){
                        idh = new int[lista3.size()];
                        contador = 0;
                        for (int i = 0; i < lista3.size(); i++) {
                            m = new Medicos();
                            mcrud = new MedicosCRUD();
                            m.setEspecialidades(especialidad);
                            m.setIdHospital(lista3.get(i).getIdHospital());
                            m = mcrud.ServiciosMedicos(m);
                            if (m != null) {
                                idh[contador] = m.getIdHospital();
                                contador++;
                            }
                        }
                        if(contador != 0){
                            out.println("<div class='container'>");
                            out.println("<h2> Coincidencia: "+servicios+", "+especialidad+"</h2>");
                            out.println("</div>");
                            Integer promedio = 0;
                            for (int i = 0; i < contador; i++) {
                                h = new Hospitales();
                                hcrud = new HospitalCRUD();
                                h.setIdHospital(idh[i]);
                                h = hcrud.readOne1(h);
                                if(h != null){
                                    rcrud = new RankeoCRUD();
                                    promedio = rcrud.promedio(idh[i]);
                                    
                                        out.println("<div class='container'>");
                                        out.println("<div class='panel-group'>");
                                        out.println("<div class='panel panel-default'>");
                                        out.println("<div class='panel-body'>");

                                        out.println("<div class='col-lg-3 sidenav' align = 'center'>");
                                        out.println("<img src = '" + h.getFoto() + "' width= '225px'><br>");
                                        out.println("</div>");
                                        out.println("<div class='col-lg-6 text-left'>"); 
                                        out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                        out.println("<input type ='hidden' name='id' value ='"+h.getIdHospital()+"'>");
                                        out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                        out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                        out.println("<h2 style='color:#1073c5' id='letras'>"+h.getNombre()+"</h2>");
                                        out.println("</button></form>");
                        
                                        out.println("<img src = 'Imagenes/" + promedio + "estrellas.png' width = '125px'><br><br>");
                                        out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                        out.println("Dirección: <br>");
                                        out.println(h.getDireccion() + "<br>");
                                        out.println("Teléfono : " + h.getTelefono());

                                        out.println("</div>");
                                        out.println("<div class='col-lg-2 sidenav' align='center'><br>");
                                        out.println("<iframe width='250' height='150' "
                                                + "src='https://maps.google.com/maps?&hl=es&q="
                                                + (h.getNombre())
                                                + "&ie=UTF8&t=&z=12&iwloc=B&output=embed' "
                                                + "frameborder='0' scrolling='no' marginheight='0' marginwidth='0'></iframe>");
                                        out.println("</div>");
                                        out.println("</div>");
                                        out.println("</div>");
                                        out.println("</div>");
                                        out.println("</div>");
                                    
                                }
                            }
                        }
                        else{
                            out.println("<div class='container'>");
                            out.println("<h2> Sugerencias </h2>");
                            out.println("</div>");
                            out.println("<div class='container'>");
                            out.println("<h2> Servicio médico: "+servicios+"</h2>");
                            out.println("</div>");
                            
                            Integer promedio = 0;
                            idh = new int[lista3.size()];
                            contador = 0;
                            for (int i = 0; i < lista3.size(); i++) {
                                h = new Hospitales();
                                hcrud = new HospitalCRUD();
                                h.setIdHospital(lista3.get(i).getIdHospital());
                                h = hcrud.readOne1(h);
                                if(h != null){
                                    r = new Rankeo();
                                    rcrud = new RankeoCRUD();
                                    promedio = rcrud.promedio(lista3.get(i).getIdHospital());
                                    out.println("<div class='container'>");
                                    out.println("<div class='panel-group'>");
                                    out.println("<div class='panel panel-default'>");
                                    out.println("<div class='panel-body'>");

                                    out.println("<div class='col-lg-3 sidenav' align = 'center'>");
                                    out.println("<img src = '" + h.getFoto() + "' width= '225px'><br>");
                                    out.println("</div>");
                                    out.println("<div class='col-lg-6 text-left'>"); 
                                    out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                    out.println("<input type ='hidden' name='id' value ='"+h.getIdHospital()+"'>");
                                    out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                    out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                    out.println("<h2 style='color:#1073c5' id='letras'>"+h.getNombre()+"</h2>");
                                    out.println("</button></form>");
                        
                                    out.println("<img src = 'Imagenes/" + promedio + "estrellas.png' width = '125px'><br><br>");
                                    out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                    out.println("Dirección: <br>");
                                    out.println(h.getDireccion() + "<br>");
                                    out.println("Teléfono : " + h.getTelefono());

                                    out.println("</div>");
                                    out.println("<div class='col-lg-2 sidenav' align='center'><br>");
                                    out.println("<iframe width='250' height='150' "
                                            + "src='https://maps.google.com/maps?&hl=es&q="
                                            + (h.getNombre())
                                            + "&ie=UTF8&t=&z=12&iwloc=B&output=embed' "
                                            + "frameborder='0' scrolling='no' marginheight='0' marginwidth='0'></iframe>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                }  
                            }


                            //Ahora se busca la especialidad con el ranking deseado     
                            out.println("<div class='container'>");
                            out.println("<h2> Especialidad médica: "+especialidad+"</h2>");
                            out.println("</div>");
                            rm = new RankeoMedicos();
                            rmcrud = new RankeoMedicosCRUD();
                            m =  new Medicos();
                            mcrud =  new MedicosCRUD();
                            m.setEspecialidades(especialidad);
                            lista2 = mcrud.Hospitales_especialidad(m);
                            for(int i = 0; i < lista2.size(); i++){
                                h = new Hospitales();
                                hcrud = new HospitalCRUD();
                                h.setIdHospital(lista2.get(i).getIdHospital());
                                h = hcrud.readOne1(h);

                                m.setIdMedicos(lista2.get(i).getIdMedicos());
                                m = (Medicos)mcrud.readMedicos(m).get(0);
                                rm.setIdHospital(lista2.get(i).getIdHospital());
                                rm.setIdMedico(lista2.get(i).getIdMedicos());
                                promedio = rmcrud.promedio(rm);
                                out.println("<div class='container'>");
                                out.println("<div class='panel-group'>");
                                out.println("<div class='panel panel-default'>");
                                out.println("<div class='panel-body'>");

                                out.println("<div class='col-md-3 sidenav' align = 'center'>");
                                out.println("<img src = '/Prototipo4_1/Imagenes/doctor.png' width= '150px'>");
                                out.println("</div>");
                                out.println("<div class='col-md-6 text-left'>"); 
                                
                                
                                out.println("<form action='/Prototipo4_1/Paginas/InformacionEspecialistas.jsp' method ='post'>");
                                out.println("<input type ='hidden' name='idhospital' value ='"+lista2.get(i).getIdHospital() +"'>");
                                out.println("<input type ='hidden' name='idusuario' value ='"+id+"'>");
                                out.println("<input type ='hidden' name='idmedico' value ='"+lista2.get(i).getIdMedicos()+"'>");
                                out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                out.println("<h2 style='color:#1073c5' id='letras'>"+m.getNombreMedico() + " " + m.getAPaternoMedico() + " " + m.getAMaternoMedico()+"</h2>");
                                out.println("</button></form>");

                                out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                out.println("<input type ='hidden' name='id' value ='"+h.getIdHospital()+"'>");
                                out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                out.println("<h7 style='color:#1073c5' id='letras'>"+h.getNombre()+"</h7>");
                                out.println("</button></form>");
                            
                                out.println("<img src = '/Prototipo4_1/Imagenes/"+promedio+"estrellas.png' width = '125px'><br><br>");

                                out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                out.println("Dirección: <br>");
                                out.println(h.getDireccion() + "<br>");
                                out.println("Teléfono : " + m.getTelefonos());

                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");

                            }
                        }
                    }
                    else{
                        out.println("<div class='container'>");
                        out.println("<h3> No se encontraron resultados </h2>");
                        out.println("</div>");
                    }
                }    
            }
            
            ///////////////////////////////////Filtro por cercania y rankeo////////////////////////////////////
            if (!"".equals(cercania) &&  !"".equals(rankeo) && "".equals(especialidad) && "".equals(servicios)) {
                h = new Hospitales();
                hcrud = new HospitalCRUD();
                List<Hospitales> hospitales = hcrud.readAll1();
                pcrud = new PacienteDAO();
                p = pcrud.seleccionar_informacion(id);
                Integer promedio;
                boolean n = false;
                if("Si".equals(cercania)){
                    if(hospitales != null){
                        contador = 0;
                        for(int i = 0; i < hospitales.size(); i++){
                            r = new Rankeo();
                            rcrud = new RankeoCRUD();
                            rankeo_entero = Integer.parseInt(rankeo);
                            prom = rcrud.promedio(hospitales.get(i).getIdHospital());
                            
                            String delegacion = hospitales.get(i).getDireccion().substring(hospitales.get(i).getDireccion().indexOf(","), hospitales.get(i).getDireccion().length());
                            if(prom == rankeo_entero && delegacion.contains(p.getDelegacion())){
                                if(n == false){
                                    out.println("<div class='container'>");
                                    out.println("<h2> Coincidencia en: calificación "+prom+"</h2>");
                                    out.println("</div>");
                                    n = true;
                                }
                                out.println("<div class='container'>");
                                out.println("<div class='panel-group'>");
                                out.println("<div class='panel panel-default'>");
                                out.println("<div class='panel-body'>");

                                out.println("<div class='col-lg-3 sidenav' align = 'center'>");
                                out.println("<img src = '" + hospitales.get(i).getFoto() + "' width= '225px'><br>");
                                out.println("</div>");
                                out.println("<div class='col-lg-6 text-left'>"); 
                                out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                out.println("<input type ='hidden' name='id' value ='"+hospitales.get(i).getIdHospital()+"'>");
                                out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                out.println("<h2 style='color:#1073c5' id='letras'>"+hospitales.get(i).getNombre()+"</h2>");
                                out.println("</button></form>");
                        
                                out.println("<img src = 'Imagenes/" + prom + "estrellas.png' width = '125px'><br><br>");
                                out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                out.println("Dirección: <br>");
                                out.println(hospitales.get(i).getDireccion() + "<br>");
                                out.println("Teléfono : " + hospitales.get(i).getTelefono());

                                out.println("</div>");
                                out.println("<div class='col-lg-2 sidenav' align='center'><br>");
                                out.println("<iframe width='250' height='150' "
                                        + "src='https://maps.google.com/maps?&hl=es&q="
                                        + (hospitales.get(i).getNombre())
                                        + "&ie=UTF8&t=&z=12&iwloc=B&output=embed' "
                                        + "frameborder='0' scrolling='no' marginheight='0' marginwidth='0'></iframe>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                                contador++;
                            }
                        }

                        out.println("<div class='container'>");
                        out.println("<h2> Sugerencias </h2>");
                        out.println("</div>");
                        n = false;
                        //4 estrellas
                        if(contador == 0){
                            contador1 = 0;

                            for(int i = 0; i < hospitales.size(); i++){
                                r = new Rankeo();
                                rcrud = new RankeoCRUD();
                                rankeo_entero = Integer.parseInt(rankeo);
                                prom = rcrud.promedio(hospitales.get(i).getIdHospital());
                                String delegacion = hospitales.get(i).getDireccion().substring(hospitales.get(i).getDireccion().indexOf(","), hospitales.get(i).getDireccion().length());
                                if(prom == rankeo_entero - 1 && delegacion.contains(p.getDelegacion())){
                                    if(n == false){
                                        out.println("<div class='container'>");
                                        out.println("<h2> Calificación: " + (rankeo_entero - 1) + "</h2>");
                                        out.println("</div>");
                                        n = true;
                                    }
                                    out.println("<div class='container'>");
                                    out.println("<div class='panel-group'>");
                                    out.println("<div class='panel panel-default'>");
                                    out.println("<div class='panel-body'>");

                                    out.println("<div class='col-lg-3 sidenav' align = 'center'>");
                                    out.println("<img src = '" + hospitales.get(i).getFoto() + "' width= '225px'><br>");
                                    out.println("</div>");
                                    out.println("<div class='col-lg-6 text-left'>"); 
                                    out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                    out.println("<input type ='hidden' name='id' value ='"+hospitales.get(i).getIdHospital()+"'>");
                                    out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                    out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                    out.println("<h2 style='color:#1073c5' id='letras'>"+hospitales.get(i).getNombre()+"</h2>");
                                    out.println("</button></form>");
                        
                                    out.println("<img src = 'Imagenes/" + prom + "estrellas.png' width = '125px'><br><br>");
                                    out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                    out.println("Dirección: <br>");
                                    out.println(hospitales.get(i).getDireccion() + "<br>");
                                    out.println("Teléfono : " + hospitales.get(i).getTelefono());

                                    out.println("</div>");
                                    out.println("<div class='col-lg-2 sidenav' align='center'><br>");
                                    out.println("<iframe width='250' height='150' "
                                            + "src='https://maps.google.com/maps?&hl=es&q="
                                            + (hospitales.get(i).getNombre())
                                            + "&ie=UTF8&t=&z=12&iwloc=B&output=embed' "
                                            + "frameborder='0' scrolling='no' marginheight='0' marginwidth='0'></iframe>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    contador1++;
                                }
                            }
                        }

                        n = false;
                        //3 estrellas    
                        if(contador1 == 0){
                            contador2 = 0;

                            for(int i = 0; i < hospitales.size(); i++){
                                r = new Rankeo();
                                rcrud = new RankeoCRUD();
                                rankeo_entero = Integer.parseInt(rankeo);
                                prom = rcrud.promedio(hospitales.get(i).getIdHospital());

                                String delegacion = hospitales.get(i).getDireccion().substring(hospitales.get(i).getDireccion().indexOf(","), hospitales.get(i).getDireccion().length());
                                if(prom == rankeo_entero - 2 && delegacion.contains(p.getDelegacion())){
                                    if(n == false){
                                        out.println("<div class='container'>");
                                        out.println("<h2> Calificación: " + (rankeo_entero - 2) + "</h2>");
                                        out.println("</div>");
                                        n = true;
                                    }
                                    out.println("<div class='container'>");
                                    out.println("<div class='panel-group'>");
                                    out.println("<div class='panel panel-default'>");
                                    out.println("<div class='panel-body'>");
                                        
                                    out.println("<div class='col-lg-3 sidenav' align = 'center'>");
                                    out.println("<img src = '" + hospitales.get(i).getFoto() + "' width= '225px'><br>");
                                    out.println("</div>");
                                    out.println("<div class='col-lg-6 text-left'>"); 
                                    out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                    out.println("<input type ='hidden' name='id' value ='"+hospitales.get(i).getIdHospital()+"'>");
                                    out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                    out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                    out.println("<h2 style='color:#1073c5' id='letras'>"+hospitales.get(i).getNombre()+"</h2>");
                                    out.println("</button></form>");
                        
                                    out.println("<img src = 'Imagenes/" + prom + "estrellas.png' width = '125px'><br><br>");
                                    out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                    out.println("Dirección: <br>");
                                    out.println(hospitales.get(i).getDireccion() + "<br>");
                                    out.println("Teléfono : " + hospitales.get(i).getTelefono());

                                    out.println("</div>");
                                    out.println("<div class='col-lg-2 sidenav' align='center'><br>");
                                    out.println("<iframe width='250' height='150' "
                                            + "src='https://maps.google.com/maps?&hl=es&q="
                                            + (hospitales.get(i).getNombre())
                                            + "&ie=UTF8&t=&z=12&iwloc=B&output=embed' "
                                            + "frameborder='0' scrolling='no' marginheight='0' marginwidth='0'></iframe>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    contador2++;
                                }
                            }
                        }    

                        n = false;
                        //2 estrellas    
                        if(contador2 == 0){
                            contador3 = 0;

                            for(int i = 0; i < hospitales.size(); i++){
                                r = new Rankeo();
                                rcrud = new RankeoCRUD();
                                rankeo_entero = Integer.parseInt(rankeo);
                                prom = rcrud.promedio(hospitales.get(i).getIdHospital());

                                String delegacion = hospitales.get(i).getDireccion().substring(hospitales.get(i).getDireccion().indexOf(","), hospitales.get(i).getDireccion().length());
                                if(prom == rankeo_entero - 3 && delegacion.contains(p.getDelegacion())){
                                    if(n == false){
                                        out.println("<div class='container'>");
                                        out.println("<h2> Calificación: " + (rankeo_entero - 3) + "</h2>");
                                        out.println("</div>");
                                        n = true;
                                    }
                                    out.println("<div class='container'>");
                                    out.println("<div class='panel-group'>");
                                    out.println("<div class='panel panel-default'>");
                                    out.println("<div class='panel-body'>");

                                    out.println("<div class='col-lg-3 sidenav' align = 'center'>");
                                    out.println("<img src = '" + hospitales.get(i).getFoto() + "' width= '225px'><br>");
                                    out.println("</div>");
                                    out.println("<div class='col-lg-6 text-left'>"); 
                                    out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                    out.println("<input type ='hidden' name='id' value ='"+hospitales.get(i).getIdHospital()+"'>");
                                    out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                    out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                    out.println("<h2 style='color:#1073c5' id='letras'>"+hospitales.get(i).getNombre()+"</h2>");
                                    out.println("</button></form>");
                        
                                    out.println("<img src = 'Imagenes/" + prom + "estrellas.png' width = '125px'><br><br>");
                                    out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                    out.println("Dirección: <br>");
                                    out.println(hospitales.get(i).getDireccion() + "<br>");
                                    out.println("Teléfono : " + hospitales.get(i).getTelefono());

                                    out.println("</div>");
                                    out.println("<div class='col-lg-2 sidenav' align='center'><br>");
                                    out.println("<iframe width='250' height='150' "
                                            + "src='https://maps.google.com/maps?&hl=es&q="
                                            + (hospitales.get(i).getNombre())
                                            + "&ie=UTF8&t=&z=12&iwloc=B&output=embed' "
                                            + "frameborder='0' scrolling='no' marginheight='0' marginwidth='0'></iframe>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    contador3++;
                                }
                            }
                        }

                        n = false;
                        
                        //1 estrella
                        if(contador3 == 0){
                            contador4 = 0;

                            for(int i = 0; i < hospitales.size(); i++){
                                r = new Rankeo();
                                rcrud = new RankeoCRUD();
                                rankeo_entero = Integer.parseInt(rankeo);
                                prom = rcrud.promedio(hospitales.get(i).getIdHospital());
                                String delegacion = hospitales.get(i).getDireccion().substring(hospitales.get(i).getDireccion().indexOf(","), hospitales.get(i).getDireccion().length());
                                if(prom == rankeo_entero - 4 && delegacion.contains(p.getDelegacion())){
                                    if(n == false){
                                        out.println("<div class='container'>");
                                        out.println("<h2> Calificación: " + (rankeo_entero - 4) + "</h2>");
                                        out.println("</div>");
                                        n = true;
                                    }
                                    out.println("<div class='container'>");
                                    out.println("<div class='panel-group'>");
                                    out.println("<div class='panel panel-default'>");
                                    out.println("<div class='panel-body'>");

                                    out.println("<div class='col-lg-3 sidenav' align = 'center'>");
                                    out.println("<img src = '" + hospitales.get(i).getFoto() + "' width= '225px'><br>");
                                    out.println("</div>");
                                    out.println("<div class='col-lg-6 text-left'>"); 
                                    out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                    out.println("<input type ='hidden' name='id' value ='"+hospitales.get(i).getIdHospital()+"'>");
                                    out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                    out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                    out.println("<h2 style='color:#1073c5' id='letras'>"+hospitales.get(i).getNombre()+"</h2>");
                                    out.println("</button></form>");
                        
                                    out.println("<img src = 'Imagenes/" + prom + "estrellas.png' width = '125px'><br><br>");
                                    out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                    out.println("Dirección: <br>");
                                    out.println(hospitales.get(i).getDireccion() + "<br>");
                                    out.println("Teléfono : " + hospitales.get(i).getTelefono());

                                    out.println("</div>");
                                    out.println("<div class='col-lg-2 sidenav' align='center'><br>");
                                    out.println("<iframe width='250' height='150' "
                                            + "src='https://maps.google.com/maps?&hl=es&q="
                                            + (hospitales.get(i).getNombre())
                                            + "&ie=UTF8&t=&z=12&iwloc=B&output=embed' "
                                            + "frameborder='0' scrolling='no' marginheight='0' marginwidth='0'></iframe>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    contador4++;
                                }
                            }
                        }

                        n = false;
                        //0 estrellas
                        if(contador4 == 0){
                        
                            for(int i = 0; i < hospitales.size(); i++){
                                r = new Rankeo();
                                rcrud = new RankeoCRUD();
                                rankeo_entero = Integer.parseInt(rankeo);
                                prom = rcrud.promedio(hospitales.get(i).getIdHospital());
                                String delegacion = hospitales.get(i).getDireccion().substring(hospitales.get(i).getDireccion().indexOf(","), hospitales.get(i).getDireccion().length());
                                if(prom == rankeo_entero - 5 && delegacion.contains(p.getDelegacion())){
                                    if(n == false){
                                        out.println("<div class='container'>");
                                        out.println("<h2> Calificación: " + (rankeo_entero - 5) + "</h2>");
                                        out.println("</div>");
                                        n = true;
                                    }
                                    
                                    out.println("<div class='container'>");
                                    out.println("<div class='panel-group'>");
                                    out.println("<div class='panel panel-default'>");
                                    out.println("<div class='panel-body'>");

                                    out.println("<div class='col-lg-3 sidenav' align = 'center'>");
                                    out.println("<img src = '" + hospitales.get(i).getFoto() + "' width= '225px'><br>");
                                    out.println("</div>");
                                    out.println("<div class='col-lg-6 text-left'>"); 
                                    out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                    out.println("<input type ='hidden' name='id' value ='"+hospitales.get(i).getIdHospital()+"'>");
                                    out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                    out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                    out.println("<h2 style='color:#1073c5' id='letras'>"+hospitales.get(i).getNombre()+"</h2>");
                                    out.println("</button></form>");
                        
                                    out.println("<img src = 'Imagenes/" + prom + "estrellas.png' width = '125px'><br><br>");
                                    out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                    out.println("Dirección: <br>");
                                    out.println(hospitales.get(i).getDireccion() + "<br>");
                                    out.println("Teléfono : " + hospitales.get(i).getTelefono());

                                    out.println("</div>");
                                    out.println("<div class='col-lg-2 sidenav' align='center'><br>");
                                    out.println("<iframe width='250' height='150' "
                                            + "src='https://maps.google.com/maps?&hl=es&q="
                                            + (hospitales.get(i).getNombre())
                                            + "&ie=UTF8&t=&z=12&iwloc=B&output=embed' "
                                            + "frameborder='0' scrolling='no' marginheight='0' marginwidth='0'></iframe>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                }
                            }
                        }
                        n=false;
                        //Si no hay hospital con rankeo solicitado en la delegación donde pertenece el paciente
                        if(contador == 0 && contador1 == 0 && contador2 == 0 && contador3 == 0 && contador4 == 0){
                            contador = 0;
                            for(int i = 0; i < hospitales.size(); i++){
                                r = new Rankeo();
                                rcrud = new RankeoCRUD();
                                rankeo_entero = Integer.parseInt(rankeo);
                                prom = rcrud.promedio(hospitales.get(i).getIdHospital());
                                String delegacion = hospitales.get(i).getDireccion().substring(hospitales.get(i).getDireccion().indexOf(","), hospitales.get(i).getDireccion().length());
                                if(delegacion.contains(p.getDelegacion())){
                                    if(n == false){
                                        out.println("<div class='container'>");
                                        out.println("<h2> Sugerencia </h2>");
                                        out.println("</div>");
                                        out.println("<div class='container'>");
                                        out.println("<h2> Delegación: " + p.getDelegacion() + "</h2>");
                                        out.println("</div>");
                                        n = true;
                                    }
                                    out.println("<div class='container'>");
                                    out.println("<div class='panel-group'>");
                                    out.println("<div class='panel panel-default'>");
                                    out.println("<div class='panel-body'>");

                                    out.println("<div class='col-lg-3 sidenav' align = 'center'>");
                                    out.println("<img src = '" + hospitales.get(i).getFoto() + "' width= '225px'><br>");
                                    out.println("</div>");
                                    out.println("<div class='col-lg-6 text-left'>"); 
                                    out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                    out.println("<input type ='hidden' name='id' value ='"+hospitales.get(i).getIdHospital()+"'>");
                                    out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                    out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                    out.println("<h2 style='color:#1073c5' id='letras'>"+hospitales.get(i).getNombre()+"</h2>");
                                    out.println("</button></form>");
                        
                                    out.println("<img src = 'Imagenes/" + prom + "estrellas.png' width = '125px'><br><br>");
                                    out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                    out.println("Dirección: <br>");
                                    out.println(hospitales.get(i).getDireccion() + "<br>");
                                    out.println("Teléfono : " + hospitales.get(i).getTelefono());

                                    out.println("</div>");
                                    out.println("<div class='col-lg-2 sidenav' align='center'><br>");
                                    out.println("<iframe width='250' height='150' "
                                            + "src='https://maps.google.com/maps?&hl=es&q="
                                            + (hospitales.get(i).getNombre())
                                            + "&ie=UTF8&t=&z=12&iwloc=B&output=embed' "
                                            + "frameborder='0' scrolling='no' marginheight='0' marginwidth='0'></iframe>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    contador++;
                                }
                            }
                            
                            
                            if(contador == 0){
                                out.println("<div class='container'>");
                                out.println("<h2> Te podría interesar: </h2>");
                                out.println("</div>");

                                for(int i = 0; i < hospitales.size(); i++){
                                    r = new Rankeo();
                                    rcrud = new RankeoCRUD();
                                    rankeo_entero = Integer.parseInt(rankeo);
                                    prom = rcrud.promedio(hospitales.get(i).getIdHospital());
                                    out.println("<div class='container'>");
                                    out.println("<div class='panel-group'>");
                                    out.println("<div class='panel panel-default'>");
                                    out.println("<div class='panel-body'>");

                                    out.println("<div class='col-lg-3 sidenav' align = 'center'>");
                                    out.println("<img src = '" + hospitales.get(i).getFoto() + "' width= '225px'><br>");
                                    out.println("</div>");
                                    out.println("<div class='col-lg-6 text-left'>"); 
                                    out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                    out.println("<input type ='hidden' name='id' value ='"+hospitales.get(i).getIdHospital()+"'>");
                                    out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                    out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                    out.println("<h2 style='color:#1073c5' id='letras'>"+hospitales.get(i).getNombre()+"</h2>");
                                    out.println("</button></form>");

                                    out.println("<img src = 'Imagenes/" + prom + "estrellas.png' width = '125px'><br><br>");
                                    out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                    out.println("Dirección: <br>");
                                    out.println(hospitales.get(i).getDireccion() + "<br>");
                                    out.println("Teléfono : " + hospitales.get(i).getTelefono());

                                    out.println("</div>");
                                    out.println("<div class='col-lg-2 sidenav' align='center'><br>");
                                    out.println("<iframe width='250' height='150' "
                                            + "src='https://maps.google.com/maps?&hl=es&q="
                                            + (hospitales.get(i).getNombre())
                                            + "&ie=UTF8&t=&z=12&iwloc=B&output=embed' "
                                            + "frameborder='0' scrolling='no' marginheight='0' marginwidth='0'></iframe>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");

                                }
                            }
                        }
                    }
                }else{
                    n=false;
                    out.println("<div class='container'>");
                    out.println("<h2> Sin cercanía</h2>");
                    out.println("</div>");

                    if(hospitales != null){
                        contador = 0;
                        for(int i = 0; i < hospitales.size(); i++){
                            r = new Rankeo();
                            rcrud = new RankeoCRUD();
                            rankeo_entero = Integer.parseInt(rankeo);
                            prom = rcrud.promedio(hospitales.get(i).getIdHospital());
                            
                            if(prom == rankeo_entero){
                                if(n == false){
                                    out.println("<div class='container'>");
                                    out.println("<h2> Coincidencia: calificación "+prom+" </h2>");
                                    out.println("</div>");
                                    n=true;
                                }
                                out.println("<div class='container'>");
                                out.println("<div class='panel-group'>");
                                out.println("<div class='panel panel-default'>");
                                out.println("<div class='panel-body'>");

                                out.println("<div class='col-lg-3 sidenav' align = 'center'>");
                                out.println("<img src = '" + hospitales.get(i).getFoto() + "' width= '225px'><br>");
                                out.println("</div>");
                                out.println("<div class='col-lg-6 text-left'>"); 
                                out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                out.println("<input type ='hidden' name='id' value ='"+hospitales.get(i).getIdHospital()+"'>");
                                out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                out.println("<h2 style='color:#1073c5' id='letras'>"+hospitales.get(i).getNombre()+"</h2>");
                                out.println("</button></form>");
                        
                                out.println("<img src = 'Imagenes/" + prom + "estrellas.png' width = '125px'><br><br>");
                                out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                out.println("Dirección: <br>");
                                out.println(hospitales.get(i).getDireccion() + "<br>");
                                out.println("Teléfono : " + hospitales.get(i).getTelefono());

                                out.println("</div>");
                                out.println("<div class='col-lg-2 sidenav' align='center'><br>");
                                out.println("<iframe width='250' height='150' "
                                        + "src='https://maps.google.com/maps?&hl=es&q="
                                        + (hospitales.get(i).getNombre())
                                        + "&ie=UTF8&t=&z=12&iwloc=B&output=embed' "
                                        + "frameborder='0' scrolling='no' marginheight='0' marginwidth='0'></iframe>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                                contador++;
                            }
                        }
                           
                        n=false;
                        
                        //4 estrellas
                        if(contador == 0){
                            out.println("<div class='container'>");
                            out.println("<h2> Sugerencias </h2>");
                            out.println("</div>");

                            contador1 = 0;

                            for(int i = 0; i < hospitales.size(); i++){
                                r = new Rankeo();
                                rcrud = new RankeoCRUD();
                                rankeo_entero = Integer.parseInt(rankeo);
                                prom = rcrud.promedio(hospitales.get(i).getIdHospital());
                                if(prom == rankeo_entero - 1){
                                    if(n == false){
                                        out.println("<div class='container'>");
                                        out.println("<h2> Calificación "+(rankeo_entero-1)+" </h2>");
                                        out.println("</div>");
                                        n=true;
                                    }
                            
                                    out.println("<div class='container'>");
                                    out.println("<div class='panel-group'>");
                                    out.println("<div class='panel panel-default'>");
                                    out.println("<div class='panel-body'>");

                                    out.println("<div class='col-lg-3 sidenav' align = 'center'>");
                                    out.println("<img src = '" + hospitales.get(i).getFoto() + "' width= '225px'><br>");
                                    out.println("</div>");
                                    out.println("<div class='col-lg-6 text-left'>"); 
                                    out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                    out.println("<input type ='hidden' name='id' value ='"+hospitales.get(i).getIdHospital()+"'>");
                                    out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                    out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                    out.println("<h2 style='color:#1073c5' id='letras'>"+hospitales.get(i).getNombre()+"</h2>");
                                    out.println("</button></form>");
                        
                                    out.println("<img src = 'Imagenes/" + prom + "estrellas.png' width = '125px'><br><br>");
                                    out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                    out.println("Dirección: <br>");
                                    out.println(hospitales.get(i).getDireccion() + "<br>");
                                    out.println("Teléfono : " + hospitales.get(i).getTelefono());

                                    out.println("</div>");
                                    out.println("<div class='col-lg-2 sidenav' align='center'><br>");
                                    out.println("<iframe width='250' height='150' "
                                            + "src='https://maps.google.com/maps?&hl=es&q="
                                            + (hospitales.get(i).getNombre())
                                            + "&ie=UTF8&t=&z=12&iwloc=B&output=embed' "
                                            + "frameborder='0' scrolling='no' marginheight='0' marginwidth='0'></iframe>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    contador1++;
                                }
                            }
                        }


                        //3 estrellas    
                        if(contador1 == 0){
                            contador2 = 0;

                            for(int i = 0; i < hospitales.size(); i++){
                                r = new Rankeo();
                                rcrud = new RankeoCRUD();
                                rankeo_entero = Integer.parseInt(rankeo);
                                prom = rcrud.promedio(hospitales.get(i).getIdHospital());

                                if(prom == rankeo_entero - 2){
                                    if(n == false){
                                        out.println("<div class='container'>");
                                        out.println("<h2> Calificación "+(rankeo_entero-2)+" </h2>");
                                        out.println("</div>");
                                        n=true;
                                    }
        
                                    out.println("<div class='container'>");
                                    out.println("<div class='panel-group'>");
                                    out.println("<div class='panel panel-default'>");
                                    out.println("<div class='panel-body'>");

                                    out.println("<div class='col-lg-3 sidenav' align = 'center'>");
                                    out.println("<img src = '" + hospitales.get(i).getFoto() + "' width= '225px'><br>");
                                    out.println("</div>");
                                    out.println("<div class='col-lg-6 text-left'>"); 
                                    out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                    out.println("<input type ='hidden' name='id' value ='"+hospitales.get(i).getIdHospital()+"'>");
                                    out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                    out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                    out.println("<h2 style='color:#1073c5' id='letras'>"+hospitales.get(i).getNombre()+"</h2>");
                                    out.println("</button></form>");
                        
                                    out.println("<img src = 'Imagenes/" + prom + "estrellas.png' width = '125px'><br><br>");
                                    out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                    out.println("Dirección: <br>");
                                    out.println(hospitales.get(i).getDireccion() + "<br>");
                                    out.println("Teléfono : " + hospitales.get(i).getTelefono());

                                    out.println("</div>");
                                    out.println("<div class='col-lg-2 sidenav' align='center'><br>");
                                    out.println("<iframe width='250' height='150' "
                                            + "src='https://maps.google.com/maps?&hl=es&q="
                                            + (hospitales.get(i).getNombre())
                                            + "&ie=UTF8&t=&z=12&iwloc=B&output=embed' "
                                            + "frameborder='0' scrolling='no' marginheight='0' marginwidth='0'></iframe>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    contador2++;
                                }
                            }
                        }    

                        //2 estrellas    
                        if(contador2 == 0){
                            contador3 = 0;

                            for(int i = 0; i < hospitales.size(); i++){
                                r = new Rankeo();
                                rcrud = new RankeoCRUD();
                                rankeo_entero = Integer.parseInt(rankeo);
                                prom = rcrud.promedio(hospitales.get(i).getIdHospital());

                                if(prom == rankeo_entero - 3){
                                    if(n == false){
                                        out.println("<div class='container'>");
                                        out.println("<h2> Calificación "+(rankeo_entero-3)+" </h2>");
                                        out.println("</div>");
                                        n=true;
                                    }
        
                                    out.println("<div class='container'>");
                                    out.println("<div class='panel-group'>");
                                    out.println("<div class='panel panel-default'>");
                                    out.println("<div class='panel-body'>");

                                    out.println("<div class='col-lg-3 sidenav' align = 'center'>");
                                    out.println("<img src = '" + hospitales.get(i).getFoto() + "' width= '225px'><br>");
                                    out.println("</div>");
                                    out.println("<div class='col-lg-6 text-left'>"); 
                                    out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                    out.println("<input type ='hidden' name='id' value ='"+hospitales.get(i).getIdHospital()+"'>");
                                    out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                    out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                    out.println("<h2 style='color:#1073c5' id='letras'>"+hospitales.get(i).getNombre()+"</h2>");
                                    out.println("</button></form>");
                        
                                    out.println("<img src = 'Imagenes/" + prom + "estrellas.png' width = '125px'><br><br>");
                                    out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                    out.println("Dirección: <br>");
                                    out.println(hospitales.get(i).getDireccion() + "<br>");
                                    out.println("Teléfono : " + hospitales.get(i).getTelefono());

                                    out.println("</div>");
                                    out.println("<div class='col-lg-2 sidenav' align='center'><br>");
                                    out.println("<iframe width='250' height='150' "
                                            + "src='https://maps.google.com/maps?&hl=es&q="
                                            + (hospitales.get(i).getNombre())
                                            + "&ie=UTF8&t=&z=12&iwloc=B&output=embed' "
                                            + "frameborder='0' scrolling='no' marginheight='0' marginwidth='0'></iframe>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    contador3++;
                                }
                            }
                        }


                        //1 estrella
                        if(contador3 == 0){
            
                            for(int i = 0; i < hospitales.size(); i++){
                                r = new Rankeo();
                                rcrud = new RankeoCRUD();
                                rankeo_entero = Integer.parseInt(rankeo);
                                prom = rcrud.promedio(hospitales.get(i).getIdHospital());
                                if(prom == rankeo_entero - 4){
                                    if(n == false){
                                        out.println("<div class='container'>");
                                        out.println("<h2> Calificación "+(rankeo_entero-4)+" </h2>");
                                        out.println("</div>");
                                        n=true;
                                    }
        
                                    out.println("<div class='container'>");
                                    out.println("<div class='panel-group'>");
                                    out.println("<div class='panel panel-default'>");
                                    out.println("<div class='panel-body'>");

                                    out.println("<div class='col-lg-3 sidenav' align = 'center'>");
                                    out.println("<img src = '" + hospitales.get(i).getFoto() + "' width= '225px'><br>");
                                    out.println("</div>");
                                    out.println("<div class='col-lg-6 text-left'>"); 
                                    out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                    out.println("<input type ='hidden' name='id' value ='"+hospitales.get(i).getIdHospital()+"'>");
                                    out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                    out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                    out.println("<h2 style='color:#1073c5' id='letras'>"+hospitales.get(i).getNombre()+"</h2>");
                                    out.println("</button></form>");
                        
                                    out.println("<img src = 'Imagenes/" + prom + "estrellas.png' width = '125px'><br><br>");
                                    out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                    out.println("Dirección: <br>");
                                    out.println(hospitales.get(i).getDireccion() + "<br>");
                                    out.println("Teléfono : " + hospitales.get(i).getTelefono());

                                    out.println("</div>");
                                    out.println("<div class='col-lg-2 sidenav' align='center'><br>");
                                    out.println("<iframe width='250' height='150' "
                                            + "src='https://maps.google.com/maps?&hl=es&q="
                                            + (hospitales.get(i).getNombre())
                                            + "&ie=UTF8&t=&z=12&iwloc=B&output=embed' "
                                            + "frameborder='0' scrolling='no' marginheight='0' marginwidth='0'></iframe>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    contador4++;
                                }
                            }
                        }

                        //0 estrellas
                        if(contador4 == 0){
            
                            for(int i = 0; i < hospitales.size(); i++){
                                r = new Rankeo();
                                rcrud = new RankeoCRUD();
                                rankeo_entero = Integer.parseInt(rankeo);
                                prom = rcrud.promedio(hospitales.get(i).getIdHospital());
                                if(prom == rankeo_entero - 5){
                                    if(n == false){
                                        out.println("<div class='container'>");
                                        out.println("<h2> Calificación "+(rankeo_entero-5)+" </h2>");
                                        out.println("</div>");
                                        n=true;
                                    }
        
                                    out.println("<div class='container'>");
                                    out.println("<div class='panel-group'>");
                                    out.println("<div class='panel panel-default'>");
                                    out.println("<div class='panel-body'>");

                                    out.println("<div class='col-lg-3 sidenav' align = 'center'>");
                                    out.println("<img src = '" + hospitales.get(i).getFoto() + "' width= '225px'><br>");
                                    out.println("</div>");
                                    out.println("<div class='col-lg-6 text-left'>"); 
                                    out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                    out.println("<input type ='hidden' name='id' value ='"+hospitales.get(i).getIdHospital()+"'>");
                                    out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                    out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                    out.println("<h2 style='color:#1073c5' id='letras'>"+hospitales.get(i).getNombre()+"</h2>");
                                    out.println("</button></form>");
                        
                                    out.println("<img src = 'Imagenes/" + prom + "estrellas.png' width = '125px'><br><br>");
                                    out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                    out.println("Dirección: <br>");
                                    out.println(hospitales.get(i).getDireccion() + "<br>");
                                    out.println("Teléfono : " + hospitales.get(i).getTelefono());

                                    out.println("</div>");
                                    out.println("<div class='col-lg-2 sidenav' align='center'><br>");
                                    out.println("<iframe width='250' height='150' "
                                            + "src='https://maps.google.com/maps?&hl=es&q="
                                            + (hospitales.get(i).getNombre())
                                            + "&ie=UTF8&t=&z=12&iwloc=B&output=embed' "
                                            + "frameborder='0' scrolling='no' marginheight='0' marginwidth='0'></iframe>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                }
                            }
                        }
                    }
                }
            }
            
            ///////////////////////////Filtro por rankeo, servicio médico y cercanía////////////////////////////
            if (!"".equals(cercania) &&  !"".equals(rankeo) && "".equals(especialidad) && !"".equals(servicios)) {
                sm = new ServiciosMedicos();
                smcrud = new ServiciosMedicosCRUD();
                r = new Rankeo();
                rcrud = new RankeoCRUD();
                pcrud = new PacienteDAO();
                p = pcrud.seleccionar_informacion(id);

                sm.setTipo(servicios);
                lista3 = smcrud.seleccionar_tipo_servicio(sm);
                rankeo_entero = Integer.parseInt(rankeo);
                Integer promedio = 0;
                int contadoraux = 0;
                h = new Hospitales();
                hcrud = new HospitalCRUD();
                boolean n = false;               
                if("Si".equals(cercania)){
                    if (lista3 != null) {
                        idh = new int[lista3.size()];
                        contador = 0;
                        for (int i = 0; i < lista3.size(); i++) {
                            promedio = rcrud.promedio(lista3.get(i).getIdHospital());
                            if (promedio == rankeo_entero){
                                idh[contador] = lista3.get(i).getIdHospital();
                                contador++;
                            }
                        }
                        if (contador != 0) {
                            for (int i = 0; i < contador; i++) {
                                h = new Hospitales();
                                hcrud = new HospitalCRUD();
                                h.setIdHospital(idh[i]);
                                h = hcrud.readOne1(h);
                                String delegacion = h.getDireccion().substring(h.getDireccion().indexOf(","), h.getDireccion().length());
                                if(promedio == rankeo_entero && delegacion.contains(p.getDelegacion())){
                                    if(n == false){
                                        out.println("<div class='container'>");
                                        out.println("<h2> Coincidencia en: calificación:"+(rankeo_entero)+", "+servicios+"</h2>");
                                        out.println("</div>");
                                        n = true;
                                    }
                                    out.println("<div class='container'>");
                                    out.println("<div class='panel-group'>");
                                    out.println("<div class='panel panel-default'>");
                                    out.println("<div class='panel-body'>");

                                    out.println("<div class='col-lg-3 sidenav' align = 'center'>");
                                    out.println("<img src = '" + h.getFoto() + "' width= '225px'><br>");
                                    out.println("</div>");
                                    out.println("<div class='col-lg-6 text-left'>"); 
                                    out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                    out.println("<input type ='hidden' name='id' value ='"+h.getIdHospital()+"'>");
                                    out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                    out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                    out.println("<h2 style='color:#1073c5' id='letras'>"+ h.getNombre()+"</h2>");
                                    out.println("</button></form>");

                                    out.println("<img src = 'Imagenes/" + rankeo_entero + "estrellas.png' width = '125px'><br><br>");
                                    out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                    out.println("Dirección: <br>");
                                    out.println(h.getDireccion() + "<br>");
                                    out.println("Teléfono : " + h.getTelefono());

                                    out.println("</div>");
                                    out.println("<div class='col-lg-2 sidenav' align='center'><br>");
                                    out.println("<iframe width='250' height='150' "
                                            + "src='https://maps.google.com/maps?&hl=es&q="
                                            + (h.getNombre())
                                            + "&ie=UTF8&t=&z=12&iwloc=B&output=embed' "
                                            + "frameborder='0' scrolling='no' marginheight='0' marginwidth='0'></iframe>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    contadoraux++;
                                }
                            }
                            n = false;
                            if(contadoraux == 0){
                                out.println("<div class='container'>");
                                out.println("<h2> Sugerencia alcaldías vecinas</h2>");
                                out.println("</div>");

                                out.println("<div class='container'>");
                                out.println("<h2> Coincidencia en: "+servicios+" y calificación:"+promedio+" y alcaldía: "+p.getDelegacion()+"</h2>");
                                out.println("</div>");
                                
                                //No encontró nada de hospitales cercanos en la delegación del paciente
                                pivote = 0;
                                if(contadoraux == 0){
                                    for(int i = 0; i < 16; i++){
                                        if(p.getDelegacion().equals(alcaldias[0][i])){
                                            pivote = i;
                                        }
                                    }
                                    contadoraux = 0;
                                    for(int i = 0; i < 8; i++){
                                        if(alcaldias[i][pivote] != null){
                                            contadoraux++;
                                        }else{
                                            break;
                                        }
                                    }

                                    for(int i = 0; i < contadoraux; i++){
                                        h.setDireccion(alcaldias[i][pivote]);
                                        lista = hcrud.readbyaddress(h);
                                        if(lista != null){
                                            for(int j = 0; j < lista.size(); j++){
                                                rcrud = new RankeoCRUD();
                                                promedio = rcrud.promedio(lista.get(j).getIdHospital());

                                                out.println("<div class='container'>");
                                                out.println("<div class='panel-group'>");
                                                out.println("<div class='panel panel-default'>");
                                                out.println("<div class='panel-body'>");

                                                out.println("<div class='col-lg-3 sidenav' align = 'center'>");
                                                out.println("<img src = '" + lista.get(j).getFoto() + "' width= '225px'><br>");
                                                out.println("</div>");
                                                out.println("<div class='col-lg-6 text-left'>"); 
                                                out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                                out.println("<input type ='hidden' name='id' value ='"+lista.get(j).getIdHospital()+"'>");
                                                out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                                out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                                out.println("<h2 style='color:#1073c5' id='letras'>"+ lista.get(j).getNombre()+"</h2>");
                                                out.println("</button></form>");

                                                out.println("<img src = 'Imagenes/" + promedio + "estrellas.png' width = '125px'><br><br>");
                                                out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                                out.println("Dirección: <br>");
                                                out.println(lista.get(j).getDireccion() + "<br>");
                                                out.println("Teléfono : " + lista.get(j).getTelefono());

                                                out.println("</div>");
                                                out.println("<div class='col-lg-2 sidenav' align='center'><br>");
                                                out.println("<iframe width='250' height='150' "
                                                        + "src='https://maps.google.com/maps?&hl=es&q="
                                                        + (lista.get(j).getNombre())
                                                        + "&ie=UTF8&t=&z=12&iwloc=B&output=embed' "
                                                        + "frameborder='0' scrolling='no' marginheight='0' marginwidth='0'></iframe>");
                                                out.println("</div>");
                                                out.println("</div>");
                                                out.println("</div>");
                                                out.println("</div>");
                                                out.println("</div>");
                                            }
                                        }
                                    }
                                }
                            }n=false;
                        
                        }
                        //Agregado Sugerencias servicio médico con diferente ranking
                        else if(contador == 0){
                            contadoraux = 0;
                                
                            for (int i = 0; i < lista3.size(); i++) {
                                h = new Hospitales();
                                hcrud = new HospitalCRUD();
                                h.setIdHospital(lista3.get(i).getIdHospital());
                                h = hcrud.readOne1(h);

                                promedio = rcrud.promedio(lista3.get(i).getIdHospital());
                                String delegacion = h.getDireccion().substring(h.getDireccion().indexOf(","), h.getDireccion().length());
                                if(promedio == rankeo_entero && delegacion.contains(p.getDelegacion())){
                                    if(n == false){
                                        out.println("<div class='container'>");
                                        out.println("<h2> Sugerencias</h2>");
                                        out.println("</div>");
                                        out.println("<div class='container'>");
                                        out.println("<h2> Alcaldía: "+p.getDelegacion()+"</h2>");
                                        out.println("</div>");
 
                                        out.println("<div class='container'>");
                                        out.println("<h2> Calificación: "+promedio+"</h2>");
                                        out.println("</div>");
                                        n = true;
                                    }
                                    out.println("<div class='container'>");
                                    out.println("<div class='panel-group'>");
                                    out.println("<div class='panel panel-default'>");
                                    out.println("<div class='panel-body'>");

                                    out.println("<div class='col-lg-3 sidenav' align = 'center'>");
                                    out.println("<img src = '" + h.getFoto() + "' width= '225px'><br>");
                                    out.println("</div>");
                                    out.println("<div class='col-lg-6 text-left'>"); 
                                    out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                    out.println("<input type ='hidden' name='id' value ='"+h.getIdHospital()+"'>");
                                    out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                    out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                    out.println("<h2 style='color:#1073c5' id='letras'>"+ h.getNombre()+"</h2>");
                                    out.println("</button></form>");
                        
                                    out.println("<img src = 'Imagenes/" + promedio + "estrellas.png' width = '125px'><br><br>");
                                    out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                    out.println("Dirección: <br>");
                                    out.println(h.getDireccion() + "<br>");
                                    out.println("Teléfono : " + h.getTelefono());

                                    out.println("</div>");
                                    out.println("<div class='col-lg-2 sidenav' align='center'><br>");
                                    out.println("<iframe width='250' height='150' "
                                            + "src='https://maps.google.com/maps?&hl=es&q="
                                            + (h.getNombre())
                                            + "&ie=UTF8&t=&z=12&iwloc=B&output=embed' "
                                            + "frameborder='0' scrolling='no' marginheight='0' marginwidth='0'></iframe>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    contadoraux++;
                                }
                            }
                            if(contadoraux == 0){
                                out.println("<div class='container'>");
                                out.println("<h2> Sugerencias</h2>");
                                //out.println("<h2> Sugerencias alcaldías vecinas</h2>");
                                out.println("</div>");
                                out.println("<div class='container'>");
                                out.println("<h2> Coincidencia en: "+servicios+"</h2>");
                                out.println("</div>");
                                    
                                //No encontró nada de hospitales cercanos en la delegación del paciente
                                pivote = 0;
                                if(contadoraux == 0){
                                    for(int i = 0; i < 16; i++){
                                        if(p.getDelegacion().equals(alcaldias[0][i])){
                                            pivote = i;
                                        }
                                    }
                                    contadoraux = 0;
                                    for(int i = 0; i < 8; i++){
                                        if(alcaldias[i][pivote] != null){
                                            contadoraux++;
                                        }else{
                                            break;
                                        }
                                    }

                                    for(int i = 0; i < contadoraux; i++){
                                        h.setDireccion(alcaldias[i][pivote]);
                                        lista = hcrud.readbyaddress(h);
                                        if(lista != null){
                                            for(int j = 0; j < lista.size(); j++){
                                                rcrud = new RankeoCRUD();
                                                promedio = rcrud.promedio(lista.get(j).getIdHospital());

                                                out.println("<div class='container'>");
                                                out.println("<div class='panel-group'>");
                                                out.println("<div class='panel panel-default'>");
                                                out.println("<div class='panel-body'>");

                                                out.println("<div class='col-lg-3 sidenav' align = 'center'>");
                                                out.println("<img src = '" + lista.get(j).getFoto() + "' width= '225px'><br>");
                                                out.println("</div>");
                                                out.println("<div class='col-lg-6 text-left'>"); 

                                                out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                                out.println("<input type ='hidden' name='id' value ='"+lista.get(j).getIdHospital()+"'>");
                                                out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                                out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                                out.println("<h2 style='color:#1073c5' id='letras'>"+ lista.get(j).getNombre() +"</h2>");
                                                out.println("</button></form>");

                                                out.println("<img src = 'Imagenes/" + promedio + "estrellas.png' width = '125px'><br><br>");
                                                out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                                out.println("Dirección: <br>");
                                                out.println(lista.get(j).getDireccion() + "<br>");
                                                out.println("Teléfono : " + lista.get(j).getTelefono());

                                                out.println("</div>");
                                                out.println("<div class='col-lg-2 sidenav' align='center'><br>");
                                                out.println("<iframe width='250' height='150' "
                                                        + "src='https://maps.google.com/maps?&hl=es&q="
                                                        + (lista.get(j).getNombre())
                                                        + "&ie=UTF8&t=&z=12&iwloc=B&output=embed' "
                                                        + "frameborder='0' scrolling='no' marginheight='0' marginwidth='0'></iframe>");
                                                out.println("</div>");
                                                out.println("</div>");
                                                out.println("</div>");
                                                out.println("</div>");
                                                out.println("</div>");
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    else{
                        out.println("<div class='container'>");
                        out.println("<h2> No se encontraron resultados </h2>");
                        out.println("</div>");
                    }
                }else{
                    if (lista3 != null) {
                        idh = new int[lista3.size()];
                        contador = 0;
                        for (int i = 0; i < lista3.size(); i++) {
                            promedio = rcrud.promedio(lista3.get(i).getIdHospital());
                            if (promedio == rankeo_entero){
                    
                                idh[contador] = lista3.get(i).getIdHospital();
                                contador++;
                            }
                        }
                        if (contador != 0) {
                            out.println("<div class='container'>");
                            out.println("<h2> Coincidencia en: "+servicios+", calificación "+promedio+"</h2>");
                            out.println("</div>");
                            
                            for (int i = 0; i < contador; i++) {
                                h = new Hospitales();
                                hcrud = new HospitalCRUD();
                                h.setIdHospital(idh[i]);
                                h = hcrud.readOne1(h);
                                if(h!=null){
                                        out.println("<div class='container'>");
                                        out.println("<div class='panel-group'>");
                                        out.println("<div class='panel panel-default'>");
                                        out.println("<div class='panel-body'>");

                                        out.println("<div class='col-lg-3 sidenav' align = 'center'>");
                                        out.println("<img src = '" + h.getFoto() + "' width= '225px'><br>");
                                        out.println("</div>");
                                        out.println("<div class='col-lg-6 text-left'>"); 
                                        out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                        out.println("<input type ='hidden' name='id' value ='"+h.getIdHospital()+"'>");
                                        out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                        out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                        out.println("<h2 style='color:#1073c5' id='letras'>"+ h.getNombre() +"</h2>");
                                        out.println("</button></form>");
                        
                                        out.println("<img src = 'Imagenes/" + rankeo_entero + "estrellas.png' width = '125px'><br><br>");
                                        out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                        out.println("Dirección: <br>");
                                        out.println(h.getDireccion() + "<br>");
                                        out.println("Teléfono : " + h.getTelefono());

                                        out.println("</div>");
                                        out.println("<div class='col-lg-2 sidenav' align='center'><br>");
                                        out.println("<iframe width='250' height='150' "
                                                + "src='https://maps.google.com/maps?&hl=es&q="
                                                + (h.getNombre())
                                                + "&ie=UTF8&t=&z=12&iwloc=B&output=embed' "
                                                + "frameborder='0' scrolling='no' marginheight='0' marginwidth='0'></iframe>");
                                        out.println("</div>");
                                        out.println("</div>");
                                        out.println("</div>");
                                        out.println("</div>");
                                        out.println("</div>");
                                }
                            }
                        }

                        //Agregado Sugerencias Servicio Medico
                        else if(contador == 0){
                            out.println("<div class='container'>");
                            out.println("<h2> Sugerencias</h2>");
                            out.println("</div>");
                            out.println("<div class='container'>");
                            out.println("<h2> Servicio médico: "+servicios+"</h2>");
                            out.println("</div>");

                            for (int i = 0; i < lista3.size(); i++) {
                                h = new Hospitales();
                                hcrud = new HospitalCRUD();
                                h.setIdHospital(lista3.get(i).getIdHospital());
                                h = hcrud.readOne1(h);

                                promedio = rcrud.promedio(lista3.get(i).getIdHospital());
                                if(h != null){
                                    out.println("<div class='container'>");
                                    out.println("<div class='panel-group'>");
                                    out.println("<div class='panel panel-default'>");
                                    out.println("<div class='panel-body'>");

                                    out.println("<div class='col-lg-3 sidenav' align = 'center'>");
                                    out.println("<img src = '" + h.getFoto() + "' width= '225px'><br>");
                                    out.println("</div>");
                                    out.println("<div class='col-lg-6 text-left'>"); 
                                    out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                    out.println("<input type ='hidden' name='id' value ='"+h.getIdHospital()+"'>");
                                    out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                    out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                    out.println("<h2 style='color:#1073c5' id='letras'>"+ h.getNombre() +"</h2>");
                                    out.println("</button></form>");
                        
                                    out.println("<img src = 'Imagenes/" + promedio + "estrellas.png' width = '125px'><br><br>");
                                    out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                    out.println("Dirección: <br>");
                                    out.println(h.getDireccion() + "<br>");
                                    out.println("Teléfono : " + h.getTelefono());

                                    out.println("</div>");
                                    out.println("<div class='col-lg-2 sidenav' align='center'><br>");
                                    out.println("<iframe width='250' height='150' "
                                            + "src='https://maps.google.com/maps?&hl=es&q="
                                            + (h.getNombre())
                                            + "&ie=UTF8&t=&z=12&iwloc=B&output=embed' "
                                            + "frameborder='0' scrolling='no' marginheight='0' marginwidth='0'></iframe>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                }
                            }
                        }
                    }
                }
            }
            
            
            ///////////////////////////Filtro por rankeo, especialidad y cercanía//////////////////////////////
            if (!"".equals(cercania) && !"".equals(rankeo) && !"".equals(especialidad) && "".equals(servicios)) {
                m = new Medicos();
                mcrud = new MedicosCRUD();
                rm = new RankeoMedicos();
                rmcrud = new RankeoMedicosCRUD();
                int promedio = 0;
                m.setEspecialidades(especialidad);
                lista2 = mcrud.Hospitales_especialidad(m);
                rankeo_entero = Integer.parseInt(rankeo);
                pcrud = new PacienteDAO();
                p = pcrud.seleccionar_informacion(id);
                int contadoraux = 0;
                boolean n = false;
                if("Si".equals(cercania)){
                    if(lista2 != null){
                        idh = new int[lista2.size()];
                        idm = new int[lista2.size()];
                        contador = 0;
                        for(int i = 0; i < lista2.size();i++){
                            rm.setIdHospital(lista2.get(i).getIdHospital());
                            rm.setIdMedico(lista2.get(i).getIdMedicos());
                            promedio = rmcrud.promedio(rm);
                            if(promedio == rankeo_entero){
                                idh[contador] = lista2.get(i).getIdHospital();
                                idm[contador] = lista2.get(i).getIdMedicos();
                                contador++;
                            }
                        }
                        if(contador != 0){
                            for(int i = 0; i < contador; i++){
                                h = new Hospitales();
                                hcrud = new HospitalCRUD();
                                h.setIdHospital(idh[i]);
                                h = hcrud.readOne1(h);
                                m.setIdMedicos(idm[i]);
                                m = (Medicos)mcrud.readMedicos(m).get(0);
                                rm.setIdHospital(idh[i]);
                                rm.setIdMedico(idm[i]);
                                promedio = rmcrud.promedio(rm);
                                String delegacion = h.getDireccion().substring(h.getDireccion().indexOf(","), h.getDireccion().length());
                                if(delegacion.contains(p.getDelegacion())){
                                    if(n == false){
                                        out.println("<div class='container'>");
                                        out.println("<h2>Coincidencia en: "+especialidad+", calificación "+rankeo_entero+"</h2>");
                                        out.println("</div>");
                                        n = true;
                                    }
                                    out.println("<div class='container'>");
                                    out.println("<div class='panel-group'>");
                                    out.println("<div class='panel panel-default'>");
                                    out.println("<div class='panel-body'>");

                                    out.println("<div class='col-md-3 sidenav' align = 'center'>");
                                    out.println("<img src = '/Prototipo4_1/Imagenes/doctor.png' width= '150px'>");
                                    out.println("</div>");
                                    out.println("<div class='col-md-6 text-left'>"); 
                                    
                                    out.println("<form action='/Prototipo4_1/Paginas/InformacionEspecialistas.jsp' method ='post'>");
                                    out.println("<input type ='hidden' name='idhospital' value ='"+idh[i] +"'>");
                                    out.println("<input type ='hidden' name='idusuario' value ='"+id+"'>");
                                    out.println("<input type ='hidden' name='idmedico' value ='"+idm[i]+"'>");
                                    out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                    out.println("<h2 style='color:#1073c5' id='letras'>"+m.getNombreMedico() + " " + m.getAPaternoMedico() + " " + m.getAMaternoMedico()+"</h2>");
                                    out.println("</button></form>");

                                    out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                    out.println("<input type ='hidden' name='id' value ='"+h.getIdHospital()+"'>");
                                    out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                    out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                    out.println("<h7 style='color:#1073c5' id='letras'>"+h.getNombre()+"</h7>");
                                    out.println("</button></form>");
                            
                                    out.println("<img src = '/Prototipo4_1/Imagenes/"+promedio+"estrellas.png' width = '125px'><br><br>");

                                    out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                    out.println("Dirección: <br>");
                                    out.println(h.getDireccion() + "<br>");
                                    out.println("Teléfono : " + m.getTelefonos());

                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    contadoraux++;
                                }
                            }
                        }
                            
                        n = false;
                        //Agregado recomendación especialidad
                        if(contador == 0 || contadoraux == 0){
                            
                            out.println("<div class='container'>");
                            out.println("<h2> Sugerencias</h2>");
                            out.println("</div>");
                            contadoraux = 0;
                            for(int i = 0; i < lista2.size();i++){
                                h = new Hospitales();
                                hcrud = new HospitalCRUD();
                                h.setIdHospital(lista2.get(i).getIdHospital());
                                h = hcrud.readOne1(h);

                                rm = new RankeoMedicos();
                                rmcrud = new RankeoMedicosCRUD();
                                rm.setIdHospital(lista2.get(i).getIdHospital());
                                rm.setIdMedico(lista2.get(i).getIdMedicos());
                                ranking = rmcrud.promedio(rm);
                                String delegacion = h.getDireccion().substring(h.getDireccion().indexOf(","), h.getDireccion().length());
                                if(delegacion.contains(p.getDelegacion())){
                                    if(n == false){
                                        out.println("<div class='container'>");
                                        out.println("<h2> Alcaldía: "+p.getDelegacion()+"</h2>");
                                        out.println("</div>");
                                        out.println("<div class='container'>");
                                        out.println("<h2> Coincidencia en: "+especialidad+"</h2>");
                                        out.println("</div>");
                                        n = true;
                                    }
                                    out.println("<div class='container'>");
                                    out.println("<div class='panel-group'>");
                                    out.println("<div class='panel panel-default'>");
                                    out.println("<div class='panel-body'>");

                                    out.println("<div class='col-md-3 sidenav' align = 'center'>");
                                    out.println("<img src = '/Prototipo4_1/Imagenes/doctor.png' width= '150px'>");
                                    out.println("</div>");
                                    out.println("<div class='col-md-6 text-left'>"); 
                                    
                                    out.println("<form action='/Prototipo4_1/Paginas/InformacionEspecialistas.jsp' method ='post'>");
                                    out.println("<input type ='hidden' name='idhospital' value ='"+ h.getIdHospital() +"'>");
                                    out.println("<input type ='hidden' name='idusuario' value ='"+id+"'>");
                                    out.println("<input type ='hidden' name='idmedico' value ='"+lista2.get(i).getIdMedicos()+"'>");
                                    out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                    out.println("<h2 style='color:#1073c5' id='letras'>"+lista2.get(i).getNombreMedico() + " " + lista2.get(i).getAPaternoMedico() + " " + lista2.get(i).getAMaternoMedico()+"</h2>");
                                    out.println("</button></form>");

                                    out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                    out.println("<input type ='hidden' name='id' value ='"+h.getIdHospital()+"'>");
                                    out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                    out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                    out.println("<h7 style='color:#1073c5' id='letras'>"+h.getNombre()+"</h7>");
                                    out.println("</button></form>");
                            
                                    out.println("<img src = '/Prototipo4_1/Imagenes/"+ranking+"estrellas.png' width = '125px'><br><br>");

                                    out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                    out.println("Dirección: <br>");
                                    out.println(h.getDireccion() + "<br>");
                                    out.println("Teléfono : " + lista2.get(i).getTelefonos());

                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    contadoraux++;
                                }
                            }
                            n=false;
                            if(contadoraux == 0){
                                contadoraux = 0;
                                for(int i = 0; i < lista2.size();i++){
                                h = new Hospitales();
                                hcrud = new HospitalCRUD();
                                h.setIdHospital(lista2.get(i).getIdHospital());
                                h = hcrud.readOne1(h);

                                rm = new RankeoMedicos();
                                rmcrud = new RankeoMedicosCRUD();
                                rm.setIdHospital(lista2.get(i).getIdHospital());
                                rm.setIdMedico(lista2.get(i).getIdMedicos());
                                ranking = rmcrud.promedio(rm);
                                if(ranking == rankeo_entero){
                                    if(n == false){
                                        out.println("<div class='container'>");
                                        out.println("<h2> Coincidencia en: "+especialidad+"</h2>");
                                        out.println("</div>");
                                        n = true;
                                    }
                                    
                                    out.println("<div class='container'>");
                                    out.println("<div class='panel-group'>");
                                    out.println("<div class='panel panel-default'>");
                                    out.println("<div class='panel-body'>");

                                    out.println("<div class='col-md-3 sidenav' align = 'center'>");
                                    out.println("<img src = '/Prototipo4_1/Imagenes/doctor.png' width= '150px'>");
                                    out.println("</div>");
                                    out.println("<div class='col-md-6 text-left'>"); 
                                    
                                    out.println("<form action='/Prototipo4_1/Paginas/InformacionEspecialistas.jsp' method ='post'>");
                                    out.println("<input type ='hidden' name='idhospital' value ='"+ h.getIdHospital() +"'>");
                                    out.println("<input type ='hidden' name='idusuario' value ='"+id+"'>");
                                    out.println("<input type ='hidden' name='idmedico' value ='"+lista2.get(i).getIdMedicos()+"'>");
                                    out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                    out.println("<h2 style='color:#1073c5' id='letras'>"+lista2.get(i).getNombreMedico() + " " + lista2.get(i).getAPaternoMedico() + " " + lista2.get(i).getAMaternoMedico()+"</h2>");
                                    out.println("</button></form>");

                                    out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                    out.println("<input type ='hidden' name='id' value ='"+h.getIdHospital()+"'>");
                                    out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                    out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                    out.println("<h7 style='color:#1073c5' id='letras'>"+h.getNombre()+"</h7>");
                                    out.println("</button></form>");
                            
                                    out.println("<img src = '/Prototipo4_1/Imagenes/"+ranking+"estrellas.png' width = '125px'><br><br>");

                                    out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                    out.println("Dirección: <br>");
                                    out.println(h.getDireccion() + "<br>");
                                    out.println("Teléfono : " + lista2.get(i).getTelefonos());

                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    contadoraux++;
                                    }
                                }
                            }n=false;
                            if(contadoraux == 0){
                                    for(int i = 0; i < lista2.size();i++){
                                    h = new Hospitales();
                                    hcrud = new HospitalCRUD();
                                    h.setIdHospital(lista2.get(i).getIdHospital());
                                    h = hcrud.readOne1(h);

                                    rm = new RankeoMedicos();
                                    rmcrud = new RankeoMedicosCRUD();
                                    rm.setIdHospital(lista2.get(i).getIdHospital());
                                    rm.setIdMedico(lista2.get(i).getIdMedicos());
                                    ranking = rmcrud.promedio(rm);
                                    if(n == false){
                                        out.println("<div class='container'>");
                                        out.println("<h2> Coincidencia en: "+especialidad+"</h2>");
                                        out.println("</div>");
                                        n = true;
                                    }
                                    out.println("<div class='container'>");
                                    out.println("<div class='panel-group'>");
                                    out.println("<div class='panel panel-default'>");
                                    out.println("<div class='panel-body'>");

                                    out.println("<div class='col-md-3 sidenav' align = 'center'>");
                                    out.println("<img src = '/Prototipo4_1/Imagenes/doctor.png' width= '150px'>");
                                    out.println("</div>");
                                    out.println("<div class='col-md-6 text-left'>"); 

                                    out.println("<form action='/Prototipo4_1/Paginas/InformacionEspecialistas.jsp' method ='post'>");
                                    out.println("<input type ='hidden' name='idhospital' value ='"+ h.getIdHospital() +"'>");
                                    out.println("<input type ='hidden' name='idusuario' value ='"+id+"'>");
                                    out.println("<input type ='hidden' name='idmedico' value ='"+lista2.get(i).getIdMedicos()+"'>");
                                    out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                    out.println("<h2 style='color:#1073c5' id='letras'>"+lista2.get(i).getNombreMedico() + " " + lista2.get(i).getAPaternoMedico() + " " + lista2.get(i).getAMaternoMedico()+"</h2>");
                                    out.println("</button></form>");

                                    out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                    out.println("<input type ='hidden' name='id' value ='"+h.getIdHospital()+"'>");
                                    out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                    out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                    out.println("<h7 style='color:#1073c5' id='letras'>"+h.getNombre()+"</h7>");
                                    out.println("</button></form>");

                                    out.println("<img src = '/Prototipo4_1/Imagenes/"+ranking+"estrellas.png' width = '125px'><br><br>");

                                    out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                    out.println("Dirección: <br>");
                                    out.println(h.getDireccion() + "<br>");
                                    out.println("Teléfono : " + lista2.get(i).getTelefonos());

                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                }
                            }
                        }
                    }
                    else{
                        out.println("<div class='container'>");
                        out.println("<h2> No se encontraron resultados </h2>");
                        out.println("</div>");
                    }
                }else{
                    out.println("<div class='container'>");
                    out.println("<h2> Sugerencias</h2>");
                    out.println("</div>");
                            
                    if(lista2 != null){
                        idh = new int[lista2.size()];
                        idm = new int[lista2.size()];
                        contador = 0;
                        for(int i = 0; i < lista2.size();i++){
                            rm.setIdHospital(lista2.get(i).getIdHospital());
                            rm.setIdMedico(lista2.get(i).getIdMedicos());
                            promedio = rmcrud.promedio(rm);

                            if(promedio == rankeo_entero){
                                idh[contador] = lista2.get(i).getIdHospital();
                                idm[contador] = lista2.get(i).getIdMedicos();
                                contador++;
                            }
                        }
                        if(contador != 0){
                            out.println("<div class='container'>");
                            out.println("<h2> Coincidencia en: calificación "+promedio+", "+servicios+"</h2>");
                            out.println("</div>");
                            for(int i = 0; i < contador; i++){
                                h = new Hospitales();
                                hcrud = new HospitalCRUD();
                                h.setIdHospital(idh[i]);
                                h = hcrud.readOne1(h);
                                m.setIdMedicos(idm[i]);
                                m = (Medicos)mcrud.readMedicos(m).get(0);
                                rm.setIdHospital(idh[i]);
                                rm.setIdMedico(idm[i]);
                                promedio = rmcrud.promedio(rm);
                                out.println("<div class='container'>");
                                out.println("<div class='panel-group'>");
                                out.println("<div class='panel panel-default'>");
                                out.println("<div class='panel-body'>");

                                out.println("<div class='col-md-3 sidenav' align = 'center'>");
                                out.println("<img src = '/Prototipo4_1/Imagenes/doctor.png' width= '150px'>");
                                out.println("</div>");
                                out.println("<div class='col-md-6 text-left'>"); 
                                
                                out.println("<form action='/Prototipo4_1/Paginas/InformacionEspecialistas.jsp' method ='post'>");
                                out.println("<input type ='hidden' name='idhospital' value ='"+ idh[i] +"'>");
                                out.println("<input type ='hidden' name='idusuario' value ='"+id+"'>");
                                out.println("<input type ='hidden' name='idmedico' value ='"+idm[i]+"'>");
                                out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                out.println("<h2 style='color:#1073c5' id='letras'>"+m.getNombreMedico() + " " + m.getAPaternoMedico() + " " + m.getAMaternoMedico()+"</h2>");
                                out.println("</button></form>");

                                out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                out.println("<input type ='hidden' name='id' value ='"+h.getIdHospital()+"'>");
                                out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                out.println("<h7 style='color:#1073c5' id='letras'>"+h.getNombre()+"</h7>");
                                out.println("</button></form>");
                            
                                out.println("<img src = '/Prototipo4_1/Imagenes/"+promedio+"estrellas.png' width = '125px'><br><br>");

                                out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                out.println("Dirección: <br>");
                                out.println(h.getDireccion() + "<br>");
                                out.println("Teléfono : " + m.getTelefonos());

                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                            }
                        }
                        //Agregado recomendación especialidad
                        else if(contador == 0){
                            /*out.println("<div class='container'>");
                            out.println("<h2> Segerencias</h2>");
                            out.println("</div>");
                            */out.println("<div class='container'>");
                            out.println("<h2> Coincidencia en: "+especialidad+"</h2>");
                            out.println("</div>");
                            for(int i = 0; i < lista2.size();i++){
                                h = new Hospitales();
                                hcrud = new HospitalCRUD();
                                h.setIdHospital(lista2.get(i).getIdHospital());
                                h = hcrud.readOne1(h);

                                rm = new RankeoMedicos();
                                rmcrud = new RankeoMedicosCRUD();
                                rm.setIdHospital(lista2.get(i).getIdHospital());
                                rm.setIdMedico(lista2.get(i).getIdMedicos());
                                ranking = rmcrud.promedio(rm);
                                out.println("<div class='container'>");
                                out.println("<div class='panel-group'>");
                                out.println("<div class='panel panel-default'>");
                                out.println("<div class='panel-body'>");

                                out.println("<div class='col-md-3 sidenav' align = 'center'>");
                                out.println("<img src = '/Prototipo4_1/Imagenes/doctor.png' width= '150px'>");
                                out.println("</div>");
                                out.println("<div class='col-md-6 text-left'>"); 
                                
                                out.println("<form action='/Prototipo4_1/Paginas/InformacionEspecialistas.jsp' method ='post'>");
                                out.println("<input type ='hidden' name='idhospital' value ='"+ h.getIdHospital() +"'>");
                                out.println("<input type ='hidden' name='idusuario' value ='"+id+"'>");
                                out.println("<input type ='hidden' name='idmedico' value ='"+lista2.get(i).getIdMedicos()+"'>");
                                out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                out.println("<h2 style='color:#1073c5' id='letras'>"+lista2.get(i).getNombreMedico() + " " + lista2.get(i).getAPaternoMedico() + " " + lista2.get(i).getAMaternoMedico()+"</h2>");
                                out.println("</button></form>");

                                out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                out.println("<input type ='hidden' name='id' value ='"+h.getIdHospital()+"'>");
                                out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                out.println("<h7 style='color:#1073c5' id='letras'>"+h.getNombre()+"</h7>");
                                out.println("</button></form>");
                            
                                out.println("<img src = '/Prototipo4_1/Imagenes/"+ranking+"estrellas.png' width = '125px'><br><br>");

                                out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                out.println("Dirección: <br>");
                                out.println(h.getDireccion() + "<br>");
                                out.println("Teléfono : " + lista2.get(i).getTelefonos());

                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                            }
                        }
                    }
                    else{
                        out.println("<div class='container'>");
                        out.println("<h2> No se encontraron resultados </h2>");
                        out.println("</div>");
                    }
                }
            }
        
            
        
            ///////////////////////////Filtro por rankeo, especialidad, servicios y cercania//////////////////////////////
            if (!"".equals(cercania) && !"".equals(rankeo) && !"".equals(especialidad) && !"".equals(servicios)) {
                sm = new ServiciosMedicos();
                smcrud = new ServiciosMedicosCRUD();
                r = new Rankeo();
                rcrud = new RankeoCRUD();
                m = new Medicos();
                mcrud = new MedicosCRUD();
                sm.setTipo(servicios);
                lista3 = smcrud.seleccionar_tipo_servicio(sm);
                rankeo_entero = Integer.parseInt(rankeo);
                Integer promedio = 0;
                pcrud = new PacienteDAO();
                p = pcrud.seleccionar_informacion(id);
                boolean n = false;
                int contador10=0;
                if("Si".equals(cercania)){
                    if (lista3 != null) {
                        idh = new int[lista3.size()];
                        contador = 0; contador1 = 0;
                        contador2 = 0; contador3 = 0;
                        contador4 = 0; contador5 = 0;

                        for (int i = 0; i < lista3.size(); i++) {
                            rcrud = new RankeoCRUD();
                            promedio = rcrud.promedio(lista3.get(i).getIdHospital());
                            if (promedio == rankeo_entero){
                                idh[contador] = lista3.get(i).getIdHospital();
                                contador++;
                            }
                        }
                        if (contador != 0) {
                            for (int i = 0; i < contador; i++) {
                                h = new Hospitales();
                                hcrud = new HospitalCRUD();
                                h.setIdHospital(idh[i]);
                                h = hcrud.readOne1(h);
                                String delegacion = h.getDireccion().substring(h.getDireccion().indexOf(","), h.getDireccion().length());
                                if(delegacion.contains(p.getDelegacion())){
                                    if(n == false){
                                        out.println("<div class='container'>");
                                        out.println("<h2> Alcaldía: "+p.getDelegacion()+"</h2>");
                                        out.println("</div>");
                                        out.println("<div class='container'>");
                                        out.println("<h2> Coincidencia: " +servicios+", "+especialidad+", calificación : "+promedio+"</h2>");
                                        out.println("</div>");
                                        n = true;
                                    }
                                    out.println("<div class='container'>");
                                    out.println("<div class='panel-group'>");
                                    out.println("<div class='panel panel-default'>");
                                    out.println("<div class='panel-body'>");

                                    out.println("<div class='col-lg-3 sidenav' align = 'center'>");
                                    out.println("<img src = '" + h.getFoto() + "' width= '225px'><br>");
                                    out.println("</div>");
                                    out.println("<div class='col-lg-6 text-left'>"); 
                                    out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                    out.println("<input type ='hidden' name='id' value ='"+h.getIdHospital()+"'>");
                                    out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                    out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                    out.println("<h2 style='color:#1073c5' id='letras'>"+ h.getNombre() +"</h2>");
                                    out.println("</button></form>");
                        
                                    out.println("<img src = 'Imagenes/" + rankeo_entero + "estrellas.png' width = '125px'><br><br>");
                                    out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                    out.println("Dirección: <br>");
                                    out.println(h.getDireccion() + "<br>");
                                    out.println("Teléfono : " + h.getTelefono());

                                    out.println("</div>");
                                    out.println("<div class='col-lg-2 sidenav' align='center'><br>");
                                    out.println("<iframe width='250' height='150' "
                                            + "src='https://maps.google.com/maps?&hl=es&q="
                                            + (h.getNombre())
                                            + "&ie=UTF8&t=&z=12&iwloc=B&output=embed' "
                                            + "frameborder='0' scrolling='no' marginheight='0' marginwidth='0'></iframe>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    contador10++;
                                }
                            }
                            if(contador10 == 0){
                                for (int i = 0; i < contador; i++) {
                                    h = new Hospitales();
                                    hcrud = new HospitalCRUD();
                                    h.setIdHospital(idh[i]);
                                    h = hcrud.readOne1(h);
                                    if(n == false){
                                        out.println("<div class='container'>");
                                        out.println("<h2> Sugerencias</h2>");
                                        out.println("</div>");
                                        out.println("<div class='container'>");
                                        out.println("<h2> Coincidencia: " +servicios+", "+especialidad+", calificación : "+rankeo_entero+"</h2>");
                                        out.println("</div>");
                                        n = true;
                                    }
                                    out.println("<div class='container'>");
                                    out.println("<div class='panel-group'>");
                                    out.println("<div class='panel panel-default'>");
                                    out.println("<div class='panel-body'>");

                                    out.println("<div class='col-lg-3 sidenav' align = 'center'>");
                                    out.println("<img src = '" + h.getFoto() + "' width= '225px'><br>");
                                    out.println("</div>");
                                    out.println("<div class='col-lg-6 text-left'>"); 
                                    out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                    out.println("<input type ='hidden' name='id' value ='"+h.getIdHospital()+"'>");
                                    out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                    out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                    out.println("<h2 style='color:#1073c5' id='letras'>"+ h.getNombre() +"</h2>");
                                    out.println("</button></form>");

                                    out.println("<img src = 'Imagenes/" + rankeo_entero + "estrellas.png' width = '125px'><br><br>");
                                    out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                    out.println("Dirección: <br>");
                                    out.println(h.getDireccion() + "<br>");
                                    out.println("Teléfono : " + h.getTelefono());

                                    out.println("</div>");
                                    out.println("<div class='col-lg-2 sidenav' align='center'><br>");
                                    out.println("<iframe width='250' height='150' "
                                            + "src='https://maps.google.com/maps?&hl=es&q="
                                            + (h.getNombre())
                                            + "&ie=UTF8&t=&z=12&iwloc=B&output=embed' "
                                            + "frameborder='0' scrolling='no' marginheight='0' marginwidth='0'></iframe>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                }
                            }
                            n=false;
                        }
                        
                        //Si no hay datos exactos requeridos por el usuario se hacen otras busquedas
                        else if(contador == 0){
                            //Buscar servicio médico con el ranking deseado
                            idh = new int[lista3.size()];
                            contador = 0;
                            for (int i = 0; i < lista3.size(); i++) {
                                promedio = rcrud.promedio(lista3.get(i).getIdHospital());
                                if (promedio == rankeo_entero){
                                    idh[contador] = lista3.get(i).getIdHospital();
                                    contador++;
                                }
                            }
                            if (contador != 0) {
                                for (int i = 0; i < contador; i++) {
                                    h = new Hospitales();
                                    hcrud = new HospitalCRUD();
                                    h.setIdHospital(idh[i]);
                                    h = hcrud.readOne1(h);
                                    String delegacion = h.getDireccion().substring(h.getDireccion().indexOf(","), h.getDireccion().length());
                                    if(h != null && delegacion.contains(p.getDelegacion())){
                                        if(n == false){
                                            out.println("<div class='container'>");
                                            out.println("<h2>Sugerencias:</h2>");
                                            out.println("</div>");

                                            out.println("<div class='container'>");
                                            out.println("<h2> Alcaldía: "+p.getDelegacion()+"</h2>");
                                            out.println("</div>");
                                            out.println("<div class='container'>");
                                            out.println("<h2> Coincidencia en: " +servicios+", calificación: "+promedio+"</h2>");
                                            out.println("</div>");
                                            n = true;
                                        }
                                        out.println("<div class='container'>");
                                        out.println("<div class='panel-group'>");
                                        out.println("<div class='panel panel-default'>");
                                        out.println("<div class='panel-body'>");

                                        out.println("<div class='col-lg-3 sidenav' align = 'center'>");
                                        out.println("<img src = '" + h.getFoto() + "' width= '225px'><br>");
                                        out.println("</div>");
                                        out.println("<div class='col-lg-6 text-left'>"); 
                                        out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                        out.println("<input type ='hidden' name='id' value ='"+h.getIdHospital()+"'>");
                                        out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                        out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                        out.println("<h2 style='color:#1073c5' id='letras'>"+ h.getNombre() +"</h2>");
                                        out.println("</button></form>");
                        
                                        out.println("<img src = 'Imagenes/" + rankeo_entero + "estrellas.png' width = '125px'><br><br>");
                                        out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                        out.println("Dirección: <br>");
                                        out.println(h.getDireccion() + "<br>");
                                        out.println("Teléfono : " + h.getTelefono());

                                        out.println("</div>");
                                        out.println("<div class='col-lg-2 sidenav' align='center'><br>");
                                        out.println("<iframe width='250' height='150' "
                                                + "src='https://maps.google.com/maps?&hl=es&q="
                                                + (h.getNombre())
                                                + "&ie=UTF8&t=&z=12&iwloc=B&output=embed' "
                                                + "frameborder='0' scrolling='no' marginheight='0' marginwidth='0'></iframe>");
                                        out.println("</div>");
                                        out.println("</div>");
                                        out.println("</div>");
                                        out.println("</div>");
                                        out.println("</div>");
                                    }
                                }n=false;
                            }

                            /*Si no se encuentra el servicio médico con el ranking deseado, se busca
                              el mismo servicio pero con diferente rankeo*/ 
                            else if(contador == 0){
                                for (int i = 0; i < lista3.size(); i++) {
                                    h = new Hospitales();
                                    hcrud = new HospitalCRUD();
                                    h.setIdHospital(lista3.get(i).getIdHospital());
                                    h = hcrud.readOne1(h);
                                    promedio = rcrud.promedio(lista3.get(i).getIdHospital());
                                    if(h != null){
                                        if(n == false){
                                            out.println("<div class='container'>");
                                            out.println("<h2>Sugerencias:</h2>");
                                            out.println("</div>");
                                            
                                            out.println("<div class='container'>");
                                            out.println("<h2> Servicio médico: " +servicios+"</h2>");
                                            out.println("</div>");
                                            n = true;
                                        }
                                        
                                        out.println("<div class='container'>");
                                        out.println("<div class='panel-group'>");
                                        out.println("<div class='panel panel-default'>");
                                        out.println("<div class='panel-body'>");

                                        out.println("<div class='col-lg-3 sidenav' align = 'center'>");
                                        out.println("<img src = '" + h.getFoto() + "' width= '225px'><br>");
                                        out.println("</div>");
                                        out.println("<div class='col-lg-6 text-left'>"); 
                                        out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                        out.println("<input type ='hidden' name='id' value ='"+h.getIdHospital()+"'>");
                                        out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                        out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                        out.println("<h2 style='color:#1073c5' id='letras'>"+ h.getNombre() +"</h2>");
                                        out.println("</button></form>");
                        
                                        out.println("<img src = 'Imagenes/" + promedio + "estrellas.png' width = '125px'><br><br>");
                                        out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                        out.println("Dirección: <br>");
                                        out.println(h.getDireccion() + "<br>");
                                        out.println("Teléfono : " + h.getTelefono());

                                        out.println("</div>");
                                        out.println("<div class='col-lg-2 sidenav' align='center'><br>");
                                        out.println("<iframe width='250' height='150' "
                                                + "src='https://maps.google.com/maps?&hl=es&q="
                                                + (h.getNombre())
                                                + "&ie=UTF8&t=&z=12&iwloc=B&output=embed' "
                                                + "frameborder='0' scrolling='no' marginheight='0' marginwidth='0'></iframe>");
                                        out.println("</div>");
                                        out.println("</div>");
                                        out.println("</div>");
                                        out.println("</div>");
                                        out.println("</div>");
                                    }
                                }
                                n = false;

                                //Ahora se busca la especialidad con el ranking deseado     
                            rm = new RankeoMedicos();
                            rmcrud = new RankeoMedicosCRUD();
                            m.setEspecialidades(especialidad);
                            lista2 = mcrud.Hospitales_especialidad(m);
                            if(lista2 != null){
                                idh = new int[lista2.size()];
                                idm = new int[lista2.size()];
                                contador = 0;
                                for(int i = 0; i < lista2.size();i++){
                                    rm.setIdHospital(lista2.get(i).getIdHospital());
                                    rm.setIdMedico(lista2.get(i).getIdMedicos());
                                    promedio = rmcrud.promedio(rm);

                                    if(promedio == rankeo_entero){
                                        idh[contador] = lista2.get(i).getIdHospital();
                                        idm[contador] = lista2.get(i).getIdMedicos();
                                        contador++;
                                    }
                                }
                                if(contador != 0){
                                    int contadoraux = 0;
                                    for(int i = 0; i < contador; i++){
                                        h = new Hospitales();
                                        hcrud = new HospitalCRUD();
                                        h.setIdHospital(idh[i]);
                                        h = hcrud.readOne1(h);

                                        m.setIdMedicos(idm[i]);
                                        m = (Medicos)mcrud.readMedicos(m).get(0);
                                        rm.setIdHospital(idh[i]);
                                        rm.setIdMedico(idm[i]);
                                        promedio = rmcrud.promedio(rm);
                                        String delegacion = h.getDireccion().substring(h.getDireccion().indexOf(","), h.getDireccion().length());
                                        if(h != null && delegacion.contains(p.getDelegacion())){
                                            if(n == false){
                                                out.println("<div class='container'>");
                                                out.println("<h2>Sugerencias:</h2>");
                                                out.println("</div>");
                                                out.println("<div class='container'>");
                                                out.println("<h2>Alcaldía:"+p.getDelegacion()+"</h2>");
                                                out.println("</div>");
                                                    
                                                out.println("<div class='container'>");
                                                out.println("<h2> Especialidad médica: " +especialidad+", calificación: "+promedio+"</h2>");
                                                out.println("</div>");
                                                n = true;
                                            }

                                            out.println("<div class='container'>");
                                            out.println("<div class='panel-group'>");
                                            out.println("<div class='panel panel-default'>");
                                            out.println("<div class='panel-body'>");

                                            out.println("<div class='col-md-3 sidenav' align = 'center'>");
                                            out.println("<img src = '/Prototipo4_1/Imagenes/doctor.png' width= '150px'>");
                                            out.println("</div>");
                                            out.println("<div class='col-md-6 text-left'>"); 
                                            
                                            out.println("<form action='/Prototipo4_1/Paginas/InformacionEspecialistas.jsp' method ='post'>");
                                            out.println("<input type ='hidden' name='idhospital' value ='"+ idh[i] +"'>");
                                            out.println("<input type ='hidden' name='idusuario' value ='"+id+"'>");
                                            out.println("<input type ='hidden' name='idmedico' value ='"+idm[i]+"'>");
                                            out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                            out.println("<h2 style='color:#1073c5' id='letras'>"+ m.getNombreMedico() + " " + m.getAPaternoMedico() + " " + m.getAMaternoMedico()+"</h2>");
                                            out.println("</button></form>");

                                            out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                            out.println("<input type ='hidden' name='id' value ='"+h.getIdHospital()+"'>");
                                            out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                            out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                            out.println("<h7 style='color:#1073c5' id='letras'>"+h.getNombre()+"</h7>");
                                            out.println("</button></form>");
                            
                                            out.println("<img src = '/Prototipo4_1/Imagenes/"+rankeo_entero+"estrellas.png' width = '125px'><br><br>");

                                            out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                            out.println("Dirección: <br>");
                                            out.println(h.getDireccion() + "<br>");
                                            out.println("Teléfono : " + m.getTelefonos());

                                            out.println("</div>");
                                            out.println("</div>");
                                            out.println("</div>");
                                            out.println("</div>");
                                            out.println("</div>");
                                            contadoraux++;
                                        }
                                    }n=false;
                                    if(contadoraux == 0){
                                        for(int i = 0; i < contador; i++){
                                        h = new Hospitales();
                                        hcrud = new HospitalCRUD();
                                        h.setIdHospital(idh[i]);
                                        h = hcrud.readOne1(h);

                                        m.setIdMedicos(idm[i]);
                                        m = (Medicos)mcrud.readMedicos(m).get(0);
                                        rm.setIdHospital(idh[i]);
                                        rm.setIdMedico(idm[i]);
                                        promedio = rmcrud.promedio(rm);
                                        String delegacion = h.getDireccion().substring(h.getDireccion().indexOf(","), h.getDireccion().length());
                                        if(h != null ){
                                            if(n == false){
                                                out.println("<div class='container'>");
                                                out.println("<h2>Sugerencias:</h2>");
                                                out.println("</div>");

                                                out.println("<div class='container'>");
                                                out.println("<h2> Especialidad médica: " +especialidad+"</h2>");
                                                out.println("</div>");
                                                n = true;
                                            }
                                            out.println("<div class='container'>");
                                            out.println("<div class='panel-group'>");
                                            out.println("<div class='panel panel-default'>");
                                            out.println("<div class='panel-body'>");

                                            out.println("<div class='col-md-3 sidenav' align = 'center'>");
                                            out.println("<img src = '/Prototipo4_1/Imagenes/doctor.png' width= '150px'>");
                                            out.println("</div>");
                                            out.println("<div class='col-md-6 text-left'>"); 
                                            
                                            out.println("<form action='/Prototipo4_1/Paginas/InformacionEspecialistas.jsp' method ='post'>");
                                            out.println("<input type ='hidden' name='idhospital' value ='"+ idh[i] +"'>");
                                            out.println("<input type ='hidden' name='idusuario' value ='"+id+"'>");
                                            out.println("<input type ='hidden' name='idmedico' value ='"+idm[i]+"'>");
                                            out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                            out.println("<h2 style='color:#1073c5' id='letras'>"+ m.getNombreMedico() + " " + m.getAPaternoMedico() + " " + m.getAMaternoMedico()+"</h2>");
                                            out.println("</button></form>");

                                            out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                            out.println("<input type ='hidden' name='id' value ='"+h.getIdHospital()+"'>");
                                            out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                            out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                            out.println("<h7 style='color:#1073c5' id='letras'>"+h.getNombre()+"</h7>");
                                            out.println("</button></form>");
                            
                                            out.println("<img src = '/Prototipo4_1/Imagenes/"+rankeo_entero+"estrellas.png' width = '125px'><br><br>");

                                            out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                            out.println("Dirección: <br>");
                                            out.println(h.getDireccion() + "<br>");
                                            out.println("Teléfono : " + m.getTelefonos());

                                            out.println("</div>");
                                            out.println("</div>");
                                            out.println("</div>");
                                            out.println("</div>");
                                            out.println("</div>");
                                        }
                                    }
                                }n=false;}
                                //Agregado recomendación especialidad
                                else if(contador == 0){
                                    for(int i = 0; i < lista2.size();i++){
                                        h = new Hospitales();
                                        hcrud = new HospitalCRUD();
                                        h.setIdHospital(lista2.get(i).getIdHospital());
                                        h = hcrud.readOne1(h);

                                        rm = new RankeoMedicos();
                                        rmcrud = new RankeoMedicosCRUD();
                                        rm.setIdHospital(lista2.get(i).getIdHospital());
                                        rm.setIdMedico(lista2.get(i).getIdMedicos());
                                        ranking = rmcrud.promedio(rm);
                                        String delegacion = h.getDireccion().substring(h.getDireccion().indexOf(","), h.getDireccion().length());
                                        if(h != null && delegacion.contains(p.getDelegacion())){
                                            if(n == false){
                                                out.println("<div class='container'>");
                                                out.println("<h2>Sugerencias:</h2>");
                                                out.println("</div>");
                                                out.println("<div class='container'>");
                                                out.println("<h2>Alcaldía:"+p.getDelegacion()+"</h2>");
                                                out.println("</div>");
                                                out.println("<div class='container'>");
                                                out.println("<h2> Especialidad médica: " +especialidad+"</h2>");
                                                out.println("</div>");
                                                n = true;
                                            }
                                            out.println("<div class='container'>");
                                            out.println("<div class='panel-group'>");
                                            out.println("<div class='panel panel-default'>");
                                            out.println("<div class='panel-body'>");

                                            out.println("<div class='col-md-3 sidenav' align = 'center'>");
                                            out.println("<img src = '/Prototipo4_1/Imagenes/doctor.png' width= '150px'>");
                                            out.println("</div>");
                                            out.println("<div class='col-md-6 text-left'>"); 
                                            out.println("<form action='/Prototipo4_1/Paginas/InformacionEspecialistas.jsp' method ='post'>");
                                            out.println("<input type ='hidden' name='idhospital' value ='"+ h.getIdHospital() +"'>");
                                            out.println("<input type ='hidden' name='idusuario' value ='"+id+"'>");
                                            out.println("<input type ='hidden' name='idmedico' value ='"+lista2.get(i).getIdMedicos()+"'>");
                                            out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                            out.println("<h2 style='color:#1073c5' id='letras'>"+ lista2.get(i).getNombreMedico() + " " + lista2.get(i).getAPaternoMedico() + " " + lista2.get(i).getAMaternoMedico()+"</h2>");
                                            out.println("</button></form>");

                                            out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                            out.println("<input type ='hidden' name='id' value ='"+h.getIdHospital()+"'>");
                                            out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                            out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                            out.println("<h7 style='color:#1073c5' id='letras'>"+h.getNombre()+"</h7>");
                                            out.println("</button></form>");
                            
                                            out.println("<img src = '/Prototipo4_1/Imagenes/"+ranking+"estrellas.png' width = '125px'><br><br>");

                                            out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                            out.println("Dirección: <br>");
                                            out.println(h.getDireccion() + "<br>");
                                            out.println("Teléfono : " + lista2.get(i).getTelefonos());

                                            out.println("</div>");
                                            out.println("</div>");
                                            out.println("</div>");
                                            out.println("</div>");
                                            out.println("</div>");
                                            }
                                        }
                                    }
                                }
                            } 
                        }
                    }
                    else{
                        out.println("<div class='container'>");
                        out.println("<h2> No se encontraron resultados </h2>");
                        out.println("</div>");
                    }
                }else{
                    if (lista3 != null) {
                        idh = new int[lista3.size()];
                        contador = 0; contador1 = 0;
                        contador2 = 0; contador3 = 0;
                        contador4 = 0; contador5 = 0;
                        
                        for (int i = 0; i < lista3.size(); i++) {
                            rcrud = new RankeoCRUD();
                            promedio = rcrud.promedio(lista3.get(i).getIdHospital());
                            if (promedio == rankeo_entero){
                                idh[contador] = lista3.get(i).getIdHospital();
                                contador++;
                            }
                        }
                        if (contador != 0) {
                            for (int i = 0; i < contador; i++) {
                                h = new Hospitales();
                                hcrud = new HospitalCRUD();
                                h.setIdHospital(idh[i]);
                                h = hcrud.readOne1(h);
                                if(h != null){
                                    if(n == false){
                                        out.println("<div class='container'>");
                                        out.println("<h2> Coincidencias: " +servicios+", "+especialidad+" y calificaicón: "+promedio+"</h2>");
                                        out.println("</div>");
                                        n = true;
                                    }

                                    out.println("<div class='container'>");
                                    out.println("<div class='panel-group'>");
                                    out.println("<div class='panel panel-default'>");
                                    out.println("<div class='panel-body'>");

                                    out.println("<div class='col-lg-3 sidenav' align = 'center'>");
                                    out.println("<img src = '" + h.getFoto() + "' width= '225px'><br>");
                                    out.println("</div>");
                                    out.println("<div class='col-lg-6 text-left'>"); 
                                    out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                    out.println("<input type ='hidden' name='id' value ='"+h.getIdHospital()+"'>");
                                    out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                    out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                    out.println("<h2 style='color:#1073c5' id='letras'>"+ h.getNombre() +"</h2>");
                                    out.println("</button></form>");
                        
                                    out.println("<img src = 'Imagenes/" + rankeo_entero + "estrellas.png' width = '125px'><br><br>");
                                    out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                    out.println("Dirección: <br>");
                                    out.println(h.getDireccion() + "<br>");
                                    out.println("Teléfono : " + h.getTelefono());

                                    out.println("</div>");
                                    out.println("<div class='col-lg-2 sidenav' align='center'><br>");
                                    out.println("<iframe width='250' height='150' "
                                            + "src='https://maps.google.com/maps?&hl=es&q="
                                            + (h.getNombre())
                                            + "&ie=UTF8&t=&z=12&iwloc=B&output=embed' "
                                            + "frameborder='0' scrolling='no' marginheight='0' marginwidth='0'></iframe>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</div>");
                                }
                            }
                        n = false;
                        }
                        //Si no hay datos exactos requeridos por el usuario se hacen otras busquedas
                        else if(contador == 0){
                            //Buscar servicio médico con el ranking deseado
                            idh = new int[lista3.size()];
                            contador = 0;
                            for (int i = 0; i < lista3.size(); i++) {
                                promedio = rcrud.promedio(lista3.get(i).getIdHospital());
                                if (promedio == rankeo_entero){
                                    idh[contador] = lista3.get(i).getIdHospital();
                                    contador++;
                                }
                            }
                            if (contador != 0) {
                                for (int i = 0; i < contador; i++) {
                                    h = new Hospitales();
                                    hcrud = new HospitalCRUD();
                                    h.setIdHospital(idh[i]);
                                    h = hcrud.readOne1(h);
                                    if(h != null){
                                        if(n == false){
                                            out.println("<div class='container'>");
                                            out.println("<h2> Sugerencias </h2>");
                                            out.println("</div>");
                                            
                                            out.println("<div class='container'>");
                                            out.println("<h2> Coincidencia en: " +servicios+", "+especialidad+" y calificación: "+promedio+"</h2>");
                                            out.println("</div>");
                                            n = true;
                                        }

                                        out.println("<div class='container'>");
                                        out.println("<div class='panel-group'>");
                                        out.println("<div class='panel panel-default'>");
                                        out.println("<div class='panel-body'>");

                                        out.println("<div class='col-lg-3 sidenav' align = 'center'>");
                                        out.println("<img src = '" + h.getFoto() + "' width= '225px'><br>");
                                        out.println("</div>");
                                        out.println("<div class='col-lg-6 text-left'>"); 
                                        out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                        out.println("<input type ='hidden' name='id' value ='"+h.getIdHospital()+"'>");
                                        out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                        out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                        out.println("<h2 style='color:#1073c5' id='letras'>"+ h.getNombre() +"</h2>");
                                        out.println("</button></form>");
                        
                                        out.println("<img src = 'Imagenes/" + rankeo_entero + "estrellas.png' width = '125px'><br><br>");
                                        out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                        out.println("Dirección: <br>");
                                        out.println(h.getDireccion() + "<br>");
                                        out.println("Teléfono : " + h.getTelefono());

                                        out.println("</div>");
                                        out.println("<div class='col-lg-2 sidenav' align='center'><br>");
                                        out.println("<iframe width='250' height='150' "
                                                + "src='https://maps.google.com/maps?&hl=es&q="
                                                + (h.getNombre())
                                                + "&ie=UTF8&t=&z=12&iwloc=B&output=embed' "
                                                + "frameborder='0' scrolling='no' marginheight='0' marginwidth='0'></iframe>");
                                        out.println("</div>");
                                        out.println("</div>");
                                        out.println("</div>");
                                        out.println("</div>");
                                        out.println("</div>");
                                    }
                                }n = false;
                            }
                            /*Si no se encuentra el servicio médico con el ranking deseado, se busca
                              el mismo servicio pero con diferente rankeo*/ 
                            else if(contador == 0){
                                for (int i = 0; i < lista3.size(); i++) {
                                    h = new Hospitales();
                                    hcrud = new HospitalCRUD();
                                    h.setIdHospital(lista3.get(i).getIdHospital());
                                    h = hcrud.readOne1(h);
                                    promedio = rcrud.promedio(lista3.get(i).getIdHospital());
                                    if(h != null){
                                        if(n == false){
                                            out.println("<div class='container'>");
                                            out.println("<h2> Sugerencias </h2>");
                                            out.println("</div>");
                                            
                                            out.println("<div class='container'>");
                                            out.println("<h2> Coincidencia en: " +servicios+", "+especialidad+"</h2>");
                                            out.println("</div>");
                                            n = true;
                                        }
                                        out.println("<div class='container'>");
                                        out.println("<div class='panel-group'>");
                                        out.println("<div class='panel panel-default'>");
                                        out.println("<div class='panel-body'>");

                                        out.println("<div class='col-lg-3 sidenav' align = 'center'>");
                                        out.println("<img src = '" + h.getFoto() + "' width= '225px'><br>");
                                        out.println("</div>");
                                        out.println("<div class='col-lg-6 text-left'>"); 
                                        out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                        out.println("<input type ='hidden' name='id' value ='"+h.getIdHospital()+"'>");
                                        out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                        out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                        out.println("<h2 style='color:#1073c5' id='letras'>"+ h.getNombre() +"</h2>");
                                        out.println("</button></form>");
                        
                                        out.println("<img src = 'Imagenes/" + promedio + "estrellas.png' width = '125px'><br><br>");
                                        out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                        out.println("Dirección: <br>");
                                        out.println(h.getDireccion() + "<br>");
                                        out.println("Teléfono : " + h.getTelefono());

                                        out.println("</div>");
                                        out.println("<div class='col-lg-2 sidenav' align='center'><br>");
                                        out.println("<iframe width='250' height='150' "
                                                + "src='https://maps.google.com/maps?&hl=es&q="
                                                + (h.getNombre())
                                                + "&ie=UTF8&t=&z=12&iwloc=B&output=embed' "
                                                + "frameborder='0' scrolling='no' marginheight='0' marginwidth='0'></iframe>");
                                        out.println("</div>");
                                        out.println("</div>");
                                        out.println("</div>");
                                        out.println("</div>");
                                        out.println("</div>");
                                    }
                                }
                                n = false;
                                //Ahora se busca la especialidad con el ranking deseado     
                            rm = new RankeoMedicos();
                            rmcrud = new RankeoMedicosCRUD();
                            m.setEspecialidades(especialidad);
                            lista2 = mcrud.Hospitales_especialidad(m);
                            if(lista2 != null){
                                idh = new int[lista2.size()];
                                idm = new int[lista2.size()];
                                contador = 0;
                                for(int i = 0; i < lista2.size();i++){
                                    rm.setIdHospital(lista2.get(i).getIdHospital());
                                    rm.setIdMedico(lista2.get(i).getIdMedicos());
                                    promedio = rmcrud.promedio(rm);

                                    if(promedio == rankeo_entero){
                                        idh[contador] = lista2.get(i).getIdHospital();
                                        idm[contador] = lista2.get(i).getIdMedicos();
                                        contador++;
                                    }
                                }
                                if(contador != 0){
                                    for(int i = 0; i < contador; i++){
                                        h = new Hospitales();
                                        hcrud = new HospitalCRUD();
                                        h.setIdHospital(idh[i]);
                                        h = hcrud.readOne1(h);

                                        m.setIdMedicos(idm[i]);
                                        m = (Medicos)mcrud.readMedicos(m).get(0);
                                        rm.setIdHospital(idh[i]);
                                        rm.setIdMedico(idm[i]);
                                        promedio = rmcrud.promedio(rm);
                                        if(n == false){
                                            out.println("<div class='container'>");
                                            out.println("<h2> Sugerencias </h2>");
                                            out.println("</div>");
                                            
                                            out.println("<div class='container'>");
                                            out.println("<h2> Coincidencia en: "+especialidad+" y calificación : "+promedio+"</h2>");
                                            out.println("</div>");
                                            n = true;
                                        }
                                        

                                        out.println("<div class='container'>");
                                        out.println("<div class='panel-group'>");
                                        out.println("<div class='panel panel-default'>");
                                        out.println("<div class='panel-body'>");

                                        out.println("<div class='col-md-3 sidenav' align = 'center'>");
                                        out.println("<img src = '/Prototipo4_1/Imagenes/doctor.png' width= '150px'>");
                                        out.println("</div>");
                                        out.println("<div class='col-md-6 text-left'>"); 
                                        
                                        out.println("<form action='/Prototipo4_1/Paginas/InformacionEspecialistas.jsp' method ='post'>");
                                        out.println("<input type ='hidden' name='idhospital' value ='"+ idh[i] +"'>");
                                        out.println("<input type ='hidden' name='idusuario' value ='"+id+"'>");
                                        out.println("<input type ='hidden' name='idmedico' value ='"+idm[i]+"'>");
                                        out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                        out.println("<h2 style='color:#1073c5' id='letras'>"+ m.getNombreMedico() + " " + m.getAPaternoMedico() + " " + m.getAMaternoMedico()+"</h2>");
                                        out.println("</button></form>");

                                        out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                        out.println("<input type ='hidden' name='id' value ='"+h.getIdHospital()+"'>");
                                        out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                        out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                        out.println("<h7 style='color:#1073c5' id='letras'>"+h.getNombre()+"</h7>");
                                        out.println("</button></form>");
                            
                                        out.println("<img src = '/Prototipo4_1/Imagenes/"+rankeo_entero+"estrellas.png' width = '125px'><br><br>");

                                        out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                        out.println("Dirección: <br>");
                                        out.println(h.getDireccion() + "<br>");
                                        out.println("Teléfono : " + m.getTelefonos());

                                        out.println("</div>");
                                        out.println("</div>");
                                        out.println("</div>");
                                        out.println("</div>");
                                        out.println("</div>");
                                    }
                                    n = false;
                                }
                                
                                //Agregado recomendación especialidad
                                else if(contador == 0){
                                    for(int i = 0; i < lista2.size();i++){
                                        h = new Hospitales();
                                        hcrud = new HospitalCRUD();
                                        h.setIdHospital(lista2.get(i).getIdHospital());
                                        h = hcrud.readOne1(h);

                                        rm = new RankeoMedicos();
                                        rmcrud = new RankeoMedicosCRUD();
                                        rm.setIdHospital(lista2.get(i).getIdHospital());
                                        rm.setIdMedico(lista2.get(i).getIdMedicos());
                                        ranking = rmcrud.promedio(rm);

                                        if(n == false){
                                            out.println("<div class='container'>");
                                            out.println("<h2> Sugerencias </h2>");
                                            out.println("</div>");
                                            
                                            out.println("<div class='container'>");
                                            out.println("<h2> Coincidencia en: "+especialidad+"</h2>");
                                            out.println("</div>");
                                            n = true;
                                        }
                                        
                                        out.println("<div class='container'>");
                                        out.println("<div class='panel-group'>");
                                        out.println("<div class='panel panel-default'>");
                                        out.println("<div class='panel-body'>");

                                        out.println("<div class='col-md-3 sidenav' align = 'center'>");
                                        out.println("<img src = '/Prototipo4_1/Imagenes/doctor.png' width= '150px'>");
                                        out.println("</div>");
                                        out.println("<div class='col-md-6 text-left'>"); 
                                        
                                        out.println("<form action='/Prototipo4_1/Paginas/InformacionEspecialistas.jsp' method ='post'>");
                                        out.println("<input type ='hidden' name='idhospital' value ='"+ h.getIdHospital() +"'>");
                                        out.println("<input type ='hidden' name='idusuario' value ='"+id+"'>");
                                        out.println("<input type ='hidden' name='idmedico' value ='"+lista2.get(i).getIdMedicos()+"'>");
                                        out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                        out.println("<h2 style='color:#1073c5' id='letras'>"+ lista2.get(i).getNombreMedico() + " " + lista2.get(i).getAPaternoMedico() + " " + lista2.get(i).getAMaternoMedico()+"</h2>");
                                        out.println("</button></form>");

                                        out.println("<form action='/Prototipo4_1/Paginas/Informacion_Hospital.jsp' method ='post'>");
                                        out.println("<input type ='hidden' name='id' value ='"+h.getIdHospital()+"'>");
                                        out.println("<input type ='hidden' name='usuario' value ='"+id+"'>");
                                        out.println("<button style='background-color:transparent; border:none;' role='link' width='10%'>");
                                        out.println("<h7 style='color:#1073c5' id='letras'>"+h.getNombre()+"</h7>");
                                        out.println("</button></form>");
                            
                                        out.println("<img src = '/Prototipo4_1/Imagenes/"+ranking+"estrellas.png' width = '125px'><br><br>");

                                        out.println("<span class='glyphicon glyphicon-map-marker'></span>");
                                        out.println("Dirección: <br>");
                                        out.println(h.getDireccion() + "<br>");
                                        out.println("Teléfono : " + lista2.get(i).getTelefonos());

                                        out.println("</div>");
                                        out.println("</div>");
                                        out.println("</div>");
                                        out.println("</div>");
                                        out.println("</div>");
                                        }
                                    }
                                }
                            } 
                        }
                    }
                    else{
                        out.println("<div class='container'>");
                        out.println("<h2> No se encontraron resultados </h2>");
                        out.println("</div>");
                    }
                }
            }
            out.println("</body>");
            out.println("</html>");
            out.println("<style type='text/css'>");
            out.println("#letras {text-decoration: none;color: #000;}");
            out.println("#letras:hover {text-decoration: underline; color: #000;}");
            out.println("</style>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(BuscarDatos.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(BuscarDatos.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
