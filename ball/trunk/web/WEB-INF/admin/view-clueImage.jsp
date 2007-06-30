<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="/orpheus_admin" prefix="admin" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="slotId" value="${param['slotId']}" scope="page"/>
<c:set var="gameId" value="${param['gameId']}" scope="page"/>
<c:set var="target" value="${param['target']}" scope="page"/>
<c:set var="imageId" value="${empty param['updated'] ? param['imageId'] : requestScope['imageId']}" scope="page"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: View Clue Image</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/styles.css"/>
    <script defer type="text/javascript" src="${ctx}/js/pngfix.js"></script>
    <script type="text/javascript" xml:space="preserve">
        function confirmRegen(link) {
            if (confirm('Are you sure you want to re-generate clue image?')) {
                window.location = link.href;
            }
        }
    </script>
</head>
<body>
    <img src="${ctx}/server/getImage.do?downloadId=${imageId}" alt="Clue Image"/>
    <br/>
    <a href="${ctx}/server/admin/regenClueImage.do?slotId=${slotId}&gameId=${gameId}&target=${target}"
        onclick="confirmRegen(this);return false;">
        Regenerate Clue Image
    </a>
</body>
</html>