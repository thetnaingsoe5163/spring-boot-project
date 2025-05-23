<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<c:set var="root" value="${pageContext.request.contextPath}" scope="request" />

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4Q6Gf2aSP4eDXB8Miphtr37CMZZQ5oXLH2yaXMJ2w8e2ZtHTl7GptT4jmndRuHDT" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/js/bootstrap.bundle.min.js" integrity="sha384-j1CDi7MgGQ12Z7Qab0qlWQ/Qqz24Gc6BM0thvEMVjHnfYGF0rmFCozFSxQBxwHKO" crossorigin="anonymous"></script>

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css">

<link rel="stylesheet" href="${root}/resources/css/common.css" />
<script type="text/javascript" src="${root}/resources/javascript/guest-home.js"></script>
</head>
<body>
	
	<nav class="navbar navbar-expand shadow">
		<div class="container">
			<a href="" class="navbar-brand">
				<i class="bi bi-fork-knife"></i> Restaurant 
			</a>
			<div class="d-flex align-items-center">
				<button class="btn position-relative me-4"> 
					<i class="bi bi-cart3"></i>
					<span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-info">
	    				99+
	    				<span class="visually-hidden">Items in the cart</span>
	  				</span>
				</button>
				<ul class="navbar-nav">
					<li class="nav-link">
						<i class="bi bi-person-fill"></i>
					</li>
				</ul>
			</div>
		</div>
	</nav>	
	
	
	<div>
		<jsp:doBody></jsp:doBody>
	</div>
	
</body>
</html>