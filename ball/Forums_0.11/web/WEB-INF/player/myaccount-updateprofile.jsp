<%@ page import="com.topcoder.user.profile.UserProfile,
				 com.topcoder.user.profile.BaseProfileType,
				 com.orpheus.user.persistence.UserConstants" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="/paging" prefix="p" %>
<%@ taglib uri="/orpheus" prefix="orpheus" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<fmt:setLocale value="en_US"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<% 	UserProfile profile = (UserProfile)request.getAttribute("profile"); %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: My Account</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/styles.css"/>
</head>

<body id="page">
<div id="container">
<c:set var="subbar" value="myaccount" scope="page"/>
<%@ include file="header.jsp" %>

<div id="wrap">
    <div id="accountData">
    	<form action="${ctx}/server/player/myAccount-updateProfile.do" name="UpdateProfileForm" id="UpdateProfileForm" method="POST">
		    <input type="hidden" name="status" value="save" />
		    <table width="0" border="0" cellspacing="0" cellpadding="0">
			  <tr>
			    <td colspan=2><img src="${ctx}/i/h/title_updateprofile.gif" alt="Update Profile" /></td>
			  </tr>
			  <tr>
			    <td width="28%">&nbsp;</td>
			    <td width="72%">&nbsp;</td>
			  </tr>
			  <tr>
			    <td valign="top">
			      <%@ include file="myaccount-left.jsp" %>
			    </td>
			    <td valign="top">
			      <p>Please update the fields below and submit the information. <font color="#FF0000"><br />*Required</font><br /></p>
			      <div id="table-form-full">
			      <table width="0" border="0" cellspacing="0" cellpadding="0">
			        <tr>
			          <td width="31%" align="right"><font color="#FF0000">*</font>Email address:</td>
			          <td width="69%">
			            <c:if test="${not empty requestScope['email.error']}">
            			  <span class="fBold cRed">${requestScope['email.error']}</span><br/>
        			    </c:if>
			            <input name="email" type="text" class="inputBox" id="email" value="<%=profile.getProperty(BaseProfileType.EMAIL_ADDRESS)%>" />  
			          </td>
			        </tr>
			        <tr>
			          <td align="right"><font color="#FF0000">*</font>First name:</td>
			          <td>
			            <c:if test="${not empty requestScope['firstName.error']}">
            			  <span class="fBold cRed">${requestScope['firstName.error']}</span><br/>
        			    </c:if>
			            <input name="firstName" type="text" class="inputBox" id="firstName" value="<%=profile.getProperty(BaseProfileType.FIRST_NAME)%>" />
			          </td>
			        </tr>
			        <tr>
			          <td align="right"><font color="#FF0000">*</font>Last name:</td>
			          <td>
			            <c:if test="${not empty requestScope['lastName.error']}">
            			  <span class="fBold cRed">${requestScope['lastName.error']}</span><br/>
        			    </c:if>
			            <input name="lastName" type="text" class="inputBox" id="lastName" value="<%=profile.getProperty(BaseProfileType.LAST_NAME)%>" />
			          </td>
			        </tr>
			        <tr>
			          <td align="right"><font color="#FF0000">*</font>Address 1:</td>
			          <td>
			            <c:if test="${not empty requestScope['addr1.error']}">
            			  <span class="fBold cRed">${requestScope['addr1.error']}</span><br/>
        			    </c:if>
			            <input name="addr1" type="text" class="inputBox" id="addr1" value="<%=profile.getProperty(UserConstants.ADDRESS_STREET_1)%>" />
			          </td>
			        </tr>
			        <tr>
			          <td align="right">Address 2:</td>
			          <td>
			            <c:if test="${not empty requestScope['addr2.error']}">
            			  <span class="fBold cRed">${requestScope['addr2.error']}</span><br/>
        			    </c:if>
			            <input name="addr2" type="text" class="inputBox" id="addr2" value="<%=profile.getProperty(UserConstants.ADDRESS_STREET_2)%>" />
			          </td>
			        </tr>
			        <tr>
			          <td align="right"><font color="#FF0000">*</font>City:</td>
			          <td>
			            <c:if test="${not empty requestScope['city.error']}">
            			  <span class="fBold cRed">${requestScope['city.error']}</span><br/>
        			    </c:if>
			            <input name="city" type="text" class="inputBox" id="city" value="<%=profile.getProperty(UserConstants.ADDRESS_CITY)%>" />
			          </td>
			        </tr>
			        <tr>
			          <td align="right">State:</td>
			          <td><%@ include file="myaccount-updateprofile-state.jsp" %></td>
			        </tr>
			        <tr>
			          <td align="right"><font color="#FF0000">*</font>Country:</td>
			          <td><%@ include file="myaccount-updateprofile-country.jsp" %></td>
			        </tr>
			        <tr>
			          <td align="right">Postal code:</td>
			          <td>
			            <c:if test="${not empty requestScope['postalCode.error']}">
            			  <span class="fBold cRed">${requestScope['postalCode.error']}</span><br/>
        			    </c:if>
			            <input name="postalCode" type="text" class="inputBox" id="postalCode" size="10" value="<%=profile.getProperty(UserConstants.ADDRESS_POSTAL_CODE)%>" />
			          </td>
			        </tr>
			        <tr>
			          <td align="right">Payment Method:</td>
			          <td><label>
			            <select name="paymentMethod" class="dropDown" id="paymentMethod">
			              <option value=""></option>
			              <%  String[] paymentMethodValues = {"Check", "PayPal"};
			              	  String[] paymentMethodFields = {"Check", "PayPal"};
			              	  for (int i=0; i<paymentMethodValues.length; i++) {
			              	  	if (paymentMethodValues[i].equals(profile.getProperty(UserConstants.PLAYER_PAYMENT_PREF))) { %>
			              	  		<option value="<%=paymentMethodValues[i]%>" selected><%=paymentMethodFields[i]%></option>
			              <%    } else { %>
			              			<option value="<%=paymentMethodValues[i]%>"><%=paymentMethodFields[i]%></option>
			              <%	} %>
			              <%  } %>
			            </select>
			          </label></td>
			        </tr>
			        <tr>
			          <td align="right"><font color="#FF0000">*</font>Telephone:</td>
			          <td>
			            <c:if test="${not empty requestScope['phone.error']}">
            			  <span class="fBold cRed">${requestScope['phone.error']}</span><br/>
        			    </c:if>
			            <input name="phone" type="text" class="inputBox" id="phone" value="<%=profile.getProperty(UserConstants.ADDRESS_PHONE_NUMBER)%>" />
			          </td>
			        </tr>
			        <tr>
			          <td align="right">&nbsp;</td>
			          <td><input type="image" src="${ctx}/i/b/btn_save.gif" alt="Save"/></td>
			        </tr>
			        <tr>
			          <td align="right">&nbsp;</td>
			          <td>&nbsp;</td>
			        </tr>
			      </table>
			      </div>      
			      <p>&nbsp;</p></td>
			  </tr>
			</table>
		</form>
	</div>
    <div class="tablebot">
      <p>&nbsp;</p>
    </div>
  </div>
</div>

</div>
<%@ include file="/includes/footer.jsp" %>
</body>
</html>