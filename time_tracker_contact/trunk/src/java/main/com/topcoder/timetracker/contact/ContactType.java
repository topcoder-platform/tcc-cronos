/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact;

import com.topcoder.util.collection.typesafeenum.Enum;


/**
 * <p>
 * This enumeration represents the contact type:
 *  <ul>
 *   <li>PROJECT</li>
 *   <li>CLIENT</li>
 *   <li>COMPANY</li>
 *   <li>USER</li>
 *  </ul>
 * </p>
 *
 * <p>
 *  <strong>Thread Safety:</strong>
 *  This class is thread-safe by being immutable.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 3.2
 */
public class ContactType extends Enum {
    /**
     * <p>
     * Represents the project contact type. Its id is fixed to be 1.
     * </p>
     */
    public static final ContactType PROJECT = new ContactType("PROJECT");

    /**
     * <p>
     * Represents the client contact type. Its id is fixed to be 2.
     * </p>
     */
    public static final ContactType CLIENT = new ContactType("CLIENT");

    /**
     * <p>
     * Represents the company contact type. Its id is fixed to be 3.
     * </p>
     */
    public static final ContactType COMPANY = new ContactType("COMPANY");

    /**
     * <p>
     * Represents the user contact type. Its id is fixed to be 4.
     * </p>
     */
    public static final ContactType USER = new ContactType("USER");

    /**
     * <p>
     * Serial Version UID.
     * </p>
     */
    private static final long serialVersionUID = -5121384344272620713L;

    /**
     * <p>
     * Represents the string representation of the type.
     * This variable is set in constructor, is immutable and non null, non empty.
     * It is referenced by the <code>toString()</code> method.
     * </p>
     */
    private final String type;

    /**
     * <p>Private constructor.</p>
     *
     * @param type the non null non empty string representing the type
     */
    private ContactType(String type) {
        this.type = type;
    }

    /**
     * <p>Get the id.</p>
     *
     * @return positive id of the type count from 1 to 4
     */
    public long getId() {
        return super.getOrdinal() + 1;
    }

    /**
     * <p>Get the string representation of the type.</p>
     *
     * @return the non null non empty string representing the type
     */
    public String toString() {
        return this.type;
    }
}
