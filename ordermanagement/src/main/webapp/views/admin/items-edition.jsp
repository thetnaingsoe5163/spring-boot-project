<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags"%>

<app:admin-layout>
	<div class="container-fluid mt-4">
		<div class="row">
			
			<div class="col-md-3">
				<h5>Categories</h5>
				<ul class="list-group">
					<a href="${root}/admin/item/edit/0"
						class="list-group-item list-group-item-action ${empty selectedCategoryId ? 'active' : ''}">
						All </a>
					<c:forEach var="category" items="${categories}">
						<a href="${root}/admin/item/${category.id()}"
							class="list-group-item list-group-item-action 
			          ${category.id() == selectedCategoryId ? 'active' : ''}">
							${category.name()} </a>
					</c:forEach>
				</ul>
			</div>


			<!-- Main Content: Item Cards -->
			<div class="col-md-9">
				<h5>Items</h5>
				<div class="row">
					<c:forEach var="item" items="${items}">
						<div class="col-lg-4 col-md-6 mb-4">
							<div class="card h-100 shadow">
								<div class="card-header">
									<div class="d-flex justify-content-between">
										<a href="${root}/admin/item/delete/${item.id()}"
											class="btn-link"
											onclick="return confirm('This will delete this item. Do you want to continue?')"> 
											<i class="bi bi-x-octagon-fill text-danger"></i>
										</a>
										<div class="d-flex justify-content-end gap-1">
											<a href="${root}/admin/item/details/${item.id()}"
												class="btn btn-outline-primary btn-sm"> 
												<i class="bi bi-info-circle"></i>
											</a>																		
											<a href="${root}/admin/item/edit/${item.id()}"
												class="btn btn-outline-primary btn-sm"> <i
												class="bi bi-pencil"></i> Edit
											</a>
										</div>																			
									</div>
								</div>
								<div class="card-body">
									<img src="${root}/resources/images/items/${item.image() eq null or item.image().isBlank() ? 'default-item.png' : item.image()}"
										class="card-img fixed-img-card-size mb-2" alt="item image">
									<h5 class="card-title">${item.englishName()}
										(${item.burmeseName()})</h5>
								</div>
							</div>
						</div>
					</c:forEach>

					<c:if test="${empty items}">
						<div class="col-12">
							<div class="alert alert-info">No items found for this
								category.</div>
						</div>
					</c:if>
				</div>
			</div>
		</div>
	</div>
</app:admin-layout>
