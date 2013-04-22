<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>
<h2>
	<fmt:message key="tag.changePassword"/>
</h2>
<form:form modelAttribute="cmsUser" action="updatePassword" method="post">
  <table>
    <tr>
      <th>
        <fmt:message key="cmsUser.currentPassword"/><form:errors path="currentPassword" cssClass="errors"/>
        <br/>
        <form:password path="currentPassword" size="30" maxlength="80"/>
      </th>
    </tr>
    <tr>
      <th>
        <fmt:message key="cmsUser.password"/><form:errors path="password" cssClass="errors"/>
        <br/>
        <form:password path="password" size="30" maxlength="80"/>
      </th>
    </tr>
    <tr>
      <th>
        <fmt:message key="cmsUser.passwordConfirmation"/><form:errors path="passwordConfirmation" cssClass="errors"/>
        <br/>
        <form:password path="passwordConfirmation" size="30" maxlength="80"/>
      </th>
    </tr>
    <tr>
      <td>
		<p class="submit"><input type="submit" value="<fmt:message key="button.changePassword"/> <fmt:message key="cmsUser"/>"/></p>
      </td>
    </tr>
  </table>
</form:form>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>
