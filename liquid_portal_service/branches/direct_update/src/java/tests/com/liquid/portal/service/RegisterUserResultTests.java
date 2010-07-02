/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.liquid.portal.service;

import junit.framework.TestCase;

/**
 * <p>
 * UnitTest cases of the <code>RegisterUserResult</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class RegisterUserResultTests extends TestCase {
    /**
     * <p>
     * Represents the instance of <code>RegisterUserResult</code>.
     * </p>
     */
    private RegisterUserResult result;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        result = new RegisterUserResult();
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {

    }

    /**
     * <p>
     * Accuracy test case for <code>getUserId()</code> and
     * <code>setUserId(long)</code>.
     * </p>
     */
    public void testSetGetUserId() {
        result.setUserId(11L);
        assertEquals(11L, result.getUserId());
    }
}
