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
            // Start date
            var date = form.month.value + '/' + form.day.value + '/' + form.year.value;
            date += ' ' + form.hour.value + ':' + form.minute.value + ' ' + form.ampm.value;
            form.startDate.value = date;
            // End date
            if (form.month_e) {
                date = form.month_e.value + '/' + form.day_e.value + '/' + form.year_e.value;
                date += ' ' + form.hour_e.value + ':' + form.minute_e.value + ' ' + form.ampm_e.value;
                form.endDate.value = date;
            }
            submitForm(form);
        }

        function showEndTimeForm() {
            var form = document.GameForm;

            var m = new Array();
            m[0] = '';
            m[1] = 'Jan';
            m[2] = 'Feb';
            m[3] = 'Mar';
            m[4] = 'Apr';
            m[5] = 'May';
            m[6] = 'Jun';
            m[7] = 'Jul';
            m[8] = 'Aug';
            m[9] = 'Sep';
            m[10] = 'Oct';
            m[11] = 'Nov';
            m[12] = 'Dec';
            
            var table = document.getElementById('GameDetailsTable');
            var td1, td2;
            var s1, s2, s3;

            // End Date
            var tr1 = table.insertRow(table.rows.length - 1);
            td1 = tr1.insertCell(0);
            td2 = tr1.insertCell(1);
            td2.colSpan = 2;

            td1.appendChild(document.createTextNode('End Date:'))

            s1 = document.createElement('select');
            s1.name = 'month_e';
            s1.id = 'month_e';
            s1.className = 'inputBox';
            s1.style.height = '20px';
            for (var i = 1; i <= 12; i++) {
                var option = new Option();
                if (i < 10) {
                    option.value = '0' + i;
                } else {
                    option.value = '' + i;
                }
                option.text = m[i];
                s1.options[s1.options.length] = option;
            }
            s1.selectedIndex = form.month.selectedIndex;

            s2 = document.createElement('select');
            s2.name = 'day_e';
            s2.id = 'day_e';
            s2.className = 'inputBox';
            s2.style.height = '20px';
            for (var i = 1; i < 32; i++) {
                var option = new Option();
                option.value = '' + i;
                option.text = '' + i;
                s2.options[s2.options.length] = option;
            }
            s2.selectedIndex = form.day.selectedIndex;

            s3 = document.createElement('select');
            s3.name = 'year_e';
            s3.id = 'year_e';
            s3.className = 'inputBox';
            s3.style.height = '20px';
            for (var i = 2006; i < 2011; i++) {
                var option = new Option();
                option.value = '' + i;
                option.text = '' + i;
                s3.options[s3.options.length] = option;
            }
            s3.selectedIndex = form.year.selectedIndex;

            td2.appendChild(s1);
            td2.appendChild(s2);
            td2.appendChild(s3);

            // End Time
            var tr2 = table.insertRow(table.rows.length - 1);
            td1 = tr2.insertCell(0);
            td2 = tr2.insertCell(1);
            td2.colSpan = 2;

            td1.appendChild(document.createTextNode('End Time:'))

            s1 = document.createElement('select');
            s1.name = 'hour_e';
            s1.id = 'hour_e';
            s1.className = 'inputBox';
            s1.style.height = '20px';
            for (var i = 1; i < 13; i++) {
                var option = new Option();
                option.value = '' + i;
                option.text = '' + i;
                s1.options[s1.options.length] = option;
            }
            s1.selectedIndex = form.hour.selectedIndex;

            s2 = document.createElement('select');
            s2.name = 'minute_e';
            s2.id = 'minute_e';
            s2.className = 'inputBox';
            s2.style.height = '20px';
            for (var i = 0; i < 60; i++) {
                var option = new Option();
                option.value = '' + i;
                option.text = '' + i;
                s2.options[s2.options.length] = option;
            }
            s2.selectedIndex = form.minute.selectedIndex;

            s3 = document.createElement('select');
            s3.name = 'ampm_e';
            s3.id = 'ampm_e';
            s3.className = 'inputBox';
            s3.style.height = '20px';
            var option;
            option = new Option();
            option.value = 'am';
            option.text = 'am';
            s3.options[s3.options.length] = option;
            option = new Option();
            option.value = 'pm';
            option.text = 'pm';
            s3.options[s3.options.length] = option;
            s3.selectedIndex = form.ampm.selectedIndex;

            td2.appendChild(s1);
            td2.appendChild(s2);
            td2.appendChild(s3);
        }

        function removeEndTimeForm() {
            var table = document.getElementById('GameDetailsTable');
            table.deleteRow(table.rows.length - 2);
            table.deleteRow(table.rows.length - 2);
        }

        function gameCompletionSelected(list) {
            var type = list.options[list.selectedIndex].value;
            if (type == '2') {
                showEndTimeForm();
            } else {
                removeEndTimeForm();
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

        <div style="clear:both;"></div>
    </td>
</tr>
<tr>
    <td>
        <div id="table-form">
            <form action="${ctx}/server/admin/continueGameCreation.do" name="GameForm" id="GameForm" method="POST">
                <input type="hidden" name="startDate" value=""/>
                <input type="hidden" name="endDate" value=""/>
                <input type="hidden" name="dateFormat" value="${dateFormat}"/>
                <table border="0" cellpadding="0" cellspacing="0" width="360" id="GameDetailsTable">
                    <tr>
                        <td class="tdTop" colspan="3"></td>
                    </tr>
                    <tr>
                        <td width="23%">Game Name:</td>
                        <td width="77%" colspan="2">
                            <select name="colorId" class="inputBox" style="height: 20px;">
                                <c:forEach items="${colors}" var="color">
                                    <option value="${color.id}" ${param['colorId'] eq color.id ? 'selected="selected"' : ''}
                                        >${color.name}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>Start Date:</td>
                        <td colspan="2">
                            <select name="month" id="hour0" class="inputBox" style="height: 20px;">
                                <option
                                    value="01" ${admin:selectIfMonthMatches(currentTime, 0, request, 'startDate', dateFormat)}>
                                    Jan
                                </option>
                                <option
                                    value="02" ${admin:selectIfMonthMatches(currentTime, 1, request, 'startDate', dateFormat)}>
                                    Feb
                                </option>
                                <option
                                    value="03" ${admin:selectIfMonthMatches(currentTime, 2, request, 'startDate', dateFormat)}>
                                    March
                                </option>
                                <option
                                    value="04" ${admin:selectIfMonthMatches(currentTime, 3, request, 'startDate', dateFormat)}>
                                    April
                                </option>
                                <option
                                    value="05" ${admin:selectIfMonthMatches(currentTime, 4, request, 'startDate', dateFormat)}>
                                    May
                                </option>
                                <option
                                    value="06" ${admin:selectIfMonthMatches(currentTime, 5, request, 'startDate', dateFormat)}>
                                    June
                                </option>
                                <option
                                    value="07" ${admin:selectIfMonthMatches(currentTime, 6, request, 'startDate', dateFormat)}>
                                    July
                                </option>
                                <option
                                    value="08" ${admin:selectIfMonthMatches(currentTime, 7, request, 'startDate', dateFormat)}>
                                    Aug
                                </option>
                                <option
                                    value="09" ${admin:selectIfMonthMatches(currentTime, 8, request, 'startDate', dateFormat)}>
                                    Sept
                                </option>
                                <option
                                    value="10" ${admin:selectIfMonthMatches(currentTime, 9, request, 'startDate', dateFormat)}>
                                    Oct
                                </option>
                                <option
                                    value="11" ${admin:selectIfMonthMatches(currentTime, 10, request, 'startDate', dateFormat)}>
                                    Nov
                                </option>
                                <option
                                    value="12" ${admin:selectIfMonthMatches(currentTime, 11, request, 'startDate', dateFormat)}>
                                    Dec
                                </option>
                            </select>
                            <select name="day" id="hour1" class="inputBox" style="height: 20px;">
                                <c:forEach begin="1" end="31" var="n">
                                    <option
                                        value="${n < 10 ? ('0' + n) : n}" ${admin:selectIfDayMatches(currentTime, n, request, 'startDate', dateFormat)}>${n}</option>
                                </c:forEach>
                            </select>
                            <select name="year" id="year" class="inputBox" style="height: 20px;">
                                <c:forEach begin="2006" end="2010" var="n">
                                    <option
                                        value="${n}" ${admin:selectIfYearMatches(currentTime, n, request, 'startDate', dateFormat)}>${n}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>Start Time:</td>
                        <td colspan="2">
                            <select name="hour" id="hour" class="inputBox" style="height: 20px;">
                                <c:forEach begin="1" end="12" var="n">
                                    <option
                                        value="${n < 10 ? ('0' + n) : n}" ${admin:selectIfHourMatches(currentTime, n, request, 'startDate', dateFormat, true)}>${n}</option>
                                </c:forEach>
                            </select>:
                            <select name="minute" id="minute" class="inputBox" style="height: 20px;">
                                <c:forEach begin="0" end="59" var="n">
                                    <option
                                        value="${n < 10 ? ('0' + n) : n}" ${admin:selectIfMinuteMatches(currentTime, n, request, 'startDate', dateFormat)}>${n < 10 ? ('0' + n) : n}</option>
                                </c:forEach>
                            </select>
                            <select name="ampm" id="ampm" class="inputBox" style="height: 20px;">
                                <option
                                    value="am" ${admin:selectIfAMPMMatches(currentTime, 0, request, 'startDate', dateFormat)}>
                                    am
                                </option>
                                <option
                                    value="pm" ${admin:selectIfAMPMMatches(currentTime, 1, request, 'startDate', dateFormat)}>
                                    pm
                                </option>
                            </select>
                        </td>
                    </tr>
                    ${admin:error('keyCount', validationErrors, 3)}
                    <tr>
                        <td nowrap colspan="2"># of Keys Needed when Ball is Found:</td>
                        <td>
                            <input name="keyCount" type="text" id="maxBid10" class="inputBox" style="width:43px;"
                                   value="${param['keyCount']}"/>
                        </td>
                    </tr>
                    ${admin:error('blockCount', validationErrors, 3)}
                    <tr>
                        <td nowrap colspan="2">Number of Blocks in the Game:</td>
                        <td>
                            <input name="blockCount" type="text" id="maxBid9" class="inputBox" style="width:43px;"
                                   value="${param['blockCount']}"/>
                        </td>
                    </tr>
                    <tr>
                        <td nowrap colspan="2">Game Creation Schema:</td>
                        <td>
                            <select name="creationType" class="inputBox" style="height: 20px;">
                                <c:forEach items="${admin:getGameCreationTypes()}" var="creationType">
                                    <option value="${creationType.id}" ${param['creationType'] eq creationType.id ? 'selected="selected"' : ''}>
                                        ${creationType.description}
                                    </option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td nowrap colspan="2">Prize Awarding Schema:</td>
                        <td>
                            <c:set var="prizeTypeSource" value="${applicationScope['PrizeCalcTypeSource']}" scope="page"/>
                            <select name="prizeType" class="inputBox" style="height: 20px;">
                                <c:forEach items="${prizeTypeSource.allTypes}" var="prizeType">
                                    <option value="${prizeType.id}" ${param['prizeType'] eq prizeType.id ? 'selected="selected"' : ''}>
                                        ${prizeType.name}
                                    </option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td nowrap colspan="2">Bounce Points Awarding Schema:</td>
                        <td>
                            <c:set var="bouncePointTypeSource" value="${applicationScope['BouncePointCalcTypeSource']}" scope="page"/>
                            <select name="bounceType" class="inputBox" style="height: 20px;">
                                <c:forEach items="${bouncePointTypeSource.allTypes}" var="bounceType">
                                    <option value="${bounceType.id}" ${param['bounceType'] eq bounceType.id ? 'selected="selected"' : ''}>
                                        ${bounceType.name}
                                    </option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td nowrap colspan="2">Game Completion Schema:</td>
                        <td>
                            <c:set var="gameCompletionTypeSource" value="${applicationScope['GameCompletionTypeSource']}" scope="page"/>
                            <select name="completionType" class="inputBox" style="height: 20px;"
                                    onchange="gameCompletionSelected(this);">
                                <c:forEach items="${gameCompletionTypeSource.allTypes}" var="completionType">
                                    <option value="${completionType.id}"  ${param['completionType'] eq completionType.id ? 'selected="selected"' : ''}>
                                        ${completionType.name}
                                    </option>
                                </c:forEach>
                            </select>                                           
                        </td>
                    </tr>
                    <c:if test="${param['completionType'] eq '2'}">
                        <tr>
                            <td>End Date:</td>
                            <td colspan="2">
                                <select name="month_e" id="hour0_" class="inputBox" style="height: 20px;">
                                    <option
                                        value="01" ${admin:selectIfMonthMatches(currentTime, 0, request, 'endDate', dateFormat)}>
                                        Jan
                                    </option>
                                    <option
                                        value="02" ${admin:selectIfMonthMatches(currentTime, 1, request, 'endDate', dateFormat)}>
                                        Feb
                                    </option>
                                    <option
                                        value="03" ${admin:selectIfMonthMatches(currentTime, 2, request, 'endDate', dateFormat)}>
                                        March
                                    </option>
                                    <option
                                        value="04" ${admin:selectIfMonthMatches(currentTime, 3, request, 'endDate', dateFormat)}>
                                        April
                                    </option>
                                    <option
                                        value="05" ${admin:selectIfMonthMatches(currentTime, 4, request, 'endDate', dateFormat)}>
                                        May
                                    </option>
                                    <option
                                        value="06" ${admin:selectIfMonthMatches(currentTime, 5, request, 'endDate', dateFormat)}>
                                        June
                                    </option>
                                    <option
                                        value="07" ${admin:selectIfMonthMatches(currentTime, 6, request, 'endDate', dateFormat)}>
                                        July
                                    </option>
                                    <option
                                        value="08" ${admin:selectIfMonthMatches(currentTime, 7, request, 'endDate', dateFormat)}>
                                        Aug
                                    </option>
                                    <option
                                        value="09" ${admin:selectIfMonthMatches(currentTime, 8, request, 'endDate', dateFormat)}>
                                        Sept
                                    </option>
                                    <option
                                        value="10" ${admin:selectIfMonthMatches(currentTime, 9, request, 'endDate', dateFormat)}>
                                        Oct
                                    </option>
                                    <option
                                        value="11" ${admin:selectIfMonthMatches(currentTime, 10, request, 'endDate', dateFormat)}>
                                        Nov
                                    </option>
                                    <option
                                        value="12" ${admin:selectIfMonthMatches(currentTime, 11, request, 'endDate', dateFormat)}>
                                        Dec
                                    </option>
                                </select>
                                <select name="day_e" id="hour1_" class="inputBox" style="height: 20px;">
                                    <c:forEach begin="1" end="31" var="n">
                                        <option
                                            value="${n < 10 ? ('0' + n) : n}" ${admin:selectIfDayMatches(currentTime, n, request, 'endDate', dateFormat)}>${n}</option>
                                    </c:forEach>
                                </select>
                                <select name="year_e" id="year_" class="inputBox" style="height: 20px;">
                                    <c:forEach begin="2006" end="2010" var="n">
                                        <option
                                            value="${n}" ${admin:selectIfYearMatches(currentTime, n, request, 'endDate', dateFormat)}>${n}</option>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>End Time:</td>
                            <td colspan="2">
                                <select name="hour_e" id="hour_" class="inputBox" style="height: 20px;">
                                    <c:forEach begin="1" end="12" var="n">
                                        <option
                                            value="${n < 10 ? ('0' + n) : n}" ${admin:selectIfHourMatches(currentTime, n, request, 'endDate', dateFormat, true)}>${n}</option>
                                    </c:forEach>
                                </select>:
                                <select name="minute_e" id="minute_" class="inputBox" style="height: 20px;">
                                    <c:forEach begin="0" end="59" var="n">
                                        <option
                                            value="${n < 10 ? ('0' + n) : n}" ${admin:selectIfMinuteMatches(currentTime, n, request, 'endDate', dateFormat)}>${n < 10 ? ('0' + n) : n}</option>
                                    </c:forEach>
                                </select>
                                <select name="ampm_e" id="ampm_" class="inputBox" style="height: 20px;">
                                    <option
                                        value="am" ${admin:selectIfAMPMMatches(currentTime, 0, request, 'endDate', dateFormat)}>
                                        am
                                    </option>
                                    <option
                                        value="pm" ${admin:selectIfAMPMMatches(currentTime, 1, request, 'endDate', dateFormat)}>
                                        pm
                                    </option>
                                </select>
                            </td>
                        </tr>
                    </c:if>
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