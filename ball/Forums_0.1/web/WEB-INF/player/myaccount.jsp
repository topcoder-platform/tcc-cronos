<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="/paging" prefix="p" %>
<%@ taglib uri="/orpheus" prefix="orpheus" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<fmt:setLocale value="en_US"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
    <div id="account">
	    <table width="0" border="0" cellspacing="0" cellpadding="0">
		  <tr>
		    <td><img src="${ctx}/i/h/title_myaccount.gif" alt="My Account" /></td>
		    <td>&nbsp;</td>
		  </tr>
		  <tr>
		    <td>&nbsp;</td>
		    <td>&nbsp;</td>
		  </tr>
		  <tr>
		    <td><h3>Account Changes</h3>
		      <ul>
		        <li><a href="${ctx}/server/player/myAccount-changePassword.do">Change my password</a></li>
		        <li><a href="${ctx}/server/player/myAccount-updateProfile.do">Update my profile</a></li>
		      </ul>
		    </td>
		    <td><h3>Preferences</h3>
		      <ul>
		        <li><a href="${ctx}/server/player/myAccount-setSound.do">Set my sound preferences</a></li>
		        <li><a href="${ctx}/server/player/myAccount-setEmailNotifications.do">Set my e-mail notifications</a></li>
		      </ul>
		    </td>
		  </tr>
		</table>
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