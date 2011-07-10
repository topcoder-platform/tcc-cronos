/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.failuretests;

import com.topcoder.web.reg.actions.profile.AccountInfoTaskChecker;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for AccountInfoTaskChecker.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class AccountInfoTaskCheckerFailureTests extends TestCase {

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(AccountInfoTaskCheckerFailureTests.class);
    }

    /**
     * <p>
     * Tests AccountInfoTaskChecker#getTaskReport(User) for failure.
     * It tests the case that when user is null and expects IllegalArgumentException.
     * </p>
     */
    public void testGetTaskReport_NullUser() {
        AccountInfoTaskChecker instance = new AccountInfoTaskChecker();
        try {
            instance.getTaskReport(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

}