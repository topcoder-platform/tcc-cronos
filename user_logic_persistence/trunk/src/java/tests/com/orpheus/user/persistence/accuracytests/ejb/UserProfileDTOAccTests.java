/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence.accuracytests.ejb;

import com.orpheus.user.persistence.ejb.UserProfileDTO;
import junit.framework.TestCase;

/**
 * Accuracy test cases for <code>UserProfileDTO</code> class.
 *
 * @author tuenm
 * @version 1.0
 */
public class UserProfileDTOAccTests extends TestCase {

    /**
     * <p>
     * The UserProfileDTO instance to test.
     * </p>
     */
    private UserProfileDTO profile = null;

    /**
     * <p>
     * Setup for each test case.
     * </p>
     */
    protected void setUp() {
        profile = new UserProfileDTO();
    }

    /**
     * <p>
     * Accuracy test of the <code>put()</code> method.
     * </p>
     */
    public void testPutKey() {
        String test = "test";
        profile.put(UserProfileDTO.ADMIN_KEY, test);
        assertEquals("Not the expected value.", test, profile.get(UserProfileDTO.ADMIN_KEY));
    }

    /**
     * <p>
     * Accuracy test of the <code>get()</code> method with a non-existent key.
     * </p>
     */
    public void testGetKeyNull() {
        assertNull("Value should be null.", profile.get(UserProfileDTO.ADMIN_KEY));
    }

    /**
     * <p>
     * Accuracy test of the <code>ADMIN_KEY</code> field.
     * </p>
     */
    public void testAdminKey() {
        assertEquals("Not the expected key value.", "admin", UserProfileDTO.ADMIN_KEY);
    }

    /**
     * <p>
     * Accuracy test of the <code>PLAYER_KEY</code> field.
     * </p>
     */
    public void testPlayerKey() {
        assertEquals("Not the expected key value.", "player", UserProfileDTO.PLAYER_KEY);
    }

    /**
     * <p>
     * Accuracy test of the <code>SPONSOR_KEY</code> field.
     * </p>
     */
    public void testSponsorKey() {
        assertEquals("Not the expected key value.", "sponsor", UserProfileDTO.SPONSOR_KEY);
    }
}
