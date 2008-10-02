/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.bamboo.rest;

import com.atlassian.bamboo.ww2.BambooActionSupport;
import com.atlassian.bamboo.ww2.aware.RestActionAware;

/**
 * Adds an user to a given group.
 * 
 * @author romanoTC
 * @version 1.0
 */
public class AddUserToGroup extends BambooActionSupport implements RestActionAware {
    /**
     * The user being added to the group.
     */
    private String username;

    /**
     * The group to add the user.
     */
    private String group;

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
        if (username == null) {
            addActionError("You need to provide the username.");
            return ERROR;
        }

        if (group == null) {
            addActionError("You need to provide the group.");
            return ERROR;
        }

        getBambooUserManager().addMembership(group, username);

        return SUCCESS;
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

    /**
     * Returns the username of the user being added to the group.
     * 
     * @return the username of the user being added to the group.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Sets the username of the user being added to the group.
     * 
     * @param username the username of the user being added to the group.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the group to add the user.
     * 
     * @return the group to add the user.
     */
    public String getGroup() {
        return this.group;
    }

    /**
     * Sets the group to add the user.
     * 
     * @param group the group to add the user.
     */
    public void setGroup(String group) {
        this.group = group;
    }

}
