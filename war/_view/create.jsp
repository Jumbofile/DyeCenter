<!DOCTYPE html>
<html lang="en" class="no-js">
	<head>
		<meta charset="UTF-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
		<meta name="viewport" content="width=device-width, initial-scale=1.0"> 
		<title>Find your next project</title>
		<meta name="description" content="A site to find the connections to make your project a reality." />
		<meta name="keywords" content="google nexus 7 menu, css transitions, sidebar, side menu, slide out menu" />
		<meta name="author" content="Codrops" />
		<link rel="shortcut icon" href="../favicon.ico">
		<link rel="stylesheet" type="text/css" href="css/normalize.css" />
		<link rel="stylesheet" type="text/css" href="css/demo.css" />
		<link rel="stylesheet" type="text/css" href="css/component.css" />
		<link rel="stylesheet" type="text/css" href="css/create.css" />
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css"/>
		<script src="js/modernizr.custom.js"></script>
	</head>
	<body>
		<div class="content">
			<div class="login">
			
				<h1>Create!</h1>
				<form action="${pageContext.servletContext.contextPath}/create"
					method="post">
					<div class = "centertext">
					What do you have to offer?
					<br>
					<div class = "center">
						<select name="t">
							<option value="need">I have an idea</option>
							<option value="have">I have a skill</option>
						</select>
					</div>
					Name of Idea/service
					<br>
					<input type="text" name="n"  maxlength="39" required="required" />
					<br>
					Sentance about your Idea/service
					<br>
					<input type="text" name="s" maxlength="299" required="required" />
					<br>
					<%--Full description and list required/wanted skills and other details.
					<br>
					<textarea name="l" rows="10" cols="30" required="required"></textarea>
					<br>--%>
					Image URL
					<br>
					<input type="text" name="i" maxlength="499" required="required" />
					<br>
					Requirements/Experience
					<br>
					<input type="text" name="sk" maxlength="499" required="required" />
					<button type="submit" name="submit" class="btn btn-primary btn-block btn-large">Create</button>
					<br>
				</form>
				</div>
			</div>

		<div class="container">

			<ul id="gn-menu" class="gn-menu-main">
				<li class="gn-trigger">
					<a class="gn-icon gn-icon-menu"><span>Menu</span></a>
					<nav class="gn-menu-wrapper">
						<div class="gn-scroller">
							<ul class="gn-menu">
								<li class="gn-search-item">
									<input placeholder="Search" type="search" class="gn-search">
									<a class="gn-icon gn-icon-search"><span>Search</span></a>
								</li>
								<li>
									<a class="gn-icon gn-icon-archive" href="/profile">Profile</a>
								</li>
								<li><a class="gn-icon gn-icon-cog">Settings</a></li>
								<li><a class="gn-icon gn-icon-help" href="/help">Help</a></li>
								<li>
									<!--<a class="gn-icon gn-icon-archive">Archives</a>
									<ul class="gn-submenu">
										<li><a class="gn-icon gn-icon-article">Articles</a></li>
										<li><a class="gn-icon gn-icon-pictures">Images</a></li>
										<li><a class="gn-icon gn-icon-videos">Videos</a></li>
									</ul>-->
								</li>
							</ul>
						</div><!-- /gn-scroller -->
					</nav>
				</li>
				<li><a href="/create"></a></li>

			</ul>

			<a href="/create" class="float">
				<i class="fa fa-plus my-float"></i>
			</a>
			<a href="/index" class="float2">
				<i class="fa fa-home my-float"></i>
			</a>
			<div class = "logo">
				<img src="webresources/logo.png" alt="IdeaConnect">
			</div>
				
		</div><!-- /container -->
		<script src="js/classie.js"></script>
		<script src="js/gnmenu.js"></script>
		<script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
		<script src='http://cdnjs.cloudflare.com/ajax/libs/modernizr/2.8.3/modernizr.min.js'></script>
		<script src="js/index.js"></script>
		<script>
			new gnMenu( document.getElementById( 'gn-menu' ) );
		</script>
		
	</body>
</html>