/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report.accuracytests;

import com.topcoder.timetracker.report.informix.ExpenseEntriesReportBuilder;
import com.topcoder.timetracker.report.informix.InformixReportSearchBuilder;

import junit.framework.TestCase;

/**
 * The test of InformixReportSearchBuilder.
 *
 * @author brain_cn
 * @version 1.0
 */
public class InformixReportSearchBuilderAccuracyTests extends TestCase {
    /** The tset InformixReportSearchBuilder for testing. */
    private InformixReportSearchBuilder instance;

    /**
     * <p>
     * Setup the test case.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        ConfigHelper.loadNamespaces();
        instance = new ExpenseEntriesReportBuilder();
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
     * Test method for 'InformixReportSearchBuilder()'
     */
    public void testInformixReportSearchBuilder() {
        assertNotNull("the instance is created", instance);
    }

}
