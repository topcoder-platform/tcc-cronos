<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Pay By Credit Card</title>

<link href="<s:url value="/css/main.css"/>" rel="stylesheet"
type="text/css"/>

</head>
<body>

<h3>PayByCreditCardAction</h3>

<!-- display action errors for projectId/contestId (both may have been specified, or neither) -->
<div style="color:#ff0000;">
<s:iterator value="fieldErrors['contestIdProjectId']">
<span><s:property escape="false" /></span>
</s:iterator>
</div> 

<s:form action="payByCreditCardAction">
	<table>
		<tr>
			<td>Project Id (should be 0 if contest ID is NOT 0)</td>
            <td><s:select name="projectId" headerKey="-1"
                list="#{0:0, 1:1}" /></td>
		</tr>
		<tr>
            <td>Contest Id (should be 0 if project ID is NOT 0)</td>
            <td><s:select name="contestId" headerKey="-1"
                list="#{0:0, 1:1}" /></td>
		</tr>
		<tr>
			<td>Card Number</td>
			<td><s:textfield name="cardNumber" /></td>
		</tr>
		<tr>
			<td>Card Type (must be Visa, MasterCard, or American Express)</td>
			<td><s:textfield name="cardType" /></td>
		</tr>
		<tr>
			<td>Card Expiry Year</td>
			<td><s:textfield name="cardExpiryYear" /></td>
		</tr>
		<tr>
			<td>Card Expiry Month (must be 1-12)</td>
			<td><s:textfield name="cardExpiryMonth" /></td>
		</tr>
		<tr>
			<td>First Name</td>
			<td><s:textfield name="firstName" /></td>
		</tr>
		<tr>
			<td>Last Name</td>
			<td><s:textfield name="lastName" /></td>
		</tr>
		<tr>
			<td>Address</td>
			<td><s:textfield name="address" /></td>
		</tr>
		<tr>
			<td>City</td>
			<td><s:textfield name="city" /></td>
		</tr>
		<tr>
			<td>State (2 chars, required if country is US)</td>
			<td><s:textfield name="state" /></td>
		</tr>
		<tr>
			<td>Country</td>
			<td><s:textfield name="country" /></td>
		</tr>
		<tr>
			<td>Zip Code (between 1 and 10 digits)</td>
			<td><s:textfield name="zipCode" /></td>
		</tr>
		<tr>
			<td>Phone (xxx) xxx-xxxx</td>
			<td><s:textfield name="phone" /></td>
		</tr>
		<tr>
			<td>Email</td>
			<td><s:textfield name="email" /></td>
		</tr>
		<tr>
			<td>Amount</td>
			<td><s:textfield name="amount" /></td>
		</tr>
		<tr>
			<td>Csc</td>
			<td><s:textfield name="csc" /></td>
		</tr>
		<tr>
			<td><s:submit value="pay" /></td>
			<td></td>
		</tr>
	</table>
</s:form>

</body>
</html>
