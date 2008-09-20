/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.jira.client.impl;

import com.atlassian.jira.rpc.exception.RemoteException;
import com.atlassian.jira.rpc.soap.beans.RemoteGroup;
import com.atlassian.jira.rpc.soap.beans.RemoteUser;
import com.atlassian.jira.rpc.soap.jirasoapservice_v2.JiraSoapServiceProxy;
import com.topcoder.jira.client.JiraAddUserService;
import com.topcoder.jira.client.JiraClientServiceException;
import com.topcoder.jira.client.RemoteServiceException;
import com.topcoder.jira.client.UnknownGroupException;
import com.topcoder.jira.client.UserAlreadyExistsException;

/**
 * Adds a new user in Jira using Jira's SOAP service.
 * 
 * @author romanoTC
 * @version 1.0
 */
public class JiraAddUserServiceImpl implements JiraAddUserService {
    /**
     * Creates a new user in Jira.
     * 
     * @param endPoint a URL of the SOAP service.
     * @param adminUserName the name of the user that has permission to create other users.
     * @param adminPassword the password of the admin user.
     * @param userName the login of the user that is going to be created..
     * @param password the new user's password.
     * @param fullName the full name of the user that is going to be created.
     * @param email the e-mail of the user that is going to be created.
     * @param groupsNames default groups to add the user to.
     * @throws RemoteServiceException any exception thrown by Jira is encapsulated in this exception.
     * @throws UserAlreadyExistsException if the user already exists in Jira.
     * @throws UnknownGroupException if any given group is not present in Jira.
     * @throws JiraClientServiceException if any other error occurs while creating the user (such as a
     *             RemoteException).
     */
    public void createUser(String endPoint, String adminUserName, String adminPassword, String userName,
        String password, String fullName, String email, String[] groupsNames) throws JiraClientServiceException {

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
                // Check if user already exists
                RemoteUser user = proxy.getUser(token, userName);
                if (user == null) {
                    throw new UserAlreadyExistsException("User [" + userName + "] already exists");
                }

                RemoteGroup[] groups = null;
                if (groupsNames != null) {
                    groups = new RemoteGroup[groupsNames.length];

                    // Check if the given groups exist
                    for (int i = 0; i < groupsNames.length; ++i) {
                        groups[i] = proxy.getGroup(token, groupsNames[i]);

                        if (groups[i] == null) {
                            throw new UnknownGroupException("Group [" + groupsNames[i] + "] does not exist");
                        }
                    }
                }

                // If user does not exist, let’s create one
                RemoteUser newUser = proxy.createUser(token, userName, password, fullName, email);

                if (groups != null) {
                    // Let's add the user to the given groups
                    for (int i = 0; i < groups.length; ++i) {
                        proxy.addUserToGroup(token, groups[i], newUser);
                    }
                }
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
