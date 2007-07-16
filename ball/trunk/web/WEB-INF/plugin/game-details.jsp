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
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/plugin.css"/>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/tabs.css"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Cache-Control" content="no-cache"/>
    <meta http-equiv="Expires" content="Wed, 09 Aug 2000 08:21:57 GMT"/>
    <script type="text/javascript" xml:space="preserve" src="${ctx}/js/pluginSupport.js">
    </script>
</head>

<body id="pagePlugin">
<div id="containerPlugin">
<div id="mastHead">
    <img src="${ctx}/i/plugin_stripes.jpg" width="668" height="62" alt=""/>
</div>

<div id="pluginTitle">GAME<span class="main"> DETAILS</span></div>

<div id="wrapPlugin">

<div id="table-details">
    <table border="0" cellpadding="0" cellspacing="0">
        <tr>
            <th colspan="2">Game Details: ${game.name}</th>
        </tr>
        <tr>
            <td class="bold" width="40%">Start Date:</td>
            <td width="60%">
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
                <a href="${ctx}/server/plugin/showPesonalizedBall.do?gameId=${game.id}">
                    ${orpheus:getPlayerRankFromList(leaderProfiles, user_profile)}
                </a>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <c:if test="${param['tab'] eq '1'}">
                    "Unlocked Domains" shows all of the domains in a particular game, on which player have found keys.
                    This list includes domains where an individual player has found a key as well as any domains on
                    which other players have found keys. Note: sites are listed in ball hosting order.
                </c:if>
                <c:if test="${param['tab'] eq '2'}">
                    "Upcoming Domains" show sites that have either hosted the ball recently or MAY be hosting the ball
                    in the near future. Note that not every site on Upcoming Domains necessarily has a key or the ball
                    at a given moment - though they have a very high probability of doing so. Upcoming domains are not
                    listed in ball-hosting order.
                </c:if>
                <c:if test="${param['tab'] eq '3'}">
                    The leaderboard shows players who have keys that are required to unlock the ball based on its
                    current position. Players with keys that are not useful in unlocking the ball at its current
                    position will not be shown.
                </c:if>
            </td>
        </tr>
    </table>
</div>

<div id="tabcontentcontainer">

<!-- UNLOCKED DOMAINS TAB -->
<c:if test="${param['tab'] eq '1'}">
    <div id="sc1" class="tabcontent" style="display:block;">
        <ul id="tablist">
            <li id="current"><a href="${ctx}/server/plugin/gameUnlockedDomains.do?gameId=${game.id}&tab=1"
                                onclick="return goto(this.href);">Unlocked Domains</a></li>
            <li><a href="${ctx}/server/plugin/gameUpcomingDomains.do?gameId=${game.id}&tab=2"
                   onclick="return goto(this.href);">Upcoming Domains</a></li>
            <li><a href="${ctx}/server/plugin/gameLeaders.do?gameId=${game.id}&tab=3"
                   onclick="return goto(this.href);">Leaderboard</a></li>
        </ul>

        <div style="clear:both;"></div>

        <div id="table-tabs">
            <p:dataPaging pageSize="10" data="${orpheus:convertToList(orpheus:reverseDomains(unlockedDomains))}" id="pager"
                          requestURL="${ctx}/server/plugin/gameUnlockedDomains.do?tab=1">
                <p:table border="0" cellpadding="0" cellspacing="0" width="100%">
                    <tr>
                        <th>Domain</th>
                    </tr>
                    <p:rowData oddRowStyle="alt2" evenRowStyle="alt" id="item" rowId="domain"
                               rowType="com.orpheus.game.persistence.Domain">
                        <td>
                            <a href="${orpheus:buildDomainUrl(domain.domainName)}"
                               onclick="openInParentWindow(this);return false;">
                                    ${domain.domainName}
                            </a>
                        </td>
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
            <li><a href="${ctx}/server/plugin/gameUnlockedDomains.do?gameId=${game.id}&tab=1"
                   onclick="return goto(this.href);">Unlocked Domains</a></li>
            <li id="current"><a href="${ctx}/server/plugin/gameUpcomingDomains.do?gameId=${game.id}&tab=2"
                                onclick="return goto(this.href);">Upcoming
                Domains</a></li>
            <li><a href="${ctx}/server/plugin/gameLeaders.do?gameId=${game.id}&tab=3"
                   onclick="return goto(this.href);">Leaderboard</a></li>
        </ul>

        <div style="clear:both;"></div>

        <div id="table-tabs">
            <p:dataPaging pageSize="10" data="${orpheus:convertToList(upcomingDomains)}" id="pager"
                          requestURL="${ctx}/server/plugin/gameUpcomingDomains.do?tab=2">
                <p:table border="0" cellpadding="0" cellspacing="0" width="100%">
                    <tr>
                        <th>Upcoming Domains for The Super Ball (listed in random order)</th>
                    </tr>
                    <p:rowData oddRowStyle="alt2" evenRowStyle="alt" id="item" rowId="domain"
                               rowType="com.orpheus.game.persistence.Domain">
                        <td>
                            <a href="${orpheus:buildDomainUrl(domain.domainName)}"
                               onclick="openInParentWindow(this);return false;">
                                    ${domain.domainName}
                            </a>
                        </td>
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
            <li><a href="${ctx}/server/plugin/gameUnlockedDomains.do?gameId=${game.id}&tab=1"
                   onclick="return goto(this.href);">Unlocked Domains</a></li>
            <li><a href="${ctx}/server/plugin/gameUpcomingDomains.do?gameId=${game.id}&tab=2"
                   onclick="return goto(this.href);">Upcoming Domains</a></li>
            <li id="current"><a href="${ctx}/server/plugin/gameLeaders.do?gameId=${game.id}&tab=3"
                                onclick="return goto(this.href);">Leaderboard</a></li>
        </ul>

        <div style="clear:both;"></div>

        <div id="table-tabs">
            <p:dataPaging pageSize="10" data="${leaderProfiles}" id="pager"
                          requestURL="${ctx}/server/plugin/gameLeaders.do?tab=3">
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
<script type="text/javascript" xml:space="preserve">
    <!--
    window.focus();
    -->
</script>
</body>
</html>