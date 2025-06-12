<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<app:admin-layout>
	<span class="d-none" id="context-path">${pageContext.request.contextPath}</span>
	<div class="container">
		<h1>This is admin page</h1>
		
		<table class="table table-striped table-hover">
			<thead>
				<tr>
					<th>ID</th>
					<th>Status</th>
					<th></th>
				</tr>
			</thead>
			<tbody id="order-list">
				<c:forEach items="${sales}" var="sale">
					<tr>
						<td>${sale.id()}</td>
						<td>${sale.status()}</td>
						<td>
							<div class="d-flex gap-3 justify-content-end" role="group">
								<a href="${root}/admin/order/details/${sale.id()}" class="btn btn-outline-info">
									<i class="bi bi-info-circle-fill"></i> Details
								</a>
								<a href="${root}/admin/order/immediate-approve/${sale.id()}" class="btn btn-outline-success">
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