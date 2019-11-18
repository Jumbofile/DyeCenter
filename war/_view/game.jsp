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
	<form id="ajaxform" action="${pageContext.servletContext.contextPath}/game" method="post">
	<!-- Modal -->
	<div class="modal fade" id="statModal" tabindex="-1" role="dialog" aria-labelledby="ModalTitle" aria-hidden="true">
		<span class="close">x</span>
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<%-- title filled via .selected class in JS--%>
					<h5 class="modal-title" id="ModalLongTitle"></h5>
				</div>
				<div class="modal-body">
					Update Player Stats
				</div>

					<div class="modal-footer">
						<h2>Points</h2>
						<button type="submit" name="points" onclick="addPoint()" class="btn btn-primary" value="1" >+1</button>

						<%-- player score filled via .selected class in JS --%>
						<span id="modalScore"></span>

						<button type="submit" name="points" onclick="delPoint()" class="btn btn-primary" value="-1">-1</button>
					</div>
					<div class="modal-footer">
						<h2>Plunks</h2>
						<button id="plunkAmount" type="submit" name="points" onclick="addPlunk()" class="btn btn-primary" value="${plunkValue}">+1</button>

						<%-- player score filled via .selected class in JS --%>
						<span id="modalPlunk"></span>

						<button type="submit" name="points" onclick="delPlunk()" class="btn btn-primary" value="-${plunkValue}" >-1</button>
					</div>

			</div>
		</div>
		<input id="playerInput" style="display: none"  type="input" name="playerFocus" value="">
		<input id="points" style="display: none"  type="input" name="points" value="">
	</div>
	</form>


	<form id="cardRefresh" action="${pageContext.servletContext.contextPath}/game" method="get">
		<div class="row">

			<!-- For demo purpose -->
			<div class="col-lg-12 mx-auto mb-5 text-white text-center">
				<h1 class="display-4">In-Game</h1>
				<p class="lead mb-0">Tap or click a player card to edit their game stats</p>
			</div>
			<!-- END -->



			<div id="t1p1Card" name="card-data-1" value="${t1p1Name},${t1p1Username},${t1p1Score},${t1p1Plunks}" onclick="openModal(this)" class="col-xl-3 col-lg-6 mb-4">

				<div class="bg-white rounded-lg p-5 shadow">
					<h2 class="h2 font-weight-bold text-center mb-4">${t1p1Name}</h2>
					<h2 class="h6 font-weight-bold text-center mb-4">${t1p1Username}</h2>

					<!-- Progress bar 1 -->
					<div class="progress mx-auto" data-value=${t1p1WLR}>
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
							<div class="points h4 font-weight-bold mb-0">${t1p1Score}</div><span class="small text-gray">Points</span>
						</div>
						<div class="col-6">
							<div class="plunks h4 font-weight-bold mb-0">${t1p1Plunks}</div><span class="small text-gray">Plunks</span>
						</div>
					</div>
					<!-- END -->
				</div>
			</div>

			<div id="t1p2Card" name="card-data-2" value="${t1p2Name},${t1p2Username},${t1p2Score},${t1p2Plunks}" onclick="openModal(this)" class="col-xl-3 col-lg-6 mb-4">
				<div class="bg-white rounded-lg p-5 shadow">
					<h2 class="h2 font-weight-bold text-center mb-4">${t1p2Name}</h2>
					<h2 class="h6 font-weight-bold text-center mb-4">${t1p2Username}</h2>

					<!-- Progress bar 1 -->
					<div class="progress mx-auto" data-value=${t1p2WLR}>
						<span class="progress-left">
							<span class="progress-bar border-primary"></span>
					  	</span>
						<span class="progress-right">
							<span class="progress-bar border-primary"></span>
			  			</span>

						<div class="progress-value w-100 h-100 rounded-circle d-flex align-items-center justify-content-center">
							<div class="small">W/L &nbsp;&nbsp;</div>
							<div class="h2 font-weight-bold">${t1p2WLR}<sup class="small">%</sup></div>
						</div>
					</div>
					<!-- END -->

					<!-- Demo info -->
					<div class="row text-center mt-4">
						<div class="col-6 border-right">
							<div class="points h4 font-weight-bold mb-0">${t1p2Score}</div><span class="small text-gray">Points</span>
						</div>
						<div class="col-6">
							<div class="plunks h4 font-weight-bold mb-0">${t1p2Plunks}</div><span class="small text-gray">Plunks</span>
						</div>
					</div>
					<!-- END -->
				</div>
			</div>

			<div id="t2p1Card" name="card-data-3" value="${t2p1Name},${t2p1Username},${t2p1Score},${t2p1Plunks}" onclick="openModal(this)" class="col-xl-3 col-lg-6 mb-4">
				<div class="bg-white rounded-lg p-5 shadow">
					<h2 class="h2 font-weight-bold text-center mb-4">${t2p1Name}</h2>
					<h2 class="h6 font-weight-bold text-center mb-4">${t2p1Username}</h2>

					<!-- Progress bar 1 -->
					<div class="progress mx-auto" data-value=${t2p1WLR}>
						<span class="progress-left">
							<span class="progress-bar border-primary"></span>
					  	</span>
						<span class="progress-right">
							<span class="progress-bar border-primary"></span>
			  			</span>

						<div class="progress-value w-100 h-100 rounded-circle d-flex align-items-center justify-content-center">
							<div class="small">W/L &nbsp;&nbsp;</div>
							<div class="h2 font-weight-bold">${t2p1WLR}<sup class="small">%</sup></div>
						</div>
					</div>
					<!-- END -->

					<!-- Demo info -->
					<div class="row text-center mt-4">
						<div class="col-6 border-right">
							<div class="points h4 font-weight-bold mb-0">${t2p1Score}</div><span class="small text-gray">Points</span>
						</div>
						<div class="col-6">
							<div class="plunks h4 font-weight-bold mb-0">${t2p1Plunks}</div><span class="small text-gray">Plunks</span>
						</div>
					</div>
					<!-- END -->
				</div>
			</div>

			<div id="t2p2Card" name="card-data-4" value="${t2p2Name},${t2p2Username},${t2p2Score},${t2p2Plunks}" onclick="openModal(this)" class="col-xl-3 col-lg-6 mb-4">
				<div class="bg-white rounded-lg p-5 shadow">
					<h2 class="h2 font-weight-bold text-center mb-4">${t2p2Name}</h2>
					<h2 class="h6 font-weight-bold text-center mb-4">${t2p2Username}</h2>

					<!-- Progress bar 1 -->
					<div class="progress mx-auto" data-value=${t2p2WLR}>
						<span class="progress-left">
							<span class="progress-bar border-primary"></span>
					  	</span>
						<span class="progress-right">
							<span class="progress-bar border-primary"></span>
			  			</span>

						<div class="progress-value w-100 h-100 rounded-circle d-flex align-items-center justify-content-center">
							<div class="small">W/L &nbsp;&nbsp;</div>
							<div class="h2 font-weight-bold">${t2p2WLR}<sup class="small">%</sup></div>
						</div>
					</div>
					<!-- END -->

					<!-- Demo info -->
					<div class="row text-center mt-4">
						<div class="col-6 border-right">
							<div class="points h4 font-weight-bold mb-0">${t2p2Score}</div><span class="small text-gray">Points</span>
						</div>
						<div class="col-6">
							<div class="plunks h4 font-weight-bold mb-0">${t2p2Plunks}</div><span class="small text-gray">Plunks</span>
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
