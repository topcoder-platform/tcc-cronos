/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import com.topcoder.util.collection.typesafeenum.Enum;


/**
 * <p>
 * This is an enumeration class, which lists possible JNDI lookup designations.
 * Currently we can only lookup local or remote interfaces.
 * This is thread-safe by its read-only virtue.
 * This is used when designationg how a JNDI looup string should be utilized.
 * This bascially allows us to decide if we are going to looup a local interface or a remote interface.
 * </p>
 *
 * @author AleaActaEst, TCSDEVELOPER
 * @version 1.0
 */
public class JndiLookupDesignation extends Enum {
    /**
     * <p>Represents a remote interface designation for ejb lookup.</p>
     *
     */
    public static final JndiLookupDesignation REMOTE = new JndiLookupDesignation(
            "Remote");

    /**
     * <p>Represents a local interface designation for ejb lookup.</p>
     *
     */
    public static final JndiLookupDesignation LOCAL = new JndiLookupDesignation(
            "Local");

    /**
     * <p>
     * Represents the name associated with the lookup. This is set through the constructor,
     * but is always set statically and is immutable after that.
     * </p>
     *
     */
    private final String name;

    /**
     * <p> Private constructor used to initialize statically created instances of this class.
     * Simply set the this.name to name.
     * </p>
     *
     * @param name name of the designation
     */
    private JndiLookupDesignation(String name) {
        this.name = name;
    }

    /**
     * <p>
     * Returns the current designation used.
     * This will currently be <code>Local</code> or <code>Remote</code>.
     * </p>
     *
     * @return current name for the designation
     */
    public String getName() {
        return name;
    }

    /**
     * <p>
     * Returns the current designation used.
     * This will currently be <code>Local</code> or <code>Remote</code>.
     * </p>
     *
     * @return current name for the designation
     */
    public String toString() {
        return name;
    }
}
