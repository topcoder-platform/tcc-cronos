<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
	<title>View name and contact</title>
</head>
<body>

  
    <s:set name="userAddress" value="%{displayedUser.addresses.iterator.next}" />
	<s:set name="phoneNumber" value="%{displayedUser.phoneNumbers.iterator.next}" />  
    <s:set name="emailAddress" value="%{displayedUser.emailAddresses.iterator.next}" />
 

<s:form action="editNameAndContactAction" namespace="/">
	First Name: <s:property value="displayedUser.firstName" /> </br>
	Last Name: <s:property value="displayedUser.lastName" /> </br>
	E-mail: <s:property value="#emailAddress.address" /> </br>
	Job Title: <s:property value="displayedUser.contact.title" /> </br>
	Company Name: <s:property value="displayedUser.contact.company.name" /> </br>
	Current Address 1: <s:property value="#userAddress.address1" /> </br>
	Current Address 2: <s:property value="#userAddress.address2" /> </br>
	Current Address 3: <s:property value="#userAddress.address3" /> </br>
	City: <s:property value="#userAddress.city" /> </br>
	State: <s:property value="#userAddress.state.code" /> </br>
	Postal Code: <s:property value="#userAddress.postalCode" /> </br>
	Province: <s:property value="#userAddress.province" /> </br>
	Country: <s:property value="#userAddress.country.name" /> </br>
	Country To Represent: <s:property value="displayedUser.coder.compCountry.name" /> </br>
	Phone Number: <s:property value="#phoneNumber.number" /> </br>	
	Time Zone: <s:property value="displayedUser.timeZone.description" /> </br>								
	<s:submit align="left" value="Edit"/> 	
 </s:form>
</body>
</html>
