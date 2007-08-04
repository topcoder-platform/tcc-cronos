<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: Stop Game</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/styles.css"/>
</head>

<body id="page" style="width:300px">
<div id="container">
    <img border="0" src="${ctx}/i/error.gif" width="16" height="16" alt=""/>
    <span class="fBold">&nbsp;Are you sure you want to stop this game?</span>

    <p align="center">
        <img border="0" src="${ctx}/i/b/btn_admin_ok.gif" width="26" height="15"
             onClick="window.open('${ctx}/server/admin/stopGame.do?gameId=${param['gameId']}', 'Orpheus');window.close();"
             alt="Move Ball To Next Slot" style="cursor:pointer;"/>
        <img border="0" src="${ctx}/i/b/btn_admin_cancel.gif" width="42" height="15" onClick="window.close();"
             alt="Cancel" style="cursor:pointer;"/>
    </p>
</div>
</body>
</html>