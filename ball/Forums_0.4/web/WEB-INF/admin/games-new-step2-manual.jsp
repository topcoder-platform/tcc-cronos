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
    <title>The Ball :: Create New Game :: Manual</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/styles.css"/>
    <script type="text/javascript" src="${ctx}/js/forms.js"></script>
    <script type="text/javascript" src="${ctx}/js/game.js"></script>
    <script type="text/javascript">
        var verifying = false;
        var verifiedBlock;
        var verifiedSlot;
        var req;

        var debug = true;
            
        var BLOCK_COUNT = ${blockCount};
        var BLOCK_DURATIONS = new Array();
        var BLOCK_SLOT_COUNTS = new Array();
        var BLOCK_SLOTS = new Array();
        var SLOT_TARGETS = new Array();
        var SLOT_DURATIONS = new Array();
        var SLOT_DOMAINS = new Array();
        var AUCTION_TYPE = MANUAL;
        for (var i = 0; i < ${blockCount}; i++) {
            BLOCK_DURATIONS[i] = 0;
            BLOCK_SLOT_COUNTS[i] = 0;
            BLOCK_SLOTS[i] = new Array();
            SLOT_DURATIONS[i] = new Array();
            SLOT_DOMAINS[i] = new Array();
            SLOT_TARGETS[i] = new Array();
        }

        function addSlotManual(ctx) {
            var bs = document.GameForm.blockNum;
            var blockNum = parseInt(bs.options[bs.selectedIndex].value) - 1;

            var domain = document.getElementById('domainId');
            var image = document.getElementById('imageId');
            var unregDomain = document.getElementById('unregDomain');
            var bid = document.getElementById('maxBid');
            var duration = document.getElementById('duration');
            var domainId = domain.options[domain.selectedIndex].value;
            var imageId = image.options[image.selectedIndex].value;
            // Validate the input
            if (domain.selectedIndex == 0) {
                if (unregDomain.value == '') {
                    alert('Please, select a domain or enter unregistered domain');
                    return;
                } else {
                    domainId = '-1';
                }
            } else {
                if (unregDomain.value != '') {
                    alert('Please, either select a domain or enter unregistered domain but not both');
                    return;
                }
            }
            if (domainId == '-1') {
                for (var i = 1; i < domain.options.length; i++) {
                    if (domain.options[i].text.toLowerCase() == unregDomain.value.toLowerCase()) {
                        alert('The domain "' + unregDomain.value + '" is already registered.\nPlease, select it from the list');
                        return;
                    }
                }
            }
            if (image.selectedIndex == 0) {
                imageId = '-1';
            }
            if (duration.value == null || duration.value == '') {
                alert('Please, enter a duration');
                return;
            }
            if (bid.value == null || bid.value == '') {
                alert('Please, enter a bid value');
                return;
            }
            // Add slot to list
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
            a.onclick = new Function('removeSlotManual(' + blockNum + ',' + slotCount + ');return false;');
            a.appendChild(img);

            var tr = table.insertRow(slotCount + 1);

            var td1 = tr.insertCell(0);
            var td2 = tr.insertCell(1);
            var td3 = tr.insertCell(2);

            var domainString = unregDomain.value;
            if (domainId != '-1') {
                domainString = domain.options[domain.selectedIndex].text;
            }

            var imageString = 'To be uploaded';
            if (imageId != '-1') {
                imageString = image.options[image.selectedIndex].text;
            }

            var b = document.createElement('b');
            b.appendChild(document.createTextNode('Domain: '));
            td1.appendChild(b);
            td1.appendChild(document.createTextNode(domainString));
            td1.appendChild(document.createElement('br'));
            b = document.createElement('b');
            b.appendChild(document.createTextNode('Image: '));
            td1.appendChild(b);
            td1.appendChild(document.createTextNode(imageString));
            td1.appendChild(document.createElement('br'));
            b = document.createElement('b');
            b.appendChild(document.createTextNode('Duration: '));
            td1.appendChild(b);
            td1.appendChild(document.createTextNode(duration.value + 'h'));
            td1.appendChild(document.createElement('br'));
            b = document.createElement('b');
            b.appendChild(document.createTextNode('Payment: '));
            td1.appendChild(b);
            td1.appendChild(document.createTextNode('$' + bid.value));
            td3.appendChild(a);

            var targetsTable = document.createElement('table');
            targetsTable.id = 'targets' + blockNum + '_' + slotCount;
            targetsTable.style.width = '450px';
            targetsTable.style.cellSpacing = '0';
            targetsTable.style.cellPadding = '0';
            targetsTable.style.border = '0';
            td2.appendChild(targetsTable);
            var row = targetsTable.insertRow();
            td1 = row.insertCell(0);
            td2 = row.insertCell(1);
            td3 = row.insertCell(2);

            b = document.createElement('b');
            b.appendChild(document.createTextNode('URL: '));
            td1.appendChild(b);

            b = document.createElement('b');
            b.appendChild(document.createTextNode('Target: '));
            td2.appendChild(b);

            var urlInput = document.createElement('input');
            urlInput.id = 'u' + blockNum + '_' + slotCount;
            var targetInput = document.createElement('input');
            targetInput.id = 't' + blockNum + '_' + slotCount;
            a = document.createElement('a');
            a.title = 'Add';
            a.href = '#';
            a.onclick = new Function('addTarget(' + blockNum + ',' + slotCount + ');return false;');
            a.appendChild(document.createTextNode('Add Target'));
            td1.appendChild(urlInput);
            td2.appendChild(targetInput);
            td3.appendChild(a);

            BLOCK_SLOTS[blockNum][BLOCK_SLOT_COUNTS[blockNum]] = '"domainId":' + domainId + ',"image":' + imageId
                + ',"maxbid":' + bid.value + ',"domain":"' + domainString + '"';
            SLOT_DURATIONS[blockNum][BLOCK_SLOT_COUNTS[blockNum]] = parseInt(duration.value) * 3600;
            SLOT_DOMAINS[blockNum][BLOCK_SLOT_COUNTS[blockNum]] = domainString;
            SLOT_TARGETS[blockNum][BLOCK_SLOT_COUNTS[blockNum]] = new Array();
            BLOCK_SLOT_COUNTS[blockNum]++;
            BLOCK_DURATIONS[blockNum] += (parseInt(duration.value) * 3600);
            recolor(table);
        }

        function removeSlotManual(blockNum, slotNum) {
            var table = document.getElementById('slotList' + blockNum);
            BLOCK_DURATIONS[blockNum] -= SLOT_DURATIONS[blockNum][slotNum];
            BLOCK_SLOTS[blockNum].splice(slotNum, 1);
            SLOT_DURATIONS[blockNum].splice(slotNum, 1);
            SLOT_DOMAINS[blockNum].splice(slotNum, 1);
            SLOT_TARGETS[blockNum].splice(slotNum, 1);
            table.deleteRow(slotNum + 1);
            for (var i = 1; i < table.rows.length; i++) {
                var a = table.rows[i].cells[2].lastChild;
                a.onclick = new Function('removeSlotManual(' + blockNum + ',' + (i - 1) + ');return false;');
            }
            BLOCK_SLOT_COUNTS[blockNum]--;
            recolor(table);
        }

        function recolor(table) {
            var n = table.rows.length;
            for (var i = 1; i < n; i++) {
                if (i % 2 == 0) {
                    table.rows[i].className = '';
                } else {
                    table.rows[i].className = 'alt';
                }
            }
        }

        function addTarget(blockNum, slotNum) {
            if (verifying) {
                alert('There is currently another target being verified!');
            } else {
                var urlInput = document.getElementById('u' + blockNum + '_' + slotNum);
                var targetInput = document.getElementById('t' + blockNum + '_' + slotNum);
                if (urlInput.value == '') {
                    alert('Please, enter the URL for new target');
                } else if (targetInput.value == '') {
                    alert('Please, enter the new target');
                } else {
                    var domain = SLOT_DOMAINS[blockNum][slotNum];
                    var http1 = "http://" + domain;
                    var http2 = "https://" + domain;
                    if ((urlInput.value.substring(0, http1.length) == http1)
                        || (urlInput.value.substring(0, http2.length) == http2)) {
                        verifying = true;
                        verifiedBlock = blockNum;
                        verifiedSlot = slotNum;
                        var u = escape(urlInput.value);
                        var t = escape(targetInput.value);
                        var url = '${ctx}' + '/server/admin/testTarget.do?url=' + u +'&text=' + t;
                        var span = document.getElementById('AJAX');
                        span.appendChild(document.createTextNode('Veryfying ...'));
                        sendRequest(url);
                    } else {
                        alert('The new target is not from [' + domain + '] domain');
                    }
                }
            }
        }

        function addTargetVerified(blockNum, slotNum) {
            var urlInput = document.getElementById('u' + blockNum + '_' + slotNum);
            var targetInput = document.getElementById('t' + blockNum + '_' + slotNum);
            var table = document.getElementById('targets' + blockNum + '_' + slotNum);
            var row = table.insertRow();
            var td1 = row.insertCell(0);
            var td2 = row.insertCell(1);
            var td3 = row.insertCell(2);
            td1.appendChild(document.createTextNode(urlInput.value));
            td2.appendChild(document.createTextNode(targetInput.value));

            var targetsCount = SLOT_TARGETS[blockNum][slotNum].length;
            SLOT_TARGETS[blockNum][slotNum][targetsCount]
                = '"url":"'+ escape(urlInput.value) + '","text":"' + escape(targetInput.value) + '"';

            var img = document.createElement('img');
            img.src = '${ctx}' + '/i/b/btn_admin_remove.gif';
            img.style.width = '49px';
            img.style.height = '15px';
            img.className = 'btn';
            img.alt = 'Remove';
            img.style.align = 'absmiddle';

            var a = document.createElement('a');
            a.title = 'Remove Target';
            a.href = '#';
            a.onclick = new Function('removeTarget(' + blockNum + ',' + slotNum + ',' + targetsCount + ');return false;');
            a.appendChild(img);
            td3.appendChild(a)
            urlInput.value = '';
            targetInput.value = '';
        }

        function removeTarget(blockNum, slotNum, tnum) {
            var table = document.getElementById('targets' + blockNum + '_' + slotNum);
            SLOT_TARGETS[blockNum][slotNum].splice(tnum, 1);
            table.deleteRow(tnum + 1);
            for (var i = 1; i < table.rows.length; i++) {
                var a = table.rows[i].cells[2].lastChild;
                a.onclick = new Function('removeTarget(' + blockNum + ','  + slotNum + ',' + (i - 1) + ');return false;');
            }
        }

        function sendRequest(url) {
            // branch for native XMLHttpRequest object
            if (window.XMLHttpRequest) {
                req = new XMLHttpRequest();
                req.onreadystatechange = processReqChange;
                req.open("GET", url, true);
                try {
                    req.overrideMimeType('text/xml');
                } catch(ex) {
                    // ignore, the browser does not support that method
                }
                req.send(null);
                // branch for IE/Windows ActiveX version
            } else if (window.ActiveXObject) {
                req = new ActiveXObject("Microsoft.XMLHTTP");
                if (req) {
                    req.onreadystatechange = processReqChange;
                    req.open("GET", url, true);
                    req.send();
                }
            }
        }

        function processReqChange() {
            verifying = false;
            if (req.readyState == 4) {
                var span = document.getElementById('AJAX');
                span.removeChild(span.firstChild);
                if (req.status == 200) {
                    var response = req.responseXML.documentElement;
                    var tl = response.getElementsByTagName('target-test');
                    if (tl.length > 0) {
                        var t = '';
                        if (tl[0].firstChild != null) {
                            t = response.getElementsByTagName('target-test')[0].firstChild.data;
                        }
                        if (t == '') {
                            addTargetVerified(verifiedBlock, verifiedSlot);
                        } else {
                            alert(t);
                        }
                   }
                } else {
                    if (debug) {
                        alert("Could not test the target for validity :\n" + req.statusText);
                    }
                }
            }
        }
<%--
        function viewImage() {
            var list = document.getElementById('sel2');
            if (list.selectedIndex == 0) {
                alert('Please, select the image');
            } else {
                NewWindow('${}', 'image', 600, 400, 'yes');
            }
        }
--%>
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
        <form action="${ctx}/server/admin/finishGameCreationManual.do" name="GameForm" id="GameForm"
              method="POST">
            <input type="hidden" name="dateFormat" value="${dateFormat}"/>
            <table border="0" cellpadding="0" cellspacing="0" id="slotList${n}">
                <tr>
                    <th colspan="2">Select registered domain and image(optional) or enter non-registered domain</th>
                </tr>
                <tr>
                    <dd:dropdownlist>
                        <td>
                            <dd:dropdown id="sel1" classStyle="inputBox" prompt="Select Domain"
                                         name="domainId" data="${domains}"/>
                            <dd:dropdown id="sel2" classStyle="inputBox" prompt="Select Image"
                                         name="imageId" data="${images}"/>
<%--
                            <a href="#" onclick="viewImage();return false;" title="View Selected Image">+</a>
--%>
                        </td>
                    </dd:dropdownlist>
                    <td>
                        <input type="text" name="domainManual" size="40" maxlength="255" id="unregDomain"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <b>Duration (hours):</b>
                        <input name="duration" type="text" id="duration" class="inputBox"
                               maxlength="9" value="${param['duration']}" size="9"
                               onkeypress="return acceptDigit(event);"/>&nbsp;&nbsp;&nbsp;
                        <b>Bid:</b>
                        $<input name="maxBid" type="text" id="maxBid" class="inputBox" size="9"
                                maxlength="9" value="${param['maxBid']}"
                                onkeypress="return acceptDigit(event);"/>&nbsp;&nbsp;&nbsp;
                        <b>Block:</b>
                        <select name="blockNum" id="blcokNum" class="inputBox">
                            <c:forEach begin="1" end="${param['blockCount']}" step="1" var="b">
                                <option value="${b}">${b}</option>
                            </c:forEach>
                        </select>&nbsp;&nbsp;&nbsp;
                    </td>
                    <td>
                        <a href="#" onclick="addSlotManual('${ctx}');return false;">
                            <img src="${ctx}/i/b/btn_admin_addslot.gif" width="55" height="15" class="btn"
                                 alt="Add Slot"/>
                        </a>&nbsp;
                        <span style="color:red" id="AJAX"></span>
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
                                <th width="25%">Slot</th>
                                <th width="65%">Targets</th>
                                <th width="10%">Action</th>
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