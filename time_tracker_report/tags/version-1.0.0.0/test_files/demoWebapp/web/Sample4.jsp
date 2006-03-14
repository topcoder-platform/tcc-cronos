<%@ taglib uri="com.topcoder.timetracker.report" prefix="report" %>
<HTML>
<TITLE> Time Tracker Report </TITLE>

<BODY>
<!%-- Render the Client TimeExpense Report.
     The namespace specifies to use Custom Configuration. -->
<report:reportdisplay
    namespace="com.topcoder.timetracker.report.CustomConfiguration"
    type="Type"
    category="Category"
    clientFilter="MyFilter1"
    billableFilter="Billable"/>
</BODY>
</HTML>
