/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.bamboo.client;

/**
 * Client interface to add users in Bamboo.
 * 
 * @author romanoTC
 * @version 1.0
 */
public interface BambooAddUserService {
    /**
     * Creates a new user in Bamboo.
     * 
     * @param endPoint a URL of the SOAP service.
     * @param adminUserName the name of the user that has permission to create other users.
     * @param adminPassword the password of the admin user.
     * @param userName the login of the user that is going to be created..
     * @param password the new user's password.
     * @param fullName the full name of the user that is going to be created.
     * @param email the e-mail of the user that is going to be created.
     * @param groupsNames default groups to add the user to.
     * @throws BambooClientServiceException if any exception occurs while creating the user.
     */
    public void createUser(String endPoint, String adminUserName, String adminPassword, String userName,
        String password, String fullName, String email, String[] groupsNames)
        throws BambooClientServiceException;
}
