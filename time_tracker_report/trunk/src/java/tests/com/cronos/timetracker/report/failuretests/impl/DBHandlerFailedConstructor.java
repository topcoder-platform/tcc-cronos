/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.report.failuretests.impl;

import com.cronos.timetracker.report.Report;
import com.cronos.timetracker.report.ReportConfiguration;
import com.cronos.timetracker.report.dbhandler.DBHandler;
import com.cronos.timetracker.report.dbhandler.ReportSQLException;

import java.sql.ResultSet;

/**
 * <p>An implementation of {@link com.cronos.timetracker.report.dbhandler.DBHandler} interface to be utilized for testing purposes.</p>
 *
 * @author  isv
 * @version 2.0
 */
public class DBHandlerFailedConstructor implements DBHandler {
    
    public DBHandlerFailedConstructor() {
        throw new RuntimeException();
    }

    public ResultSet getReportData(ReportConfiguration config) throws ReportSQLException {
        return null;
    }

    public void release(ResultSet resultset) throws ReportSQLException {
    }
}
