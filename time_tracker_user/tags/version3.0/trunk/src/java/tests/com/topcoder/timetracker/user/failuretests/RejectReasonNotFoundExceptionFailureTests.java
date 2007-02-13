/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.failuretests;

import junit.framework.TestCase;

import com.topcoder.timetracker.common.RejectReason;
import com.topcoder.timetracker.common.RejectReasonNotFoundException;

/**
 * <p>
 * Failure unit test cases for the RejectReasonNotFoundException class.
 * </p>
 * @author agh
 * @version 2.0
 * @since 2.0
 */
public class RejectReasonNotFoundExceptionFailureTests extends TestCase {

    /**
     * <p>
     * Tests RejectReasonNotFoundException(String, Throwable, RejectReason) for failure. Passes empty message,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testConstructor1() {
        try {
            new RejectReasonNotFoundException(" ", new Exception(), new RejectReason());
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
