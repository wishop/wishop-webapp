<%@ include file="/WEB-INF/jsp/header.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<h2><fmt:message key="cmsUser.details"/></h2>

  <table>
  	<tr>
      <th></th>
      <td><img src="${cmsUser.profileImage.symbolicPath}" height="200px" width="250px" alt="${cmsUser.profileImage.caption}"/></td>
    </tr>
    <tr>
      <th><fmt:message key="cmsObject.id"/></th>
      <td><b>${cmsUser.id}</b></td>
    </tr>
    <tr>
      <th><fmt:message key="tag.name"/></th>
      <td><b>${cmsUser.fullName}</b></td>
    </tr>
    <tr>
      <th><fmt:message key="cmsUser.email"/></th>
      <td>${cmsUser.email}</td>
    </tr>
    <tr>
      <th><fmt:message key="cmsUser.telephone"/></th>
      <td>${cmsUser.telephone}</td>
    </tr>
    <tr>
      <th><fmt:message key="cmsUser.mobile"/></th>
      <td>${cmsUser.mobile}</td>
    </tr>
    <tr>
      <th><fmt:message key="cmsUser.fax"/></th>
      <td>${cmsUser.fax}</td>
    </tr>
    <tr>
      <th><fmt:message key="cmsUser.dateOfBirth"/></th>
      <td><fmt:formatDate value="${cmsUser.dateOfBirth}" pattern="yyyy-MM-dd"/></td>
    </tr>
    <tr>
      <th><fmt:message key="auditInfo.creationTimestamp"/></th>
      <td><fmt:formatDate value="${cmsUser.auditInfo.creationTimestamp}" pattern="yyyy-MM-dd H:MM:ss"/></td>
    </tr>
    <tr>
      <th><fmt:message key="auditInfo.modificationTimestamp"/></th>
      <td><fmt:formatDate value="${cmsUser.auditInfo.modificationTimestamp}" pattern="yyyy-MM-dd H:MM:ss"/></td>
    </tr>
    <tr>
      <th><fmt:message key="cmsObject.deleted"/></th>
      <td>${cmsUser.deleted}</td>
    </tr>
    <tr>
      <th><fmt:message key="cmsUser.accountActive"/></th>
      <td>${cmsUser.accountActive}</td>
    </tr>
    <tr>
      <th><fmt:message key="cmsUser.profile"/></th>
      <td>${cmsUser.profile}</td>
	</tr>
    <tr>
      <th><fmt:message key="cmsAddress"/></th>
      <td>${cmsUser.cmsAddress.fullAddress}</td>
    </tr>
  </table>
  <h3><fmt:message key="cmsRoles"/></h3>
  <c:forEach var="role" items="${cmsUser.roles}">
    <table width="94%">
      <tr>
        <td valign="top">
          <table>
            <tr>
              <th><fmt:message key="tag.name"/></th>
              <td><b>${role.name }</b></td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
    <table class="table-buttons">
      <tr>
        <td>
          <spring:url value="/workbench/cmsUser/removeRole" var="removeRoleUrl" />
	        <form id="disableUserForm" action="${removeRoleUrl}" method="POST">
	            <input name="userId" type="hidden" value="${cmsUser.id}"/>
	            <input name="roleId" type="hidden" value="${role.id}"/>
	            <p class="submit"><input type="submit" value="<fmt:message key="button.remove"/> <fmt:message key="cmsRole"/>"/></p>
	        </form>
        </td>
        <td></td>
      </tr>
    </table>
  </c:forEach>
   <table class="table-buttons">
      <tr>
        <td>
          <spring:url value="/workbench/cmsUser/{userId}/addRoles" var="addRole">
            <spring:param name="userId" value="${cmsUser.id}"/>
          </spring:url>
          <a href="${fn:escapeXml(addRole)}"><fmt:message key="button.add"/> <fmt:message key="cmsRole"/></a>
        </td>
        <td></td>
      </tr>
    </table>
    <table class="table-buttons">
    <tr>
      <td>
        <spring:url value="/workbench/cmsUser/create" var="addUrl" />
        <a href="${fn:escapeXml(addUrl)}"><fmt:message key="button.add"/> <fmt:message key="cmsUser"/></a>
      </td>
      <td>
        <spring:url value="/workbench/cmsUser/edit/{userId}" var="editUrl">
        	<spring:param name="userId" value="${cmsUser.id}" />
        </spring:url>
        <a href="${fn:escapeXml(editUrl)}"><fmt:message key="button.edit"/> <fmt:message key="cmsUser"/></a>
      </td>
      <td>
        <spring:url value="/workbench/cmsUser/changePassword/{userId}" var="editUrl">
        	<spring:param name="userId" value="${cmsUser.id}" />
        </spring:url>
        <a href="${fn:escapeXml(editUrl)}"><fmt:message key="button.changePassword"/></a>
      </td>
      
      <c:choose>
	  	<c:when test="${cmsUser.accountActive}">
	      <td colspan="2" align="center">
	        <spring:url value="/workbench/cmsUser/disableAccount" var="disableAccountUrl" />
	        <form id="disableUserForm" action="${disableAccountUrl}" method="POST">
	            <input name="id" type="hidden" value="${cmsUser.id}"/>
	            <p class="submit"><input type="submit" value="<fmt:message key="button.accountInactive"/>"/></p>
	        </form>
	      </td>
	    </c:when>
        <c:otherwise>
	  	  <td colspan="2" align="center">
	        <spring:url value="/workbench/cmsUser/enableAccount" var="enableAccountUrl" />
	        <form id="enableUserForm" action="${enableAccountUrl}" method="POST">
	            <input name="id" type="hidden" value="${cmsUser.id}"/>
	            <p class="submit"><input type="submit" value="<fmt:message key="button.accountActive"/>"/></p>
	        </form>
	      </td>
	    </c:otherwise>
	  </c:choose>
      
      <c:choose>
	  	<c:when test="${cmsUser.deleted}">
	  	  <td colspan="2" align="center">
	        <spring:url value="/workbench/cmsUser/undelete" var="unDeleteUrl" />
	        <form id="user" action="${unDeleteUrl}" method="POST">
	            <input name="id" type="hidden" value="${cmsUser.id}"/>
	            <p class="submit"><input type="submit" value="<fmt:message key="button.undelete"/>"/></p>
	        </form>
	      </td>
	    </c:when>
        <c:otherwise>
	      <td colspan="2" align="center">
	        <spring:url value="/workbench/cmsUser/delete" var="deleteUrl" />
	        <form id="user" action="${deleteUrl}" method="POST">
	            <input name="id" type="hidden" value="${cmsUser.id}"/>
	            <p class="submit"><input type="submit" value="<fmt:message key="button.delete"/>"/></p>
	        </form>
	      </td>
	    </c:otherwise>
	  </c:choose>
      <td colspan="2" align="center">
        <spring:url value="/workbench/cmsUser/purge" var="purgeUrl" />
        <form id="user" action="${purgeUrl}" method="POST">
            <input name="id" type="hidden" value="${cmsUser.id}"/>
            <p class="submit"><input type="submit" value="<fmt:message key="button.purge"/>"/></p>
        </form>
      </td>
    </tr>
  </table>

  
<%@ include file="/WEB-INF/jsp/footer.jsp" %>
