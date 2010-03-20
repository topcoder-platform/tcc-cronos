/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo.failuretests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.management.contest.coo.COOReport;

/**
 * <p>
 * Set of failure tests for COOReport,
 * </p>
 * @author vilain
 * @version 1.0
 */
public class COOReportFailureTests {

    /**
     * Instance of COOReport for testing.
     */
    private COOReport cooReport;

    /**
     * Setting up environment.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        cooReport = new COOReport();
    }

    /**
     * Method under test COOReport.setContestData(ContestData). Failure testing
     * of exception in case contestData is null.
     * @throws Exception wraps all exceptions
     */
    @Test
    public final void testSetContestDataFailureNull() throws Exception {
        try {
            cooReport.setContestData(null);
            Assert.fail("IllegalArgumentException is expected since contestData is null");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Method under test COOReport.setComponentDependencies(List). Failure
     * testing of exception in case componentDependencies is null.
     * @throws Exception wraps all exceptions
     */
    @Test
    public final void testSetComponentDependenciesFailureNull() throws Exception {
        try {
            cooReport.setComponentDependencies(null);
            Assert.fail("IllegalArgumentException is expected since componentDependencies is null");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Method under test COOReport.setProjectId(long). Failure testing of
     * exception in case projectId is negative.
     * @throws Exception wraps all exceptions
     */
    @Test
    public final void testSetProjectIdFailureNegative() throws Exception {
        try {
            cooReport.setProjectId(-1);
            Assert.fail("IllegalArgumentException is expected since projectId is negative");
        } catch (IllegalArgumentException e) {
            // good
        }
    }
}