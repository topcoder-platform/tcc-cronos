<%@  page
  language="java"
  import="java.text.DecimalFormat,
          com.topcoder.shared.util.ApplicationServer,
          com.topcoder.web.common.SessionInfo,
          com.topcoder.web.common.BaseServlet,
          com.jivesoftware.base.User" %>

<% 	User user = (User) request.getAttribute("user");
    SessionInfo sessionInfo = (SessionInfo)request.getAttribute(BaseServlet.SESSION_INFO_KEY);
    String level1 = request.getParameter("level1")==null?"competition":request.getParameter("level1");
%>

userID: <%=user.getUsername()%>

<div class="topBar">
    <div style="float: right; margin: 5px 0px 0px 0px;">
        <% if ( !sessionInfo.isAnonymous() ) { %>
            Hello,&nbsp;<tc-webtag:handle coderId='<%=sessionInfo.getUserId()%>' darkBG="true" />
            <% if (level1.equals("long")) { %>
                | <a class="gMetal" href="http://<%=ApplicationServer.SERVER_NAME%>/longcontest/?module=Logout">Logout</a>
            <% } else { %>
                | <a class="gMetal" href="http://<%=ApplicationServer.SERVER_NAME%>/tc?module=Logout">Logout</a>
            <% } %>
        <% } else {
            if (level1.equals("long")) {%>
                <a class="gMetal" href="http://<%=ApplicationServer.SERVER_NAME%>/longcontest/?module=Login">Login</a>
            <% } else { %>
                <a class="gMetal" href="http://<%=ApplicationServer.SERVER_NAME%>/tc?&amp;module=Login">Login</a>
            <% } %>
        <%}%>
    </div>
</div>