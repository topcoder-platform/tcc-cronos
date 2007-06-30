<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="/orpheus_admin" prefix="admin" %>
<%@ taglib uri="/paging" prefix="p" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<fmt:setLocale value="en_US"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: Pending Winners</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/styles.css"/>
</head>

<body id="page">
<div id="container">
    <c:set var="subbar" value="winners" scope="page"/>
    <%@ include file="header.jsp" %>
    <div id="wrap">
        <div id="breadcrumb-admin">
            <span class="active">&raquo; Pending Winners &nbsp; </span>
            <span class="next">&raquo; Approve/Reject Game Winners</span>
        </div>

        <p>Select a row to view winner details and approve or unapprove the payout. </p>
        <p:dataPaging pageSize="10" data="${admin:convertToList(admin:reverse(pendingWinners))}" id="pager"
                      requestURL="${ctx}/server/admin/pendingWinners.do">
            <p:page>

                <div id="data-table-admin">
                    <p:table border="0" cellpadding="0" cellspacing="0">
                        <tr>
                            <th><a href="#">Game ID</a></th>
                            <th><a href="#">Player</a></th>
                            <th><a href="#">Payout</a></th>
                        </tr>
                        <p:rowData id="item" rowId="row" rowType="com.orpheus.administration.persistence.PendingWinner"
                                   renderTR="false">
                            <tr style="cursor: pointer;" <c:if test="${item.rowNumberOnPage mod 2 eq 1}">
                                class="alt"</c:if>
                                onclick="window.location='${ctx}/server/admin/showWinnerApprovalForm.do?gameId=${row.gameId}&playerId=${row.playerId}'"
                                onmouseover="this.style.backgroundColor='#FEF1C4';"
                                onmouseout="this.style.backgroundColor='${item.rowNumberOnPage mod 2 eq 0 ? '#ffffff' : '#f4f4f4'}';">
                                <td><a href="#">${games[admin:toLong(row.gameId)].name}</a></td>
                                <td><a href="#">${admin:getHandle(winnerProfiles[admin:toLong(row.playerId)])}</a></td>
                                <td class="right">
                                    $<fmt:formatNumber value="${row.payout}" pattern="#,##0.00"/>
                                </td>
                            </tr>
                        </p:rowData>
                    </p:table>
                </div>

                <div class="pagination">
                    <table border="0" cellpadding="0" cellspacing="0">
                        <tr>
                            <td>Showing Winners ${admin:getDataPagingFooterMessage(pager)}</td>
                            <td>
                                <ul>Page:
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


<%@ include file="footer.jsp" %>
</body>
</html>