/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

import com.topcoder.util.collection.typesafeenum.Enum;

/**
 * <p>
 * This is an enumeration that is used to distinguish between the different types of Status
 * that may be assigned to a user.
 * </p>
 *
 * <p>
 * At the moment, <code>ACTIVE</code>, <code>INACTIVE</code> and <code>LOCKED</code>
 * status may be assigned.
 * </p>
 *
 * <p>
 * Thread Safety: This class is an enum, and therefore immutable and thread-safe.
 * </p>
 *
 * @author ShindouHikaru, TCSDEVELOPER
 * @version 3.2
 */
public class Status extends Enum {

    /**
     * Automatically generated unique ID for use with serialization.
     */
    private static final long serialVersionUID = 8748057160854572375L;

    /**
     * This is an Enumeration value representing a status of <tt>ACTIVE</tt>.
     */
    public static final Status ACTIVE = new Status("Active", 1);

    /**
     * This is an Enumeration value representing a status of <tt>INACTIVE</tt>.
     */
    public static final Status INACTIVE = new Status("Inactive", 0);

    /**
     * This is an Enumeration value representing a status of <tt>LOCKED</tt>.
     */
    public static final Status LOCKED = new Status("Locked", 3);

    /**
     * This is a name that briefly describes the user status.
     * <p>
     * It is set in the constructor and never changed afterwards.
     * </p>
     */
    private final String name;

    /**
     * This is an identifier that represents the unique id of the <code>Status</code>.
     * <p>
     * It should also be used to identify the persistence record if applicable.
     * </p>
     */
    private final long id;

    /**
     * This is the private constructor for a status, accepting name and identifier arguments.
     *
     * @param name The name of the status.
     * @param id The id of the status.
     */
    private Status(String name, long id) {
        this.name = name;
        this.id = id;
    }

    /**
     * Retrieves the name of the user status.
     *
     * @return the name of the user status.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Retrieves the <code>String</code> representation of the Status, which is equivalent to it's name.
     *
     * @return the name of the user status.
     */
    public String toString() {
        return this.name;
    }

    /**
     * Retrieves the identifier that represents the unique id of the Status.
     * <p>
     * It should also be used to identify the persistence record if applicable.
     * </p>
     *
     * @return The id of the user status.
     */
    public long getId() {
        return this.id;
    }
}