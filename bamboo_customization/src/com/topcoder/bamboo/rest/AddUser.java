/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.bamboo.rest;

import com.atlassian.bamboo.ww2.BambooActionSupport;
import com.atlassian.bamboo.ww2.aware.RestActionAware;

/**
 * Adds a new user.
 * 
 * @author romanoTC
 * @version 1.0
 */
public class AddUser extends BambooActionSupport implements RestActionAware {
    /**
     * The new username.
     */
    private String username;

    /**
     * The user's password.
     */
    private String password;

    /**
     * The user's full name.
     */
    private String fullName;

    /**
     * The user's email.
     */
    private String email;

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

        if (password == null) {
            addActionError("You need to provide the password.");
            return ERROR;
        }

        getBambooUserManager().addUser(username, password, email, fullName);

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
     * Returns the username.
     * 
     * @return the username.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Sets the username.
     * 
     * @param username the username.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the user's password.
     * 
     * @return the user's password.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Sets the user's password.
     * 
     * @param password the user's password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the user's full name.
     * 
     * @return the user's full name.
     */
    public String getFullName() {
        return this.fullName;
    }

    /**
     * Sets the user's full name.
     * 
     * @param fullName the user's full name.
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Returns the user's email.
     * 
     * @return the user's email.
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Sets the user's email.
     * 
     * @param email the user's email.
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
