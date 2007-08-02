<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<li>
    <a href="#" onclick="showDownload('${ctx}');return false;">Download</a>
</li>
<li>
    <a href="${ctx}/public/faq-download.jsp">Installation FAQ</a>
</li>
