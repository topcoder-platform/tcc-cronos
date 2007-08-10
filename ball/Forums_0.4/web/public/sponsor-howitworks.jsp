<%@ page errorPage="error.jsp" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: Sponsor A Game :: How It Works</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/styles.css"/>
</head>

<body id="page">
<div id="container">
    <c:set var="subbar" value="sponsor" scope="page"/>
    <%@ include file="../includes/header.jsp" %>
    <div id="wrap">
        <h1><img src="${ctx}/i/h/title_howitworks.gif" alt="How it Works"/></h1>

        <p>
            The Ball is a little like baseball – there is no specified end date or time, so the game takes just as long
            as it takes for players to pursue clues, solve puzzles, and track The Ball across the Web.
            <br/>
            <br/>
            The analogy works for sponsors, too. Each game is composed of a number of blocks, which are sort of like
            innings in a baseball game - you don't know when exactly they'll happen, just that you'll get your turn if
            you win one of our auction slots.
            <br/>
            <br/>
            Within each block is a number of slots. In baseball terms, these would correspond to advertising slots
            within a particular inning. When your slot comes up - which is governed by the progress of the game, and a
            few randomizing factors we've built in to eliminate cheating - The Ball will be "hosted" on your Web site,
            and players will swarm all over it.
            <br/>
            <br/>
            To sponsor a game, you must register to become a sponsor. Once you're verified, you can join in our
            Sponsorship Auction by logging into our website with your sponsor credentials.
            <br/>
            <br/>
            <b>Note:</b> You or affiliated members of your company are barred from participate as a player in a game in
            which you are a sponsor. If you have any questions, please do not hesitate to
            <a href="mailto:${orpheus:getContactEmailAddress()}">contact us</a>.
        </p>
    </div>
</div>
<%@ include file="../includes/footer.jsp" %>
</body>
</html>