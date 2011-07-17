<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
	<title>Saved user</title>
</head>
<body>

User has been saved successfully! </br>
First : <s:property value="savedUser.firstName" /> </br>
Last : <s:property value="savedUser.lastName" /> </br>
Email : <s:property value="savedUser.primaryEmailAddress.address" /> </br>
TimeZone : <s:property value="savedUser.timeZone.description" /> </br>

 
</body>
</html>
