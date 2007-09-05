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
    <c:set var="subbar" value="howto" scope="page"/>
    <c:set var="subbar2" value="help" scope="page"/>
    <%@ include file="../includes/header.jsp" %>
    <div id="wrap">
        <p><img src="${ctx}/i/help/img_unlock.gif" alt="How to Play" width="175" height="18"/></p>

        <ol>
            <li>When you find the ball, you will be prompted to enter keys from the previous X sites to unlock the ball.&nbsp;
                The number of keys required to unlock the ball can differ from game to game. If you cannot remember the
                keys, click on the &ldquo;Unlock Websites&rdquo; link in the text on the Ball page to open up a new
                window and display your keys for that game so far.
            </li>
        </ol>

        <blockquote>
            <p>Note: You must enter keys that are valid for the X sites immediately preceding the ball&rsquo;s current
                location.&nbsp; You may not have all the required keys or you may have keys that are further away from
                the ball&rsquo;s current location than &ldquo;X&rdquo; and are therefore not valid to unlock the ball.
                If you do not have enough of the correct keys, you will need to start hunting again in sites that hosted
                the ball earlier to find the needed keys.&nbsp; Use the &ldquo;Unlocked Sites&rdquo; feature to get a
                hint as to where you might find other needed keys, as that screen will show the sites where all players
                have found keys so far in the game.&nbsp; Alternately you may try to keep following the ball to its next
                location in an attempt to collect the right keys.</p>
        </blockquote>
        <p class="cOrange"><strong>Screen-shot of Finding the Ball:</strong></p>

        <p align="center"><img src="${ctx}/i/help/unlock_01.jpg" width="350" height="270" alt=""/></p>

        <p class="cOrange"><strong>Screen-shot showing use of &ldquo;Unlocked Sites&rdquo; link accessed from the Found
            Ball page</strong></p>

        <p align="center"><img src="${ctx}/i/help/unlock_02.jpg" width="350" height="270" alt=""/></p>

        <p>&nbsp;</p>
        <ol start="2">
            <li> You have up to three attempts to enter keys from the previous X sites.&nbsp; If you do not successfully
                unlock the ball during this time, the ball will move on to its next hosting site, and you will be given
                a key for this site.
            </li>
        </ol>
        <p align="center"><img src="${ctx}/i/help/unlock_03.jpg" width="350" height="270" alt=""/></p>
        <ol start="3">
            <li> If you successfully unlock the ball, you will be presented with a <a href="${ctx}/server/puzzle/jigsaw.do">jigsaw 
                 puzzle</a> or a <a href="${ctx}/server/puzzle/tile.do">slider puzzle</a> to solve within our time limit, 
                 before you are eligible to win a prize.&nbsp; If you are not able to solve the puzzle in time, the ball 
                 will move on to its next hosting site and you will be given a key for the site.&nbsp; There is a timer 
                 on-screen which helps you to count down that you have left to solve the puzzle.
            </li>
        </ol>
        <p align="center"><img src="${ctx}/i/help/unlock_04.jpg" width="350" height="270" alt=""/></p>

        <p align="center">&nbsp;</p>

        <p align="center">&nbsp;</p>
    </div>
</div>
<%@ include file="../includes/footer.jsp" %>
</body>
</html>