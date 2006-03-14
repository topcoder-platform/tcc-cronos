<%@ page import="com.topcoder.timetracker.report.ReportException" %>
<%@ page import="javax.servlet.jsp.JspException" %>
<%@ taglib uri="com.topcoder.timetracker.report" prefix="ct" %>
<!%--
 Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 -->

<html>

<body>
<h1>Time Tracker report</h1>
<%

    try {
%>
<hr/>
<ct:reportdisplay namespace="com.topcoder.timetracker.report.DefaultConfiguration"
                  type="TYPE"
                  category="CATEGORY"
                  clientFilter="CLIENT"
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

<h2>Test data</h2>
Test data for the demo can be loaded into the database
by calling the ant target 'fillTestData' on the provided
build script in the component root directory.
<br/>
<b>WARNING:</b> The database will be cleared before inserting
                the data, so all data that currently exists in
                the database schema will be lost.
                The database
                can be cleared using the ant target 'clearDatabase'.

<h2>Report variants:</h2>

                The links below can be used to load different
                report vaiants by using request parameters

<a href="<%=request.getRequestURL().toString()%>">
    with default filter, type and category defined in application context</a>
<br/>
                , which is type:CLIENT, category:EXPENSE, client filter:"client1",


<h3>Here are some examples on filtering the client using a request parameter</h3>
<a href="<%=request.getRequestURL().toString()%>?CLIENT=client1">
    filtered for client1</a>
<br/>
<a href="<%=request.getRequestURL().toString()%>?CLIENT=client2">
    filtered for client2</a>
<br/>
                Multivalued filter:
<a href="<%=request.getRequestURL().toString()%>?CLIENT=client2&CLIENT=client1">
    filtered for (client1 OR client2)</a>
<br/>

<h3>Here are some examples on filtering the date using a request parameter</h3>
<a href="<%=request.getRequestURL().toString()%>?START=01-01-2006&END=01-01-2006">
    filtered for date is 01-01-2006</a>
<br/>
<a href="<%=request.getRequestURL().toString()%>?START=01-01-2005&END=12-31-2005">
    filtered for year 2005</a>
<br/>
                Multivalued filter:
<a href="<%=request.getRequestURL().toString()%>?START=01-01-2005&END=06-30-2005&START=01-01-2006&END=06-30-2006">
    filtered for first half of 2005 and first half of 2006</a>
<br/>

<h3>Here are some examples on report category switching using a request parameter</h3>
<a href="<%=request.getRequestURL().toString()%>?CATEGORY=TIME">
    category TIME</a>
<br/>
<a href="<%=request.getRequestURL().toString()%>?CATEGORY=TIMEEXPENSE">
    category TIMEEXPENSE</a>
<br/>

<h3>Here are some examples on report type switching using a request parameter</h3>
<a href="<%=request.getRequestURL().toString()%>?TYPE=EMPLOYEE&EMPLOYEE=ivern">type EMPLOYEE</a>
<br/>
<a href="project.jsp?TYPE=PROJECT&PROJECT=project1">Project Reports are not displayable with this page, as the application-global
                      Client filter is not allowed for this report type :-(, so go to this page for Project reports</a>
</body>
</html>