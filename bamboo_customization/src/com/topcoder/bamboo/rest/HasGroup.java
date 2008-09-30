/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.bamboo.rest;

import com.atlassian.bamboo.ww2.BambooActionSupport;
import com.atlassian.bamboo.ww2.aware.RestActionAware;

/**
 * Checks if a given group exists or not.
 * 
 * @author romanoTC
 * @version 1.0
 */
public class HasGroup extends BambooActionSupport implements RestActionAware {
    /**
     * The group being verified.
     */
    private String group;

    /**
     * A String holding "true" or "false" whether the group exists or not.
     */
    private String found;

    /**
     * The authentication token.
     */
    private String auth;

    /**
     * Execute the rest API call
     * 
     * @return The webwork status code
     * @throws Exception A generic failure
     */
    public String doExecute() throws Exception {
        if (group == null) {
            addActionError("You need to provide the group.");
            return ERROR;
        }

        found = Boolean.toString(getBambooUserManager().getGroup(group) != null);

        return SUCCESS;
    }

    /**
     * Returns the group name.
     * 
     * @return the group name.
     */
    public String getGroup() {
        return group;
    }

    /**
     * Sets the group name.
     * 
     * @param group the group name.
     */
    public void setGroup(String group) {
        this.group = group;
    }

    /**
     * Returns "true" or "false" whether the group exists or not.
     * 
     * @return "true" or "false" whether the group exists or not.
     */
    public String getFound() {
        return this.found;
    }

    /**
     * Sets "true" or "false" whether the group exists or not.
     * 
     * @param found "true" or "false" whether the group exists or not.
     */
    public void setFound(String found) {
        this.found = found;
    }

    /**
     * Returns the authentication token.
     * 
     * @return the authentication token.
     */
    public String getAuth() {
        return auth;
    }

    /**
     * Sets the authentication token.
     * 
     * @param auth the authentication token.
     */
    public void setAuth(String auth) {
        this.auth = auth;
    }
}
