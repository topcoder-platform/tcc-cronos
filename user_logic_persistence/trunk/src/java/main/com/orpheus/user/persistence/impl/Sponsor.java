/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence.impl;

import com.orpheus.user.persistence.UserConstants;
import com.orpheus.user.persistence.UserProfileDAO;
import com.orpheus.user.persistence.ejb.UserProfileDTO;

/**
 * <p>
 * An extension of the {@link User} class that stores the sponsor user profile
 * information. This class corresponds to the
 * {@link UserConstants#SPONSOR_TYPE_NAME} user profile type. It is wrapped
 * inside {@link UserProfileDTO} objects using the
 * {@link UserProfileDTO#SPONSOR_KEY} key, and transported between the EJB
 * client and the {@link UserProfileDAO} to facilitate the persistence of the
 * sponsor user profile information.
 * </p>
 * <p>
 * Besides the fields defined in the <code>User</code> class, this class also
 * stores the following information. The constants shown in brackets are the
 * names of the corresponding properties in a <code>UserProfile</code> object.
 * </p>
 * <ul>
 * <li>a flag indicating whether the sponsor was approved or not
 * ({@link UserConstants#SPONSOR_APPROVED})</li>
 * <li>the sponsor's fax number ({@link UserConstants#SPONSOR_FAX_NUMBER})</li>
 * <li>the sponsor's payment preference ({@link UserConstants#SPONSOR_PAYMENT_PREF})</li>
 * </ul>
 * <p>
 * <b>Thread-safety:</b><br> This class is mutable and, thus, not thread-safe.
 * </p>
 *
 * @author argolite, mpaulse
 * @version 1.0
 * @see UserProfileDTO
 */
public class Sponsor extends User {

    /**
     * <p>
     * Represents an approved sponsor.
     * </p>
     */
    public static final String APPROVED_TRUE = "Y";

    /**
     * <p>
     * Represents a sponsor that is not approved.
     * </p>
     */
    public static final String APPROVED_FALSE = "N";

    /**
     * <p>
     * Represents a sponsor that has not been evaluated for approval.
     * </p>
     */
    public static final String APPROVED_UNDECIDED = null;

    /**
     * <p>
     * The fax number.
     * </p>
     * <p>
     * This field is set and accessed in the setFax(String) and getFax()
     * methods, respectively. It can be any value.
     * </p>
     */
    private String fax;

    /**
     * <p>
     * The payment preference.
     * </p>
     * <p>
     * This field is set and accessed in the setPaymentPref(String) and
     * getPaymentPref() methods, respectively. It can be any value.
     * </p>
     */
    private String paymentPref;

    /**
     * <p>
     * A flag indicating whether the sponsor was approved or not.
     * </p>
     * <p>
     * This field is set and accessed in the setApproved(String) and
     * getApproved() methods, respectively. It can be any value, but is expected
     * to be equal to either APPROVED_TRUE, APPROVED_FALSE or
     * APPROVED_UNDECIDED.
     * </p>
     */
    private String approved;

    /**
     * <p>
     * Creates a new <code>Sponsor</code> object with the specified
     * identifier.
     * </p>
     *
     * @param id the sponsor user identifier
     */
    public Sponsor(long id) {
        super(id);
    }

    /**
     * <p>
     * Gets the fax number. This field corresponds to the
     * {@link UserConstants#SPONSOR_FAX_NUMBER} property in a
     * <code>UserProfile</code> object.
     * </p>
     * <p>
     * Since no validation is performed when the fax number is set, this method
     * may return any value, including a <code>null</code> or a blank string.
     * </p>
     *
     * @return the fax number
     * @see #setFax(String)
     */
    public String getFax() {
        return fax;
    }

    /**
     * <p>
     * Sets the fax number. This field corresponds to the
     * {@link UserConstants#SPONSOR_FAX_NUMBER} property in a
     * <code>UserProfile</code> object.
     * </p>
     * <p>
     * This method accepts any input value, even <code>null</code> or a blank
     * string.
     * </p>
     *
     * @param fax the fax number
     * @see #getFax()
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * <p>
     * Gets the sponsor's payment preference. This field corresponds to the
     * {@link UserConstants#SPONSOR_PAYMENT_PREF} property in a
     * <code>UserProfile</code> object.
     * </p>
     * <p>
     * Since no validation is performed when the payment preference is set, this
     * method may return any value, including a <code>null</code> or a blank
     * string.
     * </p>
     *
     * @return the sponsor's payment preference
     * @see #setPaymentPref(String)
     */
    public String getPaymentPref() {
        return paymentPref;
    }

    /**
     * <p>
     * Sets the player's payment preference. This field corresponds to the
     * {@link UserConstants#SPONSOR_PAYMENT_PREF} property in a
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

    /**
     * <p>
     * Gets the flag indicating whether the sponsor is approved or not. This
     * field corresponds to the {@link UserConstants#SPONSOR_APPROVED} property
     * in a <code>UserProfile</code> object.
     * </p>
     * <p>
     * Since no validation is performed when the flag is set, this method may
     * return any value, including a <code>null</code> or a blank string.
     * </p>
     *
     * @return a flag indicating whether the sponsor is approved or not
     * @see #setApproved(String)
     */
    public String getApproved() {
        return approved;
    }

    /**
     * <p>
     * Sets the flag indicating whether the sponsor is approved or not. This
     * field corresponds to the {@link UserConstants#SPONSOR_APPROVED} property
     * in a <code>UserProfile</code> object.
     * </p>
     * <p>
     * This method accepts any input value, even <code>null</code> or a blank
     * string.
     * </p>
     *
     * @param approved the flag indicating whether the sponsor is approved or
     *        not
     * @see #getApproved()
     */
    public void setApproved(String approved) {
        this.approved = approved;
    }

}
