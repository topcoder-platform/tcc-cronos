<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="/dropdown" prefix="dd" %>
<%@ taglib uri="/orpheus_admin" prefix="admin" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="request" value="${pageContext.request}" scope="page"/>
<c:set var="dateFormat" value="MM/dd/yyyy HH:mm:ss" scope="page"/>
<c:set var="currentTime" value="<%=new Date()%>" scope="page"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: Create New Game :: Private Auction</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/styles.css"/>
    <script type="text/javascript" src="${ctx}/js/forms.js"></script>
    <script type="text/javascript" src="${ctx}/js/game.js"></script>
    <script type="text/javascript">
        var BLOCK_COUNT = ${blockCount};
        var BLOCK_DURATIONS = new Array();
        var BLOCK_SLOT_COUNTS = new Array();
        var BLOCK_SLOTS = new Array();
        var AUCTION_TYPE = PRIVATE_AUCTION;
        for (var i = 0; i < ${blockCount}; i++) {
            BLOCK_DURATIONS[i] = 0;
            BLOCK_SLOT_COUNTS[i] = 0;
            BLOCK_SLOTS[i] = new Array();
        }

        function addSlotPrivateAuction(ctx) {
            var bs = document.GameForm.blockNum;
            var blockNum = parseInt(bs.options[bs.selectedIndex].value) - 1;

            var sponsor = document.getElementById('sponsorId');
            var domain = document.getElementById('domainId');
            var image = document.getElementById('imageId');
            var bid = document.getElementById('maxBid');
            var duration = document.getElementById('duration');
            var sponsorId = sponsor.options[sponsor.selectedIndex].value;
            var domainId = domain.options[domain.selectedIndex].value;
            var imageId = image.options[image.selectedIndex].value;

            if (sponsor.selectedIndex == 0) {
                alert('Plase, select a sponsor');
                return;
            }
            if (domain.selectedIndex == 0) {
                alert('Plase, select a domain');
                return;
            }
            if (image.selectedIndex == 0) {
                alert('Plase, select an image');
                return;
            }
            if (duration.value == null || duration.value == '') {
                alert('Plase, enter a duration');
                return;
            }
            if (bid.value == null || bid.value == '') {
                alert('Plase, enter a bid value');
                return;
            }

            var table = document.getElementById('slotList' + blockNum);
            var slotCount = table.rows.length - 1;

            var img = document.createElement('img');
            img.src = ctx + '/i/b/btn_admin_remove.gif';
            img.style.width = '49px';
            img.style.height = '15px';
            img.className = 'btn';
            img.alt = 'Remove';
            img.style.align = 'absmiddle';

            var a = document.createElement('a');
            a.title = 'Remove';
            a.href = '#';
            a.onclick = new Function('removeSlotPrivateAuction(' + blockNum + ',' + slotCount + ');return false;');
            a.appendChild(img);

            var tr = table.insertRow(slotCount + 1);

            var td0 = tr.insertCell(0);
            var td1 = tr.insertCell(1);
            var td2 = tr.insertCell(2);
            var td3 = tr.insertCell(3);
            var td4 = tr.insertCell(4);
            var td5 = tr.insertCell(5);

            td0.appendChild(document.createTextNode(sponsor.options[sponsor.selectedIndex].text));
            td1.appendChild(document.createTextNode(domain.options[domain.selectedIndex].text));
            td2.appendChild(document.createTextNode(image.options[image.selectedIndex].text));
            td3.appendChild(document.createTextNode(duration.value));
            td4.appendChild(document.createTextNode('$' + bid.value));
            td5.appendChild(a);

            BLOCK_SLOTS[blockNum][BLOCK_SLOT_COUNTS[blockNum]] = '"sponsor":' + sponsorId + ',"image":' + imageId
                + ',"maxbid":' + bid.value;
            BLOCK_SLOT_COUNTS[blockNum]++;
            BLOCK_DURATIONS[blockNum] += (parseInt(duration.value) * 3600);
        }

        function removeSlotPrivateAuction(blockNum, slotNum) {
            var table = document.getElementById('slotList' + blockNum);
            BLOCK_DURATIONS[blockNum] -= (parseInt(table.rows[slotNum + 1].cells[3].innerHTML) * 3600);
            table.deleteRow(slotNum + 1);
            for (var i = 1; i < table.rows.length; i++) {
                var a = table.rows[i].cells[5].lastChild;
                a.onclick = new Function('removeSlotPrivateAuction(' + blockNum + ',' + (i - 1) + ');return false;');
            }
            BLOCK_SLOT_COUNTS[blockNum]--;
        }

    </script>
</head>

<body id="page">
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
        <form action="${ctx}/server/admin/finishGameCreationPrivateAuction.do" name="GameForm" id="GameForm"
              method="POST">
            <input type="hidden" name="dateFormat" value="${dateFormat}"/>
            <table border="0" cellpadding="0" cellspacing="0" id="slotList${n}">
                <tr>
                    <th width="20">Sponsor</th>
                    <th width="20%">Domain</th>
                    <th width="20%">Image</th>
                    <th width="10%">Duration (hours)</th>
                    <th width="10%">Bid</th>
                    <th width="10%">Block</th>
                    <th width="10%">Action</th>
                </tr>
                <tr>
                    <dd:dropdownlist>
                        <td>
                            <dd:dropdown id="sel0" classStyle="inputBox" prompt="Select Sponsor"
                                         name="sponsorId" data="${sponsors}"/>
                        </td>
                        <td>
                            <dd:dropdown id="sel1" classStyle="inputBox" prompt="Select Domain"
                                         name="domainId" data="${domains}"/>
                        </td>
                        <td>
                            <dd:dropdown id="sel2" classStyle="inputBox" prompt="Select Image"
                                         name="imageId" data="${images}"/>
                        </td>
                    </dd:dropdownlist>
                    <td>
                        <input name="duration" type="text" id="duration" class="inputBox"
                               maxlength="9" value="${param['duration']}" size="9"
                               onkeypress="return acceptDigit(event);"/>
                    </td>
                    <td>
                        $<input name="maxBid" type="text" id="maxBid" class="inputBox" size="9"
                                maxlength="9" value="${param['maxBid']}"
                                onkeypress="return acceptDigit(event);"/>
                    </td>
                    <td>
                        <select name="blockNum" id="blcokNum" class="inputBox">
                            <c:forEach begin="1" end="${param['blockCount']}" step="1" var="b">
                                <option value="${b}">${b}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        <a href="#" onclick="addSlotPrivateAuction('${ctx}');return false;">
                            <img src="${ctx}/i/b/btn_admin_addslot.gif" width="55" height="15" class="btn"
                                 alt="Add Slot"/>
                        </a>
                    </td>
                </tr>
            </table>
            <ul>
                <!-- Block Start -->
                <c:forEach begin="0" end="${blockCount - 1}" var="n">
                    <input type="hidden" name="blockInfo" id="blockInfo${n}" value=""/>
                    <li class="open" id="blockHeader${n}">
                        <a href="#" onclick="clickBlock(${n});return false;">BLOCK ${n + 1}</a>
                    </li>
                    <li class="admin-block" id="blockDetails${n}">
                        <table border="0" cellpadding="0" cellspacing="0" id="slotList${n}">
                            <tr>
                                <th>Sponsor</th>
                                <th>Domain</th>
                                <th>Image</th>
                                <th>Duration (hours)</th>
                                <th>Bid</th>
                                <th>Action</th>
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
        <a href="#" title="Create Game" onclick="submitGameBlocksForm(null);return false;">
            <img src="${ctx}/i/b/btn_admin_creategame.gif" width="78" height="15" alt="Create Game"/>
        </a>
    </div>
</div>

</div>
<%@ include file="footer.jsp" %>
</body>
</html>