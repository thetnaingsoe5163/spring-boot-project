<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<app:layout-management title="Home">
	
	<div class="d-flex justify-content-between">
		<app:page-title title="Admin Home" />
		
		<div>
			<div class="btn-group" role="group">
				
				<c:url var="monthlyApi" value="${root}/admin/home/load">
					<c:param name="type" value="Monthly" />
				</c:url>			
				<input data-rest-api="${monthlyApi}" type="radio" name="display" id="monthly" checked class="btn-check"  />
				<label for="monthly" class="btn btn-outline-primary">Monthly</label>
				
				<c:url var="yearlyApi" value="${root}/admin/home/load">
					<c:param name="type" value="Yearly"></c:param>
				</c:url>					
				<input data-rest-api="${yearlyApi}" type="radio" name="display" id="yearly" class="btn-check"  />
				<label for="yearly" class="btn btn-outline-primary">Yearly</label>
			</div>
		</div>
	</div>
	
	<div class="row">
		<div class="col-3">
			<div class="card mb-3 text-bg-light">
				<div class="card-header">
					<h5 class="card-title">Last Month</h5>
				</div>
				<div class="card-body">
					<h4>${vo.lastMonth()}</h4>
				</div>
			</div>
			<div class="card mb-3 text-bg-secondary">
				<div class="card-header">
					<h5 class="card-title">Last Year</h5>
				</div>
				<div class="card-body">
					<h4>${vo.lastYear()}</h4>
				</div>
			</div>
			<div class="card mb-3 text-bg-success">
				<div class="card-header">
					<h5 class="card-title">Total Members</h5>
				</div>
				<div class="card-body">
					<h4>${vo.totalMembers()}</h4>
				</div>
			</div>
		</div>
		
		<div class="col">
			<div class="card">
				<div class="card-body">
					<h5 class="card-title">
						<i class="bi bi-people"></i> Member Registrations
					</h5>
					
					<div id="adminChart"></div>
				</div>
			</div>
		</div>
	</div>
	
	<script src="https://cdn.amcharts.com/lib/5/index.js"></script>
	<script src="https://cdn.amcharts.com/lib/5/xy.js"></script>
	<script src="https://cdn.amcharts.com/lib/5/themes/Animated.js"></script>
	<script src="${root}/resources/js/management-home.js"></script>
</app:layout-management>