<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="/orpheus" prefix="orpheus" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: Login</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/plugin.css"/>
    <script type="text/javascript" src="${ctx}/js/forms.js"></script>
</head>

<body id="pagePlugin">
<div id="containerPlugin">
    <div id="mastHead"><img src="${ctx}/i/plugin_stripes.jpg" width="668" height="62" alt=""/></div>

    <div id="pluginTitle">Please Login <span class="main"></span></div>

    <div id="wrapPlugin">
        <p>&nbsp;</p>
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
                        <form action="${ctx}/server/plugin/login.do" name="LoginForm" id="LoginForm" method="POST">
                            <table border="0" cellpadding="0" cellspacing="0" width="313">
                                <tr>
                                    <td class="tdTop" colspan="2"></td>
                                </tr>
                                <c:if test="${param['notfound'] eq 'true'}">
                                    <tr>
                                        <td colspan="2">
                                            <span class="fBold cRed">Username/Password not found.</span><br/>
                                        </td>
                                    </tr>
                                </c:if>
                                ${orpheus:error('username', validationErrors)}
                                <tr>
                                    <td>Username:</td>
                                    <td>
                                        <input name="username" type="text" id="username" class="inputBox"
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
                                               style="width:143px;" size="1"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <span class="subcontent Gray">
                                        <a href="${ctx}/plugin/retrieve-password.jsp">Forgot your password?</a>
                                        </span>
                                    </td>
                                    <td class="buttons">
                                        <a href="#" onclick="submitForm(document.LoginForm);return false;"
                                           title="Login">
                                            <img src="${ctx}/i/b/btn_login.gif" width="36" height="15" alt="Login">
                                        </a>
                                    </td>
                                </tr>
                            </table>
                        </form>
                    </div>
                </td>
            </tr>
        </table>
        <p>&nbsp;</p>
    </div>
</div>

</body>
</html>