/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Tests the values of the constants defined in the UserConstants interface.
 * </p>
 *
 * @author mpaulse
 * @version 1.0
 */
public class UserConstantsTest extends TestCase {

    /**
     * <p>
     * Tests that the UserConstants.PLAYER_TYPE_NAME constant is equal to
     * "player".
     * </p>
     */
    public void testPlayerTypeNameConstant() {
        assertEquals("PLAYER_TYPE_NAME is incorrect", UserConstants.PLAYER_TYPE_NAME, "player");
    }

    /**
     * <p>
     * Tests that the UserConstants.SPONSOR_TYPE_NAME constant is equal to
     * "sponsor".
     * </p>
     */
    public void testSponsorTypeNameConstant() {
        assertEquals("SPONSOR_TYPE_NAME is incorrect", UserConstants.SPONSOR_TYPE_NAME, "sponsor");
    }

    /**
     * <p>
     * Tests that the UserConstants.ADMIN_TYPE_NAME constant is equal to
     * "admin".
     * </p>
     */
    public void testAdminTypeNameConstant() {
        assertEquals("ADMIN_TYPE_NAME is incorrect", UserConstants.ADMIN_TYPE_NAME, "admin");
    }

    /**
     * <p>
     * Tests that the UserConstants.CREDENTIALS_TYPE_NAME constant is equal to
     * "credentials".
     * </p>
     */
    public void testCredentialsTypeNameConstant() {
        assertEquals("CREDENTIALS_TYPE_NAME is incorrect", UserConstants.CREDENTIALS_TYPE_NAME, "credentials");
    }

    /**
     * <p>
     * Tests that the UserConstants.ADDRESS_TYPE_NAME constant is equal to
     * "address".
     * </p>
     */
    public void testAddressTypeNameConstant() {
        assertEquals("ADDRESS_TYPE_NAME is incorrect", UserConstants.ADDRESS_TYPE_NAME, "address");
    }

    /**
     * <p>
     * Tests that the UserConstants.PLAYER_PAYMENT_PREF constant is equal to
     * "player-payment-pref".
     * </p>
     */
    public void testPlayerPaymentPrefConstant() {
        assertEquals("PLAYER_PAYMENT_PREF is incorrect", UserConstants.PLAYER_PAYMENT_PREF, "player-payment-pref");
    }

    /**
     * <p>
     * Tests that the UserConstants.SPONSOR_FAX_NUMBER constant is equal to
     * "sponsor-fax-number".
     * </p>
     */
    public void testSponsorFaxNumberConstant() {
        assertEquals("SPONSOR_FAX_NUMBER is incorrect", UserConstants.SPONSOR_FAX_NUMBER, "sponsor-fax-number");
    }

    /**
     * <p>
     * Tests that the UserConstants.SPONSOR_PAYMENT_PREF constant is equal to
     * "sponsor-payment-pref".
     * </p>
     */
    public void testSponsorPaymentPrefConstant() {
        assertEquals("SPONSOR_PAYMENT_PREF is incorrect", UserConstants.SPONSOR_PAYMENT_PREF, "sponsor-payment-pref");
    }

    /**
     * <p>
     * Tests that the UserConstants.SPONSOR_APPROVED constant is equal to
     * "sponsor-is-approved".
     * </p>
     */
    public void testSponsorApprovedConstant() {
        assertEquals("SPONSOR_APPROVED is incorrect", UserConstants.SPONSOR_APPROVED, "sponsor-is-approved");
    }

    /**
     * <p>
     * Tests that the UserConstants.ADDRESS_STREET_1 constant is equal to
     * "address-street1".
     * </p>
     */
    public void testAddressStreet1Constant() {
        assertEquals("ADDRESS_STREET_1 is incorrect", UserConstants.ADDRESS_STREET_1, "address-street1");
    }

    /**
     * <p>
     * Tests that the UserConstants.ADDRESS_STREET_2 constant is equal to
     * "address-street2".
     * </p>
     */
    public void testAddressStreet2Constant() {
        assertEquals("ADDRESS_STREET_2 is incorrect", UserConstants.ADDRESS_STREET_2, "address-street2");
    }

    /**
     * <p>
     * Tests that the UserConstants.ADDRESS_CITY constant is equal to
     * "address-city".
     * </p>
     */
    public void testAddressCityConstant() {
        assertEquals("ADDRESS_CITY is incorrect", UserConstants.ADDRESS_CITY, "address-city");
    }

    /**
     * <p>
     * Tests that the UserConstants.ADDRESS_STATE constant is equal to
     * "address-state".
     * </p>
     */
    public void testAddressStateConstant() {
        assertEquals("ADDRESS_STATE is incorrect", UserConstants.ADDRESS_STATE, "address-state");
    }

    /**
     * <p>
     * Tests that the UserConstants.ADDRESS_POSTAL_CODE constant is equal to
     * "address-postal-code".
     * </p>
     */
    public void testAddressPostalCodeConstant() {
        assertEquals("ADDRESS_POSTAL_CODE is incorrect", UserConstants.ADDRESS_POSTAL_CODE, "address-postal-code");
    }

    /**
     * <p>
     * Tests that the UserConstants.ADDRESS_PHONE_NUMBER constant is equal to
     * "address-phone-number".
     * </p>
     */
    public void testAddressPhoneNumberConstant() {
        assertEquals("ADDRESS_PHONE_NUMBER is incorrect", UserConstants.ADDRESS_PHONE_NUMBER, "address-phone-number");
    }

    /**
     * <p>
     * Tests that the UserConstants.ADDRESS_COUNTRY constant is equal to
     * "address-country".
     * </p>
     */
    public void testAddressCountryConstant() {
        assertEquals("ADDRESS_COUNTRY is incorrect", UserConstants.ADDRESS_COUNTRY, "address-country");
    }

    /**
     * <p>
     * Tests that the UserConstants.CREDENTIALS_HANDLE constant is equal to
     * "credentials-handle".
     * </p>
     */
    public void testCredentialsHandleConstant() {
        assertEquals("CREDENTIALS_HANDLE is incorrect", UserConstants.CREDENTIALS_HANDLE, "credentials-handle");
    }

    /**
     * <p>
     * Tests that the UserConstants.CREDENTIALS_PASSWORD constant is equal to
     * "credentials-password".
     * </p>
     */
    public void testCredentialsPasswordConstant() {
        assertEquals("CREDENTIALS_PASSWORD is incorrect", UserConstants.CREDENTIALS_PASSWORD, "credentials-password");
    }

    /**
     * <p>
     * Tests that the UserConstants.CREDENTIALS_IS_ACTIVE constant is equal to
     * "credentials-is-active".
     * </p>
     */
    public void testCredentialsIsActiveConstant() {
        assertEquals("CREDENTIALS_IS_ACTIVE is incorrect",
                     UserConstants.CREDENTIALS_IS_ACTIVE, "credentials-is-active");
    }

    /**
     * <p>
     * Tests that the UserConstants.PREFS_SOUND constant is equal to
     * "prefs-sound".
     * </p>
     */
    public void testPreferencesSoundConstant() {
        assertEquals("PREFS_SOUND is incorrect", UserConstants.PREFS_SOUND, "prefs-sound");
    }

    /**
     * <p>
     * Tests that the UserConstants.PREFS_GENERAL_NOTIFICATION constant is equal to
     * "prefs-general-notification".
     * </p>
     */
    public void testPreferencesGeneralNotificationConstant() {
        assertEquals("PREFS_GENERAL_NOTIFICATION is incorrect",
                     UserConstants.PREFS_GENERAL_NOTIFICATION, "prefs-general-notification");
    }

    /**
     * <p>
     * Returns the test suite containing all the unit tests in this test case.
     * </p>
     *
     * @return the test suite for this test case
     */
    public static Test suite() {
        return new TestSuite(UserConstantsTest.class);
    }

}
