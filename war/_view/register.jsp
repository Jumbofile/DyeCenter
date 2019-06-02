<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!--[if lt IE 7]> <html class="lt-ie9 lt-ie8 lt-ie7" lang="en"> <![endif]-->
<!--[if IE 7]> <html class="lt-ie9 lt-ie8" lang="en"> <![endif]-->
<!--[if IE 8]> <html class="lt-ie9" lang="en"> <![endif]-->
<!--[if gt IE 8]><!-->
<html lang="en">
<!--<![endif]-->
<html lang="en">
<head>
	<!-- Required meta tags -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

	<!-- Bootstrap CSS
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
	-->
	<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css" />
	<link rel="stylesheet" type="text/css" href="css/login.css" />
	<title>Register and throw</title>
</head>
<body>
<form action="${pageContext.servletContext.contextPath}/register" method="post">
<div class="container-fluid">
	<div class="row no-gutter">
		<div class="d-none d-md-flex col-md-4 col-lg-6 bg-image"></div>
		<div class="col-md-8 col-lg-6">
			<div class="login d-flex align-items-center py-5">
				<div class="container">
					<div class="row">
						<div class="col-md-9 col-lg-8 mx-auto">
							<h3 class="login-heading mb-4">Register an account</h3>
							<form>
								<div class="form-label-group">

									<input type="text" name="u" id="inputUsername" class="form-control" placeholder="username" required autofocus>
									<label for="inputUsername">Username</label>
								</div>

								<div class="form-label-group">

									<input type="text" name="n" id="inputName" class="form-control" placeholder="Your Name" required autofocus>
									<label for="inputName">Your name</label>
								</div>

								<div class="form-label-group">

									<input type="text" name="e" id="inputEmail" class="form-control" placeholder="Email address" required autofocus>
									<label for="inputEmail">Email address</label>
								</div>

								<div class="form-label-group">
									<input type="password" name="p" id="inputPassword" class="form-control" placeholder="Password" required>
									<label for="inputPassword">Password</label>
								</div>
								<div class="form-label-group">
									<input type="password" name="p2" id="inputPassword2" class="form-control" placeholder="Password" required>
									<label for="inputPassword2">Retype Password</label>
								</div>

								<button class="btn btn-lg btn-primary btn-block btn-login text-uppercase font-weight-bold mb-2" type="submit" name="submit">Register</button>
								<div class="text-center">
									Have an account? <a class="medium" href="/login">Sign in.</a>
								</div>

							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
	<div class="error">
		<c:out value="${response}" escapeXml="false" />
	</div>
	<div class="login">
		<h1>Register</h1>


			<button type="submit" name="submit"
				class="btn btn-primary btn-block btn-large">Register Account</button>
				<div class=register>
				<a href="login">Have an account? Log in.</a>
			</div>
		</form>
	</div>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</body>
</html>