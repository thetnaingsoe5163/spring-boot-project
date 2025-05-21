<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="pageResult" type="com.jdc.online.balances.model.PageResult"%>

<div class="d-flex justify-content-between">
	
	<c:if test="${pageResult ne null}">
		<div class="d-flex">
			<c:if test="${not empty pageResult.getPageLinks()}">
				<div class="input-group">
					<span class="input-group-text">Size</span>
					<select name="size" class="form-select me-2" id="sizeSelect">
						<option value="10" ${pageResult.size() eq '10' ? 'Selected' : ''}>10</option>
						<option value="25" ${pageResult.size() eq '25' ? 'Selected' : ''}>25</option>
						<option value="50" ${pageResult.size() eq '50' ? 'Selected' : ''}>50</option>
					</select>
				</div>
				
				<div class="d-flex page-links">
					<a href="#" data-page-number="0" class="btn btn-outline-primary me-2 pageLink">
						<i class="bi bi-arrow-left"></i>
					</a>
					<c:forEach var="item" items="${pageResult.getPageLinks()}">
						<a href="#" data-page-number="${item}" class="${pageResult.page() eq item ? 'active' : ''} btn btn-outline-primary me-2 pageLink">
							${item + 1}
						</a>			
					</c:forEach>
					<a href="#" data-page-number="${pageResult.getTotalPageCount() - 1}" class="btn btn-outline-primary me-2 pageLink">
						<i class="bi bi-arrow-right"></i>
					</a>
				</div>
			</c:if>
		</div>
		
		<div class="d-flex">
			<div class="input-group me-2">
				<span class="input-group-text">Total pages</span>
				<span class="form-control">${pageResult.getTotalPageCount()}</span>
			</div>
			<div class="input-group">
				<span class="input-group-text">Total result</span>
				<span class="form-control">${pageResult.count()}</span>
			</div>
		</div>	
	</c:if>	
</div>
