/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.timetracker.report.informix;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import com.topcoder.timetracker.report.BaseTestCase;

import com.topcoder.search.builder.filter.BetweenFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.GreaterThanOrEqualToFilter;
import com.topcoder.search.builder.filter.InFilter;
import com.topcoder.search.builder.filter.LessThanOrEqualToFilter;

import java.util.List;
import java.util.Date;

/**
 * <p>
 * This class provides accuracy tests for <code>InformixReportDAO</code> class. It tests:
 * <ol>
 * <li> InformixReportDAO() private constructor</li>
 * <li> getFilterCompanies(long[]) constructor</li>
 * </ol>
 * </p>
 *
 * @author rinoavd
 * @version 3.1
 */
public class TestInformixFilter extends BaseTestCase {
    /**
     * <p>
     * The only constructor is with modifier 'private'.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInformixFilter_Ctor() throws Exception {
        assertEquals("Class InformixFilter contains public Constructor.", 0, InformixFilter.class
                .getConstructors().length);

        Constructor[] ctor = InformixFilter.class.getDeclaredConstructors();
        assertTrue("Class InformixFilter should contain only 1 private Constructor.", (ctor.length == 1 && ctor[0]
                .getModifiers() == Modifier.PRIVATE));

        try {
            Class.forName("com.topcoder.timetracker.report.informix.InformixFilter").newInstance();
            fail("IllegalAccessException is expected");
        } catch (IllegalAccessException e) {
            // good
        }
    }

    /**
     * <p>
     * Accuracy test of <code>getFilterCompanies()</code> method.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInformixFilter_getFilterCompanies_Acc() throws Exception {
        Filter filter = InformixFilter.getFilterCompanies(new long[] {1 });
        assertTrue("filter should be instance of InFilter.", filter instanceof InFilter);
        assertEquals("filter name should be 'company id'", "company id", ((InFilter) filter).getName());

        List validIds = ((InFilter) filter).getList();
        assertEquals("validIDs should contains 1 element.", 1, validIds.size());
        assertTrue("validIDs[0] should be a Long.", validIds.get(0) instanceof Long);
        assertEquals("validIDs[0] should be 1.", 1, ((Long) validIds.get(0)).longValue());
    }

    /**
     * <p>
     * Failure test of <code>getFilterCompanies()</code> method.
     * </p>
     *
     * <p>
     * null is passed in. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInformixFilter_getFilterCompanies_Failure_1_NullArray() throws Exception {
        try {
            InformixFilter.getFilterCompanies(null);
            fail("IllegalArgumentException was expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test of <code>getFilterCompanies()</code> method.
     * </p>
     *
     * <p>
     * empty array is passed in. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInformixFilter_getFilterCompanies_Failure_2_EmptyArray() throws Exception {
        try {
            InformixFilter.getFilterCompanies(new long[] {});
            fail("IllegalArgumentException was expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Accuracy test of <code>getFilterProjects()</code> method.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInformixFilter_getFilterProjects_Acc() throws Exception {
        Filter filter = InformixFilter.getFilterProjects(new long[] {1 });
        assertTrue("filter should be instance of InFilter.", filter instanceof InFilter);
        assertEquals("filter name should be 'project id'", "project id", ((InFilter) filter).getName());

        List validIds = ((InFilter) filter).getList();
        assertEquals("validIDs should contains 1 element.", 1, validIds.size());
        assertTrue("validIDs[0] should be a Long.", validIds.get(0) instanceof Long);
        assertEquals("validIDs[0] should be 1.", 1, ((Long) validIds.get(0)).longValue());
    }

    /**
     * <p>
     * Failure test of <code>getFilterCompanies()</code> method.
     * </p>
     *
     * <p>
     * null is passed in. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInformixFilter_getFilterProjects_Failure_1_NullArray() throws Exception {
        try {
            InformixFilter.getFilterProjects(null);
            fail("IllegalArgumentException was expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test of <code>getFilterProjects()</code> method.
     * </p>
     *
     * <p>
     * empty array is passed in. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInformixFilter_getFilterProjects_Failure_2_EmptyArray() throws Exception {
        try {
            InformixFilter.getFilterProjects(new long[] {});
            fail("IllegalArgumentException was expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Accuracy test of <code>getFilterClients()</code> method.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInformixFilter_getFilterClients_Acc() throws Exception {
        Filter filter = InformixFilter.getFilterClients(new long[] {1 });
        assertTrue("filter should be instance of InFilter.", filter instanceof InFilter);
        assertEquals("filter name should be 'client id'", "client id", ((InFilter) filter).getName());

        List validIds = ((InFilter) filter).getList();
        assertEquals("validIDs should contains 1 element.", 1, validIds.size());
        assertTrue("validIDs[0] should be a Long.", validIds.get(0) instanceof Long);
        assertEquals("validIDs[0] should be 1.", 1, ((Long) validIds.get(0)).longValue());
    }

    /**
     * <p>
     * Failure test of <code>getFilterClients()</code> method.
     * </p>
     *
     * <p>
     * null is passed in. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInformixFilter_getFilterClients_Failure_1_NullArray() throws Exception {
        try {
            InformixFilter.getFilterClients(null);
            fail("IllegalArgumentException was expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test of <code>getFilterClients()</code> method.
     * </p>
     *
     * <p>
     * empty array is passed in. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInformixFilter_getFilterClients_Failure_2_EmptyArray() throws Exception {
        try {
            InformixFilter.getFilterClients(new long[] {});
            fail("IllegalArgumentException was expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Accuracy test of <code>getFilterEntryDate()</code> method.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInformixFilter_getFilterEntryDate_Acc_1_ValidBounds() throws Exception {
        Date lower = new Date(new Date().getTime() - ONEDAY);
        Date upper = new Date();
        Filter filter = InformixFilter.getFilterEntryDate(lower, upper);
        assertTrue("filter should be instance of BetweenFilter.", filter instanceof BetweenFilter);
    }

    /**
     * <p>
     * Accuracy test of <code>getFilterEntryDate()</code> method.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInformixFilter_getFilterEntryDate_Acc_2_MissingUpperBound() throws Exception {
        Date lower = new Date(new Date().getTime() - ONEDAY);
        // Date upper = new Date();
        Filter filter = InformixFilter.getFilterEntryDate(lower, null);
        assertTrue("filter should be instance of GreaterThanOrEqualToFilter.",
                filter instanceof GreaterThanOrEqualToFilter);
        assertEquals("filter name should be 'entry date'", "entry date",
                ((GreaterThanOrEqualToFilter) filter).getName());
        assertEquals("filter lower bound should be " + FORMATTER.format(lower),
                new java.sql.Date(lower.getTime()),
                (java.sql.Date) ((GreaterThanOrEqualToFilter) filter).getLowerThreshold());
    }

    /**
     * <p>
     * Accuracy test of <code>getFilterEntryDate()</code> method.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInformixFilter_getFilterEntryDate_Acc_3_MissingLowerBound() throws Exception {
        // Date lower = new Date(new Date().getTime() - ONEDAY);
        Date upper = new Date();
        Filter filter = InformixFilter.getFilterEntryDate(null, upper);
        assertTrue("filter should be instance of LessThanOrEqualToFilter.",
                filter instanceof LessThanOrEqualToFilter);
        assertEquals("filter name should be 'entry date'", "entry date",
                ((LessThanOrEqualToFilter) filter).getName());
        assertEquals("filter lower bound should be " + FORMATTER.format(upper),
                new java.sql.Date(upper.getTime()),
                (java.sql.Date) ((LessThanOrEqualToFilter) filter).getUpperThreshold());
    }

    /**
     * <p>
     * Failure test of <code>getFilterEntryDate()</code> method.
     * </p>
     *
     * <p>
     * Both 'from' and 'to' are null. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInformixFilter_getFilterEntryDate_Failure_1_BothNull() throws Exception {
        try {
            InformixFilter.getFilterEntryDate(null, null);
            fail("IllegalArgumentException was expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test of <code>getFilterEntryDate()</code> method.
     * </p>
     *
     * <p>
     * 'from' > 'to'. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInformixFilter_getFilterEntryDate_Failure_2_FromGreaterThanTo() throws Exception {
        try {
            Date lower = new Date(new Date().getTime() + ONEDAY);
            Date upper = new Date();
            InformixFilter.getFilterEntryDate(lower, upper);
            fail("IllegalArgumentException was expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Accuracy test of <code>getFilterType()</code> method.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInformixFilter_getFilterType_Acc() throws Exception {
        Filter filter = InformixFilter.getFilterType(1);
        assertTrue("filter should be instance of EqualToFilter.", filter instanceof EqualToFilter);
        assertEquals("filter name should be 'type'", "type", ((EqualToFilter) filter).getName());
        assertEquals("filter value should be 1", 1, ((Long) ((EqualToFilter) filter).getValue()).longValue());
    }

    /**
     * <p>
     * Accuracy test of <code>getFilterStatus()</code> method.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInformixFilter_getFilterStatus_Acc() throws Exception {
        Filter filter = InformixFilter.getFilterStatus(1);
        assertTrue("filter should be instance of EqualToFilter.", filter instanceof EqualToFilter);
        assertEquals("filter name should be 'status'", "status", ((EqualToFilter) filter).getName());
        assertEquals("filter value should be 1", 1, ((Long) ((EqualToFilter) filter).getValue()).longValue());
    }

    /**
     * <p>
     * Accuracy test of <code>getFilterUsernames()</code> method.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInformixFilter_getFilterUsernames_Acc() throws Exception {
        Filter filter = InformixFilter.getFilterUsernames(new String[] {"creationUser1" });
        assertTrue("filter should be instance of InFilter.", filter instanceof InFilter);
        assertEquals("filter name should be 'username'", "username", ((InFilter) filter).getName());

        List validIds = ((InFilter) filter).getList();
        assertEquals("validIDs should contains 1 element.", 1, validIds.size());
        assertTrue("validIDs[0] should be a String.", validIds.get(0) instanceof String);
        assertEquals("validIDs[0] should be 'creationUser1'.", "creationUser1", (String) validIds.get(0));
    }

    /**
     * <p>
     * Failure test of <code>getFilterUsernames()</code> method.
     * </p>
     *
     * <p>
     * null is passed in. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInformixFilter_getFilterUsernames_Failure_1_NullArray() throws Exception {
        try {
            InformixFilter.getFilterUsernames(null);
            fail("IllegalArgumentException was expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test of <code>getFilterUsernames()</code> method.
     * </p>
     *
     * <p>
     * empty array is passed in. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInformixFilter_getFilterUsernames_Failure_2_EmptyArray() throws Exception {
        try {
            InformixFilter.getFilterUsernames(new String[] {});
            fail("IllegalArgumentException was expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test of <code>getFilterUsernames()</code> method.
     * </p>
     *
     * <p>
     * an array with some null elements is passed in. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInformixFilter_getFilterUsernames_Failure_3_ArrayContainsNull() throws Exception {
        try {
            InformixFilter.getFilterUsernames(new String[] {"user1", null, "user3" });
            fail("IllegalArgumentException was expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }
}
