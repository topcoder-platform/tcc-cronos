/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence.impl;

import java.io.Serializable;

import com.orpheus.user.persistence.UserConstants;
import com.orpheus.user.persistence.UserProfileDAO;
import com.orpheus.user.persistence.ejb.UserProfileDTO;

/**
 * <p>
 * Stores the user information common to all the user types. Subclasses of
 * this class are wrapped inside {@link UserProfileDTO} objects, and transported
 * between the EJB client and the {@link UserProfileDAO} to facilitate the
 * persistence of user information.
 * </p>
 * <p>
 * This class corresponds to the {@link UserConstants#CREDENTIALS_TYPE_NAME} and
 * <code>BaseProfileType.BASE_NAME</code> user profile types. It stores the
 * following information. The values shown in brackets are the names of the
 * corresponding properties in a <code>UserProfile</code> object.
 * </p>
 * <ul>
 * <li>the user identifier (<code>UserProfile.getIdentifier()</code>)</li>
 * <li>the user handle ({@link UserConstants#CREDENTIALS_HANDLE})</li>
 * <li>the user's email address (<code>UserProfile.EMAIL_ADDRESS</code>)</li>
 * <li>the user password ({@link UserConstants#CREDENTIALS_PASSWORD})</li>
 * <li>a flag indicating whether the user is active or not ({@link UserConstants#CREDENTIALS_IS_ACTIVE})</li>
 * </ul>
 * <p>
 * <b>Thread-safety:</b><br> This class is mutable and, thus, not thread-safe.
 * </p>
 *
 * @author argolite, mpaulse
 * @version 1.0
 * @see UserProfileDTO
 */
public abstract class User implements Serializable {

    /**
     * <p>
     * The user identifier.
     * </p>
     * <p>
     * This field is set and accessed in the setId(long) and getId() methods,
     * respectively. It can be any value.
     * </p>
     */
    private long id;

    /**
     * <p>
     * The user handle.
     * </p>
     * <p>
     * This field is set and accessed in the setHandle(String) and getHandle()
     * methods, respectively. It can be any value.
     * </p>
     */
    private String handle;

    /**
     * <p>
     * The user's email address.
     * </p>
     * <p>
     * This field is set and accessed in the setEmail(String) and getEmail()
     * methods, respectively. It can be any value.
     * </p>
     */
    private String email;

    /**
     * <p>
     * The user password.
     * </p>
     * <p>
     * This field is set and accessed in the setPassword(String) and
     * getPassword() methods, respectively. It can be any value.
     * </p>
     */
    private String password;

    /**
     * <p>
     * A flag indicating whether the user is active or not.
     * </p>
     * <p>
     * This field is set and accessed in the setActive(String) and getActive()
     * methods, respectively. It can be any value, but is expected to be either
     * "true" or "false".
     * </p>
     */
    private String active;

    /**
     * <p>
     * Creates a new <code>User</code> object with the specified user
     * identifier.
     * </p>
     *
     * @param id the user identifier
     */
    protected User(long id) {
        setId(id);
    }

    /**
     * <p>
     * Gets the user identifier. This field corresponds to the return value of
     * the <code>UserProfile.getIdentifier()</code> method.
     * </p>
     *
     * @return the user identifier
     * @see #setId(long)
     */
    public long getId() {
        return id;
    }

    /**
     * <p>
     * Sets the user identifier. This field corresponds to the return value of
     * the <code>UserProfile.getIdentifier()</code> method.
     * </p>
     *
     * @param id the user identifier
     * @see #getId()
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * <p>
     * Gets the user handle. This field corresponds to the
     * {@link UserConstants#CREDENTIALS_HANDLE} property in a
     * <code>UserProfile</code> object.
     * </p>
     * <p>
     * Since no validation is performed when the handle is set, this method may
     * return any value, including a <code>null</code> or a blank string.
     * </p>
     *
     * @return the user handle
     * @see #setHandle(String)
     */
    public String getHandle() {
        return handle;
    }

    /**
     * <p>
     * Sets the user handle. This field corresponds to the
     * {@link UserConstants#CREDENTIALS_HANDLE} property in a
     * <code>UserProfile</code> object.
     * </p>
     * <p>
     * This method accepts any input value, even <code>null</code> or a blank
     * string.
     * </p>
     *
     * @param handle the user handle
     * @see #getHandle()
     */
    public void setHandle(String handle) {
        this.handle = handle;
    }

    /**
     * <p>
     * Gets the user's email address. This field corresponds to the
     * <code>BaseProfileType.EMAIL_ADDRESS</code> property in a
     * <code>UserProfile</code> object.
     * </p>
     * <p>
     * Since no validation is performed when the email address is set, this method may
     * return any value, including a <code>null</code> or a blank string.
     * </p>
     *
     * @return the user's email address
     * @see #setEmail(String)
     */
    public String getEmail() {
        return email;
    }

    /**
     * <p>
     * Sets the user's email address. This field corresponds to the
     * <code>BaseProfileType.EMAIL_ADDRESS</code> property in a
     * <code>UserProfile</code> object.
     * </p>
     * <p>
     * This method accepts any input value, even <code>null</code> or a blank
     * string.
     * </p>
     *
     * @param email the user's email address
     * @see #getEmail()
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * <p>
     * Gets the user password. This field corresponds to the
     * {@link UserConstants#CREDENTIALS_PASSWORD} property in a
     * <code>UserProfile</code> object.
     * </p>
     * <p>
     * Since no validation is performed when the password is set, this method may
     * return any value, including a <code>null</code> or a blank string.
     * </p>
     *
     * @return the user password
     * @see #setPassword(String)
     */
    public String getPassword() {
        return password;
    }

    /**
     * <p>
     * Sets the user password. This field corresponds to the
     * {@link UserConstants#CREDENTIALS_PASSWORD} property in a
     * <code>UserProfile</code> object.
     * </p>
     * <p>
     * This method accepts any input value, even <code>null</code> or a blank
     * string.
     * </p>
     *
     * @param password the user password
     * @see #getPassword()
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * <p>
     * Gets the flag indicating whether the user is active or not. This field
     * corresponds to the {@link UserConstants#CREDENTIALS_IS_ACTIVE} property
     * in a <code>UserProfile</code> object.
     * </p>
     * <p>
     * Since no validation is performed when the flag is set, this method may
     * return any value, including a <code>null</code> or a blank string.
     * </p>
     *
     * @return a flag indicating whether the user is active or not
     * @see #setActive(String)
     */
    public String getActive() {
        return active;
    }

    /**
     * <p>
     * Sets the flag indicating whether the user is active or not. This field
     * corresponds to the {@link UserConstants#CREDENTIALS_IS_ACTIVE} property
     * in a <code>UserProfile</code> object.
     * </p>
     * <p>
     * This method accepts any input value, even <code>null</code> or a blank
     * string.
     * </p>
     *
     * @param active the flag indicating whether the user is active or not
     * @see #getActive()
     */
    public void setActive(String active) {
        this.active = active;
    }

}
