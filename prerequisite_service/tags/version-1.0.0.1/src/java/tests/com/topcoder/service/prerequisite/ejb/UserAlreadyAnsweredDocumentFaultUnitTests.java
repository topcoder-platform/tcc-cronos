/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite.ejb;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit test for <code>{@link UserAlreadyAnsweredDocumentFault}</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UserAlreadyAnsweredDocumentFaultUnitTests extends TestCase {

    /**
     * <p>
     * Represents the <code>UserAlreadyAnsweredDocumentFault</code> instance used in tests.
     * </p>
     */
    private UserAlreadyAnsweredDocumentFault userAlreadyAnsweredDocumentFault;

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(UserAlreadyAnsweredDocumentFaultUnitTests.class);
    }

    /**
     * <p>
     * Setup the testing environment.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        userAlreadyAnsweredDocumentFault = new UserAlreadyAnsweredDocumentFault();
    }

    /**
     * <p>
     * Unit test for <code>UserAlreadyAnsweredDocumentFault#UserAlreadyAnsweredDocumentFault()</code> constructor.
     * </p>
     * <p>
     * Instance should be always created.
     * </p>
     */
    public void testUserAlreadyAnsweredDocumentFault_accuracy() {
        assertNotNull("Instance should be always created.", userAlreadyAnsweredDocumentFault);
    }

    /**
     * <p>
     * Unit test for <code>UserAlreadyAnsweredDocumentFault#getUserId()</code> method.
     * </p>
     * <p>
     * It should return 0, if not set.
     * </p>
     */
    public void testGetUserId_default() {
        assertEquals("Should return 0.", 0, userAlreadyAnsweredDocumentFault.getUserId());
    }

    /**
     * <p>
     * Unit test for <code>UserAlreadyAnsweredDocumentFault#setUserId(long)</code> method.
     * </p>
     * <p>
     * All value are valid to set.
     * </p>
     */
    public void testSetUserId_accuracy() {
        userAlreadyAnsweredDocumentFault.setUserId(-1);
        assertEquals("Incorrect user id.", -1, userAlreadyAnsweredDocumentFault.getUserId());

        userAlreadyAnsweredDocumentFault.setUserId(0);
        assertEquals("Incorrect user id.", 0, userAlreadyAnsweredDocumentFault.getUserId());

        userAlreadyAnsweredDocumentFault.setUserId(1);
        assertEquals("Incorrect user id.", 1, userAlreadyAnsweredDocumentFault.getUserId());
    }
}
