<%@ page errorPage="error.jsp" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>The Ball :: Known Bugs</title>
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/css/styles.css"/>
    <style type="text/css">
        <!--
        .style1 {
            color: #333333
        }

        .style2 {
            color: #FFFFFF;
            font-weight: bold;
        }

        -->
    </style>
</head>
<body id="page">
<div id="container">
    <%@ include file="../includes/header.jsp" %>
    <div id="wrap">
        <p><strong><a href="${ctx}/public/bug_report.jsp">Report a Bug</a></strong> | <strong>Known Bugs</strong></p>

        <div><br/>
            <table width="600" border="1" align="center" cellpadding="5" cellspacing="0" bordercolor="#CCCCCC"
                   id="bugGrid">
                <tr>
                    <td colspan="4" bgcolor="#DFDFDF"><strong class="fBold style1">KNOWN BUGS </strong></td>
                </tr>
                <tr bgcolor="#666666">
                    <td><span class="style2">Bug</span></td>
                    <td><span class="style2">How to reproduce bug </span></td>
                    <td align="center"><span class="style2">Submitter</span></td>
                    <td align="center"><span class="style2">Date</span></td>
                </tr>
                <tr>
                    <td align="left" valign="top">Awkward Plugin UI Navigation</td>
                    <td align="left" valign="top">1. Log into plugin (IE or FF)<br/>
                        2. Go to any key or ball site<br/>
                        3. Click on trailer for any game on that site<br/>
                        4. Brainteaser* to be solved appears<br/>
                        5. Click on any tab on the Plugin menu bar: &quot;Unlocked Sites&quot;, &quot;Upcoming Domains&quot;,
                        &quot;Leaderboard&quot;<br/>
                        6. Brainteaser screen is lost - accessible only by right-clicking and using &quot;Back&quot;
                        button<br/>
                        * Note this problem is generic i.e. applies to any target, not just the brainteaser
                    </td>
                    <td align="left" valign="top"><a href="#">polgara</a></td>
                    <td align="left" valign="top">04.04.2007</td>
                </tr>
                <tr>
                    <td align="left" valign="top">&nbsp;</td>
                    <td align="left" valign="top">&nbsp;</td>
                    <td align="left" valign="top">&nbsp;</td>
                    <td align="left" valign="top">&nbsp;</td>
                </tr>
                <tr>
                    <td align="left" valign="top">&nbsp;</td>
                    <td align="left" valign="top">&nbsp;</td>
                    <td align="left" valign="top">&nbsp;</td>
                    <td align="left" valign="top">&nbsp;</td>
                </tr>
                <tr>
                    <td align="left" valign="top">&nbsp;</td>
                    <td align="left" valign="top">&nbsp;</td>
                    <td align="left" valign="top">&nbsp;</td>
                    <td align="left" valign="top">&nbsp;</td>
                </tr>
            </table>
        </div>
    </div>
</div>
<%@ include file="../includes/footer.jsp" %>
</body>
</html>
