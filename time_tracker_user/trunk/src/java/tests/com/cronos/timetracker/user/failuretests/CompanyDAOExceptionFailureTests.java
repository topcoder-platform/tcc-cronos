/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.user.failuretests;

import junit.framework.TestCase;

import com.cronos.timetracker.company.Company;
import com.cronos.timetracker.company.CompanyDAOException;

/**
 * <p>
 * Failure unit test cases for the CompanyDAOException class.
 * </p>
 * @author agh
 * @version 2.0
 * @since 2.0
 */
public class CompanyDAOExceptionFailureTests extends TestCase {

    /**
     * <p>
     * Tests CompanyDAOException(String, Throwable, Company) for failure. Passes empty messsage,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testConstructor1() {
        try {
            new CompanyDAOException(" ", new Exception(), new Company());
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
