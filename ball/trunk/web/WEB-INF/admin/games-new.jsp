<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="/orpheus_admin" prefix="admin" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="request" value="${pageContext.request}" scope="page"/>
<c:set var="dateFormat" value="MM/dd/yyyy hh:mm aa" scope="page"/>
<c:set var="currentTime" value="<%=new Date()%>" scope="page"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: Create New Game</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/styles.css"/>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/tabs.css"/>
    <script type="text/javascript" src="${ctx}/js/forms.js"></script>
    <script type="text/javascript" xml:space="preserve">
        function submitGameForm() {
            var form = document.GameForm;
            var date = form.month.value + '/' + form.day.value + '/' + form.year.value;
            date += ' ' + form.hour.value + ':' + form.minute.value + ' ' + form.ampm.value;
            form.startDate.value = date;
            submitForm(form);
        }
    </script>
</head>

<body id="page">
<div id="container">
<c:set var="subbar" value="games" scope="page"/>
<%@ include file="header.jsp" %>
<div id="wrap">
<div id="breadcrumb-admin">
    &raquo; Game Management &nbsp;
    <span class="active"> &raquo; Create New Game &nbsp;</span>
    <span class="next"> &raquo; Add Blocks/Slots &nbsp; &raquo; Game Created</span>
</div>

<table border="0" width="313" cellspacing="0" cellpadding="0" id="table1" align="center">
<tr>
    <td>
        <ul id="tablistBlue">
            <li id="current"><a href="#">Set Game Information</a></li>
        </ul>

        <div style="clear:both;"></div></td>
</tr>
<tr>
<td>
<div id="table-form">
<form action="${ctx}/server/admin/continueGameCreation.do" name="GameForm" id="GameForm" method="POST">
<input type="hidden" name="startDate" value=""/>
<input type="hidden" name="dateFormat" value="${dateFormat}"/>
<table border="0" cellpadding="0" cellspacing="0" width="360">
<tr>
    <td class="tdTop" colspan="3"></td>
</tr>
<tr>
    <td width="23%">Game Name:</td>
    <td width="77%" colspan="2">
        <select name="colorId" class="inputBox">
            <c:forEach items="${colors}" var="color">
                <option value="${color.id}">${color.name}</option>
            </c:forEach>
        </select>
    </td>
</tr>
<tr>
    <td>Start Date:</td>
    <td colspan="2">
        <select name="month" id="hour0" class="inputBox">
            <option value="01" ${admin:selectIfMonthMatches(currentTime, 0, request, 'startDate', dateFormat)}>Jan</option>
            <option value="02" ${admin:selectIfMonthMatches(currentTime, 1, request, 'startDate', dateFormat)}>Feb</option>
            <option value="03" ${admin:selectIfMonthMatches(currentTime, 2, request, 'startDate', dateFormat)}>March</option>
            <option value="04" ${admin:selectIfMonthMatches(currentTime, 3, request, 'startDate', dateFormat)}>April</option>
            <option value="05" ${admin:selectIfMonthMatches(currentTime, 4, request, 'startDate', dateFormat)}>May</option>
            <option value="06" ${admin:selectIfMonthMatches(currentTime, 5, request, 'startDate', dateFormat)}>June</option>
            <option value="07" ${admin:selectIfMonthMatches(currentTime, 6, request, 'startDate', dateFormat)}>July</option>
            <option value="08" ${admin:selectIfMonthMatches(currentTime, 7, request, 'startDate', dateFormat)}>Aug</option>
            <option value="09" ${admin:selectIfMonthMatches(currentTime, 8, request, 'startDate', dateFormat)}>Sept</option>
            <option value="10" ${admin:selectIfMonthMatches(currentTime, 9, request, 'startDate', dateFormat)}>Oct</option>
            <option value="11" ${admin:selectIfMonthMatches(currentTime, 10, request, 'startDate', dateFormat)}>Nov</option>
            <option value="12" ${admin:selectIfMonthMatches(currentTime, 11, request, 'startDate', dateFormat)}>Dec</option>
        </select>
        <select name="day" id="hour1" class="inputBox">
            <c:forEach begin="1" end="31" var="n">
                <option value="${n < 10 ? ('0' + n) : n}" ${admin:selectIfDayMatches(currentTime, n, request, 'startDate', dateFormat)}>${n}</option>
            </c:forEach>
        </select>
        <select name="year" id="year" class="inputBox">
            <c:forEach begin="2006" end="2010" var="n">
                <option value="${n}" ${admin:selectIfYearMatches(currentTime, n, request, 'startDate', dateFormat)}>${n}</option>
            </c:forEach>
        </select>
    </td>
</tr>
<tr>
    <td>Start Time:</td>
    <td colspan="2">
        <select name="hour" id="hour" class="inputBox">
            <c:forEach begin="1" end="12" var="n">
                <option value="${n < 10 ? ('0' + n) : n}" ${admin:selectIfHourMatches(currentTime, n, request, 'startDate', dateFormat, true)}>${n}</option>
            </c:forEach>
        </select>:
        <select name="minute" id="minute" class="inputBox">
            <c:forEach begin="0" end="59" var="n">
                <option value="${n < 10 ? ('0' + n) : n}" ${admin:selectIfMinuteMatches(currentTime, n, request, 'startDate', dateFormat)}>${n < 10 ? ('0' + n) : n}</option>
            </c:forEach>
        </select>
        <select name="ampm" id="ampm" class="inputBox">
            <option value="am" ${admin:selectIfAMPMMatches(currentTime, 0, request, 'startDate', dateFormat)}>am</option>
            <option value="pm" ${admin:selectIfAMPMMatches(currentTime, 1, request, 'startDate', dateFormat)}>pm</option>
        </select>
    </td>
</tr>
${admin:error('keyCount', validationErrors, 3)}
<tr>
    <td nowrap colspan="2"># of Keys Needed when Ball is Found:</td>
    <td>
        <input name="keyCount" type="text" id="maxBid10" class="inputBox" style="width:43px;"/>
    </td>
</tr>
${admin:error('blockCount', validationErrors, 3)}
<tr>
    <td nowrap colspan="2">Number of Blocks in the Game:</td>
    <td>
        <input name="blockCount" type="text" id="maxBid9" class="inputBox" style="width:43px;"/>
    </td>
</tr>
<tr>
    <td nowrap colspan="2">&nbsp;</td>
    <td class="buttons">
        <a href="#" onclick="submitGameForm();return false;">
            <img src="${ctx}/i/b/btn_admin_continue.gif" class="btn" alt="continue"/>
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