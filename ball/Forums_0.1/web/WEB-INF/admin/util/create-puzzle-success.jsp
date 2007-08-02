<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="/orpheus_admin" prefix="admin" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: Create Practice Puzzle</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/styles.css"/>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/tabs.css"/>
    <script type="text/javascript" src="${ctx}/js/forms.js"></script>
</head>

<body id="page">
<div id="container">
    <c:set var="subbar" value="games" scope="page"/>
    <%@ include file="../header.jsp" %>
    <div id="wrap">
        <div id="breadcrumb-admin">
            &raquo; Utilities &nbsp; &raquo; Create Practice Puzzle &nbsp; <span class="active"> &raquo; Success &nbsp;</span>
        </div>

        <table border="0" width="313" cellspacing="0" cellpadding="0" id="table1" align="center">
            <tr>
                <td>
                    <ul id="tablistBlue">
                        <li id="current"><a href="#">Created Practice Puzzle</a></li>
                    </ul>

                    <div style="clear:both;"></div>
                </td>
            </tr>
            <tr>
                <td>
                    <div id="table-form">
                        <table border="0" cellpadding="0" cellspacing="0" width="360">
                            <tr>
                                <td class="tdTop" colspan="2"></td>
                            </tr>
                            <tr>
                                <td width="77%">ID of created practice puzzle of ${param['puzzleType']} type:</td>
                                <td width="23%">
                                    ${practicePuzzleId}
                                </td>
                            </tr>
                        </table>
                    </div>
                </td>
            </tr>
        </table>
        <br/>
    </div>
</div>
<%@ include file="../footer.jsp" %>
</body>
</html>
