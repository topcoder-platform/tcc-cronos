/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report.failuretests;

import com.topcoder.timetracker.report.ReportConfigException;
import com.topcoder.timetracker.report.ReportDAOFactory;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Failure test for <code>ReportDAOFactory</code>.
 *
 * @author enefem21
 * @version 3.1
 */
public class ReportDAOFactoryTest extends TestCase {

    /**
     * <p>
     * Return the suite for this unit test.
     * </p>
     *
     * @return the suite
     */
    public static Test suite() {
        return new TestSuite(ReportDAOFactoryTest.class);
    }

    /**
     * Tears the unit test down.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        TestHelper.clearNamespaces();

        super.tearDown();
    }

    /**
     * Test constructor for failure. Condition: the objectFactoryNamespace is empty string. Expect:
     * <code>ReportConfigException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testReportDAOFactoryEmptyObjectFactoryNamespace() throws Exception {
        TestHelper.loadConfiguration("failure/config_empty_objectFactoryNamespace.xml");

        try {
            new ReportDAOFactory();
            fail("Should throw ReportConfigException");
        } catch (ReportConfigException e) {
            // expected
        }

    }

    /**
     * Test constructor for failure. Condition: the informixReportDaoNamespace is empty string. Expect:
     * <code>ReportConfigException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testReportDAOFactoryEmptyInformixReportDaoNamespace() throws Exception {
        TestHelper.loadConfiguration("failure/config_empty_informixReportDaoNamespace.xml");

        try {
            new ReportDAOFactory();
            fail("Should throw ReportConfigException");
        } catch (ReportConfigException e) {
            // expected
        }

    }

    /**
     * Test constructor for failure. Condition: contains empty reportDao. Expect:
     * <code>ReportConfigException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testReportDAOFactoryContainsEmptyReportDao() throws Exception {
        TestHelper.loadConfiguration("failure/config_contain_empty_reportDaos.xml");

        try {
            new ReportDAOFactory();
            fail("Should throw ReportConfigException");
        } catch (ReportConfigException e) {
            // expected
        }

    }

    /**
     * Test constructor for failure. Condition: the reportDaos is not valid key. Expect:
     * <code>ReportConfigException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testReportDAOFactoryReportDaosIsMissing() throws Exception {
        TestHelper.loadConfiguration("failure/config_reportDaos_is_missing.xml");

        try {
            new ReportDAOFactory();
            fail("Should throw ReportConfigException");
        } catch (ReportConfigException e) {
            // expected
        }

    }

    /**
     * Test constructor for failure. Condition: the reportDaos is not valid key. Expect:
     * <code>ReportConfigException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testReportDAOFactoryReportDaosWrongClass() throws Exception {
        TestHelper.loadConfiguration("failure/config_reportDaos_wrong_class.xml");

        try {
            new ReportDAOFactory();
            fail("Should throw ReportConfigException");
        } catch (ReportConfigException e) {
            // expected
        }

    }

    /**
     * Test constructor for failure. Condition: namespace is null. Expect: <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testReportDAOFactoryWithNamespaceNull() throws Exception {
        try {
            new ReportDAOFactory(null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test constructor for failure. Condition: namespace is empty string. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testReportDAOFactoryWithNamespaceEmptyString() throws Exception {
        try {
            new ReportDAOFactory("   \n");
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

}
