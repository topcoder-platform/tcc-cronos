/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report.failuretests.dbhandler;

import com.topcoder.timetracker.report.ReportConfigurationException;
import com.topcoder.timetracker.report.dbhandler.DBHandlerFactory;
import com.topcoder.timetracker.report.dbhandler.DBHandlerNotFoundException;
import com.topcoder.timetracker.report.failuretests.ConfigHelper;
import com.topcoder.timetracker.report.failuretests.TestDataFactory;
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.File;

/**
 * <p>A failure test for {@link com.topcoder.timetracker.report.dbhandler.DBHandlerFactory} class.</p>
 *
 * @author isv
 * @version 1.0
 */
public class DBHandlerFactoryTest extends TestCase {

    /**
     * <p>An instance of {@link com.topcoder.timetracker.report.dbhandler.DBHandlerFactory} which is tested. This instance is initialized in {@link #setUp()}
     * method and released in {@link #tearDown()} method.<p>
     */
    private DBHandlerFactory testedInstance = null;

    /**
     * <p>Gets the test suite for {@link com.topcoder.timetracker.report.dbhandler.DBHandlerFactory} class.</p>
     *
     * @return a <code>TestSuite</code> providing the tests for {@link com.topcoder.timetracker.report.dbhandler.DBHandlerFactory} class.
     */
    public static Test suite() {
        return new TestSuite(DBHandlerFactoryTest.class);
    }

    /**
     * <p>Sets up the fixture. This method is called before a test is executed.</p>
     *
     * @throws Exception if any error occurs.
     */
    protected void setUp() throws Exception {
        ConfigHelper.releaseNamespaces();
        ConfigHelper.loadConfiguration(new File("failure/FailureTestsConfig.xml"));
        this.testedInstance = new DBHandlerFactory();
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
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.dbhandler.DBHandlerFactory#getDBHandler(String)} method for proper handling the invalid
     * input arguments.</p>
     *
     * <p>Passes <code>null</code> as <code>dbhandlerName</code> and expects the <code>NullPointerException</code> to be
     * thrown.</p>
     */
    public void testGetDBHandler_String_dbhandlerName_NULL() {
        try {
            this.testedInstance.getDBHandler(null);
            Assert.fail("NullPointerException should have been thrown");
        } catch (NullPointerException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("NullPointerException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.dbhandler.DBHandlerFactory#getDBHandler(String)} method for proper handling the invalid
     * input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#ZERO_LENGTH_STRING} as <code>dbhandlerName</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.</p>
     */
    public void testGetDBHandler_String_dbhandlerName_ZERO_LENGTH_STRING() {
        try {
            this.testedInstance.getDBHandler(TestDataFactory.ZERO_LENGTH_STRING);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.dbhandler.DBHandlerFactory#getDBHandler(String)} method for proper handling the invalid
     * input arguments.</p>
     *
     * <p>Passes {@link com.topcoder.timetracker.report.failuretests.TestDataFactory#WHITESPACE_ONLY_STRING} as
     * <code>dbhandlerName</code> and expects the <code>IllegalArgumentException</code> to be thrown.</p>
     */
    public void testGetDBHandler_String_dbhandlerName_WHITESPACE_ONLY_STRING() {
        try {
            this.testedInstance.getDBHandler(TestDataFactory.WHITESPACE_ONLY_STRING);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.dbhandler.DBHandlerFactory#getDBHandler(String)} method for proper handling the invalid
     * input arguments.</p>
     *
     * <p>Passes {@link "UnknownDBHandler"} as <code>dbhandlerName</code> and expects the
     * <code>ReportNotFoundException</code> to be thrown.</p>
     */
    public void testGetDBHandler_String_dbhandlerName_UnknownDBHandler() {
        try {
            this.testedInstance.getDBHandler("UnknownDBHandler");
            Assert.fail("DBHandlerNotFoundException should have been thrown");
        } catch (DBHandlerNotFoundException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("ReportNotFoundException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.dbhandler.DBHandlerFactory#DBHandlerFactory} for proper behavior if the configuration is
     * invalid.</p>
     *
     * <p>Removes the <code>DBHandlers</code> configuration property from the configuration namespace and expects the
     * <code>ReportConfigurationException</code> to be thrown.</p>
     */
    public void testDBHandlerFactory_NoDBHandlersProperty() {
        String[] values = ConfigHelper.removeProperty("com.topcoder.timetracker.report.DBHandlers", "DBHandlers");
        try {
            new DBHandlerFactory();
            Assert.fail("ReportConfigurationException should have been thrown");
        } catch (ReportConfigurationException e) {
            // expected behavior
        } catch (Exception e) {
            fail("ReportConfigurationException was expected but the original exception is : " + e);
        } finally {
            ConfigHelper.restoreProperty("com.topcoder.timetracker.report.DBHandlers", "DBHandlers", values);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.dbhandler.DBHandlerFactory#DBHandlerFactory} for proper behavior if the configuration is
     * invalid.</p>
     *
     * <p>Sets the <code>DBHandlers</code> configuration property from the configuration namespace to empty value and
     * expects the <code>ReportConfigurationException</code> to be thrown.</p>
     */
    public void testDBHandlerFactory_EmptyDBHandlersProperty() {
        String[] values = ConfigHelper.setProperty("com.topcoder.timetracker.report.DBHandlers", "DBHandlers", "");
        try {
            new DBHandlerFactory();
            Assert.fail("ReportConfigurationException should have been thrown");
        } catch (ReportConfigurationException e) {
            // expected behavior
        } catch (Exception e) {
            fail("ReportConfigurationException was expected but the original exception is : " + e);
        } finally {
            ConfigHelper.restoreProperty("com.topcoder.timetracker.report.DBHandlers", "DBHandlers", values);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.dbhandler.DBHandlerFactory#DBHandlerFactory} for proper behavior if the configuration is
     * invalid.</p>
     *
     * <p>Sets the <code>DBHandlers</code> configuration property from the configuration namespace to invalid value and
     * expects the <code>ReportConfigurationException</code> to be thrown.</p>
     */
    public void testDBHandlerFactory_DBHandlersPropertyWithoutMatchingClass() {
        String[] values = ConfigHelper.setProperty("com.topcoder.timetracker.report.DBHandlers", "DBHandlers", "NonExistingDBHandler");
        try {
            new DBHandlerFactory();
            Assert.fail("ReportConfigurationException should have been thrown");
        } catch (ReportConfigurationException e) {
            // expected behavior
        } catch (Exception e) {
            fail("ReportConfigurationException was expected but the original exception is : " + e);
        } finally {
            ConfigHelper.restoreProperty("com.topcoder.timetracker.report.DBHandlers", "DBHandlers", values);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.dbhandler.DBHandlerFactory#DBHandlerFactory} for proper behavior if the configuration is
     * invalid.</p>
     *
     * <p>Sets the <code>FailureDBHandler</code> configuration property from the configuration namespace to invalid
     * value and expects the <code>ReportConfigurationException</code> to be thrown.</p>
     */
    public void testDBHandlerFactory_DBHandlerPrivateConstructor() {
        String[] values = ConfigHelper.setProperty("com.topcoder.timetracker.report.DBHandlers", "FailureDBHandler", "com.topcoder.timetracker.report.failuretests.impl.DBHandlerPrivateConstructor");
        try {
            new DBHandlerFactory();
            Assert.fail("ReportConfigurationException should have been thrown");
        } catch (ReportConfigurationException e) {
            // expected behavior
        } catch (Exception e) {
            fail("ReportConfigurationException was expected but the original exception is : " + e);
        } finally {
            ConfigHelper.restoreProperty("com.topcoder.timetracker.report.DBHandlers", "FailureDBHandler", values);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.dbhandler.DBHandlerFactory#DBHandlerFactory} for proper behavior if the configuration is
     * invalid.</p>
     *
     * <p>Sets the <code>FailureDBHandler</code> configuration property from the configuration namespace to invalid
     * value and expects the <code>ReportConfigurationException</code> to be thrown.</p>
     */
    public void testDBHandlerFactory_DBHandlerNoDefaultConstructor() {
        String[] values = ConfigHelper.setProperty("com.topcoder.timetracker.report.DBHandlers", "FailureDBHandler", "com.topcoder.timetracker.report.failuretests.impl.DBHandlerNoDefaultConstructor");
        try {
            new DBHandlerFactory();
            Assert.fail("ReportConfigurationException should have been thrown");
        } catch (ReportConfigurationException e) {
            // expected behavior
        } catch (Exception e) {
            fail("ReportConfigurationException was expected but the original exception is : " + e);
        } finally {
            ConfigHelper.restoreProperty("com.topcoder.timetracker.report.DBHandlers", "FailureDBHandler", values);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.dbhandler.DBHandlerFactory#DBHandlerFactory} for proper behavior if the configuration is
     * invalid.</p>
     *
     * <p>Sets the <code>FailureDBHandler</code> configuration property from the configuration namespace to invalid
     * value and expects the <code>ReportConfigurationException</code> to be thrown.</p>
     */
    public void testDBHandlerFactory_DBHandlerFailedConstructor() {
        String[] values = ConfigHelper.setProperty("com.topcoder.timetracker.report.DBHandlers", "FailureDBHandler", "com.topcoder.timetracker.report.failuretests.impl.DBHandlerFailedConstructor");
        try {
            new DBHandlerFactory();
            Assert.fail("ReportConfigurationException should have been thrown");
        } catch (ReportConfigurationException e) {
            // expected behavior
        } catch (Exception e) {
            fail("ReportConfigurationException was expected but the original exception is : " + e);
        } finally {
            ConfigHelper.restoreProperty("com.topcoder.timetracker.report.DBHandlers", "FailureDBHandler", values);
        }
    }
}
