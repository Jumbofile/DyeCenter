<!DOCTYPE html>
<html lang="en">

<head>

	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<meta name="description" content="">
	<meta name="author" content="">

	<title>Dye Center</title>

	<!-- Bootstrap core CSS -->
	<link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<link rel="apple-touch-icon" sizes="180x180" href="webresources/apple-touch-icon.png">
	<link rel="icon" type="image/png" sizes="32x32" href="webresources/favicon-32x32.png">
	<link rel="icon" type="image/png" sizes="16x16" href="webresources/favicon-16x16.png">
	<link rel="manifest" href="webresources/site.webmanifest">
	<link rel="mask-icon" href="webresources/safari-pinned-tab.svg" color="#5bbad5">
	<meta name="msapplication-TileColor" content="#da532c">
	<meta name="theme-color" content="#ffffff">
	<!-- Custom fonts for this template -->
	<link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
	<link href="https://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css">
	<link href='https://fonts.googleapis.com/css?family=Kaushan+Script' rel='stylesheet' type='text/css'>
	<link href='https://fonts.googleapis.com/css?family=Droid+Serif:400,700,400italic,700italic' rel='stylesheet' type='text/css'>
	<link href='https://fonts.googleapis.com/css?family=Roboto+Slab:400,100,300,700' rel='stylesheet' type='text/css'>

	<!--Stylesheets-->
	<link href="css/agency.css" rel="stylesheet">
	<link href="css/dashboard.css" rel="stylesheet">

	<style>
		/* hide mobile version by default */
		.logo .mobile {
			display: none;
		}
		/* when screen is less than 600px wide
           show mobile version and hide desktop */
		@media (max-width: 600px) {
			.logo .mobile {
				display: block;
			}
			.logo .desktop {
				display: none;
			}
		}
	</style>
</head>

<body id="page-top">
<form action="${pageContext.servletContext.contextPath}/dashboard" method="post">
<!-- Navigation -->
<%--<nav class="navbar navbar-expand-lg navbar-dark fixed-top" id="mainNav">--%>
	<%--<div class="container">--%>
		<%--<a class="navbar-brand js-scroll-trigger" href="#page-top"></a>--%>
		<%--<button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">--%>
			<%--Menu--%>
			<%--<i class="fas fa-bars"></i>--%>
		<%--</button>--%>
		<%--<div class="collapse navbar-collapse" id="navbarResponsive">--%>
			<%--<ul class="navbar-nav text-uppercase ml-auto">--%>
				<%--<li class="nav-item">--%>
					<%--<a class="nav-link js-scroll-trigger" href="#about">Profile</a>--%>
				<%--</li>--%>
				<%--<li class="nav-item">--%>
					<%--<a class="nav-link js-scroll-trigger" href="#team">Team</a>--%>
				<%--</li>--%>
				<%--<li class="nav-item">--%>
					<%--<a class="nav-link js-scroll-trigger" href="#contact">Contact</a>--%>
				<%--</li>--%>
			<%--</ul>--%>
		<%--</div>--%>
	<%--</div>--%>
<%--</nav>--%>

<!-- Page Wrapper -->
<div id="wrapper">

	<!-- Sidebar -->
	<ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

		<!-- Sidebar - Brand -->
		<a class="sidebar-brand d-flex align-items-center justify-content-center" href="">
			<div class="logo">
				<div class="sidebar-brand-icon ">
					<img class="mobile" src="webresources/whiteDye.svg" alt="DyeCenter" height="85">
				</div>
				<div style="margin-left:3px" class="sidebar-brand-text">

					<img class="desktop" src="webresources/dyecenter-white.svg" alt="DyeCenter" height="85">
				</div>
			</div>

		</a>


		<!-- Divider -->
		<hr class="sidebar-divider">

		<!-- Heading -->
		<div class="sidebar-heading">
			Addons
		</div>


		<!-- Nav Item - Charts -->
		<li class="nav-item">
			<a onclick="logout()" class="nav-link" href="index">
				<i class="fas fa-sign-out-alt"></i>
				<span>Logout</span>
            </a>
		</li>

		<!-- Nav Item - Tables -->
		<li onclick="" class="nav-item">
            <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseTables" aria-expanded="true" aria-controls="collapseTables">
                <i class="fas fa-fw fa-table"></i>
                <span>Tables</span>
            </a>
            <div id="collapseTables" class="collapse" aria-labelledby="headingPages" data-parent="#accordionSidebar">
				<div id="tableDropdown" class="bg-white py-2 collapse-inner rounded">
						<div style="margin-top:10px; margin-bottom: 12px;" class="text-center">
							<a role="button" class="btn btn-success" href="/create">
								<i style="margin-right: 8px;" class="fas fa-plus text-white"></i>
								<span>Create Table</span>
							</a>
							<%--<a class="collapse-item" href="/create"><i style="margin-right: 10px;" class="fas fa-plus text-gray-500"></i>Create Table</a>--%>
						</div>
						<h6 class="collapse-header">My Tables</h6>
					<div class="collapse-divider"></div>
				</div>

            </div>
		</li>
		<!-- Divider -->
		<hr class="sidebar-divider d-none d-md-block">

	</ul>
	<!-- End of Sidebar -->

	<!-- Content Wrapper -->
	<div id="content-wrapper" class="d-flex flex-column">

		<!-- Main Content -->
		<div id="content">

			<!-- Topbar -->
			<nav class="navbar navbar-expand navbar-light bg-white topbar mb-12 static-top shadow">

				<!-- Sidebar Toggle (Topbar) -->
				<%--<button id="sidebarToggleTop" class="btn btn-link d-md-none rounded-circle mr-3">--%>
					<%--<i class="fa fa-bars"></i>--%>
				<%--</button>--%>
					<!-- Nav Item - User Information -->
				<%--style="display: block; top:5px; left: 37vw;"--%>
					<li style="" id="userIcon" class="nav-item dropdown no-arrow">
						<a  class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
							<span style="direction: rtl" class="mr-2 d-lg-inline text-gray-600 small">${name}</span>
							<img class="img-profile rounded-circle" src="https://source.unsplash.com/QAB-WJcbgJk/60x60">
						</a>
						<!-- Dropdown - User Information -->
						<div class="dropdown-menu dropdown-menu-right shadow animated--grow-in" aria-labelledby="userDropdown">
							<a class="dropdown-item" href="#">
								<i class="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></i>
								Profile
							</a>
							<a class="dropdown-item" href="#">
								<i class="fas fa-cogs fa-sm fa-fw mr-2 text-gray-400"></i>
								Settings
							</a>
							<a class="dropdown-item" href="#">
								<i class="fas fa-list fa-sm fa-fw mr-2 text-gray-400"></i>
								Activity Log
							</a>
							<div class="dropdown-divider"></div>
							<a class="dropdown-item" href="#" data-toggle="modal" data-target="#logoutModal">
								<i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
								Logout
							</a>
						</div>
					</li>

				</ul>

			</nav>
			<!-- End of Topbar -->

			<!-- Begin Page Content -->
			<div class="container-fluid">

				<!-- Page Heading -->
				<div class="d-sm-flex align-items-center justify-content-between mb-4">
					<h1 class="h3 mb-0 text-gray-800">Dashboard</h1>
				</div>

				<!-- Content Row -->
				<div class="row">

					<!-- Stats Games Played Card -->
					<div class="col-xl-3 col-md-6 mb-4">
						<div class="card border-left-primary shadow h-100 py-2">
							<div class="card-body">
								<div class="row no-gutters align-items-center">
									<div class="col mr-2">
										<div class="text-xs font-weight-bold text-primary text-uppercase mb-1">Games Played</div>
										<div class="h5 mb-0 font-weight-bold text-gray-800">
											${played}
											<span class="text-gray-500" style="padding-left:1em; font-size: 12px">
												W:
												<span id="wins">${wins}</span>
												/ L:
												<span id="losses">${loss}</span>
											</span>
										</div>
									</div>
									<div class="col-auto">
										<i class="fas fa-trophy fa-2x text-gray-300"></i>
									</div>
								</div>
							</div>
						</div>
					</div>

					<!-- Stats Plunks Card -->
					<div class="col-xl-3 col-md-6 mb-4">
						<div class="card border-left-success shadow h-100 py-2">
							<div class="card-body">
								<div class="row no-gutters align-items-center">
									<div class="col mr-2">
										<div class="text-xs font-weight-bold text-success text-uppercase mb-1">Plunks</div>
										<div class="h5 mb-0 font-weight-bold text-gray-800">
											<span id="plunks">${plunks}</span>
										</div>
									</div>
									<div class="col-auto">
										<i class="fas fa-beer fa-2x text-gray-300"></i>
									</div>
								</div>
							</div>
						</div>
					</div>

					<!-- Stats Points Card -->
					<div class="col-xl-3 col-md-6 mb-4">
						<div class="card border-left-info shadow h-100 py-2">
							<div class="card-body">
								<div class="row no-gutters align-items-center">
									<div class="col mr-2">
										<div class="text-xs font-weight-bold text-success text-uppercase mb-1">Points</div>
										<div class="h5 mb-0 font-weight-bold text-gray-800">
											<span id="points">${points}</span>
										</div>
									</div>
									<div class="col-auto">
										<%--<i class="fas fa-clipboard-list fa-2x text-gray-300"></i>--%>
										<i class="fas fa-dice-five fa-2x text-gray-300"></i>
									</div>
								</div>
							</div>
						</div>
					</div>

				</div>

				<!-- Content Row -->

				<div class="row">

					<!-- Area Chart -->
					<div class="col-xl-8 col-lg-7">
						<div class="card shadow mb-4">
							<!-- Card Header - Dropdown -->
							<div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
								<h6 class="m-0 font-weight-bold text-primary">Timeline</h6>
								<div class="dropdown no-arrow">
									<a class="dropdown-toggle" href="#" role="button" id="dropdownMenuArea" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
										<i class="fas fa-ellipsis-v fa-sm fa-fw text-gray-400"></i>
									</a>
									<div class="dropdown-menu dropdown-menu-right shadow animated--fade-in" aria-labelledby="dropdownMenuArea">
										<div class="dropdown-header">Dropdown Header:</div>
										<a class="dropdown-item" href="#">Action</a>
										<a class="dropdown-item" href="#">Another action</a>
										<div class="dropdown-divider"></div>
										<a class="dropdown-item" href="#">Something else here</a>
									</div>
								</div>
							</div>
							<!-- Card Body -->
							<div class="card-body">
								<div class="chart-area">
									<canvas id="myAreaChart"></canvas>
								</div>
							</div>
						</div>
					</div>

					<!-- Pie Chart -->
					<div class="col-xl-4 col-lg-5">
						<div class="card shadow mb-4">
							<!-- Card Header - Dropdown -->
							<div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
								<h6 class="m-0 font-weight-bold text-primary">Win/Loss Ratio</h6>
								<div class="dropdown no-arrow">
									<a class="dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
										<i class="fas fa-ellipsis-v fa-sm fa-fw text-gray-400"></i>
									</a>
									<div class="dropdown-menu dropdown-menu-right shadow animated--fade-in" aria-labelledby="dropdownMenuLink">
										<div class="dropdown-header">Dropdown Header:</div>
										<a class="dropdown-item" href="#">Action</a>
										<a class="dropdown-item" href="#">Another action</a>
										<div class="dropdown-divider"></div>
										<a class="dropdown-item" href="#">Something else here</a>
									</div>
								</div>
							</div>
							<!-- Card Body -->
							<div class="card-body">
								<div class="chart-pie pt-4 pb-2">
									<canvas id="myPieChart"></canvas>
								</div>
								<div class="mt-4 text-center small">
                                    <span class="mr-2">
								  		<i class="fas fa-circle text-primary"></i> Wins
                                    </span>
									<span class="mr-2">
										<i class="fas fa-circle text-danger"></i> Losses
                                    </span>
								</div>
							</div>
						</div>
					</div>
				</div>

			</div>
		</div>
		<!-- End of Main Content -->

		<!-- Footer -->
		<footer class="sticky-footer bg-white">
			<div class="container my-auto">
				<div class="copyright text-center my-auto">
					<span>Copyright &copy; DyeCenter 2019</span>
				</div>
			</div>
		</footer>
		<!-- End of Footer -->

	</div>
	<!-- End of Content Wrapper -->

</div>
<!-- End of Page Wrapper -->

<!-- Scroll to Top Button-->
<a class="scroll-to-top rounded" href="#page-top">
	<i class="fas fa-angle-up"></i>
</a>

<!-- Logout Modal-->
<div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
				<button class="close" type="button" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">×</span>
				</button>
			</div>
			<div class="modal-body">Select "Logout" below if you are ready to end your current session.</div>
			<div class="modal-footer">
				<button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
				<a class="btn btn-primary" href="login.html">Logout</a>
			</div>
		</div>
	</div>
</div>


<!-- Bootstrap core JavaScript-->
<script src="js/widget_js/vendor/jquery/jquery.min.js"></script>
<script src="js/widget_js/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

<!-- Core plugin JavaScript-->
<script src="js/widget_js/vendor/jquery-easing/jquery.easing.min.js"></script>

<!-- Custom scripts for all pages-->
<script src="js/widget_js/app-dashboard.js"></script>

<!-- Page level plugins -->
<script src="js/widget_js/vendor/chart.js/Chart.min.js"></script>

<!-- Page level custom scripts -->
<script src="js/widget_js/demo/chart-area-demo.js"></script>
<script src="js/widget_js/demo/chart-pie-demo.js"></script>

<script src="js/dashboard-utilities.js"></script>
	<script type="text/javascript">
		var value = "${tableNames}";
		loadTableDropdown(value) ;
	</script>
</form>
</body>

</html>
