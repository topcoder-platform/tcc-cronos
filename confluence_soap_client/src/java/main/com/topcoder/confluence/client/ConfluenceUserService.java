/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence.client;

import com.atlassian.confluence.rpc.soap.beans.RemoteUser;

/**
 * Client interface to manage users in Confluence.
 * 
 * @author romanoTC
 * @version 1.0
 */
public interface ConfluenceUserService {
    /**
     * Verifies if the given handle exists in Confluence.
     * 
     * @param endPoint a URL of the SOAP service.
     * @param adminUserName the name of the user that has permission to list other users.
     * @param adminPassword the password of the admin user.
     * @param handle the login of the user that is going to be verified.
     * @return <code>true</code> if userName already exists in Confluence, <code>false</code> otherwise.
     * @throws ConfluenceClientServiceException if any exception occurs while looking for the user.
     */
    public boolean hasUser(String endPoint, String adminUserName, String adminPassword, String handle)
        throws ConfluenceClientServiceException;

    /**
     * Returns an existing user in Confluence.
     * 
     * @param endPoint the URL of the SOAP service.
     * @param adminUserName the name of the user that has permission to list other users.
     * @param adminPassword the password of the admin user.
     * @param handle the login of the user that is going to be returned.
     * @return the user (or null if not found).
     * @throws ConfluenceClientServiceException if any exception occurs while looking for the user.
     */
    public RemoteUser getUser(String endPoint, String adminUserName, String adminPassword, String handle)
        throws ConfluenceClientServiceException;

    /**
     * Creates a new user in Confluence.
     * 
     * @param endPoint the URL of the SOAP service.
     * @param adminUserName the name of the user that has permission to create other users.
     * @param adminPassword the password of the admin user.
     * @param handle the login of the user that is going to be created..
     * @param fullName the full name of the user that is going to be created.
     * @param email the e-mail of the user that is going to be created.
     * @throws ConfluenceClientServiceException if any exception occurs while creating the user.
     */
    public void createUser(String endPoint, String adminUserName, String adminPassword, String handle,
        String fullName, String email) throws ConfluenceClientServiceException;
}
