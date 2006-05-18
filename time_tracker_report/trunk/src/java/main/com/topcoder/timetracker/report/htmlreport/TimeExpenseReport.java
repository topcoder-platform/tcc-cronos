/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report.htmlreport;

import com.topcoder.timetracker.report.AbstractReport;
import com.topcoder.timetracker.report.Column;
import com.topcoder.timetracker.report.ReportCategory;
import com.topcoder.timetracker.report.ReportConfiguration;
import com.topcoder.timetracker.report.ReportConfigurationException;
import com.topcoder.timetracker.report.ReportException;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;


/**
 * TimeExpenseReport class is a subclass of AbstractReport and is responsible for generating the ReportData and
 * organizing it in the HTML Table Format for the TimeExpense Category Reports.
 *
 * @author fastprogrammer, traugust
 * @version 1.0
 */
public class TimeExpenseReport extends AbstractReport {

    /**
     * Creates a new TimeExpenseReport.
     *
     * @throws ReportConfigurationException in case any errors are encountered during configuration of this instance
     *                                      from {@link com.topcoder.util.config.ConfigManager}
     */
    public TimeExpenseReport() throws ReportConfigurationException {
        super(ReportCategory.TIMEEXPENSE, "HTML");
    }

    /**
     * Executes the report and returns the ReportData as a HTML table.
     *
     * @param config the ReportConfiguration for the report
     *
     * @return the ReportData as a HTML table
     *
     * @throws NullPointerException if the config passed is <tt>null</tt>
     * @throws ReportException      if there are problems during the Reports execution
     */
    public String executeReport(final ReportConfiguration config) throws ReportException {
        if (config == null) {
            throw new NullPointerException("The parameter named [config] was null.");
        }

        // the aggregators that are used to calculate the cumulated amount and hours
        // of all rows iterated by the resultSet that is rendered by HTMLRenderUtil
        final HTMLRenderUtil.Aggregator totalAmount = new HTMLRenderUtil.Aggregator(Column.AMOUNT);
        final HTMLRenderUtil.Aggregator totalHours = new HTMLRenderUtil.Aggregator(Column.HOURS);

        //renders the HTML table and cumulates the amount
        final String renderedTable = HTMLRenderUtil.renderTable(config, getDBHandlerFactory(),
            new HTMLRenderUtil.Aggregator[]{totalAmount, totalHours});
        final StringBuffer ret = new StringBuffer(renderedTable);

        //append the hours line
        ret.append("<BR/><BR/><CENTER>Total Hours: ");
        ret.append(totalHours.getCurrentValue());
        ret.append("</CENTER>");

        String amtPrefix = null;
        try {
	        String prefixkey = "PREFIX_COLUMN_AMOUNT";
	        amtPrefix = ConfigManager.getInstance().getString(config.getNamespace(), prefixkey);
        }
        catch (UnknownNamespaceException e) {
        	// ignore
        }

        //append the amount line
        ret.append("<BR/><BR/><CENTER>Total Amount: ");
        ret.append(amtPrefix);
        ret.append(totalAmount.getCurrentValue());
        ret.append("</CENTER>");

        return ret.toString();
    }
}
