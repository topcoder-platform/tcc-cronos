/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report.failuretests;

import com.topcoder.timetracker.report.ReportConfiguration;
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.List;
import java.util.Map;
import java.io.File;

/**
 * <p>A failure test for {@link com.topcoder.timetracker.report.ReportConfiguration} class.</p>
 *
 * @author  isv
 * @version 1.0
 */
public class ReportConfigurationTest extends TestCase {

    /**
     * <p>An instance of {@link com.topcoder.timetracker.report.ReportConfiguration} which is tested. This instance is
     * initialized in {@link #setUp()} method and released in {@link #tearDown()} method.<p>
     */
    private ReportConfiguration testedInstance = null;

    /**
     * <p>Gets the test suite for {@link com.topcoder.timetracker.report.ReportConfiguration} class.</p>
     *
     * @return a <code>TestSuite</code> providing the tests for {@link com.topcoder.timetracker.report.ReportConfiguration} class.
     */
    public static Test suite() {
        return new TestSuite(ReportConfigurationTest.class);
    }

    /**
     * <p>Sets up the fixture. This method is called before a test is executed.</p>
     *
     * @throws Exception if any error occurs.
     */
    protected void setUp() throws Exception {
        ConfigHelper.releaseNamespaces();
        ConfigHelper.loadConfiguration(new File("failure/FailureTestsConfig.xml"));
        this.testedInstance = new ReportConfiguration(TestDataFactory.VALID_REPORT_CATEGORY,
            TestDataFactory.VALID_REPORT_TYPE,
            TestDataFactory.VALID_REPORT_CONFIGURATION_NAMESPACE);
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
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.ReportConfiguration#ReportConfiguration(com.topcoder.timetracker.report.ReportCategory,com.topcoder.timetracker.report.ReportType,String)}
     * constructor for proper handling the invalid input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#NULL} as <code>category</code> and expects the <code>NullPointerException</code>
     * to be thrown.</p>
     */
    public void testConstructor_ReportCategory_ReportType_String_category_NULL() {
        try {
            new ReportConfiguration(null, TestDataFactory.VALID_REPORT_TYPE, TestDataFactory.VALID_REPORT_CONFIGURATION_NAMESPACE);
            Assert.fail("NullPointerException should have been thrown");
        } catch (NullPointerException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("NullPointerException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.ReportConfiguration#ReportConfiguration(com.topcoder.timetracker.report.ReportCategory,com.topcoder.timetracker.report.ReportType,String)}
     * constructor for proper handling the invalid input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#NULL} as <code>type</code> and expects the <code>NullPointerException</code> to
     * be thrown.</p>
     */
    public void testConstructor_ReportCategory_ReportType_String_type_NULL() {
        try {
            new ReportConfiguration(TestDataFactory.VALID_REPORT_CATEGORY, null, TestDataFactory.VALID_REPORT_CONFIGURATION_NAMESPACE);
            Assert.fail("NullPointerException should have been thrown");
        } catch (NullPointerException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("NullPointerException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.ReportConfiguration#ReportConfiguration(com.topcoder.timetracker.report.ReportCategory,com.topcoder.timetracker.report.ReportType,String)}
     * constructor for proper handling the invalid input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#NULL} as <code>namespace</code> and expects the
     * <code>NullPointerException</code> to be thrown.</p>
     */
    public void testConstructor_ReportCategory_ReportType_String_namespace_NULL() {
        try {
            new ReportConfiguration(TestDataFactory.VALID_REPORT_CATEGORY, TestDataFactory.VALID_REPORT_TYPE, null);
            Assert.fail("NullPointerException should have been thrown");
        } catch (NullPointerException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("NullPointerException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.ReportConfiguration#ReportConfiguration(com.topcoder.timetracker.report.ReportCategory,com.topcoder.timetracker.report.ReportType,String)}
     * constructor for proper handling the invalid input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#ZERO_LENGTH_STRING} as <code>namespace</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.</p>
     */
    public void testConstructor_ReportCategory_ReportType_String_namespace_ZERO_LENGTH_STRING() {
        try {
            new ReportConfiguration(TestDataFactory.VALID_REPORT_CATEGORY, TestDataFactory.VALID_REPORT_TYPE, TestDataFactory.ZERO_LENGTH_STRING);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.ReportConfiguration#ReportConfiguration(com.topcoder.timetracker.report.ReportCategory,com.topcoder.timetracker.report.ReportType,String)}
     * constructor for proper handling the invalid input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#WHITESPACE_ONLY_STRING} as <code>namespace</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.</p>
     */
    public void testConstructor_ReportCategory_ReportType_String_namespace_WHITESPACE_ONLY_STRING() {
        try {
            new ReportConfiguration(TestDataFactory.VALID_REPORT_CATEGORY, TestDataFactory.VALID_REPORT_TYPE, TestDataFactory.WHITESPACE_ONLY_STRING);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.ReportConfiguration#setFilters(java.util.List)} method for proper handling the invalid
     * input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#NULL} as <code>filters</code> and expects the <code>NullPointerException</code>
     * to be thrown.</p>
     */
    public void testSetFilters_List_filters_NULL() {
        try {
            this.testedInstance.setFilters(null);
            Assert.fail("NullPointerException should have been thrown");
        } catch (NullPointerException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("NullPointerException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.ReportConfiguration#setFilters(java.util.List)} method for proper handling the invalid
     * input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#EMPTY_LIST} as <code>filters</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.</p>
     */
    public void testSetFilters_List_filters_EMPTY_LIST() {
        try {
            this.testedInstance.setFilters(TestDataFactory.EMPTY_LIST);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.ReportConfiguration#setFilters(java.util.List)} method for proper handling the invalid
     * input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#getFiltersListWithNullElement} as <code>filters</code> and expects the
     * <code>NullPointerException</code> to be thrown.</p>
     */
    public void testSetFilters_List_filters_FiltersListWithNullElement() {
        try {
            this.testedInstance.setFilters(TestDataFactory.getFiltersListWithNullElement());
            Assert.fail("NullPointerException should have been thrown");
        } catch (NullPointerException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("NullPointerException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.ReportConfiguration#setFilters(java.util.List)} method for proper handling the invalid
     * input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#getFiltersListWithNonFilterElement} as <code>filters</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.</p>
     */
    public void testSetFilters_List_filters_FiltersListWithNonFilterElement() {
        try {
            this.testedInstance.setFilters(TestDataFactory.getFiltersListWithNonFilterElement());
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.ReportConfiguration#setColumnDecorators(java.util.List)} method for proper handling the
     * invalid input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#NULL} as <code>columnDecorators</code> and expects the
     * <code>NullPointerException</code> to be thrown.</p>
     */
    public void testSetColumnDecorators_List_columnDecorators_NULL() {
        try {
            this.testedInstance.setColumnDecorators(null);
            Assert.fail("NullPointerException should have been thrown");
        } catch (NullPointerException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("NullPointerException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.ReportConfiguration#setColumnDecorators(java.util.List)} method for proper handling the
     * invalid input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#EMPTY_LIST} as <code>columnDecorators</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.</p>
     */
    public void testSetColumnDecorators_List_columnDecorators_EMPTY_LIST() {
        try {
            this.testedInstance.setColumnDecorators(TestDataFactory.EMPTY_LIST);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.ReportConfiguration#setColumnDecorators(java.util.List)} method for proper handling the
     * invalid input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#getColumnDecoratorsListWithNullElement} as <code>columnDecorators</code> and
     * expects the <code>NullPointerException</code> to be thrown.</p>
     */
    public void testSetColumnDecorators_List_columnDecorators_ColumnDecoratorsListWithNullElement() {
        try {
            this.testedInstance.setColumnDecorators(TestDataFactory.getColumnDecoratorsListWithNullElement());
            Assert.fail("NullPointerException should have been thrown");
        } catch (NullPointerException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("NullPointerException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.ReportConfiguration#setColumnDecorators(java.util.List)} method for proper handling the
     * invalid input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#getColumnDecoratorsListWithNonColumnDecoratorElement} as
     * <code>columnDecorators</code> and expects the <code>IllegalArgumentException</code> to be thrown.</p>
     */
    public void testSetColumnDecorators_List_columnDecorators_ColumnDecoratorsListWithNonColumnDecoratorElement() {
        try {
            this.testedInstance.setColumnDecorators(TestDataFactory.getColumnDecoratorsListWithNonColumnDecoratorElement());
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.ReportConfiguration#setStyles(java.util.Map)} method for proper handling the invalid
     * input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#NULL} as <code>styles</code> and expects the <code>NullPointerException</code>
     * to be thrown.</p>
     */
    public void testSetStyles_Map_styles_NULL() {
        try {
            this.testedInstance.setStyles(null);
            Assert.fail("NullPointerException should have been thrown");
        } catch (NullPointerException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("NullPointerException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.ReportConfiguration#setStyles(java.util.Map)} method for proper handling the invalid
     * input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#EMPTY_MAP} as <code>styles</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.</p>
     */
    public void testSetStyles_Map_styles_EMPTY_MAP() {
        try {
            this.testedInstance.setStyles(TestDataFactory.EMPTY_MAP);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.ReportConfiguration#setStyles(java.util.Map)} method for proper handling the invalid
     * input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#getStylesMapWithNullKey} as <code>styles</code> and expects the
     * <code>NullPointerException</code> to be thrown.</p>
     */
    public void testSetStyles_Map_styles_StylesMapWithNullKey() {
        try {
            this.testedInstance.setStyles(TestDataFactory.getStylesMapWithNullKey());
            Assert.fail("NullPointerException should have been thrown");
        } catch (NullPointerException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("NullPointerException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.ReportConfiguration#setStyles(java.util.Map)} method for proper handling the invalid
     * input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#getStylesMapWithNullValue} as <code>styles</code> and expects the
     * <code>NullPointerException</code> to be thrown.</p>
     */
    public void testSetStyles_Map_styles_StylesMapWithNullValue() {
        try {
            this.testedInstance.setStyles(TestDataFactory.getStylesMapWithNullValue());
            Assert.fail("NullPointerException should have been thrown");
        } catch (NullPointerException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("NullPointerException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.ReportConfiguration#setStyles(java.util.Map)} method for proper handling the invalid
     * input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#getStylesMapWithNonStyleConstantKey} as <code>styles</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.</p>
     */
    public void testSetStyles_Map_styles_StylesMapWithNonStyleConstantKey() {
        try {
            this.testedInstance.setStyles(TestDataFactory.getStylesMapWithNonStyleConstantKey());
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.ReportConfiguration#setStyles(java.util.Map)} method for proper handling the invalid
     * input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#getStylesMapWithNonStringValue} as <code>styles</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.</p>
     */
    public void testSetStyles_Map_styles_StylesMapWithNonStringValue() {
        try {
            this.testedInstance.setStyles(TestDataFactory.getStylesMapWithNonStringValue());
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }
}
