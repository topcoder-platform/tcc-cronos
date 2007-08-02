<%@ page errorPage="error.jsp" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="/orpheus" prefix="orpheus" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: Retrieve Password</title>
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
                <td>
                    <ul id="tablistBlue">
                        <li id="current"><a href="#">Retrieve Your Password</a></li>
                    </ul>

                    <div style="clear:both;"></div>
                </td>
            </tr>
            <tr>
                <td>
                    <div id="table-form">
                        <form action="${ctx}/server/retrievePassword.do"
                              name="RetrievePasswordForm" id="RetrievePasswordForm">
                            <table border="0" cellpadding="0" cellspacing="0" width="313">
                                <tr>
                                    <td class="tdTop" colspan="2"></td>
                                </tr>
                                <c:if test="${param['notFound'] eq 'true'}">
                                    <tr>
                                        <td colspan="2">
                                            <span class="fBold cRed">Email Address not found.</span><br/>
                                        </td>
                                    </tr>
                                </c:if>
                            ${orpheus:error('email', validationErrors)}
                                <tr>
                                    <td>Email:</td>
                                    <td>
                                        <input name="email" type="text" id="email" class="inputBox" maxlength="256"
                                            onkeypress="return submitOnEnter(event, this.form);"
                                               style="width:143px;" value="${param['email']}"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>&nbsp;</td>
                                    <td class="buttons">
                                        <a href="#" title="Retrieve Password"
                                           onclick="submitForm(document.RetrievePasswordForm);return false;">
                                            <img src="${ctx}/i/b/btn_continue.gif" width="57" height="15"
                                                 alt="Retrieve Password"/>
                                        </a>
                                    </td>
                                </tr>
                            </table>
                        </form>
                    </div>
                </td>
            </tr>
        </table>
    </div>
</div>
<%@ include file="../includes/footer.jsp" %>
</body>
</html>