/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.failuretests;

import junit.framework.TestCase;

import com.topcoder.timetracker.user.User;

/**
 * <p>
 * Failure unit test cases for the User class.
 * </p>
 * @author agh
 * @version 2.0
 * @since 2.0
 */
public class UserFailureTests extends TestCase {
    /**
     * <p>
     * The User instance used for testing.
     * </p>
     */
    private User user = null;

    /**
     * <p>
     * Creates User instance.
     * </p>
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        user = new User();
    }

    /**
     * <p>
     * Tests setAccountStatus(AccountStatus) for failure. Passes null, IllegalArgumentException is expected.
     * </p>
     */
    public void testSetAccountStatus1() {
        try {
            user.setAccountStatus(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests setAddress(Address) for failure. Passes null, IllegalArgumentException is expected.
     * </p>
     */
    public void testSetAddress1() {
        try {
            user.setAddress(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests setAlgorithmName(String) for failure. Passes empty string, IllegalArgumentException is expected.
     * </p>
     */
    public void testSetAlgorithmName1() {
        try {
            user.setAlgorithmName(" ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests setAlgorithmName(String) for failure. Passes name that is not contained in encryption repository,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testSetAlgorithmName2() {
        try {
            user.setAlgorithmName("abc");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests setCompanyId(String) for failure. Passes 0, IllegalArgumentException is expected.
     * </p>
     */
    public void testSetCompanyId1() {
        try {
            user.setCompanyId(0);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests setCompanyId(String) for failure. Passes negative argument, IllegalArgumentException is expected.
     * </p>
     */
    public void testSetCompanyId2() {
        try {
            user.setCompanyId(-3);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests setContactInfo(Contact) for failure. Passes null, IllegalArgumentException is expected.
     * </p>
     */
    public void testSetContactInfo() {
        try {
            user.setContactInfo(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests setFirstName(String) for failure. Passes empty string, IllegalArgumentException is expected.
     * </p>
     */
    public void testSetFirstName1() {
        try {
            user.setFirstName(" ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests setlastName(String) for failure. Passes empty string, IllegalArgumentException is expected.
     * </p>
     */
    public void testSetLastName1() {
        try {
            user.setLastName(" ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests setPassword(String) for failure. Passes empty string, IllegalArgumentException is expected.
     * </p>
     */
    public void testSetPassword1() {
        try {
            user.setPassword(" ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests setPassword(String) for failure. Passes passcode without specifying algorithm name,
     * IllegalStateException is expected.
     * </p>
     */
    public void testSetPassword2() {
        try {
            user.setPassword("abc");
            fail("IllegalStateException should be thrown");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests setPhoneNumber(String) for failure. Passes empty string, IllegalArgumentException is expected.
     * </p>
     */
    public void testSetPhoneNumber1() {
        try {
            user.setPhoneNumber(" ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests setUsername(String) for failure. Passes empty string, IllegalArgumentException is expected.
     * </p>
     */
    public void testSetUsername1() {
        try {
            user.setUsername(" ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
