<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>
<c:set var="method" value="post"/>
<h2>
	<c:choose>
		<c:when test="${cmsUser.new}"><fmt:message key="cmsUser.new"/></c:when>
		<c:otherwise><fmt:message key="cmsUser"/></c:otherwise>
	</c:choose>
</h2>
<form:form modelAttribute="cmsUser" action="save" method="${method}" enctype="multipart/form-data">
  <table>
  	<tr>
		<td><img src="${cmsUser.profileImage.symbolicPath}" height="200px" width="250px" alt="${cmsUser.profileImage.caption}"/></td>
	</tr>
    <tr>
      <th>
        <fmt:message key="cmsMultipart.file"/><form:errors path="profileImage.file" cssClass="errors"/>
        <br/>
        <form:input path="profileImage.file" size="30" type="file" maxlength="80"/>
      </th>
    </tr>
    <tr>
      <th>
        <fmt:message key="cmsUser.firstName"/><form:errors path="firstName" cssClass="errors"/>
        <br/>
        <form:input path="firstName" size="30" maxlength="80"/>
      </th>
    </tr>
    <tr>
      <th>
        <fmt:message key="cmsUser.lastName"/><form:errors path="lastName" cssClass="errors"/>
        <br/>
        <form:input path="lastName" size="30" maxlength="80"/>
      </th>
    </tr>
    <tr>
      <th>
        <fmt:message key="cmsUser.email"/><form:errors path="email" cssClass="errors"/>
        <br/>
        <form:input path="email" size="30" maxlength="80"/>
      </th>
    </tr>
    <c:if test="${cmsUser.new}" >
      <tr>
        <th>
          <fmt:message key="cmsUser.emailConfirmation"/><form:errors path="emailConfirmation" cssClass="errors"/>
          <br/>
          <form:input path="emailConfirmation" size="30" maxlength="80"/>
        </th>
      </tr>
    </c:if>
    <c:if test="${cmsUser.new}" >
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
	</c:if>
    <tr>
      <th>
        <fmt:message key="cmsUser.telephone"/><form:errors path="telephone" cssClass="errors"/>
        <br/>
        <form:input path="telephone" size="30" maxlength="80"/>
      </th>
    </tr>
    <tr>
      <th>
        <fmt:message key="cmsUser.mobile"/><form:errors path="mobile" cssClass="errors"/>
        <br/>
        <form:input path="mobile" size="30" maxlength="80"/>
      </th>
    </tr>
    <tr>
      <th>
        <fmt:message key="cmsUser.fax"/><form:errors path="fax" cssClass="errors"/>
        <br/>
        <form:input path="fax" size="30" maxlength="80"/>
      </th>
    </tr>
    <tr>
      <th>
        <fmt:message key="cmsUser.dateOfBirth"/><form:errors path="dateOfBirth" cssClass="errors"/>
        <br/>
        <form:input path="dateOfBirth" size="30" maxlength="80"/>
      </th>
    </tr>
    <tr>
      <th>
        <fmt:message key="cmsAddress.addressLine1"/><form:errors path="cmsAddress.addressLine1" cssClass="errors"/>
        <br/>
        <form:input path="cmsAddress.addressLine1" size="30" maxlength="80"/>
      </th>
    </tr>
    <tr>
      <th>
        <fmt:message key="cmsAddress.addressLine2"/><form:errors path="cmsAddress.addressLine2" cssClass="errors"/>
        <br/>
        <form:input path="cmsAddress.addressLine2" size="30" maxlength="80"/>
      </th>
    </tr>
    <tr>
      <th>
        <fmt:message key="cmsAddress.addressLine3"/><form:errors path="cmsAddress.addressLine3" cssClass="errors"/>
        <br/>
        <form:input path="cmsAddress.addressLine3" size="30" maxlength="80"/>
      </th>
    </tr>
    <tr>
      <th>
        <fmt:message key="cmsAddress.city"/><form:errors path="cmsAddress.city" cssClass="errors"/>
        <br/>
        <form:input path="cmsAddress.city" size="30" maxlength="80"/>
      </th>
    </tr>
    <tr>
      <th>
        <fmt:message key="cmsAddress.county"/><form:errors path="cmsAddress.county" cssClass="errors"/>
        <br/>
        <form:input path="cmsAddress.county" size="30" maxlength="80"/>
      </th>
    </tr>
    <tr>
      <th>
        <fmt:message key="cmsAddress.postcode"/><form:errors path="cmsAddress.postcode" cssClass="errors"/>
        <br/>
        <form:input path="cmsAddress.postcode" size="30" maxlength="80"/>
      </th>
    </tr>
    <tr>
      <th>
        <fmt:message key="cmsAddress.country"/><form:errors path="cmsAddress.country.name" cssClass="errors"/>
        <br/>
        <form:select path="cmsAddress.country">
          <c:forEach var="country" items="${countries}">
            <form:option value="${country}" label="${country.name}"/>
          </c:forEach>
        </form:select>
      </th>
    </tr>
    <tr>
      <th>
        <fmt:message key="cmsUser.profile"/><form:errors path="profile" cssClass="errors"/>
        <br/>
        <form:textarea path="profile" rows="5"  cols="25"/>
      </th>
    </tr>
    <tr>
      <td>
        <c:choose>
          <c:when test="${cmsUser.new}">
            <p class="submit"><input type="submit" value="<fmt:message key="button.add"/> <fmt:message key="cmsUser"/>"/></p>
          </c:when>
          <c:otherwise>
            <p class="submit"><input type="submit" value="<fmt:message key="button.update"/> <fmt:message key="cmsUser"/>"/></p>
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
  </table>
</form:form>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>
