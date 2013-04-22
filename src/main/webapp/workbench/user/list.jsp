<%@ page import="java.util.*" %>
<%@ page import="com.wishop.model.User" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>

<h2><fmt:message key="users"/></h2>

<table>
  <thead>
    <th><fmt:message key="object.id"/></th>
    <th><fmt:message key="tag.name"/></th>
    <th><fmt:message key="user.email"/></th>
    <th><fmt:message key="user.accountActive"/></th>
  </thead>
  <c:forEach var="user" items="${userList}">
    <tr>
      <td><a href="/workbench/user/show/${user.id}">${user.id}</a></td>
      <td>${user.fullName}</td>
      <td>${user.email}</td>
      <td>${user.accountActive}</td>
    </tr>
  </c:forEach>
</table>
<table>
  <tr>
    <td>
      <spring:url value="/workbench/user/create" var="addUrl" />
      <a href="${fn:escapeXml(addUrl)}"><fmt:message key="button.add"/> <fmt:message key="user"/></a>
    </td>
  </tr>
</table>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>