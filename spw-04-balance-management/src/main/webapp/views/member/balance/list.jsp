<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<app:layout-member title="Balance">
	<app:page-title title="Balance Report" />
	
	<form action="" class="row">
		<app:form-group label="Date From" cssClass="col-auto">
			<input type="date" name="dateFrom" value="${param.dateFrom}" class="form-control" />
		</app:form-group>
		
		<app:form-group label="Date To" cssClass="col-auto">
			<input type="date" name="dateTo" value="${param.dateTo}" class="form-control" />
		</app:form-group>
		
		<div class="col-auto btn-wrapper">
			<button class="btn btn-primary" type="submit">
				<i class="bi bi-search"></i> Search
			</button>
		</div>				
	</form>
	
	<table class="table table-striped table-hover my-3">
		<thead>
			<tr>
				<th>Issued At</th>
				<th>Ledger</th>
				<th>Particular</th>
				<th class="text-end">Expenses</th>
				<th class="text-end">Incomes</th>
				<th class="text-end">Balance</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${result.contents()}" var="item">
				<tr>
					<td>${dtf.formatLocalDateTime(item.issuedAt())}</td>
					<td>${item.ledger()}</td>
					<td>${item.particular()}</td>
					<td class="text-end">${item.expenses()}</td>
					<td class="text-end">${item.incomes()}</td>
					<td class="text-end">${item.balance()}</td>
					<td class="text-end">
						<a href="${root}/member/balance/${item.code()}">
							<i class="bi bi-arrow-right"></i>
						</a>
					</td>
				</tr>				
			</c:forEach>
		</tbody>
	</table>
	
	<app:pagination pageResult="${result}" />
</app:layout-member>	


