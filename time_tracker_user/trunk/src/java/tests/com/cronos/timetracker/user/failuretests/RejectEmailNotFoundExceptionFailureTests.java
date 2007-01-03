/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.user.failuretests;

import junit.framework.TestCase;

import com.cronos.timetracker.common.RejectEmail;
import com.cronos.timetracker.common.RejectEmailNotFoundException;

/**
 * <p>
 * Failure unit test cases for the RejectEmailNotFoundException class.
 * </p>
 * @author agh
 * @version 2.0
 * @since 2.0
 */
public class RejectEmailNotFoundExceptionFailureTests extends TestCase {

    /**
     * <p>
     * Tests RejectEmailNotFoundException(String, Throwable, RejectEmail) for failure. Passes empty message,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testConstructor1() {
        try {
            new RejectEmailNotFoundException(" ", new Exception(), new RejectEmail());
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
