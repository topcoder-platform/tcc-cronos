<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="/paging" prefix="p" %>
<%@ taglib uri="/orpheus" prefix="orpheus" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="req" value="${pageContext.request}"/>
<c:set var="user" value="${sessionScope['user_profile']}" scope="page"/>
<c:set var="firstName" value="${orpheus:getFirstName(user, req, 'firstName')}" scope="page"/>
<c:set var="lastName" value="${orpheus:getLastName(user, req, 'lastName')}" scope="page"/>
<c:set var="address1" value="${orpheus:resolveAddress1(user, req, 'address1')}" scope="page"/>
<c:set var="address2" value="${orpheus:resolveAddress2(user, req, 'address2')}" scope="page"/>
<c:set var="city" value="${orpheus:resolveCity(user, req, 'city')}" scope="page"/>
<c:set var="postalCode" value="${orpheus:resolvePostalCode(user, req, 'postalCode')}" scope="page"/>
<c:set var="phone" value="${orpheus:resolvePhone(user, req, 'phone')}" scope="page"/>
<c:set var="paymentPref" value="${orpheus:resolvePaymentPref(user, req, 'paymentPref')}" scope="page"/>
<c:set var="state" value="${orpheus:resolveState(user, req, 'state')}" scope="page"/>
<c:set var="country" value="${orpheus:resolveCountry(user, req, 'country')}" scope="page"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: Game Winner Registration</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/plugin.css"/>
    <style type="text/css">
        <!--
        .style1 {
            color: #FF0000
        }

        -->
    </style>
    <script type="text/javascript" src="${ctx}/js/forms.js"></script>
</head>

<body id="pagePlugin">
<div id="containerPlugin">
<div id="mastHead"><img src="${ctx}/i/plugin_stripes.jpg" width="668" height="62" alt=""/></div>

<div id="pluginTitle">CONGRATULATIONS!</div>

<div id="wrapPlugin">
<div class="tabletop"></div>

<form action="${ctx}/server/plugin/winnerData.do" name="WinnerDataForm" id="WinnerDataForm" method="POST">
<table width="100%" border="0" align="center" cellpadding="5" cellspacing="0" id="contactForm">
<tr>
    <td colspan="2">You have solved the puzzle.&nbsp; Please fill out the following form to receive your prize, which
        will be awarded once your eligibility is confirmed. </td>
</tr>
<tr>
    <td colspan="2"><strong>&nbsp;Enter your personal details</strong></td>
</tr>
<tr>
    <td colspan="2" align="right">&nbsp;</td>
</tr>
${orpheus:error('firstName', validationErrors)}
<tr>
    <td width="22%" align="right"><span class="style1">*</span>First Name:</td>
    <td width="78%" align="left">
        <label><input type="text" name="firstName" value="${firstName}"/></label>
    </td>
</tr>
${orpheus:error('lastName', validationErrors)}
<tr>
    <td align="right"><span class="style1">*</span>Last Name:</td>
    <td align="left"><input type="text" name="lastName" value="${lastName}"/></td>
</tr>
${orpheus:error('address1', validationErrors)}
<tr>
    <td align="right"><span class="style1">*</span>Street Address 1:</td>
    <td align="left"><input type="text" name="address1" value="${address1}"/></td>
</tr>
${orpheus:error('address2', validationErrors)}
<tr>
    <td align="right">Street Address 2:</td>
    <td align="left"><input type="text" name="address2" value="${address2}"/></td>
</tr>
${orpheus:error('city', validationErrors)}
<tr>
    <td align="right"><span class="style1">*</span>City:</td>
    <td align="left"><input type="text" name="city" value="${city}"/></td>
</tr>
${orpheus:error('country', validationErrors)}
<tr>
    <td align="right"><span class="style1">*</span>Country:</td>
    <td align="left">
        <%@ include file="/public/country.jsp" %>
    </td>
</tr>
${orpheus:error('state', validationErrors)}
<tr>
    <td align="right">State/Province:</td>
    <td align="left">
        <select name="state" size="1">
            <option value="">Select</option>
            <option value="AL" ${state eq 'AL' ? 'selected="selected"' : ''}>Alabama</option>
            <option value="AK" ${state eq 'AK' ? 'selected="selected"' : ''}>Alaska</option>
            <option value="AZ" ${state eq 'AZ' ? 'selected="selected"' : ''}>Arizona</option>
            <option value="AR" ${state eq 'AR' ? 'selected="selected"' : ''}>Arkansas</option>
            <option value="CA" ${state eq 'CA' ? 'selected="selected"' : ''}>California</option>
            <option value="CO" ${state eq 'CO' ? 'selected="selected"' : ''}>Colorado</option>
            <option value="CT" ${state eq 'CT' ? 'selected="selected"' : ''}>Connecticut</option>
            <option value="DE" ${state eq 'DE' ? 'selected="selected"' : ''}>Delaware</option>
            <option value="DC" ${state eq 'DC' ? 'selected="selected"' : ''}>Dist of Columbia</option>
            <option value="FL" ${state eq 'FL' ? 'selected="selected"' : ''}>Florida</option>
            <option value="GA" ${state eq 'GA' ? 'selected="selected"' : ''}>Georgia</option>
            <option value="HI" ${state eq 'HI' ? 'selected="selected"' : ''}>Hawaii</option>
            <option value="ID" ${state eq 'ID' ? 'selected="selected"' : ''}>Idaho</option>
            <option value="IL" ${state eq 'IL' ? 'selected="selected"' : ''}>Illinois</option>
            <option value="IN" ${state eq 'IN' ? 'selected="selected"' : ''}>Indiana</option>
            <option value="IA" ${state eq 'IA' ? 'selected="selected"' : ''}>Iowa</option>
            <option value="KS" ${state eq 'KS' ? 'selected="selected"' : ''}>Kansas</option>
            <option value="KY" ${state eq 'KY' ? 'selected="selected"' : ''}>Kentucky</option>
            <option value="LA" ${state eq 'LA' ? 'selected="selected"' : ''}>Louisiana</option>
            <option value="ME" ${state eq 'ME' ? 'selected="selected"' : ''}>Maine</option>
            <option value="MD" ${state eq 'MD' ? 'selected="selected"' : ''}>Maryland</option>
            <option value="MA" ${state eq 'MA' ? 'selected="selected"' : ''}>Massachusetts</option>
            <option value="MI" ${state eq 'MI' ? 'selected="selected"' : ''}>Michigan</option>
            <option value="MN" ${state eq 'MN' ? 'selected="selected"' : ''}>Minnesota</option>
            <option value="MS" ${state eq 'MS' ? 'selected="selected"' : ''}>Mississippi</option>
            <option value="MO" ${state eq 'MO' ? 'selected="selected"' : ''}>Missouri</option>
            <option value="MT" ${state eq 'MT' ? 'selected="selected"' : ''}>Montana</option>
            <option value="NE" ${state eq 'NE' ? 'selected="selected"' : ''}>Nebraska</option>
            <option value="NV" ${state eq 'NV' ? 'selected="selected"' : ''}>Nevada</option>
            <option value="NH" ${state eq 'NH' ? 'selected="selected"' : ''}>New Hampshire</option>
            <option value="NJ" ${state eq 'NJ' ? 'selected="selected"' : ''}>New Jersey</option>
            <option value="NM" ${state eq 'NM' ? 'selected="selected"' : ''}>New Mexico</option>
            <option value="NY" ${state eq 'NY' ? 'selected="selected"' : ''}>New York</option>
            <option value="NC" ${state eq 'NC' ? 'selected="selected"' : ''}>North Carolina</option>
            <option value="ND" ${state eq 'ND' ? 'selected="selected"' : ''}>North Dakota</option>
            <option value="OH" ${state eq 'OH' ? 'selected="selected"' : ''}>Ohio</option>
            <option value="OK" ${state eq 'OK' ? 'selected="selected"' : ''}>Oklahoma</option>
            <option value="OR" ${state eq 'OR' ? 'selected="selected"' : ''}>Oregon</option>
            <option value="PA" ${state eq 'PA' ? 'selected="selected"' : ''}>Pennsylvania</option>
            <option value="RI" ${state eq 'RI' ? 'selected="selected"' : ''}>Rhode Island</option>
            <option value="SC" ${state eq 'SC' ? 'selected="selected"' : ''}>South Carolina</option>
            <option value="SD" ${state eq 'SD' ? 'selected="selected"' : ''}>South Dakota</option>
            <option value="TN" ${state eq 'TN' ? 'selected="selected"' : ''}>Tennessee</option>
            <option value="TX" ${state eq 'TX' ? 'selected="selected"' : ''}>Texas</option>
            <option value="UT" ${state eq 'UT' ? 'selected="selected"' : ''}>Utah</option>
            <option value="VT" ${state eq 'VT' ? 'selected="selected"' : ''}>Vermont</option>
            <option value="VA" ${state eq 'VA' ? 'selected="selected"' : ''}>Virginia</option>
            <option value="WA" ${state eq 'WA' ? 'selected="selected"' : ''}>Washington</option>
            <option value="WV" ${state eq 'WV' ? 'selected="selected"' : ''}>West Virginia</option>
            <option value="WI" ${state eq 'WI' ? 'selected="selected"' : ''}>Wisconsin</option>
            <option value="WY" ${state eq 'WY' ? 'selected="selected"' : ''}>Wyoming</option>
            <option value="Alberta" ${state eq 'Alberta' ? 'selected="selected"' : ''}>Alberta</option>
            <option value="British Columbia" ${state eq 'British Columbia' ? 'selected="selected"' : ''}>British Columbia</option>
            <option value="Manitoba" ${state eq 'Manitoba' ? 'selected="selected"' : ''}>Manitoba</option>
            <option value="New Brunswick" ${state eq 'New Brunswick' ? 'selected="selected"' : ''}>New Brunswick</option>
            <option value="Newfoundland and Labrador" ${state eq 'Newfoundland and Labrador' ? 'selected="selected"' : ''}>Newfoundland and Labrador</option>
            <option value="Nova Scotia" ${state eq 'Nova Scotia' ? 'selected="selected"' : ''}>Nova Scotia</option>
            <option value="Ontario" ${state eq 'Ontario' ? 'selected="selected"' : ''}>Ontario</option>
            <option value="Prince Edward Island" ${state eq 'Prince Edward Island' ? 'selected="selected"' : ''}>Prince Edward Island</option>
            <option value="Quebec" ${state eq 'Quebec' ? 'selected="selected"' : ''}>Quebec</option>
            <option value="Saskatchewan" ${state eq 'Saskatchewan' ? 'selected="selected"' : ''}>Saskatchewan</option>
            <option value="NorthWest Territories" ${state eq 'NorthWest Territories' ? 'selected="selected"' : ''}>NorthWest Territories</option>
            <option value="Nunavut" ${state eq 'Nunavut' ? 'selected="selected"' : ''}>Nunavut</option>
            <option value="Yukon Territory" ${state eq 'Yukon Territory' ? 'selected="selected"' : ''}>Yukon Territory</option>
            <option value="N/A" ${state eq 'N/A' ? 'selected="selected"' : ''}>N/A</option>
        </select>
    </td>
</tr>
${orpheus:error('postalCode', validationErrors)}
<tr>
    <td align="right"><span class="style1">*</span>Zip/Postal Code:</td>
    <td align="left"><input type="text" name="postalCode" value="${postalCode}"/></td>
</tr>
${orpheus:error('phone', validationErrors)}
<tr>
    <td align="right"><span class="style1">*</span>Telephone:</td>
    <td align="left">
        <%-- The phone fields are united into a single field as WinnigDataHanadler does not allow to combine multiple
             parameters into a single value --%>
        <input name="phone" value="${phone}" type="text" size="10"/>
    </td>
</tr>
<tr>
    <td align="right">&nbsp;</td>
    <td align="left">
        <input type="submit" name="Submit" value="Submit" onclick="submitForm(document.WinnerDataForm);return false;"/>
    </td>
</tr>
<tr>
    <td align="right">&nbsp;</td>
    <td align="left">&nbsp;</td>
</tr>
</table>
</form>
<div class="tablebot"></div>
</div>
</div>
<script type="text/javascript" xml:space="preserve">
    <!--
    window.focus();
    -->
</script>
</body>
</html>