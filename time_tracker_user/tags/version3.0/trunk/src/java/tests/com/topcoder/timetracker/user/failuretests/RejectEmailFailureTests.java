/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.failuretests;

import junit.framework.TestCase;

import com.topcoder.timetracker.common.RejectEmail;

/**
 * <p>
 * Failure unit test cases for the RejectEmail class.
 * </p>
 * @author agh
 * @version 2.0
 * @since 2.0
 */
public class RejectEmailFailureTests extends TestCase {

    /**
     * <p>
     * Tests setBody(String) for failure. Passes empty string, IllegalArgumentException is expected.
     * </p>
     */
    public void testSetBody1() {
        try {
            (new RejectEmail()).setBody(" ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests setCompanyId(String) for failure. Passes 0, IllegalArgumentException is expected.
     * </p>
     */
    public void testSetCompanyId1() {
        try {
            (new RejectEmail()).setCompanyId(0);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests setCompanyId(String) for failure. Passes negative argument, IllegalArgumentException is expected.
     * </p>
     */
    public void testSetCompanyId2() {
        try {
            (new RejectEmail()).setCompanyId(-3);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
