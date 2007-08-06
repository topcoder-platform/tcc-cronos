<%@ page errorPage="error.jsp" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: How To Play :: Tax</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/styles.css"/>
</head>
<body id="page">
<div id="container">
    <%@ include file="../includes/header.jsp" %>
    <div id="wrap">
        <p><img src="${ctx}/i/h/tax.gif" alt="Tax" width="260" height="16"/></p>

        <div>
            <table class="wrap" cellspacing="0" cellpadding="0" border="0">
                <tr>
                    <td id="column-center"><br/>
                        <table width="500" border="0" align="center" cellpadding="10" cellspacing="0" id="taxGrid">
                            <tr>
                                <td width="197" bgcolor="#eaeaea"><strong>If you are a(n)</strong></td>
                                <td width="96" align="center" bgcolor="#eaeaea"><strong>Complete <br/>
                                    Form W-9<br/>
                                    <br/>
                                    <a href="${ctx}/download/iw9.pdf"
                                       target="_blank">Instructions</a><br/>
                                    <a href="${ctx}/download/fw9.pdf" target="_blank">Form</a></strong>
                                </td>
                                <td width="105" align="center" bgcolor="#eaeaea"><strong>Complete <br/>
                                    Form W-8BEN<br/>
                                    <br/>
                                    <a href="${ctx}/download/iw8ben.pdf"
                                       target="_blank">Instructions</a><br/>
                                    <a href="${ctx}/download/fw8ben.pdf"
                                       target="_blank">Form</a></strong></td>
                            </tr>
                            <tr>
                                <td>U.S. person, including a U.S. citizen and resident alien</td>
                                <td align="center"><img src="${ctx}/i/h/x.gif" width="20" height="20" alt=""/></td>
                                <td align="center">&nbsp;</td>
                            </tr>
                            <tr>
                                <td bgcolor="#f7f7f7">Foreign person, non-resident alien or foreign national performing
                                    work outside the U.S.
                                </td>
                                <td align="center" bgcolor="#f7f7f7">&nbsp;</td>
                                <td align="center" bgcolor="#f7f7f7"><img src="${ctx}/i/h/x.gif" width="20" alt=""
                                                                          height="20"/></td>
                            </tr>
                            <tr>
                                <td>Foreign person, non-resident alien or foreign national performing work in the U.S.
                                    <strong>and not claiming tax treaty benefits</strong></td>
                                <td align="center">&nbsp;</td>
                                <td align="center"><img src="${ctx}/i/h/x.gif" width="20" height="20" alt=""/></td>
                            </tr>
                            <tr>
                                <td bgcolor="#f7f7f7">Foreign person, non-resident alien or foreign national performing
                                    work in the U.S. <strong>and claiming exemption from or reduced income tax
                                    withholding</strong></td>
                                <td align="center" bgcolor="#f7f7f7">&nbsp;</td>
                                <td align="center" bgcolor="#f7f7f7">&nbsp;</td>
                            </tr>
                        </table>
                        <p>&nbsp;</p>

                        <p>NOTE: IT IS YOUR RESPONSIBILITY TO DETERMINE WHICH FORM APPLIES TO YOU. THE BALL CAN NOT
                            PROVIDE ANY GUIDANCE ON WHICH FORM YOU SHOULD COMPLETE.</p>

                        <p>
                            Please email any other questions to 
                            <a href="mailto:service@theball.com">service@theball.com</a>
                        </p>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</div>
<%@ include file="../includes/footer.jsp" %>
</body>
</html>
