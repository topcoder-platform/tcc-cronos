/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.cronos.timetracker.common;

/**
 * <p>
 * This bean represents the contact details of a specific entity. It may include the name of the contact person, or
 * the name of the person itself. Other details on how to get in touch like phone number and email are also
 * included.
 * </p>
 * <p>
 * Thread Safety: - This class is mutable, and not thread-safe. Multiple threads are advised to work with their own
 * instance.
 * </p>
 *
 * @author ShindouHikaru
 * @author kr00tki
 * @version 2.0
 */
public class Contact extends TimeTrackerBean {

    /**
     * <p>
     * The first name of the contact. It may be null when the Contact object is initially constructed, but it may
     * not be set to a null or empty String afterwards.
     * </p>
     * <p>
     * Initialized In: setFirstName
     * </p>
     * <p>
     * Modified In: setFirstName
     * </p>
     * <p>
     * Accessed In: getFirstName
     * </p>
     *
     *
     */
    private String firstName;

    /**
     * <p>
     * The last name of the contact. It may be null when the Contact object is initially constructed, but it may
     * not be set to a null or empty String afterwards.
     * </p>
     * <p>
     * Initialized In: setLastName
     * </p>
     * <p>
     * Modified In: setLastName
     * </p>
     * <p>
     * Accessed In: getLastName
     * </p>
     *
     */
    private String lastName;

    /**
     * <p>
     * The phone number of the contact. It may be null when the Contact object is initially constructed, but it may
     * not be set to a null or empty String afterwards.
     * </p>
     * <p>
     * Initialized In: setPhoneNumber
     * </p>
     * <p>
     * Modified In: setPhoneNumber
     * </p>
     * <p>
     * Accessed In: getPhoneNumber
     * </p>
     *
     */
    private String phoneNumber;

    /**
     * <p>
     * The email address of the contact. It may be null when the Contact object is initially constructed, but it
     * may not be set to a null or empty String afterwards.
     * </p>
     * <p>
     * Initialized In: setEmailAddress
     * </p>
     * <p>
     * Modified In: setEmailAddress
     * </p>
     * <p>
     * Accessed In: getEmailAddress
     * </p>
     *
     */
    private String emailAddress;

    /**
     * <p>
     * Default constructor.
     * </p>
     *
     */
    public Contact() {
        // your code here
    }

    /**
     * <p>
     * Retrieves the first name of the contact. It may be null when the Contact object is initially constructed,
     * but it may not be set to a null or empty String afterwards.
     * </p>
     *
     *
     * @return the first name of the contact.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * <p>
     * Sets the first name of the contact. It may be null when the Contact object is initially constructed, but it
     * may not be set to a null or empty String afterwards.
     * </p>
     * <p>
     * Implementation Notes: - If the current value that was added is not equal to the previous value, then call
     * setChanged(true).
     * </p>
     *
     *
     *
     * @param firstName the first name of the contact.
     * @throws IllegalArgumentException if firstName is a null or empty String.
     */
    public void setFirstName(String firstName) {
        Utils.checkString(firstName, "firstName", false);
        if (!firstName.equals(this.firstName)) {
            this.firstName = firstName;
            setChanged(true);
        }

    }

    /**
     * <p>
     * Retrieves the last name of the contact. It may be null when the Contact object is initially constructed, but
     * it may not be set to a null or empty String afterwards.
     * </p>
     *
     *
     *
     * @return the last name of the contact.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * <p>
     * The last name of the contact. It may be null when the Contact object is initially constructed, but it may
     * not be set to a null or empty String afterwards.
     * </p>
     * <p>
     * Implementation Notes: - If the current value that was added is not equal to the previous value, then call
     * setChanged(true).
     * </p>
     *
     *
     *
     * @param lastName the last name of the contact.
     * @throws IllegalArgumentException if lastName is a null or empty String.
     */
    public void setLastName(String lastName) {
        Utils.checkString(lastName, "lastName", false);
        if (!lastName.equals(this.lastName)) {
            this.lastName = lastName;
            setChanged(true);
        }
    }

    /**
     * <p>
     * Retrieves the phone number of the contact. It may be null when the Contact object is initially constructed,
     * but it may not be set to a null or empty String afterwards.
     * </p>
     *
     *
     * @return the phone number of the contact.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * <p>
     * Sets the phone number of the contact. It may be null when the Contact object is initially constructed, but
     * it may not be set to a null or empty String afterwards.
     * </p>
     * <p>
     * Implementation Notes: - If the current value that was added is not equal to the previous value, then call
     * setChanged(true).
     * </p>
     *
     *
     *
     * @param phoneNumber the phone number of the contact.
     * @throws IllegalArgumentException if phoneNumber is a null or empty String.
     */
    public void setPhoneNumber(String phoneNumber) {
        Utils.checkString(phoneNumber, "phoneNumber", false);
        if (!phoneNumber.equals(this.phoneNumber)) {
            this.phoneNumber = phoneNumber;
            setChanged(true);
        }
    }

    /**
     * <p>
     * Retrieves the email address of the contact. It may be null when the Contact object is initially constructed,
     * but it may not be set to a null or empty String afterwards.
     * </p>
     *
     *
     *
     * @return the email address of the contact.
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * <p>
     * Sets the email address of the contact. It may be null when the Contact object is initially constructed, but
     * it may not be set to a null or empty String afterwards.
     * </p>
     * <p>
     * Implementation Notes: - If the current value that was added is not equal to the previous value, then call
     * setChanged(true).
     * </p>
     *
     *
     * @param emailAddress the email address of the contact.
     * @throws IllegalArgumentException if emailAddress is a null or empty String.
     */
    public void setEmailAddress(String emailAddress) {
        Utils.checkString(emailAddress, "emailAddress", false);
        if (!emailAddress.equals(this.emailAddress)) {
            this.emailAddress = emailAddress;
            setChanged(true);
        }
    }
}
