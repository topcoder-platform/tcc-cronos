<%@ page isErrorPage="true" %>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="com.orpheus.administration.entities.HandlerResult"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="/paging" prefix="p" %>
<%@ taglib uri="/orpheus" prefix="orpheus" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: Error</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/plugin.css"/>
</head>

<body id="pagePlugin">
<div id="containerPlugin">
    <div id="mastHead"><img src="${ctx}/i/plugin_stripes.jpg" width="668" height="62" alt=""/></div>

    <div id="pluginTitle">
        <c:if test="${failureHandlerResult ne null}">
            <c:if test="${failureHandlerResult.cause ne null}">
                <span class="fBold cRed">ERROR CODE: ${failureHandlerResult.cause.message}</span>
                <%
                    HandlerResult hr = (HandlerResult) request.getAttribute("failureHandlerResult");
                    Exception cause = hr.getCause();
                    cause.printStackTrace(System.out);
                %>
            </c:if>
        </c:if>
        <c:if test="${failureHandlerResult eq null}">
            <span class="fBold cRed">ERROR CODE: ${exception.message}</span>
            <%
                if (exception != null) {
                    exception.printStackTrace(System.out);
                }
            %>
        </c:if>
    </div>

    <div id="wrapPlugin">
        <br/>
        An unexpected error has been encountered while servicing your request. Please, try again or contact to The Ball
        Game Server administrator.
        <br/><br/>
    </div>
</div>

</body>
</html>