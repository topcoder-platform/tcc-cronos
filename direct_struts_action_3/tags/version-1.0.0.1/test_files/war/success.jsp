<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Success</title>

<link href="<s:url value="/css/main.css"/>" rel="stylesheet"
	type="text/css" />

</head>
<body>

The game plan contest names are as follows:
<table>
	<s:iterator value="model.data['result']">
		<tr>
			<td><s:property value="name" escape="false" /></td>
		</tr>
	</s:iterator>
</table>
</body>
</html>
