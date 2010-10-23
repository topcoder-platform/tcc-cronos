/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package hr.leads.services.failuretests;

import hr.leads.services.ejb.ReportSortingComparator;
import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for ReportSortingComparator.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class ReportSortingComparatorFailureTests extends TestCase {
    /**
     * <p>
     * The ReportSortingComparator instance for testing.
     * </p>
     */
    private ReportSortingComparator instance;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    protected void setUp() {
        instance = new ReportSortingComparator<String>(String.class, new String[] {"test"});
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(ReportSortingComparatorFailureTests.class);
    }

    /**
     * <p>
     * Tests ctor ReportSortingComparator#ReportSortingComparator(Class,String[]) for failure.
     * It tests the case that when objectType is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_NullObjectType() {
        try {
            new ReportSortingComparator<String>(null, new String[] {"test"});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor ReportSortingComparator#ReportSortingComparator(Class,String[]) for failure.
     * It tests the case that when sortColumns is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_NullSortColumns() {
        try {
            new ReportSortingComparator<String>(String.class, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor ReportSortingComparator#ReportSortingComparator(Class,String[]) for failure.
     * It tests the case that when sortColumns is empty and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_EmptySortColumns() {
        try {
            new ReportSortingComparator<String>(String.class, new String[] {});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor ReportSortingComparator#ReportSortingComparator(Class,String[]) for failure.
     * It tests the case that when sortColumns contains null and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_NullInSortColumns() {
        try {
            new ReportSortingComparator<String>(String.class, new String[] {null});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor ReportSortingComparator#ReportSortingComparator(Class,String[]) for failure.
     * It tests the case that when sortColumns contains empty and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor_EmptyInSortColumns() {
        try {
            new ReportSortingComparator<String>(String.class, new String[] {" "});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ReportSortingComparator#compare(Object,Object) for failure.
     * It tests the case that when o1 is not String and expects IllegalStateException.
     * </p>
     */
    @SuppressWarnings("unchecked")
    public void testCompare() {
        try {
            instance.compare(1L, 2L);
            fail("IllegalStateException expected.");
        } catch (IllegalStateException e) {
            //good
        }
    }

}