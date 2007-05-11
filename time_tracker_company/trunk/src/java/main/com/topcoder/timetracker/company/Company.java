/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.company;

import com.topcoder.timetracker.common.TimeTrackerBean;
import com.topcoder.timetracker.contact.Address;
import com.topcoder.timetracker.contact.Contact;

/**
 * <p>
 * This bean represents a Company within the context of the Time Tracker component. It holds the different attributes
 * of the company such as the company name, address and contact information. The Company passcode is also stored
 * within in plain text form.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>This class is mutable, and not thread-safe. Multiple threads are advised to work with their
 * own instance.
 * </p>
 *
 * @author bramandia, argolite, TCSDEVELOPER
 * @version 3.2
 */
public class Company extends TimeTrackerBean {

	/**
	 * Automatically generated unique ID for use with serialization.
	 */
	private static final long serialVersionUID = -2108845653895990269L;

	/**
     * <p>
     * The name of the company.
     * </p>
     *
     * <p>
     * It may be null when the Company object is initially constructed, but it may not be set to a null or empty String
     * afterwards. Initialized In: setCompanyName, modified In: setCompanyName and accessed In: getCompanyName.
     * </p>
     */
    private String companyName;

    /**
     * <p>
     * The address where the company is located.
     * </p>
     *
     * <p>
     * It may be null when the Company object is initially constructed, but it may not be set to a null afterwards.
     * Initialized In: setAddress, modified In: setAddress and accessed In: getAddress.
     * </p>
     */
    private Address address;

    /**
     * <p>
     * Contact information for the company. The Contact describes the person to get in touch with matters concerning
     * the company.
     * </p>
     *
     * <p>
     * It may be null when the Company object is initially constructed, but it may not be set to a null afterwards.
     * Initialized In: setContact, modified In: setContact and accessed In: getContact.
     * </p>
     */
    private Contact contact;

    /**
     * <p>
     * The passcode of the Company in plain text form.
     * </p>
     *
     * <p>
     * It may be null when the Company object is initially constructed, but it may not be set to a null or empty String
     * afterwards. Initialized In: setPasscode, modified In: setPasscode and accessed In: getPasscode.
     * </p>
     */
    private String passCode;

    /**
     * <p>
     * This is the algorithm name for the encryption algorithm to be used when setting the company passcode. This field
     * is not currently used.
     * </p>
     *
     * <p>
     * It may be null when the Company object is initially constructed, but it may not be set to a null or empty String
     * afterwards. Initialized In: setAlgorithmName, modified In: setAlgorithmName, accessed In: getAlgorithmName and
     * utilized In: getPasscode/setPasscode.
     * </p>
     */
    private String algorithmName;

    /**
     * <p>
     * Default constructor. Do nothing.
     * </p>
     */
    public Company() {
    }

    /**
     * <p>
     * Retrieves the address where the company is located. It may be null when the Company object is initially
     * constructed, but it may not be set to a null afterwards.
     * </p>
     *
     * @return the address where the company is located.
     */
    public Address getAddress() {
        return address;
    }

    /**
     * <p>
     * Sets the address where the company is located. It may be null when the Company object is initially constructed,
     * but it may not be set to a null afterwards.
     * </p>
     *
     * @param address the address where the company is located.
     *
     * @throws IllegalArgumentException if address is null.
     */
    public void setAddress(Address address) {
        TimeTrackerCompanyHelper.validateNotNull(address, "address");

        if (!address.equals(this.address)) {
            setChanged(true);
        }

        this.address = address;
    }

    /**
     * <p>
     * Retrieves contact information for the company. The Contact describes the person to get in touch with matters
     * concerning the company. It may be null when the Company object is initially constructed, but it may not be set
     * to a null afterwards.
     * </p>
     *
     * @return contact information for the company.
     */
    public Contact getContact() {
        return contact;
    }

    /**
     * <p>
     * Sets the contact information for the company. The Contact describes the person to get in touch with matters
     * concerning the company. It may be null when the Company object is initially constructed, but it may not be set
     * to a null afterwards.
     * </p>
     *
     * @param contact contact information for the company.
     *
     * @throws IllegalArgumentException if contact is null.
     */
    public void setContact(Contact contact) {
        TimeTrackerCompanyHelper.validateNotNull(contact, "contact");

        if (!contact.equals(this.contact)) {
            setChanged(true);
        }

        this.contact = contact;
    }

    /**
     * <p>
     * Retrieves the name of the company. It may be null when the Company object is initially constructed, but it may
     * not be set to a null or empty String afterwards.
     * </p>
     *
     * @return the name of the company.
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * <p>
     * Sets the name of the company. It may be null when the Company object is initially constructed, but it may not be
     * set to a null or empty String afterwards.
     * </p>
     *
     * @param companyName The name of the company.
     *
     * @throws IllegalArgumentException if companyName is an empty String or null.
     */
    public void setCompanyName(String companyName) {
        TimeTrackerCompanyHelper.validateString(companyName, "companyName");

        if (!companyName.equals(this.companyName)) {
            setChanged(true);
        }

        this.companyName = companyName;
    }

    /**
     * <p>
     * Retrieves the passcode of the Company. The passcode is stored as plaintext. It may be null when the Company
     * object is initially constructed, but it may not be set to a null or empty String afterwards.
     * </p>
     *
     * @return the passcode of the Company in plain text
     */
    public String getPassCode() {
        return this.passCode;
    }

    /**
     * <p>
     * Sets the passcode of the Company. It may be null when the Company object is initially constructed, but it may
     * not be set to a null or empty String afterwards.
     * </p>
     *
     * @param passCode the passcode of the Company in plain text.
     *
     * @throws IllegalArgumentException if the passcode is null or an empty String.
     */
    public void setPassCode(String passCode) {
        TimeTrackerCompanyHelper.validateString(passCode, "passCode");

        if (!passCode.equals(this.passCode)) {
            setChanged(true);
        }

        this.passCode = passCode;
    }

    /**
     * <p>
     * Retrieves the algorithm name for the encryption algorithm to be used when setting the company passcode. This
     * field is not currently used.
     * </p>
     *
     * <p>
     * It may be null when the Company object is initially constructed, but it may not be set to a null or empty String
     * afterwards.
     * </p>
     *
     * @return the algorithm name for the encryption algorithm to be used when setting the company passcode. This field
     *         is not currently used
     */
    public String getAlgorithmName() {
        return algorithmName;
    }

    /**
     * <p>
     * Sets the algorithm name for the encryption algorithm to be used when setting the company passcode. This field is
     * not currently used.
     * </p>
     *
     * <p>
     * It may be null when the Company object is initially constructed, but it may not be set to a null or empty String
     * afterwards.
     * </p>
     *
     * @param algorithmName the algorithm name for the encryption algorithm to be used when setting the company
     *        passcode. This field is not currently used
     *
     * @throws IllegalArgumentException if algorithmName is null or empty string
     */
    public void setAlgorithmName(String algorithmName) {
        TimeTrackerCompanyHelper.validateString(algorithmName, "algorithmName");

        this.algorithmName = algorithmName;
    }
}
