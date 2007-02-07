/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report.failuretests;

import com.topcoder.timetracker.report.RangeFilter;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.File;

/**
 * <p>A failure test for {@link com.topcoder.timetracker.report.RangeFilter} class.</p>
 *
 * @author isv
 * @version 2.0
 */
public class RangeFilterTest extends TestCase {

    /**
     * <p>An instance of {@link com.topcoder.timetracker.report.RangeFilter} which is tested. This instance is
     * initialized in {@link #setUp()} method and released in {@link #tearDown()} method.<p>
     */
    private RangeFilter testedInstance = null;

    /**
     * <p>Gets the test suite for {@link com.topcoder.timetracker.report.RangeFilter} class.</p>
     *
     * @return a <code>TestSuite</code> providing the tests for {@link com.topcoder.timetracker.report.RangeFilter} class.
     */
    public static Test suite() {
        return new TestSuite(RangeFilterTest.class);
    }

    /**
     * <p>Sets up the fixture. This method is called before a test is executed.</p>
     *
     * @throws Exception if any error occurs.
     */
    protected void setUp() throws Exception {
        ConfigHelper.releaseNamespaces();
        ConfigHelper.loadConfiguration(new File("failure/FailureTestsConfig.xml"));
        this.testedInstance = new RangeFilter(TestDataFactory.VALID_COLUMN, TestDataFactory.VALID_FILTER_CATEGORY);
    }

    /**
     * <p>Tears down the fixture. This method is called after a test is executed.</p>
     *
     * @throws Exception if any error occurs.
     */
    protected void tearDown() throws Exception {
        this.testedInstance = null;
        ConfigHelper.releaseNamespaces();
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.RangeFilter#RangeFilter(
     * com.topcoder.timetracker.report.Column,com.topcoder.timetracker.report.FilterCategory)}
     * constructor for proper handling the invalid input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#NULL} as <code>column</code> and expects the <code>NullPointerException</code>
     * to be thrown.</p>
     */
    public void testConstructor_Column_FilterCategory_column_NULL() {
        try {
            new RangeFilter(null, TestDataFactory.VALID_FILTER_CATEGORY);
            Assert.fail("NullPointerException should have been thrown");
        } catch (NullPointerException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("NullPointerException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.RangeFilter#RangeFilter(
     * com.topcoder.timetracker.report.Column,com.topcoder.timetracker.report.FilterCategory)}
     * constructor for proper handling the invalid input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#NULL} as <code>category</code> and expects the <code>NullPointerException</code>
     * to be thrown.</p>
     */
    public void testConstructor_Column_FilterCategory_category_NULL() {
        try {
            new RangeFilter(TestDataFactory.VALID_COLUMN, null);
            Assert.fail("NullPointerException should have been thrown");
        } catch (NullPointerException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("NullPointerException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.RangeFilter#addFilterRange(String,String)}
     * method for proper handling the invalid input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#NULL} as <code>lower</code> and expects the <code>NullPointerException</code> to
     * be thrown.</p>
     */
    public void testAddFilterRange_String_String_lower_NULL() {
        try {
            this.testedInstance.addFilterRange(null, TestDataFactory.VALID_UPPER_RANGE);
            Assert.fail("NullPointerException should have been thrown");
        } catch (NullPointerException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("NullPointerException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.RangeFilter#addFilterRange(String,String)}
     * method for proper handling the invalid input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#ZERO_LENGTH_STRING} as <code>lower</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.</p>
     */
    public void testAddFilterRange_String_String_lower_ZERO_LENGTH_STRING() {
        try {
            this.testedInstance.addFilterRange(TestDataFactory.ZERO_LENGTH_STRING, TestDataFactory.VALID_UPPER_RANGE);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.RangeFilter#addFilterRange(String,String)}
     * method for proper handling the invalid input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#WHITESPACE_ONLY_STRING} as <code>lower</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.</p>
     */
    public void testAddFilterRange_String_String_lower_WHITESPACE_ONLY_STRING() {
        try {
            this.testedInstance.addFilterRange(TestDataFactory.WHITESPACE_ONLY_STRING,
                TestDataFactory.VALID_UPPER_RANGE);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.RangeFilter#addFilterRange(String,String)}
     * method for proper handling the invalid input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#NULL} as <code>upper</code> and expects the <code>NullPointerException</code> to
     * be thrown.</p>
     */
    public void testAddFilterRange_String_String_upper_NULL() {
        try {
            this.testedInstance.addFilterRange(TestDataFactory.VALID_LOWER_RANGE, null);
            Assert.fail("NullPointerException should have been thrown");
        } catch (NullPointerException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("NullPointerException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.RangeFilter#addFilterRange(String,String)}
     * method for proper handling the invalid input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#ZERO_LENGTH_STRING} as <code>upper</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.</p>
     */
    public void testAddFilterRange_String_String_upper_ZERO_LENGTH_STRING() {
        try {
            this.testedInstance.addFilterRange(TestDataFactory.VALID_LOWER_RANGE, TestDataFactory.ZERO_LENGTH_STRING);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.RangeFilter#addFilterRange(String,String)}
     * method for proper handling the invalid input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#WHITESPACE_ONLY_STRING} as <code>upper</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.</p>
     */
    public void testAddFilterRange_String_String_upper_WHITESPACE_ONLY_STRING() {
        try {
            this.testedInstance.addFilterRange(TestDataFactory.VALID_LOWER_RANGE,
                TestDataFactory.WHITESPACE_ONLY_STRING);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }
}
