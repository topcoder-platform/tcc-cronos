<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="/paging" prefix="p" %>
<%@ taglib uri="/orpheus" prefix="orpheus" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<fmt:setLocale value="en_US"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: Active Games</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/styles.css"/>
    <script type="text/javascript" src="${ctx}/js/pluginSupport.js"></script>
</head>

<body id="page"
    <c:if test="${param['logged'] eq 'true' and empty param['sort'] and empty param['page']}">
      onload="javascript:notifyOnLogin();"
    </c:if>>
<div id="container">
    <c:set var="subbar" value="active" scope="page"/>
    <%@ include file="header.jsp" %>
    <div id="wrap">
        <h1><img src="${ctx}/i/h/title_activegames.gif" alt="Active Games" height="16"/></h1>

        <p>
            Listed below are all of the current games in action. To find out more about one of the games, click the game
            name.
            To Join a game, just select the row of the game you would like to register for.
        </p>

        <div id="data-table">
            <p:dataPaging pageSize="10" data="${orpheus:convertToList(pendingGames)}" id="pager"
                          requestURL="${ctx}/server/player/activeGames.do">
            <p:page>
            <p:table border="0" cellpadding="0" cellspacing="0"
                     comparator="${orpheus:getPlayerActiveGamesComparator(playerRegisteredGames)}">
                <p:header>
                    <p:column width="15%" index="1" name="Start Date"/>
                    <p:column width="15%" index="2" name="Start Time"/>
                    <p:column width="20%" index="3" name="Game"/>
                    <p:column width="20%" index="4" name="Start URL"/>
                    <p:column width="15%" index="5" name="Minimum Payout"/>
                    <p:column width="15%" index="6" name="User Status"/>
                </p:header>
                <p:rowData id="item" rowId="row" rowType="com.orpheus.game.persistence.Game" renderTR="false">
                    <c:if test="${orpheus:isGameRunning(row)}">
                    <tr style="cursor: pointer;" <c:if test="${item.rowNumberOnPage mod 2 eq 1}">class="alt"</c:if>
                        <c:set var="playerStatus" value="${orpheus:getPlayerStatus(row, playerRegisteredGames)}"/>
                        <c:if test="${playerStatus eq 'Player'}">
                            onclick="javascript:window.location='${ctx}/server/player/unlockedDomains.do?tab=1&gameId=${row.id}'"
                        </c:if>
                        <c:if test="${playerStatus ne 'Player'}">
                            onclick="javascript:window.location='${ctx}/server/player/startGameJoin.do?gameId=${row.id}'"
                        </c:if>
                        onmouseover="this.style.backgroundColor='#FEF1C4';"
                        onmouseout="this.style.backgroundColor='${item.rowNumberOnPage mod 2 eq 0 ? '#ffffff' : '#f4f4f4'}';">
                        <td><fmt:formatDate value="${row.startDate}" pattern="MM/dd/yyyy"/></td>
                        <td><fmt:formatDate value="${row.startDate}" pattern="hh:mm aa"/></td>
                        <td>
                            <a href="${ctx}/server/player/unlockedDomains.do?tab=1&gameId=${row.id}">${row.name}</a>
                        </td>
                        <td>${orpheus:getStartingUrl(row)}&nbsp;</td>
                        <td class="right">
                            $<fmt:formatNumber value="${orpheus:getMinimumPayout(row)}" pattern="#,##0.00"/>
                        </td>
                        <td>${playerStatus}</td>
                    </tr>
                    </c:if>
                </p:rowData>
            </p:table>
        </div>

        <div class="pagination">
            <table border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <td>Showing Games ${orpheus:getDataPagingFooterMessage(pager)}</td>
                    <td>
                        <ul>Page:
                                <%--
                                                    <li><a href="#" class="active">1</a></li>
                                --%>
                            <p:prevLink>&laquo;</p:prevLink>
                            <p:jumpLinks maxCount="5" prefix="<li>" suffix="</li>"/>
                            <p:nextLink>&raquo;</p:nextLink>
                        </ul>
                    </td>
                </tr>
            </table>
        </div>
        </p:page>
        </p:dataPaging>

    </div>
</div>
<%@ include file="/includes/footer.jsp" %>
</body>
</html>