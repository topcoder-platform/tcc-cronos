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
<!%-- Render the Project Time Report.
     The namespace specifies to use Custom Configuration. -->
<report:reportdisplay
    namespace="com.cronos.timetracker.report.CustomConfiguration"
    type="Report_Type1"
    category="Report_Category1"
    projectFilter="Project_Filter"/>
</BODY>
</HTML>
