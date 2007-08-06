<%@ page errorPage="error.jsp" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="/registration" prefix="reg" %>
<%@ taglib uri="/orpheus" prefix="orpheus" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="phase" value="${param.phase}"/>
<c:set var="nextPhase" value="${param.nextPhase}"/>
<c:set var="registrationManager" value="${orpheus:getRegistrationManager()}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: How To Play :: Sign Up :: Enter Account Information</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/styles.css"/>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/tabs.css"/>
    <script type="text/javascript" src="${ctx}/js/forms.js"></script>
</head>

<body id="page">
<div id="container">
    <c:set var="subbar" value="howto" scope="page"/>
    <%@ include file="../includes/header.jsp" %>
    <div id="wrap">
        <h1><img src="${ctx}/i/h/title_signup.gif" alt="Sign Up"/></h1>

        <div id="breadcrumb">
            &raquo; 1. View Terms of Service &nbsp;
            <span class="active"> &raquo; 2. Enter Account Information &nbsp;</span>
            <span class="next"> &raquo; 3. Confirm Registration</span>
        </div>

        <span class="fBold">Step 2: Enter Your Account Information <br/></span>
        Select a desired name and password that you will use for your account. Your Display to other players in the game
        exactly as you create it here.<br/><br/>

        <table border="0" width="313" cellspacing="0" cellpadding="0" id="table1" align="center">
            <tr>
                <td>
                    <ul id="tablistBlue">
                        <li id="current"><a href="#">Account Information</a></li>
                    </ul>

                    <div style="clear:both;"></div>
                </td>
            </tr>
            <tr>
                <td>
                    <div id="table-form">
                        <form action="${ctx}/public/registration.jsp?phase=step2&nextPhase=step3"
                              name="SignupForm" id="SignupForm" method="POST">
                            <table border="0" cellpadding="0" cellspacing="0" width="313">
                                <tr>
                                    <td class="tdTop" colspan="2"></td>
                                </tr>
                                <tr>
                                    <td>
                                        Display Name:<br/>
                                        <span class="subcontent cGray">(25 characters max)</span>
                                    </td>
                                    <td>
                                        <c:if test="${not empty requestScope['username.error']}">
                                            <span class="fBold cRed">${requestScope['username.error']}</span><br/>
                                        </c:if>
                                        <input name="username" type="text" id="username" class="inputBox"
                                            onkeypress="return submitOnEnter(event, this.form);"
                                               value="${username}" style="width:143px;"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>Password:<br/>
                                        <span class="subcontent cGray">(6-25 characters)</span>
                                    </td>
                                    <td>
                                        <c:if test="${not empty requestScope['password0.error']}">
                                            <span class="fBold cRed">${requestScope['password0.error']}</span><br/>
                                        </c:if>
                                        <input name="password0" type="password" id="password0" class="inputBox"
                                            onkeypress="return submitOnEnter(event, this.form);"
                                               style="width:143px;" size="1"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>Confirm Password:</td>
                                    <td>
                                        <c:if test="${not empty requestScope['password1.error']}">
                                            <span class="fBold cRed">${requestScope['password1.error']}</span><br/>
                                        </c:if>
                                        <input name="password1" type="password" id="password1" class="inputBox"
                                            onkeypress="return submitOnEnter(event, this.form);"
                                               style="width:143px;" size="1"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>Email Address:</td>
                                    <td>
                                        <c:if test="${not empty requestScope['email0.error']}">
                                            <span class="fBold cRed">${requestScope['email0.error']}</span><br/>
                                        </c:if>
                                        <input name="email0" type="text" id="email0" class="inputBox" value="${email0}"
                                            onkeypress="return submitOnEnter(event, this.form);"
                                               style="width:143px;"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>Confirm Email Address:</td>
                                    <td>
                                        <c:if test="${not empty requestScope['email1.error']}">
                                            <span class="fBold cRed">${requestScope['email1.error']}</span><br/>
                                        </c:if>
                                        <input name="email1" type="text" id="email1" class="inputBox" value="${email1}"
                                            onkeypress="return submitOnEnter(event, this.form);"
                                               style="width:143px;"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>&nbsp;</td>
                                    <td class="buttons">
                                        <img src="${ctx}/i/b/btn_continue.gif" alt="Continue" class="btn"
                                             onclick="submitForm(document.SignupForm);return false;"/>
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