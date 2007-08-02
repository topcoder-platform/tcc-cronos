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
    <title>The Ball :: Reject Game Winner</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/styles.css"/>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/tabs.css"/>
    <script type="text/javascript" src="${ctx}/js/forms.js"></script>
</head>

<body id="page">
<div id="container">
    <c:set var="bar" value="winners" scope="page"/>
    <%@ include file="header.jsp" %>
    <div id="wrap">
        <div id="breadcrumb-admin">
            &raquo; Pending Winners &nbsp; &raquo; Approve/Reject Game Winners
            <span class="active">&nbsp; &raquo; Reject Game Winner</span>
        </div>

        <p>Please state your reason for rejecting this player. Your message will be emailed to the player. </p>

        <table border="0" width="313" cellspacing="0" cellpadding="0" id="table1" align="center">
            <tr>
                <td>
                    <ul id="tablistBlue">
                        <li id="current"><a href="#">Reject Game Winner</a></li>
                    </ul>

                    <div style="clear:both;"></div>
                </td>
            </tr>
            <tr>
                <td>
                    <div id="table-form">
                        <form action="${ctx}/server/admin/rejectWinner.do"
                              name="RejectWinnerForm" id="RejectWinnerForm" method="POST">
                            <input type="hidden" name="gameId" value="${param['gameId']}"/>
                            <input type="hidden" name="playerId" value="${param['playerId']}"/>
                            <table border="0" cellpadding="0" cellspacing="0" width="360">
                                <tr>
                                    <td class="tdTop" colspan="2"></td>
                                </tr>
                                ${admin:error('rejectReason', validationErrors, 2)}
                                <tr>
                                    <td width="25%">Reason code:</td>
                                    <td width="75%">
                                        <select name="rejectReason" class="inputBox" style="width:250px">
                                            <option>001 - Suspected of unfair playing or cheating</option>
                                            <option>002 - Suspected of being affiliated with sponsor</option>
                                            <option>003 - Player is under age</option>
                                            <option>004 - Player is not US resident</option>
                                            <option>005 - Further investigation required</option>
                                        </select>
                                    </td>
                                </tr>
                                ${admin:error('rejectMessage', validationErrors, 2)}
                                <tr>
                                    <td colspan="2">Message to Player:<br/>
                                        <textarea class="textbox" style="width:335px" name="rejectMessage">${param['rejectMessage']}</textarea>
                                    </td>
                                </tr>
                                <tr>
                                    <td nowrap>&nbsp;</td>
                                    <td class="buttons">
                                        <a href="#" title="Reject"
                                           onclick="submitForm(document.RejectWinnerForm);return false;">
                                            <img src="${ctx}/i/b/btn_admin_reject.gif" alt="Reject"/>
                                        </a>
                                    </td>
                                </tr>
                            </table>
                        </form>
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