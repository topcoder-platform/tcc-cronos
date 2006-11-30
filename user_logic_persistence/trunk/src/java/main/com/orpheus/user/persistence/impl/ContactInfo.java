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
 * Stores the player or sponsor contact information. This class corresponds to
 * the {@link UserConstants#ADDRESS_TYPE_NAME} and
 * <code>BaseProfileType.BASE_NAME</code> user profile types. It is wrapped
 * inside {@link UserProfileDTO} objects using the
 * {@link UserProfileDTO#CONTACT_INFO_KEY} key, and transported between the EJB
 * client and the {@link UserProfileDAO} to facilitate the persistence of the
 * player or sponsor contact information.
 * </p>
 * <p>
 * This class stores the following information. The constants shown in brackets
 * are the names of the corresponding properties in a <code>UserProfile</code>
 * object.
 * </p>
 * <ul>
 * <li>the contact information ID</li>
 * <li>the user's first name (<code>BaseProfileType.FIRST_NAME</code>)</li>
 * <li>the user's last name (<code>BaseProfileType.LAST_NAME</code>)</li>
 * <li>the first line of the user's address ({@link UserConstants#ADDRESS_STREET_1})</li>
 * <li>the second line of the user's address ({@link UserConstants#ADDRESS_STREET_2})</li>
 * <li>the city ({@link UserConstants#ADDRESS_CITY})</li>
 * <li>the state ({@link UserConstants#ADDRESS_STATE})</li>
 * <li>the postal code ({@link UserConstants#ADDRESS_POSTAL_CODE})</li>
 * <li>the user's telephone number ({@link UserConstants#ADDRESS_PHONE_NUMBER})</li>
 * </ul>
 * <p>
 * <b>Thread-safety:</b><br> This class is mutable and, thus, not thread-safe.
 * </p>
 *
 * @author argolite, mpaulse
 * @version 1.0
 * @see UserProfileDTO
 */
public class ContactInfo implements Serializable {

    /**
     * <p>
     * The contact information ID.
     * </p>
     * <p>
     * This field is set and accessed in the setId(long) and getId()
     * methods, respectively It can be any value.
     * </p>
     */
    private long id;

    /**
     * <p>
     * The user's first name.
     * </p>
     * <p>
     * This field is set and accessed in the setFirstName(String) and
     * getFirstName() methods, respectively. It can be any value.
     * </p>
     */
    private String firstName;

    /**
     * <p>
     * The user's last name.
     * </p>
     * <p>
     * This field is set and accessed in the setLastName(String) and
     * getLastName() methods, respectively. It can be any value.
     * </p>
     */
    private String lastName;

    /**
     * <p>
     * The first line of the user's address.
     * </p>
     * <p>
     * This field is set and accessed in the setAddress1(String) and
     * getAddress1() methods, respectively. It can be any value.
     * </p>
     */
    private String address1;

    /**
     * <p>
     * The second line of the user's address.
     * </p>
     * <p>
     * This field is set and accessed in the setAddress2(String) and
     * getAddress2() methods, respectively. It can be any value.
     * </p>
     */
    private String address2;

    /**
     * <p>
     * The city.
     * </p>
     * <p>
     * This field is set and accessed in the setCity(String) and getCity()
     * methods, respectively. It can be any value.
     * </p>
     */
    private String city;

    /**
     * <p>
     * The state.
     * </p>
     * <p>
     * This field is set and accessed in the setState(String) and getState()
     * methods, respectively. It can be any value.
     * </p>
     */
    private String state;

    /**
     * <p>
     * The postal code.
     * </p>
     * <p>
     * This field is set and accessed in the setPostalCode(String) and
     * getPostalCode() methods, respectively. It can be any value.
     * </p>
     */
    private String postalCode;

    /**
     * <p>
     * The user's telephone number.
     * </p>
     * <p>
     * This field is set and accessed in the setTelephone(String) and
     * getTelephone() methods, respectively. It can be any value.
     * </p>
     */
    private String telephone;

    /**
     * <p>
     * Creates a new <code>ContactInfo</code> object with the specified ID.
     * </p>
     *
     * @param id the ID of the contact information
     */
    public ContactInfo(long id) {
        setId(id);
    }

    /**
     * <p>
     * Gets the ID of the contact information.
     * </p>
     *
     * @return the ID of the contact information
     * @see #setId(long)
     */
    public long getId() {
        return id;
    }

    /**
     * <p>
     * Sets the ID of the contact information.
     * </p>
     *
     * @param id the ID of the contact information
     * @see #getId()
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * <p>
     * Gets the user's first name. This field corresponds to the
     * <code>BaseProfileType.FIRST_NAME</code> property in a
     * <code>UserProfile</code> object.
     * </p>
     * <p>
     * Since no validation is performed when the first name is set, this method
     * may return any value, including a <code>null</code> or a blank string.
     * </p>
     *
     * @return the user's first name
     * @see #setFirstName(String)
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * <p>
     * Sets the user's first name. This field corresponds to the
     * <code>BaseProfileType.FIRST_NAME</code> property in a
     * <code>UserProfile</code> object.
     * </p>
     * <p>
     * This method accepts any input value, even <code>null</code> or a blank
     * string.
     * </p>
     *
     * @param firstName the user's first name
     * @see #getFirstName()
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * <p>
     * Gets the user's last name. This field corresponds to the
     * <code>BaseProfileType.FIRST_NAME</code> property in a
     * <code>UserProfile</code> object.
     * </p>
     * <p>
     * Since no validation is performed when the last name is set, this method
     * may return any value, including a <code>null</code> or a blank string.
     * </p>
     *
     * @return the user's last name
     * @see #setLastName(String)
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * <p>
     * Sets the user's last name. This field corresponds to the
     * <code>BaseProfileType.FIRST_NAME</code> property in a
     * <code>UserProfile</code> object.
     * </p>
     * <p>
     * This method accepts any input value, even <code>null</code> or a blank
     * string.
     * </p>
     *
     * @param lastName the user's last name
     * @see #getLastName()
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * <p>
     * Gets the first line of the user's address. This field corresponds to the
     * {@link UserConstants#ADDRESS_STREET_1} property in a
     * <code>UserProfile</code> object.
     * </p>
     * <p>
     * Since no validation is performed when the address is set, this method may
     * return any value, including a <code>null</code> or a blank string.
     * </p>
     *
     * @return the first line of the user's address
     * @see #setAddress1(String)
     */
    public String getAddress1() {
        return address1;
    }

    /**
     * <p>
     * Sets the first line of the user's address. This field corresponds to the
     * {@link UserConstants#ADDRESS_STREET_1} property in a
     * <code>UserProfile</code> object.
     * </p>
     * <p>
     * This method accepts any input value, even <code>null</code> or a blank
     * string.
     * </p>
     *
     * @param address1 the first line of the user's address
     * @see #getAddress1()
     */
    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    /**
     * <p>
     * Gets the second line of the user's address. This field corresponds to the
     * {@link UserConstants#ADDRESS_STREET_2} property in a
     * <code>UserProfile</code> object.
     * </p>
     * <p>
     * Since no validation is performed when the address is set, this method may
     * return any value, including a <code>null</code> or a blank string.
     * </p>
     *
     * @return the second line of the user's address
     * @see #setAddress2(String)
     */
    public String getAddress2() {
        return address2;
    }

    /**
     * <p>
     * Sets the second line of the user's address. This field corresponds to the
     * {@link UserConstants#ADDRESS_STREET_2} property in a
     * <code>UserProfile</code> object.
     * </p>
     * <p>
     * This method accepts any input value, even <code>null</code> or a blank
     * string.
     * </p>
     *
     * @param address2 the second line of the user's address
     * @see #getAddress2()
     */
    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    /**
     * <p>
     * Gets the city. This field corresponds to the
     * {@link UserConstants#ADDRESS_CITY} property in a <code>UserProfile</code>
     * object.
     * </p>
     * <p>
     * Since no validation is performed when the city is set, this method may
     * return any value, including a <code>null</code> or a blank string.
     * </p>
     *
     * @return the city
     * @see #setCity(String)
     */
    public String getCity() {
        return city;
    }

    /**
     * <p>
     * Sets the city. This field corresponds to the
     * {@link UserConstants#ADDRESS_CITY} property in a <code>UserProfile</code>
     * object.
     * </p>
     * <p>
     * This method accepts any input value, even <code>null</code> or a blank
     * string.
     * </p>
     *
     * @param city the city
     * @see #getCity()
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * <p>
     * Gets the state. This field corresponds to the
     * {@link UserConstants#ADDRESS_STATE} property in a
     * <code>UserProfile</code> object.
     * </p>
     * <p>
     * Since no validation is performed when the state is set, this method may
     * return any value, including a <code>null</code> or a blank string.
     * </p>
     *
     * @return the state
     * @see #setState(String)
     */
    public String getState() {
        return state;
    }

    /**
     * <p>
     * Sets the state. This field corresponds to the
     * {@link UserConstants#ADDRESS_STATE} property in a
     * <code>UserProfile</code> object.
     * </p>
     * <p>
     * This method accepts any input value, even <code>null</code> or a blank
     * string.
     * </p>
     *
     * @param state the state
     * @see #getState()
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * <p>
     * Gets the postal code. This field corresponds to the
     * {@link UserConstants#ADDRESS_POSTAL_CODE} property in a
     * <code>UserProfile</code> object.
     * </p>
     * <p>
     * Since no validation is performed when the postal code is set, this method
     * may return any value, including a <code>null</code> or a blank string.
     * </p>
     *
     * @return the postal code
     * @see #setPostalCode(String)
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * <p>
     * Sets the postal code. This field corresponds to the
     * {@link UserConstants#ADDRESS_POSTAL_CODE} property in a
     * <code>UserProfile</code> object.
     * </p>
     * <p>
     * This method accepts any input value, even <code>null</code> or a blank
     * string.
     * </p>
     *
     * @param postalCode the postal code
     * @see #getPostalCode()
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * <p>
     * Gets the user's telephone number. This field corresponds to the
     * {@link UserConstants#ADDRESS_PHONE_NUMBER} property in a
     * <code>UserProfile</code> object.
     * </p>
     * <p>
     * Since no validation is performed when the telephone number is set, this
     * method may return any value, including a <code>null</code> or a blank
     * string.
     * </p>
     *
     * @return the user's telephone number
     * @see #setTelephone(String)
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * <p>
     * Sets the user's telephone number. This field corresponds to the
     * {@link UserConstants#ADDRESS_PHONE_NUMBER} property in a
     * <code>UserProfile</code> object.
     * </p>
     * <p>
     * This method accepts any input value, even <code>null</code> or a blank
     * string.
     * </p>
     *
     * @param telephone the user's telephone number
     * @see #getTelephone()
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

}
