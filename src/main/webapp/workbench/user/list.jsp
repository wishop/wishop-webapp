<%@ page import="java.util.*" %>
<%@ page import="com.wishop.model.User" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>

<h2><fmt:message key="cmsUsers"/></h2>

<table>
  <thead>
    <th><fmt:message key="cmsObject.id"/></th>
    <th><fmt:message key="tag.name"/></th>
    <th><fmt:message key="cmsUser.email"/></th>
    <th><fmt:message key="cmsUser.accountActive"/></th>
    <th><fmt:message key="cmsObject.deleted"/></th>
  </thead>
  <c:forEach var="user" items="${userList}">
    <tr>
      <td><a href="/workbench/cmsUser/show/${user.id}">${user.id}</a></td>
      <td>${user.fullName}</td>
      <td>${user.email}</td>
      <td>${user.accountActive}</td>
      <td>${user.deleted}</td>
    </tr>
  </c:forEach>
</table>
<table>
  <tr>
    <td>
      <spring:url value="/workbench/cmsUser/create" var="addUrl" />
      <a href="${fn:escapeXml(addUrl)}"><fmt:message key="button.add"/> <fmt:message key="cmsUser"/></a>
    </td>
  </tr>
</table>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>