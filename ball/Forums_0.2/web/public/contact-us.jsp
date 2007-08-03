<%@ page errorPage="error.jsp" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: Contact Us</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/styles.css"/>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/tabs.css"/>
    <script type="text/javascript" src="${ctx}/js/forms.js"></script>
</head>

<body id="page">
<div id="container">
    <%@ include file="../includes/header.jsp" %>
    <div id="wrap">
        <table border="0" width="313" cellspacing="0" cellpadding="0" id="table1" align="center">
            <tr>
                <td><ul id="tablistBlue">
                    <li id="current"><a href="#">Contact Us</a></li>
                </ul>

                    <div style="clear:both;"></div></td>
            </tr>
            <tr>
                <td>
                    <div id="table-form">
                        <form action="" name="ContactUsForm" id="ContactUsForm">
                            <table border="0" cellpadding="0" cellspacing="0" width="313">
                                <tr>
                                    <td class="tdTop" colspan="2"></td>
                                </tr>
                                <tr>
                                    <td width="76">Username:</td>
                                    <td width="237"><input name="username" type="text" id="username" class="inputBox"
                                                           style="width:200px;"/></td>
                                </tr>
                                <tr>
                                    <td width="76">Email:</td>
                                    <td width="237">
                                        <input name="email" type="text" id="email" class="inputBox" style="width:200px;"
                                               size="1"/></td>
                                </tr>
                                <tr>
                                    <td colspan="2">Comments/Questions:</td>
                                </tr>
                                <tr>
                                    <td colspan="2"><textarea class="textbox" name="S1" rows="1" cols="20"></textarea>
                                    </td>
                                </tr>
                                <tr>
                                    <td width="76">&nbsp;</td>
                                    <td class="buttons" width="237">
                                        <a href="#" onclick="submitForm(document.ContactUsForm);return false;">
                                            <img src="${ctx}/i/b/btn_submit.gif" class="btn" alt="Submit"/>
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
<%@ include file="../includes/footer.jsp" %>
</body>
</html>