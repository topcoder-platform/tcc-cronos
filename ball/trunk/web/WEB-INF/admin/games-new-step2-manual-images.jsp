<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="/dropdown" prefix="dd" %>
<%@ taglib uri="/orpheus_admin" prefix="admin" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="request" value="${pageContext.request}" scope="page"/>
<c:set var="dateFormat" value="MM/dd/yyyy HH:mm:ss" scope="page"/>
<c:set var="currentTime" value="<%=new Date()%>" scope="page"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: Create New Game :: Manual :: Upload Images</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/styles.css"/>
    <script type="text/javascript" src="${ctx}/js/forms.js"></script>
    <script type="text/javascript" src="${ctx}/js/game.js"></script>
</head>

<body id="page">
<div id="container">
<c:set var="subbar" value="games" scope="page"/>
<%@ include file="header.jsp" %>

<div id="wrap">
    <div id="breadcrumb-admin">
        &raquo; Game Management &nbsp; &raquo; Create New Game &nbsp;
        <span class="active"> &raquo; Upload Images</span>
        <span class="next"> &nbsp; &raquo; Game Created</span>
    </div>

    <div id="data-table-admin">
        <form action="${ctx}/server/admin/finishGameCreationManual.do?imagesUploaded=true" name="GameForm" id="GameForm"
              method="POST" enctype="multipart/form-data">
            <table border="0" cellpadding="0" cellspacing="0" id="slotList${n}">
                <tr>
                    <th colspan="2">Upload images for specified blocks and domains</th>
                </tr>
            </table>
            <ul>
                <!-- Block Start -->
                <c:forEach items="${missingImages}" var="block">
                    <li class="open" id="blockHeader${block.key}">
                        <a href="#">BLOCK ${block.key + 1}</a>
                    </li>
                    <li class="admin-block" id="blockDetails${block.key}">
                        <table border="0" cellpadding="0" cellspacing="0" id="slotList${block.key}">
                            <tr>
                                <th width="25%">Domain</th>
                                <th width="75%">Image</th>
                            </tr>
                            <c:forEach items="${block.value}" var="missingImage">
                                <tr>
                                    <td>${missingImage[1]}</td>
                                    <td>
                                        <input name="imageUpload_${block.key}_${missingImage[0]}" type="file" id="Image1"
                                               class="inputBox" style="height:18px"
                                               size="15"/>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </li>
                </c:forEach>
                <!-- Block END -->
            </ul>
        </form>
    </div>

    <div class="buttons">
        <a href="${ctx}/server/admin/viewGames.do" title="Cancel">
            <img src="${ctx}/i/b/btn_admin_cancel.gif" width="42" height="15" alt="Cancel"/>
        </a>
        <a href="#" title="Create Game" onclick="submitForm(document.GameForm);return false;">
            <img src="${ctx}/i/b/btn_admin_creategame.gif" width="78" height="15" alt="Create Game"/>
        </a>
    </div>
</div>

</div>
<%@ include file="footer.jsp" %>
</body>
</html>