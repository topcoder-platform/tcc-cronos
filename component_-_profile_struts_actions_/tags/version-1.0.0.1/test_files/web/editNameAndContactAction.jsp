<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
	<title>Edit name and contact</title>
</head>
<body>
  <s:set name="userAddress" value="%{displayedUser.addresses.iterator.next}" />
	<s:set name="phoneNumber" value="%{displayedUser.phoneNumbers.iterator.next}" />  
    <s:set name="emailAddress" value="%{displayedUser.emailAddresses.iterator.next}" />

 
<s:form action="saveNameAndContactAction" namespace="/">	
    <s:textfield name="savedUser.firstName" label="First Name:" value="%{displayedUser.firstName}" />		    
	 <s:textfield label="Last Name:" name="savedUser.lastName" value="%{displayedUser.lastName}" /> </br>
	<s:textfield label="E-mail" name="savedUser.primaryEmailAddress.address" value="%{#emailAddress.address}" /> </br>
	
	 <s:textfield label="Job Title:" name="savedUser.contact.title" value="%{displayedUser.contact.title}" /> </br>
	 <s:textfield label="Company Name:" name="savedUser.contact.company.name" value="%{displayedUser.contact.company.name}" /> </br>
	<s:textfield  label="Current Address 1: " name="savedUser.homeAddress.address1" value="%{#userAddress.address1}" /> </br>
	<s:textfield label="Current Address 2: " name="savedUser.homeAddress.address2" value="%{#userAddress.address2}" /> </br>
	 <s:textfield label="Current Address 3:" name="savedUser.homeAddress.address3" value="%{#userAddress.address3}" /> </br>
	 <s:textfield label="City:"  name="savedUser.homeAddress.city" value="%{#userAddress.city}" /> </br>
	 <s:textfield label="State:"  name="state" value="%{#userAddress.state.code}" /> </br>
	 <s:textfield label="Postal Code:"  name="savedUser.homeAddress.postalCode" value="%{#userAddress.postalCode}" /> </br>
	 <s:textfield label="Province:" name="savedUser.homeAddress.province" value="%{#userAddress.province}" /> </br>
	 <s:select label="Country:" name="savedUser.homeAddress.country.code" list="%{countries}" listKey="code" listValue="name" value="%{#userAddress.country.code}" /> </br>
	 <s:textfield label="Phone Number:" name="savedUser.primaryPhoneNumber.number" value="%{#phoneNumber.number}" /> </br>
	 <s:select label="Country To Represent: " name="savedUser.coder.compCountry.name" list="representationCountries" listKey="code" listValue="name" value="%{displayedUser.coder.compCountry.code}" /> </br>
	 <s:select label="Time Zone:" name="timeZoneId"  list="timezones" listKey="id" listValue="description" value="%{displayedUser.timeZone.id}" /> </br>	
	<s:submit align="left" value="Save"/> 	
 </s:form>
</body>
</html>
