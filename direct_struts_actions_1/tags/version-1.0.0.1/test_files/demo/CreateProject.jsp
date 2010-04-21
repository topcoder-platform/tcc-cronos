<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<title>Create a project</title>
</head>
<body>
<!-- The first http round trip to prepare the jsp data is made by another action in another component -->
<s:form action="createProject">
<table align=center>
	<tr>
		<th>Write project name </th>
		<th>Write description of project</th>
	</tr>

	<tr>
		<td class="nowrap"><s:textfield label="Project name" 	name="projectName"/></td>
		<td class="nowrap"><s:textarea label="Project description" 	name="projectDescription"/></td>
	</tr>
	<tr>
	  <td colspan="2"><s:submit/></td>
	</tr>
</table>
</s:form>
</body>
</html>
