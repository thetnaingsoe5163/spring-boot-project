<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="label" type="java.lang.String" required="true" %>
<%@ attribute name="icon" type="java.lang.String" %>
<%@ attribute name="value" type="java.lang.String" %>
<%@ attribute name="bgColor" type="java.lang.String" %>

<div class="card ${bgColor}">
	<div class="card-body">
		<h4><i class="${icon}"></i> ${label}</h4>
		<h5 class="mt-3">${value}</h5>
	</div>
</div>