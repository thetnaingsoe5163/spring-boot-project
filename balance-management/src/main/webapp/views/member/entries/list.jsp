<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<app:layout-member title="${type.name()}">
	<app:page-title title="${type} Management" />
	
	<form action="" class="row">
		<app:form-group label="Date From" cssClass="col-auto" >
			<input type="date" name="dateFrom" value="${param.dateFrom}" class="form-control" />
		</app:form-group>
		
		<app:form-group label="Date To" cssClass="col-auto" >
			<input type="date" name="dateTo" value="${param.dateTo}" class="form-control" />
		</app:form-group>
		
		<app:form-group label="Keyword" cssClass="col-auto">
			<input type="text" name="keyword" value="${param.keyword}"
				placeholder="Search Keyword" class="form-control" />
		</app:form-group>
		
		<div class="col-auto btn-wrapper">
			<button class="btn btn-primary">
				<i class="bi bi-search"></i> Search
			</button>
			<a href="${root}/member/entry/${type.name().toLowerCase()}/create" class="btn btn-danger">
				<i class="bi bi-plus"></i> Add New
			</a>
		</div>
	</form>
	
	<table class="table table-striped table-hover my-3">
		<thead>
			<tr>
				<th>Code</th>
				<th>Issue At</th>
				<th>Ledger</th>
				<th>Particular</th>
				<th class="text-end">Amount</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${result.contents()}" var="item">
				<tr>
					<td>${item.code()}</td>
					<td>${dtf.formatLocalDateTime(item.issuedAt())}</td>
					<td>${item.ledgerName()}</td>
					<td>${item.particular()}</td>
					<td class="text-end">${item.amount()}</td>
					<td class="text-end">
						<a href="${root}/member/balance/${item.code()}">
							<i class="bi bi-arrow-right"></i>
						</a>
					</td>
				</tr>			
			</c:forEach>
		</tbody>
	</table>
	
	<app:pagination />
</app:layout-member>	