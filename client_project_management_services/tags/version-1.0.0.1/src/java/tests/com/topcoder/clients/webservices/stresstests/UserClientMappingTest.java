/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.clients.webservices.stresstests;

import com.topcoder.clients.webservices.usermapping.impl.UserClientMapping;

import junit.framework.TestCase;

/**
 * <p>
 * Stress tests for <code>UserClientMapping</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UserClientMappingTest extends TestCase {

    /**
     * Represents the instance to test.
     */
    UserClientMapping mapping;
    /**
     * <p>
     * Sets up the environment.
     * </p>
     *
     * @throws java.lang.Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        mapping = new UserClientMapping();
    }

    /**
     * <p>
     * Clean up the environment.
     * </p>
     *
     * @throws java.lang.Exception
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test method for {@link com.topcoder.clients.webservices.usermapping.impl.
     * UserClientMapping#setClientId(long)}.
     */
    public void testSetClientId() {
        long t = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            mapping.setClientId(1L);
        }
        System.out.println("The set of client id took " + (System.currentTimeMillis() - t));

        t = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            mapping.setClientId(1L);
        }
        System.out.println("The set of client id took " + (System.currentTimeMillis() - t));

        t = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            mapping.setClientId(1L);
        }
        System.out.println("The set of client id took " + (System.currentTimeMillis() - t));
    }

    /**
     * Test method for {@link com.topcoder.clients.webservices.usermapping.impl.
     * UserClientMapping#setUserId(long)}.
     */
    public void testSetUserId() {
        long t = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            mapping.setUserId(1L);
        }
        System.out.println("The set of user id took " + (System.currentTimeMillis() - t));

        t = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            mapping.setUserId(1L);
        }
        System.out.println("The set of user id took " + (System.currentTimeMillis() - t));

        t = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            mapping.setUserId(1L);
        }
        System.out.println("The set of user id took " + (System.currentTimeMillis() - t));
    }

}
