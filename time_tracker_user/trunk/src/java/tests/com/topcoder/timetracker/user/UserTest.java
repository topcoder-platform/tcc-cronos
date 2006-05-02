/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

import junit.framework.TestCase;

/**
 * <p>
 * Tests the User class, for "regular" and "exception" cases.
 * </p>
 *
 * @see User
 *
 * @author TCSDEVELOPER
 * @version 1.0
*/
public class UserTest extends TestCase {

    /** The id number of a user, for testing. */
    private static final int USER_ID_NUMBER = 2;

    /** The username to test with. */
    private static final String USER_NAME = "name";

    /** The user store to test with. */
    private static final String USER_STORE_NAME = "storeName";

    private static final String EMAIL = "name@mail.com";

    /** The User that we're testing. */
    private User user;


    /**
     * Instantiate a User to test.
     *
     * @throws Exception never under normal conditions.
     */
    protected void setUp() throws Exception {
        super.setUp();

        user = new User(USER_ID_NUMBER, USER_NAME, USER_STORE_NAME, EMAIL);
    }


    /**
     * Tests that the constructor sets initial values correctly.  Since there are no
     * "setters", the values set in the constructor are the values for the life of the
     * object, and so we also test the getUserStoreName and getId methods here.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testCtorSetsInitialValues() throws Exception {

        // Make sure the parameters are set in the ctor.
        assertEquals("UserStoreName is not set by ctor!",
                     USER_STORE_NAME,
                     user.getUserStoreName());
        assertEquals("Name is not set by ctor!",
                     USER_NAME,
                     user.getName());
        assertEquals("id is not set by ctor!",
                     USER_ID_NUMBER,
                     user.getId());
        assertEquals("email is not set by ctor!",
                     EMAIL,
                     user.getEmail());
    }


    /**
     * Test that the constructor throws NullPointerException if name or storeName is null.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testCtorThrowsNPE() throws Exception {

        try {
            new User(USER_ID_NUMBER, null, null, EMAIL);
            fail("ctor didn't throw NullPointerException");
        } catch (NullPointerException expected) {
            assertNotNull(expected);
        }

        try {
            new User(USER_ID_NUMBER, USER_NAME, null, EMAIL);
            fail("ctor didn't throw NullPointerException");
        } catch (NullPointerException expected) {
            assertNotNull(expected);
        }

        try {
            new User(USER_ID_NUMBER, null, USER_STORE_NAME, EMAIL);
            fail("ctor didn't throw NullPointerException");
        } catch (NullPointerException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test that the constructor throws IllegalArgumentException if name or storeName is empty.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testCtorThrowsIAEEmptyString() throws Exception {

        // this should succeed - we don't trim
        new User(USER_ID_NUMBER, USER_NAME, " ", EMAIL);

        try {
            new User(USER_ID_NUMBER, "", "", null);
            fail("ctor didn't throw IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            assertNotNull(expected);
        }

        try {
            new User(USER_ID_NUMBER, USER_NAME, "", EMAIL);
            fail("ctor didn't throw IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            assertNotNull(expected);
        }

        try {
            new User(USER_ID_NUMBER, "", USER_STORE_NAME, EMAIL);
            fail("ctor didn't throw IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            assertNotNull(expected);
        }
    }


    /**
     * Test that the constructor throws IllegalArgumentException if the ID is non-positive.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testCtorThrowsIAENonPositiveId() throws Exception {

        try {
            new User(0, USER_NAME, USER_STORE_NAME, EMAIL);
            fail("ctor didn't throw IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            assertNotNull(expected);
        }

        try {
            new User(-1, USER_NAME, USER_STORE_NAME, EMAIL);
            fail("ctor didn't throw IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            assertNotNull(expected);
        }
    }

    /**
     * Test that the constructor accepts a null email address.
     *
     * @throws Exception Never under normal conditions.
     */
    public void testCtorNullEmail() throws Exception {
        user = new User(USER_ID_NUMBER, USER_NAME, USER_STORE_NAME, null);
        assertNull("The email should be null", user.getEmail());
    }
}
