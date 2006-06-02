/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.report.dbhandler;

import com.cronos.timetracker.report.ReportConfiguration;
import com.cronos.timetracker.report.ReportConfigurationException;

import java.sql.ResultSet;


/**
 * This interface defines the basic behavior of a DBHandler. The DBHandler handles the Database operations like fetching
 * of Data from the DataBase, managing the Connection to the database, setting and doing the necessary conversion for
 * the parameters values used in the queries, creating the queries dynamically for the reports, executing the queries
 * against the target Database and presenting the retrieved data in the form of a {@link ResultSet} object.
 * <p/>
 * The implementation of this class are required to handle the above mentioned operation in a manner specific to the
 * Database is abstracts.
 * <p/>
 * The implementors of this class are supposed to be thread safe.
 *
 * @author fastprogrammer, traugust
 * @version 1.0
 */
public interface DBHandler {
    /**
     * Fetches the data for the report defined by the ReportConfiguration and returns the Fetched data in the form of a
     * ResultSet. This method is supposed to handle the connection, the dynamic generation of queries for the Report,
     * setting the parameter values from the filter information specified and executing the query and fetching the data
     * from the Database.
     *
     * @param config the ReportConfiguration which specifies all the parameters required to fetch the data for the
     *               report.
     *
     * @return the {@link java.sql.ResultSet} returned from the database query
     *
     * @throws NullPointerException         if config is <tt>null</tt>
     * @throws ReportSQLException           if there are any errors during Database access
     * @throws ReportConfigurationException in case the report configuration fails due to invalid or missing
     *                                      configuration data
     */
    public ResultSet getReportData(ReportConfiguration config) throws ReportSQLException,
        ReportConfigurationException;

    /**
     * Releases the {@link ResultSet} along with the additional resources from the Database corresponding to this
     * ResultSet(like the {@link java.sql.Connection} used to fetch the ResultSet).
     *
     * @param resultset the {@link ResultSet} to be released
     *
     * @throws NullPointerException if the argument resultset is <tt>null</tt>
     * @throws ReportSQLException   if there are errors from the Database during the release operation
     */
    public void release(ResultSet resultset) throws ReportSQLException;
}


