<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="/paging" prefix="p" %>
<%@ taglib uri="/orpheus_admin" prefix="admin" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="servletContext" value="${pageContext.request.session.servletContext}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: Winner Approval</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/styles.css"/>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/tabs.css"/>
</head>

<body id="page">
<div id="container">
    <c:set var="bar" value="winners" scope="page"/>
    <%@ include file="header.jsp" %>
    <div id="wrap">
        <div id="breadcrumb-admin">
            &raquo; Pending Winners &nbsp; <span class="active">&raquo; Approve/Reject Game Winners</span>
        </div>

        <p>The following winner is awaiting approval for his/her prize.</p>

        <table border="0" width="313" cellspacing="0" cellpadding="0" id="table1" align="center">
            <tr>
                <td>
                    <ul id="tablistBlue">
                        <li id="current"><a href="#">Approve/Reject Game Winner</a></li>
                    </ul>

                    <div style="clear:both;"></div></td>
            </tr>
            <tr>
                <td>
                    <div id="table-form">
                            <table border="0" cellpadding="0" cellspacing="0" width="360">
                                <tr>
                                    <td class="tdTop" colspan="2"></td>
                                </tr>
                                <tr>
                                    <td width="55%" class="bold">Game Name:</td>
                                    <td width="45%">${game.name}</td>
                                </tr>
                                <tr>
                                    <td class="bold">Prize Payout:</td>
                                    <td>$${pendingWinner.payout}</td>
                                </tr>
                                <tr>
                                    <td class="bold">Player Nickname:</td>
                                    <td>${admin:getHandle(winner)}</td>
                                </tr>
                                <tr>
                                    <td class="bold">Player Name:</td>
                                    <td>${admin:getSponsorContactName(winner)}</td>
                                </tr>
                                <tr>
                                    <td class="bold">Address:</td>
                                    <td>
                                        ${admin:getAddress1(winner)}<br/>
                                        ${admin:getAddress2(winner)}<br/>
                                        ${admin:getCity(winner)},
                                        ${admin:getState(winner)} ${admin:getPostalCode(winner)},
                                        ${admin:getCountry(winner)}
                                    </td>
                                </tr>
                                <tr>
                                    <td class="bold">Phone:</td>
                                    <td>${admin:getPhone(winner)}</td>
                                </tr>
                                <tr>
                                    <td class="bold">Preferred Method of Payment:</td>
                                    <td>${admin:getPlayerPaymentMethodPref(winner)}</td>
                                </tr>
                                <tr>
                                    <td nowrap>&nbsp;</td>
                                    <td class="buttons">
                                        <a title="Approve" href="#"
                                           onclick="window.location = '${ctx}/server/admin/approveWinner.do?gameId=${game.id}&playerId=${winner.identifier}';return false;">
                                            <img src="${ctx}/i/b/btn_admin_approve.gif" class="btn" alt="Approve"/>
                                        </a>
                                        <a href="#"
                                           onclick="window.location = '${ctx}/server/admin/showRejectWinnerForm.do?gameId=${game.id}&playerId=${winner.identifier}';return false;"
                                           title="Reject">
                                            <img src="${ctx}/i/b/btn_admin_reject.gif" class="btn" alt="Reject"/>
                                        </a>
                                    </td>
                                </tr>
                            </table>
                    </div>
                </td>
            </tr>
        </table>
        <br/>
    </div>
</div>
<%@ include file="footer.jsp" %>
</body>
</html>