<%@ page errorPage="error.jsp" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: Help</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/styles.css"/>
</head>

<body id="page">
<div id="container">
    <c:set var="subbar" value="sponsor" scope="page"/>
    <c:set var="subbar2" value="help-sponsor" scope="page"/>
    <%@ include file="../includes/header.jsp" %>
    <div id="wrap2">
        <h1><img src="${ctx}/i/h/title_sponsoragame.gif" alt="Sponsor a Game"/></h1>

        <p>The Ball is a little like baseball &ndash; there is no specified end date or time, so the game takes just as
            long as it takes for players to pursue clues, solve puzzles, and track The Ball across the Web. <br/>
            <strong>Just follow these steps: </strong></p>
        <ol>
            <li><a href="help_create_sponsor.jsp">Creating sponsor account</a></li>
            <li><a href="help_auction_bidding.jsp">Auction bidding instructions </a></li>
        </ol>
        <p>&nbsp;    </p>
    </div>
</div>
<%@ include file="../includes/footer.jsp" %>
</body>
</html>