<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="tc-webtags.tld" prefix="tc-webtag" %>
<%@ page contentType="text/html;charset=utf-8" %>

<html>
<head>
    <link type="image/x-icon" rel="shortcut icon" href="/i/favicon.ico"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

<title>The Ball: Forums</title>
<jsp:include page="script.jsp" />
    <jsp:include page="/style.jsp">
        <jsp:param name="key" value="ball_forums"/>
    </jsp:include>


</head>

<body>

<jsp:include page="top.jsp">
    <jsp:param name="level1" value=""/>
</jsp:include>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr valign="top">

<!-- Center Column Begins -->
<td width="100%" class="rtBody">

<jsp:include page="page_title.jsp">
    <jsp:param name="image" value="forums"/>
    <jsp:param name="title" value="&#160;"/>
</jsp:include>

<span class="rtbc"><a href="?module=Main" class="rtbcLink">Forums</a> <img src="/i/interface/exp_w.gif" align="absmiddle"/> Change Log</span>

<!-- Links to versions -->

<p>
	<b>1.0 - 2007.09.xx</b>
	<ul>
		<li>Initial deployment.</li>
	</ul>
</p>

</td>
<!-- Center Column Ends -->

</tr>
</table>

<%@ include file="foot.jsp" %>

</body>

</html>