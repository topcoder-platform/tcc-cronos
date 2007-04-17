/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact;

import com.topcoder.timetracker.common.TimeTrackerBean;


/**
 * <p>
 * This class holds the information of an entry in table <em>country_name</em>.
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
public class Country extends TimeTrackerBean {
    /**
     * <p>
     * Serial Version UID.
     * </p>
     */
    private static final long serialVersionUID = 8050364302087320016L;

    /**
     * <p>
     * Represents the name of the country. This variable is set to null initially, is mutable.
     * It is only allowed to be set to non null, non empty string with length &lt;= 64 by the setter.
     * It is access by its getter and setter methods.
     * </p>
     */
    private String name = null;

    /**
     * <p>Empty constructor of the <code>Country</code>.</p>
     */
    public Country() {
        // does nothing
    }

    /**
     * <p>Get the name.</p>
     *
     * @return possible null, non empty name with length &lt;= 64
     */
    public String getName() {
        return this.name;
    }

    /**
     * <p>Set the name.</p>
     *
     * <p>
     * If the given value is valid and does not equal the value previously set, this country will be marked as changed.
     * </p>
     *
     * @param name non null, non empty name
     *
     * @throws IllegalArgumentException if the name is null or empty(trim'd)
     */
    public void setName(String name) {
        Helper.validateStringWithIAE(name, "Name of Country");
        if (!name.equals(this.name)) {
            this.setChanged(true);
        }
        this.name = name;
    }
}
