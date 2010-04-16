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

<!-- the value of parameters.type is <s:property value="%{#parameters.type[0]}"/>  -->

<s:if test="#parameters.type[0]=='fileUpload'">
    The contest file with description of
    <s:property value="contestFileDescription" />
    was added successfully. The file name is <s:property
		value="contestFileName" />,
    the content type is <s:property value="contestFileContentType" />, the contest ID is <s:property
		value="contestId" />, the 
    document type ID is <s:property value="documentTypeId" />
</s:if>

<s:if test="#parameters.type[0]=='payByCreditCard'">
    The payment for credit card number 
    <s:property value="cardNumber" /> and amount of <s:property
		value="amount" />
    was processed successfully. The type of credit card was <s:property
		value="cardType" />.
</s:if>

<s:if test="#parameters.type[0]=='payByBillingAccount'">
    The payment for PO number 
    <s:property value="poNumber" /> and client ID <s:property
		value="clientId" />
    was processed successfully. The payment type was <s:property
		value="type" />.
</s:if>

<s:if test="#parameters.type[0]=='getContestCategories'">
    The contest categories are:
<table>
		<s:iterator value="model.data['return']">
			<tr>
				<td><s:property value="description" escape="false" /></td>
			</tr>
		</s:iterator>
	</table>
</s:if>

<s:if test="#parameters.type[0]=='getContestTechnologies'">
    The contest technologies are:
<table>
		<s:iterator value="model.data['return']">
			<tr>
				<td><s:property value="description" escape="false" /></td>
			</tr>
		</s:iterator>
	</table>
</s:if>

<s:if test="#parameters.type[0]=='deleteDocument'">
    The document with document ID = 
    <s:property value="documentId" /> and contest ID = <s:property
		value="contestId" />
    was deleted successfully.
</s:if>

<s:if test="#parameters.type[0]=='getFile'">
    The document with document ID = 
    <s:property value="documentId" /> and contest ID = <s:property
		value="contestId" />
    was fetched successfully. The content disposition was <s:property
		value="contentDisposition" /> and content type was <s:property
		value="contentType" />.
</s:if>

<s:if test="#parameters.type[0]=='getDocuments'">
    The documents are:
<table>
        <s:iterator value="model.data['return']">
            <tr>
                <td><s:property value="fileName" escape="false" /></td>
            </tr>
        </s:iterator>
    </table>
</s:if>

<br />
<br />
<a href="main.jsp">Go to main page</a>
</body>
</html>
