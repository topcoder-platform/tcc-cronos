/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rates;

import com.topcoder.timetracker.common.TimeTrackerBean;
import com.topcoder.timetracker.company.Company;

/**
 * Bean class that stores information about a Rate within a Time Tracker application. This extends the
 * TimeTrackerBean base, so contains the common ID as well as creation and modification information. In addition to
 * this, information is stored containing the description of the rate, the value the rate takes, as well as a company
 * the rate is for. As it is a Bean class, it has a no-argument constructor and all members have appropriately named
 * get/set methods. In addition, no parameter checking is performed, the validation of parameters is left to the web
 * application. In the interest of speed and simplicity, these beans do not handle their thread safety - concurrent
 * access and modification is allowed. It is assumed that if required, Rate instances will be handled in a thread-safe
 * manner.
 *
 * @author AleaActaEst, sql_lall, TCSDEVELOPER
 * @version 3.2
 */
public class Rate extends TimeTrackerBean {

    /**
     * Automatically generated unique ID for use with serialization.
     */
    private static final long serialVersionUID = 7474935044046843623L;

    /**
     * The company instance that the rate is for - This is a Company instance (can be null), accessed with its
     * getter method. It is initially null, and can be changed to any Company instance or null by calling its setter
     * method.
     */
    private Company company;

    /**
     * The string description of the rate - This is a mutable String (can be null or empty), accessed with its
     * getter method. It is initially null, and can be changed to any String or null by calling its setter method.
     */
    private String description;

    /**
     * The current value of the rate - This is a mutable double, accessed with its getter method. It is
     * initially 0, and can be changed to any double by calling its setter method.
     */
    private double rate;

    /**
     * Empty no-arg constructor, does nothing.
     */
    public Rate() {
        //does nothing
    }

    /**
     * Returns the company instance this rate is connected to.
     *
     * @return this.company
     */
    public Company getCompany() {
        return this.company;
    }

    /**
     * Returns a description of what the rate is used for.
     *
     * @return this.description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns the current value of the rate.
     *
     * @return return this.rate
     */
    public double getRate() {
        return this.rate;
    }

    /**
     * Associates the given company to the rate.
     *
     * @param company The new company to link to the rate.
     */
    public void setCompany(Company company) {
        this.company = company;
        setChanged(true);
    }

    /**
     * Sets the description of the rate to a new message.
     *
     * @param desc new description to use.
     */
    public void setDescription(String desc) {
        this.description = desc;
        setChanged(true);
    }

    /**
     * Sets the new value of the rate.
     *
     * @param rate new rate value to use
     */
    public void setRate(double rate) {
        this.rate = rate;
        setChanged(true);
    }
}
