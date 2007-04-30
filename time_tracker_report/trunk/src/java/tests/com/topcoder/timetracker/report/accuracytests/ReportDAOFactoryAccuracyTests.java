/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report.accuracytests;

import com.topcoder.timetracker.report.ReportDAOFactory;

import junit.framework.TestCase;

/**
 * The test of ReportDAOFactory.
 *
 * @author brain_cn
 * @version 1.0
 */
public class ReportDAOFactoryAccuracyTests extends TestCase {
    /** The tset ReportDAOFactory for testing. */
    private ReportDAOFactory instance;

    /**
     * <p>
     * Setup the test case.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        ConfigHelper.loadNamespaces();
        instance = new ReportDAOFactory();
    }

    /**
     * <p>
     * Tear down the test case.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        ConfigHelper.releaseNamespaces();
    }

    /**
     * Test method for 'ReportDAOFactory()'
     */
    public void testReportDAOFactory() {
        assertNotNull("the instance is created", instance);
    }

    /**
     * Test method for 'ReportDAOFactory(String)'
     *
     * @throws Exception to JUnit
     */
    public void testReportDAOFactoryString() throws Exception {
        assertNotNull("the instance is created", new ReportDAOFactory(ReportDAOFactory.DEFAULT_NAMESPACE));
    }

    /**
     * Test method for 'getReportDAO(String)'
     */
    public void testGetReportDAO() {
        assertNotNull("Miss informix ReportDAO", instance.getReportDAO(ReportDAOFactory.INFORMIX));
        assertNotNull("Miss type1", instance.getReportDAO("type1"));
        assertNotNull("Miss type2", instance.getReportDAO("type2"));
    }

}
