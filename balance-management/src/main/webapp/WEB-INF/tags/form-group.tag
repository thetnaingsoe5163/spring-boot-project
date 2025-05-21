<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="label" type="java.lang.String" required="true" %>
<%@ attribute name="cssClass" type="java.lang.String" %>

<div class="${cssClass}">
	<label class="form-label">${label}</label>
	
	<jsp:doBody />
</div>