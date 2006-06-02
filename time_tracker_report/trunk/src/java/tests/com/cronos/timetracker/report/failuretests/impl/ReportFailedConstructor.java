/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.report.failuretests.impl;

import com.cronos.timetracker.report.Report;
import com.cronos.timetracker.report.ReportCategory;
import com.cronos.timetracker.report.ReportException;
import com.cronos.timetracker.report.ReportType;

import java.util.List;

/**
 * <p>An implementation of {@link com.cronos.timetracker.report.Report} interface to be utilized for testing purposes.</p>
 *
 * @author  isv
 * @version 2.0
 */
public class ReportFailedConstructor implements Report {

    public ReportFailedConstructor() throws Exception {
        throw new Exception();
    }

    public String execute(String namespace, ReportType type, List filters, String order) throws ReportException {
        return null;
    }

    public ReportCategory getCategory() {
        return null;
    }

    public String getFormat() {
        return null;
    }
}
