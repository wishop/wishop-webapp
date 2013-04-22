<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <link rel="stylesheet" href="<spring:url value="/static/styles/petclinic.css" htmlEscape="true" />" type="text/css"/>
  <title>
	<c:set var="headerTitle" value="Scribe CMS"></c:set>
  	<c:if test="${not empty folder.landingPage.title}">
  		<c:set var="headerTitle" value="${folder.landingPage.title}"></c:set>
  	</c:if>
  	${headerTitle}
  </title>
</head>

<body>
<fmt:message key="button.locale"/>:
<c:url var="englishLocaleUrl" value="">
    <c:param name="locale" value="" />
</c:url>
<c:url var="portugueseLocaleUrl" value="">
    <c:param name="locale" value="pt_PT" />
</c:url>

<a href='<c:out value="${englishLocaleUrl}"/>'><fmt:message key="locale.english"/></a>
<a href='<c:out value="${portugueseLocaleUrl}"/>'><fmt:message key="locale.portuguese"/></a>
<a href='j_spring_security_logout'><fmt:message key="logout"/></a>
  <div id="main">
