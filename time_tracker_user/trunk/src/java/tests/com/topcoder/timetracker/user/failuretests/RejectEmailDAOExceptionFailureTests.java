/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.failuretests;

import junit.framework.TestCase;

import com.topcoder.timetracker.common.RejectEmail;
import com.topcoder.timetracker.common.RejectEmailDAOException;

/**
 * <p>
 * Failure unit test cases for the RejectEmailDAOException class.
 * </p>
 * @author agh
 * @version 2.0
 * @since 2.0
 */
public class RejectEmailDAOExceptionFailureTests extends TestCase {

    /**
     * <p>
     * Tests RejectEmailDAOException(String, Throwable, RejectEmail) for failure. Passes empty message,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testConstructor1() {
        try {
            new RejectEmailDAOException(" ", new Exception(), new RejectEmail());
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
