<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<form id="signOutForm" action="${root}/signout" method="post" class="d-none">
	<sec:csrfInput/>
</form>
