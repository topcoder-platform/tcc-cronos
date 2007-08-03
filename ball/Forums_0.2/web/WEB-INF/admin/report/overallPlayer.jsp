<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="/orpheus_admin" prefix="admin" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: Player Report :: Overall</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/styles.css"/>
</head>

<body id="page">
<div id="container">
    <c:set var="subbar" value="games" scope="page"/>
    <%@ include file="../header.jsp" %>
    <div id="wrap">
        <div id="breadcrumb-admin">
            &raquo; Game Management &nbsp; &raquo; View Game Reports &nbsp;<span class="active"> &raquo; Players Overall &nbsp;</span>
        </div>

        <div id="data-table-admin">
            <table border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <th>Player</th>
                    <th>Game</th>
                    <th>Found Keys #</th>
                    <th>Unlocked Sites</th>
                    <th>Status</th>
                </tr>
                <c:forEach items="${stats}" var="stat" varStatus="index">
                    <tr <c:if test="${index.index mod 2 eq 1}">class="alt"</c:if>>
                        <td rowspan="${stat.value[1] + 1}">${admin:getHandle(stat.key)}</td>
                        <c:if test="${empty stat.value[0]}">
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                        </c:if>
                    </tr>
                    <c:forEach items="${stat.value[0]}" var="game">
                        <tr  <c:if test="${index.index mod 2 eq 1}">class="alt"</c:if>>
                            <td>${game.key.name}</td>
                            <td>${game.value[0]}</td>
                            <td>
                                <c:forEach items="${game.value[1]}" var="site">
                                    <a href="http://${site.domain.domainName}" target="OrpheusAdminSite">
                                        ${site.domain.domainName}
                                    </a>&nbsp;&nbsp;
                                </c:forEach>&nbsp;&nbsp;
                            </td>
                            <td>${game.value[2]}&nbsp;</td>
                        </tr>
                    </c:forEach>
                </c:forEach>
            </table>
        </div>
    </div>
</div>
<%@ include file="../footer.jsp" %>
</body>
</html>