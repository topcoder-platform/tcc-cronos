<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="/paging" prefix="p" %>
<%@ taglib uri="/orpheus" prefix="orpheus" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="leaderProfiles" value="${orpheus:joinProfiles(winnerProfiles, leaders)}" scope="page"/>
<fmt:setLocale value="en_US"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: My Games</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/styles.css"/>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/tabs.css"/>
</head>

<body id="page">
<div id="container">
<c:set var="subbar" value="mygames" scope="page"/>
<%@ include file="header.jsp" %>
<div id="wrap">
<h1><img src="${ctx}/i/h/title_gamedetails.gif" alt="Game Details" width="135" height="16"/></h1><br/>

<div id="table-details">
    <table border="0" cellpadding="0" cellspacing="0" width="313">
        <tr>
            <th colspan="2">Game Details: ${game.name}</th>
        </tr>
        <tr>
            <td class="bold" width="55%">Start Date:</td>
            <td width="45%">
                <fmt:formatDate value="${game.startDate}" pattern="MM/dd/yyyy hh:mm aa"/>
            </td>
        </tr>
        <tr>
            <td class="bold">Status:</td>
            <td>${orpheus:getGameStatus(game)}</td>
        </tr>
        <tr>
            <td class="bold">Minimum Payout:</td>
            <td>
                $<fmt:formatNumber value="${orpheus:getMinimumPayout(game)}" pattern="#,##0.00"/>
            </td>
        </tr>
        <tr>
            <td class="bold">My Standing:</td>
            <td>
<%--
                <a href="${ctx}/server/player/showPesonalizedBall.do?gameId=${game.id}">
--%>
                    ${orpheus:getPlayerRankFromList(leaderProfiles, user_profile)}
<%--
                </a>
--%>
            </td>
        </tr>
    </table>
</div>

<div id="tabcontentcontainer">

<!-- UNLOCKED DOMAINS TAB -->
<c:if test="${param['tab'] eq '1'}">
    <div id="sc1" class="tabcontent" style="display:block;">
        <ul id="tablist">
            <li id="current"><a href="${ctx}/server/player/unlockedDomains.do?gameId=${game.id}&tab=1">Unlocked
                Domains</a></li>
            <li><a href="${ctx}/server/player/upcomingDomains.do?gameId=${game.id}&tab=2">Upcoming Domains</a></li>
            <li><a href="${ctx}/server/player/leaders.do?gameId=${game.id}&tab=3">Leaderboard</a></li>
        </ul>

        <div style="clear:both;"></div>

        <div id="table-tabs">
            <p:dataPaging pageSize="10" data="${orpheus:convertToList(orpheus:reverseDomains(unlockedDomains))}" id="pager"
                          requestURL="${ctx}/server/player/unlockedDomains.do?tab=1">
                <p:table border="0" cellpadding="0" cellspacing="0" width="100%">
                    <tr>
                        <th>Domain</th>
                    </tr>
                    <p:rowData oddRowStyle="alt2" evenRowStyle="alt" id="item" rowId="domain"
                               rowType="com.orpheus.game.persistence.Domain">
                        <td>${domain.domainName}</td>
                    </p:rowData>
                    <tr>
                        <td class="pagination">
                            <table border="0" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td>Showing Domains ${orpheus:getDataPagingFooterMessage(pager)}</td>
                                    <td>
                                        <ul>Page:
                                            <p:prevLink>&laquo;</p:prevLink>
                                            <p:jumpLinks maxCount="5" prefix="<li>" suffix="</li>"/>
                                            <p:nextLink>&raquo;</p:nextLink>
                                        </ul>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </p:table>
            </p:dataPaging>
        </div>
    </div>
</c:if>

<!-- UPCOMING DOMAINS TAB -->
<c:if test="${param['tab'] eq '2'}">
    <div id="sc1" class="tabcontent" style="display:block;">
        <ul id="tablist">
            <li><a href="${ctx}/server/player/unlockedDomains.do?gameId=${game.id}&tab=1">Unlocked Domains</a></li>
            <li id="current"><a href="${ctx}/server/player/upcomingDomains.do?gameId=${game.id}&tab=2">Upcoming
                Domains</a></li>
            <li><a href="${ctx}/server/player/leaders.do?gameId=${game.id}&tab=3">Leaderboard</a></li>
        </ul>

        <div style="clear:both;"></div>

        <div id="table-tabs">
            <p:dataPaging pageSize="10" data="${orpheus:convertToList(upcomingDomains)}" id="pager"
                          requestURL="${ctx}/server/player/upcomingDomains.do?tab=2">
                <p:table border="0" cellpadding="0" cellspacing="0" width="100%">
                    <tr>
                        <th>Upcoming Domains for The Super Ball (listed in random order)</th>
                    </tr>
                    <p:rowData oddRowStyle="alt2" evenRowStyle="alt" id="item" rowId="domain"
                               rowType="com.orpheus.game.persistence.Domain">
                        <td>${domain.domainName}</td>
                    </p:rowData>
                    <tr>
                        <td class="pagination">
                            <table border="0" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td>Showing Domains ${orpheus:getDataPagingFooterMessage(pager)}</td>
                                    <td>
                                        <ul>Page:
                                            <p:prevLink>&laquo;</p:prevLink>
                                            <p:jumpLinks maxCount="5" prefix="<li>" suffix="</li>"/>
                                            <p:nextLink>&raquo;</p:nextLink>
                                        </ul>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </p:table>
            </p:dataPaging>
        </div>
    </div>
</c:if>

<!-- LEADERBOARD TAB -->
<c:if test="${param['tab'] eq '3'}">
    <div id="sc1" class="tabcontent" style="display:block;">
        <ul id="tablist">
            <li><a href="${ctx}/server/player/unlockedDomains.do?gameId=${game.id}&tab=1">Unlocked Domains</a></li>
            <li><a href="${ctx}/server/player/upcomingDomains.do?gameId=${game.id}&tab=2">Upcoming Domains</a></li>
            <li id="current"><a href="${ctx}/server/player/leaders.do?gameId=${game.id}&tab=3">Leaderboard</a></li>
        </ul>

        <div style="clear:both;"></div>

        <div id="table-tabs">
            <p:dataPaging pageSize="10" data="${leaderProfiles}" id="pager"
                          requestURL="${ctx}/server/player/leaders.do?tab=3">
                <p:table border="0" cellpadding="0" cellspacing="0" width="100%">
                    <%-- It does not make sense to sort the leaders list as ranking currently evaluated
                         as a position of an item in the list --%>
                    <tr>
                        <th>Player</th>
                        <th>Ranking</th>
                    </tr>
                    <p:rowData oddRowStyle="alt2" evenRowStyle="alt" id="item" rowId="leader"
                               rowType="com.topcoder.user.profile.UserProfile">
                        <td>${orpheus:getHandle(leader)}<c:if test="${orpheus:isInList(leader, winnerProfiles)}"> *</c:if></td>
                        <td>${item.rowNumberFromStart + 1}</td>
                    </p:rowData>
                    <tr>
                        <td class="pagination" colspan="2">
                            <table border="0" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td>Showing Players ${orpheus:getDataPagingFooterMessage(pager)}</td>
                                    <td>
                                        <ul>Page:
                                            <p:prevLink>&laquo;</p:prevLink>
                                            <p:jumpLinks maxCount="5" prefix="<li>" suffix="</li>"/>
                                            <p:nextLink>&raquo;</p:nextLink>
                                        </ul>
                                    </td>
                                </tr>
                                <c:if test="${not empty winnerProfiles}">
                                    <tr>
                                        <td colspan="2">* - player has completed the game and has to be approved by Administrator</td>
                                    </tr>
                                </c:if>
                            </table>
                        </td>
                    </tr>
                </p:table>
            </p:dataPaging>
        </div>
    </div>
</c:if>

</div>
</div>


</div>
<%@ include file="/includes/footer.jsp" %>
</body>
</html>