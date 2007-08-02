<%@ page errorPage="error.jsp" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ taglib uri="/orpheus" prefix="orpheus" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: Login</title>
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
                        <li id="current"><a href="#">Login to Your Account</a></li>
                    </ul>

                    <div style="clear:both;"></div>
                </td>
            </tr>
            <tr>
                <td>
                    <div id="table-form">
                        <form action="${ctx}/server/login.do" name="LoginForm" id="LoginForm" method="POST">
                            <table border="0" cellpadding="0" cellspacing="0" width="313">
                                <tr>
                                    <td class="tdTop" colspan="2"></td>
                                </tr>
                                <c:if test="${not empty param['duplicate']}">
                                    <tr>
                                        <td colspan="2">
                                            <span class="fBold cRed">
                                                Email address has previously been registered.<br/>Please login
                                            </span>
                                        </td>
                                    </tr>
                                </c:if>
                                <c:if test="${not empty param['duplicateHandle']}">
                                    <tr>
                                        <td colspan="2">
                                            <span class="fBold cRed">
                                                Handle has previously been registered.<br/>Please login
                                            </span>
                                        </td>
                                    </tr>
                                </c:if>
                                ${orpheus:error('username', validationErrors)}
                                <tr>
                                    <td>Username:</td>
                                    <td>
                                        <input name="username" type="text" id="username" class="inputBox" maxlength="25"
                                            onkeypress="return submitOnEnter(event, this.form);"
                                               style="width:143px;" value="${param['username']}"/>
                                    </td>
                                </tr>
                                ${orpheus:error('password', validationErrors)}
                                <tr>
                                    <td>Password:</td>
                                    <td>
                                        <input name="password" type="password" id="password" class="inputBox"
                                            onkeypress="return submitOnEnter(event, this.form);"
                                               maxlength="24" style="width:143px;"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                    <span class="subcontent Gray">
                                        <a href="${ctx}/public/retrieve-password.jsp">Forgot your password?</a>
                                    </span>
                                    </td>
                                    <td class="buttons">
                                        <a href="#" onclick="submitForm(document.LoginForm);return false;"
                                           title="Login">
                                            <img src="${ctx}/i/b/btn_login.gif" width="36" height="15" alt="Login"/>
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