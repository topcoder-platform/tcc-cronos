<%@ page isErrorPage="true" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: Permission Denied</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/styles.css"/>
</head>

<body id="page">
<div id="container">
    <%@ include file="../includes/header.jsp" %>
    <div id="wrap">
        <h1><img src="${ctx}/i/h/title_sorry.gif" alt="Error"/></h1><br/>
        <span class="fBold cRed">ERROR CODE: PERMISSION DENIED</span>
        <br/>
        <br/>
        You are not granted a permission to perform the requested aciton.
        <br/>
        <br/>
    </div>
</div>
<%@ include file="../includes/footer.jsp" %>
</body>
</html>