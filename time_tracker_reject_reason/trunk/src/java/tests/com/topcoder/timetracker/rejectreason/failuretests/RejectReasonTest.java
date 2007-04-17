/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.timetracker.rejectreason.RejectReason;

/**
 * Failure test for <code>{@link com.topcoder.timetracker.rejectreason.RejectReason}</code> class.
 *
 * @author mittu
 * @version 1.0
 */
public class RejectReasonTest extends TestCase {
    /**
     * <p>
     * Represents the RejectReason to test.
     * </p>
     */
    private RejectReason reason;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to junit.
     */
    protected void setUp() throws Exception {
        reason = new RejectReason();
    }

    /**
     * Failure test for <code>{@link RejectReason#setCompanyId(long)}</code>.
     */
    public void testMethodSetCompanyId_long() {
        try{
            reason.setCompanyId(0);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link RejectReason#setDescription(String)}</code>.
     */
    public void testMethodSetDescription_String() {
        try{
            reason.setDescription(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try{
            reason.setDescription(" ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Returns all tests.
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(RejectReasonTest.class);
    }
}
