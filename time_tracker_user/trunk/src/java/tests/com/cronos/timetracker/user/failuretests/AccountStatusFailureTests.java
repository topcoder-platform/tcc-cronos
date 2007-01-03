/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.user.failuretests;

import junit.framework.TestCase;

import com.cronos.timetracker.user.AccountStatus;

/**
 * <p>
 * Failure unit test cases for the AccountStatus class.
 * </p>
 * @author agh
 * @version 2.0
 * @since 2.0
 */
public class AccountStatusFailureTests extends TestCase {

    /**
     * <p>
     * Tests setDescription(String) for failure. Passes empty string, IllegalArgumentException is expected.
     * </p>
     */
    public void testSetDescription1() {
        AccountStatus status = new AccountStatus();
        try {
            status.setDescription(" ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
