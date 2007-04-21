/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact;

import com.topcoder.timetracker.common.TimeTrackerBean;


/**
 * <p>
 * This class holds the information of an entry in table <em>contact</em>.
 * </p>
 *
 * <p>
 * This class will be created by the application directly and created by the implementation of <code>AddressDAO</code>.
 * The application can get/set all the properties of it. But there will be validation on setters.
 * </p>
 *
 * <p>
 *  <strong>Thread Safety:</strong>
 *  This class is not thread safe by being mutable. This class is not supposed to be used in multi-thread environment.
 *  If it would be used in multi-thread environment, it should be synchronized externally.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 3.2
 */
public class Contact extends TimeTrackerBean {

    /**
     * <p>
     * Serial Version UID.
     * </p>
     */
    private static final long serialVersionUID = 5105071763856976919L;

    /**
     * <p>
     * Represents the first name of this contact. This variable is set to null initially, is mutable.
     * It is only allowed to be set to non null, non empty string by the setter.
     * It is access by its getter and setter methods.
     * </p>
     */
    private String firstName = null;

    /**
     * <p>
     * Represents the last name of this contact. This variable is set to null initially, is mutable.
     * It is only allowed to be set to non null, non empty string by the setter.
     * It is access by its getter and setter methods.
     * </p>
     */
    private String lastName = null;

    /**
     * <p>
     * Represents the phone number of this contact. This variable is set to null initially, is mutable.
     * It is only allowed to be set to non null, non empty string by the setter.
     * It is access by its getter and setter methods.
     * </p>
     */
    private String phoneNumber = null;

    /**
     * <p>
     * Represents the email address of this contact. This variable is set to null initially, is mutable.
     * It is only allowed to be set to non null, non empty string by the setter.
     * It is access by its getter and setter methods.
     * </p>
     */
    private String emailAddress = null;

    /**
     * <p>
     * Represents the types related by this contact. This variable is set to null initially, is mutable.
     * It is only allowed to be set to non null type.
     * It is access by its getter and setter methods.
     * </p>
     */
    private ContactType contactType = null;

    /**
     * <p>Empty constructor of the <code>Contact</code>.</p>
     */
    public Contact() {
        // does nothing
    }

    /**
     * <p>Get the first name.</p>
     *
     * @return the possible null, non empty first name
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * <p>Set the first name.</p>
     *
     * <p>
     * If the given value is valid and does not equal the value previously set, this address will be marked as changed.
     * </p>
     *
     * @param firstName the non null, non empty first name
     *
     * @throws IllegalArgumentException if the firstName is null or empty(trim'd)
     */
    public void setFirstName(String firstName) {
        Helper.validateStringWithIAE(firstName, "FirstName of Contact");
        if (!firstName.equals(this.firstName)) {
            this.setChanged(true);
        }
        this.firstName = firstName;
    }

    /**
     * <p>Get the last name.</p>
     *
     * @return the possible null, non empty first name
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * <p>Set the last name.</p>
     *
     * <p>
     * If the given value is valid and does not equal the value previously set, this address will be marked as changed.
     * </p>
     *
     * @param lastName the non null, non empty last name
     *
     * @throws IllegalArgumentException if the name is null or empty(trim'd)
     */
    public void setLastName(String lastName) {
        Helper.validateStringWithIAE(lastName, "LastName of Contact");
        if (!lastName.equals(this.lastName)) {
            this.setChanged(true);
        }
        this.lastName = lastName;
    }

    /**
     * <p>Get the phone number.</p>
     *
     * @return the possible null, non empty phone number
     */
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    /**
     * <p>Set the phone number.</p>
     *
     * <p>
     * If the given value is valid and does not equal the value previously set, this address will be marked as changed.
     * </p>
     *
     * @param phoneNumber the non null, non empty phone number
     *
     * @throws IllegalArgumentException if the phone number is null or empty(trim'd)
     */
    public void setPhoneNumber(String phoneNumber) {
        Helper.validateStringWithIAE(phoneNumber, "Phone of Contact");
        if (!phoneNumber.equals(this.phoneNumber)) {
            this.setChanged(true);
        }
        this.phoneNumber = phoneNumber;
    }

    /**
     * <p>Get the email address.</p>
     *
     * @return the possible null, non empty email address
     */
    public String getEmailAddress() {
        return this.emailAddress;
    }

    /**
     * <p>Set the email address.</p>
     *
     * <p>
     * If the given value is valid and does not equal the value previously set, this address will be marked as changed.
     * </p>
     *
     * @param emailAddress the non null, non empty email address
     *
     * @throws IllegalArgumentException if the email address is null or empty(trim'd)
     */
    public void setEmailAddress(String emailAddress) {
        Helper.validateStringWithIAE(emailAddress, "Email of Contact");
        if (!emailAddress.equals(this.emailAddress)) {
            this.setChanged(true);
        }
        this.emailAddress = emailAddress;
    }

    /**
     * <p>Get the associated contact types.</p>
     *
     * @return the contact type, possibly null if this is an initial <code>Contact</code>
     *         or it is not associated with any entity.
     */
    public ContactType getContactType() {
        return this.contactType;
    }

    /**
     * <p>Set the related contact types.</p>
     *
     * <p>
     * If the given value is valid and does not equal the value previously set, this contact will be marked as changed.
     * </p>
     *
     * @param contactType non null contact type
     *
     * @throws IllegalArgumentException if the given contact type is null
     */
    public void setContactType(ContactType contactType) {
        Helper.validateNotNullWithIAE(contactType, "ContactType of Contact");
        if (!contactType.equals(this.contactType)) {
            this.setChanged(true);
        }
        this.contactType = contactType;
    }
}
