<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<!DOCTYPE html>
<html lang="en">

    <head>

        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>T-cuido </title>

        <link href="Bootstrap/vendor/bootstrap/css/bootstrap.min1.css" rel="stylesheet">

        <link href="Bootstrap/vendor/fontawesome-free/css/all.min1.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Varela+Round" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.1.0/css/all.css" integrity="sha384-lKuwvrZot6UHsBSfcMvOkWwlCMgc0TaWr+30HWe3a4ltaBwTZhyTEggF5tJv8tbt" crossorigin="anonymous">
        <link href="Bootstrap/css/grayscale.min.css" rel="stylesheet">
        <script src="Bootstrap/vendor/bootstrap/js/bootstrap.bundle.min1.js"></script>
        <script src="Bootstrap/vendor/jquery-easing/jquery.easing.min1.js"></script>
        <script src="Bootstrap/js/grayscale.min1.js"></script>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
        <link rel ="icon" type ="image/png" href="Imagenes/Tcuido4.png"/>
        
        <style>
            .form-control-borderless {
                border: none;
            }

            .form-control-borderless:hover, .form-control-borderless:active, .form-control-borderless:focus {
                border: none;
                outline: none;
                box-shadow: none;
            }
            header{
                background-blend-mode:darken, luminosity;
                background-color:rgb(50,50,50);
            }
        </style>
    </head>

    <body background="Imagenes/Fondo.jpg">

        <!-- Para la navegacion en la pagina -->
        <nav class="navbar navbar-expand-lg navbar-light fixed-top" style="background-color:#0056b3">
            <div class="container">
                <a class="navbar-brand js-scroll-trigger" href="#page-top">
                    <img src="Imagenes/Tcuido2.png" width="55px">
                </a>
                <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
                    <i class="fas fa-bars" style='color:white;'></i>
                </button>
                <div class="collapse navbar-collapse" id="navbarResponsive" align='center'>
                    <ul class="navbar-nav text-uppercase ml-auto">
                        <li class="nav-item">
                            <a class="nav-link js-scroll-trigger" style="color:white" href="#about">Objetivo</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link js-scroll-trigger" style="color:white" href="#projects">Acerca de</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link js-scroll-trigger" style="color:white" href="#signup">Contacto</a>
                        </li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown" style='color:white'>
                                Más..
                            </a>
                            <div class="dropdown-menu dropdown-menu-right">
                                <a class="dropdown-item" href="Paginas/LoginAdministrador.jsp" style='font-size:12px'>Iniciar sesión admón hospital</a>
                                <a class="dropdown-item" href="Paginas/LoginAdministradorSistema.jsp"style='font-size:12px'>Iniciar sesión admón sistema</a>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>


        <!-- Pantalla principal -->
        <header class="masthead" style = "background: linear-gradient(to bottom, rgba(22, 22, 22, 0.3) 0%, rgba(22, 22, 22, 0.7) 75%, #161616 100%), url('Bootstrap/img/Fondo.jpg');
            background-position: center;
            background-repeat: no-repeat;
            background-attachment: scroll;
            background-size: cover;">
            <div class="align-items-center" style="padding-top:150px">
                <div class="mx-auto text-center">
                    <h1 class="mx-auto my-0 text-uppercase">T-cuido</h1>
                    <h2 class="text-white-50 mx-auto mt-2 mb-5">Prototipo de sistema web para la búsqueda de hospitales particulares en la CDMX.</h2>
                    <div class="row justify-content-center">
                        <div class="col-sm-1">
                        </div>
                        <div class="col-sm-8 ">
                            <form class="card card-sm" action="BuscarDatos" method="post">
                                <div class="card-body row no-gutters align-items-center">
                                    <div class="col-auto">
                                        <img src="Imagenes/lupa.png" width="45px"></i>
                                    </div>
                                    <div class="col">
                                        <input class="form-control form-control-lg form-control-borderless" type="search" placeholder="p. ej. Hospital Ángeles, cardiología, urgencias, Juan Carlos" name = "txtbuscar">
                                        <input type ="hidden" name="id" value ='0'>
                                        <input type ="hidden" name="rankeo" value =''>
                                        <input type ="hidden" name="especialidad" value =''>
                                        <input type ="hidden" name="servicios" value =''>
                                        <input type ="hidden" name="cercania" value =''>
                                    </div>
                                    <div class="col-auto">
                                        <button class="btn btn-lg btn-success" type="submit" style="font-family: Arial;size:7px">Buscar</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="col-sm-2" style="padding-top:15px">
                            <a href="Paginas/LoginPaciente.jsp" class="btn btn-primary js-scroll-trigger" style="background-color:#008B8B">Iniciar sesión como paciente</a>
		        </div>
                    </div>    
                </div>
            </div>
        </header>

        <!-- Objetivo -->
        <section id="about" class="about-section text-center">
            <div class="container">
                <div class="row">
                    <div class="col-lg-8 mx-auto">
                        <h2 class="text-white mb-4">Prototipo de Sistema Web para la Búsqueda de Hospitales Particulares en la CDMX</h2>
                        <p class="text-white-50">Es un buscador que proveé la localización de los hospitales particulares que
                            se encuentran dentro de la Ciudad de México, los servicios médicos que ofrece estos
                            establecimientos así como la información relacionada con los médicos especialistas en cardiología,
                            medicina general, neurología, pediatría y urología registrados.</p>
                    </div>
                </div>
            </div>
        </section>

        <section id="projects" class="projects-section bg-light">
            <div class="container">

                <!-- Acerca de -->

                <div class="row justify-content-center no-gutters mb-5 mb-lg-0">
                    <div class="col-lg-6">
                        <img class="img-fluid" src="Bootstrap/img/demo-image-01.jpg" alt="">
                    </div>
                    <div class="col-lg-6">
                        <div class="bg-black text-center h-100 project">
                            <div class="d-flex h-100">
                                <div class="project-text w-100 my-auto text-center text-lg-left">
                                    <h4 class="text-white">Filtros de búsqueda</h4>
                                    <p class="mb-0 text-white-50">Estos filtros te permitirán obtener la mejor opción de acuerdo a tus necesidades para tu búsqueda del mejor hospital.</p>
                                    <hr class="d-none d-lg-block mb-0 ml-0">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row justify-content-center no-gutters">
                    <div class="col-lg-6">
                        <img class="img-fluid" src="Bootstrap/img/demo-image-02.jpg" alt="">
                    </div>
                    <div class="col-lg-6 order-lg-first">
                        <div class="bg-black text-center h-100 project">
                            <div class="d-flex h-100">
                                <div class="project-text w-100 my-auto text-center text-lg-right">
                                    <h4 class="text-white">Sección de comentarios y evaluación</h4>
                                    <p class="mb-0 text-white-50">Si así lo deseas, puedes calificar y valorar a los médicos especialistas que estén inscritos en el sistema web.</p>
                                    <hr class="d-none d-lg-block mb-0 mr-0">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <!-- Contacto-->
        <section id="signup" class="signup-section">
            <div class="container">
                <div class="row">
                    <div class="col-md-10 col-lg-8 mx-auto text-center">
                    </div>
                </div>
            </div>
        </section>

        <section class="contact-section bg-black">
            <div class="container">

                <div class="row">

                    <div class="col-md-4 mb-3 mb-md-0">
                        <div class="card py-4 h-100">
                            <div class="card-body text-center">
                                <i class="fas fa-map-marked-alt text-primary mb-2"></i>
                                <h4 class="text-uppercase m-0">TT 2018-B021</h4>
                                <hr class="my-4">
                                <div class="small text-black-50">Instituto Politécnico Nacional</div>
                                <div class="small text-black-50">Escuela Superior de Cómputo</div>              
                            </div>
                        </div>
                    </div>

                    <div class="col-md-4 mb-3 mb-md-0">
                        <div class="card py-4 h-100">
                            <div class="card-body text-center">
                                <i class="fas fa-envelope text-primary mb-2"></i>
                                <h4 class="text-uppercase m-0">Correo electrónico</h4>
                                <hr class="my-4">
                                <div class="small text-black-50">
                                    <a href="#">t.cuido.2018b021@gmail.com</a>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-4 mb-3 mb-md-0">
                        <div class="card py-4 h-100">
                            <div class="card-body text-center">
                                <i class="fas fa-mobile-alt text-primary mb-2"></i>
                                <h4 class="text-uppercase m-0">Teléfono</h4>
                                <hr class="my-4">
                                <div class="small text-black-50">773-129-7942</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <footer class="bg-black small text-center text-white-50">
            <div class="container">

            </div>
        </footer>

    </body>

</html>