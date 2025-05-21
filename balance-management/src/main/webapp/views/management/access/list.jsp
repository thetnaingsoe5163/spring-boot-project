<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<app:layout-management title="Access History">
	
	<app:page-title title="Access History" />
	
	<form action="" class="row" id="searchForm">
	
		<input type="hidden" name="page" id="pageInput" value="${result.page()}"/>
		<input type="hidden" name="size" id="sizeInput" value="${result.size()}"/>
		
		<app:form-group label="Status" cssClass="col-auto">
			<select name="status" class="form-select">
				<option value="">Search All</option>
				<option value="Success" ${param.status eq 'Success' ? 'selected' : ''}>Success</option>
				<option value="Fail" ${param.status eq 'Fail' ? 'selected' : ''}>Fail</option>
			</select>
		</app:form-group>
		
		<app:form-group label="Date From" cssClass="col-auto">
			<input type="date" name="dateFrom" value="${param.dateFrom}" class="form-control" />
		</app:form-group>
		
		<app:form-group label="Date To" cssClass="col-auto">
			<input type="date" name="dateTo" value="${param.dateTo}" class="form-control" />
		</app:form-group>
		
		<app:form-group label="Keyword" cssClass="col-auto">
			<input type="text" name="keyword" value="${param.keyword}" class="form-control" placeholder="Search Keyword" />
		</app:form-group>
		
		<div class="col-auto btn-wrapper">
			<button class="btn btn-primary">
				<i class="bi bi-search"></i> Search
			</button>
		</div>
	</form>
	
	<table class="table table-striped table-hover my-4">
		<thead>
			<tr>
				<th>Member</th>
				<th>Access At</th>
				<th>Access Type</th>
				<th>Status</th>
				<th>Remark</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${result.contents()}">
				<tr>
					<td>${item.member()}</td>
					<td>${dtf.formatLocalDateTime(item.getAccessedAtLocalDateTime())}</td>
					<td>${item.accessType()}</td>
					<td>${item.status()}</td>
					<td>${item.remark()}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<app:pagination pageResult="${result}"/>
	
	<script type="text/javascript" src="${root}/resources/js/pagination.js">
</script>
</app:layout-management>