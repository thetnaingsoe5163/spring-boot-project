<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<app:admin-layout>
	<div class="container">
		<sf:form action="${root}/admin/item" enctype="multipart/form-data" modelAttribute="itemEditingForm" method="post" 
			class="card mt-4 w-50" id="createItemForm">
			<div class="card-header">
				<div class="d-flex justify-content-between">
					<h4 class="card-title">Create New Item</h4>		
					<button type="submit" id="formBtn" class="btn btn-outline-primary">
						<i class="bi bi-send-fill"></i> Submit
					</button>									
				</div>

			</div>
			<div class="card-body">
				<input type="hidden" name="itemId" value="${form.itemId}">
				<div class="row">
					<div class="col-4">
						<label class="form-label">Category:</label>
					</div>
					<div class="col-8">
						<sf:select path="category" class="form-select">
							<option value="">Select Category</option>
							<c:forEach items="${categories}" var="category" >
								<option ${form.category eq category.id() ? 'selected' : ''} value="${category.id()}">${category.name()}</option>							
							</c:forEach>
						</sf:select>
						<sf:errors path="category" cssClass="text-danger"></sf:errors>
					</div>
				</div>
				<div class="row mt-3">
					<div class="col-4">
						<label class="form-label">English Name:</label>
					</div>
					<div class="col-8">
						<sf:input path="englishName" type="text" class="form-control" />
						<sf:errors path="englishName" cssClass="text-danger"></sf:errors>
					</div>
				</div>
				<div class="row mt-3">
					<div class="col-4">
						<label class="form-label">Burmese Name:</label>
					</div>
					<div class="col-8">
						<sf:input path="burmeseName" type="text" class="form-control" />
						<sf:errors path="burmeseName" cssClass="text-danger"></sf:errors>
					</div>
				</div>
				<div class="row mt-3">
					<div class="col-4">
						<label class="form-label">Unit Price</label>
					</div>
					<div class="col-8">
						<sf:input path="unitPrice" type="number" class="form-control" />
						<sf:errors path="unitPrice" cssClass="text-danger"></sf:errors>
					</div>
				</div>
				<div class="row mt-3">
					<div class="col-4">
						<label class="form-label">Description:</label>
					</div>
					<div class="col-8">
						<sf:textarea path="description" class="form-control"></sf:textarea>
					</div>
				</div>
				<div class="row mt-3">
					<div class="col-4">
						<label class="form-label">Ingredient:</label>
					</div>
					<div class="col-8">
						<div class="card">
							<div class="card-header"> 
								<div class="row">
									<div class="col-8">
										<label class="form-label">Number of ingredients</label>
									</div>
									<div class="col-4">
										<input type="number" id="ingredientCount" class="form-control" />
									</div>
								</div>
							</div>
							<div class="card-body">
								<ul class="list-group list-group-flush" id="listGroup">
									<c:if test="${!form.ingredients.isEmpty()}">
										<c:forEach items="${form.ingredients}" var="item" varStatus="status">
											<li id="${status.index}" class="list-group-item">
												<div class="input-group">
													<input type="text" value="${item}" data-deleted="false" name="ingredients[${status.index}]" 
														class="form-control" />
													<button class="btn deleteBtn" id="map${status.index}">
														<i class="bi bi-trash text-danger"></i>
													</button>
												</div>														
											</li>									
										</c:forEach>
									</c:if>																		
								</ul>
							</div>
							<div class="card-footer">
								<button type="button" class="btn btn-primary" id="addBtn">
									<i class="bi bi-plus"></i> Add One
								</button>
							</div>
						</div>
					</div>
				</div>
				<div class="row mt-3">
					<div class="col-4">
						<label class="form-label">Image:</label>
					</div>
					<div class="col-8">
						<button id="imageBtn" type="button" class="btn btn-outline-primary">Upload</button>
						<sf:input id="imageInput" type="file" path="imageFile" accept="image/*" cssClass="d-none" />
						<img id="preview" src="${form.imageName ne null ? root.concat('/resources/images/items/').concat(form.imageName) : ''}" alt="preview" class="img-thumbnail ${form.imageName ne null ? '' : 'd-none'} mt-3" />
					</div>
				</div>												
			</div>			
		</sf:form>
	</div>
	
	<script src="${root}/resources/javascript/admin-create-item.js"></script>
</app:admin-layout>