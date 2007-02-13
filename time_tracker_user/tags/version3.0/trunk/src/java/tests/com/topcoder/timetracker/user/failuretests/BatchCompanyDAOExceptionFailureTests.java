/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.failuretests;

import junit.framework.TestCase;

import com.topcoder.timetracker.company.BatchCompanyDAOException;
import com.topcoder.timetracker.company.Company;

/**
 * <p>
 * Failure unit test cases for the BatchCompanyDAOException class.
 * </p>
 * @author agh
 * @version 2.0
 * @since 2.0
 */
public class BatchCompanyDAOExceptionFailureTests extends TestCase {

    /**
     * <p>
     * Tests BatchCompanyDAOException(String, Throwable[], Company[]) for failure. Passes empty message,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testConstructor1() {
        try {
            new BatchCompanyDAOException(" ", new Throwable[] {new Exception()},
                    new Company[] {new Company()});
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests BatchCompanyDAOException(String, Throwable[], Company[]) for failure. Passes null as first array,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testConstructor2() {
        try {
            new BatchCompanyDAOException("aaa", null, new Company[] {new Company()});
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests BatchCompanyDAOException(String, Throwable[], Company[]) for failure. Passes null as second
     * array, IllegalArgumentException is expected.
     * </p>
     */
    public void testConstructor3() {
        try {
            new BatchCompanyDAOException("aaa", new Throwable[] {new Exception()}, null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests BatchCompanyDAOException(String, Throwable[], Company[]) for failure. Passes null company in
     * array, IllegalArgumentException is expected.
     * </p>
     */
    public void testConstructor4() {
        try {
            new BatchCompanyDAOException("aaa", new Throwable[] {new Exception()}, new Company[] {null});
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests BatchCompanyDAOException(String, Throwable[], Company[]) for success. Passes empty arrays.
     * </p>
     */
    public void testConstructor5() {
            new BatchCompanyDAOException("aaa", new Throwable[] {}, new Company[] {});
    }

    /**
     * <p>
     * Tests BatchCompanyDAOException(String, Throwable[], Company[]) for failure. Passes array with different
     * sizes, IllegalArgumentException is expected.
     * </p>
     */
    public void testConstructor6() {
        try {
            new BatchCompanyDAOException(" ", new Throwable[] {new Exception(), new Exception()},
                    new Company[] {new Company()});
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
