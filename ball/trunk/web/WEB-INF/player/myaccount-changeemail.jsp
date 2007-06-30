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
	    <table width="0" border="0" cellspacing="0" cellpadding="0">
		  <tr>
		    <td colspan=2><img src="${ctx}/i/h/title_changeemail.gif" alt="Change Email Address" /></td>
		  </tr>
		  <tr>
		    <td width="28%">&nbsp;</td>
		    <td width="72%">&nbsp;</td>
		  </tr>
		  <tr>
		    <td valign="top">
		      <%@ include file="myaccount-left.jsp" %>
		    </td>
		    <td valign="top"><p>Please enter your current email address and the new email address to which you would like it changed.</p>
		      <table width="0" border="0" cellspacing="0" cellpadding="0">
		        <tr>
		          <td width="31%" align="right">Enter old address:</td>
		          <td width="69%">
		            <input name="textfield" type="text" class="inputBox" id="textfield" />
		          </td>
		        </tr>
		        <tr>
		          <td align="right">Enter new address:</td>
		          <td><input name="textfield2" type="text" class="inputBox" id="textfield2" /></td>
		        </tr>
		        <tr>
		          <td align="right">Confirm new address:</td>
		          <td><input name="textfield3" type="text" class="inputBox" id="textfield3" /></td>
		        </tr>
		      </table>      
		      <p>&nbsp;</p>
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