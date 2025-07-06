<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<app:admin-layout>
	<form action="${root}/admin/order/approve" class="container" method="post">
		<sec:csrfInput/>
		<input type="hidden" name="id" value="${form.id}" />
		<h3 class="d-flex justify-content-between mt-3 mb-3">
			${form.id}
			<button type="submit" class="btn btn-outline-success"> 
				<i class="bi bi-check2"></i> Approve
			</button>								
		</h3>
		<table class="table table-striped shadow">
			<thead>
				<tr>
					<th class="th-middle">Item Name</th>
					<th class="th-middle">Category</th>
					<th class="th-middle">Details</th>
					<th class="th-middle">Sale Price</th>
					<th class="th-middle">Quantity</th>
					<th class="th-middle">Total</th>	
					<th class="th-middle text-end">
						<button type="button" class="btn bg-transparent b-and-w-theme-1" id="reset">
							Reset <i class="bi bi-arrow-clockwise"></i>
						</button>													
					</th>		
				</tr>
			</thead>
			<tbody id="item-list">
				<c:forEach items="${form.orderItems}" var="item" varStatus="status">
					<input type="hidden" name="orderItems[${status.index}].saleId" value="${item.saleId}" />
					<input type="hidden" name="orderItems[${status.index}].itemId" value="${item.itemId}" />
					<input type="hidden" name="orderItems[${status.index}].name" value="${item.name}" />
					<input type="hidden" name="orderItems[${status.index}].category" value="${item.category}" />
					<input type="hidden" name="orderItems[${status.index}].details" value="${item.details}" />
					<input type="hidden" name="orderItems[${status.index}].deleted" value="${item.deleted}" id="deleted${status.index}" />
					<input type="hidden" name="orderItems[${status.index}].modified" value="${item.modified}" id="modified${status.index}" />
					<input id="price${status.index}" type="hidden" name="orderItems[${status.index}].salePrice"  value="${item.salePrice}" />
					
					<tr>
						<td class="td${status.index}">${item.name}</td>
						<td class="td${status.index}">${item.category}</td>
						<td class="td${status.index}">${item.details}</td>
						<td class="td${status.index}">${item.salePrice}</td>
						<td class="td${status.index}">
							<input id="quantity${status.index}" class="form-control quantity" type="number" name="orderItems[${status.index}].quantity" value="${item.quantity}" />
						</td>
						<td id="total${status.index}" class="total td${status.index}">${item.salePrice * item.quantity}</td>						
						<td class="td${status.index}">
							<textarea id="reason${status.index}" name="orderItems[${status.index}].reason" class="form-control" placeholder="Reason To Change" disabled="disabled"></textarea>
						</td>
					</tr>				
				</c:forEach>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="5" class="bg-dark text-white">Total Amount</td>
					<td colspan="2" id="allTotalTd" class="bg-dark text-white"></td>
				</tr>
			</tfoot>			
		</table>
	</form>
	
	<script src="${root}/resources/javascript/admin-order-details.js"></script>
</app:admin-layout>