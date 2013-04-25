<%@ include file="/WEB-INF/jsp/includes.jsp" %>

<h2><fmt:message key="user.details"/></h2>

  <table>
    <tr>
      <th><fmt:message key="object.id"/></th>
      <td><b>${user.id}</b></td>
    </tr>
    <tr>
      <th><fmt:message key="tag.name"/></th>
      <td><b>${user.fullName}</b></td>
    </tr>
    <tr>
      <th><fmt:message key="user.email"/></th>
      <td>${user.email}</td>
    </tr>
    <tr>
      <th><fmt:message key="user.telephone"/></th>
      <td>${user.telephone}</td>
    </tr>
    <tr>
      <th><fmt:message key="user.mobile"/></th>
      <td>${user.mobile}</td>
    </tr>
    <tr>
      <th><fmt:message key="user.fax"/></th>
      <td>${user.fax}</td>
    </tr>
    <tr>
      <th><fmt:message key="user.dateOfBirth"/></th>
      <td><fmt:formatDate value="${user.dateOfBirth}" pattern="yyyy-MM-dd"/></td>
    </tr>
    <tr>
      <th><fmt:message key="auditInfo.creationTimestamp"/></th>
      <td><fmt:formatDate value="${user.auditInfo.creationTimestamp}" pattern="yyyy-MM-dd H:MM:ss"/></td>
    </tr>
    <tr>
      <th><fmt:message key="auditInfo.modificationTimestamp"/></th>
      <td><fmt:formatDate value="${user.auditInfo.modificationTimestamp}" pattern="yyyy-MM-dd H:MM:ss"/></td>
    </tr>
    <tr>
      <th><fmt:message key="user.accountActive"/></th>
      <td>${user.accountActive}</td>
    </tr>
    <tr>
      <th><fmt:message key="user.profile"/></th>
      <td>${user.profile}</td>
	</tr>
    <tr>
      <th><fmt:message key="address"/></th>
      <td>${user.address.fullAddress}</td>
    </tr>
  </table>
 
    <table class="table-buttons">
    <tr>
      <td>
        <spring:url value="/workbench/user/create" var="addUrl" />
        <a href="${fn:escapeXml(addUrl)}"><fmt:message key="button.add"/> <fmt:message key="user"/></a>
      </td>
      <td>
        <spring:url value="/workbench/user/edit/{userId}" var="editUrl">
        	<spring:param name="userId" value="${user.id}" />
        </spring:url>
        <a href="${fn:escapeXml(editUrl)}"><fmt:message key="button.edit"/> <fmt:message key="user"/></a>
      </td>
      <td>
        <spring:url value="/workbench/user/changePassword/{userId}" var="editUrl">
        	<spring:param name="userId" value="${user.id}" />
        </spring:url>
        <a href="${fn:escapeXml(editUrl)}"><fmt:message key="button.changePassword"/></a>
      </td>
      
      <c:choose>
	  	<c:when test="${user.accountActive}">
	      <td colspan="2" align="center">
	        <spring:url value="/workbench/user/disableAccount" var="disableAccountUrl" />
	        <form id="disableUserForm" action="${disableAccountUrl}" method="POST">
	            <input name="id" type="hidden" value="${user.id}"/>
	            <p class="submit"><input type="submit" value="<fmt:message key="button.accountInactive"/>"/></p>
	        </form>
	      </td>
	    </c:when>
        <c:otherwise>
	  	  <td colspan="2" align="center">
	        <spring:url value="/workbench/user/enableAccount" var="enableAccountUrl" />
	        <form id="enableUserForm" action="${enableAccountUrl}" method="POST">
	            <input name="id" type="hidden" value="${user.id}"/>
	            <p class="submit"><input type="submit" value="<fmt:message key="button.accountActive"/>"/></p>
	        </form>
	      </td>
	    </c:otherwise>
	  </c:choose>
      
      <td colspan="2" align="center">
        <spring:url value="/workbench/user/purge" var="purgeUrl" />
        <form id="user" action="${purgeUrl}" method="POST">
            <input name="id" type="hidden" value="${user.id}"/>
            <p class="submit"><input type="submit" value="<fmt:message key="button.purge"/>"/></p>
        </form>
      </td>
    </tr>
  </table>