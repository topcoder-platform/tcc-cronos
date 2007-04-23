/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.servicedetail.accuracytests;

import java.util.Map;

import com.topcoder.search.builder.ValidationResult;
import com.topcoder.search.builder.filter.Filter;

/**
 * Mock for <code>Filter</code>.
 *
 * @author KLW
 * @version 1.1
 */
public class MockProjectFilter implements Filter {

    /** project id. */
    private long projectId;

    /**
     * Mock constructor.
     *
     * @param projectId
     *            id of the project
     */
    public MockProjectFilter(long projectId) {
        this.projectId = projectId;
    }

    /**
     * Mock method.
     *
     * @return 0
     */
    public int getFilterType() {
        return 0;
    }

    /**
     * Mock method.
     *
     * @param arg0
     *            not used
     * @param arg1
     *            not used
     *
     * @return null
     */
    public ValidationResult isValid(Map arg0, Map arg1) {
        return null;
    }

    /**
     * Mock method.
     *
     * @return null
     */
    public Object clone() {
        return null;
    }

    /**
     * Mock method.
     *
     * @return projectId
     */
    public long getProjectId() {
        return projectId;
    }

}
