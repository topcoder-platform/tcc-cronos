<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: Administration</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/styles.css"/>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/tabs.css"/>
</head>

<body id="page">
<div id="container">
    <c:set var="subbar" value="games" scope="page"/>
    <%@ include file="header.jsp" %>

    <div id="wrap">
        <div id="breadcrumb-admin">
            &raquo; Game Management &nbsp; &raquo; Create New Game &nbsp; &raquo; Add Blocks/Slots
            <span class="active"> &nbsp; &raquo; Game Created</span>
        </div>

        <p>
            The game <%--<span class="bold">${game.name}</span>--%> has been created and an auction to fill the blocks
            and timeslots has begun.
        </p>

        <p>
<%--
            <a href="${ctx}/server/admin/gameDetails.do?gameId=${game.id}">View the auction for this game</a><br/>
--%>
            <a href="${ctx}/server/admin/startGameCreation.do">Create Another Game</a><br/>
            <a href="${ctx}/server/admin/viewGames.do">Back to Games</a>
        </p>
    </div>
</div>
<%@ include file="footer.jsp" %>
</body>
</html>