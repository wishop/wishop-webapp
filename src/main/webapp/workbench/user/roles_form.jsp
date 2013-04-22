<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>
<h2>
	<c:choose>
		<c:when test="${user.new}"><fmt:message key="cmsUser.new"/></c:when>
		<c:otherwise><fmt:message key="cmsUser"/></c:otherwise>
	</c:choose>
</h2>
<form:form modelAttribute="cmsUser" action="saveRoles" method="post">
  <table>
    <tr>
      <th>
        <fmt:message key="cmsRoles"/><form:errors path="roles" cssClass="errors"/>
        <br/>
        <form:select path="roles">
          <c:forEach var="role" items="${roles}">
            <form:option value="${role}" label="${role.name}"/>
          </c:forEach>
        </form:select>
      </th>
    </tr>
    <tr>
      <td>
      	<p class="submit"><input type="submit" value="<fmt:message key="button.update"/> <fmt:message key="cmsUser"/>"/></p>
      </td>
    </tr>
  </table>
</form:form>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>
