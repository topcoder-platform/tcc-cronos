<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="/orpheus_admin" prefix="admin" %>
<%@ taglib uri="/paging" prefix="p" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="gameId" value="${param['gameId']}" scope="page"/>
<c:set var="tracker" value="${applicationScope['OrpheusOnlineStatusTracker']}" scope="page"/>
<c:set var="dateFormat" value="MM/dd/yyyy HH:mm:ss" scope="page"/>
<c:set var="activePlayers" value="${tracker.activePlayers}" scope="page"/>
<c:set var="activeSponsors" value="${tracker.activeSponsors}" scope="page"/>
<c:set var="activeAdmins" value="${tracker.activeAdmins}" scope="page"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: Online Users</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/styles.css"/>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/tabs.css"/>
    <script language="javascript" src="${ctx}/js/popup.js" type="text/javascript"></script>
    <script type="text/javascript">
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
    <script type="text/javascript">
        function submitLogForm(handle) {
            var form = document.LogForm;
            form.handle.value = handle;

            var e = new Date();
            var s = new Date();
            s.setTime(e.getTime() - 24 * 60 * 60 * 1000);

            var startDate = (s.getMonth() + 1) + '/' + s.getDate() + '/' + s.getFullYear();
            startDate += ' ' + s.getHours() + ':' + s.getMinutes() + ':' + s.getSeconds();
            form.startDate.value = startDate;

            var endDate = (e.getMonth() + 1) + '/' + e.getDate() + '/' + e.getFullYear();
            endDate += ' ' + e.getHours() + ':' + e.getMinutes() + ':' + e.getSeconds();
            form.endDate.value = endDate;

            form.submit();
        }
        function updateList() {
            window.location = '${ctx}/server/admin/online/listUsers.do';
        }
    </script>
</head>

<body id="page" onload="setTimeout('updateList()', 60 * 1000)">
<div id="container">
<c:set var="subbar" value="games" scope="page"/>
<%@ include file="header.jsp" %>
<div id="wrap">
<div id="breadcrumb-admin">
    &raquo; Game Management &nbsp; <span class="active"> &raquo; Online Status</span>
    </div>

<div id="data-table-admin">
    <form action="${ctx}/server/admin/report/showLog.do" name="LogForm" id="LogForm" method="POST"
          target="OrpheusLog">
        <input type="hidden" name="startDate" value=""/>
        <input type="hidden" name="endDate" value=""/>
        <input type="hidden" name="dateFormat" value="${dateFormat}"/>
        <input type="hidden" name="handle" value=""/>
        <ul>
            <!-- Players Block Start -->
            <li class="open" id="blockHeader0">
                <a href="#" onclick="clickBlock(0);return false;">Players</a>
            </li>
            <li class="admin-block" id="blockDetails0">
                <table border="0" cellpadding="0" cellspacing="0">
                    <tr>
                        <th width="50%">Handle</th>
                        <th width="50%"></th>
                    </tr>
                    <c:forEach items="${activePlayers}" var="user">
                        <tr>
                            <td>
                                <a href="#" onclick="submitLogForm('${admin:getHandle(user)}');return false;">
                                        ${admin:getHandle(user)}
                                </a>
                            </td>
                            <td>&nbsp;</td>
                        </tr>
                    </c:forEach>
                </table>
            </li>
            <!-- Players Block End -->
            <!-- Sponsors Block Start -->
            <li class="open" id="blockHeader1">
                <a href="#" onclick="clickBlock(1);return false;">Sponsors</a>
            </li>
            <li class="admin-block" id="blockDetails1">
                <table border="0" cellpadding="0" cellspacing="0">
                    <tr>
                        <th width="50%">Handle</th>
                        <th width="50%"></th>
                    </tr>
                    <c:forEach items="${activeSponsors}" var="user">
                        <tr>
                            <td>
                                <a href="#" onclick="submitLogForm('${admin:getHandle(user)}');return false;">
                                        ${admin:getHandle(user)}
                                </a>
                            </td>
                            <td>&nbsp;</td>
                        </tr>
                    </c:forEach>
                </table>
            </li>
            <!-- Sponsors Block End -->
            <!-- Admins Block Start -->
            <li class="open" id="blockHeader2">
                <a href="#" onclick="clickBlock(2);return false;">Administrators</a>
            </li>
            <li class="admin-block" id="blockDetails2">
                <table border="0" cellpadding="0" cellspacing="0">
                    <tr>
                        <th width="50%">Handle</th>
                        <th width="50%"></th>
                    </tr>
                    <c:forEach items="${activeAdmins}" var="user">
                        <tr>
                            <td>
                                <a href="#" onclick="submitLogForm('${admin:getHandle(user)}');return false;">
                                        ${admin:getHandle(user)}
                                </a>
                            </td>
                            <td>&nbsp;</td>
                        </tr>
                    </c:forEach>
                </table>
            </li>
            <!-- Sponsors Block End -->
        </ul>
    </form>
  </div>
</div>
</div>
<%@ include file="footer.jsp" %>
</body>
</html>