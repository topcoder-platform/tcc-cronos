/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report.failuretests.impl;

import com.topcoder.timetracker.report.dbhandler.DBHandler;
import com.topcoder.timetracker.report.dbhandler.ReportSQLException;
import com.topcoder.timetracker.report.Report;
import com.topcoder.timetracker.report.ReportConfiguration;

import java.sql.ResultSet;

/**
 * <p>An implementation of {@link com.topcoder.timetracker.report.dbhandler.DBHandler} interface to be utilized for testing purposes.</p>
 *
 * @author  isv
 * @version 1.0
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
