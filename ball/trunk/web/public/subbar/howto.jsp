<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<li>
    <a href="${ctx}/public/howtoplay.jsp">Overview</a>
</li>
<li>
    <a href="${ctx}/public/help_howtoplay.jsp">Help</a>
</li>
<li>
    <a href="${ctx}/public/rules.jsp">Rules</a>
</li>
<li>
    <a href="${ctx}/public/prizes.jsp">Prizes / Payouts</a>
</li>
<li>
    <a href="${ctx}/public/registration.jsp?phase=start&nextPhase=step1">Sign Up Today!</a>
</li>
<li>
    <a href="${ctx}/server/puzzle/jigsaw.do">Practice</a> 
</li>