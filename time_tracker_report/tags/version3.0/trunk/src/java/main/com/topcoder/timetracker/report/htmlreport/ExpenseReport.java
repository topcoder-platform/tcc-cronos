/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report.htmlreport;

import java.util.Iterator;
import java.util.List;

import com.topcoder.timetracker.report.AbstractReport;
import com.topcoder.timetracker.report.Column;
import com.topcoder.timetracker.report.ColumnDecorator;
import com.topcoder.timetracker.report.ReportCategory;
import com.topcoder.timetracker.report.ReportConfiguration;
import com.topcoder.timetracker.report.ReportConfigurationException;
import com.topcoder.timetracker.report.ReportException;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;


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
        //ReportType type = config.getType();
        //config.getFilters();

        //renders the HTML table and cumulates the amount
        final String renderedTable = HTMLRenderUtil.renderTable(config, getDBHandlerFactory(),
            new HTMLRenderUtil.Aggregator[]{totalAmount});
        if (renderedTable.length() == 0) {
        	return renderedTable;
        }

        final StringBuffer ret = new StringBuffer();
        //ret.append("<CENTER>");
        //ret.append(config.getHeader());
        //ret.append("</CENTER>");
        ret.append(renderedTable);

        String amtPrefix = null;
        try {
            String prefixkey = "PREFIX_COLUMN_AMOUNT";
            amtPrefix = ConfigManager.getInstance().getString(config.getNamespace(), prefixkey);
        }
        catch (UnknownNamespaceException e) {
            // ignore
        }
        
        //append the amount line
        List styles = config.getStatisticStyles();
//        ret.append("<TABLE class=\"results_table\" width=\"100%\" " +
//        		"bgColor=#ffffff><THEAD><TR>");
//        final List columnDecorators = config.getColumnDecorators();
//        for (Iterator itor = columnDecorators.iterator(); itor.hasNext();) {
//        	final ColumnDecorator columnDecorator = (ColumnDecorator) itor.next();
//        	ret.append("<TH>");
//        	ret.append("<" + columnDecorator.getStyle() + ">");
//        	ret.append("</TH>");
//        }
//        ret.append("</TR></THEAD><TBODY><tr><td ");
        ret.append("<tr><td ");
        ret.append(styles.get(0));
        ret.append(">Total Amount: </td><td " );
        ret.append(styles.get(1));
        ret.append(">");
        if (amtPrefix != null && amtPrefix.length() > 0) ret.append(amtPrefix);
        ret.append(totalAmount.getCurrentValue());
        ret.append("</td><td ");
        ret.append(styles.get(2));
        ret.append("></td></tr></TBODY></Table>");

        return ret.toString();
    }
}
