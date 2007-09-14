<%@ page import="com.jivesoftware.base.JiveGlobals,
                 com.jivesoftware.forum.Query,
                 com.jivesoftware.forum.action.util.Paginator,
                 com.jivesoftware.util.StringUtils,
                 com.topcoder.web.common.BaseProcessor,
                 java.util.HashMap,
                 java.util.Iterator"
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib uri="tc-webtags.tld" prefix="tc-webtag" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="/orpheus" prefix="orpheus" %>
<c:set var="ctx" value=""/>

<tc-webtag:useBean id="dates" name="dates" type="java.util.HashMap" toScope="request"/>
<tc-webtag:useBean id="unreadCategories" name="unreadCategories" type="java.lang.String" toScope="request"/>
<jsp:useBean id="sessionInfo" class="com.topcoder.web.common.SessionInfo" scope="request" />

<%  HashMap errors = (HashMap)request.getAttribute(BaseProcessor.ERRORS_KEY);
    Paginator paginator = (Paginator)request.getAttribute("paginator");
    Query query = (Query)request.getAttribute("query");
    String searchScope = (String)request.getAttribute("searchScope");
    String dateRange = (String)request.getAttribute("dateRange");
    String status = (String)request.getAttribute("status");
    Iterator results = (Iterator)request.getAttribute("results"); %>


<html>
<head>
    <link rel="icon" type="image/png" href="/i/favicon.png">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>The Ball :: Forums</title>
    <jsp:include page="script.jsp"/>
    <jsp:include page="style.jsp">
        <jsp:param name="key" value="ball_forums"/>
    </jsp:include>
<script type="text/javascript">
function noenter(e)
{
  var k = (window.event)? event.keyCode: e.which;
  return !(k == 13);
}
</script>
</head>

<body id="page">
<div id="container">
    <c:set var="subbar" value="forums" scope="page"/>
    <%@ include file="includes/header.jsp" %>
    <div id="wrap">

<table cellpadding="0" cellspacing="0" class="rtbcTable">
<tr>
    <td class="categoriesBox" style="padding-right: 20px;">
        <jsp:include page="categoriesHeader.jsp" />
    </td>
    <td nowrap="nowrap" valign="top" width="100%" style="padding-right: 20px;">
        <jsp:include page="searchHeader.jsp" >
            <jsp:param name="queryTerms" value="<%=query.getQueryString()%>"/>
        </jsp:include>
    </td>
    <td align="right" nowrap="nowrap" valign="top">   
        <A href="?module=History" class="rtbcLink">My Post History</A>&#160;&#160;|&#160;&#160;<A href="?module=Watches" class="rtbcLink">My Watches</A>&#160;&#160;|&#160;&#160;<A href="?module=Settings" class="rtbcLink">User Settings</A><br>
    </td>
</tr>
<tr>
    <td colspan="3" style="padding-top:3px;padding-bottom:9px;">
        <b><a href="?module=Main" class="rtbcLink">Forums</a> <img src="/i/interface/exp_w.gif" align="absmiddle"/> Search</b>
    </td>
</tr>
</table>

<%    if ("search".equals(status)) { %>
    <%    boolean displayPerThread = JiveGlobals.getJiveBooleanProperty("search.results.groupByThread", true); 
        int resultCount = (displayPerThread) ? query.getResultByThreadCount() : query.getResultCount(); 
        int categoriesCount = (request.getAttribute("categoriesCount") != null) ? ((Integer)request.getAttribute("categoriesCount")).intValue() : 0;
        if (categoriesCount > 0) { %>
        <jsp:include page="searchCategoryResults.jsp"/><p>
    <%    } %>
    <%    if (resultCount > 0) { %>
        <jsp:include page="searchResults.jsp"/>
    <%    } %>
    <%    if (categoriesCount == 0 && resultCount == 0) { %>
        <table cellpadding="0" cellspacing="0" class="rtbcTable">
            <tr>
                <td class="rtbc">No search results for "<%=StringUtils.escapeHTMLTags(query.getQueryString())%>". Please try a less restrictive search.</td>
            </tr>
        </table>
    <%    } %>
<%  } %>

<div style="clear:both;">&nbsp;</div>

    </div>
</div>
<%@ include file="includes/footer.jsp" %>
</body>
</html>