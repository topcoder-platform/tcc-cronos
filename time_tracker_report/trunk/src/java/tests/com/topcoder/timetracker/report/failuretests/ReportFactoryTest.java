/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report.failuretests;

import com.topcoder.timetracker.report.ReportConfigurationException;
import com.topcoder.timetracker.report.ReportFactory;
import com.topcoder.timetracker.report.ReportNotFoundException;
import com.topcoder.timetracker.report.ReportException;
import com.topcoder.timetracker.report.failuretests.impl.ReportDuplicateFormatCategory1;
import com.topcoder.timetracker.report.failuretests.impl.ReportDuplicateFormatCategory2;
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.File;

/**
 * <p>A failure test for {@link com.topcoder.timetracker.report.ReportFactory} class.</p>
 *
 * @author  isv
 * @version 1.0
 */
public class ReportFactoryTest extends TestCase {

    /**
     * <p>An instance of {@link com.topcoder.timetracker.report.ReportFactory} which is tested. This instance is
     * initialized in {@link #setUp()} method and released in {@link #tearDown()} method.<p>
     */
    private ReportFactory testedInstance = null;

    /**
     * <p>Gets the test suite for {@link com.topcoder.timetracker.report.ReportFactory} class.</p>
     *
     * @return a <code>TestSuite</code> providing the tests for {@link com.topcoder.timetracker.report.ReportFactory} class.
     */
    public static Test suite() {
        return new TestSuite(ReportFactoryTest.class);
    }

    /**
     * <p>Sets up the fixture. This method is called before a test is executed.</p>
     *
     * @throws Exception if any error occurs.
     */
    protected void setUp() throws Exception {
        ConfigHelper.releaseNamespaces();
        ConfigHelper.loadConfiguration(new File("failure/FailureTestsConfig.xml"));
        this.testedInstance = new ReportFactory();
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
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.ReportFactory#getReport(String,com.topcoder.timetracker.report.ReportCategory)}
     * method for proper handling the invalid input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#NULL} as <code>format</code> and expects the <code>NullPointerException</code>
     * to be thrown.</p>
     */
    public void testGetReport_String_ReportCategory_format_NULL() {
        try {
            this.testedInstance.getReport(null, TestDataFactory.VALID_REPORT_CATEGORY);
            Assert.fail("NullPointerException should have been thrown");
        } catch (NullPointerException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("NullPointerException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.ReportFactory#getReport(String,com.topcoder.timetracker.report.ReportCategory)}
     * method for proper handling the invalid input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#ZERO_LENGTH_STRING} as <code>format</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.</p>
     */
    public void testGetReport_String_ReportCategory_format_ZERO_LENGTH_STRING() {
        try {
            this.testedInstance.getReport(TestDataFactory.ZERO_LENGTH_STRING, TestDataFactory.VALID_REPORT_CATEGORY);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.ReportFactory#getReport(String,com.topcoder.timetracker.report.ReportCategory)}
     * method for proper handling the invalid input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#WHITESPACE_ONLY_STRING} as <code>format</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.</p>
     */
    public void testGetReport_String_ReportCategory_format_WHITESPACE_ONLY_STRING() {
        try {
            this.testedInstance.getReport(TestDataFactory.WHITESPACE_ONLY_STRING, TestDataFactory.VALID_REPORT_CATEGORY);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.ReportFactory#getReport(String,com.topcoder.timetracker.report.ReportCategory)}
     * method for proper handling the invalid input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#UNKNOWN_REPORT_FORMAT} as <code>format</code> and expects the
     * <code>ReportNotFoundException</code> to be thrown.</p>
     */
    public void testGetReport_String_ReportCategory_format_UNKNOWN_REPORT_FORMAT() {
        try {
            this.testedInstance.getReport(TestDataFactory.UNKNOWN_REPORT_FORMAT, TestDataFactory.VALID_REPORT_CATEGORY);
            Assert.fail("ReportNotFoundException should have been thrown");
        } catch (ReportNotFoundException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("ReportNotFoundException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.ReportFactory#getReport(String,com.topcoder.timetracker.report.ReportCategory)}
     * method for proper handling the invalid input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#NULL} as <code>category</code> and expects the <code>NullPointerException</code>
     * to be thrown.</p>
     */
    public void testGetReport_String_ReportCategory_category_NULL() {
        try {
            this.testedInstance.getReport(TestDataFactory.VALID_REPORT_FORMAT, null);
            Assert.fail("NullPointerException should have been thrown");
        } catch (NullPointerException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("NullPointerException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.ReportFactory#ReportFactory} for proper behavior if the configuration is
     * invalid.</p>
     *
     * <p>Removes the <code>ReportClasses</code> configuration property from the configuration namespace and expects the
     * <code>ReportConfigurationException</code> to be thrown.</p>
     */
    public void testReportFactory_NoReportsProperty() {
        String[] values = ConfigHelper.removeProperty("com.topcoder.timetracker.report.Reports", "ReportClasses");
        try {
            new ReportFactory();
            Assert.fail("ReportConfigurationException should have been thrown");
        } catch (ReportConfigurationException e) {
            // expected behavior
        } catch (Exception e) {
            fail("ReportConfigurationException was expected but the original exception is : " + e);
        } finally {
            ConfigHelper.restoreProperty("com.topcoder.timetracker.report.Reports", "ReportClasses", values);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.ReportFactory#ReportFactory} for proper behavior if the configuration is
     * invalid.</p>
     *
     * <p>Sets the <code>ReportClasses</code> configuration property from the configuration namespace to empty value and
     * expects the <code>ReportConfigurationException</code> to be thrown.</p>
     */
    public void testReportFactory_EmptyReportsProperty() {
        String[] values = ConfigHelper.setProperty("com.topcoder.timetracker.report.Reports", "ReportClasses", "");
        try {
            new ReportFactory();
            Assert.fail("ReportConfigurationException should have been thrown");
        } catch (ReportConfigurationException e) {
            // expected behavior
        } catch (Exception e) {
            fail("ReportConfigurationException was expected but the original exception is : " + e);
        } finally {
            ConfigHelper.restoreProperty("com.topcoder.timetracker.report.Reports", "ReportClasses", values);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.ReportFactory#ReportFactory} for proper behavior if the configuration is
     * invalid.</p>
     *
     * <p>Sets the <code>ReportClasses</code> configuration property from the configuration namespace to invalid value
     * and expects the <code>ReportConfigurationException</code> to be thrown.</p>
     */
    public void testReportFactory_ReportsPropertyWithUnknownClass() {
        String[] values = ConfigHelper.setProperty("com.topcoder.timetracker.report.Reports",
                                                   "ReportClasses",
                                                   "com.topcoder.UnknownClass");
        try {
            new ReportFactory();
            Assert.fail("ReportConfigurationException should have been thrown");
        } catch (ReportConfigurationException e) {
            // expected behavior
        } catch (Exception e) {
            fail("ReportConfigurationException was expected but the original exception is : " + e);
        } finally {
            ConfigHelper.restoreProperty("com.topcoder.timetracker.report.Reports", "ReportClasses", values);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.ReportFactory#ReportFactory} for proper behavior if the configuration is
     * invalid.</p>
     *
     * <p>Sets the <code>ReportClasses</code> configuration property from the configuration namespace to invalid value
     * and expects the <code>ReportConfigurationException</code> to be thrown.</p>
     */
    public void testReportFactory_ReportsPropertyWithNonReportClass() {
        String[] values = ConfigHelper.setProperty("com.topcoder.timetracker.report.Reports",
                                                   "ReportClasses",
                                                   "java.lang.String");
        try {
            new ReportFactory();
            Assert.fail("ReportConfigurationException should have been thrown");
        } catch (ReportConfigurationException e) {
            // expected behavior
        } catch (Exception e) {
            fail("ReportConfigurationException was expected but the original exception is : " + e);
        } finally {
            ConfigHelper.restoreProperty("com.topcoder.timetracker.report.Reports", "ReportClasses", values);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.ReportFactory#ReportFactory} for proper behavior if the configuration is
     * invalid.</p>
     *
     * <p>Sets the <code>ReportClasses</code> configuration property from the configuration namespace to invalid value
     * and expects the <code>ReportConfigurationException</code> to be thrown.</p>
     */
    public void testReportFactory_ReportFailingConstructor() {
        String[] values = ConfigHelper.setProperty("com.topcoder.timetracker.report.Reports",
                                                   "ReportClasses",
                                                   "com.topcoder.timetracker.report.failuretests.impl.ReportFailedConstructor");
        try {
            new ReportFactory();
            Assert.fail("ReportConfigurationException should have been thrown");
        } catch (ReportConfigurationException e) {
            // expected behavior
        } catch (Exception e) {
            fail("ReportConfigurationException was expected but the original exception is : " + e);
        } finally {
            ConfigHelper.restoreProperty("com.topcoder.timetracker.report.Reports", "ReportClasses", values);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.ReportFactory#ReportFactory} for proper behavior if the configuration is
     * invalid.</p>
     *
     * <p>Sets the <code>ReportClasses</code> configuration property from the configuration namespace to invalid value
     * and expects the <code>ReportConfigurationException</code> to be thrown.</p>
     */
    public void testReportFactory_ReportPrivateConstructor() {
        String[] values = ConfigHelper.setProperty("com.topcoder.timetracker.report.Reports",
                                                   "ReportClasses",
                                                   "com.topcoder.timetracker.report.failuretests.impl.ReportPrivateConstructor");
        try {
            new ReportFactory();
            Assert.fail("ReportConfigurationException should have been thrown");
        } catch (ReportConfigurationException e) {
            // expected behavior
        } catch (Exception e) {
            fail("ReportConfigurationException was expected but the original exception is : " + e);
        } finally {
            ConfigHelper.restoreProperty("com.topcoder.timetracker.report.Reports", "ReportClasses", values);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.ReportFactory#ReportFactory} for proper behavior if the configuration is
     * invalid.</p>
     *
     * <p>Sets the <code>ReportClasses</code> configuration property from the configuration namespace to invalid value
     * and expects the <code>ReportConfigurationException</code> to be thrown.</p>
     */
    public void testReportFactory_ReportNoDefaultConstructor() {
        String[] values = ConfigHelper.setProperty("com.topcoder.timetracker.report.Reports",
                                                   "ReportClasses",
                                                   "com.topcoder.timetracker.report.failuretests.impl.ReportNoDefaultConstructor");
        try {
            new ReportFactory();
            Assert.fail("ReportConfigurationException should have been thrown");
        } catch (ReportConfigurationException e) {
            // expected behavior
        } catch (Exception e) {
            fail("ReportConfigurationException was expected but the original exception is : " + e);
        } finally {
            ConfigHelper.restoreProperty("com.topcoder.timetracker.report.Reports", "ReportClasses", values);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.ReportFactory#ReportFactory} for proper behavior if the configuration is
     * invalid.</p>
     *
     * <p>Sets the <code>ReportClasses</code> configuration property from the configuration namespace to invalid value
     * and expects the <code>ReportConfigurationException</code> to be thrown.</p>
     */
    public void testReportFactory_DuplicateReports() {
        String[] values = ConfigHelper.setProperty("com.topcoder.timetracker.report.Reports",
                                                   "ReportClasses",
                                                   new String[] {ReportDuplicateFormatCategory1.class.getName(),
                                                                 ReportDuplicateFormatCategory2.class.getName()});
        try {
            new ReportFactory();
            Assert.fail("ReportException should have been thrown");
        } catch (ReportException e) {
            // expected behavior
            Assert.assertFalse("ReportException should have been thrown", e instanceof ReportConfigurationException);
        } catch (Exception e) {
            fail("ReportException was expected but the original exception is : " + e);
        } finally {
            ConfigHelper.restoreProperty("com.topcoder.timetracker.report.Reports", "ReportClasses", values);
        }
    }
}
