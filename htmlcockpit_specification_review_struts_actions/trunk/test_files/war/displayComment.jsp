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

The comment information is as follows:
<table>
	<tr>
		<td><b>Comment Id</b></td>
		<td><s:property
			value="model.data['result'].userComment.commentId" /></td>
	</tr>
	<tr>
		<td><b>Comment</b></td>
		<td><s:property value="model.data['result'].userComment.comment" /></td>
	</tr>
</table>
<a href="input.jsp">Go to main page</a>
</body>
</html>
