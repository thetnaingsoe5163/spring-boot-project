<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<app:layout-member title="Home">
	<div class="d-flex justify-content-between align-items-start">
		<app:page-title title="Membere Home"></app:page-title>
		
		<div>
			<div class="btn-group" role="group">
			
			<c:url var="monthlySummaryUrl" value="${root}/member/chart/summary">
				<c:param name="type" value="Monthly" />
			</c:url>
			<c:url var="monthlyBalanceUrl" value="${root}/member/chart/balance">
				<c:param name="type" value="Monthly" />
			</c:url>
			<c:url var="monthlyLedgersUrl" value="${root}/member/chart/ledgers">
				<c:param name="type" value="Monthly" />
			</c:url>			
			<input data-summary-url="${monthlySummaryUrl}" data-balance-url="${monthlyBalanceUrl}" 
				data-ledgers-url="${monthlyLedgersUrl}"
				type="radio" name="display" id="monthly" checked class="btn-check"  />
			<label for="monthly" class="btn btn-outline-primary">Monthly</label>
			
			<c:url var="yearlySummaryUrl" value="${root}/member/chart/summary">
				<c:param name="type" value="Yearly" />
			</c:url>
			<c:url var="yearlyBalanceUrl" value="${root}/member/chart/balance">
				<c:param name="type" value="Yearly" />
			</c:url>
			<c:url var="yearlyLedgersUrl" value="${root}/member/chart/ledgers">
				<c:param name="type" value="Monthly" />
			</c:url>					
			<input data-summary-url="${yearlySummaryUrl}" data-balance-url="${yearlyBalanceUrl}"
			 	data-ledgers-url="${yearlyLedgersUrl}"
			 	type="radio" name="display" id="yearly" class="btn-check"  />
			<label for="yearly" class="btn btn-outline-primary">Yearly</label>
		</div>
		</div>
	</div>
	
	<div class="row">
		<div class="col-3">
			<div class="card">
				<div class="card-header">
					<div class="d-flex justify-content-between align-items-start">
						<h5>
							<i class="bi bi-person"></i> Profile
						</h5>
						<a href="${root}/member/profile">
							<i class="bi bi-pencil"></i>
						</a>
					</div>
				</div>
				<div class="card-body">
					<img src="${profile.profileImage() eq null ? 
						'%s/resources/photos/%s'.formatted(root, 'default-profile.png'): '%s/resources/photos/%s'.formatted(root, profile.profileImage())}" 
						alt="profile-image" class="img-fluid" />
					
					<div class="list-group list-group-flush">
						<div class="list-group-item">
							<i class="bi bi-person"></i> ${profile.name()}
						</div>
						<div class="list-group-item">
							<i class="bi bi-telephone"></i> ${profile.phone() eq null ? 'Undefined' : profile.phone()}
						</div>
						<div class="list-group-item">
							<i class="bi bi-envelope"></i> ${profile.email()}
						</div>
						<div class="list-group-item">
							<i class="bi bi-map"></i> ${profile.address().isBlank() ? 'Undefined' : profile.address()}
						</div>
					</div>
				</div>
			</div>
			
			<div class="card mt-3">
				<div class="card-body">
					<div class="d-flex justify-content-between align-items-start">
						<h5 class="card-title">
							<i class="bi bi-shield"></i> Access
						</h5>
						
						<a href="${root}/member/access" class="btn-link">
							<i class="bi bi-send"></i>
						</a>
					</div>
					
					<div class="list-group list-group-flush">
						<div class="list-group-item">
							<div class="fw-bold">
								Registered At
							</div>
							<div>${dtf.formatLocalDateTime(profile.registeredAt())}</div>
						</div>
						<div class="list-group-item">
							<div class="fw-bold">
								Last Access
							</div>
							<div>${dtf.formatLocalDateTime(profile.lastAccessAt())}</div> 
						</div>
					</div>
				</div>
			</div>	
		</div>
		
		<div class="col">
			<div class="row">
				<div class="col-4">
					<app:summary-info title="Expenses" icon="bi bi-cart" 
						value="Loading..." color="text-bg-danger" />
				</div>
				<div class="col-4">
					<app:summary-info title="Incomes" icon="bi bi-flag" 
						value="Loading..." color="text-bg-warning" />
				</div>
				<div class="col-4">
					<app:summary-info title="Balances" icon="bi bi-bar-chart" 
						value="Loading..." color="text-bg-primary" />
				</div>
			</div>
			
			<div class="card my-3">
				<div class="card-body">
					<h5 class="card-title">
						<i class="bi bi-bar-chart"></i> Balances
					</h5>
					
					<div id="chartdiv"></div>
				</div>
			</div>
			
			<div class="row">
				<div class="col-6">
					<div class="card">
						<div class="card-body">
							<h5 class="cart-title">
								<i class="bi bi-cart"></i> Expenses
							</h5>
							
							<div id="expensesChart" class="pieChart"></div>
						</div>
					</div>
				</div>
				<div class="col-6">
					<div class="card">
						<div class="card-body">
							<h5 class="cart-title">
								<i class="bi bi-flag"></i> Incomes
							</h5>
							
							<div id="incomesChart" class="pieChart"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<script src="https://cdn.amcharts.com/lib/5/index.js"></script>
	<script src="https://cdn.amcharts.com/lib/5/xy.js"></script>
	<script src="https://cdn.amcharts.com/lib/5/themes/Animated.js"></script>
	
	
	<script src="https://cdn.amcharts.com/lib/5/percent.js"></script>

	
	<script src="${root}/resources/js/member-home.js"></script>
	
</app:layout-member>