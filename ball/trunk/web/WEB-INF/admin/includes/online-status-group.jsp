<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="/orpheus_admin" prefix="admin" %>
<%@ taglib uri="/paging" prefix="p" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="blockNum" value="${requestScope['blockNum']}"/>
<c:set var="sessions" value="${requestScope['activeSessions']}"/>
<c:set var="currentTime" value="<%=new Date()%>"/>
<c:set var="groupName" value="${requestScope['groupName']}"/>
<li class="open" id="blockHeader${blockNum}">
    <a href="#" onclick="clickBlock(${blockNum});return false;">${groupName}</a>
</li>
<li class="admin-block" id="blockDetails${blockNum}">
    <table border="0" cellpadding="0" cellspacing="0">
        <tr>
            <th width="10%">Handle</th>
            <th width="20%">Session ID</th>
            <th width="20%">Start Time</th>
            <th width="10%">Requests #</th>
            <th width="20%">Last Request Sent</th>
            <th width="10%">Duration</th>
            <th width="10%">Inactive</th>
        </tr>
        <c:forEach items="${sessions}" var="session">
            <c:set var="user" value="${session.user}" scope="page"/>
            <tr>
                <td>
                    <a href="#" onclick="submitLogForm('${admin:getHandle(user)}');return false;">
                            ${admin:getHandle(user)}
                    </a>
                </td>
                <td>${session.sessionId}</td>
                <td><fmt:formatDate value="${session.creationTime}" pattern="MM/dd/yyyy HH:mm:ss"/></td>
                <td>${session.requestsCount}</td>
                <td><fmt:formatDate value="${session.lastAccessedTime}" pattern="MM/dd/yyyy HH:mm:ss"/></td>
                <td>
                    ${admin:convertTime(currentTime.time - session.creationTime.time)}
                </td>
                <td>
                    ${admin:convertTime(currentTime.time - session.lastAccessedTime.time)}
                </td>
            </tr>
        </c:forEach>
    </table>
</li>
