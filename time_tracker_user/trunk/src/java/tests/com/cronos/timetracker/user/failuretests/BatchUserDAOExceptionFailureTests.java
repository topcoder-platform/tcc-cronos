/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.user.failuretests;

import junit.framework.TestCase;

import com.cronos.timetracker.user.BatchUserDAOException;
import com.cronos.timetracker.user.User;

/**
 * <p>
 * Failure unit test cases for the BatchUserDAOException class.
 * </p>
 * @author agh
 * @version 2.0
 * @since 2.0
 */
public class BatchUserDAOExceptionFailureTests extends TestCase {

    /**
     * <p>
     * Tests BatchUserDAOException(String, Throwable[], User[]) for failure. Passes empty message,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testConstructor1() {
        try {
            new BatchUserDAOException(" ", new Throwable[] {new Exception()}, new User[] {new User()});
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests BatchUserDAOException(String, Throwable[], User[]) for failure. Passes null as first array,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testConstructor2() {
        try {
            new BatchUserDAOException("aaa", null, new User[] {new User()});
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests BatchUserDAOException(String, Throwable[], User[]) for failure. Passes null as second array,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testConstructor3() {
        try {
            new BatchUserDAOException("aaa", new Throwable[] {new Exception()}, null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests BatchUserDAOException(String, Throwable[], User[]) for failure. Passes null company in array,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testConstructor4() {
        try {
            new BatchUserDAOException("aaa", new Throwable[] {new Exception()}, new User[] {null});
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests BatchUserDAOException(String, Throwable[], User[]) for success. Passes empty arrays
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testConstructor5() {
        new BatchUserDAOException("aaa", new Throwable[] {}, new User[] {});
    }

    /**
     * <p>
     * Tests BatchUserDAOException(String, Throwable[], User[]) for failure. Passes array with different
     * sizes, IllegalArgumentException is expected.
     * </p>
     */
    public void testConstructor6() {
        try {
            new BatchUserDAOException(" ", new Throwable[] {new Exception(), new Exception()},
                    new User[] {new User()});
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
