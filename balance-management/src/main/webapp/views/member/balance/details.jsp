<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<app:layout-member title="Balance">

	<div class="d-flex justify-content-between align-text-top">
		<app:page-title title="Entry Details" />
		
		<div>
			<c:if test="${validEdit}">
				<c:url value="${root}/member/entry/${details.type().name().toLowerCase()}/edit" var="edit">
					<c:param name="id" value="${details.code()}"></c:param>
				</c:url>
				<a href="${edit}" class="btn btn-danger">
					<i class="bi bi-pencil"></i> Edit Entry
				</a>			
			</c:if>
		</div>
	</div>
	
	<div class="row">
		<div class="col">
			<table class="table table-striped table-hover">
				<thead>
					<tr> 
						<th>No.</th>
						<th>Item Name</th>
						<th class="text-end">Price</th>
						<th class="text-end">Quantity</th>
						<th class="text-end">Total</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${details.items()}" var="item" varStatus="loop">
						<tr>
							<td>${loop.index + 1}</td>
							<td>${item.itemName()}</td>
							<td class="text-end">${item.price()}</td>
							<td class="text-end">${item.quantity()}</td>
							<td class="text-end">${item.total()}</td>
						</tr>					
					</c:forEach>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="4">All Total</td>
						<td class="text-end">100,000</td>
					</tr>
				</tfoot>
			</table>
		</div>
		<div class="col-3">
			<div class="card">
				<div class="card-body">
					<h5>
						<i class="bi bi-flag"></i> Incomes Summary
					</h5>
					<div class="list-group list-group-flush">
						<div class="list-group-item">
							<div>Code</div>
							<div>${details.code()}</div>
						</div>
						<div class="list-group-item">
							<div>Ledgers</div>
							<div>${details.ledgerName()}</div>
						</div>
						<div class="list-group-item">
							<div>Amount</div>
							<div>${details.amount()}</div>
						</div>
						<div class="list-group-item">
							<div>Issued At</div>
							<div>${dtf.formatLocalDateTime(details.issuedAt())}</div>
						</div>
						<div class="list-group-item">
							<div>Particular</div>
							<div>${details.particular()}</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</app:layout-member>