<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<app:admin-layout>
	<div class="container">
		<h3 class="d-flex justify-content-between mt-3 mb-3">
			${id}
			<a href="${root}/admin/order/approve/${id}" class="btn btn-outline-success"> 
				<i class="bi bi-check2"></i> Approve
			</a>								
		</h3>
		<table class="table table-striped shadow">
			<thead>
				<tr>
					<th>Item Name</th>
					<th>Category</th>
					<th>Sale Price</th>
					<th>Quantity</th>
					<th>Total</th>			
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${items}" var="item">
					<tr>
						<td>${item.name()}</td>
						<td>${item.category()}</td>
						<td>${item.salePrice()}</td>
						<td>${item.quantity()}</td>
						<td class="total">${item.salePrice() * item.quantity()}</td>
					</tr>				
				</c:forEach>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="4" class="bg-dark text-white">Total Amount</td>
					<td id="allTotalTd" class="bg-dark text-white"></td>
				</tr>
			</tfoot>			
		</table>
	</div>
	
	<script src="${root}/resources/javascript/admin-order-details.js"></script>
</app:admin-layout>