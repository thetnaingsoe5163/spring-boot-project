<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<app:guest-layout>	
	<div class="container">
		<ul class="nav nav-underline justify-content-center">
			<li class="nav-item">
				<a href="#" data-uri="${root}/items/0" class="nav-link text-dark category-link">
					All
				</a>			
			</li>
			<c:forEach items="${categories}" var="category">
				<li class="nav-item">
					<a href="#" data-uri="${root}/items/${category.id()}" class="nav-link text-dark category-link">
						${category.name()}
					</a>									
				</li>
			</c:forEach>					
		</ul>
	</div>
	
	<main class="mt-4">
		<div class="container">
			<div class="row">
				<c:forEach items="${items}" var="item">
					<div class="col-lg-3 col-md-4 col-sm-6 mb-3">
						<app:item-menu-card 
							imagePath="${root}/resources/images/Nan_Gyi_Thoke.jpg"
							id="${item.id()}" categoryId="${item.categoryId()}" categoryName="${item.categoryName()}" 
							englishName="${item.englishName()}" burmeseName="${item.burmeseName()}" 
							description="${item.description()}" ingredients="${item.ingredients()}" />
					</div>				
				</c:forEach>																		
			</div>		
		</div>
	</main>
	
	<div class="modal" id="orderModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Order Card</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-6">
							<figure class="figure">
								<img data-image="${root}/resources/images/Ohn_No_Khao_Swe.jpg" 
									src="${root}/resources/images/Ohn_No_Khao_Swe.jpg" alt="${root}/resources/images/Ohn_No_Khao_Swe.jpg" class="figure-img img-fluid rounded" />							
							</figure>
						</div>
						<div class="col-6">
							<ul class="list-group list-group-flush">
								<li class="list-group-item">
									<div class="row">
										<div class="col-4">
											English Name
										</div>
										<div id="modal-english-name" class="col-auto">
											Mohnga
										</div>
									</div>
								</li>							
								<li class="list-group-item">
									<div class="row">
										<div class="col-4">
											Burmese Name
										</div>
										<div class="col-auto" id="modal-burmese-name">
											မုန့်ဟင်းခါး
										</div>
									</div>
								</li>
							</ul>					
						</div>
					</div>
					<p id="modal-description">
						Burmese traditional noodle salad with chicken and chili sauces
					</p>
					<div class="card mt-2">
						<div class="card-header">
							<h5 class="card-title">Main Ingredients</h5>						
						</div>
						<div class="card-body">
							<ul class="list-group list-group-flush" id="modal-ingredients">
							</ul>							
						</div>
					</div>
										
					<form action="${root}/guest/order/add" method="post" class="mt-3">
						<input type="hidden" name="id" id="id"/>
						<input type="hidden" name="quantity" id="hiddenQuantity" value="1" />
						<input type="hidden" name="englishName" id="hiddenBurmeseName"/>
						<input type="hidden" name="burmeseName" id="hiddenEnglishName"/>
						
						<div class="d-flex align-items-center mb-2">
							<h5>Quantity: </h5>
							<button class="btn" id="minusBtn">
								<i class="bi bi-dash-lg"></i>
							</button>						
							<span id="spanQuantity">1</span>
							<button class="btn" id="addBtn">
								<i class="bi bi-plus-lg"></i>
							</button>	
						</div>
						<div class="form-floating mt-3">
	  						<textarea name="details" class="form-control" placeholder="Tell chef order details" id="floatingTextarea"></textarea>
	  						<label for="floatingTextarea">Order Details</label>
						</div>
						<button type="submit" class="btn btn-primary mt-3">
							<i class="bi bi-cart-plus"></i> Add to cart
						</button>
					</form>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="${root}/resources/javascript/order-card.js">
</script>
</app:guest-layout>