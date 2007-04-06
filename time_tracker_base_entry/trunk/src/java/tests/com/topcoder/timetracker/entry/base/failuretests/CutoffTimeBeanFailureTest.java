/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.base.failuretests;

import com.topcoder.timetracker.entry.base.CutoffTimeBean;

import junit.framework.TestCase;

/**
 * <p>
 * The failure test for <code>CutoffTimeBean</code>.
 * </p>
 *
 * @author Hacker_QC
 * @version 3.2
 */
public class CutoffTimeBeanFailureTest extends TestCase {
    
    /**
     * The CutoffTimeBean instance used for failure test.
     */
    private CutoffTimeBean bean;
    
    /**
     * Sets up the failure test environment.
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        bean = new CutoffTimeBean();
    }

    /**
     * Clears the failure test environment.
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        bean = null;
    }

    /**
     * <p>
     * Failure test for setCompanyId(long companyId).
     * </p>
     *
     * <p>
     * companyId is 0.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSetCompanyIdFailure1() throws Exception {
        try {
            bean.setCompanyId(0);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for setCompanyId(long companyId).
     * </p>
     *
     * <p>
     * companyId is negative.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSetCompanyIdFailure2() throws Exception {
        try {
            bean.setCompanyId(-1);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for setCutoffTime(Date cutoffTime).
     * </p>
     *
     * <p>
     * cutoffTime is null.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSetCutoffTimeFailure1() throws Exception {
        try {
            bean.setCutoffTime(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }
}
