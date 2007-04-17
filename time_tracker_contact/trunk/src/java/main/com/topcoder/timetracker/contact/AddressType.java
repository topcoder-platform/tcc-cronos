/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact;

import com.topcoder.util.collection.typesafeenum.Enum;


/**
 * <p>
 * This enumeration represents the address type:
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
public class AddressType extends Enum {

    /**
     * <p>
     * Represents the project address type. Its id is fixed to be 1.
     * </p>
     */
    public static final AddressType PROJECT = new AddressType("PROJECT");

    /**
     * <p>
     * Represents the client address type. Its id is fixed to be 2.
     * </p>
     */
    public static final AddressType CLIENT = new AddressType("CLIENT");

    /**
     * <p>
     * Represents the company address type. Its id is fixed to be 3.
     * </p>
     */
    public static final AddressType COMPANY = new AddressType("COMPANY");

    /**
     * <p>
     * Represents the user address type. Its id is fixed to be 4.
     * </p>
     */
    public static final AddressType USER = new AddressType("USER");

    /**
     * <p>
     * Serial Version UID.
     * </p>
     */
    private static final long serialVersionUID = -4211440822273309332L;

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
    private AddressType(String type) {
        this.type = type;
    }

    /**
     * <p>Get the id.</p>
     *
     * @return positive id of the type count from 1 to 4.
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
