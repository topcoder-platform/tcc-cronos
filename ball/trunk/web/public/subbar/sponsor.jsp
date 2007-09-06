<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<li><a href="${ctx}/public/sponsor.jsp">Overview</a> </li>
<li><a href="${ctx}/public/sponsor-howitworks.jsp">How it Works</a></li>
<li><a href="${ctx}/public/sponsorRegistration.jsp?phase=start&nextPhase=step1">Register to Become a Sponsor</a></li>
<li><a href="${ctx}/public/help_sponsor.jsp">Help</a></li>
