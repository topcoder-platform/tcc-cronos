/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.timetracker.common;

/**
 * <p>
 * This bean represents a State where an Address could be located. Some examples could be the state of Texas,
 * Illinois, etc.
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
public class State extends TimeTrackerBean {

    /**
     * <p>
     * The full name of the State. It may be null when the State object is initially constructed, but it may not be
     * set to a null or empty String afterwards.
     * </p>
     * <p>
     * Initialized In: setName
     * </p>
     * <p>
     * Modified In: setName
     * </p>
     * <p>
     * Accessed In: getName
     * </p>
     *
     *
     */
    private String name;

    /**
     * <p>
     * The abbreviated form of the State. It may be null when the State object is initially constructed, but it may
     * not be set to a null or empty String afterwards.
     * </p>
     * <p>
     * Initialized In: setAbbreviation
     * </p>
     * <p>
     * Modified In: setAbbreviation
     * </p>
     * <p>
     * Accessed In: getAbbreviation
     * </p>
     *
     */
    private String abbreviation;

    /**
     * <p>
     * Default constructor.
     * </p>
     *
     */
    public State() {
        // your code here
    }

    /**
     * <p>
     * Retrieves the full name of the State. It may be null when the State object is initially constructed, but it
     * may not be set to a null or empty String afterwards.
     * </p>
     *
     *
     * @return the full name of the State.
     */
    public String getName() {
        return name;
    }

    /**
     * <p>
     * Sets the full name of the State. It may be null when the State object is initially constructed, but it may
     * not be set to a null or empty String afterwards.
     * </p>
     * <p>
     * Implementation Notes: - If the current value that was added is not equal to the previous value, then call
     * setChanged(true).
     * </p>
     *
     *
     * @param name the full name of the State.
     * @throws IllegalArgumentException if the name is null or an empty String.
     */
    public void setName(String name) {
        Utils.checkString(name, "name", false);
        if (!name.equals(this.name)) {
            this.name = name;
            setChanged(true);
        }
    }

    /**
     * <p>
     * Retrieves the abbreviated form of the State. It may be null when the State object is initially constructed,
     * but it may not be set to a null or empty String afterwards.
     * </p>
     *
     *
     * @return the abbreviated form of the State.
     */
    public String getAbbreviation() {
        return abbreviation;
    }

    /**
     * <p>
     * Sets the abbreviated form of the State. It may be null when the State object is initially constructed, but
     * it may not be set to a null or empty String afterwards.
     * </p>
     * <p>
     * Implementation Notes: - If the current value that was added is not equal to the previous value, then call
     * setChanged(true).
     * </p>
     *
     *
     * @param abbreviation the abbreviated form of the State.
     * @throws IllegalArgumentException if abbreviation is a null or empty String.
     */
    public void setAbbreviation(String abbreviation) {
        Utils.checkString(abbreviation, "abbreviation", false);
        if (!abbreviation.equals(this.abbreviation)) {
            this.abbreviation = abbreviation;
            setChanged(true);
        }
    }
}
