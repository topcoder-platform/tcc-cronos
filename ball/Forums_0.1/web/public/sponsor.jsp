<%@ page errorPage="error.jsp" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: Sponsor A Game :: Overview</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/styles.css"/>
</head>

<body id="page">
<div id="container">
    <c:set var="subbar" value="sponsor" scope="page"/>
    <%@ include file="../includes/header.jsp" %>
    <div id="wrap">
        <h1><img src="${ctx}/i/h/title_sponsoragame.gif" alt="Sponsor a Game"/></h1>

        <p>
            Want to attract new visitors to your Web site? Want an incentive to keep them there? By sponsoring The Ball,
            your site will play "host" to it - and all the players who are looking for it. 
            <br/>
            <br/>
            In their quest to find The Ball, players must review the host site's content to look for clues. This is a
            great opportunity to get your content in front of a motivated, Internet-savvy audience - and best of all, it
            doesn't require you to install or change a thing on your Web site. We generate a series of target objects to
            be found on your website and the players flock to your site to find these objects, have a lot of fun along
            the way and possibly win some cash. It's a win-win situation for everyone involved!
            <br/>
            <br/>
        </p>

        <div class="buttonsC">
            <a href="${ctx}/public/sponsorRegistration.jsp?phase=start&nextPhase=step1" title="Get in the Game">
                <img src="${ctx}/i/b/btn_getinthegame.gif" width="96" height="15" alt="Get In The Game"/>
            </a>
        </div>
    </div>
</div>
<%@ include file="../includes/footer.jsp" %>
</body>
</html>