/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

import java.lang.reflect.Field;

import com.topcoder.timetracker.common.TimeTrackerBean;
import com.topcoder.timetracker.contact.Address;
import com.topcoder.timetracker.contact.Contact;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Unit test cases for User.
 * </p>
 *
 * @author biotrail, enefem21
 * @version 3.2.1
 * @since 3.2
 */
public class UserTests extends TestCase {
    /**
     * <p>
     * The User instance for testing.
     * </p>
     */
    private User user;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     */
    protected void setUp() {
        user = new User();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     */
    protected void tearDown() {
        user = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(UserTests.class);
    }

    /**
     * <p>
     * Tests ctor User#User() for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created User instance should not be null.
     * </p>
     */
    public void testCtor() {
        assertNotNull("Failed to create a new User instance.", user);
    }

    /**
     * <p>
     * Tests User#setPassword(String) for accuracy.
     * </p>
     *
     * <p>
     * It verifies User#setPassword(String) is correct.
     * </p>
     */
    public void testSetPassword() {
        user.setPassword("password");
        assertEquals("Failed to set the password correctly.", "password", user.getPassword());
    }

    /**
     * <p>
     * Tests User#setPassword(String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when password is null and expects IllegalArgumentException.
     * </p>
     */
    public void testSetPassword_NullPassword() {
        try {
            user.setPassword(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests User#setPassword(String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when password is empty and expects IllegalArgumentException.
     * </p>
     */
    public void testSetPassword_EmptyPassword() {
        try {
            user.setPassword(" ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests User#getUsername() for accuracy.
     * </p>
     *
     * <p>
     * It verifies User#getUsername() is correct.
     * </p>
     */
    public void testGetUsername() {
        user.setUsername("username");
        assertEquals("Failed to get the user name correctly.", "username", user.getUsername());
    }

    /**
     * <p>
     * Tests User#setUsername(String) for accuracy.
     * </p>
     *
     * <p>
     * It verifies User#setUsername(String) is correct.
     * </p>
     */
    public void testSetUsername() {
        user.setUsername("username");
        assertEquals("Failed to set the user name correctly.", "username", user.getUsername());
    }

    /**
     * <p>
     * Tests User#setUsername(String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when username is null and expects IllegalArgumentException.
     * </p>
     */
    public void testSetUsername_NullUsername() {
        try {
            user.setUsername(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests User#setUsername(String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when username is empty and expects IllegalArgumentException.
     * </p>
     */
    public void testSetUsername_EmptyUsername() {
        try {
            user.setUsername(" ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests User#getCompanyId() for accuracy.
     * </p>
     *
     * <p>
     * It verifies User#getCompanyId() is correct.
     * </p>
     */
    public void testGetCompanyId() {
        user.setCompanyId(8);
        assertEquals("Failed to get the company id correctly.", 8, user.getCompanyId());
    }

    /**
     * <p>
     * Tests User#setCompanyId(long) for accuracy.
     * </p>
     *
     * <p>
     * It verifies User#setCompanyId(long) is correct.
     * </p>
     */
    public void testSetCompanyId() {
        user.setCompanyId(8);
        assertEquals("Failed to set the company id correctly.", 8, user.getCompanyId());
    }

    /**
     * <p>
     * Tests User#setCompanyId(long) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when companyId is negative and expects IllegalArgumentException.
     * </p>
     */
    public void testSetCompanyId_NegativeId() {
        try {
            user.setCompanyId(-8);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests User#getContact() for accuracy.
     * </p>
     *
     * <p>
     * It verifies User#getContact() is correct.
     * </p>
     */
    public void testGetContact() {
        Contact contact = new Contact();
        user.setContact(contact);
        assertSame("Failed to get the contact correctly.", contact, user.getContact());
    }

    /**
     * <p>
     * Tests User#setContact(Contact) for accuracy.
     * </p>
     *
     * <p>
     * It verifies User#setContact(Contact) is correct.
     * </p>
     */
    public void testSetContact() {
        Contact contact = new Contact();
        user.setContact(contact);
        assertSame("Failed to set the contact correctly.", contact, user.getContact());
    }

    /**
     * <p>
     * Tests User#setContact(Contact) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when contact is null and expects IllegalArgumentException.
     * </p>
     */
    public void testSetContact_NullContact() {
        try {
            user.setContact(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests User#getAlgorithmName() for accuracy.
     * </p>
     *
     * <p>
     * It verifies User#getAlgorithmName() is correct.
     * </p>
     */
    public void testGetAlgorithmName() {
        user.setAlgorithmName("algorithmName");
        assertEquals("Failed to get the algorithm name correctly.", "algorithmName", user.getAlgorithmName());
    }

    /**
     * <p>
     * Tests User#setAlgorithmName(String) for accuracy.
     * </p>
     *
     * <p>
     * It verifies User#setAlgorithmName(String) is correct.
     * </p>
     */
    public void testSetAlgorithmName() {
        user.setAlgorithmName("algorithmName");
        assertEquals("Failed to set the algorithm name correctly.", "algorithmName", user.getAlgorithmName());
    }

    /**
     * <p>
     * Tests User#setAlgorithmName(String) for accuracy.
     * </p>
     *
     * <p>
     * It tests the case that when algorithmName is null and expects success.
     * </p>
     */
    public void testSetAlgorithmName_NullAlgorithmName() {
        user.setAlgorithmName(null);
        assertNull("Failed to set the algorithm name correctly.", user.getAlgorithmName());
    }

    /**
     * <p>
     * Tests User#setAlgorithmName(String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when algorithmName is empty and expects IllegalArgumentException.
     * </p>
     */
    public void testSetAlgorithmName_EmptyAlgorithmName() {
        try {
            user.setAlgorithmName(" ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests User#getAddress() for accuracy.
     * </p>
     *
     * <p>
     * It verifies User#getAddress() is correct.
     * </p>
     */
    public void testGetAddress() {
        Address address = new Address();
        user.setAddress(address);
        assertSame("Failed to get the address correctly.", address, user.getAddress());
    }

    /**
     * <p>
     * Tests User#getPassword() for accuracy.
     * </p>
     *
     * <p>
     * It verifies User#getPassword() is correct.
     * </p>
     */
    public void testGetPassword() {
        user.setPassword("password");
        assertEquals("Failed to get the password correctly.", "password", user.getPassword());
    }

    /**
     * <p>
     * Tests User#getStatus() for accuracy.
     * </p>
     *
     * <p>
     * It verifies User#getStatus() is correct.
     * </p>
     */
    public void testGetStatus() {
        user.setStatus(Status.ACTIVE);
        assertEquals("Failed to get the status correctly.", Status.ACTIVE, user.getStatus());
    }

    /**
     * <p>
     * Tests User#setStatus(Status) for accuracy.
     * </p>
     *
     * <p>
     * It verifies User#setStatus(Status) is correct.
     * </p>
     */
    public void testSetStatus() {
        user.setStatus(Status.ACTIVE);
        assertEquals("Failed to set the status correctly.", Status.ACTIVE, user.getStatus());
    }

    /**
     * <p>
     * Tests User#setStatus(Status) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when status is null and expects IllegalArgumentException.
     * </p>
     */
    public void testSetStatus_NullStatus() {
        try {
            user.setStatus(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests User#setAddress(Address) for accuracy.
     * </p>
     *
     * <p>
     * It verifies User#setAddress(Address) is correct.
     * </p>
     */
    public void testSetAddress() {
        Address address = new Address();
        user.setAddress(address);
        assertSame("Failed to set the address correctly.", address, user.getAddress());
    }

    /**
     * <p>
     * Tests User#setAddress(Address) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when address is null and expects IllegalArgumentException.
     * </p>
     */
    public void testSetAddress_NullAddress() {
        try {
            user.setAddress(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * Test <code>getUserStatus</code> for accuracy. Condition: normal. Expect: returned value is as expected.
     */
    public void testGetUserStatusAccuracy() {
        UserStatus userStatus = new UserStatus();
        user.setUserStatus(userStatus);

        assertEquals("Returned value is not as expected", userStatus, user.getUserStatus());
    }

    /**
     * Test <code>setUserStatus</code> for accuracy. Condition: normal. Expect: the field is set as expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetUserStatusAccuracy() throws Exception {
        UserStatus userStatus = new UserStatus();
        user.setUserStatus(userStatus);

        // check the userStatus fields
        Field userStatusField = User.class.getDeclaredField("userStatus");
        userStatusField.setAccessible(true);
        assertEquals("The field is not set as expected", userStatus, userStatusField.get(user));

        // check the changed fields
        Field changedField = TimeTrackerBean.class.getDeclaredField("changed");
        changedField.setAccessible(true);
        assertEquals("The field is not set as expected", Boolean.TRUE, changedField.get(user));
    }

    /**
     * Test <code>getUserType</code> for accuracy. Condition: normal. Expect: returned value is as expected.
     */
    public void testGetUserTypeAccuracy() {
        UserType userType = new UserType();
        user.setUserType(userType);

        assertEquals("Returned value is not as expected", userType, user.getUserType());
    }

    /**
     * Test <code>setUserType</code> for accuracy. Condition: normal. Expect: the field is set as expected.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetUserTypeAccuracy() throws Exception {
        UserType userType = new UserType();
        user.setUserType(userType);

        // check the userType fields
        Field userTypeField = User.class.getDeclaredField("userType");
        userTypeField.setAccessible(true);
        assertEquals("The field is not set as expected", userType, userTypeField.get(user));

        // check the changed fields
        Field changedField = TimeTrackerBean.class.getDeclaredField("changed");
        changedField.setAccessible(true);
        assertEquals("The field is not set as expected", Boolean.TRUE, changedField.get(user));
    }

}