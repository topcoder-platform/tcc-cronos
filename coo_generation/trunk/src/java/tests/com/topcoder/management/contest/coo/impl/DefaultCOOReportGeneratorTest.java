/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo.impl;

import junit.framework.TestCase;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.management.contest.coo.COOReport;

/**
 *
 * <p>
 * Unit test case of {@link DefaultCOOReportGenerator}.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DefaultCOOReportGeneratorTest extends TestCase {
    /**
     * instance created for testing.
     */
    private DefaultCOOReportGenerator instance;

    /**
     * <p>
     * set up test environment.
     * </p>
     *
     * @throws Exception to JUNIT.
     */
    protected void setUp() throws Exception {
        ConfigurationObject config = TestHelper.getConfiguration("test_files/config_2.xml");

        instance = new DefaultCOOReportGenerator(config);
    }

    /**
     * <p>
     * tear down test environment.
     * </p>
     *
     * @throws Exception to JUNIT.
     */
    protected void tearDown() throws Exception {
        instance = null;
    }

    /**
     * test constructor.
     *
     * @throws Exception to JUNIT.
     */
    public void testCtor() throws Exception {
        assertNotNull("fail to create instance.", instance);
    }

    /**
     * <p>
     * accuracy test for <code>generateCOOReport()</code> method.
     * </p>
     *
     * @throws Exception to JUNIT.
     */
    public void ttestGenerateCOOReport() throws Exception {
        COOReport report = instance.generateCOOReport(13);
        assertEquals("should be 1", 1, report.getProjectId());
        assertEquals("should be COO Generator", "COO Generator", report.getContestData().getComponentName());

    }

    /**
     * <p>
     * failure test for <code>generateCOOReport()</code> method.
     * </p>
     * <p>
     * project id is not positive.
     * </p>
     *
     * @throws Exception to JUNIT.
     */
    public void testGenerateCOOReport_fail1() throws Exception {
        try {
            instance.generateCOOReport(-2);
            fail("IAE should throw");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

}
