<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
</head>
<body>

<!-- display any action errors -->
<div style="color:#ff0000;">
<s:iterator value="actionErrors">
<span><s:property escape="false" /></span>
</s:iterator>
</div> 

<s:form action="login">
    <table>
        <tr>
            <td>Login Name:</td>
            <td><s:textfield name="loginName" value="" /></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><s:password name="password" /></td>
        </tr>
        <tr>
            <td><s:submit value="login" /></td>
        </tr>
    </table>
</s:form>
</body>
</html>
