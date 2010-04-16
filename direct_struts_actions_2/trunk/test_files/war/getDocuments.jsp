<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Get File</title>

<link href="<s:url value="/css/main.css"/>" rel="stylesheet"
type="text/css"/>

</head>
<body>

<h3>GetDocumentsContestAction</h3>

<!-- display action errors for projectId/contestId (both may have been specified, or neither) -->
<div style="color:#ff0000;">
<s:iterator value="fieldErrors['contestIdProjectId']">
<span><s:property escape="false" /></span>
</s:iterator>
</div> 

<s:form action="getDocumentsContestAction">
    <table>
        <tr>
            <td>Contest Id</td>
            <td><s:select name="contestId" headerKey="-1"
                list="#{0:0, 1:1}" /></td>
        </tr>
        <tr>
            <td><s:submit value="get documents" /></td>
            <td></td>
        </tr>
    </table>
</s:form>

</body>
</html>
