<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="/orpheus_admin" prefix="admin" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="request" value="${pageContext.request}" scope="page"/>
<c:set var="dateFormat" value="MM/dd/yyyy HH:mm:ss" scope="page"/>
<c:set var="currentTime" value="<%=new Date()%>" scope="page"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: Create New Game</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/styles.css"/>
    <script type="text/javascript">
        var BLOCK_COUNT = ${blockCount};
        var BLOCK_DURATIONS = new Array();
        var BLOCK_SLOT_COUNTS = new Array();
        var AUCTION_START_TIMES = new Array();
        var AUCTION_END_TIMES = new Array();
        for (var i = 0; i < ${blockCount}; i++) {
            BLOCK_DURATIONS[i] = 0;
            BLOCK_SLOT_COUNTS[i] = 0;
            AUCTION_START_TIMES[i] = 0;
            AUCTION_END_TIMES[i] = 0;
        }
    </script>

    <script type="text/javascript" src="${ctx}/js/forms.js"></script>
    <script type="text/javascript" src="${ctx}/js/game.js"></script>

    <script type="text/javascript">
        function recalcAllStartDates() {
            for (var i = 0; i < ${blockCount}; i++) {
                recalcAuctionStart(i);
                recalcAuctionEnd(i);
            }
        }
        function calcGameStartDate() {
            var form = document.GameForm;
            var d = new Date();
            d.setFullYear(${param['year']});
            d.setMonth(${param['month']} - 1);
            d.setDate(${param['day']});
            var h = ${param['hour']};
            var m = ${param['minute']};
            if ('${param['ampm']}' == 'am') {
                if (h == 12) {
                    h = 0;
                }
            } else {
                if (h != 12) {
                    h += 12;
                }
            }
            d.setHours(h);
            d.setMinutes(m);
            d.setSeconds(0);
            d.setMilliseconds(0);
            return d;
        }
    </script>
</head>

<body id="page" onload="recalcAllStartDates();">
<div id="container">
<c:set var="subbar" value="games" scope="page"/>
<%@ include file="header.jsp" %>

<div id="wrap">
<div id="breadcrumb-admin">
    &raquo; Game Management &nbsp; &raquo; Create New Game &nbsp;
    <span class="active"> &raquo; Add Blocks/Slots</span>
    <span class="next"> &nbsp; &raquo; Game Created</span>
</div>

<div id="data-table-admin">
<form action="${ctx}/server/admin/finishGameCreation.do" name="GameForm" id="GameForm" method="POST">
<input type="hidden" name="dateFormat" value="${dateFormat}"/>
<ul>
<!-- Block Start -->
<c:forEach begin="0" end="${blockCount - 1}" var="n">
<input type="hidden" name="blockInfo" id="blockInfo${n}" value=""/>
<li class="${n == 0 ? 'open' : 'closed'}" id="blockHeader${n}">
    <a href="#" onclick="clickBlock(${n});return false;">BLOCK ${n + 1}</a>
</li>
<li class="admin-block" ${n == 0 ? '' : 'style="display:none"'} id="blockDetails${n}">

<table border="0" cellpadding="0" cellspacing="0">
<tr>
    <th>&nbsp;</th>
    <th>Block</th>
    <th>Slot</th>
</tr>
<tr>
<td>&nbsp;</td>
<td>
    <table border="0" cellpadding="0" cellspacing="0" class="small">
        <tr>
            <td>Max Duration:</td>
            <td colspan="2" id="maxDuration${n}">0h 0m 0s</td>
        </tr>
        <tr>
            <td>Latest Possible Start:</td>
            <td colspan="2" id="latestStart${n}"></td>
        </tr>
        <tr>
            <td>Auction Start:</td>
            <td class="right">Month:</td>
            <td>
                <select name="month${n}" id="month${n}" class="inputBox"  style="height: 20px;"
                        onchange="recalcAuctionStart(${n});">
                    <option value="01" ${admin:selectIfMonthMatches(currentTime, 0, request, null, dateFormat)}>Jan</option>
                    <option value="02" ${admin:selectIfMonthMatches(currentTime, 1, request, null, dateFormat)}>Feb</option>
                    <option value="03" ${admin:selectIfMonthMatches(currentTime, 2, request, null, dateFormat)}>March</option>
                    <option value="04" ${admin:selectIfMonthMatches(currentTime, 3, request, null, dateFormat)}>April</option>
                    <option value="05" ${admin:selectIfMonthMatches(currentTime, 4, request, null, dateFormat)}>May</option>
                    <option value="06" ${admin:selectIfMonthMatches(currentTime, 5, request, null, dateFormat)}>June</option>
                    <option value="07" ${admin:selectIfMonthMatches(currentTime, 6, request, null, dateFormat)}>July</option>
                    <option value="08" ${admin:selectIfMonthMatches(currentTime, 7, request, null, dateFormat)}>Aug</option>
                    <option value="09" ${admin:selectIfMonthMatches(currentTime, 8, request, null, dateFormat)}>Sept</option>
                    <option value="10" ${admin:selectIfMonthMatches(currentTime, 9, request, null, dateFormat)}>Oct</option>
                    <option value="11" ${admin:selectIfMonthMatches(currentTime, 10, request, null, dateFormat)}>Nov</option>
                    <option value="12" ${admin:selectIfMonthMatches(currentTime, 11, request, null, dateFormat)}>Dec</option>
                </select>
            </td>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td class="right">Day:</td>
            <td>
                <select name="day${n}" id="day${n}" class="inputBox"  style="height: 20px;"
                        onchange="recalcAuctionStart(${n});">
                    <c:forEach begin="1" end="31" var="i">
                        <option value="${i < 10 ? ('0' + i) : i}" ${admin:selectIfDayMatches(currentTime, i, request, null, dateFormat)}>${i}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td class="right">Year:</td>
            <td>
                <select name="year${n}" id="year${n}" class="inputBox" style="height: 20px;"
                        onchange="recalcAuctionStart(${n});">
                    <c:forEach begin="2006" end="2010" var="i">
                        <option value="${i}" ${admin:selectIfYearMatches(currentTime, i, request, null, dateFormat)}>${i}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td class="right">Time:</td>
            <td>
                <input name="time${n}" type="text" value="${admin:getTime(currentTime)}" class="inputBox" style="width:60px"
                       onchange="recalcAuctionStart(${n});"/>
                <select name="ampm${n}" id="ampm${n}" class="inputBox" onchange="recalcAuctionStart(${n});" style="height: 20px;">
                    <option value="am" ${admin:selectIfAMPMMatches(currentTime, 0, request, null, dateFormat)}>AM</option>
                    <option value="pm" ${admin:selectIfAMPMMatches(currentTime, 1, request, null, dateFormat)}>PM</option>
                </select>
            </td>
        </tr>
        <tr>
            <td>Auction End:</td>
            <td class="right">Month:</td>
            <td>
                <select name="month2${n}" id="month2${n}" class="inputBox" style="height: 20px;"
                        onchange="recalcAuctionEnd(${n});">
                    <option value="01" ${admin:selectIfMonthMatches(currentTime, 0, request, null, dateFormat)}>Jan</option>
                    <option value="02" ${admin:selectIfMonthMatches(currentTime, 1, request, null, dateFormat)}>Feb</option>
                    <option value="03" ${admin:selectIfMonthMatches(currentTime, 2, request, null, dateFormat)}>March</option>
                    <option value="04" ${admin:selectIfMonthMatches(currentTime, 3, request, null, dateFormat)}>April</option>
                    <option value="05" ${admin:selectIfMonthMatches(currentTime, 4, request, null, dateFormat)}>May</option>
                    <option value="06" ${admin:selectIfMonthMatches(currentTime, 5, request, null, dateFormat)}>June</option>
                    <option value="07" ${admin:selectIfMonthMatches(currentTime, 6, request, null, dateFormat)}>July</option>
                    <option value="08" ${admin:selectIfMonthMatches(currentTime, 7, request, null, dateFormat)}>Aug</option>
                    <option value="09" ${admin:selectIfMonthMatches(currentTime, 8, request, null, dateFormat)}>Sept</option>
                    <option value="10" ${admin:selectIfMonthMatches(currentTime, 9, request, null, dateFormat)}>Oct</option>
                    <option value="11" ${admin:selectIfMonthMatches(currentTime, 10, request, null, dateFormat)}>Nov</option>
                    <option value="12" ${admin:selectIfMonthMatches(currentTime, 11, request, null, dateFormat)}>Dec</option>
                </select>
            </td>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td class="right">Day:</td>
            <td>
                <select name="day2${n}" id="day2${n}" class="inputBox" onchange="recalcAuctionEnd(${n});" style="height: 20px;">
                <c:forEach begin="1" end="31" var="i">
                    <option value="${i < 10 ? ('0' + i) : i}" ${admin:selectIfDayMatches(currentTime, i, request, null, dateFormat)}>${i}</option>
                </c:forEach>
            </select>
            </td>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td class="right">Year:</td>
            <td>
                <select name="year2${n}" id="year2${n}" class="inputBox" onchange="recalcAuctionEnd(${n});" style="height: 20px;">
                    <c:forEach begin="2006" end="2010" var="i">
                        <option value="${i}" ${admin:selectIfYearMatches(currentTime, i, request, null, dateFormat)}>${i}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td class="right">Time:</td>
            <td><input name="time2${n}" type="text" value="${admin:getTime(currentTime)}" class="inputBox" style="width:60px"
                       onchange="recalcAuctionEnd(${n});"/>
                <select name="ampm2${n}" id="ampm2${n}" class="inputBox" onchange="recalcAuctionEnd(${n});" style="height: 20px;">
                    <option value="am" ${admin:selectIfAMPMMatches(currentTime, 0, request, null, dateFormat)}>AM</option>
                    <option value="pm" ${admin:selectIfAMPMMatches(currentTime, 1, request, null, dateFormat)}>PM</option>
                </select>
            </td>
        </tr>
    </table>
</td>
<td>
    <table border="0" cellpadding="0" cellspacing="0" class="small" id="slotList${n}">
        <tr>
            <td>&nbsp;</td>
            <td class="buttons">
                <a href="#" onclick="addSlot(${n}, '${ctx}');return false;">
                    <img src="${ctx}/i/b/btn_admin_addslot.gif" width="55" height="15" class="btn" alt="Add Slot"/>
                </a>
            </td>
        </tr>
    </table>
</td>
</tr>
</table>
</li>
</c:forEach>

<!-- Block END -->
</ul>
</form>
</div>

<div class="buttons">
    <a href="${ctx}/server/admin/viewGames.do" title="Cancel">
        <img src="${ctx}/i/b/btn_admin_cancel.gif" width="42" height="15" alt="Cancel"/>
    </a>
    <a href="#" title="Create Game" onclick="submitGameBlocksForm(calcGameStartDate());return false;">
        <img src="${ctx}/i/b/btn_admin_creategame.gif" width="78" height="15" alt="Create Game"/>
    </a>
</div>
</div>


</div>
<%@ include file="footer.jsp" %>
</body>
</html>