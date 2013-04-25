<%@ include file="/WEB-INF/jsp/includes.jsp" %>

<html>
	<head>
		<title> <tiles:insertAttribute name="title" ignore="true" /> </title>
	</head>
	<body>
		<div class="container">
			<div class="header"> <tiles:insertAttribute name="header" /> </div>
			<div class="body-container">
				<div class="navigation"> <tiles:insertAttribute name="navigation" /> </div>
				<div class="body"> <tiles:insertAttribute name="body" /> </div>
			</div>
			<div class="footer"> <tiles:insertAttribute name="footer" /> </div>
		</div>
	</body>
</html>