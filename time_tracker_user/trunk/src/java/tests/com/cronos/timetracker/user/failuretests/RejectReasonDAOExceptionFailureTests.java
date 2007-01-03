/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.user.failuretests;

import junit.framework.TestCase;

import com.cronos.timetracker.common.RejectReason;
import com.cronos.timetracker.common.RejectReasonDAOException;

/**
 * <p>
 * Failure unit test cases for the RejectReasonDAOException class.
 * </p>
 * @author agh
 * @version 2.0
 * @since 2.0
 */
public class RejectReasonDAOExceptionFailureTests extends TestCase {

    /**
     * <p>
     * Tests RejectReasonDAOException(String, Throwable, RejectReason) for failure. Passes empty message,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testConstructor1() {
        try {
            new RejectReasonDAOException(" ", new Exception(), new RejectReason());
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
