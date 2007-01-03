/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.user.failuretests;

import junit.framework.TestCase;

import com.cronos.timetracker.common.RejectReason;

/**
 * <p>
 * Failure unit test cases for the RejectReason class.
 * </p>
 * @author agh
 * @version 2.0
 * @since 2.0
 */
public class RejectReasonFailureTests extends TestCase {
    /**
     * <p>
     * Tests setCompanyId(String) for failure. Passes 0, IllegalArgumentException is expected.
     * </p>
     */
    public void testSetCompanyId1() {
        try {
            (new RejectReason()).setCompanyId(0);
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
            (new RejectReason()).setCompanyId(-3);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests setDescription(String) for failure. Passes empty string, IllegalArgumentException is expected.
     * </p>
     */
    public void testSetDescription1() {
        try {
            (new RejectReason()).setDescription("   ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
