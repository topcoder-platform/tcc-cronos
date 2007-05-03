/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.servicedetail.accuracytests;

import java.util.Date;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.project.ProjectWorkerFilterFactory;
import com.topcoder.timetracker.project.StringMatchType;

/**
 * Mock for <code>ProjectWorkerFilterFactory</code>.
 *
 * @author KLW
 * @version 1.1
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
    public Filter createPayRateFilter(double rangeStart, double rangeEnd) {
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
     * @param userId
     *            not used
     *
     * @return null
     */
    public Filter createUserIdFilter(long userId) {
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
     * @param matchType
     *            not used
     * @param s
     *            not used
     *
     * @return null
     */
    public Filter createCreationUserFilter(StringMatchType matchType, String s) {
        return null;
    }

    /**
     * Mock method.
     *
     * @param matchType
     *          not used
     * @param s
     *          not used
     * @return null
     */
    public Filter createModificationUserFilter(StringMatchType matchType, String s){
       return null;
    }
}
