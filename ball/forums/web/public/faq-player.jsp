<%@ page errorPage="error.jsp" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: How To Play :: FAQ</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/styles.css"/>
</head>

<body id="page">
<div id="container">
    <c:set var="subbar" value="howto" scope="page"/>
    <%@ include file="../includes/header.jsp" %>
    <div id="wrap">
        <h1><img src="${ctx}/i/h/title_playerfaq.gif" alt="Player FAQ"/></h1>
        <ol>
            <li><a href="#1">Why do I have to register for each game?</a></li>
            <li><a href="#2">Can I join a game that's already in progress?</a></li>
            <li><a href="#3">Can more than one person win a game?</a></li>
            <li><a href="#4">If someone else wins, will the other players be notified?</a></li>
            <li><a href="#5">Do I have to install a plug-in?</a></li>
            <li><a href="#6">Can I install the plug-in in more than one place, or play from two different computers?</a>
            </li>
        </ol>
        <hr class="grayline"/>
        <ol>
            <li><a name="1"></a><span class="faq">Why do I have to register for each game?</span><br/>
                Since rules can potentially differ from game to game, pre-registration ensures that players have agreed
                to the specific rules that apply to a particular game.
                <br/>&nbsp;</li>

            <li><a name="2"></a><span class="faq">Can I join a game that's already in progress?</span><br/>
                You can, and you can follow other players' progress on "the leader board" to figure out where the action
                is, and how you can catch up.
                <br/>&nbsp;</li>

            <li><a name="3"></a><span class="faq">Can more than one person win a game?</span><br/>
                Nope. Once a player has found The Ball, solved the final puzzle, and successfully used the game keys,
                that's it.
                <br/>&nbsp;</li>

            <li><a name="4"></a><span class="faq">If someone else wins, will the other players be notified?</span><br/>
                Yes, you will be alerted through The Ball plug-in if a game you're registered for has been won. (Please
                note: you will need to be logged in to The Ball to be notified.) If you're not logged in, or are using a
                different computer than the one you've installed The Ball on, you can check on the status of a game by
                going The Ball's Web site.
                <br/>&nbsp;</li>

            <li><a name="5"></a><span class="faq">Do I have to install a
				plug-in?</span><br/>
                You do. The plug-in detects the presence of The Ball, and keeps track of your progress in the game. You
                can't play without it. Sorry!
                <br/>&nbsp;</li>
            <li><a name="6"></a><span class="faq">Can I install the plug-in in more than one place, or play from two different computers?</span>
                <br/>
                Sure. You can install the plug-in at home and the office, or on different computers around the house,
                and log-in from wherever you are and resume your game. You can only log-in from one computer at a time,
                however.
                <br/>&nbsp;</li>

        </ol>
    </div>
</div>
<%@ include file="../includes/footer.jsp" %>
</body>
</html>