<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
	<title>User Profile completeUserProfileForStudioForCustomer</title>
</head>
<body>








 <s:form action="completeUserProfileForStudioForCustomer" namespace="/">
	<s:fielderror />
	
	<s:if test="%{firstName != null}">	
	<s:textfield name="firstName" label="firstName" />	
	</s:if>
	<s:else>    	
    <s:textfield name="firstName" label="firstName" value="%{user.firstName}" />	
	</s:else>
	
	<s:if test="%{lastName != null}">	
	<s:textfield name="lastName" label="lastName" />	
	</s:if>
	<s:else>    	
    <s:textfield name="lastName" label="lastName" value="%{user.lastName}" />	
	</s:else>	
	
	<s:if test="%{jobTitle != null}">	
	<s:textfield name="jobTitle" label="jobTitle" />	
	</s:if>
	<s:else>    	
    <s:textfield name="jobTitle" label="jobTitle" value="%{user.contact.title}" />	
	</s:else>	
	
	<s:if test="%{companyName != null}">	
	<s:textfield name="companyName" label="companyName" />	
	</s:if>
	<s:else>    	
    <s:textfield name="companyName" label="companyName" value="%{user.contact.company.name}" />	
	</s:else>	

	<s:set name="genderId" value="1" />
	<s:set name="ageId" value="2" />
	
	
	<s:iterator value="user.demographicResponses" var="response">		
		<s:if test="%{#response.answer.id == #ageId}">	
			<s:set name="ageAnswer" value="#response.answer" />
		</s:if>
		<s:if test="%{#response.answer.id == #genderId}">	
			<s:set name="genderAnswer" value="#response.answer" />
		</s:if>
	</s:iterator>
	
	<s:set name="ageList" value="{'13-17','18-24','45 and up'}" />
	<s:set name="genderList" value="{'Male','Female'}" />
	
	<s:if test="%{age != null}">
		<s:select
		label="age"
		name="age"
		list="#ageList"
		headerKey="1"
		headerValue=""
		emptyOption="false"	
		/>
	</s:if>
		<s:else>
		<s:select
		label="age"
		name="age"
		list="#ageList"
		headerKey="1"
		headerValue=""
		emptyOption="false"
		value="%{#ageAnswer.text}"
		/>	
		
	</s:else>		
	
	<s:if test="%{gender != null}">
		<s:select
		label="gender"
		name="gender"
		list="#genderList"
		headerKey="1"
		headerValue=""
		emptyOption="false"	
		/>
	</s:if>
	<s:else>
		<s:select
		label="gender"
		name="gender"
		list="#genderList"
		headerKey="1"
		headerValue=""
		emptyOption="false"
		value="%{#genderAnswer.text}"
		/>			
	</s:else>
	
	<s:iterator value="user.addresses" var="address">				
			<s:set name="userAddress" value="#address" />		
	</s:iterator>
	
	<s:if test="%{currentAddress1 != null}">	
	<s:textfield name="currentAddress1" label="currentAddress1" />	
	</s:if>
	<s:else>    	
    <s:textfield name="currentAddress1" label="currentAddress1" value="%{#userAddress.address1}" />	
	</s:else>

	<s:if test="%{currentAddress2 != null}">	
	<s:textfield name="currentAddress2" label="currentAddress2" />	
	</s:if>
	<s:else>    	
    <s:textfield name="currentAddress2" label="currentAddress2" value="%{#userAddress.address2}" />	
	</s:else>
	
	<s:if test="%{currentAddress3 != null}">	
	<s:textfield name="currentAddress3" label="currentAddress3" />	
	</s:if>
	<s:else>    	
    <s:textfield name="currentAddress3" label="currentAddress3" value="%{#userAddress.address3}" />	
	</s:else>
	
	<s:if test="%{city != null}">	
	<s:textfield name="city" label="city" />	
	</s:if>
	<s:else>    	
    <s:textfield name="city" label="city" value="%{#userAddress.city}" />	
	</s:else>
	
	<s:set name="stateList" value="{'CA', 'DC','OK'}" />
	
	<s:if test="%{state != null}">	
	<s:select
		label="state"
		name="state"
		list="#stateList"
		headerKey="1"
		headerValue=""
		emptyOption="false"	
		/>	
	</s:if>	
	<s:else>
	<s:select
		label="state"
		name="state"
		list="#stateList"
		headerKey="1"
		headerValue=""
		emptyOption="false"
		value="%{#userAddress.state.name}"
		/>		    
	</s:else>
	
	<s:if test="%{postalCode != null}">	
	<s:textfield name="postalCode" label="postalCode" />	
	</s:if>
	<s:else>    	
    <s:textfield name="postalCode" label="postalCode" value="%{#userAddress.postalCode}" />	
	</s:else>
	
	<s:if test="%{province != null}">	
	<s:textfield name="province" label="province" />	
	</s:if>
	<s:else>    	
    <s:textfield name="province" label="province" value="%{#userAddress.province}" />	
	</s:else>
	
	<s:set name="countryList" value="#{'840':'USA', '482':'Ukraine','507':'Russia'}" />
	
	<s:if test="%{country != null}">	
	<s:select
		label="country"
		name="country"
		list="#countryList"
		headerKey="1"
		headerValue=""
		emptyOption="false"	
		/>	
	</s:if>
	    	
	<s:else>		
		<s:select
		label="country"
		name="country"
		list="#countryList"
		headerKey="1"
		headerValue=""
		emptyOption="false"
		value="%{#userAddress.country.code}"
		/>	    
	</s:else>
	
	
	
	
	<s:iterator value="user.phoneNumbers" var="phNumber">				
			<s:set name="userPhoneNumber" value="#phNumber" />		
	</s:iterator>
	
	<s:if test="%{phoneNumber != null}">	
	<s:textfield name="phoneNumber" label="phoneNumber" />	
	</s:if>
	<s:else>    	
    <s:textfield name="phoneNumber" label="phoneNumber" value="%{#userPhoneNumber.number}" />	
	</s:else>
	
	<s:set name="timeZoneList" value="{'GMT+02', 'GMT-02'}" />
	<s:if test="%{timezone != null}">	
	<s:select
		label="timezone"
		name="timezone"
		list="#timeZoneList"
		headerKey="1"
		headerValue=""
		emptyOption="false"	
		/>	
	</s:if>
	<s:else>    	
    <s:select
		label="timezone"
		name="timezone"
		list="#timeZoneList"
		headerKey="1"
		headerValue=""
		emptyOption="false"
		value="%{user.timeZone.description}"	
		/>	
	</s:else>
	

	
	<s:submit align="left"/>
 </s:form>

</body>
</html>