<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<app:layout-anonymous title="Sign in">

	<div class="card login-form shadow">
		<div class="card-header">
			<h3>Sign In</h3>
		</div>
		<div class="card-body">
			<c:if test="${message ne null}">
				<div class="alert alert-info">
					<i class="bi bi-info-circle"></i> ${message}
				</div>
			</c:if>
			<c:if test="${param.error ne null}">
				<div class="alert alert-danger">
					${param.error}
				</div>
			</c:if>
			<form action="#" method="post">
				<sec:csrfInput/>
				<app:form-group cssClass="mb-2" label="login ID">
					<input name="username" type="text" class="form-control" 
						placeholder="Enter login ID">
				</app:form-group>

				<app:form-group cssClass="mb-2" label="Password">
					<input name="password" type="password" class="form-control"
						placeholder="Enter password">
				</app:form-group>

				<div>
					<a href="${root}/signup" class="btn btn-outline-primary mt-2"> <i
						class="bi-person-plus"></i> Sign Up
					</a>

					<button class="btn btn-primary mt-2">
						<i class="bi-unlock"></i> Sign In
					</button>
				</div>
			</form>
		</div>
	</div>

</app:layout-anonymous>