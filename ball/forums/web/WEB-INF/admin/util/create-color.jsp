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
    <title>The Ball :: Create Ball Color</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/styles.css"/>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/tabs.css"/>
    <script type="text/javascript" src="${ctx}/js/forms.js"></script>
    <script type="text/javascript">
        function submitColorForm(form) {
            var a = '${ctx}/server/admin/util/createBallColor.do?';
            a += 'colorName=' + form.colorName.value;
            form.action = a;
            submitForm(form);
        }
    </script>
</head>

<body id="page">
<div id="container">
    <c:set var="subbar" value="games" scope="page"/>
    <%@ include file="../header.jsp" %>
    <div id="wrap">
        <div id="breadcrumb-admin">
            &raquo; Utilities &nbsp; <span class="active"> &raquo; Create Ball Color &nbsp;</span>
        </div>

        <table border="0" width="313" cellspacing="0" cellpadding="0" id="table1" align="center">
            <tr>
                <td>
                    <ul id="tablistBlue">
                        <li id="current"><a href="#">Create Ball Color</a></li>
                    </ul>

                    <div style="clear:both;"></div>
                </td>
            </tr>
            <tr>
                <td>
                    <div id="table-form">
                        <form action="${ctx}/server/admin/util/createPracticePuzzle.do" name="BallColorForm"
                              id="BallColorForm" enctype="multipart/form-data" method="POST">
                            <table border="0" cellpadding="0" cellspacing="0" width="360">
                                <tr>
                                    <td class="tdTop" colspan="3"></td>
                                </tr>
                                ${admin:error('colorName', validationErrors, 3)}
                                <tr>
                                    <td nowrap colspan="2">Color Name:</td>
                                    <td>
                                        <input name="colorName" type="text" class="inputBox" size="15" maxlength="15"
                                               value="${param['colorName']}"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>Ball Color Image:</td>
                                    <td colspan="2">
                                        <input name="ballColorImage" type="file" class="inputBox" style="height:18px"
                                               size="15"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td nowrap colspan="2">&nbsp;</td>
                                    <td class="buttons">
                                        <a href="#" onclick="submitColorForm(document.BallColorForm);return false;">
                                            <img src="${ctx}/i/b/btn_admin_continue.gif" class="btn" alt="continue"/>
                                        </a>
                                    </td>
                                </tr>
                            </table>
                        </form>
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
