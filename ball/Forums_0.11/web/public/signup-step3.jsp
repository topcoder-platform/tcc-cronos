<%@ page errorPage="error.jsp" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="/registration" prefix="reg" %>
<%@ taglib uri="/orpheus" prefix="orpheus" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="phase" value="${param.phase}"/>
<c:set var="nextPhase" value="${param.nextPhase}"/>
<c:set var="registrationManager" value="${orpheus:getRegistrationManager()}"/>
<c:if test="${not empty exception}">
    <c:if test="${orpheus:isDuplicateEmailAddress(exception)}">
        <c:redirect url="/public/login.jsp?duplicate=yes" />
    </c:if>
    <c:if test="${orpheus:isDuplicateHandle(exception)}">
        <c:redirect url="/public/login.jsp?duplicateHandle=yes"/>
    </c:if>
</c:if>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: How To Play :: Sign Up :: Confirm Registration</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/styles.css"/>
</head>

<body id="page">
<div id="container">
    <c:set var="subbar" value="howto" scope="page"/>
    <%@ include file="../includes/header.jsp" %>
    <div id="wrap">
        <h1><img src="${ctx}/i/h/title_signup.gif" alt="Sign Up"/></h1>

        <c:if test="${empty exception}">
            <div id="breadcrumb">
                &raquo; 1. View Terms of Service &nbsp; &raquo; 2. Enter Account Information &nbsp;
                <span class="active"> &raquo; 3. Confirm Registration</span>
            </div>

            <span class="fBold">You have successfully created an account.<br/></span>
            <p>
                You will receive a confirmation email shortly with details of your account. Once you've received your
                email, you can go ahead and <a href="${ctx}/public/login.jsp">log-in here</a> to register for a game and
                to begin playing. Instructions on how to play can be found
                <a href="${ctx}/public/faq-player.jsp">here</a>.
            </p>
        </c:if>

        <c:if test="${not empty exception}">
            <div id="breadcrumb">
                &raquo; 1. View Terms of Service &nbsp; &raquo; 2. Enter Account Information &nbsp;
                <span class="active"> &raquo; 3. Confirm Registration - ERROR</span>
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