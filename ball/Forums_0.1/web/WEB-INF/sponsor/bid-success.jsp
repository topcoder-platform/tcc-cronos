<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="/orpheus_auction" prefix="auction" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<fmt:setLocale value="en_US"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: Bid Successful</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/styles.css"/>
</head>

<body id="page">
<div id="container">
    <c:set var="subbar" value="auctions" scope="page"/>
    <%@ include file="header.jsp" %>
    <div id="wrap">
        <h1><img src="${ctx}/i/h/title_bidsuccessful.gif" alt="Bid Successful"/></h1>

        <div id="breadcrumb">
            &raquo; 1. View Games with Available Timeslots &nbsp; &raquo; 2. Bid on a Timeslot &nbsp;
            <span class="active">&raquo; 3. Bid Confirmation</span>
        </div>

        <p>
            Your bid of
            <span class="cOrange">$<fmt:formatNumber value="${auction:toLong(param['maxBid'])}" pattern="#,##0.00"/></span>
            for <span class="cOrange">${game.name}</span> has been submitted and you currently have a qualifying
            position in this auction. If you are outbid later on, you can always come back and increase your bid.
            Remember, your payment will not be deducted from your account unless the ball makes to to your website.
        </p>

    <p>Return to <a href="${ctx}/server/sponsor/openAuctions.do">Open Auctions</a></p>
</div>
</div>
<%@ include file="/includes/footer.jsp" %>
</body>
</html>