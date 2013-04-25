<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<p>	<a href="<spring:url value="/" htmlEscape="true" />">Home</a> </p>
<p>	<a href="<spring:url value="/workbench" htmlEscape="true" />">Workbench</a> </p>
<p>
	<spring:url value="/workbench/user/list" var="listUsersUrl" />
	<a href="${fn:escapeXml(listUsersUrl)}"> <fmt:message key="users"/> </a>
</p>
<p> <a href="">Logout</a> </p>

jgfghjghsd
