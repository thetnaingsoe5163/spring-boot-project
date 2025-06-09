<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<app:guest-layout>
	
	<div class="container mt-3">
		<h3 class="mb-2">Order Confirmation</h3>
		
		<table class="table table-hover">
			<thead>
				<tr>
					<th>Items</th>
					<th>Details</th>
					<th>Unit Price</th>
					<th class="text-center">Quantity</th>
					<th class="text-end">Total</th>
					<th></th>
				</tr>
			</thead>
			<tbody class="table-group-divider" id="table-body">
				<sf:form modelAttribute="orderForm"
					data-remove="${root}/guest/order/remove"
					data-save="${root}/guest/order"
					action="${root}/guest/order" method="post" id="orderDetailsForm">
					<c:forEach items="${form.items}" var="item" varStatus="status">
						<tr id="${status.index}">
							<td>${item.englishName} (${item.burmeseName})</td>
							<td>
								<sf:input path="items[${status.index}].details" type="text" class="form-control"
									value="${item.details}"
								/>
							</td>
							<td>3000 MMK</td>
							<td>
								<sf:input path="items[${status.index}].deleted" cssClass="d-none" id="deleted${status.index}"/>
								<div class="d-flex align-items-center mb-2 justify-content-center">
									<button class="btn" id="minusBtn${status.index}">
										<i class="bi bi-dash-lg"></i>
									</button>						
									<sf:input id="hiddenQuantity${status.index}" path="items[${status.index}].quantity" cssClass="d-none"/>
									<span id="spanQuantity${status.index}">${item.quantity}</span>
									<button class="btn" id="addBtn${status.index}">
										<i class="bi bi-plus-lg"></i>
									</button>	
								</div>							
							</td>
							<td class="text-end">6000 MMK</td>
							<td class="text-end">
								<i class="bi bi-trash3 text-danger"></i>
							</td>
						</tr>					
					</c:forEach>
				</sf:form>					
			</tbody>
			<tfoot>
				<tr class="table-group-divider">
					<td colspan="3">Total Amount</td>
					<td class="text-end">6000 MMK</td>
					<td></td>
				</tr>
				<tr>
					<td colspan="3"></td>
					<td colspan="2">
						<button form="orderDetailsForm" type="submit" class="btn btn-primary w-100">
							Order Now
						</button>
					</td>
				</tr>
			</tfoot>
		</table>		
	</div>
	
	<script type="text/javascript" src="${root}/resources/javascript/order-details.js">
</script>
</app:guest-layout>