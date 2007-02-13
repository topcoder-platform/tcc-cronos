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


/**
 * ExpenseReport class is a subclass of AbstractReport and is responsible for generating the ReportData and organizing
 * it in the HTML Table Format for the Expense Category Reports. This class is also thread safe as its instance
 * variables are not modifiable.
 *
 * @author fastprogrammer, traugust
 * @version 1.0
 */
public class ExpenseReport extends AbstractReport {

    /**
     * Creates a new ExpenseReport.
     *
     * @throws ReportConfigurationException in case any errors are encountered during configuration of this instance
     *                                      from {@link com.topcoder.util.config.ConfigManager}
     */
    public ExpenseReport() throws ReportConfigurationException {
        super(ReportCategory.EXPENSE, "HTML");
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

        // the aggregator that is used to calculate the cumulated amount
        // of all rows iterated by the resultSet that is rendered by HTMLRenderUtil
        final HTMLRenderUtil.Aggregator totalAmount = new HTMLRenderUtil.Aggregator(Column.AMOUNT);

        //renders the HTML table and cumulates the amount
        final String renderedTable = HTMLRenderUtil.renderTable(config, getDBHandlerFactory(),
            new HTMLRenderUtil.Aggregator[]{totalAmount});
        final StringBuffer ret = new StringBuffer(renderedTable);

        //append the amount line
        ret.append("<BR/><BR/><CENTER>Total Amount: ");
        ret.append(totalAmount.getCurrentValue());
        ret.append("</CENTER>");

        return ret.toString();
    }
}
