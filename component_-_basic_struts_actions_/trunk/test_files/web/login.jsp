<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
	<title>TC_Refactoring_Stage_1_Contest_Service</title>
</head>
<body>
    <s:form action="login">
        <s:textfield name="username" label="User Name" />
		<s:textfield name="password" label="Password" />
		
		<s:checkbox name="rememberMe" label="Remender me" />
        <s:submit value="Submit" />
    </s:form>
	
	<s:form action="recoverPassword">
        <s:hidden name="handle" value="dok_tester" />
		<s:hidden name="passwordRecoveryExpiration" value="1000" />
		<s:submit value="Recover Password" />
    </s:form>
</body>
</html>
