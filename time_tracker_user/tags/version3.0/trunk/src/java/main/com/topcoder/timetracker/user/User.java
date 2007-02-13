/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.timetracker.user;

import com.topcoder.timetracker.common.Address;
import com.topcoder.timetracker.common.Contact;
import com.topcoder.timetracker.common.EncryptionRepository;
import com.topcoder.timetracker.common.TimeTrackerBean;
import com.topcoder.timetracker.common.Utils;
import com.topcoder.encryption.AbstractEncryptionAlgorithm;

/**
 * <p>
 * This bean contains the account information of a Time Tracker user. The User password is stored within the bean
 * in encrypted form.
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
public class User extends TimeTrackerBean {

    /**
     * <p>
     * This is id of the company with which the User is associated with.
     * </p>
     *
     */
    private long companyId;

    /**
     * <p>
     * This is the User's username. It may be null when the User object is initially constructed, but it may not be
     * set to a null or empty String afterwards.
     * </p>
     * <p>
     * Initialized In: setUsername
     * </p>
     * <p>
     * Modified In: setUsername
     * </p>
     * <p>
     * Accessed In: setUsername
     * </p>
     *
     *
     */
    private String username;

    /**
     * <p>
     * The password of the User in encrypted form. When stored as a variable attribute, it will be encrypted to
     * make it harder to be obtained especially during serialization. When being set and retrieved, it will first
     * be encrypted/decrypted according to the algorithm specified in algorithmName.
     * </p>
     * <p>
     * It may be null when the User object is initially constructed, but it may not be set to a null or empty
     * String afterwards.
     * </p>
     * <p>
     * Initialized In: setPassword
     * </p>
     * <p>
     * Modified In: setPassword
     * </p>
     * <p>
     * Accessed In: getPassword
     * </p>
     *
     */
    private String password;

    /**
     * <p>
     * This is the account status of the user. It may be null when the User object is initially constructed, but it
     * may not be set to a null afterwards.
     * </p>
     * <p>
     * Initialized In: setAccountStatus
     * </p>
     * <p>
     * Modified In: setAccountStatus
     * </p>
     * <p>
     * Accessed In: getAccountStatus
     * </p>
     *
     *
     */
    private AccountStatus accountStatus;

    /**
     * <p>
     * This is the contact information of the user, including real name, phone number, etc. It may be null when the
     * User object is initially constructed, but it may not be set to a null afterwards.
     * </p>
     * <p>
     * Initialized In: setContactInfo
     * </p>
     * <p>
     * Modified In: setContactInfo
     * </p>
     * <p>
     * Accessed In: getContactInfo
     * </p>
     *
     */
    private Contact contact;

    /**
     * <p>
     * This is the address of the user. It may be null when the User object is initially constructed, but it may
     * not be set to a null afterwards.
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
     */
    private Address address;

    /**
     * <p>
     * This is the algorithm name for the encryption algorithm to be used when setting the user's password. The
     * algorithm name is used to retrieve the encryption algorithm from the Encryption component. It is expected
     * that the Encryption component would have been initialized with the encryption keys (if necessary) prior to
     * using this component.
     * </p>
     * <p>
     * It may be null when the User object is initially constructed, but it may not be set to a null or empty
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
     * Utilized In: getPassword/setPassword
     * </p>
     *
     */
    private String algorithmName;

    /**
     * <p>
     * The default constructor.
     * </p>
     *
     */
    public User() {
        // your code here
    }

    /**
     * <p>
     * Retrieves the id of the company with which the User is associated with.
     * </p>
     *
     *
     *
     * @return the id of the company with which the User is associated with.
     */
    public long getCompanyId() {
        return companyId;
    }

    /**
     * <p>
     * Sets the id of the company with which the User is assocaited with.
     * </p>
     * <p>
     * Implementation Notes: - If the current value that was added is not equal to the previous value, then call
     * setChanged(true).
     * </p>
     *
     *
     *
     * @param companyId the id of the company with which the User is associated with.
     * @throws IllegalArgumentException if the companyId is <=0.
     */
    public void setCompanyId(long companyId) {
        Utils.checkPositive(companyId, "companyId");
        if (companyId != this.companyId) {
            this.companyId = companyId;
            setChanged(true);
        }
    }

    /**
     * <p>
     * Retrieves the User's username. It may be null when the User object is initially constructed, but it may not
     * be set to a null or empty String afterwards.
     * </p>
     *
     *
     *
     * @return the User's username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * <p>
     * Sets the User's username. It may be null when the User object is initially constructed, but it may not be
     * set to a null or empty String afterwards.
     * </p>
     * <p>
     * Implementation Notes: - If the current value that was added is not equal to the previous value, then call
     * setChanged(true).
     * </p>
     *
     *
     *
     * @param username The user's username.
     * @throws IllegalArgumentException if username is null or an empty String.
     */
    public void setUsername(String username) {
        Utils.checkString(username, "username", false);
        if (!username.equals(this.username)) {
            this.username = username;
            setChanged(true);
        }
    }

    /**
     * <p>
     * Retrieves the password of the User. When stored as a variable attribute, it will be encrypted to make it
     * harder to be obtained especially during serialization. When being set and retrieved, it will first be
     * encrypted/decrypted according to the algorithm specified in algorithmName.
     * </p>
     * <p>
     * It may be null when the User object is initially constructed, but it may not be set to a null or empty
     * String afterwards.
     * </p>
     *
     * @return The password of the user.
     * @throws EncryptionException if a problem occurs while decrypting the password.
     * @throws IllegalStateException if its necessary to decrypt the password, but algorithmName is not specified.
     */
    public String getPassword() {
        if (password != null) {
            return new String(getAlgorithm().decrypt(password.getBytes()));
        }
        return password;
    }

    /**
     * <p>
     * Sets the password of the User in encrypted form. When stored as a variable attribute, it will be encrypted
     * to make it harder to be obtained especially during serialization. When being set and retrieved, it will
     * first be encrypted/decrypted according to the algorithm specified in algorithmName.
     * </p>
     * <p>
     * It may be null when the User object is initially constructed, but it may not be set to a null or empty
     * String afterwards.
     * </p>
     *
     * @param password The password of the user, in plaintext.
     * @throws IllegalArgumentException if the password is null or an empty String.
     * @throws EncryptionException if a problem occurs while encryting the password.
     * @throws IllegalStateException if its necessary to encrypt the password, but algorithmName is not specified.
     */
    public void setPassword(String password) {
        Utils.checkString(password, "password", false);
        String newPassword = new String(getAlgorithm().encrypt(password.getBytes()));
        if (!newPassword.equals(this.password)) {
            this.password = newPassword;
            setChanged(true);
        }

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
     * Retrieves the account status of the user. It may be null when the User object is initially constructed, but
     * it may not be set to a null afterwards.
     * </p>
     *
     *
     *
     * @return the account status of the user.
     */
    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    /**
     * <p>
     * Sets the account status of the user. It may be null when the User object is initially constructed, but it
     * may not be set to a null afterwards.
     * </p>
     *
     * @param accountStatus The account status of the user.
     * @throws IllegalArgumentException if the accountStatus is null.
     */
    public void setAccountStatus(AccountStatus accountStatus) {
        Utils.checkNull(accountStatus, "accountStatus");
        if (!accountStatus.equals(this.accountStatus)) {
            this.accountStatus = accountStatus;
            setChanged(true);
        }
    }

    /**
     * <p>
     * Retrieves the contact information of the user, including real name, phone number, etc. It may be null when
     * the User object is initially constructed, but it may not be set to a null afterwards.
     * </p>
     *
     *
     *
     * @return the contact information of the user.
     */
    public Contact getContactInfo() {
        return contact;
    }

    /**
     * <p>
     * Sets the contact information of the user, including real name, phone number, etc. It may be null when the
     * User object is initially constructed, but it may not be set to a null afterwards.
     * </p>
     *
     * @param contact the contact information of the user.
     * @throws IllegalArgumentException if contact is null.
     */
    public void setContactInfo(Contact contact) {
        Utils.checkNull(contact, "contact");
        if (!contact.equals(this.contact)) {
            this.contact = contact;
            setChanged(true);
        }
    }

    /**
     * <p>
     * Retrieves the address of the user. It may be null when the User object is initially constructed, but it may
     * not be set to a null afterwards.
     * </p>
     *
     *
     *
     * @return the address of the user.
     */
    public Address getAddress() {
        return address;
    }

    /**
     * <p>
     * Sets the address of the user. It may be null when the User object is initially constructed, but it may not
     * be set to a null afterwards.
     * </p>
     *
     * @param address The address of the user.
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
     * Retrieves the algorithm name for the encryption algorithm to be used when setting the company password. The
     * algorithm name is used to retrieve the encryption algorithm from the Encryption component. It is expected
     * that the Encryption component would have been initialized with the encryption keys (if necessary) prior to
     * using this component.
     * </p>
     * <p>
     * It may be null when the User object is initially constructed, but it may not be set to a null or empty
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
     * This is the name of the encryption algorithm to be used when setting the company password. The
     * algorithm name is used to retrieve the encryption algorithm from the EncryptionRepository.
     * </p>
     * <p>
     * It may be null when the User object is initially constructed, but it may not be set to a null or empty
     * String afterwards.
     * </p>
     *
     * @param algorithmName the algorithm name for the encryption algorithm to be used when setting the company
     *        passcode.
     * @throws IllegalArgumentException if the algorithmName is null, an empty String, or it doesn't exist within
     *         the EncryptionRepository.
     */
    public void setAlgorithmName(String algorithmName) {
        Utils.checkString(algorithmName, "algorithmName", false);
        if (EncryptionRepository.getInstance().retrieveAlgorithm(algorithmName) == null) {
            throw new IllegalArgumentException("The encryption algorithm not exists. Name: " + algorithmName);
        }

        this.algorithmName = algorithmName;
    }

    /**
     * <p>
     * Retrieves the first name of the User. This is equivalent to calling getContact().getFirstName().
     * </p>
     *
     *
     * @return The first name of the user.
     */
    public String getFirstName() {
        if (contact != null) {
            return contact.getFirstName();
        }
        return null;
    }

    /**
     * <p>
     * Sets the first name of the User. This is equivalent to calling getContact().setFirstName().
     * </p>
     *
     *
     *
     * @param firstName The first name of the user.
     * @throws IllegalArgumentException if the firstName is null or an empty String.
     */
    public void setFirstName(String firstName) {
        getContactInt().setFirstName(firstName);
    }

    /**
     * <p>
     * Gets the last name of the User. This is equivalent to calling getContact().getLastName().
     * </p>
     *
     *
     *
     * @return the last name of the User.
     */
    public String getLastName() {
        if (contact != null) {
            return contact.getLastName();
        }
        return null;
    }

    /**
     * <p>
     * Sets the last name of the User. This is equivalent to calling getContact().setLastName().
     * </p>
     *
     *
     * @param lastName The last name of the User.
     * @throws IllegalArgumentException if lastName is null or an empty String.
     */
    public void setLastName(String lastName) {
        getContactInt().setLastName(lastName);
    }

    /**
     * <p>
     * Gets the phone number of the User. This is equivalent to calling getContact().getPhoneNumber().
     * </p>
     *
     *
     * @return The phone number of the user.
     */
    public String getPhoneNumber() {
        if (contact != null) {
            return contact.getPhoneNumber();
        }
        return null;
    }

    /**
     * <p>
     * Sets the phone number of the User. This is equivalent to calling getContact().setPhoneNumber().
     * </p>
     *
     * @param phoneNumber The phone number of the user.
     * @throws IllegalArgumentException if phoneNumber is null or an empty String.
     */
    public void setPhoneNumber(String phoneNumber) {
        getContactInt().setPhoneNumber(phoneNumber);
    }

    /**
     * <p>
     * Gets the email address of the User. This is equivalent to calling getContact().getEmailAddress().
     * </p>
     *
     *
     *
     * @return The email address of the user.
     */
    public String getEmailAddress() {
        if (contact != null) {
            return contact.getEmailAddress();
        }
        return null;
    }

    /**
     * <p>
     * Sets the email address of the User. This is equivalent to calling getContact().setEmailAddress().
     * </p>
     *
     *
     * @param email The email address of the user.
     * @throws IllegalArgumentException if the email address is null or an empty String.
     */
    public void setEmailAddress(String email) {
        getContactInt().setEmailAddress(email);
    }

    /**
     * This method returns the Contact object that belongs to the user. If the contact is not already set,
     * the new instance will be created.
     *
     * @return the current Contact or new one if none exists.
     */
    private Contact getContactInt() {
        if (contact == null) {
            contact = new Contact();
        }

        return contact;
    }
}
