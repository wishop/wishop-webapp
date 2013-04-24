<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>
<h2>
	<fmt:message key="tag.changePassword"/>
</h2>
<form:form modelAttribute="user" action="/workbench/user/updatePassword" method="post">
  <table>
    <tr>
      <th>
        <fmt:message key="user.currentPassword"/><form:errors path="currentPassword" cssClass="errors"/>
        <br/>
        <form:password path="currentPassword" size="30" maxlength="80"/>
      </th>
    </tr>
    <tr>
      <th>
        <fmt:message key="user.password"/><form:errors path="password" cssClass="errors"/>
        <br/>
        <form:password path="password" size="30" maxlength="80"/>
      </th>
    </tr>
    <tr>
      <th>
        <fmt:message key="user.passwordConfirmation"/><form:errors path="passwordConfirmation" cssClass="errors"/>
        <br/>
        <form:password path="passwordConfirmation" size="30" maxlength="80"/>
      </th>
    </tr>
    <tr>
      <td>
		<p class="submit"><input type="submit" value="<fmt:message key="button.changePassword"/> <fmt:message key="user"/>"/></p>
      </td>
    </tr>
  </table>
</form:form>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>
