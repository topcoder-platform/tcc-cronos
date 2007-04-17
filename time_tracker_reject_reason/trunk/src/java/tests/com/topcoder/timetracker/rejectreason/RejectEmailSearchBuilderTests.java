/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason;

import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.BetweenFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.GreaterThanFilter;
import com.topcoder.search.builder.filter.GreaterThanOrEqualToFilter;
import com.topcoder.search.builder.filter.LessThanFilter;
import com.topcoder.search.builder.filter.LessThanOrEqualToFilter;
import com.topcoder.search.builder.filter.LikeFilter;
import com.topcoder.search.builder.filter.NotFilter;
import com.topcoder.search.builder.filter.OrFilter;

import junit.framework.TestCase;

import java.sql.Date;

import java.util.List;


/**
 * Unit test for <code>RejectEmailSearchBuilder</code> class. Instantiation tests and basic functionalities tests.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class RejectEmailSearchBuilderTests extends TestCase {
    /** A Filter instance for test. */
    private static LessThanFilter filter1 = new LessThanFilter("LessThan", new Integer(0));

    /** A Filter instance for test. */
    private static EqualToFilter filter2 = new EqualToFilter("EqualTo", new Long(0));

    /** A Filter instance for test. */
    private static GreaterThanFilter filter3 = new GreaterThanFilter("GreaterThan", new Integer(0));

    /** An array of Filter instance for test. */
    private static Filter[] filterArray = new Filter[] {filter1, filter2, filter3 };

    /** The RejectEmailSearchBuilder instance to be tested. */
    private RejectEmailSearchBuilder builder = new RejectEmailSearchBuilder();

    /**
     * Tests constructor, verifies the RejectEmail is instantiated correctly.
     */
    public void testConstructor() {
        assertNotNull("Unable to instantiate RejectEmailSearchBuilder.", builder);
    }

    /**
     * Tests and(Filter, Filter) method with a null argument. IllegalArgumentException should be thrown.
     */
    public void testAnd1_null1() {
        try {
            builder.and(null, filter2);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // Ok.
        }
    }

    /**
     * Tests and(Filter, Filter) method with a null argument. IllegalArgumentException should be thrown.
     */
    public void testAnd1_null2() {
        try {
            builder.and(filter1, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // Ok.
        }
    }

    /**
     * Tests and(Filter, Filter) method accuracy.
     */
    public void testAnd1_accuracy() {
        AndFilter filter = (AndFilter) builder.and(filter1, filter2);
        List filters = filter.getFilters();
        assertEquals("Filter is not created correctly.", 2, filters.size());
        assertEquals("Filter is not created correctly.", filter1, filters.get(0));
        assertEquals("Filter is not created correctly.", filter2, filters.get(1));
    }

    /**
     * Tests and(Filter[]) method with a null argument. IllegalArgumentException should be thrown.
     */
    public void testAnd2_null() {
        try {
            builder.and(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // Ok.
        }
    }

    /**
     * Tests and(Filter[]) method with an empty array. IllegalArgumentException should be thrown.
     */
    public void testAnd2_empty() {
        try {
            builder.and(new Filter[0]);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // Ok.
        }
    }

    /**
     * Tests and(Filter[]) method accuracy.
     */
    public void testAnd2_accuracy() {
        AndFilter filter = (AndFilter) builder.and(filterArray);
        List filters = filter.getFilters();
        assertEquals("Filter is not created correctly.", 3, filters.size());
        assertEquals("Filter is not created correctly.", filter1, filters.get(0));
        assertEquals("Filter is not created correctly.", filter2, filters.get(1));
        assertEquals("Filter is not created correctly.", filter3, filters.get(2));
    }

    /**
     * Tests or(Filter, Filter) method with a null argument. IllegalArgumentException should be thrown.
     */
    public void testOr1_null1() {
        try {
            builder.or(null, filter2);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // Ok.
        }
    }

    /**
     * Tests or(Filter, Filter) method with a null argument. IllegalArgumentException should be thrown.
     */
    public void testOr1_null2() {
        try {
            builder.or(filter1, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // Ok.
        }
    }

    /**
     * Tests or(Filter, Filter) method accuracy.
     */
    public void testOr1_accuracy() {
        OrFilter filter = (OrFilter) builder.or(filter1, filter2);
        List filters = filter.getFilters();
        assertEquals("Filter is not created correctly.", 2, filters.size());
        assertEquals("Filter is not created correctly.", filter1, filters.get(0));
        assertEquals("Filter is not created correctly.", filter2, filters.get(1));
    }

    /**
     * Tests or(Filter[]) method with a null argument. IllegalArgumentException should be thrown.
     */
    public void testOr2_null() {
        try {
            builder.or(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // Ok.
        }
    }

    /**
     * Tests or(Filter[]) method with an empty array. IllegalArgumentException should be thrown.
     */
    public void testOr2_empty() {
        try {
            builder.or(new Filter[0]);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // Ok.
        }
    }

    /**
     * Tests or(Filter[]) method accuracy.
     */
    public void testOr2_accuracy() {
        OrFilter filter = (OrFilter) builder.or(filterArray);
        List filters = filter.getFilters();
        assertEquals("Filter is not created correctly.", 3, filters.size());
        assertEquals("Filter is not created correctly.", filter1, filters.get(0));
        assertEquals("Filter is not created correctly.", filter2, filters.get(1));
        assertEquals("Filter is not created correctly.", filter3, filters.get(2));
    }

    /**
     * Tests not(Filter) method with a null argument. IllegalArgumentException should be thrown.
     */
    public void testNot_null() {
        try {
            builder.not(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // Ok.
        }
    }

    /**
     * Tests not(Filter) method accuracy.
     */
    public void testNot_accuracy() {
        NotFilter filter = (NotFilter) builder.not(filter3);
        GreaterThanFilter inFilter = (GreaterThanFilter) filter.getFilter();
        assertEquals("Filter is not created correctly.", filter3.getName(), inFilter.getName());
        assertEquals("Filter is not created correctly.", filter3.getValue(), inFilter.getValue());
    }

    /**
     * Tests hasCompanyIdFilter method with a negative integer. IllegalArgumentException should be thrown.
     */
    public void testHasCompanyIdFilter_negative() {
        try {
            builder.hasCompanyIdFilter(-10);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // Ok.
        }
    }

    /**
     * Tests hasCompanyIdFilter method with zero. IllegalArgumentException should be thrown.
     */
    public void testHasCompanyIdFilter_zero() {
        try {
            builder.hasCompanyIdFilter(0);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // Ok.
        }
    }

    /**
     * Tests hasCompanyIdFilter method accuracy.
     */
    public void testHasCompanyIdFilter_accuracy() {
        Long companyId = new Long(87);
        EqualToFilter filter = (EqualToFilter) builder.hasCompanyIdFilter(companyId.longValue());
        assertEquals("Filter is not created correctly.", RejectEmailDAO.SEARCH_COMPANY_ID, filter.getName());
        assertEquals("Filter is not created correctly.", companyId, filter.getValue());
    }

    /**
     * Tests hasBodyFilter method with a null argument. IllegalArgumentException should be thrown.
     */
    public void testHasBodyFilter_null() {
        try {
            builder.hasBodyFilter(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // Ok.
        }
    }

    /**
     * Tests hasBodyFilter method with an empty string. IllegalArgumentException should be thrown.
     */
    public void testHasBodyFilter_empty() {
        try {
            builder.hasBodyFilter("    ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // Ok.
        }
    }

    /**
     * Tests hasBodyFilter method accuracy.
     */
    public void testHasBodyFilter_accuracy() {
        String body = "body";
        LikeFilter filter = (LikeFilter) builder.hasBodyFilter(body);
        assertEquals("Filter is not created correctly.", RejectEmailDAO.SEARCH_BODY, filter.getName());
        assertTrue("Filter is not created correctly.", filter.getValue().toString().matches(".*" + body));
    }

    /**
     * Tests createdWithinDateRangeFilter method with two null arguments. IllegalArgumentException should be thrown.
     */
    public void testCreatedWithinDateRangeFilter_bothNull() {
        try {
            builder.createdWithinDateRangeFilter(null, null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // Ok.
        }
    }

    /**
     * Tests createdWithinDateRangeFilter method with an invalid range. IllegalArgumentException should be thrown.
     */
    public void testCreatedWithinDateRangeFilter_invalidRange() {
        try {
            Date start = new Date(new java.util.Date().getTime());
            Date end = new Date(new java.util.Date().getTime());
            end.setTime(end.getTime() + 1);
            builder.createdWithinDateRangeFilter(end, start);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // Ok.
        }
    }

    /**
     * Tests createdWithinDateRangeFilter method with null lower bound. Verifies correctly
     * <code>LessThanOrEqualToFilter</code> is returned.
     */
    public void testCreatedWithinDateRangeFilter_nullLower_accuracy() {
        Date end = new Date(new java.util.Date().getTime());
        LessThanOrEqualToFilter filter = (LessThanOrEqualToFilter) builder.createdWithinDateRangeFilter(null, end);
        assertEquals("The column name is not set correctly.", RejectEmailDAO.SEARCH_CREATED_DATE, filter.getName());
        assertEquals("The lower bound is not set correctly.", null, filter.getLowerThreshold());
        assertEquals("The upper bound is not set correctly.", end, filter.getUpperThreshold());
    }

    /**
     * Tests createdWithinDateRangeFilter method with null upper bound. Verifies correctly
     * <code>GreaterThanOrEqualToFilter</code> is returned.
     */
    public void testCreatedWithinDateRangeFilter_nullUpper_accuracy() {
        Date start = new Date(new java.util.Date().getTime());
        GreaterThanOrEqualToFilter filter = (GreaterThanOrEqualToFilter) builder.createdWithinDateRangeFilter(start,
                null);
        assertEquals("The column name is not set correctly.", RejectEmailDAO.SEARCH_CREATED_DATE, filter.getName());
        assertEquals("The lower bound is not set correctly.", start, filter.getLowerThreshold());
        assertEquals("The upper bound is not set correctly.", null, filter.getUpperThreshold());
    }

    /**
     * Tests createdWithinDateRangeFilter method with not null lower bound and upper bound. Verifies correctly
     * <code>BetweenFilter</code> is returned.
     */
    public void testCreatedWithinDateRangeFilter_accuracy() {
        Date start = new Date(new java.util.Date().getTime());
        Date end = new Date(new java.util.Date().getTime());
        end.setTime(end.getTime() + 1000);

        BetweenFilter filter = (BetweenFilter) builder.createdWithinDateRangeFilter(start, end);
        assertEquals("The column name is not set correctly.", RejectEmailDAO.SEARCH_CREATED_DATE, filter.getName());
        assertEquals("The lower bound is not set correctly.", start, filter.getLowerThreshold());
        assertEquals("The upper bound is not set correctly.", end, filter.getUpperThreshold());
    }

    /**
     * Tests createdByUserFilter method with a null argument. IllegalArgumentException should be thrown.
     */
    public void testCreatedByUserFilter_null() {
        try {
            builder.createdByUserFilter(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // Ok.
        }
    }

    /**
     * Tests createdByUserFilter method with an empty string. IllegalArgumentException should be thrown.
     */
    public void testCreatedByUserFilter_empty() {
        try {
            builder.createdByUserFilter("    ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // Ok.
        }
    }

    /**
     * Tests createdByUserFilter method accuracy.
     */
    public void testCreatedByUserFilter_accuracy() {
        String username = "user";
        EqualToFilter filter = (EqualToFilter) builder.createdByUserFilter(username);
        assertEquals("Filter is not created correctly.", RejectEmailDAO.SEARCH_CREATED_USER, filter.getName());
        assertEquals("Filter is not created correctly.", username, filter.getValue());
    }

    /**
     * Tests modifiedWithinDateRangeFilter method with two null arguments. IllegalArgumentException should be thrown.
     */
    public void testModifiedWithinDateRangeFilter_bothNull() {
        try {
            builder.modifiedWithinDateRangeFilter(null, null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // Ok.
        }
    }

    /**
     * Tests modifiedWithinDateRangeFilter method with an invalid range. IllegalArgumentException should be thrown.
     */
    public void testModifiedWithinDateRangeFilter_invalidRange() {
        try {
            Date start = new Date(new java.util.Date().getTime());
            Date end = new Date(new java.util.Date().getTime());
            end.setTime(end.getTime() + 1);
            builder.modifiedWithinDateRangeFilter(end, start);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // Ok.
        }
    }

    /**
     * Tests modifiedWithinDateRangeFilter method with null lower bound. Verifies correctly
     * <code>LessThanOrEqualToFilter</code> is returned.
     */
    public void testModifiedWithinDateRangeFilter_nullLower_accuracy() {
        Date end = new Date(new java.util.Date().getTime());
        LessThanOrEqualToFilter filter = (LessThanOrEqualToFilter) builder.modifiedWithinDateRangeFilter(null, end);
        assertEquals("The column name is not set correctly.", RejectEmailDAO.SEARCH_MODIFICATION_DATE,
            filter.getName());
        assertEquals("The lower bound is not set correctly.", null, filter.getLowerThreshold());
        assertEquals("The upper bound is not set correctly.", end, filter.getUpperThreshold());
    }

    /**
     * Tests modifiedWithinDateRangeFilter method with null upper bound. Verifies correctly
     * <code>GreaterThanOrEqualToFilter</code> is returned.
     */
    public void testModifiedWithinDateRangeFilter_nullUpper_accuracy() {
        Date start = new Date(new java.util.Date().getTime());
        GreaterThanOrEqualToFilter filter = (GreaterThanOrEqualToFilter) builder.modifiedWithinDateRangeFilter(start,
                null);
        assertEquals("The column name is not set correctly.", RejectEmailDAO.SEARCH_MODIFICATION_DATE,
            filter.getName());
        assertEquals("The lower bound is not set correctly.", start, filter.getLowerThreshold());
        assertEquals("The upper bound is not set correctly.", null, filter.getUpperThreshold());
    }

    /**
     * Tests modifiedWithinDateRangeFilter method with not null lower bound and upper bound. Verifies correctly
     * <code>BetweenFilter</code> is returned.
     */
    public void testModifiedWithinDateRangeFilter_accuracy() {
        Date start = new Date(new java.util.Date().getTime());
        Date end = new Date(new java.util.Date().getTime());
        end.setTime(end.getTime() + 1000);

        BetweenFilter filter = (BetweenFilter) builder.modifiedWithinDateRangeFilter(start, end);
        assertEquals("The column name is not set correctly.", RejectEmailDAO.SEARCH_MODIFICATION_DATE,
            filter.getName());
        assertEquals("The lower bound is not set correctly.", start, filter.getLowerThreshold());
        assertEquals("The upper bound is not set correctly.", end, filter.getUpperThreshold());
    }

    /**
     * Tests modifiedByUserFilter method with a null argument. IllegalArgumentException should be thrown.
     */
    public void testModifiedByUserFilter_null() {
        try {
            builder.modifiedByUserFilter(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // Ok.
        }
    }

    /**
     * Tests modifiedByUserFilter method with an empty string. IllegalArgumentException should be thrown.
     */
    public void testModifiedByUserFilter_empty() {
        try {
            builder.modifiedByUserFilter("    ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // Ok.
        }
    }

    /**
     * Tests modifiedByUserFilter method accuracy.
     */
    public void testModifiedByUserFilter_accuracy() {
        String username = "user";
        EqualToFilter filter = (EqualToFilter) builder.modifiedByUserFilter(username);
        assertEquals("Filter is not created correctly.", RejectEmailDAO.SEARCH_MODIFICATION_USER, filter.getName());
        assertEquals("Filter is not created correctly.", username, filter.getValue());
    }
}
