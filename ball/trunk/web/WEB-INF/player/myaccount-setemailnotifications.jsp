<%@ page import="com.topcoder.user.profile.UserProfile,
				 com.orpheus.user.persistence.UserConstants" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="/paging" prefix="p" %>
<%@ taglib uri="/orpheus" prefix="orpheus" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<fmt:setLocale value="en_US"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<% 	UserProfile profile = (UserProfile)request.getAttribute("profile"); 
	Boolean genPref = (Boolean)profile.getProperty(UserConstants.PREFS_GENERAL_NOTIFICATION); %>

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
    	<form action="${ctx}/server/player/myAccount-setEmailNotifications.do" name="SetEmailNotificationsForm" id="SetEmailNotificationsForm" method="POST">
		    <input type="hidden" name="status" value="save" />
		    <table width="0" border="0" cellspacing="0" cellpadding="0">
			  <tr>
			    <td colspan=2><img src="${ctx}/i/h/title_setemail.gif" alt="Set Email Notifications" /></td>
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
			      <p>From time to time we will be sending updates to players registered for the Ball game. 
			         These updates can include but are not limited to maintenance updates, new games, version updates and 
			         other useful information.</p>
			      <p>Please indicate whether you would like to receive these email updates.&nbsp; 
			         You can remove yourself from the email list at any time in the future:<br /></p>
			      <table width="0" border="0" cellspacing="0" cellpadding="0">
			        <tr>
			          <td width="13%" align="right">
			          	<% 	if (Boolean.TRUE.equals(genPref)) { %>
			          		<input name="general" id="general" type="checkbox" checked />
			          	<% 	} else { %>
			          		<input name="general" id="general" type="checkbox" />
			          	<% 	} %> 
			          </td>
			          <td width="87%">
			            <label>General Ball Notifications</label>
			          </td>
			        </tr>
			        <tr>
			          <td align="right">&nbsp;</td>
			          <td><input type="image" src="${ctx}/i/b/btn_save.gif" alt="Save"/></td>
			        </tr>
			      </table>
	      		</td>
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