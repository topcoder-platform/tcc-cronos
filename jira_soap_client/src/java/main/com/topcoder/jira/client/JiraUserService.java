/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.jira.client;

import com.atlassian.jira.rpc.soap.beans.RemoteUser;

/**
 * Client interface to manage users in Jira.
 * 
 * @author romanoTC
 * @version 1.0
 */
public interface JiraUserService {
    /**
     * Returns an existing user in Jira. TopCoder custom login module is responsible to automatically create an user
     * in Jira if the given handle is not present in Jira's database.
     * 
     * @param endPoint the URL of the SOAP service.
     * @param adminUserName the name of the user that has permission to list other users.
     * @param adminPassword the password of the admin user.
     * @param handle the login of the user that is going to be returned.
     * @return the user (or null if not found).
     * @throws JiraClientServiceException if any exception occurs while looking for the user.
     */
    public RemoteUser getUser(String endPoint, String adminUserName, String adminPassword, String userName)
        throws JiraClientServiceException;
}
