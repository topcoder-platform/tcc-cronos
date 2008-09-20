/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence.client.impl;

import com.atlassian.confluence.rpc.RemoteException;
import com.atlassian.confluence.rpc.soap.beans.RemoteUser;
import com.atlassian.confluence.rpc.soap.confluenceservice_v1.ConfluenceSoapServiceProxy;
import com.topcoder.confluence.client.ConfluenceAddUserService;
import com.topcoder.confluence.client.ConfluenceClientServiceException;
import com.topcoder.confluence.client.RemoteServiceException;
import com.topcoder.confluence.client.UnknownGroupException;
import com.topcoder.confluence.client.UserAlreadyExistsException;

/**
 * Adds a new user in Confluence using Confluence's SOAP service.
 * 
 * @author romanoTC
 * @version 1.0
 */
public class ConfluenceAddUserServiceImpl implements ConfluenceAddUserService {
    /**
     * Creates a new user in Confluence.
     * 
     * @param endPoint a URL of the SOAP service.
     * @param adminUserName the name of the user that has permission to create other users.
     * @param adminPassword the password of the admin user.
     * @param userName the login of the user that is going to be created..
     * @param password the new user's password.
     * @param fullName the full name of the user that is going to be created.
     * @param email the e-mail of the user that is going to be created.
     * @param groupsNames default groups to add the user to.
     * @throws RemoteServiceException any exception thrown by Confluence is encapsulated in this exception.
     * @throws UserAlreadyExistsException if the user already exists in Confluence.
     * @throws UnknownGroupException if any given group is not present in Confluence.
     * @throws ConfluenceClientServiceException if any other error occurs while creating the user (such as a
     *             RemoteException).
     */
    public void createUser(String endPoint, String adminUserName, String adminPassword, String userName,
        String password, String fullName, String email, String[] groupsNames)
        throws ConfluenceClientServiceException {

        ConfluenceSoapServiceProxy proxy = new ConfluenceSoapServiceProxy(endPoint);

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
                if (proxy.hasUser(token, userName)) {
                    throw new UserAlreadyExistsException("User [" + userName + "] already exists");
                }

                if (groupsNames != null) {
                    // Check if the given groups exist
                    for (int i = 0; i < groupsNames.length; ++i) {
                        if (!proxy.hasGroup(token, groupsNames[i])) {
                            throw new UnknownGroupException("Group [" + groupsNames[i] + "] does not exist");
                        }
                    }
                }

                RemoteUser newUser = new RemoteUser();
                newUser.setName(userName);
                newUser.setFullname(fullName);
                newUser.setEmail(email);

                // If user does not exist, let’s create one
                proxy.addUser(token, newUser, password);

                // Let's add the user to the given groups
                if (groupsNames != null) {
                    for (int i = 0; i < groupsNames.length; ++i) {
                        proxy.addUserToGroup(token, userName, groupsNames[i]);
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
            throw new ConfluenceClientServiceException("A connection problem occurred", ex);
        }
    }
}
