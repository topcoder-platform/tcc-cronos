<%@ page errorPage="error.jsp" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="/orpheus" prefix="orpheus" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="servletContext" value="${pageContext.request.session.servletContext}"/>
<c:set var="user" value="${sessionScope['user_profile']}"/>
<fmt:setLocale value="en_US"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/styles.css"/>
    <script type="text/javascript" src="${ctx}/js/forms.js"></script>
    <script type="text/javascript" src="${ctx}/js/download.js"></script>
    <link rel="Shortcut Icon" type="image/x-png" href="../i/favicon.png" />
</head>

<body id="page">
<div id="container">
    <!-- Header      -->
    <div id="header"><span class="links">
        <c:if test="${user eq null}">
            <a href="${ctx}/public/login.jsp" title="Login">Login</a>
        </c:if>
        <c:if test="${user ne null}">
            Hello, ${orpheus:getHandle(user)}&nbsp;&nbsp;|&nbsp;&nbsp;
            <a href="${ctx}/server/logout.do" title="Logout">Logout</a>&nbsp;&nbsp;|&nbsp;&nbsp;
            <a href="${ctx}/server/welcome.do" title="Home">Home</a>
        </c:if>
        <c:if test="${user eq null}">
            &nbsp;&nbsp;|&nbsp;&nbsp;
            <a href="${ctx}/public/registration.jsp?phase=start&nextPhase=step1" title="Sign Up">Sign Up</a>
        </c:if>
        &nbsp;&nbsp;|&nbsp;&nbsp;<a href="${ctx}/public/sponsor.jsp" title="Sponsors">Sponsors</a>
        &nbsp;&nbsp;|&nbsp;&nbsp;<a href="${ctx}/public/credits.jsp" title="Credits">Credits</a>
        &nbsp;&nbsp;|&nbsp;&nbsp;<a href="${ctx}/public/help_howtoplay.jsp" title="Help">Help</a></span>
        <a href="${ctx}/server/activeGames.do" title="Home">
            <img src="${ctx}/i/logo.gif" alt="The Ball" width="130" height="65"/></a>
    </div>
    <!-- Flash Piece -->
    <div id="home-flash">
        <object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
                codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0"
                width="720" height="173">
            <param name="movie" value="${ctx}/swf/home_flash.swf"/>
            <param name="quality" value="high"/>
            <embed src="${ctx}/swf/home_flash.swf" quality="high"
                   pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash"
                   width="720" height="173">
            </embed>
        </object>
    </div>
    <!-- Action Bar  -->
    <div id="action"><a href="#" onclick="showDownload('${ctx}');return false;"
                        title="Download"><img src="${ctx}/i/home/action1.gif" alt="Download" width="174" height="66"/></a><a
        href="${ctx}/public/registration.jsp?phase=start&nextPhase=step1" title="Sign Up"><img
        src="${ctx}/i/home/action2.gif" alt="Sign Up" width="205" height="66"/></a><a
        href="${ctx}/public/login.jsp" title="Play"><img src="${ctx}/i/home/action3.gif" alt="Play" width="194"
                                                         height="66"/></a><img src="${ctx}/i/home/action4.gif"
                                                                               alt="WIN!"
                                                                               width="149" height="66"/>
    </div>
    <!-- Homepage Content -->
    <div id="home-wrap">
        <!-- Login -->
        <div id="login"><br/>
            <div class="login-table">
                <table border="0" cellpadding="0" cellspacing="0">
                    <c:if test="${user eq null}">
                        <form action="${ctx}/server/login.do" method="POST" name="LoginForm" id="LoginForm">
                            <tr>
                                <td>Username<br/>
                                    <input class="input" type="text" size="10" name="username"
                                           onkeypress="return submitOnEnter(event, this.form);"/><br/>
                                    Password<br/>
                                    <input class="input" type="password" size="10" name="password"
                                           onkeypress="return submitOnEnter(event, this.form);"/></td>
                            </tr>
                        </form>
                        <tr>
                            <td align="right">
                                <a title="Login" href="#" onclick="submitForm(document.LoginForm);return false;">
                                    <img src="${ctx}/i/b/btn_login_on.gif" alt="Login" width="45" height="18"/>
                                </a>&nbsp;
                                <a href="#" onclick="document.LoginForm.reset();return false;" title="Clear">
                                    <img src="${ctx}/i/b/clear.gif" alt="Clear" width="45" height="18"/>
                                </a>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <a title="Forget Your Password?" href="${ctx}/public/retrieve-password.jsp">
                                    Forgot Your Password?
                                </a>
                            </td>
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                        </tr>
                    </c:if>
                    <tr>
                        <td class="beta">
                            <img src="${ctx}/i/home/img_beta_notice.jpg" width="147" height="26" alt="Beta notice"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <span class="bold">Attention participants!</span><br/>
                            These initial contests are part of our beta testing program and you may experience
                            occasional disruptions in service. If a disruption or problem occurs please contact us by
                            <a href="mailto:${orpheus:getContactEmailAddress()}">email</a>.

                            We would like to thank our TopCoder member community for helping to identify bugs or other
                            game issues in our special advance preview.
                            <a href="${ctx}/public/bounty.jsp">Click here</a> to see a list of qualifiers for our
                            special member Bounty pool contest.
                        </td>
                    </tr>
                    <tr>
                        <td>&nbsp;</td>
                    </tr>
                </table>
            </div>
        </div>
        <!-- End Login -->

        <!-- Center Content -->
        <div id="home-content">
            <h1><img src="${ctx}/i/h/header1.gif" alt="What is This?" width="161" height="18"/></h1>

            <p><span class="play"><a href="${ctx}/public/howtoplay.jsp"><img src="${ctx}/i/howtoplay.bmp"
                                                                             alt="Learn How To Play" width="151"
                                                   height="151"/></a></span>The
                Ball travels from site to site, across the Web. It's like an
                online treasure hunt - but unlike an "X" on a treasure map, The
                Ball doesn't sit still!<br/>
                <br/>
                As you pursue it across the Internet, you'll be presented with puzzles and challenges. Solve them
                correctly, and you'll get clues to help you find The Ball - and the keys you'll need to unlock it when
                you do. Once you've unlocked The Ball, and solved the final puzzle, you may win one of our cash prizes -
                <a href="${ctx}/public/prizes.jsp">click here</a> for details!
                <br/>
                <br/>
                <img src="${ctx}/i/h/sub_games.gif" alt="Games In Progress" width="88" height="16"/>
            </p>
            <table border="0" cellpadding="0" cellspacing="0" class="game-table">
                <tr>
                    <th>Start Date</th>
                    <th>Game</th>
                    <th>Start URL</th>
                    <th class="right">Min.Payout</th>
                </tr>
                <c:forEach items="${pendingGames}" var="game">
                    <c:if test="${orpheus:isGameRunning(game)}">
                        <tr>
                            <td><fmt:formatDate value="${game.startDate}" pattern="MM/dd/yyyy"/></td>
                            <td>${game.name}</td>
                            <td>
                                <a href="${orpheus:buildDomainUrl(orpheus:getStartingUrl(game))}" target="_blank">
                                    ${orpheus:getStartingUrl(game)}
                                </a>
                            </td>
                            <td align="right">
                                $<fmt:formatNumber value="${orpheus:getMinimumPayout(game, servletContext)}" pattern="#,##0.00"/>
                            </td>
                        </tr>
                    </c:if>
                </c:forEach>
            </table>
        </div>
    </div>
</div>
<%@ include file="../includes/footer.jsp" %>
</body>
</html>
