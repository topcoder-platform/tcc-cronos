/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report.failuretests.impl;

import com.topcoder.timetracker.report.Report;
import com.topcoder.timetracker.report.ReportConfiguration;
import com.topcoder.timetracker.report.dbhandler.DBHandler;
import com.topcoder.timetracker.report.dbhandler.ReportSQLException;

import java.sql.ResultSet;

/**
 * <p>An implementation of {@link com.topcoder.timetracker.report.dbhandler.DBHandler}
 * interface to be utilized for testing purposes.</p>
 *
 * @author  isv
 * @version 2.0
 */
public class DBHandlerNoDefaultConstructor implements DBHandler {

    public DBHandlerNoDefaultConstructor(String namespace) {
    }

    public ResultSet getReportData(ReportConfiguration config) throws ReportSQLException {
        return null;
    }

    public void release(ResultSet resultset) throws ReportSQLException {
    }
}
