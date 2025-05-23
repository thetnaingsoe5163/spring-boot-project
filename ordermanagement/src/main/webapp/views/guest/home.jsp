<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags/" %>

<app:guest-layout>	
	<div class="container">
		
		<ul class="nav nav-underline justify-content-center">
			<li class="nav-item">
				<a href="" class="nav-link text-dark">
					All
				</a>
			</li>		
			<li class="nav-item">
				<a href="" class="nav-link text-dark">
					Appetizers
				</a>
			</li>
			<li class="nav-item">
				<a href="" class="nav-link text-dark">
					Salads
				</a>
			</li>
			<li class="nav-item">
				<a href="" class="nav-link text-dark">
					Soups
				</a>
			</li>	
			<li class="nav-item">
				<a href="" class="nav-link text-dark">
					Noodle Dishes
				</a>
			</li>	
			<li class="nav-item">
				<a href="" class="nav-link text-dark">
					Desert
				</a>
			</li>			
			<li class="nav-item">
				<a href="" class="nav-link text-dark">
					Beverages
				</a>
			</li>																												
		</ul>
	</div>
	
	<main class="mt-4">
		<div class="container">
			<div class="row">
				<div class="col-lg-3 col-md-4 col-sm-6">
					<app:item-menu-card 
						imagePath="${root}/resources/images/Nan_Gyi_Thoke.jpg" 
						englishName="Nan Gyi Thoke" burmeseName="နန်းကြီးသုပ်" 
						description="Burmese traditional noodle salad with chicken and chili sauces" />
				</div>
				<div class="col-lg-3 col-md-4 col-sm-6">
					<app:item-menu-card 
						imagePath="${root}/resources/images/Mohnga.jpg" 
						englishName="Mohnga" burmeseName="မုန့်ဟင်းခါး" 
						description="Burmese Traditional noodle with fish soup" />
				</div>
				<div class="col-lg-3 col-md-4 col-sm-6">
					<app:item-menu-card 
						imagePath="${root}/resources/images/Myanmar_Traditional_Tea.jpg" 
						englishName="Myanmar Milk Tea" burmeseName="လက်ဖက်ရည်" 
						description="Burmese Traditional tea with milk" />					
				</div>
				<div class="col-lg-3 col-md-4 col-sm-6">
					<app:item-menu-card 
						imagePath="${root}/resources/images/Ohn_No_Khao_Swe.jpg" 
						englishName="Myanmar Noddle with coconut milk" burmeseName="အုန်းနို့ခေါက်ဆွဲ" 
						description="Burmese Traditional creamy noodle with coconut milk" />					
				</div>																				
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
										<div class="col-6">
											English Name
										</div>
										<div class="col-auto">
											Mohnga
										</div>
									</div>
								</li>							
								<li class="list-group-item">
									<div class="row">
										<div class="col-6">
											Burmese Name
										</div>
										<div class="col-auto">
											မုန့်ဟင်းခါး
										</div>
									</div>
								</li>
							</ul>					
						</div>
					</div>
					<p>
						Burmese traditional noodle salad with chicken and chili sauces
					</p>
					<div class="card mt-2">
						<div class="card-header">
							<h5 class="card-title">Main Ingredients</h5>						
						</div>
						<div class="card-body">
							<ul class="list-group list-group-flush">
								<li class="list-group-item">Noodle (မုန့်ဖတ်)</li>
								<li class="list-group-item">Fish soup (ငါးဟင်းရည်)</li>
							</ul>							
						</div>
					</div>
					<form action="${root}/guest/order" method="post" class="mt-3">
						<input type="hidden" name="id" value="1"/>
						<input type="hidden" name="quantity" id="hiddenQuantity" value="1" />
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
	  						<textarea class="form-control" placeholder="Tell chef order details" id="floatingTextarea"></textarea>
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