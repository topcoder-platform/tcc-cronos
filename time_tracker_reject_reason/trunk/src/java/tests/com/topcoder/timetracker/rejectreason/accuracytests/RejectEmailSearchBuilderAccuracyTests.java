/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason.accuracytests;

import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.BetweenFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.GreaterThanOrEqualToFilter;
import com.topcoder.search.builder.filter.LessThanOrEqualToFilter;
import com.topcoder.search.builder.filter.LikeFilter;
import com.topcoder.search.builder.filter.OrFilter;

import com.topcoder.timetracker.rejectreason.RejectEmailDAO;
import com.topcoder.timetracker.rejectreason.RejectEmailSearchBuilder;

import junit.framework.TestCase;

import java.sql.Date;


/**
 * <p>
 * Accuracy tests for <code>RejectEmailSearchBuilder</code>.
 * </p>
 *
 * @author kzhu
 * @version 3.2
 */
public class RejectEmailSearchBuilderAccuracyTests extends TestCase {
    /** Represents private instance used for test. */
    private RejectEmailSearchBuilder builder = null;

    /**
     * Setup the environment.
     */
    protected void setUp() {
        builder = new RejectEmailSearchBuilder();
    }

    /**
     * <p>
     * Accuracy test for Constructor.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testConstructorAccuracy() throws Exception {
        assertNotNull("Instance should be created.", builder);
    }

    /**
     * <p>
     * Accuracy test for method <code>andFilter</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testandFilter1Accuracy() throws Exception {
        Filter filter = builder.and(builder.createdByUserFilter("foo"), builder.hasCompanyIdFilter(1));

        assertTrue("The filter created should be of type AndFilter.", filter instanceof AndFilter);

        assertEquals("The and filter should contain 2 sub filters.", 2, ((AndFilter) filter).getFilters().size());
    }

    /**
     * <p>
     * Accuracy test for method <code>andFilter</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testandFilterAccuracy() throws Exception {
        Filter[] filters = {
                builder.createdByUserFilter("foo"), builder.modifiedByUserFilter("foo"), builder.hasBodyFilter("body")
        };

        Filter filter = builder.and(filters);

        assertTrue("The filter created should be of type AndFilter.", filter instanceof AndFilter);

        assertEquals("The and filter should contain 3 sub filters.", 3, ((AndFilter) filter).getFilters().size());
    }

    /**
     * <p>
     * Accuracy test for method <code>orFilter</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testorFilter1Accuracy() throws Exception {
        Filter filter = builder.or(builder.createdByUserFilter("foo"), builder.hasCompanyIdFilter(1));

        assertTrue("The filter created should be of type OrFilter.", filter instanceof OrFilter);

        assertEquals("The or filter should contain 2 sub filters.", 2, ((OrFilter) filter).getFilters().size());
    }

    /**
     * <p>
     * Accuracy test for method <code>orFilter</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testorFilterAccuracy() throws Exception {
        Filter[] filters = {
                builder.createdByUserFilter("foo"), builder.modifiedByUserFilter("foo"), builder.hasBodyFilter("body")
        };

        Filter filter = builder.or(filters);

        assertTrue("The filter created should be of type OrFilter.", filter instanceof OrFilter);

        assertEquals("The or filter should contain 3 sub filters.", 3, ((OrFilter) filter).getFilters().size());
    }

    /**
     * <p>
     * Accuracy test for method <code>notFilter</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testnotFilterAccuracy() throws Exception {
        assertNotNull("The not filter should be return.", builder.not(builder.hasCompanyIdFilter(1)));
    }

    /**
     * <p>
     * Accuracy test for method <code>hasCompanyIdFilter</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testhasCompanyIdFilterAccuracy() throws Exception {
        EqualToFilter filter = (EqualToFilter) builder.hasCompanyIdFilter(1);

        assertEquals("The filter should have the right name.", RejectEmailDAO.SEARCH_COMPANY_ID, filter.getName());
    }

    /**
     * <p>
     * Accuracy test for method <code>hasDescription</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testhasDescriptionAccuracy() throws Exception {
        LikeFilter filter = (LikeFilter) builder.hasBodyFilter("body.");

        assertEquals("The filter should have the right name.", RejectEmailDAO.SEARCH_BODY, filter.getName());
    }

    /**
     * <p>
     * Accuracy test for method <code>createdWithInDateRangeFilter</code>. With both upper and lower bound.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreatedWithInDateRangeFilterAccuracy1()
        throws Exception {
        BetweenFilter filter = (BetweenFilter) builder.createdWithinDateRangeFilter(new Date(1), new Date(2));

        assertEquals("The filter should have the right name.", RejectEmailDAO.SEARCH_CREATED_DATE, filter.getName());
        assertTrue("The lower bound should be inclusive.", filter.isLowerInclusive());
        assertTrue("The upper bound should be inclusive.", filter.isUpperInclusive());

        assertEquals("The lower bound should be Date 1", 1, ((Date) filter.getLowerThreshold()).getTime());
        assertEquals("The upper bound should be Date 2", 2, ((Date) filter.getUpperThreshold()).getTime());
    }

    /**
     * <p>
     * Accuracy test for method <code>createdWithinDateRangeFilter</code>. With only lower bound.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreatedWithinDateRangeFilterAccuracy2()
        throws Exception {
        GreaterThanOrEqualToFilter filter = (GreaterThanOrEqualToFilter) builder.createdWithinDateRangeFilter(new Date(
                    1), null);

        assertEquals("The filter should have the right name.", RejectEmailDAO.SEARCH_CREATED_DATE, filter.getName());
        assertTrue("The lower bound should be inclusive.", filter.isLowerInclusive());

        assertEquals("The lower bound should be Date 1", 1, ((Date) filter.getLowerThreshold()).getTime());
    }

    /**
     * <p>
     * Accuracy test for method <code>createdWithinDateRangeFilter</code>. With only upper bound.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreatedWithinDateRangeFilterAccuracy3()
        throws Exception {
        LessThanOrEqualToFilter filter = (LessThanOrEqualToFilter) builder.createdWithinDateRangeFilter(null,
                new Date(2));

        assertEquals("The filter should have the right name.", RejectEmailDAO.SEARCH_CREATED_DATE, filter.getName());
        assertTrue("The upper bound should be inclusive.", filter.isUpperInclusive());

        assertEquals("The upper bound should be Date 2", 2, ((Date) filter.getUpperThreshold()).getTime());
    }

    /**
     * <p>
     * Accuracy test for method <code>createdByUserFilter</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testcreatedByUserFilterAccuracy() throws Exception {
        EqualToFilter filter = (EqualToFilter) builder.createdByUserFilter("user1");

        assertEquals("The filter should have the right name.", RejectEmailDAO.SEARCH_CREATED_USER, filter.getName());
    }

    /**
     * <p>
     * Accuracy test for method <code>modifiedWithInDateRangeFilter</code>. With both upper and lower bound.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testmodifiedWithInDateRangeFilterAccuracy1()
        throws Exception {
        BetweenFilter filter = (BetweenFilter) builder.modifiedWithinDateRangeFilter(new Date(1), new Date(2));

        assertEquals("The filter should have the right name.", RejectEmailDAO.SEARCH_MODIFICATION_DATE,
            filter.getName());
        assertTrue("The lower bound should be inclusive.", filter.isLowerInclusive());
        assertTrue("The upper bound should be inclusive.", filter.isUpperInclusive());

        assertEquals("The lower bound should be Date 1", 1, ((Date) filter.getLowerThreshold()).getTime());
        assertEquals("The upper bound should be Date 2", 2, ((Date) filter.getUpperThreshold()).getTime());
    }

    /**
     * <p>
     * Accuracy test for method <code>modifiedWithinDateRangeFilter</code>. With only lower bound.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testmodifiedWithinDateRangeFilterAccuracy2()
        throws Exception {
        GreaterThanOrEqualToFilter filter = (GreaterThanOrEqualToFilter) builder.modifiedWithinDateRangeFilter(new Date(
                    1), null);

        assertEquals("The filter should have the right name.", RejectEmailDAO.SEARCH_MODIFICATION_DATE,
            filter.getName());
        assertTrue("The lower bound should be inclusive.", filter.isLowerInclusive());

        assertEquals("The lower bound should be Date 1", 1, ((Date) filter.getLowerThreshold()).getTime());
    }

    /**
     * <p>
     * Accuracy test for method <code>modifiedWithinDateRangeFilter</code>. With only upper bound.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testmodifiedWithinDateRangeFilterAccuracy3()
        throws Exception {
        LessThanOrEqualToFilter filter = (LessThanOrEqualToFilter) builder.modifiedWithinDateRangeFilter(null,
                new Date(2));

        assertEquals("The filter should have the right name.", RejectEmailDAO.SEARCH_MODIFICATION_DATE,
            filter.getName());
        assertTrue("The upper bound should be inclusive.", filter.isUpperInclusive());

        assertEquals("The upper bound should be Date 2", 2, ((Date) filter.getUpperThreshold()).getTime());
    }

    /**
     * <p>
     * Accuracy test for method <code>modifiedByUserFilter</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testmodifiedByUserFilterAccuracy() throws Exception {
        EqualToFilter filter = (EqualToFilter) builder.modifiedByUserFilter("user1");

        assertEquals("The filter should have the right name.", RejectEmailDAO.SEARCH_MODIFICATION_USER,
            filter.getName());
    }
}
