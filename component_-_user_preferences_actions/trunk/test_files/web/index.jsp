<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
	<title>Main page</title>
</head>
<body>    
	<a href="<%= request.getContextPath() %>/payment_input?action=view">Change accrual payment amount.</a> </br>
	<a href="<%= request.getContextPath() %>/forumrating_input?action=view">Change forum rating preferences.</a>
</body>
</html>