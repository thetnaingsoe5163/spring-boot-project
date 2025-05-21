<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<app:layout-management title="Member">
	<app:page-title title="Member Management" />
	
	<form action="" class="row">
		<app:form-group label="Status" cssClass="col-auto">
			<select name="status" class="form-select">
				<option value="">Search All</option>
				<option value="Active" ${param.status eq 'Active' ? 'Selected' : ''}>Active</option>
				<option value="Denied" ${param.status eq 'Denied' ? 'Selected' : ''}>Disable</option>
			</select>
		</app:form-group>
		
		<app:form-group label="Register from" cssClass="col-auto">
			<input name="dateFrom" type="date" value="${param.dateFrom}" class="form-control" >
		</app:form-group>
		
		<app:form-group label="Register to" cssClass="col-auto">
			<input name="dateTo" type="date" value="${param.dateTo}" class="form-control" >
		</app:form-group>
		
		<app:form-group label="Name" cssClass="col-auto">
			<input name="name" type="text" value="${param.name}" 
				class="form-control" placeholder="Search Name" />
		</app:form-group>
		
		<div class="col btn-wrapper">
			<button class="btn btn-primary">
				<i class="bi bi-search"></i> Search
			</button>
		</div>
	</form>
	
	<table class="table table-striped table-hover my-3">
		<thead>
			<tr>
				<th>ID</th>
				<th>Name</th>
				<th>Registered at</th>
				<th>Last login</th>
				<th>Status</th>
				<th>Change at</th>
				<th>Remark</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${result.contents()}" var="member">
				<tr>
					<td>${member.id()}</td>
					<td>${member.name()}</td>
					<td>${dtf.formatLocalDateTime(member.registeredAt())}</td>
					<td>${dtf.formatLocalDateTime(member.lastLogin())}</td>
					<td>${member.status()}</td>
					<td>${dtf.formatLocalDateTime(member.changedAt())}</td>
					<td>${member.remark()}</td>
					<td>
						<a href="${root}/admin/member/${member.id()}"><i class="bi bi-arrow-right"></i></a>
					</td>
				</tr>			
			</c:forEach>
		</tbody>
	</table>
	
	<app:pagination pageResult="${result}"/>
</app:layout-management>