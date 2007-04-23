/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.servicedetail;

import java.util.Date;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.project.ProjectWorkerFilterFactory;

/**
 * Mock for <code>ProjectWorkerFilterFactory</code>.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockProjectWorkerFilterFactory implements ProjectWorkerFilterFactory {

    /**
     * Mock method.
     *
     * @param rangeStart
     *            not used
     * @param rangeEnd
     *            not used
     *
     * @return null
     */
    public Filter createEndDateFilter(Date rangeStart, Date rangeEnd) {
        return null;
    }

    /**
     * Mock method.
     *
     * @param rangeStart
     *            not used
     * @param rangeEnd
     *            not used
     *
     * @return null
     */
    public Filter createPayRateFilter(int rangeStart, int rangeEnd) {
        return null;
    }

    /**
     * Mock method.
     *
     * @param projectId
     *            project id
     *
     * @return project filter with id projectId
     */
    public Filter createProjectIdFilter(long projectId) {
        return new MockProjectFilter(projectId);
    }

    /**
     * Mock method.
     *
     * @param rangeStart
     *            not used
     * @param rangeEnd
     *            not used
     *
     * @return null
     */
    public Filter createStartDateFilter(Date rangeStart, Date rangeEnd) {
        return null;
    }

    /**
     * Mock method.
     *
     * @param rangeStart
     *            not used
     * @param rangeEnd
     *            not used
     *
     * @return null
     */
    public Filter createCreationDateFilter(Date rangeStart, Date rangeEnd) {
        return null;
    }

    /**
     * Mock method.
     *
     * @param username
     *            not used
     *
     * @return null
     */
    public Filter createCreationUserFilter(String username) {
        return null;
    }

    /**
     * Mock method.
     *
     * @param rangeStart
     *            not used
     * @param rangeEnd
     *            not used
     *
     * @return null
     */
    public Filter createModificationDateFilter(Date rangeStart, Date rangeEnd) {
        return null;
    }

    /**
     * Mock method.
     *
     * @param username
     *            not used
     *
     * @return null
     */
    public Filter createModificationUserFilter(String username) {
        return null;
    }

}
