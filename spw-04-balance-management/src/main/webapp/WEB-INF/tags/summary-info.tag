<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="title" type="java.lang.String" %>
<%@ attribute name="icon" type="java.lang.String" %>
<%@ attribute name="value" type="java.lang.String" %>
<%@ attribute name="color" type="java.lang.String" %>

<div class="card ${color}">
	<div class="card-body">
		<div class="text-end">
			<span>${title}</span>
		</div>
		<h4 class="d-flex justify-content-between">
			<i class="${icon}"></i>		
			<span id="${title.toLowerCase()}">${value}</span>
		</h4>
	</div>
</div>
