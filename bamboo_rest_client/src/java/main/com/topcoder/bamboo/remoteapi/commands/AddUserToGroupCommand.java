/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.bamboo.remoteapi.commands;

import org.apache.commons.httpclient.NameValuePair;

import com.topcoder.bamboo.remoteapi.RemoteException;

/**
 * This class uses the Bamboo rest api to logonto a Bamboo server.
 * 
 * 
 */
public class AddUserToGroupCommand extends AbstractRESTCommand {

    /**
     * Uses the remote api to login to the bamboo server
     * 
     * @param serverUrl The URL of the bamboo server
     * @param username The username
     * @param password The password
     * @return The session authentication token used to access the api methods, null if login failed
     * @throws RemoteException
     */
    public String addUserToGroup(String serverUrl, String token, String username, String group)
        throws RemoteException {

        NameValuePair usernameArg = new NameValuePair("username", username);
        NameValuePair groupArg = new NameValuePair("group", group);

        String response = executePostMethod(serverUrl + "/api/rest/addusertogroup.action", new NameValuePair[] {
            getAuthArg(token), usernameArg, groupArg});

        return extractTagData("username", response);
    }
}
