/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.timetracker.report.informix;

import com.topcoder.timetracker.report.BaseTestCase;
import com.topcoder.timetracker.report.ReportConfigException;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;

/**
 * <p>
 * This class provides failure tests for <code>InformixReportDAO</code> class. It tests:
 * <ol>
 * <li> InformixReportDAO() constructor</li>
 * <li> InformixReportDAO(String) constructor</li>
 * <li> getExpenseEntriesReport(Filter ,String[] ,boolean[]) method</li>
 * <li> getFixedBillingEntriesReport(Filter ,String[] ,boolean[]) method</li>
 * <li> getReport(String ,String[] ,boolean[]) method</li>
 * </ol>
 * </p>
 *
 * @author rinoavd
 * @version 3.1
 */
public class TestInformixReportDAOFailure extends BaseTestCase {
    /**
     * <p>
     * This is an instance of InformixReportDAO which will be used in test cases.
     * </p>
     */
    private InformixReportDAO dao = null;

    /**
     * <p>
     * Setup the test case.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        this.setupDatabase(DATA_FILE_LOCATION);
    }

    /**
     * <p>
     * Tear down the test case.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        this.clearDatabase();
        super.tearDown();
    }

    /**
     * <p>
     * Retrieves a FixedBillingEntriesReportBuilder to use in test cases.
     * </p>
     *
     * @return the DAO to test
     * @throws Exception to JUnit
     */
    private InformixReportDAO getInformixReportDAO() throws Exception {
        return new InformixReportDAO();
    }

    /**
     * <p>
     * Failure test of <code>InformixReportDAO(String)</code> constructor.
     * </p>
     *
     * <p>
     * The default namespace is not found. ReportConfigException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInformixReportDAO_Ctor_1_Failure_MissingDefaultNamespace() throws Exception {
        this.removeConfigManagerNS();
        ConfigManager cm = ConfigManager.getInstance();
        cm.add(ERROR_CONFIGURATION_FILE_LOCATION);
        try {
            dao = new InformixReportDAO();
            fail("ReportConfigException was expected.");
        } catch (ReportConfigException e) {
            assertTrue(
                    "cause of the exception should be UnknownNamespaceException",
                    e.getCause() instanceof UnknownNamespaceException);
        }
    }

    /**
     * <p>
     * Failure test of <code>InformixReportDAO(String)</code> constructor.
     * </p>
     *
     * <p>
     * An unknown namespace is used. ReportConfigException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInformixReportDAO_Ctor_2_Failure_1_UnknownNamespace() throws Exception {
        this.removeConfigManagerNS();
        ConfigManager cm = ConfigManager.getInstance();
        cm.add(ERROR_CONFIGURATION_FILE_LOCATION);
        try {
            dao = new InformixReportDAO("unknown");
            fail("ReportConfigException was expected.");
        } catch (ReportConfigException e) {
            assertTrue(
                    "cause of the exception should be UnknownNamespaceException",
                    e.getCause() instanceof UnknownNamespaceException);
        }
    }

    /**
     * <p>
     * Failure test of <code>InformixReportDAO(String)</code> constructor.
     * </p>
     *
     * <p>
     * A key of a builder doesn't exist.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInformixReportDAO_Ctor_2_Failure_2_MissingBuilderKey() throws Exception {
        this.removeConfigManagerNS();
        ConfigManager cm = ConfigManager.getInstance();
        cm.add(ERROR_CONFIGURATION_FILE_LOCATION);
        try {
            dao = new InformixReportDAO("com.topcoder.timetracker.report.InformixReportDAO_invalid_1");
            fail("ReportConfigException was expected.");
        } catch (ReportConfigException e) {
            assertTrue(
                    "cause of the exception should be InvalidClassSpecificationException",
                    e.getCause() instanceof InvalidClassSpecificationException);
        }
    }

    /**
     * <p>
     * Failure test of <code>getFixedBillingEntriesReport()</code> method.
     * </p>
     *
     * <p>
     * filter = null, sortingColumns is null, but ascendingOrders is not null.
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInformixReportDAO_getFixedBillingEntriesReport_Failure_1_NullColumnNotNullOrder()
        throws Exception {
        dao = this.getInformixReportDAO();
        try {
            dao.getFixedBillingEntriesReport(null, null, new boolean[] {true });
            fail("IllegalArgumentException was expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test of <code>getFixedBillingEntriesReport()</code> method.
     * </p>
     *
     * <p>
     * filter = null, sortingColumns is not null, but ascendingOrders is null.
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInformixReportDAO_getFixedBillingEntriesReport_Failure_2_NotNullColumnNullOrder()
        throws Exception {
        dao = this.getInformixReportDAO();
        try {
            dao.getFixedBillingEntriesReport(null, new String[] {"project_name" }, null);
            fail("IllegalArgumentException was expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test of <code>getFixedBillingEntriesReport()</code> method.
     * </p>
     *
     * <p>
     * filter = null, sortingColumns and ascendingOrders contain different number of elements.
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInformixReportDAO_getFixedBillingEntriesReport_Failure_3_DifferentLengths()
        throws Exception {
        dao = this.getInformixReportDAO();
        try {
            dao.getFixedBillingEntriesReport(
                    null,
                    new String[] {"project_name" },
                    new boolean[] {true, false });
            fail("IllegalArgumentException was expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test of <code>getFixedBillingEntriesReport()</code> method.
     * </p>
     *
     * <p>
     * filter = null, sortingColumns and ascendingOrders contain same number of elements. But there
     * is a invalid element in sortingColumns (null value). IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInformixReportDAO_getFixedBillingEntriesReport_Failure_4_ColumnsContainNull()
        throws Exception {
        dao = this.getInformixReportDAO();
        try {
            dao.getFixedBillingEntriesReport(null, new String[] {"project_name", null }, new boolean[] {true,
                false });
            fail("IllegalArgumentException was expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test of <code>getFixedBillingEntriesReport()</code> method.
     * </p>
     *
     * <p>
     * filter = null, sortingColumns and ascendingOrders contain same number of elements. But there
     * is a invalid element in sortingColumns (empty string). IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInformixReportDAO_getFixedBillingEntriesReport_Failure_5_ColumnsContainEmptyString()
        throws Exception {
        dao = this.getInformixReportDAO();
        try {
            dao.getFixedBillingEntriesReport(null, new String[] {"project_name", "  " }, new boolean[] {true,
                false });
            fail("IllegalArgumentException was expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test of <code>getReport()</code> method.
     * </p>
     *
     * <p>
     * type = null. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInformixReportDAO_getReport_Failure_1_NullType() throws Exception {
        dao = this.getInformixReportDAO();
        try {
            dao.getReport(null, null, null, null);
            fail("IllegalArgumentException was expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test of <code>getReport()</code> method.
     * </p>
     *
     * <p>
     * type = empty string. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInformixReportDAO_getReport_Failure_2_EmptyType() throws Exception {
        dao = this.getInformixReportDAO();
        try {
            dao.getReport(" ", null, null, null);
            fail("IllegalArgumentException was expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test of <code>getReport()</code> method.
     * </p>
     *
     * <p>
     * type is valid, but sortingColumns and ascendingOrders are not. IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInformixReportDAO_getReport_Failure_3_InvalidColumnsAndOrders() throws Exception {
        dao = this.getInformixReportDAO();
        try {
            dao.getReport(
                    InformixReportDAO.FIXED_BILLING_ENTRIES,
                    null,
                    new String[] {null },
                    new boolean[] {true });
            fail("IllegalArgumentException was expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }
}
