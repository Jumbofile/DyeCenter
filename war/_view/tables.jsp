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
	<link href="css/table.css" rel="stylesheet">

	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<link rel="apple-touch-icon" sizes="180x180" href="webresources/apple-touch-icon.png">
	<link rel="icon" type="image/png" sizes="32x32" href="webresources/favicon-32x32.png">
	<link rel="icon" type="image/png" sizes="16x16" href="webresources/favicon-16x16.png">
	<link rel="manifest" href="webresources/site.webmanifest">
	<link rel="mask-icon" href="webresources/safari-pinned-tab.svg" color="#5bbad5">
	<meta name="msapplication-TileColor" content="#da532c">
	<meta name="theme-color" content="#ffffff">
	<!-- Custom fonts for this template
	<link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
	<link href="https://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css">
	<link href='https://fonts.googleapis.com/css?family=Kaushan+Script' rel='stylesheet' type='text/css'>
	<link href='https://fonts.googleapis.com/css?family=Droid+Serif:400,700,400italic,700italic' rel='stylesheet' type='text/css'>
	<link href='https://fonts.googleapis.com/css?family=Roboto+Slab:400,100,300,700' rel='stylesheet' type='text/css'>
	-->
	<!-- Custom styles for this template
	<link href="css/agency.css" rel="stylesheet">
-->
	<!-- Custom styles for this template
	<link href="css/dashboard.css" rel="stylesheet">
	-->

</head>

<body>
<div class="container col-md-6">
	<br/>
	<div class="row">
		<form action="${pageContext.servletContext.contextPath}/dashboard" method="get">
			<button type="submit" name="back" value="back" class="btn btn-primary">Back</button>
		</form>
	</div>
	<form action="${pageContext.servletContext.contextPath}/table" method="post">


		<div style="margin-top: 15vh;">
			<h2 style="margin-bottom: 30px;">Create Game</h2>
			<div class="input-group mb-3">
				<div class="input-group-prepend">
					<span class="input-group-text" id="basic-addon1">Team 1</span>
				</div>
				<input type="text" name="t1p1" class="form-control" placeholder="Player Username" aria-describedby="basic-addon1" autofocus>
				<input type="text" name="t1p2" class="form-control" placeholder="Player Username" aria-describedby="basic-addon1" autofocus>
			</div>

			<div class="input-group mb-3">
				<div class="input-group-prepend">
					<span class="input-group-text" id="basic-addon2">Team 2</span>
				</div>
				<input type="text" name="t2p1" class="form-control" placeholder="Player Username" aria-describedby="basic-addon2" >
				<input type="text" name="t2p2" class="form-control" placeholder="Player Username" aria-describedby="basic-addon2" >
			</div>

			<div style="text-align: center;">
				<button style="width:100%;" name="submit" type="submit" class="btn btn-primary">Submit</button>
			</div>
		</div>
		<br/>
		<hr>
		<div id="gameList">
			<br/>
			<h2>Games</h2>
			${gameButtons}
		</div>
	</form>
</div>
</body>

<!-- Bootstrap core JavaScript-->
<script src="js/widget_js/vendor/jquery/jquery.min.js"></script>
<script src="js/widget_js/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="js/table-utilities.js"></script>

</html>
