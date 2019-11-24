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
    <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
	<link href="https://fonts.googleapis.com/css?family=Open+Sans&display=swap" rel="stylesheet">

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<link rel="apple-touch-icon" sizes="180x180" href="webresources/apple-touch-icon.png">
	<link rel="icon" type="image/png" sizes="32x32" href="webresources/favicon-32x32.png">
	<link rel="icon" type="image/png" sizes="16x16" href="webresources/favicon-16x16.png">
	<link rel="manifest" href="webresources/site.webmanifest">
	<link rel="mask-icon" href="webresources/safari-pinned-tab.svg" color="#5bbad5">
	<meta name="msapplication-TileColor" content="#da532c">
	<meta name="theme-color" content="#ffffff">

</head>

<body>
<%--<div class="row">--%>
	<form action="${pageContext.servletContext.contextPath}/dashboard" method="get">
		<div id="back-btn-cover"></div>
		<button id="back-btn" type="submit" name="back" value="back"></button>
		<i id="back-btn-arrow" class="fas fa-arrow-left fa-w-12 fa-4x"></i>
	</form>
<%--</div>--%>
<div id="page-container" class="container col-md-6">
	<br/>

	<form action="${pageContext.servletContext.contextPath}/table" method="post">
		<div id="create-game-container">
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
				<button style="width:500px;" name="submit" type="submit" class="btn-card btn-primary btn".>Submit</button>
				<div class="dropdown-content">

				</div>
			</div>
		</div>
		<br/>
        <h2>Recent Games</h2>
        <hr/>
        <div id="game-btn-container">
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
