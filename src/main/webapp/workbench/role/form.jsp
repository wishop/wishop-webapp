<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<h2>
	<c:choose>
		<c:when test="${role.new}"><fmt:message key="role.new"/></c:when>
		<c:otherwise><fmt:message key="role"/></c:otherwise>
	</c:choose>
</h2>
<form:form modelAttribute="role" action="/workbench/role/save" method="post">
  <table>
    <tr>
      <th>
        <fmt:message key="tag.name"/><form:errors path="name" cssClass="errors"/>
        <br/>
        <form:input path="name" size="30" maxlength="80"/>
      </th>
    </tr>
    <tr>
      <td>
        <c:choose>
          <c:when test="${role.new}">
            <p class="submit"><input type="submit" value="<fmt:message key="button.add"/> <fmt:message key="role"/>"/></p>
          </c:when>
          <c:otherwise>
            <p class="submit"><input type="submit" value="<fmt:message key="button.update"/> <fmt:message key="role"/>"/></p>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
  </table>
</form:form>
