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

	<link href="css/game.css" rel="stylesheet">
	<link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">

	<meta charset="utf-8">
	<%--<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">--%>
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<link rel="apple-touch-icon" sizes="180x180" href="webresources/apple-touch-icon.png">
	<link rel="icon" type="image/png" sizes="32x32" href="webresources/favicon-32x32.png">
	<link rel="icon" type="image/png" sizes="16x16" href="webresources/favicon-16x16.png">
	<link rel="manifest" href="webresources/site.webmanifest">
	<link rel="mask-icon" href="webresources/safari-pinned-tab.svg" color="#5bbad5">
	<meta name="msapplication-TileColor" content="#da532c">
	<meta name="theme-color" content="#ffffff">

</head>

<body style="overflow-x: hidden;">

<div class="container py-5">

	<div class="row">
		<form action="${pageContext.servletContext.contextPath}/table" method="get">
			<div id="back-btn-cover"></div>
			<button id="back-btn" type="submit" name="back" value="back"></button>
			<i id="back-btn-arrow" class="fas fa-arrow-left fa-w-12 fa-4x"></i>
		</form>
	</div>
	<form id="ajaxform" action="${pageContext.servletContext.contextPath}/pregame" method="post">

		<div class="row d-flex justify-content-center">
			<div id="game-tag" class="input-group mb-3" style="width:185px;">
				<div class="input-group-prepend">
					<span class="input-group-text" id="basic-addon1">Game-ID</span>
				</div>
				<input type="text" style="background-color: white" class="form-control text-center font-weight-bold" value="${gameHash}" aria-label="Username" aria-describedby="basic-addon1" readonly>
			</div>
		</div>
	</form>
</div>


</body>

<!-- Bootstrap core JavaScript-->
<script src="js/widget_js/vendor/jquery/jquery.min.js"></script>
<script src="js/widget_js/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="js/game-utilities.js"></script>

</html>