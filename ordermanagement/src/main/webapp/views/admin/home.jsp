<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<app:admin-layout>
	<span class="d-none" id="context-path">${pageContext.request.contextPath}</span>
	<div class="container mt-3">
		<h1>Order Requests</h1>
		
		<c:if test="${not empty message}">
			<div class="alert alert-info">
				<i class="bi bi-info-circle"></i> ${message}
			</div>
		</c:if>
		
		<table id="inprogress" class="table table-striped table-hover mt-3">
			<thead>
				<tr>
					<th>Table Number</th>
					<th>ID</th>
					<th>Status</th>
					<th></th>
				</tr>
			</thead>
			<tbody id="order-list">
				<c:forEach items="${orders}" var="order">
					<tr id="${order.id()}">
						<td>${order.tableNumber()}</td>
						<td>${order.id()}</td>
						<td>${order.status()}</td>
						<td>
							<div class="d-flex gap-3 justify-content-end" role="group">
								<a href="${root}/admin/order/pay/${order.id()}" class="btn btn-outline-dark">
									Pay Bill
								</a>
								<a href="${root}/admin/order/details/${order.id()}" class="btn btn-outline-info">
									<i class="bi bi-info-circle-fill"></i> Details
								</a>
								<a href="${root}/admin/order/immediate-approve/${order.id()}" class="btn btn-outline-success">
									<i class="bi bi-check2"></i> Approve
								</a>								
							</div>
						</td>
					</tr>				
				</c:forEach>

			</tbody>
		</table>
	</div>
	<script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/stompjs@2.3.3/lib/stomp.min.js"></script>
	<script src="${root}/resources/javascript/admin-home.js"></script>
</app:admin-layout>