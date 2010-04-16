<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>File Upload</title>

<link href="<s:url value="/css/main.css"/>" rel="stylesheet"
type="text/css"/>

</head>
<body>

<!-- display any action errors -->
<div style="color: #ff0000;"><s:iterator value="actionErrors">
	<span><s:property escape="false" /></span>
</s:iterator></div>

<h3>FileUploadAttachContestFileAction</h3>
<s:form action="fileUploadAttachContestFileAction">
	<table>
		<tr>
			<td>Contest File Description</td>
			<td><s:textfield name="contestFileDescription" /></td>
		</tr>
		<tr>
			<td>Contest Id</td>
			<td><s:select name="contestId" headerKey="-1"
				list="#{1:1, 2:2, 3:3}" /></td>
		</tr>
		<tr>
			<td>Document Type ID</td>
			<td><s:select name="documentTypeId" headerKey="-1"
				list="#{1:1, 2:2, 3:3}" /></td>
		</tr>
        <tr>
            <td>Contest File Name</td>
            <td><s:select name="contestFileName" headerKey="-1"
                list="#{'test.txt':'test.txt', 'test.doc':'test.doc', 'test.pdf':'test.pdf'}"
                value="selectedContestFileName" /></td>
        </tr>
		<tr>
			<td><s:submit value="upload" /></td>
			<td></td>
		</tr>
	</table>
</s:form>

</body>
</html>
