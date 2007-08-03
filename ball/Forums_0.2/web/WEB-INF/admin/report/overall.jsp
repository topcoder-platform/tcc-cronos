<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="/orpheus_admin" prefix="admin" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: Games Report :: Overall</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/styles.css"/>
</head>

<body id="page">
<div id="container">
    <c:set var="subbar" value="games" scope="page"/>
    <%@ include file="../header.jsp" %>
    <div id="wrap">
        <div id="breadcrumb-admin">
            &raquo; Game Management &nbsp; &raquo; View Game Reports &nbsp;<span class="active"> &raquo; Overall &nbsp;</span>
        </div>

        <div id="data-table-admin">
            <table border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <th colspan="2">Overall statistics</th>
                </tr>
                <tr>
                    <td>Total no. of players who signed up for an account</td>
                    <td>${total[0]}</td>
                </tr>
                <tr>
                    <td>Total no. of player who downloaded IE plugin</td>
                    <td>${total[1]}</td>
                </tr>
                <tr>
                    <td>Total no. of player who downloaded FF plugin</td>
                    <td>${total[2]}</td>
                </tr>
                <tr>
                    <td>Total no. of games played</td>
                    <td>${total[3]}</td>
                </tr>
<%--
                <tr>
                    <td>Total Prize Payout</td>
                    <td>${total[4]}</td>
                </tr>
--%>
                <tr>
                    <td>Total no. of players registered across all games</td>
                    <td>${total[5]}</td>
                </tr>
                <tr>
                    <td>Total no. of players who have found at least 1 key on a site</td>
                    <td>${total[6]}</td>
                </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <th>Game</th>
                    <th>Registrants #</th>
                    <th>Found Keys #</th>
                    <th>Unlocked Sites</th>
                </tr>
                <c:forEach items="${stats}" var="stat" varStatus="index">
                    <tr <c:if test="${index.index mod 2 eq 1}">class="alt"</c:if>>
                        <td>${stat.key.name}</td>
                        <td>${stat.value[0]}</td>
                        <td>${stat.value[1]}</td>
                        <td>
                            <c:forEach items="${stat.value[2]}" var="site">
                                <a href="http://${site.domainName}" target="OrpheusAdminSite">
                                    ${site.domainName}
                                </a>&nbsp;&nbsp;
                            </c:forEach>
                            &nbsp;
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</div>
<%@ include file="../footer.jsp" %>
</body>
</html>