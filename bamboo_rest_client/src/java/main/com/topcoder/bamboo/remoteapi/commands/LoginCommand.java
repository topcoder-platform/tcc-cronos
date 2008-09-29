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
public class LoginCommand extends AbstractCommand {

    /**
     * Uses the remote api to login to the bamboo server
     * 
     * @param serverUrl The URL of the bamboo server
     * @param username The username
     * @param password The password
     * @return The session authentication token used to access the api methods, null if login failed
     * @throws RemoteException
     */
    public String login(String serverUrl, String username, String password) throws RemoteException {
        NameValuePair usernameArg = new NameValuePair("username", username);
        NameValuePair passwordArg = new NameValuePair("password", password);

        String response = executePostMethod(serverUrl + "/api/rest/login.action", new NameValuePair[] {usernameArg,
            passwordArg});
        if (response == null) {
            return null;
        }

        return extractTagData("auth", response);
    }

    public static void main(String[] args) throws RemoteException {
        String serverUrl = "http://localhost:8080/bamboo";
        String username = "admin";
        String password = "admin123";

        LoginCommand cmd = new LoginCommand();

        String token = cmd.login(serverUrl, username, password);

        System.out.println(token);

        HasUserCommand hasUserCmd = new HasUserCommand();
        HasGroupCommand hasGroupCmd = new HasGroupCommand();
        AddUserCommand addUserCmd = new AddUserCommand();
        AddUserToGroupCommand addUserToGroupCmd = new AddUserToGroupCommand();

        String newUserName = "gustavo11";

        System.out.println("1: " + hasUserCmd.hasUser(serverUrl, token, username));
        System.out.println("2: " + hasUserCmd.hasUser(serverUrl, token, "nocivo"));
        System.out.println("3: " + hasUserCmd.hasUser(serverUrl, token, "dok"));
        System.out.println("4: " + hasUserCmd.hasUser(serverUrl, token, "romanoTC"));
        System.out.println("5: " + hasUserCmd.hasUser(serverUrl, token, "romanotc"));
        System.out.println("6: " + hasGroupCmd.hasGroup(serverUrl, token, "users"));
        System.out.println("7: " + hasGroupCmd.hasGroup(serverUrl, token, "bamboo-users"));
        System.out.println("8: " + hasUserCmd.hasUser(serverUrl, token, "imapr21"));
        System.out.println("9: "
            + addUserCmd.addUser(serverUrl, token, newUserName, "password123", "Nome Completo",
                "email@topcoder.com"));
        System.out.println("10: " + hasUserCmd.hasUser(serverUrl, token, "imapr21"));

        addUserToGroupCmd.addUserToGroup(serverUrl, token, newUserName, "bamboo-admin");
        addUserToGroupCmd.addUserToGroup(serverUrl, token, newUserName, "topcoder-staff");

        new LogoutCommand().login(serverUrl, token);
    }
}
