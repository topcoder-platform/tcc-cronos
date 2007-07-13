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
    <div id="accountData">
    	<form action="${ctx}/server/player/myAccount-changePassword.do" name="ChangePasswordForm" id="ChangePasswordForm" method="POST">
		    <input type="hidden" name="status" value="save" />
		    <table width="0" border="0" cellspacing="0" cellpadding="0">
			  <tr>
			    <td colspan=2><img src="${ctx}/i/h/title_changepassword.gif" alt="Change Password" /></td>
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
			      <p>Please enter your current password and the new password to which you would like it changed.&nbsp; Passwords must be 6-25 characters.</p>
			      <div id="table-form-full">
			      <table width="0" border="0" cellspacing="0" cellpadding="0">
			        <tr>
			          <td width="31%" align="right">Current Password:</td>
			          <td width="69%">
        				<c:if test="${not empty requestScope['currPwd.error']}">
            			  <span class="fBold cRed">${requestScope['currPwd.error']}</span><br/>
        			    </c:if>
        			    <input name="currPwd" type="password" class="inputBox" id="currPwd" />
			          </td>
			        </tr>
			        <tr>
			          <td align="right">New Password:</td>
			          <td>
			            <c:if test="${not empty requestScope['newPwd.error']}">
            			  <span class="fBold cRed">${requestScope['newPwd.error']}</span><br/>
        			    </c:if>
			            <input name="newPwd" type="password" class="inputBox" id="newPwd" />
			          </td>
			        </tr>
			        <tr>
			          <td align="right">Confirm New Password:</td>
			          <td>
			            <c:if test="${not empty requestScope['newPwdConfirm.error']}">
            			  <span class="fBold cRed">${requestScope['newPwdConfirm.error']}</span><br/>
        			    </c:if>
			            <input name="newPwdConfirm" type="password" class="inputBox" id="newPwdConfirm" />
			          </td>
			        </tr>
			        <tr>
			          <td>&nbsp;</td>
			    	  <td><input type="image" src="${ctx}/i/b/btn_save.gif" alt="Save" align="absmiddle"/>&#160;&#160;
			    		<c:if test="${not empty requestScope.success}">
			      		  <font color="green"><b>Password updated.</b></font>
			        	</c:if>
			          </td>
			  		</tr>
			      </table>
			      </div>      
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