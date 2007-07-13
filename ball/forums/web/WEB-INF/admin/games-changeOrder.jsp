<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="/orpheus_admin" prefix="admin" %>
<%@ taglib uri="/paging" prefix="p" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="gameId" value="${param['gameId']}" scope="page"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: Game Management</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/styles.css"/>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/tabs.css"/>
    <script language="javascript" src="${ctx}/js/popup.js" type="text/javascript"></script>
    <script type="text/javascript" xml:space="preserve">
        self.name = 'Orpheus';
        function clickBlock(blockNum) {
            var li = document.getElementById('blockDetails' + blockNum);
            var li2 = document.getElementById('blockHeader' + blockNum);
            if (li != null) {
                if (li.style.display == 'none') {
                    li.style.display = 'block';
                    li2.className = 'open';
                } else {
                    li.style.display = 'none';
                    li2.className = 'closed';
                }
            }
        }
    </script>
</head>

<body id="page">
<div id="container">
<c:set var="subbar" value="games" scope="page"/>
<%@ include file="header.jsp" %>
<div id="wrap">
<div id="breadcrumb-admin">
    &raquo; Game Management &nbsp; &raquo; View All Games &nbsp;<span class="active"> &raquo;
    Change Hosting Order</span></div>

<p>Select a row to view Ball progress and alter the hosting Order of a game.</p>

<div id="data-table-admin">
<ul>
<c:forEach items="${game.blocks}" var="block" varStatus="n">
<!-- Block Start -->
<li class="${n.index == 0 ? 'open' : 'closed'}" id="blockHeader${n.index}">
    <a href="#" onclick="javascript:clickBlock(${n.index});return false;">BLOCK ${n.index + 1}</a>
</li>
<li class="admin-block" ${n.index == 0 ? '' : 'style="display:none"'} id="blockDetails${n.index}">
<table border="0" cellpadding="0" cellspacing="0">
    <tr>
        <th width="76">&nbsp;</th>
        <th width="14%">URL</th>
        <th width="5%">Sponsor</th>
        <th align="center" width="20%">Max Duration</th>
        <th width="6%">Targets</th>
        <th width="11%">Brainteaser</th>
        <th width="6%" nowrap>Puzzle</th>
        <th width="7%">Reorder</th>
        <th width="11%">Remove</th>
    </tr>
    <c:forEach items="${block.slots}" var="slot" varStatus="m">
        <c:set var="slotStatus" value="${admin:getSlotStatus(slot)}" scope="page"/>
        <c:if test="${slotStatus eq 0}">
            <tr class="strike"
                <c:if test="${empty slot.puzzleId or empty slot.brainTeaserIds}">
                    style="background-color:red"
                </c:if>>
                <td class="center">&nbsp;</td>
                <td class="strike">${slot.domain.domainName}</td>
                <td nowrap>${admin:getHandle(slotSponsors[slot.id])}</td>
                <td class="center">0h 00m</td>
                <td class="center">
                    <a href="${ctx}/server/admin/viewTargets.do?slotId=${slot.id}&gameId=${gameId}"
                       onclick="NewWindow(this.href,'Detail','700','400','yes');return false">
                        <img border="0" src="${ctx}/i/b/btn_admin_view.gif" width="36" height="15"
                             class="btn" alt="View Targets"/>
                    </a>
                </td>
                <td class="center">
                    <a href="${ctx}/server/admin/viewBrainteaser.do?slotId=${slot.id}&gameId=${game.id}"
                       onclick="NewWindow(this.href,'Detail','500','500','yes');return false">
                        <img border="0" src="${ctx}/i/b/btn_admin_view.gif" width="36" height="15"
                             class="btn" alt="View Brainteaser"/>
                    </a>
                </td>
                <td class="center">
                    <a href="${ctx}/server/admin/viewPuzzle.do?slotId=${slot.id}&gameId=${game.id}"
                       onclick="NewWindow(this.href,'Detail','500','500','yes');return false">
                        <img border="0" src="${ctx}/i/b/btn_admin_view.gif" width="36" height="15"
                             class="btn" alt="View Puzzle"/>
                    </a>
                </td>
                <td class="center">&nbsp;</td>
                <td align="center">
                    <a href="${ctx}/server/admin/showRemoveSlotDialog.do?gameId=${game.id}&slotId=${slot.id}"
                       onclick="NewWindow(this.href, 'Detail', '400', '110', 'yes');return false">
                        <img border="0" src="${ctx}/i/b/btn_admin_remove.gif" width="49" height="15" class="btn"
                             alt="Remove"/>
                    </a>
                </td>
            </tr>
        </c:if>
        <c:if test="${slotStatus eq 1}">
            <tr class="active" <c:if test="${empty slot.puzzleId or empty slot.brainTeaserIds}">
                    style="background-color:red"
                </c:if>>
                <td class="center">
                    <input type="radio" value="V1" checked name="R1"/>
                    <a href="${ctx}/server/admin/showMoveBallDialog.do?gameId=${game.id}&slotId=${slot.id}"
                       onclick="NewWindow(this.href, 'Detail', '400', '110', 'yes');return false">
                        <img border="0" src="${ctx}/i/movedown.gif" width="8" height="10" alt="Move Ball To Next Slot"/>
                    </a>
                </td>
                <td><a href="${slot.domain.domainName}">${slot.domain.domainName}</a></td>
                <td>${admin:getHandle(slotSponsors[slot.id])}</td>
                <td class="center">3h 30m</td>
                <td class="center">
                    <a href="${ctx}/server/admin/viewTargets.do?slotId=${slot.id}&gameId=${gameId}"
                       onclick="NewWindow(this.href,'Detail','700','400','yes');return false">
                        <img border="0" src="${ctx}/i/b/btn_admin_view.gif" width="36" height="15"
                             class="btn" alt="View Targets"/>
                    </a>
                </td>
                <td class="center">
                    <a href="${ctx}/server/admin/viewBrainteaser.do?slotId=${slot.id}&gameId=${game.id}"
                       onclick="NewWindow(this.href,'Detail','500','500','yes');return false">
                        <img border="0" src="${ctx}/i/b/btn_admin_view.gif" width="36" height="15" class="btn"
                             alt="View Brainteaser"/>
                    </a>
                </td>
                <td class="center">
                    <a href="${ctx}/server/admin/viewPuzzle.do?slotId=${slot.id}&gameId=${game.id}"
                       onclick="NewWindow(this.href,'Detail','500','500','yes');return false">
                        <img  border="0" src="${ctx}/i/b/btn_admin_view.gif" width="36" height="15" class="btn"
                              alt="View Puzzle"/>
                    </a>
                </td>
                <td class="center">
                    <img border="0" src="${ctx}/i/movedowndis.gif" width="8" height="10" alt="Move Down"/>
                    <img border="0" src="${ctx}/i/moveupdis.gif" width="8" height="10" alt="Move Up"/>
                </td>
                <td align="center">
                    <a href="${ctx}/server/admin/showRemoveSlotDialog.do?gameId=${game.id}&slotId=${slot.id}"
                       onclick="NewWindow(this.href,'Detail', '400', '110', 'yes');return false">
                        <img border="0" src="${ctx}/i/b/btn_admin_remove.gif" width="49" height="15" class="btn"
                             alt="Remove"/>
                    </a>
                </td>
            </tr>
        </c:if>
        <c:if test="${slotStatus eq 2}">
            <tr ${m.index mod 2 == 0 ? '' : 'class="alt"'}
                <c:if test="${empty slot.puzzleId or empty slot.brainTeaserIds}">
                    style="background-color:red"
                </c:if>>
                <td class="center"><input type="radio" value="V1" name="R1"/></td>
                <td>
                    <a href="${slot.domain.domainName}">${slot.domain.domainName}</a>
                </td>
                <td>${admin:getHandle(slotSponsors[slot.id])}</td>
                <td class="center">3h 30m</td>
                <td class="center">
                    <a href="${ctx}/server/admin/viewTargets.do?slotId=${slot.id}&gameId=${gameId}"
                       onclick="NewWindow(this.href,'Detail','700','400','yes');return false">
                        <img border="0" src="${ctx}/i/b/btn_admin_view.gif" width="36" height="15"
                             class="btn" alt="View Targets"/>
                    </a>
                </td>
                <td class="center">
                    <a href="${ctx}/server/admin/viewBrainteaser.do?slotId=${slot.id}&gameId=${game.id}"
                       onclick="NewWindow(this.href,'Detail','500','500','yes');return false">
                        <img border="0" src="${ctx}/i/b/btn_admin_view.gif" width="36" height="15" class="btn"
                             alt="View Brainteaser"/>
                    </a>
                </td>
                <td class="center">
                    <a href="${ctx}/server/admin/viewPuzzle.do?slotId=${slot.id}&gameId=${game.id}"
                       onclick="NewWindow(this.href,'Detail','500','500','yes');return false">
                        <img border="0" src="${ctx}/i/b/btn_admin_view.gif" width="36" height="15" class="btn"
                             alt="View Puzzle"/>
                    </a>
                </td>
                <td class="center">
                    <c:if test="${admin:isLast(block.slots, m.index)}">
                        <img border="0" src="${ctx}/i/movedowndis.gif" width="8" height="10" alt="Move Down"/>
                    </c:if>
                    <c:if test="${not admin:isLast(block.slots, m.index)}">
                        <a href="${ctx}/server/admin/reorderSlots.do?gameId=${game.id}&slotId=${slot.id}&offset=1">
                            <img border="0" src="${ctx}/i/movedown.gif" width="8" height="10" alt="Move Down"/>
                        </a>
                    </c:if>
                    <c:if test="${m.index ne 0}">
                        <a href="${ctx}/server/admin/reorderSlots.do?gameId=${game.id}&slotId=${slot.id}&offset=-1">
                            <img border="0" src="${ctx}/i/moveup.gif" width="8" height="10" alt="Move Up"/>
                        </a>
                    </c:if>
                    <c:if test="${m.index eq 0}">
                        <img border="0" src="${ctx}/i/moveupdis.gif" width="8" height="10" alt="Move Up"/>
                    </c:if>
                </td>
                <td class="center">
                    <a href="${ctx}/server/admin/showRemoveSlotDialog.do?gameId=${game.id}&slotId=${slot.id}"
                       onclick="NewWindow(this.href, 'Detail', '400', '110', 'yes');return false">
                        <img border="0" src="${ctx}/i/b/btn_admin_remove.gif" width="49" height="15" class="btn"
                             alt="Remove"/>
                    </a>
                </td>
            </tr>
        </c:if>
    </c:forEach>
</table>
</li>
</c:forEach>
<!-- Block End -->
</ul>
</div>
</div>
</div>
<%@ include file="footer.jsp" %>
</body>
</html>