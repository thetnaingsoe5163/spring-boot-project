<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="name" type="java.lang.String" required="true"%>
<%@ attribute name="dateOfBirth" type="java.lang.String"%>
<%@ attribute name="gender" type="java.lang.String"%>


<div class="card w-100 text-bg-light">
	<div class="card-body">
		<div class="mb-2">
			<h5>
				<i class="bi bi-person"></i> Personal Info
			</h5>
		</div>
		<div class="mb-3">
			<span class="text-secondary">Name</span>
			<div>${name}</div>
		</div>
		<div class="mb-3">
			<span class="text-secondary">Date of birth</span>
			<div>${dateOfBirth ne null and not dateOfBirth.isBlank() ? dtf.formatLocalDate(result.dateOfBirth()) : 'Undefined'}</div>
		</div>
		<div class="mb-3">
			<span class="text-secondary">Gender</span>
			<div>${gender ne null and not gender.isBlank() ? gender : 'Undefined'}</div>
		</div>
		<br>
	</div>
</div>