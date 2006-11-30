/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence.impl;

import com.orpheus.user.persistence.UserConstants;
import com.orpheus.user.persistence.UserProfileDAO;
import com.orpheus.user.persistence.ejb.UserProfileDTO;

/**
 * <p>
 * An extension of the {@link User} class that stores the player user profile
 * information. This class corresponds to the
 * {@link UserConstants#PLAYER_TYPE_NAME} user profile type. It is wrapped
 * inside {@link UserProfileDTO} objects using the
 * {@link UserProfileDTO#PLAYER_KEY} key, and transported between the EJB client
 * and the {@link UserProfileDAO} to facilitate the persistence of the player
 * user profile information.
 * </p>
 * <p>
 * Besides the fields defined in the <code>User</code> class, this class also
 * stores player's payment preference corresponding to the
 * {@link UserConstants#PLAYER_PAYMENT_PREF} property in a
 * <code>UserProfile</code> object.
 * </p>
 * <p>
 * <b>Thread-safety:</b><br> This class is mutable and, thus, not thread-safe.
 * </p>
 *
 * @author argolite, mpaulse
 * @version 1.0
 * @see UserProfileDTO
 */
public class Player extends User {

    /**
     * <p>
     * The player's payment preference.
     * </p>
     * <p>
     * This field is set and accessed in the setPaymentPref(String) and
     * getPaymentPref() methods, respectively. It can be any value.
     * </p>
     */
    private String paymentPref;

    /**
     * <p>
     * Creates a new <code>Player</code> object with the specified identifier.
     * </p>
     *
     * @param id the player user identifier
     */
    public Player(long id) {
        super(id);
    }

    /**
     * <p>
     * Gets the player's payment preference. This field corresponds to the
     * {@link UserConstants#PLAYER_PAYMENT_PREF} property in a
     * <code>UserProfile</code> object.
     * </p>
     * <p>
     * Since no validation is performed when the payment preference is set,
     * this method may return any value, including a <code>null</code> or a
     * blank string.
     * </p>
     *
     * @return the player's payment preference
     * @see #setPaymentPref(String)
     */
    public String getPaymentPref() {
        return paymentPref;
    }

    /**
     * <p>
     * Sets the player's payment preference. This field corresponds to the
     * {@link UserConstants#PLAYER_PAYMENT_PREF} property in a
     * <code>UserProfile</code> object.
     * </p>
     * <p>
     * This method accepts any input value, even <code>null</code> or a blank
     * string.
     * </p>
     *
     * @param paymentPref the player's payment preference
     * @see #getPaymentPref()
     */
    public void setPaymentPref(String paymentPref) {
        this.paymentPref = paymentPref;
    }

}
