<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/plugin.css"/>
</head>

<body id="pagePlugin">
<div id="containerPlugin">
    <div id="mastHead"><img src="${ctx}/i/plugin_stripes.jpg" width="668" height="62" alt=""/></div>

    <div id="pluginTitle">MESSAGE SENT <span class="main"></span></div>

    <div id="wrapPlugin">
        <br>
        We've found you in our databases and you will be receiving an email shortly with your account information. Once
        you receive the email, you can come back and <a href="${ctx}/plugin/login.jsp">log-in to your account here</a>.

        <p>&nbsp;</p>
    </div>
</div>

</body>
</html>