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
        <p><img src="${ctx}/i/help/img_playing.gif" alt="How to Play" width="220" height="18"/></p>
        <ol>
            <li>Download the plugin if you have not already done so.</li>
            <li>Sign-up for an account if you have not already done so, otherwise log into the plugin.</li>
            <li>If you have logged in via the website, browse around the internet as you normally would or actively
                follow-the ball by using the &ldquo;Upcoming Domains&rdquo; feature of looking at the starting URL
                presented on the Games Tab.&nbsp; If you get to a site that contains a key or the ball for an Active
                Game, a new window will pop-up and present the game(s) that are relevant for that site.
            </li>
            <li>If you have logged in via the plugin, browse around the internet as you normally would or actively
                follow-the ball by using the &ldquo;Upcoming Domains&rdquo; feature of looking at the starting URL
                presented on the Games Tab.&nbsp; If you get to a site that contains a key or the ball for an Active
                Game, the plugin pop-up window where you logged in will present the game(s) that are relevant for that
                site.
            </li>
        </ol>
        <p align="center"><img src="${ctx}/i/help/playing_01.jpg" width="350" height="270" alt=""/></p>

        <p align="left">
        </p>
        <ol start="5">
            <li> Choose a game by clicking on any of the games listed. If you have not previously been registered for
                that game, you will be prompted to do so now.&nbsp; If you are already registered you will be presented
                with a missing letter puzzle or a word scramble.&nbsp; If the puzzle clue contains &ldquo;#&rdquo;
                characters then it is a missing letter puzzle and you will need to guess at the missing letters.&nbsp;
                If the puzzle does not contain at least one &ldquo;#&rdquo; character, then it is a word scramble and
                you will need to unscramble to decipher the first target.&nbsp; Note: we do not tell you whether you can
                correctly filled in the missing letters or unscrambled the clue, however if you wait long enough, we
                will keep updating the clue to make it easier and easier until finally we give the correct answer.
            </li>
        </ol>
        <p class="cOrange"><strong>Example 1: Plugin puzzle shows as Misisng Letter puzzle (&ldquo;#&rdquo; denotes a
            missing character &ndash; letter, punctuation, etc)</strong></p>

        <p align="center"><img src="${ctx}/i/help/playing_02.jpg" width="350" height="270" alt=""/></p>

        <p align="left">&nbsp;</p>

        <p class="cOrange"><strong>Example 2: Plugin Puzzle shows as Word Scramble</strong></p>

        <p align="center" class="cOrange"><img src="${ctx}/i/help/playing_03.jpg" width="350" height="270" alt=""/></p>
        <ol start="6">
            <li>Once you have the target clue solved, you must find the correct instance of that clue on the website.&nbsp;
                Note: there may be more than one instance of the same clue on a website, but not all of them are
                correct.
            </li>
            <li>When you find a target on a site that you think is correct, right-click and choose : &ldquo;The Ball-
                Test Object&rdquo;&nbsp; off the context menu.
            </li>
        </ol>
        <p class="cOrange"><strong>Example 1: Testing the Context Menu for The Ball in Firefox</strong></p>

        <p align="center" class="cOrange"><img src="${ctx}/i/help/playing_04.jpg" width="350" height="270" alt=""/></p>

        <p class="cOrange"><strong>Example 2: Testing the Context Menu for The Ball in Internet Explorer</strong></p>

        <p align="center" class="cOrange"><img src="${ctx}/i/help/playing_05.jpg" width="350" height="270" alt=""/></p>
        <ol start="8">
            <li>If the target is invalid or if it is the seemingly correct target, but on the wrong page of the website,
                the plugin will provide a notification that the target is not the correct one:
            </li>
        </ol>
        <p class="cOrange"><strong>Example 1: Testing the wrong target in Firefox</strong></p>

        <p align="center" class="cOrange"><img src="${ctx}/i/help/playing_06.jpg" width="350" height="270" alt=""/></p>

        <p class="cOrange"><strong>Example 2: Testing the wrong target in Internet Explorer</strong></p>

        <p align="center" class="cOrange"><img src="${ctx}/i/help/playing_07.jpg" width="350" height="270" alt=""/></p>
        <ol start="9">
            <li>If it is the correct target, you will be given the next target clue to find on the same website.&nbsp;
                Note that this target clue is not scrambled or missing letters.&nbsp; Only the first clue on a host
                website is scrambled or missing letters.&nbsp; </li>
        </ol>
        <p class="cOrange"><strong>Example 1: Finding the correct target and being given the next target in
            Firefox &ndash; clue is NOT scrambled</strong></p>

        <p align="center"><img src="${ctx}/i/help/playing_08.jpg" width="350" height="270" alt=""/></p>

        <p class="cOrange"><strong>Example 2: Finding the correct target and being given the next target in Internet
            Explorer &ndash; clue is NOT scrambled </strong></p>

        <p align="center"><img src="${ctx}/i/help/playing_09.jpg" width="350" height="270" alt=""/></p>
        <ol start="10">
            <li>Find the target on the site and repeat steps 6 and 7 to test whether the target is the correct ball one.
                If it is correct, you will be presented with the next clue on that site.  We can give you up to 5 clues
                on one site to solve before you can move on to the next site.  If you have solved all of the clues on
                one site, you will be presented with a key which is a random series of numbers or letters for that site.
                The key will be displayed in the main plugin window and you can write it down on a piece of paper to
                remember it, but can also be retrieved by you at any time by clicking on the "Unlocked Sites" button on
                the ball's toolbar. In addition to the key information, you will be presented with the URL for the next
                website in the ball game.  You can click on the URL and jump to the website to begin your hunt on that
                site.
            </li>
            <li>Keys are presented when you have found all the clues on a particular sponsor site, and are the game's
                way of checking that you are actively following the ball.  When you find the ball you will be asked to
                enter a X keys (the number "X" differs from game to game) to "unlock" the ball.  These are the keys that
                come from the last X sites that have hosted the ball.  Note that the last X sites that have hosted the
                ball may not be the same as the last X sites on which you have found keys.  For example if you received
                keys for the following sites: www.topcoder.com, www.equitrader.com and www.db.com, but the ball was
                actually hosted in the order of: www.topcoder.com, software.topcoder.com, studio.topcoder.com,
                www.db.com and www.equitrader.com  and you have to enter 3 keys to unlock the balll, you will need to
                use keys from www.equitrader.com, www.db.com and studio.topcoder.com.  If you do not have a key from
                studio.topcoder.com, in this case you will be unsuccessful in unlocking the ball.  To successfully
                unlock the ball, you must get a key from studio.topcoder.com (but remember to hurry, as the ball can
                move on to another sponsor site in the meantime, and so the keys needed to unlock it may change).
            </li>
        </ol>
        <p class="cOrange"><strong>Example 1: Finding Key at end of Mini-hunt on a site in Internet Explorer</strong>
        </p>

        <p align="center"><img src="${ctx}/i/help/playing_10.jpg" width="350" height="270" alt=""/></p>

        <p class="cOrange"><strong>Example 2: Finding Key at end of Mini-hunt on a site in Firefox:</strong></p>

        <p align="center"><img src="${ctx}/i/help/playing_11.jpg" width="350" height="270" alt=""/></p>

        <p class="cOrange"><strong>Hints and Tips:</strong></p>
        <ul>
            <li>If you go off-site during a search for a target within the mini-hunt on a site and return to the site,
                you will be required to find the first clue once again.&nbsp; In some cases this error may be avoided by
                right-clicking in the plugin window and choosing &ldquo;Back&rdquo; until you get back to the latest
                clue on the website.&nbsp; Other-wise you will be required to start the hunt over on that site.
            </li>
            <li>We keep track of any keys that you find and they may be accessed at any time by logging into the plugin,
                choosing the relevant game from the &ldquo;Games&rdquo; menu and then clicking on &ldquo;Unlocked Sites&rdquo;.
            </li>
            <li>If you are unable to solve the Missing Letter Puzzle or Word Scramble, we will eventually provide the
                answer to either type of puzzle.&nbsp; The puzzle is updated every 3-5 minutes on average with the clue
                becoming progressively easier.
            </li>
            <li>&ldquo;Upcoming Domains&rdquo; show sites that have either hosted the ball recently or MAY be hosting
                the ball in the near future.&nbsp; Note that not every site on Upcoming Domains necessarily has a key or
                the ball at a given moment &ndash; though they have a very high probability of doing so.&nbsp; Upcoming
                domains are not necessarily in ball-hosting order.
            </li>
            <li>&ldquo;Unlocked Domains&rdquo; shows all of the domains in a particular game, on which a player has
                found a key.&nbsp; This list includes any domains for which the individual player has found a key but
                also includes any domains on which other players have found keys.&nbsp; Note: Other player keys are not
                accessible to the individual player and sites are listed in ball-hosting order.
            </li>
        </ul>
        <p align="left">&nbsp;</p>
    </div>
</div>
<%@ include file="../includes/footer.jsp" %>
</body>
</html>