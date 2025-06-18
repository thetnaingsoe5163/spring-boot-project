<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<app:guest-layout>	
	<div class="container">
		<div class="card shadow mt-3">
			<div class="card-header d-flex justify-content-between">
				<h5>${history.status()}</h5>
			</div>
			<div class="card-body">
				<table class="table table-striped">
					<thead>
						<tr>
							<th>Item</th>
							<th>Price</th>
							<th>Quantity</th>
							<th>Total</th>
							<th>Details</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${history.items()}" var="item">
							<tr>
								<td>${item.engName()} (${item.burName()})</td>
								<td>${item.price()}</td>
								<td>${item.quantity()}</td>
								<td>${item.getTotal()}</td>
								<td><p>${item.details()}</p></td>
							</tr>
						</c:forEach>
					</tbody>
					<tfoot>
						<tr>
							<td colspan="2" class="bg-dark text-white">Total</td>
							<td class="bg-dark text-white">${history.getTotalQuantity()}</td>
							<td colspan="2" class="bg-dark text-white">${history.getTotalAmount()}</td>
						</tr>
					</tfoot>
				</table>								
			</div>
		</div>
	</div>
</app:guest-layout>