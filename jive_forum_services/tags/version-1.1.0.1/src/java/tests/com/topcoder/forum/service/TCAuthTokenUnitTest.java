/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.forum.service;

import com.jivesoftware.base.AuthToken;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.Serializable;


/**
 * <p>
 * Unit tests for <code>TCAuthToken</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TCAuthTokenUnitTest extends TestCase {
    /**
     * <p>Returns the test suite of this class.</p>
     *
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(TCAuthTokenUnitTest.class);
    }

    /**
     * <p>
     * Accuracy test for constructor <code>TCAuthToken(long)</code>.
     * </p>
     *
     * <p>
     * Verify that the instance is created successfully, and the <code>TCAuthToken</code> is
     * instance of <code>AuthToken</code> and <code>Serializable</code>.
     * </p>
     */
    public void testCtor() {
        TCAuthToken token = new TCAuthToken(1);
        assertNotNull("Unable to create TCAuthToken instance.", token);

        assertTrue("The TCAuthToken should be instance of AuthToken.",
            token instanceof AuthToken);
        assertTrue("The TCAuthToken should be instance of Serializable.",
            token instanceof Serializable);
    }

    /**
     * <p>
     * Failure test for <code>TCAuthToken(long)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the userId is not above 0, <code>IllegalArgumentException</code> is
     * expected.
     * </p>
     */
    public void testCtor_Failure() {
        try {
            new TCAuthToken(0);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Failure test for <code>TCAuthToken(long)</code>.
     * </p>
     *
     * <p>
     * Failure cause: If the userId is not above 0, <code>IllegalArgumentException</code> is
     * expected.
     * </p>
     */
    public void testCtor_Failure1() {
        try {
            new TCAuthToken(-2);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * Accuracy test for <code>getUserID()</code>.
     * </p>
     *
     * <p>
     * Verify that the userId is gotten successfully.
     * </p>
     */
    public void testGetUserID() {
        TCAuthToken token = new TCAuthToken(1);
        assertEquals("The userId should match.", 1, token.getUserID());

        token = new TCAuthToken(100);
        assertEquals("The userId should match.", 100, token.getUserID());
    }

    /**
     * <p>
     * Accuracy test for <code>isAnonymous()</code>.
     * </p>
     *
     * <p>
     * Verify that it always returns false.
     * </p>
     */
    public void testIsAnonymous() {
        TCAuthToken token = new TCAuthToken(1);
        assertFalse("The token should be non-anonymous.", token.isAnonymous());

        token = new TCAuthToken(100);
        assertFalse("The token should be non-anonymous.", token.isAnonymous());
    }
}
