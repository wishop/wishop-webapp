<%@ include file="/WEB-INF/jsp/includes.jsp" %>

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

<h1>wishop - Workbench Area</h1>