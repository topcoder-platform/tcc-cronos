<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
	<title>This is register page</title>
</head>
<body>
    <h1>This is register page</h1>
	<% 
	    request.getSession().setAttribute("capword", "test");
	%>
	CAPTCHA file name: <s:property value="capfname" /> </br>
    <s:form action="createAccountAction" method="post" >
         <s:textfield name="user.userProfile.firstName" label="First Name" value="Javier" />
         <s:textfield name="user.userProfile.lastName" label="Last Name" value="Ivern"/>
         <s:textfield name="user.handle" label="Handle" value="ivern"/>
		 <!--
         <s:textfield name="user.emailAddresses.makeNew[0].address" label="Email" value="ivern@topcoder.com"/>
		 -->
         <s:textfield name="password" label="Password" value="123456" />
         <s:textfield name="confirmPassword" label="Confirm Password" value="123456"/>
         <s:textfield name="user.referrerHandle" label="Referrer Handle" value="hello-c"/>
         <s:textfield name="verificationCode" label="Verification Code" value="test"/>
         <s:submit />
     </s:form>
</body>
</html>
