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
    <div id="wrap2">
        <p><img src="${ctx}/i/help/img_hintstips.gif" alt="How to Play" width="151" height="20"/></p>
        <ol>
            <ol>
                <li>You must enter keys that are valid for the X sites immediately preceding the ball&rsquo;s current
                    location.&nbsp; You may not have all the required keys or you may have keys that are further away
                    from the ball&rsquo;s current location than &ldquo;X&rdquo; and are therefore not valid to unlock
                    the ball. If you do not have enough of the correct keys, you will need to start hunting again in
                    sites that hosted the ball earlier to find the needed keys.&nbsp; Use the &ldquo;Unlocked Sites&rdquo;
                    feature to get a hint as to where you might find other needed keys, as that screen will show the
                    sites where all players have found keys so far in the game.&nbsp; Alternately you may try to keep
                    following the ball to its next location in an attempt to collect the right keys.
                </li>
                <li>If you go off-site during a search for a target within the mini-hunt on a site and return to the
                    site, you will be required to find the first clue once again.&nbsp; In some cases this error may be
                    avoided by right-clicking in the plugin window and choosing &ldquo;Back&rdquo; until you get back to
                    the latest clue on the website.&nbsp; Other-wise you will be required to start the hunt over on that
                    site.
                </li>
            </ol>
        </ol>
        <p align="center"><img src="${ctx}/i/help/hinttips_01.jpg" width="350" height="270" alt=""/></p>
        <ol>
            <ol start="3">
                <li>We keep track of any keys that you find and they may be accessed at any time by logging into the
                    plugin, choosing the relevant game from the &ldquo;Games&rdquo; menu and then clicking on &ldquo;Unlocked
                    Sites&rdquo;.
                </li>
            </ol>
        </ol>
        <p><strong class="cOrange">Click on the &ldquo;Games&rdquo;</strong> tab to be found in The Ball&rsquo;s Toolbar Menu and choose a specific
            game:</p>

        <p align="center"><img src="${ctx}/i/help/hinttips_02.jpg" width="350" height="270" alt=""/></p>

        <p><strong class="cOrange">Click on &ldquo;Unlocked Sites&rdquo;</strong> and you will see any sites for which
            you have keys.&nbsp; Sites listed
            but with no keys associated with them are sites for which other players have found keys in that game.</p>

        <p align="center"><img src="${ctx}/i/help/hinttips_03.jpg" width="350" height="270" alt=""/></p>
        <ol>
            <ol start="4">
                <li>&ldquo;Unlocked Domains&rdquo; shows all of the domains in a particular game, on which a player has
                    found a key.&nbsp; This list includes any domains for which the individual player has found a key
                    but also includes any domains on which other players have found keys.&nbsp; Note: Other player keys
                    are not accessible to the individual player and sites are listed in ball-hosting order.
                </li>
            </ol>
        </ol>
        <p class="cOrange"><strong>Unlocked Domains View on Website:</strong></p>

        <p align="center"><img src="${ctx}/i/help/hinttips_04.jpg" width="350" height="270" alt=""/></p>

        <p class="cOrange"><strong>Unlocked Domains View on Website:</strong></p>

        <p align="center"><img src="${ctx}/i/help/hinttips_05.jpg" width="350" height="270" alt=""/></p>
        <ol>
            <ol start="5">
                <li>&ldquo;Upcoming Domains&rdquo; show sites that have either hosted the ball recently or MAY be
                    hosting the ball in the near future.&nbsp; Note that not every site on Upcoming Domains necessarily
                    has a key or the ball at a given moment &ndash; though they have a very high probability of doing so.&nbsp;
                    Upcoming domains are not necessarily in ball-hosting order.
                </li>
            </ol>
        </ol>
        <p class="cOrange"><strong>Upcoming Domains View in Plugin:</strong></p>

        <p align="center"><img src="${ctx}/i/help/hinttips_06.jpg" width="350" height="270" alt=""/></p>

        <p class="cOrange"><strong>Upcoming Domains View on the Website:</strong></p>

        <p align="center"><img src="${ctx}/i/help/hinttips_07.jpg" width="350" height="270" alt=""/></p>
        <ol start="6">
            <li> If you are unable to solve the Missing Letter Puzzle or Word Scramble, we will eventually provide the
                answer to either type of puzzle.&nbsp; The puzzle is updated every 3-5 minutes on average with the clue
                becoming progressively easier.
            </li>
        </ol>
        <p align="center"><img src="${ctx}/i/help/hinttips_08.jpg" width="350" height="270" alt=""/></p>

        <p align="left">&nbsp;</p>
    </div>
</div>
<%@ include file="../includes/footer.jsp" %>
</body>
</html>


