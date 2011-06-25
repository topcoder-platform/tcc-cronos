<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
	<title>This is recoverPasswordEmailSent page</title>
</head>
<body>
    <h1>This is recoverPasswordEmailSent page</h1>
	<h4>A email has sent.</h4>
	
	<s:form action="resendPasswordRecoveryEmail">
        <s:hidden name="passwordRecoveryId" value="1" />
		<s:submit value="Resent Email" />
    </s:form>
</body>
</html>
