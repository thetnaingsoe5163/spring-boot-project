<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="phone" type="java.lang.String" %>
<%@ attribute name="email" type="java.lang.String" required="true" %>
<%@ attribute name="address" type="java.lang.String" %>

<div class="card w-100 text-bg-light">
	<div class="card-body">
		<div class="mb-2">
			<h5>
				<i class="bi bi-telephone"></i> Contact Info
			</h5>
		</div>
		<div class="mb-3">
			<span class="text-secondary">Phone</span>
			<div>${phone ne null and not phone.isBlank() ? phone : 'Undefined'}</div>
		</div>
		<div class="mb-3">
			<span class="text-secondary">Email</span>
			<div>${email}</div>
		</div>
		<div class="mb-3">
			<span class="text-secondary">Address</span>
			<div>${address ne null and not address.isBlank() ? address : 'Undefined'}</div>
		</div>
		<br> 
	</div>
</div>