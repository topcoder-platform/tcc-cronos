<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<li>
    <a href="${ctx}/public/help_create_sponsor.jsp">Create sponsor account</a>
</li>
<li>
    <a href="${ctx}/public/help_auction_bidding.jsp">Sponsor Bidding</a>
</li>
<li>
    <a href="${ctx}/public/faq-sponsor.jsp">FAQ</a>
</li>
