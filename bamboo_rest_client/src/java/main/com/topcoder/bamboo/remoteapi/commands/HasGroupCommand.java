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
public class HasGroupCommand extends AbstractCommand {

    /**
     * Uses the remote api to login to the bamboo server
     * 
     * @param serverUrl The URL of the bamboo server
     * @param username The username
     * @param password The password
     * @return The session authentication token used to access the api methods, null if login failed
     * @throws RemoteException
     */
    public boolean hasGroup(String serverUrl, String token, String group) throws RemoteException {
        NameValuePair usernameArg = new NameValuePair("group", group);

        String response = executePostMethod(serverUrl + "/api/rest/hasgroup.action", new NameValuePair[] {
            getAuthArg(token), usernameArg});

        return Boolean.valueOf(extractTagData("found", response)).booleanValue();
    }
}
