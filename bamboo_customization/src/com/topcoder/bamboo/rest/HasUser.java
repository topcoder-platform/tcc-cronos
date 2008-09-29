/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.bamboo.rest;

import com.atlassian.bamboo.ww2.BambooActionSupport;
import com.atlassian.bamboo.ww2.aware.RestActionAware;

/**
 * Checks if a given user exists or not.
 * 
 * @author romanoTC
 * @version 1.0
 */
public class HasUser extends BambooActionSupport implements RestActionAware {
    private String username;

    private String found;

    private String auth;

    /**
     * Execute the rest API call.
     * 
     * @return The webwork status code.
     * @throws Exception A generic failure.
     */
    public String doExecute() throws Exception {
        if (username == null) {
            addActionError("You need to provide the username.");
            return ERROR;
        }

        found = Boolean.toString(getBambooUserManager().getBambooUser(username) != null);

        return SUCCESS;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
