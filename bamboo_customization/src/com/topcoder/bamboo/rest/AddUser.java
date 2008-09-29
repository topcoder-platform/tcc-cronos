/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.bamboo.rest;

import com.atlassian.bamboo.ww2.BambooActionSupport;
import com.atlassian.bamboo.ww2.aware.RestActionAware;

/**
 * Returns the basic project details for a given key
 */
public class AddUser extends BambooActionSupport implements RestActionAware {
    private String username;

    private String password;

    private String fullName;

    private String email;

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

        if (password == null) {
            addActionError("You need to provide the password.");
            return ERROR;
        }

        getBambooUserManager().addUser(username, password, email, fullName);

        return SUCCESS;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(final String auth) {
        this.auth = auth;
    }

    /**
     * @return the userName
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * @param username the userName to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the fullName
     */
    public String getFullName() {
        return this.fullName;
    }

    /**
     * @param fullName the fullName to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
