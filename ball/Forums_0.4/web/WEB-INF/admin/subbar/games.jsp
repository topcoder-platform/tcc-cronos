<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<li><a href="${ctx}/server/admin/viewGames.do">View All Games</a></li>
<li><a href="${ctx}/server/admin/startGameCreation.do">Create New Game</a></li>
