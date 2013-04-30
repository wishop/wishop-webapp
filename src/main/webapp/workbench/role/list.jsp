<%@ page import="com.wishop.model.Role" %>
<%@ include file="/WEB-INF/jsp/includes.jsp" %>

<h2><fmt:message key="roles"/></h2>

<table>
  <thead>
    <th><fmt:message key="object.id"/></th>
    <th><fmt:message key="tag.name"/></th>
  </thead>
  <c:forEach var="role" items="${roleList}">
    <tr>
      <td><a href="show/${role.id}">${role.id}</a></td>
      <td>${role.name}</td>
    </tr>
  </c:forEach>
</table>
<table>
  <tr>
    <td>
      <spring:url value="create" var="addUrl" />
      <a href="${fn:escapeXml(addUrl)}"><fmt:message key="button.add"/> <fmt:message key="role"/></a>
    </td>
  </tr>
</table>