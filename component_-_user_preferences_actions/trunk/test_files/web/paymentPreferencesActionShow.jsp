<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
	<title>Payment prefereces show</title>
</head>
<body>
    Payment accrual amount: <s:property value="currentPaymentAccrualAmount" /> </br>
	<a href="<%= request.getContextPath() %>/">Go to main page.</a>
</body>
</html>