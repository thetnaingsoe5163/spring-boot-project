<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<app:layout-member title="Ledgers">
	<app:page-title title="Ledgers Management" />
	
	<form action="" class="row">
		<input type="hidden" name="page" id="pageInput" />
		<input type="hidden" name="size" id="sizeInput" />
		
		<app:form-group label="Type" cssClass="col-auto">
			<select name="type" class="form-select">
				<option value="">All Type</option>
				<c:forEach items="${balanceTypes}" var="type">
					<option value="${type}" ${search.type eq type ? 'Selected' : ''} >${type}</option>
				</c:forEach>
			</select>
		</app:form-group>
		
		<app:form-group label="Keyword" cssClass="col-auto">
			<input name="keyword" value="${search.keyword}" type="text" placeholder="Search Keyword" class="form-control" />
		</app:form-group>
		
		<div class="col-auto btn-wrapper">
			<button type="submit" class="btn btn-primary">
				<i class="bi bi-search"></i> Search
			</button>			
		</div>
		
		<div class="col-auto btn-wrapper">
			<button type="button" data-bs-toggle="modal" data-bs-target="editDialog" id="addNewEntryBtn" class="btn btn-danger">
				<i class="bi bi-plus"></i> New Entry
			</button>
		</div>
		
		<script type="text/javascript" src="${root}/resources/js/ledger-management.js" ></script>
	</form>
	
	<table class="table table-striped table-hover">
		<thead>
			<tr>
				<th>ID</th>
				<th>Type</th>
				<th>Name</th>
				<th>Status</th>
				<th>Created At</th>
				<th>Modified At</th>
				<th class="text-end">Total</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${result.contents()}">
				<tr>
					<td>${item.id()}</td>
					<td>${item.type()}</td>
					<td>${item.name()}</td>
					<td>${item.status() ? 'Deleted' : 'Active'}</td>
					<td>${dtf.formatLocalDateTime(item.createdAt())}</td>
					<td>${dtf.formatLocalDateTime(item.modifiedAt())}</td>
					<td class="text-end">${item.total()}</td>
					<td class="text-end">
						<a href="#" class="edit-link" data-id="${item.id()}" 
							data-type="${item.type()}" data-name="${item.name()}" data-status="${item.status()}">
							<i class="bi bi-pencil"></i>
						</a>
					</td>
				</tr>			
			</c:forEach>
		</tbody>
	</table>
	
	<app:pagination pageResult="${result}" />
	<script type="text/javascript" src="${root}/resources/js/pagination.js"></script>
	
	<div class="modal fade" id="editDialog" tabindex="-1" aria-labelledby="editDialogLabel" aria-hidden="true">
		<div class="modal-dialog">
			<sf:form modelAttribute="ledgerForm" method="post" class="modal-content">
				<sf:input type="hidden" path="id" />
				
				<div class="modal-header">
					<h1 id="editDialogLabel" class="modal-title fs-5"> 
						<i class="bi bi-pencil"></i> Add New Category</h1>
					<button type="button" data-bs-dismiss="modal"
						class="btn-close" aria-label="Close" ></button>
				</div>
				<div class="modal-body">
					<app:form-group label="Type" cssClass="mb-3">
						<sf:select path="type" class="form-select">
							<option value="">Select Type</option>
							<c:forEach items="${balanceTypes}" var="type">
								<option value="${type}" ${type eq form.type ? 'Selected' : ''}>${type}</option>
							</c:forEach>
						</sf:select>
					</app:form-group>
					
					<app:form-group label="Ledger Name" cssClass="mb-3">
						<sf:input path="name" type="text" class="form-control" placeholder="Enter Ledger Name" />
					</app:form-group>
					
					<label class="form-label text-success" id="status-label">
						Ledger is active currently.
					</label>
					
					<div class="form-check">
						<sf:checkbox path="status" class="form-check-input"/>
						<label for="status" class="form-check-label" id="check-label" >Deleted</label> 
					</div>
				</div>
				
				<div class="modal-footer">
					<button type="submit" class="btn btn-primary">
						<i class="bi bi-save2"></i> Save Category
					</button>
				</div>
			</sf:form>
		</div>
	</div>

</app:layout-member>