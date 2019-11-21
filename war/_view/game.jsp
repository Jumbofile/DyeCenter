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

<div class="container py-5">

	<div class="row">
		<form action="${pageContext.servletContext.contextPath}/table" method="get">
			<button type="submit" name="back" value="back" class="btn btn-primary">Back</button>
		</form>
	</div>
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

		<div class="row">

			<div class="col-lg-12 mx-auto mb-5 text-white text-center">
				<h1 class="display-4">In-Game</h1>
				<p class="lead mb-0">Tap or click a player card to edit their game stats</p>

				<span class="team-text col-lg-6 mx-auto mb-5">${t1Score}</span>
				<span class="team-text col-lg-6 mx-auto mb-5">-</span>
				<span class="team-text col-lg-6 mx-auto mb-5">${t2Score}</span>
			</div>

			<div class="team-header col-xl-6 col-lg-6 mb-8" id="team1-header">
				<span class="team-text" id="team1-text">${t1Score}</span>
				<div class="player-card-container">
					<div class="col-xl-6 col-lg-12 mb-4" id="t1p1Card" name="card-data-1" value="${t1p1Name},${t1p1Username},${t1p1Score},${t1p1Plunks}" onclick="openModal(this)" >
						<div class="game-player-card player-team1  bg-white rounded-lg p-5 shadow">
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

					<div class="col-xl-6 col-lg-12 mb-4" id="t1p2Card" name="card-data-2" value="${t1p2Name},${t1p2Username},${t1p2Score},${t1p2Plunks}" onclick="openModal(this)" >
						<div class="game-player-card player-team1 bg-white rounded-lg p-5 shadow">
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
				</div>
			</div>


			<div class="team-header col-xl-6 col-lg-6 mb-8" id="team2-header">
				<span class="team-text" id="team2-text">${t2Score}</span>
				<div class="player-card-container">
					<div style="border-color: #FFF" class="col-xl-6 col-lg-12 mb-4" id="t2p1Card" name="card-data-3" value="${t2p1Name},${t2p1Username},${t2p1Score},${t2p1Plunks}" onclick="openModal(this)" >
						<div class="game-player-card player-team2 bg-white rounded-lg p-5 shadow">
							<h2 class="h2 font-weight-bold text-center mb-4">${t2p1Name}</h2>
							<h2 class="h6 font-weight-bold text-center mb-4">${t2p1Username}</h2>

							<!-- Progress bar 1 -->
							<div class="progress mx-auto" data-value=${t2p1WLR}>
								<span class="progress-left">
									<span class="progress-bar border-danger"></span>
								</span>
								<span class="progress-right">
									<span class="progress-bar border-danger"></span>
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

					<div style="border-color: #FFF" class="col-xl-6 col-lg-12 mb-4" id="t2p2Card" name="card-data-2" value="${t2p2Name},${t2p2Username},${t2p2Score},${t2p2Plunks}" onclick="openModal(this)" >
						<div class="game-player-card player-team2 bg-white rounded-lg p-5 shadow">
							<h2 class="h2 font-weight-bold text-center mb-4">${t2p2Name}</h2>
							<h2 class="h6 font-weight-bold text-center mb-4">${t2p2Username}</h2>

							<!-- Progress bar 1 -->
							<div class="progress mx-auto" data-value=${t2p2WLR}>
								<span class="progress-left">
									<span class="progress-bar border-danger"></span>
								</span>
								<span class="progress-right">
									<span class="progress-bar border-danger"></span>
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
			</div>

		</div>

		<button type="submit" name="finish" onclick="finishGame()" value="finish" class="btn btn-primary">Finish Game</button>
		<div class="row d-flex justify-content-center">
			<div class="input-group mb-3" style="width:185px;">
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
