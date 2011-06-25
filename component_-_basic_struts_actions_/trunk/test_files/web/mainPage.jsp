<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
	<title>This is main page</title>
</head>
<body>
    <h1>This is main page</h1>
	<s:form action="logout">
        <s:submit value="Logout" />
    </s:form>
	
	<s:form action="resetPassword">
        <s:hidden name="hashCode" value="hash" />
		<s:submit value="Rest Password" />
    </s:form>
	
	<!--
	<a href="resetPassword.action">Reset Password</a>
	-->
</body>
</html>
