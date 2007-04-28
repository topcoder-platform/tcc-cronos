/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project;

import com.topcoder.util.collection.typesafeenum.Enum;

/**
 * <p>
 * This is an enumeration that is used to distinguish between the different types of Entries that
 * may be added to the project.
 * </p>
 *
 * <p>
 * At the moment, it supports <tt>Time</tt>, <tt>Expense</tt>, and <tt>Fixed Billing</tt> entries.
 * </p>
 *
 * <p>
 * Thread Safety: This class is an enum, and therefore immutable and thread-safe.
 * </p>
 *
 * @author ShindouHikaru, bendlund, TCSDEVELOPER
 * @version 3.2
 */
public class EntryType extends Enum {
    /**
     * <p>
     * This is an Enumeration value representing a <tt>Time</tt> Entry.
     * </p>
     */
    public static final EntryType TIME_ENTRY = new EntryType("time_entry");

    /**
     * <p>
     * This is an Enumeration value representing a <tt>Fixed Billing</tt> Entry.
     * </p>
     */
    public static final EntryType FIXED_BILLING_ENTRY = new EntryType("fixed_billing");

    /**
     * <p>
     * This is an Enumeration value representing an <tt>Expense</tt> Entry.
     * </p>
     */
    public static final EntryType EXPENSE_ENTRY = new EntryType("expense_entry");

    /**
     * <p>
     * This is a name that briefly describes the type of the entry.
     * </p>
     *
     * <p>
     * It is set in the constructor and never changed afterwards.
     * </p>
     */
    private final String name;

    /**
     * <p>
     * Private constructor to create an <code>EntryType</code>.
     * </p>
     *
     * @param name The name of the <code>EntryType</code>.
     */
    private EntryType(String name) {
        this.name = name;
    }

    /**
     * <p>
     * Retrieves the name of the <code>EntryType</code>.
     * </p>
     *
     * @return the name of the <code>EntryType</code>.
     */
    public String getName() {
        return name;
    }

    /**
     * <p>
     * Retrieves the <code>String</code> representation of the <code>EntryType</code>, which is
     * equivalent to it's name.
     * </p>
     *
     * @return the name of the <code>EntryType</code>.
     */
    public String toString() {
        return name;
    }
}
