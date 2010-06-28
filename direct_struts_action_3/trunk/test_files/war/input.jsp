<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Game Plan Retrieval Action</title>

<link href="<s:url value="/css/main.css"/>" rel="stylesheet"
type="text/css"/>

</head>
<body>

<!-- display any action errors -->
<div style="color: #ff0000;"><s:iterator value="actionErrors">
    <span><s:property escape="false" /></span>
</s:iterator></div>

<h3>GamePlanRetrievalAction</h3>
<s:form action="gamePlanRetrievalAction">
    <table>
        <tr>
            <td>Project Id (must be positive)</td>
            <td><s:textfield name="projectId"/></td>
        </tr>
        <tr>
            <td><s:submit value="Get Game Plan" /></td>
            <td></td>
        </tr>
    </table>
</s:form>

</body>
</html>
