<%@ taglib uri="com.topcoder.timetracker.report" prefix="report" %>
<HTML>
<TITLE> Time Tracker Report </TITLE>
<BODY>
<!%-- Render the Employee Expense Report.
     The namespace specifies to use Default Configuration. -->
<report:reportdisplay
    namespace="com.topcoder.timetracker.report.DefaultConfiguration"
    type="Report_Type"
    category="Report_Category"
    employeeFilter="Filter1"
    startDateFilter="StartDate_Filter"
    endDateFilter="EndDate_Filter"
    billableFilter="Filter21"/>
</BODY>
</HTML>
