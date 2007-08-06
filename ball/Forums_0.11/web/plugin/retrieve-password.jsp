<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="/orpheus" prefix="orpheus" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: Retrieve Password</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/plugin.css"/>
    <script type="text/javascript" src="${ctx}/js/forms.js"></script>
</head>

<body id="pagePlugin">
<div id="containerPlugin">
    <div id="mastHead"><img src="${ctx}/i/plugin_stripes.jpg" width="668" height="62" alt=""/></div>

    <div id="pluginTitle">RETRIEVE YOUR PASSWORD <span class="main"></span></div>

    <div id="wrapPlugin">
        <p>&nbsp;</p>
        <table border="0" width="313" cellspacing="0" cellpadding="0" id="table1" align="center">
            <tr>
                <td><ul id="tablistBlue">
                    <li id="current"><a href="#">Enter Email Address</a></li>
                </ul>

                    <div style="clear:both;"></div></td>
            </tr>
            <tr>
                <td><div id="table-form">
                    <form action="${ctx}/server/plugin/retrievePassword.do"
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
                                    <input name="email" type="text" id="email" class="inputBox"
                                        onkeypress="return submitOnEnter(event, this.form);"
                                           style="width:143px;" value="${param['email']}"/>
                                </td>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <td class="buttons">
                                    <a href="#" title="Login"
                                       onclick="submitForm(document.RetrievePasswordForm);return false;">
                                        <img src="${ctx}/i/b/btn_continue.gif" alt="Continue">
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