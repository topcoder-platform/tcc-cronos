<%@ taglib uri="com.cronos.timetracker.report" prefix="report" %>
<%@ page import="com.topcoder.util.config.*"
import ="java.util.*"%>
<%
        try {
			ConfigManager cm = ConfigManager.getInstance();
			for (Iterator itor = cm.getAllNamespaces(); itor.hasNext();) {
        			cm.removeNamespace((String) itor.next());
        		}
            cm.add("Time_Tracker_Report.xml");
        } catch (ConfigManagerException e) {
            e.printStackTrace();
        }
%>
<HTML>
<TITLE> Time Tracker Report </TITLE>
<BODY>
<!%-- Render the Employee Expense Report.
     The namespace specifies to use Default Configuration. -->
<report:reportdisplay
    namespace="com.cronos.timetracker.report.DefaultConfiguration"
    type="Report_Type"
    category="Report_Category"
    employeeFilter="Filter1"
    endDateFilter="EndDate_Filter"
	companyFilter="Company_Filter"
    billableFilter="Filter21"/>
</BODY>
</HTML>
