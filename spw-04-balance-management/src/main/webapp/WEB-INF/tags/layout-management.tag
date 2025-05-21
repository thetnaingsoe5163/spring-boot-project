<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="title" required="true" type="java.lang.String"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Balance | ${title.toUpperCase()}</title>

<c:set var="root" value="${pageContext.request.contextPath}"
	scope="request"></c:set>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
	crossorigin="anonymous"></script>

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">

<link rel="stylesheet" href="${root}/resources/css/common.css">

</head>
<body>

	<!-- Navigation -->
	<nav class="navbar navbar-expand custom-navbar-color">
		<div class="container">
			<a href="${root}/admin/home" class="navbar-brand custom-navtext-color"> 
				<i class="bi bi-house-door-fill"></i> Admin Home
			</a>

			<ul class="navbar-nav">
				<li class="nav-item">
					<a href="${root}/admin/access" 
						class="nav-link custom-navtext-color ${title eq 'Access History' ? 'custom-active' : ''}"> 
						<i class="bi bi-calendar"></i> Access History
					</a>
				</li>
				
				<li class="nav-item">
					<a href="${root}/admin/member" 
						class="nav-link custom-navtext-color ${title eq 'Member' ? 'custom-active' : ''}"> 
						<i class="bi bi-people-fill"></i> Members
					</a>
				</li>
				
				<li class="nav-item">
					<a href="#" id="signOutMenu" class="nav-link custom-navtext-color"> 
						<i class="bi bi-box-arrow-right"></i> Sign Out
					</a>
				</li>
			</ul>
		</div>
	</nav>

	<div class="container my-2">
		<jsp:doBody />
	</div>
	
	<app:sign-out />
	<script type="text/javascript" src="${root}/resources/js/signout.js"></script>
</body>
</html>