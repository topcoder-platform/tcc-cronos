/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.audit;

import com.topcoder.util.collection.typesafeenum.Enum;

/**
 * <p>
 * This class extends the Type Safe Enumeration component's Enum class, which contains the information about all the
 * application areas available for audits. Each enumeration is identifiable by its name (e.g. "Expense" / "Fixed
 * Billing"...) or its cardinal ID.
 * </p>
 *
 * <p>
 * This class has a private constructor, instances are available only as public static final members of the class - in
 * addition, each is immutable after construction.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>This class is thread safe by being immutable.
 * </p>
 *
 * @author sql_lall, bendlund, TCSDEVELOPER
 * @version 3.2
 */
public class ApplicationArea extends Enum {

	/**
	 * Automatically generated unique ID for use with serialization.
	 */
	private static final long serialVersionUID = -7898200254891183918L;

	/** Enumeration value for the "Expense" application area. */
    public static final ApplicationArea TT_EXPENSE = new ApplicationArea("Expense");

    /** Enumeration value for the "Fixed Billing" application area. */
    public static final ApplicationArea TT_FIXED_BILLING = new ApplicationArea("Fixed Billing");

    /** Enumeration value for the "Time" application area. */
    public static final ApplicationArea TT_TIME = new ApplicationArea("Time");

    /** Enumeration value for the "Client" application area. */
    public static final ApplicationArea TT_CLIENT = new ApplicationArea("Client");

    /** Enumeration value for the "Company" application area. */
    public static final ApplicationArea TT_COMPANY = new ApplicationArea("Company");

    /** Enumeration value for the "Project" application area. */
    public static final ApplicationArea TT_PROJECT = new ApplicationArea("Project");

    /** Enumeration value for the "User" application area. */
    public static final ApplicationArea TT_USER = new ApplicationArea("User");

    /** Enumeration value for the "Invoice" application area. */
    public static final ApplicationArea TT_INVOICE = new ApplicationArea("Invoice");

    /** Enumeration value for the "Notification" application area. */
    public static final ApplicationArea TT_NOTIFICATION = new ApplicationArea("Notification");

    /** Enumeration value for the "Configuration" application area. */
    public static final ApplicationArea TT_CONFIGURATION = new ApplicationArea("Configuration");

    /**
     * <p>
     * The descriptive name containing which application area the enumeration value describes.
     * </p>
     *
     * <p>
     * This is set during (private) construction, and immutable afterwards, obtained by calling getName.
     * </p>
     */
    private final String name;

    /**
     * <p>
     * Private enumeration constructor, which calls the Enum superconstructor, as well as initlializing the description
     * name for the enumeration.
     * </p>
     *
     * @param name the descriptive name.
     */
    private ApplicationArea(String name) {
        this.name = name;
    }

    /**
     * <p>
     * This returns the cardinal number of the enumeration constant(added by 1, as the cardinal number starts from 0),
     * converted to a long for use with a database.
     * </p>
     *
     * @return Cardinal ID(added by 1), as a long.
     */
    public long getId() {
        return this.getOrdinal() + 1;
    }

    /**
     * <p>
     * Gets the name of the area.
     * </p>
     *
     * @return the name of the area.
     */
    public String getName() {
        return this.name;
    }

    /**
     * <p>
     * Gets a string representation of the enumeration. The same value as getName().
     * </p>
     *
     * @return the name of the area.
     */
    public String toString() {
        return this.name;
    }
}
