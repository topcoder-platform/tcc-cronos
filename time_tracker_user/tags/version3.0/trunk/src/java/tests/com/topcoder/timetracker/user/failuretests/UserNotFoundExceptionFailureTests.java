/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.failuretests;

import junit.framework.TestCase;

import com.topcoder.timetracker.user.User;
import com.topcoder.timetracker.user.UserNotFoundException;

/**
 * <p>
 * Failure unit test cases for the UserNotFoundException class.
 * </p>
 * @author agh
 * @version 2.0
 * @since 2.0
 */
public class UserNotFoundExceptionFailureTests extends TestCase {

    /**
     * <p>
     * Tests UserNotFoundException(String, Throwable, User) for failure. Passes empty string,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testConstructor1() {
        try {
            new UserNotFoundException(" ", new Exception(), new User());
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
