<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Main</title>
</head>
<body>

<!-- display any action errors -->
<div style="color: #ff0000;"><s:iterator value="actionErrors">
	<span><s:property escape="false" /></span>
</s:iterator></div>

<h3>GUI Demo for actions</h3>
<s:form>
	<table>
		<tr>
			<td><a href="fileUpload.jsp">FileUploadAttachContestFileAction</a></td>
		</tr>
        <tr>
            <td><a href="payByCreditCard.jsp">PayByCreditCardAction</a></td>
        </tr>
        <tr>
            <td><a href="payByBillingAccount.jsp">PayByBillingAccountAction</a></td>
        </tr>
        <tr>
            <td><a href="<s:url action="getContestCategoriesAction"/>">GetContestCategoriesAction</a></td>
        </tr>
        <tr>
            <td><a href="<s:url action="getContestTechnologiesAction"/>">GetContestTechnologiesAction</a></td>
        </tr>
        <tr>
            <td><a href="deleteDocument.jsp">DeleteDocumentContestAction</a></td>
        </tr>
        <tr>
            <td><a href="getFile.jsp">GetDocumentFileContestAction</a></td>
        </tr>
        <tr>
            <td><a href="getDocuments.jsp">GetDocumentsContestAction</a></td>
        </tr>
	</table>
</s:form>

</body>
</html>
