<%@ page errorPage="error.jsp" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="/orpheus" prefix="orpheus" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:if test="${not empty exception}">
    <c:if test="${orpheus:isDuplicateEmailAddress(exception)}">
        <c:redirect url="/public/login.jsp?duplicate=yes"/>
    </c:if>
    <c:if test="${orpheus:isDuplicateHandle(exception)}">
        <c:redirect url="/public/login.jsp?duplicateHandle=yes"/>
    </c:if>
</c:if>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: Sponsor A Game :: Sign Up :: Confirmation</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/styles.css"/>
</head>

<body id="page">
<div id="container">
    <c:set var="subbar" value="sponsor" scope="page"/>
    <%@ include file="../includes/header.jsp" %>
    <div id="wrap">
        <h1><img src="${ctx}/i/h/title_sponsorregistration.gif" alt="Sponsor Registration"/></h1>

        <c:if test="${empty exception}">
            <div id="breadcrumb">&raquo; 1. Review Terms of Service &nbsp; &raquo; 2. Enter Account Information
                &nbsp; &raquo; 3. Submit Your Website &nbsp; <span class="active"> &raquo;
                4. Confirm Registration</span>
            </div>

            <span class="fBold">You have successfully signed up as a Sponsor.</span>
            <br>
            You will receive a confirmation email shortly with the details of your account and information on how to
            complete your registration.<br>
            <br>
            Once You've received your email, you can go ahead and <a href="${ctx}/public/login.jsp">log-in here</a> to
            register for a game and begin playing.
        </c:if>
        <c:if test="${not empty exception}">
            <div id="breadcrumb">&raquo; 1. Review Terms of Service &nbsp; &raquo; 2. Enter Account Information
                &nbsp; &raquo; 3. Submit Your Website &nbsp; <span class="active"> &raquo;
                4. Confirm Registration - ERROR</span>
            </div>

            <span class="fBold">An unexpected error has been encountered while creating an account. The error message is
                : ${exception.message}<br/></span>

            <p>
                Please, try again or contact to The Ball Game Server administrator.
            </p>
        </c:if>
        <p>&nbsp;</p>
    </div>
</div>
<%@ include file="../includes/footer.jsp" %>
</body>
</html>