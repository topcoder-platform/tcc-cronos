<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: Confirmation</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/plugin.css"/>
    <script type="text/javascript" src="${ctx}/js/pluginSupport.js"></script>
</head>

<body id="pagePlugin" onload="setCurrentGame(${game.id});">
<div id="containerPlugin">
    <div id="mastHead"><img src="${ctx}/i/plugin_stripes.jpg" width="668" height="62" alt=""/></div>

    <div id="pluginTitle">CONFIRMATION</div>

    <div id="wrapPlugin">
        <br>
        You have successfully joined the game <span class="cOrange">${game.name}</span> and can begin hunting for the
        ball!
        <p>You will receive a confirmation email shortly with the details of your
        game. In the meantime, you can view game details and begin playing by
        viewing the <a href="${ctx}/server/plugin/myGames.do">game details</a> screen.</p>

        <p>&nbsp;</p>
    </div>
</div>
<script type="text/javascript" xml:space="preserve">
    <!--
    window.focus();
    -->
</script>
</body>
</html>