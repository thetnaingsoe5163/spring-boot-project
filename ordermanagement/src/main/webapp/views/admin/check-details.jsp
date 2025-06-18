<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<app:admin-layout>
	<div class="container mt-5">
		<h2 class="mb-4">Order Item Details</h2>

		<div class="table-responsive">
			<table class="table table-striped align-middle">
				<thead class="table-dark text-center">
					<tr>
						<th>Item ID</th>
						<th>English Name</th>
						<th>Burmese Name</th>
						<th>Category</th>
						<th>Details</th>
						<th>Sale Price</th>
						<th>Remaining Qy</th>
						<th>Submitted Qy</th>
						<th>Modified</th>
						<th>Reason</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="item" items="${order}">
						<tr>
							<td>${item.itemId()}</td>
							<td>${item.englishName()}</td>
							<td>${item.burmeseName()}</td>
							<td>${item.category()}</td>
							<td>${item.details()}</td>
							<td>${item.salePrice()}</td>
							<td>${item.lastQuantity()}</td>
							<td>${item.previousQuantity()}</td>
							<td><c:choose>
									<c:when test="${item.modified()}">
										<span class="badge bg-warning text-dark">Yes</span>
									</c:when>
									<c:otherwise>
										<span class="badge bg-secondary">No</span>
									</c:otherwise>
								</c:choose></td>
							<td>${item.reason()}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>

</app:admin-layout>