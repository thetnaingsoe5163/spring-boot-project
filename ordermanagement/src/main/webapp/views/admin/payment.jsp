<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<app:admin-layout>
	<div class="container mt-3">
		<h1 class="mb-4">Receipt</h1>
		
		<h5>${receipt.trxId}</h5>
		<table class="table table-striped hover">
			<thead>
				<tr>
					<th>Item</th>
					<th>Details</th>
					<th>Price</th>
					<th>Quantity</th>
					<th>Total</th>			
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${receipt.items}" var="item">
					<tr>
						<td>${item.englishName()} (${item.burmeseName()})</td>
						<td>${item.details()}</td>
						<td>${item.salePrice()}</td>
						<td>${item.quantity()}</td>
						<td>${item.totalPrice()}</td>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="4" class="bg-dark text-white">Total Amount</td>
					<td class="bg-dark text-white">${receipt.allTotalAmount()}</td>
				</tr>
			</tfoot>
		</table>
	</div>
</app:admin-layout>