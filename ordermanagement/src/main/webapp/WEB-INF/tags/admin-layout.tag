<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

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
</head>
<body>
	
	<nav class="navbar navbar-expand shadow">
		<div class="container">
			<a href="${root}/admin" class="navbar-brand">
				<i class="bi bi-fork-knife"></i> Restaurant 
			</a>
			<ul class="navbar-nav">
				<li class="nav-item align-items-center">
					<a href="${root}/admin/item/0" class="nav-link">
						<i class="bi bi-card-list"></i> Items
					</a>
				</li>
				<li class="nav-item dropdown">
					<a href="" class="nav-link dropdown-toggle" role="button" data-bs-toggle="dropdown">
						<i class="bi bi-plus-circle"></i> New
					</a>
					<ul class="dropdown-menu">
						<li>
							<a href="#" class="dropdown-item" id="addNewCategoryLink">
								<i class="bi bi-arrow-bar-right"></i> New Category
							</a>
						</li>
						<li>
							<a href="${root}/admin/item/new" class="dropdown-item">
								<i class="bi bi-arrow-bar-right"></i> New Item
							</a>
						</li>
					</ul>
				</li>
				<li class="nav-item align-items-center">
					<a href="" class="nav-link">
						logout
					</a>
				</li>
			</ul>
		</div>
	</nav>	
	
	
	<div>
		<jsp:doBody></jsp:doBody>
	</div>
	
	<div class="modal" id="addCategoryModal">
		<div class="modal-dialog">
			<sf:form action="${root}/admin/item/new/category" modelAttribute="form" method="post"
			 	id="addCategoryForm" class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Add New Category</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal" ></button>
				</div>
				<div class="modal-body">
					<label class="form-label">Enter New Category</label>
					<input type="text" name="name" class="form-control" placeholder="enter" />
					<c:forEach items="${result.fieldErrors}" var="error">
						<c:if test="${error.field == 'name'}">
							<span class="text-danger">${error.defaultMessage}</span>
						</c:if>
					</c:forEach>
					<input type="hidden" name="requestMadePath" value="${pageContext.request.requestURI}">
				</div>
				<div class="modal-footer">
					<button type="submit" id="modalBtn" class="btn btn-primary">Add</button>
				</div>
			</sf:form>
		</div>
	</div>
	
	<c:if test="${openModal == true}">
		<span style="display: none;" id="openModalFlag"></span>
	</c:if>
	
	<script type="text/javascript" src="${root}/resources/javascript/admin-layout.js"></script>
	
</body>
</html>
