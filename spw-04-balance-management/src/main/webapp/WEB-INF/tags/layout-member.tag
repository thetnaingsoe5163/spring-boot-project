<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="title" required="true" type="java.lang.String" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Balance | ${title.toUpperCase()}</title>

<c:set var="root" value="${pageContext.request.contextPath}" scope="request"></c:set>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">

<link rel="stylesheet" href="${root}/resources/css/common.css" />
</head>
<body>

	<!-- Navigation -->
	<nav class="navbar navbar-expand sticky-top bg-light shadow">
		<div class="container">
			<a href="${root}/member/home" class="navbar-brand"> 
				<i class="bi bi-house-door-fill"></i> Member Home
			</a>

			<ul class="navbar-nav">
				<li class="nav-item">
					<a href="${root}/member/balance" 
						class="nav-link ${title eq 'Balance' ? 'active' : ''}"> 
						<i class="bi bi-bar-chart"></i> Balance
					</a>
				</li>
				
				<li class="nav-item">
					<a href="${root}/member/entry/incomes" 
						class="nav-link ${title eq 'Incomes' ? 'active' : ''}"> 
						<i class="bi bi-flag"></i> Incomes
					</a>
				</li>
				
				<li class="nav-item">
					<a href="${root}/member/entry/expenses" 
						class="nav-link ${title eq 'Expenses' ? 'active' : ''}"> 
						<i class="bi bi-cart-dash"></i> Expenses
					</a>
				</li>
				
				<li class="nav-item">
					<a href="${root}/member/ledger" 
						class="nav-link ${title eq 'Ledgers' ? 'active' : ''}"> 
						<i class="bi bi-tag"></i> Ledgers
					</a>
				</li>
				
				<li class="nav-item">
					<a href="#" id="signOutMenu" class="nav-link"> 
						<i class="bi bi-box-arrow-right"></i> Sign Out
					</a>
				</li>
			</ul>
		</div>
	</nav>
	
	<div class="container my-3">
		<jsp:doBody />
	</div>
	
	<app:sign-out />
	<script type="text/javascript" src="${root}/resources/js/signout.js"></script>
</body>
</html>