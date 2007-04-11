/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.timetracker.user;

/**
 * <p>
 * This is an enumeration that is used to distinguish between the different types of Status that may
 * be assigned to a user. At the moment, ACTIVE, INACTIVE and LOCKED status may be assigned.
 * </p>
 *
 * <p>
 * Thread Safety: This clas is an enum, and therefore immutable and thread-safe.
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 3.1
 */
public class Status extends com.topcoder.util.collection.typesafeenum.Enum {

    /**
     * <p>
     * This is an Enumeration value representing a Status of ACTIVE.
     * </p>
     */
    public static final Status ACTIVE = new Status("active", 1);

    /**
     * <p>
     * This is an Enumeration value representing a Status of INACTIVE.
     * </p>
     */
    public static final Status INACTIVE = new Status("inactive", 0);

    /**
     * <p>
     * This is an Enumeration value representing a Status of LOCKED.
     * </p>
     */
    public static final Status LOCKED = new Status("locked", 2);

    /**
     * <p>
     * This is a name that briefly describes the user status.
     * </p>
     * <p>
     * Initial Value: Null
     * </p>
     * <p>
     * Accessed In: getName, toString
     * </p>
     * <p>
     * Modified In: Not Modified
     * </p>
     * <p>
     * Utilized In: Not Utilized
     * </p>
     * <p>
     * Valid Values: Nulls, or Strings that are not empty
     * </p>
     */
    private final String name;

    /**
     * <p>
     * This is an identifier that represents the unique id of the Status. It should also be used to
     * identify the persistence record if applicable.
     * </p>
     */
    private final long id;

    /**
     * <p>
     * Private constructor for a status, accepting a name and identifier argument. No exceptions are
     * thrown, since this is called privately.
     * </p>
     *
     * @param name The name of the status.
     * @param id The id of the status.
     */
    private Status(String name, long id) {
        this.name = name;
        this.id = id;
    }

    /**
     * <p>
     * Retrieves the name of the user status.
     * </p>
     *
     * @return the name of the user status.
     */
    public String getName() {
        return this.getName();
    }

    /**
     * <p>
     * Retrieves the String representation of the Status, which is equivalent to it's name.
     * </p>
     *
     * @return the name of the user status.
     */
    public String toString() {
        return this.name;
    }

    /**
     * <p>
     * Retrieves the identifier that represents the unique id of the Status. It should also be used
     * to identify the persistence record if applicable.
     * </p>
     *
     * @return The id of the Status.
     */
    public long getId() {
        return this.id;
    }
}
