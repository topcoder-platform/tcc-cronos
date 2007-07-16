<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="adminSummary" value="${requestScope['adminSummary']}" scope="page"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: Administration</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/styles.css"/>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/tabs.css"/>
</head>

<body id="page">
<div id="container">
    <c:set var="subbar" value="summary" scope="page"/>
    <%@ include file="header.jsp" %>

    <div id="wrap">
        <div class="table-alert">
            <table border="0" cellpadding="0" cellspacing="0" width="313">
                <tr>
                    <td><img src="${ctx}/i/alert.gif" width="34" height="34" alt="Alert"/></td>
                    <td>
                        <a href="${ctx}/server/admin/pendingSponsors.do">
                            There are ${adminSummary.pendingSponsorCount} sponsors awaiting approval.
                        </a>
                    </td>
                </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0" width="313">
                <tr>
                    <td><img src="${ctx}/i/alert.gif" width="34" height="34" alt="Alert"/></td>
                    <td>
                        <a href="${ctx}/server/admin/pendingWinners.do">
                            There are ${adminSummary.pendingWinnerCount} game winners awaiting approval.
                        </a>
                    </td>
                </tr>
            </table>
        </div>

        <div id="tabcontentcontainer">
            <table border="0" width="313" cellspacing="0" cellpadding="0" id="table1" align="center">
                <tr>
                    <td>
                        <ul id="tablistBlue">
                            <li id="current"><a href="#">Games Admin</a></li>
                        </ul>
                        <div style="clear:both;"></div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div id="table-form">
                            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                                <tr>
                                    <td class="tdTop"></td>
                                </tr>
                                <tr>
                                    <td><span class="lgcontent cBlue fBold">There are currently
                                        <span
                                            class="cOrange">${adminSummary.activeGameCount}</span> active games.</span>
                                    </td>
                                </tr>
                                <tr>
                                    <td><a href="${ctx}/server/admin/viewGames.do">View</a> all active games.</td>
                                </tr>
                                <tr>
                                    <td><a href="${ctx}/server/admin/startGameCreation.do">Create</a> a new game.</td>
                                </tr>
                                <tr>
                                    <td>
                                        <a href="${ctx}/server/admin/report/overall.do">View</a> overall game report.
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        View overall <a href="${ctx}/server/admin/report/playerOverall.do">player</a>
                                        report.
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        View <a href="${ctx}/server/admin/report/showLogForm.do">log</a>.
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        View <a href="${ctx}/server/admin/online/listUsers.do">online status</a>.
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </td>
                </tr>
            </table>
            <br/>
            <table border="0" width="313" cellspacing="0" cellpadding="0" id="table2" align="center">
                <tr>
                    <td>
                        <ul id="tablistBlue">
                            <li id="current"><a href="#">Utilities</a></li>
                        </ul>
                        <div style="clear:both;"></div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div id="table-form">
                            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                                <tr>
                                    <td class="tdTop"></td>
                                </tr>
                                <tr>
                                    <td>
                                        Create
                                        <a href="${ctx}/server/admin/util/showCreatePracticePuzzleForm.do">practice</a>
                                        puzzle.
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        Create new
                                        <a href="${ctx}/server/admin/util/showCreateBallColorForm.do">Ball color</a>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</div>
<%@ include file="footer.jsp" %>
</body>
</html>