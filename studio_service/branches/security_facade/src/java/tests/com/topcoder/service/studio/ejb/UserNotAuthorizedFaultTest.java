/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.ejb;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Some tests for UserNotAuthorizedFault class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UserNotAuthorizedFaultTest extends TestCase {
    /**
     * Bean to test.
     */
    private UserNotAuthorizedFault target;

    /**
     * <p>Returns the test suite of this class.</p>
     *
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(UserNotAuthorizedFaultTest.class);
    }

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception to junit
     */
    protected void setUp() throws Exception {
        target = new UserNotAuthorizedFault();
    }

    /**
     * <p>Tears down test environment.</p>
     *
     * @throws Exception to junit
     */
    protected void tearDown() throws Exception {
    }

    /**
     * Tests empty constructor.
     */
    public void testConstructor() {
        assertNotNull("created instance", new UserNotAuthorizedFault());
    }

    /**
     * Tests setter/getter for userIdNotAuthorized field.
     */
    public void testUserIdNotAuthorized() {
        assertEquals("default value", 0, target.getUserIdNotAuthorized());
        target.setUserIdNotAuthorized(35);
        assertEquals("new value", 35, target.getUserIdNotAuthorized());
    }
}
