<%@ page import="com.topcoder.common.web.data.Round,
                 com.topcoder.web.forums.ForumConstants"
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib uri="tc-webtags.tld" prefix="tc-webtag" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="/orpheus" prefix="orpheus" %>
<c:set var="ctx" value=""/>

<tc-webtag:useBean id="forumFactory" name="forumFactory" type="com.jivesoftware.forum.ForumFactory" toScope="request"/>
<tc-webtag:useBean id="unreadCategories" name="unreadCategories" type="java.lang.String" toScope="request"/>
<jsp:useBean id="sessionInfo" class="com.topcoder.web.common.SessionInfo" scope="request" />

<%--  com.jivesoftware.base.PresenceManager presenceManager = forumFactory.getPresenceManager(); 
    Iterator itOnline = presenceManager.getOnlineUsers(); --%>

<html>
<head>
    <link rel="icon" type="image/png" href="/i/favicon.png">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>The Ball :: Forums</title>
    <jsp:include page="script.jsp"/>
    <jsp:include page="style.jsp">
        <jsp:param name="key" value="ball_forums"/>
    </jsp:include>
</head>

<body id="page">
<div id="container">
    <c:set var="subbar" value="forums" scope="page"/>
    <%@ include file="includes/header.jsp" %>
    <div id="wrap">
    
<span class="rtbc"><a href="?module=Main" class="rtbcLink">Forums</a> <img src="/i/interface/exp_w.gif" align="absmiddle"/> Administration</span>

<form name="form1" method="post" action="${sessionInfo.servletPath}">
<tc-webtag:hiddenInput name="module" value="Admin"/>
<table cellpadding="0" cellspacing="0" width="100%">
   <tr>
      <td class="rtHeader" colspan="2">Admin Console</td>
   </tr>
   <tr>
      <td class="rtTextCell" nowrap="nowrap"><strong>Command:</strong></td>
      <td class="rtTextCell100">
        <select size="1" name="<%=ForumConstants.ADMIN_COMMAND%>">
        <%  String[] commandNames = {"Enable ratings"};
            String[] commandValues = {ForumConstants.ADMIN_ENABLE_RATINGS};
            for (int i=0; i<commandNames.length; i++) { %>
                <option value="<%=commandValues[i]%>"><%=commandNames[i]%></option>
        <%  } %>
        </select>
      </td>
   </tr>
   <%--
   <tr>
      <td class="rtTextCell" nowrap="nowrap"><strong>Online users:</strong></td>
      <td class="rtTextCell100">
        <%  while (itOnline.hasNext()) { %>
            <%  User u = (User)itOnline.next(); %>
            <tc-webtag:handle id="<%=u.getID()%>"/><%  if (itOnline.hasNext()) { %>, <% } %>
        <%  } %>
      </td>
   </tr>
   --%>
</table>
<div align="right">
<input type="image" src="/i/roundTables/update.gif" alt="Update" />
</div></form>

<div style="clear:both;">&nbsp;</div>

    </div>
</div>
<%@ include file="includes/footer.jsp" %>
</body>
</html>