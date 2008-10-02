/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.bamboo.client.impl;

import com.topcoder.bamboo.client.BambooAddUserService;
import com.topcoder.bamboo.client.BambooClientServiceException;
import com.topcoder.bamboo.client.RemoteServiceException;
import com.topcoder.bamboo.client.UnknownGroupException;
import com.topcoder.bamboo.client.UserAlreadyExistsException;
import com.topcoder.bamboo.remoteapi.RemoteException;
import com.topcoder.bamboo.remoteapi.commands.AddUserCommand;
import com.topcoder.bamboo.remoteapi.commands.AddUserToGroupCommand;
import com.topcoder.bamboo.remoteapi.commands.HasGroupCommand;
import com.topcoder.bamboo.remoteapi.commands.HasUserCommand;
import com.topcoder.bamboo.remoteapi.commands.LoginCommand;
import com.topcoder.bamboo.remoteapi.commands.LogoutCommand;

/**
 * Adds a new user in Bamboo using Bamboo's REST service.
 * 
 * @author romanoTC
 * @version 1.0
 */
public class BambooAddUserServiceImpl implements BambooAddUserService {
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
     * @throws RemoteServiceException any exception thrown by Bamboo is encapsulated in this exception.
     * @throws UserAlreadyExistsException if the user already exists in Bamboo.
     * @throws UnknownGroupException if any given group is not present in Bamboo.
     * @throws BambooClientServiceException if any other error occurs while creating the user (such as a
     *             RemoteException).
     */
    public void createUser(String endPoint, String adminUserName, String adminPassword, String userName,
        String password, String fullName, String email, String[] groupsNames) throws BambooClientServiceException {

        String token;

        try {
            // Log as an administrator (who can create users)
            token = new LoginCommand().login(endPoint, adminUserName, adminPassword);
        } catch (RemoteException ex) {
            throw new RemoteServiceException("Unable to login", ex);
        }

        try {
            // Check if user already exists
            if (new HasUserCommand().hasUser(endPoint, token, userName)) {
                throw new UserAlreadyExistsException("User [" + userName + "] already exists");
            }

            if (groupsNames != null) {
                HasGroupCommand hasGroupCommand = new HasGroupCommand();

                // Check if the given groups exist
                for (int i = 0; i < groupsNames.length; ++i) {
                    if (!hasGroupCommand.hasGroup(endPoint, token, groupsNames[i])) {
                        throw new UnknownGroupException("Group [" + groupsNames[i] + "] does not exist");
                    }
                }
            }

            // If user does not exist, let’s create one
            new AddUserCommand().addUser(endPoint, token, userName, password, fullName, email);

            // Let's add the user to the given groups
            if (groupsNames != null) {
                AddUserToGroupCommand addUserToGroupCommand = new AddUserToGroupCommand();

                for (int i = 0; i < groupsNames.length; ++i) {
                    addUserToGroupCommand.addUserToGroup(endPoint, token, userName, groupsNames[i]);
                }
            }
        } catch (RemoteException ex) {
            throw new RemoteServiceException("A Jira remote error occurred", ex);
        } finally {
            // tries to logout if an exception occurs
            try {
                new LogoutCommand().logout(endPoint, token);
            } catch (RemoteException ex) {
                // ignores the exception
            }
        }
    }
}
