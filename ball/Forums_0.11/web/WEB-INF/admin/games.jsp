<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="/paging" prefix="p" %>
<%@ taglib uri="/orpheus_admin" prefix="admin" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: Games</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/styles.css"/>
</head>

<body id="page">
<div id="container">
    <c:set var="subbar" value="games" scope="page"/>
    <%@ include file="header.jsp" %>
    <div id="wrap">
        <div id="breadcrumb-admin">
            &raquo; Game Management &nbsp;<span class="active"> &raquo; View All Games &nbsp;</span>
        </div>

        <p>Select a row to view Ball progress and alter the hosting Order of a game.</p>

        <p:dataPaging pageSize="10" data="${admin:convertToList(admin:reverse(games))}" id="pager"
                      requestURL="${ctx}/server/admin/viewGames.do">
            <p:page>
                <div id="data-table-admin">
                    <p:table border="0" cellpadding="0" cellspacing="0"
                             comparator="${admin:getAdminAllGamesComparator()}">
                        <p:header>
                            <p:column index="1" name="Start Date"/>
                            <p:column index="2" name="Start Time"/>
                            <p:column index="3" name="Game"/>
                            <p:column index="4" name="Current Host"/>
                            <p:column index="5" name="Game Status"/>
                            <p:column index="6" name="# of Keys"/>
                        </p:header>
                        <p:rowData id="item" rowId="row" oddRowStyle="alt" rowType="com.orpheus.game.persistence.Game"
                                   renderTR="false">
                            <c:set var="gameStatus" value="${admin:getGameStatus(row)}"/>
                            <tr style="cursor: pointer;" onmouseover="this.style.backgroundColor='#FEF1C4';"
                                onmouseout="this.style.backgroundColor='${item.rowNumberOnPage mod 2 eq 0 ? '#ffffff' : '#f4f4f4'}';"
                                <c:if test="${item.rowNumberOnPage mod 2 eq 1}">
                                    class="alt"
                                </c:if>
                                <c:if test="${gameStatus ne 'Completed'}">
                                    onClick="javascript:window.location='${ctx}/server/admin/gameDetails.do?gameId=${row.id}'"
                                </c:if>>
                                <td><fmt:formatDate value="${row.startDate}" pattern="MM/dd/yyyy"/></td>
                                <td><fmt:formatDate value="${row.startDate}" pattern="hh:mm aa"/></td>
                                <td>
                                    <a href="#">${row.name}</a>
                                </td>
                                <td>${admin:getCurrentHost(row)}</td>
                                <td>${gameStatus}</td>
                                <td>${row.keyCount}</td>
                            </tr>
                        </p:rowData>
                    </p:table>
                </div>

                <div class="pagination">
                    <table border="0" cellpadding="0" cellspacing="0">
                        <tr>
                            <td>Showing Games ${admin:getDataPagingFooterMessage(pager)}</td>
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