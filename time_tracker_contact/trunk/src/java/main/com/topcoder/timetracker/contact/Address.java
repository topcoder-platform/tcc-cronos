/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact;

import com.topcoder.timetracker.common.TimeTrackerBean;


/**
 * <p>
 * This class holds the information of an entry in table <em>address</em>.
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
public class Address extends TimeTrackerBean {
    /**
     * <p>
     * Serial Version UID.
     * </p>
     */
    private static final long serialVersionUID = -573431310653245595L;

    /**
     * <p>
     * Represents the first line of the address. This variable is set to null initially, is mutable.
     * It is only allowed to be set to non null, non empty string with length &lt;= 100 by the setter.
     * It is access by its getter and setter methods.
     * </p>
     */
    private String line1 = null;

    /**
     * <p>
     * Represents the second line of the address. This variable is set to null initially, is mutable.
     * It is only allowed to be set to null, or a non empty string with length &lt;= 100 by the setter.
     * It is access by its getter and setter methods.
     * </p>
     */
    private String line2 = null;

    /**
     * <p>
     * Represents the city of the address. This variable is set to null initially, is mutable.
     * It is only allowed to be set to non null, non empty string with length &lt;= 30 by the setter.
     * It is access by its getter and setter methods.
     * </p>
     */
    private String city = null;

    /**
     * <p>
     * Represents the postal code of the address. This variable is set to null initially, is mutable.
     * It is only allowed to be set to non null, non empty string with length &lt;= 10 by the setter.
     * It is access by its getter and setter methods.
     * </p>
     */
    private String postalCode = null;

    /**
     * <p>
     * Represents the country of the address. This variable is set to null initially, is mutable.
     * It is only allowed to be set to non null country by the setter.
     * It is access by its getter and setter methods.
     * </p>
     */
    private Country country = null;

    /**
     * <p>
     * Represents the state of the address. This variable is set to null initially, is mutable.
     * It is only allowed to be set to non null state by the setter.
     * It is access by its getter and setter methods.
     * </p>
     */
    private State state = null;

    /**
     * <p>
     * Represents the types related by the address. This variable is set to null initially, is mutable.
     * It is only allowed to be set to non null type by the setter.
     * It is access by its getter and setter methods.
     * </p>
     */
    private AddressType addressType = null;

    /**
     * <p>Empty constructor of the <code>Address</code>.</p>
     */
    public Address() {
        // does nothing
    }

    /**
     * <p>
     * Get the first line.
     * </p>
     *
     * @return possible null, non empty string with length &lt;= 100 representing line1
     */
    public String getLine1() {
        return this.line1;
    }

    /**
     * <p>Set the first line.</p>
     *
     * <p>
     * If the given value is valid and does not equal the value previously set, this address will be marked as changed.
     * </p>
     *
     * <p>
     * If the given value is with length greater than 100, <code>InvalidPropertyException</code> will be raised
     * when adding/updating this address.
     * </p>
     *
     * @param line1 non null, non empty string representing line1
     *
     * @throws IllegalArgumentException if the line1 is null or empty(trim'd).
     */
    public void setLine1(String line1) {
        Helper.validateStringWithIAE(line1, "Line1 of Address");

        if (!line1.equals(this.line1)) {
            this.setChanged(true);
        }

        this.line1 = line1;
    }

    /**
     * <p>Get the second line.</p>
     *
     * @return possible null, non empty string representing line2
     */
    public String getLine2() {
        return this.line2;
    }

    /**
     * <p>Set the second line. Could be null.</p>
     *
     * <p>
     * If the given value is valid and does not equal the value previously set, this address will be marked as changed.
     * </p>
     *
     * <p>
     * If the given value is with length greater than 100, <code>InvalidPropertyException</code> will be raised
     * when adding/updating this address.
     * </p>
     *
     * @param line2 possibly null, non empty string representing line2
     *
     * @throws IllegalArgumentException if the line2 is empty(trim'd)
     */
    public void setLine2(String line2) {
        if (line2 != null) {
            Helper.validateStringWithIAE(line2, "Line2 of Address");

            if (!line2.equals(this.line2)) {
                this.setChanged(true);
            }
        } else {
            if (this.line2 != null) {
                this.setChanged(true);
            }
        }

        this.line2 = line2;
    }

    /**
     * <p>Get the city.</p>
     *
     * @return possible null, non empty string representing city
     */
    public String getCity() {
        return this.city;
    }

    /**
     * <p>Set the city.</p>
     *
     * <p>
     * If the given value is valid and does not equal the value previously set, this address will be marked as changed.
     * </p>
     *
     * <p>
     * If the given value is with length greater than 30, <code>InvalidPropertyException</code> will be raised
     * when adding/updating this address.
     * </p>
     *
     * @param city non null, non empty string representing city
     *
     * @throws IllegalArgumentException if the city is null or empty(trim'd).
     */
    public void setCity(String city) {
        Helper.validateStringWithIAE(city, "City of Address");

        if (!city.equals(this.city)) {
            this.setChanged(true);
        }

        this.city = city;
    }

    /**
     * <p>Get the postal code.</p>
     *
     * @return possible null, non empty string representing postal code
     */
    public String getPostalCode() {
        return this.postalCode;
    }

    /**
     * <p>Set the postal code.</p>
     *
     * <p>
     * If the given value is valid and does not equal the value previously set, this address will be marked as changed.
     * </p>
     *
     * <p>
     * If the given value is with length greater than 10, <code>InvalidPropertyException</code> will be raised
     * when adding/updating this address.
     * </p>
     *
     * @param postalCode non null, non empty string representing postal code
     *
     * @throws IllegalArgumentException if the postalCode is null or empty(trim'd)
     */
    public void setPostalCode(String postalCode) {
        Helper.validateStringWithIAE(postalCode, "PostalCode of Address");

        if (!postalCode.equals(this.postalCode)) {
            this.setChanged(true);
        }

        this.postalCode = postalCode;
    }

    /**
     * <p>Get the country.</p>
     *
     * @return possible null country
     */
    public Country getCountry() {
        return this.country;
    }

    /**
     * <p>Set the country.</p>
     *
     * <p>
     * If the given value is valid and does not equal the value previously set, this address will be marked as changed.
     * </p>
     *
     * @param country non null country
     *
     * @throws IllegalArgumentException if the country is null
     */
    public void setCountry(Country country) {
        Helper.validateNotNullWithIAE(country, "Country of Address");

        if (!country.equals(this.country)) {
            this.setChanged(true);
        }

        this.country = country;
    }

    /**
     * <p>Get the state.</p>
     *
     * @return possible null state
     */
    public State getState() {
        return this.state;
    }

    /**
     * <p>Set the state.</p>
     *
     * <p>
     * If the given value is valid and does not equal the value previously set, this address will be marked as changed.
     * </p>
     *
     * @param state non null state
     *
     * @throws IllegalArgumentException if the state is null
     */
    public void setState(State state) {
        Helper.validateNotNullWithIAE(state, "State of Address");

        if (!state.equals(this.state)) {
            this.setChanged(true);
        }

        this.state = state;
    }

    /**
     * <p>Get the associated address type.</p>
     *
     * @return address type, possible null if this is an initial <code>Address</code>
     *         or it is not associated with any entity.
     */
    public AddressType getAddressType() {
        return this.addressType;
    }

    /**
     * <p>Set the related address types.</p>
     *
     * <p>
     * If the given value is valid and does not equal the value previously set, this address will be marked as changed.
     * </p>
     *
     * @param addressType non null address type
     *
     * @throws IllegalArgumentException if the given address type is null
     */
    public void setAddressType(AddressType addressType) {
        Helper.validateNotNullWithIAE(addressType, "AddressType of Address");

        if (!addressType.equals(this.addressType)) {
            this.setChanged(true);
        }

        this.addressType = addressType;
    }
}
