<%@ page errorPage="error.jsp" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: How To Play :: Overview</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/styles.css"/>
    <script type="text/javascript" src="${ctx}/js/download.js"></script>
</head>

<body id="page">
<div id="container">
    <c:set var="subbar" value="howto" scope="page"/>
    <%@ include file="../includes/header.jsp" %>
    <div id="wrap">
        <h1><img src="${ctx}/i/h/title_howtoplay.gif" alt="How to Play"/></h1>

        <p>
            Just follow these steps:
        </p>
        <ol>
            <li>Download and install The Ball's plugin. Plugins come in two flavours: Internet Explorer and Mozilla
                Firefox.</li>
            <li>Sign up for a player account
                <a href="${ctx}/public/registration.jsp?phase=start&nextPhase=step1" title="Sign Up">here</a></li>
            <li>Check out the list of active games. You can evaluate them by when they started, what kind of prizes they
                have to offer, or by who is currently leading.</li>
            <li>When you find a game you like, just register for it. Now you're in the game, and it will appear in your
                list of "active games". You can register for a game via the plugin itself in the browser or via the
                Ball's website.</li>
            <li>Open up the plugin and log into it if you have not already done so.</li>
            <li> Click on the "Games" tab to see a listing of available games. Click on the game you wish to play by
                clicking on the hyperlinks for that game in the plugin window. The plugin will show you the starting
                website for each game, and also the latest website that you have found in a specific game, if you have
                already been playing.</li>
            <li> To get details about a specific game, including upcoming website, click on the desired game (e.g.
                Blue23) under the "Games" tab in the plugin window.</li>
            <li>You may browse to the first website listed for the game as having hosted the ball or you may browse to
                any of the websites listed under "Unlocked Websites" if you would like to actively pursue keys for a
                specific game. Otherwise you may browse around generally on the internet until you find a site that has
                a key for the game. Note: You should remain logged into your browser at all times, if you wish to be
                notified of any sites that contain keys or the ball, even if you are not actively pursuing the ball or a
                game at this time.</li>
            <li>Once you get to a site that has a key or the ball, you will be presented with a notification in the
                plugin window when you go to any page on that site. If the site is hosting a ball or key for more than
                one games, you will be presented with a choice of games. Click on the game you wish to follow.</li>
            <li>You will be presented with a brainteaser puzzle (word scramble or missing letter puzzle) to first solve
                to get a "target hunt object" which you must then find on the website somewhere. Once you have solved
                the brainteaser or think you have found the target hunt object on the site, right-click and choose
                "Test Target Object" from the browser context menu that will appear. Note: You must find the correct
                instance of a target object e.g. if the target object is "stone" and that word appears several times on
                a website, only one instance is the correct one.</li>
            <li>If you are correct, you will be given the next object to find on that site. This time the hunt object
                will not be scrambled - you will simply have to find the object. We will give you up to five (5) target
                objects to find on that site, before giving you a key for that site or before you find the ball if it
                happens to be on that site at the time. Collect the keys and write them down as you may need them when
                you find the ball.</li>
            <li>If you find the ball, you will be asked to enter keys to unlock it. Keys are specific to an individual
                player and you must enter your keys correctly. You will have up to three (3) tries to enter the keys
                correctly. If you are not able to do so successfully, the ball will move on and you will have to catch
                it again, and try to unlock it with the correct keys again.</li>
            <li>If you are able to unlock the ball, you will be given a jigsaw, slider or other puzzle to solve within
                a limited time. If you are able to solve this final puzzle in time, you stand a good chance of winning
                the money for that game. Fill out the game winner details and send it to us. We will verify your
                eligibility to win and contact you as soon once we are satisfied with eligibility.</li>
            <li>Enjoy your fun-earned cash!</li>
        </ol>
        <p>
            <span class="fBold">Tips</span><br/>
            <ul lang="en">
                <li>To get details about a specific game, including upcoming website, click on the desired game (e.g.
                    Blue23) under the "Games" tab in the plugin window.</li>
                <li>"Unlocked Websites" shows all the keys that you have found so far in the game (you must choose a
                    game first to see its associated details from the "Games" tab), as well as any other websites for
                    which rival players have found keys in that game. Sites are listed in the chronological order in
                    which they hosted the ball. If a site appears more than once, it means that site has hosted the ball
                    more than once in the game.</li>
                <li>"Upcoming Domains" shows websites that have won the right to host the ball in the relatively near
                    future. Note: Upcoming Domains are NOT listed in chronological order</li>
                <li>The leaderboard shows a ranking of players based on a combination of factors including number of
                    keys found, when keys were found, proximity of keys found to the current site hosting the ball,
                    finding of the keys in continguous order and other criteria.</li>
                <li>Keys found by you are stored in the "Unlocked Websites" tab in the plugin - if you find the ball and
                    cannot remember your keys, you may access the "Unlocked Websites" tab and then use the browser's
                    "back" menu item (accessed by "right-clicking" with your mouse) to get back to the plugin window
                    screen to enter keys.</li>
            </ul>
        </p>
        Good hunting!
        <p></p>

        <p>
            <span class="fBold">Download</span><br/>
            To being playing, download The Ball plug-in
            <a href="#" onclick="showDownload('${ctx}');return false;">here.</a>
        </p>

        <p>
            <span class="fBold">Play</span><br/>Register to play
            <a href="${ctx}/public/registration.jsp?phase=start&nextPhase=step1">here.</a>
        </p>
    </div>
</div>
<%@ include file="../includes/footer.jsp" %>
</body>
</html>