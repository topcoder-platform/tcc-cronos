/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.timetracker.common;

/**
 * <p>
 * This bean represents a possible <code>Address</code> where a different
 * entity may be situated in. It may represent the address of a
 * {@link com.topcoder.timetracker.user.User User} or
 * {@link com.topcoder.timetracker.company.Company Company}.
 * </p>
 * <p>
 * <b>Thread Safety</b>: <br/> This class is mutable, and not thread-safe.
 * Multiple threads are advised to work with their own instance.
 * </p>
 *
 * @author ShindouHikaru
 * @author kr00tki
 * @version 2.0
 */
public class Address extends TimeTrackerBean {

    /**
     * <p>
     * This is the first line of the Street address. It may be null when the
     * Address object is initially constructed, but it may not be set to a null
     * or empty String afterwards.
     * </p>
     * <p>
     * Initialized In: setLine1
     * </p>
     * <p>
     * Modified In: setLine1
     * </p>
     * <p>
     * Accessed In: getLine1
     * </p>
     */
    private String line1;

    /**
     * <p>
     * This is the second line of the Street address. It may be null as this is
     * an optional property.
     * </p>
     * <p>
     * Initialized In: setLine2
     * </p>
     * <p>
     * Modified In: setLine2
     * </p>
     * <p>
     * Accessed In: getLine2
     * </p>
     */
    private String line2;

    /**
     * <p>
     * This is the City where the address is located. It may be null when the
     * Address object is initially constructed, but it may not be set to a null
     * or empty String afterwards.
     * </p>
     * <p>
     * Initialized In: setCity
     * </p>
     * <p>
     * Modified In: setCity
     * </p>
     * <p>
     * Accessed In: getCity
     * </p>
     */
    private String city;

    /**
     * <p>
     * This is the State where the address is located. It may be null when the
     * Address object is initially constructed, but it may not be set to a null
     * afterwards .
     * </p>
     * <p>
     * Initialized In: setState
     * </p>
     * <p>
     * Modified In: setState
     * </p>
     * <p>
     * Accessed In: getState
     * </p>
     */
    private State state;

    /**
     * <p>
     * This is the zip code of the address. It may be null when the
     * Address object is initially constructed, but it may not be set to a null
     * or empty String afterwards.
     * </p>
     * <p>
     * Initialized In: setZipCode
     * </p>
     * <p>
     * Modified In: setZipCode
     * </p>
     * <p>
     * Accessed In: getZipCode
     * </p>
     */
    private String zipCode;

    /**
     * <p>
     * Default constructor.
     * </p>
     */
    public Address() {
        // your code here
    }

    /**
     * <p>
     * Retrieves the first line of the Street address. It may be null when the
     * Address object is initially constructed, but it may not be set to a null
     * or empty String afterwards.
     * </p>
     *
     * @return the first line of the Street address.
     */
    public String getLine1() {
        return line1;
    }

    /**
     * <p>
     * Sets the first line of the Street address. It may be <code>null</code>
     * when the Address object is initially constructed, but it may not be set
     * to a <code>null</code> or empty String afterwards.
     * </p>
     *
     * @param line1 the first line of the Street address.
     * @throws IllegalArgumentException if line1 is an empty String or
     *         <code>null</code>.
     */
    public void setLine1(String line1) {
        Utils.checkString(line1, "line1", false);
        if (!line1.equals(this.line1)) {
            this.line1 = line1;
            setChanged(true);
        }
    }

    /**
     * <p>
     * Retrieves the second line of the Street address. It may be null as this
     * is an optional property.
     * </p>
     *
     * @return the second line of the Street address.
     */
    public String getLine2() {
        return line2;
    }

    /**
     * <p>
     * Sets the second line of the Street address. It may be null as this is an
     * optional property.
     * </p>
     * <p>
     * Implementation Notes: - If the current value that was added is not equal
     * to the previous value, then call setChanged(true).
     * </p>
     *
     * @param line2 the second line of the Street address.
     * @throws IllegalArgumentException if line2 is an empty String.
     */
    public void setLine2(String line2) {
        Utils.checkString(line2, "line2", true);
        if (!(((line2 != null) && (this.line2 != null) && line2.equals(this.line2) || line2 == this.line2))) {
            this.line2 = line2;
            setChanged(true);
        }

    }

    /**
     * <p>
     * Retrieves the City where the address is located. It may be null when the
     * Address object is initially constructed, but it may not be set to a null
     * or empty String afterwards.
     * </p>
     *
     * @return the City where the address is located.
     */
    public String getCity() {
        return city;
    }

    /**
     * <p>
     * Sets the City where the address is located. It may be null when the
     * Address object is initially constructed, but it may not be set to a null
     * or empty String afterwards.
     * </p>
     * <p>
     * Implementation Notes: - If the current value that was added is not equal
     * to the previous value, then call setChanged(true).
     * </p>
     *
     * @param city the City where the address is located.
     * @throws IllegalArgumentException if the city is an empty String or null.
     */
    public void setCity(String city) {
        Utils.checkString(city, "city", false);
        if (!city.equals(this.city)) {
            this.city = city;
            setChanged(true);
        }
    }

    /**
     * <p>
     * Retrieves the State where the address is located. It may be null when the
     * Address object is initially constructed, but it may not be set to a null
     * afterwards .
     * </p>
     *
     * @return the State where the address is located.
     */
    public State getState() {
        return state;
    }

    /**
     * <p>
     * Sets the State where the address is located. It may be null when the
     * Address object is initially constructed, but it may not be set to a null
     * afterwards .
     * </p>
     * <p>
     * Implementation Notes: - If the current value that was added is not equal
     * to the previous value, then call setChanged(true).
     * </p>
     *
     * @param state The state where the address is located.
     * @throws IllegalArgumentException if state is null.
     */
    public void setState(State state) {
        Utils.checkNull(state, "state");
        if (!state.equals(this.state)) {
            this.state = state;
            setChanged(true);
        }
    }

    /**
     * <p>
     * Returns the zip code of the address. It may be <code>null</code> if the
     * value was not set, but it won't be <code>null</code> afterwards.
     * </p>
     *
     * @return the zip code value or <code>null</code>, it wasn't set yet.
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * <p>
     * Sets the zip code for the <code>Address</code>. The value cannot be
     * <code>null</code> or empty String. If is different than the previous,
     * the <code>changed</code> flag will be set.
     * </p>
     *
     * @param zipCode the addresse's zip code.
     */
    public void setZipCode(String zipCode) {
        Utils.checkString(zipCode, "zipCode", false);
        if (!zipCode.equals(this.zipCode)) {
            this.zipCode = zipCode;
            setChanged(true);
        }
    }

}
