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
 * Tests the ContactInfo class.
 * </p>
 *
 * @author mpaulse
 * @version 1.0
 */
public class ContactInfoTest extends TestCase {

    /**
     * <p>
     * The ContactInfo instance to test.
     * </p>
     */
    private ContactInfo contactInfo = null;

    /**
     * <p>
     * Creates the test ContactInfo instance.
     * </p>
     */
    protected void setUp() {
        contactInfo = new ContactInfo(1234);
    }

    /**
     * <p>
     * Tests the ContactInfo(long id) constructor with a valid positive
     * argument. The newly created instance should not be null. The return value
     * of the getId() method should be equal to the constructor argument.
     * </p>
     */
    public void testCtorWithValidPositiveArg() {
        long id = 85837850;
        ContactInfo info = new ContactInfo(id);
        assertNotNull("The ContactInfo instance should not be null", info);
        assertEquals("The ID is incorrect", id, info.getId());
    }

    /**
     * <p>
     * Tests the ContactInfo(long id) constructor with a valid positive
     * argument. The newly created instance should not be null. The return value
     * of the getId() method should be equal to the constructor argument.
     * </p>
     */
    public void testCtorWithValidNegativeArg() {
        long id = -85837850;
        ContactInfo info = new ContactInfo(id);
        assertNotNull("The ContactInfo instance should not be null", info);
        assertEquals("The ID is incorrect", id, info.getId());
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
        contactInfo.setId(id);
        assertEquals("The ID is incorrect", id, contactInfo.getId());
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
        contactInfo.setId(id);
        assertEquals("The ID is incorrect", id, contactInfo.getId());
    }

    /**
     * <p>
     * Tests the setFirstName(String firstName) with a valid non-null non-empty
     * string argument. The return value of the getFirstName() method should be
     * equal to the method argument.
     * </p>
     */
    public void testSetFirstNameWithValidNonNullNonEmptyArg() {
        String firstName = "Joe";
        contactInfo.setFirstName(firstName);
        assertEquals("The first name is incorrect", firstName, contactInfo.getFirstName());
    }

    /**
     * <p>
     * Tests the setFirstName(String firstName) with a valid null argument. The
     * return value of the getFirstName() method should be null.
     * </p>
     */
    public void testSetFirstNameWithValidNullArg() {
        contactInfo.setFirstName(null);
        assertNull("The first name should be null", contactInfo.getFirstName());
    }

    /**
     * <p>
     * Tests the setFirstName(String firstName) with a valid empty string
     * argument. The return value of the getFirstName() method should be equal
     * to the method argument.
     * </p>
     */
    public void testSetFirstNameWithValidEmptyArg() {
        String firstName = " ";
        contactInfo.setFirstName(firstName);
        assertEquals("The first name is incorrect", firstName, contactInfo.getFirstName());
    }

    /**
     * <p>
     * Tests the setLastName(String lastName) with a valid non-null non-empty
     * string argument. The return value of the getLastName() method should be
     * equal to the method argument.
     * </p>
     */
    public void testSetLastNameWithValidNonNullNonEmptyArg() {
        String lastName = "User";
        contactInfo.setLastName(lastName);
        assertEquals("The last name is incorrect", lastName, contactInfo.getLastName());
    }

    /**
     * <p>
     * Tests the setLastName(String lastName) with a valid null argument. The
     * return value of the getLastName() method should be null.
     * </p>
     */
    public void testSetLastNameWithValidNullArg() {
        contactInfo.setLastName(null);
        assertNull("The last name should be null", contactInfo.getLastName());
    }

    /**
     * <p>
     * Tests the setLastName(String lastName) with a valid empty string
     * argument. The return value of the getLastName() method should be equal to
     * the method argument.
     * </p>
     */
    public void testSetLastNameWithValidEmptyArg() {
        String lastName = " ";
        contactInfo.setLastName(lastName);
        assertEquals("The last name is incorrect", lastName, contactInfo.getLastName());
    }

    /**
     * <p>
     * Tests the setAddress1(String address1) with a valid non-null non-empty
     * string argument. The return value of the getAddress1() method should be
     * equal to the method argument.
     * </p>
     */
    public void testSetAddress1WithValidNonNullNonEmptyArg() {
        String address1 = "743 Second Avenue";
        contactInfo.setAddress1(address1);
        assertEquals("The address1 field is incorrect", address1, contactInfo.getAddress1());
    }

    /**
     * <p>
     * Tests the setAddress1(String address1) with a valid null argument. The
     * return value of the getAddress1() method should be null.
     * </p>
     */
    public void testSetAddress1WithValidNullArg() {
        contactInfo.setAddress1(null);
        assertNull("The address1 field should be null", contactInfo.getAddress1());
    }

    /**
     * <p>
     * Tests the setAddress1(String address1) with a valid empty string
     * argument. The return value of the getAddress1() method should be equal to
     * the method argument.
     * </p>
     */
    public void testSetAddress1WithValidEmptyArg() {
        String address1 = " ";
        contactInfo.setAddress1(address1);
        assertEquals("The address1 field is incorrect", address1, contactInfo.getAddress1());
    }

    /**
     * <p>
     * Tests the setAddress2(String address2) with a valid non-null non-empty
     * string argument. The return value of the getAddress2() method should be
     * equal to the method argument.
     * </p>
     */
    public void testSetAddress2WithValidNonNullNonEmptyArg() {
        String address2 = "Imaginary Place";
        contactInfo.setAddress2(address2);
        assertEquals("The address2 field is incorrect", address2, contactInfo.getAddress2());
    }

    /**
     * <p>
     * Tests the setAddress2(String address2) with a valid null argument. The
     * return value of the getAddress2() method should be null.
     * </p>
     */
    public void testSetAddress2WithValidNullArg() {
        contactInfo.setAddress2(null);
        assertNull("The address2 field should be null", contactInfo.getAddress2());
    }

    /**
     * <p>
     * Tests the setAddress2(String address2) with a valid empty string
     * argument. The return value of the getAddress2() method should be equal to
     * the method argument.
     * </p>
     */
    public void testSetAddress2WithValidEmptyArg() {
        String address2 = " ";
        contactInfo.setAddress2(address2);
        assertEquals("The address2 field is incorrect", address2, contactInfo.getAddress2());
    }

    /**
     * <p>
     * Tests the setCity(String city) with a valid non-null non-empty string
     * argument. The return value of the getCity() method should be equal to the
     * method argument.
     * </p>
     */
    public void testSetCityWithValidNonNullNonEmptyArg() {
        String city = "Metropolis";
        contactInfo.setCity(city);
        assertEquals("The city is incorrect", city, contactInfo.getCity());
    }

    /**
     * <p>
     * Tests the setCity(String city) with a valid null argument. The return
     * value of the getCity() method should be null.
     * </p>
     */
    public void testSetCityWithValidNullArg() {
        contactInfo.setCity(null);
        assertNull("The city should be null", contactInfo.getCity());
    }

    /**
     * <p>
     * Tests the setCity(String city) with a valid empty string argument. The
     * return value of the getCity() method should be equal to the method
     * argument.
     * </p>
     */
    public void testSetCityWithValidEmptyArg() {
        String city = " ";
        contactInfo.setCity(city);
        assertEquals("The city is incorrect", city, contactInfo.getCity());
    }

    /**
     * <p>
     * Tests the setState(String state) with a valid non-null non-empty string
     * argument. The return value of the getState() method should be equal to
     * the method argument.
     * </p>
     */
    public void testSetStateWithValidNonNullNonEmptyArg() {
        String state = "Eastern Land";
        contactInfo.setState(state);
        assertEquals("The state is incorrect", state, contactInfo.getState());
    }

    /**
     * <p>
     * Tests the setState(String state) with a valid null argument. The return
     * value of the getState() method should be null.
     * </p>
     */
    public void testSetStateWithValidNullArg() {
        contactInfo.setState(null);
        assertNull("The state should be null", contactInfo.getState());
    }

    /**
     * <p>
     * Tests the setState(String state) with a valid empty string argument. The
     * return value of the getState() method should be equal to the method
     * argument.
     * </p>
     */
    public void testSetStateWithValidEmptyArg() {
        String state = " ";
        contactInfo.setState(state);
        assertEquals("The state is incorrect", state, contactInfo.getState());
    }

    /**
     * <p>
     * Tests the setPostalCode(String postalCode) with a valid non-null
     * non-empty string argument. The return value of the getPostalCode() method
     * should be equal to the method argument.
     * </p>
     */
    public void testSetPostalCodeWithValidNonNullNonEmptyArg() {
        String postalCode = "77801";
        contactInfo.setPostalCode(postalCode);
        assertEquals("The postalCode is incorrect", postalCode, contactInfo.getPostalCode());
    }

    /**
     * <p>
     * Tests the setPostalCode(String postalCode) with a valid null argument.
     * The return value of the getPostalCode() method should be null.
     * </p>
     */
    public void testSetPostalCodeWithValidNullArg() {
        contactInfo.setPostalCode(null);
        assertNull("The postalCode should be null", contactInfo.getPostalCode());
    }

    /**
     * <p>
     * Tests the setPostalCode(String postalCode) with a valid empty string
     * argument. The return value of the getPostalCode() method should be equal
     * to the method argument.
     * </p>
     */
    public void testSetPostalCodeWithValidEmptyArg() {
        String postalCode = " ";
        contactInfo.setPostalCode(postalCode);
        assertEquals("The postalCode is incorrect", postalCode, contactInfo.getPostalCode());
    }

    /**
     * <p>
     * Tests the setTelephone(String telephone) with a valid non-null non-empty
     * string argument. The return value of the getTelephone() method should be
     * equal to the method argument.
     * </p>
     */
    public void testSetTelephoneWithValidNonNullNonEmptyArg() {
        String telephone = "555-781-2982";
        contactInfo.setTelephone(telephone);
        assertEquals("The telephone is incorrect", telephone, contactInfo.getTelephone());
    }

    /**
     * <p>
     * Tests the setTelephone(String telephone) with a valid null argument. The
     * return value of the getTelephone() method should be null.
     * </p>
     */
    public void testSetTelephoneWithValidNullArg() {
        contactInfo.setTelephone(null);
        assertNull("The telephone should be null", contactInfo.getTelephone());
    }

    /**
     * <p>
     * Tests the setTelephone(String telephone) with a valid empty string
     * argument. The return value of the getTelephone() method should be equal
     * to the method argument.
     * </p>
     */
    public void testSetTelephoneWithValidEmptyArg() {
        String telephone = " ";
        contactInfo.setTelephone(telephone);
        assertEquals("The telephone is incorrect", telephone, contactInfo.getTelephone());
    }

    /**
     * <p>
     * Tests that ContactInfo implements the Serializable interface.
     * </p>
     */
    public void testInterface() {
        assertTrue("Sponsor should implement the Serializable interface", contactInfo instanceof Serializable);
    }

    /**
     * <p>
     * Returns the test suite containing all the unit tests in this test case.
     * </p>
     *
     * @return the test suite for this test case
     */
    public static Test suite() {
        return new TestSuite(ContactInfoTest.class);
    }

}
