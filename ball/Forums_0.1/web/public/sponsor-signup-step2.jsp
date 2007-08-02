<%@ page errorPage="error.jsp" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="/orpheus" prefix="orpheus" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: Sponsor A Game :: Sign Up</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/styles.css"/>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/tabs.css"/>
    <script type="text/javascript" src="${ctx}/js/forms.js"></script>
</head>

<body id="page">
<div id="container">
<c:set var="subbar" value="sponsor" scope="page"/>
<%@ include file="../includes/header.jsp" %>
<div id="wrap">
<h1><img src="${ctx}/i/h/title_sponsorregistration.gif" width="233" height="16" alt="Registration"/></h1>

<div id="breadcrumb">&raquo; 1. Review Terms of Service &nbsp;
    <span class="active"> &raquo; 2. Enter Account Information &nbsp;</span>
    <span class="next"> &raquo; 3. Submit Your Website &nbsp; &raquo; 4. Confirm Registration</span>
</div>


<span class="fBold">Step 2: Enter Your Account Information <br/></span>
Select a desired name and password that you will use for your account. Your Display to other players in the game exactly
as you create it here.<br/><br/>
<form action="${ctx}/public/sponsorRegistration.jsp?phase=step2&nextPhase=step3" name="SponsorForm"
      id="SponsorForm" method="POST">

<table border="0" width="313" cellspacing="0" cellpadding="0" id="table1" align="center">
    <tr>
        <td>
            <ul id="tablistBlue">
                <li id="current"><a href="#">Account Information</a></li>
            </ul>

            <div style="clear:both;"></div></td>
    </tr>
    <tr>
        <td>
            <div id="table-form">
                <table border="0" cellpadding="0" cellspacing="0" width="313">
                    <tr>
                        <td class="tdTop" colspan="2"></td>
                    </tr>
                    <tr>
                        <td width="45%">
                            Display Name:<br/><span class="subcontent cGray">(25 characters max)</span>
                        </td>
                        <td width="55%">
                            <c:if test="${not empty requestScope['username.error']}">
                                <span class="fBold cRed">${requestScope['username.error']}</span><br/>
                            </c:if>
                            <input name="username" type="text" id="username" class="inputBox" style="width:143px;"
                                onkeypress="return submitOnEnter(event, this.form);" value="${username}"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Password:<br/><span class="subcontent cGray">(6-25 characters)</span>
                        </td>
                        <td>
                            <c:if test="${not empty requestScope['password0.error']}">
                                <span class="fBold cRed">${requestScope['password0.error']}</span><br/>
                            </c:if>
                            <input name="password0" type="password" id="password0" class="inputBox" style="width:143px;"
                                onkeypress="return submitOnEnter(event, this.form);" size="1"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Confirm Password:</td>
                        <td>
                            <c:if test="${not empty requestScope['password1.error']}">
                                <span class="fBold cRed">${requestScope['password1.error']}</span><br/>
                            </c:if>
                            <input name="password1" type="password" id="password1" class="inputBox" style="width:143px;"
                                onkeypress="return submitOnEnter(event, this.form);" size="1"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Email Address:</td>
                        <td>
                            <c:if test="${not empty requestScope['email0.error']}">
                                <span class="fBold cRed">${requestScope['email0.error']}</span><br/>
                            </c:if>
                            <input name="email0" type="text" id="email0" class="inputBox" style="width:143px;"
                                onkeypress="return submitOnEnter(event, this.form);" value="${email0}"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Confirm Email Address:</td>
                        <td>
                            <c:if test="${not empty requestScope['email1.error']}">
                                <span class="fBold cRed">${requestScope['email1.error']}</span><br/>
                            </c:if>
                            <input name="email1" type="text" id="email1" class="inputBox" style="width:143px;"
                                onkeypress="return submitOnEnter(event, this.form);" value="${email1}"/>
                        </td>
                    </tr>
                </table>
            </div>
        </td>
    </tr>
</table>
<br/>
<table border="0" width="313" cellspacing="0" cellpadding="0" id="table1" align="center">
    <tr>
        <td>
            <ul id="tablistBlue">
                <li id="current"><a href="#">Personal Information</a></li>
            </ul>

            <div style="clear:both;"></div>
        </td>
    </tr>
    <tr>
        <td>
            <div id="table-form">
                    <table border="0" cellpadding="0" cellspacing="0" width="313">
                        <tr>
                            <td class="tdTop" colspan="2"></td>
                        </tr>
                        <tr>
                            <td width="45%">First Name:</td>
                            <td width="55%">
                                <c:if test="${not empty requestScope['firstName.error']}">
                                    <span class="fBold cRed">${requestScope['firstName.error']}</span><br/>
                                </c:if>
                                <input name="firstName" type="text" id="firstName" class="inputBox" value="${firstName}"
                                    onkeypress="return submitOnEnter(event, this.form);"
                                    style="width:143px;"/>
                            </td>
                        </tr>
                        <tr>
                            <td>Last Name:</td>
                            <td>
                                <c:if test="${not empty requestScope['lastName.error']}">
                                    <span class="fBold cRed">${requestScope['lastName.error']}</span><br/>
                                </c:if>
                                <input name="lastName" type="text" id="lastName" class="inputBox" value="${lastName}"
                                    onkeypress="return submitOnEnter(event, this.form);"
                                       style="width:143px;"/>
                            </td>
                        </tr>
                        <tr>
                            <td>Street Address:</td>
                            <td>
                                <c:if test="${not empty requestScope['address1.error']}">
                                    <span class="fBold cRed">${requestScope['address1.error']}</span><br/>
                                </c:if>
                                <input name="address1" type="text" id="address1" class="inputBox" style="width:143px;"
                                    onkeypress="return submitOnEnter(event, this.form);"
                                    value="${address1}"/>
                            </td>
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                            <td>
                                <c:if test="${not empty requestScope['address2.error']}">
                                    <span class="fBold cRed">${requestScope['address2.error']}</span><br/>
                                </c:if>
                                <input name="address2" type="text" id="address2" class="inputBox" style="width:143px;"
                                    onkeypress="return submitOnEnter(event, this.form);"
                                       value="${address2}"/>
                            </td>
                        </tr>
                        <tr>
                            <td>City:</td>
                            <td>
                                <c:if test="${not empty requestScope['city.error']}">
                                    <span class="fBold cRed">${requestScope['city.error']}</span><br/>
                                </c:if>
                                <input name="city" type="text" id="city" class="inputBox" style="width:143px;"
                                    onkeypress="return submitOnEnter(event, this.form);"
                                       value="${city}"/>
                            </td>
                        </tr>
                        <tr>
                            <td>State:</td>
                            <td>
                                <c:if test="${not empty requestScope['state.error']}">
                                    <span class="fBold cRed">${requestScope['state.error']}</span><br/>
                                </c:if>
                                <%@ include file="state.jsp" %>
                            </td>
                        </tr>
                        <tr>
                            <td>Country:</td>
                            <td>
                                <c:if test="${not empty requestScope['country.error']}">
                                    <span class="fBold cRed">${requestScope['country.error']}</span><br/>
                                </c:if>
                                <%@ include file="country.jsp" %>
                            </td>
                        </tr>
                        <tr>
                            <td>Zip Code:</td>
                            <td>
                                <c:if test="${not empty requestScope['zipCode.error']}">
                                    <span class="fBold cRed">${requestScope['zipCode.error']}</span><br/>
                                </c:if>
                                <input name="zipCode" type="text" id="zip" class="inputBox" style="width:143px;"
                                    onkeypress="return submitOnEnter(event, this.form);" value="${zipCode}"/>
                            </td>
                        </tr>
                        <tr>
                            <td>Telephone:</td>
                            <td>
                                <c:if test="${not empty requestScope['phone.error']}">
                                    <span class="fBold cRed">${requestScope['phone.error']}</span><br/>
                                </c:if>
                                <input name="phone" type="text" id="phone" class="inputBox" style="width:143px;"
                                    onkeypress="return submitOnEnter(event, this.form);" value="${phone}"/>
                            </td>
                        </tr>
                        <tr>
                            <td>Fax:</td>
                            <td>
                                <c:if test="${not empty requestScope['fax.error']}">
                                    <span class="fBold cRed">${requestScope['fax.error']}</span><br/>
                                </c:if>
                                <input name="fax" type="text" id="fax" class="inputBox" style="width:143px;"
                                       value="${fax}" onkeypress="return submitOnEnter(event, this.form);"/>
                            </td>
                        </tr>
                        <tr>
                            <td>Payment Method:</td>
                            <td>
                                <c:if test="${not empty requestScope['paymentMethod.error']}">
                                    <span class="fBold cRed">${requestScope['paymentMethod.error']}</span><br/>
                                </c:if>
                                <input name="paymentMethod" type="text" id="payment" class="inputBox"
                                    onkeypress="return submitOnEnter(event, this.form);"
                                       style="width:143px;" value="${paymentMethod}"/>
                            </td>
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                            <td class="buttons">
                                <img src="${ctx}/i/b/btn_continue.gif"
                                     onclick="submitForm(document.SponsorForm);return false;"
                                     class="btn" alt="Continue"/>
                            </td>
                        </tr>
                    </table>
            </div>
        </td>
    </tr>
</table>
</form>
</div>
</div>
<%@ include file="../includes/footer.jsp" %>
</body>
</html>