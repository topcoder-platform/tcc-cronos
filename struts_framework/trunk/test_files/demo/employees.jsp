<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Employee</title>
</head>
<body>
<table width="600" align=center>
	<tr>
		<s:url id="refresh" action="employees" />
		<td><s:a href="%{refresh}">Click Here to Refresh</s:a></td>
	</tr>
</table>
<br />
<table align=center id="empTable">
	<tr align="left">
		<th>First Name</th>
		<th>Last Name</th>
		<th>Address</th>
		<th>Age</th>
		<th>Department</th>
		<th>Introducer</th>
	</tr>
	<s:iterator value="%{model.dataAsMap['Employees']}" status="status">
		<tr align="left">
			<td><s:property value="firstName" /></td>
			<td><s:property value="lastName" /></td>
			<td><s:property value="address" /></td>
			<td><s:property value="age" /></td>
			<td><s:property value="department.name" /></td>
			<td><s:property value="introducer" /></td>
		</tr>
	</s:iterator>
</table>

</body>
</html>
