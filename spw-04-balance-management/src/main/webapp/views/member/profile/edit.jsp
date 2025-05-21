<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<app:layout-member title="profile">

	<app:page-title title="Edit Profile" />
	
	<div class="row mb-5">
		<div class="col-3">
			<img src="${profileEditForm.profileImage eq null ?
			 '%s/resources/photos/%s'.formatted(root, 'default-profile.png'): '%s/resources/photos/%s'.formatted(root, profileEditForm.profileImage)}" 
				class="img-fluid" alt="profile-pic" />
			<form action="${root}/member/profile/photo" method="post" enctype="multipart/form-data"
				id="profilePhotoForm" class="mt-3">
				<sec:csrfInput/>
				<input type="file" name="profileImage" id="profilePhotoInput" class="d-none" />
				<button type="button" id="profilePhotoButton" class="btn btn-primary w-100">
					<i class="bi bi-camera"></i> Change Profile Picture
				</button>
			</form>
		</div>
		
		<div class="col">
			<sf:form method="post" modelAttribute="profileEditForm">
				<div class="row mb-3">
					<app:form-group label="Name" cssClass="col-8">
						<sf:input type="text" path="name" placeholder="Enter Name" class="form-control" />
						<sf:errors path="name" cssClass="text-danger" ></sf:errors>
					</app:form-group>
				</div>
				
				<div class="row mb-3">
					<div class="col-4">
						<app:form-group label="Gender">
							<sf:select path="gender" class="form-select">
								<option value="">Gender</option>
								<option value="Male" ${profileEditForm.gender eq 'Male' ? 'Selected' : ''}>Male</option>
								<option value="Female" ${profileEditForm.gender eq 'Female' ? 'Selected' : ''}>Female</option>
							</sf:select>
							<sf:errors path="gender" cssClass="text-danger" ></sf:errors>
						</app:form-group>
					</div>
					<div class="col-4">
						<app:form-group label="Date of Birth">
							<sf:input type="date" path="dateOfBirth" class="form-control" />
							<sf:errors path="dateOfBirth" cssClass="text-danger" ></sf:errors>
						</app:form-group>
					</div>
				</div>
				
				<div class="row mb-3">
					<div class="col-4">
						<app:form-group label="Phone">
							<sf:input type="tel" path="phone" class="form-control" placeholder="Enter Phone Number" />
							<sf:errors path="phone" cssClass="text-danger" ></sf:errors>
						</app:form-group>
					</div>
					
					<div class="col">
						<app:form-group label="Email">
							<sf:input type="email" path="email" class="form-control" placeholder="Enter Email Address"/>
							<sf:errors path="email" cssClass="text-danger" ></sf:errors>
						</app:form-group>
					</div>
				</div>
				
				<div class="row mb-3">
					<div class="col-4">
						<app:form-group label="Region">
							<select id="region" data-fetch-api="${root}/member/location/district" name="region" class="form-select">
								<option value="">Select Region</option>
								<c:forEach items="${regions}" var="region">
									<option value="${region.id}" 
										${regionByTownship ne null and regionByTownship.id eq region.id ? 'Selected' : ''}>
											${region.name}</option>
								</c:forEach>							
							</select>
						</app:form-group>
					</div>
					<div class="col-4">
						<app:form-group label="District">
							<select id="district" data-fetch-api="${root}/member/location/township" name="district" class="form-select">
								<option value="">Select District</option>
								<c:choose>
									<c:when test="${districtByTownship ne null}">
										<option value="${districtByTownship.id}" selected="selected">${districtByTownship.name}</option>
									</c:when>
								</c:choose>
							</select>
						</app:form-group>
					</div>
					<div class="col-4">
						<app:form-group label="Township">
							<sf:select id="township" path="township" class="form-select">
								<option value="">Select Township</option>
								<c:choose>
									<c:when test="${townshipById ne null}">
										<option value="${townshipById.id}" selected="selected">${townshipById.name}</option>
									</c:when>
								</c:choose>
							</sf:select>
							<sf:errors path="township" cssClass="text-danger" ></sf:errors>
						</app:form-group>
					</div>
				</div>
				
				<div class="row mb-3">
					<app:form-group label="Address">
						<sf:textarea path="address" rows="2" class="form-control" placeholder="Enter Address"></sf:textarea>
						<sf:errors path="address" cssClass="text-danger" ></sf:errors>
					</app:form-group>
				</div>
				
				<button type="submit" class="btn btn-danger">
					<i class="bi bi-save"></i> Save Profile
				</button>
			</sf:form>
		</div>
	</div>
	
	
	<script src="${root}/resources/js/profile-edit.js"></script>
	<<script src="${root}/resources/js/member-location.js"></script>
</app:layout-member>