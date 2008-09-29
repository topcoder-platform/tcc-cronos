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
    private String group;

    private String found;

    private String auth;

    /**
     * Execute the rest api call
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

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    /**
     * @return the found
     */
    public String getFound() {
        return this.found;
    }

    /**
     * @param found the found to set
     */
    public void setFound(String found) {
        this.found = found;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(final String auth) {
        this.auth = auth;
    }
}
