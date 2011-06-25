<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
	<title>Payment accural amount</title>
</head>
<body>
Current action: <s:property value="action"/> </br>

    <s:form action="payment_execute" namespace="/">	
	<s:hidden name="action" value="submit" />
	<s:textfield name="currentPaymentAccrualAmount" label="Payment accrual amount:"/>
	<s:submit align="left"/>
    </s:form>
	<s:form action="payment_execute" namespace="/">	
	<s:hidden name="action" value="discard" />
	<s:submit value="Discard" />
    </s:form>
</body>
</html>