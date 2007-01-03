/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.cronos.timetracker.user;

import com.cronos.timetracker.common.Address;
import com.cronos.timetracker.common.Contact;

/**
 * <p>
 * The unit tests for {@link com.cronos.timetracker.user.User User} class.
 * It checks the method accuracy and failure.
 * </p>
 *
 * @author kr00tki
 * @version 2.0
 */
public class UserTest extends MyTestCase {
    /**
     * The test string value.
     */
    private static final String TEST_STRING = "test_string";

    /**
     * The test <code>Address</code> object.
     */
    private static final Address ADDRESS = new Address();

    /**
     * The test <code>Contact</code> object.
     */
    private static final Contact CONTACT = new Contact();

    /**
     * The test <code>AccountStatus</code> object.
     */
    private static final AccountStatus STATUS = new AccountStatus();

    /**
     * The company id used in tests.
     */
    private static final long ID = 10101010;

    /**
     * The <code>User</code> instance to test on.
     */
    private User user = null;

    /**
     * Sets up the test environment. Creates <code>User</code> object before each test.
     */
    protected void setUp() throws Exception {
        super.setUp();
        user = new User();
    }

    /**
     * <p>
     * Tests the {@link User#User()} constructor.
     * </p>
     */
    public void testUser() {
        user = new User();
        assertFalse("Should not be dirty.", user.isChanged());
    }

    /**
     * <p>
     * Tests the {@link User#getCompanyId()} method accuracy.
     * Checks if <code>zero</code> is returned when the value is not set.
     * </p>
     */
    public void testGetCompanyId() {
        assertEquals("Should not be set.", 0, user.getCompanyId());
    }

    /**
     * <p>
     * Tests the {@link User#setCompanyId(long)} method accuracy.
     * Checks if correct value is set and object is marked as changed.
     * </p>
     */
    public void testSetCompanyId() {
        user.setCompanyId(ID);
        assertEquals("Incorrect company id.", ID, user.getCompanyId());
        assertTrue("Should be marked as dirty.", user.isChanged());
    }

    /**
     * <p>
     * Tests the {@link User#setCompanyId(long)} method failure.
     * Checks if exception is thrown when the <code>id</code> is negative.
     * </p>
     */
    public void testSetCompanyId_Negative() {
        try {
            user.setCompanyId(-1);
            fail("Negative is, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the {@link User#getUsername()} method accuracy.
     * Checks if <code>null</code> is returned when the value is not set.
     * </p>
     */
    public void testGetUsername() {
        assertNull("Should not be set.", user.getUsername());
    }

    /**
     * <p>
     * Tests the {@link User#setUsername(String)} method accuracy.
     * Checks if correct value is set and object is marked as changed.
     * </p>
     */
    public void testSetUsername() {
        user.setUsername(TEST_STRING);
        assertEquals("Incorrect username.", TEST_STRING, user.getUsername());
        assertTrue("Should be marked as dirty.", user.isChanged());
    }

    /**
     * <p>
     * Tests the {@link User#setUsername(String)} method failure.
     * Checks if exception is thrown when the <code>username</code> is <code>null</code>.
     * </p>
     */
    public void testSetUsername_Null() {
        try {
            user.setUsername(null);
            fail("The username is null, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the {@link User#setUsername(String)} method failure.
     * Checks if exception is thrown when the <code>username</code> is <code>empty</code>.
     * </p>
     */
    public void testSetUsername_Empty() {
        try {
            user.setUsername(" ");
            fail("The username is empty, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the {@link User#getPassword()} method accuracy.
     * Checks if <code>null</code> is returned when the value is not set.
     * </p>
     */
    public void testGetPassword() {
        assertNull("Should not be set.", user.getAccountStatus());
    }

    /**
     * <p>
     * Tests the {@link User#setPassword(String)} method accuracy.
     * Checks if correct value is set and object is marked as changed.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testSetPassword() throws Exception  {
        user.setAlgorithmName(ALGORITHM_NAME);
        user.setUsername(TEST_STRING);
        assertEquals("Incorrect username.", TEST_STRING, user.getUsername());
        assertTrue("Should be marked as dirty.", user.isChanged());
        assertFalse("The password should be encrypted.", TEST_STRING.equals(getFieldValue(user, "password")));
    }

    /**
     * <p>
     * Tests the {@link User#setPassword(String)} method failure.
     * Checks if exception is thrown when the <code>password</code> is <code>null</code>.
     * </p>
     */
    public void testSetPassword_Null() {
        try {
            user.setPassword(null);
            fail("Password is null, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the {@link User#setPassword(String)} method failure.
     * Checks if exception is thrown when the <code>password</code> is <code>empty</code>.
     * </p>
     */
    public void testSetPassword_Empty() {
        try {
            user.setPassword(" ");
            fail("Password is empty, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the {@link User#setPassword(String)} method failure.
     * Checks if exception is thrown when the encryption algorithm name is not set.
     * </p>
     */
    public void testSetPassword_NoAlgorithm() {
        try {
            user.setPassword(TEST_STRING);
            fail("Password is empty, ISE expected.");
        } catch (IllegalStateException ex) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the {@link User#getAccountStatus()} method accuracy.
     * Checks if <code>null</code> is returned when the value is not set.
     * </p>
     */
    public void testGetAccountStatus() {
        assertNull("Should not be set.", user.getAccountStatus());
    }

    /**
     * <p>
     * Tests the {@link User#setAccountStatus(AccountStatus)} method accuracy.
     * Checks if correct value is set and object is marked as changed.
     * </p>
     */
    public void testSetAccountStatus() {
        user.setAccountStatus(STATUS);
        assertEquals("Incorrect account status.", STATUS, user.getAccountStatus());
        assertTrue("Should be marked as dirty.", user.isChanged());
    }

    /**
     * <p>
     * Tests the {@link User#setAccountStatus(AccountStatus)} method failure.
     * Checks if exception is thrown when the <code>status</code> is <code>null</code>.
     * </p>
     */
    public void testSetAccountStatus_Null() {
        try {
            user.setAccountStatus(null);
            fail("Status is null, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the {@link User#getContactInfo()} method accuracy.
     * Checks if <code>null</code> is returned when the value is not set.
     * </p>
     */
    public void testGetContactInfo() {
        assertNull("Should not be set.", user.getContactInfo());
    }

    /**
     * <p>
     * Tests the {@link User#setContactInfo(Contact)} method accuracy.
     * Checks if correct value is set and object is marked as changed.
     * </p>
     */
    public void testSetContactInfo() {
        user.setContactInfo(CONTACT);
        assertEquals("Incorrect contact.", CONTACT, user.getContactInfo());
        assertTrue("Should be marked as dirty.", user.isChanged());
    }

    /**
     * <p>
     * Tests the {@link User#setContactInfo(Contact)} method failure.
     * Checks if exception is thrown when the <code>contact</code> is <code>null</code>.
     * </p>
     */
    public void testSetContactInfo_Null() {
        try {
            user.setContactInfo(null);
            fail("Constact is null, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }


    /**
     * <p>
     * Tests the {@link User#getAddress()} method accuracy.
     * Checks if <code>null</code> is returned when the value is not set.
     * </p>
     */
    public void testGetAddress() {
        assertNull("Should not be set.", user.getAddress());
    }

    /**
     * <p>
     * Tests the {@link User#setAddress(Address)} method accuracy.
     * Checks if correct value is set and object is marked as changed.
     * </p>
     */
    public void testSetAddress() {
        user.setAddress(ADDRESS);
        assertEquals("Incorrect address.", ADDRESS, user.getAddress());
        assertTrue("Should be marked as dirty.", user.isChanged());
    }

    /**
     * <p>
     * Tests the {@link User#setAddress(Address)} method failure.
     * Checks if exception is thrown when the <code>address</code> is <code>null</code>.
     * </p>
     */
    public void testSetAddress_Null() {
        try {
            user.setAddress(null);
            fail("Address is null, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the {@link User#getAlgorithmName()} method accuracy.
     * Checks if <code>null</code> is returned when the value is not set.
     * </p>
     */
    public void testGetAlgorithmName() {
        assertNull("Should not be set.", user.getAlgorithmName());
    }

    /**
     * <p>
     * Tests the {@link User#setAlgorithmName(String)} method accuracy.
     * Checks if correct value is set and object is marked as changed.
     * </p>
     */
    public void testSetAlgorithmName() {
        user.setAlgorithmName(ALGORITHM_NAME);
        assertEquals("Incorrect algorithm name.", ALGORITHM_NAME, user.getAlgorithmName());
    }

    /**
     * <p>
     * Tests the {@link User#setAlgorithmName(String)} method failure.
     * Checks if exception is thrown when the <code>algorithmName</code> is <code>null</code>.
     * </p>
     */
    public void testSetAlgorithmName_Null() {
        try {
            user.setAlgorithmName(null);
            fail("AlgorithmName is null, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the {@link User#setAlgorithmName(String)} method failure.
     * Checks if exception is thrown when the <code>algorithmName</code> is <code>empty</code>.
     * </p>
     */
    public void testSetAlgorithmName_Empty() {
        try {
            user.setAlgorithmName(" ");
            fail("AlgorithmName is empty, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the {@link User#setAlgorithmName(String)} method failure.
     * Checks if exception is thrown when the <code>algorithmName</code> is not exists in repository.
     * </p>
     */
    public void testSetAlgorithmName_NotExists() {
        try {
            user.setAlgorithmName(TEST_STRING);
            fail("AlgorithmName not exists, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the {@link User#getFirstName()} method accuracy.
     * Checks if <code>null</code> is returned when the value is not set.
     * </p>
     */
    public void testGetFirstName() {
        assertNull("Should not be set.", user.getFirstName());
    }

    /**
     * <p>
     * Tests the {@link User#setFirstName(String)} method accuracy.
     * Checks if correct value is set and object is marked as changed.
     * </p>
     */
    public void testSetFirstName() {
        user.setFirstName(TEST_STRING);
        assertEquals("Incorrect first name.", TEST_STRING, user.getFirstName());
    }

    /**
     * <p>
     * Tests the {@link User#setFirstName(String)} method failure.
     * Checks if exception is thrown when the <code>firstName</code> is <code>null</code>.
     * </p>
     */
    public void testSetFirstName_Null() {
        try {
            user.setFirstName(null);
            fail("FirstName is null, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the {@link User#setFirstName(String)} method failure.
     * Checks if exception is thrown when the <code>firstName</code> is <code>empty</code>.
     * </p>
     */
    public void testSetFirstName_Empty() {
        try {
            user.setFirstName(" ");
            fail("FirstName is empty, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the {@link User#getLastName()} method accuracy.
     * Checks if <code>null</code> is returned when the value is not set.
     * </p>
     */
    public void testGetLastName() {
        assertNull("Should not be set.", user.getLastName());
    }

    /**
     * <p>
     * Tests the {@link User#setLastName(String)} method accuracy.
     * Checks if correct value is set and object is marked as changed.
     * </p>
     */
    public void testSetLastName() {
        user.setLastName(TEST_STRING);
        assertEquals("Incorrect last name.", TEST_STRING, user.getLastName());
    }

    /**
     * <p>
     * Tests the {@link User#setLastName(String)} method failure.
     * Checks if exception is thrown when the <code>lastName</code> is <code>null</code>.
     * </p>
     */
    public void testSetLastName_Null() {
        try {
            user.setLastName(null);
            fail("LastName is null, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the {@link User#setLastName(String)} method failure.
     * Checks if exception is thrown when the <code>lastName</code> is <code>empty</code>.
     * </p>
     */
    public void testSetLastName_Empty() {
        try {
            user.setAlgorithmName(" ");
            fail("LastName is empty, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the {@link User#getPhoneNumber()} method accuracy.
     * Checks if <code>null</code> is returned when the value is not set.
     * </p>
     */
    public void testGetPhoneNumber() {
        assertNull("Should not be set.", user.getPhoneNumber());
    }

    /**
     * <p>
     * Tests the {@link User#setPhoneNumber(String)} method accuracy.
     * Checks if correct value is set and object is marked as changed.
     * </p>
     */
    public void testSetPhoneNumber() {
        user.setPhoneNumber(TEST_STRING);
        assertEquals("Incorrect phone.", TEST_STRING, user.getPhoneNumber());
    }

    /**
     * <p>
     * Tests the {@link User#setPhoneNumber(String)} method failure.
     * Checks if exception is thrown when the <code>phoneNumber</code> is <code>null</code>.
     * </p>
     */
    public void testSetPhoneNumber_Null() {
        try {
            user.setPhoneNumber(null);
            fail("PhoneNumber is null, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the {@link User#setPhoneNumber(String)} method failure.
     * Checks if exception is thrown when the <code>phoneNumber</code> is <code>empty</code>.
     * </p>
     */
    public void testSetPhoneNumber_Empty() {
        try {
            user.setPhoneNumber(" ");
            fail("PhoneNumber is empty, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the {@link User#getEmailAddress()} method accuracy.
     * Checks if <code>null</code> is returned when the value is not set.
     * </p>
     */
    public void testGetEmailAddress() {
        assertNull("Should not be set.", user.getEmailAddress());
    }

    /**
     * <p>
     * Tests the {@link User#setEmailAddress(String)} method accuracy.
     * Checks if correct value is set and object is marked as changed.
     * </p>
     */
    public void testSetEmailAddress() {
        user.setEmailAddress(TEST_STRING);
        assertEquals("Incorrect email.", TEST_STRING, user.getEmailAddress());
    }

    /**
     * <p>
     * Tests the {@link User#setEmailAddress(String)} method failure.
     * Checks if exception is thrown when the <code>email</code> is <code>null</code>.
     * </p>
     */
    public void testSetEmailAddress_Null() {
        try {
            user.setEmailAddress(null);
            fail("EmailAddress is null, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the {@link User#setEmailAddress(String)} method failure.
     * Checks if exception is thrown when the <code>email</code> is <code>empty</code>.
     * </p>
     */
    public void testSetEmailAddress_Empty() {
        try {
            user.setEmailAddress(" ");
            fail("EmailAddress is empty, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

}
