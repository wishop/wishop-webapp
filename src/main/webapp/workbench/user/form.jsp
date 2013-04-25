<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<c:set var="method" value="post"/>
<h2>
	<c:choose>
		<c:when test="${user.new}"><fmt:message key="user.new"/></c:when>
		<c:otherwise><fmt:message key="user"/></c:otherwise>
	</c:choose>
</h2>
<form:form modelAttribute="user" action="/workbench/user/save" method="${method}">
  <table>
    <tr>
      <th>
        <fmt:message key="user.firstName"/><form:errors path="firstName" cssClass="errors"/>
        <br/>
        <form:input path="firstName" size="30" maxlength="80"/>
      </th>
    </tr>
    <tr>
      <th>
        <fmt:message key="user.lastName"/><form:errors path="lastName" cssClass="errors"/>
        <br/>
        <form:input path="lastName" size="30" maxlength="80"/>
      </th>
    </tr>
    <tr>
      <th>
        <fmt:message key="user.email"/><form:errors path="email" cssClass="errors"/>
        <br/>
        <form:input path="email" size="30" maxlength="80"/>
      </th>
    </tr>
    <c:if test="${user.new}" >
      <tr>
        <th>
          <fmt:message key="user.emailConfirmation"/><form:errors path="emailConfirmation" cssClass="errors"/>
          <br/>
          <form:input path="emailConfirmation" size="30" maxlength="80"/>
        </th>
      </tr>
    </c:if>
    <c:if test="${user.new}" >
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
	</c:if>
    <tr>
      <th>
        <fmt:message key="user.telephone"/><form:errors path="telephone" cssClass="errors"/>
        <br/>
        <form:input path="telephone" size="30" maxlength="80"/>
      </th>
    </tr>
    <tr>
      <th>
        <fmt:message key="user.mobile"/><form:errors path="mobile" cssClass="errors"/>
        <br/>
        <form:input path="mobile" size="30" maxlength="80"/>
      </th>
    </tr>
    <tr>
      <th>
        <fmt:message key="user.fax"/><form:errors path="fax" cssClass="errors"/>
        <br/>
        <form:input path="fax" size="30" maxlength="80"/>
      </th>
    </tr>
    <tr>
      <th>
        <fmt:message key="user.dateOfBirth"/><form:errors path="dateOfBirth" cssClass="errors"/>
        <br/>
        <form:input path="dateOfBirth" size="30" maxlength="80"/>
      </th>
    </tr>
    <tr>
      <th>
        <fmt:message key="address.addressLine1"/><form:errors path="address.addressLine1" cssClass="errors"/>
        <br/>
        <form:input path="address.addressLine1" size="30" maxlength="80"/>
      </th>
    </tr>
    <tr>
      <th>
        <fmt:message key="address.addressLine2"/><form:errors path="address.addressLine2" cssClass="errors"/>
        <br/>
        <form:input path="address.addressLine2" size="30" maxlength="80"/>
      </th>
    </tr>
    <tr>
      <th>
        <fmt:message key="address.addressLine3"/><form:errors path="address.addressLine3" cssClass="errors"/>
        <br/>
        <form:input path="address.addressLine3" size="30" maxlength="80"/>
      </th>
    </tr>
    <tr>
      <th>
        <fmt:message key="address.city"/><form:errors path="address.city" cssClass="errors"/>
        <br/>
        <form:input path="address.city" size="30" maxlength="80"/>
      </th>
    </tr>
    <tr>
      <th>
        <fmt:message key="address.county"/><form:errors path="address.county" cssClass="errors"/>
        <br/>
        <form:input path="address.county" size="30" maxlength="80"/>
      </th>
    </tr>
    <tr>
      <th>
        <fmt:message key="address.postcode"/><form:errors path="address.postcode" cssClass="errors"/>
        <br/>
        <form:input path="address.postcode" size="30" maxlength="80"/>
      </th>
    </tr>
    <tr>
      <th>
        <fmt:message key="address.country"/><form:errors path="address.country" cssClass="errors"/>
        <br/>
       <form:input path="address.country" size="30" maxlength="80"/>
      </th>
    </tr>
    <tr>
      <th>
        <fmt:message key="user.profile"/><form:errors path="profile" cssClass="errors"/>
        <br/>
        <form:textarea path="profile" rows="5"  cols="25"/>
      </th>
    </tr>
    <tr>
      <td>
        <c:choose>
          <c:when test="${user.new}">
            <p class="submit"><input type="submit" value="<fmt:message key="button.add"/> <fmt:message key="user"/>"/></p>
          </c:when>
          <c:otherwise>
            <p class="submit"><input type="submit" value="<fmt:message key="button.update"/> <fmt:message key="user"/>"/></p>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
  </table>
</form:form>