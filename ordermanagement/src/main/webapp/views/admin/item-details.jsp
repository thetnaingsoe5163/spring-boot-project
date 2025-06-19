<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<app:admin-layout>
	<div class="container py-5 d-flex justify-content-center">
		<div class="card item-card shadow-lg">			
			<img
				src="${pageContext.request.contextPath}/resources/images/items/${item.image() ne null ? item.image() : 'default-item.png'}"
				class="card-img-top item-image" alt="${item.englishName()}">
			<div class="card-body">
				<h3 class="card-title mb-3">${item.englishName()}
					<small class="text-muted">(${item.burmeseName()})</small>
				</h3>
				<h6 class="card-subtitle mb-4 text-muted">Category:
					${item.categoryName()}</h6>
				<p class="card-text">${item.description()}</p>

				<p class="mb-2">
					<span class="fw-bold">Price:</span> <span class="text-success">${item.unitPrice()}
						MMK</span>
				</p>
				
				<p class="fw-bold mb-1">Ingredients:</p>
				<ul class="list-group list-group-flush">
					<c:forEach var="ingredient" items="${item.ingredients()}">
						<li class="list-group-item">${ingredient}</li>
					</c:forEach>
				</ul>				
			</div>
		</div>
	</div>
</app:admin-layout>