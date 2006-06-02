<%@ page import="com.topcoder.timetracker.report.ReportException" %>
<%@ page import="javax.servlet.jsp.JspException" %>
<%@ taglib uri="com.topcoder.timetracker.report" prefix="ct" %>
<!%--
 Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 -->

<html>

<body>
<h1>Time Tracker Project report</h1>
<%

    try {
%>
<hr/>
<ct:reportdisplay namespace="com.topcoder.timetracker.report.CustomConfiguration"
                  type="TYPE"
                  category="CATEGORY"
                  projectFilter="PROJECT"
                  employeeFilter="EMPLOYEE"
                  billableFilter="BILLABLE"
                  startDateFilter="START"
                  endDateFilter="END"
    />
<%
    // This is a somewhat 'hacky' error handling that displays the the error, but no error page
    // In a real-world-application, the error-handling would be somewhat more sophisticate,
    // but as in this demo we don't check whether the report configuration is consistent,
    // exceptions could happen quite often.
    //
    // the links provided below the report that cn be used to call te variants DO ensure a a
    // consistent configuration, however if the user modifies the reuest URL manually,
    // the exception caught here could occur.
} catch (Exception e) {
    if (e instanceof JspException) {
        Throwable rootCause = ((JspException) e).getRootCause();
        if (rootCause instanceof ReportException) {
%><h2>There was an error that caused the report to not being displayed:</h2><%=rootCause.getMessage()%><%

            } else {
                throw e;
            }
        } else {
            throw e;
        }
    }


%>
<hr/>
<br/>
<br/>
<br/>
<br/>
<br/>


<h2>Report variants:</h2>

                The links below can be used to load different
                report vaiants by using request parameters

All of the links below filter the data for project "project1"

<h3>Here are some examples on filtering the date using a request parameter</h3>
<a href="<%=request.getRequestURL().toString()%>?START=01-01-2006&END=01-01-2006&TYPE=PROJECT&PROJECT=project1">
    filtered for date is 01-01-2006</a>
<br/>
<a href="<%=request.getRequestURL().toString()%>?START=01-01-2005&END=12-31-2005&TYPE=PROJECT&PROJECT=project1">
    filtered for year 2005</a>
<br/>
                Multivalued filter:
<a href="<%=request.getRequestURL().toString()%>?START=01-01-2005&END=06-30-2005&START=01-01-2006&END=06-30-2006&TYPE=PROJECT&PROJECT=project1">
    filtered for first half of 2005 and first half of 2006</a>
<br/>

<h3>Here are some examples on report category switching using a request parameter</h3>
<a href="<%=request.getRequestURL().toString()%>?CATEGORY=TIME&TYPE=PROJECT&PROJECT=project1">
    category TIME</a>
<br/>
<a href="<%=request.getRequestURL().toString()%>?CATEGORY=TIMEEXPENSE&TYPE=PROJECT&PROJECT=project1">
    category TIMEEXPENSE</a>
<br/>
</body>
</html>