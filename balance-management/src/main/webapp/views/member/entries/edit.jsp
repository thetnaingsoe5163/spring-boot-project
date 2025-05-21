	<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<app:layout-member title="${type.name()}">

	<app:page-title title="Edit ${type}" />
	
	<sf:form id="editForm" action="${root}/member/entry/${type.name().toLowerCase()}/save" modelAttribute="form" method="post" class="row">
		<sf:hidden path="id"/>
		
		<div class="col-4">
			<div class="card">
				<div class="card-header">
					<h4>
						<i class="bi bi-flag"></i> Ledger Entry
					</h4>
				</div>
				<div class="card-body">
					<app:form-group label="Ledger" cssClass="mb-3">
						<sf:select path="ledgerId" class="form-select">
							<option value="">Select Ledger</option>
							<c:forEach items="${ledgers}" var="ledger">
								<option value="${ledger.id()}" ${ledger.id() eq form.ledgerId ? 'selected' : ''}>${ledger.name()}</option>
							</c:forEach>
						</sf:select>
						<sf:errors path="ledgerId" cssClass="text-danger" />
					</app:form-group>
					
					<app:form-group label="Particular" cssClass="mb-3">
						<sf:textarea rows="3" cols="40" path="particular" class="form-control" 
							placeholder="Please enter particular."></sf:textarea>
					</app:form-group>
					
					<app:form-group label="Total Amount">
						<input type="text" class="form-control" id="totalAmount" />
					</app:form-group>
				</div>		
			</div>
		</div>
		
		<div class="col">
			<div class="card">
				<div class="card-header">
					<h4>
						<i class="bi bi-list"></i> Entry Items
					</h4>
				</div>
				<div class="card-body">
					<div class="row mb-2">
						<div class="col-6">
							<label>Item Name</label>
						</div>
						<div class="col-2">
							<label>Unit Price</label>
						</div>
						<div class="col-2">
							<label>Quantity</label>
						</div>
						<div class="col-2">
							<label>Total</label>
						</div>
					</div>
					<div id="entryItemContainer">
						<c:forEach var="item" varStatus="sts" items="${form.items}">
							<div class="row mb-2 ${item.deleted ? 'd-none': ''}">
								<sf:hidden path="items[${sts.index}].deleted"/>
								<div class="col-6">
									<div class="input-group">
										<sf:button type="button" data-index="${sts.index}" data-deleted-url="${root}/member/entry/${type.to()}/item/remove" class="btn btn-outline-danger input-group-text deleteButton">
											<i class="bi bi-trash"></i>
										</sf:button>
										<sf:input type="text" path="items[${sts.index}].itemName" class="form-control" placeholder="Enter Item Name" />
									</div>
								</div>
								<div class="col-2">
									<sf:input type="number" path="items[${sts.index}].unitPrice" class="form-control changesListenerForUnitPrice" />							
								</div>
								<div class="col-2">
									<sf:input type="number" path="items[${sts.index}].quantity" class="form-control changesListenerForQuantity" />
								</div>
								<div class="col-2">
									<span class="form-control" id="row${sts.index}">0</span>
								</div>
							</div>					
						</c:forEach>					
					</div>					
					<div class="mt-3">
						<sf:button id="addItemButton" data-add-url="${root}/member/entry/${type.to()}/item/add" type="button" class="btn btn-outline-primary">
							<i class="bi bi-plus"></i> Add Item
						</sf:button>
						<sf:button type="submit" class="btn btn-outline-danger">
							<i class="bi bi-save2"></i> Save Entry
						</sf:button>
					</div>
				</div>
			</div>
		</div>
	</sf:form>
	
	<script src="${root}/resources/js/member-entries.js"></script>
</app:layout-member>	