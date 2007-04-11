/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.timetracker.report;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;
import com.topcoder.timetracker.report.informix.InformixReportDAO;

/**
 * <p>
 * This class tests the <code>ReportDAOFactory</code> class. It tests:
 * <ul>
 * <li>ReportDAOFactory() constructor</li>
 * <li>ReportDAOFactory(String) constructor</li>
 * <li>getReportDAO(String) </li>
 * </ul>
 * </p>
 *
 * @author rinoavd
 * @version 3.1
 */
public class TestReportDAOFactory extends BaseTestCase {

    /**
     * <p>
     * Accuracy test of <code>ReportDAOFactory()</code> constructor.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testReportDAOFactory_Ctor1_Acc() throws Exception {
        new ReportDAOFactory();
    }

    /**
     * <p>
     * Failure test of <code>ReportDAOFactory()</code> constructor.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testReportDAOFactory_Ctor1_Failure_UnknownNamespace() throws Exception {
        this.removeConfigManagerNS();
        ConfigManager cm = ConfigManager.getInstance();
        cm.add(ERROR_CONFIGURATION_FILE_2_LOCATION);
        try {
            new ReportDAOFactory();
            fail("ReportConfigException was expected.");
        } catch (ReportConfigException e) {
            assertTrue(
                    "cause of the exception should be UnknownNamespaceException",
                    e.getCause() instanceof UnknownNamespaceException);
        }
    }

    /**
     * <p>
     * Accuracy test of <code>ReportDAOFactory(String)</code> constructor.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testReportDAOFactory_Ctor2_Acc() throws Exception {
        new ReportDAOFactory(ReportDAOFactory.DEFAULT_NAMESPACE);
    }

    /**
     * <p>
     * Failure test of <code>ReportDAOFactory(String)</code> constructor.
     * </p>
     *
     * <p>
     * An unknown namespace is used.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testReportDAOFactory_Ctor2_Failure_UnknownNamespace() throws Exception {
        try {
            new ReportDAOFactory("unknown");
            fail("ReportConfigException was expected.");
        } catch (ReportConfigException e) {
            assertTrue(
                    "The cause should be a UnknownNamespaceException",
                    e.getCause() instanceof UnknownNamespaceException);
        }
    }

    /**
     * <p>
     * Accuracy test of <code>getReportDAO()</code> method.
     * </p>
     *
     * <p>
     * Test if the correct builder is returned.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testReportDAOFactory_getReportDAO_Acc1_CorrectBuilderReturned()
        throws Exception {
        ReportDAOFactory factory = new ReportDAOFactory();
        ReportDAO dao = factory.getReportDAO(ReportDAOFactory.INFORMIX);
        assertTrue("This DAO should be InformixReportDAO.", dao instanceof InformixReportDAO);
    }

    /**
     * <p>
     * Accuracy test of <code>getReportDAO()</code> method.
     * </p>
     *
     * <p>
     * If an unknown type is passed in, null should be returned.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testReportDAOFactory_getReportDAO_Acc2_UnknownType() throws Exception {
        ReportDAOFactory factory = new ReportDAOFactory();
        ReportDAO dao = factory.getReportDAO("unknown");
        assertNull("null was expected.", dao);
    }
}
