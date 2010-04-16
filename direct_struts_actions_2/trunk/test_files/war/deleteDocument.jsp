<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Delete Document Contest</title>

<link href="<s:url value="/css/main.css"/>" rel="stylesheet"
type="text/css"/>

</head>
<body>

<h3>DeleteDocumentContestAction</h3>
<s:form action="deleteDocumentContestAction">
    <table>
        <tr>
            <td>Document Id</td>
            <td><s:select name="documentId" headerKey="-1"
                list="#{0:0, 1:1, 2:2}" /></td>
        </tr>
        <tr>
            <td>Contest ID</td>
            <td><s:select name="contestId" headerKey="-1"
                list="#{0:0, 1:1, 2:2}" /></td>
        </tr>
        <tr>
            <td><s:submit value="delete" /></td>
            <td></td>
        </tr>
    </table>
</s:form>

</body>
</html>
