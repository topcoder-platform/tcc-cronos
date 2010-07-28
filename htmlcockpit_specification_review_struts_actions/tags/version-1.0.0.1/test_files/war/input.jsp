<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Demo</title>

<link href="<s:url value="/css/main.css"/>" rel="stylesheet"
	type="text/css" />

</head>
<body>

<!-- display any action errors -->
<div style="color: #ff0000;"><s:iterator value="actionErrors">
	<span><s:property escape="false" /></span>
</s:iterator></div>

<h3>SaveSpecificationReviewCommentAction</h3>
<s:form action="saveSpecificationReviewCommentAction">
	<table>
		<tr>
			<td>Enter Comment</td>
			<td><s:textfield name="comment" /></td>
		</tr>
		<tr>
			<td><s:submit value="Save Comment" /></td>
			<td></td>
		</tr>
	</table>
	<s:hidden name="contestId" value="1" />
	<s:hidden name="studio" value="false" />
	<s:hidden name="questionId" value="2" />
	<s:hidden name="action" value="add" />
</s:form>

<h3>ViewSpecificationReviewAction</h3>
<s:form action="viewSpecificationReviewAction">
	<table>
		<tr>
			<td><s:submit value="View Specification Review Comments" /></td>
		</tr>
	</table>
	<s:hidden name="contestId" value="1" />
</s:form>

</body>
</html>
