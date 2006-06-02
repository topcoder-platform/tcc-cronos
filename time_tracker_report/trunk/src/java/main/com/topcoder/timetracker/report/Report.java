/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.report;

import java.util.List;


/**
 * The Report interface defines the basic behavior of the report. Whenever the Report is executed by calling the {@link
 * #execute(String, ReportType, java.util.List)} method, the data for the Report is gathered based on the parameters
 * passed. The gathered data is then formatted based on the Format (see {@link #getFormat()}) of the Report. The
 * namespace parameter defines the namespace from which the properties for the Report are fetched from the configuration
 * file. The formatted Report Data is the then returned as a String on the completion of the execution of the {@link
 * #execute(String, ReportType, java.util.List)} method.
 * <p/>
 * The implementations of this interface are required to be thread-safe.
 *
 * @author fastprogrammer, traugust
 * @version 1.0
 */
public interface Report {
    /**
     * Gathers the report data, based on the arguments passed and formats the report data as required and returns the
     * formatted report data as a String.
     *
     * @param namespace the namespace from which the configuration properties for the Report are read.
     * @param type      the type of the Report
     * @param filters   the Filters to be used during the data fetch for the report.
     * @param sortColumns the sort by clause.
     *
     * @return a String containing the formatted Report data.
     *
     * @throws NullPointerException     if any of the arguments except by sortColumns to the method is <tt>null</tt>
     * @throws ReportException          if there are any errors during the processing of the Report or the given filters
     *                                  list contains illegal filters for this report or does not contain the manadatory
     *                                  filter for the given report type
     * @throws IllegalArgumentException if the value for the String "namespace" is an empty (trim'd) String or the
     *                                  given filters list contains a <tt>null</tt> value
     */
    public String execute(String namespace, ReportType type, List filters, String sortColumns) throws ReportException;

    /**
     * Returns the category of the Report.
     *
     * @return the non-<tt>null</tt> category of the Report
     */
    public ReportCategory getCategory();

    /**
     * Returns a String representing the format in which the Report will render the data.
     *
     * @return the non-<tt>null</tt>,non-empty format in which the Report will render the data
     */
    public String getFormat();
}


