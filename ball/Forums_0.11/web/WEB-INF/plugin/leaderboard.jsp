<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="/paging" prefix="p" %>
<%@ taglib uri="/orpheus" prefix="orpheus" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: Leader Board</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/plugin.css"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Cache-Control" content="no-cache"/>
    <meta http-equiv="Expires" content="Wed, 09 Aug 2000 08:21:57 GMT"/>
</head>

<body id="pagePlugin">
<div id="containerPlugin">
    <div id="mastHead"><img src="${ctx}/i/plugin_stripes.jpg" width="668" height="62" alt=""/></div>

    <div id="pluginTitle">LEADER <span class="main">BOARD FOR ${game.name} </span></div>

    <div id="wrapPlugin">
        <p>The LeaderBoard shows those players who have keys that are needed to unlock the ball at its current location.
            Note that players may have keys for sites in the game and not appear on the LeaderBoard since those keys are
            not useful in unlocking the ball at its current position.</p>
        <div class="tabletop"></div>

        <p:dataPaging pageSize="10" data="${orpheus:joinProfiles(winnerProfiles, leaders)}" id="pager"
                      requestURL="${ctx}/server/plugin/leaders.do">
            <p:page>
                <div id="plugin-table">
                    <ul>
                        <li class="auction-block">
                            <p:table border="0" cellpadding="0" cellspacing="0">
                                <%-- It does not make sense to sort the leaders list as ranking currently evaluated
                                 as a position of an item in the list --%>
                                <tr class="altHeader">
                                    <th width="245"><a href="#" title="sort">Player</a></th>
                                    <th width="191"><a href="#" title="sort">Ranking</a></th>
                                </tr>
                                <p:rowData oddRowStyle="alt" id="item" rowId="leader"
                                           rowType="com.topcoder.user.profile.UserProfile">
                                    <td>${orpheus:getHandle(leader)}<c:if test="${orpheus:isInList(leader, winnerProfiles)}"> *</c:if></td>
                                    <td class="alt">${item.rowNumberFromStart + 1}</td>
                                </p:rowData>
                            </p:table>
                        </li>
                    </ul>
                </div>

                <div class="pagination">
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
                </div>
            </p:page>
        </p:dataPaging>

        <div class="tablebot"></div>
    </div>
</div>
<script type="text/javascript" xml:space="preserve">
    <!--
    window.focus();
    -->
</script>
</body>
</html>