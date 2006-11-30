/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence.impl;

import java.io.Serializable;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Tests the abstract User class.
 * </p>
 *
 * @author mpaulse
 * @version 1.0
 */
public class UserTest extends TestCase {

    /**
     * <p>
     * The User profile instance to test.
     * </p>
     */
    private User user = null;

    /**
     * <p>
     * Creates the test User profile instance. Since the User class is abstract,
     * a Admin instance is instantiated instead.
     * </p>
     */
    protected void setUp() {
        user = new Admin(1023881);
    }

    /**
     * <p>
     * Tests the setId(long id) method with a valid positive argument. The
     * return value of the getId() method should be equal to the method
     * argument.
     * </p>
     */
    public void testSetIdWithValidPositiveArg() {
        long id = 967183;
        user.setId(id);
        assertEquals("The ID is incorrect", id, user.getId());
    }

    /**
     * <p>
     * Tests the setId(long id) method with a valid negative argument. The
     * return value of the getId() method should be equal to the method
     * argument.
     * </p>
     */
    public void testSetIdWithValidNegativeArg() {
        long id = -967183;
        user.setId(id);
        assertEquals("The ID is incorrect", id, user.getId());
    }

    /**
     * <p>
     * Tests the setHandle(String handle) method with a valid non-null non-empty
     * string argument. The return value of the getHandle() method should be
     * equal to the method argument.
     * </p>
     */
    public void testSetHandleWithValidNonNullNonEmptyArg() {
        String handle = "tcsdeveloper";
        user.setHandle(handle);
        assertEquals("The handle is incorrect", handle, user.getHandle());
    }

    /**
     * <p>
     * Tests the setHandle(String handle) method with a valid null argument. The
     * return value of the getHandle() method should be null.
     * </p>
     */
    public void testSetHandleWithValidNullArg() {
        user.setHandle(null);
        assertNull("The handle should be null", user.getHandle());
    }

    /**
     * <p>
     * Tests the setHandle(String handle) method with a valid empty string
     * argument. The return value of the getHandle() method should be equal to
     * the method argument.
     * </p>
     */
    public void testSetHandleWithValidEmptyArg() {
        String handle = "  ";
        user.setHandle(handle);
        assertEquals("The handle is incorrect", handle, user.getHandle());
    }

    /**
     * <p>
     * Tests the setEmail(String email) method with a valid non-null non-empty
     * string argument. The return value of the getEmail() method should be
     * equal to the method argument.
     * </p>
     */
    public void testSetEmailWithValidNonNullNonEmptyArg() {
        String email = "tcsdeveloper@topcodersoftware.com";
        user.setEmail(email);
        assertEquals("The email is incorrect", email, user.getEmail());
    }

    /**
     * <p>
     * Tests the setEmail(String email) method with a valid null argument. The
     * return value of the getEmail() method should be null.
     * </p>
     */
    public void testSetEmailWithValidNullArg() {
        user.setEmail(null);
        assertNull("The email should be null", user.getEmail());
    }

    /**
     * <p>
     * Tests the setEmail(String email) method with a valid empty string
     * argument. The return value of the getEmail() method should be equal to
     * the method argument.
     * </p>
     */
    public void testSetEmailWithValidEmptyArg() {
        String email = " ";
        user.setEmail(email);
        assertEquals("The email is incorrect", email, user.getEmail());
    }

    /**
     * <p>
     * Tests the setPassword(String password) method with a valid non-null
     * non-empty string argument. The return value of the getPassword() method
     * should be equal to the method argument.
     * </p>
     */
    public void testSetPasswordWithValidNonNullNonEmptyArg() {
        String password = "repolevedsct";
        user.setPassword(password);
        assertEquals("The password is incorrect", password, user.getPassword());
    }

    /**
     * <p>
     * Tests the setPassword(String password) method with a valid null argument.
     * The return value of the getPassword() method should be null.
     * </p>
     */
    public void testSetPasswordWithValidNullArg() {
        user.setPassword(null);
        assertNull("The password should be null", user.getPassword());
    }

    /**
     * <p>
     * Tests the setPassword(String password) method with a valid empty string
     * argument. The return value of the getPassword() method should be equal to
     * the method argument.
     * </p>
     */
    public void testSetPasswordWithValidEmptyArg() {
        String password = " ";
        user.setPassword(password);
        assertEquals("The password is incorrect", password, user.getPassword());
    }

    /**
     * <p>
     * Tests the setActive(String active) method with a valid non-null non-empty
     * string argument. The return value of the getActive() method should be
     * equal to the method argument.
     * </p>
     */
    public void testSetActiveWithValidNonNullNonEmptyArg() {
        String active = "true";
        user.setActive(active);
        assertEquals("The active flag is incorrect", active, user.getActive());
    }

    /**
     * <p>
     * Tests the setActive(String active) method with a valid null argument. The
     * return value of the getActive() method should be null.
     * </p>
     */
    public void testSetActiveWithValidNullArg() {
        user.setActive(null);
        assertNull("The active flag should be null", user.getActive());
    }

    /**
     * <p>
     * Tests the setActive(String active) method with a valid empty string
     * argument. The return value of the getActive() method should be equal to
     * the method argument.
     * </p>
     */
    public void testSetActiveWithValidEmptyArg() {
        String active = " ";
        user.setActive(active);
        assertEquals("The active flag is incorrect", active, user.getActive());
    }

    /**
     * <p>
     * Tests that UserProfileDTO implements the Serializable interface.
     * </p>
     */
    public void testInterface() {
        assertTrue("User should implement the Serializable interface", user instanceof Serializable);
    }

    /**
     * <p>
     * Returns the test suite containing all the unit tests in this test case.
     * </p>
     *
     * @return the test suite for this test case
     */
    public static Test suite() {
        return new TestSuite(UserTest.class);
    }

}
