<%@ page errorPage="error.jsp" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: Retrieve Password :: Confirmation</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/styles.css"/>
</head>

<body id="page">
<div id="container">
    <%@ include file="../includes/header.jsp" %>
    <div id="wrap">
        <h1><img src="${ctx}/i/h/title_messagesent.gif" alt="Message Sent"/></h1>

        <p>We've found you in our databases and you will be receiving an email shortly with your account information.
            Once you receive the email, you can come back and <a href="${ctx}/public/login.jsp">log-in to your account
            here.</a>

        <p>&nbsp;</p>
    </div>
</div>
<%@ include file="../includes/footer.jsp" %>
</body>
</html>