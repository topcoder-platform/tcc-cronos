<%@  page
  language="java"
  import="com.topcoder.shared.util.ApplicationServer,
          com.jivesoftware.base.User" %>
<%@ taglib uri="tc-webtags.tld" prefix="tc-webtag" %>

<% 	User user = (User) request.getAttribute("user"); %>

<div class="topBar">
    <div style="float: right; margin: 5px 0px 0px 0px;">
        <% if (user != null) { %>
            Hello,&nbsp;<tc-webtag:handle id='<%=user.getID()%>' />
            | <a class="gMetal" href="/server/logout.do">Logout</a>
        <% } else { %>
           	<a class="gMetal" href="/public/login.jsp">Login</a>
        <% } %>
    </div>
</div>