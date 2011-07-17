/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.failuretests;

import com.topcoder.web.reg.actions.profile.ContactInfoTaskChecker;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for ContactInfoTaskChecker.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class ContactInfoTaskCheckerFailureTests extends TestCase {

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(ContactInfoTaskCheckerFailureTests.class);
    }

    /**
     * <p>
     * Tests ContactInfoTaskChecker#getTaskReport(User) for failure.
     * It tests the case that when user is null and expects IllegalArgumentException.
     * </p>
     */
    public void testGetTaskReport_NullUser() {
        ContactInfoTaskChecker instance = new ContactInfoTaskChecker();
        try {
            instance.getTaskReport(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

}