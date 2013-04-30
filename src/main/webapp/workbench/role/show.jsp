<%@ include file="/WEB-INF/jsp/includes.jsp" %>

<h2><fmt:message key="role.details"/></h2>

  <table>
    <tr>
      <th><fmt:message key="object.id"/></th>
      <td><b>${role.id}</b></td>
    </tr>
    <tr>
      <th><fmt:message key="tag.name"/></th>
      <td><b>${role.name}</b></td>
    </tr>
    <tr>
      <th><fmt:message key="auditInfo.creationTimestamp"/></th>
      <td><fmt:formatDate value="${role.auditInfo.creationTimestamp}" pattern="yyyy-MM-dd H:MM:ss"/></td>
    </tr>
    <tr>
      <th><fmt:message key="auditInfo.modificationTimestamp"/></th>
      <td><fmt:formatDate value="${role.auditInfo.modificationTimestamp}" pattern="yyyy-MM-dd H:MM:ss"/></td>
    </tr>
  </table>
  <table class="table-buttons">
    <tr>
      <td colspan="2" align="center">
        <spring:url value="/workbench/role/edit/{roleId}" var="editUrl">
        	<spring:param name="roleId" value="${role.id}" />
        </spring:url>
        <a href="${fn:escapeXml(editUrl)}"><fmt:message key="button.edit"/> <fmt:message key="role"/></a>
      </td>
      <td>
        <spring:url value="/workbench/role/create" var="addUrl" />
        <a href="${fn:escapeXml(addUrl)}"><fmt:message key="button.add"/> <fmt:message key="role"/></a>
      </td>
      <td colspan="2" align="center">
        <spring:url value="/workbench/role/purge" var="purgeUrl" />
        <form id="role" action="${purgeUrl}" method="POST">
            <input name="id" type="hidden" value="${role.id}"/>
            <p class="submit"><input type="submit" value="<fmt:message key="button.purge"/>"/></p>
        </form>
      </td>
    </tr>
  </table>