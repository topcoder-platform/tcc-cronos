<%--
  - Author: pulky
  - Date: 21 Oct 2009
  - Version: 1.0
  - Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
  -
  - Description: This is a simple page to test contest facade services
--%>
<%@ page import="java.io.StringWriter" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="javax.naming.Context" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="com.topcoder.service.facade.contest.ContestServiceFacade" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String calledOperation = null;
    Object callResult = null;
    Throwable error = null;

    try {
        String operation = request.getParameter("operation");
        calledOperation = operation;
        if ("isEligible".equals(operation)) {
            String uid = request.getParameter("user_id");
            String cid = request.getParameter("contest_id");
            String isStudio = request.getParameter("is_studio");
            
            Context context = new InitialContext();
            ContestServiceFacade contestServiceFacade =
                (ContestServiceFacade) context.lookup("remote/ContestServiceFacadeBean");

            callResult = contestServiceFacade.isEligible(Long.parseLong(uid), Long.parseLong(cid), isStudio.equalsIgnoreCase("true"));

            //callResult = "true.";
        }
    } catch (Throwable e) {
        error = e;
    }
    if (error != null) {
        StringWriter sw = new StringWriter();
        error.printStackTrace(new PrintWriter(sw));
        callResult = "ERROR!<br/>" + sw.getBuffer().toString().replaceAll("\\n", "<br/>");
    }
%>
<html>
  <head>
      <title>Contest Service Facade Demo</title>
  </head>
  <body>
      <p>Contest Eligibility tests: <%=calledOperation%></p>
      <p>Resulf of the call::::: <%=callResult%></p>
      <a href="test_services.jsp">Back to list of available operations</a>
  </body>
</html>
