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
    /**
     * The username being verified.
     */
    private String username;

    /**
     * A String holding "true" or "false" whether the user exists or not.
     */
    private String found;

    /**
     * The authentication token.
     */
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

    /**
     * Returns the username.
     * 
     * @return the username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username
     * 
     * @param username the username.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns "true" or "false" whether the user exists or not.
     * 
     * @return "true" or "false" whether the user exists or not.
     */
    public String getFound() {
        return this.found;
    }

    /**
     * Sets "true" or "false" whether the user exists or not.
     * 
     * @param found "true" or "false" whether the user exists or not.
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
