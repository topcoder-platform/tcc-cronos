/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.servicedetail.stresstests;

import java.util.Date;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.project.ProjectWorkerFilterFactory;
import com.topcoder.timetracker.project.StringMatchType;

/**
 * This is a mock implementation of <code>ProjectWorkerFilterFactory</code>. It is only used in stress tests.
 *
 * @author vividmxx
 * @version 3.2
 */
public class MockProjectWorkerFilterFactory implements ProjectWorkerFilterFactory {

    /**
     * The default constructor.
     */
    public MockProjectWorkerFilterFactory() {
    }

    /**
     * Returns a <code>Filter</code>.
     *
     * @param projectId
     *            the project id
     * @return a <code>Filter</code>
     */
    public Filter createProjectIdFilter(long projectId) {
        return new EqualToFilter("projectId", new Long(projectId));
    }

    /**
     * Returns null.
     *
     * @param rangeStart
     *            ignored
     * @param rangeEnd
     *            ignored
     * @return null
     */
    public Filter createStartDateFilter(Date rangeStart, Date rangeEnd) {
        return null;
    }

    /**
     * Returns null.
     *
     * @param rangeStart
     *            ignored
     * @param rangeEnd
     *            ignored
     * @return null
     */
    public Filter createEndDateFilter(Date rangeStart, Date rangeEnd) {
        return null;
    }

    /**
     * Returns null.
     *
     * @param rangeStart
     *            ignored
     * @param rangeEnd
     *            ignored
     * @return null
     */
    public Filter createPayRateFilter(double rangeStart, double rangeEnd) {
        return null;
    }

    /**
     * Returns null.
     *
     * @param rangeStart
     *            ignored
     * @param rangeEnd
     *            ignored
     * @return null
     */
    public Filter createCreationDateFilter(Date rangeStart, Date rangeEnd) {
        return null;
    }

    /**
     * Returns null.
     *
     * @param rangeStart
     *            ignored
     * @param rangeEnd
     *            ignored
     * @return null
     */
    public Filter createModificationDateFilter(Date rangeStart, Date rangeEnd) {
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
