/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.timetracker.rejectreason.RejectEmail;

/**
 * Failure test for <code>{@link com.topcoder.timetracker.rejectreason.RejectEmail}</code> class.
 *
 * @author mittu
 * @version 1.0
 */
public class RejectEmailTest extends TestCase {
    /**
     * <p>
     * Represents the RejectEmail to test.
     * </p>
     */
    private RejectEmail email;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to junit.
     */
    protected void setUp() throws Exception {
        email = new RejectEmail();
    }

    /**
     * Failure test for <code>{@link RejectEmail#setBody(String)}</code>.
     */
    public void testMethodSetBody_IAE() {
        try {
            email.setBody(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            email.setBody(" ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link RejectEmail#setCompanyId(long)}</code>.
     */
    public void testMethodSetCompanyId_long() {
        try {
            email.setCompanyId(0);
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
        return new TestSuite(RejectEmailTest.class);
    }
}
