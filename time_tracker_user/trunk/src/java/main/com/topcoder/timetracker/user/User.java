/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

import com.topcoder.timetracker.common.TimeTrackerBean;
import com.topcoder.timetracker.contact.Address;
import com.topcoder.timetracker.contact.Contact;

/**
 * <p>
 * This is the main data class of the component, and includes getters and setters to access the various properties
 * of a Time Tracker User.
 * </p>
 * <p>
 * This class encapsulates the user's account information within the Time Tracker component. It contains the user's
 * login and authentication details, contact information, and account status.
 * </p>
 * <p>
 * It also extends from the base <code>TimeTrackerBean</code> to include the id, creation and modification
 * details.
 * </p>
 * <p>
 * Thread Safety: This class is not thread safe. Each thread is expected to work on it's own instance, or this
 * class should be used in a read-only manner for concurrent access.
 * </p>
 * <p>
 * <strong>Version 3.2.1</strong> adds two information user status id and user type id.
 * </p>
 *
 * @author ShindouHikaru, biotrail, George1, enefem21
 * @version 3.2.1
 * @since 3.2
 */
public class User extends TimeTrackerBean {

    /**
     * Automatically generated unique ID for use with serialization.
     */
    private static final long serialVersionUID = -5134657804874224299L;

    /**
     * <p>
     * This is the user's password.
     * </p>
     * <p>
     * It is known only to the user, and is used to authenticate a user.
     * </p>
     * <p>
     * It is null initially and can be accessed by getter and setter.
     * </p>
     * <p>
     * It can not be null or empty string after it is set.
     * </p>
     */
    private String password;

    /**
     * <p>
     * This is the user name that is used by the user.
     * </p>
     * <p>
     * It is the common name which is used to refer to an individual user.
     * </p>
     * <p>
     * It is null initially and can be accessed by getter and setter.
     * </p>
     * <p>
     * It can not be null or empty string after it is set.
     * </p>
     */
    private String username;

    /**
     * <p>
     * This is a number that uniquely identifies the company that this user is associated with.
     * </p>
     * <p>
     * This is used to tie into the Time Tracker Company component.
     * </p>
     * <p>
     * It is zero initially and can be accessed by getter and setter.
     * </p>
     * <p>
     * It can not be negative or zero after it is set.
     * </p>
     */
    private long companyId;

    /**
     * <p>
     * This is the contact information that may be used in order to get in touch with the user.
     * </p>
     * <p>
     * It is null initially and can be accessed by getter and setter.
     * </p>
     * <p>
     * It can not be null after it is set.
     * </p>
     */
    private Contact contact;

    /**
     * <p>
     * This is the Address, representing the location where the user is based.
     * </p>
     * <p>
     * It is null initially and can be accessed by getter and setter.
     * </p>
     * <p>
     * It can not be null after it is set.
     * </p>
     */
    private Address address;

    /**
     * <p>
     * This is the current status of the user account.
     * </p>
     * <p>
     * A user's status may be <code>ACTIVE</code>, <code>INACTIVE</code>, or <code>LOCKED</code>.
     * </p>
     * <p>
     * It is null initially and can be accessed by getter and setter.
     * </p>
     * <p>
     * It can not be null after it is set.
     * </p>
     */
    private Status status;

    /**
     * <p>
     * This is the status of the user, which is an indication of how the User is being processed by the Time
     * Tracker system.
     * </p>
     * <p>
     * Initial Value: null
     * </p>
     * <p>
     * Accessed In: getUserStatus
     * </p>
     * <p>
     * Modified In: setUserStatus
     * </p>
     * <p>
     * Utilized In: Not Utilized
     * </p>
     * <p>
     * Valid Values: Null or Non-null UserStatus object (a status can be reset back to null if needed)
     * </p>
     */
    private UserStatus userStatus;

    /**
     * <p>
     * This indicates the type of user.
     * </p>
     * <p>
     * Initial Value: null
     * </p>
     * <p>
     * Accessed In: getUserType
     * </p>
     * <p>
     * Modified In: setUserType
     * </p>
     * <p>
     * Utilized In: Not Utilized
     * </p>
     * <p>
     * Valid Values: Null, or Non-null UserType objects (a type can be reset back to null if needed).
     * </p>
     */
    private UserType userType;

    private int billableType;

    /**
     * <p>
     * This is the name of an algorithm that may be used to encrypt the password for security purposes.
     * </p>
     * <p>
     * Note, it is not used as of this version and is left for future versions.
     * </p>
     * <p>
     * It is null initially and can be accessed by getter and setter.
     * </p>
     * <p>
     * It will never be an empty string.
     * </p>
     */
    private String algorithmName;

    /**
     * <p>
     * Default Constructor.
     * </p>
     */
    public User() {
        // empty
    }

    /**
     * <p>
     * Retrieves the user's password.
     * </p>
     *
     * <p>
     * It is known only to the user, and is used to authenticate a user.
     * </p>
     *
     * @return the user's password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * <p>
     * Sets the user's password.
     * </p>
     *
     * <p>
     * It is known only to the user, and is used to authenticate a user.
     * </p>
     *
     * @param password
     *            the description of a project.
     *
     * @throws IllegalArgumentException
     *             if password is null or an empty String.
     */
    public void setPassword(String password) {
        Util.checkString(password, "password");
        if (this.password == null || !password.equals(this.password)) {
            this.password = password;
            setChanged(true);
        }
    }

    /**
     * <p>
     * Retrieves the user name of the user.
     * </p>
     *
     * <p>
     * It is the common name which is used to refer to an individual user.
     * </p>
     *
     * @return the user name that is used by the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * <p>
     * Sets the user name of the user.
     * </p>
     *
     * <p>
     * It is the common name which is used to refer to an individual user.
     * </p>
     *
     * @param username
     *            the user name of the user.
     *
     * @throws IllegalArgumentException
     *             if user name is null or an empty String.
     */
    public void setUsername(String username) {
        Util.checkString(username, "username");
        if (this.username == null || !username.equals(this.username)) {
            this.username = username;
            setChanged(true);
        }
    }

    /**
     * <p>
     * Retrieves a number that uniquely identifies the company that this user is associated with.
     * </p>
     *
     * <p>
     * This is used to tie into the Time Tracker Company component.
     * </p>
     *
     * @return a number that uniquely identifies the company that this user is associated with.
     */
    public long getCompanyId() {
        return companyId;
    }

    /**
     * <p>
     * Sets a number that uniquely identifies the company that this user is associated with.
     * </p>
     *
     * <p>
     * This is used to tie into the Time Tracker Company component.
     * </p>
     *
     * @param companyId
     *            a number that uniquely identifies the company that this user is associated with.
     *
     * @throws IllegalArgumentException
     *             if companyId is negative or 0.
     */
    public void setCompanyId(long companyId) {
        if (companyId <= 0) {
            throw new IllegalArgumentException("The given company id is negative or zero.");
        }
        if (companyId != this.companyId) {
            this.companyId = companyId;
            setChanged(true);
        }
    }

    /**
     * <p>
     * Retrieves the contact information that may be used in order to get in touch with the user.
     * </p>
     *
     * @return the contact information for the user.
     */
    public Contact getContact() {
        return contact;
    }

    /**
     * <p>
     * Sets the contact information that may be used in order to get in touch with someone related with the
     * project.
     * </p>
     *
     *
     * @param contact
     *            the contact information for the user.
     *
     * @throws IllegalArgumentException
     *             if contact is null.
     */
    public void setContact(Contact contact) {
        Util.checkNull(contact, "contact");
        if (this.contact == null || contact.isChanged() || contact.getId() != this.contact.getId()) {
            this.contact = contact;
            this.contact.setChanged(true);
            setChanged(true);
        }
    }

    /**
     * <p>
     * Gets the <code>Address</code>, representing the location where the user is based.
     * </p>
     *
     * @return The address of the user.
     */
    public Address getAddress() {
        return address;
    }

    /**
     * <p>
     * Sets the <code>Address</code>, representing the location where the user is based.
     * </p>
     *
     * @param address
     *            The address of the user.
     *
     * @throws IllegalArgumentException
     *             if address is null.
     */
    public void setAddress(Address address) {
        Util.checkNull(address, "address");
        if (this.address == null || address.isChanged() || address.getId() != this.address.getId()) {
            this.address = address;
            this.address.setChanged(true);
            setChanged(true);
        }
    }

    /**
     * <p>
     * Retrieves the current status of the user account.
     * </p>
     *
     * <p>
     * A user's status may be <code>ACTIVE</code>, <code>INACTIVE</code>, or <code>LOCKED</code>.
     * </p>
     *
     * @return the current status of the user account.
     */
    public Status getStatus() {
        return status;
    }

    /**
     * <p>
     * Sets the current status of the user account.
     * </p>
     *
     * <p>
     * A user's status may be <code>ACTIVE</code>, <code>INACTIVE</code>, or <code>LOCKED</code>.
     * </p>
     *
     * @param status
     *            the current status of the user account.
     *
     * @throws IllegalArgumentException
     *             if status is null.
     */
    public void setStatus(Status status) {
        Util.checkNull(status, "status");
        if (this.status == null || status.getId() != this.status.getId()) {
            this.status = status;
            setChanged(true);
        }
    }

    /**
     * <p>
     * Gets the status of the user, which is an indication of how the User is being processed by the Time Tracker
     * system..
     * </p>
     *
     * @return the status of the user.
     * @since 3.2.1
     */
    public UserStatus getUserStatus() {
        return userStatus;
    }

    /**
     * <p>
     * Sets the status of the user, which is an indication of how the User is being processed by the ime Tracker
     * system.
     * </p>
     *
     * @param userStatus
     *            the status of the user.
     * @since 3.2.1
     */
    public void setUserStatus(UserStatus userStatus) {
        if ((this.userStatus == null && userStatus != null)
            || (this.userStatus != null && userStatus == null)
            || (userStatus != null && this.userStatus != null && userStatus.getId() != this.userStatus
                .getId())) {
            this.userStatus = userStatus;
            setChanged(true);
        }
    }

    /**
     * <p>
     * Gets the type of user.
     * </p>
     *
     * @return the type of user.
     * @since 3.2.1
     */
    public UserType getUserType() {
        return userType;
    }

    /**
     * <p>
     * Sets the type of user.
     * </p>
     *
     * @param userType
     *            the type of user.
     * @since 3.2.1
     */
    public void setUserType(UserType userType) {
        if ((this.userType == null && userType != null) || (this.userType != null && userType == null)
            || (userType != null && this.userType != null && userType.getId() != this.userType.getId())) {
            this.userType = userType;
            setChanged(true);
        }
    }

    /**
     * <p>
     * Retrieves the name of an algorithm that may be used to encrypt the password for security purposes.
     * </p>
     *
     * <p>
     * It is not used as of this version but is left for future versions.
     * </p>
     *
     * @return name of algorithm to encrypt the password.
     */
    public String getAlgorithmName() {
        return algorithmName;
    }

    /**
     * <p>
     * Sets the name of an algorithm that may be used to encrypt the password for security purposes.
     * </p>
     *
     * <p>
     * It is not used as of this version but is left for future versions.
     * </p>
     *
     * @param algorithmName
     *            name of algorithm to encrypt the password (may be null).
     *
     * @throws IllegalArgumentException
     *             if algorithmName is an empty String.
     */
    public void setAlgorithmName(String algorithmName) {
        if (algorithmName != null) {
            Util.checkString(algorithmName, "algorithmName");
        }
        if ((this.algorithmName != null && algorithmName == null)
            || (this.algorithmName == null && algorithmName != null)
            || (this.algorithmName != null && algorithmName != null && !this.algorithmName
                .equals(algorithmName))) {
            this.algorithmName = algorithmName;
            setChanged(true);
        }
    }

	public int getBillableType() {
		return billableType;
	}

	public void setBillableType(int billableType) {
		this.billableType = billableType;
	}


}