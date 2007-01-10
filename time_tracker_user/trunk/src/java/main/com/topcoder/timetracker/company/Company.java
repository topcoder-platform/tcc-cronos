/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.timetracker.company;

import com.topcoder.timetracker.common.Address;
import com.topcoder.timetracker.common.Contact;
import com.topcoder.timetracker.common.EncryptionRepository;
import com.topcoder.timetracker.common.TimeTrackerBean;
import com.topcoder.timetracker.common.Utils;
import com.topcoder.encryption.AbstractEncryptionAlgorithm;
import com.topcoder.encryption.EncryptionException;

/**
 * <p>
 * This bean represents a Company within the context of the Time Tracker component. It holds the different
 * attributes of the company such as the company name, address and contact information. The Company passcode is
 * also stored within in encrypted form.
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
public class Company extends TimeTrackerBean {

    /**
     * <p>
     * The name of the company. It may be null when the Company object is initially constructed, but it may not be
     * set to a null or empty String afterwards.
     * </p>
     * <p>
     * Initialized In: setCompanyName
     * </p>
     * <p>
     * Modified In: setCompanyName
     * </p>
     * <p>
     * Accessed In: getCompanyName
     * </p>
     *
     *
     */
    private String companyName;

    /**
     * <p>
     * The address where the company is located. It may be null when the Company object is initially constructed,
     * but it may not be set to a null afterwards.
     * </p>
     * <p>
     * Initialized In: setAddress
     * </p>
     * <p>
     * Modified In: setAddress
     * </p>
     * <p>
     * Accessed In: getAddress
     * </p>
     *
     *
     *
     */
    private Address address;

    /**
     * <p>
     * Contact information for the company. The Contact describes the person to get in touch with matters
     * concerning the company. It may be null when the Company object is initially constructed, but it may not be
     * set to a nullString afterwards.
     * </p>
     * <p>
     * Initialized In: setContact
     * </p>
     * <p>
     * Modified In: setContact
     * </p>
     * <p>
     * Accessed In: getContact
     * </p>
     *
     */
    private Contact contact;

    /**
     * <p>
     * The passcode of the Company in encrypted form. When stored as a variable attribute, it will be encrypted to
     * make it harder to be obtained especially during serialization. When being set and retrieved, it will first
     * be encrypted/decrypted according to the algorithm specified in algorithmName.
     * </p>
     * <p>
     * It may be null when the Company object is initially constructed, but it may not be set to a null or empty
     * String afterwards.
     * </p>
     * <p>
     * Initialized In: setPasscode
     * </p>
     * <p>
     * Modified In: setPasscode
     * </p>
     * <p>
     * Accessed In: getPasscode
     * </p>
     *
     */
    private String passcode;

    /**
     * <p>
     * This is the algorithm name for the encryption algorithm to be used when setting the company passcode. The
     * algorithm name is used to retrieve the encryption algorithm from the Encryption component. It is expected
     * that the Encryption component would have been initialized with the encryption keys (if necessary) prior to
     * using this component.
     * </p>
     * <p>
     * It may be null when the Company object is initially constructed, but it may not be set to a null or empty
     * String afterwards.
     * </p>
     * <p>
     * Initialized In: setAlgorithmName
     * </p>
     * <p>
     * Modified In: setAlgorithmName
     * </p>
     * <p>
     * Accessed In: getAlgorithmName
     * </p>
     * <p>
     * Utilized In: getPasscode/setPasscode
     * </p>
     *
     */
    private String algorithmName;

    /**
     * <p>
     * Default constructor.
     * </p>
     *
     */
    public Company() {
        // your code here
    }

    /**
     * <p>
     * Retrieves the name of the company. It may be null when the Company object is initially constructed, but it
     * may not be set to a null or empty String afterwards.
     * </p>
     *
     *
     *
     * @return the name of the company.
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * <p>
     * Sets the name of the company. It may be null when the Company object is initially constructed, but it may
     * not be set to a <code>null</code> afterwards.
     * </p>
     *
     *
     * @param companyName The name of the company.
     * @throws IllegalArgumentException if companyName is an empty String or null.
     */
    public void setCompanyName(String companyName) {
        Utils.checkString(companyName, "companyName", false);
        if (!companyName.equals(this.companyName)) {
            this.companyName = companyName;
            setChanged(true);
        }
    }

    /**
     * <p>
     * Retrieves the address where the company is located. It may be null when the Company object is initially
     * constructed, but it may not be set to a null or empty String afterwards.
     * </p>
     *
     *
     * @return the address where the company is located.
     */
    public Address getAddress() {
        return address;
    }

    /**
     * <p>
     * Sets the address where the company is located. It may be null when the Company object is initially
     * constructed, but it may not be set to a null afterwards.
     * </p>
     * <p>
     * Implementation Notes: - If the current value that was added is not equal to the previous value, then call
     * setChanged(true).
     * </p>
     *
     *
     * @param address the address where the company is located.
     * @throws IllegalArgumentException if address is null.
     */
    public void setAddress(Address address) {
        Utils.checkNull(address, "address");
        if (!address.equals(this.address)) {
            this.address = address;
            setChanged(true);
        }
    }

    /**
     * <p>
     * Retrieves contact information for the company. The Contact describes the person to get in touch with matters
     * concerning the company. It may be null when the Company object is initially constructed, but it may not be
     * set to a null or empty String afterwards.
     * </p>
     *
     *
     * @return contact information for the company.
     */
    public Contact getContact() {
        return contact;
    }

    /**
     * <p>
     * Sets the contact information for the company. The Contact describes the person to get in touch with matters
     * concerning the company. It may be null when the Company object is initially constructed, but it may not be
     * set to a null afterwards.
     * </p>
     * <p>
     * Implementation Notes: - If the current value that was added is not equal to the previous value, then call
     * setChanged(true).
     * </p>
     *
     *
     *
     * @param contact contact information for the company.
     * @throws IllegalArgumentException if contact is null.
     */
    public void setContact(Contact contact) {
        Utils.checkNull(contact, "contact");
        if (!contact.equals(this.contact)) {
            this.contact = contact;
            setChanged(true);
        }
    }

    /**
     * <p>
     * Retrieves the passcode of the Company. When stored as a variable attribute, it will be encrypted to make it
     * harder to be obtained especially during serialization. When being set and retrieved, it will first be
     * encrypted/decrypted according to the algorithm specified in algorithmName.
     * </p>
     *
     * @return the passcode of the Company (in clear text).
     * @throws EncryptionException if a problem occurs while decrypting the passcode.
     * @throws IllegalStateException if its necessary to decrypt the passcode, but algorithmName is not specified.
     */
    public String getPasscode() {
        if (passcode == null) {
            return null;
        }

        return new String(getAlgorithm().decrypt(passcode.getBytes()));
    }

    /**
     * Returns the algorithm from the EncryptionRepository.
     *
     * @return the algorithm instance.
     */
    private AbstractEncryptionAlgorithm getAlgorithm() {
        if (algorithmName == null) {
            throw new IllegalStateException("The algorithm name is not specified.");
        }

        return EncryptionRepository.getInstance().retrieveAlgorithm(algorithmName);
    }

    /**
     * <p>
     * Sets the passcode of the Company. When stored as a variable attribute, it will be encrypted to make it
     * harder to be obtained especially during serialization. When being set and retrieved, it will first be
     * encrypted/decrypted according to the algorithm specified in algorithmName.
     * </p>
     *
     * @param passcode the passcode of the Company (in clear text).
     * @throws EncryptionException if a problem occurs while encrypting the passcode.
     * @throws IllegalArgumentException if the passcode is null or an empty String.
     * @throws IllegalStateException if its necessary to encrypt the passcode, but algorithmName is not specified.
     */
    public void setPasscode(String passcode) {
        Utils.checkString(passcode, "passcode", false);
        String newPasscode = new String(getAlgorithm().encrypt(passcode.getBytes()));
        if (!newPasscode.equals(this.passcode)) {
            this.passcode = newPasscode;
            setChanged(true);
        }
    }

    /**
     * <p>
     * Retrieves the algorithm name for the encryption algorithm to be used when setting the company passcode. The
     * algorithm name is used to retrieve the encryption algorithm from the Encryption component. It is expected
     * that the Encryption component would have been initialized with the encryption keys (if necessary) prior to
     * using this component.
     * </p>
     * <p>
     * It may be null when the Company object is initially constructed, but it may not be set to a null or empty
     * String afterwards.
     * </p>
     *
     *
     *
     * @return the algorithm name for the encryption algorithm to be used when setting the company passcode.
     */
    public String getAlgorithmName() {
        return algorithmName;
    }

    /**
     * <p>
     * Sets the algorithm name for the encryption algorithm to be used when setting the company passcode. The
     * algorithm name is used to retrieve the encryption algorithm from the Encryption component. It is expected
     * that the Encryption component would have been initialized with the encryption keys (if necessary) prior to
     * using this component.
     * </p>
     * <p>
     * It may be null when the Company object is initially constructed, but it may not be set to a null or empty
     * String afterwards.
     * </p>
     *
     *
     *
     * @param algorithmName the algorithm name for the encryption algorithm to be used when setting the company
     *        passcode.
     * @throws IllegalArgumentException if the algorithmName is null, an empty String, or it doesn't exist within
     *         the EncryptionRepository.
     */
    public void setAlgorithmName(String algorithmName) {
        Utils.checkString(algorithmName, "algorithmName", false);
        if (EncryptionRepository.getInstance().retrieveAlgorithm(algorithmName) == null) {
            throw new IllegalArgumentException("The algorithm: " + algorithmName + " doesn't exist in repository.");
        }

        this.algorithmName = algorithmName;
    }
}
