/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report.failuretests;

import com.topcoder.timetracker.report.BasicColumnDecorator;
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.File;

/**
 * <p>A failure test for {@link com.topcoder.timetracker.report.BasicColumnDecorator} class.</p>
 *
 * @author  isv
 * @version 1.0
 */
public class BasicColumnDecoratorTest extends TestCase {

    /**
     * <p>An instance of {@link com.topcoder.timetracker.report.BasicColumnDecorator} which is tested. This instance is
     * initialized in {@link #setUp()} method and released in {@link #tearDown()} method.<p>
     */
    private BasicColumnDecorator testedInstance = null;

    /**
     * <p>Gets the test suite for {@link com.topcoder.timetracker.report.BasicColumnDecorator} class.</p>
     *
     * @return a <code>TestSuite</code> providing the tests for {@link com.topcoder.timetracker.report.BasicColumnDecorator} class.
     */
    public static Test suite() {
        return new TestSuite(BasicColumnDecoratorTest.class);
    }

    /**
     * <p>Sets up the fixture. This method is called before a test is executed.</p>
     *
     * @throws Exception if any error occurs.
     */
    protected void setUp() throws Exception {
        ConfigHelper.releaseNamespaces();
        ConfigHelper.loadConfiguration(new File("failure/FailureTestsConfig.xml"));
        this.testedInstance
            = new BasicColumnDecorator(TestDataFactory.VALID_COLUMN_NAME, TestDataFactory.VALID_DISPLAY_LABEL);
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
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.BasicColumnDecorator#BasicColumnDecorator(String,String)} constructor for
     * proper handling the invalid input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#NULL} as <code>colName</code> and expects the <code>NullPointerException</code>
     * to be thrown.</p>
     */
    public void testConstructor_String_String_colName_NULL() {
        try {
            new BasicColumnDecorator(null, TestDataFactory.VALID_DISPLAY_LABEL);
            Assert.fail("NullPointerException should have been thrown");
        } catch (NullPointerException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("NullPointerException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.BasicColumnDecorator#BasicColumnDecorator(String,String)} constructor for
     * proper handling the invalid input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#ZERO_LENGTH_STRING} as <code>colName</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.</p>
     */
    public void testConstructor_String_String_colName_ZERO_LENGTH_STRING() {
        try {
            new BasicColumnDecorator(TestDataFactory.ZERO_LENGTH_STRING, TestDataFactory.VALID_DISPLAY_LABEL);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.BasicColumnDecorator#BasicColumnDecorator(String,String)} constructor for
     * proper handling the invalid input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#WHITESPACE_ONLY_STRING} as <code>colName</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.</p>
     */
    public void testConstructor_String_String_colName_WHITESPACE_ONLY_STRING() {
        try {
            new BasicColumnDecorator(TestDataFactory.WHITESPACE_ONLY_STRING, TestDataFactory.VALID_DISPLAY_LABEL);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.BasicColumnDecorator#BasicColumnDecorator(String,String)} constructor for
     * proper handling the invalid input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#NULL} as <code>displayLabel</code> and expects the
     * <code>NullPointerException</code> to be thrown.</p>
     */
    public void testConstructor_String_String_displayLabel_NULL() {
        try {
            new BasicColumnDecorator(TestDataFactory.VALID_COLUMN_NAME, null);
            Assert.fail("NullPointerException should have been thrown");
        } catch (NullPointerException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("NullPointerException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.BasicColumnDecorator#BasicColumnDecorator(String,String)} constructor for
     * proper handling the invalid input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#ZERO_LENGTH_STRING} as <code>displayLabel</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.</p>
     */
    public void testConstructor_String_String_displayLabel_ZERO_LENGTH_STRING() {
        try {
            new BasicColumnDecorator(TestDataFactory.VALID_COLUMN_NAME, TestDataFactory.ZERO_LENGTH_STRING);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.BasicColumnDecorator#BasicColumnDecorator(String,String)} constructor for
     * proper handling the invalid input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#WHITESPACE_ONLY_STRING} as <code>displayLabel</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.</p>
     */
    public void testConstructor_String_String_displayLabel_WHITESPACE_ONLY_STRING() {
        try {
            new BasicColumnDecorator(TestDataFactory.VALID_COLUMN_NAME, TestDataFactory.WHITESPACE_ONLY_STRING);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.BasicColumnDecorator#setPrefix(String)} method for proper handling the invalid
     * input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#NULL} as <code>prefix</code> and expects the <code>NullPointerException</code>
     * to be thrown.</p>
     */
    public void testSetPrefix_String_prefix_NULL() {
        try {
            this.testedInstance.setPrefix(null);
            Assert.fail("NullPointerException should have been thrown");
        } catch (NullPointerException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("NullPointerException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.BasicColumnDecorator#setPrefix(String)} method for proper handling the invalid
     * input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#ZERO_LENGTH_STRING} as <code>prefix</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.</p>
     */
    public void testSetPrefix_String_prefix_ZERO_LENGTH_STRING() {
        try {
            this.testedInstance.setPrefix(TestDataFactory.ZERO_LENGTH_STRING);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.BasicColumnDecorator#setPrefix(String)} method for proper handling the invalid
     * input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#WHITESPACE_ONLY_STRING} as <code>prefix</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.</p>
     */
    public void testSetPrefix_String_prefix_WHITESPACE_ONLY_STRING() {
        try {
            this.testedInstance.setPrefix(TestDataFactory.WHITESPACE_ONLY_STRING);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.BasicColumnDecorator#setSuffix(String)} method for proper handling the invalid
     * input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#NULL} as <code>suffix</code> and expects the <code>NullPointerException</code>
     * to be thrown.</p>
     */
    public void testSetSuffix_String_suffix_NULL() {
        try {
            this.testedInstance.setSuffix(null);
            Assert.fail("NullPointerException should have been thrown");
        } catch (NullPointerException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("NullPointerException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.BasicColumnDecorator#setSuffix(String)} method for proper handling the invalid
     * input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#ZERO_LENGTH_STRING} as <code>suffix</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.</p>
     */
    public void testSetSuffix_String_suffix_ZERO_LENGTH_STRING() {
        try {
            this.testedInstance.setSuffix(TestDataFactory.ZERO_LENGTH_STRING);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.BasicColumnDecorator#setSuffix(String)} method for proper handling the invalid
     * input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#WHITESPACE_ONLY_STRING} as <code>suffix</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.</p>
     */
    public void testSetSuffix_String_suffix_WHITESPACE_ONLY_STRING() {
        try {
            this.testedInstance.setSuffix(TestDataFactory.WHITESPACE_ONLY_STRING);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }
}
