<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="/orpheus" prefix="orpheus" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: Game Registration :: Confirmation</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/styles.css"/>
    <script type="text/javascript" src="${ctx}/js/pluginSupport.js"></script>
</head>

<body id="page" onload="setCurrentGame(${game.id});">
<div id="container">
    <c:set var="subbar" value="mygames" scope="page"/>
    <%@ include file="header.jsp" %>
    <div id="wrap">
        <h1><img src="${ctx}/i/h/title_confirmation.gif" alt="Confirmation"/></h1>

        <p>You have successfully joined the game <span class="cOrange">${game.name}</span> and can begin hunting
            for the ball!</p>

        <p>You will receive a confirmation email shortly with the details of your game. In the meantime, you can view
            game
            details and begin playing by viewing the game details screen. Or view a list of all of the games your
            registered
            for by clicking the <a href="${ctx}/server/player/myGames.do" title="My Games">My Games</a> link above.</p>

        <p>&nbsp;</p>
    </div>
</div>
<%@ include file="/includes/footer.jsp" %>
</body>
</html>