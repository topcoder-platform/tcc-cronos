<%@page language="java" contentType="text/html" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
</head>
<body>
	<!-- this page only show the success execution for auditHistory action -->
	<!-- here a "for-loop" can be used to display the search result after the user clicks 'submit', which can be accessed as 'accountingAuditRecords' property.-->
	<s:iterator value="accountingAuditRecords.records" id="record">
		Action : <s:property value="#record.action"/> <br/>
		Username : <s:property value="#record.userName"/> <br/>
		Timestamp : <s:property value="#record.timestamp"/> <br/>
	</s:iterator>
</body>
</html>