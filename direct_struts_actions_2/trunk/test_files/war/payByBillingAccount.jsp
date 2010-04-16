<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Pay By Billing Account</title>

<link href="<s:url value="/css/main.css"/>" rel="stylesheet"
type="text/css"/>

</head>
<body>

<h3>PayByBillingAccountAction</h3>
<s:form action="payByBillingAccountAction">
    <table>
        <tr>
            <td>Project Id</td>
            <td><s:select name="projectId" headerKey="-1"
                list="#{0:0, 1:1}" /></td>
        </tr>
        <tr>
            <td>PO Number</td>
            <td><s:textfield name="poNumber" /></td>
        </tr>
        <tr>
            <td>Client ID</td>
            <td><s:select name="clientId" headerKey="-1"
                list="#{0:0, 1:1}" /></td>
        </tr>
        <tr>
            <td>Payment Type</td>
            <td><s:select name="type" headerKey="-1"
                list="#{'PayPalCreditCard':'PayPalCreditCard', 'TCPurchaseOrder':'TCPurchaseOrder'}" /></td>
        </tr>
        <tr>
            <td><s:submit value="pay" /></td>
            <td></td>
        </tr>
    </table>
</s:form>

</body>
</html>
