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

	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<link rel="apple-touch-icon" sizes="180x180" href="webresources/apple-touch-icon.png">
	<link rel="icon" type="image/png" sizes="32x32" href="webresources/favicon-32x32.png">
	<link rel="icon" type="image/png" sizes="16x16" href="webresources/favicon-16x16.png">
	<link rel="manifest" href="webresources/site.webmanifest">
	<link rel="mask-icon" href="webresources/safari-pinned-tab.svg" color="#5bbad5">
	<meta name="msapplication-TileColor" content="#da532c">
	<meta name="theme-color" content="#ffffff">

</head>

<body>
<form action="${pageContext.servletContext.contextPath}/game" method="post">
	<div class="container">
		<div class="row rowOutter">
			<div onclick="selectPlayer(this)" class="col-sm playerSel">
				<h2 class="float-right">${t1p1Name}</h2>
			</div>
			<div class="col-sm">

			</div>
			<div onclick="selectPlayer(this)" class="col-sm playerSel">
				<h2 class="float-left">${t2p1Name}</h2>
			</div>
		</div>

		<div class="row rowCenter">
			<div class="col-sm">
				<h1 class="float-left">Team 1</h1>
			</div>
			<div class="col-sm">
				<h2 style="text-align: center">*Table Graphic*</h2>
			</div>
			<div class="col-sm">
				<h1 class="float-right">Team 2</h1>
			</div>
		</div>

		<div class="row rowOutter">
			<div onclick="selectPlayer(this)" class="col-sm playerSel">
				<h2 class="float-right">${t1p2Name}</h2>
			</div>
			<div class="col-sm">

			</div>
			<div onclick="selectPlayer(this)" class="col-sm playerSel">
				<h2 class="float-left">${t2p2Name}</h2>
			</div>
		</div>
	</div>
</form>
</body>

<!-- Bootstrap core JavaScript-->
<script src="js/widget_js/vendor/jquery/jquery.min.js"></script>
<script src="js/widget_js/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

<script src="js/game-utilities.js"></script>

</html>
