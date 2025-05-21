<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<app:layout-member title="Access">

	<app:page-title title="Member Access" />
	
	<form id="searchForm" class="row">
		<input type="hidden" name="page" id="pageInput" />
		<input type="hidden" name="size" id="sizeInput" />
		
		<div class="col-auto">
			<app:form-group label="Status">
				<select name="status" class="form-select">
					<option value="">Search All</option>
					<option value="Success" ${param.status eq 'Success' ? 'Selected' : ''}>Success</option>
					<option value="Fail" ${param.status eq 'Fail' ? 'Selected' : ''}>Fail</option>
				</select>
			</app:form-group>
		</div>
		
		<div class="col-auto">
			<app:form-group label="Date From">
				<input type="date" name="dateFrom" value="${param.dateFrom}" class="form-control" />
			</app:form-group>
		</div>
		
		<div class="col-auto">
			<app:form-group label="Date To">
				<input type="date" name="dateTo" value="${param.dateTo}" class="form-control" />
			</app:form-group>
		</div>
		
		<div class="col-auto">
			<app:form-group label="Keyword">
				<input type="text" name="keyword" value="${param.keyword}" 
					placeholder="Search Keyword" class="form-control" />
			</app:form-group>
		</div>
		
		<div class="col-auto">
			<div class="btn-wrapper">
				<button type="submit" class="btn btn-primary">
					<i class="bi bi-search"></i> Search
				</button>
			</div>
		</div>
	</form>

	
	<table class="table table-striped table-hover my-3">
		<thead>
			<tr>
				<th>Access At</th>
				<th>Access Type</th>
				<th>Status</th>
				<th>Remark</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${result.contents()}">
				<tr>
					<td>${dtf.formatLocalDateTime(item.getAccessedAtLocalDateTime())}</td>
					<td>${item.accessType()}</td>
					<td>${item.status()}</td>
					<td>${item.remark()}</td>
				</tr>				
			</c:forEach>
		</tbody>
	</table>
	
	<app:pagination pageResult="${result}" />
	<script type="text/javascript" src="${root}/resources/js/pagination.js"></script>
</app:layout-member>