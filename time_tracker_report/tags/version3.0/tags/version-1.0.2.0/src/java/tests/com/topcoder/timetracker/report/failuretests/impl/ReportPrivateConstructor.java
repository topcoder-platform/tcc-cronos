/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report.failuretests.impl;

import com.topcoder.timetracker.report.Report;
import com.topcoder.timetracker.report.ReportType;
import com.topcoder.timetracker.report.ReportException;
import com.topcoder.timetracker.report.ReportCategory;

import java.util.List;

/**
 * <p>An implementation of {@link com.topcoder.timetracker.report.Report} interface to be utilized for testing purposes.</p>
 *
 * @author  isv
 * @version 1.0
 */
public class ReportPrivateConstructor implements Report {

    private ReportPrivateConstructor() {
    }

    public String execute(String namespace, ReportType type, List filters) throws ReportException {
        return null;
    }

    public ReportCategory getCategory() {
        return null;
    }

    public String getFormat() {
        return null;
    }
}
