/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence;

import com.orpheus.user.persistence.impl.UserProfileTranslator;

/**
 * <p>
 * Defines constants that represent the user profile types and properties which
 * the component supports. The user profile type constants correspond to the
 * names of <code>ConfigProfileType</code> objects specified in the
 * <code>ConfigProfileType</code> configuration. The user profile type
 * property constants correspond to the property keys of the relevent
 * information found in the <code>UserProfile</code> object.
 * </p>
 * <p>
 * This interface is referenced in the following situations:
 * </p>
 * <ul>
 * <li>by the {@link UserProfileTranslator} class when translating between the
 * user profile data transfer and value objects. </li>
 * <li>by {@link UserProfileDAO} implementations to convert the properties in
 * the user profile data transfer object to database table column names, and
 * vice-versa.</li>
 * <li>by applications when creating the search criteria map given to the
 * {@link OrpheusUserProfilePersistence#findProfiles(Map)} method</li>
 * </ul>
 * <p>
 * The User Logic Persistence component also makes use of the
 * <code>BaseProfileType</code> properties to represent certain user
 * information. These information are:
 * </p>
 * <ul>
 * <li><code>BaseProfileType.FIRST_NAME</code></li>
 * <li><code>BaseProfileType.LAST_NAME</code></li>
 * <li><code>BaseProfileType.EMAIL_ADDRESS</code></li>
 * </ul>
 * <p>
 * <b>Thread-safety:</b><br> This interface is not meant to be implemented.
 * Therefore, thread-safety is of no concern.
 * </p>
 *
 * @author argolite, mpaulse
 * @version 1.0
 */
public interface UserConstants {

    /**
     * <p>
     * Represents a player user profile type. Profiles of this type will contain
     * the player's user ID, email address and payment preference.
     * </p>
     */
    public String PLAYER_TYPE_NAME = "player";

    /**
     * <p>
     * Represents a sponsor user profile type. Profiles of this type will
     * contain the sponsor's user ID, email address, fax number, payment
     * preference, as well as flag indicating whether they the sponsor is
     * approved or not.
     * </p>
     */
    public String SPONSOR_TYPE_NAME = "sponsor";

    /**
     * <p>
     * Represents an admin user profile type. Profiles of this type will contain
     * the admin's user ID and email address.
     * </p>
     */
    public String ADMIN_TYPE_NAME = "admin";

    /**
     * <p>
     * Represents a user credentials profile type. Profiles of this type will
     * contain the user handle, password as well as a flag indicating whether
     * the user is active or not.
     * </p>
     */
    public String CREDENTIALS_TYPE_NAME = "credentials";

    /**
     * <p>
     * Represents a user address profile type. Profiles of this type will
     * contain the user's first and last names, two street address fields, the
     * city, state, postal code and the user's telephone number.
     * </p>
     */
    public String ADDRESS_TYPE_NAME = "address";

    /**
     * <p>
     * Represents the payment preference property of the
     * {@link #PLAYER_TYPE_NAME} profile type. This constant matches the
     * player.payment_pref column in the persistent store.
     * </p>
     */
    public String PLAYER_PAYMENT_PREF = "player-payment-pref";

    /**
     * <p>
     * Represents the fax number property of the {@link #SPONSOR_TYPE_NAME}
     * profile type. This constant matches the sponsor.fax column in the
     * persistent store.
     * </p>
     */
    public String SPONSOR_FAX_NUMBER = "sponsor-fax-number";

    /**
     * <p>
     * Represents the payment preference property of the
     * {@link #SPONSOR_TYPE_NAME} profile type. This constant matches the
     * sponsor.payment_pref column in the persistent store.
     * </p>
     */
    public String SPONSOR_PAYMENT_PREF = "sponsor-payment-pref";

    /**
     * <p>
     * Represents the approved flag property of the {@link #SPONSOR_TYPE_NAME}
     * profile type. This constant matches the sponsor.is_approved column in the
     * persistent store.
     * </p>
     */
    public String SPONSOR_APPROVED = "sponsor-is-approved";

    /**
     * <p>
     * Represents the address street 1 field property of the
     * {@link #ADDRESS_TYPE_NAME} profile type. This constant matches the
     * contact_info.address_1 column in the persistent store.
     * </p>
     */
    public String ADDRESS_STREET_1 = "address-street1";

    /**
     * <p>
     * Represents the address street 2 field property of the
     * {@link #ADDRESS_TYPE_NAME} profile type. This constant matches the
     * contact_info.address_2 column in the persistent store.
     * </p>
     */
    public String ADDRESS_STREET_2 = "address-street2";

    /**
     * <p>
     * Represents the city property of the {@link #ADDRESS_TYPE_NAME} profile
     * type. This constant matches the contact_info.city column in the
     * persistent store.
     * </p>
     */
    public String ADDRESS_CITY = "address-city";

    /**
     * <p>
     * Represents the state property of the {@link #ADDRESS_TYPE_NAME} profile
     * type. This constant matches the contact_info.state column in the
     * persistent store.
     * </p>
     */
    public String ADDRESS_STATE = "address-state";

    /**
     * <p>
     * Represents the postal code property of the {@link #ADDRESS_TYPE_NAME}
     * profile type. This constant matches the contact_info.postal_code column
     * in the persistent store.
     * </p>
     */
    public String ADDRESS_POSTAL_CODE = "address-postal-code";

    /**
     * <p>
     * Represents the telephone number property of the
     * {@link #ADDRESS_TYPE_NAME} profile type.. This constant matches the
     * contact_info.telephone column in the persistent store.
     * </p>
     */
    public String ADDRESS_PHONE_NUMBER = "address-phone-number";

    /**
     * <p>
     * Represents the user handle property of the {@link #CREDENTIALS_TYPE_NAME}
     * profile type. This constant matches the user.handle column in the
     * persistent store.
     * </p>
     */
    public String CREDENTIALS_HANDLE = "credentials-handle";

    /**
     * <p>
     * Represents the user password property of the
     * {@link #CREDENTIALS_TYPE_NAME} profile type. This constant matches the
     * user.passwd column in the persistent store.
     * </p>
     */
    public String CREDENTIALS_PASSWORD = "credentials-password";

    /**
     * <p>
     * Represents the user active flag property of the
     * {@link #CREDENTIALS_TYPE_NAME} profile type. This constant matches the
     * user.is_active column in the persistent store.
     * </p>
     */
    public String CREDENTIALS_IS_ACTIVE = "credentials-is-active";

}
