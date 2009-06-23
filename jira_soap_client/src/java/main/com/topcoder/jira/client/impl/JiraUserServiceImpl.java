/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.jira.client.impl;

import com.atlassian.jira.rpc.exception.RemoteException;
import com.atlassian.jira.rpc.soap.beans.RemoteUser;
import com.atlassian.jira.rpc.soap.jirasoapservice_v2.JiraSoapServiceProxy;
import com.topcoder.jira.client.JiraUserService;
import com.topcoder.jira.client.JiraClientServiceException;
import com.topcoder.jira.client.RemoteServiceException;

/**
 * Manages users in Jira using Jira's SOAP service.
 * 
 * @author romanoTC
 * @version 1.0
 */
public class JiraUserServiceImpl implements JiraUserService {
    /**
     * Default empty constructor.
     */
    public JiraUserServiceImpl() {
        // Empty
    }

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
    public RemoteUser getUser(String endPoint, String adminUserName, String adminPassword, String handle)
        throws JiraClientServiceException {

        JiraSoapServiceProxy proxy = new JiraSoapServiceProxy(endPoint);

        String token;

        try {
            try {
                // Log as an administrator (who can create users)
                token = proxy.login(adminUserName, adminPassword);
            } catch (RemoteException ex) {
                throw new RemoteServiceException("Unable to login", ex);
            }

            try {
                // Return the user
                return proxy.getUser(token, handle);

            } catch (RemoteException ex) {
                throw new RemoteServiceException("A Jira remote error occurred", ex);

            } finally {
                // tries to logout if an exception occurs
                proxy.logout(token);
            }
        } catch (java.rmi.RemoteException ex) {
            // If this is a RMI exception, it is probably unrecoverable
            throw new JiraClientServiceException("A connection problem occurred", ex);
        }
    }
}
