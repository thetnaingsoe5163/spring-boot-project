<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<app:guest-layout>	
	<div class="container">
		<c:forEach items="${history}" var="h">
			<div class="card shadow mt-3">
				<div class="card-header d-flex justify-content-between">
					<h3 class="card-title d-flex justify-content-between">
						${h.saleId()}
					</h3>
					<h5>${h.status()}</h5>
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
							<c:forEach items="${h.items()}" var="item">
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
								<td class="bg-dark text-white">${h.getTotalQuantity()}</td>
								<td colspan="2" class="bg-dark text-white">${h.getTotalAmount()}</td>
							</tr>
						</tfoot>
					</table>								
				</div>
			</div>
		</c:forEach>

	</div>
</app:guest-layout>