<%@ page contentType="text/html" %>

<%
    String key = request.getParameter("key");
%>
<% if (key.equals("ball_forums")) { %>
<link type="text/css" rel="stylesheet" href="/css/tcforums/style.css" />
<link type="text/css" rel="stylesheet" href="/css/tcforums/coders.css" />
<link type="text/css" rel="stylesheet" href="/css/tcforums/roundTables.css" />
<link type="text/css" rel="stylesheet" href="/css/tcforums/stats.css" />
<link type="text/css" rel="stylesheet" href="/css/tcforums/tcStyles.css" />
<% } %>