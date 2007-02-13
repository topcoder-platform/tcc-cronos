/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package demo;

import com.topcoder.timetracker.report.ReportCategory;
import com.topcoder.timetracker.report.ReportType;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;


/**
 * This is a Servlet whis represents a placeholder for the application logic that would initialize the web application,
 * so that Time Tracker Report is configured properly.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DummyServlet extends HttpServlet {
    /**
     * In this init method the configuration is loaded into {@link ConfigManager} and the default configuration is set
     * in application context. This method is called once when the web application is loaded, as the
     * <tt>load-on-startup</tt> value has been defined for this Servlet in the <tt>web.xml</tt>.
     *
     * @param sc the config to obtain the application context from
     */
    public void init(final ServletConfig sc) {
        // Here the config file is loaded
        try {
            ConfigManager.getInstance().add("Time_Tracker_Report.xml");
        } catch (ConfigManagerException e) {
            e.printStackTrace();
        }

        ServletContext servletContext = sc.getServletContext();
        // This is an exaple for setting report configuration in the application context,
        // These values will be used by all report display tags that have a
        // tag attribute type with value TYPE
        // tag attribute category with value CATEGORY
        // tag attribute clientFilter with value CLIENT
        //
        // if no overriding request attributes with name
        // TYPE,CATEGORY or CLIENT have been specified
        servletContext.setAttribute("TYPE", ReportType.CLIENT.getType());
        servletContext.setAttribute("CATEGORY", ReportCategory.EXPENSE.getCategory());
        servletContext.setAttribute("CLIENT", "client1");

    }

}
