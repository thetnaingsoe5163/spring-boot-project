<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<app:layout-management title="Member">

	<div class="d-flex justify-content-between align-items-start mt-3">
		<app:page-title title="Member Management" />
		
		<button class="btn ${result.status() ? 'btn-danger' : 'btn-primary'}" id="changeStatusButton">
			<i class="bi ${result.status() ? 'bi-x' : 'bi-check'}"></i> ${result.status() ? 'Deactivate' : 'Activate'}
		</button>
	</div>
	
	<div class="row">
		<div class="col-3">
			<img src="${result.profileImage() eq null ?
			 	'%s/resources/photos/%s'.formatted(root, 'default-profile.png'): '%s/resources/photos/%s'.formatted(root, result.profileImage())}" 
				alt="default-profile" class="img-fluid img-thumbnail profile-img" />
		</div>
		
		<div class="col">
			<div class="row mb-3">
				<div class="col-4">
					<app:information-card label="Registered At" value="${dtf.formatLocalDateTime(result.registeredAt())}"
						icon="bi bi-person-plus" bgColor="reg-info-card-color" />
				</div>
				<div class="col-4">
					<app:information-card label="Last Access" value="${dtf.formatLocalDateTime(result.lastAccessAt())}"
						icon="bi bi-calendar-check" bgColor="last-access-info-card-color" />
				</div>
				<div class="col-4">
					<app:information-card label="Status" value="${result.status() ? 'Active' : 'Denied'}"
						icon="bi bi-shield" bgColor="status-info-card-color" />
				</div>
			</div>
			<div class="row">	
				<div class="col-6">
					<app:personal-info-card name="${result.name()}"
						dateOfBirth="${result.dateOfBirth()}" 
						gender="${result.gender()}" />					
				</div>
				<div class="col-6">
					<app:contact-card email="${result.email()}"
						phone="${result.phone()}" 
						address="${result.getFullAddress()}" />
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal" id="changeStatusDialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<form action="${root}/admin/member/${result.id()}/update" method="post">
					<sec:csrfInput/>
					<input type="hidden" name="status" value="${not result.status()}" />
					
					<div class="modal-header">
						<div class="modal-title">
							<h5>${result.status() ? 'Deactivate' : 'Activate'}</h5>
						</div>
					</div>			
					<div class="modal-body">
						<app:form-group label="Reason">
							<textarea name="reason" class="form-control"></textarea>
						</app:form-group>	
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-outline-primary">
							<i class="bi bi-save"></i> Change
						</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	
	<script type="text/javascript" src="${root}/resources/js/member-details.js">
</script>
</app:layout-management>