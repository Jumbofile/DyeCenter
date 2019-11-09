<!DOCTYPE html>
<html lang="en">

<head>
	<meta http-equiv="refresh" content="10" />
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
<%--<div class="container-fluid">--%>
	<%--<div class="row">--%>
		<%--<form action="${pageContext.servletContext.contextPath}/dashboard" method="post">--%>
			<%--<button type="submit" name="back" value="back" class="btn btn-primary">Back</button>--%>
		<%--</form>--%>
	<%--</div>--%>
	<%--<form action="${pageContext.servletContext.contextPath}/game" method="post">--%>
		<%--<div class="row">--%>
			<%--<div class="col-md-4">--%>
				<%--<div onclick="selectPlayer(this, 0)" class="row playerSel">--%>
					<%--<h2 class="float-left">${t1p1Name}   ${t1p1Score}</h2>--%>
				<%--</div>--%>

				<%--<div class="row">--%>
					<%--<h2 class="float-right">Team 1:  ${t1Score}</h2>--%>
				<%--</div>--%>

				<%--<div onclick="selectPlayer(this, 1)" class="row playerSel">--%>
					<%--<h2 class="float-left">${t1p2Name}   ${t1p2Score}</h2>--%>
				<%--</div>--%>
			<%--</div>--%>

			<%--<div class="col-md">--%>
				<%--<img id="gamePic" style="width:100%" class="img-responsive" src="/webresources/dye-table.jpg" alt="Dye Table">--%>
			<%--</div>--%>

			<%--<div class="col-md-4">--%>
				<%--<div onclick="selectPlayer(this, 2)" class="row playerSel">--%>
					<%--<h2 class="float-left">${t2p1Name}   ${t2p1Score}</h2>--%>
				<%--</div>--%>

				<%--<div class="row">--%>
					<%--<h2 class="float-right">Team 2:  ${t2Score}</h2>--%>
				<%--</div>--%>

				<%--<div onclick="selectPlayer(this, 3)" class="row playerSel">--%>
					<%--<h2 class="float-left">${t2p2Name}   ${t2p2Score}</h2>--%>
				<%--</div>--%>
			<%--</div>--%>
		<%--</div>--%>



		<%--<div id="updateBtns" >--%>
			<%--<input type="number" name="player" style="display: none" id="playerValue" value="-1">--%>

			<%--<button type="submit" name="point" class="btn btn-primary" value="-${plunkValue}~">-${plunkValue}</button>--%>
			<%--<span>  Plunk  </span>--%>
			<%--<button type="submit" name="point" class="btn btn-primary" value="${plunkValue}~">+${plunkValue}</button>--%>
			<%--<br>--%>
			<%--<br>--%>
			<%--<button type="submit" name="point" class="btn btn-primary"  value="-1">-1</button>--%>
			<%--<span>  Points  </span>--%>
			<%--<button type="submit" name="point" class="btn btn-primary" value="1">+1</button>--%>
		<%--</div>--%>
		<%--<button type="submit" name="point" class="btn btn-primary" value="finish">Finish Game</button>--%>
	<%--</form>--%>
<%--</div>--%>
<div class="container py-5">
	<div class="row">
		<form action="${pageContext.servletContext.contextPath}/dashboard" method="post">
			<button type="submit" name="back" value="back" class="btn btn-primary">Back</button>
		</form>
	</div>
	<form action="${pageContext.servletContext.contextPath}/game" method="post">
		<div class="row">

			<!-- For demo purpose -->
			<div class="col-lg-12 mx-auto mb-5 text-white text-center">
				<h1 class="display-4">In-Game</h1>
				<p class="lead mb-0">Tap or click a player card to edit their game stats</p>
				<%--<p class="lead">Snippet by <a href="https://bootstrapious.com/snippets" class="text-white">--%>
					<%--<u>Bootstrapious</u></a>--%>
				<%--</p>--%>
			</div>
			<!-- END -->

			<div class="col-xl-3 col-lg-6 mb-4">
				<div class="bg-white rounded-lg p-5 shadow">
					<h2 class="h6 font-weight-bold text-center mb-4">${t1p1Name}</h2>

					<!-- Progress bar 1 -->
					<div class="progress mx-auto" data-value='80'>
						<span class="progress-left">
							<span class="progress-bar border-primary"></span>
					  	</span>
						<span class="progress-right">
							<span class="progress-bar border-primary"></span>
			  			</span>

						<div class="progress-value w-100 h-100 rounded-circle d-flex align-items-center justify-content-center">
							<div class="small">W/L &nbsp;&nbsp;</div>
							<div class="h2 font-weight-bold">${t1p1WLR}<sup class="small">%</sup></div>
						</div>
					</div>
					<!-- END -->

					<!-- Demo info -->
					<div class="row text-center mt-4">
						<div class="col-6 border-right">
							<div class="h4 font-weight-bold mb-0">${t1p1Score}</div><span class="small text-gray">Points</span>
						</div>
						<div class="col-6">
							<div class="h4 font-weight-bold mb-0">0</div><span class="small text-gray">Plunks</span>
						</div>
					</div>
					<!-- END -->
				</div>
			</div>

			<div class="col-xl-3 col-lg-6 mb-4">
				<div class="bg-white rounded-lg p-5 shadow">
					<h2 class="h6 font-weight-bold text-center mb-4">${t1p2Name}</h2>

					<!-- Progress bar 2 -->
					<div class="progress mx-auto" data-value='25'>
			  <span class="progress-left">
							<span class="progress-bar border-danger"></span>
			  </span>
						<span class="progress-right">
							<span class="progress-bar border-danger"></span>
			  </span>
						<div class="progress-value w-100 h-100 rounded-circle d-flex align-items-center justify-content-center">
							<div class="h2 font-weight-bold">25<sup class="small">%</sup></div>
						</div>
					</div>
					<!-- END -->

					<!-- Demo info-->
					<div class="row text-center mt-4">
						<div class="col-6 border-right">
							<div class="h4 font-weight-bold mb-0">28%</div><span class="small text-gray">Last week</span>
						</div>
						<div class="col-6">
							<div class="h4 font-weight-bold mb-0">60%</div><span class="small text-gray">Last month</span>
						</div>
					</div>
					<!-- END -->
				</div>
			</div>

			<div class="col-xl-3 col-lg-6 mb-4">
				<div class="bg-white rounded-lg p-5 shadow">
					<h2 class="h6 font-weight-bold text-center mb-4">${t2p1Name}</h2>

					<!-- Progress bar 3 -->
					<div class="progress mx-auto" data-value='76'>
			  <span class="progress-left">
							<span class="progress-bar border-success"></span>
			  </span>
						<span class="progress-right">
							<span class="progress-bar border-success"></span>
			  </span>
						<div class="progress-value w-100 h-100 rounded-circle d-flex align-items-center justify-content-center">
							<div class="h2 font-weight-bold">76<sup class="small">%</sup></div>
						</div>
					</div>
					<!-- END -->

					<!-- Demo info -->
					<div class="row text-center mt-4">
						<div class="col-6 border-right">
							<div class="h4 font-weight-bold mb-0">28%</div><span class="small text-gray">Last week</span>
						</div>
						<div class="col-6">
							<div class="h4 font-weight-bold mb-0">60%</div><span class="small text-gray">Last month</span>
						</div>
					</div>
					<!-- END -->
				</div>
			</div>

			<div class="col-xl-3 col-lg-6 mb-4">
				<div class="bg-white rounded-lg p-5 shadow">
					<h2 class="h6 font-weight-bold text-center mb-4">${t2p2Name}</h2>

					<!-- Progress bar 4 -->
					<div class="progress mx-auto" data-value='12'>
			  <span class="progress-left">
							<span class="progress-bar border-warning"></span>
			  </span>
						<span class="progress-right">
							<span class="progress-bar border-warning"></span>
			  </span>
						<div class="progress-value w-100 h-100 rounded-circle d-flex align-items-center justify-content-center">
							<div class="h2 font-weight-bold">12<sup class="small">%</sup></div>
						</div>
					</div>
					<!-- END -->

					<!-- Demo info -->
					<div class="row text-center mt-4">
						<div class="col-6 border-right">
							<div class="h4 font-weight-bold mb-0">28%</div><span class="small text-gray">Last week</span>
						</div>
						<div class="col-6">
							<div class="h4 font-weight-bold mb-0">60%</div><span class="small text-gray">Last month</span>
						</div>
					</div>
					<!-- END -->
				</div>
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
