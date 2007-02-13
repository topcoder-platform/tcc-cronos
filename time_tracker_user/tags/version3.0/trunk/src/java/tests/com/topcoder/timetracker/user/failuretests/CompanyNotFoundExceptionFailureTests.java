/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.failuretests;

import junit.framework.TestCase;

import com.topcoder.timetracker.company.Company;
import com.topcoder.timetracker.company.CompanyNotFoundException;

/**
 * <p>
 * Failure unit test cases for the CompanyNotFoundException class.
 * </p>
 * @author agh
 * @version 2.0
 * @since 2.0
 */
public class CompanyNotFoundExceptionFailureTests extends TestCase {

    /**
     * <p>
     * Tests CompanyNotFoundException(String, Throwable, Company) for failure. Passes empty message,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testConstructor1() {
        try {
            new CompanyNotFoundException(" ", new Exception(), new Company());
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
