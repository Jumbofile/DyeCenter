<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!--[if lt IE 7]> <html class="lt-ie9 lt-ie8 lt-ie7" lang="en"> <![endif]-->
<!--[if IE 7]> <html class="lt-ie9 lt-ie8" lang="en"> <![endif]-->
<!--[if IE 8]> <html class="lt-ie9" lang="en"> <![endif]-->
<!--[if gt IE 8]><!-->
<html lang="en">
<!--<![endif]-->
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>Register Form</title>
<head>
	<link rel="icon" type="image/x-icon" href="webresources/favicon.png" />
	<link rel="stylesheet" href="webresources/register.css" />
</head>
<body>
	<div class="error">
		<c:out value="${response}" escapeXml="false" />
	</div>
	<div class="login">
		<h1>Register</h1>
		<form action="${pageContext.servletContext.contextPath}/register"
			method="post">
			<input type="email" name="e"placeholder="Email" required="required" />
			<input type="text" name="u" placeholder="Username"required="required" /> 
			<input type="text" name="n" placeholder="First Name"required="required" /> 
			<input type="text" name="n2" placeholder="Last Name"required="required" /> 
			<input type="password" name="p"placeholder="Password" required="required" />
			<input type="password" name="p2"placeholder="Password" required="required" />
			<button type="submit" name="submit"
				class="btn btn-primary btn-block btn-large">Register Account</button>
				<div class=register>
				<a href="login">Have an account? Log in.</a>
			</div>
		</form>
	</div>
</body>
</html>