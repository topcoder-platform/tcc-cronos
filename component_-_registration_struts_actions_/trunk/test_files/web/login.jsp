<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
	<title>Registration Struts Actions</title>
</head>
<body>
    <s:form action="preCreateAccountAction">
        <s:submit value="excute preCreateAccountAction" />
    </s:form>
	<s:form action="activateAccountAction">
        <s:submit value="excute activateAccountAction" />
    </s:form>
	<s:form action="viewRegistrationPreferenceAction">
        <s:submit value="excute viewRegistrationPreferenceAction" />
    </s:form>
	<s:form action="resendAccountActivationEmailAction">
        <s:submit value="excute resendAccountActivationEmailAction" />
    </s:form>
	<!--
	<a href="registration-preferences.jsp">Show selectRegistrationPreferenceAction</a> </br>
	-->
	<s:form action="selectRegistrationPreferenceAction">
        <s:submit value="excute selectRegistrationPreferenceAction" />
    </s:form>
</body>
</html>
