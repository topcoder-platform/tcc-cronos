<%@ taglib uri="com.topcoder.timetracker.report" prefix="report" %>
<HTML>
<TITLE> Time Tracker Report </TITLE>
<BODY>
<!%-- Render the Project Time Report.
     The namespace specifies to use Custom Configuration. -->
<report:reportdisplay
    namespace="com.topcoder.timetracker.report.CustomConfiguration"
    type="Report_Type1"
    category="Report_Category1"
    projectFilter="Project_Filter"/>
</BODY>
</HTML>
