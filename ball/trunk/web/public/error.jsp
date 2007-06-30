<%@ page import="java.io.PrintWriter"%>
<%@ page import="com.orpheus.administration.entities.HandlerResult"%>
<%@ page isErrorPage="true" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="/orpheus" prefix="orpheus" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: Error</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/styles.css"/>
</head>

<body id="page">
<div id="container">
    <%@ include file="../includes/header.jsp" %>
    <div id="wrap">
        <h1><img src="${ctx}/i/h/title_sorry.gif" alt="Error"/></h1><br/>
        <c:if test="${failureHandlerResult ne null}">
            <c:set var="_handlerError_" value="true" scope="page"/>
            <c:if test="${failureHandlerResult.cause ne null}">
                <span class="fBold cRed">ERROR CODE: ${failureHandlerResult.cause.message}</span>
                An unexpected error has been encountered while servicing your request. Please, try again or contact to
                The Ball Game Server administrator.
                <%
                    HandlerResult hr = (HandlerResult) request.getAttribute("failureHandlerResult");
                    Exception cause = hr.getCause();
                    cause.printStackTrace(System.out);
                %>
            </c:if>
        </c:if>
        <c:if test="${failureHandlerResult eq null}">
            <c:set var="_pageError_" value="true" scope="page"/>
            <span class="fBold cRed">ERROR CODE: ${exception.message}</span>
            <br/>
            <br/>
            An unexpected error has been encountered while servicing your request. Please, try again or contact to
            The Ball Game Server administrator.
            <br/><br/>
            <%
                if (exception != null) {
                    exception.printStackTrace(new PrintWriter(out));
                }
            %>
        </c:if>
        <c:if test="${_handlerError_ eq null and _pageError_ eq null}">
            An unexpected error has been encountered while servicing your request. Please, try again or contact to
            The Ball Game Server administrator.
        </c:if>
    </div>
</div>
<%@ include file="../includes/footer.jsp" %>
</body>
</html>