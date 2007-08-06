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
    <%@ include file="../includes/header.jsp" %>
    <div id="wrap">
        <p><img src="${ctx}/i/help/img_sign_up.gif" alt="How to Play" width="220" height="18"/></p>

        <ol>
            <li>To play in our game, you must first sign up for a player account.&nbsp; You can do by going
                <a href="registration.jsp?phase=start&nextPhase=step1">here</a>
            </li>
        </ol>

        <p align="center"><img src="${ctx}/i/help/signup_01.jpg" width="350" height="270" alt=""/></p>

        <p align="center">
        <ol>
            <li>Read through our Terms &amp; Conditions and click in the &ldquo;I agree&rdquo; box if you agree to them.&nbsp;
                Note: You will not be allowed to play in the game if you do you not agree to the Terms &amp; Conditions.
            </li>
        </ol>
        <p align="center"><img src="${ctx}/i/help/signup_02.jpg" width="350" height="270" alt=""/></p>

        <ol start="2">
            <li>Fill out the form including providing a &ldquo;Display Name&rdquo; i.e. a nick-name that you will be
                known by to other players as well as being the user id that you will use to log into the website.
            </li>
        </ol>
        <p align="center"><img src="${ctx}/i/help/signup_03.jpg" width="350" height="270" alt=""/></p>
        <ol start="3">
            <li>When you have filled out the form, click the orange submit button on the bottom of the screen.</li>
        </ol>

        <p align="center"><img src="${ctx}/i/help/signup_04.jpg" width="350" height="270" alt=""/>
        </p>
        <ol start="4">
            <li>You will be notified whether you were successful in signing up for an account and will be given further
                instructions on how to activate your account via the link to be sent in an email from
                do-not-reply@theball.com
            </li>
        </ol>
        <p align="center"><img src="${ctx}/i/help/signup_05.jpg" width="350" height="270" alt=""/></p>

        <p class="cOrange"><strong>Logging Into the Website or the Plugin as a Player</strong></p>
        <ol>
            <li>After you have successfully downloaded and installed the plugin in either Internet Explorer or Firefox
                as well as having set up a player account, you are ready to log into system.
            </li>
            <li>There are two options to log in as a player.&nbsp; You may log into the website directly or you may log
                into the plugin.&nbsp; Logging into either option will automatically log you into the other place.
            </li>
            <li>To log into the website, simply go to the website at <a href="http://www.theball.com/">http://www.theball.com</a>
                and enter your used id and password (remember that your user id is the &ldquo;display name&rdquo;) in
                the fields for them on the home page. Note before logging into the website, the plugin status button in
                the top-right of the screen says &ldquo;Login&rdquo;.
            </li>
        </ol>
        <p align="center"><img src="${ctx}/i/help/signup_06.jpg" width="350" height="270" alt=""/></p>

        <p>
        </p>
        <ol start="4">
            <li>You will be taken to the player home page where you will be provided with additional instructions as a
                reminder of game play and you will be shown any Active Games (i.e. games which are currently in progress
                and which you may join to play).&nbsp; You can see the game status, the maximum jackpot possible, the
                name of the game and game start details.&nbsp; Note the plugin status button in the top-right of the
                screen says &ldquo;Logout&rdquo;.s
            </li>
        </ol>
        <p align="center"><img src="${ctx}/i/help/signup_07.jpg" width="350" height="270" alt=""/></p>

        <p>
        <ol start="5">
            <li>You can also choose to click on &ldquo;My Games&rdquo; which will show you what games you have
                registered for as well as their current status, the name of the game and the total jackpot value.
            </li>
        </ol>
        <p align="center"><img src="${ctx}/i/help/signup_08.jpg" width="350" height="270" alt=""/>
        </p>
        <ol start="6">
            <li>Alternately you may log into the plugin directly.&nbsp; To do so simply click on the &ldquo;Login&rdquo;
                button in The Ball&rsquo;s toolbar on the top-right of the screen
            </li>
        </ol>
        <p align="center"><img src="${ctx}/i/help/signup_09.jpg" width="350" height="270" alt=""/></p>

        <p>&nbsp;</p>
        <ol start="7">
            <li>Alternately you may log into the plugin directly.&nbsp; To do so simply click on the &ldquo;Login&rdquo;
                button in The Ball&rsquo;s toolbar on the top-right of the screen
            </li>
        </ol>
        <p align="left"><img style="padding: 5px;" src="${ctx}/i/help/signup_10.jpg" width="350"
                             height="270" alt=""/><img style="padding: 5px;" src="${ctx}/i/help/signup_11.jpg"
                                                       width="350" height="270" alt=""/></p>

        <p align="center">&nbsp;</p>

        <p align="left">&nbsp;</p>
    </div>
</div>
<%@ include file="../includes/footer.jsp" %>
</body>
</html>