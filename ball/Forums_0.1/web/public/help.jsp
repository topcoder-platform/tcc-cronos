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
    <%@ include file="../includes/header.jsp" %>
    <div id="wrap">
        <p><img src="${ctx}/i/h/title_help.gif" alt="Help"/></p>

        <p>The Ball is a little like baseball &ndash; there is no specified end date or time, so the game takes just as
            long as it takes for players to pursue clues, solve puzzles, and track The Ball across the Web. <br/>
            <strong>Playing is easy. Just follow these steps: </strong></p>
        <ol>
            <li>Download and install The Ball&rsquo;s plugin.&nbsp; Plugins come in two flavours: Internet Explorer and
                Mozilla Firefox.
            </li>
            <li>Sign up for a player account here [isv: RELATIVE LINK to Signing up for an account]</li>
            <li>Check out the list of active games. You can evaluate them by when they started, what kind of prizes they
                have to offer, or by who is currently leading.
            </li>
            <li>When you find a game you like, just register for it. Now you're in the game, and it will appear in your
                list of &quot;active games&rdquo;. You can register for a game via the plugin itself in the browser or
                via the Ball&rsquo;s website.
            </li>
            <li>Open up the plugin and log into it if you have not already done so.</li>
            <li>Click on the &ldquo;Games&rdquo; tab to see a listing of available games.&nbsp; Click on the game you
                wish to play by clicking on the hyperlinks for that game in the plugin window.&nbsp; The plugin will
                show you the starting website for each game, and also the latest website that you have found in a
                specific game, if you have already been playing.
            </li>
            <li>To get details about a specific game, including upcoming website, click on the desired game (e.g.
                Blue23) under the &ldquo;Games&rdquo; tab in the plugin window
            </li>
            <li>You may browse to the first website listed for the game as having hosted the ball or you may browse to
                any of the websites listed under &ldquo;Unlocked Websites&rdquo; if you would like to actively pursue
                keys for a specific game.&nbsp; Otherwise you may browse around generally on the internet until you find
                a site that has a key for the game.&nbsp; Note:&nbsp; You should remain logged into your browser at all
                times, if you wish to be notified of any sites that contain keys or the ball, even if you are not
                actively pursuing the ball or a game at this time.
            </li>
            <li>Once you get to a site that has a key or the ball, you will be presented with a notification in the
                plugin window when you go to any page on that site.&nbsp; If the site is hosting a ball or key for more
                than one game, you will be presented with a choice of games.&nbsp; Click on the game you wish to
                follow.
            </li>
            <li>You will be presented with a brainteaser puzzle (word scramble or missing letter puzzle) to first solve
                to get a &ldquo;target hunt object&rdquo; which you must then find on the website somewhere.&nbsp; Once
                you have solved the brainteaser or think you have found the target hunt object on the site, right-click
                and choose &ldquo;Test Target Object&rdquo; from the browser context menu that will appear.&nbsp; Note:
                You must find the correct instance of a target object e.g. if the target object is &ldquo;stone&rdquo;
                and that word appears several times on a website, only one instance is the correct one.
            </li>
            <li>If you are correct, you will be given the next object to find on that site.&nbsp; This time the hunt
                object will not be scrambled &ndash; you will simply have to find the object.&nbsp; We will give you up
                to five (5) target objects to find on that site, before giving you a key for that site or before you
                find the ball if it happens to be on that site at the time.&nbsp; Collect the keys and write them down
                as you may need them when you find the ball.
            </li>
            <li>If you find the ball, you will be asked to enter keys to unlock it.&nbsp; Keys are specific to an
                individual player and you must enter your keys correctly.&nbsp; You will have up to three (3) tries to
                enter the keys correctly.&nbsp; If you are not able to do so successfully, the ball will move on and you
                will have to catch it again, and try to unlock it with the correct keys again.
            </li>
            <li>If you are able to unlock the ball, you will be given a jigsaw, slider or other puzzle to solve within a
                limited time.&nbsp; If you are able to solve this final puzzle in time, you stand a good chance of
                winning the money for that game.&nbsp; Fill out the game winner details and send it to us.&nbsp; We will
                verify your eligibility to win and contact you as soon once we are satisfied with eligibility.
            </li>
            <li>Enjoy your fun-earned cash!</li>
        </ol>
        <p><strong>Tips:&nbsp; </strong></p>
        <ol>
            <li>To get details about a specific game, including upcoming website, click on the desired game (e.g.
                Blue23) under the &ldquo;Games&rdquo; tab in the plugin window
            </li>
            <li>&ldquo;Unlocked Websites&rdquo; shows all the keys that you have found so far in the game (you must
                choose a game first to see its associated details from the &ldquo;Games&rdquo; tab), as well as any
                other websites for which rival players have found keys in that game.&nbsp; Sites are listed in the
                chronological order in which they hosted the ball.&nbsp; If a site appears more than once, it means that
                site has hosted the ball more than once in the game.
            </li>
            <li>&nbsp;&ldquo;Upcoming Domains&rdquo; shows websites that have won the right to host the ball in the
                relatively near future.&nbsp; Note: Upcoming Domains are NOT listed in chronological order
            </li>
            <li>&nbsp;&nbsp;The leaderboard shows a ranking of players based on a combination of factors including
                number of keys found, when keys were found, proximity of keys found to the current site hosting the
                ball, finding of the keys in continguous order and other criteria.<strong> </strong></li>
        </ol>
        <h1><img src="${ctx}/i/title_sponsor.gif" alt="Sponsor a Game" width="170" height="16"/> <span class="cOrange">:</span>
            <img src="${ctx}/i/title_howitworks.gif" alt="How it Works" width="138" height="16"/></h1>
        In their quest to find The Ball, players must review the hosting site's content to look for clues. This is a
        great opportunity to get your content in front of a motivated, Internet-savvy audience - and best of all, it
        doesn't require you to install or change a thing on your Web site.&nbsp; We generate a series of target objects
        to be found on your website and the players flock to your site to find these objects, have a lot of fun along
        the way and possibly win some cash.&nbsp; It&rsquo;s a win-win situation for everyone involved!<br/>
        <br/>
        Sponsoring a game requires that you first create a sponsor account.&nbsp; A game administrator will contact you
        to verify that you are authorized to bid on behalf of the domain, and that your sponsor account details are in
        order, and then you are ready to bid for the right to host the ball!&nbsp; Each ball game is composed of a
        number of blocks, which are sort of like innings in a baseball game - you don't know when exactly they'll
        happen, just that you'll get your turn if you win one of our auction slots.<br/>
        <br/>
        Within each block is a number of slots. In baseball terms, these would correspond to advertising slots within a
        particular inning. When your slot comes up - which is governed by the progress of the game, and a few
        randomizing factors we've built in to eliminate cheating - The Ball will be &quot;hosted&quot; on your Web site,
        and players will swarm all over it. <br/>
        <br/>
        <strong>Note:</strong> You or affiliated members of your company are barred from participate as a player in a
        game in which you are a sponsor.&nbsp; If you have any questions, please do not hesitate to
        <a href="mailto:${orpheus:getContactEmailAddress()}">contact us</a> or check out our Sponsor FAQ section.
        <p>&nbsp;    </p>
    </div>
</div>
<%@ include file="../includes/footer.jsp" %>
</body>
</html>