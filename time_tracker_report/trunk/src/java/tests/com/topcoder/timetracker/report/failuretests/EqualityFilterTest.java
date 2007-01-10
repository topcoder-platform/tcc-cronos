/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.report.failuretests;

import com.cronos.timetracker.report.EqualityFilter;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.File;

/**
 * <p>A failure test for {@link com.cronos.timetracker.report.EqualityFilter} class.</p>
 *
 * @author isv
 * @version 2.0
 */
public class EqualityFilterTest extends TestCase {

    /**
     * <p>An instance of {@link com.cronos.timetracker.report.EqualityFilter} which is tested. This instance is
     * initialized in {@link #setUp()} method and released in {@link #tearDown()} method.<p>
     */
    private EqualityFilter testedInstance = null;

    /**
     * <p>Gets the test suite for {@link com.cronos.timetracker.report.EqualityFilter} class.</p>
     *
     * @return a <code>TestSuite</code> providing the tests for {@link com.cronos.timetracker.report.EqualityFilter} class.
     */
    public static Test suite() {
        return new TestSuite(EqualityFilterTest.class);
    }

    /**
     * <p>Sets up the fixture. This method is called before a test is executed.</p>
     *
     * @throws Exception if any error occurs.
     */
    protected void setUp() throws Exception {
        ConfigHelper.releaseNamespaces();
        ConfigHelper.loadConfiguration("failure/FailureTestsConfig.xml");
        this.testedInstance = new EqualityFilter(TestDataFactory.VALID_COLUMN, TestDataFactory.VALID_FILTER_CATEGORY);
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
     * <p>Failure test. Tests the {@link com.cronos.timetracker.report.EqualityFilter#EqualityFilter(com.cronos.timetracker.report.Column,com.cronos.timetracker.report.FilterCategory)}
     * constructor for proper handling the invalid input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#NULL} as <code>column</code> and expects the <code>NullPointerException</code>
     * to be thrown.</p>
     */
    public void testConstructor_Column_FilterCategory_column_NULL() {
        try {
            new EqualityFilter(null, TestDataFactory.VALID_FILTER_CATEGORY);
            Assert.fail("NullPointerException should have been thrown");
        } catch (NullPointerException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("NullPointerException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.cronos.timetracker.report.EqualityFilter#EqualityFilter(com.cronos.timetracker.report.Column,com.cronos.timetracker.report.FilterCategory)}
     * constructor for proper handling the invalid input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#NULL} as <code>category</code> and expects the <code>NullPointerException</code>
     * to be thrown.</p>
     */
    public void testConstructor_Column_FilterCategory_category_NULL() {
        try {
            new EqualityFilter(TestDataFactory.VALID_COLUMN, null);
            Assert.fail("NullPointerException should have been thrown");
        } catch (NullPointerException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("NullPointerException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.cronos.timetracker.report.EqualityFilter#addFilterValue(String)} method for proper handling the invalid
     * input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#NULL} as <code>filterValue</code> and expects the
     * <code>NullPointerException</code> to be thrown.</p>
     */
    public void testAddFilterValue_String_filterValue_NULL() {
        try {
            this.testedInstance.addFilterValue(null);
            Assert.fail("NullPointerException should have been thrown");
        } catch (NullPointerException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("NullPointerException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.cronos.timetracker.report.EqualityFilter#addFilterValue(String)} method for proper handling the invalid
     * input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#ZERO_LENGTH_STRING} as <code>filterValue</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.</p>
     */
    public void testAddFilterValue_String_filterValue_ZERO_LENGTH_STRING() {
        try {
            this.testedInstance.addFilterValue(TestDataFactory.ZERO_LENGTH_STRING);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.cronos.timetracker.report.EqualityFilter#addFilterValue(String)} method for proper handling the invalid
     * input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#WHITESPACE_ONLY_STRING} as <code>filterValue</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.</p>
     */
    public void testAddFilterValue_String_filterValue_WHITESPACE_ONLY_STRING() {
        try {
            this.testedInstance.addFilterValue(TestDataFactory.WHITESPACE_ONLY_STRING);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }
}
