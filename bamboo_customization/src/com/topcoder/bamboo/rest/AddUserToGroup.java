/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.bamboo.rest;

import com.atlassian.bamboo.ww2.BambooActionSupport;
import com.atlassian.bamboo.ww2.aware.RestActionAware;

/**
 * Returns the basic project details for a given key
 */
public class AddUserToGroup extends BambooActionSupport implements RestActionAware {
    private String username;

    private String group;

    private String auth;

    /**
     * Execute the rest api call
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

    public String getAuth() {
        return auth;
    }

    public void setAuth(final String auth) {
        this.auth = auth;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the group
     */
    public String getGroup() {
        return this.group;
    }

    /**
     * @param group the group to set
     */
    public void setGroup(String group) {
        this.group = group;
    }

}
