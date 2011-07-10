<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
	<title>Edit name and contact</title>
</head>
<body>
  

 
<s:form action="action" namespace="/">	
    <s:textfield name="firstName" value="Some first name" />
	<s:textfield name="lastName" value="Some last name" />
	<s:hidden name="entity" value="entity"/>
	<s:submit align="left" value="Save"/> 	
 </s:form>
</body>
</html>
