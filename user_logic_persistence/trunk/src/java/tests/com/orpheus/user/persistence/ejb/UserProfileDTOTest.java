/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence.ejb;

import java.io.Serializable;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.orpheus.user.persistence.impl.Admin;
import com.orpheus.user.persistence.impl.Player;

/**
 * <p>
 * Tests the UserProfileDTO class.
 * </p>
 *
 * @author mpaulse
 * @version 1.0
 */
public class UserProfileDTOTest extends TestCase {

    /**
     * <p>
     * The UserProfileDTO instance to test.
     * </p>
     */
    private UserProfileDTO profile = null;

    /**
     * <p>
     * Creates the UserProfileDTO instance to test.
     * </p>
     */
    protected void setUp() {
        profile = new UserProfileDTO();
    }

    /**
     * <p>
     * Tests the UserProfileDTO() constructor. The newly created instance should
     * not be null.
     * </p>
     */
    public void testCtor() {
        // The constructor was invoked in the setUp() method.
        assertNotNull("The UserProfileDTO instance should not be null", profile);
    }

    /**
     * <p>
     * Tests the put(String key, Object value) method. Once the method has been
     * called, the contains(String key) method should return true when given the
     * same "key" argument.
     * </p>
     */
    public void testPut() {
        Player player = new Player(123);
        profile.put(UserProfileDTO.PLAYER_KEY, player);
        assertTrue("The value was not put into the UserProfileDTO successfully",
                   profile.contains(UserProfileDTO.PLAYER_KEY));
    }

    /**
     * <p>
     * Tests the get(String key) method when the value corresponding to the
     * "key" argument exists in the user profile DTO. The return value of the
     * method should be equal to the "value" argument given to the put(String
     * key, String value) method.
     * </p>
     */
    public void testGetWhenValueExists() {
        // First insert a value.
        Admin admin = new Admin(987);
        profile.put(UserProfileDTO.ADMIN_KEY, admin);

        // Now, check if the return value is the same.
        assertEquals("The retrieved value is incorrect", admin, profile.get(UserProfileDTO.ADMIN_KEY));
    }

    /**
     * <p>
     * Tests that the get(String key) method returns null when the value
     * corresponding to the "key" argument does not exist in the user profile
     * DTO.
     * </p>
     */
    public void testGetWhenValueDoesNotExist() {
        assertNull("Null should be returned: the value is not in the UserProfileDTO", profile.get("IDontExist"));
    }

    /**
     * <p>
     * Tests that the contains(String key) method returns false when the value
     * corresponding to the "key" argument does not exist in the user profile
     * DTO.
     * </p>
     * <p>
     * The case where the value DOES exist in the user profile DTO is tested by
     * the testPut() unit test.
     * </p>
     */
    public void testContainsWhenValueDoesNotExist() {
        assertFalse("False should be returned: the value is not in the UserProfileDTO", profile.contains("IDontExist"));
    }

    /**
     * <p>
     * Tests that the UserProfileDTO.ADMIN_KEY constant is equal to "admin".
     * </p>
     */
    public void testAdminKeyConstant() {
        assertEquals("ADMIN_KEY is incorrect", UserProfileDTO.ADMIN_KEY, "admin");
    }

    /**
     * <p>
     * Tests that the UserProfileDTO.CONTACT_INFO_KEY constant is equal to
     * "contact_info".
     * </p>
     */
    public void testContactInfoKeyConstant() {
        assertEquals("CONTACT_INFO_KEY is incorrect", UserProfileDTO.CONTACT_INFO_KEY, "contact_info");
    }

    /**
     * <p>
     * Tests that the UserProfileDTO.PLAYER_KEY constant is equal to "player".
     * </p>
     */
    public void testPlayerKeyConstant() {
        assertEquals("PLAYER_KEY is incorrect", UserProfileDTO.PLAYER_KEY, "player");
    }

    /**
     * <p>
     * Tests that the UserProfileDTO.SPONSOR_KEY constant is equal to "sponsor".
     * </p>
     */
    public void testSponsorKeyConstant() {
        assertEquals("SPONSOR_KEY is incorrect", UserProfileDTO.SPONSOR_KEY, "sponsor");
    }

    /**
     * <p>
     * Tests that UserProfileDTO implements the Serializable interface.
     * </p>
     */
    public void testInterface() {
        assertTrue("UserProfileDTO should implement the Serializable interface", profile instanceof Serializable);
    }

    /**
     * <p>
     * Returns the test suite containing all the unit tests in this test case.
     * </p>
     *
     * @return the test suite for this test case
     */
    public static Test suite() {
        return new TestSuite(UserProfileDTOTest.class);
    }

}
