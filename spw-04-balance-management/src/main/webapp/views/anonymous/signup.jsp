<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<app:layout-anonymous title="Sign in">

	<div class="card login-form shadow">
		<div class="card-header">
			<h3>Sign Up</h3>
		</div>
		<div class="card-body">
			<c:if test="${message ne null}">
				<div class="alert alert-danger">
					<i class="bi bi-exclamation-octagon"></i> ${message}
				</div>
			</c:if>
			<sf:form modelAttribute="signUpForm" method="post">
				<app:form-group cssClass="mb-2" label="Name">
					<sf:input type="text" path="name" class="form-control" 
						placeholder="Enter Name"></sf:input>
					<sf:errors path="name" cssClass="text-danger"></sf:errors>
				</app:form-group>
				
				<app:form-group cssClass="mb-2" label="Login ID">
					<sf:input type="email" path="userName" class="form-control"
						placeholder="Enter Login ID"></sf:input>
					<sf:errors path="userName" cssClass="text-danger"></sf:errors>						
				</app:form-group>

				<app:form-group cssClass="mb-2" label="Password">
					<sf:input type="password" path="password" class="form-control"
						placeholder="Enter password"></sf:input>
					<sf:errors path="password" cssClass="text-danger"></sf:errors>						
				</app:form-group>

				<div>
					<a href="${root}/signin" class="btn btn-outline-primary mt-2">
						<i class="bi-unlock"></i> Sign In
					</a>
					
					<button type="submit" class="btn btn-primary mt-2" >
						<i class="bi-person-plus"></i> Sign Up
					</button>
				</div>
			</sf:form>
		</div>
	</div>

</app:layout-anonymous>